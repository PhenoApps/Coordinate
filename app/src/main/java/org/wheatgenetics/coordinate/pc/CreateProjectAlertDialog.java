package org.wheatgenetics.coordinate.pc;

/**
 * Uses:
 * android.app.Activity
 * android.view.View
 * android.view.View.OnClickListener
 * android.widget.EditText
 *
 * androidx.annotation.NonNull
 *
 * org.wheatgenetics.androidlibrary.AlertDialog
 * org.wheatgenetics.androidlibrary.Utils
 *
 * org.wheatgenetics.coordinate.R
 */
class CreateProjectAlertDialog extends org.wheatgenetics.androidlibrary.AlertDialog
{
    @java.lang.SuppressWarnings({"UnnecessaryInterfaceModifier"}) interface Handler
    { public abstract boolean handleCreateProjectDone(java.lang.String projectTitle); }

    // region Fields
    @androidx.annotation.NonNull private final
        org.wheatgenetics.coordinate.pc.CreateProjectAlertDialog.Handler handler;
    private android.widget.EditText projectTitleEditText;
    // endregion

    private void createProject()
    {
        final java.lang.String projectTitle =
            org.wheatgenetics.androidlibrary.Utils.getText(this.projectTitleEditText);
        if (projectTitle.length() < 1)
            this.showToast(
                org.wheatgenetics.coordinate.R.string.CreateProjectAlertDialogEmptyToast);
        else
            if (this.handler.handleCreateProjectDone(projectTitle))
                this.cancelAlertDialog();
            else
                this.showToast(
                    org.wheatgenetics.coordinate.R.string.CreateProjectAlertDialogInUseToast);
    }

    CreateProjectAlertDialog(final android.app.Activity activity, @androidx.annotation.NonNull
        final org.wheatgenetics.coordinate.pc.CreateProjectAlertDialog.Handler handler)
    { super(activity); this.handler = handler; }

    // region Overridden Methods
    @java.lang.Override public void configure()
    {
        this.setTitle(org.wheatgenetics.coordinate.R.string.CreateProjectAlertDialogTitle);

        {
            final android.view.View view =
                this.inflate(org.wheatgenetics.coordinate.R.layout.create_project);

            if (null == this.projectTitleEditText && null != view) this.projectTitleEditText =
                view.findViewById(org.wheatgenetics.coordinate.R.id.projectTitleEditText);

            this.setView(view);
        }

        this.setOKPositiveButton().setCancelNegativeButton();
    }

    @java.lang.Override public void show()
    {
        super.show();
        if (!this.positiveOnClickListenerHasBeenReplaced()) this.replacePositiveOnClickListener(
            new android.view.View.OnClickListener()
            {
                @java.lang.Override public void onClick(final android.view.View view)
                { org.wheatgenetics.coordinate.pc.CreateProjectAlertDialog.this.createProject(); }
            });
    }
    // endregion
}