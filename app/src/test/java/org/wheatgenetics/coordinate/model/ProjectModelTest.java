package org.wheatgenetics.coordinate.model;

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
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"}) public class ProjectModelTest
extends java.lang.Object implements org.wheatgenetics.coordinate.StringGetter
{
    // region org.wheatgenetics.coordinate.StringGetter Overridden Methods
    @java.lang.Override @androidx.annotation.Nullable public java.lang.String get(
    @androidx.annotation.StringRes final int resId)
    {
        // noinspection SwitchStatementWithTooFewBranches
        switch (resId) { default: org.junit.Assert.fail(); return null; }
    }

    @java.lang.Override @androidx.annotation.NonNull public java.lang.String getQuantity(
    @androidx.annotation.PluralsRes         final int                 resId     ,
    @androidx.annotation.IntRange(from = 0) final int                 quantity  ,
    @androidx.annotation.Nullable           final java.lang.Object... formatArgs)
    throws android.content.res.Resources.NotFoundException { org.junit.Assert.fail(); return null; }
    // endregion

    // region Tests
    @org.junit.Test() public void secondConstructorAndGetTitleSucceed()
    {
        final java.lang.String testTitle = "testTitle";
        org.junit.Assert.assertEquals(testTitle, new
            org.wheatgenetics.coordinate.model.ProjectModel(testTitle,this).getTitle());
    }

    @org.junit.Test() public void thirdConstructorAndGetIdSucceed()
    {
        final long testId = 8;
        org.junit.Assert.assertEquals(testId, new org.wheatgenetics.coordinate.model.ProjectModel(
            testId,"testTitle",123,this).getId());
    }

    @org.junit.Test() public void thirdConstructorAndGetTimestampSucceed()
    {
        final long testTimestamp = 123;
        org.junit.Assert.assertEquals(testTimestamp,
            new org.wheatgenetics.coordinate.model.ProjectModel(
                8,"testTitle", testTimestamp,this).getTimestamp());
    }
    // endregion
}