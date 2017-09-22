package org.wheatgenetics.coordinate.ui;

/**
 * Uses:
 * android.app.Activity
 * android.content.DialogInterface.OnMultiChoiceClickListener
 *
 * org.wheatgenetics.coordinate.ui.AlertDialog
 */
abstract class MultiChoiceItemsAlertDialog extends org.wheatgenetics.coordinate.ui.AlertDialog
{
    MultiChoiceItemsAlertDialog(final android.app.Activity activity) { super(activity); }

    @java.lang.Override
    void configure() { this.setNegativeButton(); }

    void show(final java.lang.String items[], final boolean checkedItems[],
    final android.content.DialogInterface.OnMultiChoiceClickListener listener)
    {
        if (null != items && null != checkedItems)
        {
            this.setMultiChoiceItems(items, checkedItems, listener);
            this.createShow();
        }
    }
}