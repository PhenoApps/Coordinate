package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * androidx.annotation.IntRange
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
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
        @androidx.annotation.NonNull final java.lang.String scope) { super(scope); }
    }

    @java.lang.SuppressWarnings({"UnnecessaryInterfaceModifier"}) public interface Checker
    {
        @androidx.annotation.Nullable public java.lang.String check(
        @androidx.annotation.IntRange(from = 1) long             gridId,
        @androidx.annotation.Nullable           java.lang.String value ,
        @androidx.annotation.NonNull            java.lang.String scope ) throws org
        .wheatgenetics.coordinate.model.DatabaseUniqueEntryModels.DatabaseDuplicateCheckException;
    }
    // endregion

    // region Fields
    @androidx.annotation.NonNull private final java.lang.String scope;
    @androidx.annotation.NonNull private final
        org.wheatgenetics.coordinate.model.DatabaseUniqueEntryModels.Checker checker;
    // endregion

    DatabaseUniqueEntryModels(
    @androidx.annotation.IntRange(from = 1) final long             gridId,
    @androidx.annotation.IntRange(from = 1) final int              rows  ,
    @androidx.annotation.IntRange(from = 1) final int              cols  ,
    @androidx.annotation.NonNull            final java.lang.String scope ,
    @androidx.annotation.NonNull            final
        org.wheatgenetics.coordinate.model.DatabaseUniqueEntryModels.Checker checker)
    { super(gridId, rows, cols); this.scope = scope; this.checker = checker; }

    @java.lang.Override @androidx.annotation.Nullable public java.lang.String check(
    @androidx.annotation.IntRange(from = 1) final int              rowIndex,
    @androidx.annotation.IntRange(from = 1) final int              colIndex,
    @androidx.annotation.Nullable           final java.lang.String value   )
    throws org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.CheckException
    {
        if (null == super.check(rowIndex, colIndex, value))                // throws CurrentGridDu-
            return null;                                                   //  plicateCheckException
        else
            return this.checker.check(this.getGridId(), value, this.scope);// throws DatabaseDu-
    }                                                                      //  plicateCheckException
}