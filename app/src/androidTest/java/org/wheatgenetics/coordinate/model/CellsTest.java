package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * android.content.res.Resources.NotFoundException
 *
 * androidx.annotation.IntRange
 * androidx.annotation.Nullable
 * androidx.annotation.PluralsRes
 * androidx.annotation.StringRes
 *
 * org.json.JSONArray
 * org.json.JSONException
 *
 * org.junit.Assert
 * org.junit.Test
 *
 * org.wheatgenetics.coordinate.StringGetter
 *
 * org.wheatgenetics.coordinate.model.Cell
 * org.wheatgenetics.coordinate.model.Cells
 * org.wheatgenetics.coordinate.model.Cells.AmountIsTooLarge.class
 * org.wheatgenetics.coordinate.model.Cells.MaxRowAndOrMaxColOutOfRange
 *
 * org.wheatgenetics.coordinate.model.CellTest
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"}) public class CellsTest
extends java.lang.Object implements org.wheatgenetics.coordinate.StringGetter
{
    // region org.wheatgenetics.coordinate.StringGetter Overridden Methods
    @java.lang.Override @androidx.annotation.Nullable public java.lang.String get(
    @androidx.annotation.StringRes final int resId)
    {
        // noinspection SwitchStatementWithTooFewBranches
        switch (resId) { default: org.junit.Assert.fail(); return null; }
    }

    @java.lang.Override @androidx.annotation.NonNull public java.lang.String getQuantity(
    @androidx.annotation.PluralsRes         final int                 resId     ,
    @androidx.annotation.IntRange(from = 0) final int                 quantity  ,
    @androidx.annotation.Nullable           final java.lang.Object... formatArgs)
    throws android.content.res.Resources.NotFoundException { org.junit.Assert.fail(); return null; }
    // endregion

    // region Constructor Tests
    // region Second Constructor Tests
    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void zeroMaxRowSecondConstructorFails()
    { new org.wheatgenetics.coordinate.model.Cells(0,5,this); }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void negativeMaxRowSecondConstructorFails()
    { new org.wheatgenetics.coordinate.model.Cells(-5,5,this); }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void zeroMaxColSecondConstructorFails()
    { new org.wheatgenetics.coordinate.model.Cells(5,0,this); }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void negativeMaxColSecondConstructorFails()
    { new org.wheatgenetics.coordinate.model.Cells(5,-5,this); }

    @org.junit.Test() public void secondConstructorSucceeds()
    { new org.wheatgenetics.coordinate.model.Cells(5,5,this); }
    // endregion

    // region Fourth Constructor Tests
    @org.junit.Test() public void fourthConstructorAndJSONSucceed() throws org.json.JSONException
    {
        final org.json.JSONArray jsonArray = new org.json.JSONArray();
        jsonArray.put(
            org.wheatgenetics.coordinate.model.CellTest.makeJSONObject(1,2));  // throws
        jsonArray.put(
            org.wheatgenetics.coordinate.model.CellTest.makeJSONObject(2,3));  // throws

        final org.wheatgenetics.coordinate.model.Cells cells =
            new org.wheatgenetics.coordinate.model.Cells(
                jsonArray.toString(),5,5,this);

        org.junit.Assert.assertEquals(jsonArray.toString(), cells.json());
    }

    @org.junit.Test() public void nullInputFourthConstructorSucceeds()
    { new org.wheatgenetics.coordinate.model.Cells(null,5,5,this); }

    @org.junit.Test() public void emptyInputFourthConstructorSucceeds()
    { new org.wheatgenetics.coordinate.model.Cells("",5,5,this); }

    @org.junit.Test() public void spacesInputFourthConstructorSucceeds()
    { new org.wheatgenetics.coordinate.model.Cells("   ",5,5,this); }
    // endregion
    // endregion

    // region Overridden Method Tests
    @org.junit.Test() public void toStringSucceeds()
    {
        final org.wheatgenetics.coordinate.model.Cells cells =
            new org.wheatgenetics.coordinate.model.Cells(75,75,this);
        org.junit.Assert.assertEquals("null", cells.toString());

        cells.add(34,56);
        org.junit.Assert.assertEquals("Cell(34, 56)", cells.toString());

        cells.add(11,61);
        org.junit.Assert.assertEquals("Cell(11, 61), Cell(34, 56)",
            cells.toString() /* sorts! */);
    }

    @org.junit.Test() public void equalsSucceedsAndFails()
    {
        final org.wheatgenetics.coordinate.model.Cells
            firstCells  = new org.wheatgenetics.coordinate.model.Cells(
                500,999,this),
            secondCells = new org.wheatgenetics.coordinate.model.Cells(
                500,999,this);

        // noinspection SimplifiableJUnitAssertion
        org.junit.Assert.assertTrue(firstCells.equals(secondCells));

        // noinspection EqualsBetweenInconvertibleTypes, SimplifiableJUnitAssertion
        org.junit.Assert.assertFalse(firstCells.equals("nonsense"));

        {
            final int row = 123, col = 456;
            firstCells.add (row, col);

            // noinspection SimplifiableJUnitAssertion
            org.junit.Assert.assertFalse(firstCells.equals(secondCells));

            secondCells.add(row, col);
        }
        // noinspection SimplifiableJUnitAssertion
        org.junit.Assert.assertTrue(firstCells.equals(secondCells));
    }

    @org.junit.Test() public void hashCodeSucceedsAndFails()
    {
        final org.wheatgenetics.coordinate.model.Cells
            firstCells  = new org.wheatgenetics.coordinate.model.Cells(
                500,999,this),
            secondCells = new org.wheatgenetics.coordinate.model.Cells(
                500,999,this);
        org.junit.Assert.assertEquals   (firstCells.hashCode(), secondCells.hashCode());
        org.junit.Assert.assertNotEquals(firstCells.hashCode(),123);

        {
            final int row = 123, col = 456;
            firstCells.add(row, col);
            org.junit.Assert.assertNotEquals(firstCells.hashCode(), secondCells.hashCode());
            secondCells.add(row, col);
        }
        org.junit.Assert.assertEquals(firstCells.hashCode(), secondCells.hashCode());
    }

    @org.junit.Test() public void cloneSucceeds()
    {
        final org.wheatgenetics.coordinate.model.Cells cells =
            new org.wheatgenetics.coordinate.model.Cells(100,100,this);

        // noinspection SimplifiableJUnitAssertion
        org.junit.Assert.assertTrue(cells.equals(cells.clone()));

        cells.add(78,78);

        // noinspection SimplifiableJUnitAssertion
        org.junit.Assert.assertTrue(cells.equals(cells.clone()));
    }
    // endregion

    // region Package Method Tests
    @org.junit.Test() public void accumulateWorks()
    {
        final org.wheatgenetics.coordinate.model.Cells receivingCells =
            new org.wheatgenetics.coordinate.model.Cells(75,75,this);

        org.junit.Assert.assertNull(receivingCells.json());

        receivingCells.accumulate(null); org.junit.Assert.assertNull(receivingCells.json());

        final org.wheatgenetics.coordinate.model.Cells contributingCells =
            new org.wheatgenetics.coordinate.model.Cells(75,75,this);
        receivingCells.accumulate(contributingCells);
        org.junit.Assert.assertNull(receivingCells.json());

        contributingCells.add(5,10);
        receivingCells.accumulate(contributingCells);
        org.junit.Assert.assertEquals("[{\"row\":5,\"col\":10}]", receivingCells.json());

        receivingCells.accumulate(contributingCells);
        org.junit.Assert.assertEquals("[{\"row\":5,\"col\":10}]", receivingCells.json());
    }

    // region makeRandomCells() Package Method Tests
    @org.junit.Test(
        expected = org.wheatgenetics.coordinate.model.Cells.MaxRowAndOrMaxColOutOfRange.class)
    public void maxRowTooSmallMakeRandomCellsFails()
    {
        new org.wheatgenetics.coordinate.model.Cells(
            3,3,this).makeRandomCells(1,0,3);
    }

    @org.junit.Test(
        expected = org.wheatgenetics.coordinate.model.Cells.MaxRowAndOrMaxColOutOfRange.class)
    public void maxRowTooBigMakeRandomCellsFails()
    {
        new org.wheatgenetics.coordinate.model.Cells(
            3,3,this).makeRandomCells(1,9,3);
    }

    @org.junit.Test(
        expected = org.wheatgenetics.coordinate.model.Cells.MaxRowAndOrMaxColOutOfRange.class)
    public void maxColTooSmallMakeRandomCellsFails()
    {
        new org.wheatgenetics.coordinate.model.Cells(
            3,3,this).makeRandomCells(1,3,-5);
    }

    @org.junit.Test(
        expected = org.wheatgenetics.coordinate.model.Cells.MaxRowAndOrMaxColOutOfRange.class)
    public void maxColTooBigMakeRandomCellsFails()
    {
        new org.wheatgenetics.coordinate.model.Cells(
            3,3,this).makeRandomCells(1,3,5);
    }

    @org.junit.Test(expected = org.wheatgenetics.coordinate.model.Cells.AmountIsTooLarge.class)
    public void tooBigAmountFails()
    {
        final int                                      maxRow = 2, maxCol = 2;
        final org.wheatgenetics.coordinate.model.Cells cells  =
            new org.wheatgenetics.coordinate.model.Cells(maxRow, maxCol,this);
        cells.makeRandomCells(200, maxRow, maxCol);
    }

    @org.junit.Test() public void makeRandomCellsFailsAndSucceeds()
    {
        final org.wheatgenetics.coordinate.model.Cells cells;
        {
            final int maxRow = 2, maxCol = 2;
            cells = new org.wheatgenetics.coordinate.model.Cells(maxRow, maxCol,this);

            cells.makeRandomCells(0, maxRow, maxCol);
            org.junit.Assert.assertEquals("null", cells.toString());

            cells.makeRandomCells(-5, maxRow, maxCol);
            org.junit.Assert.assertEquals("null", cells.toString());

            cells.makeRandomCells(2, maxRow, maxCol);
        }
        org.junit.Assert.assertNotEquals("null", cells.toString());
    }
    // endregion

    // region contains() Package Method Tests
    @org.junit.Test() public void rowTooBigContainsFails()
    {
        final org.wheatgenetics.coordinate.model.Cells cells =
            new org.wheatgenetics.coordinate.model.Cells(2,2,this);
        final int col = 1;
        cells.add(1, col); org.junit.Assert.assertFalse(cells.contains(100, col));
    }

    @org.junit.Test() public void colTooBigContainsFails()
    {
        final org.wheatgenetics.coordinate.model.Cells cells =
            new org.wheatgenetics.coordinate.model.Cells(2,2,this);
        final int row = 1;
        cells.add(row,1); org.junit.Assert.assertFalse(cells.contains(row,100));
    }

    @org.junit.Test() public void containsFailsAndSucceeds()
    {
        final org.wheatgenetics.coordinate.model.Cells cells =
            new org.wheatgenetics.coordinate.model.Cells(2,2,this);
        {
            final int row = 1, col = 1;
            cells.add(row, col); org.junit.Assert.assertTrue(cells.contains(row, col));
        }
        org.junit.Assert.assertFalse(cells.contains(2,2));
    }
    // endregion

    @org.junit.Test() public void containsAndAddSucceed()
    {
        final int                                       row  = 123, col = 456;
        final org.wheatgenetics.coordinate.model.Cells cells =
            new org.wheatgenetics.coordinate.model.Cells(250,500,this);
        cells.add(row, col); org.junit.Assert.assertTrue(cells.contains(row, col));
    }

    // region add() Package Method Tests
    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void tooSmallRowAddFails()
    {
        new org.wheatgenetics.coordinate.model.Cells(
            25,50,this).add(0,20);
    }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void tooBigRowAddFails()
    {
        new org.wheatgenetics.coordinate.model.Cells(
            25,50,this).add(50,20);
    }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void tooSmallColAddFails()
    {
        new org.wheatgenetics.coordinate.model.Cells(
        25,50,this).add(-1,25);
    }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void tooBigColAddFails()
    {
        new org.wheatgenetics.coordinate.model.Cells(
            25,50,this).add(5,100);
    }

    @org.junit.Test() public void duplicateAddDoesNotAdd()
    {
        final org.wheatgenetics.coordinate.model.Cells cells =
            new org.wheatgenetics.coordinate.model.Cells(5,5,this);
        {
            final int row = 1, col = 2;
            cells.add(row, col);
            cells.add(row, col);          // Does not add() but does not throw an exception, either.
        }
        org.junit.Assert.assertEquals("Cell(1, 2)", cells.toString());
    }
    // endregion
    // endregion

    // region Public Method Tests
    // region contains() Public Method Tests
    @org.junit.Test(expected = java.lang.NullPointerException.class) public void nullContainsFails()
    {
        // noinspection ConstantConditions
        new org.wheatgenetics.coordinate.model.Cells(
            5,5,this).contains(null);
    }

    @org.junit.Test() public void bigContainsIsFalse()
    {
        org.junit.Assert.assertFalse(new org.wheatgenetics.coordinate.model.Cells(
            5,5,this).contains(
                new org.wheatgenetics.coordinate.model.Cell(10,10)));
    }
    // endregion

    // region add() Public Method Tests
    @org.junit.Test() public void nullAddIsFalse()
    {
        org.junit.Assert.assertFalse(new org.wheatgenetics.coordinate.model.Cells(
            5,5,this).add(null));
    }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void tooBigCellAddFails()
    {
        new org.wheatgenetics.coordinate.model.Cells(5,5,this).add(
            new org.wheatgenetics.coordinate.model.Cell(9,9));
    }
    // endregion

    @org.junit.Test() public void removeWorks()
    {
        final org.wheatgenetics.coordinate.model.Cell cell =
            new org.wheatgenetics.coordinate.model.Cell(3,3);
        final org.wheatgenetics.coordinate.model.Cells cells =
            new org.wheatgenetics.coordinate.model.Cells(5,5,this);
        org.junit.Assert.assertFalse(cells.remove(cell));
        org.junit.Assert.assertFalse(cells.remove(
            new org.wheatgenetics.coordinate.model.Cell(4,4)));
        cells.add(cell); org.junit.Assert.assertTrue(cells.remove(cell));
        org.junit.Assert.assertFalse(cells.remove(cell));
    }
    // endregion
}