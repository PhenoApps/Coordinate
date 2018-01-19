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
    // region First Constructor Tests
    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void badTemplateIdFirstConstructorFails()
    { new org.wheatgenetics.coordinate.model.GridModel(0, "person", null); }

    @org.junit.Test
    public void firstConstructorAndGettersSucceed()
    {
        final long                                         templateId = 67      ;
        final java.lang.String                             person     = "person";
        final org.wheatgenetics.coordinate.model.GridModel gridModel =
            new org.wheatgenetics.coordinate.model.GridModel(templateId, person, null);
        org.junit.Assert.assertEquals(gridModel.getTemplateId(), templateId);
        org.junit.Assert.assertEquals(gridModel.getPerson    (), person    );
    }
    // endregion

    // region Second Constructor Tests
    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void badIdSecondConstructorFails()
    { new org.wheatgenetics.coordinate.model.GridModel(-1, 1, "abc", 0, 0, null, 888); }

    @org.junit.Test
    public void secondConstructorAndGettersSucceed()
    {
        final java.lang.String person    = "abc";
        final long             timestamp = 888  ;
        final org.wheatgenetics.coordinate.model.GridModel gridModel =
            new org.wheatgenetics.coordinate.model.GridModel(
                1, 1, person, 0, 0, null, timestamp);
        org.junit.Assert.assertEquals(gridModel.getPerson   (), person   );
        org.junit.Assert.assertEquals(gridModel.getTimestamp(), timestamp);
    }
    // endregion
    // endregion

    @org.junit.Test
    public void getFormattedTimestamp()
    {
        final long timestamp = 888;
        final org.wheatgenetics.coordinate.model.GridModel gridModel =
            new org.wheatgenetics.coordinate.model.GridModel(
                1, 5, "abc", 0, 0, null, timestamp);
        org.junit.Assert.assertEquals(gridModel.getFormattedTimestamp(),
            org.wheatgenetics.androidlibrary.Utils.formatDate(gridModel.getTimestamp()));
    }
}