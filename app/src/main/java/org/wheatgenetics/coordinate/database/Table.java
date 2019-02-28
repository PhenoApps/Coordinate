package org.wheatgenetics.coordinate.database;

/**
 * Uses:
 * android.content.ContentValues
 * android.content.Context
 * android.database.Cursor
 * android.database.sqlite.SQLiteDatabase
 * android.support.annotation.CallSuper
 * android.support.annotation.NonNull
 * android.support.annotation.Nullable
 * android.support.annotation.RestrictTo
 * android.support.annotation.RestrictTo.Scope
 * android.support.annotation.VisibleForTesting
 * android.util.Log
 *
 * org.wheatgenetics.coordinate.model.Model
 *
 * org.wheatgenetics.coordinate.database.Database
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
abstract class Table extends java.lang.Object
{
    static final java.lang.String ID_FIELD_NAME = "_id";

    // region Fields
    @android.support.annotation.NonNull private final android.database.sqlite.SQLiteDatabase db;
                                        private final java.lang.String           tableName, tag;
    // endregion

    // region Constructors
    private Table(
    @android.support.annotation.NonNull final android.database.sqlite.SQLiteDatabase db       ,
                                        final java.lang.String                       tableName,
                                        final java.lang.String                       tag      )
    { super(); this.db = db; this.tableName = tableName; this.tag = tag; }

    @java.lang.SuppressWarnings({"DefaultAnnotationParam"})
    @android.support.annotation.VisibleForTesting(
        otherwise = android.support.annotation.VisibleForTesting.PRIVATE)
    Table(                                              final android.content.Context context     ,
    @java.lang.SuppressWarnings({"SameParameterValue"}) final java.lang.String        tableName   ,
    @java.lang.SuppressWarnings({"SameParameterValue"}) final java.lang.String        tag         ,
                                                        final java.lang.String        databaseName)
    {
        this(org.wheatgenetics.coordinate.database.Database.db(context, databaseName),
            tableName, tag);
    }

    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    Table(final android.content.Context context, final java.lang.String tableName,
    final java.lang.String tag)
    { this(org.wheatgenetics.coordinate.database.Database.db(context), tableName, tag); }
    // endregion

    // region Internal Operations
    private void logInfo(final java.lang.String msg) { android.util.Log.i(this.tag, msg); }

    private android.database.Cursor query(final boolean distinct, final java.lang.String selection,
    final java.lang.String selectionArgs[], final java.lang.String orderBy)
    {
        return this.db.query(
            /* distinct      => */ distinct      ,
            /* table         => */ this.tableName,
            /* columns       => */null,
            /* selection     => */ selection    ,
            /* selectionArgs => */ selectionArgs,
            /* groupBy       => */null,
            /* having        => */null,
            /* orderBy       => */ orderBy,
            /* limit         => */null);
    }

    @android.support.annotation.NonNull
    private android.content.ContentValues getContentValuesForUpdate(
    @android.support.annotation.NonNull final org.wheatgenetics.coordinate.model.Model model)
    {
        final android.content.ContentValues result = this.getContentValuesForInsert(model);
        result.put(org.wheatgenetics.coordinate.database.Table.ID_FIELD_NAME, model.getId());
        return result;
    }
    // endregion

    // region query() Dependencies
    // method              scope   external usage
    // =================== ======= ======================================
    // query()             private N/A
    //     queryAll()      package                           EntriesTable
    //         queryAll()  package                           EntriesTable
    //         queryAll()  package TemplatesTable
    //     queryDistinct() package TemplatesTable GridsTable
    // endregion

    // region External Operations
    // region Package External Operations
    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    @android.support.annotation.NonNull static java.lang.String whereClause()
    { return org.wheatgenetics.coordinate.database.Table.ID_FIELD_NAME + " = ?"; }

    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    @android.support.annotation.NonNull static java.lang.String whereClause(final long id)
    { return org.wheatgenetics.coordinate.database.Table.ID_FIELD_NAME + " = " + id; }

    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    android.database.Cursor queryAll(final java.lang.String selection,
    final java.lang.String selectionArgs[], final java.lang.String orderBy)
    {
        return this.query(
            /* distinct      => */false,
            /* selection     => */ selection    ,
            /* selectionArgs => */ selectionArgs,
            /* orderBy       => */ orderBy      );
    }

    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    android.database.Cursor queryAll(final java.lang.String selection,
    final java.lang.String selectionArgs[])
    {
        return this.queryAll(
            /* selection     => */ selection    ,
            /* selectionArgs => */ selectionArgs,
            /* orderBy       => */null);
    }

    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    android.database.Cursor queryAll(final java.lang.String orderBy)
    {
        return this.queryAll(
            /* selection     => */null,
            /* selectionArgs => */null,
            /* orderBy       => */ orderBy);
    }

    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    android.database.Cursor queryDistinct(final java.lang.String selection)
    {
        return this.query(
            /* distinct      => */true,
            /* selection     => */ selection,
            /* selectionArgs => */null,
            /* orderBy       => */null);
    }

    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    android.database.Cursor rawQuery(final java.lang.String sql,
    final java.lang.String selectionArgs[])
    { return this.db.rawQuery(/* sql => */ sql, /* selectionArgs => */ selectionArgs); }

    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    android.database.Cursor rawQuery(final java.lang.String sql)
    { return this.rawQuery(/* sql => */ sql, /* selectionArgs => */null); }

    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    static boolean exists(final android.database.Cursor cursor)
    {
        if (null == cursor)
            return false;
        else
            try     { return cursor.getCount() > 0; }
            finally { cursor.close()              ; }
    }

    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    abstract org.wheatgenetics.coordinate.model.Model make(final android.database.Cursor cursor);

    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    @android.support.annotation.Nullable org.wheatgenetics.coordinate.model.Model makeFromFirst(
    final android.database.Cursor cursor)
    {
        if (null == cursor)
            return null;
        else
            try     { return cursor.moveToFirst() ? this.make(cursor) /* polymorphism */ : null; }
            finally { cursor.close();                                                            }
    }

    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    @android.support.annotation.CallSuper @android.support.annotation.NonNull
    android.content.ContentValues getContentValuesForInsert(
    @android.support.annotation.NonNull final org.wheatgenetics.coordinate.model.Model model)
    { return new android.content.ContentValues(); }

    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    boolean deleteUsingWhereClause(final java.lang.String whereClause)
    {
        this.logInfo("Deleting from table " + this.tableName + " on " + whereClause);
        return this.db.delete(
            /* table       => */ this.tableName,
            /* whereClause => */ whereClause   ,
            /* whereArgs   => */null) > 0;
    }
    // endregion

    // region Public External Operations
    public long insert(final org.wheatgenetics.coordinate.model.Model model)
    {
        this.logInfo("Inserting into table " + this.tableName);
        return this.db.insert(
            /* table          => */ this.tableName,
            /* nullColumnHack => */null,
            /* values         => */ this.getContentValuesForInsert(model) /* polymorphism */);
    }

    public void update(
    @android.support.annotation.NonNull final org.wheatgenetics.coordinate.model.Model model)
    {
        final java.lang.String whereClause =
            org.wheatgenetics.coordinate.database.Table.whereClause(model.getId());
        this.logInfo("Updating table " + this.tableName + " on " + whereClause);
        this.db.update(
            /* table       => */ this.tableName                                          ,
            /* values      => */ this.getContentValuesForUpdate(model) /* polymorphism */,
            /* whereClause => */ whereClause                                             ,
            /* whereArgs   => */null);
    }

    public boolean delete(final long id)
    {
        return this.deleteUsingWhereClause(/* whereClause => */
            org.wheatgenetics.coordinate.database.Table.whereClause(id));
    }
    // endregion
    // endregion
}