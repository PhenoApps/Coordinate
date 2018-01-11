package org.wheatgenetics.coordinate.database;

/**
 * Uses:
 * android.content.ContentValues
 * android.content.Context
 * android.database.Cursor
 *
 * org.wheatgenetics.javalib.Utils
 *
 * org.wheatgenetics.coordinate.model.EntryModel
 * org.wheatgenetics.coordinate.model.EntryModels
 * org.wheatgenetics.coordinate.model.ExcludedEntryModel
 * org.wheatgenetics.coordinate.model.IncludedEntryModel
 * org.wheatgenetics.coordinate.model.Model
 *
 * org.wheatgenetics.coordinate.database.Table
 */
class EntriesTable extends org.wheatgenetics.coordinate.database.Table
{
    // region Constants
    private static final java.lang.String TABLE_NAME      = "entries";
    private static final java.lang.String GRID_FIELD_NAME = "grid", ROW_FIELD_NAME = "row",
        COL_FIELD_NAME = "col", EDATA_FIELD_NAME = "edata", STAMP_FIELD_NAME = "stamp";
    private static final java.lang.String EXCLUDED_VALUE = "excluded";
    // endregion

    EntriesTable(final android.content.Context context)
    {
        super(
            /* context   => */ context                                                      ,
            /* tableName => */ org.wheatgenetics.coordinate.database.EntriesTable.TABLE_NAME,
            /* tag       => */ "EntriesTable"                                               );
    }

    // region Overridden Methods
    @java.lang.Override
    org.wheatgenetics.coordinate.model.Model make(final android.database.Cursor cursor)
    {
        if (null == cursor)
            return null;
        else
        {
            final long
                id = cursor.getLong(cursor.getColumnIndex(
                    org.wheatgenetics.coordinate.database.Table.ID_FIELD_NAME)),
                gridId = cursor.getLong(cursor.getColumnIndex(
                    org.wheatgenetics.coordinate.database.EntriesTable.GRID_FIELD_NAME));
            final int
                row = cursor.getInt(cursor.getColumnIndex(
                    org.wheatgenetics.coordinate.database.EntriesTable.ROW_FIELD_NAME)),
                col = cursor.getInt(cursor.getColumnIndex(
                    org.wheatgenetics.coordinate.database.EntriesTable.COL_FIELD_NAME));
            final java.lang.String value = cursor.getString(cursor.getColumnIndex(
                org.wheatgenetics.coordinate.database.EntriesTable.EDATA_FIELD_NAME));
            final long timestamp = cursor.getLong(cursor.getColumnIndex(
                org.wheatgenetics.coordinate.database.EntriesTable.STAMP_FIELD_NAME));
            if (null != value)
                if (value.equals(org.wheatgenetics.coordinate.database.EntriesTable.EXCLUDED_VALUE))
                    return new org.wheatgenetics.coordinate.model.ExcludedEntryModel(
                        /* id        => */ id        ,
                        /* gridId    => */ gridId    ,
                        /* row       => */ row       ,
                        /* col       => */ col       ,
                        /* timestamp => */ timestamp);
            return new org.wheatgenetics.coordinate.model.IncludedEntryModel(
                /* id        => */ id        ,
                /* gridId    => */ gridId    ,
                /* row       => */ row       ,
                /* col       => */ col       ,
                /* value     => */ value     ,
                /* timestamp => */ timestamp);
        }
    }

    @java.lang.Override
    android.content.ContentValues getContentValuesForInsert(
    final org.wheatgenetics.coordinate.model.Model model)
    {
        final android.content.ContentValues result = super.getContentValuesForInsert(model);
        {
            final org.wheatgenetics.coordinate.model.EntryModel entryModel =
                (org.wheatgenetics.coordinate.model.EntryModel) model;

            assert null != entryModel;
            result.put(org.wheatgenetics.coordinate.database.EntriesTable.GRID_FIELD_NAME,
                entryModel.getGridId());
            result.put(org.wheatgenetics.coordinate.database.EntriesTable.ROW_FIELD_NAME,
                entryModel.getRow());
            result.put(org.wheatgenetics.coordinate.database.EntriesTable.COL_FIELD_NAME,
                entryModel.getCol());

            if (model instanceof org.wheatgenetics.coordinate.model.ExcludedEntryModel)
                result.put(org.wheatgenetics.coordinate.database.EntriesTable.EDATA_FIELD_NAME,
                    org.wheatgenetics.coordinate.database.EntriesTable.EXCLUDED_VALUE);
            else
            {
                final org.wheatgenetics.coordinate.model.IncludedEntryModel includedEntryModel =
                    (org.wheatgenetics.coordinate.model.IncludedEntryModel) model;
                result.put(org.wheatgenetics.coordinate.database.EntriesTable.EDATA_FIELD_NAME,
                    includedEntryModel.getValue());
            }

            result.put(org.wheatgenetics.coordinate.database.EntriesTable.STAMP_FIELD_NAME,
                entryModel.getTimestamp());
        }
        return result;
    }
    // endregion

    // region Operations
    org.wheatgenetics.coordinate.model.EntryModels load(
    final long gridId, final int rows, final int cols)
    {
        final org.wheatgenetics.coordinate.model.EntryModels result;
        {
            final android.database.Cursor cursor = this.queryAll(
                /* selection => */
                    org.wheatgenetics.coordinate.database.EntriesTable.GRID_FIELD_NAME + " = ?",
                /* selectionArgs => */ org.wheatgenetics.javalib.Utils.stringArray(gridId),
                /* orderBy       => */
                    org.wheatgenetics.coordinate.database.EntriesTable.ROW_FIELD_NAME + " ASC, " +
                    org.wheatgenetics.coordinate.database.EntriesTable.COL_FIELD_NAME + " ASC"  );
            if (null == cursor)
                result = null;
            else
                try
                {
                    if (cursor.getCount() <= 0)
                        result = null;
                    else
                    {
                        result =
                            new org.wheatgenetics.coordinate.model.EntryModels(gridId, rows, cols);
                        while (cursor.moveToNext()) result.add(
                            (org.wheatgenetics.coordinate.model.EntryModel) this.make(cursor));
                    }
                }
                finally { cursor.close(); }
        }
        return result;
    }

    // region Public Operations
    public org.wheatgenetics.coordinate.model.EntryModel get(final long grid, final int row,
    final int col)
    {
        final java.lang.String selection =
            org.wheatgenetics.coordinate.database.EntriesTable.GRID_FIELD_NAME + " = ? AND " +
            org.wheatgenetics.coordinate.database.EntriesTable.COL_FIELD_NAME  + " = ? AND " +
            org.wheatgenetics.coordinate.database.EntriesTable.ROW_FIELD_NAME  + " = ?"      ;
        final java.lang.String[] selectionArgs = new java.lang.String[] {
            java.lang.String.valueOf(grid),
            java.lang.String.valueOf(col ),
            java.lang.String.valueOf(row )};
        return (org.wheatgenetics.coordinate.model.EntryModel) this.makeFromFirst(
            this.queryDistinct(/* selection => */ selection, /* selectionArgs => */ selectionArgs));
    }

    public boolean deleteByGridId(final long gridId)
    {
        return this.deleteUsingWhereClause(/* whereClause => */
            org.wheatgenetics.coordinate.database.EntriesTable.GRID_FIELD_NAME + " = " + gridId);
    }
    // endregion
    // endregion
}