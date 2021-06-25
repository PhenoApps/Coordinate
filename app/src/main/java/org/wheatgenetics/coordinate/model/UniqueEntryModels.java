package org.wheatgenetics.coordinate.model;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

import org.wheatgenetics.coordinate.StringGetter;

public abstract class UniqueEntryModels extends EntryModels
        implements CheckedIncludedEntryModel.Checker {
    UniqueEntryModels(
            @IntRange(from = 1) final long gridId,
            @IntRange(from = 1) final int rows,
            @IntRange(from = 1) final int cols,
            @NonNull final StringGetter stringGetter) {
        super(gridId, rows, cols, stringGetter);
    }

    @Nullable
    private EntryModel
    check(@Nullable final EntryModel
                  entryModel)
            throws CheckedIncludedEntryModel.CheckException {
        if (null != entryModel)
            this.check(entryModel.getRow(), entryModel.getCol(), entryModel.getValue());   // throws
        return entryModel;
    }

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    @Override
    @NonNull
    IncludedEntryModel makeButDontSetIncludedEntry(
            @IntRange(from = 1) final int row,
            @IntRange(from = 1) final int col) {
        return new CheckedIncludedEntryModel(
                this.getGridId(), row, col, this, this.stringGetter());
    }

    // region org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.Checker Overridden Method
    @Override
    @Nullable
    abstract public String check(
            @IntRange(from = 1) final int rowIndex,
            @IntRange(from = 1) final int colIndex,
            @Nullable final String value)
            throws CheckedIncludedEntryModel.CheckException;

    public void checkThenSet(final EntryModel entryModel)
            throws CheckedIncludedEntryModel.CheckException {
        this.uncheckedSet(this.check(entryModel) /* throws CheckException */);
    }
    // endregion

    public abstract static class DuplicateCheckException
            extends CheckedIncludedEntryModel.CheckException {
        DuplicateCheckException(@NonNull final String message) {
            super(message);
        }
    }
}