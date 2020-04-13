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
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"}) public class JoinedGridModelTest
extends java.lang.Object implements org.wheatgenetics.coordinate.StringGetter
{
    // region org.wheatgenetics.coordinate.StringGetter Overridden Methods
    @java.lang.Override @androidx.annotation.Nullable public java.lang.String get(
    @androidx.annotation.StringRes final int resId)
    {
        switch (resId)
        {
            case org.wheatgenetics.coordinate.R.string.BaseOptionalFieldPersonFieldName:
                return "Person";

            case org.wheatgenetics.coordinate.R.string.BaseOptionalFieldIdentificationFieldName:
                return "Identification";


            case org.wheatgenetics.coordinate.R.string.DateOptionalFieldDateFieldName:
                return "Date";


            case org.wheatgenetics.coordinate.R.string.NonNullOptionalFieldsTrayIDFieldName:
                return "Tray";
            case org.wheatgenetics.coordinate.R.string.NonNullOptionalFieldsTrayIDFieldHint:
                return "Tray ID";
            case
            org.wheatgenetics.coordinate.R.string.NonNullOptionalFieldsSeedTrayPersonFieldName:
                return "Person";
            case
            org.wheatgenetics.coordinate.R.string.NonNullOptionalFieldsSeedTrayPersonFieldHint:
                return "Person name";

            case org.wheatgenetics.coordinate.R.string.NonNullOptionalFieldsPlateIDFieldName:
                return "Plate";
            case org.wheatgenetics.coordinate.R.string.NonNullOptionalFieldsPlateIDFieldHint:
                return "Plate ID";

            case org.wheatgenetics.coordinate.R.string.NonNullOptionalFieldsPlateNameFieldName:
                return "Plate Name";

            case org.wheatgenetics.coordinate.R.string.NonNullOptionalFieldsNotesFieldName:
                return "Notes";

            case
            org.wheatgenetics.coordinate.R.string.NonNullOptionalFieldsTissueTypeFieldName:
                return "tissue_type";
            case
            org.wheatgenetics.coordinate.R.string.NonNullOptionalFieldsTissueTypeFieldValue:
                return "Leaf";

            case org.wheatgenetics.coordinate.R.string.NonNullOptionalFieldsExtractionFieldName:
                return "extraction";
            case
            org.wheatgenetics.coordinate.R.string.NonNullOptionalFieldsExtractionFieldValue:
                return "CTAB";

            case
            org.wheatgenetics.coordinate.R.string.NonNullOptionalFieldsDNAPlatePersonFieldName:
                return "person";


            default: org.junit.Assert.fail(); return null;
        }
    }

    @java.lang.Override @androidx.annotation.NonNull public java.lang.String getQuantity(
    @androidx.annotation.PluralsRes         final int                 resId     ,
    @androidx.annotation.IntRange(from = 0) final int                 quantity  ,
    @androidx.annotation.Nullable           final java.lang.Object... formatArgs)
    throws android.content.res.Resources.NotFoundException { org.junit.Assert.fail(); return null; }
    // endregion

    // region Types
    private static class Helper extends java.lang.Object
    implements org.wheatgenetics.coordinate.model.JoinedGridModel.Helper
    { @java.lang.Override public void publishProgress(final int col) {} }

    private static class FilledHandler extends java.lang.Object
    implements org.wheatgenetics.coordinate.model.EntryModels.FilledHandler
    {
        @java.lang.Override public void handleFilledGrid    () {}
        @java.lang.Override public void handleFilledRowOrCol() {}
    }
    // endregion

    @org.junit.Test(expected = java.lang.NullPointerException.class)
    public void firstConstructorFails()
    {
        // noinspection ConstantConditions
        new org.wheatgenetics.coordinate.model.JoinedGridModel(
            /* projectId      => */5,
            /* person         => */"John Doe",
            /* optionalFields => */null,
            /* stringGetter   => */this,
            /* templateModel  => */null /* throws */);
    }

    // region getElementModel() Tests
    @org.junit.Test() public void emptyGetElementModelWorks()
    {
        final org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel =
            new org.wheatgenetics.coordinate.model.JoinedGridModel(
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
        org.junit.Assert.assertNull(joinedGridModel.getElementModel(1,1));
    }

    @org.junit.Test() public void fullGetElementModelWorks()
    {
        final int           rows = 5, cols = 5, excludedRow = 3, excludedCol = 3;
        final org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel;
        {
            final long                                           gridId      = 1;
            final org.wheatgenetics.coordinate.model.EntryModels entryModels =
                new org.wheatgenetics.coordinate.model.EntryModels(
                    gridId, rows, cols,this);
            for (int row = 1; row <= rows; row++) for (int col = 1; col <= cols; col++)
                if (excludedRow == row && excludedCol == col)
                    entryModels.makeExcludedEntry(row, col);
                else
                    entryModels.makeIncludedEntry(row, col);

            joinedGridModel = new org.wheatgenetics.coordinate.model.JoinedGridModel(
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
                org.junit.Assert.assertTrue(joinedGridModel.getElementModel(row, col)
                    instanceof org.wheatgenetics.coordinate.model.ExcludedEntryModel);
            else
                org.junit.Assert.assertTrue(joinedGridModel.getElementModel(row, col)
                    instanceof org.wheatgenetics.coordinate.model.IncludedEntryModel);
    }
    // endregion

    // region Package Method Tests
    @org.junit.Test() public void nameSucceeds()
    {
        final java.lang.String                                   expectedName   ;
        final org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel;
        {
            final java.lang.String person    = "testPerson", title = "testTitle";
            final int              rows      = 5           , cols  = 2          ;
            final long             timestamp = 123                              ;

            expectedName = java.lang.String.format(
                "Person: %s\n Template: %s\n Size: (%d, %d) Date: %s\n", person, title,
                cols, rows, org.wheatgenetics.androidlibrary.Utils.formatDate(timestamp));
            joinedGridModel = new org.wheatgenetics.coordinate.model.JoinedGridModel(
                /* id                           => */5,
                /* projectId                    => */0,
                /* person                       => */ person,
                /* activeRow                    => */0,
                /* activeCol                    => */0,
                /* optionalFields               => */null,
                /* stringGetter                 => */this,
                /* timestamp                    => */ timestamp,

                /* templateId                   => */6,
                /* title                        => */ title,
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

                /* entryModels                  => */null);
        }
        org.junit.Assert.assertEquals(expectedName, joinedGridModel.name());
    }

    // region excludedCellsFromEntries() Package Method Tests
    @org.junit.Test() public void emptyExcludedCellsFromEntriesWorks()
    {
        final org.wheatgenetics.coordinate.model.Cells           expectedCells  ;
        final org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel;
        {
            final int rows = 5, cols = 5;
            expectedCells   = new org.wheatgenetics.coordinate.model.Cells(                // Empty.
                rows, cols,this);
            joinedGridModel = new org.wheatgenetics.coordinate.model.JoinedGridModel(
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
        org.junit.Assert.assertEquals(expectedCells, joinedGridModel.excludedCellsFromEntries());
    }

    @org.junit.Test() public void oneCellExcludedCellsFromEntriesWorks()
    {
        final org.wheatgenetics.coordinate.model.Cells           expectedCells  ;
        final org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel;
        {
            final int rows = 5, cols = 5;

            expectedCells = new org.wheatgenetics.coordinate.model.Cells(rows, cols,this);
            expectedCells.add(3,3);

            joinedGridModel = new org.wheatgenetics.coordinate.model.JoinedGridModel(
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
        org.junit.Assert.assertEquals(expectedCells, joinedGridModel.excludedCellsFromEntries());
    }

    @org.junit.Test() public void oneRowExcludedCellsFromEntriesWorks()
    {
        final org.wheatgenetics.coordinate.model.Cells           expectedCells  ;
        final org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel;
        {
            final int rows = 5, cols = 5;

            expectedCells = new org.wheatgenetics.coordinate.model.Cells(rows, cols,this);
            final org.wheatgenetics.coordinate.model.RowOrCols excludedRows;
            {
                final int excludedRow = 3;
                for (int col = 1; col <= cols; col++) expectedCells.add(excludedRow, col);

                excludedRows = new org.wheatgenetics.coordinate.model.RowOrCols(rows);
                excludedRows.add(excludedRow);
            }
            joinedGridModel = new org.wheatgenetics.coordinate.model.JoinedGridModel(
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
        org.junit.Assert.assertEquals(expectedCells, joinedGridModel.excludedCellsFromEntries());
    }

    @org.junit.Test() public void oneColExcludedCellsFromEntriesWorks()
    {
        final org.wheatgenetics.coordinate.model.Cells           expectedCells  ;
        final org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel;
        {
            final int rows = 5, cols = 5;

            expectedCells = new org.wheatgenetics.coordinate.model.Cells(rows, cols,this);
            final org.wheatgenetics.coordinate.model.RowOrCols excludedCols;
            {
                final int excludedCol = 4;
                for (int row = 1; row <= rows; row++) expectedCells.add(row, excludedCol);

                excludedCols = new org.wheatgenetics.coordinate.model.RowOrCols(cols);
                excludedCols.add(excludedCol);
            }
            joinedGridModel = new org.wheatgenetics.coordinate.model.JoinedGridModel(
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
        org.junit.Assert.assertEquals(expectedCells, joinedGridModel.excludedCellsFromEntries());
    }
    // endregion

    // region export() Package Method Tests
    // region SEED export() Package Method Tests
    @org.junit.Test() public void emptySeedExportSucceeds() throws java.io.IOException
    {
        final java.lang.String expectedString =
            "tray_id,cell_id,tray_num,tray_column,tray_row,seed_id,person,date\n";
        final java.io.StringWriter stringWriter = new java.io.StringWriter();
        {
            final org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel =
                new org.wheatgenetics.coordinate.model.JoinedGridModel(
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
                    /* code => */ org.wheatgenetics.coordinate.model.TemplateType.SEED.getCode(),
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
                new org.wheatgenetics.coordinate.model.JoinedGridModelTest.Helper(),
                true);
        }
        org.junit.Assert.assertEquals(expectedString, stringWriter.toString());
    }

    @org.junit.Test() public void blankSeedExportSucceeds() throws java.io.IOException
    {
        final java.lang.String expectedString =
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
        final java.io.StringWriter stringWriter = new java.io.StringWriter();
        {
            final org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel =
                new org.wheatgenetics.coordinate.model.JoinedGridModel(
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
                    /* code => */ org.wheatgenetics.coordinate.model.TemplateType.SEED.getCode(),
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
                new org.wheatgenetics.coordinate.model.JoinedGridModelTest.Helper(),
                true);
        }
        org.junit.Assert.assertEquals(expectedString, stringWriter.toString());
    }

    @org.junit.Test() public void optionalFieldsBlankSeedExportSucceeds() throws java.io.IOException
    {
        final java.lang.String     expectedString                           ;
        final java.io.StringWriter stringWriter = new java.io.StringWriter();
        {
            final java.lang.String trayId = "23", person = "John Doe";
            {
                final java.lang.String
                    safePerson = person.replace(' ','_'),
                    date       = org.wheatgenetics.coordinate
                        .optionalField.DateOptionalField.getCurrentDate();
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
                final org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel;
                {
                    final org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields
                        optionalFields = org.wheatgenetics.coordinate.optionalField
                            .NonNullOptionalFields.makeSeedDefault(trayId, person,this);
                    joinedGridModel = new org.wheatgenetics.coordinate.model.JoinedGridModel(
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
                            org.wheatgenetics.coordinate.model.TemplateType.SEED.getCode(),
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
                    new org.wheatgenetics.coordinate.model.JoinedGridModelTest.Helper(),
                    true);
            }
        }
        org.junit.Assert.assertEquals(expectedString, stringWriter.toString());
    }

    @org.junit.Test() public void someSeedExportSucceeds() throws java.io.IOException
    {
        final java.lang.String     expectedString                           ;
        final java.io.StringWriter stringWriter = new java.io.StringWriter();
        {
            final java.lang.String value = "234.105";
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
                final org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel;
                {
                    final long gridId = 5;
                    joinedGridModel = new org.wheatgenetics.coordinate.model.JoinedGridModel(
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
                            org.wheatgenetics.coordinate.model.TemplateType.SEED.getCode(),
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
                        final org.wheatgenetics.coordinate.model.IncludedEntryModel
                            includedEntryModel =
                                new org.wheatgenetics.coordinate.model.IncludedEntryModel(
                                    gridId,2,2);
                        includedEntryModel.setValue(value);
                        joinedGridModel.setEntryModel(includedEntryModel);
                    }
                }
                joinedGridModel.export(                                // throws java.io.IOException
                    stringWriter,"exportFileName",
                    new org.wheatgenetics.coordinate.model.JoinedGridModelTest.Helper(),
                    true);
            }
        }
        org.junit.Assert.assertEquals(expectedString, stringWriter.toString());
    }
    // endregion

    // region DNA export() Package Method Tests
    @org.junit.Test() public void emptyDNAExportSucceeds() throws java.io.IOException
    {
        final java.lang.String expectedString = "date,plate_id,plate_name,sample_id" +
            ",well_A01,well_01A,tissue_id,dna_person,notes,tissue_type,extraction\n";
        final java.io.StringWriter stringWriter = new java.io.StringWriter();
        {
            final org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel =
                new org.wheatgenetics.coordinate.model.JoinedGridModel(
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
                    /* code => */ org.wheatgenetics.coordinate.model.TemplateType.DNA.getCode(),
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
                new org.wheatgenetics.coordinate.model.JoinedGridModelTest.Helper(),
                true);
        }
        org.junit.Assert.assertEquals(expectedString, stringWriter.toString());
    }

    @org.junit.Test() public void blankDNAExportSucceeds() throws java.io.IOException
    {
        final java.lang.String expectedString =
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
        final java.io.StringWriter stringWriter = new java.io.StringWriter();
        {
            final org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel =
                new org.wheatgenetics.coordinate.model.JoinedGridModel(
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
                    /* code => */ org.wheatgenetics.coordinate.model.TemplateType.DNA.getCode(),
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
                new org.wheatgenetics.coordinate.model.JoinedGridModelTest.Helper(),
                true);
        }
        org.junit.Assert.assertEquals(expectedString, stringWriter.toString());
    }

    @org.junit.Test() public void optionalFieldsBlankDNAExportSucceeds() throws java.io.IOException
    {
        final java.lang.String     expectedString                           ;
        final java.io.StringWriter stringWriter = new java.io.StringWriter();
        {
            final java.lang.String plateId = "45", plateName = "test", person = "John Doe";
            {
                final java.lang.String
                    safePerson = person.replace(' ','_'),
                    date       = org.wheatgenetics.coordinate
                        .optionalField.DateOptionalField.getCurrentDate();
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
                final org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel;
                {
                    final org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields
                        optionalFields = org.wheatgenetics.coordinate.optionalField
                            .NonNullOptionalFields.makeDNADefault(plateId, plateName, person,this);
                    joinedGridModel = new org.wheatgenetics.coordinate.model.JoinedGridModel(
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
                        /* code => */ org.wheatgenetics.coordinate.model.TemplateType.DNA.getCode(),
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
                    new org.wheatgenetics.coordinate.model.JoinedGridModelTest.Helper(),
                    true);
            }
        }
        org.junit.Assert.assertEquals(expectedString, stringWriter.toString());
    }

    @org.junit.Test() public void someDNAExportSucceeds() throws java.io.IOException
    {
        final java.lang.String     expectedString                             ;
        final java.io.StringWriter stringWriter   = new java.io.StringWriter();
        {
            final java.lang.String value = "234.105";
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
                final org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel;
                {
                    final long gridId = 5;
                    joinedGridModel = new org.wheatgenetics.coordinate.model.JoinedGridModel(
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
                        /* code => */ org.wheatgenetics.coordinate.model.TemplateType.DNA.getCode(),
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
                        final org.wheatgenetics.coordinate.model.IncludedEntryModel
                            includedEntryModel =
                                new org.wheatgenetics.coordinate.model.IncludedEntryModel(
                                    gridId,2,2);
                        includedEntryModel.setValue(value);
                        joinedGridModel.setEntryModel(includedEntryModel);
                    }
                }
                joinedGridModel.export(                                // throws java.io.IOException
                    stringWriter,"exportFileName",
                    new org.wheatgenetics.coordinate.model.JoinedGridModelTest.Helper(),
                    true);
            }
        }
        org.junit.Assert.assertEquals(expectedString, stringWriter.toString());
    }
    // endregion

    // region User-Defined export() Package Method Tests
    @org.junit.Test() public void emptyUserDefinedExportSucceeds() throws java.io.IOException
    {
        final java.lang.String     expectedString = "Value,Column,Row\n"      ;
        final java.io.StringWriter stringWriter   = new java.io.StringWriter();
        {
            final org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel =
                new org.wheatgenetics.coordinate.model.JoinedGridModel(
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
                        org.wheatgenetics.coordinate.model.TemplateType.USERDEFINED.getCode(),
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
                new org.wheatgenetics.coordinate.model.JoinedGridModelTest.Helper(),
                true);
        }
        org.junit.Assert.assertEquals(expectedString, stringWriter.toString());
    }

    @org.junit.Test() public void blankUserDefinedExportSucceeds() throws java.io.IOException
    {
        final java.lang.String expectedString =
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
        final java.io.StringWriter stringWriter = new java.io.StringWriter();
        {
            final org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel =
                new org.wheatgenetics.coordinate.model.JoinedGridModel(
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
                        org.wheatgenetics.coordinate.model.TemplateType.USERDEFINED.getCode(),
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
                new org.wheatgenetics.coordinate.model.JoinedGridModelTest.Helper(),
                true);
        }
        org.junit.Assert.assertEquals(expectedString, stringWriter.toString());
    }

    @org.junit.Test()
    public void optionalFieldsBlankUserDefinedExportSucceeds() throws java.io.IOException
    {
        final java.lang.String     expectedString                           ;
        final java.io.StringWriter stringWriter = new java.io.StringWriter();
        {
            final java.lang.String identification = "alpha", person = "Jane Roe";
            {
                final java.lang.String
                    safePerson = person.replace(' ','_'),             // TODO: Use?
                    date       = org.wheatgenetics.coordinate
                        .optionalField.DateOptionalField.getCurrentDate();
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
                final org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel;
                {
                    final org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields
                        optionalFields = org.wheatgenetics.coordinate.optionalField
                            .NonNullOptionalFields.makeNew(identification, person,this);
                    joinedGridModel = new org.wheatgenetics.coordinate.model.JoinedGridModel(
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
                            org.wheatgenetics.coordinate.model.TemplateType.USERDEFINED.getCode(),
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
                    new org.wheatgenetics.coordinate.model.JoinedGridModelTest.Helper(),
                    true);
            }
        }
        org.junit.Assert.assertEquals(expectedString, stringWriter.toString());
    }

    @org.junit.Test() public void someUserDefinedExportSucceeds() throws java.io.IOException
    {
        final java.lang.String     expectedString                           ;
        final java.io.StringWriter stringWriter = new java.io.StringWriter();
        {
            final java.lang.String value = "234.105";
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
                final org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel;
                {
                    final long gridId = 5;
                    joinedGridModel = new org.wheatgenetics.coordinate.model.JoinedGridModel(
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
                            org.wheatgenetics.coordinate.model.TemplateType.USERDEFINED.getCode(),
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
                        final org.wheatgenetics.coordinate.model.IncludedEntryModel
                            includedEntryModel =
                                new org.wheatgenetics.coordinate.model.IncludedEntryModel(
                                    gridId,2,2);
                        includedEntryModel.setValue(value);
                        joinedGridModel.setEntryModel(includedEntryModel);
                    }
                }
                joinedGridModel.export(                                // throws java.io.IOException
                    stringWriter,"exportFileName",
                    new org.wheatgenetics.coordinate.model.JoinedGridModelTest.Helper(),
                    true);
            }
        }
        org.junit.Assert.assertEquals(expectedString, stringWriter.toString());
    }
    // endregion

    // region exportFile export() Package Method Tests
    @org.junit.Test() public void nullExportFileExportFails() throws java.io.IOException
    {
        final org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel =
            new org.wheatgenetics.coordinate.model.JoinedGridModel(
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
        org.junit.Assert.assertFalse(joinedGridModel.export(null,
            "exportFileName",
            new org.wheatgenetics.coordinate.model.JoinedGridModelTest.Helper()));
    }

    @org.junit.Test() public void nullHelperExportFails() throws java.io.IOException
    {
        final org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel =
            new org.wheatgenetics.coordinate.model.JoinedGridModel(
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
        org.junit.Assert.assertFalse(joinedGridModel.export(
            new java.io.File(""),"exportFileName",null));
    }
    // endregion
    // endregion
    // endregion

    // region Public Method Tests
    // region makeEntryModels() Public Method Tests
    @org.junit.Test() public void noneGeneratedMakeEntryModelWorks()
    {
        final org.wheatgenetics.coordinate.model.Cells           expectedCells  ;
        final org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel;
        {
            final org.wheatgenetics.coordinate.model.Cells     initialExcludedCells      ;
            final org.wheatgenetics.coordinate.model.RowOrCols excludedRows, excludedCols;
            final int                                          rows = 3    , cols = 3    ;
            expectedCells = new org.wheatgenetics.coordinate.model.Cells(
                /* maxRow => */ rows, /* maxCols => */ cols,this);
            {
                final int excludedRow = 1, excludedCol = 1;
                expectedCells.add(excludedRow,1); expectedCells.add(1, excludedCol);
                expectedCells.add(excludedRow,2); expectedCells.add(2, excludedCol);
                expectedCells.add(excludedRow,3); expectedCells.add(3, excludedCol);

                excludedRows = new org.wheatgenetics.coordinate.model.RowOrCols(rows);
                excludedCols = new org.wheatgenetics.coordinate.model.RowOrCols(cols);
                excludedRows.add(excludedRow); excludedCols.add(excludedCol);
            }
            {
                final int excludedCellRow = 3, excludedCellCol = 3;
                expectedCells.add(excludedCellRow, excludedCellCol);

                initialExcludedCells = new org.wheatgenetics.coordinate.model.Cells(
                    /* maxRow => */ rows, /* maxCols => */ cols,this);
                initialExcludedCells.add(excludedCellRow, excludedCellCol);
            }
            joinedGridModel = new org.wheatgenetics.coordinate.model.JoinedGridModel(
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
                    org.wheatgenetics.coordinate.model.TemplateType.USERDEFINED.getCode(),
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
        org.junit.Assert.assertEquals(expectedCells, joinedGridModel.excludedCellsFromEntries());
    }

    @org.junit.Test() public void twoGeneratedMakeEntryModelWorks()
    {
        final int                                                generatedExcludedCellsAmount = 2;
        final org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel                 ;
        {
            final int rows = 3, cols = 3;
            joinedGridModel = new org.wheatgenetics.coordinate.model.JoinedGridModel(
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
                    org.wheatgenetics.coordinate.model.TemplateType.USERDEFINED.getCode(),
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
        org.junit.Assert.assertEquals(generatedExcludedCellsAmount,
            joinedGridModel.excludedCellsFromEntries().size());
    }

    @org.junit.Test(expected = java.lang.NullPointerException.class)
    public void nullMakeEntryModelsThrows()
    {
        final org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel =
            new org.wheatgenetics.coordinate.model.JoinedGridModel(
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
                    org.wheatgenetics.coordinate.model.TemplateType.USERDEFINED.getCode(),
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

    @org.junit.Test() public void getActiveEntryModelWorks()
    {
        final int                                                activeRow       = 2, activeCol = 2;
        final org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel =
            new org.wheatgenetics.coordinate.model.JoinedGridModel(
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
                    org.wheatgenetics.coordinate.model.TemplateType.USERDEFINED.getCode(),
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
        org.junit.Assert.assertEquals(joinedGridModel.getElementModel(activeRow, activeCol),
            joinedGridModel.getActiveEntryModel());
    }

    @org.junit.Test() public void setActiveEntryModelWorks()
    {
        final org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel ;
        final int                                                oldActiveCol = 2;
        {
            final int oldActiveRow = 2;
            joinedGridModel = new org.wheatgenetics.coordinate.model.JoinedGridModel(
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
                /* code => */ org.wheatgenetics.coordinate.model.TemplateType.USERDEFINED.getCode(),
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

            org.junit.Assert.assertFalse(
                joinedGridModel.setActiveRowAndActiveCol(oldActiveRow, oldActiveCol));
        }
        final int newActiveRow = 1;
        org.junit.Assert.assertTrue(
            joinedGridModel.setActiveRowAndActiveCol(newActiveRow, oldActiveCol));
        org.junit.Assert.assertFalse(
            joinedGridModel.setActiveRowAndActiveCol(newActiveRow, oldActiveCol));
        org.junit.Assert.assertEquals(
            joinedGridModel.getElementModel(newActiveRow + 1,oldActiveCol + 1),
            joinedGridModel.getActiveEntryModel());
    }

    // region goToNext() Public Method Tests
    @org.junit.Test() public void middleGoToNextDoes()
    {
        final org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel               ;
        final int                                                currentRow = 2, currentCol = 2;
        {
            final org.wheatgenetics.coordinate.model.IncludedEntryModel activeEntryModel;
            {
                final long gridId = 5;
                joinedGridModel = new org.wheatgenetics.coordinate.model.JoinedGridModel(
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
                        org.wheatgenetics.coordinate.model.TemplateType.USERDEFINED.getCode(),
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

                activeEntryModel = new org.wheatgenetics.coordinate.model.IncludedEntryModel(
                    gridId, currentRow, currentCol);
            }
            final org.wheatgenetics.coordinate.model.EntryModels.FilledHandler filledHandler =
                new org.wheatgenetics.coordinate.model.JoinedGridModelTest.FilledHandler();

            org.junit.Assert.assertTrue(joinedGridModel.goToNext(
                /* activeEntryModel => */ activeEntryModel,
                /* direction        => */
                    org.wheatgenetics.coordinate.preference.Utils.Direction.DOWN_THEN_ACROSS,
                /* filledHandler => */ filledHandler));
            org.junit.Assert.assertSame(
                joinedGridModel.getElementModel(currentRow + 1, currentCol),
                joinedGridModel.getActiveEntryModel());

            org.junit.Assert.assertTrue(joinedGridModel.goToNext(
                /* activeEntryModel => */ activeEntryModel,
                /* direction        => */
                    org.wheatgenetics.coordinate.preference.Utils.Direction.ACROSS_THEN_DOWN,
                /* filledHandler => */ filledHandler));
        }
        org.junit.Assert.assertSame(joinedGridModel.getElementModel(currentRow,currentCol + 1),
            joinedGridModel.getActiveEntryModel());
    }

    @org.junit.Test() public void endGoToNextDoesNot()
    {
        final int                                                activeRow = 0, activeCol = 0;
        final org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel             ;
        {
            final org.wheatgenetics.coordinate.model.EntryModel activeEntryModel;
            {
                final long gridId = 5          ;
                final int  rows   = 3, cols = 3;
                joinedGridModel = new org.wheatgenetics.coordinate.model.JoinedGridModel(
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
                        org.wheatgenetics.coordinate.model.TemplateType.USERDEFINED.getCode(),
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

                activeEntryModel =
                    new org.wheatgenetics.coordinate.model.IncludedEntryModel(gridId, rows, cols);
            }
            final org.wheatgenetics.coordinate.model.EntryModels.FilledHandler filledHandler =
                new org.wheatgenetics.coordinate.model.JoinedGridModelTest.FilledHandler();

            org.junit.Assert.assertFalse(joinedGridModel.goToNext(
                /* activeEntryModel => */ activeEntryModel,
                /* direction        => */
                    org.wheatgenetics.coordinate.preference.Utils.Direction.DOWN_THEN_ACROSS,
                /* filledHandler => */ filledHandler));
            org.junit.Assert.assertSame(
                joinedGridModel.getElementModel(activeRow + 1,activeCol + 1),
                joinedGridModel.getActiveEntryModel());

            org.junit.Assert.assertFalse(joinedGridModel.goToNext(
                /* activeEntryModel => */ activeEntryModel,
                /* direction        => */
                    org.wheatgenetics.coordinate.preference.Utils.Direction.ACROSS_THEN_DOWN,
                /* filledHandler => */ filledHandler));
        }
        org.junit.Assert.assertSame(
            joinedGridModel.getElementModel(activeRow + 1,activeCol + 1),
            joinedGridModel.getActiveEntryModel());
    }
    // endregion

    // region activeRowAndOrActiveColWasAdjusted() Public Method Tests
    @org.junit.Test() public void easyActiveRowAndOrActiveColWasAdjustedWorks()
    {
        final org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel =
            new org.wheatgenetics.coordinate.model.JoinedGridModel(
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
                    org.wheatgenetics.coordinate.model.TemplateType.USERDEFINED.getCode(),
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
        org.junit.Assert.assertFalse(joinedGridModel.activeRowAndOrActiveColWasAdjusted(
            org.wheatgenetics.coordinate.preference.Utils.Direction.DOWN_THEN_ACROSS));
        org.junit.Assert.assertFalse(joinedGridModel.activeRowAndOrActiveColWasAdjusted(
            org.wheatgenetics.coordinate.preference.Utils.Direction.ACROSS_THEN_DOWN));
    }

    @org.junit.Test() public void hardActiveRowAndOrActiveColWasAdjustedWorks()
    {
        final org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel;
        {
            final int                                          rows = 3, cols = 3;
            final org.wheatgenetics.coordinate.model.RowOrCols
                excludedRows = new org.wheatgenetics.coordinate.model.RowOrCols(rows),
                excludedCols = new org.wheatgenetics.coordinate.model.RowOrCols(cols);
            excludedRows.add(1); excludedCols.add(1);
            joinedGridModel = new org.wheatgenetics.coordinate.model.JoinedGridModel(
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
                    org.wheatgenetics.coordinate.model.TemplateType.USERDEFINED.getCode(),
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
        org.junit.Assert.assertTrue(joinedGridModel.activeRowAndOrActiveColWasAdjusted(
            org.wheatgenetics.coordinate.preference.Utils.Direction.DOWN_THEN_ACROSS));


        // Make an entry on the first (0) (excluded!) col active:
        joinedGridModel.setActiveRowAndActiveCol(2,0);

        // Adjust activeCol to one that is included:
        org.junit.Assert.assertTrue(joinedGridModel.activeRowAndOrActiveColWasAdjusted(
            org.wheatgenetics.coordinate.preference.Utils.Direction.ACROSS_THEN_DOWN));
    }
    // endregion
    // endregion
}