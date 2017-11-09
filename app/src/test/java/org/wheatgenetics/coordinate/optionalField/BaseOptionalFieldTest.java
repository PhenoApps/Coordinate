package org.wheatgenetics.coordinate.optionalField;

/**
 * Uses:
 * org.junit.Assert
 * org.junit.Test
 *
 * org.wheatgenetics.coordinate.optionalField.BaseOptionalField
 */
@java.lang.SuppressWarnings("ClassExplicitlyExtendsObject")
public class BaseOptionalFieldTest extends java.lang.Object
{
    /**
     * This class was defined in order to test BaseOptionalField.  Why not just test
     * BaseOptionalField directly?  Because BaseOptionalField is abstract.  Why does that matter?
     * Because I can't instantiate an abstract class.  If I can't instantiate it I can't test it.
     */
    private class ConcreteOptionalField extends
    org.wheatgenetics.coordinate.optionalField.BaseOptionalField
    {
        private ConcreteOptionalField(final java.lang.String name, final java.lang.String hint)
        { super(name, hint); }

        private ConcreteOptionalField(final java.lang.String name) { super(name); }
    }

    // region Constructor Tests
    // region First Constructor Tests
    // region First Constructor Name Parameter Tests
    @org.junit.Test(expected = java.lang.NullPointerException.class)
    public void firstConstructorNullNameParameterFails()
    {
        new org.wheatgenetics.coordinate.optionalField.BaseOptionalFieldTest.ConcreteOptionalField(
            null, null);
    }

    @org.junit.Test(expected = java.lang.AssertionError.class)
    public void firstConstructorEmptyNameParameterFails()
    {
        new org.wheatgenetics.coordinate.optionalField.BaseOptionalFieldTest.ConcreteOptionalField(
            "", null);
    }

    @org.junit.Test(expected = java.lang.AssertionError.class)
    public void firstConstructorSpacesNameParameterFails()
    {
        new org.wheatgenetics.coordinate.optionalField.BaseOptionalFieldTest.ConcreteOptionalField(
            "  ", null);
    }

    @org.junit.Test
    public void firstConstructorTestNameParameterIsEqual()
    {
        final java.lang.String testName = "testName";
        final org.wheatgenetics.coordinate.optionalField.BaseOptionalFieldTest.ConcreteOptionalField
            concreteOptionalField = new org.wheatgenetics.coordinate.optionalField
                .BaseOptionalFieldTest.ConcreteOptionalField(testName, "testHint");
        org.junit.Assert.assertEquals(concreteOptionalField.getName(), testName);
    }
    // endregion

    // region First Constructor Hint Parameter Tests
    @org.junit.Test
    public void firstConstructorNullHintParameterIsConverted()
    {
        final org.wheatgenetics.coordinate.optionalField.BaseOptionalFieldTest.ConcreteOptionalField
            concreteOptionalField = new org.wheatgenetics.coordinate.optionalField
                .BaseOptionalFieldTest.ConcreteOptionalField("testName", null);
        org.junit.Assert.assertEquals(concreteOptionalField.getHint(), "");
    }

    @org.junit.Test
    public void firstConstructorEmptyHintParameterIsEmpty()
    {
        final java.lang.String emptyHint = "";
        final org.wheatgenetics.coordinate.optionalField.BaseOptionalFieldTest.ConcreteOptionalField
            concreteOptionalField = new org.wheatgenetics.coordinate.optionalField
                .BaseOptionalFieldTest.ConcreteOptionalField("testName", emptyHint);
        org.junit.Assert.assertEquals(concreteOptionalField.getHint(), emptyHint);
    }

    @org.junit.Test
    public void firstConstructorSpacesHintParameterIsEmpty()
    {
        final org.wheatgenetics.coordinate.optionalField.BaseOptionalFieldTest.ConcreteOptionalField
            concreteOptionalField = new org.wheatgenetics.coordinate.optionalField
                .BaseOptionalFieldTest.ConcreteOptionalField("testName", "  ");
        org.junit.Assert.assertEquals(concreteOptionalField.getHint(), "");
    }

