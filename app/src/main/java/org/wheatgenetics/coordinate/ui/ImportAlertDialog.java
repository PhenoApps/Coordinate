package org.wheatgenetics.coordinate.ui;

/**
 * Uses:
 * android.app.Activity
 * android.content.DialogInterface
 * android.content.DialogInterface.OnClickListener
 *
 * org.wheatgenetics.coordinate.R
 *
 * org.wheatgenetics.coordinate.ui.ItemsAlertDialog
 */
class ImportAlertDialog extends org.wheatgenetics.coordinate.ui.ItemsAlertDialog
{
    interface Handler { public abstract void importGrid(int which); }

    private final org.wheatgenetics.coordinate.ui.ImportAlertDialog.Handler handler;

    private void importGrid(final int which)
    { assert null != this.handler; this.handler.importGrid(which); }

    ImportAlertDialog(final android.app.Activity activity,
    final org.wheatgenetics.coordinate.ui.ImportAlertDialog.Handler handler)
    { super(activity); this.handler = handler; }

    @java.lang.Override
    void configure()
    {
        this.setTitle(org.wheatgenetics.coordinate.R.string.import_grid);
        this.setOnClickListener(new android.content.DialogInterface.OnClickListener()
            {
                @java.lang.Override
                public void onClick(final android.content.DialogInterface dialog, final int which)
                {
                    org.wheatgenetics.coordinate.ui.ImportAlertDialog.this.importGrid(which);
                    // assert null != dialog; dialog.cancel(); // TODO: Remove?
                }
            });
    }
}