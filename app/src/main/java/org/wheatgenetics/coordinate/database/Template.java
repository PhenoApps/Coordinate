package org.wheatgenetics.coordinate.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import org.wheatgenetics.coordinate.Coordinate;


public class Template {
    public static final String TAG = "Template";

    public static final String DB_TABLE = "templates";
    public static final String KEY_ID = "_id";
    public static final String KEY_TITLE = "title";
    public static final String KEY_TYPE = "type";

    public static final String KEY_COLS = "cols";
    public static final String KEY_ROWS = "rows";

    public static final String KEY_ECELLS = "ecells";
    public static final String KEY_ECOLS = "ecols";
    public static final String KEY_EROWS = "erows";

    public static final String KEY_CNUMB = "cnumb";
    public static final String KEY_RNUMB = "rnumb";

    public static final String KEY_OPTIONS = "options";

    public static final String KEY_STAMP = "stamp";

    public long id;
    public String title;
    public int type;
    public int rows;
    public int cols;

    public String ecells;

    public String erows;
    public String ecols;

    public String options;

    public int cnumbering;
    public int rnumbering;

    public long stamp;

    public Template() {
        id = 0;

        title = "";
        type = 0;

        rows = 0;
        cols = 0;

        ecells = "";
        ecols = "";
        erows = "";

        options = "";

        cnumbering = 1;
        rnumbering = 1;

        stamp = 0;

    }

    public Template(int id, String title, int type, int cols, int rows, long stamp) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.cols = cols;
        this.rows = rows;
        this.stamp = stamp;
    }

    public boolean copy(Cursor cursor) {
        try {
            id = cursor.getInt(cursor.getColumnIndex(KEY_ID));
            title = cursor.getString(cursor.getColumnIndex(KEY_TITLE));
            type = cursor.getInt(cursor.getColumnIndex(KEY_TYPE));
            cols = cursor.getInt(cursor.getColumnIndex(KEY_COLS));
            rows = cursor.getInt(cursor.getColumnIndex(KEY_ROWS));

            ecells = cursor.getString(cursor.getColumnIndex(KEY_ECELLS));
            ecols = cursor.getString(cursor.getColumnIndex(KEY_ECOLS));
            erows = cursor.getString(cursor.getColumnIndex(KEY_EROWS));

            options = cursor.getString(cursor.getColumnIndex(KEY_OPTIONS));

            cnumbering = cursor.getInt(cursor.getColumnIndex(KEY_RNUMB));
            rnumbering = cursor.getInt(cursor.getColumnIndex(KEY_CNUMB));

            stamp = cursor.getLong(cursor.getColumnIndex(KEY_STAMP));

            return true;
        } catch (Exception e) {

        }
        return false;
    }

    protected ContentValues getValues() {
        ContentValues values = new ContentValues();

        values.put(KEY_TITLE, title);
        values.put(KEY_TYPE, type);
        values.put(KEY_COLS, cols);
        values.put(KEY_ROWS, rows);

        values.put(KEY_ECELLS, ecells);
        values.put(KEY_ECOLS, ecols);
        values.put(KEY_EROWS, erows);

        values.put(KEY_CNUMB, cnumbering);
        values.put(KEY_RNUMB, rnumbering);

        values.put(KEY_OPTIONS, options);

        values.put(KEY_STAMP, stamp);

        return values;
    }

    public boolean get(long id) {
        boolean ret = false;
        Cursor mCursor = null;
        try {
            mCursor = Coordinate.db.query(true, DB_TABLE, null, KEY_ID + "=" + id, null, null, null, null, null);
            if (mCursor != null) {
                if (mCursor.moveToFirst()) {
                    ret = copy(mCursor);
                }
            }
        } catch (Exception e) {
        } finally {
            if (mCursor != null) {
                mCursor.close();
            }
        }
        return ret;
    }


    public boolean getByType(int typ) {
        boolean ret = false;
        Cursor mCursor = null;
        try {
            mCursor = Coordinate.db.query(true, DB_TABLE, null, KEY_TYPE + "=" + typ, null, null, null, null, null);
            if (mCursor != null) {
                if (mCursor.moveToFirst()) {
                    ret = copy(mCursor);
                }
            }
        } catch (Exception e) {
        } finally {
            if (mCursor != null) {
                mCursor.close();
            }
        }
        return ret;
    }

    public Cursor load() {
        Log.i(TAG, "Loading table " + DB_TABLE);
        return Coordinate.db.query(DB_TABLE, null, null, null, null, null, "type ASC");
    }

    public Cursor loadByOrder() {
        Log.i(TAG, "Loading table " + DB_TABLE);
        return Coordinate.db.query(DB_TABLE, null, null, null, null, null, "_id DESC");
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

    @Override
    public String toString() {
        return "Template [id=" + id + ", title=" + title + ", type=" + type + ", rows=" + rows + ", cols=" + cols + ", ecells=" + ecells + ", erows=" + erows + ", ecols=" + ecols + ", options=" + options + ", cnumbering=" + cnumbering + ", rnumbering=" + rnumbering + ", stamp=" + stamp + "]";
    }


}
