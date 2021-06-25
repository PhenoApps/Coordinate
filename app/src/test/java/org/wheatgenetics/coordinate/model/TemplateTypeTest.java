package org.wheatgenetics.coordinate.model;

import org.junit.Assert;
import org.junit.Test;

/**
 * Uses:
 * org.junit.Assert
 * org.junit.Test
 *
 * org.wheatgenetics.coordinate.model.TemplateType
 */
@SuppressWarnings({"ClassExplicitlyExtendsObject"})
public class TemplateTypeTest extends Object
{
    // region get() Tests
    @Test() public void getSucceeds()
    {
        Assert.assertEquals(TemplateType.SEED,
            TemplateType.get(0));
        Assert.assertEquals(TemplateType.DNA,
            TemplateType.get(1));
        Assert.assertEquals(TemplateType.USERDEFINED,
            TemplateType.get(2));
    }

    @Test(expected = IllegalArgumentException.class) public void getFails()
    { TemplateType.get(4); }
    // endregion

    @Test() public void getCodeSucceeds()
    {
        TemplateType templateType =
            TemplateType.SEED;
        Assert.assertEquals   (0, templateType.getCode());
        Assert.assertNotEquals(1, templateType.getCode());

        templateType = TemplateType.DNA;
        Assert.assertEquals   (1, templateType.getCode());
        Assert.assertNotEquals(0, templateType.getCode());

        templateType = TemplateType.USERDEFINED;
        Assert.assertEquals   ( 2, templateType.getCode());
        Assert.assertNotEquals(23, templateType.getCode());
    }

    @Test() public void isDefaultTemplate()
    {
        TemplateType templateType =
            TemplateType.SEED;
        Assert.assertTrue(templateType.isDefaultTemplate());

        templateType = TemplateType.DNA;
        Assert.assertTrue(templateType.isDefaultTemplate());

        templateType = TemplateType.USERDEFINED;
        Assert.assertFalse(templateType.isDefaultTemplate());
    }
}