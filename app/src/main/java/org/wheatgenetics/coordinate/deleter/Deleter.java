package org.wheatgenetics.coordinate.deleter;

/**
 * Uses:
 * android.content.Context
 *
 * androidx.annotation.NonNull
 * androidx.annotation.RestrictTo
 * androidx.annotation.RestrictTo.Scope
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
    // endregion

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    Deleter(@androidx.annotation.NonNull final android.content.Context context)
    { super(); this.context = context; }
}