package org.wheatgenetics.coordinate.optionalField;

/**
 * Uses:
 * org.junit.Assert
 * org.junit.Test
 *
 * org.wheatgenetics.coordinate.optionalField.BaseOptionalField
 *
 * org.wheatgenetics.coordinate.optionalField.DateOptionalField
 */
@java.lang.SuppressWarnings("ClassExplicitlyExtendsObject")
public class DateOptionalFieldTest extends java.lang.Object
{
    @org.junit.Test
    public void constructorSucceeds()
    {
        final org.wheatgenetics.coordinate.optionalField.DateOptionalField dateOptionalField =
            new org.wheatgenetics.coordinate.optionalField.DateOptionalField();
        org.junit.Assert.assertEquals(dateOptionalField.getName(), "Date");
        org.junit.Assert.assertEquals(dateOptionalField.getHint(),
            org.wheatgenetics.coordinate.optionalField.BaseOptionalField.DATE_HINT);
    }
}