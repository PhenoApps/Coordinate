package org.wheatgenetics.coordinate.database;

/**
 * Uses:
 * android.content.ContentValues
 * android.content.Context
 * android.database.Cursor
 * android.database.sqlite.SQLiteDatabase
 * android.util.Log
 *
 * androidx.annotation.CallSuper
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 * androidx.annotation.RestrictTo
 * androidx.annotation.RestrictTo.Scope
 * androidx.annotation.StringRes
 * androidx.annotation.VisibleForTesting
 *
 * org.wheatgenetics.coordinate.StringGetter
 *
 * org.wheatgenetics.coordinate.model.Model
 *
 * org.wheatgenetics.coordinate.database.Database
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"}) abstract class Table
extends java.lang.Object implements org.wheatgenetics.coordinate.StringGetter
{
    static final java.lang.String ID_FIELD_NAME = "_id";

    // region Fields
    @androidx.annotation.NonNull private final android.content.Context                context      ;
    @androidx.annotation.NonNull private final android.database.sqlite.SQLiteDatabase db           ;
                                 private final java.lang.String                      tableName, tag;
    // endregion

    // region Constructors
    private Table(
    @androidx.annotation.NonNull final android.database.sqlite.SQLiteDatabase db       ,
                                 final java.lang.String                       tableName,
                                 final java.lang.String                       tag      ,
    @androidx.annotation.NonNull final android.content.Context                context  )
    { super(); this.context = context; this.db = db; this.tableName = tableName; this.tag = tag; }

    @java.lang.SuppressWarnings({"DefaultAnnotationParam"}) @androidx.annotation.VisibleForTesting(
        otherwise = androidx.annotation.VisibleForTesting.PRIVATE) Table(
    final android.content.Context context, final java.lang.String databaseName)
    {
        this(org.wheatgenetics.coordinate.database.Database.db(context, databaseName),
            "sqlite_master","TableTest", context);
    }

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    Table(final android.content.Context context, final java.lang.String tableName,
    final java.lang.String tag)
    { this(org.wheatgenetics.coordinate.database.Database.db(context), tableName, tag, context); }
    // endregion

    // region org.wheatgenetics.coordinate.StringGetter Overridden Method
    @java.lang.Override @androidx.annotation.Nullable public java.lang.String get(
    @androidx.annotation.StringRes final int resId) { return this.context.getString(resId); }
    // endregion

    // region Internal Operations
    private void logInfo(final java.lang.String msg) { android.util.Log.i(this.tag, msg); }

    private android.database.Cursor query(final boolean distinct, final java.lang.String selection,
    @java.lang.SuppressWarnings({"CStyleArrayDeclaration"}) final java.lang.String selectionArgs[],
    final java.lang.String orderBy)
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

    @androidx.annotation.NonNull private android.content.ContentValues getContentValuesForUpdate(
    @androidx.annotation.NonNull final org.wheatgenetics.coordinate.model.Model model)
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
    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @androidx.annotation.NonNull android.content.Context getContext() { return this.context; }

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @androidx.annotation.NonNull static java.lang.String whereClause()
    { return org.wheatgenetics.coordinate.database.Table.ID_FIELD_NAME + " = ?"; }

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @androidx.annotation.NonNull static java.lang.String whereClause(final long id)
    { return org.wheatgenetics.coordinate.database.Table.ID_FIELD_NAME + " = " + id; }

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    android.database.Cursor queryAll(final java.lang.String selection,
    @java.lang.SuppressWarnings({"CStyleArrayDeclaration"}) final java.lang.String selectionArgs[],
    final java.lang.String orderBy)
    {
        return this.query(
            /* distinct      => */false,
            /* selection     => */ selection    ,
            /* selectionArgs => */ selectionArgs,
            /* orderBy       => */ orderBy      );
    }

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    android.database.Cursor queryAll(final java.lang.String selection,
    @java.lang.SuppressWarnings({"CStyleArrayDeclaration"}) final java.lang.String selectionArgs[])
    {
        return this.queryAll(
            /* selection     => */ selection    ,
            /* selectionArgs => */ selectionArgs,
            /* orderBy       => */null);
    }

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    android.database.Cursor queryAll(final java.lang.String orderBy)
    {
        return this.queryAll(
            /* selection     => */null,
            /* selectionArgs => */null,
            /* orderBy       => */ orderBy);
    }

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    android.database.Cursor queryDistinct(final java.lang.String selection)
    {
        return this.query(
            /* distinct      => */true,
            /* selection     => */ selection,
            /* selectionArgs => */null,
            /* orderBy       => */null);
    }

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    android.database.Cursor rawQuery(final java.lang.String sql,
    @java.lang.SuppressWarnings({"CStyleArrayDeclaration"}) final java.lang.String selectionArgs[])
    { return this.db.rawQuery(/* sql => */ sql, /* selectionArgs => */ selectionArgs); }

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    android.database.Cursor rawQuery(final java.lang.String sql)
    { return this.rawQuery(/* sql => */ sql, /* selectionArgs => */null); }

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    static boolean exists(final android.database.Cursor cursor)
    {
        if (null == cursor)
            return false;
        else
            try     { return cursor.getCount() > 0; }
            finally { cursor.close()              ; }
    }

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    abstract org.wheatgenetics.coordinate.model.Model make(final android.database.Cursor cursor);

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @androidx.annotation.Nullable org.wheatgenetics.coordinate.model.Model makeFromFirst(
    final android.database.Cursor cursor)
    {
        if (null == cursor)
            return null;
        else
            try     { return cursor.moveToFirst() ? this.make(cursor) /* polymorphism */ : null; }
            finally { cursor.close();                                                            }
    }

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @androidx.annotation.CallSuper @androidx.annotation.NonNull
    android.content.ContentValues getContentValuesForInsert(
    @androidx.annotation.NonNull final org.wheatgenetics.coordinate.model.Model model)
    { return new android.content.ContentValues(); }

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
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
    @androidx.annotation.NonNull final org.wheatgenetics.coordinate.model.Model model)
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