    @org.junit.Test
    public void firstConstructorTestHintParameterIsEqual()
    {
        final java.lang.String testHint = "testHint";
        final org.wheatgenetics.coordinate.optionalField.BaseOptionalFieldTest.ConcreteOptionalField
            concreteOptionalField = new org.wheatgenetics.coordinate.optionalField
                .BaseOptionalFieldTest.ConcreteOptionalField("testName", testHint);
        org.junit.Assert.assertEquals(concreteOptionalField.getHint(), testHint);
    }
    // endregion
    // endregion

    // region Second Constructor Tests
    // region Second Constructor Name Parameter Tests
    @org.junit.Test(expected = java.lang.NullPointerException.class)
    public void secondConstructorNullNameParameterFails()
    {
        new org.wheatgenetics.coordinate.optionalField.BaseOptionalFieldTest.ConcreteOptionalField(
            null);
    }

    @org.junit.Test(expected = java.lang.AssertionError.class)
    public void secondConstructorEmptyNameParameterFails()
    {
        new org.wheatgenetics.coordinate.optionalField.BaseOptionalFieldTest.ConcreteOptionalField(
            "");
    }

    @org.junit.Test(expected = java.lang.AssertionError.class)
    public void secondConstructorSpacesNameParameterFails()
    {
        new org.wheatgenetics.coordinate.optionalField.BaseOptionalFieldTest.ConcreteOptionalField(
            "  ");
    }

    @org.junit.Test
    public void secondConstructorTestNameParameterIsEqual()
    {
        final java.lang.String testName = "testName";
        final org.wheatgenetics.coordinate.optionalField.BaseOptionalFieldTest.ConcreteOptionalField
            concreteOptionalField = new org.wheatgenetics.coordinate.optionalField
                .BaseOptionalFieldTest.ConcreteOptionalField(testName);
        org.junit.Assert.assertEquals(concreteOptionalField.getName(), testName);
    }
    // endregion

    // region Second Constructor Hint Parameter Test
    @org.junit.Test
    public void secondConstructorHintIsEmpty()
    {
        final org.wheatgenetics.coordinate.optionalField.BaseOptionalFieldTest.ConcreteOptionalField
            concreteOptionalField = new org.wheatgenetics.coordinate.optionalField
                .BaseOptionalFieldTest.ConcreteOptionalField("testName");
        org.junit.Assert.assertEquals(concreteOptionalField.getHint(), "");
    }
    // endregion
    // endregion
    // endregion

    // region Overridden Method Tests
    @org.junit.Test
    public void toStringIsEqual()
    {
        final java.lang.String testName = "testName";
        final org.wheatgenetics.coordinate.optionalField.BaseOptionalFieldTest.ConcreteOptionalField
            concreteOptionalField = new org.wheatgenetics.coordinate.optionalField
                .BaseOptionalFieldTest.ConcreteOptionalField(testName);
        org.junit.Assert.assertEquals(concreteOptionalField.toString(), testName);
    }

    @org.junit.Test
    public void equalsFails()
    {
        final org.wheatgenetics.coordinate.optionalField.BaseOptionalFieldTest.ConcreteOptionalField
            firstConcreteOptionalField = new org.wheatgenetics.coordinate.optionalField
                .BaseOptionalFieldTest.ConcreteOptionalField("first");
        org.junit.Assert.assertFalse(firstConcreteOptionalField.equals(null        ));
        org.junit.Assert.assertFalse(firstConcreteOptionalField.equals("any string"));

        final org.wheatgenetics.coordinate.optionalField.BaseOptionalFieldTest.ConcreteOptionalField
            secondConcreteOptionalField = new org.wheatgenetics.coordinate.optionalField
                .BaseOptionalFieldTest.ConcreteOptionalField("second");
        org.junit.Assert.assertFalse(
            firstConcreteOptionalField.equals(secondConcreteOptionalField));
    }

