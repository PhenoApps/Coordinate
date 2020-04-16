package org.wheatgenetics.coordinate.database;

/**
 * Uses:
 * android.content.ContentValues
 * android.content.Context
 * android.database.Cursor
 * android.database.CursorWrapper
 *
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 * androidx.annotation.RestrictTo
 * androidx.annotation.RestrictTo.Scope
 *
 * org.wheatgenetics.javalib.Utils
 *
 * org.wheatgenetics.coordinate.StringGetter
 *
 * org.wheatgenetics.coordinate.model.EntryModel
 * org.wheatgenetics.coordinate.model.EntryModels
 * org.wheatgenetics.coordinate.model.EntryModels.Processor
 * org.wheatgenetics.coordinate.model.ExcludedEntryModel
 * org.wheatgenetics.coordinate.model.IncludedEntryModel
 * org.wheatgenetics.coordinate.model.Model
 *
 * org.wheatgenetics.coordinate.database.Table
 */
public class EntriesTable extends org.wheatgenetics.coordinate.database.Table
implements org.wheatgenetics.coordinate.model.EntryModels.Processor
{
    // region Constants
    static final java.lang.String TABLE_NAME = "entries";

            static final java.lang.String GRID_FIELD_NAME = "grid", EDATA_FIELD_NAME = "edata";
    private static final java.lang.String ROW_FIELD_NAME  = "row" , COL_FIELD_NAME   = "col"  ,
        STAMP_FIELD_NAME = "stamp";
    // endregion

    // region Constructors
    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    EntriesTable(final android.content.Context context,
    @androidx.annotation.NonNull final java.lang.String tag)
    {
        super(
            /* context   => */ context                                                      ,
            /* tableName => */ org.wheatgenetics.coordinate.database.EntriesTable.TABLE_NAME,
            /* tag       => */ tag                                                          );
    }

    public EntriesTable(final android.content.Context context)
    { this(/* context => */ context, /* tag => */"EntriesTable"); }
    // endregion

    // region Package Methods
    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    org.wheatgenetics.coordinate.model.IncludedEntryModel makeIncludedEntryModel(final long id,
    final long gridId, final int row, final int col, final java.lang.String value,
    final long timestamp)
    {
        return new org.wheatgenetics.coordinate.model.IncludedEntryModel(
            /* id           => */ id       ,
            /* gridId       => */ gridId   ,
            /* row          => */ row      ,
            /* col          => */ col      ,
            /* value        => */ value    ,
            /* timestamp    => */ timestamp,
            /* stringGetter => */this);
    }

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    org.wheatgenetics.coordinate.model.EntryModels makeEntryModels(
    final long gridId, final int rows, final int cols)
    { return new org.wheatgenetics.coordinate.model.EntryModels(gridId, rows, cols,this); }

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    boolean setEntryModel(
    @androidx.annotation.NonNull final org.wheatgenetics.coordinate.model.EntryModels entryModels,
    @androidx.annotation.NonNull final org.wheatgenetics.coordinate.model.EntryModel  entryModel )
    { entryModels.set(entryModel); return false; }
    // endregion

    // region Overridden Methods
    @java.lang.Override
    org.wheatgenetics.coordinate.model.Model make(final android.database.Cursor cursor)
    {
        if (null == cursor)
            return null;
        else
        {
            class CursorWrapper extends android.database.CursorWrapper
            {
                @androidx.annotation.NonNull private final org.wheatgenetics.coordinate.StringGetter
                    stringGetter;

                private boolean excluded()
                {
                    final java.lang.String value = this.value();
                    return null != value && value.equals(
                        org.wheatgenetics.coordinate.model.ExcludedEntryModel.DATABASE_VALUE);
                }

                // region get() Methods
                private long id()
                {
                    return this.getLong(this.getColumnIndex(
                        org.wheatgenetics.coordinate.database.EntriesTable.ID_FIELD_NAME));
                }

                private long gridId()
                {
                    return this.getLong(this.getColumnIndex(
                        org.wheatgenetics.coordinate.database.EntriesTable.GRID_FIELD_NAME));
                }

                private int row()
                {
                    return this.getInt(this.getColumnIndex(
                        org.wheatgenetics.coordinate.database.EntriesTable.ROW_FIELD_NAME));
                }

                private int col()
                {
                    return this.getInt(this.getColumnIndex(
                        org.wheatgenetics.coordinate.database.EntriesTable.COL_FIELD_NAME));
                }

                private java.lang.String value()
                {
                    return this.getString(this.getColumnIndex(
                        org.wheatgenetics.coordinate.database.EntriesTable.EDATA_FIELD_NAME));
                }

                private long timestamp()
                {
                    return this.getLong(this.getColumnIndex(
                        org.wheatgenetics.coordinate.database.EntriesTable.STAMP_FIELD_NAME));
                }
                // endregion

                private CursorWrapper(
                @androidx.annotation.NonNull final android.database.Cursor                   cursor,
                @androidx.annotation.NonNull final org.wheatgenetics.coordinate.StringGetter
                    stringGetter) { super(cursor); this.stringGetter = stringGetter; }

                private org.wheatgenetics.coordinate.model.EntryModel make()
                {
                    if (this.excluded())
                        return new org.wheatgenetics.coordinate.model.ExcludedEntryModel(
                            /* id           => */ this.id          (),
                            /* gridId       => */ this.gridId      (),
                            /* row          => */ this.row         (),
                            /* col          => */ this.col         (),
                            /* timestamp    => */ this.timestamp   (),
                            /* stringGetter => */ this.stringGetter  );
                    else return org.wheatgenetics.coordinate.database
                        .EntriesTable.this.makeIncludedEntryModel(
                            /* id        => */ this.id       (),
                            /* gridId    => */ this.gridId   (),
                            /* row       => */ this.row      (),
                            /* col       => */ this.col      (),
                            /* value     => */ this.value    (),
                            /* timestamp => */ this.timestamp());
                }
            }
            return new CursorWrapper(cursor,this).make();
        }
    }

    @java.lang.Override @androidx.annotation.NonNull
    android.content.ContentValues getContentValuesForInsert(
    @androidx.annotation.NonNull final org.wheatgenetics.coordinate.model.Model model)
    {
        final android.content.ContentValues result = super.getContentValuesForInsert(model);
        {
            final org.wheatgenetics.coordinate.model.EntryModel entryModel =
                (org.wheatgenetics.coordinate.model.EntryModel) model;

            result.put(org.wheatgenetics.coordinate.database.EntriesTable.GRID_FIELD_NAME,
                entryModel.getGridId());
            result.put(org.wheatgenetics.coordinate.database.EntriesTable.ROW_FIELD_NAME,
                entryModel.getRow());
            result.put(org.wheatgenetics.coordinate.database.EntriesTable.COL_FIELD_NAME,
                entryModel.getCol());
            result.put(org.wheatgenetics.coordinate.database.EntriesTable.EDATA_FIELD_NAME,
                entryModel.getDatabaseValue() /* polymorphism */);
            result.put(org.wheatgenetics.coordinate.database.EntriesTable.STAMP_FIELD_NAME,
                entryModel.getTimestamp());
        }
        return result;
    }

    // region org.wheatgenetics.coordinate.model.EntryModels.Processor Overridden Method
    @java.lang.Override
    public void process(final org.wheatgenetics.coordinate.model.EntryModel entryModel)
    { this.insertOrUpdate(entryModel); }
    // endregion
    // endregion

    // region Operations
    @androidx.annotation.Nullable org.wheatgenetics.coordinate.model.EntryModels load(
    final long gridId, final int rows, final int cols)
    {
        final org.wheatgenetics.coordinate.model.EntryModels result;
        {
            final android.database.Cursor cursor = this.queryAll(
                /* selection => */
                    org.wheatgenetics.coordinate.database.EntriesTable.GRID_FIELD_NAME +
                        " = ?",
                /* selectionArgs => */ org.wheatgenetics.javalib.Utils.stringArray(gridId),
                /* orderBy       => */
                    org.wheatgenetics.coordinate.database.EntriesTable.ROW_FIELD_NAME
                        + " ASC, " +
                    org.wheatgenetics.coordinate.database.EntriesTable.COL_FIELD_NAME + " ASC");
            if (null == cursor)
                result = null;
            else
                try
                {
                    if (cursor.getCount() <= 0)
                        result = null;
                    else
                    {
                        result = this.makeEntryModels(gridId, rows, cols);
                        while (cursor.moveToNext())
                        {
                            final org.wheatgenetics.coordinate.model.EntryModel entryModel =
                                (org.wheatgenetics.coordinate.model.EntryModel) this.make(cursor);
                            if (null == entryModel)
                                return null;
                            else
                                if (this.setEntryModel(result, entryModel)) return null;
                        }
                    }
                }
                finally { cursor.close(); }
        }
        return result;
    }

    // region Public Operations
    public void insert(final org.wheatgenetics.coordinate.model.EntryModels entryModels)
    { if (null != entryModels) entryModels.processAll(this); }

    public void insertOrUpdate(final org.wheatgenetics.coordinate.model.EntryModel entryModel)
    {
        if (null != entryModel)
        {
            final boolean exists = org.wheatgenetics.coordinate.database.EntriesTable.exists(
                this.queryAll(
                    /* selection => */
                        org.wheatgenetics.coordinate.database.EntriesTable.whereClause(),
                    /* selectionArgs => */
                        org.wheatgenetics.javalib.Utils.stringArray(entryModel.getId())));
            if (exists) this.update(entryModel); else this.insert(entryModel);
        }
    }

    public boolean deleteByGridId(final long gridId)
    {
        return this.deleteUsingWhereClause(/* whereClause => */
            org.wheatgenetics.coordinate.database.EntriesTable.GRID_FIELD_NAME + " = " + gridId);
    }
    // endregion
    // endregion
}