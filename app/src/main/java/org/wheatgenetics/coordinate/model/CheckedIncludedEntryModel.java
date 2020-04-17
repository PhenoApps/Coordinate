package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * androidx.annotation.IntRange
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 *
 * org.wheatgenetics.coordinate.R
 * org.wheatgenetics.coordinate.StringGetter
 *
 * org.wheatgenetics.coordinate.model.ExcludedEntryModel
 * org.wheatgenetics.coordinate.model.IncludedEntryModel
 */
public class CheckedIncludedEntryModel extends org.wheatgenetics.coordinate.model.IncludedEntryModel
{
    // region Types
    public abstract static class CheckException extends java.lang.Exception
    {
        CheckException(@androidx.annotation.NonNull final java.lang.String message)
        { super(message); }
    }

    @java.lang.SuppressWarnings({"UnnecessaryInterfaceModifier"}) public interface Checker
    {
        @androidx.annotation.Nullable public abstract java.lang.String check(
        @androidx.annotation.IntRange(from = 1) int              rowIndex,
        @androidx.annotation.IntRange(from = 1) int              colIndex,
        @androidx.annotation.Nullable           java.lang.String value   )
        throws org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.CheckException;
    }
    // endregion

    @androidx.annotation.NonNull private final
        org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.Checker checker;

    // region Constructors
    CheckedIncludedEntryModel(
    @androidx.annotation.IntRange(from = 1) final long gridId,
    @androidx.annotation.IntRange(from = 1) final int  row   ,
    @androidx.annotation.IntRange(from = 1) final int  col   ,
    @androidx.annotation.NonNull            final
        org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.Checker     checker     ,
    @androidx.annotation.NonNull final org.wheatgenetics.coordinate.StringGetter stringGetter)
    { super(gridId, row, col, stringGetter); this.checker = checker; }

    public CheckedIncludedEntryModel(
    @androidx.annotation.IntRange(from = 1) final long             id       ,
    @androidx.annotation.IntRange(from = 1) final long             gridId   ,
    @androidx.annotation.IntRange(from = 1) final int              row      ,
    @androidx.annotation.IntRange(from = 1) final int              col      ,
                                            final java.lang.String value    ,
    @androidx.annotation.IntRange(from = 0) final long             timestamp,
    @androidx.annotation.NonNull            final
        org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.Checker     checker     ,
    @androidx.annotation.NonNull final org.wheatgenetics.coordinate.StringGetter stringGetter)
    { super(id, gridId, row, col, value, timestamp, stringGetter); this.checker = checker; }

    public CheckedIncludedEntryModel(@androidx.annotation.NonNull final
        org.wheatgenetics.coordinate.model.ExcludedEntryModel excludedEntryModel,
    @androidx.annotation.NonNull final
        org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.Checker     checker     ,
    @androidx.annotation.NonNull final org.wheatgenetics.coordinate.StringGetter stringGetter)
    { super(excludedEntryModel, stringGetter); this.checker = checker; }
    // endregion

    @java.lang.Override public void setValue(
    @androidx.annotation.Nullable final java.lang.String value)
    {
        throw new java.lang.UnsupportedOperationException(this.stringGetter().get(
            org.wheatgenetics.coordinate.R.string.CallCheckThenSetValueInstead));
    }

    public void checkThenSetValue(@androidx.annotation.Nullable final java.lang.String value)
    throws org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.CheckException
    {
        this.uncheckedSetValue(
            this.checker.check(this.getRow(), this.getCol(), value) /* throws */);
    }
}