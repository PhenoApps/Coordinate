package org.wheatgenetics.coordinate.ui;

/**
 * Uses:
 * android.app.Activity
 * android.app.AlertDialog
 * android.app.AlertDialog.Builder
 * android.content.DialogInterface.OnClickListener
 * android.content.DialogInterface.OnMultiChoiceClickListener
 *
 * org.wheatgenetics.androidlibrary.R
 * org.wheatgenetics.androidlibrary.Utils
 */
abstract class AlertDialog extends java.lang.Object
{
    // region Fields
    private final android.app.Activity activityInstance;

    private android.app.AlertDialog.Builder builderInstance = null;
    private android.app.AlertDialog         alertDialog     = null;
    // endregion

    AlertDialog(final android.app.Activity activity)
    { super(); this.activityInstance = activity; this.configureAfterConstruction(); }

    // region Package Methods
    void configureAfterConstruction() {}
    void configureBeforeShow       () {}

    android.app.Activity activity()
    { assert null != this.activityInstance; return this.activityInstance; }

    java.lang.String getString(final int stringId) { return this.activity().getString(stringId); }

    android.app.AlertDialog.Builder builder()
    {
        if (null == this.builderInstance)
            this.builderInstance = new android.app.AlertDialog.Builder(this.activityInstance);
        return this.builderInstance;
    }

    // region set() Package Methods
    org.wheatgenetics.coordinate.ui.AlertDialog setTitle(final java.lang.String title)
    { this.builder().setTitle(title); return this;}

    org.wheatgenetics.coordinate.ui.AlertDialog setTitle(final int titleId)
    { this.builder().setTitle(titleId); return this; }

    org.wheatgenetics.coordinate.ui.AlertDialog setItems(final java.lang.String items[],
    final android.content.DialogInterface.OnClickListener onClickListener)
    { if (null != items) this.builder().setItems(items, onClickListener); return this; }

    org.wheatgenetics.coordinate.ui.AlertDialog setItems(final int itemIds[],
    final android.content.DialogInterface.OnClickListener onClickListener)
    {
        if (null != itemIds)
        {
            final java.util.ArrayList<java.lang.String> arrayList =
                new java.util.ArrayList<java.lang.String>(itemIds.length);
            for (final java.lang.Integer itemId: itemIds) arrayList.add(this.getString(itemId));

            final java.lang.String items[] = new java.lang.String[arrayList.size()];
            this.setItems(arrayList.toArray(items), onClickListener);
        }
        return this;
    }

    org.wheatgenetics.coordinate.ui.AlertDialog setMultiChoiceItems(
    final java.lang.CharSequence items[], final boolean checkedItems[],
    final android.content.DialogInterface.OnMultiChoiceClickListener listener)
    { this.builder().setMultiChoiceItems(items, checkedItems, listener); return this; }

    org.wheatgenetics.coordinate.ui.AlertDialog setOKPositiveButton(
    final android.content.DialogInterface.OnClickListener onClickListener)
    {
        this.builder().setPositiveButton(
            org.wheatgenetics.androidlibrary.R.string.okButtonText, onClickListener);
        return this;
    }

    org.wheatgenetics.coordinate.ui.AlertDialog setOKPositiveButton()
    {
        return this.setOKPositiveButton(
            org.wheatgenetics.androidlibrary.Utils.cancellingOnClickListener());
    }

    org.wheatgenetics.coordinate.ui.AlertDialog setNegativeButton()
    {
        this.builder().setNegativeButton(
            org.wheatgenetics.androidlibrary.R.string.cancelButtonText        ,
            org.wheatgenetics.androidlibrary.Utils.cancellingOnClickListener());
        return this;
    }

    org.wheatgenetics.coordinate.ui.AlertDialog setNeutralButton(final int textId,
    final android.content.DialogInterface.OnClickListener onClickListener)
    { this.builder().setNeutralButton(textId, onClickListener); return this; }
    // endregion

    void show()
    {
        if (null == this.alertDialog)
        {
            this.alertDialog = this.builder().create();
            assert null != this.alertDialog;
        }
        this.alertDialog.show();
    }
    // endregion
}