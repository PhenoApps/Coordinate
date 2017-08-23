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
 * org.wheatgenetics.androidlibrary.Utils
 *
 * org.wheatgenetics.coordinate.R
 */
class ExportAlertDialog extends java.lang.Object
{
    interface Handler { public abstract void exportGrid(java.lang.String fileName); }

    // region Fields
    private final android.app.Activity                                      activity;
    private final org.wheatgenetics.coordinate.ui.ExportAlertDialog.Handler handler ;

    private android.app.AlertDialog         alertDialog = null;
    private android.app.AlertDialog.Builder builder     = null;
    private android.widget.EditText         editText    = null;
    // endregion

    private void exportGrid()
    {
        assert null != this.editText; assert null != this.handler;
        this.handler.exportGrid(this.editText.getText().toString().trim());
    }

    ExportAlertDialog(final android.app.Activity activity,
    final org.wheatgenetics.coordinate.ui.ExportAlertDialog.Handler handler)
    { super(); this.activity = activity; this.handler = handler; }

    void show(final java.lang.String datedFirstValue)
    {
        if (null == this.alertDialog)
        {
            if (null == this.builder)
            {
                this.builder = new android.app.AlertDialog.Builder(this.activity);
                this.builder.setTitle(org.wheatgenetics.coordinate.R.string.filename_set);
                {
                    android.view.View view;
                    {
                        assert null != this.activity;
                        final android.view.LayoutInflater layoutInflater =
                            this.activity.getLayoutInflater();
                        view = layoutInflater.inflate(
                            org.wheatgenetics.coordinate.R.layout.file_input, null);
                    }

                    if (null == this.editText)
                    {
                        assert null != view; this.editText = (android.widget.EditText)
                            view.findViewById(org.wheatgenetics.coordinate.R.id.nameEdit);
                    }

                    this.builder.setView(view).setPositiveButton(
                        org.wheatgenetics.coordinate.R.string.ok,
                        new android.content.DialogInterface.OnClickListener()
                        {
                            @java.lang.Override
                            public void onClick(final android.content.DialogInterface dialog,
                            final int which)
                            {
                                assert null != dialog; dialog.cancel();
                                org.wheatgenetics.coordinate.ui.ExportAlertDialog.this.exportGrid();
                            }
                        });
                }
                this.builder.setNegativeButton(org.wheatgenetics.coordinate.R.string.cancel,
                    org.wheatgenetics.androidlibrary.Utils.cancellingOnClickListener());
            }
            this.alertDialog = this.builder.create();
            assert null != this.alertDialog;
        }

        assert null != this.editText;
        this.editText.setText(datedFirstValue);

        this.alertDialog.show();
    }
}