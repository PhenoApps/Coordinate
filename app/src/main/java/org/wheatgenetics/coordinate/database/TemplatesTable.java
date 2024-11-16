package org.wheatgenetics.coordinate.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.phenoapps.androidlibrary.Utils;
import org.wheatgenetics.coordinate.StringGetter;
import org.wheatgenetics.coordinate.model.Model;
import org.wheatgenetics.coordinate.model.TemplateModel;
import org.wheatgenetics.coordinate.model.TemplateModels;
import org.wheatgenetics.coordinate.model.TemplateType;
import org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields;

public class TemplatesTable extends Table {
    // region Constants
    static final String TABLE_NAME = "templates";

    static final String TITLE_FIELD_NAME = "title", TYPE_FIELD_NAME = "type";
    static final String ROWS_FIELD_NAME = "rows", COLS_FIELD_NAME = "cols";
    static final String ERAND_FIELD_NAME = "erand", ECELLS_FIELD_NAME = "ecells",
            EROWS_FIELD_NAME = "erows", ECOLS_FIELD_NAME = "ecols";
    static final String CNUMB_FIELD_NAME = "cnumb", RNUMB_FIELD_NAME = "rnumb";
    static final String ENTRY_LABEL_FIELD_NAME = "entryLabel";
    static final String OPTIONS_FIELD_NAME = "options";
    static final String STAMP_FIELD_NAME = "stamp";
    // endregion

    public TemplatesTable(final Context context) {
        super(
                /* context   => */ context,
                /* tableName => */ TemplatesTable.TABLE_NAME,
                /* tag       => */"TemplatesTable");
    }

    // region Private Methods
    private Cursor query(@NonNull final TemplateType templateType) {
        return this.queryDistinct(/* selection => */
                TemplatesTable.TYPE_FIELD_NAME + " = " +
                        templateType.getCode());
    }
    // endregion

