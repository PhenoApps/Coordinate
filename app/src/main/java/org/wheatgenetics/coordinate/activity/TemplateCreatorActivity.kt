package org.wheatgenetics.coordinate.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.wheatgenetics.coordinate.R
import org.wheatgenetics.coordinate.collector.Collector
import org.wheatgenetics.coordinate.collector.DataEntryFragment
import org.wheatgenetics.coordinate.griddisplay.GridDisplayFragment
import org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel
import org.wheatgenetics.coordinate.model.DisplayModel
import org.wheatgenetics.coordinate.model.ElementModel
import org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields

class TemplateCreatorActivity: AppCompatActivity(),
    DataEntryFragment.Handler,
    GridDisplayFragment.Handler {

    private var mCollector: Collector? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_template_creator)

        mCollector = Collector(this)
    }

    override fun getDisplayModel(): DisplayModel? {
        return mCollector?.displayModel
    }

    override fun toggle(elementModel: ElementModel?) {
        mCollector?.toggle(elementModel)
    }

    override fun getActiveRow(): Int {
        return mCollector?.activeRow ?: 0
    }

    override fun getActiveCol(): Int {
        return mCollector?.activeCol ?: 0
    }

    override fun activate(row: Int, col: Int) {
        mCollector?.activate(row, col)
    }

    override fun getChecker(): CheckedIncludedEntryModel.Checker? {
        return mCollector?.checker
    }

    override fun getEntryValue(): String? {
        return mCollector?.entryValue
    }

    override fun getProjectTitle(): String? {
        return mCollector?.projectTitle
    }

    override fun getTemplateTitle(): String? {
        return mCollector?.templateTitle
    }

    override fun getOptionalFields(): NonNullOptionalFields? {
        return mCollector?.optionalFields
    }

    override fun saveEntry(entryValue: String?) {
        mCollector?.saveEntry(entryValue)
    }

    override fun clearEntry() {
        mCollector?.clearEntry()
    }
}