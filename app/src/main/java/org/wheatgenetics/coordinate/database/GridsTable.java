package org.wheatgenetics.coordinate.database;

/**
 * Uses:
 * android.content.ContentValues
 * android.content.Context
 * android.database.Cursor
 * android.database.CursorWrapper
 * android.support.annotation.IntRange
 * android.support.annotation.NonNull
 * android.support.annotation.Nullable
 * android.support.annotation.RestrictTo
 * android.support.annotation.RestrictTo.Scope
 *
 * org.wheatgenetics.javalib.Utils
 *
 * org.wheatgenetics.coordinate.model.BaseJoinedGridModels
 * org.wheatgenetics.coordinate.model.EntryModels
 * org.wheatgenetics.coordinate.model.GridModel
 * org.wheatgenetics.coordinate.model.JoinedGridModel
 * org.wheatgenetics.coordinate.model.JoinedGridModels
 * org.wheatgenetics.coordinate.model.Model
 *
 * org.wheatgenetics.coordinate.database.EntriesTable
 * org.wheatgenetics.coordinate.database.Table
 * org.wheatgenetics.coordinate.database.TemplatesTable
 */
public class GridsTable extends org.wheatgenetics.coordinate.database.Table
{
    // region Constants
    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
        static final java.lang.String TABLE_NAME = "grids";

    private static final java.lang.String TEMP_FIELD_NAME = "temp";
    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
        static final java.lang.String PROJECTID_FIELD_NAME = "projectId";
    private static final java.lang.String PERSON_FIELD_NAME = "person",
        ACTIVEROW_FIELD_NAME = "activeRow", ACTIVECOL_FIELD_NAME = "activeCol",
        OPTIONS_FIELD_NAME   = "options"  , STAMP_FIELD_NAME     = "stamp"    ;
    private static final java.lang.String TEMPLATEOPTIONS_FIELD_NAME = "templateOptions",
        TEMPLATESTAMP_FIELD_NAME = "templateStamp";
    // endregion

    // region Fields
    private static java.lang.String idFieldName, joinedQuery;

    private final android.content.Context                            context;
    private       org.wheatgenetics.coordinate.database.EntriesTable
        entriesTableInstance = null;                                                    // lazy load
    // endregion

    // region Package Methods
    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    android.content.Context getContext() { return this.context; }

    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    org.wheatgenetics.coordinate.database.EntriesTable makeEntriesTable()
    { return new org.wheatgenetics.coordinate.database.EntriesTable(this.getContext()); }

    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    org.wheatgenetics.coordinate.model.BaseJoinedGridModels makeJoinedGridModels()
    { return new org.wheatgenetics.coordinate.model.JoinedGridModels(); }

    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    org.wheatgenetics.coordinate.model.JoinedGridModel makeJoinedGridModel(
    @android.support.annotation.IntRange(from = 1) final long             id            ,
    @android.support.annotation.IntRange(from = 0) final long             projectId     ,
                                                   final java.lang.String person        ,
    @android.support.annotation.IntRange(from = 0) final int              activeRow     ,
    @android.support.annotation.IntRange(from = 0) final int              activeCol     ,
    @android.support.annotation.Nullable           final java.lang.String optionalFields,
    @android.support.annotation.IntRange(from = 0) final long             timestamp     ,

    @android.support.annotation.IntRange(from = 1        ) final long             templateId     ,
                                                           final java.lang.String title          ,
    @android.support.annotation.IntRange(from = 0, to = 2) final int              code           ,
    @android.support.annotation.IntRange(from = 1        ) final int              rows           ,
    @android.support.annotation.IntRange(from = 1        ) final int              cols           ,
    @android.support.annotation.IntRange(from = 0        ) final int generatedExcludedCellsAmount,
    @android.support.annotation.Nullable final java.lang.String initialExcludedCells,
    @android.support.annotation.Nullable final java.lang.String excludedRows        ,
    @android.support.annotation.Nullable final java.lang.String excludedCols        ,
    @android.support.annotation.IntRange(from = 0, to = 1) final int      colNumbering          ,
    @android.support.annotation.IntRange(from = 0, to = 1) final int      rowNumbering          ,
                                                   final java.lang.String entryLabel            ,
    @android.support.annotation.Nullable           final java.lang.String templateOptionalFields,
    @android.support.annotation.IntRange(from = 0) final long             templateTimestamp     ,

    final org.wheatgenetics.coordinate.model.EntryModels entryModels)
    {
        return new org.wheatgenetics.coordinate.model.JoinedGridModel(id, projectId, person,
            activeRow, activeCol, optionalFields, timestamp, templateId, title, code, rows, cols,
            generatedExcludedCellsAmount, initialExcludedCells, excludedRows, excludedCols,
            colNumbering, rowNumbering, entryLabel, templateOptionalFields, templateTimestamp,
            entryModels);
    }
    // endregion

