package org.wheatgenetics.coordinate.database;

/**
 * Uses:
 * android.content.Context
 * android.support.annotation.IntRange
 * android.support.annotation.NonNull
 * android.support.annotation.Nullable
 * android.support.annotation.RestrictTo
 * android.support.annotation.RestrictTo.Scope
 *
 * org.wheatgenetics.coordinate.model.AllGridsUniqueEntryModels
 * org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.Checker
 * org.wheatgenetics.coordinate.model.DatabaseUniqueEntryModels.Checker
 * org.wheatgenetics.coordinate.model.DatabaseUniqueEntryModels.DatabaseDuplicateCheckException
 * org.wheatgenetics.coordinate.model.EntryModels
 *
 * org.wheatgenetics.coordinate.database.CheckedEntriesTable
 */
public class AllGridsUniqueEntriesTable
extends org.wheatgenetics.coordinate.database.CheckedEntriesTable
implements org.wheatgenetics.coordinate.model.DatabaseUniqueEntryModels.Checker
{
    private org.wheatgenetics.coordinate.model.AllGridsUniqueEntryModels
        allGridsUniqueEntryModels = null;

    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    private org.wheatgenetics.coordinate.model.AllGridsUniqueEntryModels
    getAllGridsUniqueEntryModels() { return this.allGridsUniqueEntryModels; }

    public AllGridsUniqueEntriesTable(final android.content.Context context)
    { super(context,"AllGridsUniqueEntriesTable"); }

    // region Overridden Methods
    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    @java.lang.Override @android.support.annotation.NonNull
    org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.Checker checker()
    { return this.getAllGridsUniqueEntryModels(); }

    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    @java.lang.Override org.wheatgenetics.coordinate.model.EntryModels makeEntryModels(
    final long gridId, final int rows, final int cols)
    {
        this.allGridsUniqueEntryModels =
            new org.wheatgenetics.coordinate.model.AllGridsUniqueEntryModels(
                gridId, rows, cols,this);
        return this.getAllGridsUniqueEntryModels();
    }

    // region org.wheatgenetics.coordinate.model.DatabaseUniqueEntryModels.Checker Overridden Method
    @java.lang.Override @android.support.annotation.Nullable public java.lang.String check(
    @android.support.annotation.IntRange(from = 1) final long             gridId,
    @android.support.annotation.Nullable           final java.lang.String value ,
    @android.support.annotation.NonNull            final java.lang.String scope ) throws
    org.wheatgenetics.coordinate.model.DatabaseUniqueEntryModels.DatabaseDuplicateCheckException
    {
        // noinspection CStyleArrayDeclaration
        final java.lang.String
            selection =
                org.wheatgenetics.coordinate.database.AllGridsUniqueEntriesTable.GRID_FIELD_NAME  +
                " <> ? AND "                                                                      +
                org.wheatgenetics.coordinate.database.AllGridsUniqueEntriesTable.EDATA_FIELD_NAME +
                " = ?",
            selectionArgs[] = new java.lang.String[]{java.lang.String.valueOf(gridId), value};
        if (org.wheatgenetics.coordinate.database.AllGridsUniqueEntriesTable.exists(
        this.queryAll(selection, selectionArgs)))
            throw new org.wheatgenetics.coordinate.model
                .DatabaseUniqueEntryModels.DatabaseDuplicateCheckException(scope);
        else
            return value;
    }
    // endregion
    // endregion
}