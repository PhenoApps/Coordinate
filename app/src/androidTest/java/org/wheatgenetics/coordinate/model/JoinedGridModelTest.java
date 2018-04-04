package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * org.junit.Assert
 * org.junit.Test
 *
 * org.wheatgenetics.androidlibrary.Utils
 *
 * org.wheatgenetics.coordinate.model.Cells
 * org.wheatgenetics.coordinate.model.EntryModels
 * org.wheatgenetics.coordinate.model.ExcludedEntryModel
 * org.wheatgenetics.coordinate.model.IncludedEntryModel
 * org.wheatgenetics.coordinate.model.JoinedGridModel
 * org.wheatgenetics.coordinate.model.JoinedGridModel.Helper
 * org.wheatgenetics.coordinate.model.RowOrCols
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
public class JoinedGridModelTest extends java.lang.Object
{
    private static class Helper extends java.lang.Object
    implements org.wheatgenetics.coordinate.model.JoinedGridModel.Helper
    { @java.lang.Override public void publishProgress(final int col) {} }

    @org.junit.Test(expected = java.lang.NullPointerException.class)
    public void firstConstructorFails()
    {
        new org.wheatgenetics.coordinate.model.JoinedGridModel(
            /* projectId      => */ 5                 ,
            /* person         => */ "John Doe"        ,
            /* optionalFields => */ null              ,
            /* templateModel  => */ null /* throws */ );
    }

    // region getElementModel() Tests
    @org.junit.Test public void emptyGetElementModelWorks()
    {
        final org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel =
            new org.wheatgenetics.coordinate.model.JoinedGridModel(
                /* id                           => */ 5           ,
                /* projectId                    => */ 0           ,
                /* person                       => */ "testPerson",
                /* activeRow                    => */ 0           ,
                /* activeCol                    => */ 0           ,
                /* optionalFields               => */ null        ,
                /* timestamp                    => */ 123         ,

                /* templateId                   => */ 6           ,
                /* title                        => */ "testTitle" ,
                /* code                         => */ 1           ,
                /* rows                         => */ 5           ,
                /* cols                         => */ 2           ,
                /* generatedExcludedCellsAmount => */ 0           ,
                /* initialExcludedCells         => */ null        ,
                /* excludedRows                 => */ null        ,
                /* excludedCols                 => */ null        ,
                /* colNumbering                 => */ 1           ,
                /* rowNumbering                 => */ 0           ,
                /* templateOptionalFields       => */ null        ,
                /* templateTimestamp            => */ 333         ,

                /* entryModels                  => */ null        );                       // Empty.
        org.junit.Assert.assertNull(joinedGridModel.getElementModel(1, 1));
    }

