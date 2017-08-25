package org.wheatgenetics.coordinate.ui;

/**
 * Uses:
 * android.app.AlertDialog.Builder
 * android.content.Context
 * android.content.DialogInterface
 * android.content.DialogInterface.OnClickListener
 *
 * org.wheatgenetics.androidlibrary.Utils
 *
 * org.wheatgenetics.coordinate.R
 * org.wheatgenetics.coordinate.ui.InternalItemsAlertDialog
 */
class ExcludeAlertDialog extends org.wheatgenetics.coordinate.ui.InternalItemsAlertDialog
{
    interface Handler
    {
        public abstract void excludeRows ();
        public abstract void excludeCols ();
        public abstract void excludeCells();
    }

    private final org.wheatgenetics.coordinate.ui.ExcludeAlertDialog.Handler handler;

    private void exclude(final int which)
    {
        assert null != this.handler; switch (which)
        {
            case 0: this.handler.excludeRows (); break;
            case 1: this.handler.excludeCols (); break;
            case 2: this.handler.excludeCells(); break;
        }
    }

    ExcludeAlertDialog(final android.content.Context context,
    final org.wheatgenetics.coordinate.ui.ExcludeAlertDialog.Handler handler)
    { super(context, org.wheatgenetics.coordinate.R.string.exclude_title); this.handler = handler; }

    @java.lang.Override
    android.app.AlertDialog.Builder makeBuilder(final int titleId)
    {
        return super.makeBuilder(titleId).setItems(new java.lang.String[] {
                this.getString(org.wheatgenetics.coordinate.R.string.rows  ),
                this.getString(org.wheatgenetics.coordinate.R.string.cols  ),
                this.getString(org.wheatgenetics.coordinate.R.string.random)},
            new android.content.DialogInterface.OnClickListener()
            {
                @java.lang.Override
                public void onClick(final android.content.DialogInterface dialog, final int which)
                { org.wheatgenetics.coordinate.ui.ExcludeAlertDialog.this.exclude(which); }
            }).setNegativeButton(org.wheatgenetics.coordinate.R.string.cancel     ,
                org.wheatgenetics.androidlibrary.Utils.cancellingOnClickListener());
    }
}