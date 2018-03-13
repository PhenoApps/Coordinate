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
    // region Types
    @java.lang.SuppressWarnings("UnnecessaryInterfaceModifier")
    public interface FilledHandler
    { public abstract void handleFilledGrid(); public abstract void handleFilledRowOrCol(); }

    @java.lang.SuppressWarnings("UnnecessaryInterfaceModifier")
    public interface Processor
    { public abstract void process(org.wheatgenetics.coordinate.model.EntryModel entryModel); }
    // endregion

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

    void set(final org.wheatgenetics.coordinate.model.EntryModel entryModel)
    {
        if (null != entryModel)
            this.entryModelArray[entryModel.getRow() - 1][entryModel.getCol() - 1] = entryModel;
    }

    org.wheatgenetics.coordinate.model.IncludedEntryModel next(
    final org.wheatgenetics.coordinate.model.EntryModel                activeEntryModel,
    final org.wheatgenetics.coordinate.model.EntryModels.FilledHandler filledHandler   )
    {
        if (null == activeEntryModel)
            return null;
        else
        {
            final int
                lastRow      = this.entryModelArray.length    - 1,
                lastCol      = this.entryModelArray[0].length - 1,
                candidateRow                                     ,
                candidateCol                                     ,
                activeCol    = activeEntryModel.getCol() - 1     ;
            final boolean filledRowOrColNeedsHandling;
            {
                final int activeRow = activeEntryModel.getRow() - 1;

                if (null != filledHandler && null == this.next(activeEntryModel, null)) // recursion
                {
                    filledHandler.handleFilledGrid();
                    filledRowOrColNeedsHandling = false;
                }
                else
                    if (activeRow < lastRow)
                        filledRowOrColNeedsHandling = null != filledHandler;
                    else
                    {
                        if (activeCol >= lastCol)
                        {
                            if (null != filledHandler) filledHandler.handleFilledGrid();
                            return null;
                        }

                        if (null != filledHandler) filledHandler.handleFilledRowOrCol();
                        filledRowOrColNeedsHandling = false;
                    }

                if (activeRow < lastRow)
                    { candidateRow = activeRow + 1; candidateCol = activeCol; }
                else
                    { candidateRow = 0; candidateCol = activeCol + 1; }
            }

            boolean onCandidateCol = true;
            for (int col = candidateCol; col <= lastCol; col++)
            {
                for (int row = onCandidateCol ? candidateRow : 0; row <= lastRow; row++)
                {
                    final org.wheatgenetics.coordinate.model.EntryModel entryModel =
                        this.entryModelArray[row][col];
                    if (entryModel instanceof org.wheatgenetics.coordinate.model.IncludedEntryModel)
                    {
                        if (filledRowOrColNeedsHandling) if (col > activeCol)
                            filledHandler.handleFilledRowOrCol();
                        return (org.wheatgenetics.coordinate.model.IncludedEntryModel) entryModel;
                    }
                }
                onCandidateCol = false;
            }
            return null;
        }
    }
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