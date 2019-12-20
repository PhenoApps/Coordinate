package org.wheatgenetics.coordinate.display;

/**
 * Uses:
 * android.widget.TextView
 *
 * androidx.annotation.IntRange
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 * androidx.test.platform.app.InstrumentationRegistry
 *
 * org.junit.Assert
 * org.junit.Test
 *
 * org.wheatgenetics.coordinate.model.ElementModel
 *
 * org.wheatgenetics.coordinate.display.Element
 * org.wheatgenetics.coordinate.display.Element.Handler
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
public class ElementTest extends java.lang.Object
{
    /**
     * This class was defined in order to test Element.  Why not just test Element directly?
     * Because Element is abstract.  Why does that matter?  Because I can't instantiate an abstract
     * class.  If I can't instantiate it I can't test it.
     */
    private static class ConcreteElement extends org.wheatgenetics.coordinate.display.Element
    {
        private ConcreteElement(@androidx.annotation.Nullable final
            org.wheatgenetics.coordinate.model.ElementModel elementModel,
        @androidx.annotation.NonNull final android.widget.TextView textView,
        @java.lang.SuppressWarnings({"SameParameterValue"}) @androidx.annotation.NonNull final
            org.wheatgenetics.coordinate.display.Element.Handler handler)
        { super(elementModel, textView, handler); }

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
        new org.wheatgenetics.coordinate.display.ElementTest.ConcreteElement(
            null,null,null).clearOnLongClickListener();
    }

    @org.junit.Test() public void clearOnLongClickListenerSucceeds()
    {
        // noinspection ConstantConditions
        new org.wheatgenetics.coordinate.display.ElementTest.ConcreteElement(
            /* elementModel => */null,
            /* textView     => */ new android.widget.TextView(androidx.test.platform.app
                .InstrumentationRegistry.getInstrumentation().getTargetContext()),
            /* handler => */null).clearOnLongClickListener();
    }
    // endregion

    // region Protected Method Tests
    // region setOnClickListener() Protected Method Tests
    @org.junit.Test(expected = java.lang.NullPointerException.class)
    public void setOnClickListenerFails()
    {
        // noinspection ConstantConditions
        new org.wheatgenetics.coordinate.display.ElementTest.ConcreteElement(
            null,null,null).setOnClickListener();
    }

    @org.junit.Test() public void setOnClickListenerSucceeds()
    {
        // noinspection ConstantConditions
        new org.wheatgenetics.coordinate.display.ElementTest.ConcreteElement(
            /* elementModel => */null,
            /* textView     => */ new android.widget.TextView(androidx.test.platform.app
                .InstrumentationRegistry.getInstrumentation().getTargetContext()),
            /* handler => */null).setOnClickListener();
    }
    // endregion

    // region clearOnClickListener() Protected Method Tests
    @org.junit.Test(expected = java.lang.NullPointerException.class)
    public void clearOnClickListenerFails()
    {
        // noinspection ConstantConditions
        new org.wheatgenetics.coordinate.display.ElementTest.ConcreteElement(
            null,null,null).clearOnClickListener();
    }

    @org.junit.Test() public void clearOnClickListenerSucceeds()
    {
        // noinspection ConstantConditions
        new org.wheatgenetics.coordinate.display.ElementTest.ConcreteElement(
            /* elementModel => */null,
            /* textView     => */ new android.widget.TextView(androidx.test.platform.app
                .InstrumentationRegistry.getInstrumentation().getTargetContext()),
            /* handler => */null).clearOnClickListener();
    }
    // endregion

    // region setOnLongClickListener() Protected Method Tests
    @org.junit.Test(expected = java.lang.NullPointerException.class)
    public void setOnLongClickListenerFails()
    {
        // noinspection ConstantConditions
        new org.wheatgenetics.coordinate.display.ElementTest.ConcreteElement(
            null,null,null).setOnLongClickListener(null);
    }

    @org.junit.Test() public void setOnLongClickListenerSucceeds()
    {
        // noinspection ConstantConditions
        new org.wheatgenetics.coordinate.display.ElementTest.ConcreteElement(
            /* elementModel => */null,
            /* textView     => */ new android.widget.TextView(androidx.test.platform.app
                .InstrumentationRegistry.getInstrumentation().getTargetContext()),
            /* handler => */null).setOnLongClickListener(null);
    }
    // endregion

    @org.junit.Test() public void elementModelIsNotNullWorks()
    {
        // noinspection ConstantConditions
        org.junit.Assert.assertFalse(
            new org.wheatgenetics.coordinate.display.ElementTest.ConcreteElement(
                null,null,null).elementModelIsNotNull());

        class ElementModel extends java.lang.Object
        implements org.wheatgenetics.coordinate.model.ElementModel
        {
            @java.lang.Override @androidx.annotation.IntRange(from = 1) public int getRowValue()
            { return 2; }

            @java.lang.Override @androidx.annotation.IntRange(from = 1) public int getColValue()
            { return 3; }
        }
        final ElementModel elementModel = new ElementModel();

        // noinspection ConstantConditions
        final org.wheatgenetics.coordinate.display.ElementTest.ConcreteElement concreteElement =
            new org.wheatgenetics.coordinate.display.ElementTest.ConcreteElement(
                elementModel,null,null);
        org.junit.Assert.assertTrue(concreteElement.elementModelIsNotNull());
    }

    @org.junit.Test(expected = java.lang.NullPointerException.class)
    public void setBackgroundResourceFails()
    {
        // noinspection ConstantConditions
        new org.wheatgenetics.coordinate.display.ElementTest.ConcreteElement(
            null,null,null).setBackgroundResource(55);
    }

    @org.junit.Test(expected = java.lang.NullPointerException.class) public void toggleFails()
    {
        // noinspection ConstantConditions
        new org.wheatgenetics.coordinate.display.ElementTest.ConcreteElement(
            null,null,null).toggle();
    }
    // endregion

    // region Public Method Tests
    @org.junit.Test() public void getRowWorks()
    {
        // noinspection ConstantConditions
        org.junit.Assert.assertEquals(-1,
            new org.wheatgenetics.coordinate.display.ElementTest.ConcreteElement(
                null,null,null).getRow());
    }

    @org.junit.Test() public void getColWorks()
    {
        // noinspection ConstantConditions
        org.junit.Assert.assertEquals(-1,
            new org.wheatgenetics.coordinate.display.ElementTest.ConcreteElement(
                null,null,null).getCol());
    }
    // endregion
}