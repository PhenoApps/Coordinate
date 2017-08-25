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
 * org.wheatgenetics.coordinate.ui.ShowingAlertDialog
 */
class ExportAlertDialog extends org.wheatgenetics.coordinate.ui.ShowingAlertDialog
{
    interface Handler { public abstract void exportGrid(java.lang.String fileName); }

    // region Fields
    private final org.wheatgenetics.coordinate.ui.ExportAlertDialog.Handler handler        ;
    private       android.widget.EditText                                   editText = null;
    // endregion

    private void exportGrid()
    {
        assert null != this.editText; assert null != this.handler;
        this.handler.exportGrid(this.editText.getText().toString().trim());
    }

    ExportAlertDialog(final android.app.Activity activity,
    final org.wheatgenetics.coordinate.ui.ExportAlertDialog.Handler handler)
    { super(activity); this.handler = handler; }

    @java.lang.Override
    android.app.AlertDialog.Builder configureOnce(final int titleId)
    {
        super.configureOnce(titleId);

        {
            final android.view.View view = this.layoutInflater().inflate(
                org.wheatgenetics.coordinate.R.layout.file_input, null);

            if (null == this.editText)
            {
                assert null != view; this.editText = (android.widget.EditText)
                    view.findViewById(org.wheatgenetics.coordinate.R.id.nameEdit);
            }

            assert null != this.builder; this.builder.setView(view);
        }

        return this.builder.setPositiveButton(org.wheatgenetics.coordinate.R.string.ok,
                new android.content.DialogInterface.OnClickListener()
                {
                    @java.lang.Override
                    public void onClick(final android.content.DialogInterface dialog,
                    final int which)
                    {
                        assert null != dialog; dialog.cancel();
                        org.wheatgenetics.coordinate.ui.ExportAlertDialog.this.exportGrid();
                    }
                })
            .setNegativeButton(org.wheatgenetics.coordinate.R.string.cancel,
                org.wheatgenetics.androidlibrary.Utils.cancellingOnClickListener());
    }

    void show(final java.lang.String datedFirstValue)
    {
        this.configureOnce(org.wheatgenetics.coordinate.R.string.filename_set);
        assert null != this.editText; this.editText.setText(datedFirstValue);
        this.show();
    }
}