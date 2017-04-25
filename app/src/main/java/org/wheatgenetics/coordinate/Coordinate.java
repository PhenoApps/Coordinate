package org.wheatgenetics.coordinate;

/**
 * Uses:
 * android.app.Application
 * android.content.ContentValues
 * android.content.Context
 * android.database.Cursor
 * android.database.sqlite.SQLiteDatabase
 * android.util.Log
 */

public class Coordinate extends android.app.Application
{
    public static java.lang.String                       appName;
    private static android.database.sqlite.SQLiteDatabase db     ;


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


    private static android.database.Cursor queryAll(final java.lang.String tableName,
    final java.lang.String selection, final java.lang.String orderBy)
    {
        assert org.wheatgenetics.coordinate.Coordinate.db != null;
        return org.wheatgenetics.coordinate.Coordinate.db.query(
            /* table         => */ tableName,
            /* columns       => */ null     ,
            /* selection     => */ selection,
            /* selectionArgs => */ null     ,
            /* groupBy       => */ null     ,
            /* having        => */ null     ,
            /* orderBy       => */ orderBy  );
    }

    public static android.database.Cursor queryAllSelection(
    final java.lang.String tableName, final java.lang.String selection)
    {
        return org.wheatgenetics.coordinate.Coordinate.queryAll(
            /* tableName => */ tableName,
            /* selection => */ selection,
            /* orderBy   => */ null     );
    }

    public static android.database.Cursor queryAllOrderBy(
    final java.lang.String tableName, final java.lang.String orderBy)
    {
        return org.wheatgenetics.coordinate.Coordinate.queryAll(
            /* tableName => */ tableName,
            /* selection => */ null     ,
            /* orderBy   => */ orderBy  );
    }

    public static android.database.Cursor queryAll(final java.lang.String tableName)
    {
        return org.wheatgenetics.coordinate.Coordinate.queryAllSelection(
            /* tableName => */ tableName,
            /* selection => */ null     );
    }


    public static android.database.Cursor queryDistinct(final java.lang.String tableName,
    final java.lang.String selection, final java.lang.String selectionArgs[])
    {
        assert org.wheatgenetics.coordinate.Coordinate.db != null;
        return org.wheatgenetics.coordinate.Coordinate.db.query(
            /* distinct      => */ true         ,
            /* table         => */ tableName    ,
            /* columns       => */ null         ,
            /* selection     => */ selection    ,
            /* selectionArgs => */ selectionArgs,
            /* groupBy       => */ null         ,
            /* having        => */ null         ,
            /* orderBy       => */ null         ,
            /* limit         => */ null         );
    }

    public static android.database.Cursor queryDistinct(
    final java.lang.String tableName, final java.lang.String selection)
    {
        return org.wheatgenetics.coordinate.Coordinate.queryDistinct(
            /* tableName     => */ tableName,
            /* selection     => */ selection,
            /* selectionArgs => */ null     );
    }


    public static android.database.Cursor rawQuery(final java.lang.String sql)
    {
        assert org.wheatgenetics.coordinate.Coordinate.db != null;
        return org.wheatgenetics.coordinate.Coordinate.db.rawQuery(
            /* sql           => */ sql ,
            /* selectionArgs => */ null);
    }


    public static long insert(final java.lang.String tableName,
    final android.content.ContentValues contentValues)
    {
        assert org.wheatgenetics.coordinate.Coordinate.db != null;
        return org.wheatgenetics.coordinate.Coordinate.db.insert(
            /* table          => */ tableName    ,
            /* nullColumnHack => */ null         ,
            /* values         => */ contentValues);
    }


    public static boolean update(final java.lang.String tableName,
    final android.content.ContentValues contentValues, final java.lang.String whereClause)
    {
        assert org.wheatgenetics.coordinate.Coordinate.db != null;
        return org.wheatgenetics.coordinate.Coordinate.db.update(
            /* table       => */ tableName    ,
            /* values      => */ contentValues,
            /* whereClause => */ whereClause  ,
            /* whereArgs   => */ null         ) > 0;
    }


    public static boolean delete(final java.lang.String tableName,
    final java.lang.String whereClause)
    {
        assert org.wheatgenetics.coordinate.Coordinate.db != null;
        return org.wheatgenetics.coordinate.Coordinate.db.delete(
            /* table       => */ tableName  ,
            /* whereClause => */ whereClause,
            /* whereArgs   => */ null       ) > 0;
    }

    public static boolean delete(final java.lang.String tableName)
    {
        return org.wheatgenetics.coordinate.Coordinate.delete(
            /* tableName   => */ tableName,
            /* whereClause => */ null    );
    }
}