package org.wheatgenetics.coordinate.ui;                     // TODO: Make this stay open after add.

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
 * org.wheatgenetics.coordinate.model.TemplateModel
 *
 * org.wheatgenetics.coordinate.ui.AlertDialog
 * org.wheatgenetics.coordinate.ui.NewOptionalFieldAlertDialog
 */
class OptionalFieldsAlertDialog extends org.wheatgenetics.coordinate.ui.AlertDialog
{
    // region Fields
    private org.wheatgenetics.coordinate.model.TemplateModel templateModel;

    private android.content.DialogInterface.OnMultiChoiceClickListener
        onMultiChoiceClickListenerInstance = null;
    private org.wheatgenetics.coordinate.ui.NewOptionalFieldAlertDialog
        newOptionalFieldAlertDialog = null;
    private boolean onClickListenerReplaced = false;                                         // TODO
    // endregion

    // region Private Methods
    private void checkOptionalField(final int i, final boolean b)
    {
        assert null != this.templateModel;
        this.templateModel.setOptionalFieldChecked(/* index => */ i, /* checked => */ b);
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
                        org.wheatgenetics.coordinate.ui.
                            OptionalFieldsAlertDialog.this.checkOptionalField(i, b);
                    }
                };
        return this.onMultiChoiceClickListenerInstance;
    }

    private void addOptionalField()
    {
        if (null == this.newOptionalFieldAlertDialog) this.newOptionalFieldAlertDialog =
            new org.wheatgenetics.coordinate.ui.NewOptionalFieldAlertDialog(this.activity());
        this.newOptionalFieldAlertDialog.show(this.templateModel);
    }
    // endregion

    OptionalFieldsAlertDialog(final android.app.Activity activity) { super(activity); }

    @java.lang.Override
    void configureAfterConstruction()
    {
        this.setNegativeButton(); // TODO: Put in common superclass.
        this.setTitle(org.wheatgenetics.coordinate.R.string.optional_fields).setOKPositiveButton()
            .setNeutralButton(org.wheatgenetics.coordinate.R.string.add_new,
                new android.content.DialogInterface.OnClickListener()
                {
                    @java.lang.Override
                    public void onClick(final android.content.DialogInterface dialog,
                    final int which)
                    {
                        org.wheatgenetics.coordinate.ui.
                            OptionalFieldsAlertDialog.this.addOptionalField();
                    }
                });
    }

    void show(final org.wheatgenetics.coordinate.model.TemplateModel templateModel)
    {
        if (null != templateModel)
        {
            this.templateModel = templateModel;
            this.setMultiChoiceItems(this.templateModel.optionalFieldNames(),
                this.templateModel.optionalFieldschecks(), this.onMultiChoiceClickListener());
            this.builder().create().show();
        }
    }
}