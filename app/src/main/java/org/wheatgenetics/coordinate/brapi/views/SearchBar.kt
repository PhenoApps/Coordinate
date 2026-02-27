package org.wheatgenetics.coordinate.brapi.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.AutoCompleteTextView
import android.widget.FrameLayout
import android.widget.ImageView
import org.wheatgenetics.coordinate.R

class SearchBar : FrameLayout {

    lateinit var editText: AutoCompleteTextView
    lateinit var clearButton: ImageView

    constructor(context: Context) : super(context) { init() }
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) { init() }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) { init() }

    private fun init() {
        val view = LayoutInflater.from(context).inflate(R.layout.view_search_bar, this, true)
        editText = view.findViewById(R.id.search_edit_text)
        clearButton = view.findViewById(R.id.search_clear_button)
        clearButton.setOnClickListener {
            editText.text.clear()
        }
    }
}
