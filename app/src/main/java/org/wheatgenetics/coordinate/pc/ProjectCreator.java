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
{
    // region Fields
    private final android.app.Activity activity;

    private org.wheatgenetics.coordinate.pc.CreateProjectAlertDialog
        createProjectAlertDialog = null;
    private org.wheatgenetics.coordinate.database.ProjectsTable projectsTableInstance = null;
    // endregion

    // region Private Methods
    private org.wheatgenetics.coordinate.database.ProjectsTable projectsTable()
    {
        if (null == this.projectsTableInstance) this.projectsTableInstance =
            new org.wheatgenetics.coordinate.database.ProjectsTable(this.activity);
        return this.projectsTableInstance;
    }

    private void insert(final java.lang.String projectTitle)
    {
        this.projectsTable().insert(
            new org.wheatgenetics.coordinate.model.ProjectModel(projectTitle));
    }
    // endregion

    public ProjectCreator(final android.app.Activity activity)
    { super(); this.activity = activity; }

    public void create()
    {
        if (null == this.createProjectAlertDialog) this.createProjectAlertDialog =
            new org.wheatgenetics.coordinate.pc.CreateProjectAlertDialog(this.activity,
                new org.wheatgenetics.coordinate.pc.CreateProjectAlertDialog.Handler()
                {
                    @java.lang.Override
                    public void handleCreateProjectDone(final java.lang.String projectTitle)
                    { org.wheatgenetics.coordinate.pc.ProjectCreator.this.insert(projectTitle); }
                });
        this.createProjectAlertDialog.show();
    }
}