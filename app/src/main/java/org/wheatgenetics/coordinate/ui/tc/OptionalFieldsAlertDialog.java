package org.wheatgenetics.coordinate.ui.tc;

/**
 * Uses:
 * android.app.Activity
 * android.content.DialogInterface
 * android.content.DialogInterface.OnClickListener
 * android.content.DialogInterface.OnMultiChoiceClickListener
 *
 * org.wheatgenetics.androidlibrary.MultiChoiceItemsAlertDialog
 *
 * org.wheatgenetics.coordinate.R
 *
 * org.wheatgenetics.coordinate.model.TemplateModel
 *
 * org.wheatgenetics.coordinate.ui.tc.NewOptionalFieldAlertDialog
 * org.wheatgenetics.coordinate.ui.tc.NewOptionalFieldAlertDialog.Handler
 */
class OptionalFieldsAlertDialog extends org.wheatgenetics.androidlibrary.MultiChoiceItemsAlertDialog
implements org.wheatgenetics.coordinate.ui.tc.NewOptionalFieldAlertDialog.Handler
{
    interface Handler { public abstract void showOptionalFieldsAlertDialog(); }

    // region Fields
    private final org.wheatgenetics.coordinate.ui.tc.OptionalFieldsAlertDialog.Handler handler;

    private org.wheatgenetics.coordinate.model.TemplateModel templateModel;

    private android.content.DialogInterface.OnMultiChoiceClickListener
        onMultiChoiceClickListenerInstance = null;
    private org.wheatgenetics.coordinate.ui.tc.NewOptionalFieldAlertDialog
        newOptionalFieldAlertDialog = null;
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
                        org.wheatgenetics.coordinate.ui.tc.
                            OptionalFieldsAlertDialog.this.checkOptionalField(i, b);
                    }
                };
        return this.onMultiChoiceClickListenerInstance;
    }

    private void addOptionalField()
    {
        if (null == this.newOptionalFieldAlertDialog) this.newOptionalFieldAlertDialog =
            new org.wheatgenetics.coordinate.ui.tc.NewOptionalFieldAlertDialog(
                this.activity(), this);
        this.newOptionalFieldAlertDialog.show(this.templateModel);
    }
    // endregion

    OptionalFieldsAlertDialog(final android.app.Activity activity,
    final org.wheatgenetics.coordinate.ui.tc.OptionalFieldsAlertDialog.Handler handler)
    { super(activity); this.handler = handler; }

    // region Overridden Methods
    @java.lang.Override
    public void configure()
    {
        super.configure();
        this.setTitle(org.wheatgenetics.coordinate.R.string.optional_fields).setOKPositiveButton()
            .setNeutralButton(org.wheatgenetics.coordinate.R.string.add_new,
                new android.content.DialogInterface.OnClickListener()
                {
                    @java.lang.Override
                    public void onClick(final android.content.DialogInterface dialog,
                    final int which)
                    {
                        org.wheatgenetics.coordinate.ui.tc.
                            OptionalFieldsAlertDialog.this.addOptionalField();
                    }
                });
    }

    // region org.wheatgenetics.coordinate.ui.tc.NewOptionalFieldAlertDialog.Handler Overridden Method
    @java.lang.Override
    public void showOptionalFieldsAlertDialog()
    { assert null != this.handler; this.handler.showOptionalFieldsAlertDialog(); }
    // endregion
    // endregion

    void show(final org.wheatgenetics.coordinate.model.TemplateModel templateModel)
    {
        if (null != templateModel)
        {
            this.templateModel = templateModel;
            this.show(this.templateModel.optionalFieldNames(),
                this.templateModel.optionalFieldschecks(), this.onMultiChoiceClickListener());
        }
    }
}