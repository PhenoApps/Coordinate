package org.wheatgenetics.coordinate.optionalField;

/**
 * Uses:
 * androidx.annotation.NonNull
 * androidx.annotation.Size
 * androidx.annotation.StringRes
 *
 * org.junit.Assert
 * org.junit.Test
 *
 * org.wheatgenetics.coordinate.R
 * org.wheatgenetics.coordinate.StringGetter
 *
 * org.wheatgenetics.coordinate.optionalField.BaseOptionalField
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"}) public class BaseOptionalFieldTest
extends java.lang.Object implements org.wheatgenetics.coordinate.StringGetter
{
    /**
     * This class was defined in order to test BaseOptionalField.  Why not just test
     * BaseOptionalField directly?  Because BaseOptionalField is abstract.  Why does that matter?
     * Because I can't instantiate an abstract class.  If I can't instantiate it I can't test it.
     */
    private static class ConcreteBaseOptionalField extends
    org.wheatgenetics.coordinate.optionalField.BaseOptionalField
    {
        private ConcreteBaseOptionalField(@androidx.annotation.NonNull
        @androidx.annotation.Size(min = 1) final java.lang.String name, final java.lang.String hint,
        @androidx.annotation.NonNull final org.wheatgenetics.coordinate.StringGetter stringGetter)
        { super(name, hint, stringGetter); }

        private ConcreteBaseOptionalField(@androidx.annotation.NonNull
        @androidx.annotation.Size(min = 1) final java.lang.String name,
        @androidx.annotation.NonNull final org.wheatgenetics.coordinate.StringGetter stringGetter)
        { super(name, stringGetter); }
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

            default: org.junit.Assert.fail(); return null;
        }
    }
    // endregion

    // region Constructor Tests
    // region First Constructor Tests
    // region First Constructor Name Parameter Tests
    @org.junit.Test(expected = java.lang.AssertionError.class)
    public void firstConstructorNullNameParameterFails()
    {
        // noinspection ConstantConditions
        new org.wheatgenetics.coordinate.optionalField.BaseOptionalFieldTest
            .ConcreteBaseOptionalField(null,null,this);
    }

    @org.junit.Test(expected = java.lang.AssertionError.class)
    public void firstConstructorEmptyNameParameterFails()
    {
        new org.wheatgenetics.coordinate.optionalField.BaseOptionalFieldTest
            .ConcreteBaseOptionalField("",null,this);
    }

    @org.junit.Test(expected = java.lang.AssertionError.class)
    public void firstConstructorSpacesNameParameterFails()
    {
        new org.wheatgenetics.coordinate.optionalField.BaseOptionalFieldTest
            .ConcreteBaseOptionalField("  ",null,this);
    }

    @org.junit.Test() public void firstConstructorTestNameParameterIsEqual()
    {
        final java.lang.String testName = "testName";
        final org.wheatgenetics.coordinate.optionalField.BaseOptionalFieldTest
            .ConcreteBaseOptionalField concreteBaseOptionalField = new org.wheatgenetics.coordinate
                .optionalField.BaseOptionalFieldTest.ConcreteBaseOptionalField(
                    testName,"testHint",this);

        org.junit.Assert.assertEquals(testName, concreteBaseOptionalField.getName());
    }
    // endregion

    // region First Constructor Hint Parameter Tests
    @org.junit.Test() public void firstConstructorNullHintParameterIsConverted()
    {
        final org.wheatgenetics.coordinate.optionalField.BaseOptionalFieldTest
            .ConcreteBaseOptionalField concreteBaseOptionalField = new org.wheatgenetics.coordinate
                .optionalField.BaseOptionalFieldTest.ConcreteBaseOptionalField(
                    "testName",null,this);
        org.junit.Assert.assertEquals("", concreteBaseOptionalField.getHint());
    }

    @org.junit.Test() public void firstConstructorEmptyHintParameterIsEmpty()
    {
        final java.lang.String emptyHint = "";
        final org.wheatgenetics.coordinate.optionalField.BaseOptionalFieldTest
            .ConcreteBaseOptionalField concreteBaseOptionalField = new org.wheatgenetics.coordinate
                .optionalField.BaseOptionalFieldTest.ConcreteBaseOptionalField(
                    "testName", emptyHint,this);
        org.junit.Assert.assertEquals(emptyHint, concreteBaseOptionalField.getHint());
    }

    @org.junit.Test() public void firstConstructorSpacesHintParameterIsEmpty()
    {
        final org.wheatgenetics.coordinate.optionalField.BaseOptionalFieldTest
            .ConcreteBaseOptionalField concreteBaseOptionalField = new org.wheatgenetics.coordinate
                .optionalField.BaseOptionalFieldTest.ConcreteBaseOptionalField(
                    "testName","  ",this);
        org.junit.Assert.assertEquals("", concreteBaseOptionalField.getHint());
    }

    @org.junit.Test() public void firstConstructorTestHintParameterIsEqual()
    {
        final java.lang.String testHint = "testHint";
        final org.wheatgenetics.coordinate.optionalField.BaseOptionalFieldTest
            .ConcreteBaseOptionalField concreteBaseOptionalField = new org.wheatgenetics.coordinate
                .optionalField.BaseOptionalFieldTest.ConcreteBaseOptionalField(
                    "testName", testHint,this);
        org.junit.Assert.assertEquals(testHint, concreteBaseOptionalField.getHint());
    }
    // endregion
    // endregion

    // region Second Constructor Tests
    // region Second Constructor Name Parameter Tests
    @org.junit.Test(expected = java.lang.AssertionError.class)
    public void secondConstructorNullNameParameterFails()
    {
        // noinspection ConstantConditions
        new org.wheatgenetics.coordinate.optionalField.BaseOptionalFieldTest
            .ConcreteBaseOptionalField(null,this);
    }

    @org.junit.Test(expected = java.lang.AssertionError.class)
    public void secondConstructorEmptyNameParameterFails()
    {
        new org.wheatgenetics.coordinate.optionalField.BaseOptionalFieldTest
            .ConcreteBaseOptionalField("",this);
    }

    @org.junit.Test(expected = java.lang.AssertionError.class)
    public void secondConstructorSpacesNameParameterFails()
    {
        new org.wheatgenetics.coordinate.optionalField.BaseOptionalFieldTest
            .ConcreteBaseOptionalField("  ",this);
    }

    @org.junit.Test() public void secondConstructorTestNameParameterIsEqual()
    {
        final java.lang.String testName = "testName";
        final org.wheatgenetics.coordinate.optionalField.BaseOptionalFieldTest
            .ConcreteBaseOptionalField concreteBaseOptionalField = new org.wheatgenetics.coordinate
                .optionalField.BaseOptionalFieldTest.ConcreteBaseOptionalField(
                    testName,this);
        org.junit.Assert.assertEquals(testName, concreteBaseOptionalField.getName());
    }
    // endregion

    // region Second Constructor Hint Parameter Test
    @org.junit.Test() public void secondConstructorHintIsEmpty()
    {
        final org.wheatgenetics.coordinate.optionalField.BaseOptionalFieldTest
            .ConcreteBaseOptionalField concreteBaseOptionalField = new org.wheatgenetics.coordinate
                .optionalField.BaseOptionalFieldTest.ConcreteBaseOptionalField(
                    "testName",this);
        org.junit.Assert.assertEquals("", concreteBaseOptionalField.getHint());
    }
    // endregion
    // endregion
    // endregion

    // region Overridden Method Tests
    @org.junit.Test() public void toStringIsEqual()
    {
        final java.lang.String testName = "testName";
        final org.wheatgenetics.coordinate.optionalField.BaseOptionalFieldTest
            .ConcreteBaseOptionalField concreteBaseOptionalField = new org.wheatgenetics.coordinate
                .optionalField.BaseOptionalFieldTest.ConcreteBaseOptionalField(
                    testName,this);
        org.junit.Assert.assertEquals(testName, concreteBaseOptionalField.toString());
    }

    @org.junit.Test() public void equalsFails()
    {
        final org.wheatgenetics.coordinate.optionalField.BaseOptionalFieldTest
            .ConcreteBaseOptionalField firstConcreteBaseOptionalField = new org.wheatgenetics
                .coordinate.optionalField.BaseOptionalFieldTest.ConcreteBaseOptionalField(
                    "first",this);

        // noinspection ConstantConditions, SimplifiableJUnitAssertion
        org.junit.Assert.assertFalse(firstConcreteBaseOptionalField.equals(null));

        // noinspection EqualsBetweenInconvertibleTypes, SimplifiableJUnitAssertion
        org.junit.Assert.assertFalse(firstConcreteBaseOptionalField.equals("any string"));

        final org.wheatgenetics.coordinate.optionalField.BaseOptionalFieldTest
            .ConcreteBaseOptionalField secondConcreteBaseOptionalField = new org.wheatgenetics
                .coordinate.optionalField.BaseOptionalFieldTest.ConcreteBaseOptionalField(
                    "second",this);

        // noinspection SimplifiableJUnitAssertion
        org.junit.Assert.assertFalse(
            firstConcreteBaseOptionalField.equals(secondConcreteBaseOptionalField));
    }

    @org.junit.Test() public void equalsSucceeds()
    {
        final java.lang.String testName = "testName";
        final org.wheatgenetics.coordinate.optionalField.BaseOptionalFieldTest
            .ConcreteBaseOptionalField firstConcreteBaseOptionalField = new org.wheatgenetics
                .coordinate.optionalField.BaseOptionalFieldTest.ConcreteBaseOptionalField(
                    testName,this);

        // noinspection EqualsWithItself, SimplifiableJUnitAssertion
        org.junit.Assert.assertTrue(firstConcreteBaseOptionalField.equals(
            firstConcreteBaseOptionalField));

        final org.wheatgenetics.coordinate.optionalField.BaseOptionalFieldTest
            .ConcreteBaseOptionalField secondConcreteBaseOptionalField = new org.wheatgenetics
                .coordinate.optionalField.BaseOptionalFieldTest.ConcreteBaseOptionalField(
                    testName,this);

        // noinspection SimplifiableJUnitAssertion
        org.junit.Assert.assertTrue(firstConcreteBaseOptionalField.equals(
            secondConcreteBaseOptionalField));
    }

    @org.junit.Test(expected = java.lang.CloneNotSupportedException.class)
    public void cloneFails() throws java.lang.CloneNotSupportedException
    {
        new org.wheatgenetics.coordinate.optionalField.BaseOptionalFieldTest
            .ConcreteBaseOptionalField("testName",this).clone();
    }
    // endregion

    // region Package Method Tests
    @org.junit.Test() public void namesAreEqualWorks()
    {
        final java.lang.String                                                testName = "testName";
        final org.wheatgenetics.coordinate.optionalField.BaseOptionalFieldTest
            .ConcreteBaseOptionalField concreteBaseOptionalField = new org.wheatgenetics.coordinate
                .optionalField.BaseOptionalFieldTest.ConcreteBaseOptionalField(
                    testName,this);
        org.junit.Assert.assertTrue (concreteBaseOptionalField.namesAreEqual(testName    ));
        org.junit.Assert.assertTrue (concreteBaseOptionalField.namesAreEqual("testName"  ));
        org.junit.Assert.assertTrue (concreteBaseOptionalField.namesAreEqual("TestName"  ));
        org.junit.Assert.assertFalse(concreteBaseOptionalField.namesAreEqual("any string"));
        org.junit.Assert.assertFalse(concreteBaseOptionalField.namesAreEqual(""          ));
        org.junit.Assert.assertFalse(concreteBaseOptionalField.namesAreEqual("  "        ));
        org.junit.Assert.assertFalse(concreteBaseOptionalField.namesAreEqual(null        ));
    }

    @org.junit.Test() public void getSafeValueWhenNameIsNotAPerson()
    {
        final org.wheatgenetics.coordinate.optionalField.BaseOptionalFieldTest
            .ConcreteBaseOptionalField concreteBaseOptionalField = new org.wheatgenetics.coordinate
                .optionalField.BaseOptionalFieldTest.ConcreteBaseOptionalField(
                    "testName",this);

        final java.lang.String safeTestValue;
        {
            final java.lang.String testValue = "test value";
            concreteBaseOptionalField.setValue(testValue);
            org.junit.Assert.assertEquals(testValue, concreteBaseOptionalField.getSafeValue());

            safeTestValue = testValue.replace(" ","_");
        }
        org.junit.Assert.assertNotEquals(safeTestValue, concreteBaseOptionalField.getSafeValue());
    }

    @org.junit.Test() public void getSafeValueWhenNameIsAPerson()
    {
        final org.wheatgenetics.coordinate.optionalField.BaseOptionalFieldTest
            .ConcreteBaseOptionalField concreteBaseOptionalField = new org.wheatgenetics.coordinate
                .optionalField.BaseOptionalFieldTest.ConcreteBaseOptionalField(
                    /* name => */"Person",this);

        final java.lang.String safeTestValue;
        {
            final java.lang.String testValue = "test value";
            concreteBaseOptionalField.setValue(testValue);
            org.junit.Assert.assertNotEquals(testValue, concreteBaseOptionalField.getSafeValue());

            safeTestValue = testValue.replace(" ","_");
        }
        org.junit.Assert.assertEquals(safeTestValue, concreteBaseOptionalField.getSafeValue());
    }

    @org.junit.Test() public void getCheckedWorks()
    {
        final org.wheatgenetics.coordinate.optionalField.BaseOptionalFieldTest
            .ConcreteBaseOptionalField concreteBaseOptionalField = new org.wheatgenetics.coordinate
                .optionalField.BaseOptionalFieldTest.ConcreteBaseOptionalField(
                    "testName",this);
        org.junit.Assert.assertTrue(concreteBaseOptionalField.getChecked());

        concreteBaseOptionalField.setChecked(false);
        org.junit.Assert.assertFalse(concreteBaseOptionalField.getChecked());

        concreteBaseOptionalField.setChecked(true);
        org.junit.Assert.assertTrue(concreteBaseOptionalField.getChecked());
    }
    // endregion

    // region Public Method Tests
    @org.junit.Test() public void getValueIsAndIsNotEqual()
    {
        final org.wheatgenetics.coordinate.optionalField.BaseOptionalFieldTest
            .ConcreteBaseOptionalField concreteBaseOptionalField = new org.wheatgenetics.coordinate
                .optionalField.BaseOptionalFieldTest.ConcreteBaseOptionalField(
                    "testName",this);
        {
            final java.lang.String testValue = "testValue";
            concreteBaseOptionalField.setValue(testValue);
            org.junit.Assert.assertEquals(testValue, concreteBaseOptionalField.getValue());
        }
        org.junit.Assert.assertNotEquals(
            "any string", concreteBaseOptionalField.getValue());
    }

    @org.junit.Test() public void setValueIsAndIsNotEqual()
    {
        final org.wheatgenetics.coordinate.optionalField.BaseOptionalFieldTest
            .ConcreteBaseOptionalField concreteBaseOptionalField = new org.wheatgenetics.coordinate
                .optionalField.BaseOptionalFieldTest.ConcreteBaseOptionalField(
                    "testName",this);
        {
            final java.lang.String testValue = "testValue";
            concreteBaseOptionalField.setValue(testValue);
            org.junit.Assert.assertEquals(testValue, concreteBaseOptionalField.getValue());
        }
        org.junit.Assert.assertNotEquals(
            "any string", concreteBaseOptionalField.getValue());

        org.junit.Assert.assertNotEquals(null, concreteBaseOptionalField.getValue());

        concreteBaseOptionalField.setValue(null);
        org.junit.Assert.assertEquals("", concreteBaseOptionalField.getValue());

        concreteBaseOptionalField.setValue("  ");
        org.junit.Assert.assertEquals("", concreteBaseOptionalField.getValue());

        concreteBaseOptionalField.setValue(" abc ");
        org.junit.Assert.assertEquals("abc", concreteBaseOptionalField.getValue());
    }

    @org.junit.Test() public void getUnspecifiedHint()
    {
        org.junit.Assert.assertEquals("", new org.wheatgenetics.coordinate.optionalField
            .BaseOptionalFieldTest.ConcreteBaseOptionalField(
                "name",this).getHint());
    }

    @org.junit.Test() public void getSpecifiedHint()
    {
        final java.lang.String hint = "hint";
        org.junit.Assert.assertEquals(hint, new org.wheatgenetics.coordinate.optionalField
            .BaseOptionalFieldTest.ConcreteBaseOptionalField(
                "name", hint,this).getHint());
    }

    @org.junit.Test() public void setCheckedWorks()
    {
        final org.wheatgenetics.coordinate.optionalField.BaseOptionalFieldTest
            .ConcreteBaseOptionalField concreteBaseOptionalField = new org.wheatgenetics.coordinate
                .optionalField.BaseOptionalFieldTest.ConcreteBaseOptionalField(org.wheatgenetics
                        .coordinate.optionalField.BaseOptionalField.identificationFieldName(
                            this),
                    this);
        org.junit.Assert.assertTrue(concreteBaseOptionalField.getChecked());

        concreteBaseOptionalField.setChecked(false);
        org.junit.Assert.assertTrue(concreteBaseOptionalField.getChecked());
    }

    @org.junit.Test() public void isAPersonWorks()
    {
        org.junit.Assert.assertTrue(new org.wheatgenetics.coordinate.optionalField
            .BaseOptionalFieldTest.ConcreteBaseOptionalField(
                "Person",this).isAPerson());

        org.junit.Assert.assertTrue(new org.wheatgenetics.coordinate.optionalField
            .BaseOptionalFieldTest.ConcreteBaseOptionalField(
                "person",this).isAPerson());

        org.junit.Assert.assertTrue(new org.wheatgenetics.coordinate.optionalField
            .BaseOptionalFieldTest.ConcreteBaseOptionalField(
                "pERSON",this).isAPerson());

        org.junit.Assert.assertTrue(new org.wheatgenetics.coordinate.optionalField
            .BaseOptionalFieldTest.ConcreteBaseOptionalField(
                "Name",this).isAPerson());

        org.junit.Assert.assertTrue(new org.wheatgenetics.coordinate.optionalField
            .BaseOptionalFieldTest.ConcreteBaseOptionalField(
                "nAME",this).isAPerson());

        org.junit.Assert.assertFalse(new org.wheatgenetics.coordinate.optionalField
            .BaseOptionalFieldTest.ConcreteBaseOptionalField(
                "testName",this).isAPerson());
    }
    // endregion
}