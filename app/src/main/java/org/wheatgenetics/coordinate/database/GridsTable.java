package org.wheatgenetics.coordinate.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

import org.wheatgenetics.coordinate.model.BaseJoinedGridModels;
import org.wheatgenetics.coordinate.model.EntryModels;
import org.wheatgenetics.coordinate.model.GridModel;
import org.wheatgenetics.coordinate.model.JoinedGridModel;
import org.wheatgenetics.coordinate.model.JoinedGridModels;
import org.wheatgenetics.coordinate.model.Model;
import org.wheatgenetics.javalib.Utils;

public class GridsTable extends Table {
    // region Constants
    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    static final
    String TABLE_NAME = "grids";
    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    static final
    String PROJECTID_FIELD_NAME = "projectId";
    private static final String TEMP_FIELD_NAME = "temp";
    private static final String PERSON_FIELD_NAME = "person",
            ACTIVEROW_FIELD_NAME = "activeRow", ACTIVECOL_FIELD_NAME = "activeCol",
            OPTIONS_FIELD_NAME = "options", STAMP_FIELD_NAME = "stamp";
    private static final String TEMPLATEOPTIONS_FIELD_NAME = "templateOptions",
            TEMPLATESTAMP_FIELD_NAME = "templateStamp";
    // endregion

    // region Fields
    private static String idFieldName, joinedQuery;
    private EntriesTable
            entriesTableInstance = null;                                                    // lazy load
    // endregion

    // region Constructors
    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    GridsTable(
            final Context context, @NonNull final String tag) {
        super(
                /* context   => */ context,
                /* tableName => */ GridsTable.TABLE_NAME,
                /* tag       => */ tag);

        final String
                gridsTableName = GridsTable.TABLE_NAME,
                templatesTableName = TemplatesTable.TABLE_NAME;
        final String
                gridsQualifier = gridsTableName + '.', templatesQualifier = templatesTableName + '.';
        GridsTable.idFieldName = gridsQualifier +
                GridsTable.ID_FIELD_NAME;
        final String
                projectIdFieldName = gridsQualifier +
                GridsTable.PROJECTID_FIELD_NAME,
                personFieldName = gridsQualifier +
                        GridsTable.PERSON_FIELD_NAME,
                activeRowFieldName = gridsQualifier +
                        GridsTable.ACTIVEROW_FIELD_NAME,
                activeColFieldName = gridsQualifier +
                        GridsTable.ACTIVECOL_FIELD_NAME,
                optionsFieldName = gridsQualifier +
                        GridsTable.OPTIONS_FIELD_NAME,
                stampFieldName = gridsQualifier +
                        GridsTable.STAMP_FIELD_NAME,
                tempFieldName = gridsQualifier +
                        GridsTable.TEMP_FIELD_NAME,

                titleFieldName = templatesQualifier +
                        TemplatesTable.TITLE_FIELD_NAME,
                typeFieldName = templatesQualifier +
                        TemplatesTable.TYPE_FIELD_NAME,
                rowsFieldName = templatesQualifier +
                        TemplatesTable.ROWS_FIELD_NAME,
                colsFieldName = templatesQualifier +
                        TemplatesTable.COLS_FIELD_NAME,
                erandFieldName = templatesQualifier +
                        TemplatesTable.ERAND_FIELD_NAME,
                ecellsFieldName = templatesQualifier +
                        TemplatesTable.ECELLS_FIELD_NAME,
                erowsFieldName = templatesQualifier +
                        TemplatesTable.EROWS_FIELD_NAME,
                ecolsFieldName = templatesQualifier +
                        TemplatesTable.ECOLS_FIELD_NAME,
                cnumbFieldName = templatesQualifier +
                        TemplatesTable.CNUMB_FIELD_NAME,
                rnumbFieldName = templatesQualifier +
                        TemplatesTable.RNUMB_FIELD_NAME,
                entryLabelFieldName = templatesQualifier +
                        TemplatesTable.ENTRY_LABEL_FIELD_NAME,
                templateOptionsFieldName = templatesQualifier +
                        TemplatesTable.OPTIONS_FIELD_NAME,
                templateStampFieldName = templatesQualifier +
                        TemplatesTable.STAMP_FIELD_NAME,
                templateIdFieldName = templatesQualifier +
                        TemplatesTable.ID_FIELD_NAME;
        GridsTable.joinedQuery = "SELECT ALL " +
                GridsTable.idFieldName + ", " +
                projectIdFieldName + ", " + personFieldName + ", " + activeRowFieldName + ", " +
                activeColFieldName + ", " + optionsFieldName + ", " + stampFieldName + ", " +
                tempFieldName + ", " +

                titleFieldName + ", " + typeFieldName + ", " + rowsFieldName + ", " +
                colsFieldName + ", " + erandFieldName + ", " + ecellsFieldName + ", " +
                erowsFieldName + ", " + ecolsFieldName + ", " + cnumbFieldName + ", " +
                rnumbFieldName + ", " + entryLabelFieldName + ", " +
                templateOptionsFieldName + " AS " +
                GridsTable.TEMPLATEOPTIONS_FIELD_NAME + ", " +
                templateStampFieldName + " AS " +
                GridsTable.TEMPLATESTAMP_FIELD_NAME +
                " FROM " + gridsTableName + ", " + templatesTableName +
                " WHERE " + tempFieldName + " = " + templateIdFieldName;
    }

