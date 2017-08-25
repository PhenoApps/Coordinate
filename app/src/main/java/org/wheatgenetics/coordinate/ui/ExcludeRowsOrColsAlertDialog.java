package org.wheatgenetics.coordinate.ui;

/**
 * Uses:
 * android.app.AlertDialog.Builder
 * android.content.Context
 * android.content.DialogInterface
 * android.content.DialogInterface.OnClickListener
 * android.content.DialogInterface.OnMultiChoiceClickListener
 *
 * org.wheatgenetics.coordinate.R
 * org.wheatgenetics.coordinate.ui.MultiChoiceItemsAlertDialog
 */
class ExcludeRowsOrColsAlertDialog
extends org.wheatgenetics.coordinate.ui.MultiChoiceItemsAlertDialog
{
    interface Handler { public abstract void process(boolean checkedItems[]); }

    // region Fields
    private final int                                                                  label  ;
    private final org.wheatgenetics.coordinate.ui.ExcludeRowsOrColsAlertDialog.Handler handler;
    // endregion

    private void process(final boolean checkedItems[])
    { assert null != this.handler; this.handler.process(checkedItems); }

    ExcludeRowsOrColsAlertDialog(final android.content.Context context, final int label,
    final org.wheatgenetics.coordinate.ui.ExcludeRowsOrColsAlertDialog.Handler handler)
    { super(context); this.label = label; this.handler = handler; }

    // region Overridden Methods
    @java.lang.Override
    android.app.AlertDialog.Builder makeBuilder()
    {
        return super.makeBuilder().setTitle(
            this.getString(org.wheatgenetics.coordinate.R.string.exclude_title) + " - " +
            this.getString(this.label                                         )          );
    }

    @java.lang.Override
    android.app.AlertDialog.Builder configureBuilder(final java.lang.CharSequence[] items,
    final boolean[] checkedItems,
    final android.content.DialogInterface.OnMultiChoiceClickListener onMultiChoiceClickListener)
    {
        return super.configureBuilder(items, checkedItems, onMultiChoiceClickListener)
            .setPositiveButton(org.wheatgenetics.coordinate.R.string.ok,
                new android.content.DialogInterface.OnClickListener()
                {
                    @java.lang.Override
                    public void onClick(final android.content.DialogInterface dialog,
                    final int which)
                    {
                        org.wheatgenetics.coordinate.ui.
                            ExcludeRowsOrColsAlertDialog.this.process(checkedItems);
                    }
                });
    }
    // endregion

    void show(final java.lang.String items[], final boolean checkedItems[])
    {
        this.show(items, checkedItems,
            new android.content.DialogInterface.OnMultiChoiceClickListener()
            {
                @java.lang.Override
                public void onClick(final android.content.DialogInterface dialog, final int which,
                final boolean isChecked) { checkedItems[which] = isChecked; }
            });
    }
}