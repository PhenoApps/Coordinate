package org.wheatgenetics.coordinate.brapi.cache

import android.content.Context
import android.util.Log
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.brapi.v2.model.core.BrAPIProgram
import org.brapi.v2.model.core.BrAPIStudy
import org.brapi.v2.model.core.BrAPITrial

/**
 * File-based cache for BrAPI filter entity data (studies and trials).
 * Populated lazily when the user opens a filter activity; cleared along with the plate cache
 * via "Reset Cache".
 */
object BrapiEntityCache {

    private const val TAG = "BrapiEntityCache"
    private const val STUDIES_FILE = "coordinate_brapi_entity_studies.json"
    private const val TRIALS_FILE = "coordinate_brapi_entity_trials.json"
    private const val PROGRAMS_FILE = "coordinate_brapi_entity_programs.json"

    private val gson = GsonBuilder().create()

    // ── Programs ──────────────────────────────────────────────────────────────

    fun savePrograms(context: Context, programs: List<BrAPIProgram>) {
        try {
            val file = programsFile(context)
            file.writeText(gson.toJson(programs))
            Log.d(TAG, "savePrograms: cached ${programs.size} program(s)")
        } catch (e: Exception) {
            Log.e(TAG, "savePrograms: failed", e)
        }
    }

    fun loadPrograms(context: Context): List<BrAPIProgram>? {
        return try {
            val file = programsFile(context)
            if (!file.exists()) return null
            val type = object : TypeToken<List<BrAPIProgram>>() {}.type
            val list: List<BrAPIProgram> = gson.fromJson(file.readText(), type)
            Log.d(TAG, "loadPrograms: loaded ${list.size} program(s) from cache")
            list
        } catch (e: Exception) {
            Log.e(TAG, "loadPrograms: failed to read cache", e)
            null
        }
    }

    // ── Studies ───────────────────────────────────────────────────────────────

    fun saveStudies(context: Context, studies: List<BrAPIStudy>) {
        try {
            val file = studiesFile(context)
            file.writeText(gson.toJson(studies))
            Log.d(TAG, "saveStudies: cached ${studies.size} study/studies")
        } catch (e: Exception) {
            Log.e(TAG, "saveStudies: failed", e)
        }
    }

    fun loadStudies(context: Context): List<BrAPIStudy>? {
        return try {
            val file = studiesFile(context)
            if (!file.exists()) return null
            val type = object : TypeToken<List<BrAPIStudy>>() {}.type
            val list: List<BrAPIStudy> = gson.fromJson(file.readText(), type)
            Log.d(TAG, "loadStudies: loaded ${list.size} study/studies from cache")
            list
        } catch (e: Exception) {
            Log.e(TAG, "loadStudies: failed to read cache", e)
            null
        }
    }

    // ── Trials ────────────────────────────────────────────────────────────────

    fun saveTrials(context: Context, trials: List<BrAPITrial>) {
        try {
            val file = trialsFile(context)
            file.writeText(gson.toJson(trials))
            Log.d(TAG, "saveTrials: cached ${trials.size} trial(s)")
        } catch (e: Exception) {
            Log.e(TAG, "saveTrials: failed", e)
        }
    }

    fun loadTrials(context: Context): List<BrAPITrial>? {
        return try {
            val file = trialsFile(context)
            if (!file.exists()) return null
            val type = object : TypeToken<List<BrAPITrial>>() {}.type
            val list: List<BrAPITrial> = gson.fromJson(file.readText(), type)
            Log.d(TAG, "loadTrials: loaded ${list.size} trial(s) from cache")
            list
        } catch (e: Exception) {
            Log.e(TAG, "loadTrials: failed to read cache", e)
            null
        }
    }

    // ── Delete ────────────────────────────────────────────────────────────────

    fun delete(context: Context) {
        listOf(programsFile(context), studiesFile(context), trialsFile(context)).forEach { file ->
            if (file.exists()) {
                file.delete()
                Log.d(TAG, "delete: removed ${file.name}")
            }
        }
    }

    // ── Private helpers ───────────────────────────────────────────────────────

    private fun programsFile(context: Context) =
        java.io.File(context.externalCacheDir, PROGRAMS_FILE)

    private fun studiesFile(context: Context) =
        java.io.File(context.externalCacheDir, STUDIES_FILE)

    private fun trialsFile(context: Context) =
        java.io.File(context.externalCacheDir, TRIALS_FILE)
}
