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
 * org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.CheckException
 * org.wheatgenetics.coordinate.model.CurrentGridUniqueEntryModels
 * org.wheatgenetics.coordinate.model.EntryModel
 * org.wheatgenetics.coordinate.model.EntryModels
 * org.wheatgenetics.coordinate.model.JoinedGridModel
 */
public class CurrentGridUniqueJoinedGridModel
extends org.wheatgenetics.coordinate.model.JoinedGridModel
{
    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @java.lang.Override @androidx.annotation.NonNull
    org.wheatgenetics.coordinate.model.EntryModels makeEntryModels(
    @androidx.annotation.IntRange(from = 1) final int rows,
    @androidx.annotation.IntRange(from = 1) final int cols)
    {
        return new org.wheatgenetics.coordinate.model.CurrentGridUniqueEntryModels(
            /* gridId => */ this.getId(), /* rows => */ rows, /* cols => */ cols);
    }

    /** Used by CurrentGridUniqueGridsTable. */
    public CurrentGridUniqueJoinedGridModel(
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

    final org.wheatgenetics.coordinate.model.CurrentGridUniqueEntryModels
        currentGridUniqueEntryModels)
    {
        super(id, projectId, person, activeRow, activeCol, optionalFields, stringGetter, timestamp,
            templateId, title, code, rows, cols, generatedExcludedCellsAmount, initialExcludedCells,
            excludedRows, excludedCols, colNumbering, rowNumbering, entryLabel,
            templateOptionalFields, templateTimestamp, currentGridUniqueEntryModels);
    }

    @java.lang.Override
    public void setEntryModel(final org.wheatgenetics.coordinate.model.EntryModel entryModel)
    { throw new java.lang.UnsupportedOperationException("Call checkThenSetEntryModel() instead"); }

    public void checkThenSetEntryModel(
    final org.wheatgenetics.coordinate.model.EntryModel entryModel)
    throws org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.CheckException
    {
        if (!this.entryModelsIsNull())
            // noinspection ConstantConditions
            ((org.wheatgenetics.coordinate.model.CurrentGridUniqueEntryModels)
                this.getEntryModels()).checkThenSet(entryModel);                           // throws
    }
}