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
 * org.wheatgenetics.coordinate.model.AllGridsUniqueEntryModels.Checker
 * org.wheatgenetics.coordinate.model.AllGridsUniqueJoinedGridModel
 * org.wheatgenetics.coordinate.model.AllGridsUniqueJoinedGridModels
 * org.wheatgenetics.coordinate.model.BaseJoinedGridModels
 * org.wheatgenetics.coordinate.model.DatabaseUniqueEntryModels.DatabaseDuplicateCheckException
 * org.wheatgenetics.coordinate.model.EntryModels
 * org.wheatgenetics.coordinate.model.JoinedGridModel
 *
 * org.wheatgenetics.coordinate.database.AllGridsUniqueEntriesTable
 * org.wheatgenetics.coordinate.database.EntriesTable
 * org.wheatgenetics.coordinate.database.GridsTable
 * org.wheatgenetics.coordinate.database.Table
 */
public class AllGridsUniqueGridsTable extends org.wheatgenetics.coordinate.database.GridsTable
implements org.wheatgenetics.coordinate.model.AllGridsUniqueEntryModels.Checker
{
    private boolean exists(final long id, final java.lang.String value)
    {
        final java.lang.String sql;
        {
            final java.lang.String
                GRIDS_TABLE = '[' +
                    org.wheatgenetics.coordinate.database.AllGridsUniqueGridsTable.TABLE_NAME + ']',
                ENTRIES_TABLE = '[' +
                    org.wheatgenetics.coordinate.database.EntriesTable.TABLE_NAME + ']';
            final java.lang.String
                GRIDS_TABLE_ID_FIELD = GRIDS_TABLE + ".[" +
                    org.wheatgenetics.coordinate.database.Table.ID_FIELD_NAME + ']',
                ENTRIES_TABLE_EDATA_FIELD = ENTRIES_TABLE + ".[" +
                    org.wheatgenetics.coordinate.database.EntriesTable.EDATA_FIELD_NAME + ']',
                ENTRIES_TABLE_GRID_FIELD = ENTRIES_TABLE + ".[" +
                    org.wheatgenetics.coordinate.database.EntriesTable.GRID_FIELD_NAME + ']';
            sql = java.lang.String.format("SELECT ALL %s, %s " +
                    "FROM %s INNER JOIN %s ON %s = %s " +
                    "WHERE %s <> ? AND %s = ?",
                GRIDS_TABLE_ID_FIELD, ENTRIES_TABLE_EDATA_FIELD,
                    GRIDS_TABLE, ENTRIES_TABLE, GRIDS_TABLE_ID_FIELD, ENTRIES_TABLE_GRID_FIELD,
                    GRIDS_TABLE_ID_FIELD, ENTRIES_TABLE_EDATA_FIELD);
        }
        return org.wheatgenetics.coordinate.database.Table.exists(this.rawQuery(
            /* sql           => */ sql                                                        ,
            /* selectionArgs => */ new java.lang.String[]{java.lang.String.valueOf(id), value}));
    }

    public AllGridsUniqueGridsTable(final android.content.Context context)
    { super(context,"AllGridsUniqueGridsTable"); }

    // region Overridden Methods
    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    @java.lang.Override org.wheatgenetics.coordinate.database.EntriesTable makeEntriesTable()
    {
        return new org.wheatgenetics.coordinate.database.AllGridsUniqueEntriesTable(
            this.getContext());
    }

    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    @java.lang.Override
    org.wheatgenetics.coordinate.model.BaseJoinedGridModels makeJoinedGridModels()
    { return new org.wheatgenetics.coordinate.model.AllGridsUniqueJoinedGridModels(); }

    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    @java.lang.Override org.wheatgenetics.coordinate.model.JoinedGridModel makeJoinedGridModel(
    @android.support.annotation.IntRange(from = 1) final long             id            ,
    @android.support.annotation.IntRange(from = 0) final long             projectId     ,
                                                   final java.lang.String person        ,
    @android.support.annotation.IntRange(from = 0) final int              activeRow     ,
    @android.support.annotation.IntRange(from = 0) final int              activeCol     ,
    @android.support.annotation.Nullable           final java.lang.String optionalFields,
    @android.support.annotation.IntRange(from = 0) final long             timestamp     ,

    @android.support.annotation.IntRange(from = 1        ) final long             templateId     ,
                                                           final java.lang.String title          ,
    @android.support.annotation.IntRange(from = 0, to = 2) final int              code           ,
    @android.support.annotation.IntRange(from = 1        ) final int              rows           ,
    @android.support.annotation.IntRange(from = 1        ) final int              cols           ,
    @android.support.annotation.IntRange(from = 0        ) final int generatedExcludedCellsAmount,
    @android.support.annotation.Nullable final java.lang.String initialExcludedCells,
    @android.support.annotation.Nullable final java.lang.String excludedRows        ,
    @android.support.annotation.Nullable final java.lang.String excludedCols        ,
    @android.support.annotation.IntRange(from = 0, to = 1) final int      colNumbering          ,
    @android.support.annotation.IntRange(from = 0, to = 1) final int      rowNumbering          ,
                                                   final java.lang.String entryLabel            ,
    @android.support.annotation.Nullable           final java.lang.String templateOptionalFields,
    @android.support.annotation.IntRange(from = 0) final long             templateTimestamp     ,

    final org.wheatgenetics.coordinate.model.EntryModels entryModels)
    {
        return new org.wheatgenetics.coordinate.model.AllGridsUniqueJoinedGridModel(id,
            projectId, person, activeRow, activeCol, optionalFields, timestamp, templateId, title,
            code, rows, cols, generatedExcludedCellsAmount, initialExcludedCells, excludedRows,
            excludedCols, colNumbering, rowNumbering, entryLabel, templateOptionalFields,
            templateTimestamp,
            (org.wheatgenetics.coordinate.model.AllGridsUniqueEntryModels) entryModels,
            this);
    }

    // region org.wheatgenetics.coordinate.model.AllGridsUniqueEntryModels.Checker Overridden Method
    @java.lang.Override @android.support.annotation.Nullable public java.lang.String check(
                                         final long             gridId,
    @android.support.annotation.Nullable final java.lang.String value ,
    @android.support.annotation.NonNull  final java.lang.String scope ) throws
    org.wheatgenetics.coordinate.model.DatabaseUniqueEntryModels.DatabaseDuplicateCheckException
    {
        if (this.exists(gridId, value))
            throw new org.wheatgenetics.coordinate.model
                .DatabaseUniqueEntryModels.DatabaseDuplicateCheckException(scope);
        else
            return value;
    }
    // endregion
    // endregion
}