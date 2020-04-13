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
 * org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.CheckException
 * org.wheatgenetics.coordinate.model.UniqueEntryModels
 * org.wheatgenetics.coordinate.model.UniqueEntryModels.DuplicateCheckException
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"}) public class UniqueEntryModelsTest
extends java.lang.Object implements org.wheatgenetics.coordinate.StringGetter
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
            final int cols,
        @androidx.annotation.NonNull final org.wheatgenetics.coordinate.StringGetter stringGetter)
        { super(gridId, rows, cols, stringGetter); }

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
            final int cols,
        @androidx.annotation.NonNull final org.wheatgenetics.coordinate.StringGetter stringGetter)
        { super(gridId, rows, cols, stringGetter); }

        @java.lang.Override @androidx.annotation.Nullable public java.lang.String check(
        @androidx.annotation.IntRange(from = 1) final int              rowIndex,
        @androidx.annotation.IntRange(from = 1) final int              colIndex,
        @androidx.annotation.Nullable           final java.lang.String value   ) { return value; }
    }
    // endregion

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

    // region checkThenSet() Public Method Tests
    @org.junit.Test(expected =
        org.wheatgenetics.coordinate.model.UniqueEntryModelsTest.DuplicateCheckException.class)
    public void meanCheckThenSetThrows()
    throws org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.CheckException
    {
        final org.wheatgenetics.coordinate.model.UniqueEntryModelsTest.MeanUniqueEntryModels
            meanUniqueEntryModels =
                new org.wheatgenetics.coordinate.model.UniqueEntryModelsTest.MeanUniqueEntryModels(
                    1,1,1,this);
        meanUniqueEntryModels.checkThenSet(                                                // throws
            new org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel(
                1,1,1, meanUniqueEntryModels));
    }

    @org.junit.Test() public void niceCheckThenSetThrows()
    throws org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.CheckException
    {
        new org.wheatgenetics.coordinate.model.UniqueEntryModelsTest.NiceUniqueEntryModels(
            1,1,1,this).checkThenSet(null) /* throws */;
    }
    // endregion
}