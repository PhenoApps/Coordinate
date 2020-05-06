package org.wheatgenetics.coordinate.model;

import android.content.res.Resources;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.PluralsRes;
import androidx.annotation.StringRes;

import org.json.JSONArray;
import org.json.JSONException;
import org.junit.Assert;
import org.junit.Test;
import org.wheatgenetics.coordinate.R;
import org.wheatgenetics.coordinate.StringGetter;

@SuppressWarnings({"ClassExplicitlyExtendsObject"}) public class CellsTest
extends Object implements StringGetter
{
    // region org.wheatgenetics.coordinate.StringGetter Overridden Methods
    @Override @Nullable public String get(
    @StringRes final int resId)
    {
        switch (resId)
        {
            case R.string.CellsMaxRowAndOrMaxColOutOfRange:
                return "maxRow and/or maxCol is out of range";

            case R.string.UtilsInvalidValue:
                return "value must be >= %d";

            default: Assert.fail(); return null;
        }
    }

    @Override @NonNull public String getQuantity(
    @PluralsRes         final int                 resId     ,
    @IntRange(from = 0) final int                 quantity  ,
    @Nullable           final Object... formatArgs)
    throws Resources.NotFoundException
    {
        // noinspection SwitchStatementWithTooFewBranches
        switch (resId)
        {
            case R.plurals.AmountIsTooLarge:
                return quantity < 1 ? "There is no more room for entries." :
                    1 == quantity ? "There is room for only 1 more entry." :
                        String.format(
                            "There is room for only %d more entries.", quantity);

            default: Assert.fail(); return null;
        }
    }
    // endregion

    // region Constructor Tests
    // region Second Constructor Tests
    @Test(expected = IllegalArgumentException.class)
    public void zeroMaxRowSecondConstructorFails()
    { new Cells(0,5,this); }

    @Test(expected = IllegalArgumentException.class)
    public void negativeMaxRowSecondConstructorFails()
    { new Cells(-5,5,this); }

    @Test(expected = IllegalArgumentException.class)
    public void zeroMaxColSecondConstructorFails()
    { new Cells(5,0,this); }

    @Test(expected = IllegalArgumentException.class)
    public void negativeMaxColSecondConstructorFails()
    { new Cells(5,-5,this); }

    @Test() public void secondConstructorSucceeds()
    { new Cells(5,5,this); }
    // endregion

    // region Fourth Constructor Tests
    @Test() public void fourthConstructorAndJSONSucceed() throws JSONException
    {
        final JSONArray jsonArray = new JSONArray();
        jsonArray.put(
            CellTest.makeJSONObject(1,2));  // throws
        jsonArray.put(
            CellTest.makeJSONObject(2,3));  // throws

        final Cells cells =
            new Cells(
                jsonArray.toString(),5,5,this);

        Assert.assertEquals(jsonArray.toString(), cells.json());
    }

    @Test() public void nullInputFourthConstructorSucceeds()
    { new Cells(null,5,5,this); }

    @Test() public void emptyInputFourthConstructorSucceeds()
    { new Cells("",5,5,this); }

    @Test() public void spacesInputFourthConstructorSucceeds()
    { new Cells("   ",5,5,this); }
    // endregion
    // endregion

    // region Overridden Method Tests
    @Test() public void toStringSucceeds()
    {
        final Cells cells =
            new Cells(75,75,this);
        Assert.assertEquals("null", cells.toString());

        cells.add(34,56);
        Assert.assertEquals("Cell(34, 56)", cells.toString());

        cells.add(11,61);
        Assert.assertEquals("Cell(11, 61), Cell(34, 56)",
            cells.toString() /* sorts! */);
    }

    @Test() public void equalsSucceedsAndFails()
    {
        final Cells
            firstCells  = new Cells(
                500,999,this),
            secondCells = new Cells(
                500,999,this);

        // noinspection SimplifiableJUnitAssertion
        Assert.assertTrue(firstCells.equals(secondCells));

        // noinspection EqualsBetweenInconvertibleTypes, SimplifiableJUnitAssertion
        Assert.assertFalse(firstCells.equals("nonsense"));

        {
            final int row = 123, col = 456;
            firstCells.add (row, col);

            // noinspection SimplifiableJUnitAssertion
            Assert.assertFalse(firstCells.equals(secondCells));

            secondCells.add(row, col);
        }
        // noinspection SimplifiableJUnitAssertion
        Assert.assertTrue(firstCells.equals(secondCells));
    }

    @Test() public void hashCodeSucceedsAndFails()
    {
        final Cells
            firstCells  = new Cells(
                500,999,this),
            secondCells = new Cells(
                500,999,this);
        Assert.assertEquals   (firstCells.hashCode(), secondCells.hashCode());
        Assert.assertNotEquals(firstCells.hashCode(),123);

        {
            final int row = 123, col = 456;
            firstCells.add(row, col);
            Assert.assertNotEquals(firstCells.hashCode(), secondCells.hashCode());
            secondCells.add(row, col);
        }
        Assert.assertEquals(firstCells.hashCode(), secondCells.hashCode());
    }

    @Test() public void cloneSucceeds()
    {
        final Cells cells =
            new Cells(100,100,this);

        // noinspection SimplifiableJUnitAssertion
        Assert.assertTrue(cells.equals(cells.clone()));

        cells.add(78,78);

        // noinspection SimplifiableJUnitAssertion
        Assert.assertTrue(cells.equals(cells.clone()));
    }
    // endregion

    // region Package Method Tests
    @Test() public void accumulateWorks()
    {
        final Cells receivingCells =
            new Cells(75,75,this);

        Assert.assertNull(receivingCells.json());

        receivingCells.accumulate(null); Assert.assertNull(receivingCells.json());

        final Cells contributingCells =
            new Cells(75,75,this);
        receivingCells.accumulate(contributingCells);
        Assert.assertNull(receivingCells.json());

        contributingCells.add(5,10);
        receivingCells.accumulate(contributingCells);
        Assert.assertEquals("[{\"row\":5,\"col\":10}]", receivingCells.json());

        receivingCells.accumulate(contributingCells);
        Assert.assertEquals("[{\"row\":5,\"col\":10}]", receivingCells.json());
    }

    // region makeRandomCells() Package Method Tests
    @Test(
        expected = Cells.MaxRowAndOrMaxColOutOfRange.class)
    public void maxRowTooSmallMakeRandomCellsFails()
    {
        new Cells(
            3,3,this).makeRandomCells(1,0,3);
    }

    @Test(
        expected = Cells.MaxRowAndOrMaxColOutOfRange.class)
    public void maxRowTooBigMakeRandomCellsFails()
    {
        new Cells(
            3,3,this).makeRandomCells(1,9,3);
    }

    @Test(
        expected = Cells.MaxRowAndOrMaxColOutOfRange.class)
    public void maxColTooSmallMakeRandomCellsFails()
    {
        new Cells(
            3,3,this).makeRandomCells(1,3,-5);
    }

    @Test(
        expected = Cells.MaxRowAndOrMaxColOutOfRange.class)
    public void maxColTooBigMakeRandomCellsFails()
    {
        new Cells(
            3,3,this).makeRandomCells(1,3,5);
    }

    @Test(expected = Cells.AmountIsTooLarge.class)
    public void tooBigAmountFails()
    {
        final int                                      maxRow = 2, maxCol = 2;
        final Cells cells  =
            new Cells(maxRow, maxCol,this);
        cells.makeRandomCells(200, maxRow, maxCol);
    }

    @Test() public void makeRandomCellsFailsAndSucceeds()
    {
        final Cells cells;
        {
            final int maxRow = 2, maxCol = 2;
            cells = new Cells(maxRow, maxCol,this);

            cells.makeRandomCells(0, maxRow, maxCol);
            Assert.assertEquals("null", cells.toString());

            cells.makeRandomCells(-5, maxRow, maxCol);
            Assert.assertEquals("null", cells.toString());

            cells.makeRandomCells(2, maxRow, maxCol);
        }
        Assert.assertNotEquals("null", cells.toString());
    }
    // endregion

    // region contains() Package Method Tests
    @Test() public void rowTooBigContainsFails()
    {
        final Cells cells =
            new Cells(2,2,this);
        final int col = 1;
        cells.add(1, col); Assert.assertFalse(cells.contains(100, col));
    }

    @Test() public void colTooBigContainsFails()
    {
        final Cells cells =
            new Cells(2,2,this);
        final int row = 1;
        cells.add(row,1); Assert.assertFalse(cells.contains(row,100));
    }

    @Test() public void containsFailsAndSucceeds()
    {
        final Cells cells =
            new Cells(2,2,this);
        {
            final int row = 1, col = 1;
            cells.add(row, col); Assert.assertTrue(cells.contains(row, col));
        }
        Assert.assertFalse(cells.contains(2,2));
    }
    // endregion

    @Test() public void containsAndAddSucceed()
    {
        final int                                       row  = 123, col = 456;
        final Cells cells =
            new Cells(250,500,this);
        cells.add(row, col); Assert.assertTrue(cells.contains(row, col));
    }

    // region add() Package Method Tests
    @Test(expected = IllegalArgumentException.class)
    public void tooSmallRowAddFails()
    {
        new Cells(
            25,50,this).add(0,20);
    }

    @Test(expected = IllegalArgumentException.class)
    public void tooBigRowAddFails()
    {
        new Cells(
            25,50,this).add(50,20);
    }

    @Test(expected = IllegalArgumentException.class)
    public void tooSmallColAddFails()
    {
        new Cells(
        25,50,this).add(-1,25);
    }

    @Test(expected = IllegalArgumentException.class)
    public void tooBigColAddFails()
    {
        new Cells(
            25,50,this).add(5,100);
    }

    @Test() public void duplicateAddDoesNotAdd()
    {
        final Cells cells =
            new Cells(5,5,this);
        {
            final int row = 1, col = 2;
            cells.add(row, col);
            cells.add(row, col);          // Does not add() but does not throw an exception, either.
        }
        Assert.assertEquals("Cell(1, 2)", cells.toString());
    }
    // endregion
    // endregion

    // region Public Method Tests
    // region contains() Public Method Tests
    @Test(expected = NullPointerException.class) public void nullContainsFails()
    {
        // noinspection ConstantConditions
        new Cells(
            5,5,this).contains(null);
    }

    @Test() public void bigContainsIsFalse()
    {
        Assert.assertFalse(new Cells(
            5,5,this).contains(
                new Cell(10,10,this)));
    }
    // endregion

    // region add() Public Method Tests
    @Test() public void nullAddIsFalse()
    {
        Assert.assertFalse(new Cells(
            5,5,this).add(null));
    }

    @Test(expected = IllegalArgumentException.class)
    public void tooBigCellAddFails()
    {
        new Cells(5,5,this).add(
            new Cell(9,9,this));
    }
    // endregion

    @Test() public void removeWorks()
    {
        final Cell cell =
            new Cell(3,3,this);
        final Cells cells =
            new Cells(5,5,this);
        Assert.assertFalse(cells.remove(cell));
        Assert.assertFalse(cells.remove(
            new Cell(4,4,this)));
        cells.add(cell); Assert.assertTrue(cells.remove(cell));
        Assert.assertFalse(cells.remove(cell));
    }
    // endregion
}