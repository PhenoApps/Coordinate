package org.wheatgenetics.coordinate.tc;

/**
 * Uses:
 * android.app.Activity
 * android.support.annotation.IntRange
 * android.support.annotation.NonNull
 * android.view.View
 * android.view.View.OnClickListener
 * android.widget.EditText
 *
 * org.wheatgenetics.androidlibrary.AlertDialog
 * org.wheatgenetics.androidlibrary.Utils
 *
 * org.wheatgenetics.coordinate.R
 *
 * org.wheatgenetics.coordinate.model.TemplateModel
 */
class AssignTitleRowsColsAlertDialog extends org.wheatgenetics.androidlibrary.AlertDialog
{
    // region Types
    @java.lang.SuppressWarnings({"UnnecessaryInterfaceModifier"}) interface Handler
    { public abstract void handleAssignDone(); }

    private static class Unspecified extends java.lang.Exception {}
    // endregion

    // region Fields
    @android.support.annotation.NonNull private final
        org.wheatgenetics.coordinate.tc.AssignTitleRowsColsAlertDialog.Handler handler;

    private android.widget.EditText titleEditText, rowsEditText, colsEditText;
    private org.wheatgenetics.coordinate.model.TemplateModel    templateModel;
    // endregion

    // region Private Methods
    // region convert() Private Methods
    private static java.lang.String convert(
    @android.support.annotation.IntRange(from = 1) final int integer)
    { return integer <= 0 ? "" : java.lang.String.valueOf(integer); }

    @android.support.annotation.IntRange(from = 1) private static int convert(
    final android.widget.EditText editText)
    throws org.wheatgenetics.coordinate.tc.AssignTitleRowsColsAlertDialog.Unspecified
    {
        final java.lang.String text = org.wheatgenetics.androidlibrary.Utils.getText(editText);
        if (text.length() < 1)
            throw new org.wheatgenetics.coordinate.tc.AssignTitleRowsColsAlertDialog.Unspecified();
        else
        {
            final int i = java.lang.Integer.valueOf(text);
            if (i < 1)
                throw new
                    org.wheatgenetics.coordinate.tc.AssignTitleRowsColsAlertDialog.Unspecified();
            else return i;
        }
    }
    // endregion

    private void assignTemplate()
    {
        final java.lang.String title =
            org.wheatgenetics.androidlibrary.Utils.getText(this.titleEditText);
        if (title.length() < 1)
            this.showToast(
                org.wheatgenetics.coordinate.R.string.AssignTitleRowsColsAlertDialogTitleToast);
        else
            try
            {
                final int rows =
                    org.wheatgenetics.coordinate.tc.AssignTitleRowsColsAlertDialog.convert(
                        this.rowsEditText);          // throws org.wheatgenetics.coordinate.tc
                try                                  //  .AssignTitleRowsColsAlertDialog.Unspecified
                {
                    final int cols =
                        org.wheatgenetics.coordinate.tc.AssignTitleRowsColsAlertDialog.convert(
                            this.colsEditText);      // throws org.wheatgenetics.coordinate.tc
                                                     //  .AssignTitleRowsColsAlertDialog.Unspecified
                    if (null != this.templateModel) this.templateModel.assign(
                        /* title => */ title, /* rows => */ rows, /* cols => */ cols);
                    this.cancelAlertDialog();
                    this.handler.handleAssignDone();
                }
                catch (
                final org.wheatgenetics.coordinate.tc.AssignTitleRowsColsAlertDialog.Unspecified e)
                {
                    this.showToast(org.wheatgenetics.coordinate
                        .R.string.AssignTitleRowsColsAlertDialogColsToast);
                }
            }
            catch (
            final org.wheatgenetics.coordinate.tc.AssignTitleRowsColsAlertDialog.Unspecified e)
            {
                this.showToast(
                    org.wheatgenetics.coordinate.R.string.AssignTitleRowsColsAlertDialogRowsToast);
            }
    }
    // endregion

    AssignTitleRowsColsAlertDialog(final android.app.Activity activity,
    @android.support.annotation.NonNull final
        org.wheatgenetics.coordinate.tc.AssignTitleRowsColsAlertDialog.Handler handler)
    { super(activity); this.handler = handler; }

    @java.lang.Override public void configure()
    {
        this.setTitle(org.wheatgenetics.coordinate.R.string.AssignTitleRowsColsAlertDialogTitle);

        {
            final android.view.View view =
                this.inflate(org.wheatgenetics.coordinate.R.layout.assign_title_rows_cols);

            if (null != view)
            {
                if (null == this.titleEditText) this.titleEditText =
                    view.findViewById(org.wheatgenetics.coordinate.R.id.titleEditText);
                if (null == this.rowsEditText) this.rowsEditText =
                    view.findViewById(org.wheatgenetics.coordinate.R.id.rowsEditText);
                if (null == this.colsEditText) this.colsEditText =
                    view.findViewById(org.wheatgenetics.coordinate.R.id.colsEditText);
            }
            this.setView(view);
        }

        this.setPositiveButton(org.wheatgenetics.coordinate
                .R.string.AssignTitleRowsColsAlertDialogPositiveButtonText)
            .setCancelNegativeButton();
    }

    void show(final org.wheatgenetics.coordinate.model.TemplateModel templateModel)
    {
        if (null != templateModel)
        {
            this.templateModel = templateModel;
            if (null != this.titleEditText) this.titleEditText.setText("");
            if (null != this.rowsEditText ) this.rowsEditText.setText(
                org.wheatgenetics.coordinate.tc.AssignTitleRowsColsAlertDialog.convert(
                    this.templateModel.getRows()));
            if (null != this.colsEditText) this.colsEditText.setText(
                org.wheatgenetics.coordinate.tc.AssignTitleRowsColsAlertDialog.convert(
                    this.templateModel.getCols()));
            this.show();

            if (!this.positiveOnClickListenerHasBeenReplaced()) this.replacePositiveOnClickListener(
                new android.view.View.OnClickListener()
                {
                    @java.lang.Override public void onClick(final android.view.View view)
                    {
                        org.wheatgenetics.coordinate.tc.AssignTitleRowsColsAlertDialog
                            .this.assignTemplate();
                    }
                });
        }
    }
}