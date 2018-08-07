package org.wheatgenetics.coordinate.optionalField;

/**
 * Uses:
 * org.junit.Assert
 * org.junit.Test
 *
 * org.wheatgenetics.coordinate.optionalField.CheckedOptionalFields
 * org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields
 *
 * org.wheatgenetics.coordinate.optionalField.NonNullOptionalFieldsTest
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
public class CheckedOptionalFieldsTest extends java.lang.Object
{
    // region Constructor Tests
    @org.junit.Test() public void nullConstructorParameterSucceeds()
    {
        final org.wheatgenetics.coordinate.optionalField.CheckedOptionalFields
            checkedOptionalFields =
                new org.wheatgenetics.coordinate.optionalField.CheckedOptionalFields(null);
        org.junit.Assert.assertEquals(0, checkedOptionalFields.size());
    }

    @org.junit.Test() public void constructorSucceeds()
    {
        org.junit.Assert.assertEquals(2, new org.wheatgenetics.coordinate.optionalField
            .CheckedOptionalFields(org.wheatgenetics.coordinate.optionalField
                .NonNullOptionalFieldsTest.makeNonNullOptionalFields()).size());
    }
    // endregion

    @org.junit.Test() public void iteratorWorks()
    {
        final org.wheatgenetics.coordinate.optionalField.CheckedOptionalFields
            checkedOptionalFields;
        {
            final org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields
                nonNullOptionalFields = org.wheatgenetics.coordinate.optionalField
                    .NonNullOptionalFieldsTest.makeNonNullOptionalFields();
            nonNullOptionalFields.get(0).setChecked(false);
            checkedOptionalFields =
                new org.wheatgenetics.coordinate.optionalField.CheckedOptionalFields(
                    nonNullOptionalFields);
        }
        org.junit.Assert.assertEquals(1, checkedOptionalFields.size());
    }
}