package org.wheatgenetics.coordinate;

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
 * org.wheatgenetics.coordinate.Utils
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"}) public class UtilsTest
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

    // region Public Method Tests
    // region valid() Public Method Tests
    @org.junit.Test(expected = java.lang.IllegalArgumentException.class) public void validThrows()
    {
        org.wheatgenetics.coordinate.Utils.valid(
            /* value => */0, /* minValue => */1,this);
    }

    @org.junit.Test() public void validSucceeds()
    {
        final int value = 3;
        org.junit.Assert.assertEquals(value, org.wheatgenetics.coordinate.Utils.valid(
            value, /* minValue => */0,this));
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