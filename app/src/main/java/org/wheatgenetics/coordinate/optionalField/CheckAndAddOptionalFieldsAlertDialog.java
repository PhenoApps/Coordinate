package org.wheatgenetics.coordinate.optionalField;

/**
 * Uses:
 * android.app.Activity
 * android.content.DialogInterface
 * android.content.DialogInterface.OnClickListener
 * android.content.DialogInterface.OnMultiChoiceClickListener
 *
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
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
{
    @java.lang.SuppressWarnings({"UnnecessaryInterfaceModifier"}) public interface Handler
    { public abstract void showCheckAndAddOptionalFieldsAlertDialogAgain(); }

    // region Fields
    @androidx.annotation.NonNull private final
        org.wheatgenetics.coordinate.optionalField.CheckAndAddOptionalFieldsAlertDialog.Handler
            handler;

    private org.wheatgenetics.coordinate.optionalField.AddOptionalFieldAlertDialog
        addOptionalFieldAlertDialogInstance = null;                                     // lazy load
    private org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields nonNullOptionalFields;
    private android.content.DialogInterface.OnMultiChoiceClickListener
        onMultiChoiceClickListenerInstance = null;                                      // lazy load
    // endregion

    // region Private Methods
    private void showCheckAndAddOptionalFieldsAlertDialogAgain()
    { this.handler.showCheckAndAddOptionalFieldsAlertDialogAgain(); }

    // region onMultiChoiceClickListener() Private Methods
    private void checkOptionalField(final int index, final boolean checked)
    {
        if (null != this.nonNullOptionalFields)
            this.nonNullOptionalFields.setChecked(index, checked);
    }

    @androidx.annotation.NonNull
    private android.content.DialogInterface.OnMultiChoiceClickListener onMultiChoiceClickListener()
    {
        if (null == this.onMultiChoiceClickListenerInstance)
            this.onMultiChoiceClickListenerInstance =
                new android.content.DialogInterface.OnMultiChoiceClickListener()
                {
                    @java.lang.Override
                    public void onClick(final android.content.DialogInterface dialogInterface,
                    final int which, final boolean isChecked)
                    {
                        org.wheatgenetics.coordinate.optionalField
                            .CheckAndAddOptionalFieldsAlertDialog.this.checkOptionalField(
                                which, isChecked);
                    }
                };
        return this.onMultiChoiceClickListenerInstance;
    }
    // endregion

    // region addOptionalField() Private Methods
    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.optionalField.AddOptionalFieldAlertDialog
    addOptionalFieldAlertDialog()
    {
        if (null == this.addOptionalFieldAlertDialogInstance)
            this.addOptionalFieldAlertDialogInstance =
                new org.wheatgenetics.coordinate.optionalField.AddOptionalFieldAlertDialog(
                    this.activity(), new
                    org.wheatgenetics.coordinate.optionalField.AddOptionalFieldAlertDialog.Handler()
                    {
                        @java.lang.Override public void handleAddOptionalFieldDone()
                        {
                            org.wheatgenetics.coordinate.optionalField
                                .CheckAndAddOptionalFieldsAlertDialog.this
                                .showCheckAndAddOptionalFieldsAlertDialogAgain();
                        }
                    });
        return this.addOptionalFieldAlertDialogInstance;
    }

    private void addOptionalField()
    { this.addOptionalFieldAlertDialog().show(this.nonNullOptionalFields); }
    // endregion
    // endregion

    public CheckAndAddOptionalFieldsAlertDialog(final android.app.Activity activity,
    @androidx.annotation.NonNull final
        org.wheatgenetics.coordinate.optionalField.CheckAndAddOptionalFieldsAlertDialog.Handler
        handler) { super(activity); this.handler = handler; }

    @java.lang.Override public void configure()
    {
        super.configure(); this.setTitle(
                org.wheatgenetics.coordinate.R.string.CheckAndAddOptionalFieldsAlertDialogTitle)
            .setOKPositiveButton().setNeutralButton(org.wheatgenetics.coordinate
                    .R.string.CheckAndAddOptionalFieldsAlertDialogButtonText,
                new android.content.DialogInterface.OnClickListener()
                {
                    @java.lang.Override public void onClick(
                    final android.content.DialogInterface dialog, final int which)
                    {
                        org.wheatgenetics.coordinate.optionalField
                            .CheckAndAddOptionalFieldsAlertDialog.this.addOptionalField();
                    }
                });
    }

    public void show(@androidx.annotation.Nullable
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