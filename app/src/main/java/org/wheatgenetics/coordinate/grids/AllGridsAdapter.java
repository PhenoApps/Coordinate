package org.wheatgenetics.coordinate.grids;

import android.app.Activity;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

import org.wheatgenetics.coordinate.model.BaseJoinedGridModels;

class AllGridsAdapter extends GridsAdapter {
    private BaseJoinedGridModels
            baseJoinedGridModelsInstance = null;                                            // lazy load

    AllGridsAdapter(
            @NonNull final Activity activity,
            @NonNull final View.OnClickListener
                    onCollectDataButtonClickListener,
            @NonNull final View.OnClickListener
                    onDeleteButtonClickListener,
            @NonNull final View.OnClickListener
                    onExportButtonClickListener) {
        super(activity, onCollectDataButtonClickListener,
                onDeleteButtonClickListener, onExportButtonClickListener);
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
            this.baseJoinedGridModelsInstance = this.gridsTable().load();
            applySort(this.baseJoinedGridModelsInstance);
        }
        return this.baseJoinedGridModelsInstance;
    }
    // endregion
}