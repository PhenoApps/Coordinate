package org.wheatgenetics.coordinate.tc.exclude.adapter;

import android.content.Context;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.wheatgenetics.coordinate.model.Cell;
import org.wheatgenetics.coordinate.model.DisplayModel;
import org.wheatgenetics.coordinate.model.ElementModel;

public class Adapter extends org.wheatgenetics.coordinate.display.adapter.Adapter {
    @NonNull
    private final
    DataViewHolder.TemplateHandler
            templateHandler;

    public Adapter(
            @NonNull final DisplayModel displayModel,
            @NonNull final Context context,
            @NonNull final
            DataViewHolder.Handler handler,
            @NonNull final
            DataViewHolder.TemplateHandler
                    templateHandler) {
        super(context, displayModel, handler);
        this.templateHandler = templateHandler;
    }

    // region Overridden Methods
    @Override
    @NonNull
    protected org.wheatgenetics.coordinate.display.adapter.DataViewHolder dataViewHolder(
            @NonNull final ImageView itemView) {
        return new DataViewHolder(
                itemView, this.getHandler(), this.templateHandler);
    }

    @Override
    protected void bind(
            @NonNull final org.wheatgenetics.coordinate.display.adapter.DataViewHolder
                    dataViewHolder,
            @Nullable final ElementModel
                    elementModel) {
        ((DataViewHolder) dataViewHolder).bind(
                (Cell) elementModel);
    }
    // endregion
}