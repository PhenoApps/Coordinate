package org.wheatgenetics.coordinate.display;

/**
 * Uses:
 * android.app.Activity
 * android.content.Context
 * android.support.annotation.IntRange
 * android.support.annotation.NonNull
 * android.support.annotation.Nullable
 *
 * org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.Checker
 * org.wheatgenetics.coordinate.model.ElementModel
 *
 * org.wheatgenetics.coordinate.DisplayFragment
 * org.wheatgenetics.coordinate.DisplayFragment.Handler
 *
 * org.wheatgenetics.coordinate.display.GridElement
 * org.wheatgenetics.coordinate.display.GridElement.Handler
 * org.wheatgenetics.coordinate.display.GridElements
 */
public class GridDisplayFragment extends org.wheatgenetics.coordinate.DisplayFragment
implements org.wheatgenetics.coordinate.display.GridElement.Handler
{
    @java.lang.SuppressWarnings({"UnnecessaryInterfaceModifier"})
    public interface Handler extends org.wheatgenetics.coordinate.DisplayFragment.Handler
    {
        public abstract int getActiveRow(); public abstract int getActiveCol();
        public abstract void activate(int row, int col);

        @android.support.annotation.Nullable public abstract
            org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.Checker getChecker();
    }

    public GridDisplayFragment() { /* Required empty public constructor. */ }

    // region Overridden Methods
    @java.lang.Override protected boolean setHandler(final android.content.Context context)
    {
        final boolean success;

        if (context instanceof org.wheatgenetics.coordinate.display.GridDisplayFragment.Handler)
        {
            this.handler =
                (org.wheatgenetics.coordinate.display.GridDisplayFragment.Handler) context;
            success = true;
        }
        else { this.handler = null; success = false; }

        return success;
    }

    @java.lang.Override protected void allocateElements(
    @android.support.annotation.IntRange(from = 1) final int lastRow,
    @android.support.annotation.IntRange(from = 1) final int lastCol)
    {
        final org.wheatgenetics.coordinate.display.GridDisplayFragment.Handler handler =
            (org.wheatgenetics.coordinate.display.GridDisplayFragment.Handler) this.handler;
        if (null != handler)
        {
            final int activeRow = handler.getActiveRow(), activeCol = handler.getActiveCol();
            if (null == this.elements)
            {
                final android.app.Activity activity = this.getActivity();
                final org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.Checker checker =
                    handler.getChecker();
                if (null != activity && null != checker) this.elements =
                    new org.wheatgenetics.coordinate.display.GridElements(
                        activity, lastRow, lastCol, activeRow, activeCol,this, checker);
            }
            else
                ((org.wheatgenetics.coordinate.display.GridElements) this.elements).allocate(
                    lastRow, lastCol, activeRow, activeCol);
        }
    }

    // region org.wheatgenetics.coordinate.display.GridElement.Handler Overridden Methods
    @java.lang.Override
    public void toggle(final org.wheatgenetics.coordinate.model.ElementModel elementModel)
    { super.toggle(elementModel); }

    @java.lang.Override public void activate(@android.support.annotation.NonNull
    final org.wheatgenetics.coordinate.display.GridElement gridElement)
    {
        if (null != this.handler)
            ((org.wheatgenetics.coordinate.display.GridDisplayFragment.Handler)
                this.handler).activate(gridElement.getRow(), gridElement.getCol());
    }
    // endregion
    // endregion
}