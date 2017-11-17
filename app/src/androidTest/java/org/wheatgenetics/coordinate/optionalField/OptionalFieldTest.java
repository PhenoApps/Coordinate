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
@java.lang.SuppressWarnings("ClassExplicitlyExtendsObject")
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

    static org.json.JSONObject makeJSONObject(final java.lang.String name,
    final java.lang.String value, final java.lang.String hint)
    {
        final org.json.JSONObject result = new org.json.JSONObject();

        org.wheatgenetics.coordinate.optionalField.OptionalField.put(result,
            org.wheatgenetics.coordinate.optionalField.OptionalField.NAME_JSON_NAME, name);
        org.wheatgenetics.coordinate.optionalField.OptionalField.put(result,
            org.wheatgenetics.coordinate.optionalField.OptionalField.VALUE_JSON_NAME, value);
        org.wheatgenetics.coordinate.optionalField.OptionalField.put(result,
            org.wheatgenetics.coordinate.optionalField.OptionalField.HINT_JSON_NAME, hint);

        return result;
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
            org.wheatgenetics.coordinate.optionalField.OptionalFieldTest.makeJSONObject(
                null, null, null));
    }

    @org.junit.Test(expected = java.lang.AssertionError.class)
    public void constructorNullNameParameter()
    {
        new org.wheatgenetics.coordinate.optionalField.OptionalFieldTest.ConcreteOptionalField(
            org.wheatgenetics.coordinate.optionalField.OptionalFieldTest.makeJSONObject(
                null, "testValue", null));
    }

    @org.junit.Test
    public void constructorNameParameterIsEqual()
    {
        final java.lang.String testName = "testName";
        final org.wheatgenetics.coordinate.optionalField.OptionalFieldTest.ConcreteOptionalField
            concreteOptionalField = new
                org.wheatgenetics.coordinate.optionalField.OptionalFieldTest.ConcreteOptionalField(
                    org.wheatgenetics.coordinate.optionalField.OptionalFieldTest.makeJSONObject(
                        testName, null, null));
        org.junit.Assert.assertEquals(concreteOptionalField.getName (), testName);
        org.junit.Assert.assertEquals(concreteOptionalField.getValue(), ""      );
        org.junit.Assert.assertEquals(concreteOptionalField.getHint (), ""      );
    }

    @org.junit.Test
    public void constructorHintParameterIsEqual()
    {
        final java.lang.String testName = "testName", testHint = "testHint";
        final org.wheatgenetics.coordinate.optionalField.OptionalFieldTest.ConcreteOptionalField
            concreteOptionalField = new
                org.wheatgenetics.coordinate.optionalField.OptionalFieldTest.ConcreteOptionalField(
                    org.wheatgenetics.coordinate.optionalField.OptionalFieldTest.makeJSONObject(
                        testName, null, testHint));
        org.junit.Assert.assertEquals(concreteOptionalField.getName (), testName);
        org.junit.Assert.assertEquals(concreteOptionalField.getValue(), ""      );
        org.junit.Assert.assertEquals(concreteOptionalField.getHint (), testHint);
    }

    @org.junit.Test
    public void constructorValueParameterIsEqual()
    {
        final java.lang.String testName = "testName", testValue = "testValue";
        final org.wheatgenetics.coordinate.optionalField.OptionalFieldTest.ConcreteOptionalField
            concreteOptionalField = new
                org.wheatgenetics.coordinate.optionalField.OptionalFieldTest.ConcreteOptionalField(
                    org.wheatgenetics.coordinate.optionalField.OptionalFieldTest.makeJSONObject(
                        testName, testValue, null));
        org.junit.Assert.assertEquals(concreteOptionalField.getName (), testName );
        org.junit.Assert.assertEquals(concreteOptionalField.getValue(), testValue);
        org.junit.Assert.assertEquals(concreteOptionalField.getHint (), ""       );
    }

    @org.junit.Test
    public void constructorCheckedParameterIsTrue()
    {
        final java.lang.String    testName   = "testName";
        final org.json.JSONObject jsonObject =
            org.wheatgenetics.coordinate.optionalField.OptionalFieldTest.makeJSONObject(
                testName, null, null);
        org.wheatgenetics.coordinate.optionalField.OptionalField.putChecked(jsonObject, true);

        final org.wheatgenetics.coordinate.optionalField.OptionalFieldTest.ConcreteOptionalField
            concreteOptionalField = new
                org.wheatgenetics.coordinate.optionalField.OptionalFieldTest.ConcreteOptionalField(
                    jsonObject);

        org.junit.Assert.assertEquals(concreteOptionalField.getName(), testName);
        org.junit.Assert.assertTrue  (concreteOptionalField.getChecked()       );
    }

    @org.junit.Test
    public void constructorCheckedParameterIsFalse()
    {
        final java.lang.String    testName   = "testName";
        final org.json.JSONObject jsonObject =
            org.wheatgenetics.coordinate.optionalField.OptionalFieldTest.makeJSONObject(
                testName, null, null);
        org.wheatgenetics.coordinate.optionalField.OptionalField.putChecked(jsonObject, false);

        final org.wheatgenetics.coordinate.optionalField.OptionalFieldTest.ConcreteOptionalField
            concreteOptionalField = new
                org.wheatgenetics.coordinate.optionalField.OptionalFieldTest.ConcreteOptionalField(
                    jsonObject);

        org.junit.Assert.assertEquals(concreteOptionalField.getName(), testName);
        org.junit.Assert.assertFalse (concreteOptionalField.getChecked()       );
    }
    // endregion

    // region makeJSONObject() Test
    @org.junit.Test
    public void makeJSONObject()
    {
        org.wheatgenetics.coordinate.optionalField.OptionalFieldTest.ConcreteOptionalField
            concreteOptionalField;
        java.lang.String jsonObjectAsString;
        {
            final java.lang.String testName = "testName", testValue = "testValue";
            concreteOptionalField = new
                org.wheatgenetics.coordinate.optionalField.OptionalFieldTest.ConcreteOptionalField(
                    testName, null);
            concreteOptionalField.setValue(testValue);

            final org.json.JSONObject jsonObject =
                org.wheatgenetics.coordinate.optionalField.OptionalFieldTest.makeJSONObject(
                    testName, testValue, null);
            org.wheatgenetics.coordinate.optionalField.OptionalField.putChecked(jsonObject, true);
            jsonObjectAsString = jsonObject.toString();
        }

        org.junit.Assert.assertEquals(concreteOptionalField.makeJSONObject().toString(),
            jsonObjectAsString);

        concreteOptionalField.setChecked(false);
        org.junit.Assert.assertNotEquals(concreteOptionalField.makeJSONObject().toString(),
            jsonObjectAsString);
    }
    // endregion
}