package org.wheatgenetics.coordinate.tc;

/**
 * Uses:
 * android.app.Activity
 *
 * org.wheatgenetics.coordinate.model.TemplateModel
 *
 * org.wheatgenetics.coordinate.tc.SetNumberingAlertDialog
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
public class SetNumberingAlertDialogTester extends java.lang.Object
{
    // region Fields
    private final android.app.Activity                             activity     ;
    private final org.wheatgenetics.coordinate.model.TemplateModel templateModel;

    private org.wheatgenetics.coordinate.tc.SetNumberingAlertDialog setNumberingAlertDialog = null;
    // endregion

    public SetNumberingAlertDialogTester(final android.app.Activity activity,
    final org.wheatgenetics.coordinate.model.TemplateModel templateModel)
    { super(); this.activity = activity; this.templateModel = templateModel; }

    public void testSetNumbering()
    {
        if (null == this.setNumberingAlertDialog) this.setNumberingAlertDialog =
            new org.wheatgenetics.coordinate.tc.SetNumberingAlertDialog(this.activity);
        this.setNumberingAlertDialog.show(this.templateModel);
    }
}