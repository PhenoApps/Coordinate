package org.wheatgenetics.coordinate.database;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import org.wheatgenetics.coordinate.R;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String TAG = "Database";
    public static final String DATABASE_NAME = "seedtray1.db";
    public static final int DATABASE_VERSION = 1;

    protected Context context;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String s;

        Toast.makeText(context, "Loading Database... Please wait...", Toast.LENGTH_SHORT).show();

        try {
            InputStream in = context.getResources().openRawResource(R.raw.sql);
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(in, null);

            NodeList statements = doc.getElementsByTagName("statement");
            for (int i = 0; i < statements.getLength(); i++) {
                s = statements.item(i).getChildNodes().item(0).getNodeValue();
                Log.i(TAG, "SQL: " + s);

                db.execSQL(s);
            }
        } catch (Throwable t) {
//			Toast.makeText(context, t.toString(), 50000).show();
            Log.e(TAG, "Database Error: " + t.toString());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG, "Upgrading database from version " + oldVersion
                + " to "
                + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS entries");
        onCreate(db);
    }
}
