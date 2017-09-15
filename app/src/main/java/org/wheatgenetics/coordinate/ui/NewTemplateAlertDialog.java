package org.wheatgenetics.coordinate.ui;

/**
 * Uses:
 * android.app.Activity
 * android.app.AlertDialog.Builder
 * android.content.DialogInterface
 * android.content.DialogInterface.OnClickListener
 * android.view.View
 * android.widget.EditText
 *
 * org.wheatgenetics.androidlibrary.Utils
 *
 * org.wheatgenetics.coordinate.R
 *
 * org.wheatgenetics.coordinate.ui.ShowingAlertDialog
 */
class NewTemplateAlertDialog extends org.wheatgenetics.coordinate.ui.ShowingAlertDialog
{
    // region Types
    interface Handler
    {
        public abstract void handleEmptyName      (java.lang.String message);
        public abstract void handleUnspecifiedRows(java.lang.String message);
        public abstract void handleUnspecifiedCols(java.lang.String message);
        public abstract void createNewTemplate    (java.lang.String name, int rows, int cols);
    }
    private static class Unspecified extends java.lang.Exception {};
    // endregion

    // region Fields
    private final org.wheatgenetics.coordinate.ui.NewTemplateAlertDialog.Handler handler;
    private       android.widget.EditText nameTextEdit = null, rowsTextEdit = null,
        colsTextEdit = null;
    // endregion

    // region Private Methods
    private static java.lang.String convert(final int integer)
    { return integer <= 0 ? "" : java.lang.String.valueOf(integer); }

    private static int convert(final java.lang.String string)
    throws org.wheatgenetics.coordinate.ui.NewTemplateAlertDialog.Unspecified
    {
        if (0 == string.length())
            throw new org.wheatgenetics.coordinate.ui.NewTemplateAlertDialog.Unspecified();
        else
        {
            final int i = java.lang.Integer.valueOf(string);
            if (i < 1)
                throw new org.wheatgenetics.coordinate.ui.NewTemplateAlertDialog.Unspecified();
            else
                return i;
        }
    }

    private void createNewTemplate()
    {
        assert null != this.handler;

        final java.lang.String name =
            org.wheatgenetics.androidlibrary.Utils.getText(this.nameTextEdit);
        if (0 == name.length())
            this.handler.handleEmptyName(this.getString(
                org.wheatgenetics.coordinate.R.string.template_no_name));
        else
        {
            try
            {
                final int rows = org.wheatgenetics.coordinate.ui.NewTemplateAlertDialog.convert(
                    org.wheatgenetics.androidlibrary.Utils.getText(this.rowsTextEdit));
                try
                {
                    final int cols = org.wheatgenetics.coordinate.ui.NewTemplateAlertDialog.convert(
                        org.wheatgenetics.androidlibrary.Utils.getText(this.colsTextEdit));
                    this.handler.createNewTemplate(name, rows, cols);
                }
                catch (final org.wheatgenetics.coordinate.ui.NewTemplateAlertDialog.Unspecified e)
                {
                    this.handler.handleUnspecifiedCols(this.getString(
                        org.wheatgenetics.coordinate.R.string.no_cols));
                }
            }
            catch (final org.wheatgenetics.coordinate.ui.NewTemplateAlertDialog.Unspecified e)
            {
                this.handler.handleUnspecifiedRows(this.getString(
                    org.wheatgenetics.coordinate.R.string.no_rows));
            }
        }
    }
    // endregion

    NewTemplateAlertDialog(final android.app.Activity activity,
    final org.wheatgenetics.coordinate.ui.NewTemplateAlertDialog.Handler handler)
    { super(activity); this.handler = handler; }

    @java.lang.Override
    android.app.AlertDialog.Builder makeBuilder(final int titleId)
    {
        super.makeBuilder(titleId);

        {
            final android.view.View view =
                this.inflate(org.wheatgenetics.coordinate.R.layout.template_new);

            assert null != view;
            if (null == this.nameTextEdit) this.nameTextEdit = (android.widget.EditText)
                view.findViewById(org.wheatgenetics.coordinate.R.id.nameEdit);
            if (null == this.rowsTextEdit) this.rowsTextEdit = (android.widget.EditText)
                view.findViewById(org.wheatgenetics.coordinate.R.id.rowsEdit);
            if (null == this.colsTextEdit) this.colsTextEdit = (android.widget.EditText)
                view.findViewById(org.wheatgenetics.coordinate.R.id.colsEdit);

            this.setView(view);
        }

        assert null != this.builder;
        this.builder.setPositiveButton(org.wheatgenetics.coordinate.R.string.next,
            new android.content.DialogInterface.OnClickListener()
            {
                @java.lang.Override
                public void onClick(final android.content.DialogInterface dialog, final int which)
                {
                    assert null != dialog; dialog.cancel();
                    org.wheatgenetics.coordinate.ui.NewTemplateAlertDialog.this.createNewTemplate();
                }
            });

        return this.setNegativeButton();
    }

    void show(final int rows, final int cols)
    {
        this.configure(org.wheatgenetics.coordinate.R.string.template_new);

        assert null != this.nameTextEdit; this.nameTextEdit.setText("");
        assert null != this.rowsTextEdit; this.rowsTextEdit.setText(
            org.wheatgenetics.coordinate.ui.NewTemplateAlertDialog.convert(rows));
        assert null != this.colsTextEdit; this.colsTextEdit.setText(
            org.wheatgenetics.coordinate.ui.NewTemplateAlertDialog.convert(cols));
        this.show();
    }
}