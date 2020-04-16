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
 * org.wheatgenetics.coordinate.model.ProjectModels
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"}) public class ProjectModelsTest
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
    @org.junit.Test() public void addAndGetAndSizeWork()
    {
        final org.wheatgenetics.coordinate.model.ProjectModels projectModels =
            new org.wheatgenetics.coordinate.model.ProjectModels();
        {
            final org.wheatgenetics.coordinate.model.ProjectModel projectModel =
                new org.wheatgenetics.coordinate.model.ProjectModel(
                    "testTitle",this);
            projectModels.add(projectModel);
            org.junit.Assert.assertEquals(projectModel, projectModels.get(0));
        }
        org.junit.Assert.assertEquals(1, projectModels.size());
        org.junit.Assert.assertNull  (projectModels.get(999));
    }

    @org.junit.Test() public void getWorks()
    { org.junit.Assert.assertNull(new org.wheatgenetics.coordinate.model.ProjectModels().get(0)); }

    @org.junit.Test() public void titlesWorks()
    {
        org.junit.Assert.assertNull(
            new org.wheatgenetics.coordinate.model.ProjectModels().titles());
    }

    @org.junit.Test() public void titlesSucceeds()
    {
        // noinspection CStyleArrayDeclaration
        final java.lang.String                                 expected[];
        final org.wheatgenetics.coordinate.model.ProjectModels projectModels =
            new org.wheatgenetics.coordinate.model.ProjectModels();
        {
            final java.lang.String testTitle1 = "testTitle1", testTitle2 = "testTitle2";
            expected = new java.lang.String[]{testTitle1, testTitle2};
            projectModels.add(new org.wheatgenetics.coordinate.model.ProjectModel(
                testTitle1,this));
            projectModels.add(new org.wheatgenetics.coordinate.model.ProjectModel(
                testTitle2,this));
        }
        org.junit.Assert.assertArrayEquals(expected, projectModels.titles());
    }
    // endregion
}