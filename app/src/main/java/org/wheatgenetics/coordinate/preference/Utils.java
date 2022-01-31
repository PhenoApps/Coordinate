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
                final String key = context.getString(
                        R.string.DirectionPreferenceKey);
                direction = defaultSharedPreferences.getString(
                        /* key      => */ key,
                        /* defValue => */ context.getString(
                                R.string.DirectionPreferenceDefault));
            }
            if (direction.equals(context.getString(
                    R.string.DirectionPreferenceDownThenAcrossEntryValue)))
                return Utils.Direction.DOWN_THEN_ACROSS;
            else if (direction.equals(context.getString(
                    R.string.DirectionPreferenceAcrossThenDownEntryValue)))
                return
                        Utils.Direction.ACROSS_THEN_DOWN;
            else
                return Utils.Direction.ERROR;
        }
    }
    // endregion

    public static boolean getSoundsOn(final Context context) {
        final SharedPreferences defaultSharedPreferences =
                Utils.getDefaultSharedPreferences(context);
        if (null == defaultSharedPreferences)
            return true;
        else {
            final String key = context.getString(
                    R.string.SoundsOnPreferenceKey);
            return defaultSharedPreferences.getBoolean(key, /* defValue => */true);
        }
    }

    public static Utils.ProjectExport getProjectExport(
            @NonNull final Context context) {
        final SharedPreferences defaultSharedPreferences =
                Utils.getDefaultSharedPreferences(context);
        if (null == defaultSharedPreferences)
            return Utils.ProjectExport.ERROR;
        else {
            final String projectExport;
            {
                final String key = context.getString(
                        R.string.ProjectExportPreferenceKey);
                projectExport = defaultSharedPreferences.getString(
                        /* key      => */ key,
                        /* defValue => */ context.getString(
                                R.string.ProjectExportPreferenceDefault));
            }
            if (projectExport.equals(context.getString(
                    R.string.ProjectExportPreferenceOneFilePerGrid)))
                return
                        Utils.ProjectExport.ONE_FILE_PER_GRID;
            else if (projectExport.equals(context.getString(
                    R.string.ProjectExportPreferenceOneFileEntireProject)))
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
                final String key = context.getString(
                        R.string.ScalingPreferenceKey);
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
            final String key = context.getString(
                    R.string.UniqueCheckBoxPreferenceKey);
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
                final String key = context.getString(
                        R.string.UniqueListPreferenceKey);
                unique = defaultSharedPreferences.getString(
                        /* key      => */ key,
                        /* defValue => */ context.getString(
                                R.string.UniqueListPreferenceDefault));
            }
            if (unique.equals(context.getString(
                    R.string.UniqueListPreferenceCurrentGrid)))
                return Utils.TypeOfUnique.CURRENT_GRID;
            else if (unique.equals(context.getString(
                    R.string.UniqueListPreferenceCurrentProject)))
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