package org.wheatgenetics.coordinate.database;

/**
 * Uses:
 * android.content.ContentValues
 * android.content.Context
 * android.database.Cursor
 * android.database.sqlite.SQLiteDatabase
 *
 * org.wheatgenetics.coordinate.database.Database
 */

abstract class Table extends java.lang.Object
{
    private android.database.sqlite.SQLiteDatabase db       ;
    private java.lang.String                       tableName;

    Table(final android.content.Context context, final java.lang.String tableName)
    throws java.lang.Exception
    {
        super();
        this.db        = org.wheatgenetics.coordinate.database.Database.getDb(context);
        this.tableName = tableName                                                    ;
    }

    private android.database.Cursor queryAll(final java.lang.String selection,
    final java.lang.String orderBy)
    {
        assert this.db != null;
        return this.db.query(
            /* table         => */ this.tableName,
            /* columns       => */ null          ,
            /* selection     => */ selection     ,
            /* selectionArgs => */ null          ,
            /* groupBy       => */ null          ,
            /* having        => */ null          ,
            /* orderBy       => */ orderBy       );
    }


    // region Package Methods
    android.database.Cursor queryAllSelection(final java.lang.String selection)
    {
        return this.queryAll(/* selection => */ selection, /* orderBy => */ null);
    }

    android.database.Cursor queryAllOrderBy(final java.lang.String orderBy)
    {
        return this.queryAll(/* selection => */ null, /* orderBy => */ orderBy);
    }

    android.database.Cursor queryAll() { return this.queryAllSelection(/* selection => */ null); }

    android.database.Cursor queryDistinct(final java.lang.String selection,
    final java.lang.String selectionArgs[])
    {
        assert this.db != null;
        return this.db.query(
            /* distinct      => */ true          ,
            /* table         => */ this.tableName,
            /* columns       => */ null          ,
            /* selection     => */ selection     ,
            /* selectionArgs => */ selectionArgs ,
            /* groupBy       => */ null          ,
            /* having        => */ null          ,
            /* orderBy       => */ null          ,
            /* limit         => */ null          );
    }

    android.database.Cursor queryDistinct(final java.lang.String selection)
    {
        return this.queryDistinct(/* selection => */ selection, /* selectionArgs => */ null);
    }

    android.database.Cursor rawQuery(final java.lang.String sql)
    {
        assert this.db != null;
        return this.db.rawQuery(/* sql => */ sql, /* selectionArgs => */ null);
    }

    long insert(final android.content.ContentValues contentValues)
    {
        assert this.db != null;
        return this.db.insert(
            /* table          => */ this.tableName,
            /* nullColumnHack => */ null          ,
            /* values         => */ contentValues );
    }

    boolean update(final android.content.ContentValues contentValues,
    final java.lang.String whereClause)
    {
        assert this.db != null;
        return this.db.update(
            /* table       => */ this.tableName,
            /* values      => */ contentValues ,
            /* whereClause => */ whereClause   ,
            /* whereArgs   => */ null          ) > 0;
    }

    boolean delete(final java.lang.String whereClause)
    {
        assert this.db != null;
        return this.db.delete(
            /* table       => */ this.tableName,
            /* whereClause => */ whereClause   ,
            /* whereArgs   => */ null          ) > 0;
    }

    boolean delete() { return this.delete(/* whereClause => */ null); }
    // endregion
}