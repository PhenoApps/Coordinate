package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * android.support.annotation.IntRange
 * android.support.annotation.NonNull
 * android.support.annotation.Nullable
 * android.support.annotation.RestrictTo
 * android.support.annotation.RestrictTo.Scope
 *
 * org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel
 * org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.CheckException
 * org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.Checker
 * org.wheatgenetics.coordinate.model.EntryModel
 * org.wheatgenetics.coordinate.model.EntryModels
 * org.wheatgenetics.coordinate.model.IncludedEntryModel
 */
public abstract class UniqueEntryModels extends org.wheatgenetics.coordinate.model.EntryModels
implements org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.Checker
{
    public abstract static class DuplicateCheckException
    extends org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.CheckException
    {
        DuplicateCheckException(@android.support.annotation.NonNull final java.lang.String message)
        { super(message);}
    }

    @android.support.annotation.Nullable private org.wheatgenetics.coordinate.model.EntryModel
    check(@android.support.annotation.Nullable final
        org.wheatgenetics.coordinate.model.EntryModel entryModel)
    throws org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.CheckException
    {
        if (null != entryModel)
            this.check(entryModel.getRow(), entryModel.getCol(), entryModel.getValue());   // throws
        return entryModel;
    }

    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    @java.lang.Override @android.support.annotation.NonNull
    org.wheatgenetics.coordinate.model.IncludedEntryModel makeButDontSetIncludedEntry(
    @android.support.annotation.IntRange(from = 1) final int row,
    @android.support.annotation.IntRange(from = 1) final int col)
    {
        return new org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel(
            this.getGridId(), row, col,this);
    }

    UniqueEntryModels(
    @android.support.annotation.IntRange(from = 1) final long gridId,
    @android.support.annotation.IntRange(from = 1) final int  rows  ,
    @android.support.annotation.IntRange(from = 1) final int  cols  ) { super(gridId, rows, cols); }

    // region org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.Checker Overridden Method
    @java.lang.Override @android.support.annotation.Nullable abstract public java.lang.String check(
    @android.support.annotation.IntRange(from = 1) final int              rowIndex,
    @android.support.annotation.IntRange(from = 1) final int              colIndex,
    @android.support.annotation.Nullable           final java.lang.String value   )
    throws org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.CheckException;
    // endregion

    public void checkThenSet(final org.wheatgenetics.coordinate.model.EntryModel entryModel)
    throws org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.CheckException
    { this.uncheckedSet(this.check(entryModel) /* throws CheckException */); }
}