package org.wheatgenetics.coordinate;

/**
 * Uses:
 * android.app.Activity
 * android.content.res.Resources
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
    @androidx.annotation.NonNull private final android.app.Activity          activity;
                                 private       android.content.res.Resources
                                     resourcesInstance = null;     // lazy load
    // endregion

    // region Protected Methods
    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @androidx.annotation.NonNull protected android.app.Activity activity() { return this.activity; }

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
    protected Adapter(@androidx.annotation.NonNull final android.app.Activity activity)
    { super(); this.activity = activity; }
}