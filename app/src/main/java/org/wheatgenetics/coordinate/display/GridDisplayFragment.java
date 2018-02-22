package org.wheatgenetics.coordinate.display;

/**
 * Uses:
 * android.content.Context
 *
 * org.wheatgenetics.coordinate.model.ElementModel
 *
 * org.wheatgenetics.coordinate.DisplayFragment
 * org.wheatgenetics.coordinate.DisplayFragment.Handler
 *
 * org.wheatgenetics.coordinate.display.GridElement
 * org.wheatgenetics.coordinate.display.GridElements
 * org.wheatgenetics.coordinate.display.GridElement.Handler
 */
public class GridDisplayFragment extends org.wheatgenetics.coordinate.DisplayFragment
implements org.wheatgenetics.coordinate.display.GridElement.Handler
{
    @java.lang.SuppressWarnings("UnnecessaryInterfaceModifier")
    public interface Handler extends org.wheatgenetics.coordinate.DisplayFragment.Handler
    {
        public abstract int getActiveRow(); public abstract int getActiveCol();
        public abstract void activate(int row, int col);
    }

    public GridDisplayFragment() { /* Required empty public constructor. */ }

    // region Overridden Methods
    @java.lang.Override @java.lang.SuppressWarnings("PointlessBooleanExpression")
    protected boolean setHandler(final android.content.Context context)
    {
        final boolean success = true;
        if (context instanceof org.wheatgenetics.coordinate.display.GridDisplayFragment.Handler)
        {
            this.handler =
                (org.wheatgenetics.coordinate.display.GridDisplayFragment.Handler) context;
            return success;
        }
        else return !success;
    }

    @java.lang.Override
    protected void allocateElements(final int lastRow, final int lastCol)
    {
        final int activeRow, activeCol;
        {
            final org.wheatgenetics.coordinate.display.GridDisplayFragment.Handler handler =
                (org.wheatgenetics.coordinate.display.GridDisplayFragment.Handler) this.handler;
            activeRow = handler.getActiveRow(); activeCol = handler.getActiveCol();
        }

        if (null == this.elements)
            this.elements = new org.wheatgenetics.coordinate.display.GridElements(
                this.getActivity(), lastRow, lastCol, activeRow, activeCol, this);
        else
            ((org.wheatgenetics.coordinate.display.GridElements) this.elements).allocate(
                lastRow, lastCol, activeRow, activeCol);
    }

    // region org.wheatgenetics.coordinate.display.GridElement.Handler Overridden Methods
    @java.lang.Override
    public void toggle(final org.wheatgenetics.coordinate.model.ElementModel elementModel)
    { super.toggle(elementModel); }

    @java.lang.Override
    public void activate(final org.wheatgenetics.coordinate.display.GridElement gridElement)
    {
        assert null != gridElement; assert null != this.handler;
        ((org.wheatgenetics.coordinate.display.GridDisplayFragment.Handler)
            this.handler).activate(gridElement.getRow(), gridElement.getCol());
    }
    // endregion
    // endregion
}