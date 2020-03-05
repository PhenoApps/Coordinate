package org.wheatgenetics.coordinate.griddisplay.adapter;

/**
 * Uses:
 * android.content.Context
 * android.widget.ImageView
 *
 * androidx.annotation.IntRange
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 * androidx.annotation.RestrictTo
 * androidx.annotation.RestrictTo.Scope
 *
 * org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.Checker
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
    // region Constructor Fields
    @androidx.annotation.NonNull private final android.content.Context context;
    @androidx.annotation.NonNull private final
        org.wheatgenetics.coordinate.griddisplay.adapter.DataViewHolder.GridHandler gridHandler;
    @androidx.annotation.Nullable private final
        org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.Checker checker;
    // endregion

    private int activeRow, activeCol;
    // endregion

    private void activate(@androidx.annotation.NonNull final
    org.wheatgenetics.coordinate.griddisplay.adapter.DataViewHolder dataViewHolder)
    {
        @androidx.annotation.IntRange(from = -1)
        final int newActiveRow = dataViewHolder.getRow(), newActiveCol = dataViewHolder.getCol();
        if (newActiveRow != this.activeRow || newActiveCol != this.activeCol)
        {
            this.notifyDataSetChanged(newActiveRow, newActiveCol);
            this.gridHandler.activate(dataViewHolder);
        }
    }

    public Adapter(
    @androidx.annotation.NonNull final org.wheatgenetics.coordinate.model.DisplayModel displayModel,
    @androidx.annotation.NonNull final android.content.Context                         context     ,
                                 final int activeRow, final int activeCol,
    @androidx.annotation.NonNull final
        org.wheatgenetics.coordinate.griddisplay.adapter.DataViewHolder.Handler handler,
    @androidx.annotation.NonNull final
        org.wheatgenetics.coordinate.griddisplay.adapter.DataViewHolder.GridHandler gridHandler,
    @androidx.annotation.Nullable final
        org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.Checker checker)
    {
        super(displayModel, handler);

        this.context     = context    ; this.activeRow = activeRow; this.activeCol = activeCol;
        this.gridHandler = gridHandler;                             this.checker   = checker  ;
    }

    // region Overridden Methods
    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @java.lang.Override @androidx.annotation.NonNull
    protected org.wheatgenetics.coordinate.display.adapter.DataViewHolder dataViewHolder(
    @androidx.annotation.NonNull final android.widget.ImageView itemView)
    {
        return new org.wheatgenetics.coordinate.griddisplay.adapter.DataViewHolder(
            itemView, this.context, this.getHandler(),
            new org.wheatgenetics.coordinate.griddisplay.adapter.DataViewHolder.GridHandler()
            {
                @java.lang.Override public void activate(@androidx.annotation.NonNull final
                org.wheatgenetics.coordinate.griddisplay.adapter.DataViewHolder dataViewHolder)
                {
                    org.wheatgenetics.coordinate.griddisplay.adapter.Adapter.this.activate(
                        dataViewHolder);
                }
            }, this.checker);
    }

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
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

    public void notifyDataSetChanged(final int activeRow, final int activeCol)
    { this.activeRow = activeRow; this.activeCol = activeCol; this.notifyDataSetChanged(); }
}