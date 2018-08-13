package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * org.junit.Assert
 * org.junit.Test
 *
 * org.wheatgenetics.coordinate.model.ProjectModel
 * org.wheatgenetics.coordinate.model.ProjectModels
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
public class ProjectModelsTest extends java.lang.Object
{
    @org.junit.Test() public void addAndSizeAndGetWork()
    {
        final org.wheatgenetics.coordinate.model.ProjectModels projectModels =
            new org.wheatgenetics.coordinate.model.ProjectModels();
        {
            final org.wheatgenetics.coordinate.model.ProjectModel projectModel =
                new org.wheatgenetics.coordinate.model.ProjectModel("testTitle");
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
        final java.lang.String                                 expected[];
        final org.wheatgenetics.coordinate.model.ProjectModels projectModels =
            new org.wheatgenetics.coordinate.model.ProjectModels();
        {
            final java.lang.String testTitle1 = "testTitle1", testTitle2 = "testTitle2";
            expected = new java.lang.String[]{testTitle1, testTitle2};
            projectModels.add(new org.wheatgenetics.coordinate.model.ProjectModel(testTitle1));
            projectModels.add(new org.wheatgenetics.coordinate.model.ProjectModel(testTitle2));
        }
        org.junit.Assert.assertArrayEquals(expected, projectModels.titles());
    }
}