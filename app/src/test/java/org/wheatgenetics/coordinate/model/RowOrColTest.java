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
 * org.junit.Assert
 * org.junit.Test
 *
 * org.wheatgenetics.coordinate.R
 * org.wheatgenetics.coordinate.StringGetter
 *
 * org.wheatgenetics.coordinate.model.RowOrCol
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"}) public class RowOrColTest
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
    throws android.content.res.Resources.NotFoundException
    {
        // noinspection SwitchStatementWithTooFewBranches
        switch (resId) { default: org.junit.Assert.fail(); return null; }
    }
    // endregion

    // region Constructor Tests
    // region First Constructor Tests
    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void zeroValueFirstConstructorFails()
    { new org.wheatgenetics.coordinate.model.RowOrCol(0,this); }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void negativeValueFirstConstructorFails()
    { new org.wheatgenetics.coordinate.model.RowOrCol(-9,this); }
    // endregion

    // region Second Constructor Tests
    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void zeroValueSecondConstructorFails()
    {
        new org.wheatgenetics.coordinate.model.RowOrCol(
            new org.wheatgenetics.coordinate.model.RowOrCol(
                0,this),this);
    }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void negativeValueSecondConstructorFails()
    {
        new org.wheatgenetics.coordinate.model.RowOrCol(
            new org.wheatgenetics.coordinate.model.RowOrCol(
                -1,this),this);
    }
    // endregion
    // endregion

    // region Overridden Method Tests
    @org.junit.Test() public void toStringAndHashCodeSucceed()
    {
        final java.lang.String                            expectedString;
        final org.wheatgenetics.coordinate.model.RowOrCol rowOrCol      ;
        {
            final int value = 23;
            expectedString = java.lang.Integer.toString(value);
            rowOrCol       = new org.wheatgenetics.coordinate.model.RowOrCol(value,this);
        }
        org.junit.Assert.assertEquals(expectedString           , rowOrCol.toString());
        org.junit.Assert.assertEquals(expectedString.hashCode(), rowOrCol.hashCode());
    }

    @org.junit.Test() public void equalsWorks()
    {
        final org.wheatgenetics.coordinate.model.RowOrCol rowOrCol;
        {
            final int value = 23;
            rowOrCol = new org.wheatgenetics.coordinate.model.RowOrCol(value,this);

            // noinspection SimplifiableJUnitAssertion
            org.junit.Assert.assertTrue(rowOrCol.equals(
                new org.wheatgenetics.coordinate.model.RowOrCol(value,this)));
        }
        // noinspection SimplifiableJUnitAssertion
        org.junit.Assert.assertFalse(rowOrCol.equals(
            new org.wheatgenetics.coordinate.model.RowOrCol(16,this)));
    }

    @org.junit.Test() public void cloneSucceeds()
    {
        final org.wheatgenetics.coordinate.model.RowOrCol rowOrCol =
            new org.wheatgenetics.coordinate.model.RowOrCol(48,this);

        // noinspection SimplifiableJUnitAssertion
        org.junit.Assert.assertTrue(rowOrCol.equals(rowOrCol.clone()));
    }

    @org.junit.Test() public void compareToSucceeds()
    {
        final org.wheatgenetics.coordinate.model.RowOrCol
            rowOrCol =
                new org.wheatgenetics.coordinate.model.RowOrCol(5,this),
            smallRowOrCol =
                new org.wheatgenetics.coordinate.model.RowOrCol(1,this),
            mediumRowOrCol =
                new org.wheatgenetics.coordinate.model.RowOrCol(5,this),
            largeRowOrCol =
                new org.wheatgenetics.coordinate.model.RowOrCol(9,this);
        org.junit.Assert.assertTrue  (   rowOrCol.compareTo(smallRowOrCol ) > 0);
        org.junit.Assert.assertEquals(0, rowOrCol.compareTo(mediumRowOrCol)    );
        org.junit.Assert.assertTrue  (   rowOrCol.compareTo(largeRowOrCol ) < 0);
    }
    // endregion

    // region Package Method Tests
    @org.junit.Test() public void getValueWorks()
    {
        final org.wheatgenetics.coordinate.model.RowOrCol rowOrCol;
        {
            final int value = 23;
            rowOrCol = new org.wheatgenetics.coordinate.model.RowOrCol(value,this);
            org.junit.Assert.assertEquals(value, rowOrCol.getValue());
        }
        org.junit.Assert.assertNotEquals(77, rowOrCol.getValue());
    }

    // region inRange() Package Method Tests
    @org.junit.Test(expected = java.lang.NullPointerException.class)
    public void nullMaxRowOrColInRangeFails()
    {
        // noinspection ConstantConditions
        new org.wheatgenetics.coordinate.model.RowOrCol(5,this).inRange(null);
    }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void tooSmallMaxRowOrColInRangeFails()
    {
        new org.wheatgenetics.coordinate.model.RowOrCol(5,this).inRange(
            new org.wheatgenetics.coordinate.model.RowOrCol(3,this));
    }

    @org.junit.Test() public void inRangeSucceeds()
    {
        new org.wheatgenetics.coordinate.model.RowOrCol(5,this).inRange(
            new org.wheatgenetics.coordinate.model.RowOrCol(30,this));
    }
    // endregion

    // region makeWithRandomValue() Package Method Tests
    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void zeroMaxValueMakeWithRandomValueFails()
    { org.wheatgenetics.coordinate.model.RowOrCol.makeWithRandomValue(0,this); }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void negativeMaxValueMakeWithRandomValueFails()
    {
        org.wheatgenetics.coordinate.model.RowOrCol.makeWithRandomValue(
            -9,this);
    }

    @org.junit.Test() public void oneMaxValueMakeWithRandomValueSucceeds()
    {
        org.wheatgenetics.coordinate.model.RowOrCol.makeWithRandomValue(1,this);
    }
    // endregion
    // endregion
}