package org.wheatgenetics.coordinate.model;

import android.content.res.Resources;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.PluralsRes;
import androidx.annotation.StringRes;

import org.junit.Assert;
import org.junit.Test;
import org.wheatgenetics.coordinate.StringGetter;

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
 * org.wheatgenetics.coordinate.StringGetter
 *
 * org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel
 * org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.CheckException
 * org.wheatgenetics.coordinate.model.UniqueEntryModels
 * org.wheatgenetics.coordinate.model.UniqueEntryModels.DuplicateCheckException
 */
@SuppressWarnings({"ClassExplicitlyExtendsObject"}) public class UniqueEntryModelsTest
extends Object implements StringGetter
{
    // region Types
    private static class DuplicateCheckException
    extends UniqueEntryModels.DuplicateCheckException
    { DuplicateCheckException() { super("A duplicate was simulated."); } }


    private static class MeanUniqueEntryModels
    extends UniqueEntryModels
    {
        MeanUniqueEntryModels(
        @SuppressWarnings({"SameParameterValue"}) @IntRange(from = 1)
            final long gridId,
        @SuppressWarnings({"SameParameterValue"}) @IntRange(from = 1)
            final int rows,
        @SuppressWarnings({"SameParameterValue"}) @IntRange(from = 1)
            final int cols,
        @NonNull final StringGetter stringGetter)
        { super(gridId, rows, cols, stringGetter); }

        @Override @Nullable public String check(
        @IntRange(from = 1) final int              rowIndex,
        @IntRange(from = 1) final int              colIndex,
        @Nullable           final String value   )
        throws CheckedIncludedEntryModel.CheckException
        {
            throw new
                UniqueEntryModelsTest.DuplicateCheckException();
        }
    }

    private static class NiceUniqueEntryModels
    extends UniqueEntryModels
    {
        NiceUniqueEntryModels(
        @SuppressWarnings({"SameParameterValue"}) @IntRange(from = 1)
            final long gridId,
        @SuppressWarnings({"SameParameterValue"}) @IntRange(from = 1)
            final int rows,
        @SuppressWarnings({"SameParameterValue"}) @IntRange(from = 1)
            final int cols,
        @NonNull final StringGetter stringGetter)
        { super(gridId, rows, cols, stringGetter); }

        @Override @Nullable public String check(
        @IntRange(from = 1) final int              rowIndex,
        @IntRange(from = 1) final int              colIndex,
        @Nullable           final String value   ) { return value; }
    }
    // endregion

    // region org.wheatgenetics.coordinate.StringGetter Overridden Methods
    @Override @Nullable public String get(
    @StringRes final int resId)
    {
        // noinspection SwitchStatementWithTooFewBranches
        switch (resId) { default: Assert.fail(); return null; }
    }

    @Override @NonNull public String getQuantity(
    @PluralsRes         final int                 resId     ,
    @IntRange(from = 0) final int                 quantity  ,
    @Nullable           final Object... formatArgs)
    throws Resources.NotFoundException { Assert.fail(); return null; }
    // endregion

    // region checkThenSet() Public Method Tests
    @Test(expected =
        UniqueEntryModelsTest.DuplicateCheckException.class)
    public void meanCheckThenSetThrows()
    throws CheckedIncludedEntryModel.CheckException
    {
        final UniqueEntryModelsTest.MeanUniqueEntryModels
            meanUniqueEntryModels =
                new UniqueEntryModelsTest.MeanUniqueEntryModels(
                    1,1,1,this);
        meanUniqueEntryModels.checkThenSet(                                                // throws
            new CheckedIncludedEntryModel(
                1,1,1, meanUniqueEntryModels,this));
    }

    @Test() public void niceCheckThenSetThrows()
    throws CheckedIncludedEntryModel.CheckException
    {
        new UniqueEntryModelsTest.NiceUniqueEntryModels(
            1,1,1,this).checkThenSet(null) /* throws */;
    }
    // endregion
}