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
 * androidx.annotation.NonNull
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
 * org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.Checker
 * org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.CheckException
 */
@SuppressWarnings({"ClassExplicitlyExtendsObject"})
public class CheckedIncludedEntryModelTest extends Object
implements StringGetter
{
    private static class NiceChecker extends Object
    implements CheckedIncludedEntryModel.Checker
    {
        @Override @Nullable public String check(
        @IntRange(from = 1) final int              rowIndex,
        @IntRange(from = 1) final int              colIndex,
        @Nullable           final String value   )
        { return value; }
    }

    private CheckedIncludedEntryModelTest.NiceChecker
        niceCheckerInstance = null;                                                     // lazy load

    @NonNull private
    CheckedIncludedEntryModelTest.NiceChecker niceChecker()
    {
        if (null == this.niceCheckerInstance) this.niceCheckerInstance =
            new CheckedIncludedEntryModelTest.NiceChecker();
        return this.niceCheckerInstance;
    }

    // region org.wheatgenetics.coordinate.StringGetter Overridden Methods
    @Override @Nullable public String get(
    @StringRes final int resId)
    {
        // noinspection SwitchStatementWithTooFewBranches
        switch (resId)
        {
            case R.string.CallCheckThenSetValueInstead:
                return "Call checkThenSetValue() instead";

            default: Assert.fail(); return null;
        }
    }

    @Override @NonNull public String getQuantity(
    @PluralsRes         final int                 resId     ,
    @IntRange(from = 0) final int                 quantity  ,
    @Nullable           final Object... formatArgs)
    throws Resources.NotFoundException { Assert.fail(); return null; }
    // endregion

    // region Tests
    @Test() public void niceSecondConstructorWorks()
    {
        final String value = "value";
        Assert.assertEquals(value,
            new CheckedIncludedEntryModel(1,1,
                1,1, value,0, this.niceChecker(),this).getValue());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void setValueThrows()
    {
        final CheckedIncludedEntryModel
            checkedIncludedEntryModel;
        {
            final String firstValue = "firstValue";
            checkedIncludedEntryModel =
                new CheckedIncludedEntryModel(1,1,
                    1,1, firstValue,0, this.niceChecker(),this);
            Assert.assertEquals(firstValue, checkedIncludedEntryModel.getValue());
        }
        checkedIncludedEntryModel.setValue("secondValue");          // throws java.lang.Unsupported-
    }                                                               //  OperationException

    @Test() public void checkThenSetValueWorks()
    throws CheckedIncludedEntryModel.CheckException
    {
        final CheckedIncludedEntryModel
            checkedIncludedEntryModel;
        {
            final String firstValue = "firstValue";
            checkedIncludedEntryModel =
                new CheckedIncludedEntryModel(1,1,
                    1,1, firstValue,0, this.niceChecker(),this);
            Assert.assertEquals(firstValue, checkedIncludedEntryModel.getValue());
        }

        final String secondValue = "secondValue";
        checkedIncludedEntryModel.checkThenSetValue(secondValue);                          // throws
        Assert.assertEquals(secondValue, checkedIncludedEntryModel.getValue());
    }
    // endregion
}