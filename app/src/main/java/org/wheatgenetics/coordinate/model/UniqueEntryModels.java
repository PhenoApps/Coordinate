package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * androidx.annotation.IntRange
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 * androidx.annotation.RestrictTo
 * androidx.annotation.RestrictTo.Scope
 *
 * org.wheatgenetics.coordinate.StringGetter
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
        DuplicateCheckException(@androidx.annotation.NonNull final java.lang.String message)
        { super(message);}
    }

    @androidx.annotation.Nullable private org.wheatgenetics.coordinate.model.EntryModel
    check(@androidx.annotation.Nullable final org.wheatgenetics.coordinate.model.EntryModel
        entryModel)
    throws org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.CheckException
    {
        if (null != entryModel)
            this.check(entryModel.getRow(), entryModel.getCol(), entryModel.getValue());   // throws
        return entryModel;
    }

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @java.lang.Override @androidx.annotation.NonNull
    org.wheatgenetics.coordinate.model.IncludedEntryModel makeButDontSetIncludedEntry(
    @androidx.annotation.IntRange(from = 1) final int row,
    @androidx.annotation.IntRange(from = 1) final int col)
    {
        return new org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel(
            this.getGridId(), row, col,this, this.stringGetter());
    }

    UniqueEntryModels(
    @androidx.annotation.IntRange(from = 1) final long                                 gridId      ,
    @androidx.annotation.IntRange(from = 1) final int                                  rows        ,
    @androidx.annotation.IntRange(from = 1) final int                                  cols        ,
    @androidx.annotation.NonNull       final org.wheatgenetics.coordinate.StringGetter stringGetter)
    { super(gridId, rows, cols, stringGetter); }

    // region org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.Checker Overridden Method
    @java.lang.Override @androidx.annotation.Nullable abstract public java.lang.String check(
    @androidx.annotation.IntRange(from = 1) final int              rowIndex,
    @androidx.annotation.IntRange(from = 1) final int              colIndex,
    @androidx.annotation.Nullable           final java.lang.String value   )
    throws org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.CheckException;
    // endregion

    public void checkThenSet(final org.wheatgenetics.coordinate.model.EntryModel entryModel)
    throws org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.CheckException
    { this.uncheckedSet(this.check(entryModel) /* throws CheckException */); }
}