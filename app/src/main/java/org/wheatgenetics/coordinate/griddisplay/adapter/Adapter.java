package org.wheatgenetics.coordinate.griddisplay.adapter;

/**
 * Uses:
 * android.widget.ImageView
 *
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 *
 * org.wheatgenetics.coordinate.model.DisplayModel
 * org.wheatgenetics.coordinate.model.ElementModel
 * org.wheatgenetics.coordinate.model.EntryModel
 *
 * org.wheatgenetics.coordinate.display.adapter.Adapter
 * org.wheatgenetics.coordinate.display.adapter.DataViewHolder
 *
 * org.wheatgenetics.coordinate.griddisplay.adapter.DataViewHolder
 * org.wheatgenetics.coordinate.griddisplay.adapter.DataViewHolder.Handler
 * org.wheatgenetics.coordinate.griddisplay.adapter.DataViewHolder.GridHandler
 */
public class Adapter extends org.wheatgenetics.coordinate.display.adapter.Adapter
{
    // region Fields
    @androidx.annotation.NonNull private final
        org.wheatgenetics.coordinate.griddisplay.adapter.DataViewHolder.GridHandler gridHandler;

    private int activeRow, activeCol;
    // endregion

    public Adapter(
    @androidx.annotation.NonNull final org.wheatgenetics.coordinate.model.DisplayModel displayModel,
                                 final int activeRow, final int activeCol,
    @androidx.annotation.NonNull final
        org.wheatgenetics.coordinate.griddisplay.adapter.DataViewHolder.Handler handler,
    @androidx.annotation.NonNull final
        org.wheatgenetics.coordinate.griddisplay.adapter.DataViewHolder.GridHandler gridHandler)
    {
        super(displayModel, handler);
        this.gridHandler = gridHandler; this.activeRow = activeRow; this.activeCol = activeCol;
    }

    // region Overridden Methods
    @java.lang.Override @androidx.annotation.NonNull
    protected org.wheatgenetics.coordinate.display.adapter.DataViewHolder dataViewHolder(
    @androidx.annotation.NonNull final android.widget.ImageView itemView)
    {
        return new org.wheatgenetics.coordinate.griddisplay.adapter.DataViewHolder(
            itemView, this.getHandler(), this.gridHandler);
    }

    @java.lang.Override protected void bind(
    @androidx.annotation.NonNull final org.wheatgenetics.coordinate.display.adapter.DataViewHolder
        dataViewHolder,
    @androidx.annotation.Nullable final org.wheatgenetics.coordinate.model.ElementModel
        elementModel)
    {
        ((org.wheatgenetics.coordinate.griddisplay.adapter.DataViewHolder) dataViewHolder).bind(
            (org.wheatgenetics.coordinate.model.EntryModel) elementModel,
            this.activeRow, this.activeCol);
    }
    // endregion
}