package org.wheatgenetics.coordinate.model;

import android.content.res.Resources;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.PluralsRes;
import androidx.annotation.StringRes;

import org.junit.Assert;
import org.junit.Test;
import org.wheatgenetics.coordinate.R;
import org.wheatgenetics.coordinate.StringGetter;
import org.wheatgenetics.coordinate.optionalField.TimestampOptionalField;
import org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields;
import org.wheatgenetics.coordinate.preference.Utils;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

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
 * org.wheatgenetics.coordinate.R
 * org.wheatgenetics.coordinate.StringGetter
 *
 * org.wheatgenetics.coordinate.optionalField.DateOptionalField
 * org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields
 *
 * org.wheatgenetics.coordinate.preference.Utils.Direction
 *
 * org.wheatgenetics.coordinate.model.Cells
 * org.wheatgenetics.coordinate.model.EntryModel
 * org.wheatgenetics.coordinate.model.EntryModels
 * org.wheatgenetics.coordinate.model.EntryModels.FilledHandler
 * org.wheatgenetics.coordinate.model.ExcludedEntryModel
 * org.wheatgenetics.coordinate.model.IncludedEntryModel
 * org.wheatgenetics.coordinate.model.JoinedGridModel
 * org.wheatgenetics.coordinate.model.JoinedGridModel.Helper
 * org.wheatgenetics.coordinate.model.RowOrCols
 * org.wheatgenetics.coordinate.model.TemplateType
 */
