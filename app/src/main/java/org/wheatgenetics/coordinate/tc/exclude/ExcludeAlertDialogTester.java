package org.wheatgenetics.coordinate.tc.exclude;

/**
 * Uses:
 * android.app.Activity
 *
 * androidx.annotation.NonNull
 *
 * org.wheatgenetics.coordinate.Types.RequestCode
 *
 * org.wheatgenetics.coordinate.model.TemplateModel
 *
 * org.wheatgenetics.coordinate.tc.exclude.ExcludeAlertDialog
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
public class ExcludeAlertDialogTester extends java.lang.Object
{
    // region Fields
                                                    private final android.app.Activity activity   ;
    @org.wheatgenetics.coordinate.Types.RequestCode private final int                  requestCode;
    private final org.wheatgenetics.coordinate.model.TemplateModel templateModel;

    private org.wheatgenetics.coordinate.tc.exclude.ExcludeAlertDialog
        excludeAlertDialogInstance = null;                                              // lazy load
    // endregion

    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.tc.exclude.ExcludeAlertDialog excludeAlertDialog()
    {
        if (null == this.excludeAlertDialogInstance) this.excludeAlertDialogInstance =
            new org.wheatgenetics.coordinate.tc.exclude.ExcludeAlertDialog(
                this.activity, this.requestCode);
        return this.excludeAlertDialogInstance;
    }

    public ExcludeAlertDialogTester(final android.app.Activity activity,
    @org.wheatgenetics.coordinate.Types.RequestCode final int requestCode,
    final org.wheatgenetics.coordinate.model.TemplateModel templateModel)
    {
        super();

        this.activity      = activity     ;
        this.requestCode   = requestCode  ;
        this.templateModel = templateModel;
    }

    public void testExclude() { this.excludeAlertDialog().show(this.templateModel); }
}