package org.wheatgenetics.coordinate.ui.tc;

/**
 * Uses:
 * android.app.Activity
 *
 * org.wheatgenetics.coordinate.model.TemplateModel
 *
 * org.wheatgenetics.coordinate.ui.tc.AssignTitleRowsColsAlertDialog
 * org.wheatgenetics.coordinate.ui.tc.AssignTitleRowsColsAlertDialog.Handler
 * org.wheatgenetics.coordinate.ui.tc.ExcludesOptionalFieldsNumberingAlertDialog
 * org.wheatgenetics.coordinate.ui.tc.ExcludesOptionalFieldsNumberingAlertDialog.Handler
 */
@java.lang.SuppressWarnings("ClassExplicitlyExtendsObject")
public class TemplateCreator extends java.lang.Object implements
org.wheatgenetics.coordinate.ui.tc.AssignTitleRowsColsAlertDialog.Handler,
org.wheatgenetics.coordinate.ui.tc.ExcludesOptionalFieldsNumberingAlertDialog.Handler
{
    public interface Handler { public abstract void handleTemplateCreated(); }

    // region Fields
    private final android.app.Activity                                       activity;
    private final org.wheatgenetics.coordinate.ui.tc.TemplateCreator.Handler handler ;

    private org.wheatgenetics.coordinate.model.TemplateModel templateModel;

    private org.wheatgenetics.coordinate.ui.tc.AssignTitleRowsColsAlertDialog
        assignTitleRowsColsAlertDialog = null;
    private org.wheatgenetics.coordinate.ui.tc.ExcludesOptionalFieldsNumberingAlertDialog
        excludesOptionalFieldsNumberingAlertDialog = null;
    // endregion

    public TemplateCreator(final android.app.Activity activity,
    final org.wheatgenetics.coordinate.ui.tc.TemplateCreator.Handler handler)
    { super(); this.activity = activity; this.handler = handler; }

    // region Overridden Methods
    // region org.wheatgenetics.coordinate.ui.tc.AssignTitleRowsColsAlertDialog.Handler Overridden Method
    @java.lang.Override
    public void handleNewTemplateNext()
    {
        if (null == this.excludesOptionalFieldsNumberingAlertDialog)
            this.excludesOptionalFieldsNumberingAlertDialog =
                new org.wheatgenetics.coordinate.ui.tc.ExcludesOptionalFieldsNumberingAlertDialog(
                    this.activity, this);
        this.excludesOptionalFieldsNumberingAlertDialog.show(this.templateModel);
    }
    // endregion

    // region org.wheatgenetics.coordinate.ui.tc.ExcludesOptionalFieldsNumberingAlertDialog Overridden Method
    @java.lang.Override
    public void handleExtraNewTemplateNext()
    { assert null != this.handler; this.handler.handleTemplateCreated(); }
    // endregion
    // endregion

    public void create(final org.wheatgenetics.coordinate.model.TemplateModel templateModel)
    {
        if (null != templateModel)
        {
            if (null == this.assignTitleRowsColsAlertDialog) this.assignTitleRowsColsAlertDialog =
                new org.wheatgenetics.coordinate.ui.tc.AssignTitleRowsColsAlertDialog(
                    this.activity, this);
            this.templateModel = templateModel; this.templateModel.clearExcludesAndOptionalFields();
            this.assignTitleRowsColsAlertDialog.show(this.templateModel);
        }
    }
}