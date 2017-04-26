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

        private android.content.Context context  ;
        private java.lang.Exception     exception;

        SQLiteOpenHelper(final android.content.Context context)
        {
            super(
                /* context => */ context       ,
                /* name    => */ "seedtray1.db",
                /* factory => */ null          ,
                /* version => */ 1             );
            this.context = context;
        }

        @Override
        public void onCreate(final android.database.sqlite.SQLiteDatabase db)
        {
            {
                org.w3c.dom.NodeList statementNodeList;
                {
                    org.w3c.dom.Document document;
                    {
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
                                    /* systemId => */ null      );
                            }
                            catch (final org.xml.sax.SAXException | java.io.IOException e)
                            {
                                this.exception = e;
                                return;
                            }
                        }
                        catch (final javax.xml.parsers.ParserConfigurationException e)
                        {
                            this.exception = e;
                            return;
                        }
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
            this.exception = null;
        }

        @Override
        public void onUpgrade(final android.database.sqlite.SQLiteDatabase db,
        final int oldVersion, final int newVersion)
        {
            android.util.Log.w(org.wheatgenetics.coordinate.database.Database.SQLiteOpenHelper.TAG,
                "Upgrading database from version " + oldVersion + " to " + newVersion +
                    ", which will destroy all old data");
            assert db != null;
            db.execSQL("DROP TABLE IF EXISTS entries");                 // TODO: What about tem-
            this.onCreate(db);                                          //  plates and grids tables?
        }

        java.lang.Exception getException() { return this.exception; }
    }

    private static android.database.sqlite.SQLiteDatabase db = null;

    public static void initialize(final android.content.Context context) throws java.lang.Exception
    {
        if (org.wheatgenetics.coordinate.database.Database.db != null)
            org.wheatgenetics.coordinate.database.Database.db.close();

        android.database.sqlite.SQLiteDatabase db       ;
        java.lang.Exception                    exception;
        {
            final org.wheatgenetics.coordinate.database.Database.SQLiteOpenHelper helper =
                new org.wheatgenetics.coordinate.database.Database.SQLiteOpenHelper(context);
            db        = helper.getWritableDatabase();
            exception = helper.getException();
        }

        if (exception == null)
            org.wheatgenetics.coordinate.database.Database.db = db;
        else
        {
            org.wheatgenetics.coordinate.database.Database.db = null;
            throw exception;
        }
    }

    static android.database.sqlite.SQLiteDatabase getDb(final android.content.Context context)
    throws java.lang.Exception
    {
        if (org.wheatgenetics.coordinate.database.Database.db == null)
        {
            org.wheatgenetics.coordinate.database.Database.initialize(context);
            assert org.wheatgenetics.coordinate.database.Database.db != null;
        }
        return org.wheatgenetics.coordinate.database.Database.db;
    }
}