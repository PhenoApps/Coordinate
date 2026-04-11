package org.wheatgenetics.coordinate.grids;

import android.app.Activity;
import android.view.View;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

import org.wheatgenetics.coordinate.model.BaseJoinedGridModels;

class TemplateGridsAdapter extends GridsAdapter {
    // region Fields
    @IntRange(from = 1)
    private final long templateId;
    private BaseJoinedGridModels
            baseJoinedGridModelsInstance = null;                                            // lazy load
    // endregion

    TemplateGridsAdapter(
            @NonNull final Activity activity,
            @IntRange(from = 1) final long templateId,
            @NonNull final View.OnClickListener
                    onCollectDataButtonClickListener,
            @NonNull final View.OnClickListener
                    onDeleteButtonClickListener,
            @NonNull final View.OnClickListener
                    onExportButtonClickListener) {
        super(activity, onCollectDataButtonClickListener,
                onDeleteButtonClickListener, onExportButtonClickListener);
        this.templateId = templateId;
    }

    // region Overridden Methods
    @Override
    public void notifyDataSetChanged() {
        this.baseJoinedGridModelsInstance = null;
        super.notifyDataSetChanged();
    }

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    @Override
    @Nullable
    BaseJoinedGridModels baseJoinedGridModels() {
        if (null == this.baseJoinedGridModelsInstance) {
            this.baseJoinedGridModelsInstance = this.gridsTable().loadByTemplateId(this.templateId);
            applySort(this.baseJoinedGridModelsInstance);
        }
        return this.baseJoinedGridModelsInstance;
    }
    // endregion
}