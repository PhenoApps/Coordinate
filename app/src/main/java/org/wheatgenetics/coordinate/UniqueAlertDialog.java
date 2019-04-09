package org.wheatgenetics.coordinate;

/**
 * Uses:
 * android.app.Activity
 * android.support.annotation.StringRes
 *
 * org.wheatgenetics.androidlibrary.AlertDialog
 */
class UniqueAlertDialog extends org.wheatgenetics.androidlibrary.AlertDialog
{
    UniqueAlertDialog(final android.app.Activity activity) { super(activity); }

    @java.lang.Override public void configure()
    { this.setTitle(org.wheatgenetics.coordinate.R.string.UniqueAlertDialogTitle); }

    void show(@android.support.annotation.StringRes final int message)
    { this.setMessage(message); this.show(); }
}