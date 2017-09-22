package org.wheatgenetics.coordinate.ui;

/**
 * Uses:
 * android.app.Activity
 * android.content.DialogInterface
 * android.content.DialogInterface.OnClickListener
 *
 * org.wheatgenetics.androidlibrary.AlertDialog
 *
 * org.wheatgenetics.coordinate.R
 */
class TemplateOptionsAlertDialog extends org.wheatgenetics.androidlibrary.AlertDialog
{
    interface Handler
    { public abstract void loadExistingTemplate(); public abstract void createTemplate(); }

    private final org.wheatgenetics.coordinate.ui.TemplateOptionsAlertDialog.Handler handler;

    private void choose(final int which)
    {
        assert null != this.handler; switch (which)
        {
            case 0: this.handler.loadExistingTemplate(); break;
            case 1: this.handler.createTemplate      (); break;
        }
    }

    TemplateOptionsAlertDialog(final android.app.Activity activity,
    final org.wheatgenetics.coordinate.ui.TemplateOptionsAlertDialog.Handler handler)
    { super(activity); this.handler = handler; }

    @java.lang.Override
    public void configure()
    {
        this.setTitle(org.wheatgenetics.coordinate.R.string.template_options)
            .setItems(new int[] {
                    org.wheatgenetics.coordinate.R.string.template_load,
                    org.wheatgenetics.coordinate.R.string.template_new },
                new android.content.DialogInterface.OnClickListener()
                {
                    @java.lang.Override
                    public void onClick(final android.content.DialogInterface dialog,
                    final int which)
                    {
                        org.wheatgenetics.coordinate.ui.
                            TemplateOptionsAlertDialog.this.choose(which);
                    }
                });
    }
}