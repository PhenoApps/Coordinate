package org.wheatgenetics.coordinate.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

import org.wheatgenetics.coordinate.StringGetter;
import org.wheatgenetics.coordinate.model.EntryModel;
import org.wheatgenetics.coordinate.model.EntryModels;
import org.wheatgenetics.coordinate.model.ExcludedEntryModel;
import org.wheatgenetics.coordinate.model.IncludedEntryModel;
import org.wheatgenetics.coordinate.model.Model;
import org.wheatgenetics.coordinate.Utils;

public class EntriesTable extends Table
        implements EntryModels.Processor {
    // region Constants
    static final String TABLE_NAME = "entries";

    static final String GRID_FIELD_NAME = "grid", EDATA_FIELD_NAME = "edata";
    private static final String ROW_FIELD_NAME = "row", COL_FIELD_NAME = "col",
            STAMP_FIELD_NAME = "stamp";
    // endregion

    // region Constructors
    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    EntriesTable(final Context context,
                 @NonNull final String tag) {
        super(
                /* context   => */ context,
                /* tableName => */ EntriesTable.TABLE_NAME,
                /* tag       => */ tag);
    }

    public EntriesTable(final Context context) {
        this(/* context => */ context, /* tag => */"EntriesTable");
    }
    // endregion

    public boolean deleteAll() {
        return super.deleteAll();
    }

    // region Package Methods
    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    IncludedEntryModel makeIncludedEntryModel(final long id,
                                              final long gridId, final int row, final int col, final String value,
                                              final long timestamp) {
        return new IncludedEntryModel(
                /* id           => */ id,
                /* gridId       => */ gridId,
                /* row          => */ row,
                /* col          => */ col,
                /* value        => */ value,
                /* timestamp    => */ timestamp,
                /* stringGetter => */this);
    }

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    EntryModels makeEntryModels(
            final long gridId, final int rows, final int cols) {
        return new EntryModels(gridId, rows, cols, this);
    }

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    boolean setEntryModel(
            @NonNull final EntryModels entryModels,
            @NonNull final EntryModel entryModel) {
        entryModels.set(entryModel);
        return false;
    }
    // endregion

    // region Overridden Methods
    @Override
    Model make(final Cursor cursor) {
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

                private boolean excluded() {
                    final String value = this.value();
                    return null != value && value.equals(
                            ExcludedEntryModel.DATABASE_VALUE);
                }

                // region get() Methods
                private long id() {
                    return this.getLong(this.getColumnIndex(
                            EntriesTable.ID_FIELD_NAME));
                }

                private long gridId() {
                    return this.getLong(this.getColumnIndex(
                            EntriesTable.GRID_FIELD_NAME));
                }

                private int row() {
                    return this.getInt(this.getColumnIndex(
                            EntriesTable.ROW_FIELD_NAME));
                }

                private int col() {
                    return this.getInt(this.getColumnIndex(
                            EntriesTable.COL_FIELD_NAME));
                }

                private String value() {
                    return this.getString(this.getColumnIndex(
                            EntriesTable.EDATA_FIELD_NAME));
                }
                // endregion

                private long timestamp() {
                    return this.getLong(this.getColumnIndex(
                            EntriesTable.STAMP_FIELD_NAME));
                }

                private EntryModel make() {
                    if (this.excluded())
                        return new ExcludedEntryModel(
                                /* id           => */ this.id(),
                                /* gridId       => */ this.gridId(),
                                /* row          => */ this.row(),
                                /* col          => */ this.col(),
                                /* timestamp    => */ this.timestamp(),
                                /* stringGetter => */ this.stringGetter);
                    else return EntriesTable.this.makeIncludedEntryModel(
                            /* id        => */ this.id(),
                            /* gridId    => */ this.gridId(),
                            /* row       => */ this.row(),
                            /* col       => */ this.col(),
                            /* value     => */ this.value(),
                            /* timestamp => */ this.timestamp());
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
            final EntryModel entryModel =
                    (EntryModel) model;

            result.put(EntriesTable.GRID_FIELD_NAME,
                    entryModel.getGridId());
            result.put(EntriesTable.ROW_FIELD_NAME,
                    entryModel.getRow());
            result.put(EntriesTable.COL_FIELD_NAME,
                    entryModel.getCol());
            result.put(EntriesTable.EDATA_FIELD_NAME,
                    entryModel.getDatabaseValue() /* polymorphism */);
            result.put(EntriesTable.STAMP_FIELD_NAME,
                    entryModel.getTimestamp());
        }
        return result;
    }

    // region org.wheatgenetics.coordinate.model.EntryModels.Processor Overridden Method
    @Override
    public void process(final EntryModel entryModel) {
        this.insertOrUpdate(entryModel);
    }
    // endregion
    // endregion

    // region Operations
    @Nullable
    EntryModels load(
            final long gridId, final int rows, final int cols) {
        final EntryModels result;
        {
            final Cursor cursor = this.queryAll(
                    /* selection => */
                    EntriesTable.GRID_FIELD_NAME +
                            " = ?",
                    /* selectionArgs => */ Utils.stringArray(gridId),
                    /* orderBy       => */
                    EntriesTable.ROW_FIELD_NAME
                            + " ASC, " +
                            EntriesTable.COL_FIELD_NAME + " ASC");
            if (null == cursor)
                result = null;
            else
                try {
                    if (cursor.getCount() <= 0)
                        result = null;
                    else {
                        result = this.makeEntryModels(gridId, rows, cols);
                        while (cursor.moveToNext()) {
                            final EntryModel entryModel =
                                    (EntryModel) this.make(cursor);
                            if (null == entryModel)
                                return null;
                            else if (this.setEntryModel(result, entryModel)) return null;
                        }
                    }
                } finally {
                    cursor.close();
                }
        }
        return result;
    }

    // region Public Operations
    public void insert(final EntryModels entryModels) {
        if (null != entryModels) entryModels.processAll(this);
    }

    public void insertOrUpdate(final EntryModel entryModel) {
        if (null != entryModel) {
            final boolean exists = EntriesTable.exists(
                    this.queryAll(
                            /* selection => */
                            EntriesTable.whereClause(),
                            /* selectionArgs => */
                            Utils.stringArray(entryModel.getId())));
            if (exists) this.update(entryModel);
            else this.insert(entryModel);
        }
    }

    public boolean deleteByGridId(final long gridId) {
        return this.deleteUsingWhereClause(/* whereClause => */
                EntriesTable.GRID_FIELD_NAME + " = " + gridId);
    }
    // endregion
    // endregion
}