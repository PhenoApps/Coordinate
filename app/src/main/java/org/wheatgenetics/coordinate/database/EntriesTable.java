package org.wheatgenetics.coordinate.database;

/**
 * Uses:
 * android.content.ContentValues
 * android.content.Context
 * android.database.Cursor
 *
 * org.wheatgenetics.coordinate.model.EntryModel
 * org.wheatgenetics.coordinate.model.Model
 *
 * org.wheatgenetics.coordinate.database.Table
 */
public class EntriesTable extends org.wheatgenetics.coordinate.database.Table
{
    // region Constants
    private static final java.lang.String TABLE_NAME      = "entries";
    private static final java.lang.String GRID_FIELD_NAME = "grid", ROW_FIELD_NAME = "row",
        COL_FIELD_NAME = "col", EDATA_FIELD_NAME = "edata", STAMP_FIELD_NAME = "stamp";
    // endregion

    public EntriesTable(final android.content.Context context)
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
        return null == cursor ? null : new org.wheatgenetics.coordinate.model.EntryModel(
            /* id => */ cursor.getLong(cursor.getColumnIndex(
                org.wheatgenetics.coordinate.database.Table.ID_FIELD_NAME)),
            /* gridId => */ cursor.getLong(cursor.getColumnIndex(
                org.wheatgenetics.coordinate.database.EntriesTable.GRID_FIELD_NAME)),
            /* row => */ cursor.getInt(cursor.getColumnIndex(
                org.wheatgenetics.coordinate.database.EntriesTable.ROW_FIELD_NAME)),
            /* col => */ cursor.getInt(cursor.getColumnIndex(
                org.wheatgenetics.coordinate.database.EntriesTable.COL_FIELD_NAME)),
            /* value => */ cursor.getString(cursor.getColumnIndex(
                org.wheatgenetics.coordinate.database.EntriesTable.EDATA_FIELD_NAME)),
            /* timestamp => */ cursor.getLong(cursor.getColumnIndex(
                org.wheatgenetics.coordinate.database.EntriesTable.STAMP_FIELD_NAME)));
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
            result.put(org.wheatgenetics.coordinate.database.EntriesTable.EDATA_FIELD_NAME,
                entryModel.getValue());
            result.put(org.wheatgenetics.coordinate.database.EntriesTable.STAMP_FIELD_NAME,
                entryModel.getTimestamp());
        }
        return result;
    }
    // endregion

    // region Operations
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

    public boolean deleteByGrid(final long grid)
    {
        return this.deleteUsingWhereClause(/* whereClause => */
            org.wheatgenetics.coordinate.database.EntriesTable.GRID_FIELD_NAME + " = " + grid);
    }
    // endregion
}