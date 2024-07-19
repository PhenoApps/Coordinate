package org.wheatgenetics.coordinate.preference

import android.os.Bundle
import org.wheatgenetics.coordinate.R
class LayoutPreferencesFragment : BasePreferenceFragment() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences_layout, rootKey)

        super.setToolbar(getString(R.string.preferences_layout_title))

        super.setupBottomNavigationBar()
    }
}