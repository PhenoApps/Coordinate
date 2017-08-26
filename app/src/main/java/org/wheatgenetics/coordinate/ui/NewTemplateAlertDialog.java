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
 * org.wheatgenetics.coordinate.R
 * org.wheatgenetics.coordinate.ui.ShowingAlertDialog
 * org.wheatgenetics.coordinate.ui.Utils
 */
class NewTemplateAlertDialog extends org.wheatgenetics.coordinate.ui.ShowingAlertDialog
{
    interface Handler
    {
        public abstract void createNewTemplate(java.lang.String name, java.lang.String rows,
            java.lang.String cols);
    }

    // region Fields
    private final org.wheatgenetics.coordinate.ui.NewTemplateAlertDialog.Handler handler;
    private       android.widget.EditText nameTextEdit = null, rowsTextEdit = null,
        colsTextEdit = null;
    // endregion

    private void createNewTemplate()
    {
        assert null != this.handler; this.handler.createNewTemplate(
            org.wheatgenetics.coordinate.ui.Utils.getText(this.nameTextEdit),
            org.wheatgenetics.coordinate.ui.Utils.getText(this.colsTextEdit),
            org.wheatgenetics.coordinate.ui.Utils.getText(this.rowsTextEdit));
    }

    NewTemplateAlertDialog(final android.app.Activity activity,
    final org.wheatgenetics.coordinate.ui.NewTemplateAlertDialog.Handler handler)
    { super(activity); this.handler = handler; }

    @java.lang.Override
    android.app.AlertDialog.Builder configure(final int titleId)
    {
        super.configure(titleId);

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

    void show(final java.lang.String rows, final java.lang.String cols)
    {
        this.configure(org.wheatgenetics.coordinate.R.string.template_new);

        assert null != this.nameTextEdit; this.nameTextEdit.setText(""  );
        assert null != this.rowsTextEdit; this.rowsTextEdit.setText(rows);
        assert null != this.colsTextEdit; this.colsTextEdit.setText(cols);
        this.show();
    }
}