package org.wheatgenetics.coordinate.database;

/**
 * Uses:
 * android.content.ContentValues
 * android.database.Cursor
 * android.util.Log
 * org.wheatgenetics.coordinate.Coordinate
 */

public class Grid extends java.lang.Object
{
    public long             id, template, templateId;
    public java.lang.String templateTitle           ;
    public int              templateType, rows, cols;
    public java.lang.String title                   ;
    public long             stamp                   ;

    public Grid()
    {
        super();

        this.id       = 0;
        this.template = 0;

        this.templateTitle = "";

        this.rows = 0;
        this.cols = 0;

        this.title = "";

        this.stamp = 0;
    }

    public Grid(final long template, final long stamp)                        // TODO: Remove later.
    {
        super();

        this.id       = 0       ;
        this.template = template;

        this.stamp = stamp;
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


    protected android.content.ContentValues getValues()
    {
        final android.content.ContentValues contentValues = new android.content.ContentValues();

        contentValues.put(
                org.wheatgenetics.coordinate.database.Grid.TEMPLATE_FIELD_NAME, this.template);
        contentValues.put(org.wheatgenetics.coordinate.database.Grid.TITLE_FIELD_NAME, this.title);
        contentValues.put(org.wheatgenetics.coordinate.database.Grid.STAMP_FIELD_NAME, this.stamp);

        return contentValues;
    }

    public boolean copy(final android.database.Cursor cursor)
    {
        assert cursor != null;
        try
        {
            this.id = cursor.getInt(cursor.getColumnIndex(
                    org.wheatgenetics.coordinate.database.Grid.ID_FIELD_NAME));
            this.template = cursor.getInt(cursor.getColumnIndex(
                    org.wheatgenetics.coordinate.database.Grid.TEMPLATE_FIELD_NAME));
            this.title = cursor.getString(cursor.getColumnIndex(
                    org.wheatgenetics.coordinate.database.Grid.TITLE_FIELD_NAME));
            this.stamp = cursor.getLong(cursor.getColumnIndex(
                    org.wheatgenetics.coordinate.database.Grid.STAMP_FIELD_NAME));

            return true;
        }
        catch (java.lang.Exception e) {}                                            // TODO: Really?
        return false;
    }

    public boolean get(final long id)
    {
        android.database.Cursor cursor = null;
        try
        {
            cursor = org.wheatgenetics.coordinate.Coordinate.db.rawQuery(
                    "SELECT grids._id, grids.title as gridTitle, grids.stamp, templates.type as templateType, " +
                            "templates.title as templateTitle, templates._id as templateId, templates.rows, templates.cols from grids, templates where templates._id = grids.temp and grids." +
                            org.wheatgenetics.coordinate.database.Grid.ID_FIELD_NAME + "=" + id, null);
            if (cursor != null)
            {
                cursor.moveToFirst();
                this.copyAll(cursor);
                cursor.close();
                return true;
            }
        }
        catch (java.lang.Exception e) { if (cursor != null) cursor.close(); }
        return false;
    }

    private static int sendInfoLogMsg(final java.lang.String msg)
    {
        return android.util.Log.i("Grid", msg);
    }

    public android.database.Cursor load()                                     // TODO: Remove later.
    {
        org.wheatgenetics.coordinate.database.Grid.sendInfoLogMsg("Loading table " + org.wheatgenetics.coordinate.database.Grid.TABLE_NAME);
        return org.wheatgenetics.coordinate.Coordinate.db.query(org.wheatgenetics.coordinate.database.Grid.TABLE_NAME, null, null, null, null, null, null);
    }

    public long insert()
    {
        org.wheatgenetics.coordinate.database.Grid.sendInfoLogMsg("Inserting into table " + org.wheatgenetics.coordinate.database.Grid.TABLE_NAME);
        return org.wheatgenetics.coordinate.Coordinate.db.insert(org.wheatgenetics.coordinate.database.Grid.TABLE_NAME, null, getValues());
    }

    public boolean update()                                                   // TODO: Remove later.
    {
        org.wheatgenetics.coordinate.database.Grid.sendInfoLogMsg("Updating table " + org.wheatgenetics.coordinate.database.Grid.TABLE_NAME + " on id = " + id);
        return org.wheatgenetics.coordinate.Coordinate.db.update(org.wheatgenetics.coordinate.database.Grid.TABLE_NAME, getValues(), org.wheatgenetics.coordinate.database.Grid.ID_FIELD_NAME + "=" + id, null) > 0;
    }

    public boolean delete(final long id)
    {
        org.wheatgenetics.coordinate.database.Grid.sendInfoLogMsg("Deleting from table " + org.wheatgenetics.coordinate.database.Grid.TABLE_NAME + " on id = " + id);
        return org.wheatgenetics.coordinate.Coordinate.db.delete(org.wheatgenetics.coordinate.database.Grid.TABLE_NAME, org.wheatgenetics.coordinate.database.Grid.ID_FIELD_NAME + "=" + id, null) > 0;
    }

    public boolean delete()                                                   // TODO: Remove later.
    {
        org.wheatgenetics.coordinate.database.Grid.sendInfoLogMsg("Clearing table " + org.wheatgenetics.coordinate.database.Grid.TABLE_NAME);
        return org.wheatgenetics.coordinate.Coordinate.db.delete(org.wheatgenetics.coordinate.database.Grid.TABLE_NAME, null, null) > 0;
    }

    public android.database.Cursor loadByTemplate(final long tmp)
    {
        org.wheatgenetics.coordinate.database.Grid.sendInfoLogMsg("Loading table " + org.wheatgenetics.coordinate.database.Grid.TABLE_NAME + " by entry = " + tmp);
        return org.wheatgenetics.coordinate.Coordinate.db.query(org.wheatgenetics.coordinate.database.Grid.TABLE_NAME, null, org.wheatgenetics.coordinate.database.Grid.TEMPLATE_FIELD_NAME + " = " + tmp, null, null, null, null);
    }

    public android.database.Cursor getAllGrids()
    {
        org.wheatgenetics.coordinate.database.Grid.sendInfoLogMsg("Loading table " + org.wheatgenetics.coordinate.database.Grid.TABLE_NAME);
        return org.wheatgenetics.coordinate.Coordinate.db.rawQuery("SELECT grids._id, grids.title as gridTitle, grids.stamp, templates.type as templateType, templates.title as templateTitle, templates._id as templateId, templates.rows, templates.cols from grids, templates where templates._id = grids.temp", null);
    }

    public boolean copyAll(final android.database.Cursor cursor)
    {
        assert cursor != null;
        try
        {
            this.id = cursor.getInt(cursor.getColumnIndex(org.wheatgenetics.coordinate.database.Grid.ID_FIELD_NAME));
            this.title = cursor.getString(cursor.getColumnIndex("gridTitle"));

            this.templateType = cursor.getInt(cursor.getColumnIndex("templateType"));
            this.templateTitle = cursor.getString(cursor.getColumnIndex("templateTitle"));
            this.templateId = cursor.getLong(cursor.getColumnIndex("templateId"));
            this.cols = cursor.getInt(cursor.getColumnIndex("cols"));
            this.rows = cursor.getInt(cursor.getColumnIndex("rows"));

            this.stamp = cursor.getLong(cursor.getColumnIndex(org.wheatgenetics.coordinate.database.Grid.STAMP_FIELD_NAME));

            return true;
        }
        catch (java.lang.Exception e) {}                                            // TODO: Really?
        return false;
    }

    public boolean getByTemplate(final long entry)                            // TODO: Remove later.
    {
        android.database.Cursor cursor = null;
        try
        {
            final java.lang.String sel = org.wheatgenetics.coordinate.database.Grid.TEMPLATE_FIELD_NAME + "= ?";

            final java.lang.String[] arg = new java.lang.String[]{java.lang.String.valueOf(entry)};

            cursor = org.wheatgenetics.coordinate.Coordinate.db.query(true,
                org.wheatgenetics.coordinate.database.Grid.TABLE_NAME,
                null, sel, arg, null, null, null, null);
            if (cursor != null)
            {
                boolean ret = false;
                if (cursor.moveToFirst())
                {
                    this.copy(cursor);
                    ret = true;
                }
                cursor.close();
                return ret;
            }
        }
        catch (java.lang.Exception e) { if (cursor != null) cursor.close(); }
        return false;
    }

    public boolean deleteByTemplate(long entryId)                             // TODO: Remove later.
    {
        org.wheatgenetics.coordinate.database.Grid.sendInfoLogMsg("Deleting from table " + org.wheatgenetics.coordinate.database.Grid.TABLE_NAME + " on id = " + entryId);
        return org.wheatgenetics.coordinate.Coordinate.db.delete(org.wheatgenetics.coordinate.database.Grid.TABLE_NAME, org.wheatgenetics.coordinate.database.Grid.TEMPLATE_FIELD_NAME + "=" + entryId, null) > 0;
    }
    // endregion
}