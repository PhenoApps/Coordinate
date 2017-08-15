package org.wheatgenetics.coordinate.database;

/**
 * Uses:
 * android.annotation.SuppressLint
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

    // region Fields
    private final android.database.sqlite.SQLiteDatabase db            ;
    private final java.lang.String                       tableName, tag;
    public        long                                   id = 0        ;      // TODO: Remove later.
    // endregion

    Table(final android.content.Context context, final java.lang.String tableName,
    final java.lang.String tag)
    {
        super();

        this.db        = org.wheatgenetics.coordinate.database.Database.getDb(context);
        this.tableName = tableName                                                    ;
        this.tag       = tag                                                          ;
    }

    @java.lang.Override @android.annotation.SuppressLint("DefaultLocale")
    public java.lang.String toString() { return java.lang.String.format("id: %02d", this.id); }

    // region Internal Operations
    int sendInfoLogMsg(final java.lang.String msg) { return android.util.Log.i(this.tag, msg); }  // TODO: Make private later.

    // region Database Internal Operations
    private android.database.Cursor query(final boolean distinct, final java.lang.String selection,
    final java.lang.String selectionArgs[], final java.lang.String orderBy)
    {
        assert null != this.db;
        return this.db.query(
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

    private android.database.Cursor queryAll(final java.lang.String selection,
    final java.lang.String orderBy)
    {
        return this.query(
            /* distinct      => */ false    ,
            /* selection     => */ selection,
            /* selectionArgs => */ null     ,
            /* orderBy       => */ orderBy  );
    }

    boolean deleteUsingWhereClause(final java.lang.String whereClause)  // TODO: Make private later.
    {
        assert null != this.db;
        return this.db.delete(
            /* table       => */ this.tableName,
            /* whereClause => */ whereClause   ,
            /* whereArgs   => */ null          ) > 0;
    }
    // endregion

    // region Model Internal Operations
    abstract org.wheatgenetics.coordinate.model.Model make(final android.database.Cursor cursor);

    org.wheatgenetics.coordinate.model.Model makeFromFirst(final android.database.Cursor cursor)
    {
        if (null == cursor)
            return null;
        else
            try     { return cursor.moveToFirst() ? this.make(cursor) : null; }
            finally { cursor.close();                                         }
    }

    android.content.ContentValues getContentValues() { return new android.content.ContentValues(); }  // TODO: Remove later.

    android.content.ContentValues getContentValues(
    final org.wheatgenetics.coordinate.model.Model model) throws org.json.JSONException
    { return new android.content.ContentValues(); }

    private android.content.ContentValues getContentValuesForUpdate()         // TODO: Remove later.
    {
        final android.content.ContentValues contentValues = this.getContentValues();
        assert null != contentValues;
        contentValues.put(org.wheatgenetics.coordinate.database.Table.ID_FIELD_NAME, this.id);
        return contentValues;
    }

    private android.content.ContentValues getContentValuesForUpdate(
    final org.wheatgenetics.coordinate.model.Model model) throws org.json.JSONException
    {
        final android.content.ContentValues contentValues = this.getContentValues(model);

        assert null != model        ;
        assert null != contentValues;
        contentValues.put(org.wheatgenetics.coordinate.database.Table.ID_FIELD_NAME, model.getId());

        return contentValues;
    }
    // endregion
    // endregion

    // region Query Method Dependencies
    // method                      scope   usage
    // ======                      =====   =====
    // query()                     private
    //     queryAll()              private
    //         selectionQueryAll() package                GridsTable EntriesTable
    //             queryAll()      package                GridsTable EntriesTable
    //         orderByQueryAll()   package TemplatesTable
    //     queryDistinct()         package TemplatesTable GridsTable EntriesTable
    // endregion

    // region External Operations
    android.database.Cursor selectionQueryAll(final java.lang.String selection)
    { return this.queryAll(/* selection => */ selection, /* orderBy => */ null); }

    android.database.Cursor orderByQueryAll(final java.lang.String orderBy)
    { return this.queryAll(/* selection => */ null, /* orderBy => */ orderBy); }

    android.database.Cursor queryAll() { return this.selectionQueryAll(/* selection => */ null); }

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

    android.database.Cursor rawQuery(final java.lang.String sql)
    {
        assert null != this.db;
        return this.db.rawQuery(/* sql => */ sql, /* selectionArgs => */ null);
    }

    public long insert()                                                      // TODO: Remove later.
    {
        this.sendInfoLogMsg("Inserting into table " + this.tableName);
        assert null != this.db;
        return this.db.insert(
            /* table          => */ this.tableName         ,
            /* nullColumnHack => */ null                   ,
            /* values         => */ this.getContentValues());
    }

    public long insert(final org.wheatgenetics.coordinate.model.Model model)
    {
        this.sendInfoLogMsg("Inserting into table " + this.tableName);
        assert null != this.db;
        try
        {
            return this.db.insert(
                /* table          => */ this.tableName              ,
                /* nullColumnHack => */ null                        ,
                /* values         => */ this.getContentValues(model));
        }
        catch (final org.json.JSONException e) { return -1; }
    }

    boolean update(final java.lang.String whereClause)                        // TODO: Remove later.
    {
        assert null != this.db;
        return this.db.update(
            /* table       => */ this.tableName                  ,
            /* values      => */ this.getContentValuesForUpdate(),
            /* whereClause => */ whereClause                     ,
            /* whereArgs   => */ null                            ) > 0;
    }

    public boolean update(final org.wheatgenetics.coordinate.model.Model model)
    {
        assert null != model;
        final java.lang.String whereClause =
            org.wheatgenetics.coordinate.database.Table.ID_FIELD_NAME + "=" + model.getId();

        this.sendInfoLogMsg("Updating table " + this.tableName + " on " + whereClause);

        assert null != this.db;
        try
        {
            return this.db.update(
                /* table       => */ this.tableName                       ,
                /* values      => */ this.getContentValuesForUpdate(model),
                /* whereClause => */ whereClause                          ,
                /* whereArgs   => */ null                                 ) > 0;
        }
        catch (final org.json.JSONException e) { return false; }
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
    // endregion
}