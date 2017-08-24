package org.wheatgenetics.coordinate.ui;

/**
 * Uses:
 * android.app.AlertDialog
 * android.content.Context
 *
 * org.wheatgenetics.coordinate.ui.ContextAlertDialog
 */
abstract class InternalItemsAlertDialog extends org.wheatgenetics.coordinate.ui.ContextAlertDialog
{
    private final int                     titleId           ;
    private       android.app.AlertDialog alertDialog = null;

    InternalItemsAlertDialog(final android.content.Context context, final int titleId)
    { super(context); this.titleId = titleId; }

    void show()
    {
        if (null == this.alertDialog)
        {
            if (null == this.builder) this.makeBuilder(this.titleId);
            assert null != this.builder; this.alertDialog = this.builder.create();
            assert null != this.alertDialog;
        }
        this.alertDialog.show();
    }
}