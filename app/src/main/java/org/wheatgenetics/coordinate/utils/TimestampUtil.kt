package org.wheatgenetics.coordinate.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class TimestampUtil {

    //attempted to return Date time similar in format to ISO 8601 with underscores
    //Date and time in UTC	2020-06-03T16:31:15+00:00
    //2020-06-03T16:31:15Z
    //20200603T163115Z
    fun getTime(): String {
        val timeStamp = SimpleDateFormat("yyyy-MM-dd-hh-mm-ss", Locale.getDefault())
        return timeStamp.format(Calendar.getInstance().time)
    }

    fun getDate(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dateFormat.format(Calendar.getInstance().time)
    }

    companion object {
        fun formatDate(timestamp: Long): String {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            return dateFormat.format(Date(timestamp))
        }

        fun formatTimestampString(timestamp: String): String {
            return try {
                val parts = timestamp.split("-")
                if (parts.size >= 6) {
                    "${parts[0]}-${parts[1]}-${parts[2]} ${parts[3]}:${parts[4]}:${parts[5]}"
                } else {
                    timestamp
                }
            } catch (e: Exception) {
                timestamp
            }
        }
    }
}