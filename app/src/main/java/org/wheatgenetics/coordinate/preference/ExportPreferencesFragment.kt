package org.wheatgenetics.coordinate.preference

import android.os.Bundle
import com.bytehamster.lib.preferencesearch.SearchPreferenceResult
import org.wheatgenetics.coordinate.R

class ExportPreferencesFragment(private var searchResult: SearchPreferenceResult? = null): BasePreferenceFragment()  {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences_export, rootKey)

        super.setToolbar(getString(R.string.preferences_export_title))

        searchResult?.key?.let { scrollToPreference(it) }

        searchResult?.highlight(this)
    }
}