package org.wheatgenetics.coordinate.pc;

import android.app.Activity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import org.wheatgenetics.coordinate.dialogs.AlertDialog;
import org.wheatgenetics.coordinate.Utils;
import org.wheatgenetics.coordinate.R;
import org.wheatgenetics.coordinate.utils.SoftKeyboardUtil;

public class CreateProjectAlertDialog extends AlertDialog {
    // region Fields
    @NonNull
    private final
    CreateProjectAlertDialog.Handler handler;
    private EditText projectTitleEditText;
    public CreateProjectAlertDialog(final Activity activity, @NonNull final CreateProjectAlertDialog.Handler handler) {
        super(activity);
        this.handler = handler;
    }
    // endregion

    private void createProject() {
        final String projectTitle =
                Utils.getText(this.projectTitleEditText);
        if (projectTitle.length() < 1)
            this.showToast(R.string.CreateProjectAlertDialogEmptyToast);
        else if (this.handler.handleCreateProjectDone(projectTitle)) {
            SoftKeyboardUtil.Companion.closeKeyboard(activity(), this.projectTitleEditText, null);
            this.cancelAlertDialog();
        }
        else
            this.showToast(
                    R.string.CreateProjectAlertDialogInUseToast);
    }

    // region Overridden Methods
    @Override
    public void configure() {
        this.setTitle(R.string.CreateProjectAlertDialogTitle);

        {
            final View view =
                    this.inflate(R.layout.create_project);

            if (null == this.projectTitleEditText && null != view) this.projectTitleEditText =
                    view.findViewById(R.id.projectTitleEditText);

            if (this.projectTitleEditText != null) {

                this.projectTitleEditText.setText("");

                this.projectTitleEditText.requestFocus();

                this.projectTitleEditText.setOnEditorActionListener((TextView v, int i, KeyEvent key) -> {
                    if (i == EditorInfo.IME_ACTION_DONE) {
                        createProject();
                        return true;
                    } else return false;
                });
            }

            this.setView(view);
        }

        this.setOKPositiveButton();

        setCancelNegativeButton((dialog, which) -> {
            SoftKeyboardUtil.Companion.closeKeyboard(activity(), this.projectTitleEditText, null);
            dialog.cancel();
        });
    }

    @Override
    public void show() {

        super.show();

        if (!this.positiveOnClickListenerHasBeenReplaced()) {
            this.replacePositiveOnClickListener(view -> {
                CreateProjectAlertDialog.this.createProject();
            });
        }

        if (this.projectTitleEditText != null) {
            this.projectTitleEditText.setText("");
            SoftKeyboardUtil.Companion.showKeyboard(activity(), this.projectTitleEditText, null);

        }
    }

    @SuppressWarnings({"UnnecessaryInterfaceModifier"})
    public interface Handler {
        public abstract boolean handleCreateProjectDone(String projectTitle);
    }
    // endregion
}