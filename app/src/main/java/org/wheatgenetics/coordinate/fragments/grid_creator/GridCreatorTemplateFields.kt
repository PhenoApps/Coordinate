package org.wheatgenetics.coordinate.fragments.grid_creator

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.wheatgenetics.coordinate.R
import org.wheatgenetics.coordinate.StringGetter
import org.wheatgenetics.coordinate.adapter.FieldsAdapter
import org.wheatgenetics.coordinate.database.EntriesTable
import org.wheatgenetics.coordinate.database.GridsTable
import org.wheatgenetics.coordinate.database.ProjectsTable
import org.wheatgenetics.coordinate.database.TemplatesTable
import org.wheatgenetics.coordinate.interfaces.RequiredFieldsCompleteListener
import org.wheatgenetics.coordinate.model.Cell
import org.wheatgenetics.coordinate.model.JoinedGridModel
import org.wheatgenetics.coordinate.model.TemplateModel
import org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields

class GridCreatorTemplateFields : Fragment(R.layout.fragment_grid_creator_fields),
    RequiredFieldsCompleteListener,
    StringGetter {

    private val args: GridCreatorTemplateFieldsArgs by navArgs()

    private var mTemplatesTable: TemplatesTable? = null
    private var mGridsTable: GridsTable? = null
    private var mProjectsTable: ProjectsTable? = null
    private var mEntriesTable: EntriesTable? = null

    private lateinit var fieldsAdapter: FieldsAdapter

    private val mTemplate: TemplateModel? by lazy {
        mTemplatesTable?.load()?.find { it.title == args.title }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity?.let { act ->
            mTemplatesTable = TemplatesTable(act)
            mGridsTable = GridsTable(act)
            mProjectsTable = ProjectsTable(act)
            mEntriesTable = EntriesTable(act)
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

        val titleTextView = view?.findViewById<TextView>(R.id.frag_grid_creator_fields_title_tv)
        val okButton = view?.findViewById<Button>(R.id.frag_next_btn)
        val backButton = view?.findViewById<Button>(R.id.frag_back_btn)

        titleTextView?.text = args.title

        setDisabledNext()

        okButton?.setOnClickListener {
            val id = writeToDatabase()
            if (id != -1L) {
                activity?.setResult(Activity.RESULT_OK, Intent().putExtra("gridId", id))
                activity?.finish()
            }
        }

        backButton?.setOnClickListener {
            navigateBack()
        }
    }

    private fun navigateBack() {
        val templateId = activity?.intent?.getLongExtra("templateId", -1L)
        if (templateId != -1L) {
            findNavController().popBackStack(R.id.project_options, false)
        } else findNavController().popBackStack()
    }

    private fun writeToDatabase(): Long {

        val projectId = getProjectId()
        val fields = getOptionalFields()
        var retId = -1L

        mTemplate?.let { template ->

            val jgm = JoinedGridModel(projectId, null, fields, this, template)
            mGridsTable?.insert(jgm)?.let { id ->

                retId = id

                //have to manually set the id after inserting
                jgm.id = id

                //update the first available non excluded cell (first cell the collector activity starts with)
                getActiveRowCol(template)?.let { active ->
                    jgm.setActiveRowAndActiveCol(active.first, active.second)
                }

                //update table with the active row/col and id
                mGridsTable?.update(jgm)

                //have to manually call this to populate the entries table
                jgm.makeEntryModels()
                mEntriesTable?.insert(jgm.entryModels)
            }
        }

        return retId
    }

    private fun getActiveRowCol(template: TemplateModel): Pair<Int, Int>? {
        val rows = template.rows
        val cols = template.cols
        for (j in 0 until cols) {
            for (i in 0 until rows) {
                if (!template.isExcludedCell(Cell(i+1, j+1, this))) {
                    //this is confusing the active cell is not 1-based like the grid is
                    return Pair(i, j)
                }
            }
        }
        return null
    }

    private fun getProjectId(): Long {

        //iterate over projects to find id
        args.project?.let { projectTitle ->
            val projects = mProjectsTable?.load()
            val size = projects?.size() ?: 0
            for (i in 0 until size) {
                val p = projects?.get(i)
                if (p != null && p.title == projectTitle) {
                    return p.id
                }
            }
        }

        return 0
    }

    private fun getOptionalFields(): NonNullOptionalFields {

        val fields = NonNullOptionalFields(this)

//        val listView = view?.findViewById<RecyclerView>(R.id.frag_grid_creator_fields_lv)

//        listView?.children?.forEach {
//            val nameTextView = it.findViewById<TextView>(R.id.list_item_field_tv)
//            val valueEditText = it.findViewById<EditText>(R.id.list_item_field_et)
//            fields.add(nameTextView.text.toString(), valueEditText.text.toString(), null)
//        }

        fieldsAdapter.getAllFieldValues().forEach { (name, value) ->
            fields.add(name, value, null)
        }

        return fields
    }

    //check that the base optional field "identification" value is entered
    private fun checkRequiredFieldsEntered(): Boolean {

        val requiredName = getRequiredName()

        val listView = view?.findViewById<RecyclerView>(R.id.frag_grid_creator_fields_lv)

        listView?.children?.forEach {
            val nameTextView = it.findViewById<TextView>(R.id.list_item_field_tv)
            if (nameTextView.text == requiredName) {
                val valueEditText = it.findViewById<EditText>(R.id.list_item_field_et)
                if (valueEditText.text.isNotBlank()) {
                    return true
                }
            }
        }

        return false
    }

    private fun getRequiredName(): String = when (args.title) {
        getString(R.string.DNADefaultTemplateTitle) -> getString(R.string.NonNullOptionalFieldsPlateIDFieldName)
        getString(R.string.SeedDefaultTemplateTitle) -> getString(R.string.NonNullOptionalFieldsTrayIDFieldName)
        getString(R.string.HTPGTemplateTitle) -> getString(R.string.NonNullOptionalFieldsHTPG)
        else -> getString(R.string.BaseOptionalFieldIdentificationFieldName)
    }

    private fun setDisabledNext() {
        val okButton = view?.findViewById<Button>(R.id.frag_next_btn)
        okButton?.isEnabled = false
    }

    private fun setNextText() {
        val okButton = view?.findViewById<Button>(R.id.frag_next_btn)
        okButton?.isEnabled = true
    }

    private fun setupAdapter() {

        val requiredName = getRequiredName()

        activity?.let { act ->

            //query db for optional fields
            mTemplatesTable?.getOptionalFieldsForTemplate(args.title, this)?.let { fields ->

                // set hint text for optional fields
                fields.forEach { field ->
                    if (field.name != requiredName) {
                       field.hint = getString(R.string.optional_field_hint_text)
                    }
                }

                fieldsAdapter = FieldsAdapter(this, requiredName, fields)

                val listView = view?.findViewById<RecyclerView>(R.id.frag_grid_creator_fields_lv)

                val size = fields.count()

                listView?.setItemViewCacheSize(size)

                listView?.layoutManager = LinearLayoutManager(act)

                listView?.adapter = fieldsAdapter

                (listView?.adapter as FieldsAdapter).submitList(fields.map { it })

                listView?.adapter?.notifyItemRangeChanged(0, size)
            }
        }
    }

    override fun get(resId: Int): String? = activity?.getString(resId)

    @SuppressLint("ResourceType")
    override fun getQuantity(resId: Int, quantity: Int, vararg formatArgs: Any?) = activity?.getString(resId, formatArgs) ?: String()

    override fun completed() {

        if (checkRequiredFieldsEntered()) setNextText() else setDisabledNext()

    }
}