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
 * org.wheatgenetics.coordinate.model.BaseJoinedGridModels
 * org.wheatgenetics.coordinate.model.CurrentProjectUniqueEntryModels
 * org.wheatgenetics.coordinate.model.CurrentProjectUniqueJoinedGridModel
 * org.wheatgenetics.coordinate.model.CurrentProjectUniqueJoinedGridModels
 * org.wheatgenetics.coordinate.model.DatabaseUniqueEntryModels.Checker
 * org.wheatgenetics.coordinate.model.DatabaseUniqueEntryModels.DatabaseDuplicateCheckException
 * org.wheatgenetics.coordinate.model.EntryModels
 * org.wheatgenetics.coordinate.model.JoinedGridModel
 * org.wheatgenetics.coordinate.model.Model
 *
 * org.wheatgenetics.coordinate.database.CurrentProjectUniqueEntriesTable
 * org.wheatgenetics.coordinate.database.EntriesTable
 * org.wheatgenetics.coordinate.database.GridsTable
 */
public class CurrentProjectUniqueGridsTable extends org.wheatgenetics.coordinate.database.GridsTable
implements org.wheatgenetics.coordinate.model.DatabaseUniqueEntryModels.Checker
{
    // region Private Methods
    private static java.lang.String format(
    @android.support.annotation.NonNull final java.lang.String format)
    {
        final java.lang.String
            GRIDS_TABLE = '[' +
                org.wheatgenetics.coordinate.database.CurrentProjectUniqueGridsTable.TABLE_NAME +
                ']',
            ENTRIES_TABLE = '[' +
                org.wheatgenetics.coordinate.database.EntriesTable.TABLE_NAME + ']';
        final java.lang.String
            GRIDS_TABLE_ID_FIELD = GRIDS_TABLE + ".[" +
                org.wheatgenetics.coordinate.database.CurrentProjectUniqueGridsTable.ID_FIELD_NAME +
                ']',
            ENTRIES_TABLE_EDATA_FIELD = ENTRIES_TABLE + ".[" +
                org.wheatgenetics.coordinate.database.EntriesTable.EDATA_FIELD_NAME + ']',
            ENTRIES_TABLE_GRID_FIELD = ENTRIES_TABLE + ".[" +
                org.wheatgenetics.coordinate.database.EntriesTable.GRID_FIELD_NAME + ']',
            GRIDS_TABLE_PROJECTID_FIELD = GRIDS_TABLE + ".[" +
                org.wheatgenetics.coordinate.database.GridsTable.PROJECTID_FIELD_NAME + ']';
        return java.lang.String.format(format, GRIDS_TABLE_ID_FIELD,
                ENTRIES_TABLE_EDATA_FIELD,
            GRIDS_TABLE, ENTRIES_TABLE, GRIDS_TABLE_ID_FIELD, ENTRIES_TABLE_GRID_FIELD,
            GRIDS_TABLE_ID_FIELD, ENTRIES_TABLE_EDATA_FIELD, GRIDS_TABLE_PROJECTID_FIELD,
                GRIDS_TABLE_PROJECTID_FIELD);
    }

    private boolean existsOutsideProject(final long id, final java.lang.String value)
    {
        final java.lang.String sql =
            org.wheatgenetics.coordinate.database.CurrentProjectUniqueGridsTable.format(
                "SELECT ALL %s, %s " +
                    "FROM %s INNER JOIN %s ON %s = %s " +
                    "WHERE %s <> ? AND %s = ? AND (%s IS NULL OR %s <= 0)");
        return org.wheatgenetics.coordinate.database.CurrentProjectUniqueGridsTable.exists(
            this.rawQuery(
                /* sql           => */ sql,
                /* selectionArgs => */
                    new java.lang.String[]{java.lang.String.valueOf(id), value}));
    }

    private boolean existsInsideProject(final long id, final java.lang.String value)
    {
        final java.lang.String sql =
            org.wheatgenetics.coordinate.database.CurrentProjectUniqueGridsTable.format(
                "SELECT ALL %s, %s " +
                    "FROM %s INNER JOIN %s ON %s = %s " +
                    "WHERE %s <> ? AND %s = ? AND (%s IS NOT NULL and %s >= 1)");
        return org.wheatgenetics.coordinate.database.CurrentProjectUniqueGridsTable.exists(
            this.rawQuery(
                /* sql           => */ sql,
                /* selectionArgs => */
                    new java.lang.String[]{java.lang.String.valueOf(id), value}));
    }
    // endregion

    public CurrentProjectUniqueGridsTable(final android.content.Context context)
    { super(context,"CurrentProjectUniqueGridsTable"); }

    // region Overridden Methods
    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    @java.lang.Override org.wheatgenetics.coordinate.database.EntriesTable makeEntriesTable()
    {
        return new org.wheatgenetics.coordinate.database.CurrentProjectUniqueEntriesTable(
            this.getContext(),this);
    }

    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    @java.lang.Override
    org.wheatgenetics.coordinate.model.BaseJoinedGridModels makeJoinedGridModels()
    { return new org.wheatgenetics.coordinate.model.CurrentProjectUniqueJoinedGridModels(); }

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
        return new org.wheatgenetics.coordinate.model.CurrentProjectUniqueJoinedGridModel(id,
            projectId, person, activeRow, activeCol, optionalFields, timestamp, templateId, title,
            code, rows, cols, generatedExcludedCellsAmount, initialExcludedCells, excludedRows,
            excludedCols, colNumbering, rowNumbering, entryLabel, templateOptionalFields,
            templateTimestamp,
            (org.wheatgenetics.coordinate.model.CurrentProjectUniqueEntryModels) entryModels,
            this);
    }

    // region org.wheatgenetics.coordinate.model.DatabaseUniqueEntryModels.Checker Overridden Method
    @java.lang.Override @android.support.annotation.Nullable public java.lang.String check(
                                         final long             gridId,
    @android.support.annotation.Nullable final java.lang.String value ,
    @android.support.annotation.NonNull  final java.lang.String scope ) throws
    org.wheatgenetics.coordinate.model.DatabaseUniqueEntryModels.DatabaseDuplicateCheckException
    {
        final org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel = this.get(gridId);
        if (null == joinedGridModel)
            throw new java.lang.NullPointerException();
        else
            if (org.wheatgenetics.coordinate.model.Model.illegal(joinedGridModel.getProjectId()))
                if (this.existsOutsideProject(gridId, value))
                    throw new org.wheatgenetics.coordinate.model
                        .DatabaseUniqueEntryModels.DatabaseDuplicateCheckException(scope);
                 else
                     return value;
            else
                if (this.existsInsideProject(gridId, value))
                    throw new org.wheatgenetics.coordinate.model
                        .DatabaseUniqueEntryModels.DatabaseDuplicateCheckException(scope);
                else
                    return value;
    }
    // endregion
    // endregion
}