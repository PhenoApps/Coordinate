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
@SuppressWarnings({"ClassExplicitlyExtendsObject"}) public class IncludedEntryModelTest
extends Object implements StringGetter
{
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

    @Test() public void thirdConstructorSucceeds()
    {
        final long                                                  gridId = 23       ;
        final IncludedEntryModel includedEntryModel;
        {
            final int row = 2, col = 3;
            includedEntryModel = new IncludedEntryModel(
                new ExcludedEntryModel(
                    5, gridId, row, col,6,this),this);
            Assert.assertEquals(row, includedEntryModel.getRow());
            Assert.assertEquals(col, includedEntryModel.getCol());
        }
        Assert.assertEquals(gridId, includedEntryModel.getGridId());
    }

    // region Overridden Method Tests
    @Test() public void getSeedExportValueSucceeds()
    {
        final IncludedEntryModel includedEntryModel =
            new IncludedEntryModel(
                23,2,3,this);
        Assert.assertEquals("BLANK_", includedEntryModel.getSeedExportValue());

        final String value = "value";
        includedEntryModel.setValue(value);
        Assert.assertEquals(value, includedEntryModel.getSeedExportValue());
    }

    @Test() public void getDNAExportValueSucceeds()
    {
        final IncludedEntryModel includedEntryModel =
            new IncludedEntryModel(
                23,2,3,this);
        final String sample_id = "12";
        {
            final String expected = "BLANK_" + sample_id;
            Assert.assertEquals(expected,
                includedEntryModel.getDNAExportValue(sample_id));

            includedEntryModel.setValue("");
            Assert.assertEquals(expected,
                includedEntryModel.getDNAExportValue(sample_id));
        }
        final String value = "value";
        includedEntryModel.setValue(value);                                                // throws
        Assert.assertEquals(value, includedEntryModel.getDNAExportValue(sample_id));
    }

    @Test() public void getUserDefinedExportValueSucceeds()
    {
        final IncludedEntryModel includedEntryModel =
            new IncludedEntryModel(
                23,2,3,this);
        Assert.assertNotNull(includedEntryModel.getUserDefinedExportValue());

        final String value = "value";
        includedEntryModel.setValue(value);
        Assert.assertEquals(value, includedEntryModel.getUserDefinedExportValue());
    }

    @Test() public void getValueSucceeds()
    {
        final String value = "value";
        Assert.assertEquals(value,
            new IncludedEntryModel(
                5,23,2,3, value,6,this).getValue());
    }

    @Test() public void getDatabaseValueSucceeds()
    {
        final String value = "value";
        Assert.assertEquals(value,
            new IncludedEntryModel(
                5,23,2,3, value,6,this).getDatabaseValue());
    }

    @Test() public void backgroundResourceSucceeds()
    {
        final IncludedEntryModel includedEntryModel =
            new IncludedEntryModel(
                23,2,3,this);
        Assert.assertEquals(R.drawable.empty_included_entry,
            includedEntryModel.backgroundResource());

        includedEntryModel.setValue("value");
        Assert.assertEquals(R.drawable.full_included_entry,
            includedEntryModel.backgroundResource());
    }
    // endregion

    // region Public Method Tests
    @Test() public void setValueSucceeds()
    {
        final IncludedEntryModel includedEntryModel =
            new IncludedEntryModel(
                23,2,3,this);
        Assert.assertNull(includedEntryModel.getValue());
        includedEntryModel.setValue(null);
        Assert.assertNull(includedEntryModel.getValue());

        final String value = "value";
        includedEntryModel.setValue("  " + value + "   ");                                 // Trims.
        Assert.assertEquals(value, includedEntryModel.getValue());
    }

    @Test() public void valueIsEmptySucceeds()
    {
        final IncludedEntryModel includedEntryModel =
            new IncludedEntryModel(
                23,2,3,this);
        Assert.assertTrue(includedEntryModel.valueIsEmpty());
        includedEntryModel.setValue("value");
        Assert.assertFalse(includedEntryModel.valueIsEmpty());
    }
    // endregion
}