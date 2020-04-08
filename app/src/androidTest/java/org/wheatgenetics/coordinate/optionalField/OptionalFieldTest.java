package org.wheatgenetics.coordinate.optionalField;

/**
 * Uses:
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
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
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"}) public class OptionalFieldTest
extends java.lang.Object implements org.wheatgenetics.coordinate.StringGetter
{
    /**
     * This class was defined in order to test OptionalField.  Why not just test OptionalField
     * directly?  Because OptionalField is abstract.  Why does that matter?  Because I can't
     * instantiate an abstract class.  If I can't instantiate it I can't test it.
     */
    private static class ConcreteOptionalField extends
    org.wheatgenetics.coordinate.optionalField.OptionalField
    {
        private ConcreteOptionalField(@java.lang.SuppressWarnings({"SameParameterValue"})
        @androidx.annotation.NonNull final java.lang.String                          name        ,
                                     final java.lang.String                          hint        ,
        @androidx.annotation.NonNull final org.wheatgenetics.coordinate.StringGetter stringGetter)
        { super(name, hint, stringGetter); }

        private ConcreteOptionalField(
        @androidx.annotation.NonNull final org.json.JSONObject                       jsonObject  ,
        @androidx.annotation.NonNull final org.wheatgenetics.coordinate.StringGetter stringGetter)
        { super(jsonObject, stringGetter); }
    }

    // region org.wheatgenetics.coordinate.StringGetter Overridden Method
    @java.lang.Override @androidx.annotation.Nullable public java.lang.String get(
    @androidx.annotation.StringRes final int resId)
    {
        switch (resId)
        {
            case org.wheatgenetics.coordinate.R.string.BaseOptionalFieldPersonFieldName:
                return "Person";

            case org.wheatgenetics.coordinate.R.string.BaseOptionalFieldNameFieldName:
                return "Name";

            case org.wheatgenetics.coordinate.R.string.BaseOptionalFieldIdentificationFieldName:
                return "Identification";

            default: return null;
        }
    }
    // endregion

    // region Constructor Tests
    @org.junit.Test(expected = java.lang.NullPointerException.class)
    public void constructorNullParameterFails()
    {
        // noinspection ConstantConditions
        new org.wheatgenetics.coordinate.optionalField
            .OptionalFieldTest.ConcreteOptionalField(null,this);
    }

    @org.junit.Test(expected = java.lang.AssertionError.class)
    public void constructorEmptyParameterFails()
    {
        // noinspection ConstantConditions
        new org.wheatgenetics.coordinate.optionalField.OptionalFieldTest.ConcreteOptionalField(
            org.wheatgenetics.coordinate.optionalField.OptionalField.makeJSONObject(
                null,null,null,this),this);
    }

    @org.junit.Test(expected = java.lang.AssertionError.class)
    public void constructorNullNameParameterFails()
    {
        // noinspection ConstantConditions
        new org.wheatgenetics.coordinate.optionalField.OptionalFieldTest.ConcreteOptionalField(
            org.wheatgenetics.coordinate.optionalField.OptionalField.makeJSONObject(
                null,"testValue",null,this),this);
    }

    @org.junit.Test() public void constructorNameParameterIsEqual()
    {
        final org.wheatgenetics.coordinate.optionalField.OptionalFieldTest.ConcreteOptionalField
            concreteOptionalField;
        {
            final java.lang.String testName = "testName";
            concreteOptionalField = new
                org.wheatgenetics.coordinate.optionalField.OptionalFieldTest.ConcreteOptionalField(
                    org.wheatgenetics.coordinate.optionalField.OptionalField.makeJSONObject(
                        testName,null,null,this),this);
            org.junit.Assert.assertEquals(testName, concreteOptionalField.getName());
        }
        org.junit.Assert.assertEquals("", concreteOptionalField.getValue());
        org.junit.Assert.assertEquals("", concreteOptionalField.getHint ());
    }

    @org.junit.Test() public void constructorHintParameterIsEqual()
    {
        final java.lang.String testHint = "testHint";
        final org.wheatgenetics.coordinate.optionalField.OptionalFieldTest.ConcreteOptionalField
            concreteOptionalField;
        {
            final java.lang.String testName = "testName";
            concreteOptionalField = new
                org.wheatgenetics.coordinate.optionalField.OptionalFieldTest.ConcreteOptionalField(
                    org.wheatgenetics.coordinate.optionalField.OptionalField.makeJSONObject(
                        testName,null, testHint,this),this);
            org.junit.Assert.assertEquals(testName, concreteOptionalField.getName());
        }
        org.junit.Assert.assertEquals("", concreteOptionalField.getValue());
        org.junit.Assert.assertEquals(testHint, concreteOptionalField.getHint());
    }

    @org.junit.Test() public void constructorValueParameterIsEqual()
    {
        final org.wheatgenetics.coordinate.optionalField.OptionalFieldTest.ConcreteOptionalField
            concreteOptionalField;
        {
            final java.lang.String testValue = "testValue";
            {
                final java.lang.String testName = "testName";
                concreteOptionalField = new org.wheatgenetics.coordinate
                    .optionalField.OptionalFieldTest.ConcreteOptionalField(
                        org.wheatgenetics.coordinate.optionalField.OptionalField.makeJSONObject(
                            testName, testValue,null,this),this);
                org.junit.Assert.assertEquals(testName, concreteOptionalField.getName());
            }
            org.junit.Assert.assertEquals(testValue, concreteOptionalField.getValue());
        }
        org.junit.Assert.assertEquals("", concreteOptionalField.getHint());
    }

    @org.junit.Test() public void constructorCheckedParameterIsTrue()
    {
        final org.wheatgenetics.coordinate.optionalField.OptionalFieldTest.ConcreteOptionalField
            concreteOptionalField;
        {
            final java.lang.String testName = "testName";
            concreteOptionalField = new
                org.wheatgenetics.coordinate.optionalField.OptionalFieldTest.ConcreteOptionalField(
                    org.wheatgenetics.coordinate.optionalField.OptionalField.makeJSONObject(
                        testName,null,null,true,this),this);
            org.junit.Assert.assertEquals(testName, concreteOptionalField.getName());
        }
        org.junit.Assert.assertTrue(concreteOptionalField.getChecked());
    }

    @org.junit.Test() public void constructorCheckedParameterIsFalse()
    {
        final org.wheatgenetics.coordinate.optionalField.OptionalFieldTest.ConcreteOptionalField
            concreteOptionalField;
        {
            final java.lang.String testName = "testName";
            concreteOptionalField = new
                org.wheatgenetics.coordinate.optionalField.OptionalFieldTest.ConcreteOptionalField(
                    org.wheatgenetics.coordinate.optionalField.OptionalField.makeJSONObject(
                        testName,null,null,false,this),
                    this);
            org.junit.Assert.assertEquals(testName, concreteOptionalField.getName());
        }
        org.junit.Assert.assertFalse(concreteOptionalField.getChecked());
    }
    // endregion

    // region makeJSONObject() Test
    @org.junit.Test() public void makeJSONObject()
    {
        final org.wheatgenetics.coordinate.optionalField.OptionalFieldTest.ConcreteOptionalField
            concreteOptionalField;
        final java.lang.String jsonObjectAsString;
        {
            final org.json.JSONObject jsonObject;
            {
                final java.lang.String testName = "testName",
                    testHint = null, testValue = "testValue";

                // noinspection ConstantConditions
                concreteOptionalField = new org.wheatgenetics.coordinate.optionalField
                    .OptionalFieldTest.ConcreteOptionalField(testName, testHint,this);
                concreteOptionalField.setValue(testValue);

                // noinspection ConstantConditions
                jsonObject =
                    org.wheatgenetics.coordinate.optionalField.OptionalField.makeJSONObject(
                        testName, testValue, testHint,this);
            }
            org.wheatgenetics.coordinate.optionalField.OptionalField.putChecked(
                jsonObject,true,this);
            jsonObjectAsString = jsonObject.toString();
        }

        org.junit.Assert.assertEquals(jsonObjectAsString,
            concreteOptionalField.makeJSONObject(this).toString());

        concreteOptionalField.setChecked(false);
        org.junit.Assert.assertNotEquals(jsonObjectAsString,
            concreteOptionalField.makeJSONObject(this).toString());
    }

    @org.junit.Test() public void identificationMakeJSONObject()
    {
        final org.wheatgenetics.coordinate.optionalField.OptionalFieldTest.ConcreteOptionalField
            concreteOptionalField;
        final java.lang.String jsonObjectAsString;
        {
            final org.json.JSONObject jsonObject;
            {
                final java.lang.String testName = org.wheatgenetics.coordinate.optionalField
                        .BaseOptionalField.identificationFieldName(this),
                    testHint = null, testValue = "testValue";

                // noinspection ConstantConditions
                concreteOptionalField = new org.wheatgenetics.coordinate.optionalField
                    .OptionalFieldTest.ConcreteOptionalField(testName, testHint,this);
                concreteOptionalField.setValue(testValue);

                // noinspection ConstantConditions
                jsonObject =
                    org.wheatgenetics.coordinate.optionalField.OptionalField.makeJSONObject(
                        testName, testValue, testHint,this);
            }
            org.wheatgenetics.coordinate.optionalField.OptionalField.putChecked(
                jsonObject,false,this);         // Should change false into true.
            jsonObjectAsString = jsonObject.toString();
        }

        org.junit.Assert.assertEquals(jsonObjectAsString,
            concreteOptionalField.makeJSONObject(this).toString());
    }
    // endregion
}