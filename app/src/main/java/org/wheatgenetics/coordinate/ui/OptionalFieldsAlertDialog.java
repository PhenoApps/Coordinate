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
 * org.wheatgenetics.coordinate.ui.NewOptionalFieldAlertDialog
 * org.wheatgenetics.coordinate.ui.NewOptionalFieldAlertDialog.Handler
 */
class OptionalFieldsAlertDialog extends org.wheatgenetics.coordinate.ui.MultiChoiceItemsAlertDialog
{
    interface Handler
    {
        public abstract void checkOptionalField (int i, boolean b);

        public abstract void showErrorMsg(final int errorMsgResId);
        public abstract void addOptionalField(java.lang.String newName,
            java.lang.String newDefault);
    }

    // region Fields
    private final android.app.Activity                                              activity;
    private final org.wheatgenetics.coordinate.ui.OptionalFieldsAlertDialog.Handler handler ;

    private android.content.DialogInterface.OnMultiChoiceClickListener
        onMultiChoiceClickListenerInstance = null;
    private org.wheatgenetics.coordinate.ui.NewOptionalFieldAlertDialog
        newOptionalFieldAlertDialog = null;

    private java.lang.String oldName, oldDefault;
    // endregion

    // region Private Methods
    private void checkOptionalField(final int i, final boolean b)
    { assert null != this.handler; this.handler.checkOptionalField(i, b); }

    private void retryAddOptionalField(final int errorMsgResId, final java.lang.String oldName,
    final java.lang.String newDefault)
    {
        assert null != this.handler; this.handler.showErrorMsg(errorMsgResId);
        this.oldName = oldName; this.oldDefault = newDefault; this.addNewOptionalField();
    }

    private void addOptionalField(final java.lang.String newName, final java.lang.String newDefault)
    { assert null != this.handler; this.handler.addOptionalField(newName, newDefault); }

    private void addNewOptionalField()
    {
        if (null == this.newOptionalFieldAlertDialog) this.newOptionalFieldAlertDialog =
            new org.wheatgenetics.coordinate.ui.NewOptionalFieldAlertDialog(this.activity,
                new org.wheatgenetics.coordinate.ui.NewOptionalFieldAlertDialog.Handler()
                {
                    @java.lang.Override
                    public void retry(final int errorMsgResId, final java.lang.String oldName,
                    final java.lang.String newDefault)
                    {
                        org.wheatgenetics.coordinate.ui.OptionalFieldsAlertDialog.
                            this.retryAddOptionalField(errorMsgResId, oldName, newDefault);
                    }

                    @java.lang.Override
                    public void addOptionalField(final java.lang.String newName,
                    final java.lang.String newDefault)
                    {
                        org.wheatgenetics.coordinate.ui.OptionalFieldsAlertDialog.
                            this.addOptionalField(newName, newDefault);
                    }
                });
        this.newOptionalFieldAlertDialog.show(this.oldName, this.oldDefault);
    }

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
                        org.wheatgenetics.coordinate.ui.OptionalFieldsAlertDialog.
                            this.checkOptionalField(i, b);
                    }
                };
        return this.onMultiChoiceClickListenerInstance;
    }
    // endregion

    OptionalFieldsAlertDialog(final android.app.Activity activity,
    final org.wheatgenetics.coordinate.ui.OptionalFieldsAlertDialog.Handler handler)
    { super(activity); this.activity = activity; this.handler = handler; }

    @java.lang.Override
    android.app.AlertDialog.Builder makeBuilder()
    {
        super.makeBuilder().setTitle(org.wheatgenetics.coordinate.R.string.optional_fields)
            .setNeutralButton(org.wheatgenetics.coordinate.R.string.add_new,
                new android.content.DialogInterface.OnClickListener()
                {
                    @java.lang.Override
                    public void onClick(final android.content.DialogInterface dialog,
                    final int which)
                    {
                        assert null != dialog; dialog.cancel();
                        org.wheatgenetics.coordinate.ui.OptionalFieldsAlertDialog.
                            this.addNewOptionalField();
                    }
                });
        return this.setOKPositiveButton();
    }

    void show(final java.lang.CharSequence items[], final boolean checkedItems[])
    {
        this.oldName = this.oldDefault = "";
        this.show(items, checkedItems, this.onMultiChoiceClickListener());
    }
}