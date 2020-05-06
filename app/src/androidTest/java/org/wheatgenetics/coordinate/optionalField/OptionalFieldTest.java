package org.wheatgenetics.coordinate.optionalField;

import android.content.res.Resources;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.PluralsRes;
import androidx.annotation.StringRes;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
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
 * org.json.JSONObject
 *
 * org.junit.Assert
 * org.junit.Test
 *
 * org.wheatgenetics.coordinate.R
 * org.wheatgenetics.coordinate.StringGetter
 *
 * org.wheatgenetics.coordinate.optionalField.BaseOptionalField
 * org.wheatgenetics.coordinate.optionalField.OptionalField
 */
@SuppressWarnings({"ClassExplicitlyExtendsObject"}) public class OptionalFieldTest
extends Object implements StringGetter
{
    /**
     * This class was defined in order to test OptionalField.  Why not just test OptionalField
     * directly?  Because OptionalField is abstract.  Why does that matter?  Because I can't
     * instantiate an abstract class.  If I can't instantiate it I can't test it.
     */
    private static class ConcreteOptionalField extends
    OptionalField
    {
        private ConcreteOptionalField(@SuppressWarnings({"SameParameterValue"})
        @NonNull final String                          name        ,
                                     final String                          hint        ,
        @NonNull final StringGetter stringGetter)
        { super(name, hint, stringGetter); }

        private ConcreteOptionalField(
        @NonNull final JSONObject                       jsonObject  ,
        @NonNull final StringGetter stringGetter)
        { super(jsonObject, stringGetter); }
    }

    // region org.wheatgenetics.coordinate.StringGetter Overridden Methods
    @Override @Nullable public String get(
    @StringRes final int resId)
    {
        switch (resId)
        {
            case R.string.BaseOptionalFieldPersonFieldName:
                return "Person";

            case R.string.BaseOptionalFieldNameFieldName:
                return "Name";

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
    @Test(expected = NullPointerException.class)
    public void constructorNullParameterFails()
    {
        // noinspection ConstantConditions
        new OptionalFieldTest.ConcreteOptionalField(null,this);
    }

    @Test(expected = AssertionError.class)
    public void constructorEmptyParameterFails()
    {
        // noinspection ConstantConditions
        new OptionalFieldTest.ConcreteOptionalField(
            OptionalField.makeJSONObject(
                null,null,null,this),this);
    }

    @Test(expected = AssertionError.class)
    public void constructorNullNameParameterFails()
    {
        // noinspection ConstantConditions
        new OptionalFieldTest.ConcreteOptionalField(
            OptionalField.makeJSONObject(
                null,"testValue",null,this),this);
    }

    @Test() public void constructorNameParameterIsEqual()
    {
        final OptionalFieldTest.ConcreteOptionalField
            concreteOptionalField;
        {
            final String testName = "testName";
            concreteOptionalField = new
                OptionalFieldTest.ConcreteOptionalField(
                    OptionalField.makeJSONObject(
                        testName,null,null,this),this);
            Assert.assertEquals(testName, concreteOptionalField.getName());
        }
        Assert.assertEquals("", concreteOptionalField.getValue());
        Assert.assertEquals("", concreteOptionalField.getHint ());
    }

    @Test() public void constructorHintParameterIsEqual()
    {
        final String testHint = "testHint";
        final OptionalFieldTest.ConcreteOptionalField
            concreteOptionalField;
        {
            final String testName = "testName";
            concreteOptionalField = new
                OptionalFieldTest.ConcreteOptionalField(
                    OptionalField.makeJSONObject(
                        testName,null, testHint,this),this);
            Assert.assertEquals(testName, concreteOptionalField.getName());
        }
        Assert.assertEquals("", concreteOptionalField.getValue());
        Assert.assertEquals(testHint, concreteOptionalField.getHint());
    }

    @Test() public void constructorValueParameterIsEqual()
    {
        final OptionalFieldTest.ConcreteOptionalField
            concreteOptionalField;
        {
            final String testValue = "testValue";
            {
                final String testName = "testName";
                concreteOptionalField = new OptionalFieldTest.ConcreteOptionalField(
                        OptionalField.makeJSONObject(
                            testName, testValue,null,this),this);
                Assert.assertEquals(testName, concreteOptionalField.getName());
            }
            Assert.assertEquals(testValue, concreteOptionalField.getValue());
        }
        Assert.assertEquals("", concreteOptionalField.getHint());
    }

    @Test() public void constructorCheckedParameterIsTrue()
    {
        final OptionalFieldTest.ConcreteOptionalField
            concreteOptionalField;
        {
            final String testName = "testName";
            concreteOptionalField = new
                OptionalFieldTest.ConcreteOptionalField(
                    OptionalField.makeJSONObject(
                        testName,null,null,true,this),this);
            Assert.assertEquals(testName, concreteOptionalField.getName());
        }
        Assert.assertTrue(concreteOptionalField.getChecked());
    }

    @Test() public void constructorCheckedParameterIsFalse()
    {
        final OptionalFieldTest.ConcreteOptionalField
            concreteOptionalField;
        {
            final String testName = "testName";
            concreteOptionalField = new
                OptionalFieldTest.ConcreteOptionalField(
                    OptionalField.makeJSONObject(
                        testName,null,null,false,this),
                    this);
            Assert.assertEquals(testName, concreteOptionalField.getName());
        }
        Assert.assertFalse(concreteOptionalField.getChecked());
    }
    // endregion

    // region makeJSONObject() Test
    @Test() public void makeJSONObject()
    {
        final OptionalFieldTest.ConcreteOptionalField
            concreteOptionalField;
        final String jsonObjectAsString;
        {
            final JSONObject jsonObject;
            {
                final String testName = "testName",
                    testHint = null, testValue = "testValue";

                // noinspection ConstantConditions
                concreteOptionalField = new OptionalFieldTest.ConcreteOptionalField(testName, testHint,this);
                concreteOptionalField.setValue(testValue);

                // noinspection ConstantConditions
                jsonObject =
                    OptionalField.makeJSONObject(
                        testName, testValue, testHint,this);
            }
            OptionalField.putChecked(
                jsonObject,true,this);
            jsonObjectAsString = jsonObject.toString();
        }

        Assert.assertEquals(jsonObjectAsString,
            concreteOptionalField.makeJSONObject(this).toString());

        concreteOptionalField.setChecked(false);
        Assert.assertNotEquals(jsonObjectAsString,
            concreteOptionalField.makeJSONObject(this).toString());
    }

    @Test() public void identificationMakeJSONObject()
    {
        final OptionalFieldTest.ConcreteOptionalField
            concreteOptionalField;
        final String jsonObjectAsString;
        {
            final JSONObject jsonObject;
            {
                final String testName = BaseOptionalField.identificationFieldName(this),
                    testHint = null, testValue = "testValue";

                // noinspection ConstantConditions
                concreteOptionalField = new OptionalFieldTest.ConcreteOptionalField(testName, testHint,this);
                concreteOptionalField.setValue(testValue);

                // noinspection ConstantConditions
                jsonObject =
                    OptionalField.makeJSONObject(
                        testName, testValue, testHint,this);
            }
            OptionalField.putChecked(
                jsonObject,false,this);         // Should change false into true.
            jsonObjectAsString = jsonObject.toString();
        }

        Assert.assertEquals(jsonObjectAsString,
            concreteOptionalField.makeJSONObject(this).toString());
    }
    // endregion
}