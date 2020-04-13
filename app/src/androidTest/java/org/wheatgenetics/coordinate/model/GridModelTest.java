package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * android.content.res.Resources.NotFoundException
 *
 * androidx.annotation.IntRange
 * androidx.annotation.Nullable
 * androidx.annotation.PluralsRes
 * androidx.annotation.StringRes
 *
 * org.junit.Assert
 * org.junit.Test
 *
 * org.wheatgenetics.androidlibrary.Utils
 *
 * org.wheatgenetics.coordinate.StringGetter
 *
 * org.wheatgenetics.coordinate.model.GridModel
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"}) public class GridModelTest
extends java.lang.Object implements org.wheatgenetics.coordinate.StringGetter
{
    // region org.wheatgenetics.coordinate.StringGetter Overridden Methods
    @java.lang.Override @androidx.annotation.Nullable public java.lang.String get(
    @androidx.annotation.StringRes final int resId)
    {
        // noinspection SwitchStatementWithTooFewBranches
        switch (resId) { default: org.junit.Assert.fail(); return null; }
    }

    @java.lang.Override @androidx.annotation.NonNull public java.lang.String getQuantity(
    @androidx.annotation.PluralsRes         final int                 resId     ,
    @androidx.annotation.IntRange(from = 0) final int                 quantity  ,
    @androidx.annotation.Nullable           final java.lang.Object... formatArgs)
    throws android.content.res.Resources.NotFoundException { org.junit.Assert.fail(); return null; }
    // endregion

    // region Constructor Tests
    // region First Constructor Tests
    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void badTemplateIdFirstConstructorFails()
    {
        new org.wheatgenetics.coordinate.model.GridModel(
            0,0,"person",null,this);
    }

    @org.junit.Test() public void badProjectIdFirstConstructorWorks()
    {
        org.junit.Assert.assertEquals(0, new org.wheatgenetics.coordinate.model.GridModel(
            8,-10,"person",null,this)
                .getProjectId());
    }

    @org.junit.Test() public void firstConstructorAndGettersSucceed()
    {
        final long                                         templateId = 67;
        final org.wheatgenetics.coordinate.model.GridModel gridModel      ;
        {
            final java.lang.String person = "person";
            gridModel = new org.wheatgenetics.coordinate.model.GridModel(
                templateId,0, person,null,this);
            org.junit.Assert.assertEquals(person, gridModel.getPerson());
        }
        org.junit.Assert.assertEquals(templateId, gridModel.getTemplateId());
    }
    // endregion

    // region Second Constructor Tests
    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void badIdSecondConstructorFails()
    {
        new org.wheatgenetics.coordinate.model.GridModel(-1,1,0,
            "abc",0,0,null,this,888);
    }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void badTemplateIdSecondConstructorFails()
    {
        new org.wheatgenetics.coordinate.model.GridModel(1,-1,0,
            "abc",0,0,null,this,888);
    }

    @org.junit.Test() public void badProjectIdSecondConstructorWorks()
    {
        org.junit.Assert.assertEquals(0, new org.wheatgenetics.coordinate.model.GridModel(
            1,1,-10,"abc",0,0,
            null,this,888).getProjectId());
    }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void badActiveRowSecondConstructorFails()
    {
        new org.wheatgenetics.coordinate.model.GridModel(1,1,0,
            "abc",-1,0,null,this,888);
    }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void badActiveColSecondConstructorFails()
    {
        new org.wheatgenetics.coordinate.model.GridModel(1,1,0,
            "abc",0,-1,null,this,888);
    }

    @org.junit.Test() public void secondConstructorAndGettersSucceed()
    {
        final long                                         timestamp = 888;
        final org.wheatgenetics.coordinate.model.GridModel gridModel      ;
        {
            final java.lang.String person = "abc";
            gridModel = new org.wheatgenetics.coordinate.model.GridModel(
                1,1,0, person,0,
                0,null,this, timestamp);
            org.junit.Assert.assertEquals(person, gridModel.getPerson());
        }
        org.junit.Assert.assertEquals(timestamp, gridModel.getTimestamp());
    }
    // endregion
    // endregion

    // region Public Method Tests
    @org.junit.Test() public void getFormattedTimestamp()
    {
        final long                                         timestamp = 888;
        final org.wheatgenetics.coordinate.model.GridModel gridModel =
            new org.wheatgenetics.coordinate.model.GridModel(1,5,0,
                "abc",0,0,null,this, timestamp);
        org.junit.Assert.assertEquals(
            org.wheatgenetics.androidlibrary.Utils.formatDate(gridModel.getTimestamp()),
            gridModel.getFormattedTimestamp()                                          );
    }

    @org.junit.Test() public void optionalFieldsMethodsSucceed()
    {
        final org.wheatgenetics.coordinate.model.GridModel gridModel =
            new org.wheatgenetics.coordinate.model.GridModel(
                /* templateId     => */10,
                /* projectId      => */10,
                /* person         => */"testPerson",
                /* optionalFields => */null,
                /* stringGetter   => */ this);
        org.junit.Assert.assertNull(gridModel.optionalFields                 ());
        org.junit.Assert.assertNull(gridModel.optionalFieldsAsJson           ());
        org.junit.Assert.assertNull(gridModel.getFirstOptionalFieldDatedValue());
    }
    // endregion
}