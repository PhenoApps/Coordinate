package org.wheatgenetics.coordinate;

/**
 * Uses:
 * android.view.View
 * android.view.View.OnClickListener
 * android.view.View.OnLongClickListener
 * android.widget.TextView
 *
 * androidx.annotation.DrawableRes
 * androidx.annotation.IntRange
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 * androidx.annotation.RestrictTo
 * androidx.annotation.RestrictTo.Scope
 * androidx.annotation.VisibleForTesting
 *
 * org.wheatgenetics.coordinate.model.ElementModel
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
public abstract class Element extends java.lang.Object implements android.view.View.OnClickListener
{
    @java.lang.SuppressWarnings({"UnnecessaryInterfaceModifier"}) public interface Handler
    {
        public abstract void toggle(@androidx.annotation.Nullable
        org.wheatgenetics.coordinate.model.ElementModel elementModel);
    }

    // region Fields
    @androidx.annotation.NonNull private final android.widget.TextView                     textView;
    @androidx.annotation.NonNull private final org.wheatgenetics.coordinate.Element.Handler handler;

    @androidx.annotation.Nullable protected org.wheatgenetics.coordinate.model.ElementModel
        elementModel;
    // endregion

    private void setOnClickListener(
    @androidx.annotation.Nullable final android.view.View.OnClickListener onClickListener)
    { this.textView.setOnClickListener(onClickListener); }

    @java.lang.SuppressWarnings({"DefaultAnnotationParam"}) @androidx.annotation.VisibleForTesting(
        otherwise = androidx.annotation.VisibleForTesting.PRIVATE)
    void clearOnLongClickListener() { this.setOnLongClickListener(null); }

    // region Protected Methods
    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    protected void setOnClickListener() { this.setOnClickListener(this); }

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    protected void clearOnClickListener() { this.setOnClickListener(null); }


    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    protected void setOnLongClickListener(@androidx.annotation.Nullable
    final android.view.View.OnLongClickListener onLongClickListener)
    { this.textView.setOnLongClickListener(onLongClickListener); }


    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    protected boolean elementModelIsNotNull() { return null != this.elementModel; }


    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    protected abstract void respondToClick();


    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    protected void setBackgroundResource(@androidx.annotation.DrawableRes final int resId)
    { this.textView.setBackgroundResource(resId); }

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    protected abstract void setBackgroundResource();

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    protected void toggle()
    { this.setBackgroundResource(); this.handler.toggle(this.elementModel); }
    // endregion

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    protected Element(@androidx.annotation.Nullable final
        org.wheatgenetics.coordinate.model.ElementModel elementModel,
    @androidx.annotation.NonNull final android.widget.TextView                      textView,
    @androidx.annotation.NonNull final org.wheatgenetics.coordinate.Element.Handler handler )
    { super(); this.elementModel = elementModel; this.textView = textView; this.handler = handler; }

    // region android.view.View.OnClickListener Overridden Method
    @java.lang.Override public void onClick(final android.view.View v) { this.respondToClick(); }
    // endregion

    // region Public Methods
    @androidx.annotation.IntRange(from = -1) public int getRow()
    { return null == this.elementModel ? -1 : this.elementModel.getRowValue() - 1; }

    @androidx.annotation.IntRange(from = -1) public int getCol()
    { return null == this.elementModel ? -1 : this.elementModel.getColValue() - 1; }
    // endregion
}