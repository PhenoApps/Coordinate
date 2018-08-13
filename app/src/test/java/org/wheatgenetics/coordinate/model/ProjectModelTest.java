package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * org.junit.Assert
 * org.junit.Test
 *
 * org.wheatgenetics.coordinate.model.ProjectModel
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
public class ProjectModelTest extends java.lang.Object
{
    @org.junit.Test() public void secondConstructorAndGetTitleSucceed()
    {
        final java.lang.String testTitle = "testTitle";
        org.junit.Assert.assertTrue(testTitle.equals(
            new org.wheatgenetics.coordinate.model.ProjectModel(testTitle).getTitle()));
    }

    @org.junit.Test() public void thirdConstructorAndGetIdSucceed()
    {
        final long testId = 8;
        org.junit.Assert.assertEquals(testId, new org.wheatgenetics.coordinate.model.ProjectModel(
            testId,"testTitle",123).getId());
    }

    @org.junit.Test() public void thirdConstructorAndGetTimestampSucceed()
    {
        final long testTimestamp = 123;
        org.junit.Assert.assertEquals(testTimestamp,
            new org.wheatgenetics.coordinate.model.ProjectModel(
                8,"testTitle", testTimestamp).getTimestamp());
    }
}