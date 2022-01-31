package org.wheatgenetics.coordinate.utils

import android.os.Build
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class DateUtil {

    //attempted to return Date time similar in format to ISO 8601 with underscores
    //Date and time in UTC	2020-06-03T16:31:15+00:00
    //2020-06-03T16:31:15Z
    //20200603T163115Z
    fun getTime(): String = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        LocalDateTime.now().format(DateTimeFormatter.ofPattern(
                "yyyy-MM-dd_HH_mm_ss_SSS"))
    } else {
        Calendar.getInstance().time.toString()
    }
}