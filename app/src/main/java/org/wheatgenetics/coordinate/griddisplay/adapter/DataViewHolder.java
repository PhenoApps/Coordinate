package org.wheatgenetics.coordinate.griddisplay.adapter;

/**
 * Uses:
 * android.widget.ImageView
 *
 * androidx.annotation.IntRange
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 * androidx.annotation.RestrictTo
 * androidx.annotation.RestrictTo.Scope
 *
 * org.wheatgenetics.coordinate.R
 *
 * org.wheatgenetics.coordinate.model.IncludedEntryModel
 * org.wheatgenetics.coordinate.model.EntryModel
 *
 * org.wheatgenetics.coordinate.display.adapter.DataViewHolder
 */
public class DataViewHolder extends org.wheatgenetics.coordinate.display.adapter.DataViewHolder
{
    @java.lang.SuppressWarnings({"UnnecessaryInterfaceModifier"}) public interface GridHandler
    {
        public abstract void activate(@androidx.annotation.NonNull
        org.wheatgenetics.coordinate.griddisplay.adapter.DataViewHolder dataViewHolder);
    }

    @androidx.annotation.NonNull private final
        org.wheatgenetics.coordinate.griddisplay.adapter.DataViewHolder.GridHandler gridHandler;

    // region Private Methods
    private boolean elementModelIsNotNull() { return null != this.elementModel; }

    private void activate()
    {
        this.setImage(org.wheatgenetics.coordinate.R.drawable.active_entry);
        this.gridHandler.activate(this);
    }
    // endregion

    DataViewHolder(
    @androidx.annotation.NonNull final android.widget.ImageView itemView,
    @androidx.annotation.NonNull final
        org.wheatgenetics.coordinate.griddisplay.adapter.DataViewHolder.Handler handler,
    @androidx.annotation.NonNull final
        org.wheatgenetics.coordinate.griddisplay.adapter.DataViewHolder.GridHandler gridHandler)
    { super(itemView, handler); this.gridHandler = gridHandler; }

    // region Overridden Methods
    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @java.lang.Override protected void setImage()
    {
        if (this.elementModelIsNotNull())
            this.setImage(((org.wheatgenetics.coordinate.model.EntryModel)
                this.elementModel).backgroundResource());
    }

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @java.lang.Override protected void respondToClick() { this.activate(); }
    // endregion

    void bind(
    @androidx.annotation.Nullable final org.wheatgenetics.coordinate.model.EntryModel entryModel,
    int activeRow, int activeCol)
    {
        this.elementModel = entryModel;

        activeRow = java.lang.Math.max(activeRow, -1);
        activeCol = java.lang.Math.max(activeCol, -1);
        if (this.getRow() == activeRow && this.getCol() == activeCol
        ||  -1            == activeRow && -1            == activeCol)
            this.activate();
        else
            this.setImage();

        if (this.elementModel instanceof org.wheatgenetics.coordinate.model.IncludedEntryModel)
            this.setOnClickListener();
        else
            this.clearOnClickListener();
    }

    // region Public Methods
    @androidx.annotation.IntRange(from = -1) public int getRow()
    { return this.elementModelIsNotNull() ? this.elementModel.getRowValue() - 1 : -1; }

    @androidx.annotation.IntRange(from = -1) public int getCol()
    { return this.elementModelIsNotNull() ? this.elementModel.getColValue() - 1 : -1; }
    // endregion
}