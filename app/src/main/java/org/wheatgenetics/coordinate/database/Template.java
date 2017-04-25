package org.wheatgenetics.coordinate.database;

/**
 * Uses:
 * android.content.ContentValues
 * android.database.Cursor
 * android.util.Log
 * org.wheatgenetics.coordinate.Coordinate
 */

public class Template extends java.lang.Object
{
    public long             id   ;
    public java.lang.String title;
    public int              type ;
    public int              rows ;
    public int              cols ;

    public java.lang.String ecells;

    public java.lang.String erows;
    public java.lang.String ecols;

    public java.lang.String options;

    public int cnumbering;
    public int rnumbering;

    public long stamp;

    public Template()
    {
        super();

        this.id = 0;

        this.title = "";
        this.type  = 0 ;

        this.rows = 0;
        this.cols = 0;

        this.ecells = "";
        this.ecols  = "";
        this.erows  = "";

        this.options = "";

        this.cnumbering = 1;
        this.rnumbering = 1;

        this.stamp = 0;
    }

    public Template(final int id, final java.lang.String title, final int type,
    final int cols, final int rows, final long stamp)
    {
        super();

        this.id    = id   ;
        this.title = title;
        this.type  = type ;
        this.cols  = cols ;
        this.rows  = rows ;
        this.stamp = stamp;
    }

    @Override
    public java.lang.String toString()
    {
        return "Template" +
                " [id="         + this.id         + ", title="      + this.title      +
                ", type="       + this.type       + ", rows="       + this.rows       +
                ", cols="       + this.cols       + ", ecells="     + this.ecells     +
                ", erows="      + this.erows      + ", ecols="      + this.ecols      +
                ", options="    + this.options    + ", cnumbering=" + this.cnumbering +
                ", rnumbering=" + this.rnumbering + ", stamp="      + this.stamp      + "]";
    }


    // region Storage
    // region Private Constants
    private static final java.lang.String TABLE_NAME = "templates";

    private static final java.lang.String
        ID_FIELD_NAME = "_id", TITLE_FIELD_NAME = "title", TYPE_FIELD_NAME = "type";
    private static final java.lang.String COLS_FIELD_NAME = "cols", ROWS_FIELD_NAME = "rows";
    private static final java.lang.String
        ECELLS_FIELD_NAME = "ecells", ECOLS_FIELD_NAME = "ecols", EROWS_FIELD_NAME = "erows";
    private static final java.lang.String CNUMB_FIELD_NAME = "cnumb", RNUMB_FIELD_NAME = "rnumb";
    private static final java.lang.String OPTIONS_FIELD_NAME = "options";
    private static final java.lang.String STAMP_FIELD_NAME = "stamp";
    // endregion


    protected android.content.ContentValues getValues()
    {
        final android.content.ContentValues contentValues = new android.content.ContentValues();

        contentValues.put(org.wheatgenetics.coordinate.database.Template.TITLE_FIELD_NAME, this.title);
        contentValues.put(org.wheatgenetics.coordinate.database.Template.TYPE_FIELD_NAME, this.type);
        contentValues.put(org.wheatgenetics.coordinate.database.Template.COLS_FIELD_NAME, this.cols);
        contentValues.put(org.wheatgenetics.coordinate.database.Template.ROWS_FIELD_NAME, this.rows);

        contentValues.put(org.wheatgenetics.coordinate.database.Template.ECELLS_FIELD_NAME, this.ecells);
        contentValues.put(org.wheatgenetics.coordinate.database.Template.ECOLS_FIELD_NAME, this.ecols);
        contentValues.put(org.wheatgenetics.coordinate.database.Template.EROWS_FIELD_NAME, this.erows);

        contentValues.put(org.wheatgenetics.coordinate.database.Template.CNUMB_FIELD_NAME, this.cnumbering);
        contentValues.put(org.wheatgenetics.coordinate.database.Template.RNUMB_FIELD_NAME, this.rnumbering);

        contentValues.put(org.wheatgenetics.coordinate.database.Template.OPTIONS_FIELD_NAME, this.options);

        contentValues.put(org.wheatgenetics.coordinate.database.Template.STAMP_FIELD_NAME, this.stamp);

        return contentValues;
    }

    public boolean copy(final android.database.Cursor cursor)
    {
        assert cursor != null;
        try
        {
            this.id = cursor.getInt(cursor.getColumnIndex(org.wheatgenetics.coordinate.database.Template.ID_FIELD_NAME));
            this.title = cursor.getString(cursor.getColumnIndex(org.wheatgenetics.coordinate.database.Template.TITLE_FIELD_NAME));
            this.type = cursor.getInt(cursor.getColumnIndex(org.wheatgenetics.coordinate.database.Template.TYPE_FIELD_NAME));
            this.cols = cursor.getInt(cursor.getColumnIndex(org.wheatgenetics.coordinate.database.Template.COLS_FIELD_NAME));
            this.rows = cursor.getInt(cursor.getColumnIndex(org.wheatgenetics.coordinate.database.Template.ROWS_FIELD_NAME));

            this.ecells = cursor.getString(cursor.getColumnIndex(org.wheatgenetics.coordinate.database.Template.ECELLS_FIELD_NAME));
            this.ecols = cursor.getString(cursor.getColumnIndex(org.wheatgenetics.coordinate.database.Template.ECOLS_FIELD_NAME));
            this.erows = cursor.getString(cursor.getColumnIndex(org.wheatgenetics.coordinate.database.Template.EROWS_FIELD_NAME));

            this.options = cursor.getString(cursor.getColumnIndex(org.wheatgenetics.coordinate.database.Template.OPTIONS_FIELD_NAME));

            this.cnumbering = cursor.getInt(cursor.getColumnIndex(org.wheatgenetics.coordinate.database.Template.RNUMB_FIELD_NAME));
            this.rnumbering = cursor.getInt(cursor.getColumnIndex(org.wheatgenetics.coordinate.database.Template.CNUMB_FIELD_NAME));

            this.stamp = cursor.getLong(cursor.getColumnIndex(org.wheatgenetics.coordinate.database.Template.STAMP_FIELD_NAME));

            return true;
        }
        catch (java.lang.Exception e) {}                                            // TODO: Really?
        return false;
    }

