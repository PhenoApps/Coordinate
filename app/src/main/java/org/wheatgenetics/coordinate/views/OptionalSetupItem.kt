package org.wheatgenetics.coordinate.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import org.wheatgenetics.coordinate.R

class OptionalSetupItem @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val titleView: TextView
    private val summaryView: TextView
    private val setupCheckBox: CheckBox

    init {
        orientation = HORIZONTAL
        val view = LayoutInflater.from(context).inflate(R.layout.optional_setup_item, this, true)

        titleView = view.findViewById(R.id.setup_title)
        summaryView = view.findViewById(R.id.setup_summary)
        setupCheckBox = view.findViewById(R.id.setup_checkbox)
    }

    fun setTitle(title: String) {
        titleView.text = title
    }

    fun setSummary(summary: String) {
        summaryView.text = summary
    }

    fun setCheckbox(value: Boolean) {
        setupCheckBox.isChecked = value
    }

    fun isChecked(): Boolean {
        return setupCheckBox.isChecked
    }
}