package org.wheatgenetics.coordinate.database;

import android.content.Context;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

import org.wheatgenetics.coordinate.model.BaseJoinedGridModels;
import org.wheatgenetics.coordinate.model.CurrentGridUniqueEntryModels;
import org.wheatgenetics.coordinate.model.CurrentGridUniqueJoinedGridModel;
import org.wheatgenetics.coordinate.model.CurrentGridUniqueJoinedGridModels;
import org.wheatgenetics.coordinate.model.EntryModels;
import org.wheatgenetics.coordinate.model.JoinedGridModel;

public class CurrentGridUniqueGridsTable extends GridsTable {
    // region Constructors
    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    CurrentGridUniqueGridsTable(final Context context,
                                @NonNull final String tag) {
        super(context, tag);
    }

    public CurrentGridUniqueGridsTable(final Context context) {
        this(context, "CurrentGridUniqueGridsTable");
    }
    // endregion

    // region Overridden Methods
    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    @Override
    EntriesTable makeEntriesTable() {
        return new CurrentGridUniqueEntriesTable(
                this.getContext());
    }

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    @Override
    BaseJoinedGridModels makeJoinedGridModels() {
        return new CurrentGridUniqueJoinedGridModels(
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
        return new CurrentGridUniqueJoinedGridModel(id,
                projectId, person, activeRow, activeCol, optionalFields, this, timestamp,
                templateId, title, code, rows, cols, generatedExcludedCellsAmount, initialExcludedCells,
                excludedRows, excludedCols, colNumbering, rowNumbering, entryLabel,
                templateOptionalFields, templateTimestamp,
                (CurrentGridUniqueEntryModels) entryModels);
    }
    // endregion
}