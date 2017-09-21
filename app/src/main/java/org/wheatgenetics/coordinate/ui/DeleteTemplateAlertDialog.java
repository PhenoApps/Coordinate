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
class DeleteTemplateAlertDialog extends org.wheatgenetics.coordinate.ui.ItemsAlertDialog
{
    interface Handler { public abstract void deleteTemplate(int which); }

    // region Fields
    private final org.wheatgenetics.coordinate.ui.DeleteTemplateAlertDialog.Handler handler;
    private       java.lang.String                                                  items[];
    // endregion

    private void deleteTemplate(final int which)
    { assert null != this.handler; this.handler.deleteTemplate(which); }

    DeleteTemplateAlertDialog(final android.app.Activity activity,
    final org.wheatgenetics.coordinate.ui.DeleteTemplateAlertDialog.Handler handler)
    { super(activity); this.handler = handler; }

    // region Overridden Methods
    @java.lang.Override
    void configureAfterConstruction()
    {
        this.setTitleId(org.wheatgenetics.coordinate.R.string.delete_template)
            .setOnClickListener(new android.content.DialogInterface.OnClickListener()
                {
                    @java.lang.Override
                    public void onClick(final android.content.DialogInterface dialog,
                    final int which)
                    {
                        org.wheatgenetics.coordinate.ui.
                            DeleteTemplateAlertDialog.this.deleteTemplate(which);
                    }
                });
    }

    @java.lang.Override
    void configureBeforeShow() { this.setItems(this.items); }
    // endregion

    void show(final java.lang.String items[])
    { if (null != items) { this.items = items; this.configureAndShow(); } }
}