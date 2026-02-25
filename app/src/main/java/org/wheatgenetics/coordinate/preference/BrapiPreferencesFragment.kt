package org.wheatgenetics.coordinate.preference

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.preference.CheckBoxPreference
import androidx.preference.EditTextPreference
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceManager
import dagger.hilt.android.AndroidEntryPoint
import org.wheatgenetics.coordinate.R
import org.wheatgenetics.coordinate.brapi.BrapiAuthActivity

@AndroidEntryPoint
class BrapiPreferencesFragment(
    private val searchResult: com.bytehamster.lib.preferencesearch.SearchPreferenceResult? = null,
) : BasePreferenceFragment() {

    private val authLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
    ) { result ->
        if (result.resultCode == android.app.Activity.RESULT_OK) {
            updateRevokeVisibility()
        }
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences_brapi, rootKey)
        setToolbar(getString(R.string.pref_brapi_title))

        val prefs = PreferenceManager.getDefaultSharedPreferences(requireContext())

        val enabledPref = findPreference<CheckBoxPreference>(GeneralKeys.BRAPI_ENABLED)
        val serverCategory = findPreference<androidx.preference.PreferenceCategory>("brapi_server")
        val oidcSettingsCategory = findPreference<androidx.preference.PreferenceCategory>("brapi_oidc_settings")
        val advancedCategory = findPreference<androidx.preference.PreferenceCategory>("brapi_advanced")
        val baseUrlPref = findPreference<EditTextPreference>(GeneralKeys.BRAPI_BASE_URL)
        val oidcUrlPref = findPreference<EditTextPreference>(GeneralKeys.BRAPI_OIDC_URL)
        val oidcFlowPref = findPreference<ListPreference>(GeneralKeys.BRAPI_OIDC_FLOW)
        val authorizePref = findPreference<Preference>("brapi_authorize")
        val revokePref = findPreference<Preference>("brapi_revoke")

        // Show/hide all categories based on BRAPI_ENABLED
        val isEnabled = enabledPref?.isChecked ?: false
        serverCategory?.isVisible = isEnabled
        oidcSettingsCategory?.isVisible = isEnabled
        advancedCategory?.isVisible = isEnabled

        enabledPref?.setOnPreferenceChangeListener { _, newValue ->
            val enabled = newValue as Boolean
            serverCategory?.isVisible = enabled
            oidcSettingsCategory?.isVisible = enabled
            advancedCategory?.isVisible = enabled
            updateHiddenTemplates(enabled)
            if (!enabled) {
                prefs.edit()
                    .remove(GeneralKeys.BRAPI_TOKEN)
                    .remove(GeneralKeys.BRAPI_ID_TOKEN)
                    .apply()
            }
            true
        }

        // Update OIDC URL when base URL changes (unless explicitly set by user)
        baseUrlPref?.setOnPreferenceChangeListener { _, newValue ->
            val newUrl = newValue.toString()
            if (!prefs.getBoolean(GeneralKeys.BRAPI_EXPLICIT_OIDC_URL, false)) {
                val oldUrl = prefs.getString(GeneralKeys.BRAPI_BASE_URL, "") ?: ""
                val oldOidcUrl = prefs.getString(GeneralKeys.BRAPI_OIDC_URL, "") ?: ""
                val newOidcUrl = when {
                    // If the OIDC URL already contains the old base URL, replace it
                    oldUrl.isNotEmpty() && oldOidcUrl.contains(oldUrl) ->
                        oldOidcUrl.replaceFirst(oldUrl, newUrl)
                    // If the OIDC URL is empty (first-time setup), construct the standard discovery URL
                    oldOidcUrl.isEmpty() ->
                        newUrl.trimEnd('/') + "/.well-known/openid-configuration"
                    else -> null
                }
                if (newOidcUrl != null) {
                    prefs.edit().putString(GeneralKeys.BRAPI_OIDC_URL, newOidcUrl).apply()
                    oidcUrlPref?.text = newOidcUrl
                }
            }
            true
        }

        // Mark OIDC URL as explicitly set when user changes it
        oidcUrlPref?.setOnPreferenceChangeListener { _, _ ->
            prefs.edit().putBoolean(GeneralKeys.BRAPI_EXPLICIT_OIDC_URL, true).apply()
            true
        }

        authorizePref?.setOnPreferenceClickListener {
            val intent = Intent(requireContext(), BrapiAuthActivity::class.java)
            authLauncher.launch(intent)
            true
        }

        revokePref?.setOnPreferenceClickListener {
            prefs.edit()
                .remove(GeneralKeys.BRAPI_TOKEN)
                .remove(GeneralKeys.BRAPI_ID_TOKEN)
                .apply()
            Toast.makeText(requireContext(), R.string.pref_brapi_token_revoked, Toast.LENGTH_SHORT).show()
            updateRevokeVisibility()
            true
        }

        updateRevokeVisibility()
    }

    override fun onResume() {
        super.onResume()
        updateRevokeVisibility()
    }

    private fun updateRevokeVisibility() {
        val prefs = PreferenceManager.getDefaultSharedPreferences(requireContext())
        val hasToken = prefs.getString(GeneralKeys.BRAPI_TOKEN, null) != null
        findPreference<Preference>("brapi_revoke")?.isVisible = hasToken
    }

    private fun updateHiddenTemplates(brapiEnabled: Boolean) {
        val prefs = PreferenceManager.getDefaultSharedPreferences(requireContext())
        val brapiCode = "5"
        val current = prefs.getString(GeneralKeys.HIDDEN_BUILTIN_TEMPLATES, "") ?: ""
        val codes = current.split(",").map { it.trim() }.filter { it.isNotEmpty() }.toMutableList()

        if (brapiEnabled) {
            codes.remove(brapiCode)
        } else {
            if (!codes.contains(brapiCode)) codes.add(brapiCode)
        }

        prefs.edit()
            .putString(GeneralKeys.HIDDEN_BUILTIN_TEMPLATES, codes.joinToString(","))
            .apply()
    }
}
