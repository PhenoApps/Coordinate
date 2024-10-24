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
import android.widget.ListView
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import org.wheatgenetics.coordinate.R
import org.wheatgenetics.coordinate.database.TemplatesTable

class TemplateCreatorNaming : Fragment(R.layout.fragment_template_creator_naming) {

    private val args: TemplateCreatorNamingArgs by navArgs()

    private var mTemplateTable: TemplatesTable? = null

    private var mSelectedRowEnumeration = AxisLabel.Numeric

    private var mSelectedColEnumeration = AxisLabel.Numeric

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mTemplateTable = TemplatesTable(context)
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
        val backButton = view?.findViewById<Button>(R.id.frag_back_btn)
        val nextButton = view?.findViewById<Button>(R.id.frag_next_btn)

        backButton?.setOnClickListener {
            navigateBack()
        }

        nextButton?.setOnClickListener {
            writeToDatabase(mSelectedColEnumeration.ordinal, mSelectedRowEnumeration.ordinal)
            findNavController().navigate(TemplateCreatorNamingDirections
                .actionTemplateNamingToTemplateExcludeOptions(args.title))
        }
    }

    private fun navigateBack() {
        writeToDatabase(mSelectedColEnumeration.ordinal, mSelectedRowEnumeration.ordinal)
        findNavController().navigate(TemplateCreatorNamingDirections
            .actionTemplateNamingPop())
    }

    private fun writeToDatabase(col: Int, row: Int) {

        mTemplateTable?.load()?.find { it.title == args.title }?.let { template ->

            template.colNumbering = col != 0

            template.rowNumbering = row != 0

            mTemplateTable?.update(template)
        }
    }

    private fun setupAdapter() {

        activity?.let { act ->

            for (n in 0..1) {

                val adapter = NamingAdapter(act, n)

                val listView = view?.findViewById<ListView>(
                    if (n == 0) R.id.frag_template_creator_column_naming_lv
                    else R.id.frag_template_creator_row_naming_lv)

                listView?.adapter = adapter

                listView?.setOnItemClickListener { adapterView, view, i, _ ->
                    with (view as CheckedTextView) {
                        isChecked = !isChecked
                        if (isChecked) {
                            val selection = if (i == 0) AxisLabel.Alphabetic else AxisLabel.Numeric
                            if (n == 0) mSelectedColEnumeration = selection
                            else mSelectedRowEnumeration = selection

                            // Set the color of the check mark for selected item
                            val color = ContextCompat.getColor(context, R.color.colorAccent)
                            checkMarkTintList = ColorStateList.valueOf(color)
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

                    checkButtonText()
                }

                mTemplateTable?.load()?.find { it.title == args.title }?.let { template ->
                    if (n == 0) mSelectedColEnumeration = if (template.colNumbering) AxisLabel.Numeric else AxisLabel.Alphabetic
                    else mSelectedRowEnumeration = if (template.rowNumbering) AxisLabel.Numeric else AxisLabel.Alphabetic
                }

                listView?.children?.forEachIndexed { index, view ->
                    val ordinal = if (n == 0) mSelectedColEnumeration.ordinal else mSelectedRowEnumeration.ordinal
                    if (index == ordinal) {
                        (view as CheckedTextView).isChecked = true
                    }
                }

                adapter.addAll(getString(R.string.alphabetic), getString(R.string.numeric))

                (listView?.adapter as? ArrayAdapter<*>)?.notifyDataSetChanged()
            }
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
    private fun checkButtonText() {
        if (view?.findViewById<ListView>(R.id.frag_template_creator_column_naming_lv)?.children
                ?.any { (it as CheckedTextView).isChecked } == true) setNextText()
        else setDisabledNext()
    }

    private inner class NamingAdapter(ctx: Context, private val axis: Int):
        ArrayAdapter<String>(ctx, android.R.layout.simple_list_item_checked) {

            // color of the check mark for initially selected item
            private val color = ContextCompat.getColor(context, R.color.colorAccent)
            private val tintList = ColorStateList.valueOf(color)

            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view = super.getView(position, convertView, parent)

            with (view as CheckedTextView) {

                text = if (position == 0) {
                    getString(R.string.alphabetic)
                } else getString(R.string.numeric)

                if (axis == 0) {
                    if (position == mSelectedColEnumeration.ordinal) {
                        isChecked = true
                        checkMarkTintList = tintList
                    }
                } else {
                    if (position == mSelectedRowEnumeration.ordinal) {
                        isChecked = true
                        checkMarkTintList = tintList
                    }
                }
            }

            return view
        }
    }
}