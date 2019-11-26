package org.wheatgenetics.coordinate;

/**
 * Uses:
 * android.annotation.SuppressLint
 * android.app.Activity
 * android.support.annotation.IntRange
 * android.support.annotation.NonNull
 * android.support.annotation.Nullable
 * android.support.annotation.RestrictTo
 * android.support.annotation.RestrictTo.Scope
 * android.view.LayoutInflater
 * android.widget.LinearLayout
 * android.widget.TextView
 *
 * org.wheatgenetics.coordinate.model.ElementModel
 *
 * org.wheatgenetics.coordinate.Element
 * org.wheatgenetics.coordinate.R
 * org.wheatgenetics.coordinate.Utils
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
public abstract class Elements extends java.lang.Object
{
    // region Fields
    @android.support.annotation.NonNull private final android.app.Activity activity;

    @android.support.annotation.Nullable @java.lang.SuppressWarnings({"CStyleArrayDeclaration"})
    private org.wheatgenetics.coordinate.Element elementArray[][];
    // endregion

    // region Protected Methods
    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    @android.support.annotation.NonNull
    protected abstract org.wheatgenetics.coordinate.Element makeElement(
    final org.wheatgenetics.coordinate.model.ElementModel elementModel,
    final android.widget.TextView                         textView    );


    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    @android.support.annotation.NonNull protected android.app.Activity getActivity()
    { return this.activity; }

    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    @android.support.annotation.Nullable protected org.wheatgenetics.coordinate.Element[][]
    getElementArray() { return this.elementArray; }
    // endregion

    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    protected Elements(@android.support.annotation.NonNull final android.app.Activity activity)
    { super(); this.activity = activity; }

    @android.annotation.SuppressLint({"InflateParams"}) @android.support.annotation.Nullable
    android.widget.LinearLayout add(
    final org.wheatgenetics.coordinate.model.ElementModel elementModel)
    {
        if (null == elementModel)
            return null;
        else
        {
            final android.widget.LinearLayout result;
            {
                final android.view.LayoutInflater layoutInflater =
                    this.getActivity().getLayoutInflater();

                result = (android.widget.LinearLayout) layoutInflater.inflate(
                    org.wheatgenetics.coordinate.R.layout.display_table_cell,null);
            }
            {
                if (null == result) return null;
                final org.wheatgenetics.coordinate.Element element =
                    this.makeElement(elementModel, (android.widget.TextView)
                        result.findViewById(org.wheatgenetics.coordinate.R.id.displayTextView));
                if (null == this.elementArray)
                    return null;
                else
                    this.elementArray[element.getRow()][element.getCol()] = element;
            }
            return result;
        }
    }

    protected void clear() { this.elementArray = null; }

    public void allocate(
    @android.support.annotation.IntRange(from = 1) final int rows,
    @android.support.annotation.IntRange(from = 1) final int cols)
    {
        this.elementArray = new org.wheatgenetics.coordinate.Element
            [org.wheatgenetics.coordinate.Utils.valid(rows,1)]
            [org.wheatgenetics.coordinate.Utils.valid(cols,1)];
    }
}