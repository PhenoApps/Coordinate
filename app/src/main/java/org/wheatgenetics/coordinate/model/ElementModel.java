package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * android.support.annotation.IntRange
 */
@java.lang.SuppressWarnings({"UnnecessaryInterfaceModifier"}) public interface ElementModel
{
    public abstract @android.support.annotation.IntRange(from = 1) int getRowValue();
    public abstract @android.support.annotation.IntRange(from = 1) int getColValue();
}