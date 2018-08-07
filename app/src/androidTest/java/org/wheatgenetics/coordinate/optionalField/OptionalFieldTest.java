package org.wheatgenetics.coordinate.optionalField;

/**
 * Uses:
 * org.json.JSONObject
 *
 * org.junit.Assert
 * org.junit.Test
 *
 * org.wheatgenetics.coordinate.optionalField.OptionalField
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
public class OptionalFieldTest extends java.lang.Object
{
    /**
     * This class was defined in order to test OptionalField.  Why not just test OptionalField
     * directly?  Because OptionalField is abstract.  Why does that matter?  Because I can't
     * instantiate an abstract class.  If I can't instantiate it I can't test it.
     */
    private class ConcreteOptionalField extends
    org.wheatgenetics.coordinate.optionalField.OptionalField
    {
        private ConcreteOptionalField(final java.lang.String name, final java.lang.String hint)
        { super(name, hint); }

        private ConcreteOptionalField(final org.json.JSONObject jsonObject) { super(jsonObject); }
    }

    // region Constructor Tests
    @org.junit.Test(expected = java.lang.NullPointerException.class)
    public void constructorNullParameterFails()
    {
        new org.wheatgenetics.coordinate.optionalField
            .OptionalFieldTest.ConcreteOptionalField(null);
    }

    @org.junit.Test(expected = java.lang.AssertionError.class)
    public void constructorEmptyParameterFails()
    {
        new org.wheatgenetics.coordinate.optionalField.OptionalFieldTest.ConcreteOptionalField(
            org.wheatgenetics.coordinate.optionalField.OptionalField.makeJSONObject(
                null,null,null));
    }

    @org.junit.Test(expected = java.lang.AssertionError.class)
    public void constructorNullNameParameterFails()
    {
        new org.wheatgenetics.coordinate.optionalField.OptionalFieldTest.ConcreteOptionalField(
            org.wheatgenetics.coordinate.optionalField.OptionalField.makeJSONObject(
                null,"testValue",null));
    }

    @org.junit.Test() public void constructorNameParameterIsEqual()
    {
        final java.lang.String testName = "testName";
        final org.wheatgenetics.coordinate.optionalField.OptionalFieldTest.ConcreteOptionalField
            concreteOptionalField = new
                org.wheatgenetics.coordinate.optionalField.OptionalFieldTest.ConcreteOptionalField(
                    org.wheatgenetics.coordinate.optionalField.OptionalField.makeJSONObject(
                        testName,null,null));
        org.junit.Assert.assertTrue(concreteOptionalField.getName ().equals(testName));
        org.junit.Assert.assertTrue(concreteOptionalField.getValue().equals(""      ));
        org.junit.Assert.assertTrue(concreteOptionalField.getHint ().equals(""      ));
    }

    @org.junit.Test() public void constructorHintParameterIsEqual()
    {
        final java.lang.String testName = "testName", testHint = "testHint";
        final org.wheatgenetics.coordinate.optionalField.OptionalFieldTest.ConcreteOptionalField
            concreteOptionalField = new
                org.wheatgenetics.coordinate.optionalField.OptionalFieldTest.ConcreteOptionalField(
                    org.wheatgenetics.coordinate.optionalField.OptionalField.makeJSONObject(
                        testName,null, testHint));
        org.junit.Assert.assertTrue(concreteOptionalField.getName ().equals(testName));
        org.junit.Assert.assertTrue(concreteOptionalField.getValue().equals(""      ));
        org.junit.Assert.assertTrue(concreteOptionalField.getHint ().equals(testHint));
    }

    @org.junit.Test() public void constructorValueParameterIsEqual()
    {
        final java.lang.String testName = "testName", testValue = "testValue";
        final org.wheatgenetics.coordinate.optionalField.OptionalFieldTest.ConcreteOptionalField
            concreteOptionalField = new
                org.wheatgenetics.coordinate.optionalField.OptionalFieldTest.ConcreteOptionalField(
                    org.wheatgenetics.coordinate.optionalField.OptionalField.makeJSONObject(
                        testName, testValue,null));
        org.junit.Assert.assertTrue(concreteOptionalField.getName ().equals(testName ));
        org.junit.Assert.assertTrue(concreteOptionalField.getValue().equals(testValue));
        org.junit.Assert.assertTrue(concreteOptionalField.getHint ().equals(""       ));
    }

    @org.junit.Test() public void constructorCheckedParameterIsTrue()
    {
        final java.lang.String testName = "testName";
        final org.wheatgenetics.coordinate.optionalField.OptionalFieldTest.ConcreteOptionalField
            concreteOptionalField = new
                org.wheatgenetics.coordinate.optionalField.OptionalFieldTest.ConcreteOptionalField(
                    org.wheatgenetics.coordinate.optionalField.OptionalField.makeJSONObject(
                        testName,null,null,true));
        org.junit.Assert.assertTrue(concreteOptionalField.getName().equals(testName));
        org.junit.Assert.assertTrue(concreteOptionalField.getChecked()              );
    }

    @org.junit.Test() public void constructorCheckedParameterIsFalse()
    {
        final java.lang.String testName = "testName";
        final org.wheatgenetics.coordinate.optionalField.OptionalFieldTest.ConcreteOptionalField
            concreteOptionalField = new
                org.wheatgenetics.coordinate.optionalField.OptionalFieldTest.ConcreteOptionalField(
                    org.wheatgenetics.coordinate.optionalField.OptionalField.makeJSONObject(
                        testName,null,null,false));
        org.junit.Assert.assertTrue (concreteOptionalField.getName().equals(testName));
        org.junit.Assert.assertFalse(concreteOptionalField.getChecked()              );
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
                concreteOptionalField = new org.wheatgenetics.coordinate.optionalField
                    .OptionalFieldTest.ConcreteOptionalField(testName, testHint);
                concreteOptionalField.setValue(testValue);

                jsonObject =
                    org.wheatgenetics.coordinate.optionalField.OptionalField.makeJSONObject(
                        testName, testValue, testHint);
            }
            org.wheatgenetics.coordinate.optionalField.OptionalField.putChecked(
                jsonObject,true);
            jsonObjectAsString = jsonObject.toString();
        }

        org.junit.Assert.assertTrue(jsonObjectAsString.equals(
            concreteOptionalField.makeJSONObject().toString()));

        concreteOptionalField.setChecked(false);
        org.junit.Assert.assertFalse(jsonObjectAsString.equals(
            concreteOptionalField.makeJSONObject().toString()));
    }
    // endregion
}