package org.wheatgenetics.coordinate.model;

import androidx.annotation.IntRange;

public interface ElementModel {
    @IntRange(from = 1)
    public abstract int getRowValue();

    @IntRange(from = 1)
    public abstract int getColValue();
}