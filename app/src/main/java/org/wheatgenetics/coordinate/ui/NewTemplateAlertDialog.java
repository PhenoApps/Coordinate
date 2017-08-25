package org.wheatgenetics.coordinate.ui;

/**
 * Uses:
 * android.app.Activity
 * android.app.AlertDialog
 * android.app.AlertDialog.Builder
 * android.content.DialogInterface
 * android.content.DialogInterface.OnClickListener
 * android.view.LayoutInflater
 * android.view.View
 * android.widget.EditText
 * android.widget.LinearLayout
 *
 * org.wheatgenetics.androidlibrary.Utils
 *
 * org.wheatgenetics.coordinate.R
 */
class NewTemplateAlertDialog extends java.lang.Object
{
    interface Handler
    {
        public abstract void createNewTemplate(java.lang.String name, java.lang.String rows,
            java.lang.String cols);
    }

    // region Fields
    private final android.app.Activity                                           activity;
    private final org.wheatgenetics.coordinate.ui.NewTemplateAlertDialog.Handler handler ;

    private android.app.AlertDialog         alertDialog  = null;
    private android.app.AlertDialog.Builder builder      = null;
    private android.widget.EditText nameTextEdit = null, rowsTextEdit = null, colsTextEdit = null;
    // endregion

    private void createNewTemplate()
    {
        assert null != this.nameTextEdit; assert null != this.colsTextEdit;
        assert null != this.rowsTextEdit; assert null != this.handler     ;
        this.handler.createNewTemplate(
            this.nameTextEdit.getText().toString().trim(),
            this.colsTextEdit.getText().toString().trim(),
            this.rowsTextEdit.getText().toString().trim());
    }

    NewTemplateAlertDialog(final android.app.Activity activity,
    final org.wheatgenetics.coordinate.ui.NewTemplateAlertDialog.Handler handler)
    { super(); this.activity = activity; this.handler = handler; }

    void show(final java.lang.String rows, final java.lang.String cols)
    {
        if (null == this.alertDialog)
        {
            if (null == this.builder)
            {
                this.builder = new android.app.AlertDialog.Builder(this.activity);
                this.builder.setTitle(org.wheatgenetics.coordinate.R.string.template_new);
                {
                    android.view.View view;
                    {
                        assert null != this.activity;
                        final android.view.LayoutInflater layoutInflater =
                            this.activity.getLayoutInflater();
                        view = layoutInflater.inflate(
                            org.wheatgenetics.coordinate.R.layout.template_new,
                            new android.widget.LinearLayout(this.activity), false);
                    }

                    assert null != view;
                    if (null == this.nameTextEdit) this.nameTextEdit = (android.widget.EditText)
                        view.findViewById(org.wheatgenetics.coordinate.R.id.nameEdit);
                    if (null == this.rowsTextEdit) this.rowsTextEdit = (android.widget.EditText)
                        view.findViewById(org.wheatgenetics.coordinate.R.id.rowsEdit);
                    if (null == this.colsTextEdit) this.colsTextEdit = (android.widget.EditText)
                        view.findViewById(org.wheatgenetics.coordinate.R.id.colsEdit);

                    this.builder.setView(view);
                }
                this.builder.setPositiveButton(org.wheatgenetics.coordinate.R.string.next,
                        new android.content.DialogInterface.OnClickListener()
                        {
                            @java.lang.Override
                            public void onClick(final android.content.DialogInterface dialog,
                            final int which)
                            {
                                assert null != dialog; dialog.cancel();
                                org.wheatgenetics.coordinate.ui.
                                    NewTemplateAlertDialog.this.createNewTemplate();
                            }
                        })
                    .setNegativeButton(org.wheatgenetics.coordinate.R.string.cancel,
                        org.wheatgenetics.androidlibrary.Utils.cancellingOnClickListener()).show();
            }
            this.alertDialog = this.builder.create();
            assert null != this.alertDialog;
        }

        assert null != this.nameTextEdit; this.nameTextEdit.setText(""  );
        assert null != this.rowsTextEdit; this.rowsTextEdit.setText(rows);
        assert null != this.colsTextEdit; this.colsTextEdit.setText(cols);

        this.alertDialog.show();
    }
}