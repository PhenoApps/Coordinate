package org.wheatgenetics.coordinate.utils;

/**
 * Uses:
 * android.app.AlertDialog
 * android.app.AlertDialog.Builder
 * android.content.Context
 * android.content.DialogInterface
 * android.text.format.DateFormat
 *
 * org.wheatgenetics.androidlibrary.Utils
 */
public class Utils extends java.lang.Object
{
    // region AlertDialog Methods
    private static void alert(final android.content.Context context, final java.lang.String title,
    final java.lang.String message, final java.lang.String positiveButtonText,
    final android.content.DialogInterface.OnClickListener positiveButtonOnClickListener,
    final android.content.DialogInterface.OnClickListener negativeButtonOnClickListener)
    {
        final android.app.AlertDialog.Builder builder =
            new android.app.AlertDialog.Builder(context);
        if (null != negativeButtonOnClickListener)
            builder.setNegativeButton("No", negativeButtonOnClickListener);
        builder.setTitle(title).setMessage(message).setPositiveButton(positiveButtonText,
            positiveButtonOnClickListener).show();
    }

    public static void alert(final android.content.Context context, final java.lang.String title,
    final java.lang.String message)
    {
        org.wheatgenetics.coordinate.utils.Utils.alert(context, title, message, "Ok",
            org.wheatgenetics.androidlibrary.Utils.cancellingOnClickListener(), null);
    }

    public static void alert(final android.content.Context context, final java.lang.String title,
    final java.lang.String message, final java.lang.Runnable yesRunnable)
    {
        org.wheatgenetics.coordinate.utils.Utils.alert(context, title, message, "Ok",
            new android.content.DialogInterface.OnClickListener()
                {
                    @java.lang.Override
                    public void onClick(final android.content.DialogInterface dialog, final int id)
                    {
                        assert null != dialog;
                        dialog.cancel();
                        if (null != yesRunnable) yesRunnable.run();
                    }
                },
            null);
    }

    public static void confirm(final android.content.Context context, final java.lang.String title,
    final java.lang.String message, final java.lang.Runnable yesRunnable,
    final java.lang.Runnable noRunnable)
    {
        org.wheatgenetics.coordinate.utils.Utils.alert(context, title, message, "Yes",
            new android.content.DialogInterface.OnClickListener()
            {
                @java.lang.Override
                public void onClick(final android.content.DialogInterface dialog, final int id)
                {
                    assert null != dialog;
                    dialog.cancel();
                    if (null != yesRunnable) yesRunnable.run();
                }
            }, new android.content.DialogInterface.OnClickListener()
            {
                @java.lang.Override
                public void onClick(final android.content.DialogInterface dialog, final int id)
                {
                    assert null != dialog;
                    dialog.cancel();
                    if (null != noRunnable) noRunnable.run();
                }
            });
    }
    // endregion

    // region Date Methods
    public static java.lang.CharSequence formatDate(final long date)
    { return android.text.format.DateFormat.format("yyyy-MM-dd", new java.util.Date(date)); }

    public static java.lang.String getCurrentDate()
    {
        return org.wheatgenetics.coordinate.utils.Utils.formatDate(
            java.lang.System.currentTimeMillis()).toString();
    }
    // endregion

    public static int parseInt(final java.lang.String s)
    {
        try                                             { return java.lang.Integer.parseInt(s); }
        catch (final java.lang.NumberFormatException e) { return                            -1; }
    }
}