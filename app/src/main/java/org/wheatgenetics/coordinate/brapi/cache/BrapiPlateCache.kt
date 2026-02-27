package org.wheatgenetics.coordinate.brapi.cache

import android.content.Context
import android.util.Log
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.brapi.v2.model.geno.BrAPIPlate
import java.io.File

object BrapiPlateCache {

    private const val TAG = "BrapiPlateCache"
    private const val CACHE_FILE = "coordinate_brapi_plates.json"

    private val gson = GsonBuilder().create()

    private fun cacheFile(context: Context): File =
        File(context.externalCacheDir, CACHE_FILE)

    fun save(context: Context, plates: List<BrAPIPlate>) {
        try {
            val json = gson.toJson(plates)
            cacheFile(context).writeText(json)
            Log.d(TAG, "save: cached ${plates.size} plate(s)")
        } catch (e: Exception) {
            Log.e(TAG, "save: failed to write cache", e)
        }
    }

    fun load(context: Context): List<BrAPIPlate>? {
        return try {
            val file = cacheFile(context)
            if (!file.exists()) {
                Log.d(TAG, "load: no cache file found")
                return null
            }
            val json = file.readText()
            val type = object : TypeToken<List<BrAPIPlate>>() {}.type
            val plates: List<BrAPIPlate> = gson.fromJson(json, type)
            Log.d(TAG, "load: loaded ${plates.size} plate(s) from cache")
            plates
        } catch (e: Exception) {
            Log.e(TAG, "load: failed to read cache", e)
            null
        }
    }

    fun delete(context: Context) {
        try {
            val file = cacheFile(context)
            if (file.exists()) {
                file.delete()
                Log.d(TAG, "delete: cache file deleted")
            }
        } catch (e: Exception) {
            Log.e(TAG, "delete: failed to delete cache", e)
        }
    }
}
