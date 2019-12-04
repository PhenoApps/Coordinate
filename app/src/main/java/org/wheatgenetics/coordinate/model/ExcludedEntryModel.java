package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * androidx.annotation.DrawableRes
 * androidx.annotation.IntRange
 * androidx.annotation.NonNull
 *
 * org.wheatgenetics.coordinate.R
 *
 * org.wheatgenetics.coordinate.model.EntryModel
 * org.wheatgenetics.coordinate.model.IncludedEntryModel
 */
public class ExcludedEntryModel extends org.wheatgenetics.coordinate.model.EntryModel
{
    public static final java.lang.String DATABASE_VALUE = "excluded";

    // region Constructors
    ExcludedEntryModel(
    @androidx.annotation.IntRange(from = 1) final long gridId,
    @androidx.annotation.IntRange(from = 1) final int  row   ,
    @androidx.annotation.IntRange(from = 1) final int  col   ) { super(gridId, row, col); }

    public ExcludedEntryModel(
    @androidx.annotation.IntRange(from = 1) final long id       ,
    @androidx.annotation.IntRange(from = 1) final long gridId   ,
    @androidx.annotation.IntRange(from = 1) final int  row      ,
    @androidx.annotation.IntRange(from = 1) final int  col      ,
    @androidx.annotation.IntRange(from = 0) final long timestamp)
    { super(id, gridId, row, col, timestamp); }

    public ExcludedEntryModel(@androidx.annotation.NonNull final
        org.wheatgenetics.coordinate.model.IncludedEntryModel includedEntryModel)
    { super(includedEntryModel); }
    // endregion

    // region Overridden Methods
    @java.lang.Override        java.lang.String getSeedExportValue       () { return "exclude"; }
    @java.lang.Override        java.lang.String getUserDefinedExportValue() { return "exclude"; }
    @java.lang.Override public java.lang.String getValue                 () { return null     ; }

    @java.lang.Override public java.lang.String getDatabaseValue()
    { return org.wheatgenetics.coordinate.model.ExcludedEntryModel.DATABASE_VALUE; }

    @java.lang.Override @androidx.annotation.DrawableRes public int backgroundResource()
    { return org.wheatgenetics.coordinate.R.drawable.excluded_entry; }
    // endregion
}