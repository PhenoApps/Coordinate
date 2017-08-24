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
 * org.wheatgenetics.coordinate.ui.MultiChoiceItemsAlertDialog
 */
class OptionalFieldsAlertDialog extends org.wheatgenetics.coordinate.ui.MultiChoiceItemsAlertDialog
{
    interface Handler
    {
        public abstract void checkOptionalField (int i, boolean b);
        public abstract void addNewOptionalField()                ;
    }

    // region Fields
    private final org.wheatgenetics.coordinate.ui.OptionalFieldsAlertDialog.Handler handler;
    private       android.content.DialogInterface.OnMultiChoiceClickListener
        onMultiChoiceClickListenerInstance = null;
    // endregion

    // region Private Methods
    private void checkOptionalField(final int i, final boolean b)
    { assert null != this.handler; this.handler.checkOptionalField(i, b); }

    private void addNewOptionalField()
    { assert null != this.handler; this.handler.addNewOptionalField(); }

    private android.content.DialogInterface.OnMultiChoiceClickListener onMultiChoiceClickListener()
    {
        if (null == this.onMultiChoiceClickListenerInstance)
            this.onMultiChoiceClickListenerInstance =
                new android.content.DialogInterface.OnMultiChoiceClickListener()
                {
                    @java.lang.Override
                    public void onClick(final android.content.DialogInterface dialogInterface,
                    final int i, final boolean b)
                    {
                        org.wheatgenetics.coordinate.ui.
                            OptionalFieldsAlertDialog.this.checkOptionalField(i, b);
                    }
                };
        return this.onMultiChoiceClickListenerInstance;
    }
    // endregion

    OptionalFieldsAlertDialog(final android.content.Context context,
    final org.wheatgenetics.coordinate.ui.OptionalFieldsAlertDialog.Handler handler)
    { super(context); this.handler = handler; }

    @java.lang.Override
    android.app.AlertDialog.Builder makeBuilder()
    {
        return super.makeBuilder().setTitle(org.wheatgenetics.coordinate.R.string.optional_fields)
            .setNeutralButton(org.wheatgenetics.coordinate.R.string.add_new,
                new android.content.DialogInterface.OnClickListener()
                {
                    @java.lang.Override
                    public void onClick(final android.content.DialogInterface dialog,
                    final int which)
                    {
                        assert null != dialog; dialog.cancel();
                        org.wheatgenetics.coordinate.ui.
                            OptionalFieldsAlertDialog.this.addNewOptionalField();
                    }
                })
            .setPositiveButton(org.wheatgenetics.coordinate.R.string.ok,
                org.wheatgenetics.androidlibrary.Utils.cancellingOnClickListener());
    }

    void show(final java.lang.CharSequence items[], final boolean checkedItems[])
    { this.show(items, checkedItems, this.onMultiChoiceClickListener()); }
}