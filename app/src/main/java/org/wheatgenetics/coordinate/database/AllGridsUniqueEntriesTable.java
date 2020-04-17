package org.wheatgenetics.coordinate.database;

/**
 * Uses:
 * android.content.Context
 * 
 * androidx.annotation.IntRange
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 * androidx.annotation.RestrictTo
 * androidx.annotation.RestrictTo.Scope
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

    private org.wheatgenetics.coordinate.model.AllGridsUniqueEntryModels
    getAllGridsUniqueEntryModels() { return this.allGridsUniqueEntryModels; }

    public AllGridsUniqueEntriesTable(final android.content.Context context)
    { super(context,"AllGridsUniqueEntriesTable"); }

    // region Overridden Methods
    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @java.lang.Override @androidx.annotation.NonNull
    org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.Checker checker()
    { return this.getAllGridsUniqueEntryModels(); }

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @java.lang.Override org.wheatgenetics.coordinate.model.EntryModels makeEntryModels(
    final long gridId, final int rows, final int cols)
    {
        this.allGridsUniqueEntryModels =
            new org.wheatgenetics.coordinate.model.AllGridsUniqueEntryModels(
                gridId, rows, cols,this,this);
        return this.getAllGridsUniqueEntryModels();
    }

    // region org.wheatgenetics.coordinate.model.DatabaseUniqueEntryModels.Checker Overridden Method
    @java.lang.Override @androidx.annotation.Nullable public java.lang.String check(
    @androidx.annotation.IntRange(from = 1) final long             gridId,
    @androidx.annotation.Nullable           final java.lang.String value ,
    @androidx.annotation.NonNull            final java.lang.String scope ) throws
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
                .DatabaseUniqueEntryModels.DatabaseDuplicateCheckException(scope, this);
        else
            return value;
    }
    // endregion
    // endregion
}