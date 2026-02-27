package org.wheatgenetics.coordinate.brapi

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.brapi.v2.model.geno.BrAPIPlate
import org.brapi.v2.model.geno.BrAPISample
import org.wheatgenetics.coordinate.BackActivity
import org.wheatgenetics.coordinate.R
import org.wheatgenetics.coordinate.brapi.service.BrapiGenotypingService
import org.wheatgenetics.coordinate.utils.InsetHandler

class BrapiImportActivity : BackActivity() {

    companion object {
        const val EXTRA_PLATE_DB_IDS = BrapiPlateListActivity.EXTRA_PLATE_DB_IDS
        const val EXTRA_GRID_ID = "extra_grid_id"
        private const val TAG = "BrapiImportActivity"
    }

    private lateinit var service: BrapiGenotypingService
    private lateinit var importer: BrapiGridImporter

    private var plate: BrAPIPlate? = null
    private var samples: List<BrAPISample> = emptyList()
    private var resolvedProgramName: String? = null
    private var resolvedStudyName: String? = null
    private var resolvedTrialName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_brapi_import)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            title = getString(R.string.brapi_import_title)
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
        }

        val rootView = findViewById<View>(android.R.id.content)
        InsetHandler.setupStandardInsets(rootView, toolbar)

        service = BrapiGenotypingService(this)
        importer = BrapiGridImporter(this)

        val plateDbIds = intent.getStringArrayListExtra(EXTRA_PLATE_DB_IDS) ?: emptyList<String>()
        val plateDbId = plateDbIds.firstOrNull()

        if (plateDbId == null) {
            Toast.makeText(this, R.string.brapi_not_configured, Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        val scrollView         = findViewById<View>(R.id.brapi_import_scroll)
        val plateNameText      = findViewById<TextView>(R.id.brapi_plate_name_text)
        val plateProgramText   = findViewById<TextView>(R.id.brapi_plate_program_text)
        val plateTrialText     = findViewById<TextView>(R.id.brapi_plate_trial_text)
        val plateStudyText     = findViewById<TextView>(R.id.brapi_plate_study_text)
        val plateFormatText    = findViewById<TextView>(R.id.brapi_plate_format_text)
        val plateSampleTypeText= findViewById<TextView>(R.id.brapi_plate_sample_type_text)
        val plateSampleCountText = findViewById<TextView>(R.id.brapi_plate_sample_count_text)
        val modeGroup          = findViewById<RadioGroup>(R.id.brapi_import_mode_group)
        val modeConfirm        = findViewById<RadioButton>(R.id.brapi_mode_empty)       // "Import and Confirm Only" → WITH_SAMPLES
        val modeCollect        = findViewById<RadioButton>(R.id.brapi_mode_with_samples) // "Collect New Samples" → EMPTY
        val progressBar        = findViewById<View>(R.id.brapi_import_progress)
        val importButton       = findViewById<Button>(R.id.brapi_import_btn)

        // Show only progress bar until data is loaded
        progressBar.visibility = View.VISIBLE
        scrollView.visibility = View.GONE
        importButton.visibility = View.GONE

        lifecycleScope.launch {
            try {
                val fetchedPlate   = withContext(Dispatchers.IO) { service.getPlate(plateDbId) }
                val fetchedSamples = withContext(Dispatchers.IO) { service.getSamplesForPlate(plateDbId) }

                plate   = fetchedPlate
                samples = fetchedSamples

                plateNameText.text = fetchedPlate?.plateName ?: plateDbId
                plateSampleCountText.text = getString(R.string.brapi_plate_sample_count, fetchedSamples.size)

                val programDbId = fetchedPlate?.programDbId
                val studyDbId   = fetchedPlate?.studyDbId
                val trialDbId   = fetchedPlate?.trialDbId

                if (!programDbId.isNullOrEmpty()) {
                    resolvedProgramName = withContext(Dispatchers.IO) {
                        runCatching { service.getProgramName(programDbId) }.getOrNull()
                    }
                }
                if (!studyDbId.isNullOrEmpty()) {
                    resolvedStudyName = withContext(Dispatchers.IO) {
                        runCatching { service.getStudyName(studyDbId) }.getOrNull()
                    }
                }
                if (!trialDbId.isNullOrEmpty()) {
                    resolvedTrialName = withContext(Dispatchers.IO) {
                        runCatching { service.getTrialName(trialDbId) }.getOrNull()
                    }
                }

                fun setDetail(tv: TextView, label: String, value: String?) {
                    if (value.isNullOrEmpty()) { tv.visibility = View.GONE }
                    else { tv.text = "$label: $value"; tv.visibility = View.VISIBLE }
                }
                setDetail(plateProgramText,    getString(R.string.brapi_detail_program),     resolvedProgramName)
                setDetail(plateTrialText,      getString(R.string.brapi_detail_trial),       resolvedTrialName)
                setDetail(plateStudyText,      getString(R.string.brapi_detail_study),       resolvedStudyName)
                setDetail(plateFormatText,     getString(R.string.brapi_detail_plate_format),fetchedPlate?.plateFormat?.toString())
                setDetail(plateSampleTypeText, getString(R.string.brapi_detail_sample_type), fetchedPlate?.sampleType?.toString())

                // "Read Only" requires samples to confirm; "Read + Write" is always available
                modeConfirm.isEnabled = fetchedSamples.isNotEmpty()
                if (fetchedSamples.isNotEmpty()) {
                    modeConfirm.isChecked = true
                    modeCollect.isChecked = false
                } else {
                    modeCollect.isChecked = true
                }

                // Reveal UI now that data is ready
                progressBar.visibility = View.GONE
                scrollView.visibility = View.VISIBLE
                importButton.visibility = View.VISIBLE
            } catch (e: Exception) {
                Log.e(TAG, "Error fetching plate/samples for plateDbId=$plateDbId", e)
                progressBar.visibility = View.GONE
                Toast.makeText(this@BrapiImportActivity,
                    getString(R.string.brapi_export_error, e.message ?: ""),
                    Toast.LENGTH_LONG).show()
            }
        }

        importButton.setOnClickListener {
            // brapi_mode_empty     = "Read Only"    → WITH_SAMPLES (confirm existing)
            // brapi_mode_with_samples = "Read + Write" → READ_WRITE  (confirm + collect new)
            val mode = when (modeGroup.checkedRadioButtonId) {
                R.id.brapi_mode_with_samples -> BrapiImportMode.READ_WRITE
                else -> BrapiImportMode.WITH_SAMPLES
            }
            doImport(mode, progressBar, importButton)
        }
    }

    private fun doImport(mode: BrapiImportMode, progressBar: View, importButton: Button) {
        val currentPlate = plate ?: return
        importButton.isEnabled = false
        progressBar.visibility = View.VISIBLE

        lifecycleScope.launch {
            try {
                val gridId = withContext(Dispatchers.IO) {
                    importer.importPlate(currentPlate, samples, mode,
                        resolvedProgramName, resolvedStudyName, resolvedTrialName)
                }
                val toast = if (mode == BrapiImportMode.WITH_SAMPLES || mode == BrapiImportMode.READ_WRITE)
                                R.string.brapi_import_samples_toast
                            else R.string.brapi_import_empty_toast
                Toast.makeText(this@BrapiImportActivity, toast, Toast.LENGTH_LONG).show()

                val resultIntent = android.content.Intent().apply { putExtra(EXTRA_GRID_ID, gridId) }
                setResult(RESULT_OK, resultIntent)
                finish()
            } catch (e: Exception) {
                Log.e(TAG, "doImport: import failed", e)
                progressBar.visibility = View.GONE
                importButton.isEnabled = true
                Toast.makeText(this@BrapiImportActivity,
                    getString(R.string.brapi_export_error, e.message ?: ""),
                    Toast.LENGTH_LONG).show()
            }
        }
    }
}
