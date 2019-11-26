package org.wheatgenetics.coordinate;

/**
 * Uses:
 * android.support.annotation.DrawableRes
 * android.support.annotation.IntRange
 * android.support.annotation.NonNull
 * android.support.annotation.Nullable
 * android.support.annotation.RestrictTo
 * android.support.annotation.RestrictTo.Scope
 * android.support.annotation.VisibleForTesting
 * android.view.View
 * android.view.View.OnClickListener
 * android.view.View.OnLongClickListener
 * android.widget.TextView
 *
 * org.wheatgenetics.coordinate.model.ElementModel
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
public abstract class Element extends java.lang.Object implements android.view.View.OnClickListener
{
    @java.lang.SuppressWarnings({"UnnecessaryInterfaceModifier"}) public interface Handler
    {
        public abstract void toggle(@android.support.annotation.Nullable
        org.wheatgenetics.coordinate.model.ElementModel elementModel);
    }

    // region Fields
    @android.support.annotation.NonNull private final android.widget.TextView              textView;
    @android.support.annotation.NonNull private final org.wheatgenetics.coordinate.Element.Handler
                                            handler;

    @android.support.annotation.Nullable protected org.wheatgenetics.coordinate.model.ElementModel
        elementModel;
    // endregion

    private void setOnClickListener(
    @android.support.annotation.Nullable final android.view.View.OnClickListener onClickListener)
    { this.textView.setOnClickListener(onClickListener); }

    @java.lang.SuppressWarnings({"DefaultAnnotationParam"})
    @android.support.annotation.VisibleForTesting(
        otherwise = android.support.annotation.VisibleForTesting.PRIVATE)
    void clearOnLongClickListener() { this.setOnLongClickListener(null); }

    // region Protected Methods
    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    protected void setOnClickListener() { this.setOnClickListener(this); }

    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    protected void clearOnClickListener() { this.setOnClickListener(null); }


    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    protected void setOnLongClickListener(@android.support.annotation.Nullable
    final android.view.View.OnLongClickListener onLongClickListener)
    { this.textView.setOnLongClickListener(onLongClickListener); }


    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    protected boolean elementModelIsNotNull() { return null != this.elementModel; }


    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    protected abstract void respondToClick();


    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    protected void setBackgroundResource(@android.support.annotation.DrawableRes final int resId)
    { this.textView.setBackgroundResource(resId); }

    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    protected abstract void setBackgroundResource();

    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    protected void toggle()
    { this.setBackgroundResource(); this.handler.toggle(this.elementModel); }
    // endregion

    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    protected Element(@android.support.annotation.Nullable final
        org.wheatgenetics.coordinate.model.ElementModel elementModel,
    @android.support.annotation.NonNull final android.widget.TextView                      textView,
    @android.support.annotation.NonNull final org.wheatgenetics.coordinate.Element.Handler handler )
    { super(); this.elementModel = elementModel; this.textView = textView; this.handler = handler; }

    // region android.view.View.OnClickListener Overridden Method
    @java.lang.Override public void onClick(final android.view.View v) { this.respondToClick(); }
    // endregion

    // region Public Methods
    @android.support.annotation.IntRange(from = -1) public int getRow()
    { return null == this.elementModel ? -1 : this.elementModel.getRowValue() - 1; }

    @android.support.annotation.IntRange(from = -1) public int getCol()
    { return null == this.elementModel ? -1 : this.elementModel.getColValue() - 1; }
    // endregion
}