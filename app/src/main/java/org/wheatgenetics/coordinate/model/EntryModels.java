package org.wheatgenetics.coordinate.model;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

import org.wheatgenetics.coordinate.StringGetter;
import org.wheatgenetics.coordinate.preference.Utils;

public class EntryModels {
    // region Fields
    // region Constructor Fields
    @IntRange(from = 1)
    private final long gridId;
    @NonNull
    private final
    StringGetter stringGetter;
    // endregion
    @SuppressWarnings({"CStyleArrayDeclaration"})
    @NonNull
    private final EntryModel entryModelArray[][];
    public EntryModels(
            @IntRange(from = 1) final long gridId,
            @IntRange(from = 1) final int rows,
            @IntRange(from = 1) final int cols,
            @NonNull final StringGetter stringGetter) {
        super();

        this.gridId = Model.valid(gridId, stringGetter);
        this.stringGetter = stringGetter;
        this.entryModelArray = new EntryModel
                [org.wheatgenetics.coordinate.Utils.valid(rows, 1, this.stringGetter())]
                [org.wheatgenetics.coordinate.Utils.valid(cols, 1, this.stringGetter())];
    }
    // endregion

    // region Private Methods
    @Nullable
    private IncludedEntryModel downThenAcrossNext(
            @IntRange(from = 0) final int lastRow,
            @IntRange(from = 0) final int lastCol,
            @IntRange(from = 0) final int activeRow,
            @IntRange(from = 0) final int activeCol,
            @Nullable final
            EntryModels.FilledHandler filledHandler) {
        @IntRange(from = 0) final int candidateRow, candidateCol;
        final boolean filledRowOrColNeedsChecking;
        {
            final boolean recursion = null == filledHandler;
            if (!recursion && null == this.downThenAcrossNext(                          // recursion
                    lastRow, lastCol, activeRow, activeCol, null)) {
                filledHandler.handleFilledGrid();
                filledRowOrColNeedsChecking = false;
            } else if (activeRow < lastRow)
                filledRowOrColNeedsChecking = null != filledHandler;
            else {
                if (activeCol >= lastCol) {
                    if (null != filledHandler) filledHandler.handleFilledGrid();
                    return null;
                }

                if (null != filledHandler) filledHandler.handleFilledRowOrCol();
                filledRowOrColNeedsChecking = false;                 // Since I just handled it.
            }
        }

        if (activeRow < lastRow) {
            candidateRow = activeRow + 1;
            candidateCol = Math.min(activeCol, lastCol);
        } else {
            candidateRow = 0;
            candidateCol = activeCol + 1;
        }

        boolean onCandidateCol = true;
        for (int col = candidateCol; col <= lastCol; col++) {
            for (int row = onCandidateCol ? candidateRow : 0; row <= lastRow; row++) {
                final EntryModel entryModel =
                        this.entryModelArray[row][col];
                if (entryModel instanceof IncludedEntryModel) {
                    if (filledRowOrColNeedsChecking) if (col > activeCol)
                        filledHandler.handleFilledRowOrCol();
                    return (IncludedEntryModel) entryModel;
                }
            }
            onCandidateCol = false;
        }
        return null;
    }
    // endregion

    @Nullable
    private IncludedEntryModel acrossThenDownNext(
            @IntRange(from = 0) final int lastRow,
            @IntRange(from = 0) final int lastCol,
            @IntRange(from = 0) final int activeRow,
            @IntRange(from = 0) final int activeCol,
            @Nullable final
            EntryModels.FilledHandler filledHandler) {
        @IntRange(from = 0) final int candidateRow, candidateCol;
        final boolean filledRowOrColNeedsChecking;
        {
            final boolean recursion = null == filledHandler;
            if (!recursion && null == this.acrossThenDownNext(                          // recursion
                    lastRow, lastCol, activeRow, activeCol, null)) {
                filledHandler.handleFilledGrid();
                filledRowOrColNeedsChecking = false;
            } else if (activeCol < lastCol)
                filledRowOrColNeedsChecking = null != filledHandler;
            else {
                if (activeRow >= lastRow) {
                    if (null != filledHandler) filledHandler.handleFilledGrid();
                    return null;
                }

                if (null != filledHandler) filledHandler.handleFilledRowOrCol();
                filledRowOrColNeedsChecking = false;                 // Since I just handled it.
            }
        }

        if (activeCol < lastCol) {
            candidateRow = Math.min(activeRow, lastRow);
            candidateCol = activeCol + 1;
        } else {
            candidateRow = activeRow + 1;
            candidateCol = 0;
        }

        boolean onCandidateRow = true;
        for (int row = candidateRow; row <= lastRow; row++) {
            for (int col = onCandidateRow ? candidateCol : 0; col <= lastCol; col++) {
                final EntryModel entryModel =
                        this.entryModelArray[row][col];
                if (entryModel instanceof IncludedEntryModel) {
                    if (filledRowOrColNeedsChecking) if (row > activeRow)
                        filledHandler.handleFilledRowOrCol();
                    return (IncludedEntryModel) entryModel;
                }
            }
            onCandidateRow = false;
        }
        return null;
    }

