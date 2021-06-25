package org.wheatgenetics.coordinate.model;

import android.content.res.Resources;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.PluralsRes;
import androidx.annotation.StringRes;

import org.junit.Assert;
import org.junit.Test;
import org.wheatgenetics.coordinate.R;
import org.wheatgenetics.coordinate.StringGetter;
import org.wheatgenetics.coordinate.preference.Utils;

/**
 * Uses:
 * android.content.res.Resources.NotFoundException
 *
 * androidx.annotation.IntRange
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 * androidx.annotation.PluralsRes
 * androidx.annotation.StringRes
 *
 * org.junit.Assert
 * org.junit.Test
 *
 * org.wheatgenetics.coordinate.preference.Utils.Direction
 *
 * org.wheatgenetics.coordinate.R
 * org.wheatgenetics.coordinate.StringGetter
 *
 * org.wheatgenetics.coordinate.model.Cells
 * org.wheatgenetics.coordinate.model.EntryModel
 * org.wheatgenetics.coordinate.model.EntryModels
 * org.wheatgenetics.coordinate.model.EntryModels.FilledHandler
 * org.wheatgenetics.coordinate.model.EntryModels.Processor
 * org.wheatgenetics.coordinate.model.ExcludedEntryModel
 * org.wheatgenetics.coordinate.model.IncludedEntryModel
 */
