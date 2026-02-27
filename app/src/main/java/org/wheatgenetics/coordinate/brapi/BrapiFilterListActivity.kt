package org.wheatgenetics.coordinate.brapi

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.launch
import org.brapi.v2.model.core.BrAPITrial
import org.wheatgenetics.coordinate.BackActivity
import org.wheatgenetics.coordinate.R
import org.wheatgenetics.coordinate.brapi.cache.BrapiEntityCache
import org.wheatgenetics.coordinate.brapi.cache.BrapiPlateCache
import org.wheatgenetics.coordinate.brapi.service.BrapiGenotypingService
import org.wheatgenetics.coordinate.utils.InsetHandler

/**
 * Full-screen filter selection activity used for all five BrAPI filter dimensions
 * (Program, Trial, Study, Season, Crop). Data is fetched lazily from BrAPI and cached
 * in [BrapiEntityCache]; subsequent opens reuse the cache.
 *
 * Extras in:
 *   [EXTRA_FILTER_TYPE]   – display label, e.g. "Program"
 *   [EXTRA_SELECTED_IDS]  – ArrayList<String> of currently active filter IDs
 *
 * Result extras (RESULT_OK):
 *   [RESULT_NAMES]  – ArrayList<String> of selected display names (for chips)
 *   [RESULT_IDS]    – ArrayList<String> of IDs used for plate matching
 */
class BrapiFilterListActivity : BackActivity() {

    companion object {
        const val EXTRA_FILTER_TYPE = "extra_filter_type"
        const val EXTRA_SELECTED_IDS = "extra_selected_ids"
        const val RESULT_NAMES = "result_names"
        const val RESULT_IDS = "result_ids"

        const val TYPE_PROGRAM = "Program"
        const val TYPE_TRIAL = "Trial"
        const val TYPE_STUDY = "Study"

        private const val TAG = "BrapiFilterList"
    }

    /**
     * Internal representation of one selectable filter item.
     * For Program/Trial/Study: [id] is the entity's DbId; [filterIds] == listOf(id).
     * For Season/Crop: [id] is the season/crop string; [filterIds] is the list of studyDbIds
     * whose study has that season/crop.
     */
    private data class FilterItem(
        val id: String,
        val label: String,
        val filterIds: List<String>,
    )

    private lateinit var filterType: String
    private lateinit var service: BrapiGenotypingService
    private lateinit var progressBar: View
    private lateinit var emptyText: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FilterAdapter

    private val allItems = mutableListOf<FilterItem>()
    private val selectedIds = mutableSetOf<String>()

