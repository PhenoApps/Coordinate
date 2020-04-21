package org.wheatgenetics.coordinate.model;

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
 * org.json.JSONException
 * org.json.JSONObject
 *
 * org.junit.Assert
 * org.junit.Test
 *
 * org.wheatgenetics.coordinate.R
 * org.wheatgenetics.coordinate.StringGetter
 *
 * org.wheatgenetics.coordinate.model.Cell
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"}) public class CellTest
extends java.lang.Object implements org.wheatgenetics.coordinate.StringGetter
{
    // region org.wheatgenetics.coordinate.StringGetter Overridden Methods
    @java.lang.Override @androidx.annotation.Nullable public java.lang.String get(
    @androidx.annotation.StringRes final int resId)
    {
        // noinspection SwitchStatementWithTooFewBranches
        switch (resId)
        {
            case org.wheatgenetics.coordinate.R.string.UtilsInvalidValue:
                return "value must be >= %d";
            default: org.junit.Assert.fail(); return null;
        }
    }

    @java.lang.Override @androidx.annotation.NonNull public java.lang.String getQuantity(
    @androidx.annotation.PluralsRes         final int                 resId     ,
    @androidx.annotation.IntRange(from = 0) final int                 quantity  ,
    @androidx.annotation.Nullable           final java.lang.Object... formatArgs)
    throws android.content.res.Resources.NotFoundException { org.junit.Assert.fail(); return null; }
    // endregion

    @androidx.annotation.NonNull static org.json.JSONObject makeJSONObject(
    final int row, final int col) throws org.json.JSONException
    {
        final org.json.JSONObject result = new org.json.JSONObject();

        result.put("row", row);                               // throws org.json.JSONException
        result.put("col", col);                               // throws org.json.JSONException

        return result;
    }

    // region Constructor Tests
    // region Second Constructor Tests
    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void zeroRowSecondConstructorFails()
    {
        new org.wheatgenetics.coordinate.model.Cell(
            new org.wheatgenetics.coordinate.model.Cell(
                0,5,this),this);
    }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void negativeRowSecondConstructorFails()
    {
        new org.wheatgenetics.coordinate.model.Cell(
            new org.wheatgenetics.coordinate.model.Cell(
                -10,5,this),this);
    }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void zeroColSecondConstructorFails()
    {
        new org.wheatgenetics.coordinate.model.Cell(
            new org.wheatgenetics.coordinate.model.Cell(
                8,0,this),this);
    }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void negativeColSecondConstructorFails()
    {
        new org.wheatgenetics.coordinate.model.Cell(
            new org.wheatgenetics.coordinate.model.Cell(
                8,-5,this),this);
    }
    // endregion

    // region Third Constructor Tests
    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void zeroRowThirdConstructorFails()
    { new org.wheatgenetics.coordinate.model.Cell(0,5,this); }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void negativeRowThirdConstructorFails()
    { new org.wheatgenetics.coordinate.model.Cell(-10,5,this); }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void zeroColThirdConstructorFails()
    { new org.wheatgenetics.coordinate.model.Cell(8,0,this); }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void negativeColThirdConstructorFails()
    { new org.wheatgenetics.coordinate.model.Cell(8,-5,this); }
    // endregion

    // region Fourth Constructor Tests
    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void zeroRowFourthConstructorFails() throws org.json.JSONException
    {
        new org.wheatgenetics.coordinate.model.Cell(
            org.wheatgenetics.coordinate.model.CellTest.makeJSONObject(          // throws org.json-
                0,2),this);                                  //  .JSONException
    }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void negativeRowFourthConstructorFails() throws org.json.JSONException
    {
        new org.wheatgenetics.coordinate.model.Cell(
            org.wheatgenetics.coordinate.model.CellTest.makeJSONObject(          // throws org.json-
                -1,2),this);                                 //  .JSONException
    }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void zeroColFourthConstructorFails() throws org.json.JSONException
    {
        new org.wheatgenetics.coordinate.model.Cell(
            org.wheatgenetics.coordinate.model.CellTest.makeJSONObject(          // throws org.json-
                1,0),this);                                  //  .JSONException
    }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void negativeColFourthConstructorFails() throws org.json.JSONException
    {
        new org.wheatgenetics.coordinate.model.Cell(
            org.wheatgenetics.coordinate.model.CellTest.makeJSONObject(          // throws org.json-
                1,-2),this);                                 //  .JSONException
    }

    @org.junit.Test() public void fourthConstructorSucceeds() throws org.json.JSONException
    {
        new org.wheatgenetics.coordinate.model.Cell(
            org.wheatgenetics.coordinate.model.CellTest.makeJSONObject(          // throws org.json-
                1,2),this);                                  //  .JSONException
    }

    @org.junit.Test(expected = java.lang.NullPointerException.class)
    public void nullInputFourthConstructorFails() throws org.json.JSONException
    {
        // noinspection ConstantConditions
        new org.wheatgenetics.coordinate.model.Cell(                // throws org.json.JSONException
            (org.json.JSONObject) null,this);
    }

    @org.junit.Test(expected = org.json.JSONException.class)
    public void emptyInputFourthConstructorFails() throws org.json.JSONException
    {
        new org.wheatgenetics.coordinate.model.Cell(                // throws org.json.JSONException
            new org.json.JSONObject(),this);
    }
    // endregion
    // endregion

    // region Overridden Method Tests
    @org.junit.Test() public void toStringAndHashCodeSucceed()
    {
        final java.lang.String                        expectedString;
        final org.wheatgenetics.coordinate.model.Cell cell          ;
        {
            final int row = 1, col = 2;
            expectedString = java.lang.String.format("Cell(%d, %d)", row, col);
            cell           = new org.wheatgenetics.coordinate.model.Cell(row, col,this);
        }
        org.junit.Assert.assertEquals(expectedString           , cell.toString());
        org.junit.Assert.assertEquals(expectedString.hashCode(), cell.hashCode());
    }

    @org.junit.Test() public void equalsWorks()
    {
        final int                                     row  = 1, col = 2;
        final org.wheatgenetics.coordinate.model.Cell cell =
            new org.wheatgenetics.coordinate.model.Cell(row, col,this);

        // noinspection SimplifiableJUnitAssertion
        org.junit.Assert.assertTrue(cell.equals(
            new org.wheatgenetics.coordinate.model.Cell(row, col,this)));

        // noinspection SimplifiableJUnitAssertion
        org.junit.Assert.assertFalse(cell.equals(
            new org.wheatgenetics.coordinate.model.Cell(3, col,this)));

        // noinspection SimplifiableJUnitAssertion
        org.junit.Assert.assertFalse(cell.equals(
            new org.wheatgenetics.coordinate.model.Cell(row,4,this)));
    }

    @org.junit.Test() public void cloneSucceeds()
    {
        final org.wheatgenetics.coordinate.model.Cell cell =
            new org.wheatgenetics.coordinate.model.Cell(23,999,this);

        // noinspection SimplifiableJUnitAssertion
        org.junit.Assert.assertTrue(cell.equals(cell.clone()));
    }

    @org.junit.Test() public void compareToSucceeds()
    {
        final org.wheatgenetics.coordinate.model.Cell cell =
            new org.wheatgenetics.coordinate.model.Cell(5,5,this);

        // Same col:
        org.junit.Assert.assertEquals(0, cell.compareTo(
            new org.wheatgenetics.coordinate.model.Cell(5,5,this)));
        org.junit.Assert.assertTrue(cell.compareTo(
            new org.wheatgenetics.coordinate.model.Cell(1,5,this)) > 0);
        org.junit.Assert.assertTrue(cell.compareTo(
            new org.wheatgenetics.coordinate.model.Cell(9,5,this)) < 0);

        // Same row:
        org.junit.Assert.assertEquals(0, cell.compareTo(
            new org.wheatgenetics.coordinate.model.Cell(5,5,this)));
        org.junit.Assert.assertTrue(cell.compareTo(
            new org.wheatgenetics.coordinate.model.Cell(5,1,this)) > 0);
        org.junit.Assert.assertTrue(cell.compareTo(
            new org.wheatgenetics.coordinate.model.Cell(5,9,this)) < 0);

        // Different row and different col:
        org.junit.Assert.assertTrue(cell.compareTo(
            new org.wheatgenetics.coordinate.model.Cell(1,1,this)) > 0);
        org.junit.Assert.assertTrue(cell.compareTo(
            new org.wheatgenetics.coordinate.model.Cell(9,9,this)) < 0);
    }

    @org.junit.Test() public void getRowValueWorks()
    {
        final org.wheatgenetics.coordinate.model.Cell cell =
            new org.wheatgenetics.coordinate.model.Cell(5,5,this);
        org.junit.Assert.assertEquals   (5, cell.getRowValue());
        org.junit.Assert.assertNotEquals(6, cell.getRowValue());
    }

    @org.junit.Test() public void getColValueWorks()
    {
        final org.wheatgenetics.coordinate.model.Cell cell =
            new org.wheatgenetics.coordinate.model.Cell(5,5,this);
        org.junit.Assert.assertEquals   (5, cell.getColValue());
        org.junit.Assert.assertNotEquals(6, cell.getColValue());
    }
    // endregion

    // region Package Method Tests
    @org.junit.Test() public void getRowAndGetColWork()
    {
        final org.wheatgenetics.coordinate.model.Cell cell =
            new org.wheatgenetics.coordinate.model.Cell(4,5,this);
        org.junit.Assert.assertNotEquals(1, cell.getRow().getValue());
        org.junit.Assert.assertNotEquals(1, cell.getCol().getValue());

        org.junit.Assert.assertEquals(4, cell.getRow().getValue());
        org.junit.Assert.assertEquals(5, cell.getCol().getValue());
    }

    // region inRange() Package Method Tests
    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void bigRowInRangeFails()
    {
        new org.wheatgenetics.coordinate.model.Cell(9,1,this).inRange(
            new org.wheatgenetics.coordinate.model.Cell(5,5,this));
    }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void smallRowInRangeFails()
    {
        new org.wheatgenetics.coordinate.model.Cell(0,1,this).inRange(
            new org.wheatgenetics.coordinate.model.Cell(5,5,this));
    }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void bigColInRangeFails()
    {
        new org.wheatgenetics.coordinate.model.Cell(1,9,this).inRange(
            new org.wheatgenetics.coordinate.model.Cell(5,5,this));
    }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void smallColInRangeFails()
    {
        new org.wheatgenetics.coordinate.model.Cell(1,-1,this).inRange(
            new org.wheatgenetics.coordinate.model.Cell(5,5,this));
    }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void bigRowBigColInRangeFails()
    {
        new org.wheatgenetics.coordinate.model.Cell(9,9,this).inRange(
            new org.wheatgenetics.coordinate.model.Cell(5,5,this));
    }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void smallRowSmallColInRangeFails()
    {
        new org.wheatgenetics.coordinate.model.Cell(-9,0,this).inRange(
            new org.wheatgenetics.coordinate.model.Cell(5,5,this));
    }

    @org.junit.Test() public void inRangeSucceeds()
    {
        new org.wheatgenetics.coordinate.model.Cell(1,1,this).inRange(
            new org.wheatgenetics.coordinate.model.Cell(5,5,this));
    }
    // endregion

    @org.junit.Test() public void jsonSucceeds() throws org.json.JSONException
    {
        final org.json.JSONObject                     jsonObject;
        final org.wheatgenetics.coordinate.model.Cell cell      ;
        {
            final int row = 1, col = 2;
            jsonObject = org.wheatgenetics.coordinate.model.CellTest.makeJSONObject( // throws org-
                row, col);                                                           //  .json.JSON-
            cell = new org.wheatgenetics.coordinate.model.Cell(                      //  Exception
                row, col,this);
        }
        org.junit.Assert.assertEquals(jsonObject.toString(), cell.json().toString());
    }

    // region makeWithRandomValues() Package Method Tests
    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void zeroMaxRowMakeWithRandomValuesFails()
    {
        org.wheatgenetics.coordinate.model.Cell.makeWithRandomValues(
            0,9,this);
    }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void negativeMaxRowMakeWithRandomValuesFails()
    {
        org.wheatgenetics.coordinate.model.Cell.makeWithRandomValues(
            -5,9,this);
    }

    @org.junit.Test() public void oneMaxRowMakeWithRandomValuesSucceeds()
    {
        org.wheatgenetics.coordinate.model.Cell.makeWithRandomValues(
            1,9,this);
    }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void zeroMaxColMakeWithRandomValuesFails()
    {
        org.wheatgenetics.coordinate.model.Cell.makeWithRandomValues(
            5,0,this);
    }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void negativeMaxColMakeWithRandomValuesFails()
    {
        org.wheatgenetics.coordinate.model.Cell.makeWithRandomValues(
            5,-9,this);
    }

    @org.junit.Test() public void oneMaxColMakeWithRandomValuesSucceeds()
    {
        org.wheatgenetics.coordinate.model.Cell.makeWithRandomValues(
            5,1,this);
    }
    // endregion
    // endregion
}