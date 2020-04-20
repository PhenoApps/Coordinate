package org.wheatgenetics.coordinate.preference;

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
 * androidx.preference.SeekBarPreference
 *
 * org.wheatgenetics.coordinate.R
 */
public class PreferenceFragment extends androidx.preference.PreferenceFragmentCompat
implements android.content.SharedPreferences.OnSharedPreferenceChangeListener
{
    // region Fields
    @java.lang.SuppressWarnings({"Convert2Diamond"})
    private final java.util.TreeMap<java.lang.String, java.lang.String>
        directionTreeMap = new java.util.TreeMap<java.lang.String, java.lang.String>();
    @java.lang.SuppressWarnings({"Convert2Diamond"})
    private final java.util.TreeMap<java.lang.String, java.lang.String>
        projectExportTreeMap = new java.util.TreeMap<java.lang.String, java.lang.String>();
    @java.lang.SuppressWarnings({"Convert2Diamond"})
    private final java.util.TreeMap<java.lang.String, java.lang.String>
        uniqueTreeMap = new java.util.TreeMap<java.lang.String, java.lang.String>();

    private java.lang.String directionKey, projectExportKey,
        scalingKey, uniqueCheckBoxKey, uniqueListKey;
    private androidx.preference.ListPreference directionPreference,
        projectExportPreference, uniqueListPreference;
    private androidx.preference.SeekBarPreference                    scalingPreference       ;
    private androidx.preference.CheckBoxPreference                   uniqueCheckBoxPreference;
    private androidx.preference.Preference.OnPreferenceClickListener
        onUniquePreferenceClickListener = null;     // TODO: Replace w/ onSharedPreferenceChanged()?

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

    private void setScaling()
    {
        if (null != this.scalingPreference) this.scalingPreference.setValue(
            java.lang.Math.max((this.scalingPreference.getValue() / 25) * 25, 100));
    }

    private void setUniqueListPreferenceEnabled()
    {
        if (null != this.uniqueCheckBoxPreference && null != this.uniqueListPreference)
            this.uniqueListPreference.setEnabled(this.uniqueCheckBoxPreference.isChecked());
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

    private void setDirectionSummary()
    {
        this.setSummary(this.directionPreference,
            org.wheatgenetics.coordinate.R.string.DirectionPreferenceSummary,
            this.directionTreeMap                                           );
    }

    private void setProjectExportSummary()
    {
        this.setSummary(this.projectExportPreference,
            org.wheatgenetics.coordinate.R.string.ProjectExportPreferenceSummary,
            this.projectExportTreeMap                                           );
    }

    private void setUniqueSummary()
    {
        this.setSummary(this.uniqueListPreference,
            org.wheatgenetics.coordinate.R.string.UniqueListPreferenceSummary, this.uniqueTreeMap);
    }

    private void setSummaries()
    { this.setDirectionSummary(); this.setProjectExportSummary(); this.setUniqueSummary(); }
    // endregion
    // endregion

    // region Overridden Methods
    @java.lang.Override public void onAttach(
    @androidx.annotation.NonNull final android.content.Context context)
    {
        super.onAttach(context);

        if (context instanceof androidx.preference.Preference.OnPreferenceClickListener)
            this.onUniquePreferenceClickListener =
                (androidx.preference.Preference.OnPreferenceClickListener) context;
        else throw new java.lang.RuntimeException(java.lang.String.format(
            this.getString(org.wheatgenetics.coordinate.R.string.PreferenceFragmentWrongContext),
            context.toString()                                                                  ));
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
                    org.wheatgenetics.coordinate.preference.PreferenceFragment.populateTreeMap(
                        resources                                                           ,
                        org.wheatgenetics.coordinate.R.array.UniqueListPreferenceEntryValues,
                        org.wheatgenetics.coordinate.R.array.UniqueListPreferenceEntries    ,
                        this.uniqueTreeMap                                                  );

                    org.wheatgenetics.coordinate.preference.PreferenceFragment.populateTreeMap(
                        resources                                                              ,
                        org.wheatgenetics.coordinate.R.array.ProjectExportPreferenceEntryValues,
                        org.wheatgenetics.coordinate.R.array.ProjectExportPreferenceEntries    ,
                        this.projectExportTreeMap                                              );

                    org.wheatgenetics.coordinate.preference.PreferenceFragment.populateTreeMap(
                        resources                                                          ,
                        org.wheatgenetics.coordinate.R.array.DirectionPreferenceEntryValues,
                        org.wheatgenetics.coordinate.R.array.DirectionPreferenceEntries    ,
                        this.directionTreeMap                                              );
                }

                this.directionKey = activity.getString(
                    org.wheatgenetics.coordinate.R.string.DirectionPreferenceKey);
                this.projectExportKey = activity.getString(
                    org.wheatgenetics.coordinate.R.string.ProjectExportPreferenceKey);
                this.scalingKey = activity.getString(
                    org.wheatgenetics.coordinate.R.string.ScalingPreferenceKey);
                this.uniqueCheckBoxKey = activity.getString(
                    org.wheatgenetics.coordinate.R.string.UniqueCheckBoxPreferenceKey);
                this.uniqueListKey = activity.getString(
                    org.wheatgenetics.coordinate.R.string.UniqueListPreferenceKey);

                this.directionPreference      = this.findPreference(this.directionKey     );
                this.projectExportPreference  = this.findPreference(this.projectExportKey );
                this.scalingPreference        = this.findPreference(this.scalingKey       );
                this.uniqueCheckBoxPreference = this.findPreference(this.uniqueCheckBoxKey);
                this.uniqueListPreference     = this.findPreference(this.uniqueListKey    );

                this.setUniqueListPreferenceEnabled(); this.setSummaries();

                if (null != this.uniqueListPreference)
                    this.uniqueListPreference.setOnPreferenceClickListener(
                        this.onUniquePreferenceClickListener);
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
    { this.onUniquePreferenceClickListener = null; super.onDetach(); }

    // region android.content.SharedPreferences.OnSharedPreferenceChangeListener Overridden Method
    @java.lang.Override public void onSharedPreferenceChanged(
    final android.content.SharedPreferences sharedPreferences, final java.lang.String key)
    {
        if (null != key)
            if (key.equals(this.directionKey))
                this.setDirectionSummary();
            else
                if (key.equals(this.projectExportKey))
                    this.setProjectExportSummary();
                else
                    if (key.equals(this.scalingKey))
                        this.setScaling();
                    else
                        if (key.equals(this.uniqueCheckBoxKey))
                        {
                            this.setUniqueListPreferenceEnabled();
                            if (null != this.onUniquePreferenceClickListener)
                                this.onUniquePreferenceClickListener.onPreferenceClick(
                                    this.uniqueCheckBoxPreference);
                        }
                        else if (key.equals(this.uniqueListKey)) this.setUniqueSummary();
    }
    // endregion
    // endregion
}