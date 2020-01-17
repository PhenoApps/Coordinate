package org.wheatgenetics.coordinate;

/**
 * Uses:
 * android.app.Activity
 * android.app.AlertDialog.Builder
 * android.content.Context
 * android.content.DialogInterface
 * android.content.DialogInterface.OnClickListener
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
 * org.wheatgenetics.coordinate.database.TemplatesTable
 *
 * org.wheatgenetics.coordinate.model.TemplateModel
 * org.wheatgenetics.coordinate.model.TemplateModels
 * org.wheatgenetics.coordinate.model.TemplateType
 *
 * org.wheatgenetics.coordinate.Consts
 * org.wheatgenetics.coordinate.R
 * org.wheatgenetics.coordinate.TemplatesDir
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
public class Utils extends java.lang.Object
{
    private static org.wheatgenetics.coordinate.database.TemplatesTable
        templatesTableInstance = null;                                                  // lazy load

    // region AlertDialog Methods
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
        org.wheatgenetics.coordinate.Utils.alert(
            context, context.getString(title), message,"Ok",
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

    // region initializeDefaultTemplates() Methods
    @androidx.annotation.NonNull
    private static org.wheatgenetics.coordinate.database.TemplatesTable templatesTable(
    final android.content.Context context)
    {
        if (null == org.wheatgenetics.coordinate.Utils.templatesTableInstance)
            org.wheatgenetics.coordinate.Utils.templatesTableInstance =
                new org.wheatgenetics.coordinate.database.TemplatesTable(context);
        return org.wheatgenetics.coordinate.Utils.templatesTableInstance;
    }

    /*
     * Adds default templates to database if they aren't there already.  If they are there then they
     * are updated to their default values.
     */
    public static void initializeDefaultTemplates(final android.content.Context context)
    {
        final org.wheatgenetics.coordinate.model.TemplateModels defaultTemplateModels =
            org.wheatgenetics.coordinate.model.TemplateModels.makeDefault();
        if (defaultTemplateModels.size() > 0)
        {
            final org.wheatgenetics.coordinate.database.TemplatesTable templatesTable =
                org.wheatgenetics.coordinate.Utils.templatesTable(context);
            for (final org.wheatgenetics.coordinate.model.TemplateModel defaultTemplateModel:
            defaultTemplateModels)
            {
                final org.wheatgenetics.coordinate.model.TemplateType defaultTemplateType =
                    defaultTemplateModel.getType();
                if (templatesTable.exists(defaultTemplateType))
                {
                    {
                        final org.wheatgenetics.coordinate.model.TemplateModel
                            existingTemplateModel = templatesTable.get(defaultTemplateType);
                        if (null != existingTemplateModel)
                            defaultTemplateModel.setId(existingTemplateModel.getId());
                    }
                    templatesTable.update(defaultTemplateModel);
                }
                else templatesTable.insert(defaultTemplateModel);
            }
        }
    }
    // endregion
}