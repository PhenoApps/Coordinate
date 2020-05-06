package org.wheatgenetics.coordinate.optionalField;

import android.content.res.Resources;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.PluralsRes;
import androidx.annotation.Size;
import androidx.annotation.StringRes;

import org.junit.Assert;
import org.junit.Test;
import org.wheatgenetics.coordinate.R;
import org.wheatgenetics.coordinate.StringGetter;

/**
 * Uses:
 * android.content.res.Resources.NotFoundException
 *
 * androidx.annotation.IntRange
 * androidx.annotation.NonNull
 * androidx.annotation.PluralsRes
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
@SuppressWarnings({"ClassExplicitlyExtendsObject"}) public class BaseOptionalFieldTest
extends Object implements StringGetter
{
    /**
     * This class was defined in order to test BaseOptionalField.  Why not just test
     * BaseOptionalField directly?  Because BaseOptionalField is abstract.  Why does that matter?
     * Because I can't instantiate an abstract class.  If I can't instantiate it I can't test it.
     */
    private static class ConcreteBaseOptionalField extends
    BaseOptionalField
    {
        private ConcreteBaseOptionalField(@NonNull
        @Size(min = 1) final String name, final String hint,
                                          @NonNull final StringGetter stringGetter)
        { super(name, hint, stringGetter); }

        private ConcreteBaseOptionalField(@NonNull
        @Size(min = 1) final String name,
        @NonNull final StringGetter stringGetter)
        { super(name, stringGetter); }
    }

    // region org.wheatgenetics.coordinate.StringGetter Overridden Methods
    @Override @Nullable public String get(
    @StringRes final int resId)
    {
        switch (resId)
        {
            case R.string.BaseOptionalFieldPersonFieldName:
                return "Person";

            case R.string.BaseOptionalFieldNameFieldName:
                return "Name";

            case R.string.BaseOptionalFieldIdentificationFieldName:
                return "Identification";

            default: Assert.fail(); return null;
        }
    }

    @Override @NonNull public String getQuantity(
    @PluralsRes         final int                 resId     ,
    @IntRange(from = 0) final int                 quantity  ,
    @Nullable           final Object... formatArgs)
    throws Resources.NotFoundException { Assert.fail(); return null; }
    // endregion

    // region Constructor Tests
    // region First Constructor Tests
    // region First Constructor Name Parameter Tests
    @Test(expected = AssertionError.class)
    public void firstConstructorNullNameParameterFails()
    {
        // noinspection ConstantConditions
        new BaseOptionalFieldTest
            .ConcreteBaseOptionalField(null,null,this);
    }

    @Test(expected = AssertionError.class)
    public void firstConstructorEmptyNameParameterFails()
    {
        new BaseOptionalFieldTest
            .ConcreteBaseOptionalField("",null,this);
    }

    @Test(expected = AssertionError.class)
    public void firstConstructorSpacesNameParameterFails()
    {
        new BaseOptionalFieldTest
            .ConcreteBaseOptionalField("  ",null,this);
    }

    @Test() public void firstConstructorTestNameParameterIsEqual()
    {
        final String testName = "testName";
        final BaseOptionalFieldTest
            .ConcreteBaseOptionalField concreteBaseOptionalField = new BaseOptionalFieldTest.ConcreteBaseOptionalField(
                    testName,"testHint",this);

        Assert.assertEquals(testName, concreteBaseOptionalField.getName());
    }
    // endregion

    // region First Constructor Hint Parameter Tests
    @Test() public void firstConstructorNullHintParameterIsConverted()
    {
        final BaseOptionalFieldTest
            .ConcreteBaseOptionalField concreteBaseOptionalField = new BaseOptionalFieldTest.ConcreteBaseOptionalField(
                    "testName",null,this);
        Assert.assertEquals("", concreteBaseOptionalField.getHint());
    }

    @Test() public void firstConstructorEmptyHintParameterIsEmpty()
    {
        final String emptyHint = "";
        final BaseOptionalFieldTest
            .ConcreteBaseOptionalField concreteBaseOptionalField = new BaseOptionalFieldTest.ConcreteBaseOptionalField(
                    "testName", emptyHint,this);
        Assert.assertEquals(emptyHint, concreteBaseOptionalField.getHint());
    }

    @Test() public void firstConstructorSpacesHintParameterIsEmpty()
    {
        final BaseOptionalFieldTest
            .ConcreteBaseOptionalField concreteBaseOptionalField = new BaseOptionalFieldTest.ConcreteBaseOptionalField(
                    "testName","  ",this);
        Assert.assertEquals("", concreteBaseOptionalField.getHint());
    }

    @Test() public void firstConstructorTestHintParameterIsEqual()
    {
        final String testHint = "testHint";
        final BaseOptionalFieldTest
            .ConcreteBaseOptionalField concreteBaseOptionalField = new BaseOptionalFieldTest.ConcreteBaseOptionalField(
                    "testName", testHint,this);
        Assert.assertEquals(testHint, concreteBaseOptionalField.getHint());
    }
    // endregion
    // endregion

    // region Second Constructor Tests
    // region Second Constructor Name Parameter Tests
    @Test(expected = AssertionError.class)
    public void secondConstructorNullNameParameterFails()
    {
        // noinspection ConstantConditions
        new BaseOptionalFieldTest
            .ConcreteBaseOptionalField(null,this);
    }

    @Test(expected = AssertionError.class)
    public void secondConstructorEmptyNameParameterFails()
    {
        new BaseOptionalFieldTest
            .ConcreteBaseOptionalField("",this);
    }

    @Test(expected = AssertionError.class)
    public void secondConstructorSpacesNameParameterFails()
    {
        new BaseOptionalFieldTest
            .ConcreteBaseOptionalField("  ",this);
    }

    @Test() public void secondConstructorTestNameParameterIsEqual()
    {
        final String testName = "testName";
        final BaseOptionalFieldTest
            .ConcreteBaseOptionalField concreteBaseOptionalField = new BaseOptionalFieldTest.ConcreteBaseOptionalField(
                    testName,this);
        Assert.assertEquals(testName, concreteBaseOptionalField.getName());
    }
    // endregion

    // region Second Constructor Hint Parameter Test
    @Test() public void secondConstructorHintIsEmpty()
    {
        final BaseOptionalFieldTest
            .ConcreteBaseOptionalField concreteBaseOptionalField = new BaseOptionalFieldTest.ConcreteBaseOptionalField(
                    "testName",this);
        Assert.assertEquals("", concreteBaseOptionalField.getHint());
    }
    // endregion
    // endregion
    // endregion

    // region Overridden Method Tests
    @Test() public void toStringIsEqual()
    {
        final String testName = "testName";
        final BaseOptionalFieldTest
            .ConcreteBaseOptionalField concreteBaseOptionalField = new BaseOptionalFieldTest.ConcreteBaseOptionalField(
                    testName,this);
        Assert.assertEquals(testName, concreteBaseOptionalField.toString());
    }

    @Test() public void equalsFails()
    {
        final BaseOptionalFieldTest
            .ConcreteBaseOptionalField firstConcreteBaseOptionalField = new BaseOptionalFieldTest.ConcreteBaseOptionalField(
                    "first",this);

        // noinspection ConstantConditions, SimplifiableJUnitAssertion
        Assert.assertFalse(firstConcreteBaseOptionalField.equals(null));

        // noinspection EqualsBetweenInconvertibleTypes, SimplifiableJUnitAssertion
        Assert.assertFalse(firstConcreteBaseOptionalField.equals("any string"));

        final BaseOptionalFieldTest
            .ConcreteBaseOptionalField secondConcreteBaseOptionalField = new BaseOptionalFieldTest.ConcreteBaseOptionalField(
                    "second",this);

        // noinspection SimplifiableJUnitAssertion
        Assert.assertFalse(
            firstConcreteBaseOptionalField.equals(secondConcreteBaseOptionalField));
    }

    @Test() public void equalsSucceeds()
    {
        final String testName = "testName";
        final BaseOptionalFieldTest
            .ConcreteBaseOptionalField firstConcreteBaseOptionalField = new BaseOptionalFieldTest.ConcreteBaseOptionalField(
                    testName,this);

        // noinspection EqualsWithItself, SimplifiableJUnitAssertion
        Assert.assertTrue(firstConcreteBaseOptionalField.equals(
            firstConcreteBaseOptionalField));

        final BaseOptionalFieldTest
            .ConcreteBaseOptionalField secondConcreteBaseOptionalField = new BaseOptionalFieldTest.ConcreteBaseOptionalField(
                    testName,this);

        // noinspection SimplifiableJUnitAssertion
        Assert.assertTrue(firstConcreteBaseOptionalField.equals(
            secondConcreteBaseOptionalField));
    }

    @Test(expected = CloneNotSupportedException.class)
    public void cloneFails() throws CloneNotSupportedException
    {
        new BaseOptionalFieldTest
            .ConcreteBaseOptionalField("testName",this).clone();
    }
    // endregion

    // region Package Method Tests
    @Test() public void namesAreEqualWorks()
    {
        final String                                                testName = "testName";
        final BaseOptionalFieldTest
            .ConcreteBaseOptionalField concreteBaseOptionalField = new BaseOptionalFieldTest.ConcreteBaseOptionalField(
                    testName,this);
        Assert.assertTrue (concreteBaseOptionalField.namesAreEqual(testName    ));
        Assert.assertTrue (concreteBaseOptionalField.namesAreEqual("testName"  ));
        Assert.assertTrue (concreteBaseOptionalField.namesAreEqual("TestName"  ));
        Assert.assertFalse(concreteBaseOptionalField.namesAreEqual("any string"));
        Assert.assertFalse(concreteBaseOptionalField.namesAreEqual(""          ));
        Assert.assertFalse(concreteBaseOptionalField.namesAreEqual("  "        ));
        Assert.assertFalse(concreteBaseOptionalField.namesAreEqual(null        ));
    }

    @Test() public void getSafeValueWhenNameIsNotAPerson()
    {
        final BaseOptionalFieldTest
            .ConcreteBaseOptionalField concreteBaseOptionalField = new BaseOptionalFieldTest.ConcreteBaseOptionalField(
                    "testName",this);

        final String safeTestValue;
        {
            final String testValue = "test value";
            concreteBaseOptionalField.setValue(testValue);
            Assert.assertEquals(testValue, concreteBaseOptionalField.getSafeValue());

            safeTestValue = testValue.replace(" ","_");
        }
        Assert.assertNotEquals(safeTestValue, concreteBaseOptionalField.getSafeValue());
    }

    @Test() public void getSafeValueWhenNameIsAPerson()
    {
        final BaseOptionalFieldTest
            .ConcreteBaseOptionalField concreteBaseOptionalField = new BaseOptionalFieldTest.ConcreteBaseOptionalField(
                    /* name => */"Person",this);

        final String safeTestValue;
        {
            final String testValue = "test value";
            concreteBaseOptionalField.setValue(testValue);
            Assert.assertNotEquals(testValue, concreteBaseOptionalField.getSafeValue());

            safeTestValue = testValue.replace(" ","_");
        }
        Assert.assertEquals(safeTestValue, concreteBaseOptionalField.getSafeValue());
    }

    @Test() public void getCheckedWorks()
    {
        final BaseOptionalFieldTest
            .ConcreteBaseOptionalField concreteBaseOptionalField = new BaseOptionalFieldTest.ConcreteBaseOptionalField(
                    "testName",this);
        Assert.assertTrue(concreteBaseOptionalField.getChecked());

        concreteBaseOptionalField.setChecked(false);
        Assert.assertFalse(concreteBaseOptionalField.getChecked());

        concreteBaseOptionalField.setChecked(true);
        Assert.assertTrue(concreteBaseOptionalField.getChecked());
    }
    // endregion

    // region Public Method Tests
    @Test() public void getValueIsAndIsNotEqual()
    {
        final BaseOptionalFieldTest
            .ConcreteBaseOptionalField concreteBaseOptionalField = new BaseOptionalFieldTest.ConcreteBaseOptionalField(
                    "testName",this);
        {
            final String testValue = "testValue";
            concreteBaseOptionalField.setValue(testValue);
            Assert.assertEquals(testValue, concreteBaseOptionalField.getValue());
        }
        Assert.assertNotEquals(
            "any string", concreteBaseOptionalField.getValue());
    }

    @Test() public void setValueIsAndIsNotEqual()
    {
        final BaseOptionalFieldTest
            .ConcreteBaseOptionalField concreteBaseOptionalField = new BaseOptionalFieldTest.ConcreteBaseOptionalField(
                    "testName",this);
        {
            final String testValue = "testValue";
            concreteBaseOptionalField.setValue(testValue);
            Assert.assertEquals(testValue, concreteBaseOptionalField.getValue());
        }
        Assert.assertNotEquals(
            "any string", concreteBaseOptionalField.getValue());

        Assert.assertNotEquals(null, concreteBaseOptionalField.getValue());

        concreteBaseOptionalField.setValue(null);
        Assert.assertEquals("", concreteBaseOptionalField.getValue());

        concreteBaseOptionalField.setValue("  ");
        Assert.assertEquals("", concreteBaseOptionalField.getValue());

        concreteBaseOptionalField.setValue(" abc ");
        Assert.assertEquals("abc", concreteBaseOptionalField.getValue());
    }

    @Test() public void getUnspecifiedHint()
    {
        Assert.assertEquals("", new BaseOptionalFieldTest.ConcreteBaseOptionalField(
                "name",this).getHint());
    }

    @Test() public void getSpecifiedHint()
    {
        final String hint = "hint";
        Assert.assertEquals(hint, new BaseOptionalFieldTest.ConcreteBaseOptionalField(
                "name", hint,this).getHint());
    }

    @Test() public void setCheckedWorks()
    {
        final BaseOptionalFieldTest
            .ConcreteBaseOptionalField concreteBaseOptionalField = new BaseOptionalFieldTest.ConcreteBaseOptionalField(BaseOptionalField.identificationFieldName(
                            this),
                    this);
        Assert.assertTrue(concreteBaseOptionalField.getChecked());

        concreteBaseOptionalField.setChecked(false);
        Assert.assertTrue(concreteBaseOptionalField.getChecked());
    }

    @Test() public void isAPersonWorks()
    {
        Assert.assertTrue(new BaseOptionalFieldTest.ConcreteBaseOptionalField(
                "Person",this).isAPerson());

        Assert.assertTrue(new BaseOptionalFieldTest.ConcreteBaseOptionalField(
                "person",this).isAPerson());

        Assert.assertTrue(new BaseOptionalFieldTest.ConcreteBaseOptionalField(
                "pERSON",this).isAPerson());

        Assert.assertTrue(new BaseOptionalFieldTest.ConcreteBaseOptionalField(
                "Name",this).isAPerson());

        Assert.assertTrue(new BaseOptionalFieldTest.ConcreteBaseOptionalField(
                "nAME",this).isAPerson());

        Assert.assertFalse(new BaseOptionalFieldTest.ConcreteBaseOptionalField(
                "testName",this).isAPerson());
    }
    // endregion
}