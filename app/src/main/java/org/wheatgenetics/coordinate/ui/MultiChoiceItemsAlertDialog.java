package org.wheatgenetics.coordinate.ui;

/**
 * Uses:
 * android.content.Context
 * android.content.DialogInterface.OnMultiChoiceClickListener
 *
 * org.wheatgenetics.coordinate.ui.ContextAlertDialog
 */
abstract class MultiChoiceItemsAlertDialog
extends org.wheatgenetics.coordinate.ui.ContextAlertDialog
{
    MultiChoiceItemsAlertDialog(final android.content.Context context) { super(context); }

    @java.lang.Override
    android.app.AlertDialog.Builder makeBuilder()
    { super.makeBuilder(); return this.setNegativeButton(); }

    // region Package Methods
    android.app.AlertDialog.Builder configureBuilder(final java.lang.CharSequence items[], // TODO: Rename.
    final boolean checkedItems[],
    final android.content.DialogInterface.OnMultiChoiceClickListener listener)
    {
        assert null != this.builder;
        return this.builder.setMultiChoiceItems(items, checkedItems, listener);
    }

    void show(final java.lang.CharSequence items[], final boolean checkedItems[],
    final android.content.DialogInterface.OnMultiChoiceClickListener listener)
    {
        if (null == this.builder)  // TODO: Move to configure().
        {
            this.builder = this.makeBuilder();
            assert null != this.builder;
        }
        this.configureBuilder(items, checkedItems, listener);
        this.builder.create().show();
    }
    // endregion
}