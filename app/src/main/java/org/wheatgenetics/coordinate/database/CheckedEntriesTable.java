package org.wheatgenetics.coordinate.database;

/**
 * Uses:
 * android.content.Context
 *
 * androidx.annotation.NonNull
 * androidx.annotation.RestrictTo
 * androidx.annotation.RestrictTo.Scope
 *
 * org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel
 * org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.Checker
 * org.wheatgenetics.coordinate.model.IncludedEntryModel
 *
 * org.wheatgenetics.coordinate.database.EntriesTable
 */
abstract class CheckedEntriesTable extends org.wheatgenetics.coordinate.database.EntriesTable
{
    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @androidx.annotation.NonNull
    abstract org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.Checker checker();

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    CheckedEntriesTable(final android.content.Context context,
    @androidx.annotation.NonNull final java.lang.String tag) { super(context, tag); }

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @java.lang.Override org.wheatgenetics.coordinate.model.IncludedEntryModel
    makeIncludedEntryModel(final long id, final long gridId, final int row, final int col,
    final java.lang.String value, final long timestamp)
    {
        return new org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel(
            /* id           => */ id            ,
            /* gridId       => */ gridId        ,
            /* row          => */ row           ,
            /* col          => */ col           ,
            /* value        => */ value         ,
            /* timestamp    => */ timestamp     ,
            /* checker      => */ this.checker(),
            /* stringGetter => */this);
    }
}