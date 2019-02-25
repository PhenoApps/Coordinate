package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * android.support.annotation.IntRange
 */
@java.lang.SuppressWarnings({"UnnecessaryInterfaceModifier"}) public interface ElementModel
{
    @android.support.annotation.IntRange(from = 1) public abstract int getRowValue();
    @android.support.annotation.IntRange(from = 1) public abstract int getColValue();
}