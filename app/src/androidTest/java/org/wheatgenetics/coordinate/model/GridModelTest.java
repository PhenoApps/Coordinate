package org.wheatgenetics.coordinate.model;

import android.content.res.Resources;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.PluralsRes;
import androidx.annotation.StringRes;

import org.junit.Assert;
import org.junit.Test;
import org.phenoapps.androidlibrary.Utils;
import org.wheatgenetics.coordinate.R;
import org.wheatgenetics.coordinate.StringGetter;

/**
 * Uses:
 * android.content.res.Resources.NotFoundException
 *
 * androidx.annotation.IntRange
 * androidx.annotation.Nullable
 * androidx.annotation.PluralsRes
 * androidx.annotation.StringRes
 *
 * org.junit.Assert
 * org.junit.Test
 *
 * org.wheatgenetics.androidlibrary.Utils
 *
 * org.wheatgenetics.coordinate.R
 * org.wheatgenetics.coordinate.StringGetter
 *
 * org.wheatgenetics.coordinate.model.GridModel
 */
@SuppressWarnings({"ClassExplicitlyExtendsObject"}) public class GridModelTest
extends Object implements StringGetter
{
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
    public void badTemplateIdFirstConstructorFails()
    {
        new GridModel(
            0,0,"person",null,this);
    }

    @Test() public void badProjectIdFirstConstructorWorks()
    {
        Assert.assertEquals(0, new GridModel(
            8,-10,"person",null,this)
                .getProjectId());
    }

    @Test() public void firstConstructorAndGettersSucceed()
    {
        final long                                         templateId = 67;
        final GridModel gridModel      ;
        {
            final String person = "person";
            gridModel = new GridModel(
                templateId,0, person,null,this);
            Assert.assertEquals(person, gridModel.getPerson());
        }
        Assert.assertEquals(templateId, gridModel.getTemplateId());
    }
    // endregion

    // region Second Constructor Tests
    @Test(expected = IllegalArgumentException.class)
    public void badIdSecondConstructorFails()
    {
        new GridModel(-1,1,0,
            "abc",0,0,null,this,888);
    }

    @Test(expected = IllegalArgumentException.class)
    public void badTemplateIdSecondConstructorFails()
    {
        new GridModel(1,-1,0,
            "abc",0,0,null,this,888);
    }

    @Test() public void badProjectIdSecondConstructorWorks()
    {
        Assert.assertEquals(0, new GridModel(
            1,1,-10,"abc",0,0,
            null,this,888).getProjectId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void badActiveRowSecondConstructorFails()
    {
        new GridModel(1,1,0,
            "abc",-1,0,null,this,888);
    }

    @Test(expected = IllegalArgumentException.class)
    public void badActiveColSecondConstructorFails()
    {
        new GridModel(1,1,0,
            "abc",0,-1,null,this,888);
    }

    @Test() public void secondConstructorAndGettersSucceed()
    {
        final long                                         timestamp = 888;
        final GridModel gridModel      ;
        {
            final String person = "abc";
            gridModel = new GridModel(
                1,1,0, person,0,
                0,null,this, timestamp);
            Assert.assertEquals(person, gridModel.getPerson());
        }
        Assert.assertEquals(timestamp, gridModel.getTimestamp());
    }
    // endregion
    // endregion

    // region Public Method Tests
    @Test() public void getFormattedTimestamp()
    {
        final long                                         timestamp = 888;
        final GridModel gridModel =
            new GridModel(1,5,0,
                "abc",0,0,null,this, timestamp);
        Assert.assertEquals(
            Utils.formatDate(gridModel.getTimestamp()),
            gridModel.getFormattedTimestamp()                                          );
    }

    @Test() public void optionalFieldsMethodsSucceed()
    {
        final GridModel gridModel =
            new GridModel(
                /* templateId     => */10,
                /* projectId      => */10,
                /* person         => */"testPerson",
                /* optionalFields => */null,
                /* stringGetter   => */ this);
        Assert.assertNull(gridModel.optionalFields                 ());
        Assert.assertNull(gridModel.optionalFieldsAsJson           ());
        Assert.assertNull(gridModel.getFirstOptionalFieldDatedValue());
    }
    // endregion
}