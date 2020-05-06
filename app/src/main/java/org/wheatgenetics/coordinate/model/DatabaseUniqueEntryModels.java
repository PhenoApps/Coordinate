package org.wheatgenetics.coordinate.model;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.wheatgenetics.coordinate.StringGetter;

public abstract class DatabaseUniqueEntryModels
        extends CurrentGridUniqueEntryModels {
    // region Fields
    @NonNull
    private final String scope;
    @NonNull
    private final
    DatabaseUniqueEntryModels.Checker checker;
    // endregion

    DatabaseUniqueEntryModels(
            @IntRange(from = 1) final long gridId,
            @IntRange(from = 1) final int rows,
            @IntRange(from = 1) final int cols,
            @NonNull final String scope,
            @NonNull final
            DatabaseUniqueEntryModels.Checker checker,
            @NonNull final StringGetter stringGetter) {
        super(gridId, rows, cols, stringGetter);
        this.scope = scope;
        this.checker = checker;
    }

    @Override
    @Nullable
    public String check(
            @IntRange(from = 1) final int rowIndex,
            @IntRange(from = 1) final int colIndex,
            @Nullable final String value)
            throws CheckedIncludedEntryModel.CheckException {
        if (null == super.check(rowIndex, colIndex, value))                // throws CurrentGridDu-
            return null;                                                   //  plicateCheckException
        else
            return this.checker.check(this.getGridId(), value, this.scope);// throws DatabaseDu-
    }                                                                      //  plicateCheckException
    // endregion

    @SuppressWarnings({"UnnecessaryInterfaceModifier"})
    public interface Checker {
        @Nullable
        public String check(
                @IntRange(from = 1) long gridId,
                @Nullable String value,
                @NonNull String scope) throws DatabaseUniqueEntryModels.DatabaseDuplicateCheckException;
    }

    // region Types
    public static class DatabaseDuplicateCheckException extends CurrentGridUniqueEntryModels.CurrentGridDuplicateCheckException {
        public DatabaseDuplicateCheckException(
                @NonNull final String scope,
                @NonNull final StringGetter stringGetter) {
            super(scope, stringGetter);
        }
    }
}