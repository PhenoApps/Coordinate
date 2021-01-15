package org.wheatgenetics.androidlibrary;

import org.wheatgenetics.coordinate.R;

/**
 * Uses:
 * android.content.Context
 * android.content.DialogInterface
 * android.content.DialogInterface.OnClickListener
 * android.content.Intent
 * android.media.MediaScannerConnection
 * android.net.Uri
 * android.text.format.DateFormat
 * android.widget.EditText
 * android.widget.TextView
 * android.widget.Toast
 *
 * org.wheatgenetics.javalib.Utils
 *
 * org.wheatgenetics.androidlibrary.R
 */
@SuppressWarnings({"ClassExplicitlyExtendsObject"})
public class Utils extends Object
{
    private static android.content.DialogInterface.OnClickListener
        dismissingOnClickListenerInstance = null, cancellingOnClickListenerInstance = null;

    // region OnClickListener Methods
    public static android.content.DialogInterface.OnClickListener dismissingOnClickListener()
    {
        if (null == Utils.dismissingOnClickListenerInstance)
            Utils.dismissingOnClickListenerInstance =
                new android.content.DialogInterface.OnClickListener()
                {
                    @Override public void onClick(
                    final android.content.DialogInterface dialog, final int which)
                    { if (null != dialog) dialog.dismiss(); }
                };
        return Utils.dismissingOnClickListenerInstance;
    }

    @SuppressWarnings({"WeakerAccess"})
    public static android.content.DialogInterface.OnClickListener cancellingOnClickListener()
    {
        if (null == Utils.cancellingOnClickListenerInstance)
            Utils.cancellingOnClickListenerInstance =
                new android.content.DialogInterface.OnClickListener()
                {
                    @Override public void onClick(
                    final android.content.DialogInterface dialog, final int which)
                    { if (null != dialog) dialog.cancel(); }
                };
        return Utils.cancellingOnClickListenerInstance;
    }
    // endregion

    // region Toast Methods
    private static void showToast(final android.content.Context context,
    final CharSequence text, final int duration)
    { android.widget.Toast.makeText(context, text, duration).show(); }

    public static void showLongToast(final android.content.Context context,
    final CharSequence text)
    {
        Utils.showToast(
            context, text, android.widget.Toast.LENGTH_LONG);
    }

    public static void showShortToast(final android.content.Context context,
    final CharSequence text)
    {
        Utils.showToast(
            context, text, android.widget.Toast.LENGTH_SHORT);
    }
    // endregion

    // region File Methods
    @SuppressWarnings({"WeakerAccess", "UnusedReturnValue"})
    public static java.io.File makeFileDiscoverable(final android.content.Context context,
    final java.io.File file)
    {
        if (null != file)
        {
            android.media.MediaScannerConnection.scanFile(
                /* context   => */ context                                                    ,
                /* paths     => */ org.wheatgenetics.javalib.Utils.stringArray(file.getPath()),
                /* mimeTypes => */null,
                /* callback  => */null);
            if (null != context) context.sendBroadcast(new android.content.Intent(
                /* action => */ android.content.Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                /* uri    => */ android.net.Uri.fromFile(file)                       ));
        }
        return file;
    }

    @SuppressWarnings({"unused"}) public static void shareFile(
    final android.content.Context context, final android.net.Uri uri)
    {
        final android.content.Intent intent = new android.content.Intent(
            android.content.Intent.ACTION_SEND).setType("text/plain").putExtra(
                android.content.Intent.EXTRA_STREAM, uri);

        if (null != context) context.startActivity(android.content.Intent.createChooser(
            intent, context.getString(R.string.sendingFile)));
    }
    // endregion

    // region getText() Methods
    @SuppressWarnings({"unused"})
    public static String getText(final android.widget.TextView textView)
    {
        return null == textView ? null :
            org.wheatgenetics.javalib.Utils.adjust(textView.getText().toString());
    }

    public static String getText(final android.widget.EditText editText)
    {
        return null == editText ? null :
            org.wheatgenetics.javalib.Utils.adjust(editText.getText().toString());
    }
    // endregion

    @SuppressWarnings({"unused"})
    public static CharSequence formatDate(final long date)
    {
        return android.text.format.DateFormat.format(
            "yyyy-MM-dd", new java.util.Date(date));
    }
}