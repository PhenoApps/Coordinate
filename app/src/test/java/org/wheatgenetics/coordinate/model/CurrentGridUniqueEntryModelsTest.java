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
 * org.wheatgenetics.coordinate.model.CurrentGridUniqueEntryModels.DuplicateCheckException
 */
@SuppressWarnings({"ClassExplicitlyExtendsObject"})
public class CurrentGridUniqueEntryModelsTest extends Object
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

            default: Assert.fail(); return null;
        }
    }

    @Override @NonNull public String getQuantity(
    @PluralsRes         final int                 resId     ,
    @IntRange(from = 0) final int                 quantity  ,
    @Nullable           final Object... formatArgs)
    throws Resources.NotFoundException { Assert.fail(); return null; }
    // endregion

    @Test(expected = CurrentGridUniqueEntryModels.DuplicateCheckException.class)
    public void checkThenSetThrows()
    throws CheckedIncludedEntryModel.CheckException
    {
        final CurrentGridUniqueEntryModels
            currentGridUniqueEntryModels =
                new CurrentGridUniqueEntryModels(
                    1,1,2,this);
        {
            // Set first entry.
            final CheckedIncludedEntryModel
                checkedIncludedEntryModel =
                    new CheckedIncludedEntryModel(
                        1,1,1, currentGridUniqueEntryModels,this);
            checkedIncludedEntryModel.checkThenSetValue("1.1");                            // throws
            currentGridUniqueEntryModels.checkThenSet(checkedIncludedEntryModel);          // throws
        }
        {
            // Set same value but is not a duplicate since entry *replaces* first entry.
            final CheckedIncludedEntryModel
                checkedIncludedEntryModel =
                    new CheckedIncludedEntryModel(
                        1,1,1, currentGridUniqueEntryModels,this);
            checkedIncludedEntryModel.checkThenSetValue("1.1");                            // throws
            currentGridUniqueEntryModels.checkThenSet(checkedIncludedEntryModel);          // throws
        }
        {
            // Set different value so is not a duplicate.
            final CheckedIncludedEntryModel
                checkedIncludedEntryModel =
                    new CheckedIncludedEntryModel(
                        1,1,2, currentGridUniqueEntryModels,this);
            checkedIncludedEntryModel.checkThenSetValue("2.2");                            // throws
            currentGridUniqueEntryModels.checkThenSet(checkedIncludedEntryModel);          // throws
        }
        {
            // Set same value in a different location: duplicate!
            final CheckedIncludedEntryModel
                checkedIncludedEntryModel =
                new CheckedIncludedEntryModel(
                    1,1,1, currentGridUniqueEntryModels,this);
            checkedIncludedEntryModel.checkThenSetValue("2.2");                            // throws
            currentGridUniqueEntryModels.checkThenSet(checkedIncludedEntryModel);          // throws
        }
    }
}