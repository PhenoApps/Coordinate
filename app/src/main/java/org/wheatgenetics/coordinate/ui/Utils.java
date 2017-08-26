package org.wheatgenetics.coordinate.ui;

/**
 * Uses:
 * android.app.AlertDialog.Builder
 * android.content.Context
 * android.content.DialogInterface
 * android.content.DialogInterface.OnClickListener
 * android.widget.EditText
 *
 * org.wheatgenetics.javalib.Utils
 *
 * org.wheatgenetics.androidlibrary.Utils
 */
class Utils extends java.lang.Object
{
    // region AlertDialog Methods
    // region alert() AlertDialog Methods
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

    // region alert(context, title, message) AlertDialog Methods
    static void alert(final android.content.Context context, final java.lang.String title,
    final java.lang.String message)
    {
        org.wheatgenetics.coordinate.ui.Utils.alert(context, title, message, "Ok",
            org.wheatgenetics.androidlibrary.Utils.cancellingOnClickListener(), null);
    }

    static void alert(final android.content.Context context, final int title,
    final java.lang.String message)
    {
        assert null != context;
        org.wheatgenetics.coordinate.ui.Utils.alert(context, context.getString(title), message);
    }

    static void alert(final android.content.Context context, final java.lang.String title,
    final int message)
    {
        assert null != context;
        org.wheatgenetics.coordinate.ui.Utils.alert(context, title, context.getString(message));
    }
    // endregion

    // region alert(context, title, message, yesRunnable) AlertDialog Methods
    static void alert(final android.content.Context context, final java.lang.String title,
    final java.lang.String message, final java.lang.Runnable yesRunnable)
    {
        org.wheatgenetics.coordinate.ui.Utils.alert(context, title, message, "Ok",
            new android.content.DialogInterface.OnClickListener()
            {
                @java.lang.Override
                public void onClick(final android.content.DialogInterface dialog, final int id)
                {
                    assert null != dialog; dialog.cancel();
                    if (null != yesRunnable) yesRunnable.run();
                }
            }, null);
    }

    static void alert(final android.content.Context context, final java.lang.String title,
    final int message, final java.lang.Runnable yesRunnable)
    {
        assert null != context; org.wheatgenetics.coordinate.ui.Utils.alert(
            context, title, context.getString(message), yesRunnable);
    }
    // endregion
    // endregion

    // region confirm() AlertDialog Methods
    private static void confirm(final android.content.Context context, final java.lang.String title,
    final java.lang.String message, final java.lang.Runnable yesRunnable,
    final java.lang.Runnable noRunnable)
    {
        org.wheatgenetics.coordinate.ui.Utils.alert(context, title, message, "Yes",
            new android.content.DialogInterface.OnClickListener()
            {
                @java.lang.Override
                public void onClick(final android.content.DialogInterface dialog, final int id)
                {
                    assert null != dialog; dialog.cancel();
                    if (null != yesRunnable) yesRunnable.run();
                }
            }, new android.content.DialogInterface.OnClickListener()
            {
                @java.lang.Override
                public void onClick(final android.content.DialogInterface dialog, final int id)
                {
                    assert null != dialog; dialog.cancel();
                    if (null != noRunnable) noRunnable.run();
                }
            });
    }

    // region confirm(context, title, message, yesRunnable) AlertDialog Methods
    static void confirm(final android.content.Context context, final java.lang.String title,
    final java.lang.String message, final java.lang.Runnable yesRunnable)
    { org.wheatgenetics.coordinate.ui.Utils.confirm(context, title, message, yesRunnable, null); }

    static void confirm(final android.content.Context context, final java.lang.String title,
    final int message, final java.lang.Runnable yesRunnable)
    {
        assert null != context; org.wheatgenetics.coordinate.ui.Utils.confirm(
            context, title, context.getString(message), yesRunnable);
    }

    static void confirm(final android.content.Context context, final int title, final int message,
    final java.lang.Runnable yesRunnable)
    {
        assert null != context; org.wheatgenetics.coordinate.ui.Utils.confirm(
            context, context.getString(title), message, yesRunnable);
    }
    // endregion

    // region confirm(context, title, message, yesRunnable, noRunnable) AlertDialog Methods
    static void confirm(final android.content.Context context, final java.lang.String title,
    final int message, final java.lang.Runnable yesRunnable, final java.lang.Runnable noRunnable)
    {
        assert null != context; org.wheatgenetics.coordinate.ui.Utils.confirm(
            context, title, context.getString(message), yesRunnable, noRunnable);
    }
    // endregion
    // endregion
    // endregion

    static java.lang.String getText(final android.widget.EditText editText)
    {
        assert null != editText;
        return org.wheatgenetics.javalib.Utils.adjust(editText.getText().toString());
    }
}