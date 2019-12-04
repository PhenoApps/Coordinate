package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * androidx.annotation.IntRange
 * androidx.annotation.NonNull
 * androidx.annotation.RestrictTo
 * androidx.annotation.RestrictTo.Scope
 *
 * org.wheatgenetics.coordinate.model.EntryModel
 * org.wheatgenetics.coordinate.model.FullyCheckedIncludedEntryModel
 * org.wheatgenetics.coordinate.model.IncludedEntryModel
 * org.wheatgenetics.coordinate.model.UniqueEntryModels
 */
public abstract class FullyUniqueEntryModels
extends org.wheatgenetics.coordinate.model.UniqueEntryModels
{
    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @java.lang.Override @androidx.annotation.NonNull
    org.wheatgenetics.coordinate.model.IncludedEntryModel makeButDontSetIncludedEntry(
    @androidx.annotation.IntRange(from = 1) final int row,
    @androidx.annotation.IntRange(from = 1) final int col)
    {
        return new org.wheatgenetics.coordinate.model.FullyCheckedIncludedEntryModel(
            this.getGridId(), row, col,this);
    }

    FullyUniqueEntryModels(
    @androidx.annotation.IntRange(from = 1) final long gridId,
    @androidx.annotation.IntRange(from = 1) final int  rows  ,
    @androidx.annotation.IntRange(from = 1) final int  cols  ) { super(gridId, rows, cols); }

    @java.lang.Override
    public void set(final org.wheatgenetics.coordinate.model.EntryModel entryModel)
    { throw new java.lang.UnsupportedOperationException("Call checkThenSet() instead"); }
}