package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * android.support.annotation.IntRange
 *
 * org.wheatgenetics.coordinate.model.EntryModel
 */
public class IncludedEntryModel extends org.wheatgenetics.coordinate.model.EntryModel
{
    private java.lang.String value;

    // region Constructors
    IncludedEntryModel(
    @android.support.annotation.IntRange(from = 1) final long gridId,
    @android.support.annotation.IntRange(from = 1) final int row    ,
    @android.support.annotation.IntRange(from = 1) final int col    ) { super(gridId, row, col); }

    public IncludedEntryModel(
    @android.support.annotation.IntRange(from = 1) final long id    ,
    @android.support.annotation.IntRange(from = 1) final long gridId,
    @android.support.annotation.IntRange(from = 1) final int row    ,
    @android.support.annotation.IntRange(from = 1) final int col    ,
    final java.lang.String value, final long timestamp)
    { super(id, gridId, row, col, timestamp); this.value = value; }
    // endregion

    void setValue(final java.lang.String value) { this.value = value; }

    public java.lang.String getValue() { return this.value ; }
}