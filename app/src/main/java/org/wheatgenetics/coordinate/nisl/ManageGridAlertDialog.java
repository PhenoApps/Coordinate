package org.wheatgenetics.coordinate.nisl;

/**
 * Uses:
 * android.app.Activity
 * android.content.DialogInterface
 * android.content.DialogInterface.OnClickListener
 *
 * androidx.annotation.NonNull
 * androidx.annotation.StringRes
 *
 * org.wheatgenetics.androidlibrary.AlertDialog
 *
 * org.wheatgenetics.coordinate.R
 */
class ManageGridAlertDialog extends org.wheatgenetics.androidlibrary.AlertDialog
{
    @java.lang.SuppressWarnings({"UnnecessaryInterfaceModifier"}) interface Handler
    { public void loadGrid(); public void deleteGrid(); }

    // region Fields
    @java.lang.SuppressWarnings({"CStyleArrayDeclaration", "RedundantSuppression"})
    @androidx.annotation.StringRes private static final int
        BOTH_ITEMS[] = new int[]{
            org.wheatgenetics.coordinate.R.string.ManageGridAlertDialogLoad  ,
            org.wheatgenetics.coordinate.R.string.ManageGridAlertDialogDelete},
        LOAD_ITEM[] = new int[]{
            org.wheatgenetics.coordinate.R.string.ManageGridAlertDialogLoad};

    @androidx.annotation.NonNull private final
        org.wheatgenetics.coordinate.nisl.ManageGridAlertDialog.Handler handler;

    private android.content.DialogInterface.OnClickListener onClickListenerInstance = null; // lazy
    // endregion                                                                            //  load

    // region Private Methods
    private void loadGrid  () { this.handler.loadGrid  (); }
    private void deleteGrid() { this.handler.deleteGrid(); }

    @androidx.annotation.NonNull
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

    ManageGridAlertDialog(final android.app.Activity activity, @androidx.annotation.NonNull
    final org.wheatgenetics.coordinate.nisl.ManageGridAlertDialog.Handler handler)
    { super(activity); this.handler = handler; }

    @java.lang.Override public void configure()
    { this.setTitle(org.wheatgenetics.coordinate.R.string.ManageGridAlertDialogTitle); }

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