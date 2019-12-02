package org.wheatgenetics.coordinate.database;

/**
 * Uses:
 * android.content.Context
 * android.support.annotation.NonNull
 * android.support.annotation.RestrictTo
 * android.support.annotation.RestrictTo.Scope
 *
 * org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.Checker
 * org.wheatgenetics.coordinate.model.CurrentProjectUniqueEntryModels
 * org.wheatgenetics.coordinate.model.DatabaseUniqueEntryModels.Checker
 * org.wheatgenetics.coordinate.model.EntryModels
 *
 * org.wheatgenetics.coordinate.database.CheckedEntriesTable
 */
public class CurrentProjectUniqueEntriesTable
extends org.wheatgenetics.coordinate.database.CheckedEntriesTable
{
    // region Fields
    @android.support.annotation.NonNull private final
        org.wheatgenetics.coordinate.model.DatabaseUniqueEntryModels.Checker checker;

    private org.wheatgenetics.coordinate.model.CurrentProjectUniqueEntryModels
        currentProjectUniqueEntryModels = null;
    // endregion

    private org.wheatgenetics.coordinate.model.CurrentProjectUniqueEntryModels
    getCurrentProjectUniqueEntryModels() { return this.currentProjectUniqueEntryModels; }

    public CurrentProjectUniqueEntriesTable(final android.content.Context context,
    @android.support.annotation.NonNull final
        org.wheatgenetics.coordinate.model.DatabaseUniqueEntryModels.Checker checker)
    { super(context,"CurrentProjectUniqueEntriesTable"); this.checker = checker; }

    // region Overridden Methods
    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    @java.lang.Override @android.support.annotation.NonNull
    org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.Checker checker()
    { return this.getCurrentProjectUniqueEntryModels(); }

    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    @java.lang.Override org.wheatgenetics.coordinate.model.EntryModels makeEntryModels(
    final long gridId, final int rows, final int cols)
    {
        this.currentProjectUniqueEntryModels =
            new org.wheatgenetics.coordinate.model.CurrentProjectUniqueEntryModels(
                gridId, rows, cols, this.checker);
        return this.getCurrentProjectUniqueEntryModels();
    }
    // endregion
}