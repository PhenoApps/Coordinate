package org.wheatgenetics.coordinate.model;

import android.content.res.Resources;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.PluralsRes;
import androidx.annotation.StringRes;

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
 * org.junit.Assert
 * org.junit.Test
 *
 * org.wheatgenetics.coordinate.R
 * org.wheatgenetics.coordinate.StringGetter
 *
 * org.wheatgenetics.coordinate.model.RowOrCol
 */
@SuppressWarnings({"ClassExplicitlyExtendsObject"}) public class RowOrColTest
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
    throws Resources.NotFoundException
    {
        // noinspection SwitchStatementWithTooFewBranches
        switch (resId) { default: Assert.fail(); return null; }
    }
    // endregion

    // region Constructor Tests
    // region First Constructor Tests
    @Test(expected = IllegalArgumentException.class)
    public void zeroValueFirstConstructorFails()
    { new RowOrCol(0,this); }

    @Test(expected = IllegalArgumentException.class)
    public void negativeValueFirstConstructorFails()
    { new RowOrCol(-9,this); }
    // endregion

    // region Second Constructor Tests
    @Test(expected = IllegalArgumentException.class)
    public void zeroValueSecondConstructorFails()
    {
        new RowOrCol(
            new RowOrCol(
                0,this),this);
    }

    @Test(expected = IllegalArgumentException.class)
    public void negativeValueSecondConstructorFails()
    {
        new RowOrCol(
            new RowOrCol(
                -1,this),this);
    }
    // endregion
    // endregion

    // region Overridden Method Tests
    @Test() public void toStringAndHashCodeSucceed()
    {
        final String                            expectedString;
        final RowOrCol rowOrCol      ;
        {
            final int value = 23;
            expectedString = Integer.toString(value);
            rowOrCol       = new RowOrCol(value,this);
        }
        Assert.assertEquals(expectedString           , rowOrCol.toString());
        Assert.assertEquals(expectedString.hashCode(), rowOrCol.hashCode());
    }

    @Test() public void equalsWorks()
    {
        final RowOrCol rowOrCol;
        {
            final int value = 23;
            rowOrCol = new RowOrCol(value,this);

            // noinspection SimplifiableJUnitAssertion
            Assert.assertTrue(rowOrCol.equals(
                new RowOrCol(value,this)));
        }
        // noinspection SimplifiableJUnitAssertion
        Assert.assertFalse(rowOrCol.equals(
            new RowOrCol(16,this)));
    }

    @Test() public void cloneSucceeds()
    {
        final RowOrCol rowOrCol =
            new RowOrCol(48,this);

        // noinspection SimplifiableJUnitAssertion
        Assert.assertTrue(rowOrCol.equals(rowOrCol.clone()));
    }

    @Test() public void compareToSucceeds()
    {
        final RowOrCol
            rowOrCol =
                new RowOrCol(5,this),
            smallRowOrCol =
                new RowOrCol(1,this),
            mediumRowOrCol =
                new RowOrCol(5,this),
            largeRowOrCol =
                new RowOrCol(9,this);
        Assert.assertTrue  (   rowOrCol.compareTo(smallRowOrCol ) > 0);
        Assert.assertEquals(0, rowOrCol.compareTo(mediumRowOrCol)    );
        Assert.assertTrue  (   rowOrCol.compareTo(largeRowOrCol ) < 0);
    }
    // endregion

    // region Package Method Tests
    @Test() public void getValueWorks()
    {
        final RowOrCol rowOrCol;
        {
            final int value = 23;
            rowOrCol = new RowOrCol(value,this);
            Assert.assertEquals(value, rowOrCol.getValue());
        }
        Assert.assertNotEquals(77, rowOrCol.getValue());
    }

    // region inRange() Package Method Tests
    @Test(expected = NullPointerException.class)
    public void nullMaxRowOrColInRangeFails()
    {
        // noinspection ConstantConditions
        new RowOrCol(5,this).inRange(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void tooSmallMaxRowOrColInRangeFails()
    {
        new RowOrCol(5,this).inRange(
            new RowOrCol(3,this));
    }

    @Test() public void inRangeSucceeds()
    {
        new RowOrCol(5,this).inRange(
            new RowOrCol(30,this));
    }
    // endregion

    // region makeWithRandomValue() Package Method Tests
    @Test(expected = IllegalArgumentException.class)
    public void zeroMaxValueMakeWithRandomValueFails()
    { RowOrCol.makeWithRandomValue(0,this); }

    @Test(expected = IllegalArgumentException.class)
    public void negativeMaxValueMakeWithRandomValueFails()
    {
        RowOrCol.makeWithRandomValue(
            -9,this);
    }

    @Test() public void oneMaxValueMakeWithRandomValueSucceeds()
    {
        RowOrCol.makeWithRandomValue(1,this);
    }
    // endregion
    // endregion
}