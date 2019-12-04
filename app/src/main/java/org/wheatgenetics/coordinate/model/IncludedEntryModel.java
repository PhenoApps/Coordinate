package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * androidx.annotation.DrawableRes
 * androidx.annotation.IntRange
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 * androidx.annotation.RestrictTo
 * androidx.annotation.RestrictTo.Scope
 *
 * org.wheatgenetics.javalib.Utils
 *
 * org.wheatgenetics.coordinate.R
 *
 * org.wheatgenetics.coordinate.model.EntryModel
 * org.wheatgenetics.coordinate.model.ExcludedEntryModel
 */
public class IncludedEntryModel extends org.wheatgenetics.coordinate.model.EntryModel
{
    private java.lang.String value;

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    void uncheckedSetValue(@androidx.annotation.Nullable final java.lang.String value)
    { this.value = null == value ? null : value.trim(); }

    // region Constructors
    IncludedEntryModel(
    @androidx.annotation.IntRange(from = 1) final long gridId,
    @androidx.annotation.IntRange(from = 1) final int  row   ,
    @androidx.annotation.IntRange(from = 1) final int  col   ) { super(gridId, row, col); }

    public IncludedEntryModel(
    @androidx.annotation.IntRange(from = 1) final long             id       ,
    @androidx.annotation.IntRange(from = 1) final long             gridId   ,
    @androidx.annotation.IntRange(from = 1) final int              row      ,
    @androidx.annotation.IntRange(from = 1) final int              col      ,
                                            final java.lang.String value    ,
    @androidx.annotation.IntRange(from = 0) final long             timestamp)
    { super(id, gridId, row, col, timestamp); this.uncheckedSetValue(value); }

    public IncludedEntryModel(@androidx.annotation.NonNull final
        org.wheatgenetics.coordinate.model.ExcludedEntryModel excludedEntryModel)
    { super(excludedEntryModel); }
    // endregion

    // region Overridden Methods
    @java.lang.Override java.lang.String getSeedExportValue()
    { return org.wheatgenetics.javalib.Utils.replaceIfNull(this.getValue(),"BLANK_"); }

    @java.lang.Override @androidx.annotation.NonNull java.lang.String getDNAExportValue(
    final java.lang.String sample_id)
    {
        final java.lang.String value = this.getValue();
        if (null == value || value.length() < 1)
            return super.getDNAExportValue(sample_id);
        else
            return value;
    }

    @java.lang.Override java.lang.String getUserDefinedExportValue()
    { return org.wheatgenetics.javalib.Utils.makeEmptyIfNull(this.getValue()); }

    @java.lang.Override public java.lang.String getValue        () { return this.value     ; }
    @java.lang.Override public java.lang.String getDatabaseValue() { return this.getValue(); }

    @java.lang.Override @androidx.annotation.DrawableRes public int backgroundResource()
    {
        return this.valueIsEmpty() ?
            org.wheatgenetics.coordinate.R.drawable.empty_included_entry :
            org.wheatgenetics.coordinate.R.drawable.full_included_entry  ;
    }
    // endregion

    // region Public Methods
    public void setValue(@androidx.annotation.Nullable final java.lang.String value)
    { this.uncheckedSetValue(value); }

    public boolean valueIsEmpty()
    {
        final java.lang.String value = this.getValue();

        // noinspection SimplifiableConditionalExpression
        return null == value ? true : value.length() <= 0;
    }
    // endregion
}