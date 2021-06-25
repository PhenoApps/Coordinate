package org.wheatgenetics.coordinate.model;

import androidx.annotation.IntRange;
import androidx.annotation.Nullable;

public interface DisplayModel {
    @IntRange(from = 1)
    public abstract int getRows();

    @IntRange(from = 1)
    public abstract int getCols();

    public abstract boolean getColNumbering();

    public abstract boolean getRowNumbering();

    @Nullable
    public abstract ElementModel getElementModel(
            @IntRange(from = 1) final int row,
            @IntRange(from = 1) final int col);
}