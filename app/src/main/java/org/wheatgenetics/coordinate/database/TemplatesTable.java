package org.wheatgenetics.coordinate.database;

/**
 * Uses:
 * android.content.ContentValues
 * android.content.Context
 * android.database.Cursor
 *
 * org.wheatgenetics.coordinate.database.Table
 * org.wheatgenetics.coordinate.model.TemplateModel
 * org.wheatgenetics.coordinate.model.TemplateType
 */

public class TemplatesTable extends org.wheatgenetics.coordinate.database.Table
{
    // region Fields
    public java.lang.String title = "";
    public int              type  =  0;
    public int              rows  =  0;
    public int              cols  =  0;

    public java.lang.String excludeCells = "";

    public java.lang.String excludeRows = "";
    public java.lang.String excludeCols = "";

    public java.lang.String options = "";

    public int colNumbering = 1;
    public int rowNumbering = 1;

    public long stamp = 0;
    // endregion

    public TemplatesTable(final android.content.Context context)
    {
        super(
            /* context   => */ context                                                        ,
            /* tableName => */ org.wheatgenetics.coordinate.database.TemplatesTable.TABLE_NAME,
            /* tag       => */ "TemplatesTable"                                               );
    }

    @java.lang.Override
    public java.lang.String toString()
    {
        return "TemplatesTable" +
            " [" + super.toString()               + ", title="        + this.title        +
            ", type="         + this.type         + ", rows="         + this.rows         +
            ", cols="         + this.cols         + ", excludeCells=" + this.excludeCells +
            ", excludeRows="  + this.excludeRows  + ", excludeCols="  + this.excludeCols  +
            ", options="      + this.options      + ", colNumbering=" + this.colNumbering +
            ", rowNumbering=" + this.rowNumbering + ", stamp="        + this.stamp        + "]";
    }

    // region Storage
    // region Private Constants
    private static final java.lang.String TABLE_NAME = "templates";

    private static final java.lang.String TITLE_FIELD_NAME = "title", TYPE_FIELD_NAME = "type";
    private static final java.lang.String COLS_FIELD_NAME  = "cols" , ROWS_FIELD_NAME = "rows";
    private static final java.lang.String
        ECELLS_FIELD_NAME = "ecells", ECOLS_FIELD_NAME = "ecols", EROWS_FIELD_NAME = "erows";
    private static final java.lang.String CNUMB_FIELD_NAME   = "cnumb", RNUMB_FIELD_NAME = "rnumb";
    private static final java.lang.String OPTIONS_FIELD_NAME = "options";
    private static final java.lang.String STAMP_FIELD_NAME   = "stamp"  ;
    // endregion


    @java.lang.Override
    org.wheatgenetics.coordinate.model.TemplateModel make(final android.database.Cursor cursor)  // TODO: Make private.
    {
        if (null == cursor)
            return null;
        else
            return new org.wheatgenetics.coordinate.model.TemplateModel(
                /* id => */ cursor.getInt(cursor.getColumnIndex(
                    org.wheatgenetics.coordinate.database.Table.ID_FIELD_NAME)),
                /* title => */ cursor.getString(cursor.getColumnIndex(
                    org.wheatgenetics.coordinate.database.TemplatesTable.TITLE_FIELD_NAME)),
                /* type => */ cursor.getInt(cursor.getColumnIndex(
                    org.wheatgenetics.coordinate.database.TemplatesTable.TYPE_FIELD_NAME)),
                /* rows => */ cursor.getInt(cursor.getColumnIndex(
                    org.wheatgenetics.coordinate.database.TemplatesTable.COLS_FIELD_NAME)),
                /* cols => */ cursor.getInt(cursor.getColumnIndex(
                    org.wheatgenetics.coordinate.database.TemplatesTable.ROWS_FIELD_NAME)),
                /* excludeCells => */ cursor.getString(cursor.getColumnIndex(
                    org.wheatgenetics.coordinate.database.TemplatesTable.ECELLS_FIELD_NAME)),
                /* excludeRows => */ cursor.getString(cursor.getColumnIndex(
                    org.wheatgenetics.coordinate.database.TemplatesTable.ECOLS_FIELD_NAME)),
                /* excludeCols => */ cursor.getString(cursor.getColumnIndex(
                    org.wheatgenetics.coordinate.database.TemplatesTable.EROWS_FIELD_NAME)),
                /* colNumbering => */ cursor.getInt(cursor.getColumnIndex(
                    org.wheatgenetics.coordinate.database.TemplatesTable.CNUMB_FIELD_NAME)),
                /* rowNumbering => */ cursor.getInt(cursor.getColumnIndex(
                    org.wheatgenetics.coordinate.database.TemplatesTable.RNUMB_FIELD_NAME)),
                /* optionalFields => */ cursor.getString(cursor.getColumnIndex(
                    org.wheatgenetics.coordinate.database.TemplatesTable.OPTIONS_FIELD_NAME)),
                /* timestamp => */ cursor.getLong(cursor.getColumnIndex(
                    org.wheatgenetics.coordinate.database.TemplatesTable.STAMP_FIELD_NAME)));
    }

