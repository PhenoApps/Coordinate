package org.wheatgenetics.sharedpreferences;

/**
 * Uses:
 * android.content.SharedPreferences
 * android.support.annotation.IntRange
 * android.support.annotation.NonNull
 *
 * org.wheatgenetics.sharedpreferences.UpdateVersionSharedPreferences
 */
public class SharedPreferences
extends org.wheatgenetics.sharedpreferences.UpdateVersionSharedPreferences
{
    private static final java.lang.String CURRENT_GRID = "CurrentGrid";

    private void uncheckSetCurrentGrid(final long currentGrid)
    {
        this.setLong(org.wheatgenetics.sharedpreferences.SharedPreferences.CURRENT_GRID,
            currentGrid);
    }

    public SharedPreferences(@android.support.annotation.NonNull
    final android.content.SharedPreferences sharedPreferences) { super(sharedPreferences); }

    // region Public Methods
    public long getCurrentGrid()
    { return this.getLong(org.wheatgenetics.sharedpreferences.SharedPreferences.CURRENT_GRID); }

    public void setCurrentGrid(
    @android.support.annotation.IntRange(from = 1) final long currentGrid)
    {
        if (currentGrid < 1)
            throw new java.lang.IllegalArgumentException("currentGrid must be > 0");
        this.uncheckSetCurrentGrid(currentGrid);
    }

    public void clearCurrentGrid() { this.uncheckSetCurrentGrid(-1); }

    public boolean currentGridIsSet() { return this.getCurrentGrid() > -1; }
    // endregion
}