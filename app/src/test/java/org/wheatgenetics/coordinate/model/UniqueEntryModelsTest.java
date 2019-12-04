package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * androidx.annotation.IntRange
 * androidx.annotation.Nullable
 *
 * org.junit.Test
 *
 * org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel
 * org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.CheckException
 * org.wheatgenetics.coordinate.model.UniqueEntryModels
 * org.wheatgenetics.coordinate.model.UniqueEntryModels.DuplicateCheckException
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
public class UniqueEntryModelsTest extends java.lang.Object
{
    // region Types
    private static class DuplicateCheckException
    extends org.wheatgenetics.coordinate.model.UniqueEntryModels.DuplicateCheckException
    { DuplicateCheckException() { super("A duplicate was simulated."); } }


    private static class MeanUniqueEntryModels
    extends org.wheatgenetics.coordinate.model.UniqueEntryModels
    {
        MeanUniqueEntryModels(
        @java.lang.SuppressWarnings({"SameParameterValue"}) @androidx.annotation.IntRange(from = 1)
            final long gridId,
        @java.lang.SuppressWarnings({"SameParameterValue"}) @androidx.annotation.IntRange(from = 1)
            final int rows,
        @java.lang.SuppressWarnings({"SameParameterValue"}) @androidx.annotation.IntRange(from = 1)
            final int cols)
        { super(gridId, rows, cols); }

        @java.lang.Override @androidx.annotation.Nullable public java.lang.String check(
        @androidx.annotation.IntRange(from = 1) final int              rowIndex,
        @androidx.annotation.IntRange(from = 1) final int              colIndex,
        @androidx.annotation.Nullable           final java.lang.String value   )
        throws org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.CheckException
        {
            throw new
                org.wheatgenetics.coordinate.model.UniqueEntryModelsTest.DuplicateCheckException();
        }
    }

    private static class NiceUniqueEntryModels
    extends org.wheatgenetics.coordinate.model.UniqueEntryModels
    {
        NiceUniqueEntryModels(
        @java.lang.SuppressWarnings({"SameParameterValue"}) @androidx.annotation.IntRange(from = 1)
            final long gridId,
        @java.lang.SuppressWarnings({"SameParameterValue"}) @androidx.annotation.IntRange(from = 1)
            final int rows,
        @java.lang.SuppressWarnings({"SameParameterValue"}) @androidx.annotation.IntRange(from = 1)
            final int cols)
        { super(gridId, rows, cols); }

        @java.lang.Override @androidx.annotation.Nullable public java.lang.String check(
        @androidx.annotation.IntRange(from = 1) final int              rowIndex,
        @androidx.annotation.IntRange(from = 1) final int              colIndex,
        @androidx.annotation.Nullable           final java.lang.String value   ) { return value; }
    }
    // endregion

    // region checkThenSet() Public Method Tests
    @org.junit.Test(expected =
        org.wheatgenetics.coordinate.model.UniqueEntryModelsTest.DuplicateCheckException.class)
    public void meanCheckThenSetThrows()
    throws org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.CheckException
    {
        final org.wheatgenetics.coordinate.model.UniqueEntryModelsTest.MeanUniqueEntryModels
            meanUniqueEntryModels =
                new org.wheatgenetics.coordinate.model.UniqueEntryModelsTest.MeanUniqueEntryModels(
                    1,1,1);
        meanUniqueEntryModels.checkThenSet(                                                // throws
            new org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel(
                1,1,1, meanUniqueEntryModels));
    }

    @org.junit.Test() public void niceCheckThenSetThrows()
    throws org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.CheckException
    {
        new org.wheatgenetics.coordinate.model.UniqueEntryModelsTest.NiceUniqueEntryModels(
            1,1,1).checkThenSet(null) /* throws */;
    }
    // endregion
}