    // get optional fields for a template
    public NonNullOptionalFields getOptionalFieldsForTemplate(String templateTitle, StringGetter stringGetter) {
        NonNullOptionalFields optionalFields = null;

        Cursor cursor = queryAll(
                TITLE_FIELD_NAME + " = ?",
                Utils.stringArray(templateTitle)
        );

        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    String json = cursor.getString(cursor.getColumnIndexOrThrow(OPTIONS_FIELD_NAME));
                    if (json != null && !json.isEmpty()) {
                        optionalFields = new NonNullOptionalFields(json, stringGetter, true);
                    }
                }
            } finally {
                cursor.close();
            }
        }
        return optionalFields;
    }

    @Nullable
    private TemplateModels
    makeTemplateModels(@Nullable final Cursor cursor) {
        final TemplateModels result;
        if (null == cursor)
            result = null;
        else
            try {
                if (cursor.getCount() <= 0)
                    result = null;
                else {
                    result = new TemplateModels();
                    while (cursor.moveToNext()) result.add(
                            (TemplateModel) this.make(cursor));
                }
            } finally {
                cursor.close();
            }
        return result;
    }

    // region Overridden Methods
    @Override
    Model make(
            final Cursor cursor) {
        if (null == cursor)
            return null;
        else {
            class CursorWrapper extends android.database.CursorWrapper {
                @NonNull
                private final StringGetter
                        stringGetter;

                private CursorWrapper(
                        @NonNull final Cursor cursor,
                        @NonNull final StringGetter
                                stringGetter) {
                    super(cursor);
                    this.stringGetter = stringGetter;
                }

                // region get() Methods
                private long id() {
                    return this.getLong(this.getColumnIndex(
                            TemplatesTable.ID_FIELD_NAME));
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

                private String excludeCells() {
                    return this.getString(this.getColumnIndex(
                            TemplatesTable.ECELLS_FIELD_NAME));
                }

                private String excludeRows() {
                    return this.getString(this.getColumnIndex(
                            TemplatesTable.EROWS_FIELD_NAME));
                }

                private String excludeCols() {
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

                private String optionalFields() {
                    return this.getString(this.getColumnIndex(
                            TemplatesTable.OPTIONS_FIELD_NAME));
                }
                // endregion

                private long timestamp() {
                    return this.getLong(this.getColumnIndex(
                            TemplatesTable.STAMP_FIELD_NAME));
                }

                private TemplateModel make() {
                    return new TemplateModel(
                            /* id                           => */ this.id(),
                            /* title                        => */ this.title(),
                            /* code                         => */ this.code(),
                            /* rows                         => */ this.rows(),
                            /* cols                         => */ this.cols(),
                            /* generatedExcludedCellsAmount => */ this.generatedExcludedCellsAmount(),
                            /* excludeCells                 => */ this.excludeCells(),
                            /* excludeRows                  => */ this.excludeRows(),
                            /* excludeCols                  => */ this.excludeCols(),
                            /* colNumbering                 => */ this.colNumbering(),
                            /* rowNumbering                 => */ this.rowNumbering(),
                            /* entryLabel                   => */ this.entryLabel(),
                            /* optionalFields               => */ this.optionalFields(),
                            /* stringGetter                 => */ this.stringGetter,
                            /* timestamp                    => */ this.timestamp());
                }
            }
            return new CursorWrapper(cursor, this).make();
        }
    }

    @Override
    @NonNull
    ContentValues getContentValuesForInsert(
            @NonNull final Model model) {
        final ContentValues result = super.getContentValuesForInsert(model);
        {
            final TemplateModel templateModel =
                    (TemplateModel) model;

            result.put(TemplatesTable.TITLE_FIELD_NAME,
                    templateModel.getTitle());
            result.put(TemplatesTable.TYPE_FIELD_NAME,
                    templateModel.getType().getCode());
            result.put(TemplatesTable.ROWS_FIELD_NAME,
                    templateModel.getRows());
            result.put(TemplatesTable.COLS_FIELD_NAME,
                    templateModel.getCols());

            result.put(TemplatesTable.ERAND_FIELD_NAME,
                    templateModel.getGeneratedExcludedCellsAmount());
            result.put(TemplatesTable.ECELLS_FIELD_NAME,
                    templateModel.getExcludedCellsAsJson());
            result.put(TemplatesTable.EROWS_FIELD_NAME,
                    templateModel.getExcludedRowsAsJson());
            result.put(TemplatesTable.ECOLS_FIELD_NAME,
                    templateModel.getExcludedColsAsJson());

            result.put(TemplatesTable.CNUMB_FIELD_NAME,
                    templateModel.getColNumbering());
            result.put(TemplatesTable.RNUMB_FIELD_NAME,
                    templateModel.getRowNumbering());

            result.put(TemplatesTable.ENTRY_LABEL_FIELD_NAME,
                    templateModel.getEntryLabel());

            result.put(TemplatesTable.OPTIONS_FIELD_NAME,
                    templateModel.optionalFieldsAsJson());

            result.put(TemplatesTable.STAMP_FIELD_NAME,
                    templateModel.getTimestamp());
        }
        return result;
    }
    // endregion

    // region Operations
    public boolean exists(@NonNull final TemplateType templateType) {
        return TemplatesTable.exists(
                this.query(templateType));
    }

    @Nullable
    public TemplateModel get(final long id) {
        return (TemplateModel) this.makeFromFirst(this.queryAll(
                /* selection => */ TemplatesTable.whereClause(),
                /* selectionArgs => */ Utils.stringArray(id)));
    }

    @Nullable
    public TemplateModel get(
            @NonNull final TemplateType templateType) {
        return (TemplateModel)
                this.makeFromFirst(this.query(templateType));
    }

    @Nullable
    public TemplateModels load() {
        return this.makeTemplateModels(this.queryAll(/* orderBy => */
                TemplatesTable.TYPE_FIELD_NAME + " ASC"));
    }

    @Nullable
    public TemplateModels loadUserDefined() {
        return this.makeTemplateModels(this.query(
                TemplateType.USERDEFINED));
    }

    public boolean deleteUserDefined() {
        boolean failed = false;
        try {
            this.deleteUsingWhereClause("_id > 2");
        } catch (SQLException e) {
            e.printStackTrace();
            failed = true;
        }
        return failed;
    }
    // endregion
}