@SuppressWarnings({"ClassExplicitlyExtendsObject"}) public class JoinedGridModelTest
extends Object implements StringGetter
{
    // region org.wheatgenetics.coordinate.StringGetter Overridden Methods
    @Override @Nullable public String get(
    @StringRes final int resId)
    {
        switch (resId)
        {
            case R.string.BaseOptionalFieldPersonFieldName:
                return "Person";

            case R.string.BaseOptionalFieldIdentificationFieldName:
                return "Identification";


            case R.string.TimestampOptionalFieldDateFieldName:
                return "Date";


            case R.string.NonNullOptionalFieldsTrayIDFieldName:
                return "Tray";
            case R.string.NonNullOptionalFieldsTrayIDFieldHint:
                return "Tray ID";
            case
            R.string.NonNullOptionalFieldsSeedTrayPersonFieldName:
                return "Person";
            case
            R.string.NonNullOptionalFieldsSeedTrayPersonFieldHint:
                return "Person name";

            case R.string.NonNullOptionalFieldsPlateIDFieldName:
                return "Plate";
            case R.string.NonNullOptionalFieldsPlateIDFieldHint:
                return "Plate ID";

            case R.string.NonNullOptionalFieldsPlateNameFieldName:
                return "Plate Name";

            case R.string.NonNullOptionalFieldsNotesFieldName:
                return "Notes";

            case
            R.string.NonNullOptionalFieldsTissueTypeFieldName:
                return "tissue_type";
            case
            R.string.NonNullOptionalFieldsTissueTypeFieldValue:
                return "Leaf";

            case R.string.NonNullOptionalFieldsExtractionFieldName:
                return "extraction";
            case
            R.string.NonNullOptionalFieldsExtractionFieldValue:
                return "CTAB";

            case
            R.string.NonNullOptionalFieldsDNAPlatePersonFieldName:
                return "person";


            case R.string.JoinedGridModelSeedTrayTimestampFieldName:
                return "date";
            case R.string.JoinedGridModelDNAPlateTimestampFieldName:
                return "date";

            default: Assert.fail(); return null;
        }
    }

    @Override @NonNull public String getQuantity(
    @PluralsRes         final int                 resId     ,
    @IntRange(from = 0) final int                 quantity  ,
    @Nullable           final Object... formatArgs)
    throws Resources.NotFoundException { Assert.fail(); return null; }
    // endregion

    // region Types
    private static class Helper extends Object
    implements JoinedGridModel.Helper
    { @Override public void publishProgress(final int col) {} }

    private static class FilledHandler extends Object
    implements EntryModels.FilledHandler
    {
        @Override public void handleFilledGrid    () {}
        @Override public void handleFilledRowOrCol() {}
    }
    // endregion

    @Test(expected = NullPointerException.class)
    public void firstConstructorFails()
    {
        // noinspection ConstantConditions
        new JoinedGridModel(
            /* projectId      => */5,
            /* person         => */"John Doe",
            /* optionalFields => */null,
            /* stringGetter   => */this,
            /* templateModel  => */null /* throws */);
    }

    // region getElementModel() Tests
    @Test() public void emptyGetElementModelWorks()
    {
        final JoinedGridModel joinedGridModel =
            new JoinedGridModel(
                /* id                           => */5,
                /* projectId                    => */0,
                /* person                       => */"testPerson",
                /* activeRow                    => */0,
                /* activeCol                    => */0,
                /* optionalFields               => */null,
                /* stringGetter                 => */this,
                /* timestamp                    => */123,

                /* templateId                   => */6,
                /* title                        => */"testTitle",
                /* code                         => */1,
                /* rows                         => */5,
                /* cols                         => */2,
                /* generatedExcludedCellsAmount => */0,
                /* initialExcludedCells         => */null,
                /* excludedRows                 => */null,
                /* excludedCols                 => */null,
                /* colNumbering                 => */1,
                /* rowNumbering                 => */0,
                /* entryLabel                   => */null,
                /* templateOptionalFields       => */null,
                /* templateTimestamp            => */333,

                /* entryModels                  => */null);                    // Empty.
        Assert.assertNull(joinedGridModel.getElementModel(1,1));
    }

    @Test() public void fullGetElementModelWorks()
    {
        final int           rows = 5, cols = 5, excludedRow = 3, excludedCol = 3;
        final JoinedGridModel joinedGridModel;
        {
            final long                                           gridId      = 1;
            final EntryModels entryModels =
                new EntryModels(
                    gridId, rows, cols,this);
            for (int row = 1; row <= rows; row++) for (int col = 1; col <= cols; col++)
                if (excludedRow == row && excludedCol == col)
                    entryModels.makeExcludedEntry(row, col);
                else
                    entryModels.makeIncludedEntry(row, col);

            joinedGridModel = new JoinedGridModel(
                /* id                           => */ gridId,
                /* projectId                    => */0,
                /* person                       => */"testPerson",
                /* activeRow                    => */0,
                /* activeCol                    => */0,
                /* optionalFields               => */null,
                /* stringGetter                 => */this,
                /* timestamp                    => */123,

                /* templateId                   => */6,
                /* title                        => */"testTitle",
                /* code                         => */1,
                /* rows                         => */ rows,
                /* cols                         => */ cols,
                /* generatedExcludedCellsAmount => */0,
                /* initialExcludedCells         => */null,
                /* excludedRows                 => */null,
                /* excludedCols                 => */null,
                /* colNumbering                 => */1,
                /* rowNumbering                 => */0,
                /* entryLabel                   => */null,
                /* templateOptionalFields       => */null,
                /* templateTimestamp            => */333,

                /* entryModels                  => */ entryModels);                         // Full.
        }
        for (int row = 1; row <= rows; row++) for (int col = 1; col <= cols; col++)
            if (excludedRow == row && excludedCol == col)
                Assert.assertTrue(joinedGridModel.getElementModel(row, col)
                    instanceof ExcludedEntryModel);
            else
                Assert.assertTrue(joinedGridModel.getElementModel(row, col)
                    instanceof IncludedEntryModel);
    }
    // endregion

    // region Package Method Tests
    @Test() public void nameSucceeds()
    {


        //commented this test out because the androidlibrary is no longer resolving


//        final String                                   expectedName   ;
//        final JoinedGridModel joinedGridModel;
//        {
//            final String person    = "testPerson", title = "testTitle";
//            final int              rows      = 5           , cols  = 2          ;
//            final long             timestamp = 123                              ;
//
//            expectedName = String.format(
//                "Person: %s\n Template: %s\n Size: (%d, %d) Date: %s\n", person, title,
//                cols, rows, org.wheatgenetics.androidlibrary.Utils.formatDate(timestamp));
//            joinedGridModel = new JoinedGridModel(
//                /* id                           => */5,
//                /* projectId                    => */0,
//                /* person                       => */ person,
//                /* activeRow                    => */0,
//                /* activeCol                    => */0,
//                /* optionalFields               => */null,
//                /* stringGetter                 => */this,
//                /* timestamp                    => */ timestamp,
//
//                /* templateId                   => */6,
//                /* title                        => */ title,
//                /* code                         => */1,
//                /* rows                         => */ rows,
//                /* cols                         => */ cols,
//                /* generatedExcludedCellsAmount => */0,
//                /* initialExcludedCells         => */null,
//                /* excludedRows                 => */null,
//                /* excludedCols                 => */null,
//                /* colNumbering                 => */1,
//                /* rowNumbering                 => */0,
//                /* entryLabel                   => */null,
//                /* templateOptionalFields       => */null,
//                /* templateTimestamp            => */333,
//
//                /* entryModels                  => */null);
//        }
//        Assert.assertEquals(expectedName, joinedGridModel.name());
    }

    // region excludedCellsFromEntries() Package Method Tests
    @Test() public void emptyExcludedCellsFromEntriesWorks()
    {
        final Cells           expectedCells  ;
        final JoinedGridModel joinedGridModel;
        {
            final int rows = 5, cols = 5;
            expectedCells   = new Cells(                // Empty.
                rows, cols,this);
            joinedGridModel = new JoinedGridModel(
                /* id                           => */5,
                /* projectId                    => */0,
                /* person                       => */"testPerson",
                /* activeRow                    => */0,
                /* activeCol                    => */0,
                /* optionalFields               => */null,
                /* stringGetter                 => */this,
                /* timestamp                    => */123,

                /* templateId                   => */6,
                /* title                        => */"testTitle",
                /* code                         => */1,
                /* rows                         => */ rows,
                /* cols                         => */ cols,
                /* generatedExcludedCellsAmount => */0,
                /* initialExcludedCells         => */null,                // Empty.
                /* excludedRows                 => */null,                    // Empty.
                /* excludedCols                 => */null,                    // Empty.
                /* colNumbering                 => */1,
                /* rowNumbering                 => */0,
                /* entryLabel                   => */null,
                /* templateOptionalFields       => */null,
                /* templateTimestamp            => */333,

                /* entryModels                  => */null);                    // Empty.
        }
        Assert.assertEquals(expectedCells, joinedGridModel.excludedCellsFromEntries());
    }

    @Test() public void oneCellExcludedCellsFromEntriesWorks()
    {
        final Cells           expectedCells  ;
        final JoinedGridModel joinedGridModel;
        {
            final int rows = 5, cols = 5;

            expectedCells = new Cells(rows, cols,this);
            expectedCells.add(3,3);

            joinedGridModel = new JoinedGridModel(
                /* id                           => */5,
                /* projectId                    => */0,
                /* person                       => */"testPerson",
                /* activeRow                    => */0,
                /* activeCol                    => */0,
                /* optionalFields               => */null,
                /* stringGetter                 => */this,
                /* timestamp                    => */123,

                /* templateId                   => */6,
                /* title                        => */"testTitle",
                /* code                         => */1,
                /* rows                         => */ rows,
                /* cols                         => */ cols,
                /* generatedExcludedCellsAmount => */0,
                /* initialExcludedCells         => */ expectedCells.json(),                  // One.
                /* excludedRows                 => */null,
                /* excludedCols                 => */null,
                /* colNumbering                 => */1,
                /* rowNumbering                 => */0,
                /* entryLabel                   => */null,
                /* templateOptionalFields       => */null,
                /* templateTimestamp            => */333,

                /* entryModels                  => */null);
        }
        joinedGridModel.makeEntryModels();
        Assert.assertEquals(expectedCells, joinedGridModel.excludedCellsFromEntries());
    }

    @Test() public void oneRowExcludedCellsFromEntriesWorks()
    {
        final Cells           expectedCells  ;
        final JoinedGridModel joinedGridModel;
        {
            final int rows = 5, cols = 5;

            expectedCells = new Cells(rows, cols,this);
            final RowOrCols excludedRows;
            {
                final int excludedRow = 3;
                for (int col = 1; col <= cols; col++) expectedCells.add(excludedRow, col);

                excludedRows =
                    new RowOrCols(rows,this);
                excludedRows.add(excludedRow);
            }
            joinedGridModel = new JoinedGridModel(
                /* id                           => */5,
                /* projectId                    => */0,
                /* person                       => */"testPerson",
                /* activeRow                    => */0,
                /* activeCol                    => */0,
                /* optionalFields               => */null,
                /* stringGetter                 => */this,
                /* timestamp                    => */123,

                /* templateId                   => */6,
                /* title                        => */"testTitle",
                /* code                         => */1,
                /* rows                         => */ rows,
                /* cols                         => */ cols,
                /* generatedExcludedCellsAmount => */0,
                /* initialExcludedCells         => */null,
                /* excludedRows                 => */ excludedRows.json(),                   // One.
                /* excludedCols                 => */null,
                /* colNumbering                 => */1,
                /* rowNumbering                 => */0,
                /* entryLabel                   => */null,
                /* templateOptionalFields       => */null,
                /* templateTimestamp            => */333,

                /* entryModels                  => */null);
        }
        joinedGridModel.makeEntryModels();
        Assert.assertEquals(expectedCells, joinedGridModel.excludedCellsFromEntries());
    }

    @Test() public void oneColExcludedCellsFromEntriesWorks()
    {
        final Cells           expectedCells  ;
        final JoinedGridModel joinedGridModel;
        {
            final int rows = 5, cols = 5;

            expectedCells = new Cells(rows, cols,this);
            final RowOrCols excludedCols;
            {
                final int excludedCol = 4;
                for (int row = 1; row <= rows; row++) expectedCells.add(row, excludedCol);

                excludedCols =
                    new RowOrCols(cols,this);
                excludedCols.add(excludedCol);
            }
            joinedGridModel = new JoinedGridModel(
                /* id                           => */5,
                /* projectId                    => */0,
                /* person                       => */"testPerson",
                /* activeRow                    => */0,
                /* activeCol                    => */0,
                /* optionalFields               => */null,
                /* stringGetter                 => */this,
                /* timestamp                    => */123,

                /* templateId                   => */6,
                /* title                        => */"testTitle",
                /* code                         => */1,
                /* rows                         => */ rows,
                /* cols                         => */ cols,
                /* generatedExcludedCellsAmount => */0,
                /* initialExcludedCells         => */null,
                /* excludedRows                 => */null,
                /* excludedCols                 => */ excludedCols.json(),                   // One.
                /* colNumbering                 => */1,
                /* rowNumbering                 => */0,
                /* entryLabel                   => */null,
                /* templateOptionalFields       => */null,
                /* templateTimestamp            => */333,

                /* entryModels                  => */null);
        }
        joinedGridModel.makeEntryModels();
        Assert.assertEquals(expectedCells, joinedGridModel.excludedCellsFromEntries());
    }
    // endregion

    // region export() Package Method Tests
    // region SEED export() Package Method Tests
    @Test() public void emptySeedExportSucceeds() throws IOException
    {
        final String expectedString =
            "tray_id,cell_id,tray_num,tray_column,tray_row,seed_id,person,date\n";
        final StringWriter stringWriter = new StringWriter();
        {
            final JoinedGridModel joinedGridModel =
                new JoinedGridModel(
                    /* id             => */5,
                    /* projectId      => */0,
                    /* person         => */"testPerson",
                    /* activeRow      => */0,
                    /* activeCol      => */0,
                    /* optionalFields => */null,
                    /* stringGetter   => */this,
                    /* timestamp      => */123,

                    /* templateId => */6,
                    /* title      => */"testTitle",
                    /* code => */ TemplateType.SEED.getCode(),
                    /* rows                         => */3,
                    /* cols                         => */3,
                    /* generatedExcludedCellsAmount => */0,
                    /* initialExcludedCells         => */null,
                    /* excludedRows                 => */null,
                    /* excludedCols                 => */null,
                    /* colNumbering                 => */1,
                    /* rowNumbering                 => */0,
                    /* entryLabel                   => */null,
                    /* templateOptionalFields       => */null,
                    /* templateTimestamp            => */333,

                    /* entryModels => */null);
            joinedGridModel.export(                                    // throws java.io.IOException
                stringWriter,"exportFileName",
                true);
        }
        Assert.assertEquals(expectedString, stringWriter.toString());
    }

    @Test() public void blankSeedExportSucceeds() throws IOException
    {
        final String expectedString =
            "tray_id,cell_id,tray_num,tray_column,tray_row,seed_id,person,date\n" +
            "\"\",exportFileName_C01_R1,,1,1,BLANK_,,\n"                          +
            "\"\",exportFileName_C01_R2,,1,2,BLANK_,,\n"                          +
            "\"\",exportFileName_C01_R3,,1,3,BLANK_,,\n"                          +
            "\"\",exportFileName_C02_R1,,2,1,BLANK_,,\n"                          +
            "\"\",exportFileName_C02_R2,,2,2,BLANK_,,\n"                          +
            "\"\",exportFileName_C02_R3,,2,3,BLANK_,,\n"                          +
            "\"\",exportFileName_C03_R1,,3,1,BLANK_,,\n"                          +
            "\"\",exportFileName_C03_R2,,3,2,BLANK_,,\n"                          +
            "\"\",exportFileName_C03_R3,,3,3,BLANK_,,\n"                          ;
        final StringWriter stringWriter = new StringWriter();
        {
            final JoinedGridModel joinedGridModel =
                new JoinedGridModel(
                    /* id             => */5,
                    /* projectId      => */0,
                    /* person         => */"testPerson",
                    /* activeRow      => */0,
                    /* activeCol      => */0,
                    /* optionalFields => */null,
                    /* stringGetter   => */this,
                    /* timestamp      => */123,

                    /* templateId => */6,
                    /* title      => */"testTitle",
                    /* code => */ TemplateType.SEED.getCode(),
                    /* rows                         => */3,
                    /* cols                         => */3,
                    /* generatedExcludedCellsAmount => */0,
                    /* initialExcludedCells         => */null,
                    /* excludedRows                 => */null,
                    /* excludedCols                 => */null,
                    /* colNumbering                 => */1,
                    /* rowNumbering                 => */0,
                    /* entryLabel                   => */null,
                    /* templateOptionalFields       => */null,
                    /* templateTimestamp            => */333,

                    /* entryModels => */null);
            joinedGridModel.makeEntryModels();
            joinedGridModel.export(                                    // throws java.io.IOException
                stringWriter,"exportFileName",
                true);
        }
        Assert.assertEquals(expectedString, stringWriter.toString());
    }

    @Test() public void optionalFieldsBlankSeedExportSucceeds() throws IOException
    {
        final String     expectedString                           ;
        final StringWriter stringWriter = new StringWriter();
        {
            final String trayId = "23", person = "John Doe";
            {
                final String
                    safePerson = person.replace(' ','_'),
                    date       = TimestampOptionalField.getCurrentTimestamp();
                expectedString =
                    "tray_id,cell_id,tray_num,tray_column,tray_row,seed_id,person,date\n"           +
                    trayId + ",exportFileName_C01_R1,,1,1,BLANK_," + safePerson + ',' + date + '\n' +
                    trayId + ",exportFileName_C01_R2,,1,2,BLANK_," + safePerson + ',' + date + '\n' +
                    trayId + ",exportFileName_C01_R3,,1,3,BLANK_," + safePerson + ',' + date + '\n' +
                    trayId + ",exportFileName_C02_R1,,2,1,BLANK_," + safePerson + ',' + date + '\n' +
                    trayId + ",exportFileName_C02_R2,,2,2,BLANK_," + safePerson + ',' + date + '\n' +
                    trayId + ",exportFileName_C02_R3,,2,3,BLANK_," + safePerson + ',' + date + '\n' +
                    trayId + ",exportFileName_C03_R1,,3,1,BLANK_," + safePerson + ',' + date + '\n' +
                    trayId + ",exportFileName_C03_R2,,3,2,BLANK_," + safePerson + ',' + date + '\n' +
                    trayId + ",exportFileName_C03_R3,,3,3,BLANK_," + safePerson + ',' + date + '\n' ;
            }
            {
                final JoinedGridModel joinedGridModel;
                {
                    final NonNullOptionalFields
                        optionalFields = NonNullOptionalFields.makeSeedDefault(trayId, person,this);
                    joinedGridModel = new JoinedGridModel(
                        /* id             => */5,
                        /* projectId      => */0,
                        /* person         => */"testPerson",
                        /* activeRow      => */0,
                        /* activeCol      => */0,
                        /* optionalFields => */ optionalFields.toJson(),                // Not null.
                        /* stringGetter   => */this,
                        /* timestamp      => */123,

                        /* templateId => */6,
                        /* title      => */"testTitle",
                        /* code       => */
                            TemplateType.SEED.getCode(),
                        /* rows                         => */3,
                        /* cols                         => */3,
                        /* generatedExcludedCellsAmount => */0,
                        /* initialExcludedCells         => */null,
                        /* excludedRows                 => */null,
                        /* excludedCols                 => */null,
                        /* colNumbering                 => */1,
                        /* rowNumbering                 => */0,
                        /* entryLabel                   => */null,
                        /* templateOptionalFields       => */null,
                        /* templateTimestamp            => */333,

                        /* entryModels => */null);
                }
                joinedGridModel.makeEntryModels();
                joinedGridModel.export(                                // throws java.io.IOException
                    stringWriter,"exportFileName",
                    true);
            }
        }
        Assert.assertEquals(expectedString, stringWriter.toString());
    }

    @Test() public void someSeedExportSucceeds() throws IOException
    {
        final String     expectedString                           ;
        final StringWriter stringWriter = new StringWriter();
        {
            final String value = "234.105";
            expectedString =
                "tray_id,cell_id,tray_num,tray_column,tray_row,seed_id,person,date\n" +
                "\"\",exportFileName_C01_R1,,1,1,BLANK_,,\n"                          +
                "\"\",exportFileName_C01_R2,,1,2,BLANK_,,\n"                          +
                "\"\",exportFileName_C01_R3,,1,3,BLANK_,,\n"                          +
                "\"\",exportFileName_C02_R1,,2,1,BLANK_,,\n"                          +
                "\"\",exportFileName_C02_R2,,2,2," + value + ",,\n"                   +
                "\"\",exportFileName_C02_R3,,2,3,BLANK_,,\n"                          +
                "\"\",exportFileName_C03_R1,,3,1,BLANK_,,\n"                          +
                "\"\",exportFileName_C03_R2,,3,2,BLANK_,,\n"                          +
                "\"\",exportFileName_C03_R3,,3,3,BLANK_,,\n"                          ;
            {
                final JoinedGridModel joinedGridModel;
                {
                    final long gridId = 5;
                    joinedGridModel = new JoinedGridModel(
                        /* id             => */ gridId,
                        /* projectId      => */0,
                        /* person         => */"testPerson",
                        /* activeRow      => */0,
                        /* activeCol      => */0,
                        /* optionalFields => */null,
                        /* stringGetter   => */this,
                        /* timestamp      => */123,

                        /* templateId => */6,
                        /* title      => */"testTitle",
                        /* code       => */
                            TemplateType.SEED.getCode(),
                        /* rows                         => */3,
                        /* cols                         => */3,
                        /* generatedExcludedCellsAmount => */0,
                        /* initialExcludedCells         => */null,
                        /* excludedRows                 => */null,
                        /* excludedCols                 => */null,
                        /* colNumbering                 => */1,
                        /* rowNumbering                 => */0,
                        /* entryLabel                   => */null,
                        /* templateOptionalFields       => */null,
                        /* templateTimestamp            => */333,

                        /* entryModels => */null);
                    joinedGridModel.makeEntryModels();
                    {
                        final IncludedEntryModel
                            includedEntryModel =
                                new IncludedEntryModel(
                                    gridId,2,2,this);
                        includedEntryModel.setValue(value);
                        joinedGridModel.setEntryModel(includedEntryModel);
                    }
                }
                joinedGridModel.export(                                // throws java.io.IOException
                    stringWriter,"exportFileName",
                    true);
            }
        }
        Assert.assertEquals(expectedString, stringWriter.toString());
    }
    // endregion

    // region DNA export() Package Method Tests
    @Test() public void emptyDNAExportSucceeds() throws IOException
    {
        final String expectedString = "date,plate_id,plate_name,sample_id" +
            ",well_A01,well_01A,tissue_id,dna_person,notes,tissue_type,extraction\n";
        final StringWriter stringWriter = new StringWriter();
        {
            final JoinedGridModel joinedGridModel =
                new JoinedGridModel(
                    /* id             => */5,
                    /* projectId      => */0,
                    /* person         => */"testPerson",
                    /* activeRow      => */0,
                    /* activeCol      => */0,
                    /* optionalFields => */null,
                    /* stringGetter   => */this,
                    /* timestamp      => */123,

                    /* templateId => */6,
                    /* title      => */"testTitle",
                    /* code => */ TemplateType.DNA.getCode(),
                    /* rows                         => */3,
                    /* cols                         => */3,
                    /* generatedExcludedCellsAmount => */0,
                    /* initialExcludedCells         => */null,
                    /* excludedRows                 => */null,
                    /* excludedCols                 => */null,
                    /* colNumbering                 => */1,
                    /* rowNumbering                 => */0  ,
                    /* entryLabel                   => */null,
                    /* templateOptionalFields       => */null,
                    /* templateTimestamp            => */333,

                    /* entryModels => */null);
            joinedGridModel.export(                                    // throws java.io.IOException
                stringWriter,"exportFileName",
                true);
        }
        Assert.assertEquals(expectedString, stringWriter.toString());
    }

    @Test() public void blankDNAExportSucceeds() throws IOException
    {
        final String expectedString =
            "date,plate_id,plate_name,sample_id,well_A01,well_01A,t" +
                "issue_id,dna_person,notes,tissue_type,extraction\n" +
            "\"\",,,null_A01,A01,01A,BLANK_null_A01,,,,\n"           +
            "\"\",,,null_B01,B01,01B,BLANK_null_B01,,,,\n"           +
            "\"\",,,null_C01,C01,01C,BLANK_null_C01,,,,\n"           +
            "\"\",,,null_A02,A02,02A,BLANK_null_A02,,,,\n"           +
            "\"\",,,null_B02,B02,02B,BLANK_null_B02,,,,\n"           +
            "\"\",,,null_C02,C02,02C,BLANK_null_C02,,,,\n"           +
            "\"\",,,null_A03,A03,03A,BLANK_null_A03,,,,\n"           +
            "\"\",,,null_B03,B03,03B,BLANK_null_B03,,,,\n"           +
            "\"\",,,null_C03,C03,03C,BLANK_null_C03,,,,\n"           ;
        final StringWriter stringWriter = new StringWriter();
        {
            final JoinedGridModel joinedGridModel =
                new JoinedGridModel(
                    /* id             => */5,
                    /* projectId      => */0,
                    /* person         => */"testPerson",
                    /* activeRow      => */0,
                    /* activeCol      => */0,
                    /* optionalFields => */null,
                    /* stringGetter   => */this,
                    /* timestamp      => */123,

                    /* templateId => */6,
                    /* title      => */"testTitle",
                    /* code => */ TemplateType.DNA.getCode(),
                    /* rows                         => */3,
                    /* cols                         => */3,
                    /* generatedExcludedCellsAmount => */0,
                    /* initialExcludedCells         => */null,
                    /* excludedRows                 => */null,
                    /* excludedCols                 => */null,
                    /* colNumbering                 => */1,
                    /* rowNumbering                 => */0,
                    /* entryLabel                   => */null,
                    /* templateOptionalFields       => */null,
                    /* templateTimestamp            => */333,

                    /* entryModels => */null);
            joinedGridModel.makeEntryModels();
            joinedGridModel.export(                                    // throws java.io.IOException
                stringWriter,"exportFileName",
                true);
        }
        Assert.assertEquals(expectedString, stringWriter.toString());
    }

    @Test() public void optionalFieldsBlankDNAExportSucceeds() throws IOException
    {
        final String     expectedString                           ;
        final StringWriter stringWriter = new StringWriter();
        {
            final String plateId = "45", plateName = "test", person = "John Doe";
            {
                final String
                    safePerson = person.replace(' ','_'),
                    date       = TimestampOptionalField.getCurrentTimestamp();
                expectedString =
                    "date,plate_id,plate_name,sample_id,well_A01,well_01A,t" +
                        "issue_id,dna_person,notes,tissue_type,extraction\n" +
                    date + ',' + plateId + ',' + plateName + ',' + plateId +
                        "_A01,A01,01A,BLANK_" + plateId + "_A01," + safePerson + ",,Leaf,CTAB\n" +
                    date + ',' + plateId + ',' + plateName + ',' + plateId +
                        "_B01,B01,01B,BLANK_" + plateId + "_B01," + safePerson + ",,Leaf,CTAB\n" +
                    date + ',' + plateId + ',' + plateName + ',' + plateId +
                        "_C01,C01,01C,BLANK_" + plateId + "_C01," + safePerson + ",,Leaf,CTAB\n" +
                    date + ',' + plateId + ',' + plateName + ',' + plateId +
                        "_A02,A02,02A,BLANK_" + plateId + "_A02," + safePerson + ",,Leaf,CTAB\n" +
                    date + ',' + plateId + ',' + plateName + ',' + plateId +
                        "_B02,B02,02B,BLANK_" + plateId + "_B02," + safePerson + ",,Leaf,CTAB\n" +
                    date + ',' + plateId + ',' + plateName + ',' + plateId +
                        "_C02,C02,02C,BLANK_" + plateId + "_C02," + safePerson + ",,Leaf,CTAB\n" +
                    date + ',' + plateId + ',' + plateName + ',' + plateId +
                        "_A03,A03,03A,BLANK_" + plateId + "_A03," + safePerson + ",,Leaf,CTAB\n" +
                    date + ',' + plateId + ',' + plateName + ',' + plateId +
                        "_B03,B03,03B,BLANK_" + plateId + "_B03," + safePerson + ",,Leaf,CTAB\n" +
                    date + ',' + plateId + ',' + plateName + ',' + plateId +
                        "_C03,C03,03C,BLANK_" + plateId + "_C03," + safePerson + ",,Leaf,CTAB\n";
            }
            {
                final JoinedGridModel joinedGridModel;
                {
                    final NonNullOptionalFields
                        optionalFields = NonNullOptionalFields.makeDNADefault(plateId, plateName, person,this);
                    joinedGridModel = new JoinedGridModel(
                        /* id             => */5,
                        /* projectId      => */0,
                        /* person         => */"testPerson",
                        /* activeRow      => */0,
                        /* activeCol      => */0,
                        /* optionalFields => */ optionalFields.toJson(),                // Not null.
                        /* stringGetter   => */this,
                        /* timestamp      => */123,

                        /* templateId => */6,
                        /* title      => */"testTitle",
                        /* code => */ TemplateType.DNA.getCode(),
                        /* rows                         => */3,
                        /* cols                         => */3,
                        /* generatedExcludedCellsAmount => */0,
                        /* initialExcludedCells         => */null,
                        /* excludedRows                 => */null,
                        /* excludedCols                 => */null,
                        /* colNumbering                 => */1,
                        /* rowNumbering                 => */0,
                        /* entryLabel                   => */null,
                        /* templateOptionalFields       => */null,
                        /* templateTimestamp            => */333,

                        /* entryModels => */null);
                }
                joinedGridModel.makeEntryModels();
                joinedGridModel.export(                                // throws java.io.IOException
                    stringWriter,"exportFileName",
                    true);
            }
        }
        Assert.assertEquals(expectedString, stringWriter.toString());
    }

    @Test() public void someDNAExportSucceeds() throws IOException
    {
        final String     expectedString                             ;
        final StringWriter stringWriter   = new StringWriter();
        {
            final String value = "234.105";
            expectedString =
                "date,plate_id,plate_name,sample_id,well_A01,well_01A,t" +
                    "issue_id,dna_person,notes,tissue_type,extraction\n" +
                "\"\",,,null_A01,A01,01A,BLANK_null_A01,,,,\n"           +
                "\"\",,,null_B01,B01,01B,BLANK_null_B01,,,,\n"           +
                "\"\",,,null_C01,C01,01C,BLANK_null_C01,,,,\n"           +
                "\"\",,,null_A02,A02,02A,BLANK_null_A02,,,,\n"           +
                "\"\",,,null_B02,B02,02B," + value + ",,,,\n"            +
                "\"\",,,null_C02,C02,02C,BLANK_null_C02,,,,\n"           +
                "\"\",,,null_A03,A03,03A,BLANK_null_A03,,,,\n"           +
                "\"\",,,null_B03,B03,03B,BLANK_null_B03,,,,\n"           +
                "\"\",,,null_C03,C03,03C,BLANK_null_C03,,,,\n"           ;
            {
                final JoinedGridModel joinedGridModel;
                {
                    final long gridId = 5;
                    joinedGridModel = new JoinedGridModel(
                        /* id             => */ gridId,
                        /* projectId      => */0,
                        /* person         => */"testPerson",
                        /* activeRow      => */0,
                        /* activeCol      => */0,
                        /* optionalFields => */null,
                        /* stringGetter   => */this,
                        /* timestamp      => */123,

                        /* templateId => */6,
                        /* title      => */"testTitle",
                        /* code => */ TemplateType.DNA.getCode(),
                        /* rows                         => */3,
                        /* cols                         => */3,
                        /* generatedExcludedCellsAmount => */0,
                        /* initialExcludedCells         => */null,
                        /* excludedRows                 => */null,
                        /* excludedCols                 => */null,
                        /* colNumbering                 => */1,
                        /* rowNumbering                 => */0,
                        /* entryLabel                   => */null,
                        /* templateOptionalFields       => */null,
                        /* templateTimestamp            => */333,

                        /* entryModels => */null);
                    joinedGridModel.makeEntryModels();
                    {
                        final IncludedEntryModel
                            includedEntryModel =
                                new IncludedEntryModel(
                                    gridId,2,2,this);
                        includedEntryModel.setValue(value);
                        joinedGridModel.setEntryModel(includedEntryModel);
                    }
                }
                joinedGridModel.export(                                // throws java.io.IOException
                    stringWriter,"exportFileName",
                    true);
            }
        }
        Assert.assertEquals(expectedString, stringWriter.toString());
    }
    // endregion

    // region User-Defined export() Package Method Tests
    @Test() public void emptyUserDefinedExportSucceeds() throws IOException
    {
        final String     expectedString = "Value,Column,Row\n"      ;
        final StringWriter stringWriter   = new StringWriter();
        {
            final JoinedGridModel joinedGridModel =
                new JoinedGridModel(
                    /* id             => */5,
                    /* projectId      => */0,
                    /* person         => */"testPerson",
                    /* activeRow      => */0,
                    /* activeCol      => */0,
                    /* optionalFields => */null,
                    /* stringGetter   => */this,
                    /* timestamp      => */123,

                    /* templateId => */6,
                    /* title      => */"testTitle",
                    /* code       => */
                        TemplateType.USERDEFINED.getCode(),
                    /* rows                         => */3,
                    /* cols                         => */3,
                    /* generatedExcludedCellsAmount => */0,
                    /* initialExcludedCells         => */null,
                    /* excludedRows                 => */null,
                    /* excludedCols                 => */null,
                    /* colNumbering                 => */1,
                    /* rowNumbering                 => */0,
                    /* entryLabel                   => */null,
                    /* templateOptionalFields       => */null,
                    /* templateTimestamp            => */333,

                    /* entryModels => */null);
            joinedGridModel.export(                                    // throws java.io.IOException
                stringWriter,"exportFileName",
                true);
        }
        Assert.assertEquals(expectedString, stringWriter.toString());
    }

    @Test() public void blankUserDefinedExportSucceeds() throws IOException
    {
        final String expectedString =
            "Value,Column,Row\n" +
            "\"\",A,1\n"         +
            "\"\",A,2\n"         +
            "\"\",A,3\n"         +
            "\"\",B,1\n"         +
            "\"\",B,2\n"         +
            "\"\",B,3\n"         +
            "\"\",C,1\n"         +
            "\"\",C,2\n"         +
            "\"\",C,3\n"         ;
        final StringWriter stringWriter = new StringWriter();
        {
            final JoinedGridModel joinedGridModel =
                new JoinedGridModel(
                    /* id             => */5,
                    /* projectId      => */0,
                    /* person         => */"testPerson",
                    /* activeRow      => */0,
                    /* activeCol      => */0,
                    /* optionalFields => */null,
                    /* stringGetter   => */this,
                    /* timestamp      => */123,

                    /* templateId => */6,
                    /* title      => */"testTitle",
                    /* code       => */
                        TemplateType.USERDEFINED.getCode(),
                    /* rows                         => */3,
                    /* cols                         => */3,
                    /* generatedExcludedCellsAmount => */0,
                    /* initialExcludedCells         => */null,
                    /* excludedRows                 => */null,
                    /* excludedCols                 => */null,
                    /* colNumbering                 => */0,                    // false
                    /* rowNumbering                 => */1,                   // true
                    /* entryLabel                   => */null,
                    /* templateOptionalFields       => */null,
                    /* templateTimestamp            => */333,

                    /* entryModels => */null);
            joinedGridModel.makeEntryModels();
            joinedGridModel.export(                                    // throws java.io.IOException
                stringWriter,"exportFileName",
                true);
        }
        Assert.assertEquals(expectedString, stringWriter.toString());
    }

    @Test()
    public void optionalFieldsBlankUserDefinedExportSucceeds() throws IOException
    {
        final String     expectedString                           ;
        final StringWriter stringWriter = new StringWriter();
        {
            final String identification = "alpha", person = "Jane Roe";
            {
                final String
                    safePerson = person.replace(' ','_'),             // TODO: Use?
                    date       = TimestampOptionalField.getCurrentTimestamp();
                expectedString =
                    "Value,Column,Row,Identification,Person,Date\n"                 +
                    "\"\",1,A," + identification + ',' + person + ',' + date + '\n' +
                    "\"\",1,B," + identification + ',' + person + ',' + date + '\n' +
                    "\"\",1,C," + identification + ',' + person + ',' + date + '\n' +
                    "\"\",2,A," + identification + ',' + person + ',' + date + '\n' +
                    "\"\",2,B," + identification + ',' + person + ',' + date + '\n' +
                    "\"\",2,C," + identification + ',' + person + ',' + date + '\n' +
                    "\"\",3,A," + identification + ',' + person + ',' + date + '\n' +
                    "\"\",3,B," + identification + ',' + person + ',' + date + '\n' +
                    "\"\",3,C," + identification + ',' + person + ',' + date + '\n' ;
            }
            {
                final JoinedGridModel joinedGridModel;
                {
                    final NonNullOptionalFields
                        optionalFields = NonNullOptionalFields.makeNew(identification, person,this);
                    joinedGridModel = new JoinedGridModel(
                        /* id             => */5,
                        /* projectId      => */0,
                        /* person         => */"testPerson",
                        /* activeRow      => */0,
                        /* activeCol      => */0,
                        /* optionalFields => */ optionalFields.toJson(),                // Not null.
                        /* stringGetter   => */this,
                        /* timestamp      => */123,

                        /* templateId => */6,
                        /* title      => */"testTitle",
                        /* code       => */
                            TemplateType.USERDEFINED.getCode(),
                        /* rows                         => */3,
                        /* cols                         => */3,
                        /* generatedExcludedCellsAmount => */0,
                        /* initialExcludedCells         => */null,
                        /* excludedRows                 => */null,
                        /* excludedCols                 => */null,
                        /* colNumbering                 => */1,                // true
                        /* rowNumbering                 => */0,               // false
                        /* entryLabel                   => */null,
                        /* templateOptionalFields       => */null,
                        /* templateTimestamp            => */333,

                        /* entryModels => */null);
                }
                joinedGridModel.makeEntryModels();
                joinedGridModel.export(                                // throws java.io.IOException
                    stringWriter,"exportFileName",
                    true);
            }
        }
        Assert.assertEquals(expectedString, stringWriter.toString());
    }

    @Test() public void someUserDefinedExportSucceeds() throws IOException
    {
        final String     expectedString                           ;
        final StringWriter stringWriter = new StringWriter();
        {
            final String value = "234.105";
            expectedString =
                "Value,Column,Row\n" +
                    "\"\",1,A\n" +
                    "\"\",1,B\n" +
                    "\"\",1,C\n" +
                    "\"\",2,A\n" +
                value + ",2,B\n" +
                    "\"\",2,C\n" +
                    "\"\",3,A\n" +
                    "\"\",3,B\n" +
                    "\"\",3,C\n" ;
            {
                final JoinedGridModel joinedGridModel;
                {
                    final long gridId = 5;
                    joinedGridModel = new JoinedGridModel(
                        /* id             => */ gridId,
                        /* projectId      => */0,
                        /* person         => */"testPerson",
                        /* activeRow      => */0,
                        /* activeCol      => */0,
                        /* optionalFields => */null,
                        /* stringGetter   => */this,
                        /* timestamp      => */123,

                        /* templateId => */6,
                        /* title      => */"testTitle",
                        /* code       => */
                            TemplateType.USERDEFINED.getCode(),
                        /* rows                         => */3,
                        /* cols                         => */3,
                        /* generatedExcludedCellsAmount => */0,
                        /* initialExcludedCells         => */null,
                        /* excludedRows                 => */null,
                        /* excludedCols                 => */null,
                        /* colNumbering                 => */1,                // true
                        /* rowNumbering                 => */0,               // false
                        /* entryLabel                   => */null,
                        /* templateOptionalFields       => */null,
                        /* templateTimestamp            => */333,

                        /* entryModels => */null);
                    joinedGridModel.makeEntryModels();
                    {
                        final IncludedEntryModel
                            includedEntryModel =
                                new IncludedEntryModel(
                                    gridId,2,2,this);
                        includedEntryModel.setValue(value);
                        joinedGridModel.setEntryModel(includedEntryModel);
                    }
                }
                joinedGridModel.export(                                // throws java.io.IOException
                    stringWriter,"exportFileName",
                    true);
            }
        }
        Assert.assertEquals(expectedString, stringWriter.toString());
    }
    // endregion

    // region exportFile export() Package Method Tests
    @Test() public void nullExportFileExportFails() throws IOException
    {
        final JoinedGridModel joinedGridModel =
            new JoinedGridModel(
                /* id                           => */5,
                /* projectId                    => */0,
                /* person                       => */"testPerson",
                /* activeRow                    => */0,
                /* activeCol                    => */0,
                /* optionalFields               => */null,
                /* stringGetter                 => */this,
                /* timestamp                    => */123,

                /* templateId                   => */6,
                /* title                        => */"testTitle",
                /* code                         => */1,
                /* rows                         => */5,
                /* cols                         => */5,
                /* generatedExcludedCellsAmount => */0,
                /* initialExcludedCells         => */null,
                /* excludedRows                 => */null,
                /* excludedCols                 => */null,
                /* colNumbering                 => */1,
                /* rowNumbering                 => */0,
                /* entryLabel                   => */null,
                /* templateOptionalFields       => */null,
                /* templateTimestamp            => */333,

                /* entryModels                  => */null);
        Assert.assertFalse(joinedGridModel.export(null,
            "exportFileName",
            new JoinedGridModelTest.Helper()));
    }

    @Test() public void nullHelperExportFails() throws IOException
    {
        final JoinedGridModel joinedGridModel =
            new JoinedGridModel(
                /* id                           => */5,
                /* projectId                    => */0,
                /* person                       => */"testPerson",
                /* activeRow                    => */0,
                /* activeCol                    => */0,
                /* optionalFields               => */null,
                /* stringGetter                 => */this,
                /* timestamp                    => */123,

                /* templateId                   => */6,
                /* title                        => */"testTitle",
                /* code                         => */1,
                /* rows                         => */5,
                /* cols                         => */5,
                /* generatedExcludedCellsAmount => */0,
                /* initialExcludedCells         => */null,
                /* excludedRows                 => */null,
                /* excludedCols                 => */null,
                /* colNumbering                 => */1,
                /* rowNumbering                 => */0,
                /* entryLabel                   => */null,
                /* templateOptionalFields       => */null,
                /* templateTimestamp            => */333,

                /* entryModels                  => */null);
        Assert.assertFalse(joinedGridModel.export(
            new File(""),"exportFileName"));
    }
    // endregion
    // endregion
    // endregion

    // region Public Method Tests
    // region makeEntryModels() Public Method Tests
    @Test() public void noneGeneratedMakeEntryModelWorks()
    {
        final Cells           expectedCells  ;
        final JoinedGridModel joinedGridModel;
        {
            final Cells     initialExcludedCells      ;
            final RowOrCols excludedRows, excludedCols;
            final int                                          rows = 3    , cols = 3    ;
            expectedCells = new Cells(
                /* maxRow => */ rows, /* maxCols => */ cols,this);
            {
                final int excludedRow = 1, excludedCol = 1;
                expectedCells.add(excludedRow,1); expectedCells.add(1, excludedCol);
                expectedCells.add(excludedRow,2); expectedCells.add(2, excludedCol);
                expectedCells.add(excludedRow,3); expectedCells.add(3, excludedCol);

                excludedRows =
                    new RowOrCols(rows,this);
                excludedCols =
                    new RowOrCols(cols,this);
                excludedRows.add(excludedRow); excludedCols.add(excludedCol);
            }
            {
                final int excludedCellRow = 3, excludedCellCol = 3;
                expectedCells.add(excludedCellRow, excludedCellCol);

                initialExcludedCells = new Cells(
                    /* maxRow => */ rows, /* maxCols => */ cols,this);
                initialExcludedCells.add(excludedCellRow, excludedCellCol);
            }
            joinedGridModel = new JoinedGridModel(
                /* id             => */5,
                /* projectId      => */0,
                /* person         => */"testPerson",
                /* activeRow      => */0,
                /* activeCol      => */0,
                /* optionalFields => */null,
                /* stringGetter   => */this,
                /* timestamp      => */123,

                /* templateId => */6,
                /* title      => */"testTitle",
                /* code       => */
                    TemplateType.USERDEFINED.getCode(),
                /* rows                         => */ rows,
                /* cols                         => */ cols,
                /* generatedExcludedCellsAmount => */0,
                /* initialExcludedCells         => */ initialExcludedCells.json(),
                /* excludedRows                 => */ excludedRows.json()        ,
                /* excludedCols                 => */ excludedCols.json()        ,
                /* colNumbering                 => */1,
                /* rowNumbering                 => */0,
                /* entryLabel                   => */null,
                /* templateOptionalFields       => */null,
                /* templateTimestamp            => */333,

                /* entryModels => */null);
        }
        joinedGridModel.makeEntryModels();
        Assert.assertEquals(expectedCells, joinedGridModel.excludedCellsFromEntries());
    }

    @Test() public void twoGeneratedMakeEntryModelWorks()
    {
        final int                                                generatedExcludedCellsAmount = 2;
        final JoinedGridModel joinedGridModel                 ;
        {
            final int rows = 3, cols = 3;
            joinedGridModel = new JoinedGridModel(
                /* id             => */5,
                /* projectId      => */0,
                /* person         => */"testPerson",
                /* activeRow      => */0,
                /* activeCol      => */0,
                /* optionalFields => */null,
                /* stringGetter   => */this,
                /* timestamp      => */123,

                /* templateId => */6,
                /* title      => */"testTitle",
                /* code       => */
                    TemplateType.USERDEFINED.getCode(),
                /* rows                         => */ rows                        ,
                /* cols                         => */ cols                        ,
                /* generatedExcludedCellsAmount => */ generatedExcludedCellsAmount,        // Not 0.
                /* initialExcludedCells         => */null,
                /* excludedRows                 => */null,
                /* excludedCols                 => */null,
                /* colNumbering                 => */1,
                /* rowNumbering                 => */0,
                /* entryLabel                   => */null,
                /* templateOptionalFields       => */null,
                /* templateTimestamp            => */333,

                /* entryModels => */null);
        }
        joinedGridModel.makeEntryModels();
        Assert.assertEquals(generatedExcludedCellsAmount,
            joinedGridModel.excludedCellsFromEntries().size());
    }

    @Test(expected = NullPointerException.class)
    public void nullMakeEntryModelsThrows()
    {
        final JoinedGridModel joinedGridModel =
            new JoinedGridModel(
                /* id             => */5,
                /* projectId      => */0,
                /* person         => */"testPerson",
                /* activeRow      => */0,
                /* activeCol      => */0,
                /* optionalFields => */null,
                /* stringGetter   => */this,
                /* timestamp      => */123,

                /* templateId => */6,
                /* title      => */"testTitle",
                /* code       => */
                    TemplateType.USERDEFINED.getCode(),
                /* rows                         => */3,
                /* cols                         => */3,
                /* generatedExcludedCellsAmount => */0,
                /* initialExcludedCells         => */null,
                /* excludedRows                 => */null,
                /* excludedCols                 => */null,
                /* colNumbering                 => */1,
                /* rowNumbering                 => */0,
                /* entryLabel                   => */null,
                /* templateOptionalFields       => */null,
                /* templateTimestamp            => */333,

                /* entryModels => */null);
        // noinspection ConstantConditions
        joinedGridModel.makeEntryModels(null);
    }
    // endregion

    @Test() public void getActiveEntryModelWorks()
    {
        final int                                                activeRow       = 2, activeCol = 2;
        final JoinedGridModel joinedGridModel =
            new JoinedGridModel(
                /* id             => */5,
                /* projectId      => */0,
                /* person         => */"testPerson",
                /* activeRow      => */activeRow - 1,
                /* activeCol      => */activeCol - 1,
                /* optionalFields => */null,
                /* stringGetter   => */this,
                /* timestamp      => */123,

                /* templateId => */6,
                /* title      => */"testTitle",
                /* code       => */
                    TemplateType.USERDEFINED.getCode(),
                /* rows                         => */3,
                /* cols                         => */3,
                /* generatedExcludedCellsAmount => */0,
                /* initialExcludedCells         => */null,
                /* excludedRows                 => */null,
                /* excludedCols                 => */null,
                /* colNumbering                 => */1,
                /* rowNumbering                 => */0,
                /* entryLabel                   => */null,
                /* templateOptionalFields       => */null,
                /* templateTimestamp            => */333,

                /* entryModels => */null);
        joinedGridModel.makeEntryModels();
        Assert.assertEquals(joinedGridModel.getElementModel(activeRow, activeCol),
            joinedGridModel.getActiveEntryModel());
    }

    @Test() public void setActiveEntryModelWorks()
    {
        final JoinedGridModel joinedGridModel ;
        final int                                                oldActiveCol = 2;
        {
            final int oldActiveRow = 2;
            joinedGridModel = new JoinedGridModel(
                /* id             => */5,
                /* projectId      => */0,
                /* person         => */"testPerson",
                /* activeRow      => */ oldActiveRow,
                /* activeCol      => */ oldActiveCol,
                /* optionalFields => */null,
                /* stringGetter   => */this,
                /* timestamp      => */123,

                /* templateId => */6,
                /* title      => */"testTitle",
                /* code => */ TemplateType.USERDEFINED.getCode(),
                /* rows                         => */3,
                /* cols                         => */3,
                /* generatedExcludedCellsAmount => */0,
                /* initialExcludedCells         => */null,
                /* excludedRows                 => */null,
                /* excludedCols                 => */null,
                /* colNumbering                 => */1,
                /* rowNumbering                 => */0,
                /* entryLabel                   => */null,
                /* templateOptionalFields       => */null,
                /* templateTimestamp            => */333,

                /* entryModels => */null);
            joinedGridModel.makeEntryModels();

            Assert.assertFalse(
                joinedGridModel.setActiveRowAndActiveCol(oldActiveRow, oldActiveCol));
        }
        final int newActiveRow = 1;
        Assert.assertTrue(
            joinedGridModel.setActiveRowAndActiveCol(newActiveRow, oldActiveCol));
        Assert.assertFalse(
            joinedGridModel.setActiveRowAndActiveCol(newActiveRow, oldActiveCol));
        Assert.assertEquals(
            joinedGridModel.getElementModel(newActiveRow + 1,oldActiveCol + 1),
            joinedGridModel.getActiveEntryModel());
    }

    // region goToNext() Public Method Tests
    @Test() public void middleGoToNextDoes()
    {
        final JoinedGridModel joinedGridModel               ;
        final int                                                currentRow = 2, currentCol = 2;
        {
            final IncludedEntryModel activeEntryModel;
            {
                final long gridId = 5;
                joinedGridModel = new JoinedGridModel(
                    /* id             => */ gridId,
                    /* projectId      => */0,
                    /* person         => */"testPerson",
                    /* activeRow      => */0,
                    /* activeCol      => */0,
                    /* optionalFields => */null,
                    /* stringGetter   => */this,
                    /* timestamp      => */123,

                    /* templateId => */6,
                    /* title      => */"testTitle",
                    /* code       => */
                        TemplateType.USERDEFINED.getCode(),
                    /* rows                         => */3,
                    /* cols                         => */3,
                    /* generatedExcludedCellsAmount => */0,
                    /* initialExcludedCells         => */null,
                    /* excludedRows                 => */null,
                    /* excludedCols                 => */null,
                    /* colNumbering                 => */1,
                    /* rowNumbering                 => */0,
                    /* entryLabel                   => */null,
                    /* templateOptionalFields       => */null,
                    /* templateTimestamp            => */333,

                    /* entryModels => */null);
                joinedGridModel.makeEntryModels();

                activeEntryModel = new IncludedEntryModel(
                    gridId, currentRow, currentCol,this);
            }
            final EntryModels.FilledHandler filledHandler =
                new JoinedGridModelTest.FilledHandler();

            Assert.assertTrue(joinedGridModel.goToNext(
                /* activeEntryModel => */ activeEntryModel,
                /* direction        => */
                    Utils.Direction.DOWN_THEN_ACROSS,
                /* filledHandler => */ filledHandler));
            Assert.assertSame(
                joinedGridModel.getElementModel(currentRow + 1, currentCol),
                joinedGridModel.getActiveEntryModel());

            Assert.assertTrue(joinedGridModel.goToNext(
                /* activeEntryModel => */ activeEntryModel,
                /* direction        => */
                    Utils.Direction.ACROSS_THEN_DOWN,
                /* filledHandler => */ filledHandler));
        }
        Assert.assertSame(joinedGridModel.getElementModel(currentRow,currentCol + 1),
            joinedGridModel.getActiveEntryModel());
    }

    @Test() public void endGoToNextDoesNot()
    {
        final int                                                activeRow = 0, activeCol = 0;
        final JoinedGridModel joinedGridModel             ;
        {
            final EntryModel activeEntryModel;
            {
                final long gridId = 5          ;
                final int  rows   = 3, cols = 3;
                joinedGridModel = new JoinedGridModel(
                    /* id             => */ gridId,
                    /* projectId      => */0,
                    /* person         => */"testPerson",
                    /* activeRow      => */ activeRow,
                    /* activeCol      => */ activeCol,
                    /* optionalFields => */null,
                    /* stringGetter   => */this,
                    /* timestamp      => */123,

                    /* templateId => */6,
                    /* title      => */"testTitle",
                    /* code       => */
                        TemplateType.USERDEFINED.getCode(),
                    /* rows                         => */ rows,
                    /* cols                         => */ cols,
                    /* generatedExcludedCellsAmount => */0,
                    /* initialExcludedCells         => */null,
                    /* excludedRows                 => */null,
                    /* excludedCols                 => */null,
                    /* colNumbering                 => */1,
                    /* rowNumbering                 => */0,
                    /* entryLabel                   => */null,
                    /* templateOptionalFields       => */null,
                    /* templateTimestamp            => */333,

                    /* entryModels => */null);
                joinedGridModel.makeEntryModels();

                activeEntryModel = new IncludedEntryModel(
                    gridId, rows, cols,this);
            }
            final EntryModels.FilledHandler filledHandler =
                new JoinedGridModelTest.FilledHandler();

            Assert.assertFalse(joinedGridModel.goToNext(
                /* activeEntryModel => */ activeEntryModel,
                /* direction        => */
                    Utils.Direction.DOWN_THEN_ACROSS,
                /* filledHandler => */ filledHandler));
            Assert.assertSame(
                joinedGridModel.getElementModel(activeRow + 1,activeCol + 1),
                joinedGridModel.getActiveEntryModel());

            Assert.assertFalse(joinedGridModel.goToNext(
                /* activeEntryModel => */ activeEntryModel,
                /* direction        => */
                    Utils.Direction.ACROSS_THEN_DOWN,
                /* filledHandler => */ filledHandler));
        }
        Assert.assertSame(
            joinedGridModel.getElementModel(activeRow + 1,activeCol + 1),
            joinedGridModel.getActiveEntryModel());
    }
    // endregion

    // region activeRowAndOrActiveColWasAdjusted() Public Method Tests
    @Test() public void easyActiveRowAndOrActiveColWasAdjustedWorks()
    {
        final JoinedGridModel joinedGridModel =
            new JoinedGridModel(
                /* id             => */5,
                /* projectId      => */0,
                /* person         => */"testPerson",
                /* activeRow      => */0,
                /* activeCol      => */0,
                /* optionalFields => */null,
                /* stringGetter   => */this,
                /* timestamp      => */123,

                /* templateId => */6,
                /* title      => */"testTitle",
                /* code       => */
                    TemplateType.USERDEFINED.getCode(),
                /* rows                         => */3,
                /* cols                         => */3,
                /* generatedExcludedCellsAmount => */0,
                /* initialExcludedCells         => */null,
                /* excludedRows                 => */null,
                /* excludedCols                 => */null,
                /* colNumbering                 => */1,
                /* rowNumbering                 => */0,
                /* entryLabel                   => */null,
                /* templateOptionalFields       => */null,
                /* templateTimestamp            => */333,

                /* entryModels => */null);
        joinedGridModel.makeEntryModels();

        joinedGridModel.setActiveRowAndActiveCol(0,2);
        Assert.assertFalse(joinedGridModel.activeRowAndOrActiveColWasAdjusted(
            Utils.Direction.DOWN_THEN_ACROSS));
        Assert.assertFalse(joinedGridModel.activeRowAndOrActiveColWasAdjusted(
            Utils.Direction.ACROSS_THEN_DOWN));
    }

    @Test() public void hardActiveRowAndOrActiveColWasAdjustedWorks()
    {
        final JoinedGridModel joinedGridModel;
        {
            final int                                          rows = 3, cols = 3;
            final RowOrCols
                excludedRows =
                    new RowOrCols(rows,this),
                excludedCols =
                    new RowOrCols(cols,this);
            excludedRows.add(1); excludedCols.add(1);
            joinedGridModel = new JoinedGridModel(
                /* id             => */5,
                /* projectId      => */0,
                /* person         => */"testPerson",
                /* activeRow      => */0,
                /* activeCol      => */0,
                /* optionalFields => */null,
                /* stringGetter   => */this,
                /* timestamp      => */123,

                /* templateId => */6,
                /* title      => */"testTitle",
                /* code       => */
                    TemplateType.USERDEFINED.getCode(),
                /* rows                         => */ rows,
                /* cols                         => */ cols,
                /* generatedExcludedCellsAmount => */0,
                /* initialExcludedCells         => */null,
                /* excludedRows                 => */ excludedRows.json(),
                /* excludedCols                 => */ excludedCols.json(),
                /* colNumbering                 => */1,
                /* rowNumbering                 => */0,
                /* entryLabel                   => */null,
                /* templateOptionalFields       => */null,
                /* templateTimestamp            => */333,

                /* entryModels => */null);
        }
        joinedGridModel.makeEntryModels();


        // Make an entry on the first (0) (excluded!) row active:
        joinedGridModel.setActiveRowAndActiveCol(0,2);

        // Adjust activeRow to one that is included:
        Assert.assertTrue(joinedGridModel.activeRowAndOrActiveColWasAdjusted(
            Utils.Direction.DOWN_THEN_ACROSS));


        // Make an entry on the first (0) (excluded!) col active:
        joinedGridModel.setActiveRowAndActiveCol(2,0);

        // Adjust activeCol to one that is included:
        Assert.assertTrue(joinedGridModel.activeRowAndOrActiveColWasAdjusted(
            Utils.Direction.ACROSS_THEN_DOWN));
    }
    // endregion
    // endregion
}