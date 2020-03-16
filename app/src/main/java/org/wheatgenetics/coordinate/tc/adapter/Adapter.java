package org.wheatgenetics.coordinate.tc.adapter;

/**
 * Uses:
 * android.content.Context
 * android.widget.ImageView
 *
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 *
 * org.wheatgenetics.coordinate.model.Cell
 * org.wheatgenetics.coordinate.model.DisplayModel
 * org.wheatgenetics.coordinate.model.ElementModel
 *
 * org.wheatgenetics.coordinate.display.adapter.Adapter
 * org.wheatgenetics.coordinate.display.adapter.DataViewHolder
 *
 * org.wheatgenetics.coordinate.tc.adapter.DataViewHolder
 * org.wheatgenetics.coordinate.tc.adapter.DataViewHolder.Handler
 * org.wheatgenetics.coordinate.tc.adapter.DataViewHolder.TemplateHandler
 */
public class Adapter extends org.wheatgenetics.coordinate.display.adapter.Adapter
{
    @androidx.annotation.NonNull private final
        org.wheatgenetics.coordinate.tc.adapter.DataViewHolder.TemplateHandler templateHandler;

    public Adapter(
    @androidx.annotation.NonNull final org.wheatgenetics.coordinate.model.DisplayModel displayModel,
    @androidx.annotation.NonNull final android.content.Context                         context     ,
    @androidx.annotation.NonNull final
        org.wheatgenetics.coordinate.tc.adapter.DataViewHolder.Handler handler,
    @androidx.annotation.NonNull final
        org.wheatgenetics.coordinate.tc.adapter.DataViewHolder.TemplateHandler templateHandler)
    { super(context, displayModel, handler); this.templateHandler = templateHandler; }

    // region Overridden Methods
    @java.lang.Override @androidx.annotation.NonNull
    protected org.wheatgenetics.coordinate.display.adapter.DataViewHolder dataViewHolder(
    @androidx.annotation.NonNull final android.widget.ImageView itemView)
    {
        return new org.wheatgenetics.coordinate.tc.adapter.DataViewHolder(
            itemView, this.getHandler(), this.templateHandler);
    }

    @java.lang.Override protected void bind(
    @androidx.annotation.NonNull final org.wheatgenetics.coordinate.display.adapter.DataViewHolder
        dataViewHolder,
    @androidx.annotation.Nullable final org.wheatgenetics.coordinate.model.ElementModel
        elementModel)
    {
        ((org.wheatgenetics.coordinate.tc.adapter.DataViewHolder) dataViewHolder).bind(
            (org.wheatgenetics.coordinate.model.Cell) elementModel);
    }
    // endregion
}