package org.wheatgenetics.coordinate.activity

import android.app.Activity
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import dagger.hilt.android.AndroidEntryPoint
import org.phenoapps.utils.BaseDocumentTreeUtil
import org.wheatgenetics.coordinate.R
import org.wheatgenetics.coordinate.utils.InsetHandler

@AndroidEntryPoint
class DefineStorageActivity : AppCompatActivity() {

    private var mBackButtonEnabled = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finishWithStorageResult()
            }
        })
        InsetHandler.enableEdgeToEdge(this)
        setContentView(R.layout.activity_define_storage)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val rootView = window.decorView.findViewById<android.view.View>(android.R.id.content)
        InsetHandler.setupStandardInsets(rootView, toolbar)
    }

    private fun finishWithStorageResult() {

        if (mBackButtonEnabled) {
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