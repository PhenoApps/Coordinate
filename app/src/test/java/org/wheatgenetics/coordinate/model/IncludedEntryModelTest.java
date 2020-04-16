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
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"}) public class IncludedEntryModelTest
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

    @org.junit.Test() public void thirdConstructorSucceeds()
    {
        final long                                                  gridId = 23       ;
        final org.wheatgenetics.coordinate.model.IncludedEntryModel includedEntryModel;
        {
            final int row = 2, col = 3;
            includedEntryModel = new org.wheatgenetics.coordinate.model.IncludedEntryModel(
                new org.wheatgenetics.coordinate.model.ExcludedEntryModel(
                    5, gridId, row, col,6,this),this);
            org.junit.Assert.assertEquals(row, includedEntryModel.getRow());
            org.junit.Assert.assertEquals(col, includedEntryModel.getCol());
        }
        org.junit.Assert.assertEquals(gridId, includedEntryModel.getGridId());
    }

    // region Overridden Method Tests
    @org.junit.Test() public void getSeedExportValueSucceeds()
    {
        final org.wheatgenetics.coordinate.model.IncludedEntryModel includedEntryModel =
            new org.wheatgenetics.coordinate.model.IncludedEntryModel(
                23,2,3,this);
        org.junit.Assert.assertEquals("BLANK_", includedEntryModel.getSeedExportValue());

        final java.lang.String value = "value";
        includedEntryModel.setValue(value);
        org.junit.Assert.assertEquals(value, includedEntryModel.getSeedExportValue());
    }

    @org.junit.Test() public void getDNAExportValueSucceeds()
    {
        final org.wheatgenetics.coordinate.model.IncludedEntryModel includedEntryModel =
            new org.wheatgenetics.coordinate.model.IncludedEntryModel(
                23,2,3,this);
        final java.lang.String sample_id = "12";
        {
            final java.lang.String expected = "BLANK_" + sample_id;
            org.junit.Assert.assertEquals(expected,
                includedEntryModel.getDNAExportValue(sample_id));

            includedEntryModel.setValue("");
            org.junit.Assert.assertEquals(expected,
                includedEntryModel.getDNAExportValue(sample_id));
        }
        final java.lang.String value = "value";
        includedEntryModel.setValue(value);                                                // throws
        org.junit.Assert.assertEquals(value, includedEntryModel.getDNAExportValue(sample_id));
    }

    @org.junit.Test() public void getUserDefinedExportValueSucceeds()
    {
        final org.wheatgenetics.coordinate.model.IncludedEntryModel includedEntryModel =
            new org.wheatgenetics.coordinate.model.IncludedEntryModel(
                23,2,3,this);
        org.junit.Assert.assertNotNull(includedEntryModel.getUserDefinedExportValue());

        final java.lang.String value = "value";
        includedEntryModel.setValue(value);
        org.junit.Assert.assertEquals(value, includedEntryModel.getUserDefinedExportValue());
    }

    @org.junit.Test() public void getValueSucceeds()
    {
        final java.lang.String value = "value";
        org.junit.Assert.assertEquals(value,
            new org.wheatgenetics.coordinate.model.IncludedEntryModel(
                5,23,2,3, value,6,this).getValue());
    }

    @org.junit.Test() public void getDatabaseValueSucceeds()
    {
        final java.lang.String value = "value";
        org.junit.Assert.assertEquals(value,
            new org.wheatgenetics.coordinate.model.IncludedEntryModel(
                5,23,2,3, value,6,this).getDatabaseValue());
    }

    @org.junit.Test() public void backgroundResourceSucceeds()
    {
        final org.wheatgenetics.coordinate.model.IncludedEntryModel includedEntryModel =
            new org.wheatgenetics.coordinate.model.IncludedEntryModel(
                23,2,3,this);
        org.junit.Assert.assertEquals(org.wheatgenetics.coordinate.R.drawable.empty_included_entry,
            includedEntryModel.backgroundResource());

        includedEntryModel.setValue("value");
        org.junit.Assert.assertEquals(org.wheatgenetics.coordinate.R.drawable.full_included_entry,
            includedEntryModel.backgroundResource());
    }
    // endregion

    // region Public Method Tests
    @org.junit.Test() public void setValueSucceeds()
    {
        final org.wheatgenetics.coordinate.model.IncludedEntryModel includedEntryModel =
            new org.wheatgenetics.coordinate.model.IncludedEntryModel(
                23,2,3,this);
        org.junit.Assert.assertNull(includedEntryModel.getValue());
        includedEntryModel.setValue(null);
        org.junit.Assert.assertNull(includedEntryModel.getValue());

        final java.lang.String value = "value";
        includedEntryModel.setValue("  " + value + "   ");                                 // Trims.
        org.junit.Assert.assertEquals(value, includedEntryModel.getValue());
    }

    @org.junit.Test() public void valueIsEmptySucceeds()
    {
        final org.wheatgenetics.coordinate.model.IncludedEntryModel includedEntryModel =
            new org.wheatgenetics.coordinate.model.IncludedEntryModel(
                23,2,3,this);
        org.junit.Assert.assertTrue(includedEntryModel.valueIsEmpty());
        includedEntryModel.setValue("value");
        org.junit.Assert.assertFalse(includedEntryModel.valueIsEmpty());
    }
    // endregion
}