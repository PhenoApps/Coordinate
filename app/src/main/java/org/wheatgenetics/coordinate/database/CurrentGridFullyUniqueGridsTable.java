package org.wheatgenetics.coordinate.database;

/**
 * Uses:
 * android.content.Context
 * android.support.annotation.NonNull
 * android.support.annotation.RestrictTo
 * android.support.annotation.RestrictTo.Scope
 *
 * org.wheatgenetics.coordinate.database.CurrentGridFullyUniqueEntriesTable
 * org.wheatgenetics.coordinate.database.CurrentGridFullyUniqueEntriesTable.Handler
 * org.wheatgenetics.coordinate.database.CurrentGridUniqueGridsTable
 * org.wheatgenetics.coordinate.database.EntriesTable
 */
public class CurrentGridFullyUniqueGridsTable
extends org.wheatgenetics.coordinate.database.CurrentGridUniqueGridsTable
{
    @android.support.annotation.NonNull private final
        org.wheatgenetics.coordinate.database.CurrentGridFullyUniqueEntriesTable.Handler handler;

    public CurrentGridFullyUniqueGridsTable(final android.content.Context context,
    @android.support.annotation.NonNull final
        org.wheatgenetics.coordinate.database.CurrentGridFullyUniqueEntriesTable.Handler handler)
    { super(context,"CurrentGridFullyUniqueGridsTable"); this.handler = handler; }

    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    @java.lang.Override org.wheatgenetics.coordinate.database.EntriesTable makeEntriesTable()
    {
        return new org.wheatgenetics.coordinate.database.CurrentGridFullyUniqueEntriesTable(
            this.getContext(), this.handler);
    }
}