package org.wheatgenetics.coordinate.optionalField;

import android.content.res.Resources;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.PluralsRes;
import androidx.annotation.StringRes;

import org.junit.Assert;
import org.junit.Test;
import org.wheatgenetics.coordinate.R;
import org.wheatgenetics.coordinate.StringGetter;

/**
 * Uses:
 * android.content.res.Resources.NotFoundException
 *
 * androidx.annotation.IntRange
 * androidx.annotation.Nullable
 * androidx.annotation.PluralsRes
 * androidx.annotation.StringRes
 *
 * org.junit.Assert
 * org.junit.Test
 *
 * org.wheatgenetics.coordinate.R
 * org.wheatgenetics.coordinate.StringGetter
 *
 * org.wheatgenetics.coordinate.optionalField.BaseOptionalField
 * org.wheatgenetics.coordinate.optionalField.OptionalField
 * org.wheatgenetics.coordinate.optionalField.OtherOptionalField
 * org.wheatgenetics.coordinate.optionalField.OtherOptionalField.WrongClass
 */
@SuppressWarnings({"ClassExplicitlyExtendsObject"}) public class OtherOptionalFieldTest
extends Object implements StringGetter
{
    // region org.wheatgenetics.coordinate.StringGetter Overridden Methods
    @Override @Nullable public String get(
    @StringRes final int resId)
    {
        // noinspection SwitchStatementWithTooFewBranches
        switch (resId)
        {
            case R.string.BaseOptionalFieldIdentificationFieldName:
                return "Identification";

            default: Assert.fail(); return null;
        }
    }

    @Override @NonNull public String getQuantity(
    @PluralsRes         final int                 resId     ,
    @IntRange(from = 0) final int                 quantity  ,
    @Nullable           final Object... formatArgs)
    throws Resources.NotFoundException { Assert.fail(); return null; }
    // endregion

    // region Constructor Tests
    @Test(
        expected = OtherOptionalField.WrongClass.class)
    public void constructorFails()
    throws OtherOptionalField.WrongClass
    {
        new OtherOptionalField(
            OptionalField.makeJSONObject(
                "testName",null,
                BaseOptionalField.TIMESTAMP_HINT,
                this),this);
    }

    @Test() public void constructorSucceeds()
    throws OtherOptionalField.WrongClass
    {
        new OtherOptionalField(
            OptionalField.makeJSONObject(
                "testName",null,"testHint",this),this);
    }
    // endregion

    @Test() public void cloneSucceeds()
    {
        final OtherOptionalField
            otherOptionalField, clonedOtherOptionalField;
        {
            final String testName = "testName",
                testValue = "testValue", testHint = "testHint";
            otherOptionalField = new OtherOptionalField(
                testName, testValue, testHint,this);
            otherOptionalField.setChecked(false);

            clonedOtherOptionalField =
                (OtherOptionalField)
                    otherOptionalField.clone();

            Assert.assertEquals(testName , clonedOtherOptionalField.getName ());
            Assert.assertEquals(testValue, clonedOtherOptionalField.getValue());
            Assert.assertEquals(testHint , clonedOtherOptionalField.getHint ());
        }
        Assert.assertFalse(clonedOtherOptionalField.getChecked());

        // noinspection SimplifiableJUnitAssertion
        Assert.assertTrue(otherOptionalField.equals(clonedOtherOptionalField));
    }
}