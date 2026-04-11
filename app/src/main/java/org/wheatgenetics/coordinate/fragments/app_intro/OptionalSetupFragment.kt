package org.wheatgenetics.coordinate.fragments.app_intro

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import org.wheatgenetics.coordinate.R
import org.wheatgenetics.coordinate.model.TemplateType
import org.wheatgenetics.coordinate.preference.GeneralKeys
import org.wheatgenetics.coordinate.views.OptionalSetupItem

class OptionalSetupFragment : Fragment(){
    private var slideTitle: String? = null
    private var slideSummary: String? = null
    private var slideBackgroundColor: Int? = null

    private var loadSampleDataSetupItem: OptionalSetupItem? = null
    private var tutorialSetupItem: OptionalSetupItem? = null
    private var seedTrayTemplateSetupItem: OptionalSetupItem? = null
    private var dnaPlateTemplateSetupItem: OptionalSetupItem? = null
    private var htpgPlateTemplateSetupItem: OptionalSetupItem? = null

    private var prefs: SharedPreferences? = null

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
        tutorialSetupItem = view.findViewById(R.id.tutorial_setup_item)
        seedTrayTemplateSetupItem = view.findViewById(R.id.seed_tray_template_setup_item)
        dnaPlateTemplateSetupItem = view.findViewById(R.id.dna_plate_template_setup_item)
        htpgPlateTemplateSetupItem = view.findViewById(R.id.htpg_plate_template_setup_item)

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

        tutorialSetupItem?.apply {
            setTitle(getString(R.string.app_intro_enable_tutorial_title))
            setSummary(getString(R.string.app_intro_enable_tutorial_summary))

            setCheckbox(prefs?.getBoolean(GeneralKeys.TIPS, true) != false)// set as checked by default
            prefs?.edit()?.putBoolean(GeneralKeys.TIPS, true)?.apply()

            setOnClickListener {
                tutorialToggle()
            }
        }

        // Default template checkboxes — all checked by default
        seedTrayTemplateSetupItem?.apply {
            setTitle(getString(R.string.app_intro_seed_tray_template_title))
            setSummary(getString(R.string.app_intro_seed_tray_template_summary))
            setCheckbox(!isBuiltinHidden(TemplateType.SEED))
            setOnClickListener { templateToggle(TemplateType.SEED, this) }
        }

        dnaPlateTemplateSetupItem?.apply {
            setTitle(getString(R.string.app_intro_dna_plate_template_title))
            setSummary(getString(R.string.app_intro_dna_plate_template_summary))
            setCheckbox(!isBuiltinHidden(TemplateType.DNA))
            setOnClickListener { templateToggle(TemplateType.DNA, this) }
        }

        htpgPlateTemplateSetupItem?.apply {
            setTitle(getString(R.string.app_intro_htpg_plate_template_title))
            setSummary(getString(R.string.app_intro_htpg_plate_template_summary))
            setCheckbox(!isBuiltinHidden(TemplateType.HTPG))
            setOnClickListener { templateToggle(TemplateType.HTPG, this) }
        }
    }

    private fun isBuiltinHidden(type: TemplateType): Boolean {
        val raw = prefs?.getString(GeneralKeys.HIDDEN_BUILTIN_TEMPLATES, "") ?: return false
        if (raw.isEmpty()) return false
        return raw.split(",").contains(type.code.toString())
    }

    private fun setBuiltinHidden(type: TemplateType, hidden: Boolean) {
        val raw = prefs?.getString(GeneralKeys.HIDDEN_BUILTIN_TEMPLATES, "") ?: ""
        val codes = if (raw.isEmpty()) mutableSetOf() else raw.split(",").toMutableSet()
        val typeCode = type.code.toString()
        if (hidden) codes.add(typeCode) else codes.remove(typeCode)
        prefs?.edit()?.putString(GeneralKeys.HIDDEN_BUILTIN_TEMPLATES, codes.joinToString(","))?.apply()
    }

    private fun loadSampleToggle() {
        loadSampleDataSetupItem?.let {
            it.setCheckbox(!it.isChecked())

            prefs?.edit()?.putBoolean(GeneralKeys.LOAD_SAMPLE_DATA, it.isChecked())?.apply()
        }
    }

    private fun tutorialToggle() {
        tutorialSetupItem?.let {
            it.setCheckbox(!it.isChecked())

            prefs?.edit()?.putBoolean(GeneralKeys.TIPS, it.isChecked())?.apply()
        }
    }

    private fun templateToggle(type: TemplateType, item: OptionalSetupItem) {
        val nowChecked = !item.isChecked()
        item.setCheckbox(nowChecked)
        setBuiltinHidden(type, !nowChecked)
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
