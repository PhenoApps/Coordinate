package org.wheatgenetics.coordinate.preference

import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import org.wheatgenetics.coordinate.R

@AndroidEntryPoint
class BrapiAdvancedPreferencesFragment : BasePreferenceFragment() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences_brapi_advanced, rootKey)
        setToolbar(getString(R.string.brapi_advanced_settings))
    }
}
