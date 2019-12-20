package org.wheatgenetics.coordinate.tc;

/**
 * Uses:
 * android.app.Activity
 * android.widget.TextView
 *
 * androidx.annotation.IntRange
 * androidx.annotation.NonNull
 * androidx.annotation.RestrictTo
 * androidx.annotation.RestrictTo.Scope
 *
 * org.wheatgenetics.coordinate.display.Element
 * org.wheatgenetics.coordinate.display.Elements
 *
 * org.wheatgenetics.coordinate.model.Cell
 * org.wheatgenetics.coordinate.model.ElementModel
 *
 * org.wheatgenetics.coordinate.tc.TemplateElement
 * org.wheatgenetics.coordinate.tc.TemplateElement.Handler
 * org.wheatgenetics.coordinate.tc.TemplateElement.TemplateHandler
 */
class TemplateElements extends org.wheatgenetics.coordinate.display.Elements
{
    // region Fields
    @androidx.annotation.NonNull private final
        org.wheatgenetics.coordinate.tc.TemplateElement.Handler handler;
    @androidx.annotation.NonNull private final
        org.wheatgenetics.coordinate.tc.TemplateElement.TemplateHandler templateHandler;
    // endregion

    TemplateElements(
    @androidx.annotation.NonNull            final android.app.Activity activity,
    @androidx.annotation.IntRange(from = 1) final int                  rows    ,
    @androidx.annotation.IntRange(from = 1) final int                  cols    ,
    @androidx.annotation.NonNull            final
        org.wheatgenetics.coordinate.tc.TemplateElement.Handler handler,
    @androidx.annotation.NonNull final
        org.wheatgenetics.coordinate.tc.TemplateElement.TemplateHandler templateHandler)
    {
        super(activity);
        this.handler = handler; this.templateHandler = templateHandler; this.allocate(rows, cols);
    }

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @java.lang.Override @androidx.annotation.NonNull
    protected org.wheatgenetics.coordinate.display.Element makeElement(
    final org.wheatgenetics.coordinate.model.ElementModel elementModel,
    final android.widget.TextView                         textView    )
    {
        return new org.wheatgenetics.coordinate.tc.TemplateElement(
            /* cell            => */ (org.wheatgenetics.coordinate.model.Cell) elementModel,
            /* textView        => */ textView                                              ,
            /* handler         => */ this.handler                                          ,
            /* templateHandler => */ this.templateHandler                                  );
    }
}