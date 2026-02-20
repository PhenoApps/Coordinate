package org.wheatgenetics.coordinate.dialogs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;

import org.wheatgenetics.coordinate.R;

/**
 * Migrated from org.phenoapps.androidlibrary.GetExportFileNameAlertDialog.
 */
@SuppressWarnings({"unused"})
public class GetExportFileNameAlertDialog extends AlertDialog {
    @SuppressWarnings({"UnnecessaryInterfaceModifier"})
    public interface Handler {
        void handleGetFileNameDone(String fileName);
    }

    // region Fields
    private final GetExportFileNameAlertDialog.Handler handler;
    private EditText editText;
    // endregion

    private void handleGetFileNameDone() {
        final String fileName = editText != null ? editText.getText().toString().trim() : "";
        if (fileName.length() < 1)
            this.showToast(R.string.getExportFileNameAlertDialogToast);
        else {
            this.cancelAlertDialog();
            if (null != this.handler) this.handler.handleGetFileNameDone(fileName);
        }
    }

    public GetExportFileNameAlertDialog(@NonNull final Activity activity,
                                        final GetExportFileNameAlertDialog.Handler handler) {
        super(activity);
        this.handler = handler;
    }

    @Override
    public void configure() {
        this.setTitle(R.string.getExportFileNameAlertDialogTitle);

        {
            @SuppressLint({"InflateParams"})
            final View view = this.layoutInflater().inflate(
                    R.layout.alert_dialog_get_export_file_name, null);

            if (null != view)
                if (null == this.editText)
                    this.editText = view.findViewById(R.id.editText);

            this.setView(view);
        }

        this.setOKPositiveButton(null).setCancelNegativeButton();
    }

    public void show(final String initialFileName) {
        if (null != this.editText) {
            this.editText.setText(initialFileName);
            this.editText.selectAll();

            this.show();
            if (!this.positiveOnClickListenerHasBeenReplaced())
                this.replacePositiveOnClickListener(
                        view -> GetExportFileNameAlertDialog.this.handleGetFileNameDone());
        }
    }
}
