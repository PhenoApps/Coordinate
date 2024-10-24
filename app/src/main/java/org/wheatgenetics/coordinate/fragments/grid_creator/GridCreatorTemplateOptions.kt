package org.wheatgenetics.coordinate.fragments.grid_creator

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.wheatgenetics.coordinate.R
import org.wheatgenetics.coordinate.activity.TemplateCreatorActivity
import org.wheatgenetics.coordinate.adapter.TitleChoiceAdapter
import org.wheatgenetics.coordinate.database.TemplatesTable
import org.wheatgenetics.coordinate.interfaces.TitleSelectedListener

class GridCreatorTemplateOptions : Fragment(R.layout.fragment_grid_creator_template_options),
    TitleSelectedListener {

    private val args: GridCreatorTemplateOptionsArgs by navArgs()

    private var mTemplatesTable: TemplatesTable? = null

    private var mSelectedTemplateTitle: String? = null

    private val mTemplateActivityStarter = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

        result?.let {

            setupAdapter()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity?.let { act ->
            mTemplatesTable = TemplatesTable(act)
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

        // check if this creator started from the templates activity
        // or if from grids activity via template filter
        // (skip this fragment)
        val templateId = activity?.intent?.getLongExtra("templateId", -1L)
        if (templateId != -1L) {
            mTemplatesTable?.load()?.find { it.id == templateId }?.title?.let { title ->
                findNavController().navigate(GridCreatorTemplateOptionsDirections
                    .actionTemplateOptionsToTemplateFields(title).setProject(args.project))
            }
        }
    }

    private fun setupButtons() {

        val okButton = view?.findViewById<Button>(R.id.frag_next_btn)
        val backButton = view?.findViewById<Button>(R.id.frag_back_btn)

        setDisabledNext()

        okButton?.setOnClickListener {
            mSelectedTemplateTitle?.let { title ->
                findNavController().navigate(GridCreatorTemplateOptionsDirections
                    .actionTemplateOptionsToTemplateFields(title).setProject(args.project))
            }
        }

        backButton?.setOnClickListener {
            navigateBack()
        }
    }

    private fun navigateBack() {
        val projectId = activity?.intent?.getLongExtra("projectId", -1L)
        with (findNavController()) {
            when {
                projectId != -1L -> {
                    activity?.setResult(Activity.RESULT_CANCELED)
                    activity?.finish()
                }
                (previousBackStackEntry?.destination?.id ?: -1) == R.id.project_options -> {
                    popBackStack(R.id.project_options, false)
                }
                else -> {
                    activity?.setResult(Activity.RESULT_CANCELED)
                    activity?.finish()
                }
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

    private fun setupAdapter() {

        activity?.let { act ->

            //query db for optional fields
            mTemplatesTable?.load()?.let { templates ->

                templates.titles()?.let { titles ->

                    val adapter = TitleChoiceAdapter(this, TitleChoiceAdapter.AdapterType.TEMPLATE)

                    val listView = view?.findViewById<RecyclerView>(R.id.frag_grid_creator_add_template_lv)

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

        mSelectedTemplateTitle = title

        setNextText()

    }

    override fun onAddNewItemClicked() {
        mTemplateActivityStarter.launch(Intent(requireActivity(), TemplateCreatorActivity::class.java))
    }
}