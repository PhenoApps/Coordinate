package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * android.support.annotation.IntRange
 * android.support.annotation.NonNull
 * android.support.annotation.RestrictTo
 * android.support.annotation.RestrictTo.Scope
 *
 * org.wheatgenetics.coordinate.model.EntryModel
 * org.wheatgenetics.coordinate.model.FullyCheckedIncludedEntryModel
 * org.wheatgenetics.coordinate.model.IncludedEntryModel
 * org.wheatgenetics.coordinate.model.UniqueEntryModels
 */
public abstract class FullyUniqueEntryModels
extends org.wheatgenetics.coordinate.model.UniqueEntryModels
{
    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    @java.lang.Override @android.support.annotation.NonNull
    org.wheatgenetics.coordinate.model.IncludedEntryModel makeButDontSetIncludedEntry(
    @android.support.annotation.IntRange(from = 1) final int row,
    @android.support.annotation.IntRange(from = 1) final int col)
    {
        return new org.wheatgenetics.coordinate.model.FullyCheckedIncludedEntryModel(
            this.getGridId(), row, col,this);
    }

    FullyUniqueEntryModels(
    @android.support.annotation.IntRange(from = 1) final long gridId,
    @android.support.annotation.IntRange(from = 1) final int  rows  ,
    @android.support.annotation.IntRange(from = 1) final int  cols  ) { super(gridId, rows, cols); }

    @java.lang.Override
    public void set(final org.wheatgenetics.coordinate.model.EntryModel entryModel)
    { throw new java.lang.UnsupportedOperationException("Call checkThenSet() instead"); }
}