package org.wheatgenetics.coordinate.database;

/**
 * Uses:
 * android.content.Context
 * android.util.Log
 *
 * androidx.annotation.IntRange
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 * androidx.annotation.RestrictTo
 * androidx.annotation.RestrictTo.Scope
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
    // region Constants
    private static final java.lang.String GRIDS_TABLE =
        '[' + org.wheatgenetics.coordinate.database.CurrentProjectUniqueGridsTable.TABLE_NAME + ']';
    private static final java.lang.String GRIDS_TABLE_PROJECTID_FIELD =
        org.wheatgenetics.coordinate.database.CurrentProjectUniqueGridsTable.GRIDS_TABLE +
        ".[" + org.wheatgenetics.coordinate.database.GridsTable.PROJECTID_FIELD_NAME + ']';
    // endregion

    // region Private Methods
    private static void log(final java.lang.String msg)
    {
        android.util.Log.d(
            org.wheatgenetics.coordinate.database.CurrentProjectUniqueGridsTable.class.getName(),
            msg                                                                                 );
    }

    private static java.lang.String firstPartOfSql()
    {
        final java.lang.String format =
            "SELECT ALL %s, %s "                    +
                "FROM %s INNER JOIN %s ON %s = %s " +
                "WHERE %s <> ? AND %s = ?"          ;
        final java.lang.String
            ENTRIES_TABLE = '[' +
                org.wheatgenetics.coordinate.database.EntriesTable.TABLE_NAME + ']';
        final java.lang.String
            GRIDS_TABLE_ID_FIELD =
                org.wheatgenetics.coordinate.database.CurrentProjectUniqueGridsTable.GRIDS_TABLE +
                ".[" +
                org.wheatgenetics.coordinate.database.CurrentProjectUniqueGridsTable.ID_FIELD_NAME +
                ']',
            ENTRIES_TABLE_EDATA_FIELD = ENTRIES_TABLE + ".[" +
                org.wheatgenetics.coordinate.database.EntriesTable.EDATA_FIELD_NAME + ']',
            ENTRIES_TABLE_GRID_FIELD = ENTRIES_TABLE + ".[" +
                org.wheatgenetics.coordinate.database.EntriesTable.GRID_FIELD_NAME + ']';
        return java.lang.String.format(format, GRIDS_TABLE_ID_FIELD, ENTRIES_TABLE_EDATA_FIELD,
            org.wheatgenetics.coordinate.database.CurrentProjectUniqueGridsTable.GRIDS_TABLE,
            ENTRIES_TABLE, GRIDS_TABLE_ID_FIELD, ENTRIES_TABLE_GRID_FIELD, GRIDS_TABLE_ID_FIELD,
            ENTRIES_TABLE_EDATA_FIELD);
    }

    private boolean existsOutsideProject(final long id, final java.lang.String value)
    {
        final java.lang.String sql;
        {
            final java.lang.String GRIDS_TABLE_PROJECTID_FIELD = org.wheatgenetics.coordinate
                .database.CurrentProjectUniqueGridsTable.GRIDS_TABLE_PROJECTID_FIELD;
            sql = org.wheatgenetics.coordinate.database.CurrentProjectUniqueGridsTable
                .firstPartOfSql() + java.lang.String.format(" AND (%s IS NULL OR %s <= 0)",
                    GRIDS_TABLE_PROJECTID_FIELD, GRIDS_TABLE_PROJECTID_FIELD);
        }
        return org.wheatgenetics.coordinate.database.CurrentProjectUniqueGridsTable.exists(
            this.rawQuery(
                /* sql           => */ sql,
                /* selectionArgs => */
                    new java.lang.String[]{java.lang.String.valueOf(id), value}));
    }

    private boolean existsInsideProject(final long id,
    final java.lang.String value, final long projectId)
    {
        final android.database.Cursor cursor;
        {
            final java.lang.String sql;
            {
                final java.lang.String GRIDS_TABLE_PROJECTID_FIELD = org.wheatgenetics.coordinate
                    .database.CurrentProjectUniqueGridsTable.GRIDS_TABLE_PROJECTID_FIELD;
                sql = org.wheatgenetics.coordinate.database.CurrentProjectUniqueGridsTable
                    .firstPartOfSql() + java.lang.String.format(" AND (%s IS NOT NULL AND %s = ?)",
                        GRIDS_TABLE_PROJECTID_FIELD, GRIDS_TABLE_PROJECTID_FIELD);
            }
            cursor = this.rawQuery(
                /* sql           => */ sql,
                /* selectionArgs => */ new java.lang.String[]{
                    java.lang.String.valueOf(id), value, java.lang.String.valueOf(projectId)});
        }
        if (null != cursor) if (cursor.moveToFirst())
        {
            org.wheatgenetics.coordinate.database.CurrentProjectUniqueGridsTable.log(
                "gridId == " + id + ", value == " + value + ", projectId == " + projectId);
            do
            {
                org.wheatgenetics.coordinate.database.CurrentProjectUniqueGridsTable.log(
                    "[grids].[_id] == "   + cursor.getString(0) +
                    ", [entries].[edata] == "   + cursor.getString(1) +
                    ", [grids].[projectId] == " + cursor.getString(2));
            }
            while (cursor.moveToNext());
        }
        return org.wheatgenetics.coordinate.database.CurrentProjectUniqueGridsTable.exists(cursor);
    }
    // endregion

    public CurrentProjectUniqueGridsTable(final android.content.Context context)
    { super(context,"CurrentProjectUniqueGridsTable"); }

    // region Overridden Methods
    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @java.lang.Override org.wheatgenetics.coordinate.database.EntriesTable makeEntriesTable()
    {
        return new org.wheatgenetics.coordinate.database.CurrentProjectUniqueEntriesTable(
            this.getContext(),this);
    }

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @java.lang.Override
    org.wheatgenetics.coordinate.model.BaseJoinedGridModels makeJoinedGridModels()
    { return new org.wheatgenetics.coordinate.model.CurrentProjectUniqueJoinedGridModels(); }

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
    @androidx.annotation.IntRange(from = 0        ) final long             templateTimestamp     ,

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
    @java.lang.Override @androidx.annotation.Nullable public java.lang.String check(
                                  final long             gridId,
    @androidx.annotation.Nullable final java.lang.String value ,
    @androidx.annotation.NonNull  final java.lang.String scope ) throws
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
                if (this.existsInsideProject(gridId, value, joinedGridModel.getProjectId()))
                    throw new org.wheatgenetics.coordinate.model
                        .DatabaseUniqueEntryModels.DatabaseDuplicateCheckException(scope);
                else
                    return value;
    }
    // endregion
    // endregion
}