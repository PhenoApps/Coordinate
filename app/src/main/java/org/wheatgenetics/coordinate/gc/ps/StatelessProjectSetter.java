package org.wheatgenetics.coordinate.gc.ps;

/**
 * The StatelessProjectSetter is for use with the version of Coordinate that does not maintain
 * state.  There is no currently-loaded-project mechanism.  The StatelessProjectSetter will either
 * 1) ask the user if the grid is supposed to be in a project (and if it is then which project) or
 * 2) not be used because the GridCreator will be told what project the grid is supposed to be in.
 *
 * Uses:
 * android.app.Activity
 *
 * androidx.annotation.IntRange
 * androidx.annotation.NonNull
 * androidx.annotation.RestrictTo
 * androidx.annotation.RestrictTo.Scope
 *
 * org.wheatgenetics.coordinate.R
 * org.wheatgenetics.coordinate.SelectAlertDialog
 * org.wheatgenetics.coordinate.SelectAlertDialog.Handler
 *
 * org.wheatgenetics.coordinate.database.ProjectsTable
 *
 * org.wheatgenetics.coordinate.model.ProjectModel
 * org.wheatgenetics.coordinate.model.ProjectModels
 *
 * org.wheatgenetics.coordinate.gc.ps.ProjectSetter
 */
public class StatelessProjectSetter extends org.wheatgenetics.coordinate.gc.ps.ProjectSetter
{
    // region Fields
    private org.wheatgenetics.coordinate.database.ProjectsTable projectsTableInstance  = null; // ll
    private org.wheatgenetics.coordinate.model.ProjectModels    projectModels          = null;
    private org.wheatgenetics.coordinate.SelectAlertDialog   selectAlertDialogInstance = null; // ll
    // endregion

    // region Private Methods
    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.database.ProjectsTable projectsTable()
    {
        if (null == this.projectsTableInstance) this.projectsTableInstance =
            new org.wheatgenetics.coordinate.database.ProjectsTable(this.activity());
        return this.projectsTableInstance;
    }

    // region SelectAlertDialog Private Methods
    private void chooseExistingAfterSelect(final int which)
    {
        if (null != this.projectModels)
        {
            final org.wheatgenetics.coordinate.model.ProjectModel projectModel =
                this.projectModels.get(which);
            if (null != projectModel)
                { this.setProjectId(projectModel.getId()); this.handleExistingProjectSet(); }
            this.projectModels = null;
        }
    }

    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.SelectAlertDialog selectAlertDialog()
    {
        if (null == this.selectAlertDialogInstance) this.selectAlertDialogInstance =
            new org.wheatgenetics.coordinate.SelectAlertDialog(this.activity(),
                new org.wheatgenetics.coordinate.SelectAlertDialog.Handler()
                {
                    @java.lang.Override public void select(final int which)
                    {
                        org.wheatgenetics.coordinate.gc.ps.StatelessProjectSetter
                            .this.chooseExistingAfterSelect(which);
                    }
                });
        return this.selectAlertDialogInstance;
    }

    private void showSelectAlertDialog()
    {
        this.projectModels = this.projectsTable().load();
        if (null != this.projectModels) this.selectAlertDialog().show(
            org.wheatgenetics.coordinate.R.string.StatelessProjectSetterSelectProject,
            this.projectModels.titles()                                              );
    }
    // endregion
    // endregion

    public StatelessProjectSetter(final android.app.Activity activity, @androidx.annotation.NonNull
    final org.wheatgenetics.coordinate.gc.ps.StatelessProjectSetter.Handler handler)
    { super(activity, handler); }

    // region Overridden Methods
    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @java.lang.Override void handleCreateProjectDone(
    @androidx.annotation.IntRange(from = 1) final long projectId)
    { this.setProjectId(projectId); this.handleNewProjectSet(); }

    /**
     * which                 item
     * ===== =====================================
     *   0   Don't add this grid to a project.
     *   1   Create a project for this grid.
     *   2   Add this grid to an existing project.
     */
    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @java.lang.Override void handleProjectChoice(
    @androidx.annotation.IntRange(from = 0, to = 2) final int which)
    {
        switch (which)
        {
            case 0 : this.clearProjectId(); this.handleNoProjectSet(); break;
            case 1 : this.projectCreator().createAndReturn()         ; break;
            case 2 : this.showSelectAlertDialog()                    ; break;
            default: throw new java.lang.IllegalArgumentException()         ;
        }
    }
    // endregion

    public void set()
    {
        this.showProjectChoiceAlertDialog(this.projectsTable().exists() ? this.activity().getString(
            org.wheatgenetics.coordinate.R.string.StatelessProjectSetterSelectProjectItem) : null);
    }
}