package org.wheatgenetics.coordinate.activity

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import org.wheatgenetics.coordinate.R
import org.wheatgenetics.coordinate.collector.Collector

class TemplateCreatorActivity : AppCompatActivity() {

    private var mCollector: Collector? = null

    companion object {
        const val TEMPLATE_EDIT = "org.wheatgenetics.coordinate.TEMPLATE_EDIT"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_template_creator)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        
        supportActionBar?.apply {
            title = if (intent?.hasExtra(TEMPLATE_EDIT) == true)
                getString(R.string.edit_template_title)
            else getString(R.string.new_template_title)
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }

    }
}