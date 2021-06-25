package org.wheatgenetics.coordinate.model;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.wheatgenetics.coordinate.R;
import org.wheatgenetics.coordinate.StringGetter;

public class CheckedIncludedEntryModel extends IncludedEntryModel {
    @NonNull
    private final
    CheckedIncludedEntryModel.Checker checker;

    // region Constructors
    CheckedIncludedEntryModel(
            @IntRange(from = 1) final long gridId,
            @IntRange(from = 1) final int row,
            @IntRange(from = 1) final int col,
            @NonNull final
            CheckedIncludedEntryModel.Checker checker,
            @NonNull final StringGetter stringGetter) {
        super(gridId, row, col, stringGetter);
        this.checker = checker;
    }
    // endregion

    public CheckedIncludedEntryModel(
            @IntRange(from = 1) final long id,
            @IntRange(from = 1) final long gridId,
            @IntRange(from = 1) final int row,
            @IntRange(from = 1) final int col,
            final String value,
            @IntRange(from = 0) final long timestamp,
            @NonNull final
            CheckedIncludedEntryModel.Checker checker,
            @NonNull final StringGetter stringGetter) {
        super(id, gridId, row, col, value, timestamp, stringGetter);
        this.checker = checker;
    }

    public CheckedIncludedEntryModel(@NonNull final
                                     ExcludedEntryModel excludedEntryModel,
                                     @NonNull final
                                     CheckedIncludedEntryModel.Checker checker,
                                     @NonNull final StringGetter stringGetter) {
        super(excludedEntryModel, stringGetter);
        this.checker = checker;
    }

    @Override
    public void setValue(
            @Nullable final String value) {
        throw new UnsupportedOperationException(this.stringGetter().get(
                R.string.CallCheckThenSetValueInstead));
    }

    public void checkThenSetValue(@Nullable final String value)
            throws CheckedIncludedEntryModel.CheckException {
        this.uncheckedSetValue(
                this.checker.check(this.getRow(), this.getCol(), value) /* throws */);
    }
    // endregion

    @SuppressWarnings({"UnnecessaryInterfaceModifier"})
    public interface Checker {
        @Nullable
        public abstract String check(
                @IntRange(from = 1) int rowIndex,
                @IntRange(from = 1) int colIndex,
                @Nullable String value)
                throws CheckedIncludedEntryModel.CheckException;
    }

    // region Types
    public abstract static class CheckException extends Exception {
        CheckException(@NonNull final String message) {
            super(message);
        }
    }
}