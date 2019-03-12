package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * org.junit.Assert
 * org.junit.Test
 *
 * org.wheatgenetics.coordinate.R
 *
 * org.wheatgenetics.coordinate.model.IncludedEntryModel
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
public class IncludedEntryModelTest extends java.lang.Object
{
    // region Second Constructor Tests
    @org.junit.Test() public void secondConstructorWorks()
    {
        final org.wheatgenetics.coordinate.model.IncludedEntryModel includedEntryModel;
        {
            final java.lang.String value = "value";
            includedEntryModel = new org.wheatgenetics.coordinate.model.IncludedEntryModel(
                1,1,1,1, value,1);
            org.junit.Assert.assertEquals(value, includedEntryModel.getValue());
        }
        org.junit.Assert.assertNotEquals("abc", includedEntryModel.getValue());
    }

    @org.junit.Test() public void secondConstructorTrims()
    {
        org.junit.Assert.assertEquals("value",
            new org.wheatgenetics.coordinate.model.IncludedEntryModel(
                1,1,1,1,"   value",1).getValue());
        org.junit.Assert.assertEquals("value",
            new org.wheatgenetics.coordinate.model.IncludedEntryModel(
                1,1,1,1,"value ",1).getValue());
        org.junit.Assert.assertEquals("value",
            new org.wheatgenetics.coordinate.model.IncludedEntryModel(
                1,1,1,1,"   value  ",1).getValue());
        org.junit.Assert.assertEquals("",
            new org.wheatgenetics.coordinate.model.IncludedEntryModel(
                1,1,1,1,"     ",1).getValue());
    }
    // endregion

    // region Overridden Method Tests
    @org.junit.Test() public void getSeedExportValueWorks()
    {
        org.wheatgenetics.coordinate.model.IncludedEntryModel includedEntryModel;
        {
            final java.lang.String value = "value";
            includedEntryModel = new org.wheatgenetics.coordinate.model.IncludedEntryModel(
                1,1,1,1, value,1);
            org.junit.Assert.assertEquals(value, includedEntryModel.getSeedExportValue());
        }

        includedEntryModel = new org.wheatgenetics.coordinate.model.IncludedEntryModel(
            1,1,1,1,null,1);
        org.junit.Assert.assertEquals("BLANK_", includedEntryModel.getSeedExportValue());
    }

    @org.junit.Test() public void getDNAExportValueWorks()
    {
        final java.lang.String                                      sample_id = "sample_id";
              org.wheatgenetics.coordinate.model.IncludedEntryModel includedEntryModel     ;
        {
            final java.lang.String value = "value";
            includedEntryModel = new org.wheatgenetics.coordinate.model.IncludedEntryModel(
                1,1,1,1, value,1);
            org.junit.Assert.assertEquals(value, includedEntryModel.getDNAExportValue(sample_id));
        }
        org.junit.Assert.assertNotEquals(sample_id,
            includedEntryModel.getDNAExportValue(sample_id));

        final java.lang.String BLANK_sample_id = "BLANK_" + sample_id;
        includedEntryModel = new org.wheatgenetics.coordinate.model.IncludedEntryModel(
            1,1,1,1,null,1);
        org.junit.Assert.assertEquals(BLANK_sample_id,
            includedEntryModel.getDNAExportValue(sample_id));

        includedEntryModel = new org.wheatgenetics.coordinate.model.IncludedEntryModel(
            1,1,1,1,"",1);
        org.junit.Assert.assertEquals(BLANK_sample_id,
            includedEntryModel.getDNAExportValue(sample_id));
    }

    @org.junit.Test() public void getUserDefinedExportValueWorks()
    {
        org.wheatgenetics.coordinate.model.IncludedEntryModel includedEntryModel;
        {
            final java.lang.String value = "value";
            includedEntryModel = new org.wheatgenetics.coordinate.model.IncludedEntryModel(
                1,1,1,1, value,1);
            org.junit.Assert.assertEquals(value, includedEntryModel.getUserDefinedExportValue());
        }

        includedEntryModel = new org.wheatgenetics.coordinate.model.IncludedEntryModel(
            1,1,1,1,null,1);
        org.junit.Assert.assertEquals("", includedEntryModel.getUserDefinedExportValue());
    }

    @org.junit.Test() public void getDatabaseValueWorks()
    {
        final IncludedEntryModel includedEntryModel =
            new org.wheatgenetics.coordinate.model.IncludedEntryModel(
                1,1,1,1,"value",1);
        org.junit.Assert.assertEquals(includedEntryModel.getValue(),
            includedEntryModel.getDatabaseValue());
    }

    @org.junit.Test() public void backgroundResourceWorks()
    {
        org.wheatgenetics.coordinate.model.IncludedEntryModel includedEntryModel =
            new org.wheatgenetics.coordinate.model.IncludedEntryModel(
                1,1,1,1,"value",1);
            org.junit.Assert.assertEquals(
                org.wheatgenetics.coordinate.R.drawable.full_included_entry,
                includedEntryModel.backgroundResource()                    );

        includedEntryModel = new org.wheatgenetics.coordinate.model.IncludedEntryModel(
            1,1,1,1,null,1);
        org.junit.Assert.assertEquals(
            org.wheatgenetics.coordinate.R.drawable.empty_included_entry,
            includedEntryModel.backgroundResource()                     );
    }
    // endregion

    // region Public Method Tests
    @org.junit.Test() public void setValueTrims()
    {
        final org.wheatgenetics.coordinate.model.IncludedEntryModel includedEntryModel =
            new org.wheatgenetics.coordinate.model.IncludedEntryModel(
                1,1,1,1,"  value",1);
        org.junit.Assert.assertEquals("value", includedEntryModel.getValue());

        includedEntryModel.setValue("value         ");
        org.junit.Assert.assertEquals("value", includedEntryModel.getValue());

        includedEntryModel.setValue("  value   ");
        org.junit.Assert.assertEquals("value", includedEntryModel.getValue());

        includedEntryModel.setValue("     ");
        org.junit.Assert.assertEquals("", includedEntryModel.getValue());
    }

    @org.junit.Test() public void valueIsEmptyWorks()
    {
        org.wheatgenetics.coordinate.model.IncludedEntryModel includedEntryModel =
            new org.wheatgenetics.coordinate.model.IncludedEntryModel(
                1,1,1,1,"value",1);
        org.junit.Assert.assertFalse(includedEntryModel.valueIsEmpty());

        includedEntryModel = new org.wheatgenetics.coordinate.model.IncludedEntryModel(
            1,1,1,1,null,1);
        org.junit.Assert.assertTrue(includedEntryModel.valueIsEmpty());
    }
    // endregion
}