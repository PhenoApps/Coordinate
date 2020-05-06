package org.wheatgenetics.coordinate.database;

import android.content.Context;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

import org.wheatgenetics.coordinate.model.AllGridsUniqueEntryModels;
import org.wheatgenetics.coordinate.model.AllGridsUniqueJoinedGridModel;
import org.wheatgenetics.coordinate.model.AllGridsUniqueJoinedGridModels;
import org.wheatgenetics.coordinate.model.BaseJoinedGridModels;
import org.wheatgenetics.coordinate.model.DatabaseUniqueEntryModels;
import org.wheatgenetics.coordinate.model.EntryModels;
import org.wheatgenetics.coordinate.model.JoinedGridModel;

public class AllGridsUniqueGridsTable extends GridsTable
        implements AllGridsUniqueEntryModels.Checker {
    public AllGridsUniqueGridsTable(final Context context) {
        super(context, "AllGridsUniqueGridsTable");
    }

    private boolean exists(final long id, final String value) {
        final String sql;
        {
            final String
                    GRIDS_TABLE = '[' +
                    AllGridsUniqueGridsTable.TABLE_NAME + ']',
                    ENTRIES_TABLE = '[' +
                            EntriesTable.TABLE_NAME + ']';
            final String
                    GRIDS_TABLE_ID_FIELD = GRIDS_TABLE + ".[" +
                    AllGridsUniqueGridsTable.ID_FIELD_NAME +
                    ']',
                    ENTRIES_TABLE_EDATA_FIELD = ENTRIES_TABLE + ".[" +
                            EntriesTable.EDATA_FIELD_NAME + ']',
                    ENTRIES_TABLE_GRID_FIELD = ENTRIES_TABLE + ".[" +
                            EntriesTable.GRID_FIELD_NAME + ']';
            sql = String.format("SELECT ALL %s, %s " +
                            "FROM %s INNER JOIN %s ON %s = %s " +
                            "WHERE %s <> ? AND %s = ?",
                    GRIDS_TABLE_ID_FIELD, ENTRIES_TABLE_EDATA_FIELD,
                    GRIDS_TABLE, ENTRIES_TABLE, GRIDS_TABLE_ID_FIELD, ENTRIES_TABLE_GRID_FIELD,
                    GRIDS_TABLE_ID_FIELD, ENTRIES_TABLE_EDATA_FIELD);
        }
        return AllGridsUniqueGridsTable.exists(this.rawQuery(
                /* sql           => */ sql,
                /* selectionArgs => */ new String[]{String.valueOf(id), value}));
    }

    // region Overridden Methods
    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    @Override
    EntriesTable makeEntriesTable() {
        return new AllGridsUniqueEntriesTable(
                this.getContext());
    }

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    @Override
    BaseJoinedGridModels makeJoinedGridModels() {
        return new AllGridsUniqueJoinedGridModels(
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
        return new AllGridsUniqueJoinedGridModel(id,
                projectId, person, activeRow, activeCol, optionalFields, this, timestamp,
                templateId, title, code, rows, cols, generatedExcludedCellsAmount, initialExcludedCells,
                excludedRows, excludedCols, colNumbering, rowNumbering, entryLabel,
                templateOptionalFields, templateTimestamp,
                (AllGridsUniqueEntryModels) entryModels,
                this);
    }

    // region org.wheatgenetics.coordinate.model.AllGridsUniqueEntryModels.Checker Overridden Method
    @Override
    @Nullable
    public String check(
            final long gridId,
            @Nullable final String value,
            @NonNull final String scope) throws
            DatabaseUniqueEntryModels.DatabaseDuplicateCheckException {
        if (this.exists(gridId, value))
            throw new DatabaseUniqueEntryModels.DatabaseDuplicateCheckException(scope, this);
        else
            return value;
    }
    // endregion
    // endregion
}