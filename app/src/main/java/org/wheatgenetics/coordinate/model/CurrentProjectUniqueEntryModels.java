package org.wheatgenetics.coordinate.model;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

import org.wheatgenetics.coordinate.R;
import org.wheatgenetics.coordinate.StringGetter;

public class CurrentProjectUniqueEntryModels
        extends DatabaseUniqueEntryModels {
    public CurrentProjectUniqueEntryModels(
            @IntRange(from = 1) final long gridId,
            @IntRange(from = 1) final int rows,
            @IntRange(from = 1) final int cols,
            @NonNull final
            CurrentProjectUniqueEntryModels.Checker checker,
            @NonNull final StringGetter stringGetter) {
        // noinspection ConstantConditions
        super(gridId, rows, cols, /* scope => */ null == stringGetter.get(
                R.string.CurrentProjectUniqueEntryModelsScope) ?
                        "current project" :
                        stringGetter.get(
                                R.string.CurrentProjectUniqueEntryModelsScope),
                checker, stringGetter);
    }
}