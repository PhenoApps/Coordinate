package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * android.support.annotation.IntRange
 * android.support.annotation.NonNull
 * android.support.annotation.Nullable
 *
 * org.junit.Assert
 * org.junit.Test
 *
 * org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel
 * org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.Checker
 * org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.CheckException
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
public class CheckedIncludedEntryModelTest extends java.lang.Object
{
    private static class NiceChecker extends java.lang.Object
    implements org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.Checker
    {
        @java.lang.Override @android.support.annotation.Nullable public java.lang.String check(
        @android.support.annotation.IntRange(from = 1) final int              rowIndex,
        @android.support.annotation.IntRange(from = 1) final int              colIndex,
        @android.support.annotation.Nullable           final java.lang.String value   )
        { return value; }
    }

    private org.wheatgenetics.coordinate.model.CheckedIncludedEntryModelTest.NiceChecker
        niceCheckerInstance = null;                                                     // lazy load

    @android.support.annotation.NonNull private
    org.wheatgenetics.coordinate.model.CheckedIncludedEntryModelTest.NiceChecker niceChecker()
    {
        if (null == this.niceCheckerInstance) this.niceCheckerInstance =
            new org.wheatgenetics.coordinate.model.CheckedIncludedEntryModelTest.NiceChecker();
        return this.niceCheckerInstance;
    }

    @org.junit.Test() public void niceSecondConstructorWorks()
    {
        final java.lang.String value = "value";
        org.junit.Assert.assertEquals(value,
            new org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel(1,
                1,1,1, value,0, this.niceChecker()).getValue());
    }

    @org.junit.Test(expected = java.lang.UnsupportedOperationException.class)
    public void setValueThrows()
    {
        final org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel
            checkedIncludedEntryModel;
        {
            final java.lang.String firstValue = "firstValue";
            checkedIncludedEntryModel = new
                org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel(
                    1,1,1,1, firstValue,0,
                    this.niceChecker());
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
                new org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel(
                    1,1,1,1, firstValue,0, this.niceChecker());
            org.junit.Assert.assertEquals(firstValue, checkedIncludedEntryModel.getValue());
        }

        final java.lang.String secondValue = "secondValue";
        checkedIncludedEntryModel.checkThenSetValue(secondValue);                          // throws
        org.junit.Assert.assertEquals(secondValue, checkedIncludedEntryModel.getValue());
    }
}