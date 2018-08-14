package org.wheatgenetics.coordinate.tc;

/**
 * Uses:
 * android.app.Activity
 * android.content.DialogInterface
 * android.content.DialogInterface.OnClickListener
 * android.content.DialogInterface.OnMultiChoiceClickListener
 * android.support.annotation.StringRes
 *
 * org.wheatgenetics.androidlibrary.MultiChoiceItemsAlertDialog
 *
 * org.wheatgenetics.coordinate.R
 */
class ExcludedRowsOrColsAlertDialog
extends org.wheatgenetics.androidlibrary.MultiChoiceItemsAlertDialog
{
    @java.lang.SuppressWarnings({"UnnecessaryInterfaceModifier"})
    interface Handler { public abstract void excludeRowsOrCols(boolean checkedItems[]); }

    // region Fields
    private final java.lang.String                                                      label  ;
    private final org.wheatgenetics.coordinate.tc.ExcludedRowsOrColsAlertDialog.Handler handler;

    private boolean titleHasBeenSet = false;
    // endregion

    private void excludeRowsOrCols(final boolean checkedItems[])
    { assert null != this.handler; this.handler.excludeRowsOrCols(checkedItems); }

    ExcludedRowsOrColsAlertDialog(final android.app.Activity activity,
    @android.support.annotation.StringRes final int label,
    final org.wheatgenetics.coordinate.tc.ExcludedRowsOrColsAlertDialog.Handler handler)
    { super(activity); this.label = this.getString(label); this.handler = handler; }

    void show(final java.lang.String items[], final boolean checkedItems[])
    {
        if (null != items && null != checkedItems)
        {
            if (!this.titleHasBeenSet)
            {
                this.setTitle(this.getString(
                    org.wheatgenetics.coordinate.R.string.ExcludedRowsOrColsAlertDialogTitle) +
                    " - " + this.label + 's');
                this.titleHasBeenSet = true;
            }

            this.setOKPositiveButton(new android.content.DialogInterface.OnClickListener()
                {
                    @java.lang.Override public void onClick(
                    final android.content.DialogInterface dialog, final int which)
                    {
                        org.wheatgenetics.coordinate.tc
                            .ExcludedRowsOrColsAlertDialog.this.excludeRowsOrCols(checkedItems);
                    }
                });
            this.show(items, checkedItems,
                new android.content.DialogInterface.OnMultiChoiceClickListener()
                {
                    @java.lang.Override
                    public void onClick(final android.content.DialogInterface dialog,
                    final int which, final boolean isChecked) { checkedItems[which] = isChecked; }
                });
        }
    }
}