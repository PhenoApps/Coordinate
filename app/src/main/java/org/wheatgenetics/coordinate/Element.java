package org.wheatgenetics.coordinate;

/**
 * Uses:
 * android.support.annotation.DrawableRes
 * android.support.annotation.RestrictTo
 * android.support.annotation.RestrictTo.Scope
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
    @java.lang.SuppressWarnings({"UnnecessaryInterfaceModifier"})
    public interface Handler
    { public abstract void toggle(org.wheatgenetics.coordinate.model.ElementModel elementModel); }

    // region Fields
    private final android.widget.TextView                      textView;
    private final org.wheatgenetics.coordinate.Element.Handler handler ;

    protected org.wheatgenetics.coordinate.model.ElementModel elementModel;
    // endregion

    private void setOnClickListener(final android.view.View.OnClickListener onClickListener)
    { assert null != this.textView; this.textView.setOnClickListener(onClickListener); }

    // region Protected Methods
    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    protected void setOnClickListener() { this.setOnClickListener(this); }

    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    protected void clearOnClickListener() { this.setOnClickListener(null); }


    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    protected void setOnLongClickListener(
    final android.view.View.OnLongClickListener onLongClickListener)
    { assert null != this.textView; this.textView.setOnLongClickListener(onLongClickListener); }

    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    protected void clearOnLongClickListener() { this.setOnLongClickListener(null); }


    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    protected org.wheatgenetics.coordinate.Element.Handler getHandler() { return this.handler; }


    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    protected boolean elementModelIsNotNull() { return null != this.elementModel; }


    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    protected abstract void respondToClick();


    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    protected void setBackgroundResource(@android.support.annotation.DrawableRes final int resId)
    { assert null != this.textView; this.textView.setBackgroundResource(resId); }

    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    protected abstract void setBackgroundResource();

    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    protected void toggle()
    {
        this.setBackgroundResource();
        assert null != this.handler; this.handler.toggle(this.elementModel);
    }
    // endregion

    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    protected Element(
    final org.wheatgenetics.coordinate.model.ElementModel elementModel,
    final android.widget.TextView                         textView    ,
    final org.wheatgenetics.coordinate.Element.Handler    handler     )
    { super(); this.elementModel = elementModel; this.textView = textView; this.handler = handler; }

    // region android.view.View.OnClickListener Overridden Method
    @java.lang.Override public void onClick(final android.view.View v) { this.respondToClick(); }
    // endregion

    // region Public Methods
    public int getRow()
    { return null == this.elementModel ? -1 : this.elementModel.getRowValue() - 1; }

    public int getCol()
    { return null == this.elementModel ? -1 : this.elementModel.getColValue() - 1; }
    // endregion
}