    public boolean get(final long id)
    {
        boolean                 ret    = false;
        android.database.Cursor cursor = null ;
        try
        {
            cursor = org.wheatgenetics.coordinate.Coordinate.queryDistinct(
                /* tableName => */ org.wheatgenetics.coordinate.database.Template.TABLE_NAME,
                /* selection => */
                    org.wheatgenetics.coordinate.database.Template.ID_FIELD_NAME + "=" + id);
            if (cursor != null) if (cursor.moveToFirst()) ret = this.copy(cursor);
        }
        catch (java.lang.Exception e) {}                                            // TODO: Really?
        finally { if (cursor != null) cursor.close(); }
        return ret;
    }


    public boolean getByType(final int typ)
    {
        boolean                 ret    = false;
        android.database.Cursor cursor = null ;
        try
        {
            cursor = org.wheatgenetics.coordinate.Coordinate.queryDistinct(
                /* tableName => */ org.wheatgenetics.coordinate.database.Template.TABLE_NAME,
                /* selection => */
                    org.wheatgenetics.coordinate.database.Template.TYPE_FIELD_NAME + "=" + typ);
            if (cursor != null) if (cursor.moveToFirst()) ret = this.copy(cursor);
        }
        finally { if (cursor != null) cursor.close(); }
        return ret;
    }

    private static int sendInfoLogMsg(final java.lang.String msg)
    {
        return android.util.Log.i("Template", msg);
    }

    public android.database.Cursor load()
    {
        org.wheatgenetics.coordinate.database.Template.sendInfoLogMsg(
            "Loading table " + org.wheatgenetics.coordinate.database.Template.TABLE_NAME);
        return org.wheatgenetics.coordinate.Coordinate.queryAllOrderBy(
            /* tableName => */ org.wheatgenetics.coordinate.database.Template.TABLE_NAME,
            /* orderBy   => */ "type ASC"                                               );
    }

    public android.database.Cursor loadByOrder()
    {
        org.wheatgenetics.coordinate.database.Template.sendInfoLogMsg(
            "Loading table " + org.wheatgenetics.coordinate.database.Template.TABLE_NAME);
        return org.wheatgenetics.coordinate.Coordinate.queryAllOrderBy(
            /* tableName => */ org.wheatgenetics.coordinate.database.Template.TABLE_NAME,
            /* orderBy   => */ "_id DESC"                                               );
    }

    public long insert()
    {
        org.wheatgenetics.coordinate.database.Template.sendInfoLogMsg(
            "Inserting into table " + org.wheatgenetics.coordinate.database.Template.TABLE_NAME);
        return org.wheatgenetics.coordinate.Coordinate.insert(
            org.wheatgenetics.coordinate.database.Template.TABLE_NAME, this.getValues());
    }

    public boolean update()
    {
        org.wheatgenetics.coordinate.database.Template.sendInfoLogMsg( "Updating table " +
            org.wheatgenetics.coordinate.database.Template.TABLE_NAME + " on id = " + id);
        return org.wheatgenetics.coordinate.Coordinate.update(
            /* tableName     => */ org.wheatgenetics.coordinate.database.Template.TABLE_NAME,
            /* contentValues => */ this.getValues()                                         ,
            /* whereClause   => */
                org.wheatgenetics.coordinate.database.Template.ID_FIELD_NAME + "=" + id);
    }


    public boolean delete(final long id)
    {
        org.wheatgenetics.coordinate.database.Template.sendInfoLogMsg("Deleting from table " +
            org.wheatgenetics.coordinate.database.Template.TABLE_NAME + " on id = " + id);
        return org.wheatgenetics.coordinate.Coordinate.delete(
            org.wheatgenetics.coordinate.database.Template.TABLE_NAME                    ,
            /* whereClause => */ org.wheatgenetics.coordinate.database.Template.ID_FIELD_NAME + "=" + id);
    }

    public boolean delete()
    {
        org.wheatgenetics.coordinate.database.Template.sendInfoLogMsg(
            "Clearing table " + org.wheatgenetics.coordinate.database.Template.TABLE_NAME);
        return org.wheatgenetics.coordinate.Coordinate.delete(
            org.wheatgenetics.coordinate.database.Template.TABLE_NAME);
    }
    // endregion
}