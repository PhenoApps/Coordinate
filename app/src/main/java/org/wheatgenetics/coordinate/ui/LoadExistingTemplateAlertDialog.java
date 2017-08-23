package org.wheatgenetics.coordinate.ui;

/**
 * Uses:
 * android.app.AlertDialog.Builder
 * android.content.Context
 * android.content.DialogInterface
 * android.content.DialogInterface.OnClickListener
 *
 * org.wheatgenetics.coordinate.R
 */
class LoadExistingTemplateAlertDialog extends java.lang.Object
{
    interface Handler { public abstract void loadTemplate(int which); }

    // region Fields
    private final android.content.Context                                                 context;
    private final org.wheatgenetics.coordinate.ui.LoadExistingTemplateAlertDialog.Handler handler;

    private android.app.AlertDialog.Builder                 builder                 = null;
    private android.content.DialogInterface.OnClickListener onClickListenerInstance = null;
    // endregion

    // region Private Methods
    private void loadTemplate(final int which)
    { assert null != this.handler; this.handler.loadTemplate(which); }

    private android.content.DialogInterface.OnClickListener onClickListener()
    {
        if (null == this.onClickListenerInstance) this.onClickListenerInstance =
            new android.content.DialogInterface.OnClickListener()
            {
                @java.lang.Override
                public void onClick(final android.content.DialogInterface dialog, final int which)
                {
                    org.wheatgenetics.coordinate.ui.
                        LoadExistingTemplateAlertDialog.this.loadTemplate(which);
                }
            };
        return this.onClickListenerInstance;
    }
    // endregion

    LoadExistingTemplateAlertDialog(final android.content.Context context,
    final org.wheatgenetics.coordinate.ui.LoadExistingTemplateAlertDialog.Handler handler)
    { super(); this.context = context; this.handler = handler; }

    void show(final java.lang.CharSequence items[])
    {
        if (null == this.builder)
        {
            this.builder = new android.app.AlertDialog.Builder(this.context);
            this.builder.setTitle(org.wheatgenetics.coordinate.R.string.template_load);
        }
        this.builder.setItems(items, this.onClickListener());
        this.builder.create().show();
    }
}