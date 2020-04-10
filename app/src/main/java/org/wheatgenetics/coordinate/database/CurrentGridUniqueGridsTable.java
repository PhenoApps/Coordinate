package org.wheatgenetics.coordinate.database;

/**
 * Uses:
 * android.content.Context
 *
 * androidx.annotation.IntRange
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 * androidx.annotation.RestrictTo
 * androidx.annotation.RestrictTo.Scope
 *
 * org.wheatgenetics.coordinate.model.BaseJoinedGridModels
 * org.wheatgenetics.coordinate.model.CurrentGridUniqueEntryModels
 * org.wheatgenetics.coordinate.model.CurrentGridUniqueJoinedGridModel
 * org.wheatgenetics.coordinate.model.CurrentGridUniqueJoinedGridModels
 * org.wheatgenetics.coordinate.model.EntryModels
 * org.wheatgenetics.coordinate.model.JoinedGridModel
 *
 * org.wheatgenetics.coordinate.database.CurrentGridUniqueEntriesTable
 * org.wheatgenetics.coordinate.database.EntriesTable
 * org.wheatgenetics.coordinate.database.GridsTable
 */
public class CurrentGridUniqueGridsTable extends org.wheatgenetics.coordinate.database.GridsTable
{
    // region Constructors
    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    CurrentGridUniqueGridsTable(final android.content.Context context,
    @androidx.annotation.NonNull final java.lang.String tag) { super(context, tag); }

    public CurrentGridUniqueGridsTable(final android.content.Context context)
    { this(context,"CurrentGridUniqueGridsTable"); }
    // endregion

    // region Overridden Methods
    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @java.lang.Override org.wheatgenetics.coordinate.database.EntriesTable makeEntriesTable()
    {
        return new org.wheatgenetics.coordinate.database.CurrentGridUniqueEntriesTable(
            this.getContext());
    }

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @java.lang.Override
    org.wheatgenetics.coordinate.model.BaseJoinedGridModels makeJoinedGridModels()
    {
        return new org.wheatgenetics.coordinate.model.CurrentGridUniqueJoinedGridModels(
            this);
    }

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @java.lang.Override org.wheatgenetics.coordinate.model.JoinedGridModel makeJoinedGridModel(
    @androidx.annotation.IntRange(from = 1) final long             id            ,
    @androidx.annotation.IntRange(from = 0) final long             projectId     ,
                                            final java.lang.String person        ,
    @androidx.annotation.IntRange(from = 0) final int              activeRow     ,
    @androidx.annotation.IntRange(from = 0) final int              activeCol     ,
    @androidx.annotation.Nullable           final java.lang.String optionalFields,
    @androidx.annotation.IntRange(from = 0) final long             timestamp     ,

    @androidx.annotation.IntRange(from = 1        ) final long             templateId            ,
                                                    final java.lang.String title                 ,
    @androidx.annotation.IntRange(from = 0, to = 2) final int              code                  ,
    @androidx.annotation.IntRange(from = 1        ) final int              rows                  ,
    @androidx.annotation.IntRange(from = 1        ) final int              cols                  ,
    @androidx.annotation.IntRange(from = 0        ) final int generatedExcludedCellsAmount       ,
    @androidx.annotation.Nullable                   final java.lang.String initialExcludedCells  ,
    @androidx.annotation.Nullable                   final java.lang.String excludedRows          ,
    @androidx.annotation.Nullable                   final java.lang.String excludedCols          ,
    @androidx.annotation.IntRange(from = 0, to = 1) final int              colNumbering          ,
    @androidx.annotation.IntRange(from = 0, to = 1) final int              rowNumbering          ,
                                                    final java.lang.String entryLabel            ,
    @androidx.annotation.Nullable                   final java.lang.String templateOptionalFields,
    @androidx.annotation.IntRange(from = 0)         final long             templateTimestamp     ,

    final org.wheatgenetics.coordinate.model.EntryModels entryModels)
    {
        return new org.wheatgenetics.coordinate.model.CurrentGridUniqueJoinedGridModel(id,
            projectId, person, activeRow, activeCol, optionalFields,this, timestamp,
            templateId, title, code, rows, cols, generatedExcludedCellsAmount, initialExcludedCells,
            excludedRows, excludedCols, colNumbering, rowNumbering, entryLabel,
            templateOptionalFields, templateTimestamp,
            (org.wheatgenetics.coordinate.model.CurrentGridUniqueEntryModels) entryModels);
    }
    // endregion
}