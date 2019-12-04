package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * androidx.annotation.IntRange
 * androidx.annotation.Nullable
 *
 * org.wheatgenetics.coordinate.model.ElementModel
 */
@java.lang.SuppressWarnings({"UnnecessaryInterfaceModifier"}) public interface DisplayModel
{
    @androidx.annotation.IntRange(from = 1) public abstract int getRows();
    @androidx.annotation.IntRange(from = 1) public abstract int getCols();
    public abstract boolean getColNumbering(); public abstract boolean getRowNumbering();

    @androidx.annotation.Nullable
    public abstract org.wheatgenetics.coordinate.model.ElementModel getElementModel(
    @androidx.annotation.IntRange(from = 1) final int row,
    @androidx.annotation.IntRange(from = 1) final int col);
}