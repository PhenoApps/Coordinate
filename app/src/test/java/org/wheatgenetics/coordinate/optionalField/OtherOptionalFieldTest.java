package org.wheatgenetics.coordinate.optionalField;

/**
 * Uses:
 * org.junit.Assert
 * org.junit.Test
 *
 * org.wheatgenetics.coordinate.optionalField.OtherOptionalField
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
public class OtherOptionalFieldTest extends java.lang.Object
{
    @org.junit.Test public void cloneSucceeds()
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

            org.junit.Assert.assertEquals(testName , clonedOtherOptionalField.getName ());
            org.junit.Assert.assertEquals(testValue, clonedOtherOptionalField.getValue());
            org.junit.Assert.assertEquals(testHint , clonedOtherOptionalField.getHint ());
        }
        org.junit.Assert.assertFalse(clonedOtherOptionalField.getChecked()              );
        org.junit.Assert.assertTrue (otherOptionalField.equals(clonedOtherOptionalField));
    }
}