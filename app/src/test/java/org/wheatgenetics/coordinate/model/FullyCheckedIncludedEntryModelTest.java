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
 * org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.CheckException
 * org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.Checker
 * org.wheatgenetics.coordinate.model.FullyCheckedIncludedEntryModel
 */
@SuppressWarnings({"ClassExplicitlyExtendsObject"})
public class FullyCheckedIncludedEntryModelTest extends Object
implements StringGetter
{
    // region Types
    private static class MeanCheckException
    extends CheckedIncludedEntryModel.CheckException
    { MeanCheckException() { super("You will always fail the check"); } }

    private static class MeanChecker extends Object
    implements CheckedIncludedEntryModel.Checker
    {
        @Override @Nullable public String check(
        @IntRange(from = 1) final int              rowIndex,
        @IntRange(from = 1) final int              colIndex,
        @Nullable           final String value   )
        throws CheckedIncludedEntryModel.CheckException
        {
            throw new FullyCheckedIncludedEntryModelTest.MeanCheckException();
        }
    }
    // endregion

    private FullyCheckedIncludedEntryModelTest.MeanChecker
        meanCheckerInstance = null;                                                     // lazy load

    @NonNull private
    FullyCheckedIncludedEntryModelTest.MeanChecker meanChecker()
    {
        if (null == this.meanCheckerInstance) this.meanCheckerInstance = new
            FullyCheckedIncludedEntryModelTest.MeanChecker();
        return this.meanCheckerInstance;
    }

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

    @Test(expected =
    FullyCheckedIncludedEntryModelTest.MeanCheckException.class)
    public void meanSecondConstructorThrows()
    throws CheckedIncludedEntryModel.CheckException
    {
        new FullyCheckedIncludedEntryModel(1,1,
            1,1,"value",0, this.meanChecker(),this);   // throws
    }
}