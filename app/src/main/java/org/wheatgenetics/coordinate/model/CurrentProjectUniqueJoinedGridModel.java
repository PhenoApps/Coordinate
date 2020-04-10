package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * androidx.annotation.IntRange
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 * androidx.annotation.RestrictTo
 * androidx.annotation.RestrictTo.Scope
 *
 * org.wheatgenetics.coordinate.StringGetter
 *
 * org.wheatgenetics.coordinate.model.CurrentGridUniqueJoinedGridModel
 * org.wheatgenetics.coordinate.model.CurrentProjectUniqueEntryModels
 * org.wheatgenetics.coordinate.model.CurrentProjectUniqueEntryModels.Checker
 * org.wheatgenetics.coordinate.model.EntryModels
 */
public class CurrentProjectUniqueJoinedGridModel
extends org.wheatgenetics.coordinate.model.CurrentGridUniqueJoinedGridModel
{
    @androidx.annotation.NonNull private final
        org.wheatgenetics.coordinate.model.CurrentProjectUniqueEntryModels.Checker checker;

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @java.lang.Override @androidx.annotation.NonNull
    org.wheatgenetics.coordinate.model.EntryModels makeEntryModels(
    @androidx.annotation.IntRange(from = 1) final int rows,
    @androidx.annotation.IntRange(from = 1) final int cols)
    {
        return new org.wheatgenetics.coordinate.model.CurrentProjectUniqueEntryModels(
            /* gridId => */ this.getId(), rows, cols, this.checker, this.stringGetter());
    }

    /** Used by CurrentProjectUniqueGridsTable. */
    public CurrentProjectUniqueJoinedGridModel(
    @androidx.annotation.IntRange(from = 1) final long                           id            ,
    @androidx.annotation.IntRange(from = 0) final long                           projectId     ,
                                            final java.lang.String               person        ,
    @androidx.annotation.IntRange(from = 0) final int                            activeRow     ,
    @androidx.annotation.IntRange(from = 0) final int                            activeCol     ,
    @androidx.annotation.Nullable           final java.lang.String               optionalFields,
    @androidx.annotation.NonNull final org.wheatgenetics.coordinate.StringGetter stringGetter  ,
    @androidx.annotation.IntRange(from = 0) final long                           timestamp     ,

    @androidx.annotation.IntRange(from = 1        ) final long             templateId     ,
                                                    final java.lang.String title          ,
    @androidx.annotation.IntRange(from = 0, to = 2) final int              code           ,
    @androidx.annotation.IntRange(from = 1        ) final int              rows           ,
    @androidx.annotation.IntRange(from = 1        ) final int              cols           ,
    @androidx.annotation.IntRange(from = 0        ) final int generatedExcludedCellsAmount,
    @androidx.annotation.Nullable                   final java.lang.String initialExcludedCells  ,
    @androidx.annotation.Nullable                   final java.lang.String excludedRows          ,
    @androidx.annotation.Nullable                   final java.lang.String excludedCols          ,
    @androidx.annotation.IntRange(from = 0, to = 1) final int              colNumbering          ,
    @androidx.annotation.IntRange(from = 0, to = 1) final int              rowNumbering          ,
                                                    final java.lang.String entryLabel            ,
    @androidx.annotation.Nullable                   final java.lang.String templateOptionalFields,
    @androidx.annotation.IntRange(from = 0)         final long             templateTimestamp     ,

    final org.wheatgenetics.coordinate.model.CurrentProjectUniqueEntryModels
        currentProjectUniqueEntryModels,
    @androidx.annotation.NonNull final
        org.wheatgenetics.coordinate.model.CurrentProjectUniqueEntryModels.Checker checker)
    {
        super(id, projectId, person, activeRow, activeCol, optionalFields, stringGetter, timestamp,
            templateId, title, code, rows, cols, generatedExcludedCellsAmount, initialExcludedCells,
            excludedRows, excludedCols, colNumbering, rowNumbering, entryLabel,
            templateOptionalFields, templateTimestamp, currentProjectUniqueEntryModels);
        this.checker = checker;
    }
}