package org.wheatgenetics.coordinate.tc;

/**
 * Uses:
 * android.widget.TextView
 *
 * androidx.annotation.NonNull
 *
 * org.wheatgenetics.coordinate.R
 *
 * org.wheatgenetics.coordinate.display.Element
 *
 * org.wheatgenetics.coordinate.model.Cell
 */
class TemplateElement extends org.wheatgenetics.coordinate.display.Element
{
    @java.lang.SuppressWarnings({"UnnecessaryInterfaceModifier"}) interface TemplateHandler
    { public abstract boolean isExcluded(org.wheatgenetics.coordinate.model.Cell cell); }

    @androidx.annotation.NonNull private final
        org.wheatgenetics.coordinate.tc.TemplateElement.TemplateHandler templateHandler;

    private boolean isExcluded()
    {
        return this.templateHandler.isExcluded(
            (org.wheatgenetics.coordinate.model.Cell) this.elementModel);
    }

    TemplateElement(             final org.wheatgenetics.coordinate.model.Cell cell    ,
    @androidx.annotation.NonNull final android.widget.TextView                 textView,
    @androidx.annotation.NonNull final org.wheatgenetics.coordinate.tc.TemplateElement.Handler
        handler,
    @androidx.annotation.NonNull final
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