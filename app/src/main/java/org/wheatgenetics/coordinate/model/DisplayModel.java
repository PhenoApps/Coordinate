package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * android.support.annotation.IntRange
 *
 * org.wheatgenetics.coordinate.model.ElementModel
 */
@java.lang.SuppressWarnings({"UnnecessaryInterfaceModifier"}) public interface DisplayModel
{
    public abstract @android.support.annotation.IntRange(from = 1) int getRows();
    public abstract @android.support.annotation.IntRange(from = 1) int getCols();
    public abstract boolean getColNumbering(); public abstract boolean getRowNumbering();

    public abstract org.wheatgenetics.coordinate.model.ElementModel getElementModel(
    @android.support.annotation.IntRange(from = 1) final int row,
    @android.support.annotation.IntRange(from = 1) final int col);
}