    // region Private Methods
    @android.support.annotation.NonNull
    private org.wheatgenetics.coordinate.database.EntriesTable entriesTable()
    {
        if (null == this.entriesTableInstance) this.entriesTableInstance = this.makeEntriesTable();
        return this.entriesTableInstance;
    }

    @android.support.annotation.Nullable
    private org.wheatgenetics.coordinate.model.BaseJoinedGridModels makeJoinedGridModels(
    @android.support.annotation.Nullable final android.database.Cursor cursor)
    {
        final org.wheatgenetics.coordinate.model.BaseJoinedGridModels result;
        if (null == cursor)
            result = null;
        else
            try
            {
                if (cursor.getCount() <= 0)
                    result = null;
                else
                {
                    result = this.makeJoinedGridModels();
                    while (cursor.moveToNext()) result.add(
                        (org.wheatgenetics.coordinate.model.JoinedGridModel) this.make(cursor));
                }
            }
            finally { cursor.close(); }
        return result;
    }
    // endregion

    // region Constructors
    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    GridsTable(final android.content.Context context,
    @android.support.annotation.NonNull final java.lang.String tag)
    {
        super(
            /* context   => */ context                                                    ,
            /* tableName => */ org.wheatgenetics.coordinate.database.GridsTable.TABLE_NAME,
            /* tag       => */ tag                                                        );
        this.context = context;

        final java.lang.String
            gridsTableName     = org.wheatgenetics.coordinate.database.GridsTable.TABLE_NAME    ,
            templatesTableName = org.wheatgenetics.coordinate.database.TemplatesTable.TABLE_NAME;
        final java.lang.String
            gridsQualifier = gridsTableName + '.', templatesQualifier = templatesTableName + '.';
        org.wheatgenetics.coordinate.database.GridsTable.idFieldName = gridsQualifier +
            org.wheatgenetics.coordinate.database.GridsTable.ID_FIELD_NAME;
        final java.lang.String
            projectIdFieldName = gridsQualifier +
                org.wheatgenetics.coordinate.database.GridsTable.PROJECTID_FIELD_NAME,
            personFieldName = gridsQualifier +
                org.wheatgenetics.coordinate.database.GridsTable.PERSON_FIELD_NAME,
            activeRowFieldName = gridsQualifier +
                org.wheatgenetics.coordinate.database.GridsTable.ACTIVEROW_FIELD_NAME,
            activeColFieldName = gridsQualifier +
                org.wheatgenetics.coordinate.database.GridsTable.ACTIVECOL_FIELD_NAME,
            optionsFieldName = gridsQualifier +
                org.wheatgenetics.coordinate.database.GridsTable.OPTIONS_FIELD_NAME,
            stampFieldName = gridsQualifier +
                org.wheatgenetics.coordinate.database.GridsTable.STAMP_FIELD_NAME,
            tempFieldName = gridsQualifier +
                org.wheatgenetics.coordinate.database.GridsTable.TEMP_FIELD_NAME,

            titleFieldName = templatesQualifier +
                org.wheatgenetics.coordinate.database.TemplatesTable.TITLE_FIELD_NAME,
            typeFieldName = templatesQualifier +
                org.wheatgenetics.coordinate.database.TemplatesTable.TYPE_FIELD_NAME,
            rowsFieldName = templatesQualifier +
                org.wheatgenetics.coordinate.database.TemplatesTable.ROWS_FIELD_NAME,
            colsFieldName = templatesQualifier +
                org.wheatgenetics.coordinate.database.TemplatesTable.COLS_FIELD_NAME,
            erandFieldName = templatesQualifier +
                org.wheatgenetics.coordinate.database.TemplatesTable.ERAND_FIELD_NAME,
            ecellsFieldName = templatesQualifier +
                org.wheatgenetics.coordinate.database.TemplatesTable.ECELLS_FIELD_NAME,
            erowsFieldName = templatesQualifier +
                org.wheatgenetics.coordinate.database.TemplatesTable.EROWS_FIELD_NAME,
            ecolsFieldName = templatesQualifier +
                org.wheatgenetics.coordinate.database.TemplatesTable.ECOLS_FIELD_NAME,
            cnumbFieldName = templatesQualifier +
                org.wheatgenetics.coordinate.database.TemplatesTable.CNUMB_FIELD_NAME,
            rnumbFieldName = templatesQualifier +
                org.wheatgenetics.coordinate.database.TemplatesTable.RNUMB_FIELD_NAME,
            entryLabelFieldName = templatesQualifier +
                org.wheatgenetics.coordinate.database.TemplatesTable.ENTRY_LABEL_FIELD_NAME,
            templateOptionsFieldName = templatesQualifier +
                org.wheatgenetics.coordinate.database.TemplatesTable.OPTIONS_FIELD_NAME,
            templateStampFieldName = templatesQualifier +
                org.wheatgenetics.coordinate.database.TemplatesTable.STAMP_FIELD_NAME,
            templateIdFieldName = templatesQualifier +
                org.wheatgenetics.coordinate.database.TemplatesTable.ID_FIELD_NAME;
        org.wheatgenetics.coordinate.database.GridsTable.joinedQuery = "SELECT ALL " +
            org.wheatgenetics.coordinate.database.GridsTable.idFieldName + ", " +
            projectIdFieldName + ", " + personFieldName  + ", " + activeRowFieldName + ", " +
            activeColFieldName + ", " + optionsFieldName + ", " + stampFieldName     + ", " +
            tempFieldName      + ", " +

            titleFieldName + ", " + typeFieldName       + ", " + rowsFieldName   + ", " +
            colsFieldName  + ", " + erandFieldName      + ", " + ecellsFieldName + ", " +
            erowsFieldName + ", " + ecolsFieldName      + ", " + cnumbFieldName  + ", " +
            rnumbFieldName + ", " + entryLabelFieldName + ", " +
            templateOptionsFieldName + " AS " +
                org.wheatgenetics.coordinate.database.GridsTable.TEMPLATEOPTIONS_FIELD_NAME + ", " +
            templateStampFieldName + " AS " +
                org.wheatgenetics.coordinate.database.GridsTable.TEMPLATESTAMP_FIELD_NAME +
                    " FROM "  + gridsTableName + ", " + templatesTableName +
                    " WHERE " + tempFieldName + " = " + templateIdFieldName;
    }

