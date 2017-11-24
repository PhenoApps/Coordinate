package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * org.json.JSONArray
 * org.json.JSONException
 *
 * org.junit.Assert
 * org.junit.Test
 *
 * org.wheatgenetics.coordinate.model.Coordinates
 */
@java.lang.SuppressWarnings("ClassExplicitlyExtendsObject")
public class CoordinatesTest extends java.lang.Object
{
    // region Constructor Tests
    @org.junit.Test
    public void secondConstructorAndJSONSucceed() throws org.json.JSONException
    {
        final int firstCoordinate = 123, secondCoordinate = 27;
        final org.json.JSONArray jsonArray = new org.json.JSONArray();
        jsonArray.put(firstCoordinate); jsonArray.put(secondCoordinate);

        final org.wheatgenetics.coordinate.model.Coordinates coordinates =
            new org.wheatgenetics.coordinate.model.Coordinates(jsonArray.toString());

        org.junit.Assert.assertEquals(coordinates.json(), jsonArray.toString());
    }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void zeroInputSecondConstructorFails()
    {
        final org.json.JSONArray jsonArray = new org.json.JSONArray();
        jsonArray.put(0);
        new org.wheatgenetics.coordinate.model.Coordinates(jsonArray.toString());
    }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void negativeInputSecondConstructorFails()
    {
        final org.json.JSONArray jsonArray = new org.json.JSONArray();
        jsonArray.put(-123);
        new org.wheatgenetics.coordinate.model.Coordinates(jsonArray.toString());
    }

    @org.junit.Test
    public void nullInputSecondConstructorSucceeds() throws org.json.JSONException
    {
        final org.wheatgenetics.coordinate.model.Coordinates
            firstCoordinates  = new org.wheatgenetics.coordinate.model.Coordinates(null),  // throws
            secondCoordinates = new org.wheatgenetics.coordinate.model.Coordinates()    ;
        org.junit.Assert.assertTrue(firstCoordinates.equals(secondCoordinates));
    }

    @org.junit.Test
    public void emptyInputSecondConstructorSucceeds() throws org.json.JSONException
    {
        final org.wheatgenetics.coordinate.model.Coordinates
            firstCoordinates  = new org.wheatgenetics.coordinate.model.Coordinates(""),    // throws
            secondCoordinates = new org.wheatgenetics.coordinate.model.Coordinates()  ;
        org.junit.Assert.assertTrue(firstCoordinates.equals(secondCoordinates));
    }

    @org.junit.Test
    public void spacesInputSecondConstructorSucceeds() throws org.json.JSONException
    {
        final org.wheatgenetics.coordinate.model.Coordinates
            firstCoordinates  = new org.wheatgenetics.coordinate.model.Coordinates("  "),  // throws
            secondCoordinates = new org.wheatgenetics.coordinate.model.Coordinates()    ;
        org.junit.Assert.assertTrue(firstCoordinates.equals(secondCoordinates));
    }
    // endregion

    // region Overridden Method Tests
    @org.junit.Test
    public void toStringAndClearSucceed()
    {
        final org.wheatgenetics.coordinate.model.Coordinates coordinates =
            new org.wheatgenetics.coordinate.model.Coordinates();
        org.junit.Assert.assertEquals(coordinates.toString(), "null");

        coordinates.add(34); org.junit.Assert.assertEquals(coordinates.toString(), "34"    );
        coordinates.add(11); org.junit.Assert.assertEquals(coordinates.toString(), "34, 11");
        coordinates.clear(); org.junit.Assert.assertEquals(coordinates.toString(), "empty" );
    }

    @org.junit.Test @java.lang.SuppressWarnings("EqualsBetweenInconvertibleTypes")
    public void equalsSucceedsAndFails()
    {
        final org.wheatgenetics.coordinate.model.Coordinates
            firstCoordinates  = new org.wheatgenetics.coordinate.model.Coordinates(),
            secondCoordinates = new org.wheatgenetics.coordinate.model.Coordinates();
        org.junit.Assert.assertTrue (firstCoordinates.equals(secondCoordinates));
        org.junit.Assert.assertFalse(firstCoordinates.equals("nonsense"       ));

        final int a = 123;
        firstCoordinates.add(a); secondCoordinates.add(a);
        org.junit.Assert.assertTrue(firstCoordinates.equals(secondCoordinates));

        secondCoordinates.clear();
        org.junit.Assert.assertFalse(firstCoordinates.equals(secondCoordinates));

        secondCoordinates.add(45);
        org.junit.Assert.assertFalse(firstCoordinates.equals(secondCoordinates));
    }

    @org.junit.Test
    public void hashCodeSucceeds()
    {
        final org.wheatgenetics.coordinate.model.Coordinates
            firstCoordinates  = new org.wheatgenetics.coordinate.model.Coordinates(),
            secondCoordinates = new org.wheatgenetics.coordinate.model.Coordinates();
        org.junit.Assert.assertEquals   (firstCoordinates.hashCode(), secondCoordinates.hashCode());
        org.junit.Assert.assertNotEquals(firstCoordinates.hashCode(), 123                         );

        final int a = 123;
        firstCoordinates.add(a); secondCoordinates.add(a);
        org.junit.Assert.assertEquals(firstCoordinates.hashCode(), secondCoordinates.hashCode());

        secondCoordinates.clear();
        org.junit.Assert.assertNotEquals(firstCoordinates.hashCode(), secondCoordinates.hashCode());

        secondCoordinates.add(99);
        org.junit.Assert.assertNotEquals(firstCoordinates.hashCode(), secondCoordinates.hashCode());
    }

    @org.junit.Test
    public void cloneSucceeds()
    {
        final org.wheatgenetics.coordinate.model.Coordinates coordinates =
            new org.wheatgenetics.coordinate.model.Coordinates();
        org.junit.Assert.assertTrue(coordinates.equals(coordinates.clone()));

        coordinates.add(2);
        org.junit.Assert.assertTrue(coordinates.equals(coordinates.clone()));
    }
    // endregion

    @org.junit.Test
    public void addAndIsPresentSucceed()
    {
        final int a = 123;
        final org.wheatgenetics.coordinate.model.Coordinates coordinates =
            new org.wheatgenetics.coordinate.model.Coordinates();
        coordinates.add(a);
        org.junit.Assert.assertTrue(coordinates.isPresent(a));
    }
}