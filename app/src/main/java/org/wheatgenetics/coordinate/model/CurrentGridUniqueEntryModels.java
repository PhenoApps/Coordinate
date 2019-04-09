package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * android.support.annotation.IntRange
 * android.support.annotation.Nullable
 *
 * org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.CheckException
 * org.wheatgenetics.coordinate.model.EntryModel
 * org.wheatgenetics.coordinate.model.EntryModels.Processor
 * org.wheatgenetics.coordinate.model.IncludedEntryModel
 * org.wheatgenetics.coordinate.model.UniqueEntryModels
 * org.wheatgenetics.coordinate.model.UniqueEntryModels.DuplicateCheckException
 */
public class CurrentGridUniqueEntryModels
extends org.wheatgenetics.coordinate.model.UniqueEntryModels
{
    public static class CurrentGridDuplicateCheckException
    extends org.wheatgenetics.coordinate.model.UniqueEntryModels.DuplicateCheckException
    {
        CurrentGridDuplicateCheckException()
        { super("The current grid already has an entry with that value."); }
    }

    public CurrentGridUniqueEntryModels(
    @android.support.annotation.IntRange(from = 1) final long gridId,
    @android.support.annotation.IntRange(from = 1) final int  rows  ,
    @android.support.annotation.IntRange(from = 1) final int  cols  ) { super(gridId, rows, cols); }

    @java.lang.Override @android.support.annotation.Nullable public java.lang.String check(
    @android.support.annotation.IntRange(from = 1) final int              rowIndex,
    @android.support.annotation.IntRange(from = 1) final int              colIndex,
    @android.support.annotation.Nullable           final java.lang.String value   )
    throws org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.CheckException
    {
        if (null == value)
            return null;
        else
        {
            @java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
            class Processor extends java.lang.Object
            implements org.wheatgenetics.coordinate.model.EntryModels.Processor
            {
                // region Fields
                @android.support.annotation.IntRange(from = 1) private final int rowIndex, colIndex;
                @android.support.annotation.Nullable           private final java.lang.String value;

                private boolean duplicateFound = false;
                // endregion

                private Processor(
                @android.support.annotation.IntRange(from = 1) final int              rowIndex,
                @android.support.annotation.IntRange(from = 1) final int              colIndex,
                @android.support.annotation.Nullable           final java.lang.String value   )
                { super(); this.rowIndex = rowIndex; this.colIndex = colIndex; this.value = value; }

                @java.lang.Override public void process(
                final org.wheatgenetics.coordinate.model.EntryModel entryModel)
                {
                    if (!this.duplicateFound)
                        if (entryModel instanceof
                        org.wheatgenetics.coordinate.model.IncludedEntryModel)
                            if (entryModel.getRow() != this.rowIndex
                            ||  entryModel.getCol() != this.colIndex)
                            {
                                final java.lang.String existingValue = entryModel.getValue();
                                if (null != existingValue && existingValue.equals(this.value))
                                    this.duplicateFound = true;
                            }
                }

                private boolean getDuplicateFound() { return this.duplicateFound; }
            }

            final Processor processor = new Processor(rowIndex, colIndex, value);
            this.processAll(processor);
            if (processor.getDuplicateFound())
                throw new org.wheatgenetics.coordinate.model
                    .CurrentGridUniqueEntryModels.CurrentGridDuplicateCheckException();
            else
                return value;
        }
    }
}