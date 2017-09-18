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
    // region Fields
    private final int titleId;

    private boolean                 configured  = false;
    private android.app.AlertDialog alertDialog = null ;
    // endregion

    InternalItemsAlertDialog(final android.content.Context context, final int titleId)
    { super(context); this.titleId = titleId; }

    // region Package Methods
    void makeConfiguredTrue() { this.configured = true; }

    void show()
    {
        if (this.configured)
        {
            if (null == this.alertDialog)
            {
                if (null == this.builder) this.makeBuilder(this.titleId);
                assert null != this.builder; this.alertDialog = this.builder.create();
                assert null != this.alertDialog;
            }
            this.alertDialog.show(); this.configured = false;
        }
    }
    // endregion
}