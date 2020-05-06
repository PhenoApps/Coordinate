package org.wheatgenetics.coordinate.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;

import org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel;
import org.wheatgenetics.coordinate.model.EntryModel;
import org.wheatgenetics.coordinate.model.EntryModels;
import org.wheatgenetics.coordinate.model.FullyCheckedIncludedEntryModel;
import org.wheatgenetics.coordinate.model.FullyUniqueEntryModels;
import org.wheatgenetics.coordinate.model.IncludedEntryModel;

class CurrentGridFullyUniqueEntriesTable
        extends CurrentGridUniqueEntriesTable {
    @NonNull
    private final
    CurrentGridFullyUniqueEntriesTable.Handler handler;

    CurrentGridFullyUniqueEntriesTable(final Context context,
                                       @NonNull final
                                       CurrentGridFullyUniqueEntriesTable.Handler handler) {
        super(context, "CurrentGridFullyUniqueEntriesTable");
        this.handler = handler;
    }

    // region Overridden Methods
    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    @Override
    IncludedEntryModel
    makeIncludedEntryModel(final long id, final long gridId, final int row, final int col,
                           final String value, final long timestamp) {
        try {
            return new FullyCheckedIncludedEntryModel(  // throws
                    /* id           => */ id,
                    /* gridId       => */ gridId,
                    /* row          => */ row,
                    /* col          => */ col,
                    /* value        => */ value,
                    /* timestamp    => */ timestamp,
                    /* checker      => */ this.getCurrentGridUniqueEntryModels(),
                    /* stringGetter => */this);
        } catch (final CheckedIncludedEntryModel.CheckException e) {
            this.handler.handleCGFUETCheckException();
            return null;
        }
    }

    @Override
    boolean setEntryModel(
            @NonNull final EntryModels entryModels,
            @NonNull final EntryModel entryModel) {
        if (entryModels instanceof FullyUniqueEntryModels)
            try {
                ((FullyUniqueEntryModels)
                        entryModels).checkThenSet(entryModel);                  // throws CheckException
                return false;
            } catch (final CheckedIncludedEntryModel.CheckException
                    e) {
                return true;
            }
        else return true;
    }

    @SuppressWarnings({"UnnecessaryInterfaceModifier"})
    public interface Handler {
        public void handleCGFUETCheckException() /* CGFUET == CurrentGridFullyUniqueEntriesTable */;
    }
    // endregion
}