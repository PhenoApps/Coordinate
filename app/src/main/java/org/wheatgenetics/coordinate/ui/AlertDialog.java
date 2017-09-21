package org.wheatgenetics.coordinate.ui;

/**
 * Uses:
 * android.app.Activity
 * android.app.AlertDialog
 * android.app.AlertDialog.Builder
 * android.content.DialogInterface.OnClickListener
 *
 * org.wheatgenetics.androidlibrary.R
 * org.wheatgenetics.androidlibrary.Utils
 */
abstract class AlertDialog extends java.lang.Object
{
    // region Fields
    private final android.app.Activity activityInstance;

    private android.app.AlertDialog.Builder                 builderInstance = null;
    private android.content.DialogInterface.OnClickListener onClickListener = null;
    private android.app.AlertDialog                         alertDialog     = null;
    // endregion

    private android.app.AlertDialog.Builder builder()
    {
        if (null == this.builderInstance)
            this.builderInstance = new android.app.AlertDialog.Builder(this.activityInstance);
        return this.builderInstance;
    }

    AlertDialog(final android.app.Activity activity)
    { super(); this.activityInstance = activity; this.configureAfterConstruction(); }

    // region Package Methods
    void configureAfterConstruction() {}
    void configureBeforeShow       () {}


    android.app.Activity activity()
    { assert null != this.activityInstance; return this.activityInstance; }

    java.lang.String getString(final int resId) { return this.activity().getString(resId); }


    org.wheatgenetics.coordinate.ui.AlertDialog setTitleId(final int titleId)
    { this.builder().setTitle(titleId); return this; }

    org.wheatgenetics.coordinate.ui.AlertDialog setOnClickListener(
    final android.content.DialogInterface.OnClickListener onClickListener)
    { this.onClickListener = onClickListener; return this; }

    org.wheatgenetics.coordinate.ui.AlertDialog setItems(final java.lang.String items[],
    final android.content.DialogInterface.OnClickListener onClickListener)
    { if (null != items) this.builder().setItems(items, onClickListener); return this; }

    org.wheatgenetics.coordinate.ui.AlertDialog setItems(final int itemResIds[],
    final android.content.DialogInterface.OnClickListener onClickListener)
    {
        if (null != itemResIds)
        {
            final java.util.ArrayList<java.lang.String> arrayList =
                new java.util.ArrayList<java.lang.String>(itemResIds.length);
            for (final java.lang.Integer itemResId: itemResIds)
                arrayList.add(this.getString(itemResId));

            final java.lang.String items[] = new java.lang.String[arrayList.size()];
            this.setItems(arrayList.toArray(items), onClickListener);
        }
        return this;
    }

    org.wheatgenetics.coordinate.ui.AlertDialog setItems(final java.lang.String items[])
    { this.setItems(items, this.onClickListener); return this; }

    org.wheatgenetics.coordinate.ui.AlertDialog setNegativeButton()
    {
        this.builder().setNegativeButton(
            org.wheatgenetics.androidlibrary.R.string.cancelButtonText        ,
            org.wheatgenetics.androidlibrary.Utils.cancellingOnClickListener());
        return this;
    }


    void show()
    {
        if (null == this.alertDialog)
        {
            this.alertDialog = this.builder().create();
            assert null != this.alertDialog;
        }
        this.alertDialog.show();
    }

    void configureAndShow() { this.configureBeforeShow(); this.builder().create().show(); }
    // endregion
}