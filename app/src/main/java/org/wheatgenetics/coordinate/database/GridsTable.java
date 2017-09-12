package org.wheatgenetics.coordinate.database;

/**
 * Uses:
 * android.content.ContentValues
 * android.content.Context
 * android.database.Cursor
 *
 * org.wheatgenetics.javalib.Utils
 *
 * org.wheatgenetics.coordinate.model.GridModel
 * org.wheatgenetics.coordinate.model.JoinedGridModel
 * org.wheatgenetics.coordinate.model.Model
 *
 * org.wheatgenetics.coordinate.database.Table
 * org.wheatgenetics.coordinate.database.TemplatesTable
 */
public class GridsTable extends org.wheatgenetics.coordinate.database.Table
{
    // region Constants
    private static final java.lang.String TABLE_NAME      = "grids";
    private static final java.lang.String TEMP_FIELD_NAME = "temp",
        TITLE_FIELD_NAME = "title", STAMP_FIELD_NAME = "stamp";
    private static final java.lang.String TEMPLATETITLE_FIELD_NAME = "templateTitle",
        TEMPLATETYPE_FIELD_NAME ="templateType";
    // endregion

    private static java.lang.String idFieldName, joinedQuery;

    public GridsTable(final android.content.Context context)
    {
        super(
            /* context   => */ context                                                    ,
            /* tableName => */ org.wheatgenetics.coordinate.database.GridsTable.TABLE_NAME,
            /* tag       => */ "GridsTable"                                               );

        final java.lang.String
            gridsTableName     = org.wheatgenetics.coordinate.database.GridsTable.TABLE_NAME    ,
            templatesTableName = org.wheatgenetics.coordinate.database.TemplatesTable.TABLE_NAME;
        final java.lang.String
            gridsQualifier = gridsTableName + '.', templatesQualifier = templatesTableName + '.';
        org.wheatgenetics.coordinate.database.GridsTable.idFieldName = gridsQualifier +
            org.wheatgenetics.coordinate.database.Table.ID_FIELD_NAME;
        final java.lang.String
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
            cnumbFieldName = templatesQualifier +
                org.wheatgenetics.coordinate.database.TemplatesTable.CNUMB_FIELD_NAME,
            rnumbFieldName = templatesQualifier +
                org.wheatgenetics.coordinate.database.TemplatesTable.RNUMB_FIELD_NAME,
            optionsFieldName = templatesQualifier +
                org.wheatgenetics.coordinate.database.TemplatesTable.OPTIONS_FIELD_NAME,
            templateIdFieldName = templatesQualifier +
                org.wheatgenetics.coordinate.database.Table.ID_FIELD_NAME;
        org.wheatgenetics.coordinate.database.GridsTable.joinedQuery = "SELECT ALL " +
            org.wheatgenetics.coordinate.database.GridsTable.idFieldName + ", "      +
            titleFieldName + ", " + stampFieldName + ", " + tempFieldName            +
            ", " + templateTitleFieldName + " AS " +
                org.wheatgenetics.coordinate.database.GridsTable.TEMPLATETITLE_FIELD_NAME +
            ", " + templateTypeFieldName + " AS " +
                org.wheatgenetics.coordinate.database.GridsTable.TEMPLATETYPE_FIELD_NAME +
            ", " + rowsFieldName + ", " + colsFieldName + ", " + cnumbFieldName + ", " +
            rnumbFieldName + ", " + optionsFieldName +
                " FROM " + gridsTableName + ", " + templatesTableName +
                " WHERE " + tempFieldName + " = " + templateIdFieldName;
    }

