package org.wheatgenetics.coordinate.database;

/**
 * Uses:
 * android.content.ContentValues
 * android.content.Context
 * android.database.Cursor
 *
 * org.wheatgenetics.coordinate.database.Table
 */

public class EntriesTable extends org.wheatgenetics.coordinate.database.Table
{
    public  long             id , grid;
    public  int              col, row ;
    public  java.lang.String value    ;
    private long             stamp    ;


    public EntriesTable(final android.content.Context context)
    {
        super(
            /* context   => */ context                                                      ,
            /* tableName => */ org.wheatgenetics.coordinate.database.EntriesTable.TABLE_NAME,
            /* tag       => */ "EntriesTable"                                               );

        this.id   = 0;
        this.grid = 0;

        this.col = 0;
        this.row = 0;

        this.value = "";

        this.stamp = 0;
    }

    @Override
    public java.lang.String toString()
    {
        return java.lang.String.format("id: %02d entry: %02d col: %02d row: %02d value: %.2f",
            this.id, this.grid, this.col, this.row, this.value);
    }


    // region Storage
    // region Private Constants
    private static final java.lang.String TABLE_NAME = "entries";
    private static final java.lang.String
        ID_FIELD_NAME  = "_id", GRID_FIELD_NAME  = "grid" , COL_FIELD_NAME   = "col"  ,
        ROW_FIELD_NAME = "row", EDATA_FIELD_NAME = "edata", STAMP_FIELD_NAME = "stamp";
    // endregion


    @Override
    android.content.ContentValues getContentValues()
    {
        final android.content.ContentValues contentValues = new android.content.ContentValues();

        contentValues.put(
            org.wheatgenetics.coordinate.database.EntriesTable.GRID_FIELD_NAME , this.grid );
        contentValues.put(
            org.wheatgenetics.coordinate.database.EntriesTable.COL_FIELD_NAME  , this.col  );
        contentValues.put(
            org.wheatgenetics.coordinate.database.EntriesTable.ROW_FIELD_NAME  , this.row  );
        contentValues.put(
            org.wheatgenetics.coordinate.database.EntriesTable.EDATA_FIELD_NAME, this.value);
        contentValues.put(
            org.wheatgenetics.coordinate.database.EntriesTable.STAMP_FIELD_NAME, this.stamp);

        return contentValues;
    }

    private void copy(final android.database.Cursor cursor)
    {
        assert cursor != null;
        this.id = cursor.getInt(cursor.getColumnIndex(
            org.wheatgenetics.coordinate.database.EntriesTable.ID_FIELD_NAME   ));
        this.grid = cursor.getInt(cursor.getColumnIndex(
            org.wheatgenetics.coordinate.database.EntriesTable.GRID_FIELD_NAME ));

        this.col = cursor.getInt(cursor.getColumnIndex(
            org.wheatgenetics.coordinate.database.EntriesTable.COL_FIELD_NAME  ));
        this.row = cursor.getInt(cursor.getColumnIndex(
            org.wheatgenetics.coordinate.database.EntriesTable.ROW_FIELD_NAME  ));

        this.value = cursor.getString(cursor.getColumnIndex(
            org.wheatgenetics.coordinate.database.EntriesTable.EDATA_FIELD_NAME));

        this.stamp = cursor.getLong(cursor.getColumnIndex(
            org.wheatgenetics.coordinate.database.EntriesTable.STAMP_FIELD_NAME));
    }

    public boolean get(final long id)                                         // TODO: Remove later.
    {
        final android.database.Cursor cursor = this.queryDistinct(
            org.wheatgenetics.coordinate.database.EntriesTable.ID_FIELD_NAME + "=" + id);
        if (cursor == null)
            return false;
        else
            try
            {
                if (cursor.moveToFirst())
                {
                    this.copy(cursor);
                    return true;
                }
                else return false;
            }
            finally { cursor.close(); }
    }

    public android.database.Cursor load()                                     // TODO: Remove later.
    {
        this.sendInfoLogMsg(
            "Loading table " + org.wheatgenetics.coordinate.database.EntriesTable.TABLE_NAME);
        return this.queryAll();
    }

    public android.database.Cursor loadByEntry(final int entry)               // TODO: Remove later.
    {
        this.sendInfoLogMsg("Loading table " +
            org.wheatgenetics.coordinate.database.EntriesTable.TABLE_NAME + " by entry = " + entry);
        return this.queryAllSelection(/* selection => */
            org.wheatgenetics.coordinate.database.EntriesTable.GRID_FIELD_NAME + " = " + entry);
    }

    public boolean getByGrid(final long grid, final int row, final int col)
    {
        android.database.Cursor cursor;
        {
            final java.lang.String selection =
                org.wheatgenetics.coordinate.database.EntriesTable.GRID_FIELD_NAME + "= ? AND " +
                org.wheatgenetics.coordinate.database.EntriesTable.COL_FIELD_NAME  + "= ? AND " +
                org.wheatgenetics.coordinate.database.EntriesTable.ROW_FIELD_NAME  + "= ?"      ;
            final java.lang.String[] selectionArgs = new java.lang.String[]{
                java.lang.String.valueOf(grid),
                java.lang.String.valueOf(col ),
                java.lang.String.valueOf(row )};
            cursor = this.queryDistinct(
                /* selection => */ selection, /* selectionArgs => */ selectionArgs);
        }
        if (cursor == null)                                                             // TODO: DRY
            return false;
        else
            try
            {
                if (cursor.moveToFirst())
                {
                    this.copy(cursor);
                    return true;
                }
                else return false;
            }
            finally { cursor.close(); }
    }

    public boolean update()
    {
        this.sendInfoLogMsg("Updating table " +
            org.wheatgenetics.coordinate.database.EntriesTable.TABLE_NAME + " on id = " + id);
        return this.update(/* whereClause   => */
            org.wheatgenetics.coordinate.database.EntriesTable.ID_FIELD_NAME + "=" + id);
    }

    public boolean delete(final long id)                                      // TODO: Remove later.
    {
        this.sendInfoLogMsg("Deleting from table " +
            org.wheatgenetics.coordinate.database.EntriesTable.TABLE_NAME + " on id = " + id);
        return this.delete(/* whereClause => */
            org.wheatgenetics.coordinate.database.EntriesTable.ID_FIELD_NAME + "=" + id);
    }

    public boolean deleteByGrid(final long grid)
    {
        this.sendInfoLogMsg("Deleting from table " +
            org.wheatgenetics.coordinate.database.EntriesTable.TABLE_NAME + " on id = " + grid);
        return this.delete(/* whereClause => */
            org.wheatgenetics.coordinate.database.EntriesTable.GRID_FIELD_NAME + "=" + grid);
    }
    // endregion
}