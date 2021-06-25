package org.wheatgenetics.coordinate.tc.exclude.adapter;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

import org.wheatgenetics.coordinate.R;
import org.wheatgenetics.coordinate.model.Cell;

public class DataViewHolder extends org.wheatgenetics.coordinate.display.adapter.DataViewHolder {
    @NonNull
    private final
    DataViewHolder.TemplateHandler
            templateHandler;

    DataViewHolder(
            @NonNull final ImageView itemView,
            @NonNull final
            DataViewHolder.Handler handler,
            @NonNull final
            DataViewHolder.TemplateHandler
                    templateHandler) {
        super(itemView, handler);
        this.templateHandler = templateHandler;
        this.setOnClickListener();
    }

    private boolean isExcluded() {
        return this.templateHandler.isExcluded(
                (Cell) this.elementModel);
    }

    // region Overridden Methods
    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    @Override
    protected void setImage() {
        this.setImage(this.isExcluded() ?
                R.drawable.excluded_entry :
                R.drawable.empty_included_entry);
    }

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    @Override
    protected void respondToClick() {
        this.toggle();
    }

    void bind(@Nullable final Cell cell) {
        this.setElementModelAndImage(cell);
    }
    // endregion

    @SuppressWarnings({"UnnecessaryInterfaceModifier"})
    public interface TemplateHandler {
        public abstract boolean isExcluded(Cell cell);
    }
}