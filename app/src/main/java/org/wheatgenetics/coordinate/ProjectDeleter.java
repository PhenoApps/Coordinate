package org.wheatgenetics.coordinate;

/**
 * Uses:
 * android.content.Context
 *
 * androidx.annotation.IntRange
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 *
 * org.wheatgenetics.androidlibrary.Utils
 *
 * org.wheatgenetics.coordinate.database.EntriesTable
 * org.wheatgenetics.coordinate.database.GridsTable
 * org.wheatgenetics.coordinate.database.ProjectsTable
 *
 * org.wheatgenetics.coordinate.model.BaseJoinedGridModels
 * org.wheatgenetics.coordinate.model.BaseJoinedGridModels.Processor
 * org.wheatgenetics.coordinate.model.JoinedGridModel
 * org.wheatgenetics.coordinate.model.ProjectModel
 *
 * org.wheatgenetics.coordinate.R
 * org.wheatgenetics.coordinate.Utils
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
public class ProjectDeleter extends java.lang.Object
implements org.wheatgenetics.coordinate.model.BaseJoinedGridModels.Processor
{
    @java.lang.SuppressWarnings({"UnnecessaryInterfaceModifier"}) public interface Handler
    { public abstract void respondToDeletedProject(); }

    // region Fields
    @androidx.annotation.NonNull private final android.content.Context context;
    @androidx.annotation.NonNull private final
        org.wheatgenetics.coordinate.ProjectDeleter.Handler handler;

    @androidx.annotation.IntRange(from = 1) private long projectId;

    // region Table Fields
    private org.wheatgenetics.coordinate.database.GridsTable    gridsTableInstance    = null;  // ll
    private org.wheatgenetics.coordinate.database.EntriesTable  entriesTableInstance  = null;  // ll
    private org.wheatgenetics.coordinate.database.ProjectsTable projectsTableInstance = null;  // ll
    // endregion
    // endregion

    // region Private Methods
    // region Table Private Methods
    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.database.GridsTable gridsTable()
    {
        if (null == this.gridsTableInstance) this.gridsTableInstance =
            new org.wheatgenetics.coordinate.database.GridsTable(this.context);
        return this.gridsTableInstance;
    }

    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.database.EntriesTable entriesTable()
    {
        if (null == this.entriesTableInstance) this.entriesTableInstance =
            new org.wheatgenetics.coordinate.database.EntriesTable(this.context);
        return this.entriesTableInstance;
    }

    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.database.ProjectsTable projectsTable()
    {
        if (null == this.projectsTableInstance) this.projectsTableInstance =
            new org.wheatgenetics.coordinate.database.ProjectsTable(this.context);
        return this.projectsTableInstance;
    }
    // endregion

    // region Toast Private Methods
    // region Long Toast Private Methods
    private void showLongToast(final java.lang.String text)
    { org.wheatgenetics.androidlibrary.Utils.showLongToast(this.context, text); }

    private void showLongToast(@androidx.annotation.StringRes final int text)
    { this.showLongToast(this.context.getString(text)); }
    // endregion

    // region Short Toast Private Methods
    private void showShortToast(final java.lang.String text)
    { org.wheatgenetics.androidlibrary.Utils.showShortToast(this.context, text); }

    private void showShortToast(@androidx.annotation.StringRes final int text)
    { this.showShortToast(this.context.getString(text)); }
    // endregion
    // endregion

    private void deleteProjectStep3()
    {
        final boolean success = this.projectsTable().delete(this.projectId);
        if (success)
        {
            this.showLongToast(
                org.wheatgenetics.coordinate.R.string.ProjectDeleterProjectSuccessToast);
            this.handler.respondToDeletedProject();
        }
        else this.showLongToast(
            org.wheatgenetics.coordinate.R.string.ProjectDeleterProjectFailToast);
    }

    private void deleteProjectStep2()
    {
        {
            final org.wheatgenetics.coordinate.model.BaseJoinedGridModels baseJoinedGridModels =
                this.gridsTable().loadByProjectId(this.projectId);
            if (null != baseJoinedGridModels)
                baseJoinedGridModels.processAll(this);                   // delete entries
        }

        if (this.gridsTable().deleteByProjectId(this.projectId))                   // delete grids
            this.showShortToast(
                org.wheatgenetics.coordinate.R.string.ProjectDeleterGridsSuccessToast);
        else
            this.showShortToast(org.wheatgenetics.coordinate.R.string.ProjectDeleterGridsFailToast);

        this.deleteProjectStep3();
    }
    // endregion

    public ProjectDeleter(
    @androidx.annotation.NonNull final android.content.Context                             context,
    @androidx.annotation.NonNull final org.wheatgenetics.coordinate.ProjectDeleter.Handler handler)
    { super(); this.context = context; this.handler = handler; }

    // region org.wheatgenetics.coordinate.model.BaseJoinedGridModels.Processor Overridden Method
    @java.lang.Override
    public void process(final org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel)
    { if (null != joinedGridModel) this.entriesTable().deleteByGridId(joinedGridModel.getId()); }
    // endregion

    // region Public Methods
    public void delete(@androidx.annotation.IntRange(from = 1) final long projectId)
    {
        this.projectId = projectId;
        if (this.gridsTable().existsInProject(this.projectId))
            org.wheatgenetics.coordinate.Utils.confirm(
                /* context => */ this.context,
                /* title   => */
                    org.wheatgenetics.coordinate.R.string.ProjectDeleterConfirmationTitle,
                /* message => */
                    org.wheatgenetics.coordinate.R.string.ProjectDeleterConfirmationMessage,
                /* yesRunnable => */ new java.lang.Runnable()
                    {
                        @java.lang.Override public void run()
                        { org.wheatgenetics.coordinate.ProjectDeleter.this.deleteProjectStep2(); }
                    });
        else this.deleteProjectStep3();
    }

    public void delete(@androidx.annotation.Nullable
    final org.wheatgenetics.coordinate.model.ProjectModel projectModel)
    { if (null != projectModel) this.delete(projectModel.getId()); }
    // endregion
}