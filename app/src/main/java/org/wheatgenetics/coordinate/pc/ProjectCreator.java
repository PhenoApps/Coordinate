package org.wheatgenetics.coordinate.pc;

/**
 * Uses:
 * android.app.Activity
 *
 * org.wheatgenetics.coordinate.pc.CreateProjectAlertDialog
 * org.wheatgenetics.coordinate.pc.CreateProjectAlertDialog.Handler
 */
@java.lang.SuppressWarnings("ClassExplicitlyExtendsObject")
public class ProjectCreator extends java.lang.Object
implements org.wheatgenetics.coordinate.pc.CreateProjectAlertDialog.Handler
{
    @java.lang.SuppressWarnings("UnnecessaryInterfaceModifier")
    public interface Handler
    { public abstract void handleCreateProjectDone(java.lang.String projectTitle); }

    // region Fields
    private final android.app.Activity                                   activity;
    private final org.wheatgenetics.coordinate.pc.ProjectCreator.Handler handler ;

    private org.wheatgenetics.coordinate.pc.CreateProjectAlertDialog
        createProjectAlertDialog = null;
    // endregion

    public ProjectCreator(final android.app.Activity activity,
    final org.wheatgenetics.coordinate.pc.ProjectCreator.Handler handler)
    { super(); this.activity = activity; this.handler = handler; }

    @java.lang.Override
    public void handleCreateProjectDone(final java.lang.String projectTitle)
    { assert null != this.handler; this.handler.handleCreateProjectDone(projectTitle); }

    public void create()
    {
        if (null == this.createProjectAlertDialog) this.createProjectAlertDialog =
            new org.wheatgenetics.coordinate.pc.CreateProjectAlertDialog(this.activity, this);
        this.createProjectAlertDialog.show();
    }
}