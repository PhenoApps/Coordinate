package org.wheatgenetics.coordinate.preference

import android.os.Bundle
import org.wheatgenetics.coordinate.R

class ExportPreferencesFragment: BasePreferenceFragment()  {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences_export, rootKey)

        super.setToolbar(getString(R.string.preferences_export_title))
    }
}