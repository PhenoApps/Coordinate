package org.wheatgenetics.coordinate.model;

import android.content.res.Resources;

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
 * org.wheatgenetics.coordinate.model.ExcludedEntryModel
 * org.wheatgenetics.coordinate.model.IncludedEntryModel
 */
@SuppressWarnings({"ClassExplicitlyExtendsObject"}) public class ExcludedEntryModelTest
extends Object implements StringGetter
{
    @Test() public void thirdConstructorSucceeds()
    {
        final long                                                  gridId = 23       ;
        final ExcludedEntryModel excludedEntryModel;
        {
            final int row = 2, col = 3;
            excludedEntryModel = new ExcludedEntryModel(
                new IncludedEntryModel(5, gridId,
                    row, col,"value",6,this),this);
            Assert.assertEquals(row, excludedEntryModel.getRow());
            Assert.assertEquals(col, excludedEntryModel.getCol());
        }
        Assert.assertEquals(gridId, excludedEntryModel.getGridId());
    }

    // region org.wheatgenetics.coordinate.StringGetter Overridden Methods
    @Override @Nullable public String get(
    @StringRes final int resId)
    {
        // noinspection SwitchStatementWithTooFewBranches
        switch (resId) { default: Assert.fail(); return null; }
    }

    @Override @NonNull public String getQuantity(
    @PluralsRes         final int                 resId     ,
    @IntRange(from = 0) final int                 quantity  ,
    @Nullable           final Object... formatArgs)
    throws Resources.NotFoundException { Assert.fail(); return null; }
    // endregion

    // region Overridden Method Tests
    @Test() public void getSeedExportValueSucceeds()
    {
        Assert.assertEquals("exclude",
            new ExcludedEntryModel(
                1,1,1,this).getSeedExportValue());
    }

    @Test() public void getUserDefinedExportValueSucceeds()
    {
        Assert.assertEquals("exclude",
            new ExcludedEntryModel(
                1,1,1,this).getUserDefinedExportValue());
    }

    @Test() public void getValueWorks()
    {
        Assert.assertNull(new ExcludedEntryModel(
            1,1,1,this).getValue());
    }

    @Test() public void getDatabaseValueSucceeds()
    {
        Assert.assertEquals("excluded",
            new ExcludedEntryModel(
                1,1,1,this).getDatabaseValue());
    }

    @Test() public void backgroundResourceSucceeds()
    {
        Assert.assertEquals(R.drawable.excluded_entry,
            new ExcludedEntryModel(
                1,1,1,this).backgroundResource());
    }
    // endregion
}