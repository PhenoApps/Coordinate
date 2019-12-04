package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * androidx.annotation.IntRange
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 *
 * org.junit.Test
 *
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
        @java.lang.Override @androidx.annotation.Nullable public java.lang.String check(
        @androidx.annotation.IntRange(from = 1) final int              rowIndex,
        @androidx.annotation.IntRange(from = 1) final int              colIndex,
        @androidx.annotation.Nullable           final java.lang.String value   )
        throws org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.CheckException
        {
            throw new org.wheatgenetics.coordinate.model
                .FullyCheckedIncludedEntryModelTest.MeanCheckException();
        }
    }
    // endregion

    private org.wheatgenetics.coordinate.model.FullyCheckedIncludedEntryModelTest.MeanChecker
        meanCheckerInstance = null;                                                     // lazy load

    @androidx.annotation.NonNull private
    org.wheatgenetics.coordinate.model.FullyCheckedIncludedEntryModelTest.MeanChecker meanChecker()
    {
        if (null == this.meanCheckerInstance) this.meanCheckerInstance = new
            org.wheatgenetics.coordinate.model.FullyCheckedIncludedEntryModelTest.MeanChecker();
        return this.meanCheckerInstance;
    }

    @org.junit.Test(expected =
    org.wheatgenetics.coordinate.model.FullyCheckedIncludedEntryModelTest.MeanCheckException.class)
    public void meanSecondConstructorThrows()
    throws org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.CheckException
    {
        new org.wheatgenetics.coordinate.model.FullyCheckedIncludedEntryModel(
            1,1,1,1,"value",0, this.meanChecker());  // throws
    }
}