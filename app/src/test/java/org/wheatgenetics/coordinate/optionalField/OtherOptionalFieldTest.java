package org.wheatgenetics.coordinate.optionalField;

/**
 * Uses:
 * org.junit.Assert
 * org.junit.Test
 *
 * org.wheatgenetics.coordinate.optionalField.OtherOptionalField
 */
@java.lang.SuppressWarnings("ClassExplicitlyExtendsObject")
public class OtherOptionalFieldTest extends java.lang.Object
{
    @org.junit.Test
    public void cloneSucceeds()
    {
        final java.lang.String testName = "testName",
            testValue = "testValue", testHint = "testHint";
        final org.wheatgenetics.coordinate.optionalField.OtherOptionalField otherOptionalField =
            new org.wheatgenetics.coordinate.optionalField.OtherOptionalField(
                testName, testValue, testHint);
        otherOptionalField.setChecked(false);

        final org.wheatgenetics.coordinate.optionalField.OtherOptionalField
            clonedBaseOtherOptionalField =
                (org.wheatgenetics.coordinate.optionalField.OtherOptionalField)
                    otherOptionalField.clone();
        
        org.junit.Assert.assertEquals(clonedBaseOtherOptionalField.getName (), testName );
        org.junit.Assert.assertEquals(clonedBaseOtherOptionalField.getValue(), testValue);
        org.junit.Assert.assertEquals(clonedBaseOtherOptionalField.getHint (), testHint );
        org.junit.Assert.assertFalse (clonedBaseOtherOptionalField.getChecked()         );
        org.junit.Assert.assertTrue  (otherOptionalField.equals(clonedBaseOtherOptionalField));
    }
}