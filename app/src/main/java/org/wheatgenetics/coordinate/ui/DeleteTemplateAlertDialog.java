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
class DeleteTemplateAlertDialog extends org.wheatgenetics.androidlibrary.ItemsAlertDialog
{
    interface Handler { public abstract void deleteTemplate(int which); }

    private final org.wheatgenetics.coordinate.ui.DeleteTemplateAlertDialog.Handler handler;

    private void deleteTemplate(final int which)
    { assert null != this.handler; this.handler.deleteTemplate(which); }

    DeleteTemplateAlertDialog(final android.app.Activity activity,
    final org.wheatgenetics.coordinate.ui.DeleteTemplateAlertDialog.Handler handler)
    { super(activity); this.handler = handler; }

    @java.lang.Override
    public void configure()
    {
        this.setTitle(org.wheatgenetics.coordinate.R.string.delete_template);
        this.setOnClickListener(new android.content.DialogInterface.OnClickListener()
            {
                @java.lang.Override
                public void onClick(final android.content.DialogInterface dialog, final int which)
                {
                    org.wheatgenetics.coordinate.ui.
                        DeleteTemplateAlertDialog.this.deleteTemplate(which);
                }
            });
    }
}