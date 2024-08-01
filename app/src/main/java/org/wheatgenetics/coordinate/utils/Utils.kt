package org.wheatgenetics.coordinate.utils

import android.content.Context
import android.view.Gravity
import android.widget.Toast

class Utils {
    companion object {
        private var currentToast: Toast? = null
        fun makeToast(context: Context?, message: String?) {
            currentToast?.cancel()
            currentToast = Toast.makeText(context, message, Toast.LENGTH_LONG)
            currentToast?.setGravity(Gravity.TOP, 0, 0)
            currentToast?.show()
        }
    }
}