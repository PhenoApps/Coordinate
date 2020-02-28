package org.wheatgenetics.coordinate.grids;

/**
 * Uses:
 * android.app.Activity activity
 * android.view.View.OnClickListener
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
class TemplateGridsAdapter extends org.wheatgenetics.coordinate.grids.GridsAdapter
{
    // region Fields
    @androidx.annotation.IntRange(from = 1) private final long templateId;
    private org.wheatgenetics.coordinate.model.BaseJoinedGridModels
        baseJoinedGridModelsInstance = null;                                            // lazy load
    // endregion

    TemplateGridsAdapter(
    @androidx.annotation.NonNull            final android.app.Activity              activity  ,
    @androidx.annotation.IntRange(from = 1) final long                              templateId,
    @androidx.annotation.NonNull            final android.view.View.OnClickListener
        onDeleteButtonClickListener,
    @androidx.annotation.NonNull final android.view.View.OnClickListener
        onExportButtonClickListener)
    {
        super(activity, onDeleteButtonClickListener, onExportButtonClickListener);
        this.templateId = templateId;
    }

    // region Overridden Methods
    @java.lang.Override public void notifyDataSetChanged()
    { this.baseJoinedGridModelsInstance = null; super.notifyDataSetChanged(); }

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @java.lang.Override @androidx.annotation.Nullable
    org.wheatgenetics.coordinate.model.BaseJoinedGridModels baseJoinedGridModels()
    {
        if (null == this.baseJoinedGridModelsInstance)
            this.baseJoinedGridModelsInstance = this.gridsTable().loadByTemplateId(this.templateId);
        return this.baseJoinedGridModelsInstance;
    }
    // endregion
}