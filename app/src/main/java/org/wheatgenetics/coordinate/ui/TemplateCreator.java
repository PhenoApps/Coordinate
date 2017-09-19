package org.wheatgenetics.coordinate.ui;

/**
 * Uses:
 * android.app.Activity
 *
 * org.wheatgenetics.androidlibrary.Utils.showLongToast
 *
 * org.wheatgenetics.coordinate.model.TemplateModel
 *
 * org.wheatgenetics.coordinate.ui.ExcludeAlertDialog
 * org.wheatgenetics.coordinate.ui.ExtraNewTemplateAlertDialog
 * org.wheatgenetics.coordinate.ui.ExtraNewTemplateAlertDialog.Handler
 * org.wheatgenetics.coordinate.ui.NamingAlertDialog
 * org.wheatgenetics.coordinate.ui.NamingAlertDialog.Handler
 * org.wheatgenetics.coordinate.ui.NewTemplateAlertDialog
 * org.wheatgenetics.coordinate.ui.NewTemplateAlertDialog.Handler
 * org.wheatgenetics.coordinate.ui.OptionalFieldsAlertDialog
 * org.wheatgenetics.coordinate.ui.OptionalFieldsAlertDialog.Handler
 */
class TemplateCreator extends java.lang.Object implements
org.wheatgenetics.coordinate.ui.NewTemplateAlertDialog.Handler,
org.wheatgenetics.coordinate.ui.ExtraNewTemplateAlertDialog.Handler,
org.wheatgenetics.coordinate.ui.OptionalFieldsAlertDialog.Handler,
org.wheatgenetics.coordinate.ui.NamingAlertDialog.Handler
{
    interface Handler { public abstract void handleTemplateCreated(); }

    // region Fields
    private final android.app.Activity                                    activity;
    private final org.wheatgenetics.coordinate.ui.TemplateCreator.Handler handler ;

    private org.wheatgenetics.coordinate.model.TemplateModel templateModel;

    private org.wheatgenetics.coordinate.ui.NewTemplateAlertDialog newTemplateAlertDialog = null;
    private org.wheatgenetics.coordinate.ui.ExtraNewTemplateAlertDialog
        extraNewTemplateAlertDialog = null;
    private org.wheatgenetics.coordinate.ui.OptionalFieldsAlertDialog
        optionalFieldsAlertDialog = null;
    private org.wheatgenetics.coordinate.ui.ExcludeAlertDialog excludeAlertDialog = null;
    private org.wheatgenetics.coordinate.ui.NamingAlertDialog  namingAlertDialog  = null;
    // endregion

    // region Private Methods
    // region showLongToast() Private Methods
    private void showLongToast(final java.lang.String text)
    { org.wheatgenetics.androidlibrary.Utils.showLongToast(this.activity, text); }

    private void showLongToast(final int text)
    { assert null != this.activity; this.showLongToast(this.activity.getString(text)); }
    // endregion

    private void performStep2()
    {
        if (null == this.extraNewTemplateAlertDialog) this.extraNewTemplateAlertDialog =
            new org.wheatgenetics.coordinate.ui.ExtraNewTemplateAlertDialog(this.activity, this);
        this.extraNewTemplateAlertDialog.show();
    }
    // endregion

    TemplateCreator(final android.app.Activity activity,
    final org.wheatgenetics.coordinate.ui.TemplateCreator.Handler handler)
    { super(); this.activity = activity; this.handler = handler; }

    // region org.wheatgenetics.coordinate.ui.NewTemplateAlertDialog.Handler Overridden Method
    @java.lang.Override
    public void handleNewTemplateNext() { this.performStep2(); }
    // endregion

    // region org.wheatgenetics.coordinate.ui.ExtraNewTemplateAlertDialog Overridden Methods
    @java.lang.Override
    public void addOptionalFields()
    {
        if (null == this.optionalFieldsAlertDialog) this.optionalFieldsAlertDialog =
            new org.wheatgenetics.coordinate.ui.OptionalFieldsAlertDialog(this.activity, this);
        assert null != this.templateModel; this.optionalFieldsAlertDialog.show(
            this.templateModel.optionalFieldNames(), this.templateModel.optionalFieldschecks());
    }

    @java.lang.Override
    public void addExcludes()
    {
        if (null == this.excludeAlertDialog) this.excludeAlertDialog =
            new org.wheatgenetics.coordinate.ui.ExcludeAlertDialog(this.activity);
        this.excludeAlertDialog.show(this.templateModel);
    }

    @java.lang.Override
    public void addNaming()
    {
        if (null == this.namingAlertDialog) this.namingAlertDialog =
            new org.wheatgenetics.coordinate.ui.NamingAlertDialog(this.activity, this);

        assert null != this.templateModel; this.namingAlertDialog.show(
            this.templateModel.getRowNumbering(), this.templateModel.getColNumbering());
    }

    @java.lang.Override
    public void handleExtraNewTemplateNext()
    { assert null != this.handler; this.handler.handleTemplateCreated(); }
    // endregion

    // region org.wheatgenetics.coordinate.ui.OptionalFieldsAlertDialog.Handler Overridden Methods
    @java.lang.Override
    public void checkOptionalField(final int i, final boolean b)
    {
        assert null != this.templateModel;
        this.templateModel.setOptionalFieldChecked(/* index => */ i, /* checked => */ b);
    }

    @java.lang.Override
    public void retryAddOptionalField(final int errorMsgResId)
    { this.showLongToast(errorMsgResId); this.addOptionalFields(); }

    @java.lang.Override
    public void addOptionalField(final java.lang.String newName, final java.lang.String newDefault)
    {
        assert null != this.templateModel;
        this.templateModel.addOptionalField(/* name => */ newName, /* value => */ newDefault);
        this.addOptionalFields();
    }
    // endregion

    // region org.wheatgenetics.coordinate.ui.NamingAlertDialog.Handler Overridden Method
    @java.lang.Override
    public void setNumbering(final boolean rowNumbering, final boolean colNumbering)
    {
        assert null != this.templateModel;
        this.templateModel.setRowNumbering(rowNumbering);
        this.templateModel.setColNumbering(colNumbering);
    }
    // endregion

    void create(final org.wheatgenetics.coordinate.model.TemplateModel templateModel)
    {
        if (null != templateModel)
        {
            if (null == this.newTemplateAlertDialog) this.newTemplateAlertDialog =
                new org.wheatgenetics.coordinate.ui.NewTemplateAlertDialog(this.activity, this);
            this.templateModel = templateModel; this.templateModel.clearExcludesAndOptionalFields();
            this.newTemplateAlertDialog.show(this.templateModel);
        }
    }
}