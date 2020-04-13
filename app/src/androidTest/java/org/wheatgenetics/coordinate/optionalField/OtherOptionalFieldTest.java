package org.wheatgenetics.coordinate.optionalField;

/**
 * Uses:
 * org.junit.Assert
 * org.junit.Test
 *
 * androidx.annotation.Nullable
 * androidx.annotation.StringRes
 *
 * org.wheatgenetics.coordinate.R
 * org.wheatgenetics.coordinate.StringGetter
 *
 * org.wheatgenetics.coordinate.optionalField.BaseOptionalField
 * org.wheatgenetics.coordinate.optionalField.OptionalField
 * org.wheatgenetics.coordinate.optionalField.OtherOptionalField
 * org.wheatgenetics.coordinate.optionalField.OtherOptionalField.WrongClass
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"}) public class OtherOptionalFieldTest
extends java.lang.Object implements org.wheatgenetics.coordinate.StringGetter
{
    // region org.wheatgenetics.coordinate.StringGetter Overridden Method
    @java.lang.Override @androidx.annotation.Nullable public java.lang.String get(
    @androidx.annotation.StringRes final int resId)
    {
        // noinspection SwitchStatementWithTooFewBranches
        switch (resId)
        {
            case org.wheatgenetics.coordinate.R.string.BaseOptionalFieldIdentificationFieldName:
                return "Identification";

            default: org.junit.Assert.fail(); return null;
        }
    }
    // endregion

    // region Constructor Tests
    @org.junit.Test(
        expected = org.wheatgenetics.coordinate.optionalField.OtherOptionalField.WrongClass.class)
    public void constructorFails()
    throws org.wheatgenetics.coordinate.optionalField.OtherOptionalField.WrongClass
    {
        new org.wheatgenetics.coordinate.optionalField.OtherOptionalField(
            org.wheatgenetics.coordinate.optionalField.OptionalField.makeJSONObject(
                "testName",null,
                org.wheatgenetics.coordinate.optionalField.BaseOptionalField.DATE_HINT,
                this),this);
    }

    @org.junit.Test() public void constructorSucceeds()
    throws org.wheatgenetics.coordinate.optionalField.OtherOptionalField.WrongClass
    {
        new org.wheatgenetics.coordinate.optionalField.OtherOptionalField(
            org.wheatgenetics.coordinate.optionalField.OptionalField.makeJSONObject(
                "testName",null,"testHint",this),this);
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
                testName, testValue, testHint,this);
            otherOptionalField.setChecked(false);

            clonedOtherOptionalField =
                (org.wheatgenetics.coordinate.optionalField.OtherOptionalField)
                    otherOptionalField.clone();

            org.junit.Assert.assertEquals(testName , clonedOtherOptionalField.getName ());
            org.junit.Assert.assertEquals(testValue, clonedOtherOptionalField.getValue());
            org.junit.Assert.assertEquals(testHint , clonedOtherOptionalField.getHint ());
        }
        org.junit.Assert.assertFalse(clonedOtherOptionalField.getChecked());

        // noinspection SimplifiableJUnitAssertion
        org.junit.Assert.assertTrue(otherOptionalField.equals(clonedOtherOptionalField));
    }
}