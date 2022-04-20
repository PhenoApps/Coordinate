package org.wheatgenetics.coordinate.fragments.grid_creator

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.children
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()
        setupButtons()

        //check if this creator started from the templates activity (skip this fragment)
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

        //button that will start an add optional fields fragment
        val addButton = view?.findViewById<ImageButton>(R.id.frag_grid_creator_template_options_new_btn)

        activity?.let { act ->

            //query db for optional fields
            mTemplatesTable?.load()?.let { templates ->

                addButton?.setOnClickListener {

                    mTemplateActivityStarter.launch(Intent(act, TemplateCreatorActivity::class.java))
                }

                templates.titles()?.let { titles ->

                    val adapter = TitleChoiceAdapter(this)

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
}