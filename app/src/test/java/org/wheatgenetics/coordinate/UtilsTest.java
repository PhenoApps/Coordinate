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
    // region Public Method Tests
    // region valid() Public Method Tests
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
    // endregion

    // region convert() Public Method Tests
    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void badOffsetConvertThrows()
    { org.wheatgenetics.coordinate.Utils.convert(-5); }

    @org.junit.Test() public void smallOffsetConvertSucceeds()
    {
        org.junit.Assert.assertEquals("C",
            org.wheatgenetics.coordinate.Utils.convert(2));
    }

    @org.junit.Test() public void mediumOffsetConvertSucceeds()
    {
        org.junit.Assert.assertEquals("AC",
            org.wheatgenetics.coordinate.Utils.convert(26 + 2));
    }

    @org.junit.Test() public void largeOffsetConvertSucceeds()
    {
        org.junit.Assert.assertEquals("BC",
            org.wheatgenetics.coordinate.Utils.convert(26 + 26 + 2));
    }
    // endregion
    // endregion
}