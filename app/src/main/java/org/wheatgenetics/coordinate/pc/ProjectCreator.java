package org.wheatgenetics.coordinate.pc;

/**
 * Uses:
 * android.app.Activity
 * android.content.res.Resources.NotFoundException
 *
 * androidx.annotation.IntRange
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 * androidx.annotation.PluralsRes
 * androidx.annotation.StringRes
 *
 * org.wheatgenetics.coordinate.StringGetter
 *
 * org.wheatgenetics.coordinate.database.ProjectsTable
 *
 * org.wheatgenetics.coordinate.model.ProjectModel
 *
 * org.wheatgenetics.coordinate.pc.CreateProjectAlertDialog
 * org.wheatgenetics.coordinate.pc.CreateProjectAlertDialog.Handler
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"}) public class ProjectCreator
extends java.lang.Object implements org.wheatgenetics.coordinate.StringGetter
{
    @java.lang.SuppressWarnings({"UnnecessaryInterfaceModifier"}) public interface Handler
    {
        public abstract void handleCreateProjectDone(
        @androidx.annotation.IntRange(from = 1) long projectId);
    }

    // region Fields
    // region Constructor Fields
                                  private final android.app.Activity activity;
    @androidx.annotation.Nullable private final
        org.wheatgenetics.coordinate.pc.ProjectCreator.Handler handler;
    // endregion

    private org.wheatgenetics.coordinate.pc.CreateProjectAlertDialog                   // lazy loads
        createProjectAlertDialogInstance = null, createAndReturnProjectAlertDialogInstance = null;
    private org.wheatgenetics.coordinate.database.ProjectsTable
        projectsTableInstance = null;                                                   // lazy load
    // endregion

    // region Private Methods
    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.database.ProjectsTable projectsTable()
    {
        if (null == this.projectsTableInstance) this.projectsTableInstance =
            new org.wheatgenetics.coordinate.database.ProjectsTable(this.activity);
        return this.projectsTableInstance;
    }

    private long sharedInsert(final java.lang.String projectTitle)
    {
        return this.projectsTable().insert(
            new org.wheatgenetics.coordinate.model.ProjectModel(projectTitle,this));
    }

    // region create() Private Methods
    private boolean insert(final java.lang.String projectTitle)
    { return this.sharedInsert(projectTitle) > 0; }

    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.pc.CreateProjectAlertDialog createProjectAlertDialog()
    {
        if (null == this.createProjectAlertDialogInstance) this.createProjectAlertDialogInstance =
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
        return this.createProjectAlertDialogInstance;
    }
    // endregion

    // region createAndReturn() Private Methods
    private boolean insertAndReturn(final java.lang.String projectTitle)
    {
        final long projectId = this.sharedInsert(projectTitle);
        if (projectId > 0)
        {
            if (null != this.handler) this.handler.handleCreateProjectDone(projectId);
            return true;
        }
        else return false;
    }

    @androidx.annotation.NonNull private
    org.wheatgenetics.coordinate.pc.CreateProjectAlertDialog createAndReturnProjectAlertDialog()
    {
        if (null == this.createAndReturnProjectAlertDialogInstance)
            this.createAndReturnProjectAlertDialogInstance =
                new org.wheatgenetics.coordinate.pc.CreateProjectAlertDialog(this.activity,
                    new org.wheatgenetics.coordinate.pc.CreateProjectAlertDialog.Handler()
                    {
                        @java.lang.Override public boolean handleCreateProjectDone(
                        final java.lang.String projectTitle)
                        {
                            return
                                org.wheatgenetics.coordinate.pc.ProjectCreator.this.insertAndReturn(
                                    projectTitle);
                        }
                    });
        return this.createAndReturnProjectAlertDialogInstance;
    }
    // endregion
    // endregion

    // region Constructors
    public ProjectCreator(final android.app.Activity activity, @androidx.annotation.Nullable
    final org.wheatgenetics.coordinate.pc.ProjectCreator.Handler handler)
    { super(); this.activity = activity; this.handler = handler; }

    public ProjectCreator(@androidx.annotation.NonNull final android.app.Activity activity)
    { this(activity,null); }
    // endregion

    // region org.wheatgenetics.coordinate.StringGetter Overridden Methods
    @java.lang.Override @androidx.annotation.Nullable public java.lang.String get(
    @androidx.annotation.StringRes final int resId) { return this.activity.getString(resId); }

    @java.lang.Override @androidx.annotation.NonNull public java.lang.String getQuantity(
    @androidx.annotation.PluralsRes         final int                 resId     ,
    @androidx.annotation.IntRange(from = 0) final int                 quantity  ,
    @androidx.annotation.Nullable           final java.lang.Object... formatArgs)
    throws android.content.res.Resources.NotFoundException
    { return this.activity.getResources().getQuantityString(resId, quantity, formatArgs); }
    // endregion

    // region Public Methods
    public void create() { this.createProjectAlertDialog().show(); }

    public void createAndReturn() { this.createAndReturnProjectAlertDialog().show(); }
    // endregion
}