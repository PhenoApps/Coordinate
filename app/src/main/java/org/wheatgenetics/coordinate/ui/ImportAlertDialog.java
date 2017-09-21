package org.wheatgenetics.coordinate.ui;

/**
 * Uses:
 * android.app.Activity
 * android.content.DialogInterface
 * android.content.DialogInterface.OnClickListener
 *
 * org.wheatgenetics.coordinate.R
 *
 * org.wheatgenetics.coordinate.ui.AlertDialog
 */
class ImportAlertDialog extends org.wheatgenetics.coordinate.ui.AlertDialog
{
    interface Handler { public abstract void importGrid(int which); }

    // region Fields
    private final org.wheatgenetics.coordinate.ui.ImportAlertDialog.Handler handler;
    private       java.lang.String                                          items[];
    // endregion

    private void importGrid(final int which)
    { assert null != this.handler; this.handler.importGrid(which); }

    ImportAlertDialog(final android.app.Activity activity,
    final org.wheatgenetics.coordinate.ui.ImportAlertDialog.Handler handler)
    { super(activity); this.handler = handler; }

    // region Overridden Methods
    @java.lang.Override
    void configureAfterConstruction()
    {
        this.setTitleId(org.wheatgenetics.coordinate.R.string.import_grid)
            .setOnClickListener(new android.content.DialogInterface.OnClickListener()
                {
                    @java.lang.Override
                    public void onClick(final android.content.DialogInterface dialog,
                    final int which)
                    {
                        org.wheatgenetics.coordinate.ui.ImportAlertDialog.this.importGrid(which);
                        // assert null != dialog; dialog.cancel(); // TODO: Remove?
                    }
                });
    }

    @java.lang.Override
    void configureBeforeShow() { this.setItems(this.items); }
    // endregion

    void show(final java.lang.String items[])
    { if (null != items) { this.items = items; this.configureAndShow(); } }
}