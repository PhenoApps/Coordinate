package org.wheatgenetics.coordinate.ui;

/**
 * Uses:
 * android.app.AlertDialog.Builder
 * android.content.Context
 * android.content.DialogInterface
 * android.content.DialogInterface.OnClickListener
 *
 * org.wheatgenetics.coordinate.R
 */
public class ImportAlertDialog extends java.lang.Object
{
    interface Handler { public abstract void importGrid(int i); }

    // region Fields
    private final android.content.Context                                   context;
    private final org.wheatgenetics.coordinate.ui.ImportAlertDialog.Handler handler;

    private android.app.AlertDialog.Builder                 builder                 = null;
    private android.content.DialogInterface.OnClickListener onClickListenerInstance = null;
    // endregion

    // region Private Methods
    private void importGrid(final int i)
    { assert null != this.handler; this.handler.importGrid(i); }

    private android.content.DialogInterface.OnClickListener onClickListener()
    {
        if (null == this.onClickListenerInstance) this.onClickListenerInstance =
            new android.content.DialogInterface.OnClickListener()
            {
                @java.lang.Override
                public void onClick(final android.content.DialogInterface dialogInterface,
                final int i)
                {
                    org.wheatgenetics.coordinate.ui.ImportAlertDialog.this.importGrid(i);
                    assert null != dialogInterface; dialogInterface.cancel();
                }
            };
        return this.onClickListenerInstance;
    }
    // endregion

    ImportAlertDialog(final android.content.Context context,
    final org.wheatgenetics.coordinate.ui.ImportAlertDialog.Handler handler)
    { super(); this.context = context; this.handler = handler; }

    void show(final java.lang.CharSequence items[])
    {
        if (null == this.builder)
        {
            this.builder = new android.app.AlertDialog.Builder(this.context);
            this.builder.setTitle(org.wheatgenetics.coordinate.R.string.import_grid);
        }
        this.builder.setItems(items, this.onClickListener());
        this.builder.create().show();
    }
}