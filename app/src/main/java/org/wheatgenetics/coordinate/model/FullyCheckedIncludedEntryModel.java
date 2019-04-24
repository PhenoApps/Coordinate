package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * android.support.annotation.IntRange
 * android.support.annotation.NonNull
 *
 * org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel
 * org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.CheckException
 * org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.Checker
 */
public class FullyCheckedIncludedEntryModel
extends org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel
{
    // region Constructors
    FullyCheckedIncludedEntryModel(
    @android.support.annotation.IntRange(from = 1) final long gridId,
    @android.support.annotation.IntRange(from = 1) final int  row   ,
    @android.support.annotation.IntRange(from = 1) final int  col   ,
    @android.support.annotation.NonNull            final
        org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.Checker checker)
    { super(gridId, row, col, checker); }

    public FullyCheckedIncludedEntryModel(
    @android.support.annotation.IntRange(from = 1) final long             id       ,
    @android.support.annotation.IntRange(from = 1) final long             gridId   ,
    @android.support.annotation.IntRange(from = 1) final int              row      ,
    @android.support.annotation.IntRange(from = 1) final int              col      ,
                                                   final java.lang.String value    ,
    @android.support.annotation.IntRange(from = 0) final long             timestamp,
    @android.support.annotation.NonNull            final
        org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.Checker checker)
    throws org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.CheckException
    {
        super(id, gridId, row, col, checker.check( /* throws */
                row, col, value), timestamp, checker);
    }
    // endregion
}