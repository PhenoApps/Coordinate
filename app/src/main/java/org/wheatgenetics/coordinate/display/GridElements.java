package org.wheatgenetics.coordinate.display;

/**
 * Uses:
 * android.app.Activity
 * android.support.annotation.IntRange
 * android.widget.TextView
 *
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
    private int activeRow, activeCol;

    GridElements(final android.app.Activity activity,
    @android.support.annotation.IntRange(from = 1) final int rows,
    @android.support.annotation.IntRange(from = 1) final int cols,
    final int activeRow, final int activeCol,
    final org.wheatgenetics.coordinate.display.GridElement.Handler handler)
    {
        super(activity, handler);                                   // TODO: Should handler be this?
        this.allocate(rows, cols, activeRow, activeCol);
    }

    // region Overridden Methods
    @java.lang.Override
    protected org.wheatgenetics.coordinate.Element makeElement(
    final org.wheatgenetics.coordinate.model.ElementModel elementModel,
    final android.widget.TextView                         textView    )
    {
        return new org.wheatgenetics.coordinate.display.GridElement(
            /* context    => */ this.getActivity()                                          ,
            /* entryModel => */ (org.wheatgenetics.coordinate.model.EntryModel) elementModel,
            /* textView   => */ textView                                                    ,
            /* handler    => */ this                                                        ,//TODO:?
            /* activeRow  => */ this.activeRow                                              ,
            /* activeCol  => */ this.activeCol                                              );
    }

    @java.lang.Override protected void clear()
    { super.clear(); this.activeRow = this.activeCol = -1; }

    // region org.wheatgenetics.coordinate.display.GridElement.Handler Overridden Method
    @java.lang.Override
    public void activate(final org.wheatgenetics.coordinate.display.GridElement gridElement)
    {
        if (null != gridElement)
        {
            final int newActiveRow = gridElement.getRow(), newActiveCol = gridElement.getCol();
            if (newActiveRow != this.activeRow || newActiveCol != this.activeCol)
            {
                if (this.activeRow > -1 && this.activeCol > -1)
                {
                    ((org.wheatgenetics.coordinate.display.GridElement)                    // TODO:?
                        this.getElementArray()[this.activeRow][this.activeCol]).inactivate();
                }

                this.activeRow = newActiveRow; this.activeCol = newActiveCol;

                ((org.wheatgenetics.coordinate.display.GridElement.Handler)
                    this.getHandler()).activate(gridElement);
            }
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