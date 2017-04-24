package org.wheatgenetics.coordinate;

/**
 * Uses:
 * android.app.Application
 * android.content.Context
 * android.database.sqlite.SQLiteDatabase
 * android.util.Log
 */

public class Coordinate extends android.app.Application
{
    public static java.lang.String                       appName;
    public static android.database.sqlite.SQLiteDatabase db     ;


    private static int sendDebugLogMsg(final java.lang.String msg)
    {
        return android.util.Log.d("SeedTray", msg);
    }

    public static void initializeDb(final android.content.Context context)
    {
        final org.wheatgenetics.coordinate.database.DatabaseHelper databaseHelper =
            new org.wheatgenetics.coordinate.database.DatabaseHelper(context);
        org.wheatgenetics.coordinate.Coordinate.db = databaseHelper.getWritableDatabase();
    }


    @Override
    public void onCreate()
    {
        super.onCreate();
        org.wheatgenetics.coordinate.Coordinate.sendDebugLogMsg("Starting...");

        org.wheatgenetics.coordinate.Coordinate.appName =
            this.getResources().getString(org.wheatgenetics.coordinate.R.string.app_name);

        org.wheatgenetics.coordinate.Coordinate.initializeDb(this);
    }

    @Override
    public void onTerminate()
    {
        org.wheatgenetics.coordinate.Coordinate.sendDebugLogMsg("Terminating...");
        if (org.wheatgenetics.coordinate.Coordinate.db != null)
            org.wheatgenetics.coordinate.Coordinate.db.close();
        super.onTerminate();
    }
}