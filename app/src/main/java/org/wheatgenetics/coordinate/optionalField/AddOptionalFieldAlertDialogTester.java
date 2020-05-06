package org.wheatgenetics.coordinate.optionalField;

import android.app.Activity;

import androidx.annotation.NonNull;

public class AddOptionalFieldAlertDialogTester implements AddOptionalFieldAlertDialog.Handler {
    // region Fields
    private final Activity activity;
    @NonNull
    private final
    AddOptionalFieldAlertDialogTester.Handler
            handler;
    private AddOptionalFieldAlertDialog
            addOptionalFieldAlertDialogInstance = null;                                     // lazy load

    public AddOptionalFieldAlertDialogTester(final Activity activity,
                                             @NonNull final
                                             AddOptionalFieldAlertDialogTester.Handler
                                                     handler) {
        super();
        this.activity = activity;
        this.handler = handler;
    }
    // endregion

    @NonNull
    private AddOptionalFieldAlertDialog
    addOptionalFieldAlertDialog() {
        if (null == this.addOptionalFieldAlertDialogInstance)
            this.addOptionalFieldAlertDialogInstance =
                    new AddOptionalFieldAlertDialog(
                            this.activity, this);
        return this.addOptionalFieldAlertDialogInstance;
    }

    // region org.wheatgenetics.coordinate.optionalField.AddOptionalFieldAlertDialog.Handler Overridden Method
    @Override
    public void handleAddOptionalFieldDone() {
        this.handler.handleAddOptionalFieldDone();
    }

    public void test(final NonNullOptionalFields
                             nonNullOptionalFields) {
        if (null != nonNullOptionalFields)
            this.addOptionalFieldAlertDialog().show(nonNullOptionalFields);
    }
    // endregion

    @SuppressWarnings({"UnnecessaryInterfaceModifier"})
    public interface Handler {
        public abstract void handleAddOptionalFieldDone();
    }
}