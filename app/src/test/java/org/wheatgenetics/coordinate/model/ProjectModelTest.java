package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * org.junit.Assert
 * org.junit.Test
 *
 * org.wheatgenetics.coordinate.model.ProjectModel
 */
@java.lang.SuppressWarnings("ClassExplicitlyExtendsObject")
public class ProjectModelTest extends java.lang.Object
{
    @org.junit.Test
    public void secondConstructorAndGetTitleSucceed()
    {
        final java.lang.String testTitle = "testTitle";
        org.junit.Assert.assertEquals(testTitle,
            new org.wheatgenetics.coordinate.model.ProjectModel(testTitle).getTitle());
    }

    @org.junit.Test
    public void thirdConstructorSucceeds()
    {
        final long testId = 8;
        org.junit.Assert.assertEquals(testId,
            new org.wheatgenetics.coordinate.model.ProjectModel(testId, "testTitle", 123).getId());
    }
}