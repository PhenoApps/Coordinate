package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * org.json.JSONArray
 * org.json.JSONException
 *
 * org.junit.Assert
 * org.junit.Test
 *
 * org.wheatgenetics.coordinate.model.RowOrCols
 */
@java.lang.SuppressWarnings("ClassExplicitlyExtendsObject")
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

    @org.junit.Test
    public void secondConstructorSucceeds()
    { new org.wheatgenetics.coordinate.model.RowOrCols(10); }
    // endregion

    // region Third Constructor Tests
    @org.junit.Test
    public void thirdConstructorAndJSONSucceed() throws org.json.JSONException
    {
        final int                firstValue = 27, secondValue = 123;
        final org.json.JSONArray jsonArray  = new org.json.JSONArray();
        jsonArray.put(firstValue); jsonArray.put(secondValue);

        final org.wheatgenetics.coordinate.model.RowOrCols rowOrCols =
            new org.wheatgenetics.coordinate.model.RowOrCols(jsonArray.toString(), 125);

        org.junit.Assert.assertEquals(rowOrCols.json(), jsonArray.toString());
    }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void zeroInputThirdConstructorFails()
    {
        final org.json.JSONArray jsonArray = new org.json.JSONArray();
        jsonArray.put(0);
        new org.wheatgenetics.coordinate.model.RowOrCols(jsonArray.toString(), 125);
    }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void negativeInputThirdConstructorFails()
    {
        final org.json.JSONArray jsonArray = new org.json.JSONArray();
        jsonArray.put(-123);
        new org.wheatgenetics.coordinate.model.RowOrCols(jsonArray.toString(), 125);
    }

    @org.junit.Test
    public void nullInputThirdConstructorSucceeds() throws org.json.JSONException
    {
        final org.wheatgenetics.coordinate.model.RowOrCols
            firstRowOrCols  = new org.wheatgenetics.coordinate.model.RowOrCols(null, 125), // throws
            secondRowOrCols = new org.wheatgenetics.coordinate.model.RowOrCols(      125);
        org.junit.Assert.assertTrue(firstRowOrCols.equals(secondRowOrCols));
    }

    @org.junit.Test
    public void emptyInputThirdConstructorSucceeds() throws org.json.JSONException
    {
        final org.wheatgenetics.coordinate.model.RowOrCols
            firstRowOrCols  = new org.wheatgenetics.coordinate.model.RowOrCols("", 125),   // throws
            secondRowOrCols = new org.wheatgenetics.coordinate.model.RowOrCols(    125);
        org.junit.Assert.assertTrue(firstRowOrCols.equals(secondRowOrCols));
    }

    @org.junit.Test
    public void spacesInputThirdConstructorSucceeds() throws org.json.JSONException
    {
        final org.wheatgenetics.coordinate.model.RowOrCols
            firstRowOrCols  = new org.wheatgenetics.coordinate.model.RowOrCols("  ", 125), // throws
            secondRowOrCols = new org.wheatgenetics.coordinate.model.RowOrCols(      125);
        org.junit.Assert.assertTrue(firstRowOrCols.equals(secondRowOrCols));
    }
    // endregion
    // endregion

    // region Overridden Method Tests
    @org.junit.Test
    public void toStringAndClearSucceed()
    {
        final org.wheatgenetics.coordinate.model.RowOrCols rowOrCols =
            new org.wheatgenetics.coordinate.model.RowOrCols(50);
        org.junit.Assert.assertEquals(rowOrCols.toString(), "null");

        rowOrCols.add(34); org.junit.Assert.assertEquals(rowOrCols.toString(), "34"    );
        rowOrCols.add(11); org.junit.Assert.assertEquals(rowOrCols.toString(), "11, 34");  // sorts!
        rowOrCols.clear(); org.junit.Assert.assertEquals(rowOrCols.toString(), "empty" );
    }

    @org.junit.Test @java.lang.SuppressWarnings("EqualsBetweenInconvertibleTypes")
    public void equalsSucceedsAndFails()
    {
        final org.wheatgenetics.coordinate.model.RowOrCols
            firstRowOrCols  = new org.wheatgenetics.coordinate.model.RowOrCols(150),
            secondRowOrCols = new org.wheatgenetics.coordinate.model.RowOrCols(125);
        org.junit.Assert.assertTrue (firstRowOrCols.equals(secondRowOrCols));
        org.junit.Assert.assertFalse(firstRowOrCols.equals("nonsense"     ));

        final int value = 123;
        firstRowOrCols.add(value); secondRowOrCols.add(value);
        org.junit.Assert.assertTrue(firstRowOrCols.equals(secondRowOrCols));

        secondRowOrCols.clear();
        org.junit.Assert.assertFalse(firstRowOrCols.equals(secondRowOrCols));

        secondRowOrCols.add(45);
        org.junit.Assert.assertFalse(firstRowOrCols.equals(secondRowOrCols));
    }

    @org.junit.Test
    public void hashCodeSucceeds()
    {
        final org.wheatgenetics.coordinate.model.RowOrCols
            firstRowOrCols  = new org.wheatgenetics.coordinate.model.RowOrCols(125),
            secondRowOrCols = new org.wheatgenetics.coordinate.model.RowOrCols(125);
        org.junit.Assert.assertEquals   (firstRowOrCols.hashCode(), secondRowOrCols.hashCode());
        org.junit.Assert.assertNotEquals(firstRowOrCols.hashCode(), 123                       );

        {
            final int value = 123;
            firstRowOrCols.add(value); secondRowOrCols.add(value);
        }
        org.junit.Assert.assertEquals(firstRowOrCols.hashCode(), secondRowOrCols.hashCode());

        secondRowOrCols.clear();
        org.junit.Assert.assertNotEquals(firstRowOrCols.hashCode(), secondRowOrCols.hashCode());

        secondRowOrCols.add(99);
        org.junit.Assert.assertNotEquals(firstRowOrCols.hashCode(), secondRowOrCols.hashCode());
    }

    @org.junit.Test
    public void cloneSucceeds()
    {
        final org.wheatgenetics.coordinate.model.RowOrCols rowOrCols =
            new org.wheatgenetics.coordinate.model.RowOrCols(5);
        org.junit.Assert.assertTrue(rowOrCols.equals(rowOrCols.clone()));

        rowOrCols.add(2);
        org.junit.Assert.assertTrue(rowOrCols.equals(rowOrCols.clone()));
    }
    // endregion

    // region Package Method Tests
    @org.junit.Test
    public void addAndContainsSucceedAndFail()
    {
        final org.wheatgenetics.coordinate.model.RowOrCols rowOrCols =
            new org.wheatgenetics.coordinate.model.RowOrCols(125);
        {
            final int value = 123;
            rowOrCols.add(value);
            org.junit.Assert.assertTrue(rowOrCols.contains(value));
        }
        org.junit.Assert.assertFalse(rowOrCols.contains(111));
        org.junit.Assert.assertFalse(rowOrCols.contains(456));
    }

    // region add() Package Method Tests
    @org.junit.Test
    public void duplicateAddFails()
    {
        final org.wheatgenetics.coordinate.model.RowOrCols rowOrCols =
            new org.wheatgenetics.coordinate.model.RowOrCols(125);
        {
            final int value = 123;
            rowOrCols.add(value);
            rowOrCols.add(value);         // Does not add() but does not throw an exception, either.
        }
        org.junit.Assert.assertEquals(rowOrCols.toString(), "123");// Note: value add()ed only once.
    }

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
    // endregion
}