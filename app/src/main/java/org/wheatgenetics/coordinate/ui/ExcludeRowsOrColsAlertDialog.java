package org.wheatgenetics.coordinate.ui;

/**
 * Uses:
 * android.app.AlertDialog.Builder
 * android.content.Context
 * android.content.DialogInterface
 * android.content.DialogInterface.OnClickListener
 * android.content.DialogInterface.OnMultiChoiceClickListener
 *
 * org.wheatgenetics.androidlibrary.Utils
 *
 * org.wheatgenetics.coordinate.R
 */
class ExcludeRowsOrColsAlertDialog extends java.lang.Object
{
    interface Handler { public abstract void process(boolean checkedItems[]); }

    // region Fields
    private final android.content.Context                                              context;
    private final int                                                                  label  ;
    private final org.wheatgenetics.coordinate.ui.ExcludeRowsOrColsAlertDialog.Handler handler;

    private android.app.AlertDialog.Builder builder = null;
    // endregion

    private void process(final boolean checkedItems[])
    {
        assert null != this.handler;
        this.handler.process(checkedItems);
    }

    ExcludeRowsOrColsAlertDialog(final android.content.Context context, final int label,
    final org.wheatgenetics.coordinate.ui.ExcludeRowsOrColsAlertDialog.Handler handler)
    { super(); this.context = context; this.label = label; this.handler = handler; }

    void show(final java.lang.String items[], final boolean checkedItems[])
    {
        if (null == this.builder)
        {
            this.builder = new android.app.AlertDialog.Builder(this.context);
            assert null != this.context;
            this.builder.setTitle(
                    this.context.getString(
                        org.wheatgenetics.coordinate.R.string.exclude_title) +
                    " - " + this.context.getString(this.label))
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
                    })
                .setNegativeButton(org.wheatgenetics.coordinate.R.string.cancel,
                    org.wheatgenetics.androidlibrary.Utils.cancellingOnClickListener());
        }
        this.builder.setMultiChoiceItems(items, checkedItems,
            new android.content.DialogInterface.OnMultiChoiceClickListener()
            {
                @java.lang.Override
                public void onClick(final android.content.DialogInterface dialog, final int which,
                final boolean isChecked) { checkedItems[which] = isChecked; }
            });
        this.builder.create().show();
    }
}