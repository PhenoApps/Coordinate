package org.wheatgenetics.coordinate.optionalField;

/**
 * Uses:
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 * androidx.annotation.StringRes
 *
 * org.junit.Assert
 * org.junit.Test
 *
 * org.wheatgenetics.androidlibrary.Utils
 *
 * org.wheatgenetics.coordinate.R
 * org.wheatgenetics.coordinate.StringGetter
 *
 * org.wheatgenetics.coordinate.optionalField.BaseOptionalField
 * org.wheatgenetics.coordinate.optionalField.DateOptionalField
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"}) public class DateOptionalFieldTest
extends java.lang.Object implements org.wheatgenetics.coordinate.StringGetter
{
    @androidx.annotation.NonNull private static java.lang.String expectedCurrentDate()
    {
        return org.wheatgenetics.androidlibrary.Utils.formatDate(
            java.lang.System.currentTimeMillis()).toString();
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

    @org.junit.Test() public void constructorSucceeds()
    {
        final org.wheatgenetics.coordinate.optionalField.DateOptionalField dateOptionalField =
            new org.wheatgenetics.coordinate.optionalField.DateOptionalField(this);
        org.junit.Assert.assertEquals("Date", dateOptionalField.getName());
        org.junit.Assert.assertEquals(
            org.wheatgenetics.coordinate.optionalField.BaseOptionalField.DATE_HINT,
            dateOptionalField.getHint()                                           );
    }

    // region Overridden Method Tests
    @org.junit.Test() public void cloneSucceeds()
    {
        final org.wheatgenetics.coordinate.optionalField.DateOptionalField dateOptionalField =
            new org.wheatgenetics.coordinate.optionalField.DateOptionalField(this);
        final org.wheatgenetics.coordinate.optionalField.DateOptionalField clonedDateOptionalField =
            (org.wheatgenetics.coordinate.optionalField.DateOptionalField)
                    dateOptionalField.clone();
        org.junit.Assert.assertEquals("Date", clonedDateOptionalField.getName());
        org.junit.Assert.assertEquals(
            org.wheatgenetics.coordinate.optionalField.BaseOptionalField.DATE_HINT,
            clonedDateOptionalField.getHint()                                     );

        // noinspection SimplifiableJUnitAssertion
        org.junit.Assert.assertTrue(dateOptionalField.equals(clonedDateOptionalField));
    }

    @org.junit.Test() public void getValue()
    {
        org.junit.Assert.assertEquals(
            org.wheatgenetics.coordinate.optionalField.DateOptionalFieldTest.expectedCurrentDate(),
            new org.wheatgenetics.coordinate.optionalField.DateOptionalField(
                this).getValue());
    }
    // endregion

    @org.junit.Test() public void getCurrentDate()
    {
        org.junit.Assert.assertEquals(
            org.wheatgenetics.coordinate.optionalField.DateOptionalFieldTest.expectedCurrentDate(),
            org.wheatgenetics.coordinate.optionalField.DateOptionalField.getCurrentDate()         );
    }
}