package org.wheatgenetics.coordinate.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.wheatgenetics.coordinate.R
import org.wheatgenetics.coordinate.collector.Collector
import org.wheatgenetics.coordinate.collector.DataEntryDialogFragment
import org.wheatgenetics.coordinate.griddisplay.GridDisplayFragment
import org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel
import org.wheatgenetics.coordinate.model.DisplayModel
import org.wheatgenetics.coordinate.model.ElementModel
import org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields

class TemplateCreatorActivity: AppCompatActivity(),
    DataEntryDialogFragment.Handler,
    GridDisplayFragment.Handler {

    private var mCollector: Collector? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_template_creator)

        supportActionBar?.title = getString(R.string.new_template_title)

    }
}