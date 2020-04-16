package org.wheatgenetics.coordinate.database;

/**
 * Uses:
 * android.content.Context
 *
 * androidx.annotation.NonNull
 * androidx.annotation.RestrictTo
 * androidx.annotation.RestrictTo.Scope
 *
 * org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.CheckException
 * org.wheatgenetics.coordinate.model.EntryModel
 * org.wheatgenetics.coordinate.model.EntryModels
 * org.wheatgenetics.coordinate.model.FullyCheckedIncludedEntryModel
 * org.wheatgenetics.coordinate.model.FullyUniqueEntryModels
 * org.wheatgenetics.coordinate.model.IncludedEntryModel
 *
 * org.wheatgenetics.coordinate.database.CurrentGridUniqueEntriesTable
 */
class CurrentGridFullyUniqueEntriesTable
extends org.wheatgenetics.coordinate.database.CurrentGridUniqueEntriesTable
{
    @java.lang.SuppressWarnings({"UnnecessaryInterfaceModifier"}) public interface Handler
    { public void handleCGFUETCheckException() /* CGFUET == CurrentGridFullyUniqueEntriesTable */; }

    @androidx.annotation.NonNull private final
        org.wheatgenetics.coordinate.database.CurrentGridFullyUniqueEntriesTable.Handler handler;

    CurrentGridFullyUniqueEntriesTable(final android.content.Context context,
    @androidx.annotation.NonNull final
        org.wheatgenetics.coordinate.database.CurrentGridFullyUniqueEntriesTable.Handler handler)
    { super(context,"CurrentGridFullyUniqueEntriesTable"); this.handler = handler; }

    // region Overridden Methods
    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @java.lang.Override org.wheatgenetics.coordinate.model.IncludedEntryModel
    makeIncludedEntryModel(final long id, final long gridId, final int row, final int col,
    final java.lang.String value, final long timestamp)
    {
        try
        {
            return new org.wheatgenetics.coordinate.model.FullyCheckedIncludedEntryModel(  // throws
                /* id           => */ id                                    ,
                /* gridId       => */ gridId                                ,
                /* row          => */ row                                   ,
                /* col          => */ col                                   ,
                /* value        => */ value                                 ,
                /* timestamp    => */ timestamp                             ,
                /* checker      => */ this.getCurrentGridUniqueEntryModels(),
                /* stringGetter => */this);
        }
        catch (final org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.CheckException e)
        { this.handler.handleCGFUETCheckException(); return null; }
    }

    @java.lang.Override boolean setEntryModel(
    @androidx.annotation.NonNull final org.wheatgenetics.coordinate.model.EntryModels entryModels,
    @androidx.annotation.NonNull final org.wheatgenetics.coordinate.model.EntryModel  entryModel )
    {
        if (entryModels instanceof org.wheatgenetics.coordinate.model.FullyUniqueEntryModels)
            try
            {
                ((org.wheatgenetics.coordinate.model.FullyUniqueEntryModels)
                    entryModels).checkThenSet(entryModel);                  // throws CheckException
                return false;
            }
            catch (final org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.CheckException
            e) { return true; }
        else return true;
    }
    // endregion
}