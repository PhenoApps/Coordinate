package org.wheatgenetics.coordinate.utils

import java.text.SimpleDateFormat
import java.util.Calendar
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
}