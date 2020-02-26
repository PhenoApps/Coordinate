package org.wheatgenetics.coordinate.display.adapter;

/**
 * Uses:
 * android.view.View
 * android.view.View.OnClickListener
 * android.widget.ImageView
 *
 * androidx.annotation.DrawableRes
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 * androidx.annotation.RestrictTo
 * androidx.annotation.RestrictTo.Scope
 *
 * org.wheatgenetics.coordinate.model.ElementModel
 *
 * org.wheatgenetics.coordinate.display.adapter.ViewHolder
 */
public abstract class DataViewHolder extends org.wheatgenetics.coordinate.display.adapter.ViewHolder
{
    @java.lang.SuppressWarnings({"UnnecessaryInterfaceModifier"}) public interface Handler
    {
        @androidx.annotation.Nullable
        public abstract org.wheatgenetics.coordinate.model.ElementModel toggle(
        @androidx.annotation.Nullable org.wheatgenetics.coordinate.model.ElementModel elementModel);
    }

    // region Fields
    @androidx.annotation.NonNull
    private final org.wheatgenetics.coordinate.display.adapter.DataViewHolder.Handler handler;

    @androidx.annotation.Nullable protected org.wheatgenetics.coordinate.model.ElementModel
        elementModel = null;

    private android.view.View.OnClickListener onClickListenerInstance = null;           // lazy load
    // endregion

    // region Private Methods
    @androidx.annotation.NonNull private android.view.View.OnClickListener onClickListener()
    {
        if (null == this.onClickListenerInstance) this.onClickListenerInstance =
            new android.view.View.OnClickListener()
            {
                @java.lang.Override public void onClick(final android.view.View view)
                {
                    org.wheatgenetics.coordinate.display.adapter
                        .DataViewHolder.this.respondToClick();
                }
            };
        return this.onClickListenerInstance;
    }

    private void setOnClickListener(@androidx.annotation.Nullable
    final android.view.View.OnClickListener onClickListener)
    { this.itemView.setOnClickListener(onClickListener); }
    // endregion

    // region Protected Methods
    // region setImage() Protected Methods
    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    protected void setImage(@androidx.annotation.DrawableRes final int resId)
    { ((android.widget.ImageView) this.itemView).setImageResource(resId); }

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    protected abstract void setImage();
    // endregion

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    protected void setElementModelAndImage(@androidx.annotation.Nullable
    final org.wheatgenetics.coordinate.model.ElementModel elementModel)
    { this.elementModel = elementModel; this.setImage(); }

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    protected void toggle()
    { this.setElementModelAndImage(this.handler.toggle(this.elementModel)); }

    // region onClickListener Protected Methods
    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    protected abstract void respondToClick();

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    protected void setOnClickListener() { this.setOnClickListener(this.onClickListener()); }

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    protected void clearOnClickListener() { this.setOnClickListener(null); }
    // endregion
    // endregion

    public DataViewHolder(
    @androidx.annotation.NonNull final android.widget.ImageView itemView,
    @androidx.annotation.NonNull final
        org.wheatgenetics.coordinate.display.adapter.DataViewHolder.Handler handler )
    { super(itemView); this.handler = handler; }
}