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
                    org.wheatgenetics.coordinate.database.AllGridsUniqueGridsTable.ID_FIELD_NAME +
                    ']',
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
        return org.wheatgenetics.coordinate.database.AllGridsUniqueGridsTable.exists(this.rawQuery(
            /* sql           => */ sql                                                        ,
            /* selectionArgs => */ new java.lang.String[]{java.lang.String.valueOf(id), value}));
    }

    public AllGridsUniqueGridsTable(final android.content.Context context)
    { super(context,"AllGridsUniqueGridsTable"); }

    // region Overridden Methods
    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @java.lang.Override org.wheatgenetics.coordinate.database.EntriesTable makeEntriesTable()
    {
        return new org.wheatgenetics.coordinate.database.AllGridsUniqueEntriesTable(
            this.getContext());
    }

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @java.lang.Override
    org.wheatgenetics.coordinate.model.BaseJoinedGridModels makeJoinedGridModels()
    {
        return new org.wheatgenetics.coordinate.model.AllGridsUniqueJoinedGridModels(
            this);
    }

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @java.lang.Override org.wheatgenetics.coordinate.model.JoinedGridModel makeJoinedGridModel(
    @androidx.annotation.IntRange(from = 1) final long             id            ,
    @androidx.annotation.IntRange(from = 0) final long             projectId     ,
                                            final java.lang.String person        ,
    @androidx.annotation.IntRange(from = 0) final int              activeRow     ,
    @androidx.annotation.IntRange(from = 0) final int              activeCol     ,
    @androidx.annotation.Nullable           final java.lang.String optionalFields,
    @androidx.annotation.IntRange(from = 0) final long             timestamp     ,

    @androidx.annotation.IntRange(from = 1        ) final long             templateId            ,
                                                    final java.lang.String title                 ,
    @androidx.annotation.IntRange(from = 0, to = 2) final int              code                  ,
    @androidx.annotation.IntRange(from = 1        ) final int              rows                  ,
    @androidx.annotation.IntRange(from = 1        ) final int              cols                  ,
    @androidx.annotation.IntRange(from = 0        ) final int generatedExcludedCellsAmount       ,
    @androidx.annotation.Nullable                   final java.lang.String initialExcludedCells  ,
    @androidx.annotation.Nullable                   final java.lang.String excludedRows          ,
    @androidx.annotation.Nullable                   final java.lang.String excludedCols          ,
    @androidx.annotation.IntRange(from = 0, to = 1) final int              colNumbering          ,
    @androidx.annotation.IntRange(from = 0, to = 1) final int              rowNumbering          ,
                                                    final java.lang.String entryLabel            ,
    @androidx.annotation.Nullable                   final java.lang.String templateOptionalFields,
    @androidx.annotation.IntRange(from = 0)         final long             templateTimestamp     ,

    final org.wheatgenetics.coordinate.model.EntryModels entryModels)
    {
        return new org.wheatgenetics.coordinate.model.AllGridsUniqueJoinedGridModel(id,
            projectId, person, activeRow, activeCol, optionalFields,this, timestamp,
            templateId, title, code, rows, cols, generatedExcludedCellsAmount, initialExcludedCells,
            excludedRows, excludedCols, colNumbering, rowNumbering, entryLabel,
            templateOptionalFields, templateTimestamp,
            (org.wheatgenetics.coordinate.model.AllGridsUniqueEntryModels) entryModels,
            this);
    }

    // region org.wheatgenetics.coordinate.model.AllGridsUniqueEntryModels.Checker Overridden Method
    @java.lang.Override @androidx.annotation.Nullable public java.lang.String check(
                                  final long             gridId,
    @androidx.annotation.Nullable final java.lang.String value ,
    @androidx.annotation.NonNull  final java.lang.String scope ) throws
    org.wheatgenetics.coordinate.model.DatabaseUniqueEntryModels.DatabaseDuplicateCheckException
    {
        if (this.exists(gridId, value))
            throw new org.wheatgenetics.coordinate.model
                .DatabaseUniqueEntryModels.DatabaseDuplicateCheckException(scope, this);
        else
            return value;
    }
    // endregion
    // endregion
}