package org.wheatgenetics.coordinate.tc;

/**
 * Uses:
 * android.annotation.SuppressLint
 * android.app.Activity
 * android.view.View
 * android.view.View.OnClickListener
 * android.widget.EditText
 * android.widget.TextView
 *
 * org.wheatgenetics.javalib.Utils
 *
 * org.wheatgenetics.androidlibrary.AlertDialog
 * org.wheatgenetics.androidlibrary.Utils
 *
 * org.wheatgenetics.coordinate.R
 *
 * org.wheatgenetics.coordinate.model.TemplateModel
 */
class GeneratedExcludedCellsAlertDialog extends org.wheatgenetics.androidlibrary.AlertDialog
{
    // region Fields
    private android.widget.EditText                          editText         ;
    private android.widget.TextView                          exceptionTextView;
    private org.wheatgenetics.coordinate.model.TemplateModel templateModel    ;
    // endregion

    private void setGeneratedExcludedCellsAmount()
    {
        final int amount = org.wheatgenetics.javalib.Utils.convert(
            org.wheatgenetics.androidlibrary.Utils.getText(this.editText));

        if (null != this.templateModel)
            try
            {
                this.templateModel.setGeneratedExcludedCellsAmount(amount);// throws java.lang.Ille-
                this.cancelAlertDialog();                                  //  galArgumentException
            }
            catch (final java.lang.IllegalArgumentException e)
            {
                if (null != this.exceptionTextView)
                {
                    this.exceptionTextView.setText(e.getMessage());
                    this.exceptionTextView.setVisibility(android.view.View.VISIBLE);
                }
                if (null != this.editText) this.editText.selectAll();
            }
    }

    GeneratedExcludedCellsAlertDialog(final android.app.Activity activity) { super(activity); }

    @java.lang.Override public void configure()
    {
        this.setTitle(org.wheatgenetics.coordinate.R.string.GeneratedExcludedCellsAlertDialogTitle)
            .setCancelableToFalse();

        {
            @android.annotation.SuppressLint({"InflateParams"}) final android.view.View view =
                this.layoutInflater().inflate(
                    org.wheatgenetics.coordinate.R.layout.generated_excluded_cells,null);
            if (null != view)
            {
                if (null == this.editText) this.editText = view.findViewById(
                    org.wheatgenetics.coordinate.R.id.generatedExcludedCellsEditText);
                if (null == this.exceptionTextView) this.exceptionTextView = view.findViewById(
                    org.wheatgenetics.coordinate.R.id.generatedExcludedCellsExceptionTextView);
            }
            this.setView(view);
        }

        this.setOKPositiveButton(null).setCancelNegativeButton();
    }

    void show(final org.wheatgenetics.coordinate.model.TemplateModel templateModel)
    {
        if (null != templateModel)
        {
            this.templateModel = templateModel;

            if (null != this.editText) this.editText.selectAll();

            if (null != this.exceptionTextView)
                this.exceptionTextView.setVisibility(android.view.View.INVISIBLE);

            this.show();

            if (!this.positiveOnClickListenerHasBeenReplaced()) this.replacePositiveOnClickListener(
                new android.view.View.OnClickListener()
                {
                    @java.lang.Override public void onClick(final android.view.View view)
                    {
                        org.wheatgenetics.coordinate.tc.GeneratedExcludedCellsAlertDialog
                            .this.setGeneratedExcludedCellsAmount();
                    }
                });
        }
    }
}