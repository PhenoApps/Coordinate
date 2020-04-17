package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * androidx.annotation.IntRange
 * androidx.annotation.NonNull
 *
 * org.wheatgenetics.coordinate.R
 * org.wheatgenetics.coordinate.StringGetter
 *
 * org.wheatgenetics.coordinate.model.DatabaseUniqueEntryModels
 */
public class AllGridsUniqueEntryModels
extends org.wheatgenetics.coordinate.model.DatabaseUniqueEntryModels
{
    public AllGridsUniqueEntryModels(
    @androidx.annotation.IntRange(from = 1) final long gridId,
    @androidx.annotation.IntRange(from = 1) final int  rows  ,
    @androidx.annotation.IntRange(from = 1) final int  cols  ,
    @androidx.annotation.NonNull            final
        org.wheatgenetics.coordinate.model.AllGridsUniqueEntryModels.Checker     checker     ,
    @androidx.annotation.NonNull final org.wheatgenetics.coordinate.StringGetter stringGetter)
    {
        // noinspection ConstantConditions
        super(gridId, rows, cols, /* scope => */ null == stringGetter.get(
                    org.wheatgenetics.coordinate.R.string.AllGridsUniqueEntryModelsScope) ?
                "database"                                                                :
                stringGetter.get(
                    org.wheatgenetics.coordinate.R.string.AllGridsUniqueEntryModelsScope) ,
            checker, stringGetter);
    }
}