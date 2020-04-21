package org.wheatgenetics.coordinate.preference;

/**
 * Uses:
 * android.content.Context
 * android.content.SharedPreferences
 * android.preference.PreferenceManager
 *
 * androidx.annotation.FloatRange
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 *
 * org.wheatgenetics.coordinate.R
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
public class Utils extends java.lang.Object
{
    private static final float minScaling = 0.5f, maxScaling = 2.0f;

    // region Types
    public enum Direction     { ERROR, DOWN_THEN_ACROSS , ACROSS_THEN_DOWN                   }
    public enum ProjectExport { ERROR, ONE_FILE_PER_GRID, ONE_FILE_ENTIRE_PROJECT            }
    public enum TypeOfUnique  { ERROR, CURRENT_GRID     , CURRENT_PROJECT        , ALL_GRIDS }
    // endregion

    private static android.content.SharedPreferences defaultSharedPreferencesInstance = null;  // ll

    @androidx.annotation.Nullable private static android.content.SharedPreferences
    getDefaultSharedPreferences(final android.content.Context context)
    {
        if (null == org.wheatgenetics.coordinate.preference.Utils.defaultSharedPreferencesInstance)
            org.wheatgenetics.coordinate.preference.Utils.defaultSharedPreferencesInstance =
                android.preference.PreferenceManager.getDefaultSharedPreferences(context);
        return org.wheatgenetics.coordinate.preference.Utils.defaultSharedPreferencesInstance;
    }

    // region Public Methods
    public static org.wheatgenetics.coordinate.preference.Utils.Direction getDirection(
    @androidx.annotation.NonNull final android.content.Context context)
    {
        final android.content.SharedPreferences defaultSharedPreferences =
            org.wheatgenetics.coordinate.preference.Utils.getDefaultSharedPreferences(context);
        if (null == defaultSharedPreferences)
            return org.wheatgenetics.coordinate.preference.Utils.Direction.ERROR;
        else
        {
            final java.lang.String direction;
            {
                final java.lang.String key = context.getString(
                    org.wheatgenetics.coordinate.R.string.DirectionPreferenceKey);
                direction = defaultSharedPreferences.getString(
                    /* key      => */ key,
                    /* defValue => */ context.getString(
                        org.wheatgenetics.coordinate.R.string.DirectionPreferenceDefault));
            }
            if (direction.equals(context.getString(
            org.wheatgenetics.coordinate.R.string.DirectionPreferenceDownThenAcrossEntryValue)))
                return org.wheatgenetics.coordinate.preference.Utils.Direction.DOWN_THEN_ACROSS;
            else
                if (direction.equals(context.getString(
                org.wheatgenetics.coordinate.R.string.DirectionPreferenceAcrossThenDownEntryValue)))
                    return
                        org.wheatgenetics.coordinate.preference.Utils.Direction.ACROSS_THEN_DOWN;
                else
                    return org.wheatgenetics.coordinate.preference.Utils.Direction.ERROR;
        }
    }

    public static boolean getSoundsOn(final android.content.Context context)
    {
        final android.content.SharedPreferences defaultSharedPreferences =
            org.wheatgenetics.coordinate.preference.Utils.getDefaultSharedPreferences(context);
        if (null == defaultSharedPreferences)
            return true;
        else
        {
            final java.lang.String key = context.getString(
                org.wheatgenetics.coordinate.R.string.SoundsOnPreferenceKey);
            return defaultSharedPreferences.getBoolean(key, /* defValue => */true);
        }
    }

    public static org.wheatgenetics.coordinate.preference.Utils.ProjectExport getProjectExport(
    @androidx.annotation.NonNull final android.content.Context context)
    {
        final android.content.SharedPreferences defaultSharedPreferences =
            org.wheatgenetics.coordinate.preference.Utils.getDefaultSharedPreferences(context);
        if (null == defaultSharedPreferences)
            return org.wheatgenetics.coordinate.preference.Utils.ProjectExport.ERROR;
        else
        {
            final java.lang.String projectExport;
            {
                final java.lang.String key = context.getString(
                    org.wheatgenetics.coordinate.R.string.ProjectExportPreferenceKey);
                projectExport = defaultSharedPreferences.getString(
                    /* key      => */ key,
                    /* defValue => */ context.getString(
                        org.wheatgenetics.coordinate.R.string.ProjectExportPreferenceDefault));
            }
            if (projectExport.equals(context.getString(
            org.wheatgenetics.coordinate.R.string.ProjectExportPreferenceOneFilePerGrid)))
                return
                    org.wheatgenetics.coordinate.preference.Utils.ProjectExport.ONE_FILE_PER_GRID;
            else
                if (projectExport.equals(context.getString(
                org.wheatgenetics.coordinate.R.string.ProjectExportPreferenceOneFileEntireProject)))
                    return org.wheatgenetics.coordinate.preference
                        .Utils.ProjectExport.ONE_FILE_ENTIRE_PROJECT;
                else
                    return org.wheatgenetics.coordinate.preference.Utils.ProjectExport.ERROR;
        }
    }

    @androidx.annotation.FloatRange(
    from = org.wheatgenetics.coordinate.preference.Utils.minScaling,
    to   = org.wheatgenetics.coordinate.preference.Utils.maxScaling)
    public static float getScaling(
    @androidx.annotation.NonNull final android.content.Context context)
    {
        final android.content.SharedPreferences defaultSharedPreferences =
            org.wheatgenetics.coordinate.preference.Utils.getDefaultSharedPreferences(context);
        if (null == defaultSharedPreferences)
            return 1.0f;
        else
        {
            final int scaling;
            {
                final java.lang.String key = context.getString(
                    org.wheatgenetics.coordinate.R.string.ScalingPreferenceKey);
                scaling = defaultSharedPreferences.getInt(key, /* defValue => */100);
            }
            return (float) scaling / 100.0f;
        }
    }

    public static boolean getUnique(
    @androidx.annotation.NonNull final android.content.Context context)
    {
        final android.content.SharedPreferences defaultSharedPreferences =
            org.wheatgenetics.coordinate.preference.Utils.getDefaultSharedPreferences(context);
        if (null == defaultSharedPreferences)
            return false;
        else
        {
            final java.lang.String key = context.getString(
                org.wheatgenetics.coordinate.R.string.UniqueCheckBoxPreferenceKey);
            return defaultSharedPreferences.getBoolean(key, /* defValue => */false);
        }
    }

    public static org.wheatgenetics.coordinate.preference.Utils.TypeOfUnique getTypeOfUniqueness(
    @androidx.annotation.NonNull final android.content.Context context)
    {
        final android.content.SharedPreferences defaultSharedPreferences =
            org.wheatgenetics.coordinate.preference.Utils.getDefaultSharedPreferences(context);
        if (null == defaultSharedPreferences)
            return org.wheatgenetics.coordinate.preference.Utils.TypeOfUnique.ERROR;
        else
        {
            final java.lang.String unique;
            {
                final java.lang.String key = context.getString(
                    org.wheatgenetics.coordinate.R.string.UniqueListPreferenceKey);
                unique = defaultSharedPreferences.getString(
                    /* key      => */ key,
                    /* defValue => */ context.getString(
                        org.wheatgenetics.coordinate.R.string.UniqueListPreferenceDefault));
            }
            if (unique.equals(context.getString(
            org.wheatgenetics.coordinate.R.string.UniqueListPreferenceCurrentGrid)))
                return org.wheatgenetics.coordinate.preference.Utils.TypeOfUnique.CURRENT_GRID;
            else
                if (unique.equals(context.getString(
                org.wheatgenetics.coordinate.R.string.UniqueListPreferenceCurrentProject)))
                    return
                        org.wheatgenetics.coordinate.preference.Utils.TypeOfUnique.CURRENT_PROJECT;
                else
                    return org.wheatgenetics.coordinate.preference.Utils.TypeOfUnique.ALL_GRIDS;
        }
    }
    // endregion
}