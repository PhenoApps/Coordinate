package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * androidx.annotation.IntRange
 * androidx.annotation.NonNull
 *
 * org.wheatgenetics.coordinate.StringGetter
 *
 * org.wheatgenetics.coordinate.model.DatabaseUniqueEntryModels
 */
public class CurrentProjectUniqueEntryModels
extends org.wheatgenetics.coordinate.model.DatabaseUniqueEntryModels
{
    public CurrentProjectUniqueEntryModels(
    @androidx.annotation.IntRange(from = 1) final long gridId,
    @androidx.annotation.IntRange(from = 1) final int  rows  ,
    @androidx.annotation.IntRange(from = 1) final int  cols  ,
    @androidx.annotation.NonNull            final
        org.wheatgenetics.coordinate.model.CurrentProjectUniqueEntryModels.Checker checker     ,
    @androidx.annotation.NonNull final org.wheatgenetics.coordinate.StringGetter   stringGetter)
    { super(gridId, rows, cols,"current project", checker, stringGetter); }
}