package org.wheatgenetics.coordinate;

/**
 * Uses:
 * android.app.Activity
 * android.app.AlertDialog.Builder
 * android.content.Context
 * android.content.DialogInterface
 * android.content.DialogInterface.OnClickListener
 * android.content.SharedPreferences
 * android.preference.PreferenceManager
 *
 * androidx.annotation.IntRange
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 * androidx.annotation.StringRes
 *
 * org.wheatgenetics.javalib.Dir.PermissionException
 *
 * org.wheatgenetics.androidlibrary.RequestDir
 * org.wheatgenetics.androidlibrary.Utils
 *
 * org.wheatgenetics.coordinate.Consts
 * org.wheatgenetics.coordinate.R
 * org.wheatgenetics.coordinate.SelectAlertDialog
 * org.wheatgenetics.coordinate.SelectAlertDialog.Handler
 * org.wheatgenetics.coordinate.TemplatesDir
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
public class Utils extends java.lang.Object
{
    // region Types
    public enum Advancement      { ERROR, DOWN_THEN_ACROSS , ACROSS_THEN_DOWN        }
    public enum ProjectExport    { ERROR, ONE_FILE_PER_GRID, ONE_FILE_ENTIRE_PROJECT }
    public enum TypeOfUniqueness { ERROR, CURRENT_GRID, CURRENT_PROJECT, ALL_GRIDS   }

    @java.lang.SuppressWarnings({"UnnecessaryInterfaceModifier"}) public interface Handler
    { public abstract void importTemplate(java.lang.String fileName); }
    // endregion

    private static android.content.SharedPreferences                                        // lazy
        defaultSharedPreferencesInstance = null;                                            //  load

    // region AlertDialog Methods
    // region Overview
    // private alert(String title, String message, positiveText, positiveListener, negativeListener)
    //     public alert(int title, String message)
    //         public alert(int message)
    //     public alert(int message, yesRunnable)
    //     private confirm(int title, String message, yesRunnable, noRunnable)
    //         public confirm(int title, String message, yesRunnable)
    //         private confirm(int title, int message, yesRunnable, noRunnable)
    //             confirm(int title, int message, yesRunnable)
    //                 confirm(int message, yesRunnable)
    //             public confirm(int message, yesRunnable, noRunnable)
    // endregion

    // region alert() AlertDialog Methods
    private static void alert(final android.content.Context context, final java.lang.String title,
    final java.lang.String message, final java.lang.String positiveButtonText,
    final android.content.DialogInterface.OnClickListener positiveButtonOnClickListener,
    @androidx.annotation.Nullable final android.content.DialogInterface.OnClickListener
        negativeButtonOnClickListener)
    {
        final android.app.AlertDialog.Builder builder =
            new android.app.AlertDialog.Builder(context);
        if (null != negativeButtonOnClickListener)
            builder.setNegativeButton("No", negativeButtonOnClickListener);
        builder.setTitle(title).setMessage(message).setPositiveButton(positiveButtonText,
            positiveButtonOnClickListener).show();
    }

    // region alert(title, message) AlertDialog Methods
    public static void alert(
    @androidx.annotation.NonNull   final android.content.Context context,
    @androidx.annotation.StringRes final int title, final java.lang.String message)
    {
        org.wheatgenetics.coordinate.Utils.alert(context, context.getString(title), message,
            "Ok",
            org.wheatgenetics.androidlibrary.Utils.cancellingOnClickListener(),
            null);
    }

    public static void alert(
    @androidx.annotation.NonNull   final android.content.Context context,
    @androidx.annotation.StringRes final int                     message)
    {
        org.wheatgenetics.coordinate.Utils.alert(context,
            org.wheatgenetics.coordinate.R.string.app_name, context.getString(message));
    }
    // endregion

    // region alert(title, message, yesRunnable) AlertDialog Method
    public static void alert(
    @androidx.annotation.NonNull   final android.content.Context context,
    @androidx.annotation.StringRes final int message, final java.lang.Runnable yesRunnable)
    {
        org.wheatgenetics.coordinate.Utils.alert(context,
            context.getString(org.wheatgenetics.coordinate.R.string.app_name),
            context.getString(message),"Ok",
            new android.content.DialogInterface.OnClickListener()
            {
                @java.lang.Override
                public void onClick(final android.content.DialogInterface dialog, final int which)
                {
                    if (null != dialog) dialog.cancel();
                    if (null != yesRunnable) yesRunnable.run();
                }
            },null);
    }
    // endregion
    // endregion

    // region confirm() AlertDialog Methods
    private static void confirm(
    @androidx.annotation.NonNull   final android.content.Context context,
    @androidx.annotation.StringRes final int title, final java.lang.String message,
                                   final java.lang.Runnable yesRunnable,
    @androidx.annotation.Nullable  final java.lang.Runnable noRunnable )
    {
        org.wheatgenetics.coordinate.Utils.alert(context, context.getString(title),
            message,"Yes", new android.content.DialogInterface.OnClickListener()
            {
                @java.lang.Override
                public void onClick(final android.content.DialogInterface dialog, final int which)
                {
                    if (null != dialog) dialog.cancel();
                    if (null != yesRunnable) yesRunnable.run();
                }
            }, new android.content.DialogInterface.OnClickListener()
            {
                @java.lang.Override
                public void onClick(final android.content.DialogInterface dialog, final int which)
                {
                    if (null != dialog) dialog.cancel();
                    if (null != noRunnable) noRunnable.run();
                }
            });
    }

    // region confirm(title, String message, yesRunnable) AlertDialog Method
    public static void confirm(
    @androidx.annotation.NonNull   final android.content.Context context,
    @androidx.annotation.StringRes final int title, final java.lang.String message,
                                   final java.lang.Runnable yesRunnable)
    {
        org.wheatgenetics.coordinate.Utils.confirm(
            context, title, message, yesRunnable,null);
    }
    // endregion

    // region confirm(title, int message, yesRunnable) AlertDialog Methods
    private static void confirm(
    @androidx.annotation.NonNull   final android.content.Context context    ,
    @androidx.annotation.StringRes final int                     title      ,
    @androidx.annotation.StringRes final int                     message    ,
                                   final java.lang.Runnable      yesRunnable,
    @androidx.annotation.Nullable  final java.lang.Runnable      noRunnable )
    {
        org.wheatgenetics.coordinate.Utils.confirm(context, title,
            context.getString(message), yesRunnable, noRunnable);
    }

    public static void confirm(
    @androidx.annotation.NonNull   final android.content.Context context,
    @androidx.annotation.StringRes final int                     title  ,
    @androidx.annotation.StringRes final int message, final java.lang.Runnable yesRunnable)
    {
        org.wheatgenetics.coordinate.Utils.confirm(
            context, title, message, yesRunnable,null);
    }

    public static void confirm(
    @androidx.annotation.NonNull   final android.content.Context context,
    @androidx.annotation.StringRes final int message, final java.lang.Runnable yesRunnable)
    {
        org.wheatgenetics.coordinate.Utils.confirm(context,
            org.wheatgenetics.coordinate.R.string.app_name, message, yesRunnable);
    }
    // endregion

    // region confirm(title, message, yesRunnable, noRunnable) AlertDialog Method
    public static void confirm(
    @androidx.annotation.NonNull   final android.content.Context context,
    @androidx.annotation.StringRes final int message, final java.lang.Runnable yesRunnable,
                                   final java.lang.Runnable noRunnable)
    {
        org.wheatgenetics.coordinate.Utils.confirm(context,
            org.wheatgenetics.coordinate.R.string.app_name, message, yesRunnable, noRunnable);
    }
    // endregion
    // endregion
    // endregion

    public static int valid(final int value, final int minValue)
    {
        if (value < minValue)
            throw new java.lang.IllegalArgumentException(
                java.lang.String.format("value must be >= %d", minValue));
        else
            return value;
    }

    // region Default SharedPreferences Methods
    private static android.content.SharedPreferences getDefaultSharedPreferences(
    final android.content.Context context)
    {
        if (null == org.wheatgenetics.coordinate.Utils.defaultSharedPreferencesInstance)
            org.wheatgenetics.coordinate.Utils.defaultSharedPreferencesInstance =
                android.preference.PreferenceManager.getDefaultSharedPreferences(context);
        return org.wheatgenetics.coordinate.Utils.defaultSharedPreferencesInstance;
    }

    public static org.wheatgenetics.coordinate.Utils.Advancement getAdvancement(
    @androidx.annotation.NonNull final android.content.Context context)
    {
        final android.content.SharedPreferences defaultSharedPreferences =
            org.wheatgenetics.coordinate.Utils.getDefaultSharedPreferences(context);
        if (null == defaultSharedPreferences)
            return org.wheatgenetics.coordinate.Utils.Advancement.ERROR;
        else
        {
            final java.lang.String advancement;
            {
                final java.lang.String key = context.getString(
                    org.wheatgenetics.coordinate.R.string.AdvancementPreferenceKey);
                advancement = defaultSharedPreferences.getString(
                    /* key      => */ key,
                    /* defValue => */ context.getString(
                        org.wheatgenetics.coordinate.R.string.AdvancementPreferenceDefault));
            }
            if (advancement.equals(context.getString(
            org.wheatgenetics.coordinate.R.string.AdvancementPreferenceDownThenAcrossEntryValue)))
                return org.wheatgenetics.coordinate.Utils.Advancement.DOWN_THEN_ACROSS;
            else
                if (advancement.equals(context.getString(org.wheatgenetics.coordinate
                .R.string.AdvancementPreferenceAcrossThenDownEntryValue)))
                    return org.wheatgenetics.coordinate.Utils.Advancement.ACROSS_THEN_DOWN;
                else
                    return org.wheatgenetics.coordinate.Utils.Advancement.ERROR;
        }
    }

    public static boolean getSoundOn(final android.content.Context context)
    {
        final android.content.SharedPreferences defaultSharedPreferences =
            org.wheatgenetics.coordinate.Utils.getDefaultSharedPreferences(context);
        if (null == defaultSharedPreferences)
            return true;
        else
        {
            final java.lang.String key = context.getString(
                org.wheatgenetics.coordinate.R.string.SoundOnPreferenceKey);
            return defaultSharedPreferences.getBoolean(key, /* defValue => */true);
        }
    }

    public static org.wheatgenetics.coordinate.Utils.ProjectExport getProjectExport(
    @androidx.annotation.NonNull final android.content.Context context)
    {
        final android.content.SharedPreferences defaultSharedPreferences =
            org.wheatgenetics.coordinate.Utils.getDefaultSharedPreferences(context);
        if (null == defaultSharedPreferences)
            return org.wheatgenetics.coordinate.Utils.ProjectExport.ERROR;
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
                return org.wheatgenetics.coordinate.Utils.ProjectExport.ONE_FILE_PER_GRID;
            else
                if (projectExport.equals(context.getString(
                org.wheatgenetics.coordinate.R.string.ProjectExportPreferenceOneFileEntireProject)))
                    return org.wheatgenetics.coordinate.Utils.ProjectExport.ONE_FILE_ENTIRE_PROJECT;
                else
                    return org.wheatgenetics.coordinate.Utils.ProjectExport.ERROR;
        }
    }

    public static boolean getUniqueness(
    @androidx.annotation.NonNull final android.content.Context context)
    {
        final android.content.SharedPreferences defaultSharedPreferences =
            org.wheatgenetics.coordinate.Utils.getDefaultSharedPreferences(context);
        if (null == defaultSharedPreferences)
            return false;
        else
        {
            final java.lang.String key = context.getString(
                org.wheatgenetics.coordinate.R.string.UniquenessCheckBoxPreferenceKey);
            return defaultSharedPreferences.getBoolean(key, /* defValue => */false);
        }
    }

    public static org.wheatgenetics.coordinate.Utils.TypeOfUniqueness getTypeOfUniqueness(
    @androidx.annotation.NonNull final android.content.Context context)
    {
        final android.content.SharedPreferences defaultSharedPreferences =
            org.wheatgenetics.coordinate.Utils.getDefaultSharedPreferences(context);
        if (null == defaultSharedPreferences)
            return org.wheatgenetics.coordinate.Utils.TypeOfUniqueness.ERROR;
        else
        {
            final java.lang.String uniqueness;
            {
                final java.lang.String key = context.getString(
                    org.wheatgenetics.coordinate.R.string.UniquenessListPreferenceKey);
                uniqueness = defaultSharedPreferences.getString(
                    /* key      => */ key,
                    /* defValue => */ context.getString(
                        org.wheatgenetics.coordinate.R.string.UniquenessListPreferenceDefault));
            }
            if (uniqueness.equals(context.getString(
            org.wheatgenetics.coordinate.R.string.UniquenessListPreferenceCurrentGrid)))
                return org.wheatgenetics.coordinate.Utils.TypeOfUniqueness.CURRENT_GRID;
            else
                if (uniqueness.equals(context.getString(
                org.wheatgenetics.coordinate.R.string.UniquenessListPreferenceCurrentProject)))
                    return org.wheatgenetics.coordinate.Utils.TypeOfUniqueness.CURRENT_PROJECT;
                else
                    return org.wheatgenetics.coordinate.Utils.TypeOfUniqueness.ALL_GRIDS;
        }
    }
    // endregion

    public static java.lang.String convert(
    @androidx.annotation.IntRange(from = 0) final int offsetFromA)
    {
        if (offsetFromA < 0)
            throw new java.lang.IllegalArgumentException();
        else
        {
            @androidx.annotation.IntRange(from = 0) final int              modulas     ;
                                                    final java.lang.String rightPortion;
            {
                final int numberOfUppercaseLetters = 26;
                modulas      = offsetFromA / numberOfUppercaseLetters;
                rightPortion = java.lang.Character.toString(
                    (char) ('A' + offsetFromA % numberOfUppercaseLetters));
            }

            if (0 == modulas)
                return rightPortion;
            else
                return org.wheatgenetics.coordinate.Utils.convert(                      // recursion
                    modulas - 1) + rightPortion;
        }
    }

    // region RequestDir Methods
    @androidx.annotation.NonNull
    public static org.wheatgenetics.androidlibrary.RequestDir makeRequestDir(
    final android.app.Activity activity, final java.lang.String name, final int requestCode)
    {
        return new org.wheatgenetics.androidlibrary.RequestDir(
            /* activity            => */ activity                                                  ,
            /* name                => */ name                                                      ,
            /* blankHiddenFileName => */ org.wheatgenetics.coordinate.Consts.BLANK_HIDDEN_FILE_NAME,
            /* requestCode         => */ requestCode);
    }

    public static void createCoordinateDirIfMissing(
    final android.app.Activity activity, final int requestCode)
    throws java.io.IOException, org.wheatgenetics.javalib.Dir.PermissionException
    {
        final org.wheatgenetics.androidlibrary.RequestDir coordinateDir =
            org.wheatgenetics.coordinate.Utils.makeRequestDir(activity,
                org.wheatgenetics.coordinate.Consts.COORDINATE_DIR_NAME, requestCode);
        coordinateDir.createIfMissing();                 // throws java.io.IOException, org.wheatge-
    }                                                    //  netics.javalib.Dir.PermissionException

    /** This directory is used to transfer templates between devices. */
    @androidx.annotation.NonNull
    public static org.wheatgenetics.coordinate.TemplatesDir templatesDir(
    final android.app.Activity activity, final int requestCode)
    throws java.io.IOException, org.wheatgenetics.javalib.Dir.PermissionException
    {
        org.wheatgenetics.coordinate.Utils.createCoordinateDirIfMissing(// throws java.io.IOExcep-
            activity, requestCode);                                     //  tion, org.wheatgenetics-
                                                                        //  .javalib.Dir.Permission-
        final org.wheatgenetics.coordinate.TemplatesDir result =        //  Exception
            new org.wheatgenetics.coordinate.TemplatesDir(
                /* activity => */ activity,
                /* name     => */org.wheatgenetics.coordinate.Consts.COORDINATE_DIR_NAME +
                    "/Templates",
                /* blankHiddenFileName => */
                    org.wheatgenetics.coordinate.Consts.BLANK_HIDDEN_FILE_NAME,
                /* requestCode => */ requestCode);
        result.createIfMissing();                        // throws java.io.IOException, org.wheatge-
        return result;                                   //  netics.javalib.Dir.PermissionException
    }
    // endregion

    public static void selectExportedTemplate(
    @androidx.annotation.NonNull final org.wheatgenetics.coordinate.TemplatesDir  templatesDir,
    @androidx.annotation.NonNull final android.app.Activity                       activity    ,
    @androidx.annotation.NonNull final org.wheatgenetics.coordinate.Utils.Handler handler     )
    {
        // noinspection CStyleArrayDeclaration
        final java.lang.String fileNames[];
        try { fileNames = templatesDir.listXml() /* throws PermissionException */; }
        catch (final org.wheatgenetics.javalib.Dir.PermissionException e)
        { org.wheatgenetics.androidlibrary.Utils.showLongToast(activity, e.getMessage()); return; }

        if (null != fileNames) if (fileNames.length > 0)
        {
            final org.wheatgenetics.coordinate.SelectAlertDialog selectAlertDialog =
                new org.wheatgenetics.coordinate.SelectAlertDialog(activity,
                    new org.wheatgenetics.coordinate.SelectAlertDialog.Handler()
                    {
                        @java.lang.Override public void select(final int which)
                        { handler.importTemplate(fileNames[which]); }
                    });
            selectAlertDialog.show(
                org.wheatgenetics.coordinate.R.string.UtilsSelectExportedTemplateTitle, fileNames);
        }

    }
}