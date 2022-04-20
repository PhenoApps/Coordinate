package org.wheatgenetics.coordinate.fragments.template_creator

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.wheatgenetics.coordinate.R
import org.wheatgenetics.coordinate.StringGetter
import org.wheatgenetics.coordinate.activity.TemplateCreatorActivity
import org.wheatgenetics.coordinate.database.TemplatesTable
import org.wheatgenetics.coordinate.model.TemplateModel

/**
 * The purpose of this fragment is to enter a template name, row and columns sizes.
 * This is the first step in the template creator navigation/template_creator_graph
 * The template name should not be empty or already exist in the db.
 * The rows and columns should be greater than zero and less than max int.
 */
class TemplateCreatorDimensions : Fragment(R.layout.fragment_template_creator_dimensions), StringGetter {

    private var mEdit: Long = -1L

    private var mTemplateTable: TemplatesTable? = null

    private var mPreviousName: String? = null
    private var mPreviousRow: Int? = null
    private var mPreviousCol: Int? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        mTemplateTable = TemplatesTable(context)
    }

    private fun updateUiForEdit(template: TemplateModel) {
        val name = view?.findViewById<EditText>(R.id.frag_grid_creator_template_name_et)
        val rows = view?.findViewById<EditText>(R.id.frag_grid_creator_template_rows_et)
        val columns = view?.findViewById<EditText>(R.id.frag_grid_creator_template_columns_et)

        name?.setText(template.title)
        rows?.setText(template.rows.toString())
        columns?.setText(template.cols.toString())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //check if this is an edit action, populate ui
        if (activity?.intent?.hasExtra(TemplateCreatorActivity.TEMPLATE_EDIT) == true) {

            mEdit = activity?.intent?.getLongExtra(TemplateCreatorActivity.TEMPLATE_EDIT, -1L) ?: -1L

            if (mEdit != -1L) {

                mTemplateTable?.load()?.find { it.id == mEdit }?.let { template ->

                    mPreviousName = template.title
                    mPreviousRow = template.rows
                    mPreviousCol = template.cols

                    updateUiForEdit(template)
                }
            }
        }

        setupNextButton()
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        val name = view?.findViewById<EditText>(R.id.frag_grid_creator_template_name_et)?.text.toString()
        val rows = view?.findViewById<EditText>(R.id.frag_grid_creator_template_rows_et)?.text.toString()
        val columns = view?.findViewById<EditText>(R.id.frag_grid_creator_template_columns_et)?.text.toString()

        if (name.isNotBlank()) mPreviousName = name
        if (rows.isNotBlank()) mPreviousRow = rows.toIntOrNull() ?: 0
        if (columns.isNotBlank()) mPreviousCol = columns.toIntOrNull() ?: 0

    }

    private fun setupNextButton() {

        context?.let { ctx ->

            //back button
            view?.findViewById<Button>(R.id.frag_back_btn)?.setOnClickListener {
                activity?.setResult(Activity.RESULT_CANCELED)
                activity?.finish()

                //make sure on editing we don't delete the template after canceling
                if (mPreviousName?.isNotBlank() == true && mEdit == -1L) {
                    mTemplateTable?.load()?.find { it.title == mPreviousName }?.let { template ->
                        mTemplateTable?.delete(template.id)
                    }
                }
            }

            //next button
            view?.findViewById<Button>(R.id.frag_next_btn)?.setOnClickListener {

                val name = view?.findViewById<EditText>(R.id.frag_grid_creator_template_name_et)?.text.toString()
                val rows = view?.findViewById<EditText>(R.id.frag_grid_creator_template_rows_et)?.text.toString().toIntOrNull()
                val columns = view?.findViewById<EditText>(R.id.frag_grid_creator_template_columns_et)?.text.toString().toIntOrNull()

                if (name.isNotBlank()) {

                    //check if name already exists or we are updating the dimensions (after going back from next fragment)
                    if (!existsInDatabase(name) || mPreviousName?.isNotBlank() == true) {

                        if (mPreviousName != name && existsInDatabase(name)) {

                            Toast.makeText(ctx, R.string.frag_template_creator_name_duplicate_error, Toast.LENGTH_SHORT).show()

                            return@setOnClickListener
                        }

                        if (verifyGridRange(rows)) {

                            if (verifyGridRange(columns)) {

                                writeToDatabase(name, rows ?: 0, columns ?: 0)
                                findNavController().navigate(TemplateCreatorDimensionsDirections
                                    .actionTemplateDimensionsToTemplateOptionalFields(name))

                            } else {

                                Toast.makeText(ctx, R.string.frag_template_creator_dimension_input_error, Toast.LENGTH_SHORT).show()
                            }

                        } else {

                            Toast.makeText(ctx, R.string.frag_template_creator_dimension_input_error, Toast.LENGTH_SHORT).show()

                        }

                    } else {

                        Toast.makeText(ctx, R.string.frag_template_creator_name_duplicate_error, Toast.LENGTH_SHORT).show()

                    }

                } else {

                    Toast.makeText(ctx, R.string.frag_template_creator_name_input_error, Toast.LENGTH_SHORT).show()

                }
            }
        }
    }

    private fun clearDatabaseExclusions(temp: TemplateModel) {

        for (i in 0 until temp.rows) {
            for (j in 0 until temp.cols) {
                val cell = org.wheatgenetics.coordinate.model.Cell(i + 1, j + 1, this)
                if (temp.isExcludedCell(cell)) {
                    temp.remove(cell)
                }
            }
        }

        mTemplateTable?.update(temp)

    }

    //write the name, rows, columns to database
    private fun writeToDatabase(name: String, rows: Int, columns: Int) {

        val template = TemplateModel.makeUserDefined(this).apply {
            assign(name, rows, columns)
        }

        //check if this is an update, otherwise insert new template
        if ((mPreviousName != null && mPreviousName?.isNotBlank() == true)) {

            mTemplateTable?.load()?.find { t -> t.title == mPreviousName }?.let { temp ->

                //clear previous exclusions if dimensions have changed
                if (rows != mPreviousRow || columns != mPreviousCol) {

                    clearDatabaseExclusions(temp)
                }

                temp.assign(name, rows, columns)
                mTemplateTable?.update(temp)
            }
        } else {
            mTemplateTable?.insert(template)?.let { tid ->
                template.id = tid
            }
        }
    }

    //check that this name is not already defined as a template
    private fun existsInDatabase(name: String) = mTemplateTable?.load()?.any { template -> template.title == name } ?: false

    //simply checks if the dimension is within range of spec
    private fun verifyGridRange(dim: Int? = 0) = dim in 1 until Int.MAX_VALUE

    override fun get(resId: Int): String? = activity?.getString(resId)

    @SuppressLint("ResourceType")
    override fun getQuantity(resId: Int, quantity: Int, vararg formatArgs: Any?) = activity?.getString(resId, formatArgs) ?: String()
}