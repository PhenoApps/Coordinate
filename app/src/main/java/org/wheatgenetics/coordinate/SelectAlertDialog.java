package org.wheatgenetics.coordinate;

/**
 * Uses:
 * android.app.Activity
 * android.content.DialogInterface
 * android.content.DialogInterface.OnClickListener
 * android.support.annotation.StringRes
 *
 * org.wheatgenetics.androidlibrary.ItemsAlertDialog
 */
public class SelectAlertDialog extends org.wheatgenetics.androidlibrary.ItemsAlertDialog
{
    @java.lang.SuppressWarnings({"UnnecessaryInterfaceModifier"})
    public interface Handler { public abstract void select(int which); }

    private final org.wheatgenetics.coordinate.SelectAlertDialog.Handler handler;

    private void select(final int which)
    { assert null != this.handler; this.handler.select(which); }

    public SelectAlertDialog(final android.app.Activity activity,
    final org.wheatgenetics.coordinate.SelectAlertDialog.Handler handler)
    { super(activity); this.handler = handler; }

    @java.lang.Override public void configure()
    {
        this.setOnClickListener(new android.content.DialogInterface.OnClickListener()
            {
                @java.lang.Override
                public void onClick(final android.content.DialogInterface dialog, final int which)
                { org.wheatgenetics.coordinate.SelectAlertDialog.this.select(which); }
            });
    }

    public void show(@android.support.annotation.StringRes final int title,
    final java.lang.String items[]) { this.setTitle(title); this.show(items); }
}