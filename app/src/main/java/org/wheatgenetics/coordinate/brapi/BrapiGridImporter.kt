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
    /** Read Only: pre-fill cells from existing samples; user confirms in ImportedCollectorActivity. */
    WITH_SAMPLES,
    /** Read + Write: pre-fill cells from existing samples; user confirms existing AND collects new. */
    READ_WRITE,
}

/**
 * Creates a BrAPI grid in the database from a BrAPI plate.
 */
class BrapiGridImporter(private val context: Context) {

    fun importPlate(
        plate: BrAPIPlate,
        samples: List<BrAPISample>,
        mode: BrapiImportMode,
        programName: String? = null,
        studyName: String? = null,
        trialName: String? = null,
    ): Long {
        Log.d(TAG, "importPlate: plateDbId=${plate.plateDbId}, plateName=${plate.plateName}, mode=$mode, sampleCount=${samples.size}")
        val db = Database.db(context)
        val now = System.currentTimeMillis()
        val person = PreferenceManager.getDefaultSharedPreferences(context)
            .getString(GeneralKeys.PERSON_NAME, "") ?: ""

        val templateId: Long = db.query(
            "templates", arrayOf("_id"),
            "title=?", arrayOf("BrAPI Plate"),
            null, null, null
        ).use { cursor ->
            if (cursor.moveToFirst()) cursor.getLong(0) else -1L
        }.let { existing ->
            if (existing >= 0) {
                existing
            } else {
                val templateCv = ContentValues().apply {
                    put("title", "BrAPI Plate")
                    put("type", 5)
                    put("rows", 8)
                    put("cols", 12)
                    put("erand", 0)
                    putNull("ecells"); putNull("erows"); putNull("ecols")
                    put("cnumb", 1); put("rnumb", 0)
                    putNull("entryLabel"); putNull("options")
                    put("stamp", now)
                }
                db.insert("templates", null, templateCv)
            }
        }
        check(templateId >= 0) { "Failed to create BrAPI sentinel template" }

        val optionsJson = buildPlateOptionsJson(plate, person, mode, programName, studyName, trialName)

        val gridCv = ContentValues().apply {
            put("temp", templateId)
            putNull("projectId")
            put("person", person.ifEmpty { null })
            put("activeRow", 0); put("activeCol", 0)
            put("options", optionsJson)
            put("stamp", now)
        }
        val gridId = db.insert("grids", null, gridCv)
        check(gridId >= 0) { "Failed to create BrAPI grid" }

        val prefillFromSamples = mode == BrapiImportMode.WITH_SAMPLES || mode == BrapiImportMode.READ_WRITE
        val wellToSample: Map<String, BrAPISample> = if (prefillFromSamples) {
            samples.associateBy { computeWell(it) }
        } else emptyMap()

        for (row in 1..8) {
            for (col in 1..12) {
                val well = wellForRowCol(row, col)
                val entryCv = ContentValues()
                entryCv.put("grid", gridId)
                entryCv.put("row", row)
                entryCv.put("col", col)
                entryCv.put("stamp", now)

                if (prefillFromSamples) {
                    val sample = wellToSample[well]
                    if (sample == null) {
                        entryCv.putNull("edata"); entryCv.putNull("original_value")
                        entryCv.putNull("brapi_data"); entryCv.putNull("taken_by")
                        entryCv.putNull("confirmed_timestamp")
                    } else {
                        // original_value = sampleName so isReplaced() works correctly (pending = blue)
                        // brapi_data = JSON with sampleDbId/germplasmDbId for BrAPI sync
                        entryCv.put("edata", sample.sampleName ?: "")
                        entryCv.put("original_value", sample.sampleName ?: "")
                        entryCv.put("brapi_data", buildBrapiDataJson(sample))
                        entryCv.putNull("taken_by")
                        entryCv.putNull("confirmed_timestamp")
                    }
                } else {
                    entryCv.putNull("edata"); entryCv.putNull("original_value")
                    entryCv.putNull("brapi_data"); entryCv.putNull("taken_by")
                    entryCv.putNull("confirmed_timestamp")
                }

                db.insert("entries", null, entryCv)
            }
        }

        return gridId
    }

    private fun buildPlateOptionsJson(
        plate: BrAPIPlate,
        takenBy: String,
        mode: BrapiImportMode,
        programName: String?,
        studyName: String?,
        trialName: String?,
    ): String {
        val array = JSONArray()
        fun addField(field: String, value: String?, hint: String = "") {
            array.put(JSONObject().apply {
                put("field", field)
                put("hint", hint)
                put("value", value ?: "")
                put("checked", true)
            })
        }
        // Human-readable name fields (exported in CSV)
        addField("plateName",   plate.plateName,   "Plate Name")
        addField("programName", programName ?: "", "Program Name")
        addField("studyName",   studyName   ?: "", "Study Name")
        addField("trialName",   trialName   ?: "", "Trial Name")
        // ID fields (for BrAPI sync; also exported but excluded via exportBrapi())
        addField("plateDbId",   plate.plateDbId,   "Plate DB ID")
        addField("programDbId", plate.programDbId, "Program DB ID")
        addField("trialDbId",   plate.trialDbId,   "Trial DB ID")
        addField("studyDbId",   plate.studyDbId,   "Study DB ID")
        // Metadata
        addField("takenBy",     takenBy.ifEmpty { null }, "Person who collected samples")
        addField("importMode",  mode.name, "Import Mode")
        return array.toString()
    }

    private fun buildBrapiDataJson(sample: BrAPISample): String {
        return JSONObject().apply {
            put("sampleDbId",   sample.sampleDbId   ?: "")
            put("germplasmDbId", sample.germplasmDbId ?: "")
        }.toString()
    }

    private fun computeWell(sample: BrAPISample): String {
        val row = sample.row ?: return ""
        val col = sample.column ?: return ""
        // Support both letter rows ("A"–"H") and numeric rows ("1"–"8")
        val rowLetter = if (row.length == 1 && row[0].isLetter()) {
            row.uppercase()
        } else {
            val n = row.toIntOrNull() ?: return ""
            if (n < 1 || n > 8) return ""
            ROW_LETTERS[n - 1].toString()
        }
        return "$rowLetter${String.format("%02d", col)}"
    }

    companion object {
        private const val TAG = "BrapiGridImporter"
        private val ROW_LETTERS = "ABCDEFGH"

        fun wellForRowCol(row: Int, col: Int): String {
            return "${ROW_LETTERS[row - 1]}${String.format("%02d", col)}"
        }

        /**
         * Returns the set of plateDbIds that have already been imported as grids.
         * Used by BrapiPlateListActivity to exclude already-imported plates from the list.
         */
        fun getImportedPlateDbIds(context: Context): Set<String> {
            val db = Database.db(context)
            val ids = mutableSetOf<String>()
            db.query("grids", arrayOf("options"), "options IS NOT NULL", null, null, null, null)
                .use { cursor ->
                    while (cursor.moveToNext()) {
                        val options = cursor.getString(0) ?: continue
                        try {
                            val arr = org.json.JSONArray(options)
                            for (i in 0 until arr.length()) {
                                val obj = arr.getJSONObject(i)
                                if (obj.optString("field") == "plateDbId") {
                                    val id = obj.optString("value")
                                    if (id.isNotEmpty()) ids.add(id)
                                }
                            }
                        } catch (_: Exception) { }
                    }
                }
            return ids
        }
    }
}
