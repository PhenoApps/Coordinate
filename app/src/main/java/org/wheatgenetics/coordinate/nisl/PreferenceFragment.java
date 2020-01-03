package org.wheatgenetics.coordinate.nisl;

/**
 * Uses:
 * android.app.Activity
 * android.content.Context
 * android.content.SharedPreferences
 * android.content.SharedPreferences.OnSharedPreferenceChangeListener
 * android.content.res.Resources
 * android.os.Bundle
 *
 * androidx.annotation.ArrayRes
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 * androidx.annotation.StringRes
 * androidx.preference.CheckBoxPreference
 * androidx.preference.ListPreference
 * androidx.preference.Preference.OnPreferenceClickListener
 * androidx.preference.PreferenceFragmentCompat
 * androidx.preference.PreferenceScreen
 *
 * org.wheatgenetics.coordinate.R
 */
class PreferenceFragment extends androidx.preference.PreferenceFragmentCompat
implements android.content.SharedPreferences.OnSharedPreferenceChangeListener
{
    // region Fields
    @java.lang.SuppressWarnings({"Convert2Diamond"})
    private final java.util.TreeMap<java.lang.String, java.lang.String>
        advancementTreeMap = new java.util.TreeMap<java.lang.String, java.lang.String>();
    @java.lang.SuppressWarnings({"Convert2Diamond"})
    private final java.util.TreeMap<java.lang.String, java.lang.String>
        projectExportTreeMap = new java.util.TreeMap<java.lang.String, java.lang.String>();
    @java.lang.SuppressWarnings({"Convert2Diamond"})
    private final java.util.TreeMap<java.lang.String, java.lang.String>
        uniquenessTreeMap = new java.util.TreeMap<java.lang.String, java.lang.String>();

    private java.lang.String advancementKey, projectExportKey,
        uniquenessCheckBoxKey, uniquenessListKey;
    private androidx.preference.ListPreference advancementPreference,
        projectExportPreference, uniquenessListPreference;
    private androidx.preference.CheckBoxPreference                   uniquenessCheckBoxPreference;
    private androidx.preference.Preference.OnPreferenceClickListener
        onUniquenessPreferenceClickListener = null; // TODO: Replace w/ onSharedPreferenceChanged()?

    private android.content.SharedPreferences sharedPreferences;
    // endregion

    // region Private Methods
    private static void populateTreeMap(
    @androidx.annotation.Nullable final android.content.res.Resources resources,
    @androidx.annotation.ArrayRes final int                           keysRes  ,
    @androidx.annotation.ArrayRes final int                           valuesRes,
    @androidx.annotation.NonNull  final java.util.TreeMap<java.lang.String, java.lang.String>
        treeMap)
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

    private void setUniquenessListPreferenceEnabled()
    {
        if (null != this.uniquenessCheckBoxPreference && null != this.uniquenessListPreference)
            this.uniquenessListPreference.setEnabled(this.uniquenessCheckBoxPreference.isChecked());
    }

    // region setSummaries() Private Methods
    private void setSummary(
    @androidx.annotation.Nullable  final androidx.preference.ListPreference preference,
    @androidx.annotation.StringRes final int                                summaryRes,
    @androidx.annotation.NonNull   final java.util.TreeMap<java.lang.String, java.lang.String>
        treeMap)
    {
        if (null != preference)
            preference.setSummary(this.getString(summaryRes, treeMap.get(preference.getValue())));
    }

    private void setAdvancementSummary()
    {
        this.setSummary(this.advancementPreference,
            org.wheatgenetics.coordinate.R.string.AdvancementPreferenceSummary,
            this.advancementTreeMap                                           );
    }

    private void setProjectExportSummary()
    {
        this.setSummary(this.projectExportPreference,
            org.wheatgenetics.coordinate.R.string.ProjectExportPreferenceSummary,
            this.projectExportTreeMap                                           );
    }

    private void setUniquenessSummary()
    {
        this.setSummary(this.uniquenessListPreference,
            org.wheatgenetics.coordinate.R.string.UniquenessListPreferenceSummary,
            this.uniquenessTreeMap                                               );
    }

    private void setSummaries()
    { this.setAdvancementSummary(); this.setProjectExportSummary(); this.setUniquenessSummary(); }
    // endregion
    // endregion

    // region Overridden Methods
    @java.lang.Override public void onAttach(
    @androidx.annotation.NonNull final android.content.Context context)
    {
        super.onAttach(context);

        if (context instanceof androidx.preference.Preference.OnPreferenceClickListener)
            this.onUniquenessPreferenceClickListener =
                (androidx.preference.Preference.OnPreferenceClickListener) context;
        else throw new java.lang.RuntimeException(
            context.toString() + " must implement OnPreferenceClickListener");
    }

    @java.lang.Override public void onCreatePreferences(
    final android.os.Bundle savedInstanceState, final java.lang.String rootKey)
    {
        this.addPreferencesFromResource(org.wheatgenetics.coordinate.R.xml.preferences);
        {
            final android.app.Activity activity = this.getActivity();
            if (null != activity)
            {
                {
                    final android.content.res.Resources resources = activity.getResources();
                    org.wheatgenetics.coordinate.nisl.PreferenceFragment.populateTreeMap(resources,
                        org.wheatgenetics.coordinate.R.array.UniquenessListPreferenceEntryValues,
                        org.wheatgenetics.coordinate.R.array.UniquenessListPreferenceEntries    ,
                        this.uniquenessTreeMap                                                  );

                    org.wheatgenetics.coordinate.nisl.PreferenceFragment.populateTreeMap(resources,
                        org.wheatgenetics.coordinate.R.array.ProjectExportPreferenceEntryValues,
                        org.wheatgenetics.coordinate.R.array.ProjectExportPreferenceEntries    ,
                        this.projectExportTreeMap                                              );

                    org.wheatgenetics.coordinate.nisl.PreferenceFragment.populateTreeMap(resources,
                        org.wheatgenetics.coordinate.R.array.AdvancementPreferenceEntryValues,
                        org.wheatgenetics.coordinate.R.array.AdvancementPreferenceEntries    ,
                        this.advancementTreeMap                                              );
                }

                this.advancementKey = activity.getString(
                    org.wheatgenetics.coordinate.R.string.AdvancementPreferenceKey);
                this.projectExportKey = activity.getString(
                    org.wheatgenetics.coordinate.R.string.ProjectExportPreferenceKey);
                this.uniquenessCheckBoxKey = activity.getString(
                    org.wheatgenetics.coordinate.R.string.UniquenessCheckBoxPreferenceKey);
                this.uniquenessListKey = activity.getString(
                    org.wheatgenetics.coordinate.R.string.UniquenessListPreferenceKey);

                this.advancementPreference    = this.findPreference(this.advancementKey   );
                this.projectExportPreference  = this.findPreference(this.projectExportKey );
                this.uniquenessListPreference = this.findPreference(this.uniquenessListKey);

                this.uniquenessCheckBoxPreference = this.findPreference(this.uniquenessCheckBoxKey);

                this.setUniquenessListPreferenceEnabled(); this.setSummaries();

                if (null != this.uniquenessListPreference)
                    this.uniquenessListPreference.setOnPreferenceClickListener(
                        this.onUniquenessPreferenceClickListener);
            }
        }
        {
            final androidx.preference.PreferenceScreen preferenceScreen =
                this.getPreferenceScreen();
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
            if (key.equals(this.advancementKey))
                this.setAdvancementSummary();
            else
                if (key.equals(this.projectExportKey))
                    this.setProjectExportSummary();
                else
                    if (key.equals(this.uniquenessCheckBoxKey))
                    {
                        this.setUniquenessListPreferenceEnabled();
                        if (null != this.onUniquenessPreferenceClickListener)
                            this.onUniquenessPreferenceClickListener.onPreferenceClick(
                                this.uniquenessCheckBoxPreference);
                    }
                    else if (key.equals(this.uniquenessListKey)) this.setUniquenessSummary();
    }
    // endregion
    // endregion
}