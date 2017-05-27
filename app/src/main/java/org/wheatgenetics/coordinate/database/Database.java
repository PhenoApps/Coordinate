package org.wheatgenetics.coordinate.database;

/**
 * Uses:
 * android.content.Context
 * android.database.sqlite.SQLiteDatabase
 * android.database.sqlite.SQLiteOpenHelper
 * android.util.Log
 *
 * org.w3c.dom.Document
 * org.w3c.dom.NodeList
 *
 * org.xml.sax.SAXException
 *
 * org.wheatgenetics.coordinate.R
 */

public class Database extends java.lang.Object
{
    private static class SQLiteOpenHelper extends android.database.sqlite.SQLiteOpenHelper
    {
        private static final java.lang.String TAG = "SQLiteOpenHelper";

        private final android.content.Context context                                      ;
        private       boolean                 createNeeded = false, createSucceeded = false;

        SQLiteOpenHelper(final android.content.Context context)
        {
            super(
                /* context => */ context       ,
                /* name    => */ "seedtray1.db",
                /* factory => */ null          ,
                /* version => */ 1             );
            this.context = context;
        }

        @java.lang.Override
        public void onCreate(final android.database.sqlite.SQLiteDatabase db)
        {
            this.createNeeded = true;
            {
                org.w3c.dom.NodeList statementNodeList;
                {
                    org.w3c.dom.Document document;
                    {
                        assert this.context != null;
                        final java.io.InputStream inputStream =
                            this.context.getResources().openRawResource(
                                org.wheatgenetics.coordinate.R.raw.sql);
                        try
                        {
                            final javax.xml.parsers.DocumentBuilder documentBuilder =
                                javax.xml.parsers.DocumentBuilderFactory.newInstance()
                                    .newDocumentBuilder();           // throws java.xml.parsers.Par-
                            assert documentBuilder != null;          //  serConfigurationException
                            try
                            {
                                document = documentBuilder.parse(    // throws org.xml.sax.SAXExcep-
                                    /* is       => */ inputStream,   //  tion, java.io.IOException
                                    /* systemId => */ null       );
                            }
                            catch (final org.xml.sax.SAXException | java.io.IOException e)
                            { return; }               // this.createSucceeded will not be made true.
                        }
                        catch (final javax.xml.parsers.ParserConfigurationException e)
                        { return; }                   // this.createSucceeded will not be made true.
                    }
                    assert document != null;
                    statementNodeList = document.getElementsByTagName("statement");
                }
                assert statementNodeList != null;
                assert db                != null;
                {
                    java.lang.String statement;
                    for (int i = 0; i < statementNodeList.getLength(); i++)
                    {
                        statement =
                            statementNodeList.item(i).getChildNodes().item(0).getNodeValue();
                        android.util.Log.i(
                            org.wheatgenetics.coordinate.database.Database.SQLiteOpenHelper.TAG,
                            "statement: " + statement                                          );
                        db.execSQL(statement);
                    }
                }
            }
            this.createSucceeded = true;
        }

        @java.lang.Override
        public void onUpgrade(final android.database.sqlite.SQLiteDatabase db,
        final int oldVersion, final int newVersion)
        {
            android.util.Log.w(org.wheatgenetics.coordinate.database.Database.SQLiteOpenHelper.TAG,
                "Upgrading database from version " + oldVersion + " to " + newVersion +
                    ", which will destroy all old data");
            assert db != null;
            db.execSQL("DROP TABLE IF EXISTS entries");                // TODO: What about templates
            this.onCreate(db);                                         // TODO:  and grids tables?
        }

        @java.lang.Override
        public android.database.sqlite.SQLiteDatabase getReadableDatabase()
        {
            if (this.createNeeded)
                if (this.createSucceeded) return super.getReadableDatabase(); else return null;
            else
                return super.getReadableDatabase();
        }

        @java.lang.Override
        public android.database.sqlite.SQLiteDatabase getWritableDatabase()
        {
            if (this.createNeeded)
                if (this.createSucceeded) return super.getWritableDatabase(); else return null;
            else
                return super.getWritableDatabase();
        }
    }

    private static android.database.sqlite.SQLiteDatabase db = null;

    public static void initialize(final android.content.Context context)
    {
        if (org.wheatgenetics.coordinate.database.Database.db != null)
            org.wheatgenetics.coordinate.database.Database.db.close();

        final org.wheatgenetics.coordinate.database.Database.SQLiteOpenHelper sqLiteOpenHelper =
            new org.wheatgenetics.coordinate.database.Database.SQLiteOpenHelper(context);
        org.wheatgenetics.coordinate.database.Database.db = sqLiteOpenHelper.getWritableDatabase();
    }

    static android.database.sqlite.SQLiteDatabase getDb(final android.content.Context context)
    {
        if (org.wheatgenetics.coordinate.database.Database.db == null)
        {
            org.wheatgenetics.coordinate.database.Database.initialize(context);
            assert org.wheatgenetics.coordinate.database.Database.db != null;
        }
        return org.wheatgenetics.coordinate.database.Database.db;
    }
}