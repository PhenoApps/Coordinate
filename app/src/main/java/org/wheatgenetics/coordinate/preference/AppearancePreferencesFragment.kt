package org.wheatgenetics.coordinate.preference

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.bytehamster.lib.preferencesearch.SearchPreferenceResult
import org.wheatgenetics.coordinate.R

class AppearancePreferencesFragment(private val searchResult: SearchPreferenceResult? = null) : BasePreferenceFragment() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences_appearance, rootKey)

        super.setToolbar(getString(R.string.pref_appearance_title))

        searchResult?.key?.let { scrollToPreference(it) }

        searchResult?.highlight(this)
    }
}