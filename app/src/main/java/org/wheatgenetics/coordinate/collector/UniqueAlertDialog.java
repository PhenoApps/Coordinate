package org.wheatgenetics.coordinate.collector;

/**
 * Uses:
 * android.app.Activity
 *
 * androidx.annotation.NonNull
 *
 * org.wheatgenetics.androidlibrary.AlertDialog
 *
 * org.wheatgenetics.coordinate.R
 */
class UniqueAlertDialog extends org.wheatgenetics.androidlibrary.AlertDialog
{
    UniqueAlertDialog(final android.app.Activity activity) { super(activity); }

    @java.lang.Override public void configure()
    { this.setTitle(org.wheatgenetics.coordinate.R.string.UniqueAlertDialogTitle); }

    void show(@androidx.annotation.NonNull final java.lang.String message)
    { this.setMessage(message); this.createShow(); }
}