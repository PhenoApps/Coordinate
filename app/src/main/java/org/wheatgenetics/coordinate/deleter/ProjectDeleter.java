package org.wheatgenetics.coordinate.deleter;

/**
 * Uses:
 * android.content.Context
 *
 * androidx.annotation.IntRange
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 * androidx.annotation.StringRes
 *
 * org.wheatgenetics.androidlibrary.Utils
 *
 * org.wheatgenetics.coordinate.R
 * org.wheatgenetics.coordinate.Utils
 *
 * org.wheatgenetics.coordinate.database.GridsTable
 * org.wheatgenetics.coordinate.database.ProjectsTable
 *
 * org.wheatgenetics.coordinate.model.BaseJoinedGridModels
 * org.wheatgenetics.coordinate.model.BaseJoinedGridModels.Processor
 * org.wheatgenetics.coordinate.model.JoinedGridModel
 * org.wheatgenetics.coordinate.model.ProjectModel
 *
 * org.wheatgenetics.coordinate.deleter.PackageGridDeleter
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
public class ProjectDeleter extends java.lang.Object
implements org.wheatgenetics.coordinate.model.BaseJoinedGridModels.Processor
{
    @java.lang.SuppressWarnings({"UnnecessaryInterfaceModifier"}) public interface Handler
    {
        public abstract void respondToDeletedProject(
        @androidx.annotation.IntRange(from = 1) long projectId);
    }

    // region Fields
    @androidx.annotation.NonNull private final android.content.Context context;
    @androidx.annotation.NonNull private final
        org.wheatgenetics.coordinate.deleter.ProjectDeleter.Handler handler;

    @androidx.annotation.IntRange(from = 1) private long projectId;

    // region Table Fields
    private org.wheatgenetics.coordinate.database.GridsTable    gridsTableInstance    = null;  // ll
    private org.wheatgenetics.coordinate.database.ProjectsTable projectsTableInstance = null;  // ll
    // endregion

    private boolean                                                 atLeastOneGridWasDeleted;
    private org.wheatgenetics.coordinate.deleter.PackageGridDeleter
        packageGridDeleterInstance = null;                                              // lazy load
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
    private org.wheatgenetics.coordinate.database.ProjectsTable projectsTable()
    {
        if (null == this.projectsTableInstance) this.projectsTableInstance =
            new org.wheatgenetics.coordinate.database.ProjectsTable(this.context);
        return this.projectsTableInstance;
    }
    // endregion

    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.deleter.PackageGridDeleter packageGridDeleter()
    {
        if (null == this.packageGridDeleterInstance) this.packageGridDeleterInstance =
            new org.wheatgenetics.coordinate.deleter.PackageGridDeleter(this.context);
        return this.packageGridDeleterInstance;
    }

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
        {
            @androidx.annotation.StringRes final int text = success ?
                org.wheatgenetics.coordinate.R.string.ProjectDeleterProjectSuccessToast :
                org.wheatgenetics.coordinate.R.string.ProjectDeleterProjectFailToast    ;
            this.showLongToast(text);
        }
        if (success) this.handler.respondToDeletedProject(this.projectId);
    }

    private void deleteProjectStep2()
    {
        {
            final org.wheatgenetics.coordinate.model.BaseJoinedGridModels baseJoinedGridModels =
                this.gridsTable().loadByProjectId(this.projectId);
            if (null != baseJoinedGridModels)
            {
                this.atLeastOneGridWasDeleted = false;
                baseJoinedGridModels.processAll(this);

                {
                    @androidx.annotation.StringRes final int text = this.atLeastOneGridWasDeleted ?
                        org.wheatgenetics.coordinate.R.string.DeleterGridsSuccessToast :
                        org.wheatgenetics.coordinate.R.string.DeleterGridsFailToast    ;
                    this.showShortToast(text);
                }
            }
        }
        this.deleteProjectStep3();
    }
    // endregion

    public ProjectDeleter(@androidx.annotation.NonNull final android.content.Context context,
    @androidx.annotation.NonNull final org.wheatgenetics.coordinate.deleter.ProjectDeleter.Handler
        handler)
    { super(); this.context = context; this.handler = handler; }

    // region org.wheatgenetics.coordinate.model.BaseJoinedGridModels.Processor Overridden Method
    @java.lang.Override
    public void process(final org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel)
    {
        if (null != joinedGridModel)
        {
            final boolean gridWasDeleted =
                this.packageGridDeleter().delete(joinedGridModel.getId());
            if (!this.atLeastOneGridWasDeleted && gridWasDeleted)
                this.atLeastOneGridWasDeleted = true;
        }
    }
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
                        {
                            org.wheatgenetics.coordinate.deleter
                                .ProjectDeleter.this.deleteProjectStep2();
                        }
                    });
        else this.deleteProjectStep3();
    }

    public void delete(@androidx.annotation.Nullable
    final org.wheatgenetics.coordinate.model.ProjectModel projectModel)
    { if (null != projectModel) this.delete(projectModel.getId()); }
    // endregion
}