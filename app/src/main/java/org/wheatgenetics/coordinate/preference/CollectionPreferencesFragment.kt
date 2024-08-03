package org.wheatgenetics.coordinate.preference

import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.CheckBoxPreference
import androidx.preference.ListPreference
import androidx.preference.Preference
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

        val directionPreference = findPreference<ListPreference>(GeneralKeys.DIRECTION)

        val uniqueValues = findPreference<CheckBoxPreference>(GeneralKeys.UNIQUE_VALUES)
        val uniqueOptions = findPreference<ListPreference>(GeneralKeys.UNIQUE_OPTIONS)

        val navigationSound =
            findPreference<Preference>(GeneralKeys.NAVIGATION_SOUND)
        val duplicateEntrySound = findPreference<Preference>(GeneralKeys.DUPLICATE_ENTRY_SOUND)

        // set unique options and duplicate sound visibility initially
        if (uniqueValues != null) {
            uniqueOptions?.isEnabled = uniqueValues.isChecked
            uniqueOptions?.isVisible = uniqueValues.isChecked
            duplicateEntrySound?.isVisible = uniqueValues.isChecked
        }

        // initially, set navigation sound summary
        directionPreference?.value?.let { setNavigationSoundSummary(it, navigationSound) }

        uniqueValues?.setOnPreferenceChangeListener { _, newValue ->
            uniqueOptions?.isEnabled = (newValue == true)
            uniqueOptions?.isVisible = (newValue == true)
            duplicateEntrySound?.isVisible = (newValue == true)
            true
        }

        // depending on direction selected, change the navigation sound summary
        directionPreference?.setOnPreferenceChangeListener { _, newValue ->
            setNavigationSoundSummary(newValue, navigationSound)
            true
        }
    }

    private fun setNavigationSoundSummary(value: Any, filledRowOrColumnSound: Preference?) {
        if (value == getString(R.string.preferences_direction_down_then_across_entry_value)) {
            filledRowOrColumnSound?.summary = String.format(getString(R.string.preferences_sound_filled_row_or_column_summary), getString(R.string.preferences_sound_filled_column))
        } else {
            filledRowOrColumnSound?.summary = String.format(getString(R.string.preferences_sound_filled_row_or_column_summary), getString(R.string.preferences_sound_filled_row))
        }
    }
}