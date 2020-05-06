package org.wheatgenetics.coordinate.deleter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;

import org.wheatgenetics.coordinate.database.EntriesTable;

abstract class BaseGridDeleter extends Deleter {
    private EntriesTable entriesTableInstance = null;    // ll

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    BaseGridDeleter(@NonNull final Context context) {
        super(context);
    }

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    @NonNull
    EntriesTable entriesTable() {
        if (null == this.entriesTableInstance) this.entriesTableInstance =
                new EntriesTable(this.context());
        return this.entriesTableInstance;
    }
}