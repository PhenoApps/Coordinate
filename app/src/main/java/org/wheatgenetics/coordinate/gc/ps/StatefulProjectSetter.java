package org.wheatgenetics.coordinate.gc.ps;

/**
 * The StatefulProjectSetter is for use with the version of Coordinate that maintains state.  The
 * state that is maintained is the currently loaded project, if any.
 *
 * Uses:
 * android.app.Activity
 *
 * androidx.annotation.IntRange
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 * androidx.annotation.RestrictTo
 * androidx.annotation.RestrictTo.Scope
 *
 * org.wheatgenetics.coordinate.model.ProjectModel
 *
 * org.wheatgenetics.coordinate.gc.ps.ProjectSetter
 * org.wheatgenetics.coordinate.gc.ps.ProjectSetter.Handler
 */
public class StatefulProjectSetter extends org.wheatgenetics.coordinate.gc.ps.ProjectSetter
{
    @java.lang.SuppressWarnings({"UnnecessaryInterfaceModifier"})
    public interface Handler extends org.wheatgenetics.coordinate.gc.ps.ProjectSetter.Handler
    {
        public abstract void clearProjectModel();
        public abstract void loadProjectModel (
            @androidx.annotation.IntRange(from = 1) long projectId);
    }

    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.gc.ps.StatefulProjectSetter.Handler statefulHandler()
    { return (org.wheatgenetics.coordinate.gc.ps.StatefulProjectSetter.Handler) this.handler(); }

    public StatefulProjectSetter(final android.app.Activity activity, @androidx.annotation.NonNull
    final org.wheatgenetics.coordinate.gc.ps.StatefulProjectSetter.Handler handler)
    { super(activity, handler); }

    // region Overridden Methods
    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @java.lang.Override void handleCreateProjectDone(
    @androidx.annotation.IntRange(from = 1) final long projectId)
    {
        this.setProjectId(projectId); this.statefulHandler().loadProjectModel(projectId);
        this.handleNewProjectSet();
    }

    /**
     * which                          item
     * ===== =======================================================
     *   0   Don't add this grid to a project.
     *   1   Create a project for this grid then select new project.
     *   2   Add this grid to "X" project.
     *
     * projectId which             side effect
     * ========= ===== ===================================
     *  cleared    0   none
     *  cleared    1   load new project
     *    set      0   clear old project
     *    set      1   clear old project, load new project
     *    set      2   none
     */
    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @java.lang.Override void handleProjectChoice(
    @androidx.annotation.IntRange(from = 0, to = 2) final int which)
    {
        if (this.projectIdIsCleared())
            switch (which)
            {
                case 0 : this.handleNoProjectSet()              ; break;
                case 1 : this.projectCreator().createAndReturn(); break;
                default: throw new java.lang.IllegalArgumentException();
            }
        else
            switch (which)
            {
                case 0: this.clearProjectId(); this.statefulHandler().clearProjectModel();
                    this.handleNoProjectSet(); break;

                case 1 : this.projectCreator().createAndReturn(); break;
                case 2 : this.handleExistingProjectSet()        ; break;
                default: throw new java.lang.IllegalArgumentException();
            }
    }
    // endregion

    public void set(@androidx.annotation.Nullable
    final org.wheatgenetics.coordinate.model.ProjectModel projectModel)
    {
        @androidx.annotation.Nullable final java.lang.String thirdItem;
        if (null == projectModel)
            { thirdItem = null; this.clearProjectId(); }
        else
        {
            thirdItem = java.lang.String.format(this.activity().getString(
                org.wheatgenetics.coordinate.R.string.StatefulProjectSetterThirdItem),
                projectModel.getTitle()                                              );
            this.setProjectId(projectModel.getId());
        }
        this.showProjectChoiceAlertDialog(thirdItem);
    }
}