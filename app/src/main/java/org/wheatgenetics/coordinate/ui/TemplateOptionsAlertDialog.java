package org.wheatgenetics.coordinate.ui;

/**
 * Uses:
 * android.app.AlertDialog.Builder
 * android.content.Context
 * android.content.DialogInterface
 * android.content.DialogInterface.OnClickListener
 *
 * org.wheatgenetics.coordinate.R
 * org.wheatgenetics.coordinate.ui.InternalItemsAlertDialog
 */
class TemplateOptionsAlertDialog extends org.wheatgenetics.coordinate.ui.InternalItemsAlertDialog
{
    interface Handler
    { public abstract void loadExistingTemplate(); public abstract void createNewTemplate(); }

    private final org.wheatgenetics.coordinate.ui.TemplateOptionsAlertDialog.Handler handler;

    private void choose(final int which)
    {
        assert null != this.handler; switch (which)
        {
            case 0: this.handler.loadExistingTemplate(); break;
            case 1: this.handler.createNewTemplate   (); break;
        }
    }

    TemplateOptionsAlertDialog(final android.content.Context context,
    final org.wheatgenetics.coordinate.ui.TemplateOptionsAlertDialog.Handler handler)
    {
        super(context, org.wheatgenetics.coordinate.R.string.template_options);
        this.handler = handler;
    }

    @java.lang.Override
    android.app.AlertDialog.Builder makeBuilder(final int titleId)
    {
        return super.makeBuilder(titleId).setItems(new java.lang.String[] {
                this.getString(org.wheatgenetics.coordinate.R.string.template_load),
                this.getString(org.wheatgenetics.coordinate.R.string.template_new )},
            new android.content.DialogInterface.OnClickListener()
            {
                @java.lang.Override
                public void onClick(final android.content.DialogInterface dialog, final int which)
                { org.wheatgenetics.coordinate.ui.TemplateOptionsAlertDialog.this.choose(which); }
            });
    }
}