package org.wheatgenetics.coordinate.fragments.app_intro

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.wheatgenetics.coordinate.R
import org.wheatgenetics.coordinate.preference.GeneralKeys
import org.wheatgenetics.coordinate.views.OptionalSetupItem

class OptionalSetupFragment : Fragment(){
    private var slideTitle: String? = null
    private var slideSummary: String? = null
    private var slideBackgroundColor: Int? = null

    private var loadSampleDataSetupItem: OptionalSetupItem? = null

    private var prefs: SharedPreferences? = null

    private val scope by lazy { CoroutineScope(Dispatchers.Main) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.app_intro_optional_setup_slide, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prefs = context?.let { PreferenceManager.getDefaultSharedPreferences(it) }

        val slideTitle = view.findViewById<TextView>(R.id.slide_title)
        val slideSummary = view.findViewById<TextView>(R.id.slide_summary)

        slideTitle?.text = this.slideTitle
        slideSummary?.text = this.slideSummary

        slideBackgroundColor?.let { view.setBackgroundResource(it) }


        loadSampleDataSetupItem = view.findViewById(R.id.load_sample_data_setup_item)

        initSetupItems()
    }

    private fun initSetupItems() {

        loadSampleDataSetupItem?.apply {
            setTitle(getString(R.string.app_intro_load_sample_data_title))
            setSummary(getString(R.string.app_intro_load_sample_data_summary))
            setOnClickListener {
                loadSampleToggle()
            }
        }
    }

    private fun loadSampleToggle() {
        loadSampleDataSetupItem?.let {
            it.setCheckbox(!it.isChecked())

            prefs?.edit()?.putBoolean(GeneralKeys.LOAD_SAMPLE_DATA, it.isChecked())?.apply()
        }
    }

    companion object {
        fun newInstance(
            slideTitle: String,
            slideSummary: String,
            slideBackgroundColor: Int
        ): OptionalSetupFragment {
            val fragment = OptionalSetupFragment()
            fragment.slideTitle = slideTitle
            fragment.slideSummary = slideSummary
            fragment.slideBackgroundColor = slideBackgroundColor
            return fragment
        }

    }
}