    @org.junit.Test
    public void equalsSucceeds()
    {
        final java.lang.String testName = "testName";
        final org.wheatgenetics.coordinate.optionalField.BaseOptionalFieldTest.ConcreteOptionalField
            firstConcreteOptionalField = new org.wheatgenetics.coordinate.optionalField
                .BaseOptionalFieldTest.ConcreteOptionalField(testName);
        org.junit.Assert.assertTrue(firstConcreteOptionalField.equals(firstConcreteOptionalField));

        final org.wheatgenetics.coordinate.optionalField.BaseOptionalFieldTest.ConcreteOptionalField
            secondConcreteOptionalField = new org.wheatgenetics.coordinate.optionalField
                .BaseOptionalFieldTest.ConcreteOptionalField(testName);
        org.junit.Assert.assertTrue(firstConcreteOptionalField.equals(secondConcreteOptionalField));
    }

    @org.junit.Test(expected = java.lang.CloneNotSupportedException.class)
    public void cloneFails() throws java.lang.CloneNotSupportedException
    {
        new org.wheatgenetics.coordinate.optionalField.BaseOptionalFieldTest.ConcreteOptionalField(
            "testName").clone();
    }
    // endregion

    // region Package Method Tests
    @org.junit.Test
    public void namesAreEqualSucceedsAndFails()
    {
        final java.lang.String testName = "testName";
        final org.wheatgenetics.coordinate.optionalField.BaseOptionalFieldTest.ConcreteOptionalField
            concreteOptionalField = new org.wheatgenetics.coordinate.optionalField
                .BaseOptionalFieldTest.ConcreteOptionalField(testName);
        org.junit.Assert.assertTrue (concreteOptionalField.namesAreEqual(testName    ));
        org.junit.Assert.assertTrue (concreteOptionalField.namesAreEqual("testName"  ));
        org.junit.Assert.assertTrue (concreteOptionalField.namesAreEqual("TestName"  ));
        org.junit.Assert.assertFalse(concreteOptionalField.namesAreEqual("any string"));
        org.junit.Assert.assertFalse(concreteOptionalField.namesAreEqual(""          ));
        org.junit.Assert.assertFalse(concreteOptionalField.namesAreEqual("  "        ));
        org.junit.Assert.assertFalse(concreteOptionalField.namesAreEqual(null        ));
    }

    @org.junit.Test
    public void getSafeValueWhenNameIsNotAPerson()
    {
        final org.wheatgenetics.coordinate.optionalField.BaseOptionalFieldTest.ConcreteOptionalField
            concreteOptionalField = new org.wheatgenetics.coordinate.optionalField
                .BaseOptionalFieldTest.ConcreteOptionalField("testName");
        final java.lang.String testValue = "test value";
        concreteOptionalField.setValue(testValue);
        org.junit.Assert.assertEquals(concreteOptionalField.getSafeValue(), testValue);

        final java.lang.String safeTestValue = testValue.replace(" ", "_");
        org.junit.Assert.assertNotEquals(concreteOptionalField.getSafeValue(), safeTestValue);
    }

    @org.junit.Test
    public void getSafeValueWhenNameIsAPerson()
    {
        final org.wheatgenetics.coordinate.optionalField.BaseOptionalFieldTest.ConcreteOptionalField
            concreteOptionalField = new org.wheatgenetics.coordinate.optionalField
                .BaseOptionalFieldTest.ConcreteOptionalField("Person");
        final java.lang.String testValue = "test value";
        concreteOptionalField.setValue(testValue);
        org.junit.Assert.assertNotEquals(concreteOptionalField.getSafeValue(), testValue);

        final java.lang.String safeTestValue = testValue.replace(" ", "_");
        org.junit.Assert.assertEquals(concreteOptionalField.getSafeValue(), safeTestValue);
    }

