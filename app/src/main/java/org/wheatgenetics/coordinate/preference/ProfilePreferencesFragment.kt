package org.wheatgenetics.coordinate.preference

import android.os.Bundle
import android.text.InputType
import androidx.preference.EditTextPreference
import androidx.preference.PreferenceManager
import com.bytehamster.lib.preferencesearch.SearchPreferenceResult
import dagger.hilt.android.AndroidEntryPoint
import org.wheatgenetics.coordinate.R

@AndroidEntryPoint
class ProfilePreferencesFragment(private val searchResult: SearchPreferenceResult? = null) :
    BasePreferenceFragment() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences_profile, rootKey)

        super.setToolbar(getString(R.string.pref_profile_title))

        searchResult?.key?.let { scrollToPreference(it) }
        searchResult?.highlight(this)

        val personPref = findPreference<EditTextPreference>(GeneralKeys.PERSON_NAME)
        personPref?.setOnBindEditTextListener { editText ->
            editText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_WORDS
            editText.setSelection(editText.text.length)
        }
        personPref?.summaryProvider = EditTextPreference.SimpleSummaryProvider.getInstance()
    }

    override fun onResume() {
        super.onResume()
        setToolbar(getString(R.string.pref_profile_title))

        // Record the last time this profile screen was opened for the 24-hr reminder
        PreferenceManager.getDefaultSharedPreferences(requireContext())
            .edit()
            .putLong(GeneralKeys.LAST_TIME_OPENED, System.currentTimeMillis())
            .apply()
    }
}
