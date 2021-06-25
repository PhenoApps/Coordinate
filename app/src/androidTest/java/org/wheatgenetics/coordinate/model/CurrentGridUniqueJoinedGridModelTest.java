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
 * org.wheatgenetics.coordinate.R
 * org.wheatgenetics.coordinate.StringGetter
 *
 * org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel
 * org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.CheckException
 * org.wheatgenetics.coordinate.model.CurrentGridUniqueEntryModels
 * org.wheatgenetics.coordinate.model.CurrentGridUniqueJoinedGridModel
 */
@SuppressWarnings({"ClassExplicitlyExtendsObject"})
public class CurrentGridUniqueJoinedGridModelTest extends Object
implements StringGetter
{
    // region org.wheatgenetics.coordinate.StringGetter Overridden Methods
    @Override @Nullable public String get(
    @StringRes final int resId)
    {
        switch (resId)
        {
            case R.string.CurrentGridDuplicateCheckExceptionMsg:
                return "The %s already has an entry with that value.";
            case R.string.CurrentGridDuplicateCheckExceptionScope:
                return "current grid";
            case R.string.CallCheckThenSetEntryModelInstead:
                return "Call checkThenSetEntryModel() instead";

            default: Assert.fail(); return null;
        }
    }

    @Override @NonNull public String getQuantity(
    @PluralsRes         final int                 resId     ,
    @IntRange(from = 0) final int                 quantity  ,
    @Nullable           final Object... formatArgs)
    throws Resources.NotFoundException { Assert.fail(); return null; }
    // endregion

    @Test(expected = UnsupportedOperationException.class)
    public void setEntryModelThrows()
    {
        final CurrentGridUniqueJoinedGridModel
            currentGridUniqueJoinedGridModel =
                new CurrentGridUniqueJoinedGridModel(
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
    @Test() public void checkThenSetEntryModelWorks()
    throws CheckedIncludedEntryModel.CheckException
    {
        final long                                                            gridId = 1;
        final CurrentGridUniqueEntryModels
            currentGridUniqueEntryModels;
        {
            final int rows = 5, cols = 5;
            currentGridUniqueEntryModels =
                new CurrentGridUniqueEntryModels(
                    gridId, rows, cols,this);
            for (int row = 1; row <= rows; row++) for (int col = 1; col <= cols; col++)
                currentGridUniqueEntryModels.makeIncludedEntry(row, col);
        }

        final CurrentGridUniqueJoinedGridModel
            currentGridUniqueJoinedGridModel =
                new CurrentGridUniqueJoinedGridModel(
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
            final CheckedIncludedEntryModel
                checkedIncludedEntryModel =
                    new CheckedIncludedEntryModel(
                        gridId,1,1, currentGridUniqueEntryModels,this);
            checkedIncludedEntryModel.checkThenSetValue("ABC");                            // throws
            currentGridUniqueJoinedGridModel.checkThenSetEntryModel(                       // throws
                checkedIncludedEntryModel);
        }
        {
            // Not a duplicate because although value is the same the new entry *replaces* the old.
            final CheckedIncludedEntryModel
                checkedIncludedEntryModel =
                    new CheckedIncludedEntryModel(
                        gridId,1,1, currentGridUniqueEntryModels,this);
            checkedIncludedEntryModel.checkThenSetValue("ABC");                            // throws
            currentGridUniqueJoinedGridModel.checkThenSetEntryModel(                       // throws
                checkedIncludedEntryModel);
        }
        {
            // Not a duplicate because value is different.
            final CheckedIncludedEntryModel
                checkedIncludedEntryModel =
                    new CheckedIncludedEntryModel(
                        gridId,2,2, currentGridUniqueEntryModels,this);
            checkedIncludedEntryModel.checkThenSetValue("DEF");                            // throws
            currentGridUniqueJoinedGridModel.checkThenSetEntryModel(                       // throws
                checkedIncludedEntryModel);
        }
    }

    @Test(expected =
        CheckedIncludedEntryModel.CheckException.class)
    public void checkThenSetEntryModelThrows()
    throws CheckedIncludedEntryModel.CheckException
    {
        final long                                                            gridId = 1;
        final CurrentGridUniqueEntryModels
            currentGridUniqueEntryModels;
        {
            final int rows = 5, cols = 5;
            currentGridUniqueEntryModels =
                new CurrentGridUniqueEntryModels(
                    gridId, rows, cols,this);
            for (int row = 1; row <= rows; row++) for (int col = 1; col <= cols; col++)
                currentGridUniqueEntryModels.makeIncludedEntry(row, col);
        }

        final CurrentGridUniqueJoinedGridModel
            currentGridUniqueJoinedGridModel =
                new CurrentGridUniqueJoinedGridModel(
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
            final CheckedIncludedEntryModel
                checkedIncludedEntryModel =
                    new CheckedIncludedEntryModel(
                        gridId,1,1, currentGridUniqueEntryModels,this);
            checkedIncludedEntryModel.checkThenSetValue("ABC");                            // throws
            currentGridUniqueJoinedGridModel.checkThenSetEntryModel(                       // throws
                checkedIncludedEntryModel);
        }
        {
            final CheckedIncludedEntryModel
                checkedIncludedEntryModel =
                    new CheckedIncludedEntryModel(
                        gridId,2,2, currentGridUniqueEntryModels,this);
            checkedIncludedEntryModel.checkThenSetValue("ABC");                            // throws
            currentGridUniqueJoinedGridModel.checkThenSetEntryModel(                       // throws
                checkedIncludedEntryModel);
        }
    }
    // endregion
}