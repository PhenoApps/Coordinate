package org.wheatgenetics.coordinate.ui;

/**
 * Uses:
 * android.app.Activity
 * android.content.DialogInterface
 * android.content.DialogInterface.OnClickListener
 *
 * org.wheatgenetics.androidlibrary.ItemsAlertDialog
 */
class SelectAlertDialog extends org.wheatgenetics.androidlibrary.ItemsAlertDialog
{
    interface Handler { public abstract void select(int which); }

    // region Fields
    private final org.wheatgenetics.coordinate.ui.SelectAlertDialog.Handler handler;
    private final int                                                       title  ;
    // endregion

    private void select(final int which)
    { assert null != this.handler; this.handler.select(which); }

    SelectAlertDialog(final android.app.Activity activity, final int title,
    final org.wheatgenetics.coordinate.ui.SelectAlertDialog.Handler handler)
    { super(activity); this.title = title; this.handler = handler; }

    @java.lang.Override
    public void configure()
    {
        this.setTitle(this.title);
        this.setOnClickListener(new android.content.DialogInterface.OnClickListener()
            {
                @java.lang.Override
                public void onClick(final android.content.DialogInterface dialog, final int which)
                { org.wheatgenetics.coordinate.ui.SelectAlertDialog.this.select(which); }
            });
    }
}