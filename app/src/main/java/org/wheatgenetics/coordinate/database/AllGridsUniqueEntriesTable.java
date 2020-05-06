package org.wheatgenetics.coordinate.database;

import android.content.Context;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

import org.wheatgenetics.coordinate.model.AllGridsUniqueEntryModels;
import org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel;
import org.wheatgenetics.coordinate.model.DatabaseUniqueEntryModels;
import org.wheatgenetics.coordinate.model.EntryModels;

public class AllGridsUniqueEntriesTable
        extends CheckedEntriesTable
        implements DatabaseUniqueEntryModels.Checker {
    private AllGridsUniqueEntryModels
            allGridsUniqueEntryModels = null;

    public AllGridsUniqueEntriesTable(final Context context) {
        super(context, "AllGridsUniqueEntriesTable");
    }

    private AllGridsUniqueEntryModels
    getAllGridsUniqueEntryModels() {
        return this.allGridsUniqueEntryModels;
    }

    // region Overridden Methods
    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    @Override
    @NonNull
    CheckedIncludedEntryModel.Checker checker() {
        return this.getAllGridsUniqueEntryModels();
    }

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    @Override
    EntryModels makeEntryModels(
            final long gridId, final int rows, final int cols) {
        this.allGridsUniqueEntryModels =
                new AllGridsUniqueEntryModels(
                        gridId, rows, cols, this, this);
        return this.getAllGridsUniqueEntryModels();
    }

    // region org.wheatgenetics.coordinate.model.DatabaseUniqueEntryModels.Checker Overridden Method
    @Override
    @Nullable
    public String check(
            @IntRange(from = 1) final long gridId,
            @Nullable final String value,
            @NonNull final String scope) throws
            DatabaseUniqueEntryModels.DatabaseDuplicateCheckException {
        // noinspection CStyleArrayDeclaration
        final String
                selection =
                AllGridsUniqueEntriesTable.GRID_FIELD_NAME +
                        " <> ? AND " +
                        AllGridsUniqueEntriesTable.EDATA_FIELD_NAME +
                        " = ?",
                selectionArgs[] = new String[]{String.valueOf(gridId), value};
        if (AllGridsUniqueEntriesTable.exists(
                this.queryAll(selection, selectionArgs)))
            throw new DatabaseUniqueEntryModels.DatabaseDuplicateCheckException(scope, this);
        else
            return value;
    }
    // endregion
    // endregion
}