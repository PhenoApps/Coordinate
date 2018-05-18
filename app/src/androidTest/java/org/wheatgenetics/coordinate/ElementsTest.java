package org.wheatgenetics.coordinate;

/**
 * Uses:
 * org.junit.Assert
 * org.junit.Test
 *
 * android.app.Activity
 * android.widget.TextView
 *
 * org.wheatgenetics.coordinate.model.ElementModel
 *
 * org.wheatgenetics.coordinate.Element
 * org.wheatgenetics.coordinate.Element.Handler
 * org.wheatgenetics.coordinate.Elements
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
public class ElementsTest extends java.lang.Object
{
    /**
     * This class was defined in order to test Elements.  Why not just test Elements directly?
     * Because Elements is abstract.  Why does that matter?  Because I can't instantiate an abstract
     * class.  If I can't instantiate it I can't test it.
     */
    private static class ConcreteElements extends org.wheatgenetics.coordinate.Elements
    {
        private ConcreteElements() { super(null, null); }

        @java.lang.Override
        protected org.wheatgenetics.coordinate.Element makeElement(
        final org.wheatgenetics.coordinate.model.ElementModel elementModel,
        final android.widget.TextView                         textView    ) { return null; }
    }

    // region Protected Method Tests
    @org.junit.Test public void getActivityWorks()
    {
        org.junit.Assert.assertNull(
            new org.wheatgenetics.coordinate.ElementsTest.ConcreteElements().getActivity());
    }

    @org.junit.Test public void getHandlerWorks()
    {
        org.junit.Assert.assertNull(
            new org.wheatgenetics.coordinate.ElementsTest.ConcreteElements().getHandler());
    }

    @org.junit.Test public void getElementArrayWorks()
    {
        org.junit.Assert.assertNull(
            new org.wheatgenetics.coordinate.ElementsTest.ConcreteElements().getElementArray());
    }
    // endregion

    @org.junit.Test(expected = java.lang.NullPointerException.class) public void toggleFails()
    { new org.wheatgenetics.coordinate.ElementsTest.ConcreteElements().toggle(null); }

    @org.junit.Test public void clearAndAllocateWork()
    {
        final org.wheatgenetics.coordinate.ElementsTest.ConcreteElements concreteElements =
            new org.wheatgenetics.coordinate.ElementsTest.ConcreteElements();
        org.junit.Assert.assertNull(concreteElements.getElementArray());

        concreteElements.allocate(5, 2);
        org.junit.Assert.assertNotNull(concreteElements.getElementArray());

        concreteElements.clear(); org.junit.Assert.assertNull(concreteElements.getElementArray());
    }

    @org.junit.Test public void nullAddWorks()
    {
        org.junit.Assert.assertNull(
            new org.wheatgenetics.coordinate.ElementsTest.ConcreteElements().add(null));
    }

    // region allocate() Tests
    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void invalidRowAllocateFails()
    { new org.wheatgenetics.coordinate.ElementsTest.ConcreteElements().allocate(-7, 5); }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void invalidColAllocateFails()
    { new org.wheatgenetics.coordinate.ElementsTest.ConcreteElements().allocate(7, 0); }
    // endregion
}