package org.wheatgenetics.coordinate.database;

/**
 * Uses:
 * android.content.ContentValues
 * android.content.Context
 * android.database.Cursor
 *
 * org.wheatgenetics.coordinate.database.Table
 */

public class TemplatesTable extends org.wheatgenetics.coordinate.database.Table
{
    public long             id   ;
    public java.lang.String title;
    public int              type ;
    public int              rows ;
    public int              cols ;

    public java.lang.String excludeCells;

    public java.lang.String excludeRows;
    public java.lang.String excludeCols;

    public java.lang.String options;

    public int colNumbering;
    public int rowNumbering;

    public long stamp;

    public TemplatesTable(final android.content.Context context)
    {
        super(
            /* context   => */ context                                                        ,
            /* tableName => */ org.wheatgenetics.coordinate.database.TemplatesTable.TABLE_NAME,
            /* tag       => */ "TemplatesTable"                                               );

        this.id = 0;

        this.title = "";
        this.type  = 0 ;

        this.rows = 0;
        this.cols = 0;

        this.excludeCells = "";
        this.excludeCols  = "";
        this.excludeRows  = "";

        this.options = "";

        this.colNumbering = 1;
        this.rowNumbering = 1;

        this.stamp = 0;
    }

    @Override
    public java.lang.String toString()
    {
        return "TemplatesTable" +
            " [id="           + this.id           + ", title="        + this.title        +
            ", type="         + this.type         + ", rows="         + this.rows         +
            ", cols="         + this.cols         + ", excludeCells="       + this.excludeCells       +
            ", excludeRows="        + this.excludeRows        + ", excludeCols="        + this.excludeCols        +
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
    private static final java.lang.String CNUMB_FIELD_NAME = "cnumb", RNUMB_FIELD_NAME = "rnumb";
    private static final java.lang.String OPTIONS_FIELD_NAME = "options";
    private static final java.lang.String STAMP_FIELD_NAME = "stamp";
    // endregion


    @Override
    android.content.ContentValues getContentValues()
    {
        final android.content.ContentValues contentValues = new android.content.ContentValues();

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
        if (cursor == null)
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

    public boolean get(final long id)
    {
        final android.database.Cursor cursor = this.queryDistinct(/* selection => */
            org.wheatgenetics.coordinate.database.Table.ID_FIELD_NAME + "=" + id);
        if (cursor == null)
            return false;
        else
            try     { if (cursor.moveToFirst()) return this.copy(cursor); else return false; }
            finally { cursor.close();                                                        }
    }

    public boolean getByType(final int type)  // TODO: Change from int to TemplateType.
    {
        final android.database.Cursor cursor = this.queryDistinct(/* selection => */
            org.wheatgenetics.coordinate.database.TemplatesTable.TYPE_FIELD_NAME + "=" + type);
        if (cursor == null)
            return false;
        else
            try     { if (cursor.moveToFirst()) return this.copy(cursor); else return false; }
            finally { cursor.close();                                                        }
    }

    public android.database.Cursor load()
    {
        this.sendInfoLogMsg(
            "Loading table " + org.wheatgenetics.coordinate.database.TemplatesTable.TABLE_NAME);
        return this.queryAllOrderBy(/* orderBy => */ "type ASC");
    }

    public android.database.Cursor loadByOrder()
    {
        this.sendInfoLogMsg(
            "Loading table " + org.wheatgenetics.coordinate.database.TemplatesTable.TABLE_NAME);
        return this.queryAllOrderBy(/* orderBy => */ "_id DESC");
    }

    public boolean update()
    {
        this.sendInfoLogMsg("Updating table " +
            org.wheatgenetics.coordinate.database.TemplatesTable.TABLE_NAME + " on id = " + id);
        return this.update(/* whereClause   => */
            org.wheatgenetics.coordinate.database.Table.ID_FIELD_NAME + "=" + id);
    }
    // endregion
}