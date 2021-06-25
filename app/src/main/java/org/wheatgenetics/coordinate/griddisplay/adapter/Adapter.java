package org.wheatgenetics.coordinate.griddisplay.adapter;

import android.content.Context;
import android.widget.ImageView;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

import org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel;
import org.wheatgenetics.coordinate.model.DisplayModel;
import org.wheatgenetics.coordinate.model.ElementModel;
import org.wheatgenetics.coordinate.model.EntryModel;

public class Adapter extends org.wheatgenetics.coordinate.display.adapter.Adapter {
    // region Fields
    // region Constructor Fields
    @NonNull
    private final
    DataViewHolder.GridHandler gridHandler;
    @Nullable
    private final
    CheckedIncludedEntryModel.Checker checker;
    // endregion

    private int activeRow, activeCol;
    // endregion

    public Adapter(
            @NonNull final DisplayModel displayModel,
            @NonNull final Context context,
            final int activeRow, final int activeCol,
            @NonNull final
            DataViewHolder.Handler handler,
            @NonNull final
            DataViewHolder.GridHandler gridHandler,
            @Nullable final
            CheckedIncludedEntryModel.Checker checker) {
        super(context, displayModel, handler);

        this.activeRow = activeRow;
        this.activeCol = activeCol;
        this.gridHandler = gridHandler;
        this.checker = checker;
    }

    private void activate(@NonNull final
                          DataViewHolder dataViewHolder) {
        @IntRange(from = -1) final int newActiveRow = dataViewHolder.getRow(), newActiveCol = dataViewHolder.getCol();
        if (newActiveRow != this.activeRow || newActiveCol != this.activeCol) {
            this.notifyDataSetChanged(newActiveRow, newActiveCol);
            this.gridHandler.activate(dataViewHolder);
        }
    }

    // region Overridden Methods
    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    @Override
    @NonNull
    protected org.wheatgenetics.coordinate.display.adapter.DataViewHolder dataViewHolder(
            @NonNull final ImageView itemView) {
        return new DataViewHolder(
                itemView, this.getContext(), this.getHandler(),
                new DataViewHolder.GridHandler() {
                    @Override
                    public void activate(@NonNull final
                                         DataViewHolder dataViewHolder) {
                        Adapter.this.activate(
                                dataViewHolder);
                    }
                }, this.checker);
    }

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    @Override
    protected void bind(
            @NonNull final org.wheatgenetics.coordinate.display.adapter.DataViewHolder
                    dataViewHolder,
            @Nullable final ElementModel
                    elementModel) {
        ((DataViewHolder) dataViewHolder).bind(
                (EntryModel) elementModel,
                this.activeRow, this.activeCol);
    }
    // endregion

    public void notifyDataSetChanged(final int activeRow, final int activeCol) {
        this.activeRow = activeRow;
        this.activeCol = activeCol;
        this.notifyDataSetChanged();
    }
}