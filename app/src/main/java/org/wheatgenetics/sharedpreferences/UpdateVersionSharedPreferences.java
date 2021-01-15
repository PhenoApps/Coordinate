package org.wheatgenetics.sharedpreferences;

/**
 * Uses:
 * android.content.SharedPreferences
 *
 * androidx.annotation.NonNull
 *
 * org.wheatgenetics.sharedpreferences.BaseSharedPreferences
 */
@SuppressWarnings({"unused"}) public class UpdateVersionSharedPreferences
extends org.wheatgenetics.sharedpreferences.BaseSharedPreferences
{
    private static final String UPDATE_VERSION = "UpdateVersion";

    private int getUpdateVersion()
    {
        return this.getInt(
            UpdateVersionSharedPreferences.UPDATE_VERSION);
    }

    public UpdateVersionSharedPreferences(
    @androidx.annotation.NonNull final android.content.SharedPreferences sharedPreferences)
    { super(sharedPreferences); }

    // region Public Methods
    public boolean updateVersionIsSet(final int newUpdateVersion)
    { return this.getUpdateVersion() >= newUpdateVersion; }

    public void setUpdateVersion(final int newUpdateVersion)
    {
        if (this.getUpdateVersion() != newUpdateVersion) this.setInt(
            UpdateVersionSharedPreferences.UPDATE_VERSION,
            newUpdateVersion                                                                 );
    }
    // endregion
}