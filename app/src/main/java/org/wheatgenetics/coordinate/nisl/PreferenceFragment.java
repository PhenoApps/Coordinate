package org.wheatgenetics.coordinate.nisl;

/**
 * Uses:
 * android.app.Activity
 * android.content.Context
 * android.content.SharedPreferences
 * android.content.SharedPreferences.OnSharedPreferenceChangeListener
 * android.content.res.Resources
 * android.os.Bundle
 * android.preference.Fragment
 * android.preference.ListPreference
 * android.preference.Preference.OnPreferenceClickListener
 * android.preference.PreferenceScreen
 * android.support.annotation.Nullable
 *
 * org.wheatgenetics.coordinate.R
 */
public class PreferenceFragment extends android.preference.PreferenceFragment
implements android.content.SharedPreferences.OnSharedPreferenceChangeListener
{
    // region Fields
    @java.lang.SuppressWarnings({"Convert2Diamond"})
    private java.util.TreeMap<java.lang.String, java.lang.String> uniquenessTreeMap =
        new java.util.TreeMap<java.lang.String, java.lang.String>();

    private java.lang.String                                        uniquenessKey       ;
    private android.preference.ListPreference                       uniquenessPreference;
    private android.preference.Preference.OnPreferenceClickListener
        onUniquenessPreferenceClickListener = null; // TODO: Replace w/ onSharedPreferenceChanged()?

    private android.content.SharedPreferences sharedPreferences;
    // endregion

    private void setUniquenessSummary()
    {
        if (null != this.uniquenessPreference) this.uniquenessPreference.setSummary(
            this.getString(org.wheatgenetics.coordinate.R.string.UniquenessPreferenceSummary,
                this.uniquenessTreeMap.get(this.uniquenessPreference.getValue())));
    }

    // region Overridden Methods
    @java.lang.Override public void onAttach(final android.content.Context context)
    {
        super.onAttach(context);

        if (context instanceof android.preference.Preference.OnPreferenceClickListener)
            this.onUniquenessPreferenceClickListener =
                (android.preference.Preference.OnPreferenceClickListener) context;
        else
            throw new java.lang.RuntimeException(null == context ?
                "context" : context.toString() + " must implement OnPreferenceClickListener");
    }

    @java.lang.Override public void onCreate(
    @android.support.annotation.Nullable final android.os.Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource.
        this.addPreferencesFromResource(org.wheatgenetics.coordinate.R.xml.preferences);

        {
            final android.app.Activity activity = this.getActivity();
            if (null != activity)
            {
                {
                    final android.content.res.Resources resources = activity.getResources();
                    if (null != resources)
                    {
                        final java.lang.String
                            keys[] = resources.getStringArray(org.wheatgenetics
                                .coordinate.R.array.UniquenessPreferenceEntryValues),
                            values[] = resources.getStringArray(
                                org.wheatgenetics.coordinate.R.array.UniquenessPreferenceEntries);
                        final int first = 0, last = keys.length - 1;
                        for (int i = first; i <= last; i++)
                            this.uniquenessTreeMap.put(keys[i], values[i]);
                    }
                }
                this.uniquenessKey = activity.getString(
                    org.wheatgenetics.coordinate.R.string.UniquenessPreferenceTitle);
                this.uniquenessPreference =
                    (android.preference.ListPreference) this.findPreference(this.uniquenessKey);
                if (null != this.uniquenessPreference)
                    this.uniquenessPreference.setOnPreferenceClickListener(
                        this.onUniquenessPreferenceClickListener);
                this.setUniquenessSummary();
            }
        }

        {
            final android.preference.PreferenceScreen preferenceScreen = this.getPreferenceScreen();
            this.sharedPreferences = null == preferenceScreen ?
                null : preferenceScreen.getSharedPreferences();
        }
    }

    @java.lang.Override public void onResume()
    {
        if (null != this.sharedPreferences)
            this.sharedPreferences.registerOnSharedPreferenceChangeListener(this);
        super.onResume();
    }

    @java.lang.Override public void onPause()
    {
        if (null != this.sharedPreferences)
            this.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
        super.onPause();
    }

    @java.lang.Override public void onDetach()
    { this.onUniquenessPreferenceClickListener = null; super.onDetach(); }

    // region android.content.SharedPreferences.OnSharedPreferenceChangeListener Overridden Method
    @java.lang.Override public void onSharedPreferenceChanged(
    final android.content.SharedPreferences sharedPreferences, final java.lang.String key)
    { if (null != key) if (key.equals(this.uniquenessKey)) this.setUniquenessSummary(); }
    // endregion
    // endregion
}