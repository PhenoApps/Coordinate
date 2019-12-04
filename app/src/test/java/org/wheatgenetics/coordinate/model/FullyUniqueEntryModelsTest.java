package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * androidx.annotation.IntRange
 * androidx.annotation.Nullable
 *
 * org.junit.Test
 *
 * org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.CheckException
 * org.wheatgenetics.coordinate.model.FullyUniqueEntryModels
 * org.wheatgenetics.coordinate.model.FullyUniqueEntryModels.DuplicateCheckException
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
public class FullyUniqueEntryModelsTest extends java.lang.Object
{
    // region Types
    private static class DuplicateCheckException
    extends org.wheatgenetics.coordinate.model.FullyUniqueEntryModels.DuplicateCheckException
    { DuplicateCheckException() { super("A duplicate was simulated."); } }


    private static class MeanUniqueEntryModels
    extends org.wheatgenetics.coordinate.model.FullyUniqueEntryModels
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
            throw new org.wheatgenetics.coordinate.model
                .FullyUniqueEntryModelsTest.DuplicateCheckException();
        }
    }

    private static class NiceUniqueEntryModels
    extends org.wheatgenetics.coordinate.model.FullyUniqueEntryModels
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

    // region set() Overridden Method Tests
    @org.junit.Test(expected = java.lang.UnsupportedOperationException.class)
    public void meanSetThrows()
    {
        new org.wheatgenetics.coordinate.model.FullyUniqueEntryModelsTest.MeanUniqueEntryModels(
            1,1,1).set(null) /* throws */;
    }

    @org.junit.Test(expected = java.lang.UnsupportedOperationException.class)
    public void niceSetThrows()
    {
        new org.wheatgenetics.coordinate.model.FullyUniqueEntryModelsTest.NiceUniqueEntryModels(
            1,1,1).set(null) /* throws */;
    }
    // endregion
}