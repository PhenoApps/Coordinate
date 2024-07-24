package org.wheatgenetics.coordinate.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.wheatgenetics.coordinate.R
import org.wheatgenetics.coordinate.collector.Collector

class TemplateCreatorActivity: AppCompatActivity() {

    private var mCollector: Collector? = null

    companion object {
        const val TEMPLATE_EDIT = "org.wheatgenetics.coordinate.TEMPLATE_EDIT"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_template_creator)

        supportActionBar?.apply{
            title = getString(R.string.new_template_title)
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }

    }
}