    public GridsTable(final Context context) {
        this(/* context => */ context, /* tag => */"GridsTable");
    }

    // region Package Methods
    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    EntriesTable makeEntriesTable() {
        return new EntriesTable(this.getContext());
    }
    // endregion

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    BaseJoinedGridModels makeJoinedGridModels() {
        return new JoinedGridModels(this);
    }

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    JoinedGridModel makeJoinedGridModel(
            @IntRange(from = 1) final long id,
            @IntRange(from = 0) final long projectId,
            final String person,
            @IntRange(from = 0) final int activeRow,
            @IntRange(from = 0) final int activeCol,
            @Nullable final String optionalFields,
            @IntRange(from = 0) final long timestamp,

            @IntRange(from = 1) final long templateId,
            final String title,
            @IntRange(from = 0, to = 2) final int code,
            @IntRange(from = 1) final int rows,
            @IntRange(from = 1) final int cols,
            @IntRange(from = 0) final int generatedExcludedCellsAmount,
            @Nullable final String initialExcludedCells,
            @Nullable final String excludedRows,
            @Nullable final String excludedCols,
            @IntRange(from = 0, to = 1) final int colNumbering,
            @IntRange(from = 0, to = 1) final int rowNumbering,
            final String entryLabel,
            @Nullable final String templateOptionalFields,
            @IntRange(from = 0) final long templateTimestamp,

            final EntryModels entryModels) {
        return new JoinedGridModel(id, projectId, person,
                activeRow, activeCol, optionalFields, this, timestamp, templateId, title,
                code, rows, cols, generatedExcludedCellsAmount, initialExcludedCells, excludedRows,
                excludedCols, colNumbering, rowNumbering, entryLabel, templateOptionalFields,
                templateTimestamp, entryModels);
    }
    // endregion

    // region Private Methods
    @NonNull
    private EntriesTable entriesTable() {
        if (this.entriesTableInstance == null) this.entriesTableInstance = this.makeEntriesTable();
        return this.entriesTableInstance;
    }

    @Nullable
    private BaseJoinedGridModels
    makeJoinedGridModels(@Nullable final Cursor cursor) {
        final BaseJoinedGridModels result;
        if (null == cursor)
            result = null;
        else
            try {
                if (cursor.getCount() <= 0)
                    result = null;
                else {
                    result = this.makeJoinedGridModels();
                    while (cursor.moveToNext()) result.add(
                            (JoinedGridModel) this.make(cursor));
                }
            } finally {
                cursor.close();
            }
        return result;
    }
    // endregion

