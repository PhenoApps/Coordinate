package org.wheatgenetics.coordinate.optionalField;

/**
 * Uses:
 * android.app.Activity
 *
 * androidx.annotation.NonNull
 *
 * org.wheatgenetics.coordinate.optionalField.AddOptionalFieldAlertDialog
 * org.wheatgenetics.coordinate.optionalField.AddOptionalFieldAlertDialog.Handler
 * org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
public class AddOptionalFieldAlertDialogTester extends java.lang.Object
implements org.wheatgenetics.coordinate.optionalField.AddOptionalFieldAlertDialog.Handler
{
    @java.lang.SuppressWarnings({"UnnecessaryInterfaceModifier"}) public interface Handler
    { public abstract void handleAddOptionalFieldDone(); }

    // region Fields
                                 private final android.app.Activity activity;
    @androidx.annotation.NonNull private final
        org.wheatgenetics.coordinate.optionalField.AddOptionalFieldAlertDialogTester.Handler
            handler;

    private org.wheatgenetics.coordinate.optionalField.AddOptionalFieldAlertDialog
        addOptionalFieldAlertDialogInstance = null;                                     // lazy load
    // endregion

    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.optionalField.AddOptionalFieldAlertDialog
    addOptionalFieldAlertDialog()
    {
        if (null == this.addOptionalFieldAlertDialogInstance)
            this.addOptionalFieldAlertDialogInstance =
                new org.wheatgenetics.coordinate.optionalField.AddOptionalFieldAlertDialog(
                    this.activity,this);
        return this.addOptionalFieldAlertDialogInstance;
    }

    public AddOptionalFieldAlertDialogTester(final android.app.Activity activity,
    @androidx.annotation.NonNull final
        org.wheatgenetics.coordinate.optionalField.AddOptionalFieldAlertDialogTester.Handler
        handler) { super(); this.activity = activity; this.handler = handler; }

    // region org.wheatgenetics.coordinate.optionalField.AddOptionalFieldAlertDialog.Handler Overridden Method
    @java.lang.Override public void handleAddOptionalFieldDone()
    { this.handler.handleAddOptionalFieldDone(); }
    // endregion

    public void test(final org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields
    nonNullOptionalFields)
    {
        if (null != nonNullOptionalFields)
            this.addOptionalFieldAlertDialog().show(nonNullOptionalFields);
    }
}