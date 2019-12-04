package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * androidx.annotation.IntRange
 */
@java.lang.SuppressWarnings({"UnnecessaryInterfaceModifier"}) public interface ElementModel
{
    @androidx.annotation.IntRange(from = 1) public abstract int getRowValue();
    @androidx.annotation.IntRange(from = 1) public abstract int getColValue();
}