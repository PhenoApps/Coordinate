package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * android.content.res.Resources.NotFoundException
 *
 * androidx.annotation.DrawableRes
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
 * org.wheatgenetics.coordinate.model.EntryModel
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"}) public class EntryModelTest
extends java.lang.Object implements org.wheatgenetics.coordinate.StringGetter
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
        @androidx.annotation.IntRange(from = 1) final long                             gridId      ,
        @androidx.annotation.IntRange(from = 1) final int                              row         ,
        @androidx.annotation.IntRange(from = 1) final int                              col         ,
        @androidx.annotation.NonNull   final org.wheatgenetics.coordinate.StringGetter stringGetter)
        { super(gridId, row, col, stringGetter); }

        ConcreteEntryModel(
        @androidx.annotation.IntRange(from = 1) final long id    ,
        @androidx.annotation.IntRange(from = 1) final long gridId,
        @androidx.annotation.IntRange(from = 1) final int  row   ,
        @androidx.annotation.IntRange(from = 1) final int  col   ,
        @androidx.annotation.IntRange(from = 0) @java.lang.SuppressWarnings({"SameParameterValue"})
            final long timestamp,
        @androidx.annotation.NonNull final org.wheatgenetics.coordinate.StringGetter stringGetter)
        { super(id, gridId, row, col, timestamp, stringGetter); }
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

    // region org.wheatgenetics.coordinate.StringGetter Overridden Methods
    @java.lang.Override @androidx.annotation.Nullable public java.lang.String get(
    @androidx.annotation.StringRes final int resId)
    {
        switch (resId)
        {
            case org.wheatgenetics.coordinate.R.string.ModelIdMustBeGreaterThanZero:
                return "id must be > 0";

            case org.wheatgenetics.coordinate.R.string.UtilsInvalidValue:
                return "value must be >= %d";

            default: org.junit.Assert.fail(); return null;
        }
    }

    @java.lang.Override @androidx.annotation.NonNull public java.lang.String getQuantity(
    @androidx.annotation.PluralsRes         final int                 resId     ,
    @androidx.annotation.IntRange(from = 0) final int                 quantity  ,
    @androidx.annotation.Nullable           final java.lang.Object... formatArgs)
    throws android.content.res.Resources.NotFoundException { org.junit.Assert.fail(); return null; }
    // endregion

    // region Constructor Tests
    // region First Constructor Tests
    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void badGridIdFirstConstructorFails()
    {
        new org.wheatgenetics.coordinate.model.EntryModelTest.ConcreteEntryModel(
            0,1,1,this);
    }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void badRowFirstConstructorFails()
    {
        new org.wheatgenetics.coordinate.model.EntryModelTest.ConcreteEntryModel(
            1,0,1,this);
    }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void badColFirstConstructorFails()
    {
        new org.wheatgenetics.coordinate.model.EntryModelTest.ConcreteEntryModel(
            1,1,0,this);
    }

    @org.junit.Test() public void firstConstructorSucceeds()
    {
        new org.wheatgenetics.coordinate.model.EntryModelTest.ConcreteEntryModel(
            1,1,1,this);
    }
    // endregion

    // region Second Constructor Tests
    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void badIdSecondConstructorFails()
    {
        new org.wheatgenetics.coordinate.model.EntryModelTest.ConcreteEntryModel(
            0,1,1,1,1,this);
    }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void badGridIdSecondConstructorFails()
    {
        new org.wheatgenetics.coordinate.model.EntryModelTest.ConcreteEntryModel(
            1,0,1,1,1,this);
    }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void badRowSecondConstructorFails()
    {
        new org.wheatgenetics.coordinate.model.EntryModelTest.ConcreteEntryModel(
            1,1,0,1,1,this);
    }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void badColSecondConstructorFails()
    {
        new org.wheatgenetics.coordinate.model.EntryModelTest.ConcreteEntryModel(
            1,1,1,0,1,this);
    }

    @org.junit.Test() public void secondConstructorSucceeds()
    {
        new org.wheatgenetics.coordinate.model.EntryModelTest.ConcreteEntryModel(
            1,1,1,1,1,this);
    }
    // endregion
    // endregion

    // region org.wheatgenetics.coordinate.model.ElementModel Overridden Method Tests
    @org.junit.Test() public void getRowValueSucceeds()
    {
        org.junit.Assert.assertEquals(2,
            new org.wheatgenetics.coordinate.model.EntryModelTest.ConcreteEntryModel(
                1,2,3,this).getRowValue());
    }

    @org.junit.Test() public void getColValueSucceeds()
    {
        org.junit.Assert.assertEquals(3,
            new org.wheatgenetics.coordinate.model.EntryModelTest.ConcreteEntryModel(
                1,2,3,this).getColValue());
    }
    // endregion

    @org.junit.Test() public void getDNAExportValueSucceeds()
    {
        final java.lang.String sample_id = "sample_id";
        org.junit.Assert.assertEquals("BLANK_" + sample_id,
            new org.wheatgenetics.coordinate.model.EntryModelTest.ConcreteEntryModel(
                1,1,1,this).getDNAExportValue(sample_id));
    }
}