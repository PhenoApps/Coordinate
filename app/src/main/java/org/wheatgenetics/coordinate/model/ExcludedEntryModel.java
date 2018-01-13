package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * android.support.annotation.IntRange
 *
 * org.wheatgenetics.coordinate.BuildConfig
 * org.wheatgenetics.coordinate.R
 *
 * org.wheatgenetics.coordinate.model.EntryModel
 */
public class ExcludedEntryModel extends org.wheatgenetics.coordinate.model.EntryModel
{
    // region Constructors
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
    // endregion

    // region Overridden Methods
    @java.lang.Override
    public java.lang.String getValue() { return null; }

    @java.lang.Override
    public int backgroundResource()
    { return org.wheatgenetics.coordinate.R.drawable.excluded_entry; }
    // endregion
}