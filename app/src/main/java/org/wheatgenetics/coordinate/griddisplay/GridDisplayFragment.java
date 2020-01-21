package org.wheatgenetics.coordinate.griddisplay;

/**
 * Uses:
 * android.app.Activity
 * android.content.Context
 *
 * androidx.annotation.IntRange
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 * androidx.annotation.RestrictTo
 * androidx.annotation.RestrictTo.Scope
 *
 * org.wheatgenetics.coordinate.display.DisplayFragment
 * org.wheatgenetics.coordinate.display.DisplayFragment.Handler
 *
 * org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.Checker
 *
 * org.wheatgenetics.coordinate.griddisplay.GridElement
 * org.wheatgenetics.coordinate.griddisplay.GridElement.GridHandler
 * org.wheatgenetics.coordinate.griddisplay.GridElements
 */
public class GridDisplayFragment extends org.wheatgenetics.coordinate.display.DisplayFragment
implements org.wheatgenetics.coordinate.griddisplay.GridElement.GridHandler
{
    @java.lang.SuppressWarnings({"UnnecessaryInterfaceModifier"})
    public interface Handler extends org.wheatgenetics.coordinate.display.DisplayFragment.Handler
    {
        public abstract int getActiveRow(); public abstract int getActiveCol();
        public abstract void activate(int row, int col);

        @androidx.annotation.Nullable public abstract
        org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.Checker getChecker();
    }

    @androidx.annotation.Nullable
    private org.wheatgenetics.coordinate.griddisplay.GridDisplayFragment.Handler
    gridDisplayFragmenthandler()
    { return (org.wheatgenetics.coordinate.griddisplay.GridDisplayFragment.Handler) this.handler; }

    public GridDisplayFragment() { /* Required empty public constructor. */ }

    // region Overridden Methods
    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @java.lang.Override protected boolean setHandler(final android.content.Context context)
    {
        final boolean success;

        if (context instanceof org.wheatgenetics.coordinate.griddisplay.GridDisplayFragment.Handler)
        {
            this.handler =
                (org.wheatgenetics.coordinate.griddisplay.GridDisplayFragment.Handler) context;
            success = true;
        }
        else { this.handler = null; success = false; }

        return success;
    }

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @java.lang.Override protected void allocateElements(
    @androidx.annotation.IntRange(from = 1) final int lastRow,
    @androidx.annotation.IntRange(from = 1) final int lastCol)
    {
        final org.wheatgenetics.coordinate.griddisplay.GridDisplayFragment.Handler
            gridDisplayFragmenthandler = this.gridDisplayFragmenthandler();
        if (null != gridDisplayFragmenthandler)
        {
            final int
                activeRow = gridDisplayFragmenthandler.getActiveRow(),
                activeCol = gridDisplayFragmenthandler.getActiveCol();
            if (null == this.elements)
            {
                final android.app.Activity activity = this.getActivity();
                if (null != activity) this.elements =
                    new org.wheatgenetics.coordinate.griddisplay.GridElements(activity,
                        lastRow, lastCol, activeRow, activeCol,this,this,
                        gridDisplayFragmenthandler.getChecker());
            }
            else
                ((org.wheatgenetics.coordinate.griddisplay.GridElements) this.elements).allocate(
                    lastRow, lastCol, activeRow, activeCol);
        }
    }

    // region org.wheatgenetics.coordinate.griddisplay.GridElement.GridHandler Overridden Method
    @java.lang.Override public void activate(@androidx.annotation.NonNull
    final org.wheatgenetics.coordinate.griddisplay.GridElement gridElement)
    {
        final org.wheatgenetics.coordinate.griddisplay.GridDisplayFragment.Handler
            gridDisplayFragmenthandler = this.gridDisplayFragmenthandler();
        if (null != gridDisplayFragmenthandler)
                gridDisplayFragmenthandler.activate(gridElement.getRow(), gridElement.getCol());
    }
    // endregion
    // endregion
}