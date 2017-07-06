package org.wheatgenetics.coordinate.database;

/**
 * Uses:
 * android.content.ContentValues
 * android.content.Context
 * android.database.Cursor
 * android.database.sqlite.SQLiteDatabase
 * android.util.Log
 *
 * org.wheatgenetics.coordinate.database.Database
 * org.wheatgenetics.coordinate.model.Model
 */

abstract class Table extends java.lang.Object
{
    static final java.lang.String ID_FIELD_NAME = "_id";                // TODO: Make private later.

    private final android.database.sqlite.SQLiteDatabase db            ;
    private final java.lang.String                       tableName, tag;
    public        long                                   id = 0        ;

    Table(final android.content.Context context, final java.lang.String tableName,
    final java.lang.String tag)
    {
        super();

        this.db        = org.wheatgenetics.coordinate.database.Database.getDb(context);
        this.tableName = tableName                                                    ;
        this.tag       = tag                                                          ;
    }

    @java.lang.Override
    public java.lang.String toString() { return java.lang.String.format("id: %02d", this.id); }

    int sendInfoLogMsg(final java.lang.String msg) { return android.util.Log.i(this.tag, msg); }  // TODO: Make private later.

    private android.database.Cursor queryAll(final java.lang.String selection,
    final java.lang.String orderBy)
    {
        assert null != this.db;
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
    boolean deleteUsingWhereClause(final java.lang.String whereClause)  // TODO: Make private later.
    {
        assert null != this.db;
        return this.db.delete(
            /* table       => */ this.tableName,
            /* whereClause => */ whereClause   ,
            /* whereArgs   => */ null          ) > 0;
    }

    abstract org.wheatgenetics.coordinate.model.Model make(final android.database.Cursor cursor);

    org.wheatgenetics.coordinate.model.Model makeFromFirst(final android.database.Cursor cursor)
    {
        if (null == cursor)
            return null;
        else
            try     { if (cursor.moveToFirst()) return this.make(cursor); else return null; }
            finally { cursor.close(); }
    }

    android.content.ContentValues getContentValues()
    {
        final android.content.ContentValues contentValues = new android.content.ContentValues();
        contentValues.put(org.wheatgenetics.coordinate.database.Table.ID_FIELD_NAME, this.id);
        return contentValues;
    }

    android.database.Cursor selectionQueryAll(final java.lang.String selection)
    { return this.queryAll(/* selection => */ selection, /* orderBy => */ null); }

    android.database.Cursor orderByQueryAll(final java.lang.String orderBy)
    { return this.queryAll(/* selection => */ null, /* orderBy => */ orderBy); }

    android.database.Cursor queryAll() { return this.selectionQueryAll(/* selection => */ null); }

    android.database.Cursor queryDistinct(final java.lang.String selection,
    final java.lang.String selectionArgs[])
    {
        assert null != this.db;
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
    { return this.queryDistinct(/* selection => */ selection, /* selectionArgs => */ null); }

    android.database.Cursor rawQuery(final java.lang.String sql)
    {
        assert null != this.db;
        return this.db.rawQuery(/* sql => */ sql, /* selectionArgs => */ null);
    }
    // endregion

    public long insert()
    {
        this.sendInfoLogMsg("Inserting into table " + this.tableName);
        assert null != this.db;
        return this.db.insert(
            /* table          => */ this.tableName         ,
            /* nullColumnHack => */ null                   ,
            /* values         => */ this.getContentValues());
    }

    boolean update(final java.lang.String whereClause)
    {
        assert null != this.db;
        return this.db.update(
            /* table       => */ this.tableName         ,
            /* values      => */ this.getContentValues(),
            /* whereClause => */ whereClause            ,
            /* whereArgs   => */ null                   ) > 0;
    }

    public boolean delete(final org.wheatgenetics.coordinate.model.Model model)  // TODO: Remove later?
    {
        assert null != model;
        final java.lang.String whereClause =                              // TODO: Make into method.
            org.wheatgenetics.coordinate.database.Table.ID_FIELD_NAME + "=" + model.getId();
        this.sendInfoLogMsg("Deleting from table " + this.tableName + " on " + whereClause);
        return this.deleteUsingWhereClause(/* whereClause => */ whereClause);
    }

    boolean delete()                                                          // TODO: Remove later.
    {
        this.sendInfoLogMsg("Clearing table " + this.tableName);
        return this.deleteUsingWhereClause(/* whereClause => */ null);
    }
}