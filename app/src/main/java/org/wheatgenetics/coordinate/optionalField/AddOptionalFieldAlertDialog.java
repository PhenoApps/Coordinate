package org.wheatgenetics.coordinate.optionalField;

/**
 * Uses:
 * android.annotation.SuppressLint
 * android.app.Activity
 * android.view.View
 * android.view.View.OnClickListener
 * android.widget.EditText
 *
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 *
 * org.wheatgenetics.androidlibrary.AlertDialog
 * org.wheatgenetics.androidlibrary.Utils
 *
 * org.wheatgenetics.coordinate.R
 *
 * org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields
 */
class AddOptionalFieldAlertDialog extends org.wheatgenetics.androidlibrary.AlertDialog
{
    @java.lang.SuppressWarnings({"UnnecessaryInterfaceModifier"}) interface Handler
    { public abstract void handleAddOptionalFieldDone(); }

    // region Fields
    @androidx.annotation.NonNull private final
        org.wheatgenetics.coordinate.optionalField.AddOptionalFieldAlertDialog.Handler handler;

    private android.widget.EditText                             nameEditText, defaultValueEditText;
    private org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields nonNullOptionalFields;
    // endregion

    private void addOptionalField()
    {
        final java.lang.String name =
            org.wheatgenetics.androidlibrary.Utils.getText(this.nameEditText);

        if (name.length() < 1)
            this.showToast(org.wheatgenetics.coordinate.R.string.AddOptionalFieldAlertDialogToast);
        else
        {
            if (null != this.nonNullOptionalFields) this.nonNullOptionalFields.add(
                /* name  => */ name,
                /* value => */ org.wheatgenetics.androidlibrary.Utils.getText(
                    this.defaultValueEditText),
                /* hint => */null);
            this.cancelAlertDialog(); this.handler.handleAddOptionalFieldDone();
        }
    }

    AddOptionalFieldAlertDialog(final android.app.Activity activity, @androidx.annotation.NonNull
    final org.wheatgenetics.coordinate.optionalField.AddOptionalFieldAlertDialog.Handler handler)
    { super(activity); this.handler = handler; }

    @java.lang.Override public void configure()
    {
        this.setTitle(org.wheatgenetics.coordinate.R.string.AddOptionalFieldAlertDialogTitle)
            .setCancelableToFalse();

        {
            @android.annotation.SuppressLint({"InflateParams"}) final android.view.View view =
                this.layoutInflater().inflate(
                    org.wheatgenetics.coordinate.R.layout.add_optional_field,null);

            if (null != view)
            {
                if (null == this.nameEditText) this.nameEditText = view.findViewById(
                    org.wheatgenetics.coordinate.R.id.nameEditText);
                if (null == this.defaultValueEditText) this.defaultValueEditText =
                    view.findViewById(org.wheatgenetics.coordinate.R.id.defaultValueEditText);
            }

            this.setView(view);
        }

        this.setOKPositiveButton(null).setCancelNegativeButton();
    }

    void show(@androidx.annotation.Nullable
    final org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields nonNullOptionalFields)
    {
        if (null != nonNullOptionalFields)
        {
            if (null != this.nameEditText        ) this.nameEditText.setText        ("");
            if (null != this.defaultValueEditText) this.defaultValueEditText.setText("");
            this.nonNullOptionalFields = nonNullOptionalFields; this.show();

            if (!this.positiveOnClickListenerHasBeenReplaced()) this.replacePositiveOnClickListener(
                new android.view.View.OnClickListener()
                {
                    @java.lang.Override public void onClick(final android.view.View view)
                    {
                        org.wheatgenetics.coordinate.optionalField
                            .AddOptionalFieldAlertDialog.this.addOptionalField();
                    }
                });
        }
    }
}