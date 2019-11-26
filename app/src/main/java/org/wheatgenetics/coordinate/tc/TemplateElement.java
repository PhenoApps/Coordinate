package org.wheatgenetics.coordinate.tc;

/**
 * Uses:
 * android.support.annotation.NonNull
 * android.widget.TextView
 *
 * org.wheatgenetics.coordinate.model.Cell
 *
 * org.wheatgenetics.coordinate.Element
 * org.wheatgenetics.coordinate.R
 */
class TemplateElement extends org.wheatgenetics.coordinate.Element
{
    @java.lang.SuppressWarnings({"UnnecessaryInterfaceModifier"}) interface TemplateHandler
    { public abstract boolean isExcluded(org.wheatgenetics.coordinate.model.Cell cell); }

    @android.support.annotation.NonNull private final
        org.wheatgenetics.coordinate.tc.TemplateElement.TemplateHandler templateHandler;

    private boolean isExcluded()
    {
        return this.templateHandler.isExcluded(
            (org.wheatgenetics.coordinate.model.Cell) this.elementModel);
    }

    TemplateElement(                    final org.wheatgenetics.coordinate.model.Cell cell    ,
    @android.support.annotation.NonNull final android.widget.TextView                 textView,
    @android.support.annotation.NonNull final
        org.wheatgenetics.coordinate.tc.TemplateElement.Handler handler,
    @android.support.annotation.NonNull final
        org.wheatgenetics.coordinate.tc.TemplateElement.TemplateHandler templateHandler)
    {
        super(cell, textView, handler); this.templateHandler = templateHandler;
        this.setBackgroundResource(); this.setOnClickListener();
    }

    // region Overridden Methods
    @java.lang.Override protected void respondToClick()
    { this.toggle(); this.setBackgroundResource(); }

    @java.lang.Override protected void setBackgroundResource()
    {
        this.setBackgroundResource(this.isExcluded() ?
            org.wheatgenetics.coordinate.R.drawable.excluded_entry      :
            org.wheatgenetics.coordinate.R.drawable.empty_included_entry);
    }
    // endregion
}