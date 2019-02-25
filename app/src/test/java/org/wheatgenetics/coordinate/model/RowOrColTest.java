package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * org.junit.Assert
 * org.junit.Test
 *
 * org.wheatgenetics.coordinate.model.RowOrCol
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
public class RowOrColTest extends java.lang.Object
{
    // region Constructor Tests
    // region First Constructor Tests
    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void zeroValueFirstConstructorFails()
    { new org.wheatgenetics.coordinate.model.RowOrCol(0); }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void negativeValueFirstConstructorFails()
    { new org.wheatgenetics.coordinate.model.RowOrCol(-9); }
    // endregion

    // region Second Constructor Tests
    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void zeroValueSecondConstructorFails()
    {
        new org.wheatgenetics.coordinate.model.RowOrCol(
            new org.wheatgenetics.coordinate.model.RowOrCol(0));
    }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void negativeValueSecondConstructorFails()
    {
        new org.wheatgenetics.coordinate.model.RowOrCol(
            new org.wheatgenetics.coordinate.model.RowOrCol(-1));
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
            expectedString = java.lang.Integer.toString(value)                     ;
            rowOrCol       = new org.wheatgenetics.coordinate.model.RowOrCol(value);
        }
        org.junit.Assert.assertEquals(expectedString           , rowOrCol.toString());
        org.junit.Assert.assertEquals(expectedString.hashCode(), rowOrCol.hashCode());
    }

    @org.junit.Test() public void equalsWorks()
    {
        final org.wheatgenetics.coordinate.model.RowOrCol rowOrCol;
        {
            final int value = 23;
            rowOrCol = new org.wheatgenetics.coordinate.model.RowOrCol(value);

            // noinspection SimplifiableJUnitAssertion
            org.junit.Assert.assertTrue(rowOrCol.equals(
                new org.wheatgenetics.coordinate.model.RowOrCol(value)));
        }
        // noinspection SimplifiableJUnitAssertion
        org.junit.Assert.assertFalse(rowOrCol.equals(
            new org.wheatgenetics.coordinate.model.RowOrCol(16)));
    }

    @org.junit.Test() public void cloneSucceeds()
    {
        final org.wheatgenetics.coordinate.model.RowOrCol rowOrCol =
            new org.wheatgenetics.coordinate.model.RowOrCol(48);

        // noinspection SimplifiableJUnitAssertion
        org.junit.Assert.assertTrue(rowOrCol.equals(rowOrCol.clone()));
    }

    @org.junit.Test() public void compareToSucceeds()
    {
        final org.wheatgenetics.coordinate.model.RowOrCol
            rowOrCol       = new org.wheatgenetics.coordinate.model.RowOrCol(5),
            smallRowOrCol  = new org.wheatgenetics.coordinate.model.RowOrCol(1),
            mediumRowOrCol = new org.wheatgenetics.coordinate.model.RowOrCol(5),
            largeRowOrCol  = new org.wheatgenetics.coordinate.model.RowOrCol(9);
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
            rowOrCol = new org.wheatgenetics.coordinate.model.RowOrCol(value);
            org.junit.Assert.assertEquals(value, rowOrCol.getValue());
        }
        org.junit.Assert.assertNotEquals(77, rowOrCol.getValue());
    }

    // region inRange() Package Method Tests
    @org.junit.Test(expected = java.lang.NullPointerException.class)
    public void nullMaxRowOrColInRangeFails()
    {
        // noinspection ConstantConditions
        new org.wheatgenetics.coordinate.model.RowOrCol(5).inRange(null);
    }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void tooSmallMaxRowOrColInRangeFails()
    {
        new org.wheatgenetics.coordinate.model.RowOrCol(5).inRange(
            new org.wheatgenetics.coordinate.model.RowOrCol(3));
    }

    @org.junit.Test() public void inRangeSucceeds()
    {
        new org.wheatgenetics.coordinate.model.RowOrCol(5).inRange(
            new org.wheatgenetics.coordinate.model.RowOrCol(30));
    }
    // endregion

    // region makeWithRandomValue() Package Method Tests
    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void zeroMaxValueMakeWithRandomValueFails()
    { org.wheatgenetics.coordinate.model.RowOrCol.makeWithRandomValue(0); }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void negativeMaxValueMakeWithRandomValueFails()
    { org.wheatgenetics.coordinate.model.RowOrCol.makeWithRandomValue(-9); }

    @org.junit.Test() public void oneMaxValueMakeWithRandomValueSucceeds()
    { org.wheatgenetics.coordinate.model.RowOrCol.makeWithRandomValue(1); }
    // endregion
    // endregion
}