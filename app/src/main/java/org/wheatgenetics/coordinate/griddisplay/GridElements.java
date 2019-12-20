package org.wheatgenetics.coordinate.griddisplay;

/**
 * Uses:
 * android.app.Activity
 * android.widget.TextView
 *
 * androidx.annotation.IntRange
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 * androidx.annotation.RestrictTo
 * androidx.annotation.RestrictTo.Scope
 *
 * org.wheatgenetics.coordinate.display.Element
 * org.wheatgenetics.coordinate.display.Elements
 *
 * org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.Checker
 * org.wheatgenetics.coordinate.model.ElementModel
 * org.wheatgenetics.coordinate.model.EntryModel
 *
 * org.wheatgenetics.coordinate.griddisplay.GridElement
 * org.wheatgenetics.coordinate.griddisplay.GridElement.Handler
 * org.wheatgenetics.coordinate.griddisplay.GridElement.GridHandler
 */
class GridElements extends org.wheatgenetics.coordinate.display.Elements
implements org.wheatgenetics.coordinate.griddisplay.GridElement.GridHandler
{
    // region Fields
    @androidx.annotation.NonNull private final
        org.wheatgenetics.coordinate.griddisplay.GridElement.Handler handler;
    @androidx.annotation.NonNull private final
        org.wheatgenetics.coordinate.griddisplay.GridElement.GridHandler gridHandler;
    @androidx.annotation.Nullable private final
        org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.Checker checker;

    private int activeRow, activeCol;
    // endregion

    GridElements(
    @androidx.annotation.NonNull            final android.app.Activity activity,
    @androidx.annotation.IntRange(from = 1) final int                  rows    ,
    @androidx.annotation.IntRange(from = 1) final int                  cols    ,
    final int activeRow, final int activeCol,
    @androidx.annotation.NonNull final org.wheatgenetics.coordinate.griddisplay.GridElement.Handler
        handler,
    @androidx.annotation.NonNull final
        org.wheatgenetics.coordinate.griddisplay.GridElement.GridHandler gridHandler,
    @androidx.annotation.Nullable final
        org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.Checker checker)
    {
        super(activity);
        this.handler = handler; this.gridHandler = gridHandler; this.checker = checker;
        this.allocate(rows, cols, activeRow, activeCol);
    }

    // region Overridden Methods
    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @java.lang.Override @androidx.annotation.NonNull
    protected org.wheatgenetics.coordinate.display.Element makeElement(
    final org.wheatgenetics.coordinate.model.ElementModel elementModel,
    final android.widget.TextView                         textView    )
    {
        return new org.wheatgenetics.coordinate.griddisplay.GridElement(
            /* context     => */ this.getActivity()                                          ,
            /* entryModel  => */ (org.wheatgenetics.coordinate.model.EntryModel) elementModel,
            /* textView    => */ textView                                                    ,
            /* handler     => */ this.handler                                                ,
            /* gridHandler => */this,           // Make this handle activate()
            /* activeRow   => */ this.activeRow,           //  then make this.gridHandler handle it.
            /* activeCol   => */ this.activeCol,
            /* checker     => */ this.checker  );
    }

    @java.lang.Override protected void clear()
    { super.clear(); this.activeRow = this.activeCol = -1; }

    // region org.wheatgenetics.coordinate.griddisplay.GridElement.Handler Overridden Method
    @java.lang.Override public void activate(@androidx.annotation.NonNull
    final org.wheatgenetics.coordinate.griddisplay.GridElement gridElement)
    {
        final int newActiveRow = gridElement.getRow(), newActiveCol = gridElement.getCol();
        if (newActiveRow != this.activeRow || newActiveCol != this.activeCol)
        {
            if (this.activeRow > -1 && this.activeCol > -1)
            {
                final org.wheatgenetics.coordinate.display.Element[][] elementArray =
                    this.getElementArray();
                if (null != elementArray)
                    ((org.wheatgenetics.coordinate.griddisplay.GridElement)
                        elementArray[this.activeRow][this.activeCol]).inactivate();
            }

            this.activeRow = newActiveRow; this.activeCol = newActiveCol;
            this.gridHandler.activate(gridElement);
        }
    }
    // endregion
    // endregion

    /** Note: Overloaded, not overridden. */
    void allocate(
    @androidx.annotation.IntRange(from = 1) final int rows,
    @androidx.annotation.IntRange(from = 1) final int cols,
    final int activeRow, final int activeCol)
    { this.allocate(rows, cols); this.activeRow = activeRow; this.activeCol = activeCol; }
}