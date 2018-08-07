package org.wheatgenetics.coordinate;

/**
 * Uses:
 * org.junit.Assert
 * org.junit.Test
 *
 * org.wheatgenetics.coordinate.Utils
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
public class UtilsTest extends java.lang.Object
{
    @org.junit.Test(expected = java.lang.IllegalArgumentException.class) public void validThrows()
    {
        org.wheatgenetics.coordinate.Utils.valid(
            /* value => */0, /* minValue => */1);
    }

    @org.junit.Test() public void validSucceeds()
    {
        final int value = 3;
        org.junit.Assert.assertEquals(value,
            org.wheatgenetics.coordinate.Utils.valid(value, /* minValue => */0));
    }
}