    public GridsTable(final android.content.Context context)
    { this(/* context => */ context, /* tag => */"GridsTable"); }
    // endregion

    // region Overridden Methods
    @java.lang.Override
    org.wheatgenetics.coordinate.model.Model make(final android.database.Cursor cursor)
    {
        if (null == cursor)
            return null;
        else
        {
            class CursorWrapper extends android.database.CursorWrapper
            {
                // region get() Methods
                private long gridId()
                {
                    return this.getLong(this.getColumnIndex(
                        org.wheatgenetics.coordinate.database.GridsTable.ID_FIELD_NAME));
                }

                private long projectId()
                {
                    return this.getLong(this.getColumnIndex(
                        org.wheatgenetics.coordinate.database.GridsTable.PROJECTID_FIELD_NAME));
                }

                private java.lang.String person()
                {
                    return this.getString(this.getColumnIndex(
                        org.wheatgenetics.coordinate.database.GridsTable.PERSON_FIELD_NAME));
                }

                private int activeRow()
                {
                    return this.getInt(this.getColumnIndex(
                        org.wheatgenetics.coordinate.database.GridsTable.ACTIVEROW_FIELD_NAME));
                }

                private int activeCol()
                {
                    return this.getInt(this.getColumnIndex(
                        org.wheatgenetics.coordinate.database.GridsTable.ACTIVECOL_FIELD_NAME));
                }

                private java.lang.String optionalFields()
                {
                    return this.getString(this.getColumnIndex(
                        org.wheatgenetics.coordinate.database.GridsTable.OPTIONS_FIELD_NAME));
                }

                private long timestamp()
                {
                    return this.getLong(this.getColumnIndex(
                        org.wheatgenetics.coordinate.database.GridsTable.STAMP_FIELD_NAME));
                }

                private long templateId()
                {
                    return this.getLong(this.getColumnIndex(
                        org.wheatgenetics.coordinate.database.GridsTable.TEMP_FIELD_NAME));
                }

                private java.lang.String title()
                {
                    return this.getString(this.getColumnIndex(
                        org.wheatgenetics.coordinate.database.TemplatesTable.TITLE_FIELD_NAME));
                }

                private int code()
                {
                    return this.getInt(this.getColumnIndex(
                        org.wheatgenetics.coordinate.database.TemplatesTable.TYPE_FIELD_NAME));
                }

                private int rows()
                {
                    return this.getInt(this.getColumnIndex(
                        org.wheatgenetics.coordinate.database.TemplatesTable.ROWS_FIELD_NAME));
                }

                private int cols()
                {
                    return this.getInt(this.getColumnIndex(
                        org.wheatgenetics.coordinate.database.TemplatesTable.COLS_FIELD_NAME));
                }

                private int generatedExcludedCellsAmount()
                {
                    return this.getInt(this.getColumnIndex(
                        org.wheatgenetics.coordinate.database.TemplatesTable.ERAND_FIELD_NAME));
                }

                private java.lang.String initialExcludedCells()
                {
                    return this.getString(this.getColumnIndex(
                        org.wheatgenetics.coordinate.database.TemplatesTable.ECELLS_FIELD_NAME));
                }

                private java.lang.String excludedRows()
                {
                    return this.getString(this.getColumnIndex(
                        org.wheatgenetics.coordinate.database.TemplatesTable.EROWS_FIELD_NAME));
                }

                private java.lang.String excludedCols()
                {
                    return this.getString(this.getColumnIndex(
                        org.wheatgenetics.coordinate.database.TemplatesTable.ECOLS_FIELD_NAME));
                }

                private int colNumbering()
                {
                    return this.getInt(this.getColumnIndex(
                        org.wheatgenetics.coordinate.database.TemplatesTable.CNUMB_FIELD_NAME));
                }

                private int rowNumbering()
                {
                    return this.getInt(this.getColumnIndex(
                        org.wheatgenetics.coordinate.database.TemplatesTable.RNUMB_FIELD_NAME));
                }

                private java.lang.String entryLabel()
                {
                    return this.getString(this.getColumnIndex(org.wheatgenetics
                        .coordinate.database.TemplatesTable.ENTRY_LABEL_FIELD_NAME));
                }

                private java.lang.String templateOptionalFields()
                {
                    return this.getString(this.getColumnIndex(org.wheatgenetics
                        .coordinate.database.GridsTable.TEMPLATEOPTIONS_FIELD_NAME));
                }

                private long templateTimestamp()
                {
                    return this.getLong(this.getColumnIndex(
                        org.wheatgenetics.coordinate.database.GridsTable.TEMPLATESTAMP_FIELD_NAME));
                }
                // endregion

                private CursorWrapper(
                @android.support.annotation.NonNull final android.database.Cursor cursor)
                { super(cursor); }

                private org.wheatgenetics.coordinate.model.JoinedGridModel make()
                {
                    final long gridId = this.gridId()                  ;
                    final int  rows   = this.rows(), cols = this.cols();
                    return
                        org.wheatgenetics.coordinate.database.GridsTable.this.makeJoinedGridModel(
                            /* id             => */ gridId               ,
                            /* projectId      => */ this.projectId     (),
                            /* person         => */ this.person        (),
                            /* activeRow      => */ this.activeRow     (),
                            /* activeCol      => */ this.activeCol     (),
                            /* optionalFields => */ this.optionalFields(),
                            /* timestamp      => */ this.timestamp     (),

                            /* templateId                   => */ this.templateId(),
                            /* title                        => */ this.title     (),
                            /* code                         => */ this.code      (),
                            /* rows                         => */ rows             ,
                            /* cols                         => */ cols             ,
                            /* generatedExcludedCellsAmount => */
                                this.generatedExcludedCellsAmount(),
                            /* initialExcludedCells   => */ this.initialExcludedCells  (),
                            /* excludedRows           => */ this.excludedRows          (),
                            /* excludedCols           => */ this.excludedCols          (),
                            /* colNumbering           => */ this.colNumbering          (),
                            /* rowNumbering           => */ this.rowNumbering          (),
                            /* entryLabel             => */ this.entryLabel            (),
                            /* templateOptionalFields => */ this.templateOptionalFields(),
                            /* templateTimestamp      => */ this.templateTimestamp     (),

                            /* entryModels => */ org.wheatgenetics.coordinate.database
                                .GridsTable.this.entriesTable().load(gridId, rows, cols));
                }
            }
            return new CursorWrapper(cursor).make();
        }
    }

