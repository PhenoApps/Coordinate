package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * android.support.annotation.IntRange
 *
 * org.wheatgenetics.coordinate.model.EntryModel
 * org.wheatgenetics.coordinate.model.ExcludedEntryModel
 * org.wheatgenetics.coordinate.model.IncludedEntryModel
 * org.wheatgenetics.coordinate.model.Model
 */
@java.lang.SuppressWarnings("ClassExplicitlyExtendsObject")
public class EntryModels extends java.lang.Object
{
    @java.lang.SuppressWarnings("UnnecessaryInterfaceModifier")
    public interface Processor
    { public abstract void process(org.wheatgenetics.coordinate.model.EntryModel entryModel); }

    // region Fields
    private final long                                          gridId             ;
    private final org.wheatgenetics.coordinate.model.EntryModel entryModelArray[][];
    // endregion

    public EntryModels(
    @android.support.annotation.IntRange(from = 1) final long gridId,
    @android.support.annotation.IntRange(from = 1) final int  rows  ,
    @android.support.annotation.IntRange(from = 1) final int  cols  )
    {
        super();

        this.gridId          = org.wheatgenetics.coordinate.model.Model.valid(gridId)       ;
        this.entryModelArray = new org.wheatgenetics.coordinate.model.EntryModel[rows][cols];
    }

    // region Package Methods
    void makeExcludedEntry(
    @android.support.annotation.IntRange(from = 1) final int row,
    @android.support.annotation.IntRange(from = 1) final int col)
    {
        this.entryModelArray[row - 1][col - 1] =
            new org.wheatgenetics.coordinate.model.ExcludedEntryModel(this.gridId, row, col);
    }

    void makeIncludedEntry(
    @android.support.annotation.IntRange(from = 1) final int row,
    @android.support.annotation.IntRange(from = 1) final int col)
    {
        this.entryModelArray[row - 1][col - 1] =
            new org.wheatgenetics.coordinate.model.IncludedEntryModel(this.gridId, row, col);
    }

    org.wheatgenetics.coordinate.model.EntryModel get(
    @android.support.annotation.IntRange(from = 1) final int row,
    @android.support.annotation.IntRange(from = 1) final int col)
    { return this.entryModelArray[row - 1][col - 1]; }
    // endregion

    // region Public Methods
    public void add(final org.wheatgenetics.coordinate.model.EntryModel entryModel)
    {
        if (null != entryModel)
            this.entryModelArray[entryModel.getRow() - 1][entryModel.getCol() - 1] = entryModel;
    }

    public void processAll(final org.wheatgenetics.coordinate.model.EntryModels.Processor processor)
    {
        if (null != processor)
            for (final org.wheatgenetics.coordinate.model.EntryModel[] row: this.entryModelArray)
                for (final org.wheatgenetics.coordinate.model.EntryModel entryModel: row)
                    processor.process(entryModel);
    }
    // endregion
}