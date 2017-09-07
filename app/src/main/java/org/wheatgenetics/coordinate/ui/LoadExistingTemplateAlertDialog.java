package org.wheatgenetics.coordinate.ui;

/**
 * Uses:
 * android.content.Context
 * android.content.DialogInterface
 * android.content.DialogInterface.OnClickListener
 *
 * org.wheatgenetics.coordinate.R
 *
 * org.wheatgenetics.coordinate.ui.ExternalItemsAlertDialog
 */
class LoadExistingTemplateAlertDialog
extends org.wheatgenetics.coordinate.ui.ExternalItemsAlertDialog
{
    interface Handler { public abstract void loadTemplate(int which); }

    private final org.wheatgenetics.coordinate.ui.LoadExistingTemplateAlertDialog.Handler handler;

    private void loadTemplate(final int which)
    { assert null != this.handler; this.handler.loadTemplate(which); }

    LoadExistingTemplateAlertDialog(final android.content.Context context,
    final org.wheatgenetics.coordinate.ui.LoadExistingTemplateAlertDialog.Handler handler)
    { super(context, org.wheatgenetics.coordinate.R.string.template_load); this.handler = handler; }

    @java.lang.Override
    android.content.DialogInterface.OnClickListener makeOnClickListener()
    {
        return new android.content.DialogInterface.OnClickListener()
            {
                @java.lang.Override
                public void onClick(final android.content.DialogInterface dialog, final int which)
                {
                    org.wheatgenetics.coordinate.ui.
                        LoadExistingTemplateAlertDialog.this.loadTemplate(which);
                }
            };
    }
}