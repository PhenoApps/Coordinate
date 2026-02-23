package org.wheatgenetics.coordinate.fragments.grid_creator

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.preference.PreferenceManager
import com.google.zxing.integration.android.IntentIntegrator
import org.wheatgenetics.coordinate.R
import org.wheatgenetics.coordinate.StringGetter
import org.wheatgenetics.coordinate.adapter.FieldsAdapter
import org.wheatgenetics.coordinate.database.TemplatesTable
import org.wheatgenetics.coordinate.interfaces.FieldsAdapterListener
import org.wheatgenetics.coordinate.model.TemplateModel
import org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields
import org.wheatgenetics.coordinate.preference.GeneralKeys

class GridCreatorTemplateFields : Fragment(R.layout.fragment_grid_creator_fields),
    FieldsAdapterListener,
    StringGetter {

    private val args: GridCreatorTemplateFieldsArgs by navArgs()
    private val viewModel: GridCreatorViewModel by activityViewModels()

    private var mTemplatesTable: TemplatesTable? = null

    private lateinit var fieldsAdapter: FieldsAdapter
    private var templateFields: NonNullOptionalFields? = null

    private val mTemplate: TemplateModel? by lazy {
        mTemplatesTable?.load()?.find { it.title == args.title }
    }

    private val barcodeScannerLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result -> parseActivityResult(result.resultCode, result.data) }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity?.let { act ->
            mTemplatesTable = TemplatesTable(act)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
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

    override fun onBarcodeScanRequested() {
        IntentIntegrator(requireActivity()).apply {
            setOrientationLocked(false)
            setPrompt("Scan a barcode")
            setBeepEnabled(true)
        }.createScanIntent().also {
            barcodeScannerLauncher.launch(it)
        }
    }

    private fun parseActivityResult(resultCode: Int, data: Intent?): Boolean {
        val result = IntentIntegrator.parseActivityResult(resultCode, data)
        if (result == null) {
            return false
        } else {
            val barcodeScannerResult = result.contents
            if (barcodeScannerResult != null && barcodeScannerResult.isNotEmpty()) {
                setRequiredField(barcodeScannerResult)
            }
            return true
        }
    }

    private fun setupButtons() {

        val titleTextView = view?.findViewById<TextView>(R.id.frag_grid_creator_fields_title_tv)
        val okButton = view?.findViewById<Button>(R.id.frag_next_btn)
        val backButton = view?.findViewById<Button>(R.id.frag_back_btn)

        titleTextView?.text = args.title

        setDisabledNext()

        okButton?.setOnClickListener {
            viewModel.template = mTemplate
            viewModel.optionalFields = getOptionalFields()
            findNavController().navigate(GridCreatorTemplateFieldsDirections
                .actionTemplateFieldsToProjectOptions())
        }

        backButton?.setOnClickListener {
            navigateBack()
        }
    }

    private fun navigateBack() {
        val templateId = activity?.intent?.getLongExtra("templateId", -1L)
        if (templateId != -1L || !findNavController().popBackStack()) {
            // Either templateId fast-path skipped template_options, or template_options was
            // auto-popped (e.g. hide-templates single-template path) — finish the activity.
            activity?.setResult(Activity.RESULT_CANCELED)
            activity?.finish()
        }
    }

    //check that the base optional field "identification" value is entered
    private fun setRequiredField(text: String) {

        val requiredName = getRequiredName()

        templateFields?.forEachIndexed { index, field ->
            if (field.name == requiredName) {
                field.value = text
                fieldsAdapter.notifyItemChanged(index)
            }
        }
    }

    //check that the base optional field "identification" value is entered
    private fun checkRequiredFieldsEntered(): Boolean {

        val requiredName = getRequiredName()

        return templateFields?.any { it.name == requiredName && it.value.isNotBlank() } ?: false
    }

    private fun getRequiredName(): String = when (args.title) {
        getString(R.string.DNADefaultTemplateTitle) -> getString(R.string.NonNullOptionalFieldsPlateIDFieldName)
        getString(R.string.SeedDefaultTemplateTitle) -> getString(R.string.NonNullOptionalFieldsTrayIDFieldName)
        getString(R.string.HTPGDefaultTemplateTitle) -> getString(R.string.NonNullOptionalFieldsHTPG)
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

                // set hint text for optional fields (preserve existing hint if set by template)
                fields.forEach { field ->
                    if (field.name != requiredName && field.hint.isEmpty()) {
                       field.hint = getString(R.string.optional_field_hint_text)
                    }
                }

                // pre-fill person-like fields from profile preference
                val savedPerson = PreferenceManager.getDefaultSharedPreferences(act)
                    .getString(GeneralKeys.PERSON_NAME, "") ?: ""
                if (savedPerson.isNotEmpty()) {
                    fields.forEach { field ->
                        val nameLower = field.name.lowercase()
                        if (nameLower == "person" || nameLower == "name" ||
                            nameLower == "operator" || nameLower == "username") {
                            field.value = savedPerson
                        }
                    }
                }

                // pre-fill Date field with today's date for HTPG template
                if (args.title == getString(R.string.HTPGDefaultTemplateTitle)) {
                    val today = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())
                        .format(java.util.Date())
                    fields.forEach { field ->
                        if (field.name.equals("date", ignoreCase = true)) {
                            field.value = today
                        }
                    }
                }

                templateFields = fields

                fieldsAdapter = FieldsAdapter(this, requiredName)

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

    private fun getOptionalFields(): NonNullOptionalFields {

        val fields = NonNullOptionalFields(this)

        templateFields?.forEach { field ->
            fields.add(field.name, field.value, null)
        }

        return fields
    }

    override fun get(resId: Int): String? = activity?.getString(resId)

    @SuppressLint("ResourceType")
    override fun getQuantity(resId: Int, quantity: Int, vararg formatArgs: Any?) = activity?.getString(resId, formatArgs) ?: String()

    override fun onRequiredFieldCompleted() {

        if (checkRequiredFieldsEntered()) setNextText() else setDisabledNext()

    }
}
