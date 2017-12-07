package org.wheatgenetics.coordinate.tc;

/**
 * Uses:
 * android.app.Activity
 *
 * org.wheatgenetics.coordinate.model.TemplateModel
 *
 * org.wheatgenetics.coordinate.tc.ExcludeAlertDialog
 */
@java.lang.SuppressWarnings("ClassExplicitlyExtendsObject")
public class ExcludeAlertDialogTester extends java.lang.Object
{
    // region Fields
    private final android.app.Activity                             activity     ;
    private final org.wheatgenetics.coordinate.model.TemplateModel templateModel;

    private org.wheatgenetics.coordinate.tc.ExcludeAlertDialog excludeAlertDialog = null;
    // endregion

    public ExcludeAlertDialogTester(final android.app.Activity activity,
    final org.wheatgenetics.coordinate.model.TemplateModel templateModel)
    { super(); this.activity = activity; this.templateModel = templateModel; }

    public void testExclude()
    {
        if (null == this.excludeAlertDialog) this.excludeAlertDialog =
            new org.wheatgenetics.coordinate.tc.ExcludeAlertDialog(this.activity);
        this.excludeAlertDialog.show(this.templateModel);
    }
}