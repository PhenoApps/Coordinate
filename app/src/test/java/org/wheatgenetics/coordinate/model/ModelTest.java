package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * org.junit.Assert
 * org.junit.Test
 *
 * org.wheatgenetics.coordinate.model.Model
 */
@java.lang.SuppressWarnings("ClassExplicitlyExtendsObject")
public class ModelTest extends java.lang.Object
{
    /**
     * This class was defined in order to test Model.  Why not just test Model directly?  Because
     * Model is abstract.  Why does that matter?  Because I can't instantiate an abstract class.  If
     * I can't instantiate it I can't test it.
     */
    private static class ConcreteModel extends org.wheatgenetics.coordinate.model.Model
    { ConcreteModel() { super(); } ConcreteModel(final long id) { super(id); } }

    // region Constructor Tests
    @org.junit.Test
    public void firstConstructorSucceeds()
    {
        final org.wheatgenetics.coordinate.model.ModelTest.ConcreteModel concreteModel =
            new org.wheatgenetics.coordinate.model.ModelTest.ConcreteModel();
        org.junit.Assert.assertEquals(concreteModel.getId(), 0);
    }

    @org.junit.Test
    public void secondConstructorSucceeds()
    {
        final long testId = 5;
        final org.wheatgenetics.coordinate.model.ModelTest.ConcreteModel concreteModel =
            new org.wheatgenetics.coordinate.model.ModelTest.ConcreteModel(testId);
        org.junit.Assert.assertEquals(concreteModel.getId(), testId);
    }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void secondConstructorFails()
    { new org.wheatgenetics.coordinate.model.ModelTest.ConcreteModel(0); }
    // endregion

    // region Overridden Method Tests
    @org.junit.Test @java.lang.SuppressWarnings("DefaultLocale")
    public void toStringSucceeds()
    {
        final long testId = 5;
        final org.wheatgenetics.coordinate.model.ModelTest.ConcreteModel concreteModel =
            new org.wheatgenetics.coordinate.model.ModelTest.ConcreteModel(testId);
        org.junit.Assert.assertEquals(
            concreteModel.toString(), java.lang.String.format("id: %02d", testId));
    }

    @org.junit.Test @java.lang.SuppressWarnings("DefaultLocale")
    public void hashCodeSucceeds()
    {
        final long testId = 5;
        final org.wheatgenetics.coordinate.model.ModelTest.ConcreteModel concreteModel =
            new org.wheatgenetics.coordinate.model.ModelTest.ConcreteModel(testId);
        org.junit.Assert.assertEquals(
            concreteModel.hashCode(), java.lang.String.format("id: %02d", testId).hashCode());
    }
    // endregion

    @org.junit.Test
    public void equalsAndSetIdSucceedAndFail()
    {
        final long testId = 5;
        final org.wheatgenetics.coordinate.model.ModelTest.ConcreteModel
            firstConcreteModel =
                new org.wheatgenetics.coordinate.model.ModelTest.ConcreteModel(testId),
            secondConcreteModel =
                new org.wheatgenetics.coordinate.model.ModelTest.ConcreteModel(testId);
        org.junit.Assert.assertTrue(firstConcreteModel.equals(secondConcreteModel));

        secondConcreteModel.setId(3);
        org.junit.Assert.assertFalse(firstConcreteModel.equals(secondConcreteModel));
    }

    @org.junit.Test
    public void getIdSucceedsAndFails()
    {
        final long testId = 5;
        final org.wheatgenetics.coordinate.model.ModelTest.ConcreteModel concreteModel =
            new org.wheatgenetics.coordinate.model.ModelTest.ConcreteModel(testId);
        org.junit.Assert.assertEquals   (concreteModel.getId(), testId);
        org.junit.Assert.assertNotEquals(concreteModel.getId(), 3     );
    }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void setIdFails()
    {
        final org.wheatgenetics.coordinate.model.ModelTest.ConcreteModel concreteModel =
            new org.wheatgenetics.coordinate.model.ModelTest.ConcreteModel();
        concreteModel.setId(-9);
    }
}