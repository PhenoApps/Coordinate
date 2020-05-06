package org.wheatgenetics.coordinate.optionalField;

import org.junit.Assert;
import org.junit.Test;

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
@SuppressWarnings({"ClassExplicitlyExtendsObject"})
public class CheckedOptionalFieldsTest extends Object
{
    // region Constructor Tests
    @Test() public void constructorNullParameterSucceeds()
    {
        Assert.assertEquals(0,
            new CheckedOptionalFields(null).size());
    }

    @Test() public void constructorSucceeds()
    {
        Assert.assertEquals(2, new CheckedOptionalFields(NonNullOptionalFieldsTest.makeNonNullOptionalFields()).size());
    }
    // endregion

    @Test() public void iteratorWorks()
    {
        final CheckedOptionalFields
            checkedOptionalFields;
        {
            final NonNullOptionalFields
                nonNullOptionalFields = NonNullOptionalFieldsTest.makeNonNullOptionalFields();
            nonNullOptionalFields.get(0).setChecked(false);
            checkedOptionalFields =
                new CheckedOptionalFields(
                    nonNullOptionalFields);
        }
        Assert.assertEquals(1, checkedOptionalFields.size());
    }
}