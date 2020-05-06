package org.wheatgenetics.coordinate.model;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;

import org.wheatgenetics.coordinate.R;
import org.wheatgenetics.coordinate.StringGetter;

public abstract class FullyUniqueEntryModels
        extends UniqueEntryModels {
    FullyUniqueEntryModels(
            @IntRange(from = 1) final long gridId,
            @IntRange(from = 1) final int rows,
            @IntRange(from = 1) final int cols,
            @NonNull final StringGetter stringGetter) {
        super(gridId, rows, cols, stringGetter);
    }

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    @Override
    @NonNull
    IncludedEntryModel makeButDontSetIncludedEntry(
            @IntRange(from = 1) final int row,
            @IntRange(from = 1) final int col) {
        return new FullyCheckedIncludedEntryModel(
                this.getGridId(), row, col, this, this.stringGetter());
    }

    @Override
    public void set(final EntryModel entryModel) {
        throw new UnsupportedOperationException(this.stringGetter().get(
                R.string.CallCheckThenSetInstead));
    }
}