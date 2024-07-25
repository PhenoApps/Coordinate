package org.wheatgenetics.coordinate.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.wheatgenetics.coordinate.R;

public class Utils {
    private static final float minScaling = 0.5f, maxScaling = 2.0f;
    private static SharedPreferences defaultSharedPreferencesInstance = null;  // ll

    @Nullable
    public static SharedPreferences
    getDefaultSharedPreferences(final Context context) {
        if (null == Utils.defaultSharedPreferencesInstance)
            Utils.defaultSharedPreferencesInstance =
                    PreferenceManager.getDefaultSharedPreferences(context);
        return Utils.defaultSharedPreferencesInstance;
    }

    // region Public Methods
    public static Utils.Direction getDirection(
            @NonNull final Context context) {
        final SharedPreferences defaultSharedPreferences =
                Utils.getDefaultSharedPreferences(context);
        if (null == defaultSharedPreferences)
            return Utils.Direction.ERROR;
        else {
            final String direction;
            {
                final String key = GeneralKeys.DIRECTION;
                direction = defaultSharedPreferences.getString(
                        /* key      => */ key,
                        /* defValue => */ context.getString(
                                R.string.preferences_direction_default));
            }
            if (direction.equals(context.getString(
                    R.string.preferences_direction_down_then_across_entry_value)))
                return Utils.Direction.DOWN_THEN_ACROSS;
            else if (direction.equals(context.getString(
                    R.string.preferences_direction_across_then_down_entry_value)))
                return
                        Utils.Direction.ACROSS_THEN_DOWN;
            else
                return Utils.Direction.ERROR;
        }
    }
    // endregion

    public static Utils.ProjectExport getProjectExport(
            @NonNull final Context context) {
        final SharedPreferences defaultSharedPreferences =
                Utils.getDefaultSharedPreferences(context);
        if (null == defaultSharedPreferences)
            return Utils.ProjectExport.ERROR;
        else {
            final String projectExport;
            {
                final String key = GeneralKeys.PROJECT_EXPORT;
                projectExport = defaultSharedPreferences.getString(
                        /* key      => */ key,
                        /* defValue => */ context.getString(
                                R.string.preferences_project_export_default));
            }
            if (projectExport.equals(context.getString(
                    R.string.preferences_project_export_one_file_per_grid)))
                return
                        Utils.ProjectExport.ONE_FILE_PER_GRID;
            else if (projectExport.equals(context.getString(
                    R.string.preferences_project_export_one_file_entire_project)))
                return Utils.ProjectExport.ONE_FILE_ENTIRE_PROJECT;
            else
                return Utils.ProjectExport.ERROR;
        }
    }

    @FloatRange(
            from = Utils.minScaling,
            to = Utils.maxScaling)
    public static float getScaling(
            @NonNull final Context context) {
        final SharedPreferences defaultSharedPreferences =
                Utils.getDefaultSharedPreferences(context);
        if (null == defaultSharedPreferences)
            return 1.0f;
        else {
            final int scaling;
            {
                final String key = GeneralKeys.SCALING;
                scaling = defaultSharedPreferences.getInt(key, /* defValue => */100);
            }
            return (float) scaling / 100.0f;
        }
    }

    public static boolean getUnique(
            @NonNull final Context context) {
        final SharedPreferences defaultSharedPreferences =
                Utils.getDefaultSharedPreferences(context);
        if (null == defaultSharedPreferences)
            return false;
        else {
            final String key = GeneralKeys.UNIQUE_VALUES;
            return defaultSharedPreferences.getBoolean(key, /* defValue => */false);
        }
    }

    public static Utils.TypeOfUnique getTypeOfUniqueness(
            @NonNull final Context context) {
        final SharedPreferences defaultSharedPreferences =
                Utils.getDefaultSharedPreferences(context);
        if (null == defaultSharedPreferences)
            return Utils.TypeOfUnique.ERROR;
        else {
            final String unique;
            {
                final String key = GeneralKeys.UNIQUE_OPTIONS;
                unique = defaultSharedPreferences.getString(
                        /* key      => */ key,
                        /* defValue => */ context.getString(
                                R.string.preferences_unique_options_default));
            }
            if (unique.equals(context.getString(
                    R.string.preferences_unique_options_current_grid)))
                return Utils.TypeOfUnique.CURRENT_GRID;
            else if (unique.equals(context.getString(
                    R.string.preferences_unique_options_current_project)))
                return
                        Utils.TypeOfUnique.CURRENT_PROJECT;
            else
                return Utils.TypeOfUnique.ALL_GRIDS;
        }
    }

    // region Types
    public enum Direction {ERROR, DOWN_THEN_ACROSS, ACROSS_THEN_DOWN}

    public enum ProjectExport {ERROR, ONE_FILE_PER_GRID, ONE_FILE_ENTIRE_PROJECT}

    public enum TypeOfUnique {ERROR, CURRENT_GRID, CURRENT_PROJECT, ALL_GRIDS}
    // endregion
}