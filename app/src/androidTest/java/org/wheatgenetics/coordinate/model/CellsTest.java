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
    @org.junit.Test
    public void secondConstructorAndJSONSucceeds() throws org.json.JSONException
    {
        final org.json.JSONArray jsonArray = new org.json.JSONArray();
        jsonArray.put(org.wheatgenetics.coordinate.model.CellTest.makeJSONObject(1, 2));   // throws
        jsonArray.put(org.wheatgenetics.coordinate.model.CellTest.makeJSONObject(2, 3));   // throws

        final org.wheatgenetics.coordinate.model.Cells cells =
            new org.wheatgenetics.coordinate.model.Cells(jsonArray.toString());

        org.junit.Assert.assertEquals(cells.json(), jsonArray.toString());
    }

    // region Overridden Method Tests
    @org.junit.Test
    public void toStringAndClearSucceed()
    {
        final org.wheatgenetics.coordinate.model.Cells cells =
            new org.wheatgenetics.coordinate.model.Cells();
        org.junit.Assert.assertEquals(cells.toString(), "null");

        cells.add(34, 56); org.junit.Assert.assertEquals(cells.toString(), "Point(34, 56)");

        cells.add(11, 61);
        org.junit.Assert.assertEquals(cells.toString(), "Point(34, 56), Point(11, 61)");

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

        final int x = 123, y = 456;
        firstCells.add(x, y); secondCells.add(x, y);
        org.junit.Assert.assertTrue(firstCells.equals(secondCells));

        secondCells.clear();
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

        final int x = 123, y = 456;
        firstCells.add(x, y); secondCells.add(x, y);
        org.junit.Assert.assertEquals(firstCells.hashCode(), secondCells.hashCode());

        secondCells.clear();
        org.junit.Assert.assertFalse(firstCells.equals(secondCells));
    }

    @org.junit.Test
    public void cloneSucceeds()
    {
        final org.wheatgenetics.coordinate.model.Cells cells =
            new org.wheatgenetics.coordinate.model.Cells();
        org.junit.Assert.assertTrue(cells.equals(cells.clone()));
    }
    // endregion

    // region Package Method Tests
    // region makeOneRandomCell() Package Method Tests
    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void xBoundMakeOneRandomCellFails()
    { new org.wheatgenetics.coordinate.model.Cells().makeOneRandomCell(1, 3); }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void yBoundMakeOneRandomCellFails()
    { new org.wheatgenetics.coordinate.model.Cells().makeOneRandomCell(100, -3); }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void xBoundAndYBoundMakeOneRandomCellFails()
    { new org.wheatgenetics.coordinate.model.Cells().makeOneRandomCell(1, 0); }

    @org.junit.Test
    public void makeOneRandomCellSucceeds()
    { new org.wheatgenetics.coordinate.model.Cells().makeOneRandomCell(23, 45); }
    // endregion

    // region makeRandomCells() Package Method Tests
    @org.junit.Test
    public void makeRandomCellsSucceeds()
    {
        final org.wheatgenetics.coordinate.model.Cells cells =
            new org.wheatgenetics.coordinate.model.Cells();
        cells.makeRandomCells( 0, 3, 3); org.junit.Assert.assertEquals   (cells.toString(), "null");
        cells.makeRandomCells(-5, 3, 3); org.junit.Assert.assertEquals   (cells.toString(), "null");
        cells.makeRandomCells( 2, 3, 3); org.junit.Assert.assertNotEquals(cells.toString(), "null");
    }
    // endregion

    @org.junit.Test
    public void isPresentAndAddSucceed()
    {
        final int x = 123, y = 456;
        final org.wheatgenetics.coordinate.model.Cells cells =
            new org.wheatgenetics.coordinate.model.Cells();
        cells.add(x, y);
        org.junit.Assert.assertTrue(cells.isPresent(x, y));
    }
    // endregion
}