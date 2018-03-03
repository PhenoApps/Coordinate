package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * org.json.JSONArray
 * org.json.JSONException
 *
 * org.junit.Assert
 * org.junit.Test
 *
 * org.wheatgenetics.coordinate.model.Cells
 *
 * org.wheatgenetics.coordinate.model.CellTest
 */
@java.lang.SuppressWarnings("ClassExplicitlyExtendsObject")
public class CellsTest extends java.lang.Object
{
    // region Constructor Tests
    // region Second Constructor Tests
    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void zeroMaxXSecondConstructorFails()
    { new org.wheatgenetics.coordinate.model.Cells(0, 5); }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void negativeMaxXSecondConstructorFails()
    { new org.wheatgenetics.coordinate.model.Cells(-5, 5); }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void zeroMaxYSecondConstructorFails()
    { new org.wheatgenetics.coordinate.model.Cells(5, 0); }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void negativeMaxYSecondConstructorFails()
    { new org.wheatgenetics.coordinate.model.Cells(5, -5); }

    @org.junit.Test
    public void secondConstructorSucceeds() { new org.wheatgenetics.coordinate.model.Cells(5, 5); }
    // endregion

    // region Third Constructor Tests
    @org.junit.Test
    public void thirdConstructorAndJSONSucceed() throws org.json.JSONException
    {
        final org.json.JSONArray jsonArray = new org.json.JSONArray();
        jsonArray.put(org.wheatgenetics.coordinate.model.CellTest.makeJSONObject(1, 2));   // throws
        jsonArray.put(org.wheatgenetics.coordinate.model.CellTest.makeJSONObject(2, 3));   // throws

        final org.wheatgenetics.coordinate.model.Cells cells =
            new org.wheatgenetics.coordinate.model.Cells(jsonArray.toString(), 5, 5);

        org.junit.Assert.assertEquals(cells.json(), jsonArray.toString());
    }

    @org.junit.Test
    public void nullInputThirdConstructorSucceeds()
    { new org.wheatgenetics.coordinate.model.Cells(null, 5, 5); }

    @org.junit.Test
    public void emptyInputThirdConstructorSucceeds()
    { new org.wheatgenetics.coordinate.model.Cells("", 5, 5); }

    @org.junit.Test
    public void spacesInputThirdConstructorSucceeds()
    { new org.wheatgenetics.coordinate.model.Cells("   ", 5, 5); }
    // endregion
    // endregion

    // region Overridden Method Tests
    @org.junit.Test
    public void toStringSucceeds()
    {
        final org.wheatgenetics.coordinate.model.Cells cells =
            new org.wheatgenetics.coordinate.model.Cells(75, 75);
        org.junit.Assert.assertEquals(cells.toString(), "null");

        cells.add(34, 56); org.junit.Assert.assertEquals(cells.toString(), "Cell(34, 56)");

        cells.add(11, 61);
        org.junit.Assert.assertEquals(cells.toString(), "Cell(11, 61), Cell(34, 56)");     // sorts!
    }

    @org.junit.Test @java.lang.SuppressWarnings("EqualsBetweenInconvertibleTypes")
    public void equalsSucceedsAndFails()
    {
        final org.wheatgenetics.coordinate.model.Cells
            firstCells  = new org.wheatgenetics.coordinate.model.Cells(500, 999),
            secondCells = new org.wheatgenetics.coordinate.model.Cells(500, 999);
        org.junit.Assert.assertTrue (firstCells.equals(secondCells));
        org.junit.Assert.assertFalse(firstCells.equals("nonsense" ));

        {
            final int row = 123, col = 456;
            firstCells.add(row, col);
            org.junit.Assert.assertFalse(firstCells.equals(secondCells));
            secondCells.add(row, col);
        }
        org.junit.Assert.assertTrue(firstCells.equals(secondCells));
    }

    @org.junit.Test
    public void hashCodeSucceedsAndFails()
    {
        final org.wheatgenetics.coordinate.model.Cells
            firstCells  = new org.wheatgenetics.coordinate.model.Cells(500, 999),
            secondCells = new org.wheatgenetics.coordinate.model.Cells(500, 999);
        org.junit.Assert.assertEquals   (firstCells.hashCode(), secondCells.hashCode());
        org.junit.Assert.assertNotEquals(firstCells.hashCode(), 123                   );

        {
            final int row = 123, col = 456;
            firstCells.add(row, col);
            org.junit.Assert.assertNotEquals(firstCells.hashCode(), secondCells.hashCode());
            secondCells.add(row, col);
        }
        org.junit.Assert.assertEquals(firstCells.hashCode(), secondCells.hashCode());
    }

