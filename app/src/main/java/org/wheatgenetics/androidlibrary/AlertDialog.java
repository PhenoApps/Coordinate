package org.wheatgenetics.androidlibrary;

import org.wheatgenetics.coordinate.R;

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
 * android.view.Window
 * android.view.WindowManager.LayoutParams
 * android.widget.Button
 * android.widget.LinearLayout
 *
 * androidx.annotation.LayoutRes
 * androidx.annotation.NonNull
 * androidx.annotation.Size
 * androidx.annotation.StringRes
 *
 * org.wheatgenetics.androidlibrary.BuildConfig
 * org.wheatgenetics.androidlibrary.R
 * org.wheatgenetics.androidlibrary.Utils
 */
@SuppressWarnings({"ClassExplicitlyExtendsObject"})
public abstract class AlertDialog extends Object
{
    // region Fields
    @androidx.annotation.NonNull private final android.app.Activity activity;

    private android.app.AlertDialog.Builder builderInstance                 = null ;    // lazy load
    private android.view.LayoutInflater     layoutInflaterInstance          = null ;    // lazy load
    private android.app.AlertDialog         alertDialog                     = null ;
    private boolean                         positiveOnClickListenerReplaced = false;
    // endregion

    // region Private Methods
    @androidx.annotation.NonNull private android.app.AlertDialog.Builder builder()
    {
        if (null == this.builderInstance)
            this.builderInstance = new android.app.AlertDialog.Builder(this.activity());
        return this.builderInstance;
    }

    private void createAlertDialog()
    {
        this.alertDialog                     = this.builder().create();
        this.positiveOnClickListenerReplaced = false                  ;
        if (null == this.alertDialog) throw new AssertionError();
    }

    private void replaceOnClickListener(final int whichButton,
    final android.view.View.OnClickListener onClickListener)
    {
        if (null != this.alertDialog)
        {
            final android.widget.Button button = this.alertDialog.getButton(whichButton);
            if (null != button) button.setOnClickListener(onClickListener);
        }
    }
    // endregion

    public AlertDialog(@androidx.annotation.NonNull final android.app.Activity activity)
    { super(); this.activity = activity; this.configure(); }

    // region Public Methods
    public abstract void configure();

    @androidx.annotation.NonNull public android.app.Activity activity() { return this.activity; }

    @androidx.annotation.NonNull public String getString(
    @androidx.annotation.StringRes final int resId) { return this.activity().getString(resId); }

    // region set() Public Methods
    @androidx.annotation.NonNull public AlertDialog setTitle(
    final String title) { this.builder().setTitle(title); return this; }

    @androidx.annotation.NonNull public AlertDialog setTitle(
    @androidx.annotation.StringRes final int resId) { this.builder().setTitle(resId); return this; }

    @androidx.annotation.NonNull
    public AlertDialog setCancelableToFalse()
    { this.builder().setCancelable(false); return this; }

    @androidx.annotation.NonNull
    public AlertDialog setCancelableToTrue()
    { this.builder().setCancelable(true); return this; }

    @SuppressWarnings({"WeakerAccess"}) @androidx.annotation.NonNull
    public AlertDialog setMessage(final String message)
    { this.builder().setMessage(message); return this; }

    @SuppressWarnings({"unused"}) @androidx.annotation.NonNull
    public AlertDialog setMessage(
    @androidx.annotation.StringRes final int resId)
    { return this.setMessage(this.getString(resId)); }

    @SuppressWarnings({"WeakerAccess", "UnusedReturnValue"}) @androidx.annotation.NonNull
    public AlertDialog setItems(
    @SuppressWarnings({"CStyleArrayDeclaration"}) @androidx.annotation.Size(min = 1)
        final String items[],

    final android.content.DialogInterface.OnClickListener onClickListener)
    { this.builder().setItems(items, onClickListener); return this; }

    @SuppressWarnings({"WeakerAccess", "UnusedReturnValue"}) @androidx.annotation.NonNull
    public AlertDialog setItems(
    @SuppressWarnings({"CStyleArrayDeclaration"}) @androidx.annotation.Size(min = 1)
        final int itemIds[],

    final android.content.DialogInterface.OnClickListener onClickListener)
    {
        if (null != itemIds)
        {
            final int itemIdsLength = itemIds.length;
            if (itemIdsLength > 0)
            {
                @SuppressWarnings({"Convert2Diamond"})
                final java.util.ArrayList<String> arrayList =
                    new java.util.ArrayList<String>(itemIdsLength);
                for (@androidx.annotation.StringRes final Integer itemId: itemIds)
                    arrayList.add(this.getString(itemId));

                @SuppressWarnings({"CStyleArrayDeclaration"})
                final String items[] = new String[arrayList.size()];
                this.setItems(arrayList.toArray(items), onClickListener);
            }
        }
        return this;
    }

    @SuppressWarnings({"WeakerAccess", "UnusedReturnValue"}) @androidx.annotation.NonNull
    public AlertDialog setMultiChoiceItems(
    @SuppressWarnings({"CStyleArrayDeclaration"}) @androidx.annotation.Size(min = 1)
        final CharSequence items[],

    @SuppressWarnings({"CStyleArrayDeclaration"}) final boolean checkedItems[],

    final android.content.DialogInterface.OnMultiChoiceClickListener listener)
    {
        if (null != items)
        {
            //if (org.wheatgenetics.androidlibrary.BuildConfig.DEBUG) if (null != checkedItems)
            //    if (checkedItems.length != items.length) { throw new AssertionError(); }
            //this.builder().setMultiChoiceItems(items, checkedItems, listener);
        }
        return this;
    }

