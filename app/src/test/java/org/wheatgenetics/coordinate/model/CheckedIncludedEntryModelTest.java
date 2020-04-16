package org.wheatgenetics.coordinate.model;

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
 * org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.Checker
 * org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.CheckException
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
public class CheckedIncludedEntryModelTest extends java.lang.Object
implements org.wheatgenetics.coordinate.StringGetter
{
    private static class NiceChecker extends java.lang.Object
    implements org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.Checker
    {
        @java.lang.Override @androidx.annotation.Nullable public java.lang.String check(
        @androidx.annotation.IntRange(from = 1) final int              rowIndex,
        @androidx.annotation.IntRange(from = 1) final int              colIndex,
        @androidx.annotation.Nullable           final java.lang.String value   )
        { return value; }
    }

    private org.wheatgenetics.coordinate.model.CheckedIncludedEntryModelTest.NiceChecker
        niceCheckerInstance = null;                                                     // lazy load

    @androidx.annotation.NonNull private
    org.wheatgenetics.coordinate.model.CheckedIncludedEntryModelTest.NiceChecker niceChecker()
    {
        if (null == this.niceCheckerInstance) this.niceCheckerInstance =
            new org.wheatgenetics.coordinate.model.CheckedIncludedEntryModelTest.NiceChecker();
        return this.niceCheckerInstance;
    }

    // region org.wheatgenetics.coordinate.StringGetter Overridden Methods
    @java.lang.Override @androidx.annotation.Nullable public java.lang.String get(
    @androidx.annotation.StringRes final int resId)
    {
        // noinspection SwitchStatementWithTooFewBranches
        switch (resId) { default: org.junit.Assert.fail(); return null; }
    }

    @java.lang.Override @androidx.annotation.NonNull public java.lang.String getQuantity(
    @androidx.annotation.PluralsRes         final int                 resId     ,
    @androidx.annotation.IntRange(from = 0) final int                 quantity  ,
    @androidx.annotation.Nullable           final java.lang.Object... formatArgs)
    throws android.content.res.Resources.NotFoundException { org.junit.Assert.fail(); return null; }
    // endregion

    // region Tests
    @org.junit.Test() public void niceSecondConstructorWorks()
    {
        final java.lang.String value = "value";
        org.junit.Assert.assertEquals(value,
            new org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel(1,1,
                1,1, value,0, this.niceChecker(),this).getValue());
    }

    @org.junit.Test(expected = java.lang.UnsupportedOperationException.class)
    public void setValueThrows()
    {
        final org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel
            checkedIncludedEntryModel;
        {
            final java.lang.String firstValue = "firstValue";
            checkedIncludedEntryModel =
                new org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel(1,1,
                    1,1, firstValue,0, this.niceChecker(),this);
            org.junit.Assert.assertEquals(firstValue, checkedIncludedEntryModel.getValue());
        }
        checkedIncludedEntryModel.setValue("secondValue");          // throws java.lang.Unsupported-
    }                                                               //  OperationException

    @org.junit.Test() public void checkThenSetValueWorks()
    throws org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.CheckException
    {
        final org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel
            checkedIncludedEntryModel;
        {
            final java.lang.String firstValue = "firstValue";
            checkedIncludedEntryModel =
                new org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel(1,1,
                    1,1, firstValue,0, this.niceChecker(),this);
            org.junit.Assert.assertEquals(firstValue, checkedIncludedEntryModel.getValue());
        }

        final java.lang.String secondValue = "secondValue";
        checkedIncludedEntryModel.checkThenSetValue(secondValue);                          // throws
        org.junit.Assert.assertEquals(secondValue, checkedIncludedEntryModel.getValue());
    }
    // endregion
}