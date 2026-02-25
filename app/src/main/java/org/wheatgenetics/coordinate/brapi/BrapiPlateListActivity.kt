package org.wheatgenetics.coordinate.brapi

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import org.brapi.v2.model.geno.BrAPIPlate
import org.wheatgenetics.coordinate.BackActivity
import org.wheatgenetics.coordinate.R
import org.wheatgenetics.coordinate.brapi.service.BrapiGenotypingService
import org.wheatgenetics.coordinate.utils.InsetHandler

class BrapiPlateListActivity : BackActivity() {

    companion object {
        const val EXTRA_PLATE_DB_IDS = "extra_plate_db_ids"
        private const val TAG = "BrapiPlateList"
    }

    private lateinit var adapter: BrapiPlateAdapter
    private lateinit var service: BrapiGenotypingService
    private var allPlates: List<BrAPIPlate> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_brapi_plate_list)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            title = getString(R.string.brapi_plate_list_title)
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
        }

        val rootView = findViewById<View>(android.R.id.content)
        InsetHandler.setupStandardInsets(rootView, toolbar)

        service = BrapiGenotypingService(this)

        val recyclerView = findViewById<RecyclerView>(R.id.brapi_plate_recycler)
        val progressBar = findViewById<ProgressBar>(R.id.brapi_progress)
        val emptyText = findViewById<TextView>(R.id.brapi_empty_text)
        val importButton = findViewById<Button>(R.id.brapi_import_button)
        val filterEdit = findViewById<EditText>(R.id.brapi_filter_edit)

        adapter = BrapiPlateAdapter { selectedCount ->
            importButton.isEnabled = selectedCount > 0
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        importButton.setOnClickListener {
            val selected = ArrayList(adapter.getSelectedPlateDbIds())
            Log.d(TAG, "Import button clicked: ${selected.size} plate(s) selected: $selected")
            if (selected.isNotEmpty()) {
                val intent = Intent(this, BrapiImportActivity::class.java)
                intent.putStringArrayListExtra(EXTRA_PLATE_DB_IDS, selected)
                setResult(RESULT_OK, intent)
                finish()
            }
        }

        filterEdit.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val query = s?.toString()?.trim() ?: ""
                adapter.filter(query)
                emptyText.visibility =
                    if (adapter.itemCount == 0 && allPlates.isNotEmpty()) View.VISIBLE else View.GONE
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        loadPlates(progressBar, emptyText)
    }

    private fun loadPlates(progressBar: ProgressBar, emptyText: TextView) {
        Log.d(TAG, "loadPlates: starting plate list fetch")
        progressBar.visibility = View.VISIBLE
        emptyText.visibility = View.GONE

        lifecycleScope.launch {
            try {
                Log.d(TAG, "loadPlates: calling service.getPlates()")
                allPlates = service.getPlates()
                Log.d(TAG, "loadPlates: received ${allPlates.size} plate(s)")
                allPlates.forEachIndexed { i, p ->
                    Log.d(TAG, "  plate[$i]: plateDbId=${p.plateDbId}, plateName=${p.plateName}, studyDbId=${p.studyDbId}")
                }
                adapter.setPlates(allPlates)
                progressBar.visibility = View.GONE
                if (allPlates.isEmpty()) {
                    Log.d(TAG, "loadPlates: no plates returned – showing empty state")
                    emptyText.visibility = View.VISIBLE
                }
            } catch (e: Exception) {
                Log.e(TAG, "loadPlates: error fetching plates", e)
                progressBar.visibility = View.GONE
                Toast.makeText(
                    this@BrapiPlateListActivity,
                    getString(R.string.brapi_export_error, e.message ?: ""),
                    Toast.LENGTH_LONG,
                ).show()
                emptyText.visibility = View.VISIBLE
            }
        }
    }
}
