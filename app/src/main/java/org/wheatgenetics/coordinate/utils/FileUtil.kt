package org.wheatgenetics.coordinate.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.preference.PreferenceManager

class FileUtil {

    companion object {

        const val SHARE_ON_EXPORT = "org.wheatgenetics.coordinate.preferences.SHARE_ON_EXPORT"

        fun shareFile(context: Context?, uri: Uri) {

            context?.let { ctx ->
                try {
                    val prefs = PreferenceManager.getDefaultSharedPreferences(ctx)
                    if (prefs.getBoolean(SHARE_ON_EXPORT, false)) {
                        val intent = Intent()
                        intent.action = Intent.ACTION_SEND
                        intent.type = "text/plain"
                        intent.putExtra(Intent.EXTRA_STREAM, uri)
                        ctx.startActivity(Intent.createChooser(intent, "Sending File..."))
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}