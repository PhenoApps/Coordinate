package org.wheatgenetics.coordinate.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import org.wheatgenetics.coordinate.Coordinate;

public class Grid {
    public static final String TAG = "Grid";

    public static final String DB_TABLE = "grids";
    public static final String KEY_ID = "_id";
    public static final String KEY_TEMPLATE = "temp";
    public static final String KEY_TITLE = "title";
    public static final String KEY_STAMP = "stamp";

    public long id;
    public long template;
    public long templateId;
    public String templateTitle;
    public int templateType;
    public int rows;
    public int cols;
    public String title;
    public long stamp;

    public Grid() {
        id = 0;
        template = 0;
        templateTitle = "";
        rows = 0;
        cols = 0;

        title = "";
        stamp = 0;
    }

    public Grid(long template, long stamp) {
        this.id = 0;
        this.template = template;
        this.stamp = stamp;
        this.templateTitle = templateTitle;
        this.templateType = templateType;
        this.templateId = templateId;
        this.rows = rows;
        this.cols = cols;
    }

    public boolean copy(Cursor cursor) {
        try {
            id = cursor.getInt(cursor.getColumnIndex(KEY_ID));
            template = cursor.getInt(cursor.getColumnIndex(KEY_TEMPLATE));
            title = cursor.getString(cursor.getColumnIndex(KEY_TITLE));
            stamp = cursor.getLong(cursor.getColumnIndex(KEY_STAMP));

            return true;
        } catch (Exception e) {

        }
        return false;
    }

    protected ContentValues getValues() {
        ContentValues values = new ContentValues();

        values.put(KEY_TEMPLATE, template);
        values.put(KEY_TITLE, title);
        values.put(KEY_STAMP, stamp);

        return values;
    }

    public boolean get(long id) {
        Cursor mCursor = null;
        try {
            mCursor = Coordinate.db.rawQuery("SELECT grids._id, grids.title as gridTitle, grids.stamp, templates.type as templateType, " +
                    "templates.title as templateTitle, templates._id as templateId, templates.rows, templates.cols from grids, templates where templates._id = grids.temp and grids." +
                    KEY_ID + "=" + id, null);

            if (mCursor != null) {
                mCursor.moveToFirst();
                copyAll(mCursor);
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

    public Cursor loadByTemplate(long tmp) {
        Log.i(TAG, "Loading table " + DB_TABLE + " by entry = " + tmp);
        return Coordinate.db.query(DB_TABLE, null, KEY_TEMPLATE + " = " + tmp, null, null, null, null);
    }

    public Cursor getAllGrids() {
        Log.i(TAG, "Loading table " + DB_TABLE);
        return Coordinate.db.rawQuery("SELECT grids._id, grids.title as gridTitle, grids.stamp, templates.type as templateType, templates.title as templateTitle, templates._id as templateId, templates.rows, templates.cols from grids, templates where templates._id = grids.temp", null);
    }

    public boolean copyAll(Cursor cursor) {
        try {
            id = cursor.getInt(cursor.getColumnIndex(KEY_ID));
            title = cursor.getString(cursor.getColumnIndex("gridTitle"));

            templateType = cursor.getInt(cursor.getColumnIndex("templateType"));
            templateTitle = cursor.getString(cursor.getColumnIndex("templateTitle"));
            templateId = cursor.getLong(cursor.getColumnIndex("templateId"));
            cols = cursor.getInt(cursor.getColumnIndex("cols"));
            rows = cursor.getInt(cursor.getColumnIndex("rows"));

            stamp = cursor.getLong(cursor.getColumnIndex(KEY_STAMP));

            return true;
        } catch (Exception e) {

        }
        return false;
    }

    public boolean getByTemplate(long entry) {
        Cursor mCursor = null;
        try {
            String sel = KEY_TEMPLATE + "= ?";

            String[] arg = new String[]{String.valueOf(entry)};

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

    public boolean deleteByTemplate(long entryId) {
        Log.i(TAG, "Deleting from table " + DB_TABLE + " on id = " + entryId);
        return Coordinate.db.delete(DB_TABLE, KEY_TEMPLATE + "=" + entryId, null) > 0;
    }

    @Override
    public String toString() {
        return String.format("id: %02d template: %02d stamp: %02d", id, template, stamp);
    }
}
