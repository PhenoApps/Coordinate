package org.wheatgenetics.coordinate.fragments.template_creator

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import org.wheatgenetics.coordinate.R
import org.wheatgenetics.coordinate.database.TemplatesTable

class TemplateCreatorRowNaming : Fragment(R.layout.fragment_template_creator_row_naming) {

    private val args: TemplateCreatorRowNamingArgs by navArgs()

    private var mTemplateTable: TemplatesTable? = null

    private var mSelectedEnumeration = AxisLabel.Numeric

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mTemplateTable = TemplatesTable(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()
        setupButtons()
    }

    private fun setupButtons() {
        val backButton = view?.findViewById<Button>(R.id.frag_back_btn)
        val nextButton = view?.findViewById<Button>(R.id.frag_next_btn)

        backButton?.setOnClickListener {
            writeToDatabase(mSelectedEnumeration.ordinal)
            findNavController().navigate(TemplateCreatorRowNamingDirections
                .actionTemplateNamingPop())
        }

        nextButton?.setOnClickListener {
            writeToDatabase(mSelectedEnumeration.ordinal)
            findNavController().navigate(TemplateCreatorRowNamingDirections
                .actionTemplateRowNamingToTemplatePreview(args.title))

        }
    }

    private fun writeToDatabase(selection: Int) {

        mTemplateTable?.load()?.find { it.title == args.title }?.let { template ->

            template.rowNumbering = selection != 0

            mTemplateTable?.update(template)
        }
    }

    private fun setupAdapter() {

        activity?.let { act ->

            val adapter = NamingAdapter(act)

            val listView = view?.findViewById<ListView>(R.id.frag_template_creator_row_naming_lv)

            listView?.adapter = adapter

            listView?.setOnItemClickListener { adapterView, view, i, _ ->
                with (view as CheckedTextView) {
                    isChecked = !isChecked
                    if (isChecked) {
                        mSelectedEnumeration = if (i == 0) AxisLabel.Alphabetic else AxisLabel.Numeric
                    }
                }

                //uncheck all other selections
                adapterView?.children?.forEachIndexed { index, ctv ->
                    with (ctv as CheckedTextView) {
                        if (index != i) {
                            this.isChecked = false
                        }
                    }
                }

                checkButtonText(adapterView)
            }

            mTemplateTable?.load()?.find { it.title == args.title }?.let { template ->
                mSelectedEnumeration = if (template.rowNumbering) AxisLabel.Numeric else AxisLabel.Alphabetic
            }

            listView?.children?.forEachIndexed { index, view ->
                if (index == mSelectedEnumeration.ordinal) {
                    (view as CheckedTextView).isChecked = true
                }
            }

            adapter.addAll(getString(R.string.alphabetic), getString(R.string.numeric))

            (listView?.adapter as? ArrayAdapter<*>)?.notifyDataSetChanged()
        }
    }

    private fun setDisabledNext() {
        val okButton = view?.findViewById<Button>(R.id.frag_next_btn)
        okButton?.isEnabled = false
    }

    private fun setNextText() {
        val okButton = view?.findViewById<Button>(R.id.frag_next_btn)
        okButton?.isEnabled = true
    }

    //iterates over all list items and changes button text depending on if user selected one
    private fun checkButtonText(view: AdapterView<*>) {
        if (view.children.any { (it as CheckedTextView).isChecked }) setNextText()
        else setDisabledNext()
    }

    private inner class NamingAdapter(ctx: Context):
        ArrayAdapter<String>(ctx, android.R.layout.simple_list_item_checked) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view = super.getView(position, convertView, parent)

            with (view as CheckedTextView) {

                text = if (position == 0) {
                    getString(R.string.alphabetic)
                } else getString(R.string.numeric)

                if (position == mSelectedEnumeration.ordinal) {
                    isChecked = true
                }
            }

            return view
        }
    }
}