    @java.lang.Override @android.support.annotation.NonNull
    android.content.ContentValues getContentValuesForInsert(
    @android.support.annotation.NonNull final org.wheatgenetics.coordinate.model.Model model)
    {
        final android.content.ContentValues result = super.getContentValuesForInsert(model);
        {
            final org.wheatgenetics.coordinate.model.GridModel gridModel =
                (org.wheatgenetics.coordinate.model.GridModel) model;

            result.put(org.wheatgenetics.coordinate.database.GridsTable.TEMP_FIELD_NAME,
                gridModel.getTemplateId());
            result.put(org.wheatgenetics.coordinate.database.GridsTable.PROJECTID_FIELD_NAME,
                gridModel.getProjectId());
            result.put(org.wheatgenetics.coordinate.database.GridsTable.PERSON_FIELD_NAME,
                gridModel.getPerson());
            result.put(org.wheatgenetics.coordinate.database.GridsTable.ACTIVEROW_FIELD_NAME,
                gridModel.getActiveRow());
            result.put(org.wheatgenetics.coordinate.database.GridsTable.ACTIVECOL_FIELD_NAME,
                gridModel.getActiveCol());
            result.put(org.wheatgenetics.coordinate.database.GridsTable.OPTIONS_FIELD_NAME,
                gridModel.optionalFieldsAsJson());
            result.put(org.wheatgenetics.coordinate.database.GridsTable.STAMP_FIELD_NAME,
                gridModel.getTimestamp());
        }
        return result;
    }
    // endregion

