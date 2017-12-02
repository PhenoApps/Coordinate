package org.wheatgenetics.coordinate.model;

/**
 * Uses:
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
    static org.json.JSONObject makeJSONObject(final int row, final int col)
    throws org.json.JSONException
    {
        final org.json.JSONObject result = new org.json.JSONObject();

        result.put("row", row);                                     // throws org.json.JSONException
        result.put("col", col);                                     // throws org.json.JSONException

        return result;
    }

    // region Constructor Tests
    // region Second Constructor Tests
    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void zeroRowSecondConstructorFails()
    { new org.wheatgenetics.coordinate.model.Cell(0, 5); }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void negativeRowSecondConstructorFails()
    { new org.wheatgenetics.coordinate.model.Cell(-10, 5); }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void zeroColSecondConstructorFails()
    { new org.wheatgenetics.coordinate.model.Cell(8, 0); }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void negativeColSecondConstructorFails()
    { new org.wheatgenetics.coordinate.model.Cell(8, -5); }
    // endregion

    // region Third Constructor Tests
    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void zeroRowThirdConstructorFails() throws org.json.JSONException
    {
        new org.wheatgenetics.coordinate.model.Cell(
            org.wheatgenetics.coordinate.model.CellTest.makeJSONObject(0, 2));   // throws org.json-
    }                                                                            //  .JSONException

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void negativeRowThirdConstructorFails() throws org.json.JSONException
    {
        new org.wheatgenetics.coordinate.model.Cell(
            org.wheatgenetics.coordinate.model.CellTest.makeJSONObject(-1, 2));  // throws org.json-
    }                                                                            //  .JSONException

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void zeroColThirdConstructorFails()  throws org.json.JSONException
    {
        new org.wheatgenetics.coordinate.model.Cell(
            org.wheatgenetics.coordinate.model.CellTest.makeJSONObject(1, 0));   // throws org.json-
    }                                                                            //  .JSONException

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void negativeColThirdConstructorFails() throws org.json.JSONException
    {
        new org.wheatgenetics.coordinate.model.Cell(
            org.wheatgenetics.coordinate.model.CellTest.makeJSONObject(1, -2));  // throws org.json-
    }                                                                            //  .JSONException

    @org.junit.Test
    public void thirdConstructorSucceeds() throws org.json.JSONException
    {
        new org.wheatgenetics.coordinate.model.Cell(
            org.wheatgenetics.coordinate.model.CellTest.makeJSONObject(1, 2));   // throws org.json-
    }                                                                            //  .JSONException

    @org.junit.Test(expected = java.lang.NullPointerException.class)
    public void nullInputThirdConstructorFails() throws org.json.JSONException
    { new org.wheatgenetics.coordinate.model.Cell(null); /* throws org.json.JSONException */ }

    @org.junit.Test(expected = org.json.JSONException.class)
    public void emptyInputThirdConstructorFails() throws org.json.JSONException
    {
        new org.wheatgenetics.coordinate.model.Cell(                // throws org.json.JSONException
            new org.json.JSONObject());
    }
    // endregion
    // endregion

    // region Overridden Method Tests
    @org.junit.Test @java.lang.SuppressWarnings("DefaultLocale")
    public void toStringAndHashCodeSucceed()
    {
        final int                                     row  = 1, col = 2;
        final org.wheatgenetics.coordinate.model.Cell cell =
            new org.wheatgenetics.coordinate.model.Cell(row, col);
        final java.lang.String expectedString = java.lang.String.format("Cell(%d, %d)", row, col);
        org.junit.Assert.assertEquals(cell.toString(), expectedString           );
        org.junit.Assert.assertEquals(cell.hashCode(), expectedString.hashCode());
    }

    @org.junit.Test
    public void equalsSucceedsAndFails()
    {
        final int row = 1, col = 2;
        final org.wheatgenetics.coordinate.model.Cell cell =
            new org.wheatgenetics.coordinate.model.Cell(row, col);
        org.junit.Assert.assertTrue(cell.equals(
            new org.wheatgenetics.coordinate.model.Cell(row, col)));
        org.junit.Assert.assertFalse(cell.equals(
            new org.wheatgenetics.coordinate.model.Cell(3, col)));
        org.junit.Assert.assertFalse(cell.equals(
            new org.wheatgenetics.coordinate.model.Cell(row, 4)));
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
    // region inRange() Package Method Tests
    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void bigXInRangeFails()
    {
        new org.wheatgenetics.coordinate.model.Cell(9, 1).inRange(
            new org.wheatgenetics.coordinate.model.Cell(5, 5));
    }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void smallXInRangeFails()
    {
        new org.wheatgenetics.coordinate.model.Cell(0, 1).inRange(
            new org.wheatgenetics.coordinate.model.Cell(5, 5));
    }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void bigYInRangeFails()
    {
        new org.wheatgenetics.coordinate.model.Cell(1, 9).inRange(
            new org.wheatgenetics.coordinate.model.Cell(5, 5));
    }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void smallYInRangeFails()
    {
        new org.wheatgenetics.coordinate.model.Cell(1, -1).inRange(
            new org.wheatgenetics.coordinate.model.Cell(5, 5));
    }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void bigXbigYInRangeFails()
    {
        new org.wheatgenetics.coordinate.model.Cell(9, 9).inRange(
            new org.wheatgenetics.coordinate.model.Cell(5, 5));
    }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void smallXsmallYInRangeFails()
    {
        new org.wheatgenetics.coordinate.model.Cell(-9, 0).inRange(
            new org.wheatgenetics.coordinate.model.Cell(5, 5));
    }

    @org.junit.Test
    public void inRangeSucceeds()
    {
        new org.wheatgenetics.coordinate.model.Cell(1, 1).inRange(
            new org.wheatgenetics.coordinate.model.Cell(5, 5));
    }
    // endregion

    // region makeWithRandomValues() Package Method Tests
    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void zeroMaxRowMakeWithRandomValuesFails()
    { org.wheatgenetics.coordinate.model.Cell.makeWithRandomValues(0, 9); }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void negativeMaxRowMakeWithRandomValuesFails()
    { org.wheatgenetics.coordinate.model.Cell.makeWithRandomValues(-5, 9); }

    @org.junit.Test
    public void oneMaxRowMakeWithRandomValuesSucceeds()
    { org.wheatgenetics.coordinate.model.Cell.makeWithRandomValues(1, 9); }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void zeroMaxColMakeWithRandomValuesFails()
    { org.wheatgenetics.coordinate.model.Cell.makeWithRandomValues(5, 0); }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void negativeMaxColMakeWithRandomValuesFails()
    { org.wheatgenetics.coordinate.model.Cell.makeWithRandomValues(5, -9); }

    @org.junit.Test
    public void oneMaxColMakeWithRandomValuesSucceeds()
    { org.wheatgenetics.coordinate.model.Cell.makeWithRandomValues(5, 1); }
    // endregion

    @org.junit.Test
    public void jsonSucceeds() throws org.json.JSONException
    {
        final int row = 1, col = 2;
        final org.wheatgenetics.coordinate.model.Cell cell =
            new org.wheatgenetics.coordinate.model.Cell(row, col);

        final org.json.JSONObject jsonObject =
            org.wheatgenetics.coordinate.model.CellTest.makeJSONObject(row, col);  // throws org-
                                                                                   //  .json.JSONEx-
        assert null != jsonObject;                                                 //  ception
        org.junit.Assert.assertEquals(cell.json().toString(), jsonObject.toString());
    }
    // endregion
}