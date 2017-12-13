package org.wheatgenetics.coordinate.database;

/**
 * Uses:
 * android.content.Context
 * android.database.sqlite.SQLiteDatabase
 * android.database.sqlite.SQLiteOpenHelper
 * android.support.annotation.VisibleForTesting
 * android.util.Log
 *
 * org.w3c.dom.Document
 * org.w3c.dom.NodeList
 *
 * org.xml.sax.SAXException
 *
 * org.wheatgenetics.coordinate.R
 */
@java.lang.SuppressWarnings("ClassExplicitlyExtendsObject")
class Database extends java.lang.Object
{
    private static android.database.sqlite.SQLiteDatabase dbInstance = null;

    @android.support.annotation.VisibleForTesting(
        otherwise = android.support.annotation.VisibleForTesting.PRIVATE)
    static android.database.sqlite.SQLiteDatabase db(final android.content.Context context,
    final java.lang.String fileName)
    {
        if (null == org.wheatgenetics.coordinate.database.Database.dbInstance)
        {
            class SQLiteOpenHelper extends android.database.sqlite.SQLiteOpenHelper
            {
                // region Fields
                private final android.content.Context context                                      ;
                private       boolean                 createNeeded = false, createSucceeded = false;
                // endregion

                private int logWarning(final java.lang.String msg)
                { return android.util.Log.w("SQLiteOpenHelper", msg); }

                private SQLiteOpenHelper(final android.content.Context context,
                final java.lang.String fileName)
                {
                    super(
                        /* context => */ context ,
                        /* name    => */ fileName,
                        /* factory => */ null    ,
                        /* version => */ 1       );
                    this.context = context;
                }

                // region Overridden Methods
                @java.lang.Override
                public void onCreate(final android.database.sqlite.SQLiteDatabase db)
                {
                    this.createNeeded = true;
                    {
                        org.w3c.dom.NodeList statementNodeList;
                        {
                            org.w3c.dom.Document document;
                            {
                                assert null != this.context;
                                final java.io.InputStream inputStream =
                                    this.context.getResources().openRawResource(org.wheatgenetics
                                        .coordinate.R.raw.create_table_sql_statements);
                                try
                                {
                                    final javax.xml.parsers.DocumentBuilder documentBuilder =
                                        javax.xml.parsers.DocumentBuilderFactory.newInstance()
                                            .newDocumentBuilder();   // throws java.xml.parsers.Par-
                                    assert null != documentBuilder;  //  serConfigurationException
                                    try
                                    {
                                        document = documentBuilder.parse( // throws org.xml.sax.SAX-
                                            /* is       => */ inputStream,    //  Exception, java.-
                                            /* systemId => */ null       );   //  io.IOException
                                    }
                                    catch (final org.xml.sax.SAXException | java.io.IOException e)
                                    { return; }           // this.createSucceeded will remain false.
                                }
                                catch (final javax.xml.parsers.ParserConfigurationException e)
                                { return; }               // this.createSucceeded will remain false.
                            }
                            assert null != document;
                            statementNodeList = document.getElementsByTagName("statement");
                        }
                        assert null != statementNodeList; assert null != db;
                        {
                            final int length = statementNodeList.getLength();
                            for (int i = 0; i < length; i++)
                            {
                                final java.lang.String statement = statementNodeList.item(i)
                                    .getChildNodes().item(0).getNodeValue();
                                this.logWarning("statement: " + statement);
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
                    this.logWarning("Upgrading database from version " + oldVersion +
                        " to " + newVersion + ", which will destroy all old data");
                    {
                        final java.lang.String format = "DROP %s IF EXISTS %s";
                        assert null != db;
                        db.execSQL(java.lang.String.format(format, "entries"  , "entries"  ));
                        db.execSQL(java.lang.String.format(format, "grids"    , "grids"    ));
                        db.execSQL(java.lang.String.format(format, "templates", "templates"));
                    }
                    this.onCreate(db);
                }

                @java.lang.Override
                public android.database.sqlite.SQLiteDatabase getReadableDatabase()
                {
                    if (this.createNeeded)
                        return this.createSucceeded ? super.getReadableDatabase() : null;
                    else
                        return super.getReadableDatabase();
                }

                @java.lang.Override
                public android.database.sqlite.SQLiteDatabase getWritableDatabase()
                {
                    if (this.createNeeded)
                        return this.createSucceeded ? super.getWritableDatabase() : null;
                    else
                        return super.getWritableDatabase();
                }
                // endregion
            }
            org.wheatgenetics.coordinate.database.Database.dbInstance =
                new SQLiteOpenHelper(context, fileName).getWritableDatabase();
        }
        return org.wheatgenetics.coordinate.database.Database.dbInstance;
    }

    static android.database.sqlite.SQLiteDatabase db(final android.content.Context context)
    { return org.wheatgenetics.coordinate.database.Database.db(context, "seedtray1.db"); }
}