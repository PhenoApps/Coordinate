package org.wheatgenetics.coordinate.brapi

import android.content.Context
import androidx.preference.PreferenceManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.brapi.v2.model.geno.BrAPISample
import org.json.JSONObject
import org.wheatgenetics.coordinate.brapi.service.BrapiGenotypingService
import org.wheatgenetics.coordinate.database.Database
import org.wheatgenetics.coordinate.database.GridsTable
import org.wheatgenetics.coordinate.model.ExcludedEntryModel
import org.wheatgenetics.coordinate.preference.GeneralKeys
import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneOffset

class BrapiExporter(private val context: Context) {

    private val service = BrapiGenotypingService(context)

    /** Blocking wrapper for calling from Java threads. */
    @Throws(Exception::class)
    fun exportGridSync(gridId: Long) = runBlocking { exportGrid(gridId) }

    /** Exports all filled cells of a BRAPI grid to the BrAPI server. */
    suspend fun exportGrid(gridId: Long) = withContext(Dispatchers.IO) {
        val gridsTable = GridsTable(context)
        val grid = gridsTable.get(gridId) ?: return@withContext

        // Get plateDbId from grid optional fields
        val plateDbId = grid.optionalFields()
            ?.values(arrayOf("plateDbId"))
            ?.getOrNull(0)
            ?.takeIf { it.isNotEmpty() }
            ?: return@withContext  // can't export without plateDbId

        val takenBy = PreferenceManager.getDefaultSharedPreferences(context)
            .getString(GeneralKeys.PERSON_NAME, "") ?: ""

        val db = Database.db(context)
        val cursor = db.query(
            /* table       */ "entries",
            /* columns     */ arrayOf("row", "col", "edata", "stamp", "original_value", "confirmed_timestamp"),
            /* selection   */ "grid = ? AND edata IS NOT NULL AND edata != '' AND edata != ?",
            /* selectionArgs */ arrayOf(gridId.toString(), ExcludedEntryModel.DATABASE_VALUE),
            /* groupBy     */ null,
            /* having      */ null,
            /* orderBy     */ "row ASC, col ASC"
        )

        val toCreate = mutableListOf<BrAPISample>()
        val toUpdate = mutableListOf<Pair<String, BrAPISample>>() // sampleDbId → sample

        cursor.use {
            while (it.moveToNext()) {
                val row = it.getInt(it.getColumnIndexOrThrow("row"))
                val col = it.getInt(it.getColumnIndexOrThrow("col"))
                val sampleName = it.getString(it.getColumnIndexOrThrow("edata"))
                val stamp = it.getLong(it.getColumnIndexOrThrow("stamp"))
                val originalValue = it.getString(it.getColumnIndexOrThrow("original_value"))
                val confirmedTs = it.getLong(it.getColumnIndexOrThrow("confirmed_timestamp"))

                val rowLetter = "ABCDEFGH"[row - 1].toString()
                val well = rowLetter + String.format("%02d", col)

                // Use confirmed timestamp for imported entries, otherwise entry stamp
                val tsMillis = if (originalValue != null && confirmedTs > 0) confirmedTs else stamp
                val sampleTimestamp = OffsetDateTime.ofInstant(
                    Instant.ofEpochMilli(tsMillis), ZoneOffset.UTC
                )

                val sample = BrAPISample().apply {
                    this.sampleName = sampleName
                    this.well = well
                    this.row = rowLetter
                    this.column = col
                    this.plateDbId = plateDbId
                    this.takenBy = takenBy
                    this.sampleTimestamp = sampleTimestamp
                }

                // Parse original_value for existing sampleDbId + germplasm info
                val sampleDbId = originalValue?.let { json ->
                    runCatching { JSONObject(json).optString("sampleDbId").takeIf { it.isNotEmpty() } }.getOrNull()
                }
                val germplasmDbId = originalValue?.let { json ->
                    runCatching { JSONObject(json).optString("germplasmDbId").takeIf { it.isNotEmpty() } }.getOrNull()
                }
                if (!germplasmDbId.isNullOrEmpty()) {
                    sample.germplasmDbId = germplasmDbId
                }

                if (!sampleDbId.isNullOrEmpty()) {
                    toUpdate.add(sampleDbId to sample)
                } else {
                    toCreate.add(sample)
                }
            }
        }

        // POST new samples in one batch
        if (toCreate.isNotEmpty()) {
            service.createSamples(toCreate)
        }
        // PUT existing samples individually
        for ((sampleDbId, sample) in toUpdate) {
            service.updateSample(sampleDbId, sample)
        }
    }
}
