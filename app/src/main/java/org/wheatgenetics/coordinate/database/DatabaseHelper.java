package org.wheatgenetics.coordinate.database;

/**
 * Uses:
 * android.content.Context
 * android.database.sqlite.SQLiteDatabase
 * android.database.sqlite.SQLiteOpenHelper
 * android.util.Log
 * android.widget.Toast
 *
 * org.w3c.dom.Document
 * org.w3c.dom.NodeList
 */

public class DatabaseHelper extends android.database.sqlite.SQLiteOpenHelper
{
    private static final java.lang.String TAG = "DatabaseHelper";

    private android.content.Context context;
    private boolean                 created = false;


    public DatabaseHelper(final android.content.Context context)
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
        android.widget.Toast.makeText(this.context,
            "Loading Database... Please wait...", android.widget.Toast.LENGTH_SHORT).show();
        {
            org.w3c.dom.NodeList statementNodeList;
            {
                org.w3c.dom.Document document;
                {
                    final java.io.InputStream inputStream = this.context.getResources()
                        .openRawResource(org.wheatgenetics.coordinate.R.raw.sql);
                    try
                    {
                        final javax.xml.parsers.DocumentBuilder documentBuilder =
                            javax.xml.parsers.DocumentBuilderFactory.newInstance()
                                .newDocumentBuilder();        // throws ParserConfigurationException
                        assert documentBuilder != null;
                        try
                        {
                            document = documentBuilder.parse(    // throws SAXException, IOException
                                /* is       => */ inputStream,
                                /* systemId => */ null      );
                        }
                        catch (final org.xml.sax.SAXException | java.io.IOException e)
                        {
                            return;       // created will not be set true at the end of this method.
                        }
                    }
                    catch (final javax.xml.parsers.ParserConfigurationException e)
                    {
                        return;           // created will not be set true at the end of this method.
                    }
                }
                assert document != null;
                statementNodeList = document.getElementsByTagName("statement");
            }
            assert statementNodeList != null;
            assert db != null;
            {
                java.lang.String statement;
                for (int i = 0; i < statementNodeList.getLength(); i++)
                {
                    statement = statementNodeList.item(i).getChildNodes().item(0).getNodeValue();
                    android.util.Log.i(org.wheatgenetics.coordinate.database.DatabaseHelper.TAG,
                        "SQL: " + statement);
                    db.execSQL(statement);
                }
            }
        }
        this.created = true;
    }

    @Override
    public void onUpgrade(final android.database.sqlite.SQLiteDatabase db,
    final int oldVersion, final int newVersion)
    {
        android.util.Log.w(org.wheatgenetics.coordinate.database.DatabaseHelper.TAG,
            "Upgrading database from version " + oldVersion + " to " + newVersion +
            ", which will destroy all old data");
        assert db != null;
        db.execSQL("DROP TABLE IF EXISTS entries");  // TODO: What about templates and grids tables?
        this.created = false;
        this.onCreate(db);
    }
}