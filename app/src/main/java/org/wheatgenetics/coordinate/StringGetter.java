package org.wheatgenetics.coordinate;

/**
 * Uses:
 * android.content.res.Resources.NotFoundException
 *
 * androidx.annotation.IntRange
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 * androidx.annotation.PluralsRes
 * androidx.annotation.StringRes
 */
@java.lang.SuppressWarnings({"UnnecessaryInterfaceModifier"}) public interface StringGetter
{
    @androidx.annotation.Nullable
    public abstract java.lang.String get(@androidx.annotation.StringRes int resId);

    @androidx.annotation.NonNull public abstract java.lang.String getQuantity(
    @androidx.annotation.PluralsRes         int                     resId     ,
    @androidx.annotation.IntRange(from = 0) int                     quantity  ,
    @androidx.annotation.Nullable               java.lang.Object... formatArgs)
    throws android.content.res.Resources.NotFoundException;
}