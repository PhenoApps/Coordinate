package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * androidx.annotation.DrawableRes
 * androidx.annotation.IntRange
 *
 * org.junit.Assert
 * org.junit.Test
 *
 * org.wheatgenetics.coordinate.model.EntryModel
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
public class EntryModelTest extends java.lang.Object
{
    /**
     * This class was defined in order to test EntryModel.  Why not just test EntryModel directly?
     * Because EntryModel is abstract.  Why does that matter?  Because I can't instantiate an
     * abstract class.  If I can't instantiate it I can't test it.
     */
    private static class ConcreteEntryModel extends org.wheatgenetics.coordinate.model.EntryModel
    {
        // region Constructors
        ConcreteEntryModel(
        @androidx.annotation.IntRange(from = 1) final long gridId,
        @androidx.annotation.IntRange(from = 1) final int  row   ,
        @androidx.annotation.IntRange(from = 1) final int  col   )
        { super(gridId, row, col); }

        ConcreteEntryModel(
        @androidx.annotation.IntRange(from = 1) final long id    ,
        @androidx.annotation.IntRange(from = 1) final long gridId,
        @androidx.annotation.IntRange(from = 1) final int  row   ,
        @androidx.annotation.IntRange(from = 1) final int  col   ,
        @androidx.annotation.IntRange(from = 0) @java.lang.SuppressWarnings({"SameParameterValue"})
            final long timestamp) { super(id, gridId, row, col, timestamp); }
        // endregion

        // region Overridden Methods
        @java.lang.Override java.lang.String getSeedExportValue() { return "seedExportValue"; }

        @java.lang.Override java.lang.String getUserDefinedExportValue()
        { return "userDefinedExportValue"; }

        @java.lang.Override public java.lang.String getValue          () { return "value"        ; }
        @java.lang.Override public java.lang.String getDatabaseValue  () { return "databaseValue"; }

        @java.lang.Override @androidx.annotation.DrawableRes public int backgroundResource()
        { return 0; }
        // endregion
    }

    // region Constructor Tests
    // region First Constructor Tests
    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void badGridIdFirstConstructorFails()
    {
        new org.wheatgenetics.coordinate.model.EntryModelTest.ConcreteEntryModel(
            0,1,1);
    }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void badRowFirstConstructorFails()
    {
        new org.wheatgenetics.coordinate.model.EntryModelTest.ConcreteEntryModel(
            1,0,1);
    }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void badColFirstConstructorFails()
    {
        new org.wheatgenetics.coordinate.model.EntryModelTest.ConcreteEntryModel(
            1,1,0);
    }

    @org.junit.Test() public void firstConstructorSucceeds()
    {
        new org.wheatgenetics.coordinate.model.EntryModelTest.ConcreteEntryModel(
            1,1,1);
    }
    // endregion

    // region Second Constructor Tests
    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void badIdSecondConstructorFails()
    {
        new org.wheatgenetics.coordinate.model.EntryModelTest.ConcreteEntryModel(
            0,1,1,1,1);
    }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void badGridIdSecondConstructorFails()
    {
        new org.wheatgenetics.coordinate.model.EntryModelTest.ConcreteEntryModel(
            1,0,1,1,1);
    }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void badRowSecondConstructorFails()
    {
        new org.wheatgenetics.coordinate.model.EntryModelTest.ConcreteEntryModel(
            1,1,0,1,1);
    }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void badColSecondConstructorFails()
    {
        new org.wheatgenetics.coordinate.model.EntryModelTest.ConcreteEntryModel(
            1,1,1,0,1);
    }

    @org.junit.Test() public void secondConstructorSucceeds()
    {
        new org.wheatgenetics.coordinate.model.EntryModelTest.ConcreteEntryModel(
            1,1,1,1,1);
    }
    // endregion
    // endregion

    // region org.wheatgenetics.coordinate.model.ElementModel Overridden Method Tests
    @org.junit.Test() public void getRowValueSucceeds()
    {
        org.junit.Assert.assertEquals(2,
            new org.wheatgenetics.coordinate.model.EntryModelTest.ConcreteEntryModel(
                1,2,3).getRowValue());
    }

    @org.junit.Test() public void getColValueSucceeds()
    {
        org.junit.Assert.assertEquals(3,
            new org.wheatgenetics.coordinate.model.EntryModelTest.ConcreteEntryModel(
                1,2,3).getColValue());
    }
    // endregion

    @org.junit.Test() public void getDNAExportValueSucceeds()
    {
        final java.lang.String sample_id = "sample_id";
        org.junit.Assert.assertEquals("BLANK_" + sample_id,
            new org.wheatgenetics.coordinate.model.EntryModelTest.ConcreteEntryModel(
                1,1,1).getDNAExportValue(sample_id));
    }
}