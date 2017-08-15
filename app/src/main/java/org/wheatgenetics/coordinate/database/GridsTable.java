package org.wheatgenetics.coordinate.database;

/**
 * Uses:
 * android.annotation.SuppressLint
 * android.content.ContentValues
 * android.content.Context
 * android.database.Cursor
 *
 *  org.json.JSONException
 *
 * org.wheatgenetics.coordinate.database.Table
 * org.wheatgenetics.coordinate.model.GridModel
 * org.wheatgenetics.coordinate.model.Model
 */
public class GridsTable extends org.wheatgenetics.coordinate.database.Table
{
    // region Fields
    // region grids Table
    public long             templateId =  0;
    public java.lang.String title      = "";
    public long             timestamp  =  0;
    // endregion

    // region templates Table
    public java.lang.String templateTitle = ""                              ;
    public int              templateType, templateRows = 0, templateCols = 0;
    // endregion
    // endregion

    public GridsTable(final android.content.Context context)
    {
        super(
            /* context   => */ context                                                    ,
            /* tableName => */ org.wheatgenetics.coordinate.database.GridsTable.TABLE_NAME,
            /* tag       => */ "GridsTable"                                               );
    }

    @java.lang.Override @android.annotation.SuppressLint("DefaultLocale")
    public java.lang.String toString()
    {
        return super.toString() + java.lang.String.format(
            " templateId: %02d timestamp: %02d", this.templateId, this.timestamp);
    }

    // region Storage
    // region Constants
    private static final java.lang.String TABLE_NAME      = "grids";
    private static final java.lang.String TEMP_FIELD_NAME = "temp",
        TITLE_FIELD_NAME = "title", STAMP_FIELD_NAME = "stamp";
    // endregion

    @java.lang.Override
    org.wheatgenetics.coordinate.model.Model make(final android.database.Cursor cursor)
    {
        return null == cursor ? null : new org.wheatgenetics.coordinate.model.GridModel(
            /* id => */ cursor.getInt(cursor.getColumnIndex(        // TODO: Why getInt() not getLong()? Others?
                org.wheatgenetics.coordinate.database.Table.ID_FIELD_NAME)),
            /* title => */ cursor.getString(cursor.getColumnIndex(
                org.wheatgenetics.coordinate.database.GridsTable.TITLE_FIELD_NAME)),
            /* timestamp => */ cursor.getLong(cursor.getColumnIndex(
                org.wheatgenetics.coordinate.database.GridsTable.STAMP_FIELD_NAME)),
            /* templateId => */ cursor.getInt(cursor.getColumnIndex(
                org.wheatgenetics.coordinate.database.GridsTable.TEMP_FIELD_NAME)),
            /* templateTitle => */ cursor.getString(cursor.getColumnIndex("templateTitle")),
            /* templateType  => */ cursor.getInt   (cursor.getColumnIndex("templateType" )),
            /* templateRows  => */ cursor.getInt   (cursor.getColumnIndex("rows"         )),
            /* templateCols  => */ cursor.getInt   (cursor.getColumnIndex("cols"         )));
    }

    @java.lang.Override
    android.content.ContentValues getContentValues()
    {
        final android.content.ContentValues contentValues = super.getContentValues();

        contentValues.put(
            org.wheatgenetics.coordinate.database.GridsTable.TEMP_FIELD_NAME , this.templateId);
        contentValues.put(
            org.wheatgenetics.coordinate.database.GridsTable.TITLE_FIELD_NAME, this.title     );
        contentValues.put(
            org.wheatgenetics.coordinate.database.GridsTable.STAMP_FIELD_NAME, this.timestamp );

        return contentValues;
    }

    @java.lang.Override
    android.content.ContentValues getContentValues(  // TODO: U will need another getContentValues() to replace copyAll().
    final org.wheatgenetics.coordinate.model.Model model) throws org.json.JSONException
    {
        final android.content.ContentValues contentValues = super.getContentValues(model);

        final org.wheatgenetics.coordinate.model.GridModel gridModel =
            (org.wheatgenetics.coordinate.model.GridModel) model;

        assert null != gridModel;
        contentValues.put(org.wheatgenetics.coordinate.database.GridsTable.TEMP_FIELD_NAME,
            gridModel.getId());
        contentValues.put(org.wheatgenetics.coordinate.database.GridsTable.TITLE_FIELD_NAME,
            gridModel.getTitle());
        contentValues.put(org.wheatgenetics.coordinate.database.GridsTable.STAMP_FIELD_NAME,
            gridModel.getTimestamp());

        return contentValues;
    }