    @org.junit.Test public void fullGetElementModelWorks()
    {
        final int rows = 5, cols = 5, excludedRow = 3, excludedCol = 3          ;
        final org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel;
        {
            final long                                           gridId      = 1;
            final org.wheatgenetics.coordinate.model.EntryModels entryModels =
                new org.wheatgenetics.coordinate.model.EntryModels(gridId, rows, cols);
            for (int row = 1; row <= rows; row++) for (int col = 1; col <= cols; col++)
                if (excludedRow == row && excludedCol == col)
                    entryModels.makeExcludedEntry(row, col);
                else
                    entryModels.makeIncludedEntry(row, col);

            joinedGridModel = new org.wheatgenetics.coordinate.model.JoinedGridModel(
                /* id                           => */ gridId      ,
                /* projectId                    => */ 0           ,
                /* person                       => */ "testPerson",
                /* activeRow                    => */ 0           ,
                /* activeCol                    => */ 0           ,
                /* optionalFields               => */ null        ,
                /* timestamp                    => */ 123         ,

                /* templateId                   => */ 6           ,
                /* title                        => */ "testTitle" ,
                /* code                         => */ 1           ,
                /* rows                         => */ rows        ,
                /* cols                         => */ cols        ,
                /* generatedExcludedCellsAmount => */ 0           ,
                /* initialExcludedCells         => */ null        ,
                /* excludedRows                 => */ null        ,
                /* excludedCols                 => */ null        ,
                /* colNumbering                 => */ 1           ,
                /* rowNumbering                 => */ 0           ,
                /* templateOptionalFields       => */ null        ,
                /* templateTimestamp            => */ 333         ,

                /* entryModels                  => */ entryModels );                        // Full.
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
    @org.junit.Test @java.lang.SuppressWarnings({"DefaultLocale"}) public void nameSucceeds()
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
                /* id                           => */ 5        ,
                /* projectId                    => */ 0        ,
                /* person                       => */ person   ,
                /* activeRow                    => */ 0        ,
                /* activeCol                    => */ 0        ,
                /* optionalFields               => */ null     ,
                /* timestamp                    => */ timestamp,

                /* templateId                   => */ 6        ,
                /* title                        => */ title    ,
                /* code                         => */ 1        ,
                /* rows                         => */ rows     ,
                /* cols                         => */ cols     ,
                /* generatedExcludedCellsAmount => */ 0        ,
                /* initialExcludedCells         => */ null     ,
                /* excludedRows                 => */ null     ,
                /* excludedCols                 => */ null     ,
                /* colNumbering                 => */ 1        ,
                /* rowNumbering                 => */ 0        ,
                /* templateOptionalFields       => */ null     ,
                /* templateTimestamp            => */ 333      ,

                /* entryModels                  => */ null     );
        }
        org.junit.Assert.assertEquals(expectedName, joinedGridModel.name());
    }

    // region excludedCellsFromEntries() Package Method Tests
    @org.junit.Test public void emptyExcludedCellsFromEntriesWorks()
    {
        final org.wheatgenetics.coordinate.model.Cells           expectedCells  ;
        final org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel;
        {
            final int rows = 5, cols = 5;
            expectedCells   = new org.wheatgenetics.coordinate.model.Cells(rows, cols);    // Empty.
            joinedGridModel = new org.wheatgenetics.coordinate.model.JoinedGridModel(
                /* id                           => */ 5           ,
                /* projectId                    => */ 0           ,
                /* person                       => */ "testPerson",
                /* activeRow                    => */ 0           ,
                /* activeCol                    => */ 0           ,
                /* optionalFields               => */ null        ,
                /* timestamp                    => */ 123         ,

                /* templateId                   => */ 6           ,
                /* title                        => */ "testTitle" ,
                /* code                         => */ 1           ,
                /* rows                         => */ rows        ,
                /* cols                         => */ cols        ,
                /* generatedExcludedCellsAmount => */ 0           ,
                /* initialExcludedCells         => */ null        ,                        // Empty.
                /* excludedRows                 => */ null        ,                        // Empty.
                /* excludedCols                 => */ null        ,                        // Empty.
                /* colNumbering                 => */ 1           ,
                /* rowNumbering                 => */ 0           ,
                /* templateOptionalFields       => */ null        ,
                /* templateTimestamp            => */ 333         ,

                /* entryModels                  => */ null        );                       // Empty.
        }
        org.junit.Assert.assertTrue(expectedCells.equals(
            joinedGridModel.excludedCellsFromEntries()));
    }

    @org.junit.Test public void oneCellExcludedCellsFromEntriesWorks()
    {
        final org.wheatgenetics.coordinate.model.Cells           expectedCells  ;
        final org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel;
        {
            final int rows = 5, cols = 5;

            expectedCells = new org.wheatgenetics.coordinate.model.Cells(rows, cols);
            expectedCells.add(3, 3);

            joinedGridModel = new org.wheatgenetics.coordinate.model.JoinedGridModel(
                /* id                           => */ 5                   ,
                /* projectId                    => */ 0                   ,
                /* person                       => */ "testPerson"        ,
                /* activeRow                    => */ 0                   ,
                /* activeCol                    => */ 0                   ,
                /* optionalFields               => */ null                ,
                /* timestamp                    => */ 123                 ,

                /* templateId                   => */ 6                   ,
                /* title                        => */ "testTitle"         ,
                /* code                         => */ 1                   ,
                /* rows                         => */ rows                ,
                /* cols                         => */ cols                ,
                /* generatedExcludedCellsAmount => */ 0                   ,
                /* initialExcludedCells         => */ expectedCells.json(),                  // One.
                /* excludedRows                 => */ null                ,
                /* excludedCols                 => */ null                ,
                /* colNumbering                 => */ 1                   ,
                /* rowNumbering                 => */ 0                   ,
                /* templateOptionalFields       => */ null                ,
                /* templateTimestamp            => */ 333                 ,

                /* entryModels                  => */ null                );
        }
        joinedGridModel.makeEntryModels();
        org.junit.Assert.assertEquals(expectedCells, joinedGridModel.excludedCellsFromEntries());
    }

    @org.junit.Test public void oneRowExcludedCellsFromEntriesWorks()
    {
        final org.wheatgenetics.coordinate.model.Cells           expectedCells  ;
        final org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel;
        {
            final int rows = 5, cols = 5;

            expectedCells = new org.wheatgenetics.coordinate.model.Cells(rows, cols);
            final org.wheatgenetics.coordinate.model.RowOrCols excludedRows;
            {
                final int excludedRow = 3;
                for (int col = 1; col <= cols; col++) expectedCells.add(excludedRow, col);

                excludedRows = new org.wheatgenetics.coordinate.model.RowOrCols(rows);
                excludedRows.add(excludedRow);
            }
            joinedGridModel = new org.wheatgenetics.coordinate.model.JoinedGridModel(
                /* id                           => */ 5                  ,
                /* projectId                    => */ 0                  ,
                /* person                       => */ "testPerson"       ,
                /* activeRow                    => */ 0                  ,
                /* activeCol                    => */ 0                  ,
                /* optionalFields               => */ null               ,
                /* timestamp                    => */ 123                ,

                /* templateId                   => */ 6                  ,
                /* title                        => */ "testTitle"        ,
                /* code                         => */ 1                  ,
                /* rows                         => */ rows               ,
                /* cols                         => */ cols               ,
                /* generatedExcludedCellsAmount => */ 0                  ,
                /* initialExcludedCells         => */ null               ,
                /* excludedRows                 => */ excludedRows.json(),                   // One.
                /* excludedCols                 => */ null               ,
                /* colNumbering                 => */ 1                  ,
                /* rowNumbering                 => */ 0                  ,
                /* templateOptionalFields       => */ null               ,
                /* templateTimestamp            => */ 333                ,

                /* entryModels                  => */ null               );
        }
        joinedGridModel.makeEntryModels();
        org.junit.Assert.assertEquals(expectedCells, joinedGridModel.excludedCellsFromEntries());
    }

    @org.junit.Test public void oneColExcludedCellsFromEntriesWorks()
    {
        final org.wheatgenetics.coordinate.model.Cells           expectedCells  ;
        final org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel;
        {
            final int rows = 5, cols = 5;

            expectedCells = new org.wheatgenetics.coordinate.model.Cells(rows, cols);
            final org.wheatgenetics.coordinate.model.RowOrCols excludedCols;
            {
                final int excludedCol = 4;
                for (int row = 1; row <= rows; row++) expectedCells.add(row, excludedCol);

                excludedCols = new org.wheatgenetics.coordinate.model.RowOrCols(cols);
                excludedCols.add(excludedCol);
            }
            joinedGridModel = new org.wheatgenetics.coordinate.model.JoinedGridModel(
                /* id                           => */ 5                  ,
                /* projectId                    => */ 0                  ,
                /* person                       => */ "testPerson"       ,
                /* activeRow                    => */ 0                  ,
                /* activeCol                    => */ 0                  ,
                /* optionalFields               => */ null               ,
                /* timestamp                    => */ 123                ,

                /* templateId                   => */ 6                  ,
                /* title                        => */ "testTitle"        ,
                /* code                         => */ 1                  ,
                /* rows                         => */ rows               ,
                /* cols                         => */ cols               ,
                /* generatedExcludedCellsAmount => */ 0                  ,
                /* initialExcludedCells         => */ null               ,
                /* excludedRows                 => */ null               ,
                /* excludedCols                 => */ excludedCols.json(),                   // One.
                /* colNumbering                 => */ 1                  ,
                /* rowNumbering                 => */ 0                  ,
                /* templateOptionalFields       => */ null               ,
                /* templateTimestamp            => */ 333                ,

                /* entryModels                  => */ null               );
        }
        joinedGridModel.makeEntryModels();
        org.junit.Assert.assertEquals(expectedCells, joinedGridModel.excludedCellsFromEntries());
    }
    // endregion
    // endregion

    // region export() Package Method Tests
    @org.junit.Test public void nullExportFileExportFails() throws java.io.IOException
    {
        final org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel =
            new org.wheatgenetics.coordinate.model.JoinedGridModel(
                /* id                           => */ 5           ,
                /* projectId                    => */ 0           ,
                /* person                       => */ "testPerson",
                /* activeRow                    => */ 0           ,
                /* activeCol                    => */ 0           ,
                /* optionalFields               => */ null        ,
                /* timestamp                    => */ 123         ,

                /* templateId                   => */ 6           ,
                /* title                        => */ "testTitle" ,
                /* code                         => */ 1           ,
                /* rows                         => */ 5           ,
                /* cols                         => */ 5           ,
                /* generatedExcludedCellsAmount => */ 0           ,
                /* initialExcludedCells         => */ null        ,
                /* excludedRows                 => */ null        ,
                /* excludedCols                 => */ null        ,
                /* colNumbering                 => */ 1           ,
                /* rowNumbering                 => */ 0           ,
                /* templateOptionalFields       => */ null        ,
                /* templateTimestamp            => */ 333         ,

                /* entryModels                  => */ null        );
        org.junit.Assert.assertFalse(joinedGridModel.export(null, "exportFileName",
            new org.wheatgenetics.coordinate.model.JoinedGridModelTest.Helper()));
    }

    @org.junit.Test public void nullHelperExportFails() throws java.io.IOException
    {
        final org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel =
            new org.wheatgenetics.coordinate.model.JoinedGridModel(
                /* id                           => */ 5           ,
                /* projectId                    => */ 0           ,
                /* person                       => */ "testPerson",
                /* activeRow                    => */ 0           ,
                /* activeCol                    => */ 0           ,
                /* optionalFields               => */ null        ,
                /* timestamp                    => */ 123         ,

                /* templateId                   => */ 6           ,
                /* title                        => */ "testTitle" ,
                /* code                         => */ 1           ,
                /* rows                         => */ 5           ,
                /* cols                         => */ 5           ,
                /* generatedExcludedCellsAmount => */ 0           ,
                /* initialExcludedCells         => */ null        ,
                /* excludedRows                 => */ null        ,
                /* excludedCols                 => */ null        ,
                /* colNumbering                 => */ 1           ,
                /* rowNumbering                 => */ 0           ,
                /* templateOptionalFields       => */ null        ,
                /* templateTimestamp            => */ 333         ,

                /* entryModels                  => */ null        );
        org.junit.Assert.assertFalse(joinedGridModel.export(
            new java.io.File(""), "exportFileName", null));
    }
    // endregion
}