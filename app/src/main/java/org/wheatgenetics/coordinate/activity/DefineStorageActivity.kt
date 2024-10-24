package org.wheatgenetics.coordinate.activity

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import org.phenoapps.utils.BaseDocumentTreeUtil
import org.wheatgenetics.coordinate.R

@AndroidEntryPoint
class DefineStorageActivity : AppCompatActivity() {

    private var mBackButtonEnabled = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_define_storage)
    }

    override fun onBackPressed() {

        if (mBackButtonEnabled) {
            super.onBackPressed()
            setResult(
                if (BaseDocumentTreeUtil.isEnabled(this)) {
                    Activity.RESULT_OK
                } else Activity.RESULT_CANCELED
            )

            finish()
        }
    }

    fun enableBackButton(enable: Boolean) {
        mBackButtonEnabled = enable
    }
}