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

    /**
     * Returns the display label for the given 1-based column index, or null to use
     * the default numeric/alphabetic label derived from getColNumbering().
     */
    @Nullable
    default String getColLabel(@IntRange(from = 1) final int col) {
        return null;
    }

    /**
     * Returns the display label for the given 1-based row index, or null to use
     * the default numeric/alphabetic label derived from getRowNumbering().
     */
    @Nullable
    default String getRowLabel(@IntRange(from = 1) final int row) {
        return null;
    }
}