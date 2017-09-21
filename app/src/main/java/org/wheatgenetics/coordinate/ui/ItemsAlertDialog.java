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
    private android.content.DialogInterface.OnClickListener onClickListener = null;

    ItemsAlertDialog(final android.app.Activity activity) { super(activity); }

    @java.lang.Override
    org.wheatgenetics.coordinate.ui.AlertDialog setOnClickListener(
    final android.content.DialogInterface.OnClickListener onClickListener)
    { this.onClickListener = onClickListener; return this; }

    // region Package Methods
    void configureBeforeShow() {}

    org.wheatgenetics.coordinate.ui.AlertDialog setItems(final java.lang.String items[])
    { this.setItems(items, this.onClickListener); return this; }

    void configureAndShow() { this.configureBeforeShow(); this.builder().create().show(); }
    // endregion
}