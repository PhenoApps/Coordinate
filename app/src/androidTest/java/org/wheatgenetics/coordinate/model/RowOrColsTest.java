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
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"}) public class RowOrColsTest
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

    // region Constructor Tests
    // region Second Constructor Tests
    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void zeroMaxValueSecondConstructorFails()
    { new org.wheatgenetics.coordinate.model.RowOrCols(0,this); }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void negativeMaxValueSecondConstructorFails()
    { new org.wheatgenetics.coordinate.model.RowOrCols(-80,this); }

    @org.junit.Test() public void secondConstructorSucceeds()
    { new org.wheatgenetics.coordinate.model.RowOrCols(10,this); }
    // endregion

    // region Third Constructor Tests
    @org.junit.Test() public void thirdConstructorAndJSONSucceed()
    {
        final org.json.JSONArray jsonArray = new org.json.JSONArray();
        {
            final int firstValue = 27, secondValue = 123;
            jsonArray.put(firstValue); jsonArray.put(secondValue);
        }
        final org.wheatgenetics.coordinate.model.RowOrCols rowOrCols =
            new org.wheatgenetics.coordinate.model.RowOrCols(
                jsonArray.toString(),125,this);
        org.junit.Assert.assertEquals(jsonArray.toString(), rowOrCols.json());
    }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void zeroInputThirdConstructorFails()
    {
        final org.json.JSONArray jsonArray = new org.json.JSONArray();
        jsonArray.put(0);
        new org.wheatgenetics.coordinate.model.RowOrCols(
            jsonArray.toString(),125,this);
    }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void negativeInputThirdConstructorFails()
    {
        final org.json.JSONArray jsonArray = new org.json.JSONArray();
        jsonArray.put(-123);
        new org.wheatgenetics.coordinate.model.RowOrCols(
            jsonArray.toString(),125,this);
    }

    @org.junit.Test() public void nullInputThirdConstructorSucceeds()
    {
        final org.wheatgenetics.coordinate.model.RowOrCols
            firstRowOrCols = new org.wheatgenetics.coordinate.model.RowOrCols(
                null,125,this),
            secondRowOrCols = new org.wheatgenetics.coordinate.model.RowOrCols(
                125,this);

        // noinspection SimplifiableJUnitAssertion
        org.junit.Assert.assertTrue(firstRowOrCols.equals(secondRowOrCols));
    }

    @org.junit.Test() public void emptyInputThirdConstructorSucceeds()
    {
        final org.wheatgenetics.coordinate.model.RowOrCols
            firstRowOrCols = new org.wheatgenetics.coordinate.model.RowOrCols(
                "",125,this),
            secondRowOrCols = new org.wheatgenetics.coordinate.model.RowOrCols(
                125,this);

        // noinspection SimplifiableJUnitAssertion
        org.junit.Assert.assertTrue(firstRowOrCols.equals(secondRowOrCols));
    }

    @org.junit.Test() public void spacesInputThirdConstructorSucceeds()
    {
        final org.wheatgenetics.coordinate.model.RowOrCols
            firstRowOrCols = new org.wheatgenetics.coordinate.model.RowOrCols(
                "  ",125,this),
            secondRowOrCols = new org.wheatgenetics.coordinate.model.RowOrCols(
                125,this);

        // noinspection SimplifiableJUnitAssertion
        org.junit.Assert.assertTrue(firstRowOrCols.equals(secondRowOrCols));
    }
    // endregion
    // endregion

    // region Overridden Method Tests
    @org.junit.Test() public void toStringSucceeds()
    {
        final org.wheatgenetics.coordinate.model.RowOrCols rowOrCols =
            new org.wheatgenetics.coordinate.model.RowOrCols(50,this);
        org.junit.Assert.assertEquals("null", rowOrCols.toString());

        rowOrCols.add(34); org.junit.Assert.assertEquals("34", rowOrCols.toString());

        rowOrCols.add(11);
        org.junit.Assert.assertEquals("11, 34", rowOrCols.toString() /* sorts! */);
    }

    @org.junit.Test() public void equalsWorks()
    {
        final org.wheatgenetics.coordinate.model.RowOrCols
            firstRowOrCols =
            new org.wheatgenetics.coordinate.model.RowOrCols(150,this),
            secondRowOrCols =
                new org.wheatgenetics.coordinate.model.RowOrCols(125,this);

        // noinspection SimplifiableJUnitAssertion
        org.junit.Assert.assertTrue(firstRowOrCols.equals(secondRowOrCols));

        // noinspection SimplifiableJUnitAssertion, EqualsBetweenInconvertibleTypes
        org.junit.Assert.assertFalse(firstRowOrCols.equals("nonsense"));

        {
            final int value = 123;
            firstRowOrCols.add(value);

            // noinspection SimplifiableJUnitAssertion
            org.junit.Assert.assertFalse(firstRowOrCols.equals(secondRowOrCols));

            secondRowOrCols.add(value);
        }
        // noinspection SimplifiableJUnitAssertion
        org.junit.Assert.assertTrue(firstRowOrCols.equals(secondRowOrCols));
    }

    @org.junit.Test() public void hashCodeSucceeds()
    {
        final org.wheatgenetics.coordinate.model.RowOrCols
            firstRowOrCols =
            new org.wheatgenetics.coordinate.model.RowOrCols(125,this),
            secondRowOrCols =
                new org.wheatgenetics.coordinate.model.RowOrCols(125,this);
        org.junit.Assert.assertEquals   (firstRowOrCols.hashCode(), secondRowOrCols.hashCode());
        org.junit.Assert.assertNotEquals(123, secondRowOrCols.hashCode());

        {
            final int value = 123;
            firstRowOrCols.add(value);
            org.junit.Assert.assertNotEquals(firstRowOrCols.hashCode(), secondRowOrCols.hashCode());
            secondRowOrCols.add(value);
        }
        org.junit.Assert.assertEquals(firstRowOrCols.hashCode(), secondRowOrCols.hashCode());
    }

    @org.junit.Test() public void cloneSucceeds()
    {
        final org.wheatgenetics.coordinate.model.RowOrCols rowOrCols =
            new org.wheatgenetics.coordinate.model.RowOrCols(5,this);

        // noinspection SimplifiableJUnitAssertion
        org.junit.Assert.assertTrue(rowOrCols.equals(rowOrCols.clone()));

        rowOrCols.add(2);

        // noinspection SimplifiableJUnitAssertion
        org.junit.Assert.assertTrue(rowOrCols.equals(rowOrCols.clone()));
    }
    // endregion

    // region Package Method Tests
    // region add() Package Method Tests
    @org.junit.Test() public void addAndContainsWork()
    {
        final org.wheatgenetics.coordinate.model.RowOrCols rowOrCols =
            new org.wheatgenetics.coordinate.model.RowOrCols(125,this);
        {
            final int value = 123;
            rowOrCols.add(value); org.junit.Assert.assertTrue(rowOrCols.contains(value));
        }
        org.junit.Assert.assertFalse(rowOrCols.contains(111));
        org.junit.Assert.assertFalse(rowOrCols.contains(456));
    }

    @org.junit.Test() public void duplicateAddDoesNotAdd()
    {
        final org.wheatgenetics.coordinate.model.RowOrCols rowOrCols =
            new org.wheatgenetics.coordinate.model.RowOrCols(125,this);
        {
            final int value = 123;
            rowOrCols.add(value);
            rowOrCols.add(value);         // Does not add() but does not throw an exception, either.
        }
        org.junit.Assert.assertEquals("123", rowOrCols.toString());  // Note: value add()ed
    }                                                                         //  only once.

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class) public void zeroAddFails()
    { new org.wheatgenetics.coordinate.model.RowOrCols(12,this).add(0); }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void negativeAddFails()
    { new org.wheatgenetics.coordinate.model.RowOrCols(12,this).add(-20); }
    // endregion

    @org.junit.Test() public void clearSucceeds()
    {
        final org.wheatgenetics.coordinate.model.RowOrCols rowOrCols =
            new org.wheatgenetics.coordinate.model.RowOrCols(125,this);
        final int value = 123;
        rowOrCols.add(value); org.junit.Assert.assertTrue (rowOrCols.contains(value));
        rowOrCols.clear()   ; org.junit.Assert.assertFalse(rowOrCols.contains(value));
    }
    // endregion
}