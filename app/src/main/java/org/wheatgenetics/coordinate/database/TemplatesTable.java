package org.wheatgenetics.coordinate.database;

/**
 * Uses:
 * android.content.ContentValues
 * android.content.Context
 * android.database.Cursor
 *
 * org.json.JSONException
 *
 * org.wheatgenetics.coordinate.model.Model
 * org.wheatgenetics.coordinate.model.TemplateModel
 * org.wheatgenetics.coordinate.model.TemplateModels
 * org.wheatgenetics.coordinate.model.TemplateType
 *
 * org.wheatgenetics.coordinate.database.Table
 */
public class TemplatesTable extends org.wheatgenetics.coordinate.database.Table
{
    // region Constants
    private static final java.lang.String TABLE_NAME = "templates";

    private static final java.lang.String TITLE_FIELD_NAME = "title", TYPE_FIELD_NAME = "type";
    private static final java.lang.String COLS_FIELD_NAME  = "cols" , ROWS_FIELD_NAME = "rows";
    private static final java.lang.String
        ECELLS_FIELD_NAME = "ecells", ECOLS_FIELD_NAME = "ecols", EROWS_FIELD_NAME = "erows";
    private static final java.lang.String CNUMB_FIELD_NAME   = "cnumb", RNUMB_FIELD_NAME = "rnumb";
    private static final java.lang.String OPTIONS_FIELD_NAME = "options";
    private static final java.lang.String STAMP_FIELD_NAME   = "stamp"  ;
    // endregion

    public TemplatesTable(final android.content.Context context)
    {
        super(
            /* context   => */ context                                                        ,
            /* tableName => */ org.wheatgenetics.coordinate.database.TemplatesTable.TABLE_NAME,
            /* tag       => */ "TemplatesTable"                                               );
    }

    // region Overridden Methods
    @java.lang.Override
    org.wheatgenetics.coordinate.model.Model make(final android.database.Cursor cursor)  // TODO: Make private.
    {
        return null == cursor ? null : new org.wheatgenetics.coordinate.model.TemplateModel(
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
        final android.content.ContentValues result =
            super.getContentValues(model);                          // throws org.json.JSONException

        final org.wheatgenetics.coordinate.model.TemplateModel templateModel =
            (org.wheatgenetics.coordinate.model.TemplateModel) model;

        assert null != templateModel;
        result.put(org.wheatgenetics.coordinate.database.TemplatesTable.TITLE_FIELD_NAME,
            templateModel.getTitle());
        result.put(org.wheatgenetics.coordinate.database.TemplatesTable.TYPE_FIELD_NAME,
            templateModel.getType().getCode());
        result.put(org.wheatgenetics.coordinate.database.TemplatesTable.COLS_FIELD_NAME,
            templateModel.getCols());
        result.put(org.wheatgenetics.coordinate.database.TemplatesTable.ROWS_FIELD_NAME,
            templateModel.getRows());

        result.put(org.wheatgenetics.coordinate.database.TemplatesTable.ECELLS_FIELD_NAME,
            templateModel.getExcludeCellsAsJson());
        result.put(org.wheatgenetics.coordinate.database.TemplatesTable.ECOLS_FIELD_NAME,
            templateModel.getExcludeColsAsJson());
        result.put(org.wheatgenetics.coordinate.database.TemplatesTable.EROWS_FIELD_NAME,
            templateModel.getExcludeRowsAsJson());

        result.put(org.wheatgenetics.coordinate.database.TemplatesTable.CNUMB_FIELD_NAME,
            templateModel.getColNumbering());
        result.put(org.wheatgenetics.coordinate.database.TemplatesTable.RNUMB_FIELD_NAME,
            templateModel.getRowNumbering());

        result.put(org.wheatgenetics.coordinate.database.TemplatesTable.OPTIONS_FIELD_NAME,
            templateModel.getOptionalFields());

        result.put(org.wheatgenetics.coordinate.database.TemplatesTable.STAMP_FIELD_NAME,
            templateModel.getTimestamp());

        return result;
    }
    // endregion

    // region Operations
    private android.database.Cursor query(
    final org.wheatgenetics.coordinate.model.TemplateType templateType)
    {
        assert null != templateType; return this.queryDistinct(/* selection => */
            org.wheatgenetics.coordinate.database.TemplatesTable.TYPE_FIELD_NAME + "=" +
                templateType.getCode());
    }

    private android.database.Cursor query(final long id)                // TODO: Push to superclass?
    {
        return this.queryDistinct(/* selection => */
            org.wheatgenetics.coordinate.database.Table.ID_FIELD_NAME + "=" + id);
    }

    public boolean exists(final org.wheatgenetics.coordinate.model.TemplateType templateType)
    {
        final android.database.Cursor cursor = this.query(templateType);
        if (null == cursor)
            return false;
        else
            try     { return cursor.getCount() > 0; }
            finally { cursor.close()              ; }
    }

    public org.wheatgenetics.coordinate.model.TemplateModel get(
    final org.wheatgenetics.coordinate.model.TemplateType templateType)
    {
        final org.wheatgenetics.coordinate.model.TemplateModel result =
            (org.wheatgenetics.coordinate.model.TemplateModel)
                this.makeFromFirst(this.query(templateType));

        if (null != result)
            if (result.getType() == org.wheatgenetics.coordinate.model.TemplateType.DNA)
                result.makeOneRandomCell();         // TODO: Time to subclass based on TemplateType?

        return result;
    }

    public boolean exists(final long id)                                // TODO: Push to superclass?
    {
        final android.database.Cursor cursor = this.query(id);
        if (null == cursor)
            return false;
        else
            try     { return cursor.getCount() > 0; }
            finally { cursor.close()              ; }
    }

    public org.wheatgenetics.coordinate.model.TemplateModel get(final long id)  // TODO: Push to superclass?
    {
        return (org.wheatgenetics.coordinate.model.TemplateModel)
            this.makeFromFirst(this.query(id));
    }

    public org.wheatgenetics.coordinate.model.TemplateModels load()     // TODO: Push to superclass?
    {
        this.sendInfoLogMsg(
            "Loading table " + org.wheatgenetics.coordinate.database.TemplatesTable.TABLE_NAME);

        org.wheatgenetics.coordinate.model.TemplateModels result;
        {
            final android.database.Cursor cursor = this.orderByQueryAll(/* orderBy => */
                org.wheatgenetics.coordinate.database.TemplatesTable.TYPE_FIELD_NAME + " ASC");
            if (null == cursor)
                result = null;
            else
            {
                if (cursor.getCount() <= 0)
                    result = null;
                else
                {
                    result = new org.wheatgenetics.coordinate.model.TemplateModels();
                    while (cursor.moveToNext()) result.add(
                        (org.wheatgenetics.coordinate.model.TemplateModel) this.make(cursor));
                }
                cursor.close();
            }
        }
        return result;
    }
    // endregion
}