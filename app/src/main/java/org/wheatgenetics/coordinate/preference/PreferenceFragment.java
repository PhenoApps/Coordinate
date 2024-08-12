package org.wheatgenetics.coordinate.preference;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bytehamster.lib.preferencesearch.SearchConfiguration;
import com.bytehamster.lib.preferencesearch.SearchPreference;
import com.bytehamster.lib.preferencesearch.SearchPreferenceResult;

import org.wheatgenetics.coordinate.AboutFragment;
import org.wheatgenetics.coordinate.R;

public class PreferenceFragment extends BasePreferenceFragment {
    private SearchPreference searchPreference;

    private PreferenceActivity preferenceActivity;

    public static final String TAG = "PreferenceFragment";

    @Override
    public void onCreatePreferences(final Bundle savedInstanceState, final String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);

        setSearchConfiguration();
    }

    private void setSearchConfiguration() {
        searchPreference = findPreference("searchPreference");
        if (searchPreference != null) {
            SearchConfiguration config;
            config = searchPreference.getSearchConfiguration();

            config.setActivity(preferenceActivity);
            config.setFragmentContainerViewId(R.id.act_prefs_fragment);

            config.index(R.xml.preferences);
            config.index(R.xml.preferences_collection);
            config.index(R.xml.preferences_export);
            config.index(R.xml.preferences_storage);
        }
    }

    public void onSearchResultClicked(SearchPreferenceResult result) {
        if (result.getResourceFile() == R.xml.preferences) {
            getParentFragmentManager().beginTransaction().replace(R.id.act_prefs_fragment, this).addToBackStack("PrefsFragment").commit();
            searchPreference.setVisible(false); // Do not allow to click search multiple times
            // Scroll to the selected preference
            scrollToPreference(result.getKey());
            findPreference(result.getKey());
            result.highlight(this);
        } else {
            Fragment newFragment = getFragment(result);

            if (newFragment != null) {
                preferenceActivity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.act_prefs_fragment, newFragment)
                        .addToBackStack(null)
                        .commit();
            }
        }
    }

    private static Fragment getFragment(SearchPreferenceResult result) {
        int resFile = result.getResourceFile();
        Fragment newFragment = null;
        if (resFile == R.xml.preferences_collection) {
            newFragment = new CollectionPreferencesFragment(result);
        } else if (resFile == R.xml.preferences_export) {
            newFragment = new ExportPreferencesFragment(result);
        } else if (resFile == R.xml.preferences_storage) {
            newFragment = new StoragePreferencesFragment(result);
        } else if (resFile == R.layout.activity_about) {
            newFragment = new AboutFragment();
        }
        return newFragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            preferenceActivity = (PreferenceActivity) context;
        } catch (Exception e){
            throw new IllegalStateException();
        }
    }

    @Override
    public void onResume() {
        initToolbar();
        super.onResume();
    }
}