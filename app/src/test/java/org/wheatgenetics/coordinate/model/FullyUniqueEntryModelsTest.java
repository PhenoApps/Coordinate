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
 * org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.CheckException
 * org.wheatgenetics.coordinate.model.FullyUniqueEntryModels
 * org.wheatgenetics.coordinate.model.FullyUniqueEntryModels.DuplicateCheckException
 */
@SuppressWarnings({"ClassExplicitlyExtendsObject"})
public class FullyUniqueEntryModelsTest extends Object
implements StringGetter
{
    // region Types
    private static class DuplicateCheckException
    extends FullyUniqueEntryModels.DuplicateCheckException
    { DuplicateCheckException() { super("A duplicate was simulated."); } }


    private static class MeanUniqueEntryModels
    extends FullyUniqueEntryModels
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
            throw new FullyUniqueEntryModelsTest.DuplicateCheckException();
        }
    }

    private static class NiceUniqueEntryModels
    extends FullyUniqueEntryModels
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
        switch (resId)
        {
            case R.string.CallCheckThenSetInstead:
                return "Call checkThenSet() instead";
            default: Assert.fail(); return null;
        }
    }

    @Override @NonNull public String getQuantity(
    @PluralsRes         final int                 resId     ,
    @IntRange(from = 0) final int                 quantity  ,
    @Nullable           final Object... formatArgs)
    throws Resources.NotFoundException { Assert.fail(); return null; }
    // endregion

    // region set() Overridden Method Tests
    @Test(expected = UnsupportedOperationException.class)
    public void meanSetThrows()
    {
        new FullyUniqueEntryModelsTest.MeanUniqueEntryModels(
            1,1,1,this).set(null) /* throws */;
    }

    @Test(expected = UnsupportedOperationException.class)
    public void niceSetThrows()
    {
        new FullyUniqueEntryModelsTest.NiceUniqueEntryModels(
            1,1,1,this).set(null) /* throws */;
    }
    // endregion
}