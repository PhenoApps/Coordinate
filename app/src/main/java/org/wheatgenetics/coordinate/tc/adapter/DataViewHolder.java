package org.wheatgenetics.coordinate.tc.adapter;

/**
 * Uses:
 * android.widget.ImageView
 *
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 * androidx.annotation.RestrictTo
 * androidx.annotation.RestrictTo.Scope
 *
 * org.wheatgenetics.coordinate.R
 *
 * org.wheatgenetics.coordinate.model.Cell
 *
 * org.wheatgenetics.coordinate.display.adapter.DataViewHolder
 */
public class DataViewHolder extends org.wheatgenetics.coordinate.display.adapter.DataViewHolder
{
    @java.lang.SuppressWarnings({"UnnecessaryInterfaceModifier"}) public interface TemplateHandler
    { public abstract boolean isExcluded(org.wheatgenetics.coordinate.model.Cell cell); }

    @androidx.annotation.NonNull private final
        org.wheatgenetics.coordinate.tc.adapter.DataViewHolder.TemplateHandler templateHandler;

    private boolean isExcluded()
    {
        return this.templateHandler.isExcluded(
            (org.wheatgenetics.coordinate.model.Cell) this.elementModel);
    }

    DataViewHolder(
    @androidx.annotation.NonNull final android.widget.ImageView itemView,
    @androidx.annotation.NonNull final
        org.wheatgenetics.coordinate.tc.adapter.DataViewHolder.Handler handler,
    @androidx.annotation.NonNull final
        org.wheatgenetics.coordinate.tc.adapter.DataViewHolder.TemplateHandler templateHandler)
    { super(itemView, handler); this.templateHandler = templateHandler; this.setOnClickListener(); }

    // region Overridden Methods
    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @java.lang.Override protected void setImage()
    {
        this.setImage(this.isExcluded() ?
            org.wheatgenetics.coordinate.R.drawable.excluded_entry       :
            org.wheatgenetics.coordinate.R.drawable.empty_included_entry);
    }

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @java.lang.Override protected void respondToClick() { this.toggle(); }
    // endregion

    void bind(@androidx.annotation.Nullable final org.wheatgenetics.coordinate.model.Cell cell)
    { this.setElementModelAndImage(cell); }
}