    public boolean copy(final android.database.Cursor cursor)
    {
        if (null == cursor)
            return false;
        else
        {
            this.id = cursor.getInt(cursor.getColumnIndex(
                org.wheatgenetics.coordinate.database.Table.ID_FIELD_NAME));
            this.templateId = cursor.getInt(cursor.getColumnIndex(
                org.wheatgenetics.coordinate.database.GridsTable.TEMP_FIELD_NAME));
            this.title = cursor.getString(cursor.getColumnIndex(
                org.wheatgenetics.coordinate.database.GridsTable.TITLE_FIELD_NAME));
            this.timestamp = cursor.getLong(cursor.getColumnIndex(
                org.wheatgenetics.coordinate.database.GridsTable.STAMP_FIELD_NAME));

            return true;
        }
    }

    public boolean copyAll(final android.database.Cursor cursor)
    {
        if (null == cursor)
            return false;
        else
        {
            this.id = cursor.getInt(cursor.getColumnIndex(org.wheatgenetics.coordinate.database.Table.ID_FIELD_NAME));
            this.title = cursor.getString(cursor.getColumnIndex("gridTitle"));

            this.templateType = cursor.getInt(cursor.getColumnIndex("templateType"));
            this.templateTitle = cursor.getString(cursor.getColumnIndex("templateTitle"));
            this.templateId = cursor.getLong(cursor.getColumnIndex("templateId"));
            this.templateCols = cursor.getInt(cursor.getColumnIndex("cols"));  // TODO: Bug?
            this.templateRows = cursor.getInt(cursor.getColumnIndex("rows"));  // TODO: Bug?

            this.timestamp =
                cursor.getLong(cursor.getColumnIndex(org.wheatgenetics.coordinate.database.GridsTable.STAMP_FIELD_NAME));

            return true;
        }
    }

    // region Operations
    public boolean get(final long id)
    {
        final android.database.Cursor cursor = this.rawQuery(
            "SELECT grids._id, grids.title as gridTitle, grids.stamp, " +
                "templates.type as templateType, templates.title as templateTitle, " +
                "templates._id as templateId, templates.rows, templates.cols from grids, " +
                "templates " +
                    "where templates._id = grids.temp and grids." +
                        org.wheatgenetics.coordinate.database.Table.ID_FIELD_NAME + "=" + id);
        if (null == cursor)
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
        return this.selectionQueryAll(/* selection => */
            org.wheatgenetics.coordinate.database.GridsTable.TEMP_FIELD_NAME + " = " + tmp);
    }

    public android.database.Cursor getAllGrids()
    {
        this.sendInfoLogMsg(
            "Loading table " + org.wheatgenetics.coordinate.database.GridsTable.TABLE_NAME);
        return this.rawQuery(
            "SELECT grids._id, grids.title as gridTitle, grids.stamp, templates.type as templateType, templates.title as templateTitle, " +
                "templates._id as templateId, templates.rows, templates.cols from grids, templates where templates._id = grids.temp");
    }

    public boolean getByTemplate(final long entry)                            // TODO: Remove later.
    {
        android.database.Cursor cursor;
        {
            final java.lang.String selection = org.wheatgenetics.coordinate.database.GridsTable.TEMP_FIELD_NAME + "= ?";
            final java.lang.String[] selectionArgs = new java.lang.String[]{java.lang.String.valueOf(entry)};
            cursor = this.queryDistinct(/* selection => */ selection, /* selectionArgs => */ selectionArgs);
        }
        if (null == cursor)
            return false;
        else
            try     { return cursor.moveToFirst() ? this.copy(cursor) : false; }
            finally { cursor.close();                                          }
    }

    public boolean update()                                                   // TODO: Remove later.
    {
        this.sendInfoLogMsg("Updating table " +
            org.wheatgenetics.coordinate.database.GridsTable.TABLE_NAME + " on id = " + id);
        return this.update(/* whereClause   => */
            org.wheatgenetics.coordinate.database.Table.ID_FIELD_NAME + "=" + id);
    }

    public boolean deleteByTemplate(long entryId)                             // TODO: Remove later.
    {
        this.sendInfoLogMsg("Deleting from table " +
            org.wheatgenetics.coordinate.database.GridsTable.TABLE_NAME + " on id = " + entryId);
        return this.deleteUsingWhereClause(/* whereClause => */
            org.wheatgenetics.coordinate.database.GridsTable.TEMP_FIELD_NAME + "=" + entryId);
    }
    // endregion
    // endregion
}