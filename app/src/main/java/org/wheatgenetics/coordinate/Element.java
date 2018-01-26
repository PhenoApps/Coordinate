package org.wheatgenetics.coordinate;

/**
 * Uses:
 * android.support.annotation.RestrictTo
 * android.support.annotation.RestrictTo.Scope
 * android.view.View
 * android.view.View.OnLongClickListener
 * android.widget.TextView
 *
 * org.wheatgenetics.coordinate.model.ElementModel
 */
@java.lang.SuppressWarnings("ClassExplicitlyExtendsObject")
public abstract class Element extends java.lang.Object
implements android.view.View.OnLongClickListener
{
    @java.lang.SuppressWarnings("UnnecessaryInterfaceModifier")
    public interface Handler
    { public abstract void toggle(org.wheatgenetics.coordinate.model.ElementModel elementModel); }

    // region Fields
    private final android.widget.TextView                      textView;
    private final org.wheatgenetics.coordinate.Element.Handler handler ;

    protected org.wheatgenetics.coordinate.model.ElementModel elementModel;
    // endregion

    // region Protected Methods
    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    protected android.widget.TextView getTextView() { return this.textView; }

    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    protected org.wheatgenetics.coordinate.Element.Handler getHandler() { return this.handler; }


    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    protected abstract void respondToLongClick();


    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    protected void setBackgroundResource(final int resId)
    { assert null != this.textView; this.textView.setBackgroundResource(resId); }

    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    protected abstract void setBackgroundResource();

    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    protected void toggle()
    { this.setBackgroundResource(); this.getHandler().toggle(this.elementModel); }
    // endregion

    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    protected Element(
    final org.wheatgenetics.coordinate.model.ElementModel elementModel,
    final android.widget.TextView                         textView    ,
    final org.wheatgenetics.coordinate.Element.Handler    handler     )
    {
        super();

        this.elementModel = elementModel; this.textView = textView; this.handler = handler;
        assert null != this.textView; this.textView.setOnLongClickListener(this);
    }

    // region android.view.View.OnLongClickListener Overridden Method
    @java.lang.Override
    public boolean onLongClick(final android.view.View v)
    { if (null != this.elementModel) this.respondToLongClick(); return true; }
    // endregion

    // region Public Methods
    public int getRow()
    { return null == this.elementModel ? -1 : this.elementModel.getRowValue() - 1; }

    public int getCol()
    { return null == this.elementModel ? -1 : this.elementModel.getColValue() - 1; }
    // endregion
}