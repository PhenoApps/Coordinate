package org.wheatgenetics.coordinate.dialogs;

import android.app.Activity;
import android.content.DialogInterface;

import androidx.annotation.NonNull;
import androidx.annotation.Size;

/**
 * Migrated from org.phenoapps.androidlibrary.ItemsAlertDialog.
 */
public abstract class ItemsAlertDialog extends AlertDialog {
    private DialogInterface.OnClickListener onClickListener;

    @SuppressWarnings({"unused"})
    public ItemsAlertDialog(@NonNull final Activity activity) {
        super(activity);
    }

    // region Public Methods
    @SuppressWarnings({"unused"})
    public void setOnClickListener(final DialogInterface.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @SuppressWarnings({"unused"})
    public void show(@SuppressWarnings({"CStyleArrayDeclaration"}) @Size(min = 1) final String items[]) {
        if (null != items) {
            this.setItems(items, this.onClickListener);
            this.createShow();
        }
    }

    @SuppressWarnings({"unused"})
    public void show(@SuppressWarnings({"CStyleArrayDeclaration"}) @Size(min = 1) final int items[]) {
        if (null != items) {
            this.setItems(items, this.onClickListener);
            this.createShow();
        }
    }
    // endregion
}
