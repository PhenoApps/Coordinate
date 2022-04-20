package org.wheatgenetics.coordinate.contracts

import android.app.Activity
import android.content.Context
import androidx.activity.result.contract.ActivityResultContract
import androidx.annotation.CallSuper
import android.content.Intent
import android.net.Uri

class OpenDocumentFancy : ActivityResultContract<String, Uri?>() {

    @CallSuper
    override fun createIntent(context: Context, input: String): Intent {
        return Intent(Intent.ACTION_OPEN_DOCUMENT)
            .putExtra("android.content.extra.SHOW_ADVANCED", true)
            .putExtra("android.content.extra.FANCY", true)
            .putExtra("android.content.extra.SHOW_FILESIZE", true)
            .setType("*/*")
            .putExtra(Intent.EXTRA_TITLE, input)
    }

    override fun getSynchronousResult(
        context: Context,
        input: String
    ): SynchronousResult<Uri?>? {
        return null
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
        return if (intent == null || resultCode != Activity.RESULT_OK) null else intent.data
    }
}