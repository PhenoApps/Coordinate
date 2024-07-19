package org.wheatgenetics.coordinate.preference;

import android.os.Bundle;

import org.wheatgenetics.coordinate.R;

public class PreferenceFragment extends BasePreferenceFragment {

    public static final String TAG = "PreferenceFragment";

    @Override
    public void onCreatePreferences(final Bundle savedInstanceState, final String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
    }

    @Override
    public void onResume() {
        super.initToolbar();
        super.onResume();
    }
}