    // region Operations
    public boolean exists(final long id)
    {
        return org.wheatgenetics.coordinate.database.GridsTable.exists(this.queryDistinct(
            /* selection => */ org.wheatgenetics.coordinate.database.GridsTable.whereClause(id)));
    }

    @android.support.annotation.Nullable
    public org.wheatgenetics.coordinate.model.JoinedGridModel get(final long id)
    {
        return (org.wheatgenetics.coordinate.model.JoinedGridModel) this.makeFromFirst(
            this.rawQuery(
                /* sql => */org.wheatgenetics.coordinate.database.GridsTable.joinedQuery +
                    " AND " + org.wheatgenetics.coordinate.database.GridsTable.idFieldName + " = ?",
                /* selectionArgs => */ org.wheatgenetics.javalib.Utils.stringArray(id)));
    }

    public boolean exists()
    {
        return org.wheatgenetics.coordinate.database.GridsTable.exists(this.rawQuery(
            "SELECT ALL * FROM " +
                org.wheatgenetics.coordinate.database.GridsTable.TABLE_NAME));
    }

    public boolean existsInProject()
    {
        return org.wheatgenetics.coordinate.database.GridsTable.exists(this.rawQuery(
            /* sql => */"SELECT ALL * FROM " +
                org.wheatgenetics.coordinate.database.GridsTable.TABLE_NAME + " WHERE " +
                org.wheatgenetics.coordinate.database.GridsTable.PROJECTID_FIELD_NAME + " > 0"));
    }

    public boolean deleteByTemplateId(final long templateId)
    {
        return this.deleteUsingWhereClause(/* whereClause => */
            org.wheatgenetics.coordinate.database.GridsTable.TEMP_FIELD_NAME + " = " + templateId);
    }

    public boolean deleteByProjectId(final long projectId)
    {
        return this.deleteUsingWhereClause(/* whereClause => */
            org.wheatgenetics.coordinate.database.GridsTable.PROJECTID_FIELD_NAME +
                " = " + projectId);
    }

    @android.support.annotation.Nullable
    public org.wheatgenetics.coordinate.model.BaseJoinedGridModels load()
    {
        return this.makeJoinedGridModels(this.rawQuery(/* sql => */
            org.wheatgenetics.coordinate.database.GridsTable.joinedQuery));
    }

    @android.support.annotation.Nullable public
    org.wheatgenetics.coordinate.model.BaseJoinedGridModels loadByTemplateId(final long templateId)
    {
        return this.makeJoinedGridModels(this.rawQuery(
            /* sql => */org.wheatgenetics.coordinate.database.GridsTable.joinedQuery +
                " AND " + org.wheatgenetics.coordinate.database.GridsTable.TEMP_FIELD_NAME + " = ?",
            /* selectionArgs => */ org.wheatgenetics.javalib.Utils.stringArray(templateId)));
    }

    @android.support.annotation.Nullable public
    org.wheatgenetics.coordinate.model.BaseJoinedGridModels loadByProjectId(final long projectId)
    {
        return this.makeJoinedGridModels(this.rawQuery(
            /* sql => */org.wheatgenetics.coordinate.database.GridsTable.joinedQuery +
                " AND " + org.wheatgenetics.coordinate.database.GridsTable.PROJECTID_FIELD_NAME +
                " = ?",
            /* selectionArgs => */ org.wheatgenetics.javalib.Utils.stringArray(projectId)));
    }
    // endregion
}