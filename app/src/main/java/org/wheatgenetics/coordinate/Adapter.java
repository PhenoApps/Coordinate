package org.wheatgenetics.coordinate;

/**
 * Uses:
 * android.app.Activity
 * android.content.res.Resources
 * android.view.View.OnClickListener
 * android.widget.BaseAdapter
 * android.widget.TableLayout
 *
 * androidx.annotation.NonNull
 * androidx.annotation.RestrictTo
 * androidx.annotation.RestrictTo.Scope
 */
public abstract class Adapter extends android.widget.BaseAdapter
{
    // region Fields
    // region Constructor Fields
    @androidx.annotation.NonNull private final android.app.Activity              activity;
    @androidx.annotation.NonNull private final android.view.View.OnClickListener
        onDeleteButtonClickListener;
    // endregion

    private android.content.res.Resources resourcesInstance = null;                     // lazy load
    // endregion

    // region Protected Methods
    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @androidx.annotation.NonNull protected android.app.Activity activity() { return this.activity; }

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @androidx.annotation.NonNull protected android.view.View.OnClickListener
    onDeleteButtonClickListener() { return this.onDeleteButtonClickListener; }

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @androidx.annotation.NonNull protected android.widget.TableLayout makeEmptyTableLayout()
    { return new android.widget.TableLayout(this.activity()); }

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @androidx.annotation.NonNull protected android.content.res.Resources resources()
    {
        if (null == this.resourcesInstance) this.resourcesInstance = this.activity().getResources();
        return this.resourcesInstance;
    }
    // endregion

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    protected Adapter(@androidx.annotation.NonNull final android.app.Activity activity,
    @androidx.annotation.NonNull final android.view.View.OnClickListener
        onDeleteButtonClickListener)
    {
        super();
        this.activity = activity; this.onDeleteButtonClickListener = onDeleteButtonClickListener;
    }
}