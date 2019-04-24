package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * android.support.annotation.IntRange
 * android.support.annotation.Nullable
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
        @android.support.annotation.IntRange(from = 1) final long gridId,
        @android.support.annotation.IntRange(from = 1) final int  rows  ,
        @android.support.annotation.IntRange(from = 1) final int  cols  )
        { super(gridId, rows, cols); }

        @java.lang.Override @android.support.annotation.Nullable public java.lang.String check(
        @android.support.annotation.IntRange(from = 1) final int              rowIndex,
        @android.support.annotation.IntRange(from = 1) final int              colIndex,
        @android.support.annotation.Nullable           final java.lang.String value   )
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
        @android.support.annotation.IntRange(from = 1) final long gridId,
        @android.support.annotation.IntRange(from = 1) final int  rows  ,
        @android.support.annotation.IntRange(from = 1) final int  cols  )
        { super(gridId, rows, cols); }

        @java.lang.Override @android.support.annotation.Nullable public java.lang.String check(
        @android.support.annotation.IntRange(from = 1) final int              rowIndex,
        @android.support.annotation.IntRange(from = 1) final int              colIndex,
        @android.support.annotation.Nullable           final java.lang.String value   )
        { return value; }
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