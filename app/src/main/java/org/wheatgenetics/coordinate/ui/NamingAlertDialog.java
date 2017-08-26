package org.wheatgenetics.coordinate.ui;

/**
 * Uses:
 * android.app.Activity
 * android.app.AlertDialog.Builder
 * android.content.DialogInterface
 * android.content.DialogInterface.OnClickListener
 * android.view.View
 * android.widget.Spinner
 *
 * org.wheatgenetics.coordinate.R
 * org.wheatgenetics.coordinate.ui.ShowingAlertDialog
 */
class NamingAlertDialog extends org.wheatgenetics.coordinate.ui.ShowingAlertDialog
{
    interface Handler
    { public abstract void setNumbering(boolean rowNumbering, boolean colNumbering); }

    // region Fields
    private final org.wheatgenetics.coordinate.ui.NamingAlertDialog.Handler handler;
    private       android.widget.Spinner       rowSpinner = null, colSpinner = null;
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
    { super(activity); this.handler = handler; }

    @java.lang.Override
    android.app.AlertDialog.Builder configure(final int titleId)
    {
        super.configure(titleId).setCancelable(false);

        {
            final android.view.View view = this.layoutInflater().inflate(
                org.wheatgenetics.coordinate.R.layout.naming, null);

            assert null != view;
            if (null == this.rowSpinner) this.rowSpinner = (android.widget.Spinner)
                view.findViewById(org.wheatgenetics.coordinate.R.id.rowSpinner);
            if (null == this.colSpinner) this.colSpinner = (android.widget.Spinner)
                view.findViewById(org.wheatgenetics.coordinate.R.id.colSpinner);

            this.builder.setView(view);
        }

        this.builder.setPositiveButton(org.wheatgenetics.coordinate.R.string.ok,
            new android.content.DialogInterface.OnClickListener()
            {
                @java.lang.Override
                public void onClick(final android.content.DialogInterface dialog, final int which)
                {
                    org.wheatgenetics.coordinate.ui.NamingAlertDialog.this.setNumbering();
                    assert null != dialog; dialog.cancel();
                }
            });

        return this.setNegativeButton();
    }

    void show(final boolean rowNumbering, final boolean colNumbering)
    {
        this.configure(org.wheatgenetics.coordinate.R.string.naming);

        assert null != this.rowSpinner; this.rowSpinner.setSelection(rowNumbering ? 0 : 1);
        assert null != this.colSpinner; this.colSpinner.setSelection(colNumbering ? 0 : 1);
        this.show();
    }
}