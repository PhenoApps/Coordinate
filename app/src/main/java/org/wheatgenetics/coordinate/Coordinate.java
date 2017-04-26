package org.wheatgenetics.coordinate;

/**
 * Uses:
 * android.app.Application
 * android.util.Log
 *
 * org.wheatgenetics.coordinate.R
 */

public class Coordinate extends android.app.Application
{
    public static java.lang.String appName;

    @Override
    public void onCreate()
    {
        super.onCreate();
        android.util.Log.d("SeedTray", "Starting...");

        org.wheatgenetics.coordinate.Coordinate.appName =
            this.getResources().getString(org.wheatgenetics.coordinate.R.string.app_name);
    }
}