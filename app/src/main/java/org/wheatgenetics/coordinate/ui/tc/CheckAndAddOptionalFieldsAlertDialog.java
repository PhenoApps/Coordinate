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
 * org.wheatgenetics.coordinate.ui.tc.AddOptionalFieldAlertDialog
 * org.wheatgenetics.coordinate.ui.tc.AddOptionalFieldAlertDialog.Handler
 */
class CheckAndAddOptionalFieldsAlertDialog
extends org.wheatgenetics.androidlibrary.MultiChoiceItemsAlertDialog
implements org.wheatgenetics.coordinate.ui.tc.AddOptionalFieldAlertDialog.Handler
{
    @java.lang.SuppressWarnings("UnnecessaryInterfaceModifier")
    interface Handler { public abstract void handleAddOptionalFieldDone(); }

    // region Fields
    private final org.wheatgenetics.coordinate.ui.tc.CheckAndAddOptionalFieldsAlertDialog.Handler
        handler;

    private org.wheatgenetics.coordinate.model.TemplateModel templateModel;

    private android.content.DialogInterface.OnMultiChoiceClickListener
        onMultiChoiceClickListenerInstance = null;
    private org.wheatgenetics.coordinate.ui.tc.AddOptionalFieldAlertDialog
        addOptionalFieldAlertDialog = null;
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
                        org.wheatgenetics.coordinate.ui.tc
                            .CheckAndAddOptionalFieldsAlertDialog.this.checkOptionalField(i, b);
                    }
                };
        return this.onMultiChoiceClickListenerInstance;
    }

    private void addOptionalField()
    {
        if (null == this.addOptionalFieldAlertDialog) this.addOptionalFieldAlertDialog =
            new org.wheatgenetics.coordinate.ui.tc.AddOptionalFieldAlertDialog(
                this.activity(), this);
        this.addOptionalFieldAlertDialog.show(this.templateModel);
    }
    // endregion

    CheckAndAddOptionalFieldsAlertDialog(final android.app.Activity activity,
    final org.wheatgenetics.coordinate.ui.tc.CheckAndAddOptionalFieldsAlertDialog.Handler handler)
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
                        org.wheatgenetics.coordinate.ui.tc
                            .CheckAndAddOptionalFieldsAlertDialog.this.addOptionalField();
                    }
                });
    }

    // region org.wheatgenetics.coordinate.ui.tc.AddOptionalFieldAlertDialog.Handler Overridden Method
    @java.lang.Override
    public void handleAddOptionalFieldDone()
    { assert null != this.handler; this.handler.handleAddOptionalFieldDone(); }
    // endregion
    // endregion

    void show(final org.wheatgenetics.coordinate.model.TemplateModel templateModel)
    {
        if (null != templateModel)
        {
            this.templateModel = templateModel;
            this.show(this.templateModel.optionalFieldNames(),
                this.templateModel.optionalFieldChecks(), this.onMultiChoiceClickListener());
        }
    }
}