package org.wheatgenetics.coordinate.deleter;

/**
 * Uses:
 * android.content.Context
 *
 * androidx.annotation.NonNull
 * androidx.annotation.RestrictTo
 * androidx.annotation.RestrictTo.Scope
 *
 * org.wheatgenetics.coordinate.database.EntriesTable
 * org.wheatgenetics.coordinate.database.GridsTable
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
abstract class BaseGridDeleter extends java.lang.Object
{
    // region Fields
    @androidx.annotation.NonNull private final android.content.Context contextInstance;

    // region Table Fields
    private org.wheatgenetics.coordinate.database.EntriesTable entriesTableInstance = null;    // ll
    private org.wheatgenetics.coordinate.database.GridsTable   gridsTableInstance   = null;    // ll
    // endregion
    // endregion

    // region Package Methods
    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @androidx.annotation.NonNull android.content.Context context() { return this.contextInstance; }

    // region Table Package Methods
    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @androidx.annotation.NonNull org.wheatgenetics.coordinate.database.EntriesTable entriesTable()
    {
        if (null == this.entriesTableInstance) this.entriesTableInstance =
            new org.wheatgenetics.coordinate.database.EntriesTable(this.context());
        return this.entriesTableInstance;
    }

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @androidx.annotation.NonNull org.wheatgenetics.coordinate.database.GridsTable gridsTable()
    {
        if (null == this.gridsTableInstance) this.gridsTableInstance =
            new org.wheatgenetics.coordinate.database.GridsTable(this.context());
        return this.gridsTableInstance;
    }
    // endregion
    // endregion

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    BaseGridDeleter(@androidx.annotation.NonNull final android.content.Context context)
    { super(); this.contextInstance = context; }
}