package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * android.support.annotation.IntRange
 *
 * org.junit.Assert
 * org.junit.Test
 *
 * org.wheatgenetics.coordinate.model.Model
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
public class ModelTest extends java.lang.Object
{
    /**
     * This class was defined in order to test Model.  Why not just test Model directly?  Because
     * Model is abstract.  Why does that matter?  Because I can't instantiate an abstract class.  If
     * I can't instantiate it I can't test it.
     */
    private static class ConcreteModel extends org.wheatgenetics.coordinate.model.Model
    {
        ConcreteModel()                                                             { super()  ; }
        ConcreteModel(@android.support.annotation.IntRange(from = 1) final long id) { super(id); }
    }

    // region Constructor Tests
    @org.junit.Test() public void firstConstructorSucceeds()
    {
        final org.wheatgenetics.coordinate.model.ModelTest.ConcreteModel concreteModel =
            new org.wheatgenetics.coordinate.model.ModelTest.ConcreteModel();
        org.junit.Assert.assertEquals(0, concreteModel.getId());
    }

    @org.junit.Test() public void secondConstructorSucceeds()
    {
        final long                                                       testId        = 5;
        final org.wheatgenetics.coordinate.model.ModelTest.ConcreteModel concreteModel =
            new org.wheatgenetics.coordinate.model.ModelTest.ConcreteModel(testId);
        org.junit.Assert.assertEquals(testId, concreteModel.getId());
    }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void secondConstructorFails()
    { new org.wheatgenetics.coordinate.model.ModelTest.ConcreteModel(0); }
    // endregion

    // region Overridden Method Tests
    @org.junit.Test() public void toStringSucceeds()
    {
        final long                                                       testId        = 5;
        final org.wheatgenetics.coordinate.model.ModelTest.ConcreteModel concreteModel =
            new org.wheatgenetics.coordinate.model.ModelTest.ConcreteModel(testId);
        org.junit.Assert.assertEquals(java.lang.String.format("id: %02d", testId),
            concreteModel.toString());
    }

    @org.junit.Test() public void hashCodeSucceeds()
    {
        final long                                                       testId        = 5;
        final org.wheatgenetics.coordinate.model.ModelTest.ConcreteModel concreteModel =
            new org.wheatgenetics.coordinate.model.ModelTest.ConcreteModel(testId);
        org.junit.Assert.assertEquals(java.lang.String.format("id: %02d", testId).hashCode(),
            concreteModel.hashCode());
    }
    // endregion

    // region Public Method Tests
    @org.junit.Test() public void illegalSucceeds()
    {
        org.junit.Assert.assertFalse(org.wheatgenetics.coordinate.model.Model.illegal(+5));
        org.junit.Assert.assertTrue (org.wheatgenetics.coordinate.model.Model.illegal( 0));
        org.junit.Assert.assertTrue (org.wheatgenetics.coordinate.model.Model.illegal(-5));
    }

    @org.junit.Test() public void validSucceeds()
    {
        final long testId = 5;
        org.junit.Assert.assertEquals(testId,
            org.wheatgenetics.coordinate.model.Model.valid(testId));
    }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class) public void validThrows()
    { org.wheatgenetics.coordinate.model.Model.valid(-5); }

    @org.junit.Test() public void equalsAndSetIdWork()
    {
        final long                                                       testId = 5;
        final org.wheatgenetics.coordinate.model.ModelTest.ConcreteModel
            firstConcreteModel =
                new org.wheatgenetics.coordinate.model.ModelTest.ConcreteModel(testId),
            secondConcreteModel =
                new org.wheatgenetics.coordinate.model.ModelTest.ConcreteModel(testId);

        // noinspection SimplifiableJUnitAssertion
        org.junit.Assert.assertTrue(firstConcreteModel.equals(secondConcreteModel));

        secondConcreteModel.setId(3);

        // noinspection SimplifiableJUnitAssertion
        org.junit.Assert.assertFalse(firstConcreteModel.equals(secondConcreteModel));
    }

    @org.junit.Test() public void getIdWorks()
    {
        final long                                                       testId        = 5;
        final org.wheatgenetics.coordinate.model.ModelTest.ConcreteModel concreteModel =
            new org.wheatgenetics.coordinate.model.ModelTest.ConcreteModel(testId);
        org.junit.Assert.assertEquals   (testId, concreteModel.getId());
        org.junit.Assert.assertNotEquals(3, concreteModel.getId());
    }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class) public void setIdFails()
    {
        final org.wheatgenetics.coordinate.model.ModelTest.ConcreteModel concreteModel =
            new org.wheatgenetics.coordinate.model.ModelTest.ConcreteModel();
        concreteModel.setId(-9);
    }
    // endregion
}