    // region Package Methods
    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    @IntRange(from = 1)
    long getGridId() {
        return this.gridId;
    }
    // endregion

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    @NonNull
    StringGetter stringGetter() {
        return this.stringGetter;
    }

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    void uncheckedSet(final EntryModel entryModel) {
        if (null != entryModel)
            this.entryModelArray[entryModel.getRow() - 1][entryModel.getCol() - 1] = entryModel;
    }

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    @NonNull
    IncludedEntryModel makeButDontSetIncludedEntry(
            @IntRange(from = 1) final int row,
            @IntRange(from = 1) final int col) {
        return new IncludedEntryModel(
                this.getGridId(), row, col, this.stringGetter());
    }

    // region Package Methods
    void makeExcludedEntry(
            @IntRange(from = 1) final int row,
            @IntRange(from = 1) final int col) {
        this.uncheckedSet(new ExcludedEntryModel(
                this.getGridId(), row, col, this.stringGetter()));
    }
    // endregion

    void makeIncludedEntry(
            @IntRange(from = 1) final int row,
            @IntRange(from = 1) final int col) {
        this.uncheckedSet(this.makeButDontSetIncludedEntry(row, col));
    }

    EntryModel get(
            @IntRange(from = 1) final int row,
            @IntRange(from = 1) final int col) {
        return this.entryModelArray[row - 1][col - 1];
    }

    @NonNull
    Cells excludedCells() {
        final Cells result =
                new Cells(
                        /* maxRow       => */ this.entryModelArray.length,
                        /* maxCol       => */ this.entryModelArray[0].length,
                        /* stringGetter => */ this.stringGetter());
        for (final EntryModel[] row : this.entryModelArray)
            for (final EntryModel entryModel : row)
                if (entryModel instanceof ExcludedEntryModel)
                    result.add(entryModel.getRow(), entryModel.getCol());
        return result;
    }

    @Nullable
    IncludedEntryModel next(
            final EntryModel activeEntryModel,
            final Utils.Direction direction,
            final EntryModels.FilledHandler filledHandler) {
        if (null == activeEntryModel)
            return null;
        else {
            @IntRange(from = 0) final int
                    lastRow = this.entryModelArray.length - 1,
                    lastCol = this.entryModelArray[0].length - 1,
                    activeRow = activeEntryModel.getRow() - 1,
                    activeCol = activeEntryModel.getCol() - 1;
            switch (direction) {
                case DOWN_THEN_ACROSS:
                    return this.downThenAcrossNext(
                            lastRow, lastCol, activeRow, activeCol, filledHandler);

                case ACROSS_THEN_DOWN:
                    return this.acrossThenDownNext(
                            lastRow, lastCol, activeRow, activeCol, filledHandler);
            }
            return null;
        }
    }

    // region Public Methods
    public void set(final EntryModel entryModel) {
        this.uncheckedSet(entryModel);
    }

    public void processAll(final EntryModels.Processor processor) {
        if (null != processor)
            for (final EntryModel[] row : this.entryModelArray)
                for (final EntryModel entryModel : row)
                    processor.process(entryModel);
    }
    // endregion

    // region Types
    @SuppressWarnings({"UnnecessaryInterfaceModifier"})
    public interface FilledHandler {
        public abstract void handleFilledGrid();

        public abstract void handleFilledRowOrCol();
    }

    @SuppressWarnings({"UnnecessaryInterfaceModifier"})
    public interface Processor {
        public abstract void process(EntryModel entryModel);
    }
    // endregion
}