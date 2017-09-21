package org.wheatgenetics.coordinate.ui;

/**
 * Uses:
 * android.app.Activity
 * android.app.AlertDialog
 * android.app.AlertDialog.Builder
 * android.view.View
 * android.view.View.OnClickListener
 * android.widget.EditText
 *
 * org.wheatgenetics.androidlibrary.Utils
 *
 * org.wheatgenetics.coordinate.R
 *
 * org.wheatgenetics.coordinate.model.TemplateModel
 *
 * org.wheatgenetics.coordinate.ui.ShowingAlertDialog
 */
class NewOptionalFieldAlertDialog extends org.wheatgenetics.coordinate.ui.ShowingAlertDialog
{
    // region Fields
    private android.widget.EditText              nameEditText = null, defaultEditText = null;
    private org.wheatgenetics.coordinate.model.TemplateModel templateModel                  ;
    private boolean                                          onClickListenerReplaced = false;
    // endregion

    private void handlePositiveButtonClick()
    {
        final java.lang.String newName =
            org.wheatgenetics.androidlibrary.Utils.getText(this.nameEditText);
        final java.lang.String newDefault =
            org.wheatgenetics.androidlibrary.Utils.getText(this.defaultEditText);

        if (0 == newName.length())
            this.showLongToast(org.wheatgenetics.coordinate.R.string.new_optional_field_no_name);
        else
        {
            assert null != this.templateModel;
            this.templateModel.addOptionalField(/* name => */ newName, /* value => */ newDefault);
            assert null != this.alertDialog; this.alertDialog.cancel();   // TODO: Make into method?
        }
    }

    NewOptionalFieldAlertDialog(final android.app.Activity activity) { super(activity); }

    @java.lang.Override
    android.app.AlertDialog.Builder makeBuilder(final int titleId)
    {
        super.makeBuilder(titleId).setCancelable(false);

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

        this.setOKPositiveButton(null);
        return this.setNegativeButton();
    }

    void show(final org.wheatgenetics.coordinate.model.TemplateModel templateModel)
    {
        if (null != templateModel)
        {
            this.configure(org.wheatgenetics.coordinate.R.string.new_optional_field);

            assert null != this.nameEditText   ; this.nameEditText.setText   ("");
            assert null != this.defaultEditText; this.defaultEditText.setText("");
            this.templateModel = templateModel; this.show();

            if (!this.onClickListenerReplaced)
            {
                assert null != this.alertDialog; this.alertDialog.getButton(
                    android.app.AlertDialog.BUTTON_POSITIVE).setOnClickListener(
                        new android.view.View.OnClickListener()
                        {
                            @java.lang.Override
                            public void onClick(final android.view.View view)
                            {
                                org.wheatgenetics.coordinate.ui.
                                    NewOptionalFieldAlertDialog.this.handlePositiveButtonClick();
                            }
                        });
                this.onClickListenerReplaced = true;
            }
        }
    }
}