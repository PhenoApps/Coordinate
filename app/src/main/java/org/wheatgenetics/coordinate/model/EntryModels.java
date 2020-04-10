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
 * org.wheatgenetics.coordinate.Utils
 *
 * org.wheatgenetics.coordinate.preference.Utils.Direction
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
    // region Constructor Fields
    @androidx.annotation.IntRange(from = 1) private final long gridId;
    @androidx.annotation.NonNull            private final
        org.wheatgenetics.coordinate.StringGetter stringGetter;
    // endregion

    @java.lang.SuppressWarnings({"CStyleArrayDeclaration"}) @androidx.annotation.NonNull
        private final org.wheatgenetics.coordinate.model.EntryModel entryModelArray[][];
    // endregion

    // region Private Methods
    @androidx.annotation.Nullable
    private org.wheatgenetics.coordinate.model.IncludedEntryModel downThenAcrossNext(
    @androidx.annotation.IntRange(from = 0) final int lastRow  ,
    @androidx.annotation.IntRange(from = 0) final int lastCol  ,
    @androidx.annotation.IntRange(from = 0) final int activeRow,
    @androidx.annotation.IntRange(from = 0) final int activeCol,
    @androidx.annotation.Nullable           final
        org.wheatgenetics.coordinate.model.EntryModels.FilledHandler filledHandler)
    {
        @androidx.annotation.IntRange(from = 0) final int     candidateRow, candidateCol ;
                                                final boolean filledRowOrColNeedsChecking;
        {
            final boolean recursion = null == filledHandler;
            if (!recursion && null == this.downThenAcrossNext(                          // recursion
            lastRow, lastCol, activeRow, activeCol,null))
                { filledHandler.handleFilledGrid(); filledRowOrColNeedsChecking = false; }
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
                    filledRowOrColNeedsChecking = false;                 // Since I just handled it.
                }
        }

        if (activeRow < lastRow)
            { candidateRow = activeRow + 1; candidateCol = java.lang.Math.min(activeCol, lastCol); }
        else
            { candidateRow = 0; candidateCol = activeCol + 1; }

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

    @androidx.annotation.Nullable
    private org.wheatgenetics.coordinate.model.IncludedEntryModel acrossThenDownNext(
    @androidx.annotation.IntRange(from = 0) final int lastRow  ,
    @androidx.annotation.IntRange(from = 0) final int lastCol  ,
    @androidx.annotation.IntRange(from = 0) final int activeRow,
    @androidx.annotation.IntRange(from = 0) final int activeCol,
    @androidx.annotation.Nullable           final
        org.wheatgenetics.coordinate.model.EntryModels.FilledHandler filledHandler)
    {
        @androidx.annotation.IntRange(from = 0) final int     candidateRow, candidateCol ;
                                                final boolean filledRowOrColNeedsChecking;
        {
            final boolean recursion = null == filledHandler;
            if (!recursion && null == this.acrossThenDownNext(                          // recursion
            lastRow, lastCol, activeRow, activeCol,null))
                { filledHandler.handleFilledGrid(); filledRowOrColNeedsChecking = false; }
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
                    filledRowOrColNeedsChecking = false;                 // Since I just handled it.
                }
        }

        if (activeCol < lastCol)
            { candidateRow = java.lang.Math.min(activeRow, lastRow); candidateCol = activeCol + 1; }
        else
            { candidateRow = activeRow + 1; candidateCol = 0; }

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

    // region Package Methods
    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @androidx.annotation.IntRange(from = 1) long getGridId() { return this.gridId; }

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    void uncheckedSet(final org.wheatgenetics.coordinate.model.EntryModel entryModel)
    {
        if (null != entryModel)
            this.entryModelArray[entryModel.getRow() - 1][entryModel.getCol() - 1] = entryModel;
    }

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @androidx.annotation.NonNull
    org.wheatgenetics.coordinate.model.IncludedEntryModel makeButDontSetIncludedEntry(
    @androidx.annotation.IntRange(from = 1) final int row,
    @androidx.annotation.IntRange(from = 1) final int col)
    {
        return new org.wheatgenetics.coordinate.model.IncludedEntryModel(
            this.getGridId(), row, col);
    }
    // endregion

    public EntryModels(
    @androidx.annotation.IntRange(from = 1) final long                                 gridId      ,
    @androidx.annotation.IntRange(from = 1) final int                                  rows        ,
    @androidx.annotation.IntRange(from = 1) final int                                  cols        ,
    @androidx.annotation.NonNull       final org.wheatgenetics.coordinate.StringGetter stringGetter)
    {
        super();

        this.gridId          = org.wheatgenetics.coordinate.model.Model.valid(gridId);
        this.stringGetter    = stringGetter                                          ;
        this.entryModelArray = new org.wheatgenetics.coordinate.model.EntryModel
            [org.wheatgenetics.coordinate.Utils.valid(rows,1)]
            [org.wheatgenetics.coordinate.Utils.valid(cols,1)];
    }

    // region Package Methods
    void makeExcludedEntry(
    @androidx.annotation.IntRange(from = 1) final int row,
    @androidx.annotation.IntRange(from = 1) final int col)
    {
        this.uncheckedSet(new org.wheatgenetics.coordinate.model.ExcludedEntryModel(
            this.getGridId(), row, col));
    }

    void makeIncludedEntry(
    @androidx.annotation.IntRange(from = 1) final int row,
    @androidx.annotation.IntRange(from = 1) final int col)
    { this.uncheckedSet(this.makeButDontSetIncludedEntry(row, col)); }

    org.wheatgenetics.coordinate.model.EntryModel get(
    @androidx.annotation.IntRange(from = 1) final int row,
    @androidx.annotation.IntRange(from = 1) final int col)
    { return this.entryModelArray[row - 1][col - 1]; }

    @androidx.annotation.NonNull org.wheatgenetics.coordinate.model.Cells excludedCells()
    {
        final org.wheatgenetics.coordinate.model.Cells result =
            new org.wheatgenetics.coordinate.model.Cells(
                /* maxRow       => */ this.entryModelArray.length   ,
                /* maxCol       => */ this.entryModelArray[0].length,
                /* stringGetter => */ this.stringGetter             );
        for (final org.wheatgenetics.coordinate.model.EntryModel[] row: this.entryModelArray)
            for (final org.wheatgenetics.coordinate.model.EntryModel entryModel: row)
                if (entryModel instanceof org.wheatgenetics.coordinate.model.ExcludedEntryModel)
                    result.add(entryModel.getRow(), entryModel.getCol());
        return result;
    }

    @androidx.annotation.Nullable org.wheatgenetics.coordinate.model.IncludedEntryModel next(
    final org.wheatgenetics.coordinate.model.EntryModel                activeEntryModel,
    final org.wheatgenetics.coordinate.preference.Utils.Direction      direction       ,
    final org.wheatgenetics.coordinate.model.EntryModels.FilledHandler filledHandler   )
    {
        if (null == activeEntryModel)
            return null;
        else
        {
            @androidx.annotation.IntRange(from = 0) final int
                lastRow   = this.entryModelArray.length    - 1,
                lastCol   = this.entryModelArray[0].length - 1,
                activeRow = activeEntryModel.getRow()      - 1,
                activeCol = activeEntryModel.getCol()      - 1;
            switch (direction)
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
    { this.uncheckedSet(entryModel); }

    public void processAll(final org.wheatgenetics.coordinate.model.EntryModels.Processor processor)
    {
        if (null != processor)
            for (final org.wheatgenetics.coordinate.model.EntryModel[] row: this.entryModelArray)
                for (final org.wheatgenetics.coordinate.model.EntryModel entryModel: row)
                    processor.process(entryModel);
    }
    // endregion
}