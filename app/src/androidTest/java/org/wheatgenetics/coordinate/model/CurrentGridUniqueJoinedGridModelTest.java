package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * androidx.annotation.Nullable
 * androidx.annotation.StringRes
 *
 * org.junit.Test
 *
 * org.wheatgenetics.coordinate.R
 * org.wheatgenetics.coordinate.StringGetter
 *
 * org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel
 * org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.CheckException
 * org.wheatgenetics.coordinate.model.CurrentGridUniqueEntryModels
 * org.wheatgenetics.coordinate.model.CurrentGridUniqueJoinedGridModel
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
public class CurrentGridUniqueJoinedGridModelTest extends java.lang.Object
implements org.wheatgenetics.coordinate.StringGetter
{
    // region org.wheatgenetics.coordinate.StringGetter Overridden Method
    @java.lang.Override @androidx.annotation.Nullable public java.lang.String get(
    @androidx.annotation.StringRes final int resId)
    {
        switch (resId)
        {
            case org.wheatgenetics.coordinate.R.string.BaseOptionalFieldPersonFieldName:
                return "Person";

            case org.wheatgenetics.coordinate.R.string.BaseOptionalFieldNameFieldName:
                return "Name";

            case org.wheatgenetics.coordinate.R.string.BaseOptionalFieldIdentificationFieldName:
                return "Identification";

            default: return null;
        }
    }
    // endregion

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
                    /* stringGetter                 => */this,
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
            // Not a duplicate because although value is the same the new entry *replaces* the old.
            final org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel
                checkedIncludedEntryModel =
                    new org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel(
                        gridId,1,1, currentGridUniqueEntryModels);
            checkedIncludedEntryModel.checkThenSetValue("ABC");                            // throws
            currentGridUniqueJoinedGridModel.checkThenSetEntryModel(                       // throws
                checkedIncludedEntryModel);
        }
        {
            // Not a duplicate because value is different.
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
                    /* stringGetter                 => */this,
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