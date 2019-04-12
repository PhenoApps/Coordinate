package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * android.support.annotation.IntRange
 * android.support.annotation.Nullable
 *
 * org.wheatgenetics.coordinate.model.ElementModel
 */
@java.lang.SuppressWarnings({"UnnecessaryInterfaceModifier"}) public interface DisplayModel
{
    @android.support.annotation.IntRange(from = 1) public abstract int getRows();
    @android.support.annotation.IntRange(from = 1) public abstract int getCols();
    public abstract boolean getColNumbering(); public abstract boolean getRowNumbering();

    @android.support.annotation.Nullable
    public abstract org.wheatgenetics.coordinate.model.ElementModel getElementModel(
    @android.support.annotation.IntRange(from = 1) final int row,
    @android.support.annotation.IntRange(from = 1) final int col);
}