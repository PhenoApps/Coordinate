package org.wheatgenetics.coordinate;

/**
 * Uses:
 * android.app.Activity
 * android.widget.BaseAdapter
 * android.widget.TableLayout
 *
 * androidx.annotation.NonNull
 * androidx.annotation.RestrictTo
 * androidx.annotation.RestrictTo.Scope
 */
public abstract class Adapter extends android.widget.BaseAdapter
{
    @androidx.annotation.NonNull private final android.app.Activity activity;

    // region Protected Methods
    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @androidx.annotation.NonNull protected android.app.Activity activity() { return this.activity; }

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @androidx.annotation.NonNull protected android.widget.TableLayout makeEmptyTableLayout()
    { return new android.widget.TableLayout(this.activity()); }
    // endregion

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    protected Adapter(@androidx.annotation.NonNull final android.app.Activity activity)
    { super(); this.activity = activity; }
}