    // ── Lifecycle ─────────────────────────────────────────────────────────────

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_brapi_filter_list)

        filterType = intent.getStringExtra(EXTRA_FILTER_TYPE) ?: run {
            Log.e(TAG, "onCreate: missing EXTRA_FILTER_TYPE")
            finish()
            return
        }

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            title = getString(R.string.brapi_filter_by_title, filterType)
            setDisplayHomeAsUpEnabled(true)
        }
        InsetHandler.setupStandardInsets(findViewById(android.R.id.content), toolbar)

        service = BrapiGenotypingService(this)
        progressBar = findViewById(R.id.filter_progress)
        emptyText = TextView(this).also { it.visibility = View.GONE }  // unused placeholder
        recyclerView = findViewById(R.id.filter_rv)

        adapter = FilterAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val preSelected = intent.getStringArrayListExtra(EXTRA_SELECTED_IDS) ?: emptyList<String>()
        selectedIds.addAll(preSelected)

        findViewById<MaterialButton>(R.id.filter_apply_btn).setOnClickListener { applyAndReturn() }

        loadData()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_brapi_filter_list, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_select_all -> {
                toggleSelectAll()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // ── Data loading ──────────────────────────────────────────────────────────

    private fun loadData() {
        progressBar.visibility = View.VISIBLE
        lifecycleScope.launch {
            try {
                val items = when (filterType) {
                    TYPE_PROGRAM -> buildProgramItems()
                    TYPE_TRIAL   -> buildTrialItems()
                    TYPE_STUDY   -> buildStudyItems()
                    else -> emptyList()
                }
                allItems.clear()
                allItems.addAll(items)
                progressBar.visibility = View.GONE
                if (items.isEmpty()) {
                    Toast.makeText(this@BrapiFilterListActivity,
                        getString(R.string.brapi_filter_no_items), Toast.LENGTH_SHORT).show()
                }
                adapter.notifyDataSetChanged()
            } catch (e: Exception) {
                Log.e(TAG, "loadData: error", e)
                progressBar.visibility = View.GONE
                Toast.makeText(this@BrapiFilterListActivity,
                    getString(R.string.brapi_export_error, e.message ?: ""),
                    Toast.LENGTH_LONG).show()
            }
        }
    }

    private suspend fun getPrograms() = run {
        BrapiEntityCache.loadPrograms(this)?.let { return@run it }
        val programs = service.getPrograms()
        BrapiEntityCache.savePrograms(this, programs)
        programs
    }

    private suspend fun getTrials(): List<BrAPITrial> {
        BrapiEntityCache.loadTrials(this)?.let { return it }
        val trials = service.getTrials()
        BrapiEntityCache.saveTrials(this, trials)
        return trials
    }

    private suspend fun buildProgramItems(): List<FilterItem> {
        // Determine which programDbIds are represented in the cached plates
        val platesInCache = BrapiPlateCache.load(this) ?: emptyList()
        val programIdsInPlates = platesInCache.mapNotNull { it.programDbId }.toSet()

        // Fetch the full programs list from BrAPI and restrict to those that have plates
        return getPrograms()
            .filter { p -> p.programDbId != null && p.programDbId in programIdsInPlates }
            .mapNotNull { p ->
                val id = p.programDbId ?: return@mapNotNull null
                val name = p.programName ?: return@mapNotNull null
                id to name
            }
            .distinctBy { it.first }
            .sortedBy { it.second }
            .map { (id, name) -> FilterItem(id, name, listOf(id)) }
    }

    private suspend fun buildTrialItems(): List<FilterItem> =
        getTrials()
            .mapNotNull { t ->
                val id = t.trialDbId ?: return@mapNotNull null
                val name = t.trialName ?: return@mapNotNull null
                id to name
            }
            .distinctBy { it.first }
            .sortedBy { it.second }
            .map { (id, name) -> FilterItem(id, name, listOf(id)) }

    private suspend fun buildStudyItems(): List<FilterItem> {
        val studies = run {
            BrapiEntityCache.loadStudies(this)?.let { return@run it }
            val fetched = service.getStudies()
            BrapiEntityCache.saveStudies(this, fetched)
            fetched
        }
        return studies
            .mapNotNull { s ->
                val id = s.studyDbId ?: return@mapNotNull null
                val name = s.studyName ?: return@mapNotNull null
                id to name
            }
            .distinctBy { it.first }
            .sortedBy { it.second }
            .map { (id, name) -> FilterItem(id, name, listOf(id)) }
    }

    // ── Select all ────────────────────────────────────────────────────────────

    private fun toggleSelectAll() {
        val allSelected = allItems.all { it.id in selectedIds }
        if (allSelected) {
            selectedIds.clear()
        } else {
            allItems.forEach { selectedIds.add(it.id) }
        }
        adapter.notifyDataSetChanged()
    }

    // ── Apply / return ────────────────────────────────────────────────────────

    private fun applyAndReturn() {
        val selected = allItems.filter { it.id in selectedIds }
        val names = ArrayList(selected.map { it.label })
        val ids = ArrayList(selected.flatMap { it.filterIds }.distinct())
        val data = Intent().apply {
            putStringArrayListExtra(RESULT_NAMES, names)
            putStringArrayListExtra(RESULT_IDS, ids)
        }
        setResult(RESULT_OK, data)
        finish()
    }

    // ── Adapter ───────────────────────────────────────────────────────────────

    inner class FilterAdapter : RecyclerView.Adapter<FilterAdapter.VH>() {

        inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val name: TextView = itemView.findViewById(R.id.filter_item_name)
            val checkbox: CheckBox = itemView.findViewById(R.id.filter_item_cb)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
            val v = layoutInflater.inflate(R.layout.item_brapi_filter, parent, false)
            return VH(v)
        }

        override fun getItemCount(): Int = allItems.size

        override fun onBindViewHolder(holder: VH, position: Int) {
            val item = allItems[position]
            holder.name.text = item.label
            holder.checkbox.setOnCheckedChangeListener(null)
            holder.checkbox.isChecked = item.id in selectedIds
            holder.checkbox.setOnCheckedChangeListener { _, checked ->
                if (checked) selectedIds.add(item.id) else selectedIds.remove(item.id)
            }
            holder.itemView.setOnClickListener {
                holder.checkbox.isChecked = !holder.checkbox.isChecked
            }
        }
    }
}
