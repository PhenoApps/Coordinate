package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * android.support.annotation.IntRange
 *
 * org.wheatgenetics.coordinate.model.EntryModel
 */
public class ExcludedEntryModel extends org.wheatgenetics.coordinate.model.EntryModel
{
    ExcludedEntryModel(
    @android.support.annotation.IntRange(from = 1) final long gridId,
    @android.support.annotation.IntRange(from = 1) final int row    ,
    @android.support.annotation.IntRange(from = 1) final int col    ) { super(gridId, row, col); }

    public ExcludedEntryModel(
    @android.support.annotation.IntRange(from = 1) final long id       ,
    @android.support.annotation.IntRange(from = 1) final long gridId   ,
    @android.support.annotation.IntRange(from = 1) final int row       ,
    @android.support.annotation.IntRange(from = 1) final int col       ,
                                                   final long timestamp)
    { super(id, gridId, row, col, timestamp); }
}