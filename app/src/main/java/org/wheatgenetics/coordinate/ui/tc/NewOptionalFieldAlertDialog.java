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
class NewOptionalFieldAlertDialog extends org.wheatgenetics.androidlibrary.AlertDialog
{
    interface Handler { public abstract void showOptionalFieldsAlertDialog(); }

    // region Fields
    private final org.wheatgenetics.coordinate.ui.tc.NewOptionalFieldAlertDialog.Handler handler;

    private android.widget.EditText                          nameEditText, defaultEditText;
    private org.wheatgenetics.coordinate.model.TemplateModel templateModel                ;
    // endregion

    private void handlePositiveButtonClick()
    {
        final java.lang.String newName =
            org.wheatgenetics.androidlibrary.Utils.getText(this.nameEditText);
        final java.lang.String newDefault =
            org.wheatgenetics.androidlibrary.Utils.getText(this.defaultEditText);

        if (0 == newName.length())
            this.showToast(org.wheatgenetics.coordinate.R.string.new_optional_field_no_name);
        else
        {
            assert null != this.templateModel;
            this.templateModel.addOptionalField(/* name => */ newName, /* value => */ newDefault);
            this.cancelAlertDialog();
            assert null != this.handler; this.handler.showOptionalFieldsAlertDialog();
        }
    }

    NewOptionalFieldAlertDialog(final android.app.Activity activity,
    final org.wheatgenetics.coordinate.ui.tc.NewOptionalFieldAlertDialog.Handler handler)
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
            if (null == this.defaultEditText) this.defaultEditText = (android.widget.EditText)
                view.findViewById(org.wheatgenetics.coordinate.R.id.valueEdit);

            this.setView(view);
        }

        this.setOKPositiveButton(null).setCancelNegativeButton();
    }

    void show(final org.wheatgenetics.coordinate.model.TemplateModel templateModel)
    {
        if (null != templateModel)
        {
            assert null != this.nameEditText   ; this.nameEditText.setText   ("");
            assert null != this.defaultEditText; this.defaultEditText.setText("");
            this.templateModel = templateModel; this.show();

            if (!this.positiveOnClickListenerHasBeenReplaced()) this.replacePositiveOnClickListener(
                new android.view.View.OnClickListener()
                {
                    @java.lang.Override
                    public void onClick(final android.view.View view)
                    {
                        org.wheatgenetics.coordinate.ui.tc.
                            NewOptionalFieldAlertDialog.this.handlePositiveButtonClick();
                    }
                });
        }
    }
}