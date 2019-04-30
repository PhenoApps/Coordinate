package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * android.support.annotation.IntRange
 * android.support.annotation.NonNull
 *
 * org.wheatgenetics.coordinate.model.DatabaseUniqueEntryModels
 */
public class CurrentProjectUniqueEntryModels
extends org.wheatgenetics.coordinate.model.DatabaseUniqueEntryModels
{
    public CurrentProjectUniqueEntryModels(
    @android.support.annotation.IntRange(from = 1) final long gridId,
    @android.support.annotation.IntRange(from = 1) final int  rows  ,
    @android.support.annotation.IntRange(from = 1) final int  cols  ,
    @android.support.annotation.NonNull            final
        org.wheatgenetics.coordinate.model.CurrentProjectUniqueEntryModels.Checker checker)
    { super(gridId, rows, cols,"current project", checker); }
}