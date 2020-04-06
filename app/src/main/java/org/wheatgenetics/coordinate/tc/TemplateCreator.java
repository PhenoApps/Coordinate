package org.wheatgenetics.coordinate.tc;

/**
 * Uses:
 * android.app.Activity
 * android.os.Bundle
 *
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 * androidx.annotation.StringRes
 *
 * org.wheatgenetics.coordinate.StringGetter
 * org.wheatgenetics.coordinate.Types.RequestCode
 *
 * org.wheatgenetics.coordinate.model.TemplateModel
 *
 * org.wheatgenetics.coordinate.tc.AssignTitleRowsColsAlertDialog
 * org.wheatgenetics.coordinate.tc.AssignTitleRowsColsAlertDialog.Handler
 * org.wheatgenetics.coordinate.tc.SetExcludesOptionalFieldsNumberingAlertDialog
 * org.wheatgenetics.coordinate.tc.SetExcludesOptionalFieldsNumberingAlertDialog.Handler
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
public class TemplateCreator extends java.lang.Object implements
org.wheatgenetics.coordinate.tc.AssignTitleRowsColsAlertDialog.Handler               ,
org.wheatgenetics.coordinate.tc.SetExcludesOptionalFieldsNumberingAlertDialog.Handler,
org.wheatgenetics.coordinate.StringGetter
{
    @java.lang.SuppressWarnings({"UnnecessaryInterfaceModifier"}) public interface Handler
    {
        public abstract void handleTemplateCreated(@androidx.annotation.NonNull
        org.wheatgenetics.coordinate.model.TemplateModel templateModel);
    }

    // region Fields
    // region Constructor Fields
                                                    private final android.app.Activity activity   ;
    @org.wheatgenetics.coordinate.Types.RequestCode private final int                  requestCode;
    @androidx.annotation.NonNull                    private final
        org.wheatgenetics.coordinate.tc.TemplateCreator.Handler handler;
    // endregion

    private org.wheatgenetics.coordinate.model.TemplateModel templateModel;

    private org.wheatgenetics.coordinate.tc.AssignTitleRowsColsAlertDialog
        assignTitleRowsColsAlertDialogInstance = null;                                  // lazy load
    private org.wheatgenetics.coordinate.tc.SetExcludesOptionalFieldsNumberingAlertDialog
        setExcludesOptionalFieldsNumberingAlertDialogInstance = null;                   // lazy load
    // endregion

    // region Private Methods
    @androidx.annotation.NonNull private
    org.wheatgenetics.coordinate.tc.AssignTitleRowsColsAlertDialog assignTitleRowsColsAlertDialog()
    {
        if (null == this.assignTitleRowsColsAlertDialogInstance)
            this.assignTitleRowsColsAlertDialogInstance =
                new org.wheatgenetics.coordinate.tc.AssignTitleRowsColsAlertDialog(
                    this.activity,this);
        return this.assignTitleRowsColsAlertDialogInstance;
    }

    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.tc.SetExcludesOptionalFieldsNumberingAlertDialog
    setExcludesOptionalFieldsNumberingAlertDialog()
    {
        if (null == this.setExcludesOptionalFieldsNumberingAlertDialogInstance)
            this.setExcludesOptionalFieldsNumberingAlertDialogInstance =
                new org.wheatgenetics.coordinate.tc.SetExcludesOptionalFieldsNumberingAlertDialog(
                    this.activity, this.requestCode,this);
        return this.setExcludesOptionalFieldsNumberingAlertDialogInstance;
    }
    // endregion

    public TemplateCreator(final android.app.Activity activity,
    @org.wheatgenetics.coordinate.Types.RequestCode final int requestCode,
    @androidx.annotation.NonNull                    final
        org.wheatgenetics.coordinate.tc.TemplateCreator.Handler handler)
    { super(); this.activity = activity; this.requestCode = requestCode; this.handler = handler; }

    // region Overridden Methods
    // region org.wheatgenetics.coordinate.tc.AssignTitleRowsColsAlertDialog.Handler Overridden Method
    @java.lang.Override public void handleAssignDone()
    { this.setExcludesOptionalFieldsNumberingAlertDialog().show(this.templateModel); }
    // endregion

    // region org.wheatgenetics.coordinate.tc.SetExcludesOptionalFieldsNumberingAlertDialog Overridden Method
    @java.lang.Override public void handleSetDone()
    { this.handler.handleTemplateCreated(this.templateModel); }
    // endregion

    // region org.wheatgenetics.coordinate.StringGetter Overridden Method
    @java.lang.Override @androidx.annotation.Nullable public java.lang.String get(
    @androidx.annotation.StringRes final int resId) { return this.activity.getString(resId); }
    // endregion
    // endregion

    // region Public Methods
    public void create()
    {
        this.templateModel = org.wheatgenetics.coordinate.model.TemplateModel.makeUserDefined(this);
        this.assignTitleRowsColsAlertDialog().show(this.templateModel);
    }

    public void continueExcluding(final android.os.Bundle bundle)
    {
        this.templateModel =
            org.wheatgenetics.coordinate.model.TemplateModel.makeUserDefined(bundle,this);
        this.handleAssignDone();
    }
    // endregion
}