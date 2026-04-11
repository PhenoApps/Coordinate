package org.wheatgenetics.coordinate.fragments.grid_creator

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.wheatgenetics.coordinate.R
import org.wheatgenetics.coordinate.StringGetter
import org.wheatgenetics.coordinate.adapter.TitleChoiceAdapter
import org.wheatgenetics.coordinate.database.EntriesTable
import org.wheatgenetics.coordinate.database.GridsTable
import org.wheatgenetics.coordinate.database.ProjectsTable
import androidx.preference.PreferenceManager
import org.wheatgenetics.coordinate.interfaces.TitleSelectedListener
import org.wheatgenetics.coordinate.model.Cell
import org.wheatgenetics.coordinate.model.JoinedGridModel
import org.wheatgenetics.coordinate.model.ProjectModel
import org.wheatgenetics.coordinate.model.TemplateModel
import org.wheatgenetics.coordinate.pc.CreateProjectDialogFragment
import org.wheatgenetics.coordinate.preference.GeneralKeys

class GridCreatorProjectOptions : Fragment(R.layout.fragment_grid_creator_project_options),
    TitleSelectedListener, StringGetter {

    private val viewModel: GridCreatorViewModel by activityViewModels()

    private var mProjectsTable: ProjectsTable? = null
    private var mGridsTable: GridsTable? = null
    private var mEntriesTable: EntriesTable? = null

    private var mSelectedProjectTitle: String? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity?.let { act ->
            mProjectsTable = ProjectsTable(act)
            mGridsTable = GridsTable(act)
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

        val prefs = PreferenceManager.getDefaultSharedPreferences(requireContext())
        val hideProjects = prefs.getBoolean(GeneralKeys.HIDE_PROJECTS, false)

        setupAdapter(!hideProjects)
        setupButtons()

        // check if launched from projects activity with a pre-set project
        // auto-complete grid creation with that project
        val projectId = activity?.intent?.getLongExtra("projectId", -1L) ?: -1L
        if (projectId != -1L) {
            val id = writeToDatabase(projectId)
            if (id != -1L) {
                activity?.setResult(Activity.RESULT_OK, Intent().putExtra("gridId", id))
                activity?.finish()
            }
            return
        }

        // If projects section is hidden, skip project assignment (create with no project)
        if (hideProjects) {
            val id = writeToDatabase(0L)
            if (id != -1L) {
                activity?.setResult(Activity.RESULT_OK, Intent().putExtra("gridId", id))
                activity?.finish()
            }
        }
    }

    private fun setupButtons() {

        val okButton = view?.findViewById<Button>(R.id.frag_next_btn)
        val backButton = view?.findViewById<Button>(R.id.frag_back_btn)

        setSkipText()

        okButton?.setOnClickListener {
            val projectEdit = activity?.intent?.getBooleanExtra("projectEdit", false) ?: false
            val gridId = activity?.intent?.getLongExtra("gridId", -1L) ?: -1L
            if (projectEdit && gridId != -1L) {

                if (mSelectedProjectTitle == null) {
                    activity?.setResult(Activity.RESULT_CANCELED)
                    activity?.finish()
                } else {
                    getProjectId(mSelectedProjectTitle)?.let { pid ->
                        mGridsTable?.get(gridId)?.let { grid ->
                            mGridsTable?.update(grid.apply {
                                this.projectId = pid
                            })
                        }
                    }

                    activity?.setResult(Activity.RESULT_OK)
                    activity?.finish()
                }

            } else {
                val resolvedProjectId = getProjectId(mSelectedProjectTitle) ?: 0L
                val id = writeToDatabase(resolvedProjectId)
                if (id != -1L) {
                    activity?.setResult(Activity.RESULT_OK, Intent().putExtra("gridId", id))
                    activity?.finish()
                }
            }
        }

        backButton?.setOnClickListener {
            navigateBack()
        }
    }

    private fun navigateBack() {
        val projectEdit = activity?.intent?.getBooleanExtra("projectEdit", false) ?: false
        if (projectEdit) {
            activity?.setResult(Activity.RESULT_CANCELED)
            activity?.finish()
        } else {
            findNavController().popBackStack()
        }
    }

    private fun writeToDatabase(projectId: Long): Long {
        val template = viewModel.template ?: return -1L
        val fields = viewModel.optionalFields ?: return -1L
        var retId = -1L

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

        return retId
    }

    private fun getActiveRowCol(template: TemplateModel): Pair<Int, Int>? {
        val rows = template.rows
        val cols = template.cols
        for (j in 0 until cols) {
            for (i in 0 until rows) {
                if (!template.isExcludedCell(Cell(i+1, j+1, this))) {
                    return Pair(i, j)
                }
            }
        }
        return null
    }

    private fun getProjectId(title: String?): Long? {
        val models = mProjectsTable?.load()
        val size = models?.size() ?: 0
        for (i in 0 until size) {
            models?.get(i)?.let { project ->
                if (project.title == title) {
                    return project.id
                }
            }
        }
        return null
    }

    private fun setSkipText() {
        val okButton = view?.findViewById<Button>(R.id.frag_next_btn)
        //set button text to skip if no project is selected
        okButton?.text = getString(R.string.skip)
    }

    private fun setNextText() {
        val okButton = view?.findViewById<Button>(R.id.frag_next_btn)
        okButton?.text = getString(R.string.frag_grid_creator_one_next_btn)
    }

    private fun setupAdapter(showAddNew: Boolean = true) {

        activity?.let { act ->

            //query db for optional fields
            mProjectsTable?.load()?.let { projects ->

                projects.titles()?.let { titles ->

                    val adapter = TitleChoiceAdapter(this, TitleChoiceAdapter.AdapterType.PROJECT, showAddNew)

                    val listView = view?.findViewById<RecyclerView>(R.id.frag_grid_creator_add_project_lv)

                    val size = titles.size

                    listView?.setItemViewCacheSize(size)

                    listView?.layoutManager = LinearLayoutManager(act)

                    listView?.adapter = adapter

                    (listView?.adapter as TitleChoiceAdapter).submitList(titles.map { it })

                    listView?.adapter?.notifyItemRangeChanged(0, size)
                }
            }
        }
    }

    override fun checked(title: String) {

        mSelectedProjectTitle = title

        setNextText()
    }

    override fun onAddNewItemClicked() {
        val createProjectAlertDialog = CreateProjectDialogFragment.newInstance(object : CreateProjectDialogFragment.Handler {
            override fun handleCreateProjectDone(projectTitle: String): Boolean {
                return onCreateProjectDone(projectTitle)
            }
        })
        activity?.supportFragmentManager?.let { createProjectAlertDialog.show(it, "CreateProjectDialogFragment") }
    }

    //here we must check if the project already exists in projects table
    fun onCreateProjectDone(projectTitle: String?): Boolean {
        return if (!(mProjectsTable?.load()?.titles() ?: arrayOf()).contains(projectTitle)) {
            val id = mProjectsTable?.insert(ProjectModel(projectTitle, this))
            setupAdapter()
            id != -1L
        } else false
    }

    override fun get(resId: Int): String? = activity?.getString(resId)

    @SuppressLint("ResourceType")
    override fun getQuantity(resId: Int, quantity: Int, vararg formatArgs: Any?) = activity?.getString(resId, formatArgs) ?: String()
}
