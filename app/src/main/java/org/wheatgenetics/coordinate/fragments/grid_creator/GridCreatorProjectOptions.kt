package org.wheatgenetics.coordinate.fragments.grid_creator

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.wheatgenetics.coordinate.R
import org.wheatgenetics.coordinate.StringGetter
import org.wheatgenetics.coordinate.database.GridsTable
import org.wheatgenetics.coordinate.database.ProjectsTable
import org.wheatgenetics.coordinate.model.ProjectModel
import org.wheatgenetics.coordinate.pc.CreateProjectAlertDialog

class GridCreatorProjectOptions : Fragment(R.layout.fragment_grid_creator_project_options),
    CreateProjectAlertDialog.Handler, StringGetter {

    private var mProjectsTable: ProjectsTable? = null

    private var mGridsTable: GridsTable? = null

    private var mSelectedProjectTitle: String? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity?.let { act ->
            mProjectsTable = ProjectsTable(act)
            mGridsTable = GridsTable(act)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()
        setupButtons()

        //check if this creator started from the projects activity (skip this fragment)
        val projectId = activity?.intent?.getLongExtra("projectId", -1L)
        if (projectId != -1L) {

            val projects = mProjectsTable?.load()
            val size = projects?.size() ?: 0
            for (i in 0 until size) {
                projects?.get(i)?.let { p ->
                    if (p.id == projectId) {
                        findNavController().navigate(GridCreatorProjectOptionsDirections
                            .actionProjectOptionsToTemplateOptions().setProject(p.title))
                    }
                }
            }
        }
    }

    private fun setupButtons() {

        val okButton = view?.findViewById<Button>(R.id.frag_next_btn)
        val backButton = view?.findViewById<Button>(R.id.frag_back_btn)
        //button that will start an add optional fields fragment
        val addButton = view?.findViewById<ImageButton>(R.id.frag_grid_creator_project_options_new_btn)

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
                                projectId = pid
                            })
                        }
                    }

                    activity?.setResult(Activity.RESULT_OK)
                    activity?.finish()
                }

            } else {
                findNavController().navigate(GridCreatorProjectOptionsDirections
                    .actionProjectOptionsToTemplateOptions().setProject(mSelectedProjectTitle))
            }
        }

        backButton?.setOnClickListener {
            activity?.setResult(Activity.RESULT_CANCELED)
            activity?.finish()
        }

        addButton?.setOnClickListener {
            activity?.let { act ->
                if (isAdded) {
                    //when alert dialog finishes handleCreateProjectDone is called
                    CreateProjectAlertDialog(act, this).show()
                }
            }
        }
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

    //iterates over all list items and changes button text depending on if user selected one
    private fun checkButtonText(view: AdapterView<*>) {
        if (view.children.any { (it as CheckedTextView).isChecked }) setNextText()
        else setSkipText()
    }

    private fun setupAdapter() {

        activity?.let { act ->

            //query db for optional fields
            mProjectsTable?.load()?.let { projects ->

                projects.titles()?.let { titles ->

                    val adapter = ArrayAdapter(act, android.R.layout.simple_list_item_checked, titles)

                    val listView = view?.findViewById<ListView>(R.id.frag_grid_creator_add_project_lv)

                    listView?.adapter = adapter

                    listView?.setOnItemClickListener { adapterView, view, i, l ->
                        with (view as CheckedTextView) {
                            isChecked = !isChecked
                            mSelectedProjectTitle = if (isChecked) view.text.toString() else null
                        }

                        //uncheck all other selections
                        adapterView?.children?.forEachIndexed { index, ctv ->
                            with (ctv as CheckedTextView) {
                                if (index != i) this.isChecked = false
                            }
                        }

                        checkButtonText(adapterView)
                    }

                    (listView?.adapter as? ArrayAdapter<*>)?.notifyDataSetChanged()
                }
            }
        }
    }

    //here we must check if the project already exists in projects table
    override fun handleCreateProjectDone(projectTitle: String?): Boolean {
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