    @org.junit.Test
    public void cloneSucceeds()
    {
        final org.wheatgenetics.coordinate.model.Cells cells =
            new org.wheatgenetics.coordinate.model.Cells(100, 100);
        org.junit.Assert.assertTrue(cells.equals(cells.clone()));

        cells.add(78, 78);
        org.junit.Assert.assertTrue(cells.equals(cells.clone()));
    }
    // endregion

    // region Package Method Tests
    // region makeRandomCells() Package Method Tests
    @org.junit.Test
    public void makeRandomCellsFailsAndSucceeds()
    {
        final int                                      maxRow = 2, maxCol = 2;
        final org.wheatgenetics.coordinate.model.Cells cells  =
            new org.wheatgenetics.coordinate.model.Cells(maxRow, maxCol);

        cells.makeRandomCells(0, maxRow, maxCol, null);
        org.junit.Assert.assertEquals(cells.toString(), "null");

        cells.makeRandomCells(-5, maxRow, maxCol, null);
        org.junit.Assert.assertEquals(cells.toString(), "null");

        cells.makeRandomCells(2, maxRow, maxCol, null);
        org.junit.Assert.assertNotEquals(cells.toString(), "null");
    }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void tooBigAmountFails()
    {
        final int                                      maxRow = 2, maxCol = 2;
        final org.wheatgenetics.coordinate.model.Cells cells  =
            new org.wheatgenetics.coordinate.model.Cells(maxRow, maxCol);
        cells.makeRandomCells(200, maxRow, maxCol, null);
    }
    // endregion

    // region contains() Package Method Tests
    @org.junit.Test
    public void rowTooBigContainsFails()
    {
        final org.wheatgenetics.coordinate.model.Cells cells =
            new org.wheatgenetics.coordinate.model.Cells(2, 2);
        final int col = 1;
        cells.add(1, col);
        org.junit.Assert.assertFalse(cells.contains(100, col));
    }

    @org.junit.Test
    public void colTooBigContainsFails()
    {
        final org.wheatgenetics.coordinate.model.Cells cells =
            new org.wheatgenetics.coordinate.model.Cells(2, 2);
        final int row = 1;
        cells.add(row, 1);
        org.junit.Assert.assertFalse(cells.contains(row, 100));
    }

    @org.junit.Test
    public void containsFailsAndSucceeds()
    {
        final org.wheatgenetics.coordinate.model.Cells cells =
            new org.wheatgenetics.coordinate.model.Cells(2, 2);
        {
            final int row = 1, col = 1;
            cells.add(row, col);
            org.junit.Assert.assertTrue(cells.contains(row, col));
        }
        org.junit.Assert.assertFalse(cells.contains(2, 2));
    }
    // endregion

    @org.junit.Test
    public void containsAndAddSucceed()
    {
        final int row = 123, col = 456;
        final org.wheatgenetics.coordinate.model.Cells cells =
            new org.wheatgenetics.coordinate.model.Cells(250, 500);
        cells.add(row, col);
        org.junit.Assert.assertTrue(cells.contains(row, col));
    }

    // region add() Package Method Tests
    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void tooSmallRowAddFails()
    {
        final org.wheatgenetics.coordinate.model.Cells cells =
            new org.wheatgenetics.coordinate.model.Cells(25, 50);
        cells.add(0, 20);
    }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void tooBigRowAddFails()
    {
        final org.wheatgenetics.coordinate.model.Cells cells =
            new org.wheatgenetics.coordinate.model.Cells(25, 50);
        cells.add(50, 20);
    }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void tooSmallColAddFails()
    {
        final org.wheatgenetics.coordinate.model.Cells cells =
            new org.wheatgenetics.coordinate.model.Cells(25, 50);
        cells.add(-1, 25);
    }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void tooBigColAddFails()
    {
        final org.wheatgenetics.coordinate.model.Cells cells =
            new org.wheatgenetics.coordinate.model.Cells(25, 50);
        cells.add(5, 100);
    }

    @org.junit.Test
    public void duplicateAddFails()
    {
        final org.wheatgenetics.coordinate.model.Cells cells =
            new org.wheatgenetics.coordinate.model.Cells(5, 5);
        {
            final int row = 1, col = 2;
            cells.add(row, col);
            cells.add(row, col);          // Does not add() but does not throw an exception, either.
        }
        org.junit.Assert.assertEquals(cells.toString(), "Cell(1, 2)");
    }
    // endregion
    // endregion
}