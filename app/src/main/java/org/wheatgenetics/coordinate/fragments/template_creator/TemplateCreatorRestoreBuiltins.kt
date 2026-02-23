package org.wheatgenetics.coordinate.fragments.template_creator

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import org.wheatgenetics.coordinate.R
import org.wheatgenetics.coordinate.activity.TemplateCreatorActivity
import org.wheatgenetics.coordinate.database.TemplatesTable
import org.wheatgenetics.coordinate.model.TemplateType
import org.wheatgenetics.coordinate.preference.GeneralKeys

/**
 * Shown at the start of template creation to offer restoration of hidden built-in templates.
 * Skipped automatically (removed from back stack) when there are no hidden templates or
 * when in template-edit mode.
 */
class TemplateCreatorRestoreBuiltins :
    Fragment(R.layout.fragment_template_creator_restore_builtins) {

    private var mTemplatesTable: TemplatesTable? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mTemplatesTable = TemplatesTable(context)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) navigateBack()
        return super.onOptionsItemSelected(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        // Skip this page in edit mode
        if (activity?.intent?.hasExtra(TemplateCreatorActivity.TEMPLATE_EDIT) == true) {
            skipToNext()
            return
        }

        val hiddenTypes = hiddenBuiltinTypes()
        if (hiddenTypes.isEmpty()) {
            skipToNext()
            return
        }

        setupList(hiddenTypes)
        setupButtons(hiddenTypes)
    }

    private fun hiddenBuiltinTypes(): List<TemplateType> {
        val prefs = PreferenceManager.getDefaultSharedPreferences(requireContext())
        val raw = prefs.getString(GeneralKeys.HIDDEN_BUILTIN_TEMPLATES, "") ?: ""
        if (raw.isEmpty()) return emptyList()
        val codes = raw.split(",").toSet()
        return TemplateType.values()
            .filter { it.isDefaultTemplate && codes.contains(it.code.toString()) }
    }

    private fun templateDisplayTitle(type: TemplateType): String = when (type) {
        TemplateType.SEED -> getString(R.string.SeedDefaultTemplateTitle)
        TemplateType.DNA -> getString(R.string.DNADefaultTemplateTitle)
        TemplateType.HTPG -> getString(R.string.HTPGDefaultTemplateTitle)
        else -> type.name
    }

    private fun setupList(hiddenTypes: List<TemplateType>) {
        val listView = view?.findViewById<ListView>(R.id.frag_restore_builtins_lv) ?: return
        val titles = hiddenTypes.map { templateDisplayTitle(it) }.toMutableList()
        titles.add(getString(R.string.new_template_title))
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_multiple_choice, titles)
        listView.adapter = adapter
        // Items are unselected by default
        listView.setOnItemClickListener { _, _, _, _ ->
            updateNextButtonText(listView, hiddenTypes.size)
        }
    }

    private fun updateNextButtonText(listView: ListView, createNewIndex: Int) {
        val nextBtn = view?.findViewById<Button>(R.id.frag_next_btn) ?: return
        val createNewChecked = listView.isItemChecked(createNewIndex)
        val anyBuiltinChecked = (0 until createNewIndex).any { listView.isItemChecked(it) }
        nextBtn.text = when {
            createNewChecked -> getString(R.string.frag_grid_creator_one_next_btn)
            anyBuiltinChecked -> getString(R.string.TemplatesActivityImportMenuItemTitle)
            else -> getString(R.string.frag_grid_creator_one_next_btn)
        }
    }

    private fun setupButtons(hiddenTypes: List<TemplateType>) {
        view?.findViewById<Button>(R.id.frag_back_btn)?.setOnClickListener { navigateBack() }

        val createNewIndex = hiddenTypes.size

        view?.findViewById<Button>(R.id.frag_next_btn)?.setOnClickListener {
            val listView = view?.findViewById<ListView>(R.id.frag_restore_builtins_lv)
            val createNewChecked = listView?.isItemChecked(createNewIndex) == true
            val anyBuiltinChecked = listView != null && (0 until hiddenTypes.size).any { listView.isItemChecked(it) }

            if (anyBuiltinChecked && listView != null) {
                // Restore any selected built-in templates
                val prefs = PreferenceManager.getDefaultSharedPreferences(requireContext())
                val raw = prefs.getString(GeneralKeys.HIDDEN_BUILTIN_TEMPLATES, "") ?: ""
                val codes = if (raw.isEmpty()) mutableSetOf() else raw.split(",").toMutableSet()
                for (i in hiddenTypes.indices) {
                    if (listView.isItemChecked(i)) {
                        codes.remove(hiddenTypes[i].code.toString())
                    }
                }
                prefs.edit().putString(GeneralKeys.HIDDEN_BUILTIN_TEMPLATES, codes.joinToString(",")).apply()
            }

            if (createNewChecked || !anyBuiltinChecked) {
                // "Create new template" is checked, or nothing is selected: navigate to dimensions
                findNavController().navigate(
                    TemplateCreatorRestoreBuiltinsDirections.actionRestoreBuiltinsToDimensions()
                )
            } else {
                // Only built-in templates were restored; close the activity
                activity?.setResult(Activity.RESULT_OK)
                activity?.finish()
            }
        }
    }

    private fun skipToNext() {
        // Pop this fragment from the back stack so Back in Dimensions finishes the activity
        findNavController().navigate(
            TemplateCreatorRestoreBuiltinsDirections.actionRestoreBuiltinsToDimensions(),
            NavOptions.Builder()
                .setPopUpTo(R.id.restore_builtins, true)
                .build()
        )
    }

    private fun navigateBack() {
        activity?.setResult(Activity.RESULT_CANCELED)
        activity?.finish()
    }
}
