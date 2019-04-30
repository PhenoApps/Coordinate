package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * android.support.annotation.IntRange
 * android.support.annotation.NonNull
 * android.support.annotation.Nullable
 *
 * org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.CheckException
 * org.wheatgenetics.coordinate.model.CurrentGridUniqueEntryModels
 * org.wheatgenetics.coordinate.model.CurrentGridUniqueEntryModels
 *     .CurrentGridDuplicateCheckException
 */
public abstract class DatabaseUniqueEntryModels
extends org.wheatgenetics.coordinate.model.CurrentGridUniqueEntryModels
{
    // region Types
    public static class DatabaseDuplicateCheckException extends org.wheatgenetics
    .coordinate.model.CurrentGridUniqueEntryModels.CurrentGridDuplicateCheckException
    {
        public DatabaseDuplicateCheckException(
        @android.support.annotation.NonNull final java.lang.String scope) { super(scope); }
    }

    @java.lang.SuppressWarnings({"UnnecessaryInterfaceModifier"}) public interface Checker
    {
        @android.support.annotation.Nullable public java.lang.String check(
        @android.support.annotation.IntRange(from = 1) long             gridId,
        @android.support.annotation.Nullable           java.lang.String value ,
        @android.support.annotation.NonNull            java.lang.String scope ) throws org
        .wheatgenetics.coordinate.model.DatabaseUniqueEntryModels.DatabaseDuplicateCheckException;
    }
    // endregion

    // region Fields
    @android.support.annotation.NonNull private final java.lang.String scope;
    @android.support.annotation.NonNull private final
        org.wheatgenetics.coordinate.model.DatabaseUniqueEntryModels.Checker checker;
    // endregion

    DatabaseUniqueEntryModels(
    @android.support.annotation.IntRange(from = 1) final long             gridId,
    @android.support.annotation.IntRange(from = 1) final int              rows  ,
    @android.support.annotation.IntRange(from = 1) final int              cols  ,
    @android.support.annotation.NonNull            final java.lang.String scope ,
    @android.support.annotation.NonNull            final
        org.wheatgenetics.coordinate.model.DatabaseUniqueEntryModels.Checker checker)
    { super(gridId, rows, cols); this.scope = scope; this.checker = checker; }

    @java.lang.Override @android.support.annotation.Nullable public java.lang.String check(
    @android.support.annotation.IntRange(from = 1) final int              rowIndex,
    @android.support.annotation.IntRange(from = 1) final int              colIndex,
    @android.support.annotation.Nullable           final java.lang.String value   )
    throws org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.CheckException
    {
        if (null == super.check(rowIndex, colIndex, value))                // throws CurrentGridDu-
            return null;                                                   //  plicateCheckException
        else
            return this.checker.check(this.getGridId(), value, this.scope);// throws DatabaseDu-
    }                                                                      //  plicateCheckException
}