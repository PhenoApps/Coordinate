package org.wheatgenetics.coordinate.optionalField;

/**
 * Uses:
 * org.json.JSONObject
 *
 * org.junit.Test
 *
 * org.wheatgenetics.coordinate.optionalField.BaseOptionalField
 * org.wheatgenetics.coordinate.optionalField.OptionalField
 * org.wheatgenetics.coordinate.optionalField.OtherOptionalField
 * org.wheatgenetics.coordinate.optionalField.OtherOptionalField.WrongClass
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
public class OtherOptionalFieldTest extends java.lang.Object
{
    @org.junit.Test(
        expected = org.wheatgenetics.coordinate.optionalField.OtherOptionalField.WrongClass.class)
    public void constructorFails()
    throws org.wheatgenetics.coordinate.optionalField.OtherOptionalField.WrongClass
    {
        final org.json.JSONObject jsonObject =
            org.wheatgenetics.coordinate.optionalField.OptionalField.makeJSONObject("testName",
                null, org.wheatgenetics.coordinate.optionalField.BaseOptionalField.DATE_HINT);
        new org.wheatgenetics.coordinate.optionalField.OtherOptionalField(jsonObject);
    }

    @org.junit.Test public void constructorSucceeds()
    throws org.wheatgenetics.coordinate.optionalField.OtherOptionalField.WrongClass
    {
        final org.json.JSONObject jsonObject =
            org.wheatgenetics.coordinate.optionalField.OptionalField.makeJSONObject(
                "testName", null, "testHint");
        new org.wheatgenetics.coordinate.optionalField.OtherOptionalField(jsonObject);
    }
}