    // region Overridden Methods
    @Override
    Model make(final Cursor cursor) {
        if (null == cursor)
            return null;
        else {
            class CursorWrapper extends android.database.CursorWrapper {
                private CursorWrapper(@NonNull final Cursor cursor) {
                    super(cursor);
                }

                // region get() Methods
                private long gridId() {
                    return this.getLong(this.getColumnIndex(
                            GridsTable.ID_FIELD_NAME));
                }

                private long projectId() {
                    return this.getLong(this.getColumnIndex(
                            GridsTable.PROJECTID_FIELD_NAME));
                }

                private String person() {
                    return this.getString(this.getColumnIndex(
                            GridsTable.PERSON_FIELD_NAME));
                }

                private int activeRow() {
                    return this.getInt(this.getColumnIndex(
                            GridsTable.ACTIVEROW_FIELD_NAME));
                }

                private int activeCol() {
                    return this.getInt(this.getColumnIndex(
                            GridsTable.ACTIVECOL_FIELD_NAME));
                }

                private String optionalFields() {
                    return this.getString(this.getColumnIndex(
                            GridsTable.OPTIONS_FIELD_NAME));
                }

                private long timestamp() {
                    return this.getLong(this.getColumnIndex(
                            GridsTable.STAMP_FIELD_NAME));
                }

                private long templateId() {
                    return this.getLong(this.getColumnIndex(
                            GridsTable.TEMP_FIELD_NAME));
                }

                private String title() {
                    return this.getString(this.getColumnIndex(
                            TemplatesTable.TITLE_FIELD_NAME));
                }

                private int code() {
                    return this.getInt(this.getColumnIndex(
                            TemplatesTable.TYPE_FIELD_NAME));
                }

                private int rows() {
                    return this.getInt(this.getColumnIndex(
                            TemplatesTable.ROWS_FIELD_NAME));
                }

                private int cols() {
                    return this.getInt(this.getColumnIndex(
                            TemplatesTable.COLS_FIELD_NAME));
                }

                private int generatedExcludedCellsAmount() {
                    return this.getInt(this.getColumnIndex(
                            TemplatesTable.ERAND_FIELD_NAME));
                }

                private String initialExcludedCells() {
                    return this.getString(this.getColumnIndex(
                            TemplatesTable.ECELLS_FIELD_NAME));
                }

                private String excludedRows() {
                    return this.getString(this.getColumnIndex(
                            TemplatesTable.EROWS_FIELD_NAME));
                }

                private String excludedCols() {
                    return this.getString(this.getColumnIndex(
                            TemplatesTable.ECOLS_FIELD_NAME));
                }

                private int colNumbering() {
                    return this.getInt(this.getColumnIndex(
                            TemplatesTable.CNUMB_FIELD_NAME));
                }

                private int rowNumbering() {
                    return this.getInt(this.getColumnIndex(
                            TemplatesTable.RNUMB_FIELD_NAME));
                }

                private String entryLabel() {
                    return this.getString(this.getColumnIndex(TemplatesTable.ENTRY_LABEL_FIELD_NAME));
                }

                private String templateOptionalFields() {
                    return this.getString(this.getColumnIndex(GridsTable.TEMPLATEOPTIONS_FIELD_NAME));
                }
                // endregion

                private long templateTimestamp() {
                    return this.getLong(this.getColumnIndex(
                            GridsTable.TEMPLATESTAMP_FIELD_NAME));
                }

                private JoinedGridModel make() {
                    final long gridId = this.gridId();
                    final int rows = this.rows(), cols = this.cols();
                    return
                            GridsTable.this.makeJoinedGridModel(
                                    /* id             => */ gridId,
                                    /* projectId      => */ this.projectId(),
                                    /* person         => */ this.person(),
                                    /* activeRow      => */ this.activeRow(),
                                    /* activeCol      => */ this.activeCol(),
                                    /* optionalFields => */ this.optionalFields(),
                                    /* timestamp      => */ this.timestamp(),

                                    /* templateId                   => */ this.templateId(),
                                    /* title                        => */ this.title(),
                                    /* code                         => */ this.code(),
                                    /* rows                         => */ rows,
                                    /* cols                         => */ cols,
                                    /* generatedExcludedCellsAmount => */
                                    this.generatedExcludedCellsAmount(),
                                    /* initialExcludedCells   => */ this.initialExcludedCells(),
                                    /* excludedRows           => */ this.excludedRows(),
                                    /* excludedCols           => */ this.excludedCols(),
                                    /* colNumbering           => */ this.colNumbering(),
                                    /* rowNumbering           => */ this.rowNumbering(),
                                    /* entryLabel             => */ this.entryLabel(),
                                    /* templateOptionalFields => */ this.templateOptionalFields(),
                                    /* templateTimestamp      => */ this.templateTimestamp(),

                                    /* entryModels => */ GridsTable.this.entriesTable().load(gridId, rows, cols));
                }
            }
            return new CursorWrapper(cursor).make();
        }
    }

