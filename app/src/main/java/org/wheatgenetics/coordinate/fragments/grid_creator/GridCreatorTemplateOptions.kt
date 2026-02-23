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
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.wheatgenetics.coordinate.R
import org.wheatgenetics.coordinate.activity.TemplateCreatorActivity
import org.wheatgenetics.coordinate.adapter.TitleChoiceAdapter
import org.wheatgenetics.coordinate.database.TemplatesTable
import org.wheatgenetics.coordinate.interfaces.TitleSelectedListener
import org.wheatgenetics.coordinate.preference.GeneralKeys

class GridCreatorTemplateOptions : Fragment(R.layout.fragment_grid_creator_template_options),
    TitleSelectedListener {

    private var mTemplatesTable: TemplatesTable? = null
    private val mTemplateActivityStarter = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

        result?.let {
            val hideTemplates = PreferenceManager.getDefaultSharedPreferences(requireContext())
                .getBoolean(GeneralKeys.HIDE_TEMPLATES, false)
            setupAdapter(!hideTemplates)
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

        val prefs = PreferenceManager.getDefaultSharedPreferences(requireContext())
        val hideTemplates = prefs.getBoolean(GeneralKeys.HIDE_TEMPLATES, false)

        setupAdapter(!hideTemplates)
        setupButtons()

        // Pop this fragment from the back stack on auto-navigation so pressing back
        // from the destination finishes the activity instead of looping back here.
        val popSelf = NavOptions.Builder()
            .setPopUpTo(R.id.template_options, true)
            .build()

        // if in project-edit mode, skip directly to project selection
        val projectEdit = activity?.intent?.getBooleanExtra("projectEdit", false) ?: false
        if (projectEdit) {
            findNavController().navigate(
                GridCreatorTemplateOptionsDirections.actionTemplateOptionsToProjectOptions(),
                popSelf
            )
            return
        }

        // check if this creator started from the templates activity
        // or if from grids activity via template filter
        // (skip this fragment)
        val templateId = activity?.intent?.getLongExtra("templateId", -1L)
        if (templateId != -1L) {
            mTemplatesTable?.load()?.find { it.id == templateId }?.title?.let { title ->
                findNavController().navigate(
                    GridCreatorTemplateOptionsDirections.actionTemplateOptionsToTemplateFields(title),
                    popSelf
                )
            }
            return
        }

        // If templates section is hidden and only one template exists, auto-select it
        if (hideTemplates) {
            mTemplatesTable?.load()?.let { templates ->
                val hiddenRaw = prefs.getString(GeneralKeys.HIDDEN_BUILTIN_TEMPLATES, "") ?: ""
                val hiddenCodes = if (hiddenRaw.isEmpty()) emptySet<String>()
                    else hiddenRaw.split(",").toSet()
                val filteredTitles = templates.filter { t ->
                    !t.isDefaultTemplate || !hiddenCodes.contains(t.type.code.toString())
                }.mapNotNull { it.title }
                if (filteredTitles.size == 1) {
                    findNavController().navigate(
                        GridCreatorTemplateOptionsDirections.actionTemplateOptionsToTemplateFields(filteredTitles[0]),
                        popSelf
                    )
                }
            }
        }
    }

    private fun setupButtons() {

        val okButton = view?.findViewById<Button>(R.id.frag_next_btn)
        val backButton = view?.findViewById<Button>(R.id.frag_back_btn)

        // Next button is not needed — selection navigates immediately
        okButton?.visibility = View.GONE

        backButton?.setOnClickListener {
            navigateBack()
        }
    }

    private fun navigateBack() {
        activity?.setResult(Activity.RESULT_CANCELED)
        activity?.finish()
    }


    private fun setupAdapter(showAddNew: Boolean = true) {

        activity?.let { act ->

            //query db for optional fields
            mTemplatesTable?.load()?.let { templates ->

                // Filter out hidden built-in templates
                val hiddenRaw = PreferenceManager.getDefaultSharedPreferences(act)
                    .getString(GeneralKeys.HIDDEN_BUILTIN_TEMPLATES, "") ?: ""
                val hiddenCodes = if (hiddenRaw.isEmpty()) emptySet<String>()
                    else hiddenRaw.split(",").toSet()
                val filteredTitles = mutableListOf<String>()
                for (template in templates) {
                    val typeCode = template.type.code.toString()
                    if (!template.isDefaultTemplate || !hiddenCodes.contains(typeCode)) {
                        template.title?.let { filteredTitles.add(it) }
                    }
                }

                val adapter = TitleChoiceAdapter(this, TitleChoiceAdapter.AdapterType.TEMPLATE, showAddNew)

                val listView = view?.findViewById<RecyclerView>(R.id.frag_grid_creator_add_template_lv)

                val size = filteredTitles.size

                listView?.setItemViewCacheSize(size)

                listView?.layoutManager = LinearLayoutManager(act)

                listView?.adapter = adapter

                (listView?.adapter as TitleChoiceAdapter).submitList(filteredTitles)

                listView?.adapter?.notifyItemRangeChanged(0, size)
            }
        }
    }

    override fun checked(title: String) {
        findNavController().navigate(GridCreatorTemplateOptionsDirections
            .actionTemplateOptionsToTemplateFields(title))
    }

    override fun onAddNewItemClicked() {
        mTemplateActivityStarter.launch(Intent(requireActivity(), TemplateCreatorActivity::class.java))
    }
}
