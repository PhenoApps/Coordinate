package org.wheatgenetics.coordinate.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;

import org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel;
import org.wheatgenetics.coordinate.model.CurrentGridUniqueEntryModels;
import org.wheatgenetics.coordinate.model.EntryModels;

public class CurrentGridUniqueEntriesTable
        extends CheckedEntriesTable {
    private CurrentGridUniqueEntryModels
            currentGridUniqueEntryModels = null;

    // region Constructors
    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    CurrentGridUniqueEntriesTable(final Context context,
                                  @NonNull final String tag) {
        super(context, tag);
    }

    public CurrentGridUniqueEntriesTable(final Context context) {
        this(context, "CurrentGridUniqueEntriesTable");
    }

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    CurrentGridUniqueEntryModels
    getCurrentGridUniqueEntryModels() {
        return this.currentGridUniqueEntryModels;
    }
    // endregion

    // region Overridden Methods
    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    @Override
    @NonNull
    CheckedIncludedEntryModel.Checker checker() {
        return this.getCurrentGridUniqueEntryModels();
    }

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    @Override
    EntryModels makeEntryModels(
            final long gridId, final int rows, final int cols) {
        this.currentGridUniqueEntryModels =
                new CurrentGridUniqueEntryModels(
                        gridId, rows, cols, this);
        return this.getCurrentGridUniqueEntryModels();
    }
    // endregion
}