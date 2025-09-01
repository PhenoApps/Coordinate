package org.wheatgenetics.coordinate.utils

import org.json.JSONArray

object GridOptionsParser {
    fun extractGridName(options: String?): String {
        if (options.isNullOrBlank()) return "Untitled Grid"

        return try {
            val jsonArray = JSONArray(options)
            for (i in 0 until jsonArray.length()) {
                val item = jsonArray.getJSONObject(i)
                val field = item.getString("field").lowercase()
                if (field in listOf("plate", "tray", "identification") && item.has("value")) {
                    return item.getString("value")
                }
            }
            "Untitled Grid"
        } catch (e: Exception) {
            "Untitled Grid"
        }
    }

    fun extractTimestamp(options: String?): String? {
        if (options.isNullOrBlank()) return null

        return try {
            val jsonArray = JSONArray(options)
            for (i in 0 until jsonArray.length()) {
                val item = jsonArray.getJSONObject(i)
                if (item.getString("field").lowercase() == "timestamp" && item.has("value")) {
                    return item.getString("value")
                }
            }
            null
        } catch (e: Exception) {
            null
        }
    }
}