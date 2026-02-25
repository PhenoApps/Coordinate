package org.wheatgenetics.coordinate.brapi

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
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
    }

    private lateinit var service: BrapiGenotypingService
    private lateinit var importer: BrapiGridImporter

    private var plate: BrAPIPlate? = null
    private var samples: List<BrAPISample> = emptyList()

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

        val plateNameText = findViewById<TextView>(R.id.brapi_plate_name_text)
        val plateStudyText = findViewById<TextView>(R.id.brapi_plate_study_text)
        val plateSampleCountText = findViewById<TextView>(R.id.brapi_plate_sample_count_text)
        val modeGroup = findViewById<RadioGroup>(R.id.brapi_import_mode_group)
        val modeWithSamples = findViewById<RadioButton>(R.id.brapi_mode_with_samples)
        val progressBar = findViewById<ProgressBar>(R.id.brapi_import_progress)
        val importButton = findViewById<Button>(R.id.brapi_import_btn)

        importButton.isEnabled = false

        lifecycleScope.launch {
            progressBar.visibility = View.VISIBLE
            try {
                val fetchedPlate = withContext(Dispatchers.IO) { service.getPlate(plateDbId) }
                val fetchedSamples = withContext(Dispatchers.IO) { service.getSamplesForPlate(plateDbId) }

                plate = fetchedPlate
                samples = fetchedSamples

                plateNameText.text = fetchedPlate?.plateName ?: plateDbId
                plateStudyText.text = fetchedPlate?.studyDbId ?: ""
                plateSampleCountText.text = getString(
                    R.string.brapi_plate_sample_count,
                    fetchedSamples.size,
                    96,
                )

                if (fetchedSamples.isNotEmpty()) {
                    modeWithSamples.isEnabled = true
                }
                importButton.isEnabled = true
            } catch (e: Exception) {
                Toast.makeText(
                    this@BrapiImportActivity,
                    getString(R.string.brapi_export_error, e.message ?: ""),
                    Toast.LENGTH_LONG,
                ).show()
            } finally {
                progressBar.visibility = View.GONE
            }
        }

        importButton.setOnClickListener {
            val mode = when (modeGroup.checkedRadioButtonId) {
                R.id.brapi_mode_with_samples -> BrapiImportMode.WITH_SAMPLES
                else -> BrapiImportMode.EMPTY
            }
            doImport(mode, progressBar, importButton)
        }
    }

    private fun doImport(
        mode: BrapiImportMode,
        progressBar: ProgressBar,
        importButton: Button,
    ) {
        val currentPlate = plate ?: return
        importButton.isEnabled = false
        progressBar.visibility = View.VISIBLE

        lifecycleScope.launch {
            try {
                val gridId = withContext(Dispatchers.IO) {
                    importer.importPlate(currentPlate, samples, mode)
                }
                val toast = if (mode == BrapiImportMode.EMPTY) {
                    R.string.brapi_import_empty_toast
                } else {
                    R.string.brapi_import_samples_toast
                }
                Toast.makeText(this@BrapiImportActivity, toast, Toast.LENGTH_LONG).show()

                val resultIntent = android.content.Intent().apply {
                    putExtra(EXTRA_GRID_ID, gridId)
                }
                setResult(RESULT_OK, resultIntent)
                finish()
            } catch (e: Exception) {
                progressBar.visibility = View.GONE
                importButton.isEnabled = true
                Toast.makeText(
                    this@BrapiImportActivity,
                    getString(R.string.brapi_export_error, e.message ?: ""),
                    Toast.LENGTH_LONG,
                ).show()
            }
        }
    }
}
