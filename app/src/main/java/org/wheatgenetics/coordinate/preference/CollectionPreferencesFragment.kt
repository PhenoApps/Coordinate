package org.wheatgenetics.coordinate.preference

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.preference.CheckBoxPreference
import androidx.preference.ListPreference
import dagger.hilt.android.AndroidEntryPoint
import org.wheatgenetics.coordinate.R
import javax.inject.Inject

@AndroidEntryPoint
class CollectionPreferencesFragment : BasePreferenceFragment() {

    @Inject
    lateinit var preferences: SharedPreferences
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences_collection, rootKey)

        super.setToolbar(getString(R.string.preferences_collection_title))

        super.setupBottomNavigationBar()

        val uniqueValues = findPreference<CheckBoxPreference>(GeneralKeys.UNIQUE_VALUES)
        val uniqueOptions = findPreference<ListPreference>(GeneralKeys.UNIQUE_OPTIONS)

        // set unique options visibility initially
        if (uniqueValues != null) {
            uniqueOptions?.isEnabled = uniqueValues.isChecked
        }

        uniqueValues?.setOnPreferenceChangeListener { _, newValue ->
            uniqueOptions?.isEnabled = (newValue == true)
            true
        }
    }
}