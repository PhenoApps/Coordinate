package org.wheatgenetics.coordinate.ui;

/**
 * Uses:
 * android.content.Context
 * android.content.DialogInterface
 * android.content.DialogInterface.OnClickListener
 *
 * org.wheatgenetics.coordinate.R
 *
 * org.wheatgenetics.coordinate.ui.ExternalItemsAlertDialog
 */
class ImportAlertDialog extends org.wheatgenetics.coordinate.ui.ExternalItemsAlertDialog
{
    interface Handler { public abstract void importGrid(int which); }

    private final org.wheatgenetics.coordinate.ui.ImportAlertDialog.Handler handler;

    private void importGrid(final int which)
    { assert null != this.handler; this.handler.importGrid(which); }

    ImportAlertDialog(final android.content.Context context,
    final org.wheatgenetics.coordinate.ui.ImportAlertDialog.Handler handler)
    { super(context, org.wheatgenetics.coordinate.R.string.import_grid); this.handler = handler; }

    @java.lang.Override
    android.content.DialogInterface.OnClickListener makeOnClickListener()
    {
        return new android.content.DialogInterface.OnClickListener()
            {
                @java.lang.Override
                public void onClick(final android.content.DialogInterface dialog, final int which)
                {
                    org.wheatgenetics.coordinate.ui.ImportAlertDialog.this.importGrid(which);
                    assert null != dialog; dialog.cancel();
                }
            };
    }
}