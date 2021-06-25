package org.wheatgenetics.coordinate.model;

import android.content.res.Resources;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.PluralsRes;
import androidx.annotation.StringRes;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.wheatgenetics.coordinate.R;
import org.wheatgenetics.coordinate.StringGetter;

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
@SuppressWarnings({"ClassExplicitlyExtendsObject"}) public class CellTest
extends Object implements StringGetter
{
    // region org.wheatgenetics.coordinate.StringGetter Overridden Methods
    @Override @Nullable public String get(
    @StringRes final int resId)
    {
        // noinspection SwitchStatementWithTooFewBranches
        switch (resId)
        {
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

    @NonNull static JSONObject makeJSONObject(
    final int row, final int col) throws JSONException
    {
        final JSONObject result = new JSONObject();

        result.put("row", row);                               // throws org.json.JSONException
        result.put("col", col);                               // throws org.json.JSONException

        return result;
    }

    // region Constructor Tests
    // region Second Constructor Tests
    @Test(expected = IllegalArgumentException.class)
    public void zeroRowSecondConstructorFails()
    {
        new Cell(
            new Cell(
                0,5,this),this);
    }

    @Test(expected = IllegalArgumentException.class)
    public void negativeRowSecondConstructorFails()
    {
        new Cell(
            new Cell(
                -10,5,this),this);
    }

    @Test(expected = IllegalArgumentException.class)
    public void zeroColSecondConstructorFails()
    {
        new Cell(
            new Cell(
                8,0,this),this);
    }

    @Test(expected = IllegalArgumentException.class)
    public void negativeColSecondConstructorFails()
    {
        new Cell(
            new Cell(
                8,-5,this),this);
    }
    // endregion

    // region Third Constructor Tests
    @Test(expected = IllegalArgumentException.class)
    public void zeroRowThirdConstructorFails()
    { new Cell(0,5,this); }

    @Test(expected = IllegalArgumentException.class)
    public void negativeRowThirdConstructorFails()
    { new Cell(-10,5,this); }

    @Test(expected = IllegalArgumentException.class)
    public void zeroColThirdConstructorFails()
    { new Cell(8,0,this); }

    @Test(expected = IllegalArgumentException.class)
    public void negativeColThirdConstructorFails()
    { new Cell(8,-5,this); }
    // endregion

    // region Fourth Constructor Tests
    @Test(expected = IllegalArgumentException.class)
    public void zeroRowFourthConstructorFails() throws JSONException
    {
        new Cell(
            CellTest.makeJSONObject(          // throws org.json-
                0,2),this);                                  //  .JSONException
    }

    @Test(expected = IllegalArgumentException.class)
    public void negativeRowFourthConstructorFails() throws JSONException
    {
        new Cell(
            CellTest.makeJSONObject(          // throws org.json-
                -1,2),this);                                 //  .JSONException
    }

    @Test(expected = IllegalArgumentException.class)
    public void zeroColFourthConstructorFails() throws JSONException
    {
        new Cell(
            CellTest.makeJSONObject(          // throws org.json-
                1,0),this);                                  //  .JSONException
    }

    @Test(expected = IllegalArgumentException.class)
    public void negativeColFourthConstructorFails() throws JSONException
    {
        new Cell(
            CellTest.makeJSONObject(          // throws org.json-
                1,-2),this);                                 //  .JSONException
    }

    @Test() public void fourthConstructorSucceeds() throws JSONException
    {
        new Cell(
            CellTest.makeJSONObject(          // throws org.json-
                1,2),this);                                  //  .JSONException
    }

    @Test(expected = NullPointerException.class)
    public void nullInputFourthConstructorFails() throws JSONException
    {
        // noinspection ConstantConditions
        new Cell(                // throws org.json.JSONException
            (JSONObject) null,this);
    }

    @Test(expected = JSONException.class)
    public void emptyInputFourthConstructorFails() throws JSONException
    {
        new Cell(                // throws org.json.JSONException
            new JSONObject(),this);
    }
    // endregion
    // endregion

    // region Overridden Method Tests
    @Test() public void toStringAndHashCodeSucceed()
    {
        final String                        expectedString;
        final Cell cell          ;
        {
            final int row = 1, col = 2;
            expectedString = String.format("Cell(%d, %d)", row, col);
            cell           = new Cell(row, col,this);
        }
        Assert.assertEquals(expectedString           , cell.toString());
        Assert.assertEquals(expectedString.hashCode(), cell.hashCode());
    }

    @Test() public void equalsWorks()
    {
        final int                                     row  = 1, col = 2;
        final Cell cell =
            new Cell(row, col,this);

        // noinspection SimplifiableJUnitAssertion
        Assert.assertTrue(cell.equals(
            new Cell(row, col,this)));

        // noinspection SimplifiableJUnitAssertion
        Assert.assertFalse(cell.equals(
            new Cell(3, col,this)));

        // noinspection SimplifiableJUnitAssertion
        Assert.assertFalse(cell.equals(
            new Cell(row,4,this)));
    }

    @Test() public void cloneSucceeds()
    {
        final Cell cell =
            new Cell(23,999,this);

        // noinspection SimplifiableJUnitAssertion
        Assert.assertTrue(cell.equals(cell.clone()));
    }

    @Test() public void compareToSucceeds()
    {
        final Cell cell =
            new Cell(5,5,this);

        // Same col:
        Assert.assertEquals(0, cell.compareTo(
            new Cell(5,5,this)));
        Assert.assertTrue(cell.compareTo(
            new Cell(1,5,this)) > 0);
        Assert.assertTrue(cell.compareTo(
            new Cell(9,5,this)) < 0);

        // Same row:
        Assert.assertEquals(0, cell.compareTo(
            new Cell(5,5,this)));
        Assert.assertTrue(cell.compareTo(
            new Cell(5,1,this)) > 0);
        Assert.assertTrue(cell.compareTo(
            new Cell(5,9,this)) < 0);

        // Different row and different col:
        Assert.assertTrue(cell.compareTo(
            new Cell(1,1,this)) > 0);
        Assert.assertTrue(cell.compareTo(
            new Cell(9,9,this)) < 0);
    }

    @Test() public void getRowValueWorks()
    {
        final Cell cell =
            new Cell(5,5,this);
        Assert.assertEquals   (5, cell.getRowValue());
        Assert.assertNotEquals(6, cell.getRowValue());
    }

    @Test() public void getColValueWorks()
    {
        final Cell cell =
            new Cell(5,5,this);
        Assert.assertEquals   (5, cell.getColValue());
        Assert.assertNotEquals(6, cell.getColValue());
    }
    // endregion

    // region Package Method Tests
    @Test() public void getRowAndGetColWork()
    {
        final Cell cell =
            new Cell(4,5,this);
        Assert.assertNotEquals(1, cell.getRow().getValue());
        Assert.assertNotEquals(1, cell.getCol().getValue());

        Assert.assertEquals(4, cell.getRow().getValue());
        Assert.assertEquals(5, cell.getCol().getValue());
    }

    // region inRange() Package Method Tests
    @Test(expected = IllegalArgumentException.class)
    public void bigRowInRangeFails()
    {
        new Cell(9,1,this).inRange(
            new Cell(5,5,this));
    }

    @Test(expected = IllegalArgumentException.class)
    public void smallRowInRangeFails()
    {
        new Cell(0,1,this).inRange(
            new Cell(5,5,this));
    }

    @Test(expected = IllegalArgumentException.class)
    public void bigColInRangeFails()
    {
        new Cell(1,9,this).inRange(
            new Cell(5,5,this));
    }

    @Test(expected = IllegalArgumentException.class)
    public void smallColInRangeFails()
    {
        new Cell(1,-1,this).inRange(
            new Cell(5,5,this));
    }

    @Test(expected = IllegalArgumentException.class)
    public void bigRowBigColInRangeFails()
    {
        new Cell(9,9,this).inRange(
            new Cell(5,5,this));
    }

    @Test(expected = IllegalArgumentException.class)
    public void smallRowSmallColInRangeFails()
    {
        new Cell(-9,0,this).inRange(
            new Cell(5,5,this));
    }

    @Test() public void inRangeSucceeds()
    {
        new Cell(1,1,this).inRange(
            new Cell(5,5,this));
    }
    // endregion

    @Test() public void jsonSucceeds() throws JSONException
    {
        final JSONObject                     jsonObject;
        final Cell cell      ;
        {
            final int row = 1, col = 2;
            jsonObject = CellTest.makeJSONObject( // throws org-
                row, col);                                                           //  .json.JSON-
            cell = new Cell(                      //  Exception
                row, col,this);
        }
        Assert.assertEquals(jsonObject.toString(), cell.json().toString());
    }

    // region makeWithRandomValues() Package Method Tests
    @Test(expected = IllegalArgumentException.class)
    public void zeroMaxRowMakeWithRandomValuesFails()
    {
        Cell.makeWithRandomValues(
            0,9,this);
    }

    @Test(expected = IllegalArgumentException.class)
    public void negativeMaxRowMakeWithRandomValuesFails()
    {
        Cell.makeWithRandomValues(
            -5,9,this);
    }

    @Test() public void oneMaxRowMakeWithRandomValuesSucceeds()
    {
        Cell.makeWithRandomValues(
            1,9,this);
    }

    @Test(expected = IllegalArgumentException.class)
    public void zeroMaxColMakeWithRandomValuesFails()
    {
        Cell.makeWithRandomValues(
            5,0,this);
    }

    @Test(expected = IllegalArgumentException.class)
    public void negativeMaxColMakeWithRandomValuesFails()
    {
        Cell.makeWithRandomValues(
            5,-9,this);
    }

    @Test() public void oneMaxColMakeWithRandomValuesSucceeds()
    {
        Cell.makeWithRandomValues(
            5,1,this);
    }
    // endregion
    // endregion
}