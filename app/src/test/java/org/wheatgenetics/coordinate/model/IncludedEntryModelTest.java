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
 * org.wheatgenetics.coordinate.model.IncludedEntryModel.CheckException
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
public class IncludedEntryModelTest extends java.lang.Object
{
    @org.junit.Test() public void thirdConstructorSucceeds()
    {
        final long                                                  gridId = 23       ;
        final org.wheatgenetics.coordinate.model.IncludedEntryModel includedEntryModel;
        {
            final int row = 2, col = 3;
            includedEntryModel = new org.wheatgenetics.coordinate.model.IncludedEntryModel(
                new org.wheatgenetics.coordinate.model.ExcludedEntryModel(
                    5, gridId, row, col,6));
            org.junit.Assert.assertEquals(row, includedEntryModel.getRow());
            org.junit.Assert.assertEquals(col, includedEntryModel.getCol());
        }
        org.junit.Assert.assertEquals(gridId, includedEntryModel.getGridId());
    }

    // region Overridden Method Tests
    @org.junit.Test() public void getSeedExportValueSucceeds()
    throws org.wheatgenetics.coordinate.model.IncludedEntryModel.CheckException
    {
        final org.wheatgenetics.coordinate.model.IncludedEntryModel includedEntryModel =
            new org.wheatgenetics.coordinate.model.IncludedEntryModel(23,2,3);
        org.junit.Assert.assertEquals("BLANK_", includedEntryModel.getSeedExportValue());

        final java.lang.String value = "value";
        includedEntryModel.setValue(value);                                                // throws
        org.junit.Assert.assertEquals(value, includedEntryModel.getSeedExportValue());
    }

    @org.junit.Test() public void getDNAExportValueSucceeds()
    throws org.wheatgenetics.coordinate.model.IncludedEntryModel.CheckException
    {
        final org.wheatgenetics.coordinate.model.IncludedEntryModel includedEntryModel =
            new org.wheatgenetics.coordinate.model.IncludedEntryModel(23,2,3);
        final java.lang.String sample_id = "12";
        {
            final java.lang.String expected = "BLANK_" + sample_id;
            org.junit.Assert.assertEquals(expected,
                includedEntryModel.getDNAExportValue(sample_id));

            includedEntryModel.setValue("");                                               // throws
            org.junit.Assert.assertEquals(expected,
                includedEntryModel.getDNAExportValue(sample_id));
        }
        final java.lang.String value = "value";
        includedEntryModel.setValue(value);                                                // throws
        org.junit.Assert.assertEquals(value, includedEntryModel.getDNAExportValue(sample_id));
    }

    @org.junit.Test() public void getUserDefinedExportValueSucceeds()
    throws org.wheatgenetics.coordinate.model.IncludedEntryModel.CheckException
    {
        final org.wheatgenetics.coordinate.model.IncludedEntryModel includedEntryModel =
            new org.wheatgenetics.coordinate.model.IncludedEntryModel(23,2,3);
        org.junit.Assert.assertNotNull(includedEntryModel.getUserDefinedExportValue());

        final java.lang.String value = "value";
        includedEntryModel.setValue(value);                                                // throws
        org.junit.Assert.assertEquals(value, includedEntryModel.getUserDefinedExportValue());
    }

    @org.junit.Test() public void getValueSucceeds()
    throws org.wheatgenetics.coordinate.model.IncludedEntryModel.CheckException
    {
        final java.lang.String value = "value";
        org.junit.Assert.assertEquals(value,
            new org.wheatgenetics.coordinate.model.IncludedEntryModel(                     // throws
                5,23,2,3, value,6).getValue());
    }

    @org.junit.Test() public void getDatabaseValueSucceeds()
    throws org.wheatgenetics.coordinate.model.IncludedEntryModel.CheckException
    {
        final java.lang.String value = "value";
        org.junit.Assert.assertEquals(value,
            new org.wheatgenetics.coordinate.model.IncludedEntryModel(                     // throws
                5,23,2,3, value,6).getDatabaseValue());
    }

    @org.junit.Test() public void backgroundResourceSucceeds()
    throws org.wheatgenetics.coordinate.model.IncludedEntryModel.CheckException
    {
        final org.wheatgenetics.coordinate.model.IncludedEntryModel includedEntryModel =
            new org.wheatgenetics.coordinate.model.IncludedEntryModel(23,2,3);
        org.junit.Assert.assertEquals(org.wheatgenetics.coordinate.R.drawable.empty_included_entry,
            includedEntryModel.backgroundResource());

        includedEntryModel.setValue("value");                                              // throws
        org.junit.Assert.assertEquals(org.wheatgenetics.coordinate.R.drawable.full_included_entry,
            includedEntryModel.backgroundResource());
    }
    // endregion

    // region Public Method Tests
    @org.junit.Test() public void setValueSucceeds()
    throws org.wheatgenetics.coordinate.model.IncludedEntryModel.CheckException
    {
        final org.wheatgenetics.coordinate.model.IncludedEntryModel includedEntryModel =
            new org.wheatgenetics.coordinate.model.IncludedEntryModel(23,2,3);
        org.junit.Assert.assertNull(includedEntryModel.getValue());
        includedEntryModel.setValue(null);                                                 // throws
        org.junit.Assert.assertNull(includedEntryModel.getValue());

        final java.lang.String value = "value";
        includedEntryModel.setValue("  " + value + "   ");                                 // throws
        org.junit.Assert.assertEquals(value, includedEntryModel.getValue());
    }

    @org.junit.Test() public void valueIsEmptySucceeds()
    throws org.wheatgenetics.coordinate.model.IncludedEntryModel.CheckException
    {
        final org.wheatgenetics.coordinate.model.IncludedEntryModel includedEntryModel =
            new org.wheatgenetics.coordinate.model.IncludedEntryModel(23,2,3);
        org.junit.Assert.assertTrue(includedEntryModel.valueIsEmpty());
        includedEntryModel.setValue("value");                                              // throws
        org.junit.Assert.assertFalse(includedEntryModel.valueIsEmpty());
    }
    // endregion
}