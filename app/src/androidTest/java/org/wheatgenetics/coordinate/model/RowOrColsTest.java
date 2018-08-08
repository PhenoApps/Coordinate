package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * org.json.JSONArray
 *
 * org.junit.Assert
 * org.junit.Test
 *
 * org.wheatgenetics.coordinate.model.RowOrCols
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
public class RowOrColsTest extends java.lang.Object
{
    // region Constructor Tests
    // region Second Constructor Tests
    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void zeroMaxValueSecondConstructorFails()
    { new org.wheatgenetics.coordinate.model.RowOrCols(0); }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void negativeMaxValueSecondConstructorFails()
    { new org.wheatgenetics.coordinate.model.RowOrCols(-80); }

    @org.junit.Test() public void secondConstructorSucceeds()
    { new org.wheatgenetics.coordinate.model.RowOrCols(10); }
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
            new org.wheatgenetics.coordinate.model.RowOrCols(jsonArray.toString(),125);
        org.junit.Assert.assertEquals(jsonArray.toString(), rowOrCols.json());
    }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void zeroInputThirdConstructorFails()
    {
        final org.json.JSONArray jsonArray = new org.json.JSONArray();
        jsonArray.put(0);
        new org.wheatgenetics.coordinate.model.RowOrCols(jsonArray.toString(),125);
    }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void negativeInputThirdConstructorFails()
    {
        final org.json.JSONArray jsonArray = new org.json.JSONArray();
        jsonArray.put(-123);
        new org.wheatgenetics.coordinate.model.RowOrCols(jsonArray.toString(),125);
    }

    @org.junit.Test() public void nullInputThirdConstructorSucceeds()
    {
        final org.wheatgenetics.coordinate.model.RowOrCols
            firstRowOrCols =
                new org.wheatgenetics.coordinate.model.RowOrCols(null,125),
            secondRowOrCols = new org.wheatgenetics.coordinate.model.RowOrCols(125);
        org.junit.Assert.assertTrue(firstRowOrCols.equals(secondRowOrCols));
    }

    @org.junit.Test() public void emptyInputThirdConstructorSucceeds()
    {
        final org.wheatgenetics.coordinate.model.RowOrCols
            firstRowOrCols =
                new org.wheatgenetics.coordinate.model.RowOrCols("",125),
            secondRowOrCols = new org.wheatgenetics.coordinate.model.RowOrCols(125);
        org.junit.Assert.assertTrue(firstRowOrCols.equals(secondRowOrCols));
    }

    @org.junit.Test() public void spacesInputThirdConstructorSucceeds()
    {
        final org.wheatgenetics.coordinate.model.RowOrCols
            firstRowOrCols =
                new org.wheatgenetics.coordinate.model.RowOrCols("  ",125),
            secondRowOrCols = new org.wheatgenetics.coordinate.model.RowOrCols(125);
        org.junit.Assert.assertTrue(firstRowOrCols.equals(secondRowOrCols));
    }
    // endregion
    // endregion

    // region Overridden Method Tests
    @org.junit.Test() public void toStringSucceeds()
    {
        final org.wheatgenetics.coordinate.model.RowOrCols rowOrCols =
            new org.wheatgenetics.coordinate.model.RowOrCols(50);
        org.junit.Assert.assertEquals("null", rowOrCols.toString());

        rowOrCols.add(34); org.junit.Assert.assertTrue("34".equals(rowOrCols.toString()));

        rowOrCols.add(11);
        org.junit.Assert.assertTrue("11, 34".equals(rowOrCols.toString() /* sorts! */));
    }

    @org.junit.Test() public void equalsWorks()
    {
        final org.wheatgenetics.coordinate.model.RowOrCols
            firstRowOrCols  = new org.wheatgenetics.coordinate.model.RowOrCols(150),
            secondRowOrCols = new org.wheatgenetics.coordinate.model.RowOrCols(125);
        org.junit.Assert.assertTrue(firstRowOrCols.equals(secondRowOrCols));

        // noinspection EqualsBetweenInconvertibleTypes
        org.junit.Assert.assertFalse(firstRowOrCols.equals("nonsense"));

        {
            final int value = 123;
            firstRowOrCols.add(value);
            org.junit.Assert.assertFalse(firstRowOrCols.equals(secondRowOrCols));
            secondRowOrCols.add(value);
        }
        org.junit.Assert.assertTrue(firstRowOrCols.equals(secondRowOrCols));
    }

    @org.junit.Test() public void hashCodeSucceeds()
    {
        final org.wheatgenetics.coordinate.model.RowOrCols
            firstRowOrCols  = new org.wheatgenetics.coordinate.model.RowOrCols(125),
            secondRowOrCols = new org.wheatgenetics.coordinate.model.RowOrCols(125);
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
            new org.wheatgenetics.coordinate.model.RowOrCols(5);
        org.junit.Assert.assertTrue(rowOrCols.equals(rowOrCols.clone()));

        rowOrCols.add(2); org.junit.Assert.assertTrue(rowOrCols.equals(rowOrCols.clone()));
    }
    // endregion

    // region Package Method Tests
    // region add() Package Method Tests
    @org.junit.Test() public void addAndContainsWork()
    {
        final org.wheatgenetics.coordinate.model.RowOrCols rowOrCols =
            new org.wheatgenetics.coordinate.model.RowOrCols(125);
        {
            final int value = 123;
            rowOrCols.add(value); org.junit.Assert.assertTrue(rowOrCols.contains(value));
        }
        org.junit.Assert.assertFalse(rowOrCols.contains(111));
        org.junit.Assert.assertFalse(rowOrCols.contains(456));
    }

    @org.junit.Test() public void duplicateAddFails()
    {
        final org.wheatgenetics.coordinate.model.RowOrCols rowOrCols =
            new org.wheatgenetics.coordinate.model.RowOrCols(125);
        {
            final int value = 123;
            rowOrCols.add(value);
            rowOrCols.add(value);         // Does not add() but does not throw an exception, either.
        }
        org.junit.Assert.assertTrue("123".equals(rowOrCols.toString()));      // Note: value add()ed
    }                                                                         //  only once.

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void zeroAddFails()
    {
        final org.wheatgenetics.coordinate.model.RowOrCols rowOrCols =
            new org.wheatgenetics.coordinate.model.RowOrCols(12);
        rowOrCols.add(0);
    }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void negativeAddFails()
    {
        final org.wheatgenetics.coordinate.model.RowOrCols rowOrCols =
            new org.wheatgenetics.coordinate.model.RowOrCols(12);
        rowOrCols.add(-20);
    }
    // endregion

    @org.junit.Test() public void clearSucceeds()
    {
        final org.wheatgenetics.coordinate.model.RowOrCols rowOrCols =
            new org.wheatgenetics.coordinate.model.RowOrCols(125);
        final int value = 123;
        rowOrCols.add(value); org.junit.Assert.assertTrue (rowOrCols.contains(value));
        rowOrCols.clear()   ; org.junit.Assert.assertFalse(rowOrCols.contains(value));

    }
    // endregion
}