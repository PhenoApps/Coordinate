package org.wheatgenetics.coordinate.tc.exclude;

/**
 * Uses:
 * android.app.Activity
 *
 * androidx.annotation.NonNull
 *
 * org.wheatgenetics.coordinate.model.TemplateModel
 *
 * org.wheatgenetics.coordinate.tc.exclude.GeneratedExcludedCellsAlertDialog
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
public class GeneratedExcludedCellsAlertDialogTester extends java.lang.Object
{
    // region Fields
    private final android.app.Activity                             activity     ;
    private final org.wheatgenetics.coordinate.model.TemplateModel templateModel;

    private org.wheatgenetics.coordinate.tc.exclude.GeneratedExcludedCellsAlertDialog
        generatedExcludedCellsAlertDialogInstance = null;                               // lazy load
    // endregion

    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.tc.exclude.GeneratedExcludedCellsAlertDialog
    generatedExcludedCellsAlertDialog()
    {
        if (null == this.generatedExcludedCellsAlertDialogInstance)
            this.generatedExcludedCellsAlertDialogInstance =
                new org.wheatgenetics.coordinate.tc.exclude.GeneratedExcludedCellsAlertDialog(
                    this.activity);
        return this.generatedExcludedCellsAlertDialogInstance;
    }

    public GeneratedExcludedCellsAlertDialogTester(final android.app.Activity activity,
    final org.wheatgenetics.coordinate.model.TemplateModel templateModel)
    { super(); this.activity = activity; this.templateModel = templateModel; }

    public void testGeneratedExcludedCells()
    { this.generatedExcludedCellsAlertDialog().show(this.templateModel); }
}