@SuppressWarnings({"ClassExplicitlyExtendsObject"}) public class EntryModelsTest
extends Object implements StringGetter
{
    private static class FilledHandler extends Object
    implements EntryModels.FilledHandler
    {
        @Override public void handleFilledGrid    () {}
        @Override public void handleFilledRowOrCol() {}
    }

    // region org.wheatgenetics.coordinate.StringGetter Overridden Methods
    @Override @Nullable public String get(
    @StringRes final int resId)
    {
        switch (resId)
        {
            case R.string.ModelIdMustBeGreaterThanZero:
                return "id must be > 0";

            case R.string.UtilsInvalidValue:
                return "value must be >= %d";

            default: Assert.fail(); return null;
        }
    }

    @Override @NonNull public String getQuantity(
    @PluralsRes         final int                 resId     ,
    @IntRange(from = 0) final int                 quantity  ,
    @Nullable           final Object... formatArgs)
    throws Resources.NotFoundException { Assert.fail(); return null; }
    // endregion

    // region Constructor Tests
    @Test(expected = IllegalArgumentException.class)
    public void badGridIdConstructorFails()
    { new EntryModels(0,5,5,this); }

    @Test(expected = IllegalArgumentException.class)
    public void badRowsConstructorFails()
    { new EntryModels(1,0,5,this); }

    @Test(expected = IllegalArgumentException.class)
    public void badColsConstructorFails()
    { new EntryModels(1,5,0,this); }
    // endregion

    // region Package Method Tests
    // region makeExcludedEntry() Package Method Tests
    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void makeExcludedEntryFails()
    {
        new EntryModels(1,5,5,this)
            .makeExcludedEntry(50,1);
    }

    @Test() public void makeExcludedEntrySucceeds()
    {
        final ExcludedEntryModel excludedEntryModel;
        final int                                                   row = 3, col = 3  ;
        {
            final long gridId = 1;
            {
                final EntryModels entryModels =
                    new EntryModels(
                        gridId,5,5,this);
                entryModels.makeExcludedEntry(row, col);
                Assert.assertTrue(entryModels.get(row, col)
                    instanceof ExcludedEntryModel);
                excludedEntryModel = (ExcludedEntryModel)
                    entryModels.get(row, col);
            }
            Assert.assertEquals(gridId, excludedEntryModel.getGridId());
        }
        Assert.assertEquals(row, excludedEntryModel.getRow());
        Assert.assertEquals(col, excludedEntryModel.getCol());
    }
    // endregion

    // region makeIncludedEntry() Package Method Tests
    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void makeIncludedEntryFails()
    {
        new EntryModels(1,5,5,this)
            .makeIncludedEntry(5,10);
    }

    @Test() public void makeIncludedEntrySucceeds()
    {
        final IncludedEntryModel includedEntryModel;
        final int                                                   row = 3, col = 3  ;
        {
            final long gridId = 1;
            {
                final EntryModels entryModels =
                    new EntryModels(
                        gridId,5,5,this);
                entryModels.makeIncludedEntry(row, col);
                Assert.assertTrue(entryModels.get(row, col)
                    instanceof IncludedEntryModel);
                includedEntryModel = (IncludedEntryModel)
                    entryModels.get(row, col);
            }
            Assert.assertEquals(gridId, includedEntryModel.getGridId());
        }
        Assert.assertEquals(row, includedEntryModel.getRow());
        Assert.assertEquals(col, includedEntryModel.getCol());
    }
    // endregion

    // region excludedCells() Package Method Tests
    @Test() public void emptyExcludedCellsWorks()
    {
        final Cells       expectedCells;
        final EntryModels entryModels  ;
        {
            final int rows = 5, cols = 5;
            expectedCells = new Cells(rows, cols,this);
            entryModels   = new EntryModels(
                1, rows, cols,this);
        }
        Assert.assertEquals(expectedCells, entryModels.excludedCells());
    }

    @Test() public void oneCellExcludedCellsWorks()
    {
        final Cells       expectedCells;
        final EntryModels entryModels  ;
        {
            final int rows = 5, cols = 5, excludedRow = 3, excludedCol = 3;

            expectedCells = new Cells(rows, cols,this);
            expectedCells.add(excludedRow, excludedCol);

            entryModels = new EntryModels(
                1, rows, cols,this);
            for (int row = 1; row <= rows; row++) for (int col = 1; col <= cols; col++)
                if (excludedRow == row && excludedCol == col)
                    entryModels.makeExcludedEntry(row, col);
                else
                    entryModels.makeIncludedEntry(row, col);
        }
        Assert.assertEquals(expectedCells, entryModels.excludedCells());
    }
    // endregion

    // region next() Package Method Tests
    @Test() public void allIncludedNextSucceeds()
    {
        final long                                           gridId = 1          ;
        final int                                            rows   = 5, cols = 5;
        final EntryModels entryModels =
            new EntryModels(gridId, rows, cols,this);
        for (int row = 1; row <= rows; row++) for (int col = 1; col <= cols; col++)
            entryModels.makeIncludedEntry(row, col);

        final EntryModelsTest.FilledHandler filledHandler =
            new EntryModelsTest.FilledHandler();


        Assert.assertNull(entryModels.next(null,
            Utils.Direction.DOWN_THEN_ACROSS,
            filledHandler                                                           ));
        Assert.assertNull(entryModels.next(null,
            Utils.Direction.ACROSS_THEN_DOWN,
            filledHandler                                                           ));


        IncludedEntryModel activeIncludedEntryModel =
            new IncludedEntryModel(
                gridId,1,1,this);
        IncludedEntryModel nextIncludedEntryModel =
            entryModels.next(activeIncludedEntryModel,
                Utils.Direction.DOWN_THEN_ACROSS,
                filledHandler                                                           );
        Assert.assertNotNull(nextIncludedEntryModel);
        Assert.assertEquals (2, nextIncludedEntryModel.getRow());
        Assert.assertEquals (1, nextIncludedEntryModel.getCol());

        nextIncludedEntryModel = entryModels.next(activeIncludedEntryModel,
            Utils.Direction.ACROSS_THEN_DOWN,
            filledHandler                                                           );
        Assert.assertNotNull(nextIncludedEntryModel);
        Assert.assertEquals (1, nextIncludedEntryModel.getRow());
        Assert.assertEquals (2, nextIncludedEntryModel.getCol());


        activeIncludedEntryModel = new IncludedEntryModel(
            gridId, rows,1,this);
        nextIncludedEntryModel = entryModels.next(activeIncludedEntryModel,
            Utils.Direction.DOWN_THEN_ACROSS,
            filledHandler                                                           );
        Assert.assertNotNull(nextIncludedEntryModel);
        Assert.assertEquals (1, nextIncludedEntryModel.getRow());
        Assert.assertEquals (2, nextIncludedEntryModel.getCol());

        activeIncludedEntryModel = new IncludedEntryModel(
            gridId,1, cols,this);
        nextIncludedEntryModel = entryModels.next(activeIncludedEntryModel,
            Utils.Direction.ACROSS_THEN_DOWN,
            filledHandler                                                           );
        Assert.assertNotNull(nextIncludedEntryModel);
        Assert.assertEquals (2, nextIncludedEntryModel.getRow());
        Assert.assertEquals (1, nextIncludedEntryModel.getCol());


        activeIncludedEntryModel = new IncludedEntryModel(
            gridId,999,1,this);
        nextIncludedEntryModel = entryModels.next(activeIncludedEntryModel,
            Utils.Direction.DOWN_THEN_ACROSS,
            filledHandler                                                           );
        Assert.assertNotNull(nextIncludedEntryModel);
        Assert.assertEquals (1, nextIncludedEntryModel.getRow());
        Assert.assertEquals (2, nextIncludedEntryModel.getCol());

        activeIncludedEntryModel = new IncludedEntryModel(
            gridId,1,999,this);
        nextIncludedEntryModel = entryModels.next(activeIncludedEntryModel,
            Utils.Direction.ACROSS_THEN_DOWN,
            filledHandler                                                           );
        Assert.assertNotNull(nextIncludedEntryModel);
        Assert.assertEquals (2, nextIncludedEntryModel.getRow());
        Assert.assertEquals (1, nextIncludedEntryModel.getCol());


        activeIncludedEntryModel = new IncludedEntryModel(
            gridId,1, cols,this);
        nextIncludedEntryModel = entryModels.next(activeIncludedEntryModel,
            Utils.Direction.DOWN_THEN_ACROSS,
            filledHandler                                                           );
        Assert.assertNotNull(nextIncludedEntryModel);
        Assert.assertEquals (2, nextIncludedEntryModel.getRow());
        Assert.assertEquals (cols, nextIncludedEntryModel.getCol());

        activeIncludedEntryModel = new IncludedEntryModel(
            gridId, rows,1,this);
        nextIncludedEntryModel = entryModels.next(activeIncludedEntryModel,
            Utils.Direction.ACROSS_THEN_DOWN,
            filledHandler                                                           );
        Assert.assertNotNull(nextIncludedEntryModel               );
        Assert.assertEquals (rows, nextIncludedEntryModel.getRow());
        Assert.assertEquals (2, nextIncludedEntryModel.getCol());


        activeIncludedEntryModel = new IncludedEntryModel(
            gridId,1,999,this);
        nextIncludedEntryModel = entryModels.next(activeIncludedEntryModel,
            Utils.Direction.DOWN_THEN_ACROSS,
            filledHandler                                                           );
        Assert.assertNotNull(nextIncludedEntryModel);
        Assert.assertEquals (2, nextIncludedEntryModel.getRow());
        Assert.assertEquals (cols, nextIncludedEntryModel.getCol());

        activeIncludedEntryModel = new IncludedEntryModel(
            gridId,999,1,this);
        nextIncludedEntryModel = entryModels.next(activeIncludedEntryModel,
            Utils.Direction.ACROSS_THEN_DOWN,
            filledHandler                                                           );
        Assert.assertNotNull(nextIncludedEntryModel               );
        Assert.assertEquals (rows, nextIncludedEntryModel.getRow());
        Assert.assertEquals (2, nextIncludedEntryModel.getCol());


        activeIncludedEntryModel = new IncludedEntryModel(
            gridId, rows, cols,this);
        nextIncludedEntryModel = entryModels.next(activeIncludedEntryModel,
            Utils.Direction.DOWN_THEN_ACROSS,
            filledHandler                                                           );
        Assert.assertNull(nextIncludedEntryModel);

        nextIncludedEntryModel = entryModels.next(activeIncludedEntryModel,
            Utils.Direction.ACROSS_THEN_DOWN,
            filledHandler                                                           );
        Assert.assertNull(nextIncludedEntryModel);


        activeIncludedEntryModel = new IncludedEntryModel(
            gridId,999,999,this);
        nextIncludedEntryModel = entryModels.next(activeIncludedEntryModel,
            Utils.Direction.DOWN_THEN_ACROSS,
            filledHandler                                                           );
        Assert.assertNull(nextIncludedEntryModel);

        nextIncludedEntryModel = entryModels.next(activeIncludedEntryModel,
            Utils.Direction.ACROSS_THEN_DOWN,
            filledHandler                                                           );
        Assert.assertNull(nextIncludedEntryModel);
    }

    @Test() public void someExcludedNextSucceeds()
    {
        final long                                           gridId = 1 ;
        final EntryModels entryModels;
        {
            final int rows = 5, cols = 5;
            entryModels = new EntryModels(
                gridId, rows, cols,this);
            for (int row = 1; row <= rows; row++) for (int col = 1; col <= cols; col++)
                if (2 == row && 2 == col)
                    entryModels.makeExcludedEntry(row, col);
                else
                    entryModels.makeIncludedEntry(row, col);
        }

        final EntryModelsTest.FilledHandler filledHandler =
            new EntryModelsTest.FilledHandler();


        IncludedEntryModel activeIncludedEntryModel =
            new IncludedEntryModel(
                gridId,1,2,this);
        IncludedEntryModel nextIncludedEntryModel =
            entryModels.next(activeIncludedEntryModel,
                Utils.Direction.DOWN_THEN_ACROSS,
                filledHandler                                                           );
        Assert.assertNotNull(nextIncludedEntryModel);
        Assert.assertEquals (3, nextIncludedEntryModel.getRow());
        Assert.assertEquals (2, nextIncludedEntryModel.getCol());

        activeIncludedEntryModel = new IncludedEntryModel(
            gridId,2,1,this);
        nextIncludedEntryModel = entryModels.next(activeIncludedEntryModel,
            Utils.Direction.ACROSS_THEN_DOWN,
            filledHandler                                                           );
        Assert.assertNotNull(nextIncludedEntryModel);
        Assert.assertEquals (2, nextIncludedEntryModel.getRow());
        Assert.assertEquals (3, nextIncludedEntryModel.getCol());


        activeIncludedEntryModel = new IncludedEntryModel(
            gridId,1,2,this);
        entryModels.makeExcludedEntry(3,2);    // Two excluded entries next to each other.
        nextIncludedEntryModel = entryModels.next(activeIncludedEntryModel,
            Utils.Direction.DOWN_THEN_ACROSS,
            filledHandler                                                           );
        Assert.assertNotNull(nextIncludedEntryModel);
        Assert.assertEquals (4, nextIncludedEntryModel.getRow());
        Assert.assertEquals (2, nextIncludedEntryModel.getCol());

        activeIncludedEntryModel = new IncludedEntryModel(
            gridId,2,1,this);
        entryModels.makeExcludedEntry(2,3);    // Two excluded entries next to each other.
        nextIncludedEntryModel = entryModels.next(activeIncludedEntryModel,
            Utils.Direction.ACROSS_THEN_DOWN,
            filledHandler                                                           );
        Assert.assertNotNull(nextIncludedEntryModel);
        Assert.assertEquals (2, nextIncludedEntryModel.getRow());
        Assert.assertEquals (4, nextIncludedEntryModel.getCol());
    }
    // endregion
    // endregion

    // region Public Method Tests
    @Test() public void setSucceeds()
    {
        final long                                           gridId = 1 ;
        final EntryModels entryModels;
        {
            final int rows = 5, cols = 5;
            entryModels = new EntryModels(
                gridId, rows, cols,this);
            for (int row = 1; row <= rows; row++) for (int col = 1; col <= cols; col++)
                entryModels.makeIncludedEntry(row, col);
        }

        entryModels.set(null);

        final int row = 3, col = 3;
        {
            final IncludedEntryModel
                expectedIncludedEntryModel = (IncludedEntryModel)
                    entryModels.get(row, col),
                actualIncludedEntryModel = (IncludedEntryModel)
                    entryModels.get(row, col);
            Assert.assertEquals(expectedIncludedEntryModel, actualIncludedEntryModel);
        }

        final IncludedEntryModel unexpectedIncludedEntryModel =
            (IncludedEntryModel) entryModels.get(row, col);
        {
            final IncludedEntryModel newIncludedEntryModel =
                new IncludedEntryModel(
                    gridId, row, col,this);
            newIncludedEntryModel.setValue("gobbledygook");
            entryModels.set(newIncludedEntryModel);
        }
        {
            final IncludedEntryModel actualIncludedEntryModel =
                (IncludedEntryModel) entryModels.get(row, col);
            Assert.assertNotEquals(unexpectedIncludedEntryModel.getValue(),
                actualIncludedEntryModel.getValue());
            Assert.assertEquals("gobbledygook",
                actualIncludedEntryModel.getValue());
        }
        Assert.assertNull(unexpectedIncludedEntryModel.getValue());
    }

    @Test() public void processAll()
    {
        final EntryModels entryModels;
        {
            final int rows = 5, cols = 5;
            entryModels = new EntryModels(
                1, rows, cols,this);
            for (int row = 1; row <= rows; row++) for (int col = 1; col <= cols; col++)
                entryModels.makeIncludedEntry(row, col);
        }

        class Processor extends Object
        implements EntryModels.Processor
        {
            @Override
            public void process(final EntryModel entryModel)
            { Assert.assertNotNull(entryModel); }
        }

        entryModels.processAll(new Processor());
    }
    // endregion
}