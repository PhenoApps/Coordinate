package org.wheatgenetics.coordinate.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;

import org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel;
import org.wheatgenetics.coordinate.model.CurrentProjectUniqueEntryModels;
import org.wheatgenetics.coordinate.model.DatabaseUniqueEntryModels;
import org.wheatgenetics.coordinate.model.EntryModels;

public class CurrentProjectUniqueEntriesTable
        extends CheckedEntriesTable {
    // region Fields
    @NonNull
    private final
    DatabaseUniqueEntryModels.Checker checker;

    private CurrentProjectUniqueEntryModels
            currentProjectUniqueEntryModels = null;
    // endregion

    public CurrentProjectUniqueEntriesTable(final Context context,
                                            @NonNull final
                                            DatabaseUniqueEntryModels.Checker checker) {
        super(context, "CurrentProjectUniqueEntriesTable");
        this.checker = checker;
    }

    private CurrentProjectUniqueEntryModels
    getCurrentProjectUniqueEntryModels() {
        return this.currentProjectUniqueEntryModels;
    }

    // region Overridden Methods
    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    @Override
    @NonNull
    CheckedIncludedEntryModel.Checker checker() {
        return this.getCurrentProjectUniqueEntryModels();
    }

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    @Override
    EntryModels makeEntryModels(
            final long gridId, final int rows, final int cols) {
        this.currentProjectUniqueEntryModels =
                new CurrentProjectUniqueEntryModels(
                        gridId, rows, cols, this.checker, this);
        return this.getCurrentProjectUniqueEntryModels();
    }
    // endregion
}