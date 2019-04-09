package org.wheatgenetics.coordinate.database;

/**
 * Uses:
 * android.content.Context
 * android.support.annotation.IntRange
 * android.support.annotation.NonNull
 * android.support.annotation.Nullable
 * android.support.annotation.RestrictTo
 * android.support.annotation.RestrictTo.Scope
 *
 * org.wheatgenetics.coordinate.model.BaseJoinedGridModels
 * org.wheatgenetics.coordinate.model.CurrentGridUniqueEntryModels
 * org.wheatgenetics.coordinate.model.CurrentGridUniqueJoinedGridModel
 * org.wheatgenetics.coordinate.model.EntryModels
 * org.wheatgenetics.coordinate.model.JoinedGridModel
 *
 * org.wheatgenetics.coordinate.database.CurrentGridUniqueEntriesTable
 * org.wheatgenetics.coordinate.database.CurrentGridUniqueEntriesTable.Handler
 * org.wheatgenetics.coordinate.database.EntriesTable
 * org.wheatgenetics.coordinate.database.GridsTable
 */
public class CurrentGridUniqueGridsTable extends org.wheatgenetics.coordinate.database.GridsTable
{
    @android.support.annotation.NonNull private final
        org.wheatgenetics.coordinate.database.CurrentGridUniqueEntriesTable.Handler handler;

    public CurrentGridUniqueGridsTable(final android.content.Context context,
    @android.support.annotation.NonNull final
        org.wheatgenetics.coordinate.database.CurrentGridUniqueEntriesTable.Handler handler)
    { super(context,"CurrentGridUniqueGridsTable"); this.handler = handler; }

    // region Overridden Methods
    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    @java.lang.Override org.wheatgenetics.coordinate.database.EntriesTable makeEntriesTables()
    {
        return new org.wheatgenetics.coordinate.database.CurrentGridUniqueEntriesTable(
            this.getContext(), this.handler);
    }

    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    @java.lang.Override
    org.wheatgenetics.coordinate.model.BaseJoinedGridModels makeJoinedGridModels()
    { return new org.wheatgenetics.coordinate.model.CurrentGridUniqueJoinedGridModels(); }

    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    @java.lang.Override org.wheatgenetics.coordinate.model.JoinedGridModel makeJoinedGridModel(
    @android.support.annotation.IntRange(from = 1) final long             id            ,
    @android.support.annotation.IntRange(from = 0) final long             projectId     ,
                                                   final java.lang.String person        ,
    @android.support.annotation.IntRange(from = 0) final int              activeRow     ,
    @android.support.annotation.IntRange(from = 0) final int              activeCol     ,
    @android.support.annotation.Nullable           final java.lang.String optionalFields,
    @android.support.annotation.IntRange(from = 0) final long             timestamp     ,

    @android.support.annotation.IntRange(from = 1        ) final long             templateId     ,
                                                           final java.lang.String title          ,
    @android.support.annotation.IntRange(from = 0, to = 2) final int              code           ,
    @android.support.annotation.IntRange(from = 1        ) final int              rows           ,
    @android.support.annotation.IntRange(from = 1        ) final int              cols           ,
    @android.support.annotation.IntRange(from = 0        ) final int generatedExcludedCellsAmount,
    @android.support.annotation.Nullable final java.lang.String initialExcludedCells,
    @android.support.annotation.Nullable final java.lang.String excludedRows        ,
    @android.support.annotation.Nullable final java.lang.String excludedCols        ,
    @android.support.annotation.IntRange(from = 0, to = 1) final int      colNumbering          ,
    @android.support.annotation.IntRange(from = 0, to = 1) final int      rowNumbering          ,
                                                   final java.lang.String entryLabel            ,
    @android.support.annotation.Nullable           final java.lang.String templateOptionalFields,
    @android.support.annotation.IntRange(from = 0) final long             templateTimestamp     ,

    final org.wheatgenetics.coordinate.model.EntryModels entryModels)
    {
        return new org.wheatgenetics.coordinate.model.CurrentGridUniqueJoinedGridModel(id,
            projectId, person, activeRow, activeCol, optionalFields, timestamp, templateId, title,
            code, rows, cols, generatedExcludedCellsAmount, initialExcludedCells, excludedRows,
            excludedCols, colNumbering, rowNumbering, entryLabel, templateOptionalFields,
            templateTimestamp,
            (org.wheatgenetics.coordinate.model.CurrentGridUniqueEntryModels) entryModels);
    }
    // endregion
}