package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * android.content.res.Resources.NotFoundException
 *
 * androidx.annotation.IntRange
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 * androidx.annotation.PluralsRes
 * androidx.annotation.StringRes
 *
 * org.junit.Assert
 * org.junit.Test
 *
 * org.wheatgenetics.coordinate.R
 * org.wheatgenetics.coordinate.StringGetter
 *
 * org.wheatgenetics.coordinate.model.ExcludedEntryModel
 * org.wheatgenetics.coordinate.model.IncludedEntryModel
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"}) public class ExcludedEntryModelTest
extends java.lang.Object implements org.wheatgenetics.coordinate.StringGetter
{
    @org.junit.Test() public void thirdConstructorSucceeds()
    {
        final long                                                  gridId = 23       ;
        final org.wheatgenetics.coordinate.model.ExcludedEntryModel excludedEntryModel;
        {
            final int row = 2, col = 3;
            excludedEntryModel = new org.wheatgenetics.coordinate.model.ExcludedEntryModel(
                new org.wheatgenetics.coordinate.model.IncludedEntryModel(5, gridId,
                    row, col,"value",6,this),this);
            org.junit.Assert.assertEquals(row, excludedEntryModel.getRow());
            org.junit.Assert.assertEquals(col, excludedEntryModel.getCol());
        }
        org.junit.Assert.assertEquals(gridId, excludedEntryModel.getGridId());
    }

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

    // region Overridden Method Tests
    @org.junit.Test() public void getSeedExportValueSucceeds()
    {
        org.junit.Assert.assertEquals("exclude",
            new org.wheatgenetics.coordinate.model.ExcludedEntryModel(
                1,1,1,this).getSeedExportValue());
    }

    @org.junit.Test() public void getUserDefinedExportValueSucceeds()
    {
        org.junit.Assert.assertEquals("exclude",
            new org.wheatgenetics.coordinate.model.ExcludedEntryModel(
                1,1,1,this).getUserDefinedExportValue());
    }

    @org.junit.Test() public void getValueWorks()
    {
        org.junit.Assert.assertNull(new org.wheatgenetics.coordinate.model.ExcludedEntryModel(
            1,1,1,this).getValue());
    }

    @org.junit.Test() public void getDatabaseValueSucceeds()
    {
        org.junit.Assert.assertEquals("excluded",
            new org.wheatgenetics.coordinate.model.ExcludedEntryModel(
                1,1,1,this).getDatabaseValue());
    }

    @org.junit.Test() public void backgroundResourceSucceeds()
    {
        org.junit.Assert.assertEquals(org.wheatgenetics.coordinate.R.drawable.excluded_entry,
            new org.wheatgenetics.coordinate.model.ExcludedEntryModel(
                1,1,1,this).backgroundResource());
    }
    // endregion
}