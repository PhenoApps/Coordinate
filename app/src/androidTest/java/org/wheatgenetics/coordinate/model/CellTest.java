package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * android.graphics.Point
 *
 * org.json.JSONException
 * org.json.JSONObject
 *
 * org.junit.Assert
 * org.junit.Test
 *
 * org.wheatgenetics.coordinate.model.Cell
 */
@java.lang.SuppressWarnings("ClassExplicitlyExtendsObject")
public class CellTest extends java.lang.Object
{
    static org.json.JSONObject makeJSONObject(final int x, final int y)
    throws org.json.JSONException
    {
        final org.json.JSONObject result = new org.json.JSONObject();

        result.put("row", y);                                       // throws org.json.JSONException
        result.put("col", x);                                       // throws org.json.JSONException

        return result;
    }

    // region Constructor Tests
    // region First Constructor Tests
    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void zeroXFirstConstructorFails() { new org.wheatgenetics.coordinate.model.Cell(0, 5); }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void negativeXFirstConstructorFails()
    { new org.wheatgenetics.coordinate.model.Cell(-10, 5); }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void zeroYFirstConstructorFails() { new org.wheatgenetics.coordinate.model.Cell(8, 0); }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void negativeYFirstConstructorFails()
    { new org.wheatgenetics.coordinate.model.Cell(8, -5); }
    // endregion

    // region Second Constructor Tests
    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void zeroXSecondConstructorFails() throws org.json.JSONException
    {
        final int x = 0, y = 2;
        final org.wheatgenetics.coordinate.model.Cell cell =
            new org.wheatgenetics.coordinate.model.Cell(x, y);
        org.junit.Assert.assertTrue(cell.equals(new org.wheatgenetics.coordinate.model.Cell(
            org.wheatgenetics.coordinate.model.CellTest.makeJSONObject(x, y))));     // throws org.-
    }                                                                                //  json.JSON-
                                                                                     //  Exception
    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void negativeXSecondConstructorFails() throws org.json.JSONException
    {
        final int x = -1, y = 2;
        final org.wheatgenetics.coordinate.model.Cell cell =
            new org.wheatgenetics.coordinate.model.Cell(x, y);
        org.junit.Assert.assertTrue(cell.equals(new org.wheatgenetics.coordinate.model.Cell(
            org.wheatgenetics.coordinate.model.CellTest.makeJSONObject(x, y))));     // throws org.-
    }                                                                                //  json.JSON-
                                                                                     //  Exception
    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void zeroYSecondConstructorFails()  throws org.json.JSONException
    {
        final int x = 1, y = 0;
        final org.wheatgenetics.coordinate.model.Cell cell =
            new org.wheatgenetics.coordinate.model.Cell(x, y);
        org.junit.Assert.assertTrue(cell.equals(new org.wheatgenetics.coordinate.model.Cell(
            org.wheatgenetics.coordinate.model.CellTest.makeJSONObject(x, y))));     // throws org.-
    }                                                                                //  json.JSON-
                                                                                     //  Exception
    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void netativeYSecondConstructorFails() throws org.json.JSONException
    {
        final int x = 1, y = -2;
        final org.wheatgenetics.coordinate.model.Cell cell =
            new org.wheatgenetics.coordinate.model.Cell(x, y);
        org.junit.Assert.assertTrue(cell.equals(new org.wheatgenetics.coordinate.model.Cell(
            org.wheatgenetics.coordinate.model.CellTest.makeJSONObject(x, y))));     // throws org.-
    }                                                                                //  json.JSON-
                                                                                     //  Exception
    @org.junit.Test
    public void secondConstructorSucceeds() throws org.json.JSONException
    {
        final int x = 1, y = 2;
        final org.wheatgenetics.coordinate.model.Cell cell =
            new org.wheatgenetics.coordinate.model.Cell(x, y);
        org.junit.Assert.assertTrue(cell.equals(new org.wheatgenetics.coordinate.model.Cell(
            org.wheatgenetics.coordinate.model.CellTest.makeJSONObject(x, y))));     // throws org.-
    }                                                                                //  json.JSON-
                                                                                     //  Exception
    @org.junit.Test(expected = java.lang.NullPointerException.class)
    public void nullInputSecondConstructorFails() throws org.json.JSONException
    { new org.wheatgenetics.coordinate.model.Cell(null); /* throws org.json.JSONException */ }

    @org.junit.Test(expected = org.json.JSONException.class)
    public void emptyInputSecondConstructorFails() throws org.json.JSONException
    {
        new org.wheatgenetics.coordinate.model.Cell(                // throws org.json.JSONException
            new org.json.JSONObject());
    }
    // endregion
    // endregion

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
    public void zeroXRandomFails() { org.wheatgenetics.coordinate.model.Cell.random(0, 9); }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void negativeXRandomFails() { org.wheatgenetics.coordinate.model.Cell.random(-5, 9); }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void oneXRandomFails() { org.wheatgenetics.coordinate.model.Cell.random(1, 9); }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void zeroYRandomFails() { org.wheatgenetics.coordinate.model.Cell.random(5, 0); }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void negativeYRandomFails() { org.wheatgenetics.coordinate.model.Cell.random(5, -9); }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void oneYRandomFails() { org.wheatgenetics.coordinate.model.Cell.random(5, 1); }

    @org.junit.Test
    public void jsonSucceeds() throws org.json.JSONException
    {
        final int x = 1, y = 2;
        final org.wheatgenetics.coordinate.model.Cell cell =
            new org.wheatgenetics.coordinate.model.Cell(x, y);

        final org.json.JSONObject jsonObject =
            org.wheatgenetics.coordinate.model.CellTest.makeJSONObject(x, y);   // throws org.json.-
                                                                                //  JSONException
        assert null != jsonObject;
        org.junit.Assert.assertEquals(cell.json().toString(), jsonObject.toString());
    }
    // endregion
}