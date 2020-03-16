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
 * androidx.annotation.NonNull
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

    // region Private Methods
    private static boolean numbering(
    @androidx.annotation.NonNull final android.widget.Spinner spinner)
    { return spinner.getSelectedItemPosition() == 0; }

    private void setNumbering()
    {
        if (null != this.templateModel)
        {
            if (null != this.rowSpinner) this.templateModel.setRowNumbering(
                org.wheatgenetics.coordinate.tc.SetNumberingAlertDialog.numbering(this.rowSpinner));

            if (null != this.colSpinner) this.templateModel.setColNumbering(
                org.wheatgenetics.coordinate.tc.SetNumberingAlertDialog.numbering(this.colSpinner));
        }
    }

    private static int position(final boolean numbering) { return numbering ? 0 : 1; }
    // endregion

    SetNumberingAlertDialog(final android.app.Activity activity) { super(activity); }

    @java.lang.Override public void configure()
    {
        this.setTitle(org.wheatgenetics.coordinate.R.string.SetNumberingAlertDialogTitle)
            .setCancelableToFalse();

        {
            @android.annotation.SuppressLint({"InflateParams"}) final android.view.View view =
                this.layoutInflater().inflate(
                    org.wheatgenetics.coordinate.R.layout.set_numbering,null);

            if (null != view)
            {
                if (null == this.rowSpinner) this.rowSpinner = view.findViewById(
                    org.wheatgenetics.coordinate.R.id.rowSpinner);
                if (null == this.colSpinner) this.colSpinner = view.findViewById(
                    org.wheatgenetics.coordinate.R.id.colSpinner);
            }
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

            if (null != this.rowSpinner) this.rowSpinner.setSelection(
                org.wheatgenetics.coordinate.tc.SetNumberingAlertDialog.position(
                    this.templateModel.getRowNumbering()));
            if (null != this.colSpinner) this.colSpinner.setSelection(
                org.wheatgenetics.coordinate.tc.SetNumberingAlertDialog.position(
                    this.templateModel.getColNumbering()));

            this.show();
        }
    }
}