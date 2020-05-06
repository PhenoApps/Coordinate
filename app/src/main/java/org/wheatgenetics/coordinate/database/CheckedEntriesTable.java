package org.wheatgenetics.coordinate.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;

import org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel;
import org.wheatgenetics.coordinate.model.IncludedEntryModel;

abstract class CheckedEntriesTable extends EntriesTable {
    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    CheckedEntriesTable(final Context context,
                        @NonNull final String tag) {
        super(context, tag);
    }

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    @NonNull
    abstract CheckedIncludedEntryModel.Checker checker();

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    @Override
    IncludedEntryModel
    makeIncludedEntryModel(final long id, final long gridId, final int row, final int col,
                           final String value, final long timestamp) {
        return new CheckedIncludedEntryModel(
                /* id           => */ id,
                /* gridId       => */ gridId,
                /* row          => */ row,
                /* col          => */ col,
                /* value        => */ value,
                /* timestamp    => */ timestamp,
                /* checker      => */ this.checker(),
                /* stringGetter => */this);
    }
}