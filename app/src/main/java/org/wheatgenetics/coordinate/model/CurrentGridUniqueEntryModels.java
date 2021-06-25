package org.wheatgenetics.coordinate.model;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

import org.wheatgenetics.coordinate.R;
import org.wheatgenetics.coordinate.StringGetter;

public class CurrentGridUniqueEntryModels
        extends UniqueEntryModels {
    public CurrentGridUniqueEntryModels(
            @IntRange(from = 1) final long gridId,
            @IntRange(from = 1) final int rows,
            @IntRange(from = 1) final int cols,
            @NonNull final StringGetter stringGetter) {
        super(gridId, rows, cols, stringGetter);
    }

    @Override
    @Nullable
    public String check(
            @IntRange(from = 1) final int rowIndex,
            @IntRange(from = 1) final int colIndex,
            @Nullable final String value)
            throws CheckedIncludedEntryModel.CheckException {
        if (null == value)
            return null;
        else {
            @SuppressWarnings({"ClassExplicitlyExtendsObject"})
            class Processor extends Object
                    implements EntryModels.Processor {
                // region Fields
                @IntRange(from = 1)
                private final int rowIndex, colIndex;
                @Nullable
                private final String value;

                private boolean duplicateFound = false;
                // endregion

                private Processor(
                        @IntRange(from = 1) final int rowIndex,
                        @IntRange(from = 1) final int colIndex,
                        @Nullable final String value) {
                    super();
                    this.rowIndex = rowIndex;
                    this.colIndex = colIndex;
                    this.value = value;
                }

                @Override
                public void process(
                        final EntryModel entryModel) {
                    if (!this.duplicateFound)
                        if (entryModel instanceof
                                IncludedEntryModel)
                            if (entryModel.getRow() != this.rowIndex
                                    || entryModel.getCol() != this.colIndex) {
                                final String existingValue = entryModel.getValue();
                                if (null != existingValue && existingValue.equals(this.value))
                                    this.duplicateFound = true;
                            }
                }

                private boolean getDuplicateFound() {
                    return this.duplicateFound;
                }
            }

            final Processor processor = new Processor(rowIndex, colIndex, value);
            this.processAll(processor);
            if (processor.getDuplicateFound())
                throw new CurrentGridUniqueEntryModels
                        .CurrentGridDuplicateCheckException(this.stringGetter());
            else
                return value;
        }
    }

    static class CurrentGridDuplicateCheckException
            extends UniqueEntryModels.DuplicateCheckException {
        @RestrictTo(RestrictTo.Scope.SUBCLASSES)
        CurrentGridDuplicateCheckException(
                @NonNull final String scope,
                @NonNull final StringGetter stringGetter) {
            // noinspection ConstantConditions
            super(String.format(null == stringGetter.get(R.string.CurrentGridDuplicateCheckExceptionMsg) ?
                            "The %s already has an entry with that value." :
                            stringGetter.get(R
                                    .string.CurrentGridDuplicateCheckExceptionMsg),
                    scope));
        }

        CurrentGridDuplicateCheckException(
                @NonNull final StringGetter stringGetter) {
            // noinspection ConstantConditions
            this(/* scope => */ null == stringGetter.get(R.string.CurrentGridDuplicateCheckExceptionScope) ?
                            "current grid" :
                            stringGetter.get(R
                                    .string.CurrentGridDuplicateCheckExceptionScope),
                    stringGetter);
        }
    }
}