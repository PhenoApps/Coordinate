package org.wheatgenetics.coordinate.model;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

import org.wheatgenetics.coordinate.StringGetter;

public class AllGridsUniqueJoinedGridModel
        extends CurrentGridUniqueJoinedGridModel {
    @NonNull
    private final
    AllGridsUniqueEntryModels.Checker checker;

    /**
     * Used by AllGridsUniqueGridsTable.
     */
    public AllGridsUniqueJoinedGridModel(
            @IntRange(from = 1) final long id,
            @IntRange(from = 0) final long projectId,
            final String person,
            @IntRange(from = 0) final int activeRow,
            @IntRange(from = 0) final int activeCol,
            @Nullable final String optionalFields,
            @NonNull final StringGetter stringGetter,
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

            final AllGridsUniqueEntryModels allGridsUniqueEntryModels,
            @NonNull final
            AllGridsUniqueEntryModels.Checker checker) {
        super(id, projectId, person, activeRow, activeCol, optionalFields, stringGetter, timestamp,
                templateId, title, code, rows, cols, generatedExcludedCellsAmount, initialExcludedCells,
                excludedRows, excludedCols, colNumbering, rowNumbering, entryLabel,
                templateOptionalFields, templateTimestamp, allGridsUniqueEntryModels);
        this.checker = checker;
    }

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    @Override
    @NonNull
    EntryModels makeEntryModels(
            @IntRange(from = 1) final int rows,
            @IntRange(from = 1) final int cols) {
        return new AllGridsUniqueEntryModels(
                /* gridId => */ this.getId(), rows, cols, this.checker, this.stringGetter());
    }
}