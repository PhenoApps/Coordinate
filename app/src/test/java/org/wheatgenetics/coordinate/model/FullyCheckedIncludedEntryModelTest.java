package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * android.support.annotation.IntRange
 * android.support.annotation.NonNull
 * android.support.annotation.Nullable
 *
 * org.junit.Test
 *
 * org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel
 * org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.CheckException
 * org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.Checker
 * org.wheatgenetics.coordinate.model.FullyCheckedIncludedEntryModel
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
public class FullyCheckedIncludedEntryModelTest extends java.lang.Object
{
    // region Types
    private static class MeanCheckException
    extends org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.CheckException
    { MeanCheckException() { super("You will always fail the check"); } }

    private static class MeanChecker extends java.lang.Object
    implements org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.Checker
    {
        @java.lang.Override @android.support.annotation.Nullable public java.lang.String check(
        @android.support.annotation.IntRange(from = 1) final int              rowIndex,
        @android.support.annotation.IntRange(from = 1) final int              colIndex,
        @android.support.annotation.Nullable           final java.lang.String value   )
        throws org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.CheckException
        {
            throw new org.wheatgenetics.coordinate.model
                .FullyCheckedIncludedEntryModelTest.MeanCheckException();
        }
    }
    // endregion

    private org.wheatgenetics.coordinate.model.FullyCheckedIncludedEntryModelTest.MeanChecker
        meanCheckerInstance = null;                                                     // lazy load

    @android.support.annotation.NonNull private
    org.wheatgenetics.coordinate.model.FullyCheckedIncludedEntryModelTest.MeanChecker meanChecker()
    {
        if (null == this.meanCheckerInstance) this.meanCheckerInstance = new
            org.wheatgenetics.coordinate.model.FullyCheckedIncludedEntryModelTest.MeanChecker();
        return this.meanCheckerInstance;
    }

    @org.junit.Test(expected = org.wheatgenetics.coordinate
        .model.FullyCheckedIncludedEntryModelTest.MeanCheckException.class)
    public void meanSecondConstructorThrows()
    throws org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.CheckException
    {
        new org.wheatgenetics.coordinate.model.FullyCheckedIncludedEntryModel(
            1,1,1,1,"value",0, this.meanChecker());   // throws
    }
}