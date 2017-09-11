package org.wheatgenetics.coordinate.database;

/**
 * Uses:
 * android.content.ContentValues
 * android.content.Context
 * android.database.Cursor
 * android.database.sqlite.SQLiteDatabase
 * android.util.Log
 *
 * org.wheatgenetics.coordinate.model.Model
 *
 * org.wheatgenetics.coordinate.database.Database
 */
abstract class Table extends java.lang.Object
{
    static final java.lang.String ID_FIELD_NAME = "_id";

    // region Fields
    private final android.database.sqlite.SQLiteDatabase db            ;
    private final java.lang.String                       tableName, tag;
    // endregion

    Table(final android.content.Context context, final java.lang.String tableName,
    final java.lang.String tag)
    {
        super();

        this.db = org.wheatgenetics.coordinate.database.Database.getDb(context);
        this.tableName = tableName; this.tag = tag;
    }

    // region Internal Operations
    private static java.lang.String whereClause(
    final org.wheatgenetics.coordinate.model.Model model)
    {
        assert null != model;
        return org.wheatgenetics.coordinate.database.Table.ID_FIELD_NAME + " = " + model.getId();
    }

    private int sendInfoLogMsg(final java.lang.String msg)
    { return android.util.Log.i(this.tag, msg); }

    private android.database.Cursor query(final boolean distinct, final java.lang.String selection,
    final java.lang.String selectionArgs[], final java.lang.String orderBy)
    {
        assert null != this.db; return this.db.query(
            /* distinct      => */ distinct      ,
            /* table         => */ this.tableName,
            /* columns       => */ null          ,
            /* selection     => */ selection     ,
            /* selectionArgs => */ selectionArgs ,
            /* groupBy       => */ null          ,
            /* having        => */ null          ,
            /* orderBy       => */ orderBy       ,
            /* limit         => */ null          );
    }

    private android.content.ContentValues getContentValuesForUpdate(
    final org.wheatgenetics.coordinate.model.Model model)
    {
        final android.content.ContentValues contentValues = this.getContentValuesForInsert(model);

        assert null != model; assert null != contentValues;
        contentValues.put(org.wheatgenetics.coordinate.database.Table.ID_FIELD_NAME, model.getId());

        return contentValues;
    }
    // endregion

    // region Query Method Dependencies
    // method                      scope   external usage
    // =========================== ======= ===========================
    // query()                     private N/A
    //     queryAll()              package TemplatesTable
    //     queryDistinct()         package                EntriesTable
    //         queryDistinct()     package TemplatesTable
    // endregion

    // region External Operations
    // region Package External Operations
    android.database.Cursor queryAll(final java.lang.String orderBy)
    {
        return this.query(
            /* distinct      => */ false  ,
            /* selection     => */ null   ,
            /* selectionArgs => */ null   ,
            /* orderBy       => */ orderBy);
    }

    android.database.Cursor queryDistinct(final java.lang.String selection,
    final java.lang.String selectionArgs[])
    {
        return this.query(
            /* distinct      => */ true         ,
            /* selection     => */ selection    ,
            /* selectionArgs => */ selectionArgs,
            /* orderBy       => */ null         );
    }

    android.database.Cursor queryDistinct(final java.lang.String selection)
    { return this.queryDistinct(/* selection => */ selection, /* selectionArgs => */ null); }

    android.database.Cursor rawQuery(final java.lang.String sql,
    final java.lang.String selectionArgs[])
    {
        assert null != this.db;
        return this.db.rawQuery(/* sql => */ sql, /* selectionArgs => */ selectionArgs);
    }

    android.database.Cursor rawQuery(final java.lang.String sql)
    { return this.rawQuery(/* sql => */ sql, /* selectionArgs => */ null); }

    static boolean exists(final android.database.Cursor cursor)
    {
        if (null == cursor)
            return false;
        else
            try     { return cursor.getCount() > 0; }
            finally { cursor.close()              ; }
    }

    abstract org.wheatgenetics.coordinate.model.Model make(final android.database.Cursor cursor);

    org.wheatgenetics.coordinate.model.Model makeFromFirst(final android.database.Cursor cursor)
    {
        if (null == cursor)
            return null;
        else
            try     { return cursor.moveToFirst() ? this.make(cursor) : null; }
            finally { cursor.close();                                         }
    }

    android.content.ContentValues getContentValuesForInsert(
    final org.wheatgenetics.coordinate.model.Model model)
    { return new android.content.ContentValues(); }

    boolean deleteUsingWhereClause(final java.lang.String whereClause)
    {
        this.sendInfoLogMsg("Deleting from table " + this.tableName + " on " + whereClause);
        assert null != this.db; return this.db.delete(
            /* table       => */ this.tableName,
            /* whereClause => */ whereClause   ,
            /* whereArgs   => */ null          ) > 0;
    }
    // endregion

    // region Public External Operations
    public long insert(final org.wheatgenetics.coordinate.model.Model model)
    {
        this.sendInfoLogMsg("Inserting into table " + this.tableName);
        assert null != this.db; return this.db.insert(
            /* table          => */ this.tableName                       ,
            /* nullColumnHack => */ null                                 ,
            /* values         => */ this.getContentValuesForInsert(model));
    }

    public boolean update(final org.wheatgenetics.coordinate.model.Model model)
    {
        final java.lang.String whereClause =
            org.wheatgenetics.coordinate.database.Table.whereClause(model);
        this.sendInfoLogMsg("Updating table " + this.tableName + " on " + whereClause);
        assert null != this.db; return this.db.update(
            /* table       => */ this.tableName                       ,
            /* values      => */ this.getContentValuesForUpdate(model),
            /* whereClause => */ whereClause                          ,
            /* whereArgs   => */ null                                 ) > 0;
    }

    public boolean delete(final org.wheatgenetics.coordinate.model.Model model)
    {
        return this.deleteUsingWhereClause(/* whereClause => */
            org.wheatgenetics.coordinate.database.Table.whereClause(model));
    }
    // endregion
    // endregion
}