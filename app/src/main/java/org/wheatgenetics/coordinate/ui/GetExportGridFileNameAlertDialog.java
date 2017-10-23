package org.wheatgenetics.coordinate.ui;

/**
 * Uses:
 * android.app.Activity
 * android.view.View
 * android.view.View.OnClickListener
 * android.widget.EditText
 *
 * org.wheatgenetics.androidlibrary.AlertDialog
 * org.wheatgenetics.androidlibrary.Utils
 *
 * org.wheatgenetics.coordinate.R
 */
class GetExportGridFileNameAlertDialog extends org.wheatgenetics.androidlibrary.AlertDialog
{
    interface Handler { public abstract void handleGetFileNameDone(java.lang.String fileName); }

    // region Fields
    private final org.wheatgenetics.coordinate.ui.GetExportGridFileNameAlertDialog.Handler handler ;
    private       android.widget.EditText                                                  editText;
    // endregion

    private void handleGetFileNameDone()
    {
        final java.lang.String fileName =
            org.wheatgenetics.androidlibrary.Utils.getText(this.editText);
        if (0 == fileName.length())
            this.showToast(org.wheatgenetics.coordinate.R.string.filename_empty);
        else
        {
            this.cancelAlertDialog();
            assert null != this.handler; this.handler.handleGetFileNameDone(fileName);
        }
    }

    GetExportGridFileNameAlertDialog(final android.app.Activity activity,
    final org.wheatgenetics.coordinate.ui.GetExportGridFileNameAlertDialog.Handler handler)
    { super(activity); this.handler = handler; }

    @java.lang.Override
    public void configure()
    {
        this.setTitle(org.wheatgenetics.coordinate.R.string.filename_set);

        {
            final android.view.View view = this.layoutInflater().inflate(
                org.wheatgenetics.coordinate.R.layout.file_input, null);

            if (null == this.editText)
            {
                assert null != view; this.editText = (android.widget.EditText)
                    view.findViewById(org.wheatgenetics.coordinate.R.id.nameEdit);
            }

            this.setView(view);
        }

        this.setOKPositiveButton(null).setCancelNegativeButton();
    }

    void show(final java.lang.String datedFirstValue)
    {
        assert null != this.editText; this.editText.setText(datedFirstValue); this.show();
        if (!this.positiveOnClickListenerHasBeenReplaced()) this.replacePositiveOnClickListener(
            new android.view.View.OnClickListener()
            {
                @java.lang.Override
                public void onClick(final android.view.View view)
                {
                    org.wheatgenetics.coordinate.ui
                        .GetExportGridFileNameAlertDialog.this.handleGetFileNameDone();
                }
            });
    }
}