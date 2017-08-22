package org.wheatgenetics.coordinate.ui;

/**
 * Uses:
 * android.app.Activity
 * android.app.AlertDialog
 * android.app.AlertDialog.Builder
 * android.content.DialogInterface
 * android.content.DialogInterface.OnClickListener
 * android.view.LayoutInflater
 * android.view.View
 * android.widget.EditText
 *
 * org.wheatgenetics.javalib.Utils
 *
 * org.wheatgenetics.androidlibrary.Utils
 *
 * org.wheatgenetics.coordinate.R
 */
class NewOptionalFieldAlertDialog extends java.lang.Object
{
    interface Handler
    {
        public abstract void retry(int errorMsgResId, java.lang.String oldName,
          java.lang.String newDefault);
        public abstract void addOptionalField(java.lang.String newName,
            java.lang.String newDefault);
    }

    // region Fields
    private final android.app.Activity                                                activity;
    private final org.wheatgenetics.coordinate.ui.NewOptionalFieldAlertDialog.Handler handler ;

    private android.app.AlertDialog         alertDialog  = null                        ;
    private android.app.AlertDialog.Builder builder      = null                        ;
    private android.widget.EditText         nameEditText = null, defaultEditText = null;
    // endregion

    // region Private Methods
    private static java.lang.String getText(final android.widget.EditText editText)
    {
        assert null != editText;
        return org.wheatgenetics.javalib.Utils.adjust(editText.getText().toString());
    }

    private void handlePositiveButtonClick(final java.lang.String oldName,
    final android.content.DialogInterface dialog)
    {
        assert null != this.nameEditText;
        final java.lang.String newName =
            org.wheatgenetics.coordinate.ui.NewOptionalFieldAlertDialog.getText(this.nameEditText);

        assert null != this.defaultEditText;
        final java.lang.String newDefault =
            org.wheatgenetics.coordinate.ui.NewOptionalFieldAlertDialog.getText(
                this.defaultEditText);

        assert null != this.handler;
        if (0 == newName.length())
            this.handler.retry(org.wheatgenetics.coordinate.R.string.new_optional_field_no_name,
                oldName, newDefault);
        else
        {
            assert null != dialog;
            dialog.cancel();

            this.handler.addOptionalField(newName, newDefault);
        }
    }
    // endregion

    NewOptionalFieldAlertDialog(final android.app.Activity activity,
    final org.wheatgenetics.coordinate.ui.NewOptionalFieldAlertDialog.Handler handler)
    { super(); this.activity = activity; this.handler = handler; }

    void show(final java.lang.String oldName, final java.lang.String oldDefault)
    {
        if (null == this.alertDialog)
        {
            if (null == this.builder)
            {
                this.builder = new android.app.AlertDialog.Builder(this.activity);
                this.builder.setTitle(org.wheatgenetics.coordinate.R.string.new_optional_field)
                    .setCancelable(false)
                    .setNegativeButton(org.wheatgenetics.coordinate.R.string.cancel,
                        org.wheatgenetics.androidlibrary.Utils.cancellingOnClickListener());

                android.view.View view;
                {
                    assert null != this.activity;
                    final android.view.LayoutInflater layoutInflater =
                        this.activity.getLayoutInflater();
                    view = layoutInflater.inflate(
                        org.wheatgenetics.coordinate.R.layout.optional_new, null);
                }

                assert null != view;
                if (null == this.nameEditText)
                {
                    this.nameEditText = (android.widget.EditText)
                        view.findViewById(org.wheatgenetics.coordinate.R.id.fieldEdit);
                    assert null != this.nameEditText;
                }
                this.nameEditText.setText(oldName);

                if (null == this.defaultEditText)
                {
                    this.defaultEditText = (android.widget.EditText)
                        view.findViewById(org.wheatgenetics.coordinate.R.id.valueEdit);
                    assert null != this.defaultEditText;
                }
                this.defaultEditText.setText(oldDefault);

                this.builder.setView(view).setPositiveButton(
                    org.wheatgenetics.coordinate.R.string.ok,
                    new android.content.DialogInterface.OnClickListener()
                    {
                        @java.lang.Override
                        public void onClick(final android.content.DialogInterface dialog,
                        final int which)
                        {
                            org.wheatgenetics.coordinate.ui.NewOptionalFieldAlertDialog.
                                this.handlePositiveButtonClick(oldName, dialog);
                        }
                    });
            }
            this.alertDialog = this.builder.create();
            assert null != this.alertDialog;
        }
        this.alertDialog.show();
    }
}