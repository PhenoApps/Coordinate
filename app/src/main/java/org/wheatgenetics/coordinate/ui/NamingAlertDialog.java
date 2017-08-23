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
 * android.widget.Spinner
 *
 * org.wheatgenetics.coordinate.R
 *
 * org.wheatgenetics.androidlibrary.Utils
 */
class NamingAlertDialog extends java.lang.Object
{
    interface Handler
    { public abstract void setNumbering(boolean rowNumbering, boolean colNumbering); }

    // region Fields
    private final android.app.Activity                                      activity;
    private final org.wheatgenetics.coordinate.ui.NamingAlertDialog.Handler handler ;

    private android.app.AlertDialog         alertDialog  = null                   ;
    private android.app.AlertDialog.Builder builder      = null                   ;
    private android.widget.Spinner          rowSpinner   = null, colSpinner = null;
    // endregion

    private void setNumbering()
    {
        assert null != this.rowSpinner; assert null != this.colSpinner; assert null != this.handler;
        this.handler.setNumbering(
            this.rowSpinner.getSelectedItemPosition() == 0,
            this.colSpinner.getSelectedItemPosition() == 0);
    }

    NamingAlertDialog(final android.app.Activity activity,
    final org.wheatgenetics.coordinate.ui.NamingAlertDialog.Handler handler)
    { super(); this.activity = activity; this.handler = handler; }

    void show(final boolean rowNumbering, final boolean colNumbering)
    {
        if (null == this.alertDialog)
        {
            if (null == this.builder)
            {
                this.builder = new android.app.AlertDialog.Builder(this.activity);
                this.builder.setTitle(org.wheatgenetics.coordinate.R.string.naming).
                    setCancelable(false)
                    .setNegativeButton(org.wheatgenetics.coordinate.R.string.cancel,
                        org.wheatgenetics.androidlibrary.Utils.cancellingOnClickListener());
                {
                    android.view.View view;
                    {
                        assert null != this.activity;
                        final android.view.LayoutInflater layoutInflater =
                            this.activity.getLayoutInflater();
                        view = layoutInflater.inflate(
                            org.wheatgenetics.coordinate.R.layout.naming, null);
                    }

                    assert null != view;
                    if (null == this.rowSpinner) this.rowSpinner = (android.widget.Spinner)
                        view.findViewById(org.wheatgenetics.coordinate.R.id.rowSpinner);
                    if (null == this.colSpinner) this.colSpinner = (android.widget.Spinner)
                        view.findViewById(org.wheatgenetics.coordinate.R.id.colSpinner);

                    this.builder.setView(view).setPositiveButton(
                        org.wheatgenetics.coordinate.R.string.ok,
                        new android.content.DialogInterface.OnClickListener()
                        {
                            @java.lang.Override
                            public void onClick(final android.content.DialogInterface dialog,
                            final int which)
                            {
                                org.wheatgenetics.coordinate.ui.
                                    NamingAlertDialog.this.setNumbering();
                                assert null != dialog; dialog.cancel();
                            }
                        });
                }
            }
            this.alertDialog = this.builder.create();
            assert null != this.alertDialog;
        }

        assert null != this.rowSpinner; this.rowSpinner.setSelection(rowNumbering ? 0 : 1);
        assert null != this.colSpinner; this.colSpinner.setSelection(colNumbering ? 0 : 1);

        this.alertDialog.show();
    }
}