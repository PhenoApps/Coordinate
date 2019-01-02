package org.wheatgenetics.coordinate.tc;

/**
 * Uses:
 * android.annotation.SuppressLint
 * android.app.Activity
 * android.content.DialogInterface
 * android.content.DialogInterface.OnClickListener
 * android.view.View
 * android.widget.Spinner
 *
 * org.wheatgenetics.androidlibrary.AlertDialog
 *
 * org.wheatgenetics.coordinate.R
 *
 * org.wheatgenetics.coordinate.model.TemplateModel
 */
class SetNumberingAlertDialog extends org.wheatgenetics.androidlibrary.AlertDialog
{
    // region Fields
    private android.widget.Spinner                           rowSpinner, colSpinner;
    private org.wheatgenetics.coordinate.model.TemplateModel templateModel         ;
    // endregion

    private void setNumbering()
    {
        assert null != this.rowSpinner; assert null != this.templateModel;
        this.templateModel.setRowNumbering(this.rowSpinner.getSelectedItemPosition() == 0);

        assert null != this.colSpinner;
        this.templateModel.setColNumbering(this.colSpinner.getSelectedItemPosition() == 0);
    }

    SetNumberingAlertDialog(final android.app.Activity activity) { super(activity); }

    @java.lang.Override public void configure()
    {
        this.setTitle(org.wheatgenetics.coordinate.R.string.SetNumberingAlertDialogTitle)
            .setCancelableToFalse();

        {
            @android.annotation.SuppressLint({"InflateParams"}) final android.view.View view =
                this.layoutInflater().inflate(
                    org.wheatgenetics.coordinate.R.layout.set_numbering,null);

            assert null != view;
            if (null == this.rowSpinner) this.rowSpinner = view.findViewById(
                org.wheatgenetics.coordinate.R.id.rowSpinner);
            if (null == this.colSpinner) this.colSpinner = view.findViewById(
                org.wheatgenetics.coordinate.R.id.colSpinner);

            this.setView(view);
        }

        this.setOKPositiveButton(new android.content.DialogInterface.OnClickListener()
            {
                @java.lang.Override
                public void onClick(final android.content.DialogInterface dialog, final int which)
                { org.wheatgenetics.coordinate.tc.SetNumberingAlertDialog.this.setNumbering(); }
            }).setCancelNegativeButton();
    }

    void show(final org.wheatgenetics.coordinate.model.TemplateModel templateModel)
    {
        if (null != templateModel)
        {
            this.templateModel = templateModel;

            assert null != this.rowSpinner;
            this.rowSpinner.setSelection(this.templateModel.getRowNumbering() ? 0 : 1);

            assert null != this.colSpinner;
            this.colSpinner.setSelection(this.templateModel.getColNumbering() ? 0 : 1);

            this.show();
        }
    }
}