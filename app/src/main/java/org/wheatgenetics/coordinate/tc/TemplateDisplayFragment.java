package org.wheatgenetics.coordinate.tc;

/**
 * Uses:
 * android.content.Context
 *
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 * androidx.annotation.RestrictTo
 * androidx.annotation.RestrictTo.Scope
 *
 * org.wheatgenetics.coordinate.model.Cell
 * org.wheatgenetics.coordinate.model.DisplayModel
 * org.wheatgenetics.coordinate.model.ElementModel
 *
 * org.wheatgenetics.coordinate.display.DisplayFragment
 * org.wheatgenetics.coordinate.display.DisplayFragment.Handler
 * org.wheatgenetics.coordinate.display.adapter.Adapter
 *
 * org.wheatgenetics.coordinate.tc.adapter.Adapter
 * org.wheatgenetics.coordinate.tc.adapter.DataViewHolder.Handler
 * org.wheatgenetics.coordinate.tc.adapter.DataViewHolder.TemplateHandler
 */
public class TemplateDisplayFragment extends org.wheatgenetics.coordinate.display.DisplayFragment
{
    @java.lang.SuppressWarnings({"UnnecessaryInterfaceModifier"})
    interface Handler extends org.wheatgenetics.coordinate.display.DisplayFragment.Handler
    { public abstract boolean isExcluded(org.wheatgenetics.coordinate.model.Cell cell); }

    // region Private Methods
    @androidx.annotation.Nullable
    private org.wheatgenetics.coordinate.tc.TemplateDisplayFragment.Handler templateHandler()
    { return (org.wheatgenetics.coordinate.tc.TemplateDisplayFragment.Handler) this.handler; }

    private boolean isExcluded(final org.wheatgenetics.coordinate.model.Cell cell)
    {
        final org.wheatgenetics.coordinate.tc.TemplateDisplayFragment.Handler templateHandler =
            this.templateHandler();
        if (null == templateHandler)
            throw new java.lang.NullPointerException();
        else
            return templateHandler.isExcluded(cell);
    }
    // endregion

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
    @java.lang.Override protected org.wheatgenetics.coordinate.display.adapter.Adapter makeAdapter(
    @androidx.annotation.NonNull final org.wheatgenetics.coordinate.model.DisplayModel displayModel)
    {
        return new org.wheatgenetics.coordinate.tc.adapter.Adapter(displayModel,
            new org.wheatgenetics.coordinate.tc.adapter.DataViewHolder.Handler()
            {
                @java.lang.Override @androidx.annotation.Nullable
                public org.wheatgenetics.coordinate.model.ElementModel toggle(
                @androidx.annotation.Nullable final
                    org.wheatgenetics.coordinate.model.ElementModel elementModel)
                {
                    return org.wheatgenetics.coordinate.tc.TemplateDisplayFragment.this.toggle(
                        elementModel);
                }
            }, new org.wheatgenetics.coordinate.tc.adapter.DataViewHolder.TemplateHandler()
            {
                @java.lang.Override public boolean isExcluded(
                final org.wheatgenetics.coordinate.model.Cell cell)
                {
                    return org.wheatgenetics.coordinate.tc
                        .TemplateDisplayFragment.this.isExcluded(cell);
                }
            });
    }
    // endregion
}