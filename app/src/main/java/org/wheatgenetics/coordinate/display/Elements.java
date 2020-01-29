package org.wheatgenetics.coordinate.display;

/**
 * Uses:
 * android.annotation.SuppressLint
 * android.app.Activity
 * android.view.LayoutInflater
 * android.widget.LinearLayout
 * android.widget.TextView
 *
 * androidx.annotation.IntRange
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 * androidx.annotation.RestrictTo
 * androidx.annotation.RestrictTo.Scope
 *
 * org.wheatgenetics.coordinate.R
 * org.wheatgenetics.coordinate.Utils
 *
 * org.wheatgenetics.coordinate.model.ElementModel
 *
 * org.wheatgenetics.coordinate.display.Element
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
public abstract class Elements extends java.lang.Object
{
    // region Fields
    @androidx.annotation.NonNull private final android.app.Activity activity;

    @androidx.annotation.Nullable @java.lang.SuppressWarnings({"CStyleArrayDeclaration"})
    private org.wheatgenetics.coordinate.display.Element elementArray[][];
    // endregion

    // region Protected Methods
    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @androidx.annotation.NonNull
    protected abstract org.wheatgenetics.coordinate.display.Element makeElement(
    final org.wheatgenetics.coordinate.model.ElementModel elementModel,
    final android.widget.TextView                         textView    );


    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @androidx.annotation.NonNull protected android.app.Activity getActivity()
    { return this.activity; }

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @androidx.annotation.Nullable protected org.wheatgenetics.coordinate.display.Element[][]
    getElementArray() { return this.elementArray; }
    // endregion

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    protected Elements(@androidx.annotation.NonNull final android.app.Activity activity)
    { super(); this.activity = activity; }

    @android.annotation.SuppressLint({"InflateParams"}) @androidx.annotation.Nullable
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

                @java.lang.SuppressWarnings({"CStyleArrayDeclaration"})
                @androidx.annotation.Nullable final org.wheatgenetics.coordinate.display.Element
                    elementArray[][] = this.getElementArray();
                if (null == elementArray)
                    return null;
                else
                {
                    final org.wheatgenetics.coordinate.display.Element element =
                        this.makeElement(elementModel, (android.widget.TextView)
                            result.findViewById(org.wheatgenetics.coordinate.R.id.displayTextView));
                    elementArray[element.getRow()][element.getCol()] = element;
                }
            }
            return result;
        }
    }

    protected void clear() { this.elementArray = null; }

    public void allocate(
    @androidx.annotation.IntRange(from = 1) final int rows,
    @androidx.annotation.IntRange(from = 1) final int cols)
    {
        this.elementArray = new org.wheatgenetics.coordinate.display.Element
            [org.wheatgenetics.coordinate.Utils.valid(rows,1)]
            [org.wheatgenetics.coordinate.Utils.valid(cols,1)];
    }
}