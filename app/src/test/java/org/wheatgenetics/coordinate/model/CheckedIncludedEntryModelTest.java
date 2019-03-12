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
 * org.wheatgenetics.coordinate.model.IncludedEntryModel.CheckException
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
public class CheckedIncludedEntryModelTest extends java.lang.Object
{
    // region Types
    private static class MeanCheckException
    extends org.wheatgenetics.coordinate.model.IncludedEntryModel.CheckException
    { MeanCheckException() { super("You will always fail the check"); } }

    private static class MeanChecker extends java.lang.Object
        implements org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.Checker
    {
        @java.lang.Override @android.support.annotation.Nullable public java.lang.String check(
        @android.support.annotation.IntRange(from = 1) final int              rowIndex,
        @android.support.annotation.IntRange(from = 1) final int              colIndex,
        @android.support.annotation.Nullable           final java.lang.String value   )
        throws org.wheatgenetics.coordinate.model.IncludedEntryModel.CheckException
        {
            throw new org.wheatgenetics.coordinate.model
                .CheckedIncludedEntryModelTest.MeanCheckException();
        }
    }

    private static class NiceChecker extends java.lang.Object
    implements org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.Checker
    {
        @java.lang.Override @android.support.annotation.Nullable public java.lang.String check(
        @android.support.annotation.IntRange(from = 1) final int              rowIndex,
        @android.support.annotation.IntRange(from = 1) final int              colIndex,
        @android.support.annotation.Nullable           final java.lang.String value   )
        throws org.wheatgenetics.coordinate.model.IncludedEntryModel.CheckException
        { return value; }
    }
    // endregion

    // region Fields
    private org.wheatgenetics.coordinate.model.CheckedIncludedEntryModelTest.MeanChecker
        meanCheckerInstance = null;                                                     // lazy load
    private org.wheatgenetics.coordinate.model.CheckedIncludedEntryModelTest.NiceChecker
        niceCheckerInstance = null;                                                     // lazy load
    // endregion

    // region Private Methods
    @android.support.annotation.NonNull private
    org.wheatgenetics.coordinate.model.CheckedIncludedEntryModelTest.MeanChecker meanChecker()
    {
        if (null == this.meanCheckerInstance) this.meanCheckerInstance =
            new org.wheatgenetics.coordinate.model.CheckedIncludedEntryModelTest.MeanChecker();
        return this.meanCheckerInstance;
    }

    @android.support.annotation.NonNull private
    org.wheatgenetics.coordinate.model.CheckedIncludedEntryModelTest.NiceChecker niceChecker()
    {
        if (null == this.niceCheckerInstance) this.niceCheckerInstance =
            new org.wheatgenetics.coordinate.model.CheckedIncludedEntryModelTest.NiceChecker();
        return this.niceCheckerInstance;
    }
    // endregion

    // region Second Constructor Tests
    @org.junit.Test(expected =
        org.wheatgenetics.coordinate.model.CheckedIncludedEntryModelTest.MeanCheckException.class)
    public void meanSecondConstructorThrows()
    throws org.wheatgenetics.coordinate.model.IncludedEntryModel.CheckException
    {
        new org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel(
            1,1,1,1,"value",0, this.meanChecker());   // throws
    }

    @org.junit.Test() public void niceSecondConstructorWorks()
    throws org.wheatgenetics.coordinate.model.IncludedEntryModel.CheckException
    {
        final java.lang.String value = "value";
        org.junit.Assert.assertEquals(value,
            new org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel(1,        // throws
                1,1,1, value,0, this.niceChecker()).getValue());
    }
    // endregion

    @org.junit.Test() public void setValueWorks()
    throws org.wheatgenetics.coordinate.model.IncludedEntryModel.CheckException
    {
        final org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel
            checkedIncludedEntryModel;
        {
            final java.lang.String firstValue = "firstValue";
            checkedIncludedEntryModel =
                new org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel(          // throws
                    1,1,1,1, firstValue,0, this.niceChecker());
            org.junit.Assert.assertEquals(firstValue, checkedIncludedEntryModel.getValue());
        }

        final java.lang.String secondValue = "secondValue";
        checkedIncludedEntryModel.setValue(secondValue);
        org.junit.Assert.assertEquals(secondValue, checkedIncludedEntryModel.getValue());
    }
}