package org.wheatgenetics.coordinate.nisl;

/**
 * Uses:
 * android.app.Activity
 * android.content.DialogInterface
 * android.content.DialogInterface.OnClickListener
 * android.support.annotation.StringRes
 *
 * org.wheatgenetics.androidlibrary.AlertDialog
 *
 * org.wheatgenetics.coordinate.R
 */
class ManageGridAlertDialog extends org.wheatgenetics.androidlibrary.AlertDialog
{
    @java.lang.SuppressWarnings({"UnnecessaryInterfaceModifier"})
    interface Handler { public void loadGrid(); public void deleteGrid(); }

    // region Fields
    @android.support.annotation.StringRes private static final int
        BOTH_ITEMS[] = new int[]{
            org.wheatgenetics.coordinate.R.string.NavigationItemSelectedListenerLoadGrid  ,
            org.wheatgenetics.coordinate.R.string.NavigationItemSelectedListenerDeleteGrid},
        LOAD_ITEM[] = new int[]{
            org.wheatgenetics.coordinate.R.string.NavigationItemSelectedListenerLoadGrid};

    private final org.wheatgenetics.coordinate.nisl.ManageGridAlertDialog.Handler handler;

    private android.content.DialogInterface.OnClickListener onClickListenerInstance = null;
    // endregion

    // region Private Methods
    private void loadGrid  () { assert null != this.handler; this.handler.loadGrid  (); }
    private void deleteGrid() { assert null != this.handler; this.handler.deleteGrid(); }

    private android.content.DialogInterface.OnClickListener onClickListener()
    {
        if (null == this.onClickListenerInstance) this.onClickListenerInstance =
            new android.content.DialogInterface.OnClickListener()
            {
                @java.lang.Override
                public void onClick(final android.content.DialogInterface dialog, final int which)
                {
                    switch (which)
                    {
                        case 0:
                            org.wheatgenetics.coordinate.nisl.ManageGridAlertDialog.this.loadGrid();
                            break;

                        case 1:
                            org.wheatgenetics.coordinate.nisl
                                .ManageGridAlertDialog.this.deleteGrid();
                            break;
                    }
                }
            };
        return this.onClickListenerInstance;
    }
    // endregion

    ManageGridAlertDialog(final android.app.Activity activity,
    final org.wheatgenetics.coordinate.nisl.ManageGridAlertDialog.Handler handler)
    { super(activity); this.handler = handler; }

    @java.lang.Override public void configure()
    {
        this.setTitle(org.wheatgenetics.coordinate
            .R.string.NavigationItemSelectedListenerManageGridTitle);
    }

    void show(final boolean enableDeleteGridItem)
    {
        if (enableDeleteGridItem)
            this.setItems(org.wheatgenetics.coordinate.nisl.ManageGridAlertDialog.BOTH_ITEMS,
                this.onClickListener());
        else
            this.setItems(org.wheatgenetics.coordinate.nisl.ManageGridAlertDialog.LOAD_ITEM,
                this.onClickListener());
        this.createShow();
    }
}