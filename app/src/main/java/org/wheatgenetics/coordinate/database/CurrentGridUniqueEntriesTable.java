package org.wheatgenetics.coordinate.database;

/**
 * Uses:
 * android.content.Context
 * android.support.annotation.NonNull
 * android.support.annotation.RestrictTo
 * android.support.annotation.RestrictTo.Scope
 *
 * org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel
 * org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.CheckException
 * org.wheatgenetics.coordinate.model.CurrentGridUniqueEntryModels
 * org.wheatgenetics.coordinate.model.EntryModel
 * org.wheatgenetics.coordinate.model.EntryModels
 * org.wheatgenetics.coordinate.model.IncludedEntryModel
 *
 * org.wheatgenetics.coordinate.database.EntriesTable
 */
public class CurrentGridUniqueEntriesTable
extends org.wheatgenetics.coordinate.database.EntriesTable
{
    @java.lang.SuppressWarnings({"UnnecessaryInterfaceModifier"}) public interface Handler
    { public void handleCGUETCheckException() /* CGUET == CurrentGridUniqueEntriesTable */; }

    // region Fields
    @android.support.annotation.NonNull private final
        org.wheatgenetics.coordinate.database.CurrentGridUniqueEntriesTable.Handler handler;

    private org.wheatgenetics.coordinate.model.CurrentGridUniqueEntryModels
        currentGridUniqueEntryModels = null;
    // endregion

    public CurrentGridUniqueEntriesTable(final android.content.Context context,
    @android.support.annotation.NonNull final
        org.wheatgenetics.coordinate.database.CurrentGridUniqueEntriesTable.Handler handler)
    { super(context,"CurrentGridUniqueEntriesTable"); this.handler = handler; }

    // region Overridden Methods
    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    @java.lang.Override org.wheatgenetics.coordinate.model.IncludedEntryModel
    makeIncludedEntryModel(final long id, final long gridId, final int row, final int col,
    final java.lang.String value, final long timestamp)
    {
        try
        {
            return new org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel(
                /* id        => */ id,
                /* gridId    => */ gridId,
                /* row       => */ row,
                /* col       => */ col,
                /* value     => */ value,
                /* timestamp => */ timestamp,
                /* checker   => */ this.currentGridUniqueEntryModels);
        }
        catch (final org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.CheckException e)
        { this.handler.handleCGUETCheckException(); return null; }
    }

    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    org.wheatgenetics.coordinate.model.EntryModels makeEntryModels(
    final long gridId, final int rows, final int cols)
    {
        this.currentGridUniqueEntryModels =
            new org.wheatgenetics.coordinate.model.CurrentGridUniqueEntryModels(gridId, rows, cols);
        return this.currentGridUniqueEntryModels;
    }

    @java.lang.Override boolean setEntryModel(
    @android.support.annotation.NonNull
        final org.wheatgenetics.coordinate.model.EntryModels entryModels,
    @android.support.annotation.NonNull
        final org.wheatgenetics.coordinate.model.EntryModel entryModel)
    {
        if (entryModels instanceof org.wheatgenetics.coordinate.model.UniqueEntryModels)
            try
            {
                ((org.wheatgenetics.coordinate.model.UniqueEntryModels)
                    entryModels).checkThenSet(entryModel);                  // throws CheckException
                return false;
            }
            catch (final org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.CheckException
            e) { return true; }
        else
            return true;
    }
    // endregion
}