package org.wheatgenetics.androidlibrary;

/**
 * Uses:
 * android.app.Activity
 * android.content.DialogInterface.OnClickListener
 *
 * androidx.annotation.NonNull
 * androidx.annotation.Size
 *
 * org.wheatgenetics.androidlibrary.AlertDialog
 */
public abstract class ItemsAlertDialog extends AlertDialog
{
    private android.content.DialogInterface.OnClickListener onClickListener;

    @SuppressWarnings({"unused"}) public ItemsAlertDialog(@androidx.annotation.NonNull
    final android.app.Activity activity) { super(activity); }

    // region Public Methods
    @SuppressWarnings({"unused"}) public void setOnClickListener(
    final android.content.DialogInterface.OnClickListener onClickListener)
    { this.onClickListener = onClickListener; }

    @SuppressWarnings({"unused"})
    public void show(@SuppressWarnings({"CStyleArrayDeclaration"})
    @androidx.annotation.Size(min = 1) final String items[])
    { if (null != items) { this.setItems(items, this.onClickListener); this.createShow(); } }

    @SuppressWarnings({"unused"})
    public void show(@SuppressWarnings({"CStyleArrayDeclaration"})
    @androidx.annotation.Size(min = 1) final int items[])
    { if (null != items) { this.setItems(items, this.onClickListener); this.createShow(); } }
    // endregion
}