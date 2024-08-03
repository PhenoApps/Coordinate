package org.wheatgenetics.coordinate.preference;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.bytehamster.lib.preferencesearch.SearchConfiguration;
import com.bytehamster.lib.preferencesearch.SearchPreference;
import com.bytehamster.lib.preferencesearch.SearchPreferenceResult;

import org.wheatgenetics.coordinate.R;

public class PreferenceFragment extends BasePreferenceFragment {

    private SearchPreference searchPreference;

    public static final String TAG = "PreferenceFragment";

    @Override
    public void onCreatePreferences(final Bundle savedInstanceState, final String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);

        searchPreference = findPreference("searchPreference");
        SearchConfiguration config;
        if (searchPreference != null) {
            config = searchPreference.getSearchConfiguration();

            config.setActivity((AppCompatActivity) getActivity());
            config.setFragmentContainerViewId(android.R.id.content);

            config.index(R.xml.preferences);
            config.index(R.xml.preferences_collection);
            config.index(R.xml.preferences_export);
            config.index(R.xml.preferences_storage);
        }
    }

    public void onSearchResultClicked(SearchPreferenceResult result) {
        if (result.getResourceFile() == R.xml.preferences) {
            searchPreference.setVisible(false); // Do not allow to click search multiple times
            scrollToPreference(result.getKey());
            findPreference(result.getKey());
        } else {
            getPreferenceScreen().removeAll();
            addPreferencesFromResource(result.getResourceFile());
            result.highlight(this);
        }
    }

    @Override
    public void onResume() {
        super.initToolbar();
        super.onResume();
    }
}