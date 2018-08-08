package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * org.junit.Assert
 * org.junit.Test
 *
 * org.wheatgenetics.coordinate.model.TemplateType
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
public class TemplateTypeTest extends java.lang.Object
{
    // region get() Tests
    @org.junit.Test() public void getSucceeds()
    {
        org.junit.Assert.assertEquals(org.wheatgenetics.coordinate.model.TemplateType.SEED,
            org.wheatgenetics.coordinate.model.TemplateType.get(0));
        org.junit.Assert.assertEquals(org.wheatgenetics.coordinate.model.TemplateType.DNA,
            org.wheatgenetics.coordinate.model.TemplateType.get(1));
        org.junit.Assert.assertEquals(org.wheatgenetics.coordinate.model.TemplateType.USERDEFINED,
            org.wheatgenetics.coordinate.model.TemplateType.get(2));
    }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class) public void getFails()
    { org.wheatgenetics.coordinate.model.TemplateType.get(4); }
    // endregion

    @org.junit.Test() public void getCodeSucceeds()
    {
        org.wheatgenetics.coordinate.model.TemplateType templateType =
            org.wheatgenetics.coordinate.model.TemplateType.SEED;
        org.junit.Assert.assertEquals   (0, templateType.getCode());
        org.junit.Assert.assertNotEquals(1, templateType.getCode());

        templateType = org.wheatgenetics.coordinate.model.TemplateType.DNA;
        org.junit.Assert.assertEquals   (1, templateType.getCode());
        org.junit.Assert.assertNotEquals(0, templateType.getCode());

        templateType = org.wheatgenetics.coordinate.model.TemplateType.USERDEFINED;
        org.junit.Assert.assertEquals   ( 2, templateType.getCode());
        org.junit.Assert.assertNotEquals(23, templateType.getCode());
    }

    @org.junit.Test() public void isDefaultTemplate()
    {
        org.wheatgenetics.coordinate.model.TemplateType templateType =
            org.wheatgenetics.coordinate.model.TemplateType.SEED;
        org.junit.Assert.assertTrue(templateType.isDefaultTemplate());

        templateType = org.wheatgenetics.coordinate.model.TemplateType.DNA;
        org.junit.Assert.assertTrue(templateType.isDefaultTemplate());

        templateType = org.wheatgenetics.coordinate.model.TemplateType.USERDEFINED;
        org.junit.Assert.assertFalse(templateType.isDefaultTemplate());
    }
}