package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * org.junit.Assert
 * org.junit.Test
 *
 * org.wheatgenetics.coordinate.model.TemplateModel
 * org.wheatgenetics.coordinate.model.TemplateModels
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
public class TemplateModelsTest extends java.lang.Object
{
    @org.junit.Test public void iteratorWorks()
    {
        int count = 0;
        final org.wheatgenetics.coordinate.model.TemplateModels templateModels =
            org.wheatgenetics.coordinate.model.TemplateModels.makeDefault();
        for (final org.wheatgenetics.coordinate.model.TemplateModel templateModel: templateModels)
            count++;
        org.junit.Assert.assertEquals(count, templateModels.size());
    }

    // region Public Method Tests
    @org.junit.Test public void addAndSizeAndGetWork()
    {
        final org.wheatgenetics.coordinate.model.TemplateModels templateModels =
            org.wheatgenetics.coordinate.model.TemplateModels.makeDefault();
        {
            final org.wheatgenetics.coordinate.model.TemplateModel templateModel =
                new org.wheatgenetics.coordinate.model.TemplateModel(
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
                    /* entryLabel                   => */ null       ,
                    /* optionalFields               => */ null       ,
                    /* timestamp                    => */ 0          );
            assert null != templateModels; templateModels.add(templateModel);
            org.junit.Assert.assertEquals(templateModel, templateModels.get(2));
        }
        org.junit.Assert.assertEquals(templateModels.size(), 3);
        org.junit.Assert.assertNull  (templateModels.get(999) );
    }

    @org.junit.Test public void getWorks()
    { org.junit.Assert.assertNull(new org.wheatgenetics.coordinate.model.TemplateModels().get(0)); }

    @org.junit.Test public void makeDefaultAndSizeSucceed()
    {
        org.junit.Assert.assertEquals(
            org.wheatgenetics.coordinate.model.TemplateModels.makeDefault().size(), 2);
    }

    @org.junit.Test public void titlesWorks()
    {
        org.junit.Assert.assertNull(
            new org.wheatgenetics.coordinate.model.TemplateModels().titles());
    }

    @org.junit.Test public void titlesAndMakeDefaultSucceed()
    {
        final java.lang.String expected[] = new java.lang.String[]{
            org.wheatgenetics.coordinate.model.TemplateModel.makeSeedDefault().getTitle(),
            org.wheatgenetics.coordinate.model.TemplateModel.makeDNADefault ().getTitle()};
        final org.wheatgenetics.coordinate.model.TemplateModels templateModels =
            org.wheatgenetics.coordinate.model.TemplateModels.makeDefault();
        org.junit.Assert.assertArrayEquals(expected, templateModels.titles());
    }

    @org.junit.Test public void makeDefaultAndGetSucceed()
    {
        final org.wheatgenetics.coordinate.model.TemplateModels templateModels =
            org.wheatgenetics.coordinate.model.TemplateModels.makeDefault();
        org.junit.Assert.assertTrue(templateModels.get(0).equals(
            org.wheatgenetics.coordinate.model.TemplateModel.makeSeedDefault()));
        org.junit.Assert.assertTrue(templateModels.get(1).equals(
            org.wheatgenetics.coordinate.model.TemplateModel.makeDNADefault()));
    }
    // endregion
}