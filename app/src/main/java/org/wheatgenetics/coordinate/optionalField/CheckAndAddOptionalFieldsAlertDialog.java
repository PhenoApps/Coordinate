package org.wheatgenetics.coordinate.optionalField;

import android.app.Activity;
import android.content.DialogInterface;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.wheatgenetics.coordinate.dialogs.MultiChoiceItemsAlertDialog;
import org.wheatgenetics.coordinate.R;

public class CheckAndAddOptionalFieldsAlertDialog
        extends MultiChoiceItemsAlertDialog {
    // region Fields
    @NonNull
    private final
    CheckAndAddOptionalFieldsAlertDialog.Handler
            handler;
    private AddOptionalFieldAlertDialog
            addOptionalFieldAlertDialogInstance = null;                                     // lazy load
    private NonNullOptionalFields nonNullOptionalFields;
    private DialogInterface.OnMultiChoiceClickListener
            onMultiChoiceClickListenerInstance = null;                                      // lazy load
    public CheckAndAddOptionalFieldsAlertDialog(final Activity activity,
                                                @NonNull final
                                                CheckAndAddOptionalFieldsAlertDialog.Handler
                                                        handler) {
        super(activity);
        this.handler = handler;
    }
    // endregion

    // region Private Methods
    private void showCheckAndAddOptionalFieldsAlertDialogAgain() {
        this.handler.showCheckAndAddOptionalFieldsAlertDialogAgain();
    }

    // region onMultiChoiceClickListener() Private Methods
    private void checkOptionalField(final int index, final boolean checked) {
        if (null != this.nonNullOptionalFields)
            if (this.nonNullOptionalFields.setChecked(index, checked)) {
                this.cancelAlertDialog();
                this.showCheckAndAddOptionalFieldsAlertDialogAgain();
            }
    }

    @NonNull
    private DialogInterface.OnMultiChoiceClickListener onMultiChoiceClickListener() {
        if (null == this.onMultiChoiceClickListenerInstance)
            this.onMultiChoiceClickListenerInstance =
                    new DialogInterface.OnMultiChoiceClickListener() {
                        @Override
                        public void onClick(final DialogInterface dialogInterface,
                                            final int which, final boolean isChecked) {
                            CheckAndAddOptionalFieldsAlertDialog.this.checkOptionalField(
                                    which, isChecked);
                        }
                    };
        return this.onMultiChoiceClickListenerInstance;
    }
    // endregion

    // region addOptionalField() Private Methods
    @NonNull
    private AddOptionalFieldAlertDialog
    addOptionalFieldAlertDialog() {
        if (null == this.addOptionalFieldAlertDialogInstance)
            this.addOptionalFieldAlertDialogInstance =
                    new AddOptionalFieldAlertDialog(
                            this.activity(), new
                            AddOptionalFieldAlertDialog.Handler() {
                                @Override
                                public void handleAddOptionalFieldDone(String name, String defaultValue) {
                                    CheckAndAddOptionalFieldsAlertDialog.this
                                            .showCheckAndAddOptionalFieldsAlertDialogAgain();
                                }
                            });
        return this.addOptionalFieldAlertDialogInstance;
    }

    private void addOptionalField() {
        this.addOptionalFieldAlertDialog().show(this.nonNullOptionalFields);
    }
    // endregion
    // endregion

    @Override
    public void configure() {
        super.configure();
        this.setTitle(
                R.string.CheckAndAddOptionalFieldsAlertDialogTitle)
                .setOKPositiveButton().setNeutralButton(R.string.CheckAndAddOptionalFieldsAlertDialogButtonText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(
                            final DialogInterface dialog, final int which) {
                        CheckAndAddOptionalFieldsAlertDialog.this.addOptionalField();
                    }
                });
    }

    public void show(@Nullable final NonNullOptionalFields nonNullOptionalFields) {
        if (null != nonNullOptionalFields) {
            this.nonNullOptionalFields = nonNullOptionalFields;
            this.show(this.nonNullOptionalFields.displayNames(), this.nonNullOptionalFields.checks(),
                    this.onMultiChoiceClickListener());
        }
    }

    @SuppressWarnings({"UnnecessaryInterfaceModifier"})
    public interface Handler {
        public abstract void showCheckAndAddOptionalFieldsAlertDialogAgain();
    }
}