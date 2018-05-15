package org.wheatgenetics.coordinate.optionalField;

/**
 * Uses:
 * android.app.Activity
 * android.content.DialogInterface
 * android.content.DialogInterface.OnClickListener
 * android.content.DialogInterface.OnMultiChoiceClickListener
 *
 * org.wheatgenetics.androidlibrary.MultiChoiceItemsAlertDialog
 *
 * org.wheatgenetics.coordinate.R
 *
 * org.wheatgenetics.coordinate.optionalField.AddOptionalFieldAlertDialog
 * org.wheatgenetics.coordinate.optionalField.AddOptionalFieldAlertDialog.Handler
 * org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields
 */
public class CheckAndAddOptionalFieldsAlertDialog
extends org.wheatgenetics.androidlibrary.MultiChoiceItemsAlertDialog
implements org.wheatgenetics.coordinate.optionalField.AddOptionalFieldAlertDialog.Handler
{
    @java.lang.SuppressWarnings({"UnnecessaryInterfaceModifier"})
    public interface Handler { public abstract void handleAddOptionalFieldDone(); }

    // region Fields
    private final
        org.wheatgenetics.coordinate.optionalField.CheckAndAddOptionalFieldsAlertDialog.Handler
            handler;

    private org.wheatgenetics.coordinate.optionalField.AddOptionalFieldAlertDialog
        addOptionalFieldAlertDialog = null;
    private org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields nonNullOptionalFields;
    private android.content.DialogInterface.OnMultiChoiceClickListener
        onMultiChoiceClickListenerInstance = null;
    // endregion

    // region Private Methods
    private void checkOptionalField(final int i, final boolean b)
    {
        assert null != this.nonNullOptionalFields;
        this.nonNullOptionalFields.setChecked(/* index => */ i, /* checked => */ b);
    }

    private android.content.DialogInterface.OnMultiChoiceClickListener onMultiChoiceClickListener()
    {
        if (null == this.onMultiChoiceClickListenerInstance)
            this.onMultiChoiceClickListenerInstance =
                new android.content.DialogInterface.OnMultiChoiceClickListener()
                {
                    @java.lang.Override
                    public void onClick(final android.content.DialogInterface dialogInterface,
                    final int i, final boolean b)
                    {
                        org.wheatgenetics.coordinate.optionalField
                            .CheckAndAddOptionalFieldsAlertDialog.this.checkOptionalField(i, b);
                    }
                };
        return this.onMultiChoiceClickListenerInstance;
    }

    private void addOptionalField()
    {
        if (null == this.addOptionalFieldAlertDialog) this.addOptionalFieldAlertDialog =
            new org.wheatgenetics.coordinate.optionalField.AddOptionalFieldAlertDialog(
                this.activity(), this);
        this.addOptionalFieldAlertDialog.show(this.nonNullOptionalFields);
    }
    // endregion

    public CheckAndAddOptionalFieldsAlertDialog(final android.app.Activity activity,
    final org.wheatgenetics.coordinate.optionalField.CheckAndAddOptionalFieldsAlertDialog.Handler
        handler) { super(activity); this.handler = handler; }

    // region Overridden Methods
    @java.lang.Override public void configure()
    {
        super.configure();
        this.setTitle(
                org.wheatgenetics.coordinate.R.string.CheckAndAddOptionalFieldsAlertDialogTitle)
            .setOKPositiveButton().setNeutralButton(org.wheatgenetics.coordinate
                    .R.string.CheckAndAddOptionalFieldsAlertDialogButtonText,
                new android.content.DialogInterface.OnClickListener()
                {
                    @java.lang.Override
                    public void onClick(final android.content.DialogInterface dialog,
                    final int which)
                    {
                        org.wheatgenetics.coordinate.optionalField
                            .CheckAndAddOptionalFieldsAlertDialog.this.addOptionalField();
                    }
                });
    }

    // region org.wheatgenetics.coordinate.optionalField.AddOptionalFieldAlertDialog.Handler Overridden Method
    @java.lang.Override public void handleAddOptionalFieldDone()
    { assert null != this.handler; this.handler.handleAddOptionalFieldDone(); }
    // endregion
    // endregion

    public void show(
    final org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields nonNullOptionalFields)
    {
        if (null != nonNullOptionalFields)
        {
            this.nonNullOptionalFields = nonNullOptionalFields;
            this.show(this.nonNullOptionalFields.names(), this.nonNullOptionalFields.checks(),
                this.onMultiChoiceClickListener());
        }
    }
}