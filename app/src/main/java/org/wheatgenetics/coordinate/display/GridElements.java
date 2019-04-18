package org.wheatgenetics.coordinate.display;

/**
 * Uses:
 * android.app.Activity
 * android.support.annotation.IntRange
 * android.support.annotation.NonNull
 * android.widget.TextView
 *
 * org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.Checker
 * org.wheatgenetics.coordinate.model.ElementModel
 * org.wheatgenetics.coordinate.model.EntryModel
 *
 * org.wheatgenetics.coordinate.Element
 * org.wheatgenetics.coordinate.Elements
 *
 * org.wheatgenetics.coordinate.display.GridElement
 * org.wheatgenetics.coordinate.display.GridElement.Handler
 */
class GridElements extends org.wheatgenetics.coordinate.Elements
implements org.wheatgenetics.coordinate.display.GridElement.Handler
{
    // region Fields
    @android.support.annotation.NonNull private final
        org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.Checker checker;

    private int activeRow, activeCol;
    // endregion

    GridElements(
    @android.support.annotation.NonNull            final android.app.Activity activity,
    @android.support.annotation.IntRange(from = 1) final int                  rows    ,
    @android.support.annotation.IntRange(from = 1) final int                  cols    ,
    final int activeRow, final int activeCol, @android.support.annotation.NonNull
        final org.wheatgenetics.coordinate.display.GridElement.Handler handler,
    @android.support.annotation.NonNull final
        org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.Checker checker)
    {
        super(activity, handler);                                   // TODO: Should handler be this?
        this.checker = checker; this.allocate(rows, cols, activeRow, activeCol);
    }

    // region Overridden Methods
    @java.lang.Override @android.support.annotation.NonNull
    protected org.wheatgenetics.coordinate.Element makeElement(
    final org.wheatgenetics.coordinate.model.ElementModel elementModel,
    final android.widget.TextView                         textView    )
    {
        return new org.wheatgenetics.coordinate.display.GridElement(
            /* context    => */ this.getActivity()                                          ,
            /* entryModel => */ (org.wheatgenetics.coordinate.model.EntryModel) elementModel,
            /* textView   => */ textView                                                    ,
            /* handler    => */this,                                                //TODO:?
            /* activeRow  => */ this.activeRow,
            /* activeCol  => */ this.activeCol,
            /* checker    => */ this.checker  );
    }

    @java.lang.Override protected void clear()
    { super.clear(); this.activeRow = this.activeCol = -1; }

    // region org.wheatgenetics.coordinate.display.GridElement.Handler Overridden Method
    @java.lang.Override public void activate(@android.support.annotation.NonNull
    final org.wheatgenetics.coordinate.display.GridElement gridElement)
    {
        final int newActiveRow = gridElement.getRow(), newActiveCol = gridElement.getCol();
        if (newActiveRow != this.activeRow || newActiveCol != this.activeCol)
        {
            if (this.activeRow > -1 && this.activeCol > -1)
            {
                final org.wheatgenetics.coordinate.Element[][] elementArray =
                    this.getElementArray();
                if (null != elementArray)
                    ((org.wheatgenetics.coordinate.display.GridElement)                    // TODO:?
                        elementArray[this.activeRow][this.activeCol]).inactivate();
            }

            this.activeRow = newActiveRow; this.activeCol = newActiveCol;

            ((org.wheatgenetics.coordinate.display.GridElement.Handler)
                this.getHandler()).activate(gridElement);
        }
    }
    // endregion
    // endregion

    /** Note: Overloaded, not overridden. */
    void allocate(
    @android.support.annotation.IntRange(from = 1) final int rows,
    @android.support.annotation.IntRange(from = 1) final int cols,
    final int activeRow, final int activeCol)
    { this.allocate(rows, cols); this.activeRow = activeRow; this.activeCol = activeCol; }
}