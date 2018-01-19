package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * android.support.annotation.IntRange
 *
 * org.wheatgenetics.javalib.Utils
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
        final java.lang.String value                = this.getValue();
        final int              empty_included_entry =
            org.wheatgenetics.coordinate.R.drawable.empty_included_entry;
        if (null == value)
            return empty_included_entry;
        else
            return value.length() > 0 ?
                org.wheatgenetics.coordinate.R.drawable.full_included_entry : empty_included_entry;
    }
    // endregion

    // region Package Methods
    @java.lang.Override
    java.lang.String getSeedExportValue()
    { return org.wheatgenetics.javalib.Utils.replaceIfNull(this.getValue(), "BLANK_"); }

    @java.lang.Override
    java.lang.String getDNAExportValue(final java.lang.String sample_id)
    {
        final java.lang.String result = this.getValue();
        if (null == result || result.length() == 0)
            return super.getDNAExportValue(sample_id);
        else
            return result;
    }

    @java.lang.Override
    java.lang.String getUserDefinedExportValue()
    { return org.wheatgenetics.javalib.Utils.makeEmptyIfNull(this.getValue()); }
    // endregion

    public void setValue(final java.lang.String value)
    { this.value = null == value ? null : value.trim(); }
}