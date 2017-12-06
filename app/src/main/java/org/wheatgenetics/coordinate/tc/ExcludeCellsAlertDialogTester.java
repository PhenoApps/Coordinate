package org.wheatgenetics.coordinate.tc;

/**
 * Uses:
 * android.app.Activity
 *
 * org.wheatgenetics.coordinate.model.TemplateModel
 *
 * org.wheatgenetics.coordinate.tc.ExcludeCellsAlertDialog
 */
@java.lang.SuppressWarnings("ClassExplicitlyExtendsObject")
public class ExcludeCellsAlertDialogTester extends java.lang.Object
{
    // region Fields
    private final android.app.Activity                             activity     ;
    private final org.wheatgenetics.coordinate.model.TemplateModel templateModel;

    private org.wheatgenetics.coordinate.tc.ExcludeCellsAlertDialog excludeCellsAlertDialog = null;
    // endregion

    public ExcludeCellsAlertDialogTester(final android.app.Activity activity,
    final org.wheatgenetics.coordinate.model.TemplateModel templateModel)
    { super(); this.activity = activity; this.templateModel = templateModel; }

    public void testExcludeCells()
    {
        if (null == this.excludeCellsAlertDialog) this.excludeCellsAlertDialog =
            new org.wheatgenetics.coordinate.tc.ExcludeCellsAlertDialog(this.activity);
        this.excludeCellsAlertDialog.show(this.templateModel);
    }
}