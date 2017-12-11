package org.wheatgenetics.coordinate.tc;

/**
 * Uses:
 * android.app.Activity
 *
 * org.wheatgenetics.coordinate.model.TemplateModel
 *
 * org.wheatgenetics.coordinate.tc.AssignTitleRowsColsAlertDialog
 * org.wheatgenetics.coordinate.tc.AssignTitleRowsColsAlertDialog.Handler
 */
@java.lang.SuppressWarnings("ClassExplicitlyExtendsObject")
public class AssignTitleRowsColsAlertDialogTester extends java.lang.Object
implements org.wheatgenetics.coordinate.tc.AssignTitleRowsColsAlertDialog.Handler
{
    @java.lang.SuppressWarnings("UnnecessaryInterfaceModifier")
    public interface Handler { public abstract void handleAssignDone(); }

    // region Fields
    private final android.app.Activity                             activity     ;
    private final org.wheatgenetics.coordinate.model.TemplateModel templateModel;
    private final org.wheatgenetics.coordinate.tc.AssignTitleRowsColsAlertDialogTester.Handler
        handler;

    private org.wheatgenetics.coordinate.tc.AssignTitleRowsColsAlertDialog
        assignTitleRowsColsAlertDialog = null;
    // endregion

    public AssignTitleRowsColsAlertDialogTester(final android.app.Activity activity,
    final org.wheatgenetics.coordinate.model.TemplateModel templateModel,
    final org.wheatgenetics.coordinate.tc.AssignTitleRowsColsAlertDialogTester.Handler handler)
    {
        super();
        this.activity = activity; this.templateModel = templateModel; this.handler = handler;
    }

    @java.lang.Override
    public void handleAssignDone() { assert null != this.handler; this.handler.handleAssignDone(); }

    public void testAssignTitleRowsCols()
    {
        if (null == this.assignTitleRowsColsAlertDialog) this.assignTitleRowsColsAlertDialog =
            new org.wheatgenetics.coordinate.tc.AssignTitleRowsColsAlertDialog(this.activity, this);
        this.assignTitleRowsColsAlertDialog.show(this.templateModel);
    }
}