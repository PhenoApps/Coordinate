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
    @org.junit.Test
    public void secondConstructorAndJSONSucceed() throws org.json.JSONException
    {
        final int                firstValue = 27, secondValue = 123;
        final org.json.JSONArray jsonArray  = new org.json.JSONArray();
        jsonArray.put(firstValue); jsonArray.put(secondValue);

        final org.wheatgenetics.coordinate.model.RowOrCols rowOrCols =
            new org.wheatgenetics.coordinate.model.RowOrCols(jsonArray.toString());

        org.junit.Assert.assertEquals(rowOrCols.json(), jsonArray.toString());
    }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void zeroInputSecondConstructorFails()
    {
        final org.json.JSONArray jsonArray = new org.json.JSONArray();
        jsonArray.put(0);
        new org.wheatgenetics.coordinate.model.RowOrCols(jsonArray.toString());
    }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void negativeInputSecondConstructorFails()
    {
        final org.json.JSONArray jsonArray = new org.json.JSONArray();
        jsonArray.put(-123);
        new org.wheatgenetics.coordinate.model.RowOrCols(jsonArray.toString());
    }

    @org.junit.Test
    public void nullInputSecondConstructorSucceeds() throws org.json.JSONException
    {
        final org.wheatgenetics.coordinate.model.RowOrCols
            firstRowOrCols  = new org.wheatgenetics.coordinate.model.RowOrCols(null),      // throws
            secondRowOrCols = new org.wheatgenetics.coordinate.model.RowOrCols()    ;
        org.junit.Assert.assertTrue(firstRowOrCols.equals(secondRowOrCols));
    }

    @org.junit.Test
    public void emptyInputSecondConstructorSucceeds() throws org.json.JSONException
    {
        final org.wheatgenetics.coordinate.model.RowOrCols
            firstRowOrCols  = new org.wheatgenetics.coordinate.model.RowOrCols(""),        // throws
            secondRowOrCols = new org.wheatgenetics.coordinate.model.RowOrCols()  ;
        org.junit.Assert.assertTrue(firstRowOrCols.equals(secondRowOrCols));
    }

    @org.junit.Test
    public void spacesInputSecondConstructorSucceeds() throws org.json.JSONException
    {
        final org.wheatgenetics.coordinate.model.RowOrCols
            firstRowOrCols  = new org.wheatgenetics.coordinate.model.RowOrCols("  "),      // throws
            secondRowOrCols = new org.wheatgenetics.coordinate.model.RowOrCols()    ;
        org.junit.Assert.assertTrue(firstRowOrCols.equals(secondRowOrCols));
    }
    // endregion

    // region Overridden Method Tests
    @org.junit.Test
    public void toStringAndClearSucceed()
    {
        final org.wheatgenetics.coordinate.model.RowOrCols rowOrCols =
            new org.wheatgenetics.coordinate.model.RowOrCols();
        org.junit.Assert.assertEquals(rowOrCols.toString(), "null");

        rowOrCols.add(34); org.junit.Assert.assertEquals(rowOrCols.toString(), "34"    );
        rowOrCols.add(11); org.junit.Assert.assertEquals(rowOrCols.toString(), "11, 34");  // sorts!
        rowOrCols.clear(); org.junit.Assert.assertEquals(rowOrCols.toString(), "empty" );
    }

    @org.junit.Test @java.lang.SuppressWarnings("EqualsBetweenInconvertibleTypes")
    public void equalsSucceedsAndFails()
    {
        final org.wheatgenetics.coordinate.model.RowOrCols
            firstRowOrCols  = new org.wheatgenetics.coordinate.model.RowOrCols(),
            secondRowOrCols = new org.wheatgenetics.coordinate.model.RowOrCols();
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
            firstRowOrCols  = new org.wheatgenetics.coordinate.model.RowOrCols(),
            secondRowOrCols = new org.wheatgenetics.coordinate.model.RowOrCols();
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
            new org.wheatgenetics.coordinate.model.RowOrCols();
        org.junit.Assert.assertTrue(rowOrCols.equals(rowOrCols.clone()));

        rowOrCols.add(2);
        org.junit.Assert.assertTrue(rowOrCols.equals(rowOrCols.clone()));
    }
    // endregion

    // region Package Method Tests
    @org.junit.Test
    public void addAndContainsFailAndSucceed()
    {
        final org.wheatgenetics.coordinate.model.RowOrCols rowOrCols =
            new org.wheatgenetics.coordinate.model.RowOrCols();
        {
            final int value = 123;
            rowOrCols.add(value);
            org.junit.Assert.assertTrue(rowOrCols.contains(value));
        }
        org.junit.Assert.assertFalse(rowOrCols.contains(456));
    }

    @org.junit.Test
    public void duplicateAddFails()
    {
        final org.wheatgenetics.coordinate.model.RowOrCols rowOrCols =
            new org.wheatgenetics.coordinate.model.RowOrCols();
        {
            final int value = 123;
            rowOrCols.add(value);
            rowOrCols.add(value);         // Does not add() but does not throw an exception, either.
        }
        org.junit.Assert.assertEquals(rowOrCols.toString(), "123");// Note: value add()ed only once.
    }
    // endregion
}