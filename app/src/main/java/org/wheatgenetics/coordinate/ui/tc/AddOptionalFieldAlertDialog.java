package org.wheatgenetics.coordinate.ui.tc;

/**
 * Uses:
 * android.app.Activity
 * android.view.View
 * android.view.View.OnClickListener
 * android.widget.EditText
 *
 * org.wheatgenetics.androidlibrary.AlertDialog
 * org.wheatgenetics.androidlibrary.Utils
 *
 * org.wheatgenetics.coordinate.R
 *
 * org.wheatgenetics.coordinate.model.TemplateModel
 */
class AddOptionalFieldAlertDialog extends org.wheatgenetics.androidlibrary.AlertDialog
{
    @java.lang.SuppressWarnings("UnnecessaryInterfaceModifier")
    interface Handler { public abstract void handleAddOptionalFieldDone(); }

    // region Fields
    private final org.wheatgenetics.coordinate.ui.tc.AddOptionalFieldAlertDialog.Handler handler;

    private android.widget.EditText                          nameEditText, defaultValueEditText;
    private org.wheatgenetics.coordinate.model.TemplateModel templateModel                     ;
    // endregion

    private void addOptionalField()
    {
        final java.lang.String name =
            org.wheatgenetics.androidlibrary.Utils.getText(this.nameEditText);
        final java.lang.String defaultValue =
            org.wheatgenetics.androidlibrary.Utils.getText(this.defaultValueEditText);

        if (0 == name.length())
            this.showToast(org.wheatgenetics.coordinate.R.string.new_optional_field_no_name);
        else
        {
            assert null != this.templateModel;
            this.templateModel.addOptionalField(/* name => */ name, /* value => */ defaultValue);
            this.cancelAlertDialog();
            assert null != this.handler; this.handler.handleAddOptionalFieldDone();
        }
    }

    AddOptionalFieldAlertDialog(final android.app.Activity activity,
    final org.wheatgenetics.coordinate.ui.tc.AddOptionalFieldAlertDialog.Handler handler)
    { super(activity); this.handler = handler; }

    @java.lang.Override
    public void configure()
    {
        this.setTitle(org.wheatgenetics.coordinate.R.string.new_optional_field)
            .setCancelableToFalse();

        {
            final android.view.View view = this.layoutInflater().inflate(
                org.wheatgenetics.coordinate.R.layout.optional_new, null);

            assert null != view;
            if (null == this.nameEditText) this.nameEditText = (android.widget.EditText)
                view.findViewById(org.wheatgenetics.coordinate.R.id.fieldEdit);
            if (null == this.defaultValueEditText)
                this.defaultValueEditText = (android.widget.EditText)
                    view.findViewById(org.wheatgenetics.coordinate.R.id.valueEdit);

            this.setView(view);
        }

        this.setOKPositiveButton(null).setCancelNegativeButton();
    }

    void show(final org.wheatgenetics.coordinate.model.TemplateModel templateModel)
    {
        if (null != templateModel)
        {
            assert null != this.nameEditText        ; this.nameEditText.setText        ("");
            assert null != this.defaultValueEditText; this.defaultValueEditText.setText("");
            this.templateModel = templateModel; this.show();

            if (!this.positiveOnClickListenerHasBeenReplaced()) this.replacePositiveOnClickListener(
                new android.view.View.OnClickListener()
                {
                    @java.lang.Override
                    public void onClick(final android.view.View view)
                    {
                        org.wheatgenetics.coordinate.ui.tc
                            .AddOptionalFieldAlertDialog.this.addOptionalField();
                    }
                });
        }
    }
}