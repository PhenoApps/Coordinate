package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * androidx.annotation.IntRange
 * androidx.annotation.NonNull
 *
 * org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel
 * org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.CheckException
 * org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.Checker
 */
public class FullyCheckedIncludedEntryModel
extends org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel
{
    FullyCheckedIncludedEntryModel(
    @androidx.annotation.IntRange(from = 1) final long gridId,
    @androidx.annotation.IntRange(from = 1) final int  row   ,
    @androidx.annotation.IntRange(from = 1) final int  col   ,
    @androidx.annotation.NonNull            final
        org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.Checker checker)
    { super(gridId, row, col, checker); }

    public FullyCheckedIncludedEntryModel(
    @androidx.annotation.IntRange(from = 1) final long             id       ,
    @androidx.annotation.IntRange(from = 1) final long             gridId   ,
    @androidx.annotation.IntRange(from = 1) final int              row      ,
    @androidx.annotation.IntRange(from = 1) final int              col      ,
                                            final java.lang.String value    ,
    @androidx.annotation.IntRange(from = 0) final long             timestamp,
    @androidx.annotation.NonNull            final
        org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.Checker checker)
    throws org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.CheckException
    {
        super(id, gridId, row, col, checker.check( /* throws CheckException */
            row, col, value), timestamp, checker);
    }
}