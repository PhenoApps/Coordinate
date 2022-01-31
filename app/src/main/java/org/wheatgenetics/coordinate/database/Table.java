package org.wheatgenetics.coordinate.database;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.CallSuper;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.PluralsRes;
import androidx.annotation.RestrictTo;
import androidx.annotation.StringRes;
import androidx.annotation.VisibleForTesting;

import org.wheatgenetics.coordinate.StringGetter;
import org.wheatgenetics.coordinate.model.Model;

@SuppressWarnings({"ClassExplicitlyExtendsObject"})
abstract class Table
        extends Object implements StringGetter {
    static final String ID_FIELD_NAME = "_id";

    // region Fields
    @NonNull
    private final Context context;
    @NonNull
    private final SQLiteDatabase db;
    private final String tableName, tag;
    // endregion

    // region Constructors
    private Table(
            @NonNull final SQLiteDatabase db,
            final String tableName,
            final String tag,
            @NonNull final Context context) {
        super();
        this.context = context;
        this.db = db;
        this.tableName = tableName;
        this.tag = tag;
    }

    @SuppressWarnings({"DefaultAnnotationParam"})
    @VisibleForTesting(
            otherwise = VisibleForTesting.PRIVATE)
    Table(
            final Context context, final String databaseName) {
        this(Database.db(context, databaseName),
                "sqlite_master", "TableTest", context);
    }

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    Table(final Context context, final String tableName,
          final String tag) {
        this(Database.db(context), tableName, tag, context);
    }
    // endregion

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    @NonNull
    static String whereClause() {
        return Table.ID_FIELD_NAME + " = ?";
    }

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    @NonNull
    static String whereClause(final long id) {
        return Table.ID_FIELD_NAME + " = " + id;
    }
    // endregion

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    static boolean exists(final Cursor cursor) {
        if (null == cursor)
            return false;
        else
            try {
                return cursor.getCount() > 0;
            } finally {
                cursor.close();
            }
    }

    // region org.wheatgenetics.coordinate.StringGetter Overridden Methods
    @Override
    @Nullable
    public String get(
            @StringRes final int resId) {
        return this.context.getString(resId);
    }

    @Override
    @NonNull
    public String getQuantity(
            @PluralsRes final int resId,
            @IntRange(from = 0) final int quantity,
            @Nullable final Object... formatArgs)
            throws Resources.NotFoundException {
        return this.context.getResources().getQuantityString(resId, quantity, formatArgs);
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

    // region Internal Operations
    private void logInfo(final String msg) {
        Log.i(this.tag, msg);
    }

    private Cursor query(final boolean distinct, final String selection,
                         @SuppressWarnings({"CStyleArrayDeclaration"}) final String selectionArgs[],
                         final String orderBy) {
        return this.db.query(
                /* distinct      => */ distinct,
                /* table         => */ this.tableName,
                /* columns       => */null,
                /* selection     => */ selection,
                /* selectionArgs => */ selectionArgs,
                /* groupBy       => */null,
                /* having        => */null,
                /* orderBy       => */ orderBy,
                /* limit         => */null);
    }

    @NonNull
    private ContentValues getContentValuesForUpdate(
            @NonNull final Model model) {
        final ContentValues result = this.getContentValuesForInsert(model);
        result.put(Table.ID_FIELD_NAME, model.getId());
        return result;
    }

    // region External Operations
    // region Package External Operations
    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    @NonNull
    Context getContext() {
        return this.context;
    }

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    Cursor queryAll(final String selection,
                    @SuppressWarnings({"CStyleArrayDeclaration"}) final String selectionArgs[],
                    final String orderBy) {
        return this.query(
                /* distinct      => */false,
                /* selection     => */ selection,
                /* selectionArgs => */ selectionArgs,
                /* orderBy       => */ orderBy);
    }

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    Cursor queryAll(final String selection,
                    @SuppressWarnings({"CStyleArrayDeclaration"}) final String selectionArgs[]) {
        return this.queryAll(
                /* selection     => */ selection,
                /* selectionArgs => */ selectionArgs,
                /* orderBy       => */null);
    }

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    Cursor queryAll(final String orderBy) {
        return this.queryAll(
                /* selection     => */null,
                /* selectionArgs => */null,
                /* orderBy       => */ orderBy);
    }

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    Cursor queryDistinct(final String selection) {
        return this.query(
                /* distinct      => */true,
                /* selection     => */ selection,
                /* selectionArgs => */null,
                /* orderBy       => */null);
    }

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    Cursor rawQuery(final String sql,
                    @SuppressWarnings({"CStyleArrayDeclaration"}) final String selectionArgs[]) {
        return this.db.rawQuery(/* sql => */ sql, /* selectionArgs => */ selectionArgs);
    }

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    Cursor rawQuery(final String sql) {
        return this.rawQuery(/* sql => */ sql, /* selectionArgs => */null);
    }

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    abstract Model make(final Cursor cursor);

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    @Nullable
    Model makeFromFirst(
            final Cursor cursor) {
        if (null == cursor)
            return null;
        else
            try {
                return cursor.moveToFirst() ? this.make(cursor) /* polymorphism */ : null;
            } finally {
                cursor.close();
            }
    }

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    @CallSuper
    @NonNull
    ContentValues getContentValuesForInsert(
            @NonNull final Model model) {
        return new ContentValues();
    }

    boolean deleteAll() {
        boolean failed = false;
        this.logInfo("Deleting entire table " + this.tableName);
        try {
            db.beginTransaction();
            db.execSQL("DELETE FROM " + this.tableName);
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            e.printStackTrace();
            failed = true;
        } finally {
            db.endTransaction();
        }
        return failed;
    }

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    boolean deleteUsingWhereClause(final String whereClause) {
        this.logInfo("Deleting from table " + this.tableName + " on " + whereClause);
        return this.db.delete(
                /* table       => */ this.tableName,
                /* whereClause => */ whereClause,
                /* whereArgs   => */null) > 0;
    }
    // endregion

    // region Public External Operations
    public long insert(final Model model) {
        this.logInfo("Inserting into table " + this.tableName);
        return this.db.insert(
                /* table          => */ this.tableName,
                /* nullColumnHack => */null,
                /* values         => */ this.getContentValuesForInsert(model) /* polymorphism */);
    }

    public void update(
            @NonNull final Model model) {
        final String whereClause =
                Table.whereClause(model.getId());
        this.logInfo("Updating table " + this.tableName + " on " + whereClause);
        this.db.update(
                /* table       => */ this.tableName,
                /* values      => */ this.getContentValuesForUpdate(model) /* polymorphism */,
                /* whereClause => */ whereClause,
                /* whereArgs   => */null);
    }

    public boolean delete(final long id) {
        return this.deleteUsingWhereClause(/* whereClause => */
                Table.whereClause(id));
    }
    // endregion
    // endregion
}