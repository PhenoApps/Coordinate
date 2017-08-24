package org.wheatgenetics.coordinate.ui;

/**
 * Uses:
 * android.content.Context
 * android.content.DialogInterface.OnMultiChoiceClickListener
 *
 * org.wheatgenetics.androidlibrary.Utils
 *
 * org.wheatgenetics.coordinate.R
 * org.wheatgenetics.coordinate.ui.ContextAlertDialog
 */
abstract class MultiChoiceItemsAlertDialog
extends org.wheatgenetics.coordinate.ui.ContextAlertDialog
{
    MultiChoiceItemsAlertDialog(final android.content.Context context) { super(context); }

    @java.lang.Override
    android.app.AlertDialog.Builder makeBuilder()
    {
        return super.makeBuilder().setNegativeButton(
            org.wheatgenetics.coordinate.R.string.cancel                      ,
            org.wheatgenetics.androidlibrary.Utils.cancellingOnClickListener());
    }

    android.app.AlertDialog.Builder configureBuilder(final java.lang.CharSequence items[],
    final boolean checkedItems[],
    final android.content.DialogInterface.OnMultiChoiceClickListener onMultiChoiceClickListener)
    {
        assert null != this.builder;
        return this.builder.setMultiChoiceItems(items, checkedItems, onMultiChoiceClickListener);
    }

    void show(final java.lang.CharSequence items[], final boolean checkedItems[],
    final android.content.DialogInterface.OnMultiChoiceClickListener onMultiChoiceClickListener)
    {
        if (null == this.builder)
        {
            this.builder = this.makeBuilder();
            assert null != this.builder;
        }
        this.configureBuilder(items, checkedItems, onMultiChoiceClickListener);
        this.builder.create().show();
    }
}