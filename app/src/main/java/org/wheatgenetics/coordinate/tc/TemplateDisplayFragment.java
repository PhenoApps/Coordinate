package org.wheatgenetics.coordinate.tc;

/**
 * Uses:
 * android.app.Activity
 * android.content.Context
 *
 * androidx.annotation.IntRange
 * androidx.annotation.RestrictTo
 * androidx.annotation.RestrictTo.Scope
 *
 * org.wheatgenetics.coordinate.display.DisplayFragment
 * org.wheatgenetics.coordinate.display.DisplayFragment.Handler
 *
 * org.wheatgenetics.coordinate.model.Cell
 *
 * org.wheatgenetics.coordinate.tc.TemplateElement.TemplateHandler
 * org.wheatgenetics.coordinate.tc.TemplateElements
 */
public class TemplateDisplayFragment extends org.wheatgenetics.coordinate.display.DisplayFragment
implements org.wheatgenetics.coordinate.tc.TemplateElement.TemplateHandler
{
    @java.lang.SuppressWarnings({"UnnecessaryInterfaceModifier"})
    public interface Handler extends org.wheatgenetics.coordinate.display.DisplayFragment.Handler
    { public abstract boolean isExcluded(org.wheatgenetics.coordinate.model.Cell cell); }

    public TemplateDisplayFragment() { /* Required empty public constructor. */ }

    // region Overridden Methods
    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @java.lang.Override protected boolean setHandler(
    @androidx.annotation.NonNull final android.content.Context context)
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

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @java.lang.Override protected void allocateElements(
    @androidx.annotation.IntRange(from = 1) final int lastRow,
    @androidx.annotation.IntRange(from = 1) final int lastCol)
    {
        if (null == this.elements)
        {
            final android.app.Activity activity = this.getActivity();
            if (null != activity) this.elements =
                new org.wheatgenetics.coordinate.tc.TemplateElements(
                    activity, lastRow, lastCol,this,this);
        }
        else this.elements.allocate(lastRow, lastCol);
    }

    // region org.wheatgenetics.coordinate.tc.TemplateElement.TemplateHandler Overridden Method
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