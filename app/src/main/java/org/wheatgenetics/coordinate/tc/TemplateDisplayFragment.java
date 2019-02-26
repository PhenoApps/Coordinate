package org.wheatgenetics.coordinate.tc;

/**
 * Uses:
 * android.app.Activity
 * android.content.Context
 * android.support.annotation.IntRange
 *
 * org.wheatgenetics.coordinate.model.Cell
 * org.wheatgenetics.coordinate.model.ElementModel
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
    @java.lang.SuppressWarnings({"UnnecessaryInterfaceModifier"})
    public interface Handler extends org.wheatgenetics.coordinate.DisplayFragment.Handler
    { public abstract boolean isExcluded(org.wheatgenetics.coordinate.model.Cell cell); }

    public TemplateDisplayFragment() { /* Required empty public constructor. */ }

    // region Overridden Methods
    @java.lang.Override protected boolean setHandler(final android.content.Context context)
    {
        final boolean success;

        if (context instanceof org.wheatgenetics.coordinate.tc.TemplateDisplayFragment.Handler)
        {
            this.handler =
                (org.wheatgenetics.coordinate.tc.TemplateDisplayFragment.Handler) context;
            success = true;
        }
        else { this.handler = null; success = false; }

        return success;
    }

    @java.lang.Override protected void allocateElements(
    @android.support.annotation.IntRange(from = 1) final int lastRow,
    @android.support.annotation.IntRange(from = 1) final int lastCol)
    {
        if (null == this.elements)
        {
            final android.app.Activity activity = this.getActivity();
            if (null != activity) this.elements =
                new org.wheatgenetics.coordinate.tc.TemplateElements(
                    activity, lastRow, lastCol,this);
        }
        else this.elements.allocate(lastRow, lastCol);
    }

    // region org.wheatgenetics.coordinate.tc.TemplateElement.Handler Overridden Methods
    @java.lang.Override
    public void toggle(final org.wheatgenetics.coordinate.model.ElementModel elementModel)
    { super.toggle(elementModel); }

    @java.lang.Override
    public boolean isExcluded(final org.wheatgenetics.coordinate.model.Cell cell)
    {
        if (null == this.handler)
            throw new java.lang.NullPointerException();
        else
            return ((org.wheatgenetics.coordinate.tc.TemplateDisplayFragment.Handler)
                this.handler).isExcluded(cell);
    }
    // endregion
    // endregion
}