package org.wheatgenetics.coordinate.brapi

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kotlinx.coroutines.launch
import org.brapi.v2.model.geno.BrAPIPlate
import org.wheatgenetics.coordinate.BackActivity
import org.wheatgenetics.coordinate.R
import org.wheatgenetics.coordinate.brapi.BrapiFilterListActivity.Companion.TYPE_PROGRAM
import org.wheatgenetics.coordinate.brapi.BrapiFilterListActivity.Companion.TYPE_STUDY
import org.wheatgenetics.coordinate.brapi.BrapiFilterListActivity.Companion.TYPE_TRIAL
import org.wheatgenetics.coordinate.brapi.cache.BrapiEntityCache
import org.wheatgenetics.coordinate.brapi.cache.BrapiPlateCache
import org.wheatgenetics.coordinate.brapi.service.BrapiGenotypingService
import org.wheatgenetics.coordinate.brapi.views.SearchBar
import org.wheatgenetics.coordinate.utils.InsetHandler

class BrapiPlateListActivity : BackActivity() {

    companion object {
        const val EXTRA_PLATE_DB_IDS = "extra_plate_db_ids"
        private const val TAG = "BrapiPlateList"
    }

    private lateinit var adapter: BrapiPlateAdapter
    private lateinit var service: BrapiGenotypingService
    private lateinit var viewModel: BrapiPlateListViewModel
    private lateinit var searchBar: SearchBar
    private lateinit var filterChips: ChipGroup
    private lateinit var filterScroll: View
    private lateinit var emptyText: TextView
    private lateinit var progressBar: View
    private lateinit var importButton: Button
    private var clearFiltersItem: MenuItem? = null
    private var deselectAllItem: MenuItem? = null

    /** Which filter type was most recently launched, so the result callback knows where to store it */
    private var pendingFilterType: String? = null

