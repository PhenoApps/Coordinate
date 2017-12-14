package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * org.junit.Assert
 * org.junit.Test
 *
 * org.wheatgenetics.androidlibrary.Utils
 *
 * org.wheatgenetics.coordinate.model.GridModel
 */
@java.lang.SuppressWarnings("ClassExplicitlyExtendsObject")
public class GridModelTest extends java.lang.Object
{
    // region Constructor Tests
    @org.junit.Test
    public void firstConstructorAndGettersSucceed()
    {
        final long             templateId = 67   ;
        final java.lang.String title      = "abc";
        final org.wheatgenetics.coordinate.model.GridModel gridModel =
            new org.wheatgenetics.coordinate.model.GridModel(templateId, title);
        org.junit.Assert.assertEquals(gridModel.getTemplateId(), templateId);
        org.junit.Assert.assertEquals(gridModel.getTitle     (), title     );
    }

    @org.junit.Test
    public void secondConstructorAndGettersSucceed()
    {
        final java.lang.String title     = "abc";
        final long             timestamp = 888;
        final org.wheatgenetics.coordinate.model.GridModel gridModel =
            new org.wheatgenetics.coordinate.model.GridModel(1, title, timestamp);
        org.junit.Assert.assertEquals(gridModel.getTitle    (), title    );
        org.junit.Assert.assertEquals(gridModel.getTimestamp(), timestamp);
    }
    // endregion

    @org.junit.Test
    public void getFormattedTimestamp()
    {
        final long timestamp = 888;
        final org.wheatgenetics.coordinate.model.GridModel gridModel =
            new org.wheatgenetics.coordinate.model.GridModel(1, "abc", timestamp);
        org.junit.Assert.assertEquals(gridModel.getFormattedTimestamp(),
            org.wheatgenetics.androidlibrary.Utils.formatDate(gridModel.getTimestamp()));
    }
}