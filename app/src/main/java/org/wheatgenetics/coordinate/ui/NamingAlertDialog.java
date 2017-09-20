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
 *
 * org.wheatgenetics.coordinate.model.TemplateModel
 *
 * org.wheatgenetics.coordinate.ui.ShowingAlertDialog
 */
class NamingAlertDialog extends org.wheatgenetics.coordinate.ui.ShowingAlertDialog
{
    // region Fields
    private android.widget.Spinner                           rowSpinner = null, colSpinner = null;
    private org.wheatgenetics.coordinate.model.TemplateModel templateModel                       ;
    // endregion

    private void setNumbering()
    {
        assert null != this.rowSpinner; assert null != this.templateModel;
        this.templateModel.setRowNumbering(this.rowSpinner.getSelectedItemPosition() == 0);

        assert null != this.colSpinner;
        this.templateModel.setColNumbering(this.colSpinner.getSelectedItemPosition() == 0);
    }

    NamingAlertDialog(final android.app.Activity activity) { super(activity); }

    @java.lang.Override
    android.app.AlertDialog.Builder makeBuilder(final int titleId)
    {
        super.makeBuilder(titleId).setCancelable(false);

        {
            final android.view.View view = this.layoutInflater().inflate(
                org.wheatgenetics.coordinate.R.layout.naming, null);

            assert null != view;
            if (null == this.rowSpinner) this.rowSpinner = (android.widget.Spinner)
                view.findViewById(org.wheatgenetics.coordinate.R.id.rowSpinner);
            if (null == this.colSpinner) this.colSpinner = (android.widget.Spinner)
                view.findViewById(org.wheatgenetics.coordinate.R.id.colSpinner);

            this.setView(view);
        }

        this.setOKPositiveButton(new android.content.DialogInterface.OnClickListener()
            {
                @java.lang.Override
                public void onClick(final android.content.DialogInterface dialog, final int which)
                {
                    org.wheatgenetics.coordinate.ui.NamingAlertDialog.this.setNumbering();
                    // assert null != dialog; dialog.cancel();                      // TODO: Remove?
                }
            });

        return this.setNegativeButton();
    }

    void show(final org.wheatgenetics.coordinate.model.TemplateModel templateModel)
    {
        if (null != templateModel)
        {
            this.configure(org.wheatgenetics.coordinate.R.string.naming);

            this.templateModel = templateModel;

            assert null != this.rowSpinner;
            this.rowSpinner.setSelection(this.templateModel.getRowNumbering() ? 0 : 1);

            assert null != this.colSpinner;
            this.colSpinner.setSelection(this.templateModel.getColNumbering() ? 0 : 1);

            this.show();
        }
    }
}