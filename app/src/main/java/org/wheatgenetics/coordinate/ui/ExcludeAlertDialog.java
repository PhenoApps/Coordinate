package org.wheatgenetics.coordinate.ui;

/**
 * Uses:
 * android.app.AlertDialog
 * android.app.AlertDialog.Builder
 * android.content.Context
 * android.content.DialogInterface
 * android.content.DialogInterface.OnClickListener
 *
 * org.wheatgenetics.androidlibrary.Utils
 *
 * org.wheatgenetics.coordinate.R
 */
class ExcludeAlertDialog extends java.lang.Object
{
    interface Handler
    {
        public abstract void excludeRows ();
        public abstract void excludeCols ();
        public abstract void excludeCells();
    }

    // region Fields
    private final android.content.Context                                    context;
    private final org.wheatgenetics.coordinate.ui.ExcludeAlertDialog.Handler handler;

    private android.app.AlertDialog         alertDialog  = null;
    private android.app.AlertDialog.Builder builder      = null;
    // endregion

    private void exclude(final int which)
    {
        assert null != this.handler;
        switch (which)
        {
            case 0: this.handler.excludeRows (); break;
            case 1: this.handler.excludeCols (); break;
            case 2: this.handler.excludeCells(); break;
        }
    }

    ExcludeAlertDialog(final android.content.Context context,
    final org.wheatgenetics.coordinate.ui.ExcludeAlertDialog.Handler handler)
    { super(); this.context = context; this.handler = handler; }

    void show()
    {
        if (null == this.alertDialog)
        {
            if (null == this.builder)
            {
                this.builder = new android.app.AlertDialog.Builder(this.context);
                this.builder.setTitle(org.wheatgenetics.coordinate.R.string.exclude_title)
                    .setNegativeButton(org.wheatgenetics.coordinate.R.string.cancel       ,
                        org.wheatgenetics.androidlibrary.Utils.cancellingOnClickListener());

                assert null != this.context;
                final java.lang.String items[] = {
                    this.context.getString(org.wheatgenetics.coordinate.R.string.rows  ),
                    this.context.getString(org.wheatgenetics.coordinate.R.string.cols  ),
                    this.context.getString(org.wheatgenetics.coordinate.R.string.random)};
                this.builder.setItems(items, new android.content.DialogInterface.OnClickListener()
                    {
                        @java.lang.Override
                        public void onClick(final android.content.DialogInterface dialog,
                        final int which)
                        {
                            org.wheatgenetics.coordinate.ui.ExcludeAlertDialog.this.exclude(which);
                        }
                    });
            }
            this.alertDialog = this.builder.create();
            assert null != this.alertDialog;
        }
        this.alertDialog.show();
    }
}