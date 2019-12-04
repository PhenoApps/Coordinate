package org.wheatgenetics.coordinate.database;

/**
 * Uses:
 * android.content.Context
 *
 * androidx.annotation.NonNull
 * androidx.annotation.RestrictTo
 * androidx.annotation.RestrictTo.Scope
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

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    org.wheatgenetics.coordinate.model.CurrentGridUniqueEntryModels
    getCurrentGridUniqueEntryModels() { return this.currentGridUniqueEntryModels; }

    // region Constructors
    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    CurrentGridUniqueEntriesTable(final android.content.Context context,
    @androidx.annotation.NonNull final java.lang.String tag) { super(context, tag); }

    public CurrentGridUniqueEntriesTable(final android.content.Context context)
    { this(context,"CurrentGridUniqueEntriesTable"); }
    // endregion

    // region Overridden Methods
    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @java.lang.Override @androidx.annotation.NonNull
    org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.Checker checker()
    { return this.getCurrentGridUniqueEntryModels(); }

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @java.lang.Override org.wheatgenetics.coordinate.model.EntryModels makeEntryModels(
    final long gridId, final int rows, final int cols)
    {
        this.currentGridUniqueEntryModels =
            new org.wheatgenetics.coordinate.model.CurrentGridUniqueEntryModels(gridId, rows, cols);
        return this.getCurrentGridUniqueEntryModels();
    }
    // endregion
}