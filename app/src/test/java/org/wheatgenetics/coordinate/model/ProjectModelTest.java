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
 */
@SuppressWarnings({"ClassExplicitlyExtendsObject"}) public class ProjectModelTest
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
    @Test() public void secondConstructorAndGetTitleSucceed()
    {
        final String testTitle = "testTitle";
        Assert.assertEquals(testTitle, new
            ProjectModel(testTitle,this).getTitle());
    }

    @Test() public void thirdConstructorAndGetIdSucceed()
    {
        final long testId = 8;
        Assert.assertEquals(testId, new ProjectModel(
            testId,"testTitle",123,this).getId());
    }

    @Test() public void thirdConstructorAndGetTimestampSucceed()
    {
        final long testTimestamp = 123;
        Assert.assertEquals(testTimestamp,
            new ProjectModel(
                8,"testTitle", testTimestamp,this).getTimestamp());
    }
    // endregion
}