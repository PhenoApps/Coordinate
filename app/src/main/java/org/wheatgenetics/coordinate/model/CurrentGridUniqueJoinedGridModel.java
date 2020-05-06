package org.wheatgenetics.coordinate.model;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

import org.wheatgenetics.coordinate.R;
import org.wheatgenetics.coordinate.StringGetter;

public class CurrentGridUniqueJoinedGridModel
        extends JoinedGridModel {
    /**
     * Used by CurrentGridUniqueGridsTable.
     */
    public CurrentGridUniqueJoinedGridModel(
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

            final CurrentGridUniqueEntryModels
                    currentGridUniqueEntryModels) {
        super(id, projectId, person, activeRow, activeCol, optionalFields, stringGetter, timestamp,
                templateId, title, code, rows, cols, generatedExcludedCellsAmount, initialExcludedCells,
                excludedRows, excludedCols, colNumbering, rowNumbering, entryLabel,
                templateOptionalFields, templateTimestamp, currentGridUniqueEntryModels);
    }

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    @Override
    @NonNull
    EntryModels makeEntryModels(
            @IntRange(from = 1) final int rows,
            @IntRange(from = 1) final int cols) {
        return new CurrentGridUniqueEntryModels(
                /* gridId => */ this.getId(), rows, cols, this.stringGetter());
    }

    @Override
    public void setEntryModel(final EntryModel entryModel) {
        throw new UnsupportedOperationException(this.stringGetter().get(
                R.string.CallCheckThenSetEntryModelInstead));
    }

    public void checkThenSetEntryModel(
            final EntryModel entryModel)
            throws CheckedIncludedEntryModel.CheckException {
        if (!this.entryModelsIsNull())
            // noinspection ConstantConditions
            ((CurrentGridUniqueEntryModels)
                    this.getEntryModels()).checkThenSet(entryModel);                           // throws
    }
}