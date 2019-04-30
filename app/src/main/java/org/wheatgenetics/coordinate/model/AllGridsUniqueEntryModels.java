package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * android.support.annotation.IntRange
 * android.support.annotation.NonNull
 *
 * org.wheatgenetics.coordinate.model.DatabaseUniqueEntryModels
 */
public class AllGridsUniqueEntryModels
extends org.wheatgenetics.coordinate.model.DatabaseUniqueEntryModels
{
    public AllGridsUniqueEntryModels(
    @android.support.annotation.IntRange(from = 1) final long gridId,
    @android.support.annotation.IntRange(from = 1) final int  rows  ,
    @android.support.annotation.IntRange(from = 1) final int  cols  ,
    @android.support.annotation.NonNull            final
        org.wheatgenetics.coordinate.model.AllGridsUniqueEntryModels.Checker checker)
    { super(gridId, rows, cols,"database", checker); }
}