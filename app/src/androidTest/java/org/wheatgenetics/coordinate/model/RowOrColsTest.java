package org.wheatgenetics.coordinate.model;

import android.content.res.Resources;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.PluralsRes;
import androidx.annotation.StringRes;

import org.json.JSONArray;
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
 * org.json.JSONArray
 *
 * org.junit.Assert
 * org.junit.Test
 *
 * org.wheatgenetics.coordinate.R.string
 * org.wheatgenetics.coordinate.StringGetter
 *
 * org.wheatgenetics.coordinate.model.RowOrCols
 */
@SuppressWarnings({"ClassExplicitlyExtendsObject"}) public class RowOrColsTest
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

    // region Constructor Tests
    // region Second Constructor Tests
    @Test(expected = IllegalArgumentException.class)
    public void zeroMaxValueSecondConstructorFails()
    { new RowOrCols(0,this); }

    @Test(expected = IllegalArgumentException.class)
    public void negativeMaxValueSecondConstructorFails()
    { new RowOrCols(-80,this); }

    @Test() public void secondConstructorSucceeds()
    { new RowOrCols(10,this); }
    // endregion

    // region Third Constructor Tests
    @Test() public void thirdConstructorAndJSONSucceed()
    {
        final JSONArray jsonArray = new JSONArray();
        {
            final int firstValue = 27, secondValue = 123;
            jsonArray.put(firstValue); jsonArray.put(secondValue);
        }
        final RowOrCols rowOrCols =
            new RowOrCols(
                jsonArray.toString(),125,this);
        Assert.assertEquals(jsonArray.toString(), rowOrCols.json());
    }

    @Test(expected = IllegalArgumentException.class)
    public void zeroInputThirdConstructorFails()
    {
        final JSONArray jsonArray = new JSONArray();
        jsonArray.put(0);
        new RowOrCols(
            jsonArray.toString(),125,this);
    }

    @Test(expected = IllegalArgumentException.class)
    public void negativeInputThirdConstructorFails()
    {
        final JSONArray jsonArray = new JSONArray();
        jsonArray.put(-123);
        new RowOrCols(
            jsonArray.toString(),125,this);
    }

    @Test() public void nullInputThirdConstructorSucceeds()
    {
        final RowOrCols
            firstRowOrCols = new RowOrCols(
                null,125,this),
            secondRowOrCols = new RowOrCols(
                125,this);

        // noinspection SimplifiableJUnitAssertion
        Assert.assertTrue(firstRowOrCols.equals(secondRowOrCols));
    }

    @Test() public void emptyInputThirdConstructorSucceeds()
    {
        final RowOrCols
            firstRowOrCols = new RowOrCols(
                "",125,this),
            secondRowOrCols = new RowOrCols(
                125,this);

        // noinspection SimplifiableJUnitAssertion
        Assert.assertTrue(firstRowOrCols.equals(secondRowOrCols));
    }

    @Test() public void spacesInputThirdConstructorSucceeds()
    {
        final RowOrCols
            firstRowOrCols = new RowOrCols(
                "  ",125,this),
            secondRowOrCols = new RowOrCols(
                125,this);

        // noinspection SimplifiableJUnitAssertion
        Assert.assertTrue(firstRowOrCols.equals(secondRowOrCols));
    }
    // endregion
    // endregion

    // region Overridden Method Tests
    @Test() public void toStringSucceeds()
    {
        final RowOrCols rowOrCols =
            new RowOrCols(50,this);
        Assert.assertEquals("null", rowOrCols.toString());

        rowOrCols.add(34); Assert.assertEquals("34", rowOrCols.toString());

        rowOrCols.add(11);
        Assert.assertEquals("11, 34", rowOrCols.toString() /* sorts! */);
    }

    @Test() public void equalsWorks()
    {
        final RowOrCols
            firstRowOrCols =
            new RowOrCols(150,this),
            secondRowOrCols =
                new RowOrCols(125,this);

        // noinspection SimplifiableJUnitAssertion
        Assert.assertTrue(firstRowOrCols.equals(secondRowOrCols));

        // noinspection SimplifiableJUnitAssertion, EqualsBetweenInconvertibleTypes
        Assert.assertFalse(firstRowOrCols.equals("nonsense"));

        {
            final int value = 123;
            firstRowOrCols.add(value);

            // noinspection SimplifiableJUnitAssertion
            Assert.assertFalse(firstRowOrCols.equals(secondRowOrCols));

            secondRowOrCols.add(value);
        }
        // noinspection SimplifiableJUnitAssertion
        Assert.assertTrue(firstRowOrCols.equals(secondRowOrCols));
    }

    @Test() public void hashCodeSucceeds()
    {
        final RowOrCols
            firstRowOrCols =
            new RowOrCols(125,this),
            secondRowOrCols =
                new RowOrCols(125,this);
        Assert.assertEquals   (firstRowOrCols.hashCode(), secondRowOrCols.hashCode());
        Assert.assertNotEquals(123, secondRowOrCols.hashCode());

        {
            final int value = 123;
            firstRowOrCols.add(value);
            Assert.assertNotEquals(firstRowOrCols.hashCode(), secondRowOrCols.hashCode());
            secondRowOrCols.add(value);
        }
        Assert.assertEquals(firstRowOrCols.hashCode(), secondRowOrCols.hashCode());
    }

    @Test() public void cloneSucceeds()
    {
        final RowOrCols rowOrCols =
            new RowOrCols(5,this);

        // noinspection SimplifiableJUnitAssertion
        Assert.assertTrue(rowOrCols.equals(rowOrCols.clone()));

        rowOrCols.add(2);

        // noinspection SimplifiableJUnitAssertion
        Assert.assertTrue(rowOrCols.equals(rowOrCols.clone()));
    }
    // endregion

    // region Package Method Tests
    // region add() Package Method Tests
    @Test() public void addAndContainsWork()
    {
        final RowOrCols rowOrCols =
            new RowOrCols(125,this);
        {
            final int value = 123;
            rowOrCols.add(value); Assert.assertTrue(rowOrCols.contains(value));
        }
        Assert.assertFalse(rowOrCols.contains(111));
        Assert.assertFalse(rowOrCols.contains(456));
    }

    @Test() public void duplicateAddDoesNotAdd()
    {
        final RowOrCols rowOrCols =
            new RowOrCols(125,this);
        {
            final int value = 123;
            rowOrCols.add(value);
            rowOrCols.add(value);         // Does not add() but does not throw an exception, either.
        }
        Assert.assertEquals("123", rowOrCols.toString());  // Note: value add()ed
    }                                                                         //  only once.

    @Test(expected = IllegalArgumentException.class) public void zeroAddFails()
    { new RowOrCols(12,this).add(0); }

    @Test(expected = IllegalArgumentException.class)
    public void negativeAddFails()
    { new RowOrCols(12,this).add(-20); }
    // endregion

    @Test() public void clearSucceeds()
    {
        final RowOrCols rowOrCols =
            new RowOrCols(125,this);
        final int value = 123;
        rowOrCols.add(value); Assert.assertTrue (rowOrCols.contains(value));
        rowOrCols.clear()   ; Assert.assertFalse(rowOrCols.contains(value));
    }
    // endregion
}