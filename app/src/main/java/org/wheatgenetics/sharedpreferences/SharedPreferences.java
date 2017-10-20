package org.wheatgenetics.sharedpreferences;

/**
 * Uses:
 * android.content.SharedPreferences
 * android.support.annotation.NonNull
 *
 * org.wheatgenetics.sharedpreferences.UpdateVersionSharedPreferences
 */
public class SharedPreferences
extends org.wheatgenetics.sharedpreferences.UpdateVersionSharedPreferences
{
    private static final java.lang.String PERSON = "Person", CURRENT_GRID = "CurrentGrid";

    public SharedPreferences(@android.support.annotation.NonNull
    final android.content.SharedPreferences sharedPreferences) { super(sharedPreferences); }

    // region Public Methods
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
    public long getCurrentGrid()
    { return this.getLong(org.wheatgenetics.sharedpreferences.SharedPreferences.CURRENT_GRID); }

    public void setCurrentGrid(final long currentGrid)
    {
        this.setLong(org.wheatgenetics.sharedpreferences.SharedPreferences.CURRENT_GRID,
            currentGrid);
    }

    public boolean currentGridIsSet() { return this.getCurrentGrid() > -1; }
    // endregion
    // endregion
}