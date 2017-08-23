package org.wheatgenetics.coordinate.ui;

/**
 * Uses:
 * android.app.AlertDialog
 * android.app.AlertDialog.Builder
 * android.content.Context
 * android.content.DialogInterface
 * android.content.DialogInterface.OnClickListener
 *
 * org.wheatgenetics.coordinate.R
 */
class TemplateOptionsAlertDialog extends java.lang.Object
{
    interface Handler
    { public abstract void loadExistingTemplate(); public abstract void createNewTemplate(); }

    // region Fields
    private final android.content.Context                                            context;
    private final org.wheatgenetics.coordinate.ui.TemplateOptionsAlertDialog.Handler handler;

    private android.app.AlertDialog         alertDialog  = null;
    private android.app.AlertDialog.Builder builder      = null;
    // endregion

    private void onClick(final int which)
    {
        assert null != this.handler; switch (which)
        {
            case 0: this.handler.loadExistingTemplate(); break;
            case 1: this.handler.createNewTemplate   (); break;
        }
    }

    TemplateOptionsAlertDialog(final android.content.Context context,
    final org.wheatgenetics.coordinate.ui.TemplateOptionsAlertDialog.Handler handler)
    { super(); this.context = context; this.handler = handler; }

    void show()
    {
        if (null == this.alertDialog)
        {
            if (null == this.builder)
            {
                this.builder = new android.app.AlertDialog.Builder(this.context);
                assert null != this.context;
                this.builder.setTitle(org.wheatgenetics.coordinate.R.string.template_options)
                    .setItems(new java.lang.String[] {
                            this.context.getString(
                                org.wheatgenetics.coordinate.R.string.template_load),
                            this.context.getString(
                                org.wheatgenetics.coordinate.R.string.template_new)},
                        new android.content.DialogInterface.OnClickListener()
                        {
                            @java.lang.Override
                            public void onClick(final android.content.DialogInterface dialog,
                            final int which)
                            {
                                org.wheatgenetics.coordinate.ui.
                                    TemplateOptionsAlertDialog.this.onClick(which);
                            }
                        });
            }
            this.alertDialog = this.builder.create();
            assert null != this.alertDialog;
        }
        this.alertDialog.show();
    }
}