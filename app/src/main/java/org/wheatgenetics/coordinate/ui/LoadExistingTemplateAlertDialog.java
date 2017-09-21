package org.wheatgenetics.coordinate.ui;

/**
 * Uses:
 * android.app.Activity
 * android.content.DialogInterface
 * android.content.DialogInterface.OnClickListener
 *
 * org.wheatgenetics.coordinate.R
 *
 * org.wheatgenetics.coordinate.ui.ItemsAlertDialog
 */
class LoadExistingTemplateAlertDialog extends org.wheatgenetics.coordinate.ui.ItemsAlertDialog
{
    interface Handler { public abstract void loadTemplate(int which); }

    private final org.wheatgenetics.coordinate.ui.LoadExistingTemplateAlertDialog.Handler handler;

    private void loadTemplate(final int which)
    { assert null != this.handler; this.handler.loadTemplate(which); }

    LoadExistingTemplateAlertDialog(final android.app.Activity activity,
    final org.wheatgenetics.coordinate.ui.LoadExistingTemplateAlertDialog.Handler handler)
    { super(activity); this.handler = handler; }

    @java.lang.Override
    void configureAfterConstruction()
    {
        this.setTitleId(org.wheatgenetics.coordinate.R.string.template_load);
        this.setOnClickListener(new android.content.DialogInterface.OnClickListener()
            {
                @java.lang.Override
                public void onClick(final android.content.DialogInterface dialog, final int which)
                {
                    org.wheatgenetics.coordinate.ui.
                        LoadExistingTemplateAlertDialog.this.loadTemplate(which);
                }
            });
    }
}