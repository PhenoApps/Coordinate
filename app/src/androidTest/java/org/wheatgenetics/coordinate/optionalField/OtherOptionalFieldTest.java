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
    // region Constructor Tests
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

    @org.junit.Test() public void constructorSucceeds()
    throws org.wheatgenetics.coordinate.optionalField.OtherOptionalField.WrongClass
    {
        final org.json.JSONObject jsonObject =
            org.wheatgenetics.coordinate.optionalField.OptionalField.makeJSONObject(
                "testName", null, "testHint");
        new org.wheatgenetics.coordinate.optionalField.OtherOptionalField(jsonObject);
    }
    // endregion

    @org.junit.Test() public void cloneSucceeds()
    {
        final org.wheatgenetics.coordinate.optionalField.OtherOptionalField
            otherOptionalField, clonedOtherOptionalField;
        {
            final java.lang.String testName = "testName",
                testValue = "testValue", testHint = "testHint";
            otherOptionalField = new org.wheatgenetics.coordinate.optionalField.OtherOptionalField(
                testName, testValue, testHint);
            otherOptionalField.setChecked(false);

            clonedOtherOptionalField =
                (org.wheatgenetics.coordinate.optionalField.OtherOptionalField)
                    otherOptionalField.clone();

            org.junit.Assert.assertTrue(testName.equals (clonedOtherOptionalField.getName ()));
            org.junit.Assert.assertTrue(testValue.equals(clonedOtherOptionalField.getValue()));
            org.junit.Assert.assertTrue(testHint.equals (clonedOtherOptionalField.getHint ()));
        }
        org.junit.Assert.assertFalse(clonedOtherOptionalField.getChecked()              );
        org.junit.Assert.assertTrue (otherOptionalField.equals(clonedOtherOptionalField));
    }
}