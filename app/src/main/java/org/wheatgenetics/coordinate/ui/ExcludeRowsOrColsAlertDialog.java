package org.wheatgenetics.coordinate.ui;

/**
 * Uses:
 * android.app.Activity
 * android.app.AlertDialog.Builder
 * android.content.DialogInterface
 * android.content.DialogInterface.OnClickListener
 * android.content.DialogInterface.OnMultiChoiceClickListener
 *
 * org.wheatgenetics.coordinate.R
 *
 * org.wheatgenetics.coordinate.ui.MultiChoiceItemsAlertDialog
 */
class ExcludeRowsOrColsAlertDialog extends org.wheatgenetics.coordinate.ui.AlertDialog
{
    interface Handler { public abstract void process(boolean checkedItems[]); }

    // region Fields
    private final java.lang.String                                                     label  ;
    private final org.wheatgenetics.coordinate.ui.ExcludeRowsOrColsAlertDialog.Handler handler;
    // endregion

    private void process(final boolean checkedItems[])
    { assert null != this.handler; this.handler.process(checkedItems); }

    ExcludeRowsOrColsAlertDialog(final android.app.Activity activity, final int label,
    final org.wheatgenetics.coordinate.ui.ExcludeRowsOrColsAlertDialog.Handler handler)
    { super(activity); this.label = this.getString(label); this.handler = handler; }

    @java.lang.Override
    void configureAfterConstruction()
    {
        this.setNegativeButton(); // TODO: Put in common superclass.
        this.setTitle(
            this.getString(org.wheatgenetics.coordinate.R.string.exclude_title) +
            " - " + this.label                                                   );
    }

    void show(final java.lang.String items[], final boolean checkedItems[])
    {
        if (null != items && null != checkedItems)
        {
            this.setMultiChoiceItems(items, checkedItems,
                    new android.content.DialogInterface.OnMultiChoiceClickListener()
                    {
                        @java.lang.Override
                        public void onClick(final android.content.DialogInterface dialog,
                        final int which, final boolean isChecked)
                        { checkedItems[which] = isChecked; }
                    })
                .setOKPositiveButton(new android.content.DialogInterface.OnClickListener()
                    {
                        @java.lang.Override
                        public void onClick(final android.content.DialogInterface dialog,
                        final int which)
                        {
                            org.wheatgenetics.coordinate.ui.
                                ExcludeRowsOrColsAlertDialog.this.process(checkedItems);
                        }
                    });
            this.builder().create().show();
        }
    }
}