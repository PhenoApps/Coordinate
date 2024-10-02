package org.wheatgenetics.coordinate.utils

import android.Manifest
import android.app.Application

class Constants : Application() {

    companion object {
        val permissions = arrayOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
//            Manifest.permission.ACCESS_COARSE_LOCATION,
//            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.INTERNET,
//            Manifest.permission.RECORD_AUDIO,
//            Manifest.permission.SYSTEM_ALERT_WINDOW,
//            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.CAMERA
        )
    }
}