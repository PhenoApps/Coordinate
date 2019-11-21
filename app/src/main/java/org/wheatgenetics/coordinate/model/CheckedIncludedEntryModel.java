package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * android.support.annotation.IntRange
 * android.support.annotation.NonNull
 * android.support.annotation.Nullable
 *
 * org.wheatgenetics.coordinate.model.ExcludedEntryModel
 * org.wheatgenetics.coordinate.model.IncludedEntryModel
 */
public class CheckedIncludedEntryModel extends org.wheatgenetics.coordinate.model.IncludedEntryModel
{
    // region Types
    public abstract static class CheckException extends java.lang.Exception
    {
        CheckException(@android.support.annotation.NonNull final java.lang.String message)
        { super(message); }
    }

    @java.lang.SuppressWarnings({"UnnecessaryInterfaceModifier"}) public interface Checker
    {
        @android.support.annotation.Nullable public abstract java.lang.String check(
        @android.support.annotation.IntRange(from = 1) int              rowIndex,
        @android.support.annotation.IntRange(from = 1) int              colIndex,
        @android.support.annotation.Nullable           java.lang.String value   )
        throws org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.CheckException;
    }
    // endregion

    @android.support.annotation.NonNull private final
        org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.Checker checker;

    // region Constructors
    CheckedIncludedEntryModel(
    @android.support.annotation.IntRange(from = 1) final long gridId,
    @android.support.annotation.IntRange(from = 1) final int  row   ,
    @android.support.annotation.IntRange(from = 1) final int  col   ,
    @android.support.annotation.NonNull            final
        org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.Checker checker)
    { super(gridId, row, col); this.checker = checker; }

    public CheckedIncludedEntryModel(
    @android.support.annotation.IntRange(from = 1) final long             id       ,
    @android.support.annotation.IntRange(from = 1) final long             gridId   ,
    @android.support.annotation.IntRange(from = 1) final int              row      ,
    @android.support.annotation.IntRange(from = 1) final int              col      ,
                                                   final java.lang.String value    ,
    @android.support.annotation.IntRange(from = 0) final long             timestamp,
    @android.support.annotation.NonNull            final
        org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.Checker checker)
    { super(id, gridId, row, col, value, timestamp); this.checker = checker; }

    public CheckedIncludedEntryModel(@android.support.annotation.NonNull final
        org.wheatgenetics.coordinate.model.ExcludedEntryModel excludedEntryModel,
    @android.support.annotation.NonNull final
        org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.Checker checker)
    { super(excludedEntryModel); this.checker = checker; }
    // endregion

    @java.lang.Override public void setValue(
    @android.support.annotation.Nullable final java.lang.String value)
    { throw new java.lang.UnsupportedOperationException("Call checkThenSetValue() instead"); }

    public void checkThenSetValue(@android.support.annotation.Nullable final java.lang.String value)
    throws org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.CheckException
    {
        this.uncheckedSetValue(
            this.checker.check(this.getRow(), this.getCol(), value) /* throws */);
    }
}