package org.wheatgenetics.coordinate.grids;

/**
 * Uses:
 * android.app.Activity
 * android.view.View.OnClickListener
 *
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 * androidx.annotation.RestrictTo
 * androidx.annotation.RestrictTo.Scope
 *
 * org.wheatgenetics.coordinate.model.BaseJoinedGridModels
 *
 * org.wheatgenetics.coordinate.grids.GridsAdapter
 */
class AllGridsAdapter extends org.wheatgenetics.coordinate.grids.GridsAdapter
{
    private org.wheatgenetics.coordinate.model.BaseJoinedGridModels
        baseJoinedGridModelsInstance = null;                                            // lazy load

    AllGridsAdapter(
    @androidx.annotation.NonNull final android.app.Activity              activity,
    @androidx.annotation.NonNull final android.view.View.OnClickListener
        onDeleteButtonClickListener) { super(activity, onDeleteButtonClickListener); }

    // region Overridden Methods
    @java.lang.Override public void notifyDataSetChanged()
    { this.baseJoinedGridModelsInstance = null; super.notifyDataSetChanged(); }

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @java.lang.Override @androidx.annotation.Nullable
    org.wheatgenetics.coordinate.model.BaseJoinedGridModels baseJoinedGridModels()
    {
        if (null == this.baseJoinedGridModelsInstance)
            this.baseJoinedGridModelsInstance = this.gridsTable().load();
        return this.baseJoinedGridModelsInstance;
    }
    // endregion
}