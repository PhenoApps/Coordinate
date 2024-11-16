package org.wheatgenetics.coordinate.optionalField;

import android.content.res.Resources;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.PluralsRes;
import androidx.annotation.StringRes;

import org.junit.Assert;
import org.junit.Test;
import org.wheatgenetics.androidlibrary.Utils;
import org.wheatgenetics.coordinate.R;
import org.wheatgenetics.coordinate.StringGetter;

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
 * org.wheatgenetics.androidlibrary.Utils
 *
 * org.wheatgenetics.coordinate.R
 * org.wheatgenetics.coordinate.StringGetter
 *
 * org.wheatgenetics.coordinate.optionalField.BaseOptionalField
 * org.wheatgenetics.coordinate.optionalField.DateOptionalField
 */
@SuppressWarnings({"ClassExplicitlyExtendsObject"}) public class DateOptionalFieldTest
extends Object implements StringGetter
{
    @NonNull private static String expectedCurrentDate()
    {
        return Utils.formatDate(
            System.currentTimeMillis()).toString();
    }

    // region org.wheatgenetics.coordinate.StringGetter Overridden Methods
    @Override @Nullable public String get(
    @StringRes final int resId)
    {
        switch (resId)
        {
            case R.string.BaseOptionalFieldIdentificationFieldName:
                return "Identification";

            case R.string.TimestampOptionalFieldDateFieldName:
                return "Date";

            default: Assert.fail(); return null;
        }
    }

    @Override @NonNull public String getQuantity(
    @PluralsRes         final int                 resId     ,
    @IntRange(from = 0) final int                 quantity  ,
    @Nullable           final Object... formatArgs)
    throws Resources.NotFoundException { Assert.fail(); return null; }
    // endregion

    @Test() public void constructorSucceeds()
    {
        final TimestampOptionalField timestampOptionalField =
            new TimestampOptionalField(this);
        Assert.assertEquals("Date", timestampOptionalField.getName());
        Assert.assertEquals(
            BaseOptionalField.TIMESTAMP_HINT,
            timestampOptionalField.getHint()                                           );
    }

    // region Overridden Method Tests
    @Test() public void cloneSucceeds()
    {
        final TimestampOptionalField timestampOptionalField =
            new TimestampOptionalField(this);
        final TimestampOptionalField clonedTimestampOptionalField =
            (TimestampOptionalField)
                    timestampOptionalField.clone();
        Assert.assertEquals("Date", clonedTimestampOptionalField.getName());
        Assert.assertEquals(
            BaseOptionalField.TIMESTAMP_HINT,
            clonedTimestampOptionalField.getHint()                                     );

        // noinspection SimplifiableJUnitAssertion
        Assert.assertTrue(timestampOptionalField.equals(clonedTimestampOptionalField));
    }

    @Test() public void getValue()
    {
        Assert.assertEquals(
            DateOptionalFieldTest.expectedCurrentDate(),
            new TimestampOptionalField(
                this).getValue());
    }
    // endregion

    @Test() public void getCurrentDate()
    {
        Assert.assertEquals(
            DateOptionalFieldTest.expectedCurrentDate(),
            TimestampOptionalField.getCurrentTimestamp()         );
    }
}