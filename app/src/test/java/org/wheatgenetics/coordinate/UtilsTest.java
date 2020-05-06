package org.wheatgenetics.coordinate;

import android.content.res.Resources;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.PluralsRes;
import androidx.annotation.StringRes;

import org.junit.Assert;
import org.junit.Test;

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
@SuppressWarnings({"ClassExplicitlyExtendsObject"}) public class UtilsTest
extends Object implements StringGetter
{
    // region org.wheatgenetics.coordinate.StringGetter Overridden Methods
    @Override @Nullable public String get(
    @StringRes final int resId)
    {
        // noinspection SwitchStatementWithTooFewBranches
        switch (resId)
        {
            case R.string.UtilsInvalidValue:
                return "value must be >= %d";

            default: Assert.fail(); return null;
        }
    }

    @Override @NonNull public String getQuantity(
    @PluralsRes         final int                 resId     ,
    @IntRange(from = 0) final int                 quantity  ,
    @Nullable           final Object... formatArgs)
    throws Resources.NotFoundException
    {
        // noinspection SwitchStatementWithTooFewBranches
        switch (resId) { default: Assert.fail(); return null; }
    }
    // endregion

    // region Public Method Tests
    // region valid() Public Method Tests
    @Test(expected = IllegalArgumentException.class) public void validThrows()
    {
        Utils.valid(
            /* value => */0, /* minValue => */1,this);
    }

    @Test() public void validSucceeds()
    {
        final int value = 3;
        Assert.assertEquals(value, Utils.valid(
            value, /* minValue => */0,this));
    }
    // endregion

    // region convert() Public Method Tests
    @Test(expected = IllegalArgumentException.class)
    public void badOffsetConvertThrows()
    { Utils.convert(-5); }

    @Test() public void smallOffsetConvertSucceeds()
    {
        Assert.assertEquals("C",
            Utils.convert(2));
    }

    @Test() public void mediumOffsetConvertSucceeds()
    {
        Assert.assertEquals("AC",
            Utils.convert(26 + 2));
    }

    @Test() public void largeOffsetConvertSucceeds()
    {
        Assert.assertEquals("BC",
            Utils.convert(26 + 26 + 2));
    }
    // endregion
    // endregion
}