package org.wheatgenetics.coordinate.activity

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import org.wheatgenetics.coordinate.R
import org.wheatgenetics.coordinate.collector.Collector
import org.wheatgenetics.coordinate.utils.InsetHandler

class TemplateCreatorActivity : AppCompatActivity() {

    private var mCollector: Collector? = null

    companion object {
        const val TEMPLATE_EDIT = "org.wheatgenetics.coordinate.TEMPLATE_EDIT"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(R.layout.activity_template_creator)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val rootView = window.decorView.findViewById<android.view.View>(android.R.id.content)
        InsetHandler.setupStandardInsets(rootView, toolbar)

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