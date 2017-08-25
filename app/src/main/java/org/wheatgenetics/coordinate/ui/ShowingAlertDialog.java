package org.wheatgenetics.coordinate.ui;

/**
 * Uses:
 * android.app.Activity
 * android.app.AlertDialog
 * android.app.AlertDialog.Builder
 *
 * org.wheatgenetics.coordinate.ui.ActivityAlertDialog
 */
abstract class ShowingAlertDialog extends org.wheatgenetics.coordinate.ui.ActivityAlertDialog
{
    private android.app.AlertDialog alertDialog = null;

    ShowingAlertDialog(final android.app.Activity activity) { super(activity); }

    // region Package Methods
    android.app.AlertDialog.Builder configure(final int titleId)
    { if (null == this.builder) this.makeBuilder(titleId); return this.builder; }

    void show()
    {
        if (null == this.alertDialog)
        {
            assert null != this.builder;
            this.alertDialog = this.builder.create();
        }
        assert null != this.alertDialog; this.alertDialog.show();
    }
    // end
}