package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * org.junit.Assert
 * org.junit.Test
 *
 * org.wheatgenetics.coordinate.model.TemplateModel
 * org.wheatgenetics.coordinate.model.TemplateModels
 */
@java.lang.SuppressWarnings("ClassExplicitlyExtendsObject")
public class TemplateModelsTest extends java.lang.Object
{
    @org.junit.Test
    public void iteratorWorks()
    {
        int count = 0;
        final org.wheatgenetics.coordinate.model.TemplateModels templateModels =
            org.wheatgenetics.coordinate.model.TemplateModels.makeDefault();
        for (final org.wheatgenetics.coordinate.model.TemplateModel templateModel: templateModels)
            count++;
        org.junit.Assert.assertEquals(templateModels.size(), count);
    }

    @org.junit.Test
    public void addSucceeds()
    {
        final org.wheatgenetics.coordinate.model.TemplateModels templateModels =
            org.wheatgenetics.coordinate.model.TemplateModels.makeDefault();
        templateModels.add(new org.wheatgenetics.coordinate.model.TemplateModel(
            /* id                           => */ 4          ,
            /* title                        => */ "testTitle",
            /* code                         => */ 1          ,
            /* rows                         => */ 5          ,
            /* cols                         => */ 2          ,
            /* generatedExcludedCellsAmount => */ 0          ,
            /* initialExcludeCells          => */ null       ,
            /* excludeRows                  => */ null       ,
            /* excludeCols                  => */ null       ,
            /* colNumbering                 => */ 1          ,
            /* rowNumbering                 => */ 0          ,
            /* optionalFields               => */ null       ,
            /* timestamp                    => */ 0          ));
        org.junit.Assert.assertEquals(templateModels.size(), 3);
    }

    @org.junit.Test
    public void makeDefaultAndSizeSucceed()
    {
        org.junit.Assert.assertEquals(
            org.wheatgenetics.coordinate.model.TemplateModels.makeDefault().size(), 2);
    }

    @org.junit.Test
    public void makeDefaultAndTitlesSucceed()
    {
        final org.wheatgenetics.coordinate.model.TemplateModels templateModels =
            org.wheatgenetics.coordinate.model.TemplateModels.makeDefault();
        final java.lang.String titles[] = new java.lang.String[] {
            org.wheatgenetics.coordinate.model.TemplateModel.makeSeedDefault().getTitle() ,
            org.wheatgenetics.coordinate.model.TemplateModel.makeDNADefault ().getTitle() };
        org.junit.Assert.assertArrayEquals(templateModels.titles(), titles);
    }

    @org.junit.Test
    public void makeDefaultAndGetSucceed()
    {
        final org.wheatgenetics.coordinate.model.TemplateModels templateModels =
            org.wheatgenetics.coordinate.model.TemplateModels.makeDefault();
        org.junit.Assert.assertTrue(templateModels.get(0).equals(
            org.wheatgenetics.coordinate.model.TemplateModel.makeSeedDefault()));
        org.junit.Assert.assertTrue(templateModels.get(1).equals(
            org.wheatgenetics.coordinate.model.TemplateModel.makeDNADefault()));
    }
}