package org.wheatgenetics.coordinate.tc;

/**
 * Uses:
 * android.app.Activity
 *
 * org.wheatgenetics.coordinate.model.TemplateModel
 *
 * org.wheatgenetics.coordinate.tc.GeneratedExcludedCellsAlertDialog
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
public class GeneratedExcludedCellsAlertDialogTester extends java.lang.Object
{
    // region Fields
    private final android.app.Activity                             activity     ;
    private final org.wheatgenetics.coordinate.model.TemplateModel templateModel;

    private org.wheatgenetics.coordinate.tc.GeneratedExcludedCellsAlertDialog
        generatedExcludedCellsAlertDialog = null;
    // endregion

    public GeneratedExcludedCellsAlertDialogTester(final android.app.Activity activity,
    final org.wheatgenetics.coordinate.model.TemplateModel templateModel)
    { super(); this.activity = activity; this.templateModel = templateModel; }

    public void testGeneratedExcludedCells()
    {
        if (null == this.generatedExcludedCellsAlertDialog) this.generatedExcludedCellsAlertDialog =
            new org.wheatgenetics.coordinate.tc.GeneratedExcludedCellsAlertDialog(this.activity);
        this.generatedExcludedCellsAlertDialog.show(this.templateModel);
    }
}