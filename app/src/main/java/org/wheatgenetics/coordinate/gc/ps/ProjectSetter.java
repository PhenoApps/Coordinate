package org.wheatgenetics.coordinate.gc.ps;

/**
 * Uses:
 * android.annotation.SuppressLint
 * android.app.Activity
 *
 * androidx.annotation.IntRange
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 * androidx.annotation.RestrictTo
 * androidx.annotation.RestrictTo.Scope
 *
 * org.wheatgenetics.coordinate.R
 * org.wheatgenetics.coordinate.SelectAlertDialog
 * org.wheatgenetics.coordinate.SelectAlertDialog.Handler
 *
 * org.wheatgenetics.coordinate.model.Model
 *
 * org.wheatgenetics.coordinate.pc.ProjectCreator
 * org.wheatgenetics.coordinate.pc.ProjectCreator.Handler
 */
@SuppressWarnings({"ClassExplicitlyExtendsObject"})
abstract class ProjectSetter extends java.lang.Object
{
    @SuppressWarnings({"UnnecessaryInterfaceModifier"}) public interface Handler
    {
        public abstract void handleNoProjectSet();
        public abstract void handleProjectSet  (
            @androidx.annotation.IntRange(from = 1) long projectId, boolean existing);
    }

    // region Fields
                                 private final android.app.Activity activity;
    @androidx.annotation.NonNull private final
        org.wheatgenetics.coordinate.gc.ps.ProjectSetter.Handler handler;

    /** 0 means no projectId. */
    @androidx.annotation.IntRange(from = 0) private long projectId;

    private org.wheatgenetics.coordinate.pc.ProjectCreator projectCreatorInstance = null;      // ll
    private org.wheatgenetics.coordinate.SelectAlertDialog
        projectChoiceAlertDialogInstance = null;                                        // lazy load
    // endregion

    // region Private Methods
    @androidx.annotation.NonNull private static java.lang.String[] items(
    @androidx.annotation.Nullable final java.lang.String thirdItem)
    {
        final java.lang.String
            firstItem  = "Don't add this grid to a project.",
            secondItem = "Create a project for this grid."  ;
        if (null == thirdItem)
            return new java.lang.String[]{firstItem, secondItem};
        else
            return new java.lang.String[]{firstItem, secondItem, thirdItem};
    }

    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.SelectAlertDialog projectChoiceAlertDialog()
    {
        if (null == this.projectChoiceAlertDialogInstance) this.projectChoiceAlertDialogInstance =
            new org.wheatgenetics.coordinate.SelectAlertDialog(this.activity(),
                new org.wheatgenetics.coordinate.SelectAlertDialog.Handler()
                {
                    @Override public void select(final int which)
                    {
                        org.wheatgenetics.coordinate.gc.ps.ProjectSetter.this.handleProjectChoice(
                            which);
                    }
                });
        return this.projectChoiceAlertDialogInstance;
    }

    @android.annotation.SuppressLint({"Range"})
    private void handleProjectSet(final boolean existing)
    { this.handler().handleProjectSet(this.projectId, existing); }
    // endregion

    // region Package Methods
    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    android.app.Activity activity() { return this.activity; }

    // region projectId Package Methods
    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    void setProjectId(@androidx.annotation.IntRange(from = 0) final long projectId)
    { this.projectId = projectId; }

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    void clearProjectId() { this.setProjectId(0); }

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    boolean projectIdIsCleared()
    { return org.wheatgenetics.coordinate.model.Model.illegal(this.projectId); }
    // endregion

    // region projectCreator Package Methods
    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    abstract void handleCreateProjectDone(
    @androidx.annotation.IntRange(from = 1) final long projectId);

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @androidx.annotation.NonNull org.wheatgenetics.coordinate.pc.ProjectCreator projectCreator()
    {
        if (null == this.projectCreatorInstance) this.projectCreatorInstance =
            new org.wheatgenetics.coordinate.pc.ProjectCreator(this.activity(),
                new org.wheatgenetics.coordinate.pc.ProjectCreator.Handler()
                {
                    @Override public void handleCreateProjectDone(
                    @androidx.annotation.IntRange(from = 1) final long projectId)
                    {
                        org.wheatgenetics.coordinate.gc.ps.ProjectSetter
                            .this.handleCreateProjectDone(projectId);
                    }
                });
        return this.projectCreatorInstance;
    }
    // endregion

    // region projectChoiceAlertDialog Package Methods
    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    abstract void handleProjectChoice(
    @androidx.annotation.IntRange(from = 0, to = 2) final int which);

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    void showProjectChoiceAlertDialog(
    @androidx.annotation.Nullable final java.lang.String thirdItem)
    {
        this.projectChoiceAlertDialog().show(
            org.wheatgenetics.coordinate.R.string.ProjectSetterProjectChoiceAlertDialogTitle,
            org.wheatgenetics.coordinate.gc.ps.ProjectSetter.items(thirdItem)               );
    }
    // endregion

    // region handler Package Methods
    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @androidx.annotation.NonNull org.wheatgenetics.coordinate.gc.ps.ProjectSetter.Handler handler()
    { return this.handler; }

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    void handleNoProjectSet() { this.handler().handleNoProjectSet(); }

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    void handleNewProjectSet() { this.handleProjectSet(false); }

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    void handleExistingProjectSet() { this.handleProjectSet(true); }
    // endregion
    // endregion

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    ProjectSetter(final android.app.Activity activity, @androidx.annotation.NonNull
    final org.wheatgenetics.coordinate.gc.ps.ProjectSetter.Handler handler)
    { super(); this.activity = activity; this.handler = handler; }
}