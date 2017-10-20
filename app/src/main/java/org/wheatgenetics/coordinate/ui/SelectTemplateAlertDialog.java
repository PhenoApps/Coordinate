package org.wheatgenetics.coordinate.ui;

/**
 * Uses:
 * android.app.Activity
 * android.content.DialogInterface
 * android.content.DialogInterface.OnClickListener
 *
 * org.wheatgenetics.androidlibrary.ItemsAlertDialog
 *
 * org.wheatgenetics.coordinate.R
 */
class SelectTemplateAlertDialog extends org.wheatgenetics.androidlibrary.ItemsAlertDialog
{
    interface Handler { public abstract void select(int which); }

    private final org.wheatgenetics.coordinate.ui.SelectTemplateAlertDialog.Handler handler;

    private void select(final int which)
    { assert null != this.handler; this.handler.select(which); }

    SelectTemplateAlertDialog(final android.app.Activity activity,
    final org.wheatgenetics.coordinate.ui.SelectTemplateAlertDialog.Handler handler)
    { super(activity); this.handler = handler; }

    @java.lang.Override
    public void configure()
    {
        this.setTitle(org.wheatgenetics.coordinate.R.string.template_load);
        this.setOnClickListener(new android.content.DialogInterface.OnClickListener()
            {
                @java.lang.Override
                public void onClick(final android.content.DialogInterface dialog, final int which)
                { org.wheatgenetics.coordinate.ui.SelectTemplateAlertDialog.this.select(which); }
            });
    }
}