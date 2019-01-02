package org.wheatgenetics.coordinate;

/**
 * Uses:
 * android.annotation.SuppressLint
 * android.app.Activity
 * android.support.annotation.IntRange
 * android.support.annotation.RestrictTo
 * android.support.annotation.RestrictTo.Scope
 * android.view.LayoutInflater
 * android.widget.LinearLayout
 * android.widget.TextView
 *
 * org.wheatgenetics.coordinate.model.ElementModel
 *
 * org.wheatgenetics.coordinate.Element
 * org.wheatgenetics.coordinate.Element.Handler
 * org.wheatgenetics.coordinate.R
 * org.wheatgenetics.coordinate.Utils
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
public abstract class Elements extends java.lang.Object
implements org.wheatgenetics.coordinate.Element.Handler
{
    // region Fields
    private final android.app.Activity                         activity;
    private final org.wheatgenetics.coordinate.Element.Handler handler ;

    private org.wheatgenetics.coordinate.Element elementArray[][];
    // endregion

    // region Protected Methods
    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    protected abstract org.wheatgenetics.coordinate.Element makeElement(
    final org.wheatgenetics.coordinate.model.ElementModel elementModel,
    final android.widget.TextView                         textView    );


    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    protected android.app.Activity getActivity() { return this.activity; }

    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    protected org.wheatgenetics.coordinate.Element.Handler getHandler() { return this.handler; }

    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    protected org.wheatgenetics.coordinate.Element[][] getElementArray()
    { return this.elementArray; }
    // endregion

    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    protected Elements(final android.app.Activity activity,
    final org.wheatgenetics.coordinate.Element.Handler handler)
    { super(); this.activity = activity; this.handler = handler; }

    // region org.wheatgenetics.coordinate.Element.Handler Overridden Method
    @java.lang.Override
    public void toggle(final org.wheatgenetics.coordinate.model.ElementModel elementModel)
    { assert null != this.handler; this.handler.toggle(elementModel); }
    // endregion

    // region Protected Methods
    protected void clear() { this.elementArray = null; }

    @android.annotation.SuppressLint({"InflateParams"}) protected android.widget.LinearLayout add(
    final org.wheatgenetics.coordinate.model.ElementModel elementModel)
    {
        if (null == elementModel)
            return null;
        else
        {
            final android.widget.LinearLayout result;
            {
                assert null != this.activity;
                final android.view.LayoutInflater layoutInflater =
                    this.activity.getLayoutInflater();

                result = (android.widget.LinearLayout) layoutInflater.inflate(
                    org.wheatgenetics.coordinate.R.layout.display_table_cell,null);
            }
            {
                assert null != result;
                final org.wheatgenetics.coordinate.Element element =
                    this.makeElement(elementModel, (android.widget.TextView)
                        result.findViewById(org.wheatgenetics.coordinate.R.id.displayTextView));
                assert null != element; assert null != this.elementArray;
                this.elementArray[element.getRow()][element.getCol()] = element;
            }
            return result;
        }
    }
    // endregion

    public void allocate(
    @android.support.annotation.IntRange(from = 1) final int rows,
    @android.support.annotation.IntRange(from = 1) final int cols)
    {
        this.elementArray = new org.wheatgenetics.coordinate.Element
            [org.wheatgenetics.coordinate.Utils.valid(rows,1)]
            [org.wheatgenetics.coordinate.Utils.valid(cols,1)];
    }
}