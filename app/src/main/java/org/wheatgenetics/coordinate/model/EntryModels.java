package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * android.support.annotation.IntRange
 *
 * org.wheatgenetics.coordinate.Utils
 * org.wheatgenetics.coordinate.Utils.Advancement
 *
 * org.wheatgenetics.coordinate.model.Cells
 * org.wheatgenetics.coordinate.model.EntryModel
 * org.wheatgenetics.coordinate.model.ExcludedEntryModel
 * org.wheatgenetics.coordinate.model.IncludedEntryModel
 * org.wheatgenetics.coordinate.model.Model
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
public class EntryModels extends java.lang.Object
{
    // region Types
    @java.lang.SuppressWarnings({"UnnecessaryInterfaceModifier"}) public interface FilledHandler
    { public abstract void handleFilledGrid(); public abstract void handleFilledRowOrCol(); }

    @java.lang.SuppressWarnings({"UnnecessaryInterfaceModifier"}) public interface Processor
    { public abstract void process(org.wheatgenetics.coordinate.model.EntryModel entryModel); }
    // endregion

    // region Fields
    private final long                                          gridId             ;
    private final org.wheatgenetics.coordinate.model.EntryModel entryModelArray[][];
    // endregion

    // region Private Methods
    private org.wheatgenetics.coordinate.model.IncludedEntryModel downThenAcrossNext(
    final int lastRow, final int lastCol, final int activeRow, final int activeCol,
    final org.wheatgenetics.coordinate.model.EntryModels.FilledHandler filledHandler)
    {
        final int     candidateRow, candidateCol ;
        final boolean filledRowOrColNeedsChecking;
        {
            {
                final boolean recursion = null == filledHandler;
                if (!recursion && null == this.downThenAcrossNext(                      // recursion
                lastRow, lastCol, activeRow, activeCol,null))
                {
                    filledHandler.handleFilledGrid();
                    filledRowOrColNeedsChecking = false;
                }
                else
                    if (activeRow < lastRow)
                        filledRowOrColNeedsChecking = null != filledHandler;
                    else
                    {
                        if (activeCol >= lastCol)
                        {
                            if (null != filledHandler) filledHandler.handleFilledGrid();
                            return null;
                        }

                        if (null != filledHandler) filledHandler.handleFilledRowOrCol();
                        filledRowOrColNeedsChecking = false;             // Since I just handled it.
                    }
            }

            if (activeRow < lastRow)
            {
                candidateRow = activeRow + 1                         ;
                candidateCol = java.lang.Math.min(activeCol, lastCol);
            }
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
                    if (filledRowOrColNeedsChecking) if (col > activeCol)
                        filledHandler.handleFilledRowOrCol();
                    return (org.wheatgenetics.coordinate.model.IncludedEntryModel) entryModel;
                }
            }
            onCandidateCol = false;
        }
        return null;
    }

    private org.wheatgenetics.coordinate.model.IncludedEntryModel acrossThenDownNext(
    final int lastRow, final int lastCol, final int activeRow, final int activeCol,
    final org.wheatgenetics.coordinate.model.EntryModels.FilledHandler filledHandler)
    {
        final int     candidateRow, candidateCol ;
        final boolean filledRowOrColNeedsChecking;
        {
            {
                final boolean recursion = null == filledHandler;
                if (!recursion && null == this.acrossThenDownNext(                      // recursion
                lastRow, lastCol, activeRow, activeCol,null))
                {
                    filledHandler.handleFilledGrid();
                    filledRowOrColNeedsChecking = false;
                }
                else
                    if (activeCol < lastCol)
                        filledRowOrColNeedsChecking = null != filledHandler;
                    else
                    {
                        if (activeRow >= lastRow)
                        {
                            if (null != filledHandler) filledHandler.handleFilledGrid();
                            return null;
                        }

                        if (null != filledHandler) filledHandler.handleFilledRowOrCol();
                        filledRowOrColNeedsChecking = false;             // Since I just handled it.
                    }
            }

            if (activeCol < lastCol)
            {
                candidateRow = java.lang.Math.min(activeRow, lastRow);
                candidateCol = activeCol + 1                         ;
            }
            else
                { candidateRow = activeRow + 1; candidateCol = 0; }
        }

        boolean onCandidateRow = true;
        for (int row = candidateRow; row <= lastRow; row++)
        {
            for (int col = onCandidateRow ? candidateCol : 0; col <= lastCol; col++)
            {
                final org.wheatgenetics.coordinate.model.EntryModel entryModel =
                    this.entryModelArray[row][col];
                if (entryModel instanceof org.wheatgenetics.coordinate.model.IncludedEntryModel)
                {
                    if (filledRowOrColNeedsChecking) if (row > activeRow)
                        filledHandler.handleFilledRowOrCol();
                    return (org.wheatgenetics.coordinate.model.IncludedEntryModel) entryModel;
                }
            }
            onCandidateRow = false;
        }
        return null;
    }
    // endregion

    public EntryModels(
    @android.support.annotation.IntRange(from = 1) final long gridId,
    @android.support.annotation.IntRange(from = 1) final int  rows  ,
    @android.support.annotation.IntRange(from = 1) final int  cols  )
    {
        super();

        this.gridId          = org.wheatgenetics.coordinate.model.Model.valid(gridId);
        this.entryModelArray = new org.wheatgenetics.coordinate.model.EntryModel
            [org.wheatgenetics.coordinate.Utils.valid(rows,1)]
            [org.wheatgenetics.coordinate.Utils.valid(cols,1)];
    }

    // region Package Methods
    void makeExcludedEntry(
    @android.support.annotation.IntRange(from = 1) final int row,
    @android.support.annotation.IntRange(from = 1) final int col)
    { this.set(new org.wheatgenetics.coordinate.model.ExcludedEntryModel(this.gridId, row, col)); }

    void makeIncludedEntry(
    @android.support.annotation.IntRange(from = 1) final int row,
    @android.support.annotation.IntRange(from = 1) final int col)
    { this.set(new org.wheatgenetics.coordinate.model.IncludedEntryModel(this.gridId, row, col)); }

    org.wheatgenetics.coordinate.model.EntryModel get(
    @android.support.annotation.IntRange(from = 1) final int row,
    @android.support.annotation.IntRange(from = 1) final int col)
    { return this.entryModelArray[row - 1][col - 1]; }

    org.wheatgenetics.coordinate.model.Cells excludedCells()
    {
        if (null == this.entryModelArray)
            return null;
        else
        {
            final org.wheatgenetics.coordinate.model.Cells result =
                new org.wheatgenetics.coordinate.model.Cells(
                    this.entryModelArray.length, this.entryModelArray[0].length);
            for (final org.wheatgenetics.coordinate.model.EntryModel[] row: this.entryModelArray)
                for (final org.wheatgenetics.coordinate.model.EntryModel entryModel: row)
                    if (entryModel instanceof org.wheatgenetics.coordinate.model.ExcludedEntryModel)
                        result.add(entryModel.getRow(), entryModel.getCol());
            return result;
        }
    }

    org.wheatgenetics.coordinate.model.IncludedEntryModel next(
    final org.wheatgenetics.coordinate.model.EntryModel                activeEntryModel,
    final org.wheatgenetics.coordinate.Utils.Advancement               advancement     ,
    final org.wheatgenetics.coordinate.model.EntryModels.FilledHandler filledHandler   )
    {
        if (null == activeEntryModel)
            return null;
        else
        {
            final int
                lastRow   = this.entryModelArray.length    - 1,
                lastCol   = this.entryModelArray[0].length - 1,
                activeRow = activeEntryModel.getRow()      - 1,
                activeCol = activeEntryModel.getCol()      - 1;
            switch (advancement)
            {
                case DOWN_THEN_ACROSS: return this.downThenAcrossNext(
                    lastRow, lastCol, activeRow, activeCol, filledHandler);

                case ACROSS_THEN_DOWN: return this.acrossThenDownNext(
                    lastRow, lastCol, activeRow, activeCol, filledHandler);
            }
            return null;
        }
    }
    // endregion

    // region Public Methods
    public void set(final org.wheatgenetics.coordinate.model.EntryModel entryModel)
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