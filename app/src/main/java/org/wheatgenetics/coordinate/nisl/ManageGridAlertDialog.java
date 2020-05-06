package org.wheatgenetics.coordinate.nisl;

import android.app.Activity;
import android.content.DialogInterface;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import org.wheatgenetics.androidlibrary.AlertDialog;
import org.wheatgenetics.coordinate.R;

class ManageGridAlertDialog extends AlertDialog {
    // region Fields
    @SuppressWarnings({"CStyleArrayDeclaration", "RedundantSuppression"})
    @StringRes
    private static final int
            BOTH_ITEMS[] = new int[]{
            R.string.ManageGridAlertDialogLoad,
            R.string.ManageGridAlertDialogDelete},
            LOAD_ITEM[] = new int[]{
                    R.string.ManageGridAlertDialogLoad};
    @NonNull
    private final
    ManageGridAlertDialog.Handler handler;
    private DialogInterface.OnClickListener onClickListenerInstance = null; // lazy

    ManageGridAlertDialog(final Activity activity, @NonNull final ManageGridAlertDialog.Handler handler) {
        super(activity);
        this.handler = handler;
    }
    // endregion                                                                            //  load

    // region Private Methods
    private void loadGrid() {
        this.handler.loadGrid();
    }

    private void deleteGrid() {
        this.handler.deleteGrid();
    }

    @NonNull
    private DialogInterface.OnClickListener onClickListener() {
        if (null == this.onClickListenerInstance) this.onClickListenerInstance =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, final int which) {
                        switch (which) {
                            case 0:
                                ManageGridAlertDialog.this.loadGrid();
                                break;

                            case 1:
                                ManageGridAlertDialog.this.deleteGrid();
                                break;
                        }
                    }
                };
        return this.onClickListenerInstance;
    }
    // endregion

    @Override
    public void configure() {
        this.setTitle(R.string.ManageGridAlertDialogTitle);
    }

    void show(final boolean enableDeleteGridItem) {
        if (enableDeleteGridItem)
            this.setItems(ManageGridAlertDialog.BOTH_ITEMS,
                    this.onClickListener());
        else
            this.setItems(ManageGridAlertDialog.LOAD_ITEM,
                    this.onClickListener());
        this.createShow();
    }

    @SuppressWarnings({"UnnecessaryInterfaceModifier"})
    interface Handler {
        public void loadGrid();

        public void deleteGrid();
    }
}