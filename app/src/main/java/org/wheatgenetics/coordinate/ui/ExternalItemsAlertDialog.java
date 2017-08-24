package org.wheatgenetics.coordinate.ui;

/**
 * Uses:
 * android.content.Context
 * android.content.DialogInterface.OnClickListener
 *
 * org.wheatgenetics.coordinate.ui.ContextAlertDialog
 */
abstract class ExternalItemsAlertDialog extends org.wheatgenetics.coordinate.ui.ContextAlertDialog
{
    // region Fields
    private final int                                             titleId                       ;
    private       android.content.DialogInterface.OnClickListener onClickListenerInstance = null;
    // endregion

    abstract android.content.DialogInterface.OnClickListener makeOnClickListener();

    private android.content.DialogInterface.OnClickListener onClickListener()
    {
        if (null == this.onClickListenerInstance)
            this.onClickListenerInstance = this.makeOnClickListener();
        return this.onClickListenerInstance;
    }

    ExternalItemsAlertDialog(final android.content.Context context, final int titleId)
    { super(context); this.titleId = titleId; }

    void show(final java.lang.CharSequence items[])
    {
        if (null == this.builder)
        {
            this.builder = this.makeBuilder(this.titleId);
            assert null != this.builder;
        }
        this.builder.setItems(items, this.onClickListener());
        this.builder.create().show();
    }
}