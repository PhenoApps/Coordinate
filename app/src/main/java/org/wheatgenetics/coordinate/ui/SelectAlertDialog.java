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
    @java.lang.SuppressWarnings("UnnecessaryInterfaceModifier")
    interface Handler { public abstract void select(int which); }

    private final org.wheatgenetics.coordinate.ui.SelectAlertDialog.Handler handler;

    private void select(final int which)
    { assert null != this.handler; this.handler.select(which); }

    SelectAlertDialog(final android.app.Activity activity,
    final org.wheatgenetics.coordinate.ui.SelectAlertDialog.Handler handler)
    { super(activity); this.handler = handler; }

    @java.lang.Override
    public void configure()
    {
        this.setOnClickListener(new android.content.DialogInterface.OnClickListener()
            {
                @java.lang.Override
                public void onClick(final android.content.DialogInterface dialog, final int which)
                { org.wheatgenetics.coordinate.ui.SelectAlertDialog.this.select(which); }
            });
    }

    public void show(final int title, final java.lang.String items[])
    { this.setTitle(title); this.show(items); }
}