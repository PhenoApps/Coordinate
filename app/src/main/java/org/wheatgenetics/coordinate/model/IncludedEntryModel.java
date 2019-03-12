package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * android.support.annotation.DrawableRes
 * android.support.annotation.IntRange
 * android.support.annotation.RestrictTo
 * android.support.annotation.RestrictTo.Scope
 * android.support.annotation.NonNull
 * android.support.annotation.Nullable
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
    public abstract static class CheckException extends java.lang.Exception
    {
        CheckException(@android.support.annotation.NonNull final java.lang.String message)
        { super(message); }
    }

    private java.lang.String value;

    // region Constructors
    IncludedEntryModel(
    @android.support.annotation.IntRange(from = 1) final long gridId,
    @android.support.annotation.IntRange(from = 1) final int  row   ,
    @android.support.annotation.IntRange(from = 1) final int  col   ) { super(gridId, row, col); }

    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    IncludedEntryModel(
    @android.support.annotation.IntRange(from = 1) final long id       ,
    @android.support.annotation.IntRange(from = 1) final long gridId   ,
    @android.support.annotation.IntRange(from = 1) final int  row      ,
    @android.support.annotation.IntRange(from = 1) final int  col      ,
    @android.support.annotation.IntRange(from = 0) final long timestamp)
    { super(id, gridId, row, col, timestamp); }

    public IncludedEntryModel(
    @android.support.annotation.IntRange(from = 1) final long             id       ,
    @android.support.annotation.IntRange(from = 1) final long             gridId   ,
    @android.support.annotation.IntRange(from = 1) final int              row      ,
    @android.support.annotation.IntRange(from = 1) final int              col      ,
                                                   final java.lang.String value    ,
    @android.support.annotation.IntRange(from = 0) final long             timestamp)
    throws org.wheatgenetics.coordinate.model.IncludedEntryModel.CheckException
    { this(id, gridId, row, col, timestamp); this.setValue(value); }

    public IncludedEntryModel(@android.support.annotation.NonNull
        final org.wheatgenetics.coordinate.model.ExcludedEntryModel excludedEntryModel)
    { super(excludedEntryModel); }
    // endregion

    // region Overridden Methods
    @java.lang.Override java.lang.String getSeedExportValue()
    { return org.wheatgenetics.javalib.Utils.replaceIfNull(this.getValue(),"BLANK_"); }

    @android.support.annotation.NonNull @java.lang.Override java.lang.String getDNAExportValue(
    final java.lang.String sample_id)
    {
        final java.lang.String result = this.getValue();
        if (null == result || result.length() < 1)
            return super.getDNAExportValue(sample_id);
        else
            return result;
    }

    @java.lang.Override java.lang.String getUserDefinedExportValue()
    { return org.wheatgenetics.javalib.Utils.makeEmptyIfNull(this.getValue()); }

    @java.lang.Override public java.lang.String getValue        () { return this.value     ; }
    @java.lang.Override public java.lang.String getDatabaseValue() { return this.getValue(); }

    @java.lang.Override @android.support.annotation.DrawableRes public int backgroundResource()
    {
        return this.valueIsEmpty() ?
            org.wheatgenetics.coordinate.R.drawable.empty_included_entry :
            org.wheatgenetics.coordinate.R.drawable.full_included_entry  ;
    }
    // endregion

    // region Public Methods
    public void setValue(@android.support.annotation.Nullable final java.lang.String value)
    throws org.wheatgenetics.coordinate.model.IncludedEntryModel.CheckException
    { this.value = null == value ? null : value.trim(); }

    public boolean valueIsEmpty()
    {
        final java.lang.String value = this.getValue();

        // noinspection SimplifiableConditionalExpression
        return null == value ? true : value.length() <= 0;
    }
    // endregion
}