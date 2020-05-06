package org.wheatgenetics.coordinate.model;

import android.content.res.Resources;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.PluralsRes;
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
 * androidx.annotation.Nullable
 * androidx.annotation.PluralsRes
 * androidx.annotation.StringRes
 *
 * org.junit.Assert
 * org.junit.Test
 *
 * org.wheatgenetics.coordinate.R
 * org.wheatgenetics.coordinate.StringGetter
 *
 * org.wheatgenetics.coordinate.model.Model
 */
@SuppressWarnings({"ClassExplicitlyExtendsObject"}) public class ModelTest
extends Object implements StringGetter
{
    /**
     * This class was defined in order to test Model.  Why not just test Model directly?  Because
     * Model is abstract.  Why does that matter?  Because I can't instantiate an abstract class.  If
     * I can't instantiate it I can't test it.
     */
    private static class ConcreteModel extends Model
    {
        ConcreteModel(@NonNull
        final StringGetter stringGetter) { super(stringGetter); }

        ConcreteModel(@IntRange(from = 1) final long id,
        @NonNull final StringGetter stringGetter)
        { super(id, stringGetter); }
    }

    // region org.wheatgenetics.coordinate.StringGetter Overridden Methods
    @Override @Nullable public String get(
    @StringRes final int resId)
    {
        // noinspection SwitchStatementWithTooFewBranches
        switch (resId)
        {
            case R.string.ModelIdMustBeGreaterThanZero:
                return "id must be > 0";

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
    @Test() public void firstConstructorSucceeds()
    {
        Assert.assertEquals(0,
            new ModelTest.ConcreteModel(this).getId());
    }

    @Test() public void secondConstructorSucceeds()
    {
        final long                                                       testId        = 5;
        final ModelTest.ConcreteModel concreteModel =
            new ModelTest.ConcreteModel(testId,this);
        Assert.assertEquals(testId, concreteModel.getId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void secondConstructorFails()
    { new ModelTest.ConcreteModel(0,this); }
    // endregion

    // region Overridden Method Tests
    @Test() public void toStringSucceeds()
    {
        final long                                                       testId        = 5;
        final ModelTest.ConcreteModel concreteModel =
            new ModelTest.ConcreteModel(testId,this);
        Assert.assertEquals(String.format("id: %02d", testId),
            concreteModel.toString());
    }

    @Test() public void hashCodeSucceeds()
    {
        final long                                                       testId        = 5;
        final ModelTest.ConcreteModel concreteModel =
            new ModelTest.ConcreteModel(testId,this);
        Assert.assertEquals(String.format("id: %02d", testId).hashCode(),
            concreteModel.hashCode());
    }
    // endregion

    // region Public Method Tests
    @Test() public void illegalWorks()
    {
        Assert.assertFalse(Model.illegal(+5));
        Assert.assertTrue (Model.illegal( 0));
        Assert.assertTrue (Model.illegal(-5));
    }

    @Test() public void validSucceeds()
    {
        final long testId = 5;
        Assert.assertEquals(testId,
            Model.valid(testId,this));
    }

    @Test(expected = IllegalArgumentException.class) public void validThrows()
    { Model.valid(-5,this); }

    @Test() public void equalsAndSetIdWork()
    {
        final long                                                       testId = 5;
        final ModelTest.ConcreteModel
            firstConcreteModel = new
                ModelTest.ConcreteModel(testId,this),
            secondConcreteModel = new
                ModelTest.ConcreteModel(testId,this);

        // noinspection SimplifiableJUnitAssertion
        Assert.assertTrue(firstConcreteModel.equals(secondConcreteModel));

        secondConcreteModel.setId(3);

        // noinspection SimplifiableJUnitAssertion
        Assert.assertFalse(firstConcreteModel.equals(secondConcreteModel));
    }

    @Test() public void getIdWorks()
    {
        final long                                                       testId        = 5;
        final ModelTest.ConcreteModel concreteModel =
            new ModelTest.ConcreteModel(testId,this);
        Assert.assertEquals   (testId, concreteModel.getId());
        Assert.assertNotEquals(3, concreteModel.getId());
    }

    @Test(expected = IllegalArgumentException.class) public void setIdFails()
    { new ModelTest.ConcreteModel(this).setId(-9); }
    // endregion
}