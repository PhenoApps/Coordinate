package org.wheatgenetics.coordinate.pc;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;

import org.phenoapps.androidlibrary.AlertDialog;
import org.phenoapps.androidlibrary.Utils;
import org.wheatgenetics.coordinate.R;

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
            this.showToast(
                    R.string.CreateProjectAlertDialogEmptyToast);
        else if (this.handler.handleCreateProjectDone(projectTitle))
            this.cancelAlertDialog();
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

            this.setView(view);
        }

        this.setOKPositiveButton().setCancelNegativeButton();
    }

    @Override
    public void show() {
        super.show();
        if (!this.positiveOnClickListenerHasBeenReplaced()) this.replacePositiveOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(final View view) {
                        CreateProjectAlertDialog.this.createProject();
                    }
                });
    }

    @SuppressWarnings({"UnnecessaryInterfaceModifier"})
    public interface Handler {
        public abstract boolean handleCreateProjectDone(String projectTitle);
    }
    // endregion
}