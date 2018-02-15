package org.wheatgenetics.coordinate.tc;

/**
 * Uses:
 * android.content.Context
 *
 * org.wheatgenetics.coordinate.model.Cell
 * org.wheatgenetics.coordinate.model.ElementModel
 * org.wheatgenetics.coordinate.model.TemplateModel
 *
 * org.wheatgenetics.coordinate.DisplayFragment
 * org.wheatgenetics.coordinate.DisplayFragment.Handler
 *
 * org.wheatgenetics.coordinate.tc.TemplateElement.Handler
 * org.wheatgenetics.coordinate.tc.TemplateElements
 */
public class TemplateDisplayFragment extends org.wheatgenetics.coordinate.DisplayFragment
implements org.wheatgenetics.coordinate.tc.TemplateElement.Handler
{
    @java.lang.SuppressWarnings("UnnecessaryInterfaceModifier")
    public interface Handler extends org.wheatgenetics.coordinate.DisplayFragment.Handler
    { public abstract boolean isExcluded(org.wheatgenetics.coordinate.model.Cell cell); }

    public TemplateDisplayFragment() { /* Required empty public constructor. */ }

    // region Overridden Methods
    @java.lang.Override
    protected boolean setHandler(final android.content.Context context)
    {
        final boolean success = true;
        if (context instanceof org.wheatgenetics.coordinate.DisplayFragment.Handler)
        {
            this.handler = (org.wheatgenetics.coordinate.DisplayFragment.Handler) context;
            return success;
        }
        else return !success;
    }

    @java.lang.Override
    protected void allocateElements(final int lastRow, final int lastCol)
    {
        if (null == this.elements)
            this.elements = new org.wheatgenetics.coordinate.tc.TemplateElements(
                this.getActivity(), lastRow, lastCol, this);
        else
            this.elements.allocate(lastRow, lastCol);
    }

    // region org.wheatgenetics.coordinate.tc.TemplateElement.Handler Overridden Methods
    @java.lang.Override
    public void toggle(final org.wheatgenetics.coordinate.model.ElementModel elementModel)
    { super.toggle(elementModel); }

    @java.lang.Override
    public boolean isExcluded(final org.wheatgenetics.coordinate.model.Cell cell)
    {
        return ((org.wheatgenetics.coordinate.tc.TemplateDisplayFragment.Handler)
            this.handler).isExcluded(cell);
    }
    // endregion
    // endregion
}