    // region Overridden Methods
    @java.lang.Override
    org.wheatgenetics.coordinate.model.Model make(final android.database.Cursor cursor)
    {
        return null == cursor ? null : new org.wheatgenetics.coordinate.model.JoinedGridModel(
            /* id => */ cursor.getLong(cursor.getColumnIndex(
                org.wheatgenetics.coordinate.database.Table.ID_FIELD_NAME)),
            /* title => */ cursor.getString(cursor.getColumnIndex(
                org.wheatgenetics.coordinate.database.GridsTable.TITLE_FIELD_NAME)),
            /* timestamp => */ cursor.getLong(cursor.getColumnIndex(
                org.wheatgenetics.coordinate.database.GridsTable.STAMP_FIELD_NAME)),
            /* templateId => */ cursor.getLong(cursor.getColumnIndex(
                org.wheatgenetics.coordinate.database.GridsTable.TEMP_FIELD_NAME)),
            /* templateTitle => */ cursor.getString(cursor.getColumnIndex(
                org.wheatgenetics.coordinate.database.GridsTable.TEMPLATETITLE_FIELD_NAME)),
            /* code  => */ cursor.getInt(cursor.getColumnIndex(
                org.wheatgenetics.coordinate.database.GridsTable.TEMPLATETYPE_FIELD_NAME)),
            /* rows  => */ cursor.getInt(cursor.getColumnIndex(
                org.wheatgenetics.coordinate.database.TemplatesTable.ROWS_FIELD_NAME)),
            /* cols  => */ cursor.getInt(cursor.getColumnIndex(
                org.wheatgenetics.coordinate.database.TemplatesTable.COLS_FIELD_NAME)),
            /* colNumbering => */ cursor.getInt(cursor.getColumnIndex(
                org.wheatgenetics.coordinate.database.TemplatesTable.CNUMB_FIELD_NAME)),
            /* rowNumbering => */ cursor.getInt(cursor.getColumnIndex(
                org.wheatgenetics.coordinate.database.TemplatesTable.RNUMB_FIELD_NAME)),
            /* optionalFields => */ cursor.getString(cursor.getColumnIndex(
                org.wheatgenetics.coordinate.database.TemplatesTable.OPTIONS_FIELD_NAME)));
    }

    @java.lang.Override
    android.content.ContentValues getContentValuesForInsert(
    final org.wheatgenetics.coordinate.model.Model model)
    {
        final android.content.ContentValues result = super.getContentValuesForInsert(model);
        {
            final org.wheatgenetics.coordinate.model.GridModel gridModel =
                (org.wheatgenetics.coordinate.model.GridModel) model;

            assert null != gridModel;
            result.put(org.wheatgenetics.coordinate.database.GridsTable.TEMP_FIELD_NAME,
                gridModel.getTemp());
            result.put(org.wheatgenetics.coordinate.database.GridsTable.TITLE_FIELD_NAME,
                gridModel.getTitle());
            result.put(org.wheatgenetics.coordinate.database.GridsTable.STAMP_FIELD_NAME,
                gridModel.getTimestamp());
        }
        return result;
    }
    // endregion

    // region Operations
    public org.wheatgenetics.coordinate.model.JoinedGridModel get(final long id)
    {
        return (org.wheatgenetics.coordinate.model.JoinedGridModel) this.makeFromFirst(
            this.rawQuery(
                /* sql => */ org.wheatgenetics.coordinate.database.GridsTable.joinedQuery +
                    " AND " + org.wheatgenetics.coordinate.database.GridsTable.idFieldName + " = ?",
                /* selectionArgs => */ org.wheatgenetics.javalib.Utils.stringArray(id)));
    }

    public boolean deleteByTemp(final long temp)
    {
        return this.deleteUsingWhereClause(/* whereClause => */
            org.wheatgenetics.coordinate.database.GridsTable.TEMP_FIELD_NAME + " = " + temp);
    }

    public org.wheatgenetics.coordinate.model.GridModels load()
    {
        org.wheatgenetics.coordinate.model.GridModels result;
        {
            final android.database.Cursor cursor = this.rawQuery(/* sql => */
                org.wheatgenetics.coordinate.database.GridsTable.joinedQuery);
            if (null == cursor)
                result = null;
            else
            {
                if (cursor.getCount() <= 0)
                    result = null;
                else
                {
                    result = new org.wheatgenetics.coordinate.model.GridModels();
                    while (cursor.moveToNext()) result.add(
                        (org.wheatgenetics.coordinate.model.JoinedGridModel) this.make(cursor));
                }
                cursor.close();
            }
        }
        return result;
    }
    // endregion
}