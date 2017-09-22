package org.wheatgenetics.coordinate.ui;

/**
 * Uses:
 * android.app.Activity
 * android.app.AlertDialog
 * android.app.AlertDialog.Builder
 * android.content.DialogInterface.OnClickListener
 * android.content.DialogInterface.OnMultiChoiceClickListener
 * android.view.LayoutInflater
 * android.view.View
 * android.view.View.OnClickListener
 * android.view.WindowManager.LayoutParams
 * android.widget.LinearLayout
 *
 * org.wheatgenetics.androidlibrary.R
 * org.wheatgenetics.androidlibrary.Utils
 */
abstract class AlertDialog extends java.lang.Object
{
    // region Fields
    private final android.app.Activity activityInstance;

    private android.app.AlertDialog.Builder builderInstance                 = null ;
    private android.view.LayoutInflater     layoutInflaterInstance          = null ;
    private android.app.AlertDialog         alertDialog                     = null ;
    private boolean                         positiveOnClickListenerReplaced = false;
    // endregion

    // region Private Methods
    private android.app.AlertDialog.Builder builder()
    {
        if (null == this.builderInstance)
            this.builderInstance = new android.app.AlertDialog.Builder(this.activityInstance);
        return this.builderInstance;
    }

    private void createAlertDialog()
    { this.alertDialog = this.builder().create(); assert null != this.alertDialog; }
    // endregion

    AlertDialog(final android.app.Activity activity)
    { super(); this.activityInstance = activity; this.configure(); }

    // region Package Methods
    abstract void configure();

    android.app.Activity activity()
    { assert null != this.activityInstance; return this.activityInstance; }

    java.lang.String getString(final int stringId) { return this.activity().getString(stringId); }

    // region set() Package Methods
    org.wheatgenetics.coordinate.ui.AlertDialog setTitle(final java.lang.String title)
    { this.builder().setTitle(title); return this; }

    org.wheatgenetics.coordinate.ui.AlertDialog setTitle(final int titleId)
    { this.builder().setTitle(titleId); return this; }

    org.wheatgenetics.coordinate.ui.AlertDialog setCancelableToFalse()
    { this.builder().setCancelable(false); return this; }

    org.wheatgenetics.coordinate.ui.AlertDialog setItems(final java.lang.String items[],
    final android.content.DialogInterface.OnClickListener onClickListener)
    { this.builder().setItems(items, onClickListener); return this; }

    org.wheatgenetics.coordinate.ui.AlertDialog setItems(final int itemIds[],
    final android.content.DialogInterface.OnClickListener onClickListener)
    {
        if (null != itemIds)
        {
            final int itemIdsLength = itemIds.length;
            if (itemIdsLength > 0)
            {
                final java.util.ArrayList<java.lang.String> arrayList =
                    new java.util.ArrayList<java.lang.String>(itemIdsLength);
                for (final java.lang.Integer itemId: itemIds) arrayList.add(this.getString(itemId));

                final java.lang.String items[] = new java.lang.String[arrayList.size()];
                this.setItems(arrayList.toArray(items), onClickListener);
            }
        }
        return this;
    }

    org.wheatgenetics.coordinate.ui.AlertDialog setMultiChoiceItems(
    final java.lang.CharSequence items[], final boolean checkedItems[],
    final android.content.DialogInterface.OnMultiChoiceClickListener listener)
    { this.builder().setMultiChoiceItems(items, checkedItems, listener); return this; }

    org.wheatgenetics.coordinate.ui.AlertDialog setView(final android.view.View view)
    { this.builder().setView(view); return this; }

    org.wheatgenetics.coordinate.ui.AlertDialog setPositiveButton(final int textId,
    final android.content.DialogInterface.OnClickListener onClickListener)
    { this.builder().setPositiveButton(textId, onClickListener); return this; }

    org.wheatgenetics.coordinate.ui.AlertDialog setPositiveButton(final int textId)
    { return this.setPositiveButton(textId, null); }

    org.wheatgenetics.coordinate.ui.AlertDialog setOKPositiveButton(
    final android.content.DialogInterface.OnClickListener onClickListener)
    {
        return this.setPositiveButton(
            org.wheatgenetics.androidlibrary.R.string.okButtonText, onClickListener);
    }

    org.wheatgenetics.coordinate.ui.AlertDialog setOKPositiveButton()
    {
        return this.setOKPositiveButton(
            org.wheatgenetics.androidlibrary.Utils.cancellingOnClickListener());
    }

    org.wheatgenetics.coordinate.ui.AlertDialog setNegativeButton()
    {
        this.builder().setNegativeButton(
            org.wheatgenetics.androidlibrary.R.string.cancelButtonText,
            org.wheatgenetics.androidlibrary.Utils.cancellingOnClickListener());
        return this;
    }

    org.wheatgenetics.coordinate.ui.AlertDialog setNeutralButton(final int textId,
    final android.content.DialogInterface.OnClickListener onClickListener)
    { this.builder().setNeutralButton(textId, onClickListener); return this; }
    // endregion

    // region Inflater Package Methods
    android.view.LayoutInflater layoutInflater()
    {
        if (null == this.layoutInflaterInstance)
            this.layoutInflaterInstance = this.activity().getLayoutInflater();
        return this.layoutInflaterInstance;
    }

    android.view.View inflate(final int resource)
    {
        return this.layoutInflater().inflate(
            /* resource     => */ resource,
            /* root         => */ new android.widget.LinearLayout(this.activity()),
            /* attachToRoot => */ false);
    }
    // endregion

    // region show() Package Methods
    void show() { if (null == this.alertDialog) this.createAlertDialog(); this.alertDialog.show(); }

    void createShow() { this.createAlertDialog(); this.alertDialog.show(); }

    void createModifiyShow()
    {
        this.createAlertDialog();
        this.alertDialog.getWindow().setSoftInputMode(
            android.view.WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        this.alertDialog.show();
    }
    // endregion

    // region showToast() Package Methods
    void showToast(final java.lang.String text)
    { org.wheatgenetics.androidlibrary.Utils.showLongToast(this.activity(), text); }

    void showToast(final int textId) { this.showToast(this.getString(textId)); }
    // endregion

    boolean positiveOnClickListenerHasBeenReplaced()
    { return this.positiveOnClickListenerReplaced; }

    void replaceOnClickListener(final android.view.View.OnClickListener onClickListener)
    {
        if (null != this.alertDialog)
        {
            this.alertDialog.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setOnClickListener(
                onClickListener);
            this.positiveOnClickListenerReplaced = true;
        }
    }

    void cancelAlertDialog() { if (null != this.alertDialog) this.alertDialog.cancel(); }
    // endregion
}