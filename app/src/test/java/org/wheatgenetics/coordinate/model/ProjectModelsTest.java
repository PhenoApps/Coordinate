package org.wheatgenetics.coordinate.model;

import android.content.res.Resources;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.PluralsRes;
import androidx.annotation.StringRes;

import org.junit.Assert;
import org.junit.Test;
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
 * org.wheatgenetics.coordinate.StringGetter
 *
 * org.wheatgenetics.coordinate.model.ProjectModel
 * org.wheatgenetics.coordinate.model.ProjectModels
 */
@SuppressWarnings({"ClassExplicitlyExtendsObject"}) public class ProjectModelsTest
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

    // region Tests
    @Test() public void addAndGetAndSizeWork()
    {
        final ProjectModels projectModels =
            new ProjectModels();
        {
            final ProjectModel projectModel =
                new ProjectModel(
                    "testTitle",this);
            projectModels.add(projectModel);
            Assert.assertEquals(projectModel, projectModels.get(0));
        }
        Assert.assertEquals(1, projectModels.size());
        Assert.assertNull  (projectModels.get(999));
    }

    @Test() public void getWorks()
    { Assert.assertNull(new ProjectModels().get(0)); }

    @Test() public void titlesWorks()
    {
        Assert.assertNull(
            new ProjectModels().titles());
    }

    @Test() public void titlesSucceeds()
    {
        // noinspection CStyleArrayDeclaration
        final String                                 expected[];
        final ProjectModels projectModels =
            new ProjectModels();
        {
            final String testTitle1 = "testTitle1", testTitle2 = "testTitle2";
            expected = new String[]{testTitle1, testTitle2};
            projectModels.add(new ProjectModel(
                testTitle1,this));
            projectModels.add(new ProjectModel(
                testTitle2,this));
        }
        Assert.assertArrayEquals(expected, projectModels.titles());
    }
    // endregion
}