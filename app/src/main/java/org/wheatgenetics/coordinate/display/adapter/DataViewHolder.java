package org.wheatgenetics.coordinate.display.adapter;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

import org.wheatgenetics.coordinate.model.ElementModel;

public abstract class DataViewHolder extends ViewHolder {
    // region Fields
    @NonNull
    private final DataViewHolder.Handler handler;
    @Nullable
    protected ElementModel
            elementModel = null;
    private View.OnClickListener onClickListenerInstance = null;           // lazy load

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    protected DataViewHolder(
            @NonNull final ImageView itemView,
            @NonNull final
            DataViewHolder.Handler handler) {
        super(itemView);
        this.handler = handler;
    }
    // endregion

    // region Private Methods
    @NonNull
    private View.OnClickListener onClickListener() {
        if (null == this.onClickListenerInstance) this.onClickListenerInstance =
                new View.OnClickListener() {
                    @Override
                    public void onClick(final View view) {
                        DataViewHolder.this.respondToClick();
                    }
                };
        return this.onClickListenerInstance;
    }

    private void setOnClickListener(@Nullable final View.OnClickListener onClickListener) {
        this.itemView.setOnClickListener(onClickListener);
    }
    // endregion

    // region Protected Methods
    // region setImage() Protected Methods
    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    protected void setImage(@DrawableRes final int resId) {
        ((ImageView) this.itemView).setImageResource(resId);
    }

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    protected abstract void setImage();
    // endregion

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    protected void setElementModelAndImage(@Nullable final ElementModel elementModel) {
        this.elementModel = elementModel;
        this.setImage();
    }

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    protected void toggle() {
        this.handler.toggle(this.elementModel);
        this.setImage();
    }

    // region onClickListener Protected Methods
    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    protected abstract void respondToClick();

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    protected void setOnClickListener() {
        this.setOnClickListener(this.onClickListener());
    }

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    protected void clearOnClickListener() {
        this.setOnClickListener(null);
    }
    // endregion
    // endregion

    @SuppressWarnings({"UnnecessaryInterfaceModifier"})
    public interface Handler {
        public abstract void toggle(
                @Nullable ElementModel elementModel);
    }
}