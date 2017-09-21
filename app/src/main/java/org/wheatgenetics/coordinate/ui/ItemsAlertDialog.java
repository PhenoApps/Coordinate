package org.wheatgenetics.coordinate.ui;

/**
 * Uses:
 * android.app.Activity
 * android.content.DialogInterface.OnClickListener
 *
 * org.wheatgenetics.coordinate.ui.AlertDialog
 */
abstract class ItemsAlertDialog extends org.wheatgenetics.coordinate.ui.AlertDialog
{
    private android.content.DialogInterface.OnClickListener onClickListener;

    ItemsAlertDialog(final android.app.Activity activity) { super(activity); }

    // region Package Methods
    void setOnClickListener(final android.content.DialogInterface.OnClickListener onClickListener)
    { this.onClickListener = onClickListener; }

    void show(final java.lang.String items[])
    {
        if (null != items)
        {
            this.setItems(items, this.onClickListener);
            this.builder().create().show();
        }
    }
    // endregion
}