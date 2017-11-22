package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * android.graphics.Point
 *
 * org.json.JSONException
 *
 * org.junit.Assert
 * org.junit.Test
 *
 * org.wheatgenetics.coordinate.model.Cell
 */
@java.lang.SuppressWarnings("ClassExplicitlyExtendsObject")
public class CellTest extends java.lang.Object
{
    // region Overridden Method Tests
    @org.junit.Test
    public void toStringAndHashCodeSucceed()
    {
        final int x = 1, y = 2;
        final org.wheatgenetics.coordinate.model.Cell cell =
            new org.wheatgenetics.coordinate.model.Cell(x, y);
        final android.graphics.Point point = new android.graphics.Point(x, y);
        org.junit.Assert.assertEquals(cell.toString(), point.toString()           );
        org.junit.Assert.assertEquals(cell.hashCode(), point.toString().hashCode());
    }

    @org.junit.Test
    public void equalsSucceedsAndFails()
    {
        final int x = 1, y = 2;
        final org.wheatgenetics.coordinate.model.Cell cell =
            new org.wheatgenetics.coordinate.model.Cell(x, y);
        org.junit.Assert.assertTrue(cell.equals(new org.wheatgenetics.coordinate.model.Cell(x, y)));
        org.junit.Assert.assertFalse(cell.equals(
            new org.wheatgenetics.coordinate.model.Cell(3, y)));
        org.junit.Assert.assertFalse(cell.equals(
            new org.wheatgenetics.coordinate.model.Cell(x, 4)));
    }

    @org.junit.Test
    public void cloneSucceeds()
    {
        final org.wheatgenetics.coordinate.model.Cell cell =
            new org.wheatgenetics.coordinate.model.Cell(23, 999);
        org.junit.Assert.assertTrue(cell.equals(cell.clone()));
    }
    // endregion

    // region Package Method Tests
    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void randomZeroXFails() { org.wheatgenetics.coordinate.model.Cell.random(0, 9); }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void randomNegativeXFails() { org.wheatgenetics.coordinate.model.Cell.random(-5, 9); }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void randomZeroYFails() { org.wheatgenetics.coordinate.model.Cell.random(5, 0); }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void randomNegativeYFails() { org.wheatgenetics.coordinate.model.Cell.random(5, -9); }

    @org.junit.Test
    public void jsonSucceeds() throws org.json.JSONException
    {
        final int x = 1, y = 2;
        final org.wheatgenetics.coordinate.model.Cell cell =
            new org.wheatgenetics.coordinate.model.Cell(x, y);

        final org.json.JSONObject jsonObject = new org.json.JSONObject();
        jsonObject.put("row", y);                                   // throws org.json.JSONException
        jsonObject.put("col", x);                                   // throws org.json.JSONException

        org.junit.Assert.assertEquals(cell.json().toString(), jsonObject.toString());
    }
    // endregion
}