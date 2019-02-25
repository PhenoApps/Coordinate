package org.wheatgenetics.coordinate.database;

/**
 * Uses:
 * android.content.ContentValues
 * android.content.Context
 * android.database.Cursor
 * android.support.annotation.NonNull
 * android.support.annotation.Nullable
 *
 * org.wheatgenetics.javalib.Utils
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
    static final java.lang.String TABLE_NAME = "templates";

    static final java.lang.String TITLE_FIELD_NAME = "title", TYPE_FIELD_NAME   = "type"  ;
    static final java.lang.String ROWS_FIELD_NAME  = "rows" , COLS_FIELD_NAME   = "cols"  ;
    static final java.lang.String ERAND_FIELD_NAME = "erand", ECELLS_FIELD_NAME = "ecells",
        EROWS_FIELD_NAME = "erows", ECOLS_FIELD_NAME = "ecols";
    static final java.lang.String CNUMB_FIELD_NAME   = "cnumb"  , RNUMB_FIELD_NAME = "rnumb";
    static final java.lang.String ENTRY_LABEL_FIELD_NAME = "entryLabel";
    static final java.lang.String OPTIONS_FIELD_NAME     = "options";
    static final java.lang.String STAMP_FIELD_NAME       = "stamp"  ;
    // endregion

    // region Private Methods
    private android.database.Cursor query(@android.support.annotation.NonNull
    final org.wheatgenetics.coordinate.model.TemplateType templateType)
    {
        return this.queryDistinct(/* selection => */
            org.wheatgenetics.coordinate.database.TemplatesTable.TYPE_FIELD_NAME + " = " +
                templateType.getCode());
    }

    @android.support.annotation.Nullable private org.wheatgenetics.coordinate.model.TemplateModels
    makeTemplateModels(@android.support.annotation.Nullable final android.database.Cursor cursor)
    {
        final org.wheatgenetics.coordinate.model.TemplateModels result;
        if (null == cursor)
            result = null;
        else
            try
            {
                if (cursor.getCount() <= 0)
                    result = null;
                else
                {
                    result = new org.wheatgenetics.coordinate.model.TemplateModels();
                    while (cursor.moveToNext()) result.add(
                        (org.wheatgenetics.coordinate.model.TemplateModel) this.make(cursor));
                }
            }
            finally { cursor.close(); }
        return result;
    }
    // endregion

    public TemplatesTable(final android.content.Context context)
    {
        super(
            /* context   => */ context                                                        ,
            /* tableName => */ org.wheatgenetics.coordinate.database.TemplatesTable.TABLE_NAME,
            /* tag       => */"TemplatesTable");
    }

    // region Overridden Methods
    @java.lang.Override org.wheatgenetics.coordinate.model.Model make(
    final android.database.Cursor cursor)
    {
        return null == cursor ? null : new org.wheatgenetics.coordinate.model.TemplateModel(
            /* id => */ cursor.getLong(cursor.getColumnIndex(
                org.wheatgenetics.coordinate.database.Table.ID_FIELD_NAME)),
            /* title => */ cursor.getString(cursor.getColumnIndex(
                org.wheatgenetics.coordinate.database.TemplatesTable.TITLE_FIELD_NAME)),
            /* code => */ cursor.getInt(cursor.getColumnIndex(
                org.wheatgenetics.coordinate.database.TemplatesTable.TYPE_FIELD_NAME)),
            /* rows => */ cursor.getInt(cursor.getColumnIndex(
                org.wheatgenetics.coordinate.database.TemplatesTable.ROWS_FIELD_NAME)),
            /* cols => */ cursor.getInt(cursor.getColumnIndex(
                org.wheatgenetics.coordinate.database.TemplatesTable.COLS_FIELD_NAME)),
            /* generatedExcludedCellsAmount => */ cursor.getInt(cursor.getColumnIndex(
                org.wheatgenetics.coordinate.database.TemplatesTable.ERAND_FIELD_NAME)),
            /* excludeCells => */ cursor.getString(cursor.getColumnIndex(
                org.wheatgenetics.coordinate.database.TemplatesTable.ECELLS_FIELD_NAME)),
            /* excludeRows => */ cursor.getString(cursor.getColumnIndex(
                org.wheatgenetics.coordinate.database.TemplatesTable.EROWS_FIELD_NAME)),
            /* excludeCols => */ cursor.getString(cursor.getColumnIndex(
                org.wheatgenetics.coordinate.database.TemplatesTable.ECOLS_FIELD_NAME)),
            /* colNumbering => */ cursor.getInt(cursor.getColumnIndex(
                org.wheatgenetics.coordinate.database.TemplatesTable.CNUMB_FIELD_NAME)),
            /* rowNumbering => */ cursor.getInt(cursor.getColumnIndex(
                org.wheatgenetics.coordinate.database.TemplatesTable.RNUMB_FIELD_NAME)),
            /* entryLabel => */ cursor.getString(cursor.getColumnIndex(
                org.wheatgenetics.coordinate.database.TemplatesTable.ENTRY_LABEL_FIELD_NAME)),
            /* optionalFields => */ cursor.getString(cursor.getColumnIndex(
                org.wheatgenetics.coordinate.database.TemplatesTable.OPTIONS_FIELD_NAME)),
            /* timestamp => */ cursor.getLong(cursor.getColumnIndex(
                org.wheatgenetics.coordinate.database.TemplatesTable.STAMP_FIELD_NAME)));
    }

    @java.lang.Override @android.support.annotation.NonNull
    android.content.ContentValues getContentValuesForInsert(
    @android.support.annotation.NonNull final org.wheatgenetics.coordinate.model.Model model)
    {
        final android.content.ContentValues result = super.getContentValuesForInsert(model);
        {
            final org.wheatgenetics.coordinate.model.TemplateModel templateModel =
                (org.wheatgenetics.coordinate.model.TemplateModel) model;

            result.put(org.wheatgenetics.coordinate.database.TemplatesTable.TITLE_FIELD_NAME,
                templateModel.getTitle());
            result.put(org.wheatgenetics.coordinate.database.TemplatesTable.TYPE_FIELD_NAME,
                templateModel.getType().getCode());
            result.put(org.wheatgenetics.coordinate.database.TemplatesTable.ROWS_FIELD_NAME,
                templateModel.getRows());
            result.put(org.wheatgenetics.coordinate.database.TemplatesTable.COLS_FIELD_NAME,
                templateModel.getCols());

            result.put(org.wheatgenetics.coordinate.database.TemplatesTable.ERAND_FIELD_NAME,
                templateModel.getGeneratedExcludedCellsAmount());
            result.put(org.wheatgenetics.coordinate.database.TemplatesTable.ECELLS_FIELD_NAME,
                templateModel.getExcludedCellsAsJson());
            result.put(org.wheatgenetics.coordinate.database.TemplatesTable.EROWS_FIELD_NAME,
                templateModel.getExcludedRowsAsJson());
            result.put(org.wheatgenetics.coordinate.database.TemplatesTable.ECOLS_FIELD_NAME,
                templateModel.getExcludedColsAsJson());

            result.put(org.wheatgenetics.coordinate.database.TemplatesTable.CNUMB_FIELD_NAME,
                templateModel.getColNumbering());
            result.put(org.wheatgenetics.coordinate.database.TemplatesTable.RNUMB_FIELD_NAME,
                templateModel.getRowNumbering());

            result.put(org.wheatgenetics.coordinate.database.TemplatesTable.ENTRY_LABEL_FIELD_NAME,
                templateModel.getEntryLabel());

            result.put(org.wheatgenetics.coordinate.database.TemplatesTable.OPTIONS_FIELD_NAME,
                templateModel.optionalFieldsAsJson());

            result.put(org.wheatgenetics.coordinate.database.TemplatesTable.STAMP_FIELD_NAME,
                templateModel.getTimestamp());
        }
        return result;
    }
    // endregion

    // region Operations
    public boolean exists(@android.support.annotation.NonNull
    final org.wheatgenetics.coordinate.model.TemplateType templateType)
    { return org.wheatgenetics.coordinate.database.Table.exists(this.query(templateType)); }

    @android.support.annotation.Nullable
    public org.wheatgenetics.coordinate.model.TemplateModel get(final long id)
    {
        return (org.wheatgenetics.coordinate.model.TemplateModel) this.makeFromFirst(this.queryAll(
            /* selection     => */ org.wheatgenetics.coordinate.database.Table.whereClause(),
            /* selectionArgs => */ org.wheatgenetics.javalib.Utils.stringArray(id)          ));
    }

    @android.support.annotation.Nullable
    public org.wheatgenetics.coordinate.model.TemplateModel get(@android.support.annotation.NonNull
    final org.wheatgenetics.coordinate.model.TemplateType templateType)
    {
        return (org.wheatgenetics.coordinate.model.TemplateModel)
            this.makeFromFirst(this.query(templateType));
    }

    @android.support.annotation.Nullable
    public org.wheatgenetics.coordinate.model.TemplateModels load()
    {
        return this.makeTemplateModels(this.queryAll(/* orderBy => */
            org.wheatgenetics.coordinate.database.TemplatesTable.TYPE_FIELD_NAME + " ASC"));
    }

    @android.support.annotation.Nullable
    public org.wheatgenetics.coordinate.model.TemplateModels loadUserDefined()
    {
        return this.makeTemplateModels(this.query(
            org.wheatgenetics.coordinate.model.TemplateType.USERDEFINED));
    }
    // endregion
}