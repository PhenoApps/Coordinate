package org.wheatgenetics.coordinate.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import org.wheatgenetics.coordinate.Coordinate;

public class Entry {
    public static final String TAG = "Entry";

    public static final String DB_TABLE = "entries";
    public static final String KEY_ID = "_id";
    public static final String KEY_GRID = "grid";
    public static final String KEY_COL = "col";
    public static final String KEY_ROW = "row";
    public static final String KEY_VALUE = "edata";
    public static final String KEY_STAMP = "stamp";

    public long id;
    public long grid;
    public int col;
    public int row;
    public String value;
    public long stamp;

    public Entry() {
        id = 0;
        grid = 0;
        col = 0;
        row = 0;
        value = "";
        stamp = 0;
    }

    public Entry(long entry, int col, int row, String value, long stamp) {
        this.id = 0;
        this.grid = entry;
        this.col = col;
        this.row = row;
        this.value = value;
        this.stamp = stamp;
    }

    public void copy(Cursor cursor) {
        id = cursor.getInt(cursor.getColumnIndex(KEY_ID));
        grid = cursor.getInt(cursor.getColumnIndex(KEY_GRID));
        col = cursor.getInt(cursor.getColumnIndex(KEY_COL));
        row = cursor.getInt(cursor.getColumnIndex(KEY_ROW));
        value = cursor.getString(cursor.getColumnIndex(KEY_VALUE));
        stamp = cursor.getLong(cursor.getColumnIndex(KEY_STAMP));
    }

    protected ContentValues getValues() {
        ContentValues values = new ContentValues();

        values.put(KEY_GRID, grid);
        values.put(KEY_COL, col);
        values.put(KEY_ROW, row);
        values.put(KEY_VALUE, value);
        values.put(KEY_STAMP, stamp);

        return values;
    }

    public boolean get(long id) {
        Cursor mCursor = null;
        try {
            mCursor = Coordinate.db.query(true, DB_TABLE, null, KEY_ID + "=" + id, null, null, null, null, null);
            if (mCursor != null) {
                mCursor.moveToFirst();
                copy(mCursor);
                mCursor.close();
                return true;
            }
        } catch (Exception e) {
            if (mCursor != null) {
                mCursor.close();
            }
        }
        return false;
    }

    public Cursor load() {
        Log.i(TAG, "Loading table " + DB_TABLE);
        return Coordinate.db.query(DB_TABLE, null, null, null, null, null, null);
    }

    public long insert() {
        Log.i(TAG, "Inserting into table " + DB_TABLE);
        return Coordinate.db.insert(DB_TABLE, null, getValues());
    }

    public boolean update() {
        Log.i(TAG, "Updating table " + DB_TABLE + " on id = " + id);
        return Coordinate.db.update(DB_TABLE, getValues(), KEY_ID + "=" + id, null) > 0;
    }


    public boolean delete(long id) {
        Log.i(TAG, "Deleting from table " + DB_TABLE + " on id = " + id);
        return Coordinate.db.delete(DB_TABLE, KEY_ID + "=" + id, null) > 0;
    }

    public boolean delete() {
        Log.i(TAG, "Clearing table " + DB_TABLE);
        return Coordinate.db.delete(DB_TABLE, null, null) > 0;
    }

    public Cursor loadByEntry(int entry) {
        Log.i(TAG, "Loading table " + DB_TABLE + " by entry = " + entry);
        return Coordinate.db.query(DB_TABLE, null, KEY_GRID + " = " + entry, null, null, null, null);
    }

    public boolean getByGrid(long grid, int row, int col) {
        Cursor mCursor = null;
        try {
            String sel = KEY_GRID + "= ? AND " + KEY_COL + "= ? AND " + KEY_ROW + "= ?";

            String[] arg = new String[]{String.valueOf(grid), String.valueOf(col), String.valueOf(row)};

            mCursor = Coordinate.db.query(true, DB_TABLE, null, sel, arg, null, null, null, null);
            if (mCursor != null) {
                boolean ret = false;
                if (mCursor.moveToFirst()) {
                    copy(mCursor);
                    ret = true;
                }
                mCursor.close();
                return ret;
            }
        } catch (Exception e) {
            if (mCursor != null) {
                mCursor.close();
            }
        }
        return false;
    }

    public boolean deleteByGrid(long grid) {
        Log.i(TAG, "Deleting from table " + DB_TABLE + " on id = " + grid);
        return Coordinate.db.delete(DB_TABLE, KEY_GRID + "=" + grid, null) > 0;
    }

    @Override
    public String toString() {
        return String.format("id: %02d entry: %02d col: %02d row: %02d value: %.2f", id, grid, col, row, value);
    }
}
