package org.wheatgenetics.coordinate.model;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

import org.wheatgenetics.coordinate.StringGetter;

public class FullyCheckedIncludedEntryModel
        extends CheckedIncludedEntryModel {
    FullyCheckedIncludedEntryModel(
            @IntRange(from = 1) final long gridId,
            @IntRange(from = 1) final int row,
            @IntRange(from = 1) final int col,
            @NonNull final
            CheckedIncludedEntryModel.Checker checker,
            @NonNull final StringGetter stringGetter) {
        super(gridId, row, col, checker, stringGetter);
    }

    public FullyCheckedIncludedEntryModel(
            @IntRange(from = 1) final long id,
            @IntRange(from = 1) final long gridId,
            @IntRange(from = 1) final int row,
            @IntRange(from = 1) final int col,
            final String value,
            @IntRange(from = 0) final long timestamp,
            @NonNull final
            CheckedIncludedEntryModel.Checker checker,
            @NonNull final StringGetter stringGetter)
            throws CheckedIncludedEntryModel.CheckException {
        super(id, gridId, row, col, checker.check( /* throws CheckException */
                row, col, value), timestamp, checker, stringGetter);
    }
}