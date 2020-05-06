package org.wheatgenetics.coordinate.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;

public class CurrentGridFullyUniqueGridsTable
        extends CurrentGridUniqueGridsTable {
    @NonNull
    private final
    CurrentGridFullyUniqueEntriesTable.Handler handler;

    public CurrentGridFullyUniqueGridsTable(final Context context,
                                            @NonNull final
                                            CurrentGridFullyUniqueEntriesTable.Handler handler) {
        super(context, "CurrentGridFullyUniqueGridsTable");
        this.handler = handler;
    }

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    @Override
    EntriesTable makeEntriesTable() {
        return new CurrentGridFullyUniqueEntriesTable(
                this.getContext(), this.handler);
    }
}