    private val filterLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode != RESULT_OK) return@registerForActivityResult
        val names = result.data?.getStringArrayListExtra(BrapiFilterListActivity.RESULT_NAMES)
            ?: return@registerForActivityResult
        val ids = result.data?.getStringArrayListExtra(BrapiFilterListActivity.RESULT_IDS)
            ?: return@registerForActivityResult
        val filterState = when (pendingFilterType) {
            TYPE_PROGRAM -> viewModel.programFilter
            TYPE_TRIAL   -> viewModel.trialFilter
            TYPE_STUDY   -> viewModel.studyFilter
            else -> return@registerForActivityResult
        }
        filterState.set(ids, names)
        rebuildChips()
        refilter()
    }

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
        InsetHandler.setupStandardInsetsWithIme(rootView, toolbar)

        viewModel = ViewModelProvider(this).get(BrapiPlateListViewModel::class.java)
        service = BrapiGenotypingService(this)

        searchBar = findViewById(R.id.brapi_search_bar)
        filterChips = findViewById(R.id.brapi_filter_chips)
        filterScroll = findViewById(R.id.brapi_filter_scroll)
        emptyText = findViewById(R.id.brapi_empty_text)
        progressBar = findViewById(R.id.brapi_progress)
        importButton = findViewById(R.id.brapi_import_button)

        val recyclerView = findViewById<RecyclerView>(R.id.brapi_plate_recycler)
        adapter = BrapiPlateAdapter { selectedCount ->
            importButton.visibility = if (selectedCount > 0) View.VISIBLE else View.GONE
            deselectAllItem?.isVisible = selectedCount > 0
        }
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        importButton.setOnClickListener {
            val selected = ArrayList(adapter.getSelectedPlateDbIds())
            if (selected.isNotEmpty()) {
                Log.d(TAG, "Import: ${selected.size} plate(s) selected")
                val intent = Intent(this, BrapiImportActivity::class.java)
                intent.putStringArrayListExtra(EXTRA_PLATE_DB_IDS, selected)
                setResult(RESULT_OK, intent)
                finish()
            }
        }

        searchBar.editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
            override fun afterTextChanged(s: Editable?) { refilter() }
        })

        val inMemory = viewModel.plates
        if (inMemory != null) {
            Log.d(TAG, "onCreate: ${inMemory.size} in-memory plate(s)")
            lifecycleScope.launch {
                val imported = kotlinx.coroutines.withContext(kotlinx.coroutines.Dispatchers.IO) {
                    BrapiGridImporter.getImportedPlateDbIds(this@BrapiPlateListActivity)
                }
                val filtered = inMemory.filter { it.plateDbId !in imported }
                viewModel.plates = filtered
                onDataReady(filtered)
            }
        } else {
            val fileCached = BrapiPlateCache.load(this)
            if (fileCached != null) {
                Log.d(TAG, "onCreate: ${fileCached.size} file-cached plate(s)")
                lifecycleScope.launch {
                    val imported = kotlinx.coroutines.withContext(kotlinx.coroutines.Dispatchers.IO) {
                        BrapiGridImporter.getImportedPlateDbIds(this@BrapiPlateListActivity)
                    }
                    val filtered = fileCached.filter { it.plateDbId !in imported }
                    viewModel.plates = filtered
                    onDataReady(filtered)
                }
            } else {
                loadPlates()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_brapi_plate_list, menu)
        clearFiltersItem = menu.findItem(R.id.action_clear_filters)
        deselectAllItem = menu.findItem(R.id.action_deselect_all)
        updateClearFiltersVisibility()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_brapi_filter -> { showFilterChoiceDialog(); true }
            R.id.action_deselect_all -> { adapter.clearSelection(); true }
            R.id.action_clear_filters -> {
                viewModel.clearAllFilters()
                rebuildChips()
                refilter()
                true
            }
            R.id.action_reset_cache -> {
                BrapiPlateCache.delete(this)
                BrapiEntityCache.delete(this)
                viewModel.plates = null
                viewModel.clearAllFilters()
                adapter.setPlates(emptyList())
                adapter.setDisplayList(emptyList())
                rebuildChips()
                progressBar.visibility = View.VISIBLE
                emptyText.visibility = View.GONE
                loadPlates()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // ── Data loading ─────────────────────────────────────────────────────────

    private fun loadPlates() {
        Log.d(TAG, "loadPlates: start")
        progressBar.visibility = View.VISIBLE
        emptyText.visibility = View.GONE

        lifecycleScope.launch {
            try {
                val plates = service.getPlates()
                Log.d(TAG, "loadPlates: ${plates.size} plate(s)")

                val imported = kotlinx.coroutines.withContext(kotlinx.coroutines.Dispatchers.IO) {
                    BrapiGridImporter.getImportedPlateDbIds(this@BrapiPlateListActivity)
                }
                val filtered = plates.filter { it.plateDbId !in imported }
                Log.d(TAG, "loadPlates: ${filtered.size} plate(s) after excluding ${imported.size} already-imported")

                if (filtered.isEmpty()) {
                    progressBar.visibility = View.GONE
                    emptyText.visibility = View.VISIBLE
                    return@launch
                }

                BrapiPlateCache.save(this@BrapiPlateListActivity, plates)
                viewModel.plates = filtered
                progressBar.visibility = View.GONE
                onDataReady(filtered)
            } catch (e: Exception) {
                Log.e(TAG, "loadPlates: error", e)
                progressBar.visibility = View.GONE
                emptyText.visibility = View.VISIBLE
                Toast.makeText(
                    this@BrapiPlateListActivity,
                    getString(R.string.brapi_export_error, e.message ?: ""),
                    Toast.LENGTH_LONG,
                ).show()
            }
        }
    }

    private fun onDataReady(plates: List<BrAPIPlate>) {
        adapter.setPlates(plates)
        populateAutocomplete(plates)
        rebuildChips()
        refilter()
    }

    // ── Search & filter ───────────────────────────────────────────────────────

    private fun refilter() {
        val query = searchBar.editText.text?.toString() ?: ""
        val filtered = viewModel.filteredAndSearched(query)
        adapter.setDisplayList(filtered)
        val allEmpty = viewModel.plates?.isNotEmpty() == true && filtered.isEmpty()
        emptyText.visibility = if (allEmpty) View.VISIBLE else View.GONE
        updateSearchHint(filtered.size)
    }

    private fun updateSearchHint(count: Int) {
        searchBar.editText.hint = resources.getQuantityString(R.plurals.brapi_search_plates, count, count)
    }

    private fun populateAutocomplete(plates: List<BrAPIPlate>) {
        val suggestions = plates.flatMap { p ->
            listOfNotNull(p.plateName, p.plateDbId, p.studyDbId, p.programDbId, p.trialDbId)
        }.distinct().sorted()
        val ac = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, suggestions)
        searchBar.editText.setAdapter(ac)
        searchBar.editText.threshold = 1
    }

    // ── Filter dialog ─────────────────────────────────────────────────────────

    private fun showFilterChoiceDialog() {
        val plates = viewModel.plates ?: emptyList()
        val programCount = plates.mapNotNull { it.programDbId }.distinct().size
        val trialCount   = plates.mapNotNull { it.trialDbId   }.distinct().size
        val studyCount   = plates.mapNotNull { it.studyDbId   }.distinct().size

        val choices = arrayOf(
            getString(R.string.brapi_filter_programs, programCount.toString()),
            getString(R.string.brapi_filter_trials,   trialCount.toString()),
            getString(R.string.brapi_filter_studies,  studyCount.toString()),
        )
        val types = arrayOf(TYPE_PROGRAM, TYPE_TRIAL, TYPE_STUDY)
        val filterStates = arrayOf(
            viewModel.programFilter, viewModel.trialFilter, viewModel.studyFilter,
        )

        AlertDialog.Builder(this)
            .setTitle(R.string.brapi_filter_dialog_title)
            .setItems(choices) { _, which ->
                launchFilter(types[which], filterStates[which].ids)
            }
            .show()
    }

    private fun launchFilter(type: String, currentIds: Set<String>) {
        pendingFilterType = type
        filterLauncher.launch(
            Intent(this, BrapiFilterListActivity::class.java).apply {
                putExtra(BrapiFilterListActivity.EXTRA_FILTER_TYPE, type)
                putStringArrayListExtra(
                    BrapiFilterListActivity.EXTRA_SELECTED_IDS, ArrayList(currentIds))
            }
        )
    }

    // ── Chips ──────────────────────────────────────────────────────────────────

    private fun rebuildChips() {
        filterChips.removeAllViews()

        fun addChips(state: BrapiPlateListViewModel.FilterState) {
            for (name in state.names.toList()) {
                val chip = Chip(this).apply {
                    text = name
                    isCloseIconVisible = true
                    isCheckable = false
                    setOnCloseIconClickListener {
                        state.removeName(name)
                        rebuildChips()
                        refilter()
                    }
                }
                filterChips.addView(chip)
            }
        }

        addChips(viewModel.programFilter)
        addChips(viewModel.trialFilter)
        addChips(viewModel.studyFilter)

        filterScroll.visibility = if (filterChips.childCount > 0) View.VISIBLE else View.GONE
        updateClearFiltersVisibility()
    }

    private fun updateClearFiltersVisibility() {
        clearFiltersItem?.isVisible = viewModel.hasActiveFilters()
    }
}
