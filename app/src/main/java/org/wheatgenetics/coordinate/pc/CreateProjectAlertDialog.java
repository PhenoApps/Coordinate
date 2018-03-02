package org.wheatgenetics.coordinate.pc;

/**
 * Uses:
 * android.app.Activity
 * android.view.View
 * android.view.View.OnClickListener
 * android.widget.EditText
 *
 * org.wheatgenetics.androidlibrary.AlertDialog
 * org.wheatgenetics.androidlibrary.Utils
 *
 * org.wheatgenetics.coordinate.R
 */
class CreateProjectAlertDialog extends org.wheatgenetics.androidlibrary.AlertDialog
{
    @java.lang.SuppressWarnings("UnnecessaryInterfaceModifier")
    interface Handler
    { public abstract void handleCreateProjectDone(java.lang.String projectTitle); }

    // region Fields
    private final org.wheatgenetics.coordinate.pc.CreateProjectAlertDialog.Handler handler;
    private       android.widget.EditText                             projectTitleEditText;
    // endregion

    private void createProject()
    {
        final java.lang.String projectTitle =
            org.wheatgenetics.androidlibrary.Utils.getText(this.projectTitleEditText);
        if (0 == projectTitle.length())
            this.showToast(org.wheatgenetics.coordinate.R.string.CreateProjectAlertDialogToast);
        else
        {
            this.cancelAlertDialog();
            assert null != this.handler; this.handler.handleCreateProjectDone(projectTitle);
        }
    }

    CreateProjectAlertDialog(final android.app.Activity activity,
    final org.wheatgenetics.coordinate.pc.CreateProjectAlertDialog.Handler handler)
    { super(activity); this.handler = handler; }

    // region Overridden Methods
    @java.lang.Override
    public void configure()
    {
        this.setTitle(org.wheatgenetics.coordinate.R.string.CreateProjectAlertDialogTitle);

        {
            final android.view.View view =
                this.inflate(org.wheatgenetics.coordinate.R.layout.create_project);

            if (null == this.projectTitleEditText)
            {
                assert null != view;
                this.projectTitleEditText = (android.widget.EditText)
                    view.findViewById(org.wheatgenetics.coordinate.R.id.projectTitleEditText);
            }

            this.setView(view);
        }

        this.setOKPositiveButton().setCancelNegativeButton();
    }

    @java.lang.Override
    public void show()
    {
        super.show();
        if (!this.positiveOnClickListenerHasBeenReplaced()) this.replacePositiveOnClickListener(
            new android.view.View.OnClickListener()
            {
                @java.lang.Override
                public void onClick(final android.view.View view)
                { org.wheatgenetics.coordinate.pc.CreateProjectAlertDialog.this.createProject(); }
            });
    }
    // endregion
}