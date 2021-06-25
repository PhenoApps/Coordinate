package org.wheatgenetics.coordinate.optionalField;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.phenoapps.androidlibrary.AlertDialog;
import org.phenoapps.androidlibrary.Utils;
import org.wheatgenetics.coordinate.R;

class AddOptionalFieldAlertDialog extends AlertDialog {
    // region Fields
    @NonNull
    private final
    AddOptionalFieldAlertDialog.Handler handler;
    private EditText nameEditText, defaultValueEditText;
    private NonNullOptionalFields nonNullOptionalFields;
    AddOptionalFieldAlertDialog(final Activity activity, @NonNull final AddOptionalFieldAlertDialog.Handler handler) {
        super(activity);
        this.handler = handler;
    }
    // endregion

    private void addOptionalField() {
        final String name =
                Utils.getText(this.nameEditText);

        if (name.length() < 1)
            this.showToast(R.string.AddOptionalFieldAlertDialogToast);
        else {
            if (null != this.nonNullOptionalFields) this.nonNullOptionalFields.add(
                    /* name  => */ name,
                    /* value => */ Utils.getText(
                            this.defaultValueEditText),
                    /* hint => */null);
            this.cancelAlertDialog();
            this.handler.handleAddOptionalFieldDone();
        }
    }

    @Override
    public void configure() {
        this.setTitle(R.string.AddOptionalFieldAlertDialogTitle)
                .setCancelableToFalse();

        {
            @SuppressLint({"InflateParams"}) final View view =
                    this.layoutInflater().inflate(
                            R.layout.add_optional_field, null);

            if (null != view) {
                if (null == this.nameEditText) this.nameEditText = view.findViewById(
                        R.id.nameEditText);
                if (null == this.defaultValueEditText) this.defaultValueEditText =
                        view.findViewById(R.id.defaultValueEditText);
            }

            this.setView(view);
        }

        this.setOKPositiveButton(null).setCancelNegativeButton();
    }

    void show(@Nullable final NonNullOptionalFields nonNullOptionalFields) {
        if (null != nonNullOptionalFields) {
            if (null != this.nameEditText) this.nameEditText.setText("");
            if (null != this.defaultValueEditText) this.defaultValueEditText.setText("");
            this.nonNullOptionalFields = nonNullOptionalFields;
            this.show();

            if (!this.positiveOnClickListenerHasBeenReplaced()) this.replacePositiveOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(final View view) {
                            AddOptionalFieldAlertDialog.this.addOptionalField();
                        }
                    });
        }
    }

    @SuppressWarnings({"UnnecessaryInterfaceModifier"})
    interface Handler {
        public abstract void handleAddOptionalFieldDone();
    }
}