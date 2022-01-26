package org.wheatgenetics.coordinate.preference;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.ArrayRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.preference.CheckBoxPreference;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceScreen;
import androidx.preference.SeekBarPreference;

import org.wheatgenetics.coordinate.R;
import org.wheatgenetics.coordinate.database.EntriesTable;
import org.wheatgenetics.coordinate.database.GridsTable;
import org.wheatgenetics.coordinate.deleter.GridDeleter;
import org.wheatgenetics.coordinate.deleter.ProjectDeleter;
import org.wheatgenetics.coordinate.deleter.TemplateDeleter;

import java.util.TreeMap;

public class PreferenceFragment extends PreferenceFragmentCompat
        implements SharedPreferences.OnSharedPreferenceChangeListener {
    // region Fields
    @SuppressWarnings({"Convert2Diamond"})
    private final TreeMap<String, String>
            directionTreeMap = new TreeMap<String, String>();
    @SuppressWarnings({"Convert2Diamond"})
    private final TreeMap<String, String>
            projectExportTreeMap = new TreeMap<String, String>();
    @SuppressWarnings({"Convert2Diamond"})
    private final TreeMap<String, String>
            uniqueTreeMap = new TreeMap<String, String>();

    private String directionKey, projectExportKey,
            scalingKey, uniqueCheckBoxKey, uniqueListKey, databaseResetKey;
    private ListPreference directionPreference,
            projectExportPreference, uniqueListPreference;
    private SeekBarPreference scalingPreference;
    private CheckBoxPreference uniqueCheckBoxPreference;
    private Preference databaseResetPreference;
    private Preference.OnPreferenceClickListener
            onUniquePreferenceClickListener = null;     // TODO: Replace w/ onSharedPreferenceChanged()?

    private SharedPreferences sharedPreferences;
    // endregion

    // region Private Methods
    private static void populateTreeMap(
            @Nullable final Resources resources,
            @ArrayRes final int keysRes,
            @ArrayRes final int valuesRes,
            @NonNull final TreeMap<String, String>
                    treeMap) {
        if (null != resources) {
            // noinspection CStyleArrayDeclaration
            final String
                    keys[] = resources.getStringArray(keysRes),
                    values[] = resources.getStringArray(valuesRes);
            final int first = 0, last = keys.length - 1;
            for (int i = first; i <= last; i++) treeMap.put(keys[i], values[i]);
        }
    }

    private void setScaling() {
        if (null != this.scalingPreference) this.scalingPreference.setValue(
                Math.max((this.scalingPreference.getValue() / 25) * 25, 50));
    }

    private void setUniqueListPreferenceEnabled() {
        if (null != this.uniqueCheckBoxPreference && null != this.uniqueListPreference)
            this.uniqueListPreference.setEnabled(this.uniqueCheckBoxPreference.isChecked());
    }

    // region setSummaries() Private Methods
    private void setSummary(
            @Nullable final ListPreference preference,
            @StringRes final int summaryRes,
            @NonNull final TreeMap<String, String>
                    treeMap) {
        if (null != preference)
            preference.setSummary(this.getString(summaryRes, treeMap.get(preference.getValue())));
    }

    private void setDirectionSummary() {
        this.setSummary(this.directionPreference,
                R.string.DirectionPreferenceSummary,
                this.directionTreeMap);
    }

    private void setProjectExportSummary() {
        this.setSummary(this.projectExportPreference,
                R.string.ProjectExportPreferenceSummary,
                this.projectExportTreeMap);
    }

    private void setUniqueSummary() {
        this.setSummary(this.uniqueListPreference,
                R.string.UniqueListPreferenceSummary, this.uniqueTreeMap);
    }

    private void setSummaries() {
        this.setDirectionSummary();
        this.setProjectExportSummary();
        this.setUniqueSummary();
    }
    // endregion
    // endregion

    // region Overridden Methods
    @Override
    public void onAttach(
            @NonNull final Context context) {
        super.onAttach(context);

        if (context instanceof Preference.OnPreferenceClickListener)
            this.onUniquePreferenceClickListener =
                    (Preference.OnPreferenceClickListener) context;
        else throw new RuntimeException(String.format(
                this.getString(R.string.PreferenceFragmentWrongContext),
                context.toString()));
    }

    @Override
    public void onCreatePreferences(
            final Bundle savedInstanceState, final String rootKey) {
        this.addPreferencesFromResource(R.xml.preferences);
        {
            final Activity activity = this.getActivity();
            if (null != activity) {
                {
                    final Resources resources = activity.getResources();
                    PreferenceFragment.populateTreeMap(
                            resources,
                            R.array.UniqueListPreferenceEntryValues,
                            R.array.UniqueListPreferenceEntries,
                            this.uniqueTreeMap);

                    PreferenceFragment.populateTreeMap(
                            resources,
                            R.array.ProjectExportPreferenceEntryValues,
                            R.array.ProjectExportPreferenceEntries,
                            this.projectExportTreeMap);

                    PreferenceFragment.populateTreeMap(
                            resources,
                            R.array.DirectionPreferenceEntryValues,
                            R.array.DirectionPreferenceEntries,
                            this.directionTreeMap);
                }

                this.directionKey = activity.getString(
                        R.string.DirectionPreferenceKey);
                this.projectExportKey = activity.getString(
                        R.string.ProjectExportPreferenceKey);
                this.scalingKey = activity.getString(
                        R.string.ScalingPreferenceKey);
                this.uniqueCheckBoxKey = activity.getString(
                        R.string.UniqueCheckBoxPreferenceKey);
                this.uniqueListKey = activity.getString(
                        R.string.UniqueListPreferenceKey);
                this.databaseResetKey = activity.getString(R.string.pref_database_reset_key);

                this.directionPreference = this.findPreference(this.directionKey);
                this.projectExportPreference = this.findPreference(this.projectExportKey);
                this.scalingPreference = this.findPreference(this.scalingKey);
                this.uniqueCheckBoxPreference = this.findPreference(this.uniqueCheckBoxKey);
                this.uniqueListPreference = this.findPreference(this.uniqueListKey);
                this.databaseResetPreference = findPreference(databaseResetKey);
                setupDatabaseResetPreference();

                this.setUniqueListPreferenceEnabled();
                this.setSummaries();

                if (null != this.uniqueListPreference)
                    this.uniqueListPreference.setOnPreferenceClickListener(
                            this.onUniquePreferenceClickListener);
            }
        }
        {
            final PreferenceScreen preferenceScreen =
                    this.getPreferenceScreen();
            this.sharedPreferences = null == preferenceScreen ?
                    null : preferenceScreen.getSharedPreferences();
        }
    }

    /**
     * Creates and shows a dialog to the user to confirm db deletion.
     * Clears all rows in grid, project and entries tables.
     * Only the user templates are deleted.
     */
    private void setupDatabaseResetPreference() {

        if (isAdded()) {

            databaseResetPreference.setOnPreferenceClickListener((preference -> {

                new AlertDialog.Builder(getActivity())
                        .setTitle(R.string.dialog_database_reset_title)
                        .setPositiveButton(android.R.string.ok, ((dialog, which) -> {

                            Context ctx = getContext();
                            if (ctx != null) {
                                GridDeleter gd = new GridDeleter(ctx, () -> {});
                                ProjectDeleter pd = new ProjectDeleter(ctx, (a) -> {});
                                TemplateDeleter td = new TemplateDeleter(ctx, () -> {});

                                //grid deleter deletes all grids and entries to the grids
                                gd.deleteAll();

                                //template deleter will delete all user-defined templates
                                td.deleteAllUserTemplates();

                                //project delete deletes all projects
                                pd.deleteAll();
                            }

                        }))
                        .setNegativeButton(android.R.string.cancel, (((dialog, which) -> {

                        })))
                        .create()
                        .show();

                return true;
            }));
        }
    }

    @Override
    public void onResume() {
        if (null != this.sharedPreferences)
            this.sharedPreferences.registerOnSharedPreferenceChangeListener(this);
        super.onResume();
    }

    @Override
    public void onPause() {
        if (null != this.sharedPreferences)
            this.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
        super.onPause();
    }

    @Override
    public void onDetach() {
        this.onUniquePreferenceClickListener = null;
        super.onDetach();
    }

    // region android.content.SharedPreferences.OnSharedPreferenceChangeListener Overridden Method
    @Override
    public void onSharedPreferenceChanged(
            final SharedPreferences sharedPreferences, final String key) {
        if (null != key)
            if (key.equals(this.directionKey))
                this.setDirectionSummary();
            else if (key.equals(this.projectExportKey))
                this.setProjectExportSummary();
            else if (key.equals(this.scalingKey))
                this.setScaling();
            else if (key.equals(this.uniqueCheckBoxKey)) {
                this.setUniqueListPreferenceEnabled();
                if (null != this.onUniquePreferenceClickListener)
                    this.onUniquePreferenceClickListener.onPreferenceClick(
                            this.uniqueCheckBoxPreference);
            } else if (key.equals(this.uniqueListKey)) this.setUniqueSummary();
    }
    // endregion
    // endregion
}