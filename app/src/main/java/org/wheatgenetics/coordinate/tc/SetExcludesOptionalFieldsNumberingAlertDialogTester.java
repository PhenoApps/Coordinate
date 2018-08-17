package org.wheatgenetics.coordinate.tc;

/**
 * Uses:
 * android.app.Activity
 *
 * org.wheatgenetics.coordinate.Types.RequestCode
 *
 * org.wheatgenetics.coordinate.model.TemplateModel
 *
 * org.wheatgenetics.coordinate.tc.SetExcludesOptionalFieldsNumberingAlertDialog
 * org.wheatgenetics.coordinate.tc.SetExcludesOptionalFieldsNumberingAlertDialog.Handler
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
public class SetExcludesOptionalFieldsNumberingAlertDialogTester extends java.lang.Object
implements org.wheatgenetics.coordinate.tc.SetExcludesOptionalFieldsNumberingAlertDialog.Handler
{
    // region Fields
                                                    private final android.app.Activity activity   ;
    @org.wheatgenetics.coordinate.Types.RequestCode private final int                  requestCode;
    private final org.wheatgenetics.coordinate.model.TemplateModel templateModel;

    private org.wheatgenetics.coordinate.tc.SetExcludesOptionalFieldsNumberingAlertDialog
        setExcludesOptionalFieldsNumberingAlertDialog = null;
    // endregion

    public SetExcludesOptionalFieldsNumberingAlertDialogTester(final android.app.Activity activity,
    @org.wheatgenetics.coordinate.Types.RequestCode final int requestCode,
    final org.wheatgenetics.coordinate.model.TemplateModel templateModel)
    {
        super();

        this.activity      = activity     ;
        this.requestCode   = requestCode  ;
        this.templateModel = templateModel;
    }

    // region org.wheatgenetics.coordinate.tc.SetExcludesOptionalFieldsNumberingAlertDialog.Handler Overridden Method
    @java.lang.Override public void handleSetDone()
    {
        assert null != this.setExcludesOptionalFieldsNumberingAlertDialog;
        this.setExcludesOptionalFieldsNumberingAlertDialog.show(this.templateModel);
    }
    // endregion

    public void testSetExcludesOptionalFieldsNumbering()
    {
        if (null == this.setExcludesOptionalFieldsNumberingAlertDialog)
            this.setExcludesOptionalFieldsNumberingAlertDialog =
                new org.wheatgenetics.coordinate.tc.SetExcludesOptionalFieldsNumberingAlertDialog(
                    this.activity, this.requestCode,this);
        this.setExcludesOptionalFieldsNumberingAlertDialog.show(this.templateModel);
    }
}