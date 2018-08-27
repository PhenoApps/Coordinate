package org.wheatgenetics.coordinate.tc;

/**
 * Uses:
 * android.app.Activity
 * android.os.Bundle
 *
 * org.wheatgenetics.coordinate.model.TemplateModel
 *
 * org.wheatgenetics.coordinate.Types.RequestCode
 *
 * org.wheatgenetics.coordinate.tc.AssignTitleRowsColsAlertDialog
 * org.wheatgenetics.coordinate.tc.AssignTitleRowsColsAlertDialog.Handler
 * org.wheatgenetics.coordinate.tc.SetExcludesOptionalFieldsNumberingAlertDialog
 * org.wheatgenetics.coordinate.tc.SetExcludesOptionalFieldsNumberingAlertDialog.Handler
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
public class TemplateCreator extends java.lang.Object implements
org.wheatgenetics.coordinate.tc.AssignTitleRowsColsAlertDialog.Handler,
org.wheatgenetics.coordinate.tc.SetExcludesOptionalFieldsNumberingAlertDialog.Handler
{
    @java.lang.SuppressWarnings({"UnnecessaryInterfaceModifier"}) public interface Handler
    {
        public abstract void handleTemplateCreated(
            org.wheatgenetics.coordinate.model.TemplateModel templateModel);
    }

    // region Fields
                                                    private final android.app.Activity activity   ;
    @org.wheatgenetics.coordinate.Types.RequestCode private final int                  requestCode;
    private final org.wheatgenetics.coordinate.tc.TemplateCreator.Handler handler;

    private org.wheatgenetics.coordinate.model.TemplateModel templateModel;

    private org.wheatgenetics.coordinate.tc.AssignTitleRowsColsAlertDialog
        assignTitleRowsColsAlertDialog = null;
    private org.wheatgenetics.coordinate.tc.SetExcludesOptionalFieldsNumberingAlertDialog
        setExcludesOptionalFieldsNumberingAlertDialog = null;
    // endregion

    public TemplateCreator(final android.app.Activity activity,
    @org.wheatgenetics.coordinate.Types.RequestCode final int requestCode,
    final org.wheatgenetics.coordinate.tc.TemplateCreator.Handler handler)
    { super(); this.activity = activity; this.requestCode = requestCode; this.handler = handler; }

    // region Overridden Methods
    // region org.wheatgenetics.coordinate.tc.AssignTitleRowsColsAlertDialog.Handler Overridden Method
    @java.lang.Override public void handleAssignDone()
    {
        if (null == this.setExcludesOptionalFieldsNumberingAlertDialog)
            this.setExcludesOptionalFieldsNumberingAlertDialog =
                new org.wheatgenetics.coordinate.tc.SetExcludesOptionalFieldsNumberingAlertDialog(
                    this.activity, this.requestCode,this);
        this.setExcludesOptionalFieldsNumberingAlertDialog.show(this.templateModel);
    }
    // endregion

    // region org.wheatgenetics.coordinate.tc.SetExcludesOptionalFieldsNumberingAlertDialog Overridden Method
    @java.lang.Override public void handleSetDone()
    { assert null != this.handler; this.handler.handleTemplateCreated(this.templateModel); }
    // endregion
    // endregion

    // region Public Methods
    public void create()
    {
        if (null == this.assignTitleRowsColsAlertDialog) this.assignTitleRowsColsAlertDialog =
            new org.wheatgenetics.coordinate.tc.AssignTitleRowsColsAlertDialog(
                this.activity,this);
        this.templateModel = org.wheatgenetics.coordinate.model.TemplateModel.makeUserDefined();
        this.assignTitleRowsColsAlertDialog.show(this.templateModel);
    }

    public void setExcludedCells(final android.os.Bundle bundle)
    { if (null != this.templateModel) this.templateModel.setExcludedCells(bundle); }
    // endregion
}