    @androidx.annotation.NonNull public AlertDialog setView(
    final android.view.View view) { this.builder().setView(view); return this; }

    @androidx.annotation.NonNull
    public AlertDialog setPositiveButton(
    @androidx.annotation.StringRes final int                                        resId          ,
                              final android.content.DialogInterface.OnClickListener onClickListener)
    { this.builder().setPositiveButton(resId, onClickListener); return this; }

    @SuppressWarnings({"unused"}) @androidx.annotation.NonNull
    public AlertDialog setPositiveButton(
    @androidx.annotation.StringRes final int resId)
    { return this.setPositiveButton(resId,null); }

    @androidx.annotation.NonNull public AlertDialog
    setOKPositiveButton(final android.content.DialogInterface.OnClickListener onClickListener)
    {
        return this.setPositiveButton(
            R.string.okButtonText, onClickListener);
    }

    @SuppressWarnings({"unused"}) @androidx.annotation.NonNull
    public AlertDialog setOKPositiveButton()
    {
        return this.setOKPositiveButton(
            Utils.cancellingOnClickListener());
    }

    @androidx.annotation.NonNull
    public AlertDialog setNegativeButton(
    @androidx.annotation.StringRes final int                                        resId          ,
                              final android.content.DialogInterface.OnClickListener onClickListener)
    { this.builder().setNegativeButton(resId, onClickListener); return this; }

    @SuppressWarnings({"WeakerAccess"}) @androidx.annotation.NonNull
    public AlertDialog setCancelNegativeButton(
    final android.content.DialogInterface.OnClickListener onClickListener)
    {
        return this.setNegativeButton(R.string.cancelButtonText,
            onClickListener);
    }

    @SuppressWarnings({"UnusedReturnValue"}) @androidx.annotation.NonNull
    public AlertDialog setCancelNegativeButton()
    {
        return this.setCancelNegativeButton(
            Utils.cancellingOnClickListener());
    }

    @SuppressWarnings({"UnusedReturnValue"}) @androidx.annotation.NonNull
    public AlertDialog setOKNegativeButton()
    {
        return this.setNegativeButton(R.string.okButtonText,
            Utils.dismissingOnClickListener());
    }

    @SuppressWarnings({"WeakerAccess"}) @androidx.annotation.NonNull
    public AlertDialog setNeutralButton(
    @androidx.annotation.StringRes final int                                        resId          ,
                              final android.content.DialogInterface.OnClickListener onClickListener)
    { this.builder().setNeutralButton(resId, onClickListener); return this; }

    @SuppressWarnings({"unused"}) @androidx.annotation.NonNull
    public AlertDialog setNeutralButton(
    @androidx.annotation.StringRes final int resId)
    { return this.setNeutralButton(resId,null); }
    // endregion

    // region Inflater Public Methods
    @SuppressWarnings({"WeakerAccess"}) @androidx.annotation.NonNull
    public android.view.LayoutInflater layoutInflater()
    {
        if (null == this.layoutInflaterInstance)
            this.layoutInflaterInstance = this.activity().getLayoutInflater();
        return this.layoutInflaterInstance;
    }

    public android.view.View inflate(@androidx.annotation.LayoutRes final int resource)
    {
        return this.layoutInflater().inflate(
            /* resource     => */ resource                                        ,
            /* root         => */ new android.widget.LinearLayout(this.activity()),
            /* attachToRoot => */false);
    }
    // endregion

    // region show() Public Methods
    public void show()
    { if (null == this.alertDialog) this.createAlertDialog(); this.alertDialog.show(); }

    @SuppressWarnings({"WeakerAccess"}) public void createShow()
    { this.createAlertDialog(); this.alertDialog.show(); }

    @SuppressWarnings({"unused"}) public void createModifyShow()
    {
        this.createAlertDialog();
        {
            final android.view.Window window = this.alertDialog.getWindow();
            if (null != window) window.setSoftInputMode(
                android.view.WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        }
        this.alertDialog.show();
    }
    // endregion

    // region showToast() Public Methods
    @SuppressWarnings({"WeakerAccess"}) public void showToast(final String text)
    { Utils.showLongToast(this.activity(), text); }

    @SuppressWarnings({"WeakerAccess"})
    public void showToast(@androidx.annotation.StringRes final int resId)
    { this.showToast(this.getString(resId)); }
    // endregion

    // region Public Methods Used To Keep Alert Dialog From Dismissing
    @SuppressWarnings({"WeakerAccess", "BooleanMethodIsAlwaysInverted"})
    public boolean positiveOnClickListenerHasBeenReplaced()
    { return this.positiveOnClickListenerReplaced; }

    @SuppressWarnings({"WeakerAccess"}) public void replacePositiveOnClickListener(
    final android.view.View.OnClickListener onClickListener)
    {
        if (null != this.alertDialog)
        {
            this.replaceOnClickListener(android.app.AlertDialog.BUTTON_POSITIVE, onClickListener);
            this.positiveOnClickListenerReplaced = true;
        }
    }

    @SuppressWarnings({"unused"}) public void replaceNeutralOnClickListener(
    final android.view.View.OnClickListener onClickListener)
    { this.replaceOnClickListener(android.app.AlertDialog.BUTTON_NEUTRAL, onClickListener); }

    @SuppressWarnings({"WeakerAccess"}) public void cancelAlertDialog()
    { if (null != this.alertDialog) this.alertDialog.cancel(); }
    // endregion
    // endregion
}