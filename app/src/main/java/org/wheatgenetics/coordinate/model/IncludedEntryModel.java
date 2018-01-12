package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * android.support.annotation.IntRange
 *
 * org.wheatgenetics.coordinate.R
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

    // region Overridden Methods
    @java.lang.Override
    public java.lang.String getValue() { return this.value; }

    public int backgroundResource()
    {
        final int empty_included_entry =
            org.wheatgenetics.coordinate.R.drawable.empty_included_entry;
        if (null == this.value)
            return empty_included_entry;
        else
            return value.length() > 0 ?
                org.wheatgenetics.coordinate.R.drawable.full_included_entry : empty_included_entry;
    }
    // endregion

    void setValue(final java.lang.String value)
    { this.value = null == value ? null : value.trim(); }
}