package org.wheatgenetics.coordinate.tc;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Spinner;

import androidx.annotation.NonNull;

import org.wheatgenetics.androidlibrary.AlertDialog;
import org.wheatgenetics.coordinate.R;
import org.wheatgenetics.coordinate.model.TemplateModel;

class SetNumberingAlertDialog extends AlertDialog {
    // region Fields
    private Spinner rowSpinner, colSpinner;
    private TemplateModel templateModel;
    // endregion

    SetNumberingAlertDialog(final Activity activity) {
        super(activity);
    }

    // region Private Methods
    private static boolean numbering(
            @NonNull final Spinner spinner) {
        return spinner.getSelectedItemPosition() == 0;
    }

    private static int position(final boolean numbering) {
        return numbering ? 0 : 1;
    }
    // endregion

    private void setNumbering() {
        if (null != this.templateModel) {
            if (null != this.rowSpinner) this.templateModel.setRowNumbering(
                    SetNumberingAlertDialog.numbering(this.rowSpinner));

            if (null != this.colSpinner) this.templateModel.setColNumbering(
                    SetNumberingAlertDialog.numbering(this.colSpinner));
        }
    }

    @Override
    public void configure() {
        this.setTitle(R.string.SetNumberingAlertDialogTitle)
                .setCancelableToFalse();

        {
            @SuppressLint({"InflateParams"}) final View view =
                    this.layoutInflater().inflate(
                            R.layout.set_numbering, null);

            if (null != view) {
                if (null == this.rowSpinner) this.rowSpinner = view.findViewById(
                        R.id.rowSpinner);
                if (null == this.colSpinner) this.colSpinner = view.findViewById(
                        R.id.colSpinner);
            }
            this.setView(view);
        }

        this.setOKPositiveButton(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, final int which) {
                SetNumberingAlertDialog.this.setNumbering();
            }
        }).setCancelNegativeButton();
    }

    void show(final TemplateModel templateModel) {
        if (null != templateModel) {
            this.templateModel = templateModel;

            if (null != this.rowSpinner) this.rowSpinner.setSelection(
                    SetNumberingAlertDialog.position(
                            this.templateModel.getRowNumbering()));
            if (null != this.colSpinner) this.colSpinner.setSelection(
                    SetNumberingAlertDialog.position(
                            this.templateModel.getColNumbering()));

            this.show();
        }
    }
}