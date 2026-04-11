package org.wheatgenetics.coordinate.preference

import android.os.Bundle
import androidx.preference.CheckBoxPreference
import androidx.preference.PreferenceManager
import com.bytehamster.lib.preferencesearch.SearchPreferenceResult
import org.wheatgenetics.coordinate.R
import org.wheatgenetics.coordinate.database.TemplatesTable

class AppearancePreferencesFragment(private val searchResult: SearchPreferenceResult? = null) : BasePreferenceFragment() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences_appearance, rootKey)

        super.setToolbar(getString(R.string.pref_appearance_title))

        // Disable HIDE_TEMPLATES when no user-visible templates exist.
        // load() includes built-in templates that can never be deleted, so we must filter
        // out any built-ins the user has already hidden before counting.
        val prefs = PreferenceManager.getDefaultSharedPreferences(requireContext())
        val hiddenRaw = prefs.getString(GeneralKeys.HIDDEN_BUILTIN_TEMPLATES, "") ?: ""
        val hiddenCodes = if (hiddenRaw.isEmpty()) emptySet<String>()
            else hiddenRaw.split(",").toSet()
        val templates = TemplatesTable(requireContext()).load()
        var visibleCount = 0
        if (templates != null) {
            for (t in templates) {
                if (!t.isDefaultTemplate || !hiddenCodes.contains(t.type.code.toString())) {
                    visibleCount++
                }
            }
        }
        findPreference<CheckBoxPreference>(GeneralKeys.HIDE_TEMPLATES)?.isEnabled = visibleCount > 0

        searchResult?.key?.let { scrollToPreference(it) }

        searchResult?.highlight(this)
    }
}