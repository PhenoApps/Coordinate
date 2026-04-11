package org.wheatgenetics.coordinate.dialogs;

import android.app.Activity;
import android.content.DialogInterface;

import androidx.annotation.NonNull;
import androidx.annotation.Size;

/**
 * Migrated from org.phenoapps.androidlibrary.MultiChoiceItemsAlertDialog.
 */
@SuppressWarnings({"unused"})
public abstract class MultiChoiceItemsAlertDialog extends AlertDialog {
    public MultiChoiceItemsAlertDialog(@NonNull final Activity activity) {
        super(activity);
    }

    @Override
    public void configure() {
        this.setCancelNegativeButton();
    }

    public void show(
            @SuppressWarnings({"CStyleArrayDeclaration"}) @Size(min = 1) final String items[],
            @SuppressWarnings({"CStyleArrayDeclaration"}) final boolean checkedItems[],
            final DialogInterface.OnMultiChoiceClickListener listener) {
        if (null != items) {
            this.setMultiChoiceItems(items, checkedItems, listener);
            this.createShow();
        }
    }
}
