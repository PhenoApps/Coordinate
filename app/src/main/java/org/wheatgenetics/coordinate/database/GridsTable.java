package org.wheatgenetics.coordinate.database;

/**
 * Uses:
 * android.content.ContentValues
 * android.content.Context
 * android.database.Cursor
 *
 * org.wheatgenetics.coordinate.database.Table
 */

public class GridsTable extends org.wheatgenetics.coordinate.database.Table
{
    public long             id, template, templateId;
    public java.lang.String templateTitle           ;
    public int              templateType, rows, cols;
    public java.lang.String title                   ;
    public long             stamp                   ;

    public GridsTable(final android.content.Context context)
    {
        super(
            /* context   => */ context                                                    ,
            /* tableName => */ org.wheatgenetics.coordinate.database.GridsTable.TABLE_NAME,
            /* tag       => */ "GridsTable"                                               );

        this.id       = 0;
        this.template = 0;

        this.templateTitle = "";

        this.rows = 0;
        this.cols = 0;

        this.title = "";

        this.stamp = 0;
    }

    @Override
    public java.lang.String toString()
    {
        return java.lang.String.format(
            "id: %02d template: %02d stamp: %02d", this.id, this.template, this.stamp);
    }


    // region Storage
    // region Private Constants
    private static final java.lang.String TABLE_NAME = "grids";
    private static final java.lang.String
        ID_FIELD_NAME    = "_id"  , TEMPLATE_FIELD_NAME = "temp" ,
        TITLE_FIELD_NAME = "title", STAMP_FIELD_NAME    = "stamp";
    // endregion


    @Override
    android.content.ContentValues getContentValues()
    {
        final android.content.ContentValues contentValues = new android.content.ContentValues();

        contentValues.put(
            org.wheatgenetics.coordinate.database.GridsTable.TEMPLATE_FIELD_NAME, this.template);
        contentValues.put(
            org.wheatgenetics.coordinate.database.GridsTable.TITLE_FIELD_NAME   , this.title   );
        contentValues.put(
            org.wheatgenetics.coordinate.database.GridsTable.STAMP_FIELD_NAME   , this.stamp   );

        return contentValues;
    }

    public void copy(final android.database.Cursor cursor)
    {
        assert cursor != null;
        this.id = cursor.getInt(cursor.getColumnIndex(
            org.wheatgenetics.coordinate.database.GridsTable.ID_FIELD_NAME));
        this.template = cursor.getInt(cursor.getColumnIndex(
            org.wheatgenetics.coordinate.database.GridsTable.TEMPLATE_FIELD_NAME));
        this.title = cursor.getString(cursor.getColumnIndex(
            org.wheatgenetics.coordinate.database.GridsTable.TITLE_FIELD_NAME));
        this.stamp = cursor.getLong(cursor.getColumnIndex(
            org.wheatgenetics.coordinate.database.GridsTable.STAMP_FIELD_NAME));
    }

    public boolean copyAll(final android.database.Cursor cursor)     // TODO: Change name to copy().
    {
        if (cursor ==  null)
            return false;
        else
        {
            this.id = cursor.getInt(cursor.getColumnIndex(org.wheatgenetics.coordinate.database.GridsTable.ID_FIELD_NAME));
            this.title = cursor.getString(cursor.getColumnIndex("gridTitle"));

            this.templateType = cursor.getInt(cursor.getColumnIndex("templateType"));
            this.templateTitle = cursor.getString(cursor.getColumnIndex("templateTitle"));
            this.templateId = cursor.getLong(cursor.getColumnIndex("templateId"));
            this.cols = cursor.getInt(cursor.getColumnIndex("cols"));
            this.rows = cursor.getInt(cursor.getColumnIndex("rows"));

            this.stamp = cursor.getLong(cursor.getColumnIndex(org.wheatgenetics.coordinate.database.GridsTable.STAMP_FIELD_NAME));

            return true;
        }
    }

    public boolean get(final long id)
    {
        final android.database.Cursor cursor = this.rawQuery(
            "SELECT grids._id, grids.title as gridTitle, grids.stamp, templates.type as templateType, " +
                "templates.title as templateTitle, templates._id as templateId, templates.rows, templates.cols from grids, templates where templates._id = grids.temp and grids." +
                org.wheatgenetics.coordinate.database.GridsTable.ID_FIELD_NAME + "=" + id);
        if (cursor == null)
             return false;
        else
            try
            {
                if (cursor.moveToFirst())
                {
                    this.copyAll(cursor);
                    return true;
                }
                else return false;
            }
            finally { cursor.close(); }
    }

    public android.database.Cursor load()                                     // TODO: Remove later.
    {
        this.sendInfoLogMsg(
            "Loading table " + org.wheatgenetics.coordinate.database.GridsTable.TABLE_NAME);
        return this.queryAll();
    }

    public android.database.Cursor loadByTemplate(final long tmp)
    {
        this.sendInfoLogMsg("Loading table " +
            org.wheatgenetics.coordinate.database.GridsTable.TABLE_NAME + " by entry = " + tmp);
        return this.queryAllSelection(/* selection => */
            org.wheatgenetics.coordinate.database.GridsTable.TEMPLATE_FIELD_NAME + " = " + tmp);
    }

    public android.database.Cursor getAllGrids()
    {
        this.sendInfoLogMsg(
            "Loading table " + org.wheatgenetics.coordinate.database.GridsTable.TABLE_NAME);
        return this.rawQuery(
            "SELECT grids._id, grids.title as gridTitle, grids.stamp, templates.type as templateType, templates.title as templateTitle, templates._id as templateId, templates.rows, templates.cols from grids, templates where templates._id = grids.temp");
    }

    public boolean getByTemplate(final long entry)                            // TODO: Remove later.
    {
        android.database.Cursor cursor;
        {
            final java.lang.String selection = org.wheatgenetics.coordinate.database.GridsTable.TEMPLATE_FIELD_NAME + "= ?";
            final java.lang.String[] selectionArgs = new java.lang.String[]{java.lang.String.valueOf(entry)};
            cursor = this.queryDistinct(/* selection => */ selection, /* selectionArgs => */ selectionArgs);
        }
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

    public boolean update()                                                   // TODO: Remove later.
    {
        this.sendInfoLogMsg("Updating table " +
            org.wheatgenetics.coordinate.database.GridsTable.TABLE_NAME + " on id = " + id);
        return this.update(/* whereClause   => */
            org.wheatgenetics.coordinate.database.GridsTable.ID_FIELD_NAME + "=" + id);
    }

    public boolean delete(final long id)
    {
        this.sendInfoLogMsg("Deleting from table " +
            org.wheatgenetics.coordinate.database.GridsTable.TABLE_NAME + " on id = " + id);
        return this.delete(/* whereClause => */
            org.wheatgenetics.coordinate.database.GridsTable.ID_FIELD_NAME + "=" + id);
    }

    public boolean deleteByTemplate(long entryId)                             // TODO: Remove later.
    {
        this.sendInfoLogMsg("Deleting from table " +
            org.wheatgenetics.coordinate.database.GridsTable.TABLE_NAME + " on id = " + entryId);
        return this.delete(/* whereClause => */
            org.wheatgenetics.coordinate.database.GridsTable.TEMPLATE_FIELD_NAME + "=" + entryId);
    }
    // endregion
}