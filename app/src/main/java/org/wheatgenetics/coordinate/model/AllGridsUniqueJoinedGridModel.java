package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * android.support.annotation.IntRange
 * android.support.annotation.NonNull
 * android.support.annotation.Nullable
 * android.support.annotation.RestrictTo
 * android.support.annotation.RestrictTo.Scope
 *
 * org.wheatgenetics.coordinate.model.AllGridsUniqueEntryModels
 * org.wheatgenetics.coordinate.model.AllGridsUniqueEntryModels.Checker
 * org.wheatgenetics.coordinate.model.CurrentGridUniqueJoinedGridModel
 * org.wheatgenetics.coordinate.model.EntryModels
 */
public class AllGridsUniqueJoinedGridModel
extends org.wheatgenetics.coordinate.model.CurrentGridUniqueJoinedGridModel
{
    @android.support.annotation.NonNull private final
        org.wheatgenetics.coordinate.model.AllGridsUniqueEntryModels.Checker checker;

    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    @java.lang.Override @android.support.annotation.NonNull
    org.wheatgenetics.coordinate.model.EntryModels makeEntryModels(
    @android.support.annotation.IntRange(from = 1) final int rows,
    @android.support.annotation.IntRange(from = 1) final int cols)
    {
        return new org.wheatgenetics.coordinate.model.AllGridsUniqueEntryModels(
            /* gridId => */ this.getId(), /* rows => */ rows, /* cols => */ cols, this.checker);
    }

    /** Used by AllGridsUniqueGridsTable. */
    public AllGridsUniqueJoinedGridModel(
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
    @android.support.annotation.IntRange(from = 0, to = 1) final int colNumbering,
    @android.support.annotation.IntRange(from = 0, to = 1) final int rowNumbering,
                                                   final java.lang.String entryLabel            ,
    @android.support.annotation.Nullable           final java.lang.String templateOptionalFields,
    @android.support.annotation.IntRange(from = 0) final long             templateTimestamp     ,

    final org.wheatgenetics.coordinate.model.AllGridsUniqueEntryModels allGridsUniqueEntryModels,
    @android.support.annotation.NonNull final
        org.wheatgenetics.coordinate.model.AllGridsUniqueEntryModels.Checker checker)
    {
        super(id, projectId, person, activeRow, activeCol, optionalFields, timestamp, templateId,
            title, code, rows, cols, generatedExcludedCellsAmount, initialExcludedCells,
            excludedRows, excludedCols, colNumbering, rowNumbering, entryLabel,
            templateOptionalFields, templateTimestamp, allGridsUniqueEntryModels);
        this.checker = checker;
    }
}
