package org.wheatgenetics.coordinate.grids;

/**
 * Uses:
 * android.app.Activity
 *
 * androidx.annotation.IntRange
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 * androidx.annotation.RestrictTo
 * androidx.annotation.RestrictTo.Scope
 *
 * org.wheatgenetics.coordinate.model.BaseJoinedGridModels
 *
 * org.wheatgenetics.coordinate.grids.GridsAdapter
 */
class ProjectGridsAdapter extends org.wheatgenetics.coordinate.grids.GridsAdapter
{
    // region Fields
    @androidx.annotation.IntRange(from = 1) private final long projectId;
    private org.wheatgenetics.coordinate.model.BaseJoinedGridModels
        baseJoinedGridModelsInstance = null;                                            // lazy load
    // endregion

    ProjectGridsAdapter(@androidx.annotation.NonNull final android.app.Activity activity,
    @androidx.annotation.IntRange(from = 1) final long projectId)
    { super(activity); this.projectId = projectId; }

    // region Overridden Methods
    @java.lang.Override public void notifyDataSetChanged()
    { this.baseJoinedGridModelsInstance = null; super.notifyDataSetChanged(); }

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @java.lang.Override @androidx.annotation.Nullable
    org.wheatgenetics.coordinate.model.BaseJoinedGridModels baseJoinedGridModels()
    {
        if (null == this.baseJoinedGridModelsInstance)
            this.baseJoinedGridModelsInstance = this.gridsTable().loadByProjectId(this.projectId);
        return this.baseJoinedGridModelsInstance;
    }
    // endregion
}