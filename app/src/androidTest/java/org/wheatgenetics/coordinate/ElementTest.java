package org.wheatgenetics.coordinate;

/**
 * Uses:
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
        private ConcreteElement(
        final org.wheatgenetics.coordinate.model.ElementModel elementModel,
        final android.widget.TextView                         textView    ,
        final org.wheatgenetics.coordinate.Element.Handler    handler     )
        { super(elementModel, textView, handler); }

        // region Overridden Methods
        @java.lang.Override protected void respondToClick       () {}
        @java.lang.Override protected void setBackgroundResource() {}
        // endregion
    }

    // region Protected Method Tests
    // region setOnClickListener() Protected Method Tests
    @org.junit.Test(expected = java.lang.NullPointerException.class)
    public void setOnClickListenerFails()
    {
        new org.wheatgenetics.coordinate.ElementTest.ConcreteElement(
            null, null, null).setOnClickListener();
    }

    @org.junit.Test public void setOnClickListenerSucceeds()
    {
        new org.wheatgenetics.coordinate.ElementTest.ConcreteElement(
            /* elementModel => */ null,
            /* textView     => */ new android.widget.TextView(
                android.support.test.InstrumentationRegistry.getTargetContext()),
            /* handler => */ null).setOnClickListener();
    }
    // endregion

    // region clearOnClickListener() Protected Method Tests
    @org.junit.Test(expected = java.lang.NullPointerException.class)
    public void clearOnClickListenerFails()
    {
        new org.wheatgenetics.coordinate.ElementTest.ConcreteElement(
            null, null, null).clearOnClickListener();
    }

    @org.junit.Test public void clearOnClickListenerSucceeds()
    {
        new org.wheatgenetics.coordinate.ElementTest.ConcreteElement(
            /* elementModel => */ null,
            /* textView     => */ new android.widget.TextView(
                android.support.test.InstrumentationRegistry.getTargetContext()),
            /* handler => */ null).clearOnClickListener();
    }
    // endregion

    // region setOnLongClickListener() Protected Method Tests
    @org.junit.Test(expected = java.lang.NullPointerException.class)
    public void setOnLongClickListenerFails()
    {
        new org.wheatgenetics.coordinate.ElementTest.ConcreteElement(
            null, null, null).setOnLongClickListener(null);
    }

    @org.junit.Test public void setOnLongClickListenerSucceeds()
    {
        new org.wheatgenetics.coordinate.ElementTest.ConcreteElement(
            /* elementModel => */ null,
            /* textView     => */ new android.widget.TextView(
                android.support.test.InstrumentationRegistry.getTargetContext()),
            /* handler => */ null).setOnLongClickListener(null);
    }
    // endregion

    // region clearOnLongClickListener() Protected Method Tests
    @org.junit.Test(expected = java.lang.NullPointerException.class)
    public void clearOnLongClickListenerFails()
    {
        new org.wheatgenetics.coordinate.ElementTest.ConcreteElement(
            null, null, null).clearOnLongClickListener();
    }

    @org.junit.Test public void clearOnLongClickListenerSucceeds()
    {
        new org.wheatgenetics.coordinate.ElementTest.ConcreteElement(
            /* elementModel => */ null,
            /* textView     => */ new android.widget.TextView(
                android.support.test.InstrumentationRegistry.getTargetContext()),
            /* handler => */ null).clearOnLongClickListener();
    }
    // endregion

    @org.junit.Test public void getHandlerWorks()
    {
        org.junit.Assert.assertNull(new org.wheatgenetics.coordinate.ElementTest.ConcreteElement(
            null, null, null).getHandler());

        class Handler extends java.lang.Object
        implements org.wheatgenetics.coordinate.Element.Handler
        {
            @java.lang.Override
            public void toggle(final org.wheatgenetics.coordinate.model.ElementModel elementModel)
            {}
        }
        final Handler handler = new Handler();

        final org.wheatgenetics.coordinate.ElementTest.ConcreteElement concreteElement =
            new org.wheatgenetics.coordinate.ElementTest.ConcreteElement(null, null, handler);
        org.junit.Assert.assertEquals(handler, concreteElement.getHandler());
    }

    @org.junit.Test public void elementModelIsNotNullWorks()
    {
        org.junit.Assert.assertFalse(new org.wheatgenetics.coordinate.ElementTest.ConcreteElement(
            null, null, null).elementModelIsNotNull());

        class ElementModel extends java.lang.Object
        implements org.wheatgenetics.coordinate.model.ElementModel
        {
            @java.lang.Override public int getRowValue() { return 0; }
            @java.lang.Override public int getColValue() { return 0; }
        }
        final ElementModel elementModel = new ElementModel();

        final org.wheatgenetics.coordinate.ElementTest.ConcreteElement concreteElement =
            new org.wheatgenetics.coordinate.ElementTest.ConcreteElement(elementModel, null, null);
        org.junit.Assert.assertTrue(concreteElement.elementModelIsNotNull());
    }

    @org.junit.Test(expected = java.lang.NullPointerException.class)
    public void setBackgroundResourceFails()
    {
        new org.wheatgenetics.coordinate.ElementTest.ConcreteElement(
            null, null, null).setBackgroundResource(55);
    }

    @org.junit.Test(expected = java.lang.NullPointerException.class) public void toggleFails()
    { new org.wheatgenetics.coordinate.ElementTest.ConcreteElement(null, null, null).toggle(); }
    // endregion

    // region Public MethodTests
    @org.junit.Test public void getRowWorks()
    {
        org.junit.Assert.assertEquals(-1, new
            org.wheatgenetics.coordinate.ElementTest.ConcreteElement(null, null, null).getRow());
    }

    @org.junit.Test public void getColWorks()
    {
        org.junit.Assert.assertEquals(-1, new
            org.wheatgenetics.coordinate.ElementTest.ConcreteElement(null, null, null).getCol());
    }
    // endregion
}