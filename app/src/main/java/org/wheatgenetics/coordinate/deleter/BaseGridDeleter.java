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
 *
 * org.wheatgenetics.coordinate.deleter.Deleter
 */
abstract class BaseGridDeleter extends org.wheatgenetics.coordinate.deleter.Deleter
{
    private org.wheatgenetics.coordinate.database.EntriesTable entriesTableInstance = null;    // ll

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @androidx.annotation.NonNull org.wheatgenetics.coordinate.database.EntriesTable entriesTable()
    {
        if (null == this.entriesTableInstance) this.entriesTableInstance =
            new org.wheatgenetics.coordinate.database.EntriesTable(this.context());
        return this.entriesTableInstance;
    }

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    BaseGridDeleter(@androidx.annotation.NonNull final android.content.Context context)
    { super(context); }
}