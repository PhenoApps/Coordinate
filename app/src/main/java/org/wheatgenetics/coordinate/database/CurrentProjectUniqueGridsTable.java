package org.wheatgenetics.coordinate.database;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

import org.wheatgenetics.coordinate.model.BaseJoinedGridModels;
import org.wheatgenetics.coordinate.model.CurrentProjectUniqueEntryModels;
import org.wheatgenetics.coordinate.model.CurrentProjectUniqueJoinedGridModel;
import org.wheatgenetics.coordinate.model.CurrentProjectUniqueJoinedGridModels;
import org.wheatgenetics.coordinate.model.DatabaseUniqueEntryModels;
import org.wheatgenetics.coordinate.model.EntryModels;
import org.wheatgenetics.coordinate.model.JoinedGridModel;
import org.wheatgenetics.coordinate.model.Model;

public class CurrentProjectUniqueGridsTable extends GridsTable
        implements DatabaseUniqueEntryModels.Checker {
    // region Constants
    private static final String GRIDS_TABLE =
            '[' + CurrentProjectUniqueGridsTable.TABLE_NAME + ']';
    private static final String GRIDS_TABLE_PROJECTID_FIELD =
            CurrentProjectUniqueGridsTable.GRIDS_TABLE +
                    ".[" + GridsTable.PROJECTID_FIELD_NAME + ']';
    // endregion

    public CurrentProjectUniqueGridsTable(final Context context) {
        super(context, "CurrentProjectUniqueGridsTable");
    }

    // region Private Methods
    private static void log(final String msg) {
        Log.d(
                CurrentProjectUniqueGridsTable.class.getName(),
                msg);
    }

    private static String firstPartOfSql() {
        final String format =
                "SELECT ALL %s, %s " +
                        "FROM %s INNER JOIN %s ON %s = %s " +
                        "WHERE %s <> ? AND %s = ?";
        final String
                ENTRIES_TABLE = '[' +
                EntriesTable.TABLE_NAME + ']';
        final String
                GRIDS_TABLE_ID_FIELD =
                CurrentProjectUniqueGridsTable.GRIDS_TABLE +
                        ".[" +
                        CurrentProjectUniqueGridsTable.ID_FIELD_NAME +
                        ']',
                ENTRIES_TABLE_EDATA_FIELD = ENTRIES_TABLE + ".[" +
                        EntriesTable.EDATA_FIELD_NAME + ']',
                ENTRIES_TABLE_GRID_FIELD = ENTRIES_TABLE + ".[" +
                        EntriesTable.GRID_FIELD_NAME + ']';
        return String.format(format, GRIDS_TABLE_ID_FIELD, ENTRIES_TABLE_EDATA_FIELD,
                CurrentProjectUniqueGridsTable.GRIDS_TABLE,
                ENTRIES_TABLE, GRIDS_TABLE_ID_FIELD, ENTRIES_TABLE_GRID_FIELD, GRIDS_TABLE_ID_FIELD,
                ENTRIES_TABLE_EDATA_FIELD);
    }

    private boolean existsOutsideProject(final long id, final String value) {
        final String sql;
        {
            final String GRIDS_TABLE_PROJECTID_FIELD = CurrentProjectUniqueGridsTable.GRIDS_TABLE_PROJECTID_FIELD;
            sql = CurrentProjectUniqueGridsTable
                    .firstPartOfSql() + String.format(" AND (%s IS NULL OR %s <= 0)",
                    GRIDS_TABLE_PROJECTID_FIELD, GRIDS_TABLE_PROJECTID_FIELD);
        }
        return CurrentProjectUniqueGridsTable.exists(
                this.rawQuery(
                        /* sql           => */ sql,
                        /* selectionArgs => */
                        new String[]{String.valueOf(id), value}));
    }
    // endregion

    private boolean existsInsideProject(final long id,
                                        final String value, final long projectId) {
        final Cursor cursor;
        {
            final String sql;
            {
                final String GRIDS_TABLE_PROJECTID_FIELD = CurrentProjectUniqueGridsTable.GRIDS_TABLE_PROJECTID_FIELD;
                sql = CurrentProjectUniqueGridsTable
                        .firstPartOfSql() + String.format(" AND (%s IS NOT NULL AND %s = ?)",
                        GRIDS_TABLE_PROJECTID_FIELD, GRIDS_TABLE_PROJECTID_FIELD);
            }
            cursor = this.rawQuery(
                    /* sql           => */ sql,
                    /* selectionArgs => */ new String[]{
                            String.valueOf(id), value, String.valueOf(projectId)});
        }
        if (null != cursor) if (cursor.moveToFirst()) {
            CurrentProjectUniqueGridsTable.log(
                    "gridId == " + id + ", value == " + value + ", projectId == " + projectId);
            do {
                CurrentProjectUniqueGridsTable.log(
                        "[grids].[_id] == " + cursor.getString(0) +
                                ", [entries].[edata] == " + cursor.getString(1) +
                                ", [grids].[projectId] == " + cursor.getString(2));
            }
            while (cursor.moveToNext());
        }
        return CurrentProjectUniqueGridsTable.exists(cursor);
    }

    // region Overridden Methods
    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    @Override
    EntriesTable makeEntriesTable() {
        return new CurrentProjectUniqueEntriesTable(
                this.getContext(), this);
    }

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    @Override
    BaseJoinedGridModels makeJoinedGridModels() {
        return new CurrentProjectUniqueJoinedGridModels(
                this);
    }

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    @Override
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
        return new CurrentProjectUniqueJoinedGridModel(id,
                projectId, person, activeRow, activeCol, optionalFields, this, timestamp,
                templateId, title, code, rows, cols, generatedExcludedCellsAmount, initialExcludedCells,
                excludedRows, excludedCols, colNumbering, rowNumbering, entryLabel,
                templateOptionalFields, templateTimestamp,
                (CurrentProjectUniqueEntryModels) entryModels,
                this);
    }

    // region org.wheatgenetics.coordinate.model.DatabaseUniqueEntryModels.Checker Overridden Method
    @Override
    @Nullable
    public String check(
            final long gridId,
            @Nullable final String value,
            @NonNull final String scope) throws
            DatabaseUniqueEntryModels.DatabaseDuplicateCheckException {
        final JoinedGridModel joinedGridModel = this.get(gridId);
        if (null == joinedGridModel)
            throw new NullPointerException();
        else if (Model.illegal(joinedGridModel.getProjectId()))
            if (this.existsOutsideProject(gridId, value))
                throw new DatabaseUniqueEntryModels.DatabaseDuplicateCheckException(scope, this);
            else
                return value;
        else if (this.existsInsideProject(gridId, value, joinedGridModel.getProjectId()))
            throw new DatabaseUniqueEntryModels.DatabaseDuplicateCheckException(scope, this);
        else
            return value;
    }
    // endregion
    // endregion
}