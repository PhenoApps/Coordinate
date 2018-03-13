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
    @java.lang.SuppressWarnings("UnnecessaryInterfaceModifier")
    public interface Handler { public abstract void handleCreateProjectDone(long projectId); }

    // region Fields
    private final android.app.Activity                                   activity;
    private final org.wheatgenetics.coordinate.pc.ProjectCreator.Handler handler ;

    private org.wheatgenetics.coordinate.pc.CreateProjectAlertDialog
        createProjectAlertDialog = null, createAndReturnProjectAlertDialog = null;
    private org.wheatgenetics.coordinate.database.ProjectsTable projectsTableInstance = null;
    // endregion

    // region Private Methods
    private org.wheatgenetics.coordinate.database.ProjectsTable projectsTable()
    {
        if (null == this.projectsTableInstance) this.projectsTableInstance =
            new org.wheatgenetics.coordinate.database.ProjectsTable(this.activity);
        return this.projectsTableInstance;
    }

    private long sharedInsert(final java.lang.String projectTitle)
    {
        return this.projectsTable().insert(
            new org.wheatgenetics.coordinate.model.ProjectModel(projectTitle));
    }

    private boolean insert(final java.lang.String projectTitle)
    { return this.sharedInsert(projectTitle) > 0; }

    private boolean insertAndReturn(final java.lang.String projectTitle)
    {
        final long projectId = this.sharedInsert(projectTitle);
        if (projectId > 0)
        {
            assert null != this.handler; this.handler.handleCreateProjectDone(projectId);
            return true;
        }
        else return false;
    }
    // endregion

    // region Constructors
    public ProjectCreator(final android.app.Activity activity)
    { super(); this.activity = activity; this.handler = null; }

    public ProjectCreator(final android.app.Activity activity,
    final org.wheatgenetics.coordinate.pc.ProjectCreator.Handler handler)
    { super(); this.activity = activity; this.handler = handler; }
    // endregion

    // region Public Methods
    public void create()
    {
        if (null == this.createProjectAlertDialog) this.createProjectAlertDialog =
            new org.wheatgenetics.coordinate.pc.CreateProjectAlertDialog(this.activity,
                new org.wheatgenetics.coordinate.pc.CreateProjectAlertDialog.Handler()
                {
                    @java.lang.Override
                    public boolean handleCreateProjectDone(final java.lang.String projectTitle)
                    {
                        return org.wheatgenetics.coordinate.pc.ProjectCreator.this.insert(
                            projectTitle);
                    }
                });
        this.createProjectAlertDialog.show();
    }

    public void createAndReturn()
    {
        if (null == this.createAndReturnProjectAlertDialog) this.createAndReturnProjectAlertDialog =
            new org.wheatgenetics.coordinate.pc.CreateProjectAlertDialog(this.activity,
                new org.wheatgenetics.coordinate.pc.CreateProjectAlertDialog.Handler()
                {
                    @java.lang.Override
                    public boolean handleCreateProjectDone(final java.lang.String projectTitle)
                    {
                        return org.wheatgenetics.coordinate.pc.ProjectCreator.this.insertAndReturn(
                            projectTitle);
                    }
                });
        this.createAndReturnProjectAlertDialog.show();
    }
    // endregion
}