    @Override
    @NonNull
    ContentValues getContentValuesForInsert(
            @NonNull final Model model) {
        final ContentValues result = super.getContentValuesForInsert(model);
        {
            final GridModel gridModel =
                    (GridModel) model;

            result.put(GridsTable.TEMP_FIELD_NAME,
                    gridModel.getTemplateId());
            result.put(GridsTable.PROJECTID_FIELD_NAME,
                    gridModel.getProjectId());
            result.put(GridsTable.PERSON_FIELD_NAME,
                    gridModel.getPerson());
            result.put(GridsTable.ACTIVEROW_FIELD_NAME,
                    gridModel.getActiveRow());
            result.put(GridsTable.ACTIVECOL_FIELD_NAME,
                    gridModel.getActiveCol());
            result.put(GridsTable.OPTIONS_FIELD_NAME,
                    gridModel.optionalFieldsAsJson());
            result.put(GridsTable.STAMP_FIELD_NAME,
                    gridModel.getTimestamp());
        }
        return result;
    }
    // endregion

    // region Operations
    public boolean exists(final long id) {
        return GridsTable.exists(this.queryDistinct(
                /* selection => */ GridsTable.whereClause(id)));
    }

    @Nullable
    public JoinedGridModel get(final long id) {
        return (JoinedGridModel) this.makeFromFirst(
                this.rawQuery(
                        /* sql => */GridsTable.joinedQuery +
                                " AND " + GridsTable.idFieldName + " = ?",
                        /* selectionArgs => */ Utils.stringArray(id)));
    }

    public boolean exists() {
        return GridsTable.exists(this.rawQuery(
                "SELECT ALL * FROM " +
                        GridsTable.TABLE_NAME));
    }

    public boolean existsInProject() {
        return GridsTable.exists(this.rawQuery(
                /* sql => */"SELECT ALL * FROM " +
                        GridsTable.TABLE_NAME + " WHERE " +
                        GridsTable.PROJECTID_FIELD_NAME + " > 0"));
    }

    public boolean existsInTemplate(final long templateId) {
        final BaseJoinedGridModels baseJoinedGridModels =
                this.loadByTemplateId(templateId);
        // noinspection SimplifiableConditionalExpression
        return null == baseJoinedGridModels ? false : baseJoinedGridModels.size() > 0;
    }

    @IntRange(from = 0)
    public int numberInProject(final long projectId) {
        final BaseJoinedGridModels baseJoinedGridModels =
                this.loadByProjectId(projectId);
        return null == baseJoinedGridModels ? 0 : baseJoinedGridModels.size();
    }

    public boolean existsInProject(final long projectId) {
        return this.numberInProject(projectId) > 0;
    }

    public boolean deleteByTemplateId(final long templateId) {
        return this.deleteUsingWhereClause(/* whereClause => */
                GridsTable.TEMP_FIELD_NAME + " = " + templateId);
    }

    public boolean deleteByProjectId(final long projectId) {
        return this.deleteUsingWhereClause(/* whereClause => */
                GridsTable.PROJECTID_FIELD_NAME +
                        " = " + projectId);
    }

    @Nullable
    public BaseJoinedGridModels load() {
        return this.makeJoinedGridModels(this.rawQuery(/* sql => */
                GridsTable.joinedQuery));
    }

    @Nullable
    public BaseJoinedGridModels loadByTemplateId(final long templateId) {
        return this.makeJoinedGridModels(this.rawQuery(
                /* sql => */GridsTable.joinedQuery +
                        " AND " + GridsTable.TEMP_FIELD_NAME + " = ?",
                /* selectionArgs => */ Utils.stringArray(templateId)));
    }

    @Nullable
    public BaseJoinedGridModels loadByProjectId(final long projectId) {
        return this.makeJoinedGridModels(this.rawQuery(
                /* sql => */GridsTable.joinedQuery +
                        " AND " + GridsTable.PROJECTID_FIELD_NAME +
                        " = ?",
                /* selectionArgs => */ Utils.stringArray(projectId)));
    }
    // endregion
}