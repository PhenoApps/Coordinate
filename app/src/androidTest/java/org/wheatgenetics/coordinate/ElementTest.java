package org.wheatgenetics.coordinate;

/**
 * Uses:
 * android.support.annotation.IntRange
 * android.support.annotation.NonNull
 * android.support.annotation.Nullable
 *
 * org.junit.Assert
 * org.junit.Test
 *
 * android.support.test.InstrumentationRegistry
 * android.widget.TextView
 *
 * org.wheatgenetics.coordinate.model.ElementModel
 *
 * org.wheatgenetics.coordinate.Element
 * org.wheatgenetics.coordinate.Element.Handler
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
public class ElementTest extends java.lang.Object
{
    /**
     * This class was defined in order to test Element.  Why not just test Element directly?
     * Because Element is abstract.  Why does that matter?  Because I can't instantiate an abstract
     * class.  If I can't instantiate it I can't test it.
     */
    private static class ConcreteElement extends org.wheatgenetics.coordinate.Element
    {
        private ConcreteElement(@android.support.annotation.Nullable
            final org.wheatgenetics.coordinate.model.ElementModel elementModel,
        @android.support.annotation.NonNull final android.widget.TextView textView,
        @android.support.annotation.NonNull final org.wheatgenetics.coordinate.Element.Handler
            handler) { super(elementModel, textView, handler); }

        // region Overridden Methods
        @java.lang.Override protected void respondToClick       () {}
        @java.lang.Override protected void setBackgroundResource() {}
        // endregion
    }

    // region clearOnLongClickListener() Package Method Tests
    @org.junit.Test(expected = java.lang.NullPointerException.class)
    public void clearOnLongClickListenerFails()
    {
        // noinspection ConstantConditions
        new org.wheatgenetics.coordinate.ElementTest.ConcreteElement(
            null,null,null).clearOnLongClickListener();
    }

    @org.junit.Test() public void clearOnLongClickListenerSucceeds()
    {
        // noinspection ConstantConditions
        new org.wheatgenetics.coordinate.ElementTest.ConcreteElement(
            /* elementModel => */null,
            /* textView     => */ new android.widget.TextView(
            android.support.test.InstrumentationRegistry.getTargetContext()),
            /* handler => */null).clearOnLongClickListener();
    }
    // endregion

    // region Protected Method Tests
    // region setOnClickListener() Protected Method Tests
    @org.junit.Test(expected = java.lang.NullPointerException.class)
    public void setOnClickListenerFails()
    {
        // noinspection ConstantConditions
        new org.wheatgenetics.coordinate.ElementTest.ConcreteElement(
            null,null,null).setOnClickListener();
    }

    @org.junit.Test() public void setOnClickListenerSucceeds()
    {
        // noinspection ConstantConditions
        new org.wheatgenetics.coordinate.ElementTest.ConcreteElement(
            /* elementModel => */null,
            /* textView     => */ new android.widget.TextView(
                android.support.test.InstrumentationRegistry.getTargetContext()),
            /* handler => */null).setOnClickListener();
    }
    // endregion

    // region clearOnClickListener() Protected Method Tests
    @org.junit.Test(expected = java.lang.NullPointerException.class)
    public void clearOnClickListenerFails()
    {
        // noinspection ConstantConditions
        new org.wheatgenetics.coordinate.ElementTest.ConcreteElement(
            null,null,null).clearOnClickListener();
    }

    @org.junit.Test() public void clearOnClickListenerSucceeds()
    {
        // noinspection ConstantConditions
        new org.wheatgenetics.coordinate.ElementTest.ConcreteElement(
            /* elementModel => */null,
            /* textView     => */ new android.widget.TextView(
                android.support.test.InstrumentationRegistry.getTargetContext()),
            /* handler => */null).clearOnClickListener();
    }
    // endregion

    // region setOnLongClickListener() Protected Method Tests
    @org.junit.Test(expected = java.lang.NullPointerException.class)
    public void setOnLongClickListenerFails()
    {
        // noinspection ConstantConditions
        new org.wheatgenetics.coordinate.ElementTest.ConcreteElement(
            null,null,null).setOnLongClickListener(null);
    }

    @org.junit.Test() public void setOnLongClickListenerSucceeds()
    {
        // noinspection ConstantConditions
        new org.wheatgenetics.coordinate.ElementTest.ConcreteElement(
            /* elementModel => */null,
            /* textView     => */ new android.widget.TextView(
                android.support.test.InstrumentationRegistry.getTargetContext()),
            /* handler => */null).setOnLongClickListener(null);
    }
    // endregion

    @org.junit.Test() public void elementModelIsNotNullWorks()
    {
        // noinspection ConstantConditions
        org.junit.Assert.assertFalse(new org.wheatgenetics.coordinate.ElementTest.ConcreteElement(
            null,null,null).elementModelIsNotNull());

        class ElementModel extends java.lang.Object
        implements org.wheatgenetics.coordinate.model.ElementModel
        {
            @java.lang.Override @android.support.annotation.IntRange(from = 1)
            public int getRowValue() { return 2; }

            @java.lang.Override @android.support.annotation.IntRange(from = 1)
            public int getColValue() { return 3; }
        }
        final ElementModel elementModel = new ElementModel();

        // noinspection ConstantConditions
        final org.wheatgenetics.coordinate.ElementTest.ConcreteElement concreteElement =
            new org.wheatgenetics.coordinate.ElementTest.ConcreteElement(
                elementModel,null,null);
        org.junit.Assert.assertTrue(concreteElement.elementModelIsNotNull());
    }

    @org.junit.Test(expected = java.lang.NullPointerException.class)
    public void setBackgroundResourceFails()
    {
        // noinspection ConstantConditions
        new org.wheatgenetics.coordinate.ElementTest.ConcreteElement(
            null,null,null).setBackgroundResource(55);
    }

    @org.junit.Test(expected = java.lang.NullPointerException.class) public void toggleFails()
    {
        // noinspection ConstantConditions
        new org.wheatgenetics.coordinate.ElementTest.ConcreteElement(
            null,null,null).toggle();
    }
    // endregion

    // region Public Method Tests
    @org.junit.Test() public void getRowWorks()
    {
        // noinspection ConstantConditions
        org.junit.Assert.assertEquals(-1,
            new org.wheatgenetics.coordinate.ElementTest.ConcreteElement(
                null,null,null).getRow());
    }

    @org.junit.Test() public void getColWorks()
    {
        // noinspection ConstantConditions
        org.junit.Assert.assertEquals(-1,
            new org.wheatgenetics.coordinate.ElementTest.ConcreteElement(
                null,null,null).getCol());
    }
    // endregion
}