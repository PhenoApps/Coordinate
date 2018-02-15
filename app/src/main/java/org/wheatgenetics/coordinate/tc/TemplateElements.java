package org.wheatgenetics.coordinate.tc;

/**
 * Uses:
 * android.app.Activity
 * android.widget.TextView
 *
 * org.wheatgenetics.coordinate.model.Cell
 * org.wheatgenetics.coordinate.model.ElementModel
 *
 * org.wheatgenetics.coordinate.Element
 * org.wheatgenetics.coordinate.Elements
 *
 * org.wheatgenetics.coordinate.tc.TemplateElement
 * org.wheatgenetics.coordinate.tc.TemplateElement.Handler
 */
class TemplateElements extends org.wheatgenetics.coordinate.Elements
{
    TemplateElements(final android.app.Activity activity, final int rows, final int cols,
    final org.wheatgenetics.coordinate.tc.TemplateElement.Handler handler)
    { super(activity, handler); this.allocate(rows, cols); }

    @java.lang.Override
    protected org.wheatgenetics.coordinate.Element makeElement(
    final org.wheatgenetics.coordinate.model.ElementModel elementModel,
    final android.widget.TextView                         textView    )
    {
        return new org.wheatgenetics.coordinate.tc.TemplateElement(
            /* cell     => */ (org.wheatgenetics.coordinate.model.Cell) elementModel,
            /* textView => */ textView                                              ,
            /* handler  => */
                (org.wheatgenetics.coordinate.tc.TemplateElement.Handler) this.getHandler());
    }
}