package org.wheatgenetics.coordinate.optionalField;

/**
 * Uses:
 * android.app.Activity
 *
 * org.wheatgenetics.coordinate.optionalField.AddOptionalFieldAlertDialog
 * org.wheatgenetics.coordinate.optionalField.AddOptionalFieldAlertDialog.Handler
 * org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
public class AddOptionalFieldAlertDialogTester extends java.lang.Object
implements org.wheatgenetics.coordinate.optionalField.AddOptionalFieldAlertDialog.Handler
{
    @java.lang.SuppressWarnings({"UnnecessaryInterfaceModifier"})
    public interface Handler { public abstract void handleAddOptionalFieldDone(); }

    // region Fields
    private final android.app.Activity activity;
    private final
        org.wheatgenetics.coordinate.optionalField.AddOptionalFieldAlertDialogTester.Handler
            handler;

    private org.wheatgenetics.coordinate.optionalField.AddOptionalFieldAlertDialog
        addOptionalFieldAlertDialog = null;
    // endregion

    public AddOptionalFieldAlertDialogTester(final android.app.Activity activity,
    final org.wheatgenetics.coordinate.optionalField.AddOptionalFieldAlertDialogTester.Handler
        handler) { super(); this.activity = activity; this.handler = handler; }

    // region org.wheatgenetics.coordinate.optionalField.AddOptionalFieldAlertDialog.Handler Overridden Method
    @java.lang.Override public void handleAddOptionalFieldDone()
    { assert null != this.handler; this.handler.handleAddOptionalFieldDone(); }
    // endregion

    public void test(final org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields
    nonNullOptionalFields)
    {
        if (null != nonNullOptionalFields)
        {
            if (null == this.addOptionalFieldAlertDialog) this.addOptionalFieldAlertDialog =
                new org.wheatgenetics.coordinate.optionalField.AddOptionalFieldAlertDialog(
                    this.activity,this);
            this.addOptionalFieldAlertDialog.show(nonNullOptionalFields);
        }
    }
}