package org.wheatgenetics.coordinate.deleter;

/**
 * Uses:
 * android.content.Context
 *
 * androidx.annotation.IntRange
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 * androidx.annotation.RestrictTo
 * androidx.annotation.RestrictTo.Scope
 *
 * org.wheatgenetics.coordinate.R
 *
 * org.wheatgenetics.coordinate.database.ProjectsTable
 *
 * org.wheatgenetics.coordinate.model.BaseJoinedGridModels
 * org.wheatgenetics.coordinate.model.ProjectModel
 *
 * org.wheatgenetics.coordinate.deleter.CascadingDeleter
 */
public class ProjectDeleter extends org.wheatgenetics.coordinate.deleter.CascadingDeleter
{
    @java.lang.SuppressWarnings({"UnnecessaryInterfaceModifier"}) public interface Handler
    {
        public abstract void respondToDeletedProject(
        @androidx.annotation.IntRange(from = 1) long projectId);
    }

    // region Fields
    @androidx.annotation.NonNull private final
        org.wheatgenetics.coordinate.deleter.ProjectDeleter.Handler handler;

    private org.wheatgenetics.coordinate.database.ProjectsTable projectsTableInstance = null;  // ll
    // endregion

    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.database.ProjectsTable projectsTable()
    {
        if (null == this.projectsTableInstance) this.projectsTableInstance =
            new org.wheatgenetics.coordinate.database.ProjectsTable(this.context());
        return this.projectsTableInstance;
    }

    public ProjectDeleter(@androidx.annotation.NonNull final android.content.Context context,
    @androidx.annotation.NonNull final org.wheatgenetics.coordinate.deleter.ProjectDeleter.Handler
        handler)
    {
        super(context,
            org.wheatgenetics.coordinate.R.string.ProjectDeleterConfirmationTitle  ,
            org.wheatgenetics.coordinate.R.string.ProjectDeleterConfirmationMessage,
            org.wheatgenetics.coordinate.R.string.ProjectDeleterProjectSuccessToast,
            org.wheatgenetics.coordinate.R.string.ProjectDeleterProjectFailToast   );
        this.handler = handler;
    }

    // region Overridden Methods
    // region deleteStep3() Overridden Methods
    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @java.lang.Override boolean deleteMasterRecord()
    { return this.projectsTable().delete(this.id()); }

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @java.lang.Override boolean deleteStep3()
    {
        final boolean success = super.deleteStep3();
        if (success) this.handler.respondToDeletedProject(this.id());
        return success;
    }
    // endregion

    // region deleteStep2() Overridden Method
    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @java.lang.Override @androidx.annotation.Nullable
    org.wheatgenetics.coordinate.model.BaseJoinedGridModels loadDetailRecords()
    { return this.gridsTable().loadByProjectId(this.id()); }
    // endregion

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @java.lang.Override boolean detailRecordsExists()
    { return this.gridsTable().existsInProject(this.id()); }
    // endregion

    public void delete(@androidx.annotation.Nullable final
    org.wheatgenetics.coordinate.model.ProjectModel projectModel) { super.delete(projectModel); }
}