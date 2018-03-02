package org.wheatgenetics.coordinate.pc;

/**
 * Uses:
 * android.app.Activity
 *
 * org.wheatgenetics.coordinate.database.ProjectsTable
 *
 * org.wheatgenetics.coordinate.model.ProjectModel
 *
 * org.wheatgenetics.coordinate.pc.CreateProjectAlertDialog
 * org.wheatgenetics.coordinate.pc.CreateProjectAlertDialog.Handler
 */
@java.lang.SuppressWarnings("ClassExplicitlyExtendsObject")
public class ProjectCreator extends java.lang.Object
implements org.wheatgenetics.coordinate.pc.CreateProjectAlertDialog.Handler
{
    // region Fields
    private final android.app.Activity activity;

    private org.wheatgenetics.coordinate.pc.CreateProjectAlertDialog
        createProjectAlertDialog = null;
    private org.wheatgenetics.coordinate.database.ProjectsTable projectsTableInstance = null;
    // endregion

    private org.wheatgenetics.coordinate.database.ProjectsTable projectsTable()
    {
        if (null == this.projectsTableInstance) this.projectsTableInstance =
            new org.wheatgenetics.coordinate.database.ProjectsTable(this.activity);
        return this.projectsTableInstance;
    }

    public ProjectCreator(final android.app.Activity activity)
    { super(); this.activity = activity; }

    @java.lang.Override
    public void handleCreateProjectDone(final java.lang.String projectTitle)
    {
        this.projectsTable().insert(
            new org.wheatgenetics.coordinate.model.ProjectModel(projectTitle));
    }

    public void create()
    {
        if (null == this.createProjectAlertDialog) this.createProjectAlertDialog =
            new org.wheatgenetics.coordinate.pc.CreateProjectAlertDialog(this.activity, this);
        this.createProjectAlertDialog.show();
    }
}