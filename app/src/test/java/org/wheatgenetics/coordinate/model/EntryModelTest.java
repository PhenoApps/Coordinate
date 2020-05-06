package org.wheatgenetics.coordinate.model;

import android.content.res.Resources;

import androidx.annotation.DrawableRes;
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
@SuppressWarnings({"ClassExplicitlyExtendsObject"}) public class EntryModelTest
extends Object implements StringGetter
{
    /**
     * This class was defined in order to test EntryModel.  Why not just test EntryModel directly?
     * Because EntryModel is abstract.  Why does that matter?  Because I can't instantiate an
     * abstract class.  If I can't instantiate it I can't test it.
     */
    private static class ConcreteEntryModel extends EntryModel
    {
        // region Constructors
        ConcreteEntryModel(
        @IntRange(from = 1) final long                             gridId      ,
        @IntRange(from = 1) final int                              row         ,
        @IntRange(from = 1) final int                              col         ,
        @NonNull   final StringGetter stringGetter)
        { super(gridId, row, col, stringGetter); }

        ConcreteEntryModel(
        @IntRange(from = 1) final long id    ,
        @IntRange(from = 1) final long gridId,
        @IntRange(from = 1) final int  row   ,
        @IntRange(from = 1) final int  col   ,
        @IntRange(from = 0) @SuppressWarnings({"SameParameterValue"})
            final long timestamp,
        @NonNull final StringGetter stringGetter)
        { super(id, gridId, row, col, timestamp, stringGetter); }
        // endregion

        // region Overridden Methods
        @Override String getSeedExportValue() { return "seedExportValue"; }

        @Override String getUserDefinedExportValue()
        { return "userDefinedExportValue"; }

        @Override public String getValue          () { return "value"        ; }
        @Override public String getDatabaseValue  () { return "databaseValue"; }

        @Override @DrawableRes public int backgroundResource()
        { return 0; }
        // endregion
    }

    // region org.wheatgenetics.coordinate.StringGetter Overridden Methods
    @Override @Nullable public String get(
    @StringRes final int resId)
    {
        switch (resId)
        {
            case R.string.ModelIdMustBeGreaterThanZero:
                return "id must be > 0";

            case R.string.UtilsInvalidValue:
                return "value must be >= %d";

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
    // region First Constructor Tests
    @Test(expected = IllegalArgumentException.class)
    public void badGridIdFirstConstructorFails()
    {
        new EntryModelTest.ConcreteEntryModel(
            0,1,1,this);
    }

    @Test(expected = IllegalArgumentException.class)
    public void badRowFirstConstructorFails()
    {
        new EntryModelTest.ConcreteEntryModel(
            1,0,1,this);
    }

    @Test(expected = IllegalArgumentException.class)
    public void badColFirstConstructorFails()
    {
        new EntryModelTest.ConcreteEntryModel(
            1,1,0,this);
    }

    @Test() public void firstConstructorSucceeds()
    {
        new EntryModelTest.ConcreteEntryModel(
            1,1,1,this);
    }
    // endregion

    // region Second Constructor Tests
    @Test(expected = IllegalArgumentException.class)
    public void badIdSecondConstructorFails()
    {
        new EntryModelTest.ConcreteEntryModel(
            0,1,1,1,1,this);
    }

    @Test(expected = IllegalArgumentException.class)
    public void badGridIdSecondConstructorFails()
    {
        new EntryModelTest.ConcreteEntryModel(
            1,0,1,1,1,this);
    }

    @Test(expected = IllegalArgumentException.class)
    public void badRowSecondConstructorFails()
    {
        new EntryModelTest.ConcreteEntryModel(
            1,1,0,1,1,this);
    }

    @Test(expected = IllegalArgumentException.class)
    public void badColSecondConstructorFails()
    {
        new EntryModelTest.ConcreteEntryModel(
            1,1,1,0,1,this);
    }

    @Test() public void secondConstructorSucceeds()
    {
        new EntryModelTest.ConcreteEntryModel(
            1,1,1,1,1,this);
    }
    // endregion
    // endregion

    // region org.wheatgenetics.coordinate.model.ElementModel Overridden Method Tests
    @Test() public void getRowValueSucceeds()
    {
        Assert.assertEquals(2,
            new EntryModelTest.ConcreteEntryModel(
                1,2,3,this).getRowValue());
    }

    @Test() public void getColValueSucceeds()
    {
        Assert.assertEquals(3,
            new EntryModelTest.ConcreteEntryModel(
                1,2,3,this).getColValue());
    }
    // endregion

    @Test() public void getDNAExportValueSucceeds()
    {
        final String sample_id = "sample_id";
        Assert.assertEquals("BLANK_" + sample_id,
            new EntryModelTest.ConcreteEntryModel(
                1,1,1,this).getDNAExportValue(sample_id));
    }
}