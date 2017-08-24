package org.wheatgenetics.coordinate.ui;

/**
 * Uses:
 * android.content.Context
 * android.content.DialogInterface
 * android.content.DialogInterface.OnClickListener
 *
 * org.wheatgenetics.coordinate.R
 * org.wheatgenetics.coordinate.ui.ExternalItemsAlertDialog
 */
class DeleteTemplateAlertDialog extends org.wheatgenetics.coordinate.ui.ExternalItemsAlertDialog
{
    interface Handler { public abstract void deleteTemplate(int which); }

    private final org.wheatgenetics.coordinate.ui.DeleteTemplateAlertDialog.Handler handler;

    // region Private Methods
    private void deleteTemplate(final int i)
    { assert null != this.handler; this.handler.deleteTemplate(i); }

    @java.lang.Override
    android.content.DialogInterface.OnClickListener makeOnClickListener()
    {
        return new android.content.DialogInterface.OnClickListener()
            {
                @java.lang.Override
                public void onClick(final android.content.DialogInterface dialog, final int which)
                {
                    org.wheatgenetics.coordinate.ui.DeleteTemplateAlertDialog.this.deleteTemplate(
                        which);
                }
            };
    }
    // endregion

    DeleteTemplateAlertDialog(final android.content.Context context,
    final org.wheatgenetics.coordinate.ui.DeleteTemplateAlertDialog.Handler handler)
    {
        super(context, org.wheatgenetics.coordinate.R.string.delete_template);
        this.handler = handler;
    }
}