package org.wheatgenetics.coordinate.database;

/**
 * Uses:
 * android.content.Context
 * android.support.annotation.NonNull
 * android.support.annotation.RestrictTo
 * android.support.annotation.RestrictTo.Scope
 *
 * org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.Checker
 * org.wheatgenetics.coordinate.model.CurrentGridUniqueEntryModels
 * org.wheatgenetics.coordinate.model.EntryModels
 *
 * org.wheatgenetics.coordinate.database.CheckedEntriesTable
 */
public class CurrentGridUniqueEntriesTable
extends org.wheatgenetics.coordinate.database.CheckedEntriesTable
{
    private org.wheatgenetics.coordinate.model.CurrentGridUniqueEntryModels
        currentGridUniqueEntryModels = null;

    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    org.wheatgenetics.coordinate.model.CurrentGridUniqueEntryModels
    getCurrentGridUniqueEntryModels() { return this.currentGridUniqueEntryModels; }

    // region Constructors
    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    CurrentGridUniqueEntriesTable(final android.content.Context context,
    @android.support.annotation.NonNull final java.lang.String tag) { super(context, tag); }

    public CurrentGridUniqueEntriesTable(final android.content.Context context)
    { this(context,"CurrentGridUniqueEntriesTable"); }
    // endregion

    // region Overridden Methods
    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    @java.lang.Override @android.support.annotation.NonNull
    org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.Checker checker()
    { return this.getCurrentGridUniqueEntryModels(); }

    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    @java.lang.Override org.wheatgenetics.coordinate.model.EntryModels makeEntryModels(
    final long gridId, final int rows, final int cols)
    {
        this.currentGridUniqueEntryModels =
            new org.wheatgenetics.coordinate.model.CurrentGridUniqueEntryModels(gridId, rows, cols);
        return this.getCurrentGridUniqueEntryModels();
    }
    // endregion
}