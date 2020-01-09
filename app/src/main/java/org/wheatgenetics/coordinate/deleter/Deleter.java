package org.wheatgenetics.coordinate.deleter;

/**
 * Uses:
 * android.content.Context
 *
 * androidx.annotation.NonNull
 * androidx.annotation.RestrictTo
 * androidx.annotation.RestrictTo.Scope
 * androidx.annotation.StringRes
 *
 * org.wheatgenetics.androidlibrary.Utils
 *
 * org.wheatgenetics.coordinate.database.GridsTable
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
abstract class Deleter extends java.lang.Object
{
    // region Fields
    @androidx.annotation.NonNull private final android.content.Context context;

    private org.wheatgenetics.coordinate.database.GridsTable gridsTableInstance = null; // lazy load
    // endregion

    // region Package Methods
    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @androidx.annotation.NonNull android.content.Context context() { return this.context; }

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @androidx.annotation.NonNull org.wheatgenetics.coordinate.database.GridsTable gridsTable()
    {
        if (null == this.gridsTableInstance) this.gridsTableInstance =
            new org.wheatgenetics.coordinate.database.GridsTable(this.context());
        return this.gridsTableInstance;
    }

    // region Toast Package Methods
    // region Long Toast Package Methods
    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    void showLongToast(final java.lang.String text)
    { org.wheatgenetics.androidlibrary.Utils.showLongToast(this.context(), text); }

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    void showLongToast(@androidx.annotation.StringRes final int text)
    { this.showLongToast(this.context().getString(text)); }
    // endregion

    // region Short Toast Package Methods
    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    void showShortToast(final java.lang.String text)
    { org.wheatgenetics.androidlibrary.Utils.showShortToast(this.context(), text); }

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    void showShortToast(@androidx.annotation.StringRes final int text)
    { this.showShortToast(this.context().getString(text)); }
    // endregion
    // endregion
    // endregion

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    Deleter(@androidx.annotation.NonNull final android.content.Context context)
    { super(); this.context = context; }
}