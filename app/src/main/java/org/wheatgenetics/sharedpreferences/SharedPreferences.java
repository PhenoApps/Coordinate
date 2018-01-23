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
    private static final java.lang.String LOADED_GRID_ID = "CurrentGrid", SOUND_OFF = "SoundOFF";

    private void uncheckedSetLoadedGridId(final long loadedGridId)
    {
        this.setLong(org.wheatgenetics.sharedpreferences.SharedPreferences.LOADED_GRID_ID,
            loadedGridId);
    }

    public SharedPreferences(@android.support.annotation.NonNull
    final android.content.SharedPreferences sharedPreferences) { super(sharedPreferences); }

    // region Public Methods
    public long getLoadedGridId()
    { return this.getLong(org.wheatgenetics.sharedpreferences.SharedPreferences.LOADED_GRID_ID); }

    public void setLoadedGridId(
    @android.support.annotation.IntRange(from = 1) final long loadedGridId)
    {
        if (loadedGridId < 1)
            throw new java.lang.IllegalArgumentException("loadedGridId must be > 0");
        this.uncheckedSetLoadedGridId(loadedGridId);
    }

    public void clearLoadedGridId() { this.uncheckedSetLoadedGridId(-1); }

    public boolean loadedGridIdIsSet() { return this.getLoadedGridId() > -1; }


    public boolean getSoundOn()
    { return !this.getBoolean(org.wheatgenetics.sharedpreferences.SharedPreferences.SOUND_OFF); }

    public void setSoundOn(final boolean soundOn)
    {
        if (soundOn)
            this.setBooleanToFalse(org.wheatgenetics.sharedpreferences.SharedPreferences.SOUND_OFF);
        else
            this.setBooleanToTrue(org.wheatgenetics.sharedpreferences.SharedPreferences.SOUND_OFF);
    }
    // endregion
}