    @java.lang.Override
    android.content.ContentValues getContentValues()
    {
        final android.content.ContentValues contentValues = super.getContentValues();

        contentValues.put(org.wheatgenetics.coordinate.database.TemplatesTable.TITLE_FIELD_NAME,
            this.title);
        contentValues.put(org.wheatgenetics.coordinate.database.TemplatesTable.TYPE_FIELD_NAME,
            this.type);
        contentValues.put(org.wheatgenetics.coordinate.database.TemplatesTable.COLS_FIELD_NAME,
            this.cols);
        contentValues.put(org.wheatgenetics.coordinate.database.TemplatesTable.ROWS_FIELD_NAME,
            this.rows);

        contentValues.put(org.wheatgenetics.coordinate.database.TemplatesTable.ECELLS_FIELD_NAME,
            this.excludeCells);
        contentValues.put(org.wheatgenetics.coordinate.database.TemplatesTable.ECOLS_FIELD_NAME,
            this.excludeCols);
        contentValues.put(org.wheatgenetics.coordinate.database.TemplatesTable.EROWS_FIELD_NAME,
            this.excludeRows);

        contentValues.put(org.wheatgenetics.coordinate.database.TemplatesTable.CNUMB_FIELD_NAME,
            this.colNumbering);
        contentValues.put(org.wheatgenetics.coordinate.database.TemplatesTable.RNUMB_FIELD_NAME,
            this.rowNumbering);

        contentValues.put(org.wheatgenetics.coordinate.database.TemplatesTable.OPTIONS_FIELD_NAME,
            this.options);

        contentValues.put(org.wheatgenetics.coordinate.database.TemplatesTable.STAMP_FIELD_NAME,
            this.stamp);

        return contentValues;
    }

    public boolean copy(final android.database.Cursor cursor)
    {
        if (null == cursor)
            return false;
        else
        {
            this.id = cursor.getInt(cursor.getColumnIndex(org.wheatgenetics.coordinate.database.Table.ID_FIELD_NAME));
            this.title = cursor.getString(cursor.getColumnIndex(org.wheatgenetics.coordinate.database.TemplatesTable.TITLE_FIELD_NAME));
            this.type = cursor.getInt(cursor.getColumnIndex(org.wheatgenetics.coordinate.database.TemplatesTable.TYPE_FIELD_NAME));
            this.cols = cursor.getInt(cursor.getColumnIndex(org.wheatgenetics.coordinate.database.TemplatesTable.COLS_FIELD_NAME));
            this.rows = cursor.getInt(cursor.getColumnIndex(org.wheatgenetics.coordinate.database.TemplatesTable.ROWS_FIELD_NAME));

            this.excludeCells = cursor.getString(cursor.getColumnIndex(org.wheatgenetics.coordinate.database.TemplatesTable.ECELLS_FIELD_NAME));
            this.excludeCols = cursor.getString(cursor.getColumnIndex(org.wheatgenetics.coordinate.database.TemplatesTable.ECOLS_FIELD_NAME));
            this.excludeRows = cursor.getString(cursor.getColumnIndex(org.wheatgenetics.coordinate.database.TemplatesTable.EROWS_FIELD_NAME));

            this.options = cursor.getString(cursor.getColumnIndex(org.wheatgenetics.coordinate.database.TemplatesTable.OPTIONS_FIELD_NAME));

            this.colNumbering = cursor.getInt(cursor.getColumnIndex(org.wheatgenetics.coordinate.database.TemplatesTable.RNUMB_FIELD_NAME));  // TODO: Bug?
            this.rowNumbering = cursor.getInt(cursor.getColumnIndex(org.wheatgenetics.coordinate.database.TemplatesTable.CNUMB_FIELD_NAME));  // TODO: Bug?

            this.stamp = cursor.getLong(cursor.getColumnIndex(org.wheatgenetics.coordinate.database.TemplatesTable.STAMP_FIELD_NAME));

            return true;
        }
    }

    public boolean get(final long id)                                   // TODO: Push to superclass?
    {
        final android.database.Cursor cursor = this.queryDistinct(/* selection => */
            org.wheatgenetics.coordinate.database.Table.ID_FIELD_NAME + "=" + id);
        if (null == cursor)
            return false;
        else
            try     { return cursor.moveToFirst() ? this.copy(cursor) : false; }
            finally { cursor.close();                                          }
    }

    public boolean getByType(final org.wheatgenetics.coordinate.model.TemplateType templateType)
    {
        assert null != templateType;
        final android.database.Cursor cursor = this.queryDistinct(/* selection => */
            org.wheatgenetics.coordinate.database.TemplatesTable.TYPE_FIELD_NAME + "=" +
            templateType.getCode());
        if (null == cursor)
            return false;
        else
            try     { return cursor.moveToFirst() ? this.copy(cursor) : false; }
            finally { cursor.close();                                          }
    }

    public android.database.Cursor load()                               // TODO: Push to superclass?
    {
        this.sendInfoLogMsg(
            "Loading table " + org.wheatgenetics.coordinate.database.TemplatesTable.TABLE_NAME);
        return this.orderByQueryAll(/* orderBy => */ "type ASC");
    }

    public android.database.Cursor loadByOrder()                        // TODO: Push to superclass?
    {
        this.sendInfoLogMsg(
            "Loading table " + org.wheatgenetics.coordinate.database.TemplatesTable.TABLE_NAME);
        return this.orderByQueryAll(/* orderBy => */ "_id DESC");
    }

    public boolean update()                                             // TODO: Push to superclass?
    {
        this.sendInfoLogMsg("Updating table " +
            org.wheatgenetics.coordinate.database.TemplatesTable.TABLE_NAME + " on id = " + id);
        return this.update(/* whereClause   => */
            org.wheatgenetics.coordinate.database.Table.ID_FIELD_NAME + "=" + id);
    }
    // endregion
}