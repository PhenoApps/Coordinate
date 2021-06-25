package org.wheatgenetics.coordinate.tc.exclude;

import android.app.Activity;
import android.content.DialogInterface;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import org.phenoapps.androidlibrary.MultiChoiceItemsAlertDialog;
import org.wheatgenetics.coordinate.R;

class ExcludedRowsOrColsAlertDialog
        extends MultiChoiceItemsAlertDialog {
    // region Fields
    private final String label;
    @NonNull
    private final
    ExcludedRowsOrColsAlertDialog.Handler handler;
    private boolean titleHasBeenSet = false;

    ExcludedRowsOrColsAlertDialog(final Activity activity,
                                  @StringRes final int label,
                                  @NonNull final
                                  ExcludedRowsOrColsAlertDialog.Handler handler) {
        super(activity);
        this.label = this.getString(label);
        this.handler = handler;
    }
    // endregion

    private void excludeRowsOrCols(@SuppressWarnings({"CStyleArrayDeclaration"})
                                   @NonNull final boolean checkedItems[]) {
        this.handler.excludeRowsOrCols(checkedItems);
    }

    void show(
            @SuppressWarnings({"CStyleArrayDeclaration"}) final String items[],
            @SuppressWarnings({"CStyleArrayDeclaration"}) final boolean checkedItems[]) {
        if (null != items && null != checkedItems) {
            if (!this.titleHasBeenSet) {
                this.setTitle(this.getString(
                        R.string.ExcludedRowsOrColsAlertDialogTitle) +
                        " - " + this.label + 's');
                this.titleHasBeenSet = true;
            }

            this.setOKPositiveButton(new DialogInterface.OnClickListener() {
                @Override
                public void onClick(
                        final DialogInterface dialog, final int which) {
                    ExcludedRowsOrColsAlertDialog
                            .this.excludeRowsOrCols(checkedItems);
                }
            });
            this.show(items, checkedItems,
                    new DialogInterface.OnMultiChoiceClickListener() {
                        @Override
                        public void onClick(
                                final DialogInterface dialog, final int which,
                                final boolean isChecked) {
                            checkedItems[which] = isChecked;
                        }
                    });
        }
    }

    @SuppressWarnings({"UnnecessaryInterfaceModifier"})
    interface Handler {
        public abstract void excludeRowsOrCols(
                @SuppressWarnings({"CStyleArrayDeclaration"})
                @NonNull boolean checkedItems[]);
    }
}