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
 * android.support.annotation.ArrayRes
 * android.support.annotation.NonNull
 * android.support.annotation.Nullable
 * android.support.annotation.StringRes
 *
 * org.wheatgenetics.coordinate.R
 */
public class PreferenceFragment extends android.preference.PreferenceFragment
implements android.content.SharedPreferences.OnSharedPreferenceChangeListener
{
    // region Fields
    @java.lang.SuppressWarnings({"Convert2Diamond"})
    private final java.util.TreeMap<java.lang.String, java.lang.String>
        uniquenessTreeMap = new java.util.TreeMap<java.lang.String, java.lang.String>();
    @java.lang.SuppressWarnings({"Convert2Diamond"})
    private final java.util.TreeMap<java.lang.String, java.lang.String>
        projectExportTreeMap = new java.util.TreeMap<java.lang.String, java.lang.String>();
    @java.lang.SuppressWarnings({"Convert2Diamond"})
    private final java.util.TreeMap<java.lang.String, java.lang.String>
        advancementTreeMap = new java.util.TreeMap<java.lang.String, java.lang.String>();

    private java.lang.String                  uniquenessKey, projectExportKey, advancementKey;
    private android.preference.ListPreference uniquenessPreference,
        projectExportPreference, advancementPreference;
    private android.preference.Preference.OnPreferenceClickListener
        onUniquenessPreferenceClickListener = null; // TODO: Replace w/ onSharedPreferenceChanged()?

    private android.content.SharedPreferences sharedPreferences;
    // endregion

    // region Private Methods
    private static void populateTreeMap(
    @android.support.annotation.Nullable final android.content.res.Resources resources,
    @android.support.annotation.ArrayRes final int                           keysRes  ,
    @android.support.annotation.ArrayRes final int                           valuesRes,
    @android.support.annotation.NonNull  final
        java.util.TreeMap<java.lang.String, java.lang.String> treeMap)
    {
        if (null != resources)
        {
            // noinspection CStyleArrayDeclaration
            final java.lang.String
                keys  [] = resources.getStringArray(keysRes  ),
                values[] = resources.getStringArray(valuesRes);
            final int first = 0, last = keys.length - 1;
            for (int i = first; i <= last; i++) treeMap.put(keys[i], values[i]);
        }
    }

    private void setSummary(
    @android.support.annotation.Nullable  final android.preference.ListPreference preference,
    @android.support.annotation.StringRes final int                               summaryRes,
    @android.support.annotation.NonNull   final
        java.util.TreeMap<java.lang.String, java.lang.String> treeMap)
    {
        if (null != preference)
            preference.setSummary(this.getString(summaryRes, treeMap.get(preference.getValue())));
    }

    private void setUniquenessSummary()
    {
        this.setSummary(this.uniquenessPreference,
            org.wheatgenetics.coordinate.R.string.UniquenessPreferenceSummary,
            this.uniquenessTreeMap                                          );
    }

    private void setProjectExportSummary()
    {
        this.setSummary(this.projectExportPreference,
            org.wheatgenetics.coordinate.R.string.ProjectExportPreferenceSummary,
            this.projectExportTreeMap                                           );
    }

    private void setAdvancementSummary()
    {
        this.setSummary(this.advancementPreference,
            org.wheatgenetics.coordinate.R.string.AdvancementPreferenceSummary,
            this.advancementTreeMap                                           );
    }

    private void setSummaries()
    { this.setUniquenessSummary(); this.setProjectExportSummary(); this.setAdvancementSummary(); }
    // endregion

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
                    org.wheatgenetics.coordinate.nisl.PreferenceFragment.populateTreeMap(resources,
                        org.wheatgenetics.coordinate.R.array.UniquenessPreferenceEntryValues,
                        org.wheatgenetics.coordinate.R.array.UniquenessPreferenceEntries    ,
                        this.uniquenessTreeMap                                              );

                    org.wheatgenetics.coordinate.nisl.PreferenceFragment.populateTreeMap(resources,
                        org.wheatgenetics.coordinate.R.array.ProjectExportPreferenceEntryValues,
                        org.wheatgenetics.coordinate.R.array.ProjectExportPreferenceEntries    ,
                        this.projectExportTreeMap                                              );

                    org.wheatgenetics.coordinate.nisl.PreferenceFragment.populateTreeMap(resources,
                        org.wheatgenetics.coordinate.R.array.AdvancementPreferenceEntryValues,
                        org.wheatgenetics.coordinate.R.array.AdvancementPreferenceEntries    ,
                        this.advancementTreeMap                                              );
                }

                this.uniquenessKey = activity.getString(
                    org.wheatgenetics.coordinate.R.string.UniquenessPreferenceKey);
                this.projectExportKey = activity.getString(
                    org.wheatgenetics.coordinate.R.string.ProjectExportPreferenceKey);
                this.advancementKey = activity.getString(
                    org.wheatgenetics.coordinate.R.string.AdvancementPreferenceKey);

                this.uniquenessPreference =
                    (android.preference.ListPreference) this.findPreference(this.uniquenessKey);
                this.projectExportPreference =
                    (android.preference.ListPreference) this.findPreference(this.projectExportKey);
                this.advancementPreference =
                    (android.preference.ListPreference) this.findPreference(this.advancementKey);

                this.setSummaries();

                if (null != this.uniquenessPreference)
                    this.uniquenessPreference.setOnPreferenceClickListener(
                        this.onUniquenessPreferenceClickListener);
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
    {
        if (null != key)
            if (key.equals(this.uniquenessKey))
                this.setUniquenessSummary();
            else
                if (key.equals(this.projectExportKey))
                    this.setProjectExportSummary();
                else
                    if (key.equals(this.advancementKey)) this.setAdvancementSummary();
    }
    // endregion
    // endregion
}