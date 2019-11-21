package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * android.support.annotation.IntRange
 * android.support.annotation.NonNull
 * android.support.annotation.Nullable
 * android.support.annotation.RestrictTo
 * android.support.annotation.RestrictTo.Scope
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
    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    @java.lang.Override @android.support.annotation.NonNull
    org.wheatgenetics.coordinate.model.EntryModels makeEntryModels(
    @android.support.annotation.IntRange(from = 1) final int rows,
    @android.support.annotation.IntRange(from = 1) final int cols)
    {
        return new org.wheatgenetics.coordinate.model.CurrentGridUniqueEntryModels(
            /* gridId => */ this.getId(), /* rows => */ rows, /* cols => */ cols);
    }

    /** Used by CurrentGridUniqueGridsTable. */
    public CurrentGridUniqueJoinedGridModel(
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

    final org.wheatgenetics.coordinate.model.CurrentGridUniqueEntryModels
        currentGridUniqueEntryModels)
    {
        super(id, projectId, person, activeRow, activeCol, optionalFields, timestamp, templateId,
            title, code, rows, cols, generatedExcludedCellsAmount, initialExcludedCells,
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