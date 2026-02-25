package org.wheatgenetics.coordinate.brapi

import android.content.ContentValues
import android.content.Context
import android.util.Log
import androidx.preference.PreferenceManager
import org.brapi.v2.model.geno.BrAPIPlate
import org.brapi.v2.model.geno.BrAPISample
import org.json.JSONArray
import org.json.JSONObject
import org.wheatgenetics.coordinate.database.Database
import org.wheatgenetics.coordinate.preference.GeneralKeys

enum class BrapiImportMode {
    /** Insert 96 blank cells; user enters sample names in CollectorActivity. */
    EMPTY,
    /** Insert 96 pre-filled cells from existing samples; user confirms in ImportedCollectorActivity. */
    WITH_SAMPLES,
}

/**
 * Creates a BrAPI grid in the database from a BrAPI plate.
 *
 * Follows the same direct-SQLite pattern as ImportedGridImporter:
 *  - Inserts a sentinel template (type=5, 8×12)
 *  - Inserts a grid row with plate metadata in the options JSON
 *  - Inserts 96 entry rows
 *
 * All work is synchronous and must be called from a background thread.
 */
class BrapiGridImporter(private val context: Context) {

    /**
     * Imports a single plate and returns the newly created grid ID.
     *
     * @param plate     BrAPI plate metadata
     * @param samples   List of BrAPI samples (may be empty for EMPTY mode)
     * @param mode      Whether to import empty (collect) or with samples (confirm)
     */
    fun importPlate(
        plate: BrAPIPlate,
        samples: List<BrAPISample>,
        mode: BrapiImportMode,
    ): Long {
        Log.d(TAG, "importPlate: plateDbId=${plate.plateDbId}, plateName=${plate.plateName}, mode=$mode, sampleCount=${samples.size}")
        val db = Database.db(context)
        val now = System.currentTimeMillis()
        val person = PreferenceManager.getDefaultSharedPreferences(context)
            .getString(GeneralKeys.PERSON_NAME, "") ?: ""
        Log.d(TAG, "importPlate: person=$person, timestamp=$now")

        // 1. Insert sentinel template (8×12, BRAPI type=5)
        val templateCv = ContentValues().apply {
            put("title", "BrAPI Plate")
            put("type", 5) // TemplateType.BRAPI
            put("rows", 8)
            put("cols", 12)
            put("erand", 0)
            putNull("ecells")
            putNull("erows")
            putNull("ecols")
            put("cnumb", 1) // numeric columns (1-12)
            put("rnumb", 0) // alphabetic rows (A-H)
            putNull("entryLabel")
            putNull("options")
            put("stamp", now)
        }
        val templateId = db.insert("templates", null, templateCv)
        Log.d(TAG, "importPlate: inserted template, templateId=$templateId")
        check(templateId >= 0) { "Failed to create BrAPI sentinel template" }

        // 2. Build plate-level optional fields JSON
        val optionsJson = buildPlateOptionsJson(plate, person)
        Log.d(TAG, "importPlate: optionsJson=$optionsJson")

        // 3. Insert grid row
        val gridCv = ContentValues().apply {
            put("temp", templateId)
            putNull("projectId")
            put("person", person.ifEmpty { null })
            put("activeRow", 0)
            put("activeCol", 0)
            put("options", optionsJson)
            put("stamp", now)
        }
        val gridId = db.insert("grids", null, gridCv)
        Log.d(TAG, "importPlate: inserted grid, gridId=$gridId")
        check(gridId >= 0) { "Failed to create BrAPI grid" }

        // 4. Build well→sample map for WITH_SAMPLES mode
        val wellToSample: Map<String, BrAPISample> = if (mode == BrapiImportMode.WITH_SAMPLES) {
            samples.associateBy { computeWell(it) }
        } else {
            emptyMap()
        }

        Log.d(TAG, "importPlate: wellToSample has ${wellToSample.size} mapped well(s)")
        if (mode == BrapiImportMode.WITH_SAMPLES) {
            wellToSample.forEach { (well, s) ->
                Log.d(TAG, "  wellToSample[$well]: sampleDbId=${s.sampleDbId}, sampleName=${s.sampleName}")
            }
        }

        // 5. Insert entries for all 96 wells (rows 1-8, cols 1-12)
        Log.d(TAG, "importPlate: inserting 96 entries for gridId=$gridId")
        var insertedCount = 0
        for (row in 1..8) {
            for (col in 1..12) {
                val well = wellForRowCol(row, col)
                val entryCv = ContentValues()
                entryCv.put("grid", gridId)
                entryCv.put("row", row)
                entryCv.put("col", col)
                entryCv.put("stamp", now)

                if (mode == BrapiImportMode.WITH_SAMPLES) {
                    val sample = wellToSample[well]
                    if (sample == null) {
                        Log.d(TAG, "  $well: no sample mapped – inserting empty")
                    } else {
                        Log.d(TAG, "  $well: sampleDbId=${sample.sampleDbId}, sampleName=${sample.sampleName}, germplasmDbId=${sample.germplasmDbId}")
                    }
                    entryCv.put("edata", sample?.sampleName ?: "")
                    val originalJson = sample?.let { buildOriginalValueJson(it) }
                    entryCv.put("original_value", originalJson)
                    entryCv.putNull("confirmed_timestamp")
                } else {
                    entryCv.putNull("edata")
                    entryCv.putNull("original_value")
                    entryCv.putNull("confirmed_timestamp")
                }

                val rowId = db.insert("entries", null, entryCv)
                if (rowId < 0) {
                    Log.w(TAG, "importPlate: failed to insert entry for well=$well (row=$row, col=$col)")
                } else {
                    insertedCount++
                }
            }
        }
        Log.d(TAG, "importPlate: finished inserting $insertedCount/96 entries for gridId=$gridId")

        return gridId
    }

    private fun buildPlateOptionsJson(plate: BrAPIPlate, takenBy: String): String {
        val array = JSONArray()
        fun addField(field: String, value: String?, hint: String = "") {
            array.put(
                JSONObject().apply {
                    put("field", field)
                    put("hint", hint)
                    put("value", value ?: "")
                    put("checked", true)
                },
            )
        }
        addField("plateDbId", plate.plateDbId, "Plate DB ID")
        addField("plateName", plate.plateName, "Plate Name")
        addField("programDbId", plate.programDbId, "Program DB ID")
        addField("trialDbId", plate.trialDbId, "Trial DB ID")
        addField("studyDbId", plate.studyDbId, "Study DB ID")
        addField("takenBy", takenBy.ifEmpty { null }, "Person who collected samples")
        return array.toString()
    }

    private fun buildOriginalValueJson(sample: BrAPISample): String {
        return JSONObject().apply {
            put("sampleDbId", sample.sampleDbId ?: "")
            put("germplasmDbId", sample.germplasmDbId ?: "")
            put("germplasmName", "")
        }.toString()
    }

    private fun computeWell(sample: BrAPISample): String {
        val row = sample.row ?: return ""
        val col = sample.column ?: return ""
        return "$row${String.format("%02d", col)}"
    }

    companion object {
        private const val TAG = "BrapiGridImporter"
        private val ROW_LETTERS = "ABCDEFGH"

        fun wellForRowCol(row: Int, col: Int): String {
            return "${ROW_LETTERS[row - 1]}${String.format("%02d", col)}"
        }
    }
}
