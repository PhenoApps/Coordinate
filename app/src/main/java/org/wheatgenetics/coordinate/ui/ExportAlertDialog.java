package org.wheatgenetics.coordinate.ui;

/**
 * Uses:
 * android.app.Activity
 * android.content.DialogInterface
 * android.content.DialogInterface.OnClickListener
 * android.view.View
 * android.widget.EditText
 *
 * org.wheatgenetics.androidlibrary.AlertDialog
 * org.wheatgenetics.androidlibrary.Utils
 *
 * org.wheatgenetics.coordinate.R
 */
class ExportAlertDialog extends org.wheatgenetics.androidlibrary.AlertDialog
{
    interface Handler { public abstract void exportGrid(java.lang.String fileName); }

    // region Fields
    private final org.wheatgenetics.coordinate.ui.ExportAlertDialog.Handler handler ;
    private       android.widget.EditText                                   editText;
    // endregion

    private void exportGrid()
    {
        assert null != this.handler;
        this.handler.exportGrid(org.wheatgenetics.androidlibrary.Utils.getText(this.editText));
    }

    ExportAlertDialog(final android.app.Activity activity,
    final org.wheatgenetics.coordinate.ui.ExportAlertDialog.Handler handler)
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

        this.setOKPositiveButton(new android.content.DialogInterface.OnClickListener()
            {
                @java.lang.Override
                public void onClick(final android.content.DialogInterface dialog, final int which)
                { org.wheatgenetics.coordinate.ui.ExportAlertDialog.this.exportGrid(); }
            }).setNegativeButton();
    }

    void show(final java.lang.String datedFirstValue)
    { assert null != this.editText; this.editText.setText(datedFirstValue); this.show(); }
}