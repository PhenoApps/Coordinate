package org.wheatgenetics.coordinate.database;

/**
 * Uses:
 * android.content.ContentValues
 * android.content.Context
 * android.database.Cursor
 *
 * org.json.JSONException
 *
 * org.wheatgenetics.coordinate.database.Table
 * org.wheatgenetics.coordinate.model.Model
 * org.wheatgenetics.coordinate.model.TemplateModel
 * org.wheatgenetics.coordinate.model.TemplateModels
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
    android.content.ContentValues getContentValues(
    final org.wheatgenetics.coordinate.model.Model model) throws org.json.JSONException
    {
        final android.content.ContentValues contentValues = super.getContentValues(model);

        final org.wheatgenetics.coordinate.model.TemplateModel templateModel =
            (org.wheatgenetics.coordinate.model.TemplateModel) model;

        assert null != templateModel;
        contentValues.put(org.wheatgenetics.coordinate.database.TemplatesTable.TITLE_FIELD_NAME,
            templateModel.getTitle());
        contentValues.put(org.wheatgenetics.coordinate.database.TemplatesTable.TYPE_FIELD_NAME,
            templateModel.getType().getCode());
        contentValues.put(org.wheatgenetics.coordinate.database.TemplatesTable.COLS_FIELD_NAME,
            templateModel.getCols());
        contentValues.put(org.wheatgenetics.coordinate.database.TemplatesTable.ROWS_FIELD_NAME,
            templateModel.getRows());

        contentValues.put(org.wheatgenetics.coordinate.database.TemplatesTable.ECELLS_FIELD_NAME,
            templateModel.getExcludeCellsAsJson());                 // throws org.json.JSONException
        contentValues.put(org.wheatgenetics.coordinate.database.TemplatesTable.ECOLS_FIELD_NAME,
            templateModel.getExcludeColsAsJson());
        contentValues.put(org.wheatgenetics.coordinate.database.TemplatesTable.EROWS_FIELD_NAME,
            templateModel.getExcludeRowsAsJson());

        contentValues.put(org.wheatgenetics.coordinate.database.TemplatesTable.CNUMB_FIELD_NAME,
            templateModel.getColNumbering());
        contentValues.put(org.wheatgenetics.coordinate.database.TemplatesTable.RNUMB_FIELD_NAME,
            templateModel.getRowNumbering());

        contentValues.put(org.wheatgenetics.coordinate.database.TemplatesTable.OPTIONS_FIELD_NAME,
            templateModel.getOptionalFields().toJson());

        contentValues.put(org.wheatgenetics.coordinate.database.TemplatesTable.STAMP_FIELD_NAME,
            templateModel.getTimestamp());

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

    // region Operations
    private android.database.Cursor query(
    final org.wheatgenetics.coordinate.model.TemplateType templateType)
    {
        assert null != templateType;
        return this.queryDistinct(/* selection => */
            org.wheatgenetics.coordinate.database.TemplatesTable.TYPE_FIELD_NAME + "=" +
                templateType.getCode());
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

    public boolean exists(final org.wheatgenetics.coordinate.model.TemplateType templateType)
    {
        final android.database.Cursor cursor = this.query(templateType);
        if (null == cursor)
            return false;
        else
            try     { return cursor.getCount() > 0; }
            finally { cursor.close();               }
    }

    public org.wheatgenetics.coordinate.model.TemplateModel get(
    final org.wheatgenetics.coordinate.model.TemplateType templateType)
    {
        final org.wheatgenetics.coordinate.model.TemplateModel result =
            (org.wheatgenetics.coordinate.model.TemplateModel)
                this.makeFromFirst(this.query(templateType));

        if (null != result)
            if (result.getType() == org.wheatgenetics.coordinate.model.TemplateType.DNA)
                result.makeOneRandomCell();

        return result;
    }

    public org.wheatgenetics.coordinate.model.TemplateModels load()     // TODO: Push to superclass?
    {
        this.sendInfoLogMsg(
            "Loading table " + org.wheatgenetics.coordinate.database.TemplatesTable.TABLE_NAME);

        org.wheatgenetics.coordinate.model.TemplateModels templateModels;
        {
            final android.database.Cursor cursor = this.orderByQueryAll(/* orderBy => */
                org.wheatgenetics.coordinate.database.TemplatesTable.TYPE_FIELD_NAME + " ASC");
            if (null == cursor)
                templateModels = null;
            else
            {
                if (cursor.getCount() <= 0)
                    templateModels = null;
                else
                {
                    templateModels = new org.wheatgenetics.coordinate.model.TemplateModels();
                    while (cursor.moveToNext()) templateModels.add(this.make(cursor));
                }
                cursor.close();
            }
        }
        return templateModels;
    }

    public android.database.Cursor loadByOrder()                        // TODO: Push to superclass?
    {
        this.sendInfoLogMsg(
            "Loading table " + org.wheatgenetics.coordinate.database.TemplatesTable.TABLE_NAME);
        return this.orderByQueryAll(/* orderBy => */ "_id DESC");
    }
    // endregion
    // endregion
}