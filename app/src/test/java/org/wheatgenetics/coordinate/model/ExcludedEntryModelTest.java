package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * org.junit.Assert
 * org.junit.Test
 *
 * org.wheatgenetics.coordinate.R
 *
 * org.wheatgenetics.coordinate.model.ExcludedEntryModel
 * org.wheatgenetics.coordinate.model.IncludedEntryModel
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
public class ExcludedEntryModelTest extends java.lang.Object
{
    @org.junit.Test() public void thirdConstructorSucceeds()
    {
        final long                                                  gridId = 23       ;
        final org.wheatgenetics.coordinate.model.ExcludedEntryModel excludedEntryModel;
        {
            final int row = 2, col = 3;
            excludedEntryModel = new org.wheatgenetics.coordinate.model.ExcludedEntryModel(
                new org.wheatgenetics.coordinate.model.IncludedEntryModel(
                    5, gridId, row, col,"value",6));
            org.junit.Assert.assertEquals(row, excludedEntryModel.getRow());
            org.junit.Assert.assertEquals(col, excludedEntryModel.getCol());
        }
        org.junit.Assert.assertEquals(gridId, excludedEntryModel.getGridId());
    }

    // region Overridden Method Tests
    @org.junit.Test() public void getSeedExportValueSucceeds()
    {
        org.junit.Assert.assertEquals("exclude",
            new org.wheatgenetics.coordinate.model.ExcludedEntryModel(
                1,1,1).getSeedExportValue());
    }

    @org.junit.Test() public void getUserDefinedExportValueSucceeds()
    {
        org.junit.Assert.assertEquals("exclude",
            new org.wheatgenetics.coordinate.model.ExcludedEntryModel(
                1,1,1).getUserDefinedExportValue());
    }

    @org.junit.Test() public void getValueWorks()
    {
        org.junit.Assert.assertNull(new org.wheatgenetics.coordinate.model.ExcludedEntryModel(
            1,1,1).getValue());
    }

    @org.junit.Test() public void getDatabaseValueSucceeds()
    {
        org.junit.Assert.assertEquals("excluded",
            new org.wheatgenetics.coordinate.model.ExcludedEntryModel(
                1,1,1).getDatabaseValue());
    }

    @org.junit.Test() public void backgroundResourceSucceeds()
    {
        org.junit.Assert.assertEquals(org.wheatgenetics.coordinate.R.drawable.excluded_entry,
            new org.wheatgenetics.coordinate.model.ExcludedEntryModel(
                1,1,1).backgroundResource());
    }
    // endregion
}