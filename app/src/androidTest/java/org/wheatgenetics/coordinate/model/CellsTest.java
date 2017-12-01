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
    @org.junit.Test
    public void secondConstructorAndJSONSucceed() throws org.json.JSONException
    {
        final org.json.JSONArray jsonArray = new org.json.JSONArray();
        jsonArray.put(org.wheatgenetics.coordinate.model.CellTest.makeJSONObject(1, 2));   // throws
        jsonArray.put(org.wheatgenetics.coordinate.model.CellTest.makeJSONObject(2, 3));   // throws

        final org.wheatgenetics.coordinate.model.Cells cells =
            new org.wheatgenetics.coordinate.model.Cells(jsonArray.toString());

        org.junit.Assert.assertEquals(cells.json(), jsonArray.toString());
    }

    @org.junit.Test
    public void nullInputSecondConstructorSucceeds()
    { new org.wheatgenetics.coordinate.model.Cells(null); }

    @org.junit.Test
    public void emptyInputSecondConstructorSucceeds()
    { new org.wheatgenetics.coordinate.model.Cells(""); }

    @org.junit.Test
    public void spacesInputSecondConstructorSucceeds()
    { new org.wheatgenetics.coordinate.model.Cells("   "); }
    // endregion

    // region Overridden Method Tests
    @org.junit.Test
    public void toStringAndClearSucceed()
    {
        final org.wheatgenetics.coordinate.model.Cells cells =
            new org.wheatgenetics.coordinate.model.Cells();
        org.junit.Assert.assertEquals(cells.toString(), "null");

        cells.add(34, 56); org.junit.Assert.assertEquals(cells.toString(), "Cell(34, 56)");

        cells.add(11, 61);
        org.junit.Assert.assertEquals(cells.toString(), "Cell(34, 56), Cell(11, 61)");

        cells.clear(); org.junit.Assert.assertEquals(cells.toString(), "empty");
    }

    @org.junit.Test @java.lang.SuppressWarnings("EqualsBetweenInconvertibleTypes")
    public void equalsSucceedsAndFails()
    {
        final org.wheatgenetics.coordinate.model.Cells
            firstCells  = new org.wheatgenetics.coordinate.model.Cells(),
            secondCells = new org.wheatgenetics.coordinate.model.Cells();
        org.junit.Assert.assertTrue (firstCells.equals(secondCells));
        org.junit.Assert.assertFalse(firstCells.equals("nonsense" ));

        final int row = 123, col = 456;
        firstCells.add(row, col); secondCells.add(row, col);
        org.junit.Assert.assertTrue(firstCells.equals(secondCells));

        secondCells.clear();
        org.junit.Assert.assertFalse(firstCells.equals(secondCells));

        secondCells.add(7, 999);
        org.junit.Assert.assertFalse(firstCells.equals(secondCells));
    }

    @org.junit.Test
    public void hashCodeSucceedsAndFails()
    {
        final org.wheatgenetics.coordinate.model.Cells
            firstCells  = new org.wheatgenetics.coordinate.model.Cells(),
            secondCells = new org.wheatgenetics.coordinate.model.Cells();
        org.junit.Assert.assertEquals   (firstCells.hashCode(), secondCells.hashCode());
        org.junit.Assert.assertNotEquals(firstCells.hashCode(), 123                   );

        final int row = 123, col = 456;
        firstCells.add(row, col); secondCells.add(row, col);
        org.junit.Assert.assertEquals(firstCells.hashCode(), secondCells.hashCode());

        secondCells.clear();
        org.junit.Assert.assertNotEquals(firstCells.hashCode(), secondCells.hashCode());

        secondCells.add(7, 999);
        org.junit.Assert.assertNotEquals(firstCells.hashCode(), secondCells.hashCode());
    }

    @org.junit.Test
    public void cloneSucceeds()
    {
        final org.wheatgenetics.coordinate.model.Cells cells =
            new org.wheatgenetics.coordinate.model.Cells();
        org.junit.Assert.assertTrue(cells.equals(cells.clone()));

        cells.add(78, 78);
        org.junit.Assert.assertTrue(cells.equals(cells.clone()));
    }
    // endregion

    // region Package Method Tests
    // region makeOneRandomCell() Package Method Tests
    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void tooSmallMaxRowMakeOneRandomCellFails()
    { new org.wheatgenetics.coordinate.model.Cells().makeOneRandomCell(0, 3); }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void tooSmallMaxColMakeOneRandomCellFails()
    { new org.wheatgenetics.coordinate.model.Cells().makeOneRandomCell(100, -3); }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void tooSmallMaxRowAndTooSmallMaxColMakeOneRandomCellFails()
    { new org.wheatgenetics.coordinate.model.Cells().makeOneRandomCell(-1, 0); }

    @org.junit.Test
    public void makeOneRandomCellSucceeds()
    { new org.wheatgenetics.coordinate.model.Cells().makeOneRandomCell(23, 45); }
    // endregion

    @org.junit.Test
    public void makeRandomCellsSucceeds()
    {
        final org.wheatgenetics.coordinate.model.Cells cells =
            new org.wheatgenetics.coordinate.model.Cells();
        cells.makeRandomCells( 0, 3, 3); org.junit.Assert.assertEquals   (cells.toString(), "null");
        cells.makeRandomCells(-5, 3, 3); org.junit.Assert.assertEquals   (cells.toString(), "null");
        cells.makeRandomCells( 2, 3, 3); org.junit.Assert.assertNotEquals(cells.toString(), "null");
    }

    @org.junit.Test
    public void isPresentAndAddSucceed()
    {
        final int row = 123, col = 456;
        final org.wheatgenetics.coordinate.model.Cells cells =
            new org.wheatgenetics.coordinate.model.Cells();
        cells.add(row, col);
        org.junit.Assert.assertTrue(cells.isPresent(row, col));
    }
    // endregion
}