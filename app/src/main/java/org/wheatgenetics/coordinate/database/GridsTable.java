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
 * org.wheatgenetics.coordinate.model.GridModel
 * org.wheatgenetics.coordinate.model.Model
 *
 * org.wheatgenetics.coordinate.database.Table
 * org.wheatgenetics.coordinate.database.TemplatesTable
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

    // region Constants
    private static final java.lang.String TABLE_NAME      = "grids";
    private static final java.lang.String TEMP_FIELD_NAME = "temp",
        TITLE_FIELD_NAME = "title", STAMP_FIELD_NAME = "stamp";
    private static final java.lang.String TEMPLATETITLE_FIELD_NAME = "templateTitle",
        TEMPLATETYPE_FIELD_NAME ="templateType";
    // endregion

    public GridsTable(final android.content.Context context)
    {
        super(
            /* context   => */ context                                                    ,
            /* tableName => */ org.wheatgenetics.coordinate.database.GridsTable.TABLE_NAME,
            /* tag       => */ "GridsTable"                                               );
    }

    // region Overridden Methods
    @java.lang.Override @android.annotation.SuppressLint("DefaultLocale")
    public java.lang.String toString()
    {
        return super.toString() + java.lang.String.format(
            " templateId: %02d timestamp: %02d", this.templateId, this.timestamp);
    }

    @java.lang.Override
    org.wheatgenetics.coordinate.model.Model make(final android.database.Cursor cursor)  // TODO: Make private.
    {
        return null == cursor ? null : new org.wheatgenetics.coordinate.model.GridModel(
            /* id => */ cursor.getInt(cursor.getColumnIndex(        // TODO: Why getInt() not getLong()? Others? Put in ancestor?
                org.wheatgenetics.coordinate.database.Table.ID_FIELD_NAME)),
            /* title => */ cursor.getString(cursor.getColumnIndex(
                org.wheatgenetics.coordinate.database.GridsTable.TITLE_FIELD_NAME)),
            /* timestamp => */ cursor.getLong(cursor.getColumnIndex(
                org.wheatgenetics.coordinate.database.GridsTable.STAMP_FIELD_NAME)),
            /* templateId => */ cursor.getInt(cursor.getColumnIndex(
                org.wheatgenetics.coordinate.database.GridsTable.TEMP_FIELD_NAME)),
            /* templateTitle => */ cursor.getString(cursor.getColumnIndex(
                org.wheatgenetics.coordinate.database.GridsTable.TEMPLATETITLE_FIELD_NAME)),
            /* templateType  => */ cursor.getInt(cursor.getColumnIndex(
                org.wheatgenetics.coordinate.database.GridsTable.TEMPLATETYPE_FIELD_NAME)),
            /* templateRows  => */ cursor.getInt(cursor.getColumnIndex(
                org.wheatgenetics.coordinate.database.TemplatesTable.ROWS_FIELD_NAME)),
            /* templateCols  => */ cursor.getInt(cursor.getColumnIndex(
                org.wheatgenetics.coordinate.database.TemplatesTable.COLS_FIELD_NAME)));
    }

    @java.lang.Override
    android.content.ContentValues getContentValues()                          // TODO: Remove later.
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
        final android.content.ContentValues result =
            super.getContentValues(model);                          // throws org.json.JSONException

        final org.wheatgenetics.coordinate.model.GridModel gridModel =
            (org.wheatgenetics.coordinate.model.GridModel) model;

        assert null != gridModel;
        result.put(org.wheatgenetics.coordinate.database.GridsTable.TEMP_FIELD_NAME,
            gridModel.getId());
        result.put(org.wheatgenetics.coordinate.database.GridsTable.TITLE_FIELD_NAME,
            gridModel.getTitle());
        result.put(org.wheatgenetics.coordinate.database.GridsTable.STAMP_FIELD_NAME,
            gridModel.getTimestamp());

        return result;
    }
    // endregion

    public boolean copy(final android.database.Cursor cursor)                 // TODO: Remove later.
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

    public boolean copyAll(final android.database.Cursor cursor)               // TODO: Remove later.
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
    private android.database.Cursor query(final long id) // TODO: Push to superclass? Fold into get()?
    {
        final java.lang.String
            gridsTableName     = org.wheatgenetics.coordinate.database.GridsTable.TABLE_NAME    ,
            templatesTableName = org.wheatgenetics.coordinate.database.TemplatesTable.TABLE_NAME;
        final java.lang.String
            gridsQualifier = gridsTableName + '.', templatesQualifier = templatesTableName + '.';
        final java.lang.String
            idFieldName = gridsQualifier +
                org.wheatgenetics.coordinate.database.Table.ID_FIELD_NAME,
            titleFieldName = gridsQualifier +
                org.wheatgenetics.coordinate.database.GridsTable.TITLE_FIELD_NAME,
            stampFieldName = gridsQualifier +
                org.wheatgenetics.coordinate.database.GridsTable.STAMP_FIELD_NAME,
            tempFieldName = gridsQualifier +
                org.wheatgenetics.coordinate.database.GridsTable.TEMP_FIELD_NAME,
            templateTitleFieldName = templatesQualifier +
                org.wheatgenetics.coordinate.database.TemplatesTable.TITLE_FIELD_NAME,
            templateTypeFieldName = templatesQualifier +
                org.wheatgenetics.coordinate.database.TemplatesTable.TYPE_FIELD_NAME,
            rowsFieldName = templatesQualifier +
                org.wheatgenetics.coordinate.database.TemplatesTable.ROWS_FIELD_NAME,
            colsFieldName = templatesQualifier +
                org.wheatgenetics.coordinate.database.TemplatesTable.COLS_FIELD_NAME,
            templateIdFieldName = templatesQualifier +
                org.wheatgenetics.coordinate.database.Table.ID_FIELD_NAME;
        return this.rawQuery("SELECT ALL " + idFieldName + ", " + titleFieldName + ", " +
            stampFieldName + ", " + tempFieldName + ", " +
            templateTitleFieldName + " AS " +
                org.wheatgenetics.coordinate.database.GridsTable.TEMPLATETITLE_FIELD_NAME + ", " +
            templateTypeFieldName + " AS " +
                org.wheatgenetics.coordinate.database.GridsTable.TEMPLATETYPE_FIELD_NAME + ", " +
            rowsFieldName + ", " + colsFieldName +
                " FROM " + gridsTableName + ", " + templatesTableName +
                " WHERE " + tempFieldName + " = " + templateIdFieldName +
                    " AND " + idFieldName + " = " + id);
    }

    public org.wheatgenetics.coordinate.model.GridModel get(final long id)
    { return (org.wheatgenetics.coordinate.model.GridModel) this.makeFromFirst(this.query(id)); }

    public android.database.Cursor getAllGrids()
    {
        this.sendInfoLogMsg(
            "Loading table " + org.wheatgenetics.coordinate.database.GridsTable.TABLE_NAME);
        return this.rawQuery(
            "SELECT grids._id, grids.title as gridTitle, grids.stamp, templates.type as templateType, templates.title as templateTitle, " +
                "templates._id as templateId, templates.rows, templates.cols from grids, templates where templates._id = grids.temp");
    }

    public boolean delete(final long temp)
    {
        return this.deleteUsingWhereClause(/* whereClause => */
            org.wheatgenetics.coordinate.database.GridsTable.TEMP_FIELD_NAME + " = " + temp );
    }
    // endregion
}