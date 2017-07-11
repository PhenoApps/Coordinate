package org.wheatgenetics.coordinate.database;

/**
 * Uses:
 * android.annotation.SuppressLint
 * android.content.ContentValues
 * android.content.Context
 * android.database.Cursor
 *
 * org.json.JSONException
 *
 * org.wheatgenetics.coordinate.database.Table
 * org.wheatgenetics.coordinate.model.EntryModel
 * org.wheatgenetics.coordinate.model.Model
 */

public class EntriesTable extends org.wheatgenetics.coordinate.database.Table
{
    // region Fields
    public  long             grid  =  0         ;
    public  int              col   =  0, row = 0;
    public  java.lang.String value = ""         ;
    private long             stamp =  0         ;
    // endregion

    public EntriesTable(final android.content.Context context)
    {
        super(
            /* context   => */ context                                                      ,
            /* tableName => */ org.wheatgenetics.coordinate.database.EntriesTable.TABLE_NAME,
            /* tag       => */ "EntriesTable"                                               );
    }

    @java.lang.Override @android.annotation.SuppressLint("DefaultLocale")
    public java.lang.String toString()
    {
        return super.toString() + java.lang.String.format(
            " entry: %02d col: %02d row: %02d value: %.2f",
            this.grid, this.col, this.row, this.value);
    }

    // region Storage
    // region Private Constants
    private static final java.lang.String TABLE_NAME = "entries";
    private static final java.lang.String
        GRID_FIELD_NAME  = "grid" , COL_FIELD_NAME   = "col"  , ROW_FIELD_NAME = "row",
        EDATA_FIELD_NAME = "edata", STAMP_FIELD_NAME = "stamp";
    // endregion

    @java.lang.Override
    org.wheatgenetics.coordinate.model.EntryModel make(final android.database.Cursor cursor)
    {
        if (null == cursor)
            return null;
        else
            return new org.wheatgenetics.coordinate.model.EntryModel(
                /* id => */ cursor.getInt(cursor.getColumnIndex(
                    org.wheatgenetics.coordinate.database.Table.ID_FIELD_NAME)),
                /* gridId => */ cursor.getInt(cursor.getColumnIndex(
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
    android.content.ContentValues getContentValues()
    {
        final android.content.ContentValues contentValues = super.getContentValues();

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

    @java.lang.Override
    android.content.ContentValues getContentValues(
    final org.wheatgenetics.coordinate.model.Model model) throws org.json.JSONException
    {
        final android.content.ContentValues contentValues = super.getContentValues(model);

        final org.wheatgenetics.coordinate.model.EntryModel entryModel =
            (org.wheatgenetics.coordinate.model.EntryModel) model;

        assert null != entryModel;
        contentValues.put(org.wheatgenetics.coordinate.database.EntriesTable.GRID_FIELD_NAME,
            entryModel.getGridId());
        contentValues.put(org.wheatgenetics.coordinate.database.EntriesTable.COL_FIELD_NAME,
            entryModel.getCol());
        contentValues.put(org.wheatgenetics.coordinate.database.EntriesTable.ROW_FIELD_NAME,
            entryModel.getRow());
        contentValues.put(org.wheatgenetics.coordinate.database.EntriesTable.EDATA_FIELD_NAME,
            entryModel.getValue());
        contentValues.put(org.wheatgenetics.coordinate.database.EntriesTable.STAMP_FIELD_NAME,
            entryModel.getTimestamp());

        return contentValues;
    }

    private void copy(final android.database.Cursor cursor)
    {
        assert null != cursor;
        this.id = cursor.getInt(cursor.getColumnIndex(
            org.wheatgenetics.coordinate.database.Table.ID_FIELD_NAME          ));
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
            org.wheatgenetics.coordinate.database.Table.ID_FIELD_NAME + "=" + id);
        if (null == cursor)
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

    // region Operations
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
        return this.selectionQueryAll(/* selection => */
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
        if (null == cursor)                                                             // TODO: DRY
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
            org.wheatgenetics.coordinate.database.Table.ID_FIELD_NAME + "=" + id);
    }

    public boolean deleteByGrid(final long grid)
    {
        this.sendInfoLogMsg("Deleting from table " +
            org.wheatgenetics.coordinate.database.EntriesTable.TABLE_NAME + " on id = " + grid);
        return this.deleteUsingWhereClause(/* whereClause => */
            org.wheatgenetics.coordinate.database.EntriesTable.GRID_FIELD_NAME + "=" + grid);
    }
    // endregion
    // endregion
}