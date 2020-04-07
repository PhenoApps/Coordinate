package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * androidx.annotation.Nullable
 * androidx.annotation.StringRes
 *
 * org.junit.Assert
 * org.junit.Test
 *
 * org.wheatgenetics.coordinate.R
 * org.wheatgenetics.coordinate.StringGetter
 *
 * org.wheatgenetics.coordinate.model.TemplateModel
 * org.wheatgenetics.coordinate.model.TemplateModels
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"}) public class TemplateModelsTest
extends java.lang.Object implements org.wheatgenetics.coordinate.StringGetter
{
    // region org.wheatgenetics.coordinate.StringGetter Overridden Method
    @java.lang.Override @androidx.annotation.Nullable public java.lang.String get(
    @androidx.annotation.StringRes final int resId)
    {
        switch (resId)
        {
            case org.wheatgenetics.coordinate.R.string.BaseOptionalFieldPersonFieldName:
                return "Person";

            case org.wheatgenetics.coordinate.R.string.BaseOptionalFieldNameFieldName:
                return "Name";

            case org.wheatgenetics.coordinate.R.string.BaseOptionalFieldIdentificationFieldName:
                return "Identification";

            default: return null;
        }
    }
    // endregion

    @org.junit.Test() public void iteratorWorks()
    {
              int                                               count          = 0;
        final org.wheatgenetics.coordinate.model.TemplateModels templateModels =
            org.wheatgenetics.coordinate.model.TemplateModels.makeDefault(this);

        // noinspection UnusedParameters
        for (final org.wheatgenetics.coordinate.model.TemplateModel templateModel: templateModels)
            count++;

        org.junit.Assert.assertEquals(count, templateModels.size());
    }

    // region Public Method Tests
    @org.junit.Test() public void addAndSizeAndGetWork()
    {
        final org.wheatgenetics.coordinate.model.TemplateModels templateModels =
            org.wheatgenetics.coordinate.model.TemplateModels.makeDefault(this);
        {
            final org.wheatgenetics.coordinate.model.TemplateModel templateModel =
                new org.wheatgenetics.coordinate.model.TemplateModel(
                    /* id                           => */4,
                    /* title                        => */"testTitle",
                    /* code                         => */1,
                    /* rows                         => */5,
                    /* cols                         => */2,
                    /* generatedExcludedCellsAmount => */0,
                    /* initialExcludeCells          => */null,
                    /* excludeRows                  => */null,
                    /* excludeCols                  => */null,
                    /* colNumbering                 => */1,
                    /* rowNumbering                 => */0,
                    /* entryLabel                   => */null,
                    /* optionalFields               => */null,
                    /* stringGetter                 => */this,
                    /* timestamp                    => */0);
            templateModels.add(templateModel);
            org.junit.Assert.assertEquals(templateModel, templateModels.get(2));
        }
        org.junit.Assert.assertEquals(3, templateModels.size());
        org.junit.Assert.assertNull  (templateModels.get(999));
    }

    @org.junit.Test() public void getWorks()
    { org.junit.Assert.assertNull(new org.wheatgenetics.coordinate.model.TemplateModels().get(0)); }

    @org.junit.Test() public void makeDefaultAndSizeSucceed()
    {
        org.junit.Assert.assertEquals(2,
            org.wheatgenetics.coordinate.model.TemplateModels.makeDefault(this).size());
    }

    @org.junit.Test() public void titlesWorks()
    {
        org.junit.Assert.assertNull(
            new org.wheatgenetics.coordinate.model.TemplateModels().titles());
    }

    @org.junit.Test() public void titlesAndMakeDefaultSucceed()
    {
        // noinspection CStyleArrayDeclaration
        final java.lang.String expected[] = new java.lang.String[]{
            org.wheatgenetics.coordinate.model.TemplateModel.makeSeedDefault(this).getTitle(),
            org.wheatgenetics.coordinate.model.TemplateModel.makeDNADefault (this).getTitle()};
        final org.wheatgenetics.coordinate.model.TemplateModels templateModels =
            org.wheatgenetics.coordinate.model.TemplateModels.makeDefault(this);
        org.junit.Assert.assertArrayEquals(expected, templateModels.titles());
    }

    @org.junit.Test() public void makeDefaultAndGetSucceed()
    {
        final org.wheatgenetics.coordinate.model.TemplateModels templateModels =
            org.wheatgenetics.coordinate.model.TemplateModels.makeDefault(this);
        org.junit.Assert.assertEquals(templateModels.get(0),
            org.wheatgenetics.coordinate.model.TemplateModel.makeSeedDefault(this));
        org.junit.Assert.assertEquals(templateModels.get(1),
            org.wheatgenetics.coordinate.model.TemplateModel.makeDNADefault(this));
    }
    // endregion
}