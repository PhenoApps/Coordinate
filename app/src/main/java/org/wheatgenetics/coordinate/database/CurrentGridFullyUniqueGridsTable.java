package org.wheatgenetics.coordinate.database;

/**
 * Uses:
 * android.content.Context
 *
 * androidx.annotation.NonNull
 * androidx.annotation.RestrictTo
 * androidx.annotation.RestrictTo.Scope
 *
 * org.wheatgenetics.coordinate.database.CurrentGridFullyUniqueEntriesTable
 * org.wheatgenetics.coordinate.database.CurrentGridFullyUniqueEntriesTable.Handler
 * org.wheatgenetics.coordinate.database.CurrentGridUniqueGridsTable
 * org.wheatgenetics.coordinate.database.EntriesTable
 */
@java.lang.SuppressWarnings({"unused"}) public class CurrentGridFullyUniqueGridsTable
extends org.wheatgenetics.coordinate.database.CurrentGridUniqueGridsTable
{
    @androidx.annotation.NonNull private final
        org.wheatgenetics.coordinate.database.CurrentGridFullyUniqueEntriesTable.Handler handler;

    public CurrentGridFullyUniqueGridsTable(final android.content.Context context,
    @androidx.annotation.NonNull final
        org.wheatgenetics.coordinate.database.CurrentGridFullyUniqueEntriesTable.Handler handler)
    { super(context,"CurrentGridFullyUniqueGridsTable"); this.handler = handler; }

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @java.lang.Override org.wheatgenetics.coordinate.database.EntriesTable makeEntriesTable()
    {
        return new org.wheatgenetics.coordinate.database.CurrentGridFullyUniqueEntriesTable(
            this.getContext(), this.handler);
    }
}