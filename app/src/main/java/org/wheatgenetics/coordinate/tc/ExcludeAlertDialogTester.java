package org.wheatgenetics.coordinate.tc;

/**
 * Uses:
 * android.app.Activity
 *
 * org.wheatgenetics.coordinate.Types.RequestCode
 *
 * org.wheatgenetics.coordinate.model.TemplateModel
 *
 * org.wheatgenetics.coordinate.tc.ExcludeAlertDialog
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
public class ExcludeAlertDialogTester extends java.lang.Object
{
    // region Fields
                                                    private final android.app.Activity activity   ;
    @org.wheatgenetics.coordinate.Types.RequestCode private final int                  requestCode;
    private final org.wheatgenetics.coordinate.model.TemplateModel templateModel;

    private org.wheatgenetics.coordinate.tc.ExcludeAlertDialog
        excludeAlertDialog = null;                                                      // lazy load
    // endregion

    public ExcludeAlertDialogTester(final android.app.Activity activity,
    @org.wheatgenetics.coordinate.Types.RequestCode final int requestCode,
    final org.wheatgenetics.coordinate.model.TemplateModel templateModel)
    {
        super();

        this.activity      = activity     ;
        this.requestCode   = requestCode  ;
        this.templateModel = templateModel;
    }

    public void testExclude()
    {
        if (null == this.excludeAlertDialog) this.excludeAlertDialog =
            new org.wheatgenetics.coordinate.tc.ExcludeAlertDialog(this.activity, this.requestCode);
        this.excludeAlertDialog.show(this.templateModel);
    }
}