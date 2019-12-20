package org.wheatgenetics.coordinate.display;

/**
 * Uses:
 * android.widget.TextView
 *
 * androidx.annotation.NonNull
 *
 * org.junit.Assert
 * org.junit.Test
 *
 * org.wheatgenetics.coordinate.model.ElementModel
 *
 * org.wheatgenetics.coordinate.display.Element
 * org.wheatgenetics.coordinate.display.Elements
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
public class ElementsTest extends java.lang.Object
{
    /**
     * This class was defined in order to test Elements.  Why not just test Elements directly?
     * Because Elements is abstract.  Why does that matter?  Because I can't instantiate an abstract
     * class.  If I can't instantiate it I can't test it.
     */
    private static class ConcreteElements extends org.wheatgenetics.coordinate.display.Elements
    {
        private ConcreteElements()
        {
            //noinspection ConstantConditions
            super(null);
        }

        @java.lang.Override @androidx.annotation.NonNull
        protected org.wheatgenetics.coordinate.display.Element makeElement(
        final org.wheatgenetics.coordinate.model.ElementModel elementModel,
        final android.widget.TextView                         textView    )
        {
            // noinspection ConstantConditions
            return null;
        }
    }

    // region Protected Method Tests
    @org.junit.Test() public void getActivityWorks()
    {
        // noinspection ConstantConditions
        org.junit.Assert.assertNull(
            new org.wheatgenetics.coordinate.display.ElementsTest.ConcreteElements().getActivity());
    }

    @org.junit.Test() public void getElementArrayWorks()
    {
        org.junit.Assert.assertNull(new
            org.wheatgenetics.coordinate.display.ElementsTest.ConcreteElements().getElementArray());
    }
    // endregion

    @org.junit.Test() public void clearAndAllocateWork()
    {
        final org.wheatgenetics.coordinate.display.ElementsTest.ConcreteElements concreteElements =
            new org.wheatgenetics.coordinate.display.ElementsTest.ConcreteElements();
        org.junit.Assert.assertNull(concreteElements.getElementArray());

        concreteElements.allocate(5,2);
        org.junit.Assert.assertNotNull(concreteElements.getElementArray());

        concreteElements.clear(); org.junit.Assert.assertNull(concreteElements.getElementArray());
    }

    @org.junit.Test() public void nullAddWorks()
    {
        org.junit.Assert.assertNull(
            new org.wheatgenetics.coordinate.display.ElementsTest.ConcreteElements().add(null));
    }

    // region allocate() Tests
    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void invalidRowAllocateFails()
    {
        new org.wheatgenetics.coordinate.display.ElementsTest.ConcreteElements().allocate(
            -7,5);
    }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void invalidColAllocateFails()
    {
        new org.wheatgenetics.coordinate.display.ElementsTest.ConcreteElements().allocate(
            7,0);
    }
    // endregion
}