    @org.junit.Test
    public void getCheckedSucceedsAndFails()
    {
        final org.wheatgenetics.coordinate.optionalField.BaseOptionalFieldTest.ConcreteOptionalField
            concreteOptionalField = new org.wheatgenetics.coordinate.optionalField
                .BaseOptionalFieldTest.ConcreteOptionalField("testName");

        concreteOptionalField.setChecked(true);
        org.junit.Assert.assertTrue(concreteOptionalField.getChecked());

        concreteOptionalField.setChecked(false);
        org.junit.Assert.assertFalse(concreteOptionalField.getChecked());
    }
    // endregion

    // region Public Method Tests
    @org.junit.Test
    public void getValueIsAndIsNotEqual()
    {
        final org.wheatgenetics.coordinate.optionalField.BaseOptionalFieldTest.ConcreteOptionalField
            concreteOptionalField = new org.wheatgenetics.coordinate.optionalField
                .BaseOptionalFieldTest.ConcreteOptionalField("testName");
        {
            final java.lang.String testVaue = "testValue";
            concreteOptionalField.setValue(testVaue);
            org.junit.Assert.assertEquals(concreteOptionalField.getValue(), testVaue);
        }
        org.junit.Assert.assertNotEquals(concreteOptionalField.getValue(), "any string");
    }

    @org.junit.Test
    public void setValueIsAndIsNotEqual()
    {
        final org.wheatgenetics.coordinate.optionalField.BaseOptionalFieldTest.ConcreteOptionalField
            concreteOptionalField = new org.wheatgenetics.coordinate.optionalField
                .BaseOptionalFieldTest.ConcreteOptionalField("testName");
        {
            final java.lang.String testValue = "testValue";
            concreteOptionalField.setValue(testValue);
            org.junit.Assert.assertEquals(concreteOptionalField.getValue(), testValue);
        }
        org.junit.Assert.assertNotEquals(concreteOptionalField.getValue(), "any string");
        org.junit.Assert.assertNotEquals(concreteOptionalField.getValue(), null        );

        concreteOptionalField.setValue(null);
        org.junit.Assert.assertEquals(concreteOptionalField.getValue(), "");

        concreteOptionalField.setValue("  ");
        org.junit.Assert.assertEquals(concreteOptionalField.getValue(), "");

        concreteOptionalField.setValue(" abc ");
        org.junit.Assert.assertEquals(concreteOptionalField.getValue(), "abc");
    }

    @org.junit.Test
    public void isAPersonSucceedsAndFails()
    {
        org.wheatgenetics.coordinate.optionalField.BaseOptionalFieldTest.ConcreteOptionalField
            concreteOptionalField = new org.wheatgenetics.coordinate.optionalField
                .BaseOptionalFieldTest.ConcreteOptionalField("Person");
        org.junit.Assert.assertTrue(concreteOptionalField.isAPerson());

        concreteOptionalField = new
            org.wheatgenetics.coordinate.optionalField.BaseOptionalFieldTest.ConcreteOptionalField(
                "person");
        org.junit.Assert.assertTrue(concreteOptionalField.isAPerson());

        concreteOptionalField = new
            org.wheatgenetics.coordinate.optionalField.BaseOptionalFieldTest.ConcreteOptionalField(
                "pERSON");
        org.junit.Assert.assertTrue(concreteOptionalField.isAPerson());

        concreteOptionalField = new
            org.wheatgenetics.coordinate.optionalField.BaseOptionalFieldTest.ConcreteOptionalField(
                "Name");
        org.junit.Assert.assertTrue(concreteOptionalField.isAPerson());

        concreteOptionalField = new
            org.wheatgenetics.coordinate.optionalField.BaseOptionalFieldTest.ConcreteOptionalField(
                "nAME");
        org.junit.Assert.assertTrue(concreteOptionalField.isAPerson());

        concreteOptionalField = new
            org.wheatgenetics.coordinate.optionalField.BaseOptionalFieldTest.ConcreteOptionalField(
                "testName");
        org.junit.Assert.assertFalse(concreteOptionalField.isAPerson());
    }
    // endregion
}