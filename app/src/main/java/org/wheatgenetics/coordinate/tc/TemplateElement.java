package org.wheatgenetics.coordinate.tc;

/**
 * Uses:
 * android.widget.TextView
 *
 * org.wheatgenetics.coordinate.model.Cell
 *
 * org.wheatgenetics.coordinate.Element
 * org.wheatgenetics.coordinate.Element.Handler
 * org.wheatgenetics.coordinate.R
 */
class TemplateElement extends org.wheatgenetics.coordinate.Element
{
    @java.lang.SuppressWarnings("UnnecessaryInterfaceModifier")
    interface Handler extends org.wheatgenetics.coordinate.Element.Handler
    { public abstract boolean isExcluded(org.wheatgenetics.coordinate.model.Cell cell); }

    TemplateElement(
    final org.wheatgenetics.coordinate.model.Cell                 cell    ,
    final android.widget.TextView                                 textView,
    final org.wheatgenetics.coordinate.tc.TemplateElement.Handler handler )
    { super(cell, textView, handler); this.setBackgroundResource(); this.setOnClickListener(); }

    // region Overridden Methods
    @java.lang.Override
    protected void respondToClick() { this.toggle(); this.setBackgroundResource(); }

    @java.lang.Override
    protected void setBackgroundResource()
    {
        final boolean excluded = ((org.wheatgenetics.coordinate.tc.TemplateElement.Handler)
            this.getHandler()).isExcluded(
                (org.wheatgenetics.coordinate.model.Cell) this.elementModel);

        this.setBackgroundResource(excluded ?
            org.wheatgenetics.coordinate.R.drawable.excluded_entry      :
            org.wheatgenetics.coordinate.R.drawable.empty_included_entry);
    }
    // endregion
}