package org.wheatgenetics.coordinate.tc.exclude;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.wheatgenetics.androidlibrary.AlertDialog;
import org.wheatgenetics.androidlibrary.Utils;
import org.wheatgenetics.coordinate.R;
import org.wheatgenetics.coordinate.model.TemplateModel;

class GeneratedExcludedCellsAlertDialog extends AlertDialog {
    // region Fields
    private EditText editText;
    private TextView exceptionTextView;
    private TemplateModel templateModel;
    // endregion

    GeneratedExcludedCellsAlertDialog(final Activity activity) {
        super(activity);
    }

    private void setGeneratedExcludedCellsAmount() {
        final int amount = org.wheatgenetics.javalib.Utils.convert(
                Utils.getText(this.editText));

        if (null != this.templateModel)
            try {
                this.templateModel.setGeneratedExcludedCellsAmount(amount);// throws java.lang.Ille-
                this.cancelAlertDialog();                                  //  galArgumentException
            } catch (final IllegalArgumentException e) {
                if (null != this.exceptionTextView) {
                    this.exceptionTextView.setText(e.getMessage());
                    this.exceptionTextView.setVisibility(View.VISIBLE);
                }
                if (null != this.editText) this.editText.selectAll();
            }
    }

    @Override
    public void configure() {
        this.setTitle(R.string.GeneratedExcludedCellsAlertDialogTitle)
                .setCancelableToFalse();

        {
            @SuppressLint({"InflateParams"}) final View view =
                    this.layoutInflater().inflate(
                            R.layout.generated_excluded_cells, null);
            if (null != view) {
                if (null == this.editText) this.editText = view.findViewById(
                        R.id.generatedExcludedCellsEditText);
                if (null == this.exceptionTextView) this.exceptionTextView = view.findViewById(
                        R.id.generatedExcludedCellsExceptionTextView);
            }
            this.setView(view);
        }

        this.setOKPositiveButton(null).setCancelNegativeButton();
    }

    void show(final TemplateModel templateModel) {
        if (null != templateModel) {
            this.templateModel = templateModel;

            if (null != this.editText) this.editText.selectAll();

            if (null != this.exceptionTextView)
                this.exceptionTextView.setVisibility(View.INVISIBLE);

            this.show();

            if (!this.positiveOnClickListenerHasBeenReplaced()) this.replacePositiveOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(final View view) {
                            GeneratedExcludedCellsAlertDialog
                                    .this.setGeneratedExcludedCellsAmount();
                        }
                    });
        }
    }
}