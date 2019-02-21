package org.wheatgenetics.coordinate.optionalField;

/**
 * Uses:
 * android.support.annotation.NonNull
 *
 * org.junit.Assert
 * org.junit.Test
 *
 * org.wheatgenetics.androidlibrary.Utils
 *
 * org.wheatgenetics.coordinate.optionalField.BaseOptionalField
 * org.wheatgenetics.coordinate.optionalField.DateOptionalField
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
public class DateOptionalFieldTest extends java.lang.Object
{
    @android.support.annotation.NonNull private static java.lang.String expectedCurrentDate()
    {
        return org.wheatgenetics.androidlibrary.Utils.formatDate(
            java.lang.System.currentTimeMillis()).toString();
    }

    @org.junit.Test() public void constructorSucceeds()
    {
        final org.wheatgenetics.coordinate.optionalField.DateOptionalField dateOptionalField =
            new org.wheatgenetics.coordinate.optionalField.DateOptionalField();
        org.junit.Assert.assertEquals("Date", dateOptionalField.getName());
        org.junit.Assert.assertEquals(
            org.wheatgenetics.coordinate.optionalField.BaseOptionalField.DATE_HINT,
            dateOptionalField.getHint()                                           );
    }

    // region Overridden Method Tests
    @org.junit.Test() public void cloneSucceeds()
    {
        final org.wheatgenetics.coordinate.optionalField.DateOptionalField dateOptionalField =
            new org.wheatgenetics.coordinate.optionalField.DateOptionalField();
        final org.wheatgenetics.coordinate.optionalField.DateOptionalField
            clonedBaseDateOptionalField =
                (org.wheatgenetics.coordinate.optionalField.DateOptionalField)
                    dateOptionalField.clone();
        org.junit.Assert.assertEquals("Date", clonedBaseDateOptionalField.getName());
        org.junit.Assert.assertEquals(
            org.wheatgenetics.coordinate.optionalField.BaseOptionalField.DATE_HINT,
            clonedBaseDateOptionalField.getHint()                                 );

        // noinspection SimplifiableJUnitAssertion
        org.junit.Assert.assertTrue(dateOptionalField.equals(clonedBaseDateOptionalField));
    }

    @org.junit.Test() public void getValue()
    {
        org.junit.Assert.assertEquals(
            org.wheatgenetics.coordinate.optionalField.DateOptionalFieldTest.expectedCurrentDate(),
            new org.wheatgenetics.coordinate.optionalField.DateOptionalField().getValue()         );
    }
    // endregion

    @org.junit.Test() public void getCurrentDate()
    {
        org.junit.Assert.assertEquals(
            org.wheatgenetics.coordinate.optionalField.DateOptionalFieldTest.expectedCurrentDate(),
            org.wheatgenetics.coordinate.optionalField.DateOptionalField.getCurrentDate()         );
    }
}