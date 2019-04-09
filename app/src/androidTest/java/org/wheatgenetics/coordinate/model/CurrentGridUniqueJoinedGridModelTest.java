package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * org.junit.Test
 *
 * org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel
 * org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.CheckException
 * org.wheatgenetics.coordinate.model.CurrentGridUniqueEntryModels
 * org.wheatgenetics.coordinate.model.CurrentGridUniqueJoinedGridModel
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
public class CurrentGridUniqueJoinedGridModelTest extends java.lang.Object
{
    @org.junit.Test(expected = java.lang.UnsupportedOperationException.class)
    public void setEntryModelThrows()
    {
        final org.wheatgenetics.coordinate.model.CurrentGridUniqueJoinedGridModel
            currentGridUniqueJoinedGridModel =
                new org.wheatgenetics.coordinate.model.CurrentGridUniqueJoinedGridModel(
                    /* id                           => */5,
                    /* projectId                    => */0,
                    /* person                       => */"testPerson",
                    /* activeRow                    => */0,
                    /* activeCol                    => */0,
                    /* optionalFields               => */null,
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

                    /* entryModels                  => */null);
        currentGridUniqueJoinedGridModel.setEntryModel(null);                              // throws
    }

    // region checkThenSetEntryModel() Public Method Tests
    @org.junit.Test() public void checkThenSetEntryModelWorks()
    throws org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.CheckException
    {
        final long                                                            gridId = 1;
        final org.wheatgenetics.coordinate.model.CurrentGridUniqueEntryModels
            currentGridUniqueEntryModels;
        {
            final int rows = 5, cols = 5;
            currentGridUniqueEntryModels =
                new org.wheatgenetics.coordinate.model.CurrentGridUniqueEntryModels(
                    gridId, rows, cols);
            for (int row = 1; row <= rows; row++) for (int col = 1; col <= cols; col++)
                currentGridUniqueEntryModels.makeIncludedEntry(row, col);
        }

        final org.wheatgenetics.coordinate.model.CurrentGridUniqueJoinedGridModel
            currentGridUniqueJoinedGridModel =
                new org.wheatgenetics.coordinate.model.CurrentGridUniqueJoinedGridModel(
                    /* id                            => */5,
                    /* projectId                     => */0,
                    /* person                        => */"testPerson",
                    /* activeRow                     => */0,
                    /* activeCol                     => */0,
                    /* optionalFields                => */null,
                    /* timestamp                     => */123,

                    /* templateId                    => */6,
                    /* title                         => */"testTitle",
                    /* code                          => */1,
                    /* rows                          => */5,
                    /* cols                          => */2,
                    /* generatedExcludedCellsAmount  => */0,
                    /* initialExcludedCells          => */null,
                    /* excludedRows                  => */null,
                    /* excludedCols                  => */null,
                    /* colNumbering                  => */1,
                    /* rowNumbering                  => */0,
                    /* entryLabel                    => */null,
                    /* templateOptionalFields        => */null,
                    /* templateTimestamp             => */333,

                    /* currentGridUniqueEEntryModels => */ currentGridUniqueEntryModels);
        {
            final org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel
                checkedIncludedEntryModel =
                    new org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel(
                        gridId,1,1, currentGridUniqueEntryModels);
            checkedIncludedEntryModel.checkThenSetValue("ABC");                            // throws
            currentGridUniqueJoinedGridModel.checkThenSetEntryModel(                       // throws
                checkedIncludedEntryModel);
        }

        {
            final org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel
                checkedIncludedEntryModel =
                    new org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel(
                        gridId,1,1, currentGridUniqueEntryModels);
            checkedIncludedEntryModel.checkThenSetValue("ABC");                            // throws
            currentGridUniqueJoinedGridModel.checkThenSetEntryModel(                       // throws
                checkedIncludedEntryModel);
        }

        {
            final org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel
                checkedIncludedEntryModel =
                    new org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel(
                        gridId,2,2, currentGridUniqueEntryModels);
            checkedIncludedEntryModel.checkThenSetValue("DEF");                            // throws
            currentGridUniqueJoinedGridModel.checkThenSetEntryModel(                       // throws
                checkedIncludedEntryModel);
        }
    }

    @org.junit.Test(expected =
        org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.CheckException.class)
    public void checkThenSetEntryModelThrows()
    throws org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.CheckException
    {
        final long                                                            gridId = 1;
        final org.wheatgenetics.coordinate.model.CurrentGridUniqueEntryModels
            currentGridUniqueEntryModels;
        {
            final int rows = 5, cols = 5;
            currentGridUniqueEntryModels =
                new org.wheatgenetics.coordinate.model.CurrentGridUniqueEntryModels(
                    gridId, rows, cols);
            for (int row = 1; row <= rows; row++) for (int col = 1; col <= cols; col++)
                currentGridUniqueEntryModels.makeIncludedEntry(row, col);
        }

        final org.wheatgenetics.coordinate.model.CurrentGridUniqueJoinedGridModel
            currentGridUniqueJoinedGridModel =
            new org.wheatgenetics.coordinate.model.CurrentGridUniqueJoinedGridModel(
                /* id                            => */5,
                /* projectId                     => */0,
                /* person                        => */"testPerson",
                /* activeRow                     => */0,
                /* activeCol                     => */0,
                /* optionalFields                => */null,
                /* timestamp                     => */123,

                /* templateId                    => */6,
                /* title                         => */"testTitle",
                /* code                          => */1,
                /* rows                          => */5,
                /* cols                          => */2,
                /* generatedExcludedCellsAmount  => */0,
                /* initialExcludedCells          => */null,
                /* excludedRows                  => */null,
                /* excludedCols                  => */null,
                /* colNumbering                  => */1,
                /* rowNumbering                  => */0,
                /* entryLabel                    => */null,
                /* templateOptionalFields        => */null,
                /* templateTimestamp             => */333,

                /* currentGridUniqueEEntryModels => */ currentGridUniqueEntryModels);
        {
            final org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel
                checkedIncludedEntryModel =
                new org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel(
                    gridId,1,1, currentGridUniqueEntryModels);
            checkedIncludedEntryModel.checkThenSetValue("ABC");                            // throws
            currentGridUniqueJoinedGridModel.checkThenSetEntryModel(                       // throws
                checkedIncludedEntryModel);
        }

        {
            final org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel
                checkedIncludedEntryModel =
                new org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel(
                    gridId,2,2, currentGridUniqueEntryModels);
            checkedIncludedEntryModel.checkThenSetValue("ABC");                            // throws
            currentGridUniqueJoinedGridModel.checkThenSetEntryModel(                       // throws
                checkedIncludedEntryModel);
        }
    }
    // endregion
}