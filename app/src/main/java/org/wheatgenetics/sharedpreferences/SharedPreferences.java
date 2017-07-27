package org.wheatgenetics.sharedpreferences;

/**
 * Uses:
 * android.support.annotation.NonNull
 * org.wheatgenetics.sharedpreferences.UpdateVersionSharedPreferences
 */

public class SharedPreferences
extends org.wheatgenetics.sharedpreferences.UpdateVersionSharedPreferences
{
    private static final java.lang.String PERSON = "Person", CURRENT_GRID = "CurrentGrid";

    // region Public Methods
    // region Constructor Public Method
    public SharedPreferences(@android.support.annotation.NonNull
    final android.content.SharedPreferences sharedPreferences) { super(sharedPreferences); }
    // endregion

    // region Person Public Methods
    public java.lang.String getPerson()
    { return this.getString(org.wheatgenetics.sharedpreferences.SharedPreferences.PERSON); }

    public void setPerson(final java.lang.String person)
    {
        this.setString(org.wheatgenetics.sharedpreferences.SharedPreferences.PERSON,
            /* oldValue => */ this.getPerson(),
            /* newValue => */ person          );
    }
    // endregion

    // region CurrentGrid Public Methods
    public boolean currentGridIsSet() { return this.getCurrentGrid() != -1; }

    public long getCurrentGrid()
    { return this.getLong(org.wheatgenetics.sharedpreferences.SharedPreferences.CURRENT_GRID); }

    public void setCurrentGrid(final long currentGrid)
    {
        this.setLong(org.wheatgenetics.sharedpreferences.SharedPreferences.CURRENT_GRID,
            currentGrid);
    }
    // endregion
    // endregion
}