package org.wheatgenetics.coordinate.database;

/**
 * Uses:
 * android.content.Context
 * android.database.sqlite.SQLiteDatabase
 * android.database.sqlite.SQLiteOpenHelper
 * android.support.annotation.NonNull
 * android.support.annotation.Nullable
 * android.support.annotation.RawRes
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
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
class Database extends java.lang.Object
{
    private static android.database.sqlite.SQLiteDatabase dbInstance = null; // singleton, lazy load

    @android.support.annotation.NonNull @java.lang.SuppressWarnings({"DefaultAnnotationParam"})
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
                @android.support.annotation.NonNull private final android.content.Context context;
                private boolean createNeeded = false, createSucceeded = false;
                // endregion

                // region Private Methods
                private void logWarning(final java.lang.String msg)
                { android.util.Log.w("SQLiteOpenHelper", msg); }

                @android.support.annotation.Nullable private org.w3c.dom.NodeList statementNodeList(
                @android.support.annotation.RawRes final int id)
                {
                    final org.w3c.dom.Document document;
                    {
                        final java.io.InputStream inputStream =
                            this.context.getResources().openRawResource(id);
                        try
                        {
                            final javax.xml.parsers.DocumentBuilder documentBuilder =
                                javax.xml.parsers.DocumentBuilderFactory.newInstance()
                                    .newDocumentBuilder();           // throws java.xml.parsers.Par-
                            if (null == documentBuilder) return null;//  serConfigurationException
                            try
                            {
                                document = documentBuilder.parse(         // throws org.xml.sax.SAX-
                                    /* is       => */ inputStream,        //  Exception, java.-
                                    /* systemId => */null);      //  io.IOException
                            }
                            catch (final org.xml.sax.SAXException | java.io.IOException e)
                            { return null; }
                        }
                        catch (final javax.xml.parsers.ParserConfigurationException e)
                        { return null; }
                    }
                    return null == document ? null : document.getElementsByTagName("statement");
                }

                private void executeStatements(
                @android.support.annotation.NonNull final org.w3c.dom.NodeList    statementNodeList,
                @android.support.annotation.NonNull final android.database.sqlite.SQLiteDatabase db)
                {
                    final int length = statementNodeList.getLength();
                    for (int i = 0; i < length; i++)
                    {
                        final java.lang.String statement =
                            statementNodeList.item(i).getChildNodes().item(0).getNodeValue();
                        this.logWarning("statement: " + statement);
                        db.execSQL(statement);
                    }
                }
                // endregion

                private SQLiteOpenHelper(
                @android.support.annotation.NonNull final android.content.Context context ,
                                                    final java.lang.String        fileName)
                {
                    super(
                        /* context => */ context ,
                        /* name    => */ fileName,
                        /* factory => */null,
                        /* version => */2);
                    this.context = context;
                }

                // region Overridden Methods
                @java.lang.Override
                public void onCreate(final android.database.sqlite.SQLiteDatabase db)
                {
                    this.createNeeded = true;
                    {
                        final org.w3c.dom.NodeList statementNodeList = this.statementNodeList(
                            org.wheatgenetics.coordinate.R.raw.create_database_sql_statements);
                        if (null == statementNodeList)
                            return;                       // this.createSucceeded will remain false.
                        else
                            this.executeStatements(statementNodeList, db);
                    }
                    this.createSucceeded = true;
                }

                @java.lang.Override public void onUpgrade(
                final android.database.sqlite.SQLiteDatabase db,
                final int oldVersion, final int newVersion)
                {
                    final org.w3c.dom.NodeList statementNodeList = this.statementNodeList(
                        org.wheatgenetics.coordinate.R.raw.upgrade_database_sql_statements);
                    if (null != statementNodeList)
                    {
                        final int length = statementNodeList.getLength();
                        if (length > 0)
                        {
                            this.logWarning("Upgrading database from version " +
                                oldVersion + " to version " + newVersion + ".");
                            this.executeStatements(statementNodeList, db);
                        }
                    }
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

    @android.support.annotation.NonNull
    static android.database.sqlite.SQLiteDatabase db(final android.content.Context context)
    { return org.wheatgenetics.coordinate.database.Database.db(context,"seedtray1.db"); }
}