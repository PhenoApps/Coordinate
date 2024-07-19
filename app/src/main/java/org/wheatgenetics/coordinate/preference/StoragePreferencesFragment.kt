package org.wheatgenetics.coordinate.preference

import android.content.Intent
import android.os.Bundle
import androidx.preference.Preference
import org.wheatgenetics.coordinate.R
import org.wheatgenetics.coordinate.activity.DefineStorageActivity

class StoragePreferencesFragment : BasePreferenceFragment() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences_storage)

        super.setToolbar(getString(R.string.preferences_storage_title))

        super.setupBottomNavigationBar()

        val storageDefiner =
            findPreference<Preference>("org.wheatgenetics.coordinate.preferences.STORAGE_DEFINER")
        storageDefiner?.setOnPreferenceClickListener {
            if (context != null) {
                startActivity(Intent(context, DefineStorageActivity::class.java))
            }
            return@setOnPreferenceClickListener true
        }
    }
}