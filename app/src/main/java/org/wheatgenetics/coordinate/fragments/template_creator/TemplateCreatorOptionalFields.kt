package org.wheatgenetics.coordinate.fragments.template_creator

import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckedTextView
import android.widget.ImageButton
import android.widget.ListView
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import org.wheatgenetics.coordinate.R
import org.wheatgenetics.coordinate.database.TemplatesTable
import org.wheatgenetics.coordinate.optionalField.AddOptionalFieldAlertDialog
import org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields

class TemplateCreatorOptionalFields : Fragment(R.layout.fragment_template_creator_optional_fields),
    AddOptionalFieldAlertDialog.Handler {

    private val args: TemplateCreatorOptionalFieldsArgs by navArgs()

    private var mTemplateTable: TemplatesTable? = null

    //fragment is attached to activity
    override fun onAttach(context: Context) {
        super.onAttach(context)
        //initialize templates table to query from
        activity?.let { act ->
            mTemplateTable = TemplatesTable(act)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                navigateBack()
            }
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // set toolbar back button
        setHasOptionsMenu(true)

        setupAdapter()
        setupButtons()
    }

    private fun setupButtons() {

        val okButton = view?.findViewById<Button>(R.id.frag_next_btn)
        val backButton = view?.findViewById<Button>(R.id.frag_back_btn)

        okButton?.setOnClickListener {
            writeToDatabase()
            findNavController().navigate(TemplateCreatorOptionalFieldsDirections
                .actionTemplateOptionalFieldsToTemplateNaming(args.title))
        }

        backButton?.setOnClickListener {
            navigateBack()
        }
    }

    private fun navigateBack() {
        writeToDatabase()
        findNavController().navigate(TemplateCreatorOptionalFieldsDirections
            .actionTemplateOptionalFieldsPop())
    }

    /**
     * Iterate over the list view ui and update the database row's optional fields "checked"
     * JSON value with the list item's checked boolean value.
     */
    private fun writeToDatabase() {

        val listView = view?.findViewById<ListView>(R.id.frag_template_creator_options_lv)

        mTemplateTable?.load()?.let { templates ->

            templates.find { it.title == args.title }?.let { template ->

                mTemplateTable?.update(template.apply {
                    with (this.optionalFields()) {
                        listView?.children?.forEachIndexed { index, view ->
                            this?.setChecked(index, (view as CheckedTextView).isChecked)
                        }
                    }
                })
            }
        }
    }

    private fun setupAdapter() {

        //button that will start an add optional fields fragment
        val addButton = view?.findViewById<ImageButton>(R.id.frag_template_creator_options_new_btn)

        activity?.let { act ->

            if (args.title.isNotBlank()) {

                //query db for optional fields
                mTemplateTable?.load()?.let { templates ->

                    templates.find { it.title == args.title }?.optionalFields()?.let { fields ->

                        addButton?.setOnClickListener {
                            AddOptionalFieldAlertDialog(act, this).show(fields)
                        }

                        val adapter = TemplateAdapter(act, fields)

                        val listView = view?.findViewById<ListView>(R.id.frag_template_creator_options_lv)

                        listView?.adapter = adapter

                        listView?.setOnItemClickListener { adapterView, view, i, l ->
                            with (view as CheckedTextView) {
                                if (this.text != getString(R.string.BaseOptionalFieldIdentificationFieldName)) {
                                    isChecked = !isChecked
                                }
                            }
                        }

                        adapter.addAll(*fields.names())
                        (listView?.adapter as? ArrayAdapter<*>)?.notifyDataSetChanged()
                    }
                }
            }
        }
    }

    private inner class TemplateAdapter(ctx: Context, private val fields: NonNullOptionalFields):
        ArrayAdapter<String>(ctx, android.R.layout.simple_list_item_checked) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view = super.getView(position, convertView, parent)
            with(view as? CheckedTextView) {
                this?.isChecked = fields.getChecked(position)
                if (this?.text == getString(R.string.BaseOptionalFieldIdentificationFieldName)) {
                    this.isEnabled = false
                }
                // Set the color of the check mark for selected item
                if (this?.isChecked == true) {
                    val color = ContextCompat.getColor(context, R.color.colorAccent)
                    this.checkMarkTintList = ColorStateList.valueOf(color)
                }
            }
            return view
        }
    }

    override fun handleAddOptionalFieldDone(name: String, defaultValue: String) {

        mTemplateTable?.load()?.let { templates ->

            templates.find { it.title == args.title }?.let { template ->

                mTemplateTable?.update(template.apply {
                    optionalFields()?.add(name, defaultValue, null)
                })
            }
        }

        setupAdapter()
    }
}