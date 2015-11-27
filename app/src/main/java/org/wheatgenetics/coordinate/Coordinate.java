package org.wheatgenetics.coordinate;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.wheatgenetics.coordinate.database.DatabaseHelper;

public class Coordinate extends Application {
    public static final String TAG = "SeedTray";

    public static SQLiteDatabase db;

    public static String mAppName;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "Starting...");

        mAppName = getResources().getString(R.string.app_name);

        DatabaseHelper dbh = new DatabaseHelper(this);
        Coordinate.db = dbh.getWritableDatabase();
    }


    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.d(TAG, "Terminating...");

        if (Coordinate.db != null) {
            Coordinate.db.close();
        }
    }
}
