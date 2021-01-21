package org.wheatgenetics.coordinate.tc;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

import org.phenoapps.androidlibrary.AlertDialog;
import org.phenoapps.androidlibrary.Utils;
import org.wheatgenetics.coordinate.R;
import org.wheatgenetics.coordinate.model.TemplateModel;

class AssignTitleRowsColsAlertDialog extends AlertDialog {
    // region Fields
    @NonNull
    private final
    AssignTitleRowsColsAlertDialog.Handler handler;
    private EditText titleEditText, rowsEditText, colsEditText;
    // endregion
    private TemplateModel templateModel;

    AssignTitleRowsColsAlertDialog(final Activity activity,
                                   @NonNull final
                                   AssignTitleRowsColsAlertDialog.Handler handler) {
        super(activity);
        this.handler = handler;
    }

    // region Private Methods
    // region convert() Private Methods
    private static String convert(
            @IntRange(from = 1) final int integer) {
        return integer <= 0 ? "" : String.valueOf(integer);
    }
    // endregion

    @IntRange(from = 1)
    private static int convert(
            final EditText editText)
            throws AssignTitleRowsColsAlertDialog.Unspecified {
        final String text = Utils.getText(editText);
        if (text.length() < 1)
            throw new AssignTitleRowsColsAlertDialog.Unspecified();
        else {
            final int i = Integer.parseInt(text);
            if (i < 1)
                throw new
                        AssignTitleRowsColsAlertDialog.Unspecified();
            else return i;
        }
    }

    private void assignTemplate() {
        final String title =
                Utils.getText(this.titleEditText);
        if (title.length() < 1)
            this.showToast(
                    R.string.AssignTitleRowsColsAlertDialogTitleToast);
        else
            try {
                final int rows =
                        AssignTitleRowsColsAlertDialog.convert(
                                this.rowsEditText);          // throws org.wheatgenetics.coordinate.tc
                try                                  //  .AssignTitleRowsColsAlertDialog.Unspecified
                {
                    final int cols =
                            AssignTitleRowsColsAlertDialog.convert(
                                    this.colsEditText);      // throws org.wheatgenetics.coordinate.tc
                    //  .AssignTitleRowsColsAlertDialog.Unspecified
                    if (null != this.templateModel) this.templateModel.assign(
                            /* title => */ title, /* rows => */ rows, /* cols => */ cols);
                    this.cancelAlertDialog();
                    this.handler.handleAssignDone();
                } catch (
                        final AssignTitleRowsColsAlertDialog.Unspecified e) {
                    this.showToast(R.string.AssignTitleRowsColsAlertDialogColsToast);
                }
            } catch (
                    final AssignTitleRowsColsAlertDialog.Unspecified e) {
                this.showToast(
                        R.string.AssignTitleRowsColsAlertDialogRowsToast);
            }
    }
    // endregion

    @Override
    public void configure() {
        this.setTitle(R.string.AssignTitleRowsColsAlertDialogTitle);

        {
            final View view =
                    this.inflate(R.layout.assign_title_rows_cols);
            if (null != view) {
                if (null == this.titleEditText) this.titleEditText =
                        view.findViewById(R.id.titleEditText);
                if (null == this.rowsEditText) this.rowsEditText =
                        view.findViewById(R.id.rowsEditText);
                if (null == this.colsEditText) this.colsEditText =
                        view.findViewById(R.id.colsEditText);
            }
            this.setView(view);
        }

        this.setPositiveButton(R.string.AssignTitleRowsColsAlertDialogPositiveButtonText)
                .setCancelNegativeButton();
    }
    // endregion

    void show(final TemplateModel templateModel) {
        if (null != templateModel) {
            this.templateModel = templateModel;
            if (null != this.titleEditText) this.titleEditText.setText("");
            if (null != this.rowsEditText) this.rowsEditText.setText(
                    AssignTitleRowsColsAlertDialog.convert(
                            this.templateModel.getRows()));
            if (null != this.colsEditText) this.colsEditText.setText(
                    AssignTitleRowsColsAlertDialog.convert(
                            this.templateModel.getCols()));
            this.show();

            if (!this.positiveOnClickListenerHasBeenReplaced()) this.replacePositiveOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(final View view) {
                            AssignTitleRowsColsAlertDialog
                                    .this.assignTemplate();
                        }
                    });
        }
    }

    // region Types
    @SuppressWarnings({"UnnecessaryInterfaceModifier"})
    interface Handler {
        public abstract void handleAssignDone();
    }

    private static class Unspecified extends Exception {
    }
}