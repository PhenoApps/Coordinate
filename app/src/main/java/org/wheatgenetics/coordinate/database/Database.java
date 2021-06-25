package org.wheatgenetics.coordinate.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RawRes;
import androidx.annotation.VisibleForTesting;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.wheatgenetics.coordinate.R;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

@SuppressWarnings({"ClassExplicitlyExtendsObject"})
class Database extends Object {
    private static SQLiteDatabase dbInstance = null; // singleton, lazy load

    @SuppressWarnings({"DefaultAnnotationParam"})
    @VisibleForTesting(
            otherwise = VisibleForTesting.PRIVATE)
    @NonNull
    static SQLiteDatabase db(
            final Context context, final String fileName) {
        if (null == Database.dbInstance) {
            class SQLiteOpenHelper extends android.database.sqlite.SQLiteOpenHelper {
                // region Fields
                @NonNull
                private final Context context;
                private boolean createNeeded = false, createSucceeded = false;
                // endregion

                private SQLiteOpenHelper(
                        @NonNull final Context context,
                        final String fileName) {
                    super(
                            /* context => */ context,
                            /* name    => */ fileName,
                            /* factory => */null,
                            /* version => */2);
                    this.context = context;
                }

                // region Private Methods
                private void logWarning(final String msg) {
                    Log.w("SQLiteOpenHelper", msg);
                }

                @Nullable
                private NodeList statementNodeList(
                        @RawRes final int id) {
                    final Document document;
                    {
                        final InputStream inputStream =
                                this.context.getResources().openRawResource(id);
                        try {
                            final DocumentBuilder documentBuilder =
                                    DocumentBuilderFactory.newInstance()
                                            .newDocumentBuilder();           // throws java.xml.parsers.Par-
                            if (null == documentBuilder) return null;//  serConfigurationException
                            try {
                                document = documentBuilder.parse(         // throws org.xml.sax.SAX-
                                        /* is       => */ inputStream,        //  Exception, java.-
                                        /* systemId => */null);      //  io.IOException
                            } catch (final SAXException | IOException e) {
                                return null;
                            }
                        } catch (final ParserConfigurationException e) {
                            return null;
                        }
                    }
                    return null == document ? null : document.getElementsByTagName("statement");
                }
                // endregion

                private void executeStatements(
                        @NonNull final NodeList statementNodeList,
                        @NonNull final SQLiteDatabase db) {
                    final int length = statementNodeList.getLength();
                    for (int i = 0; i < length; i++) {
                        final String statement =
                                statementNodeList.item(i).getChildNodes().item(0).getNodeValue();
                        this.logWarning("statement: " + statement);
                        db.execSQL(statement);
                    }
                }

                // region Overridden Methods
                @Override
                public void onCreate(final SQLiteDatabase db) {
                    this.createNeeded = true;
                    {
                        final NodeList statementNodeList = this.statementNodeList(
                                R.raw.create_database_sql_statements);
                        if (null == statementNodeList)
                            return;                       // this.createSucceeded will remain false.
                        else
                            this.executeStatements(statementNodeList, db);
                    }
                    this.createSucceeded = true;
                }

                @Override
                public void onUpgrade(
                        final SQLiteDatabase db,
                        final int oldVersion, final int newVersion) {
                    final NodeList statementNodeList = this.statementNodeList(
                            R.raw.upgrade_database_sql_statements);
                    if (null != statementNodeList) {
                        final int length = statementNodeList.getLength();
                        if (length > 0) {
                            this.logWarning("Upgrading database from version " +
                                    oldVersion + " to version " + newVersion + ".");
                            this.executeStatements(statementNodeList, db);
                        }
                    }
                }

                @Override
                public SQLiteDatabase getReadableDatabase() {
                    if (this.createNeeded)
                        return this.createSucceeded ? super.getReadableDatabase() : null;
                    else
                        return super.getReadableDatabase();
                }

                @Override
                public SQLiteDatabase getWritableDatabase() {
                    if (this.createNeeded)
                        return this.createSucceeded ? super.getWritableDatabase() : null;
                    else
                        return super.getWritableDatabase();
                }
                // endregion
            }
            Database.dbInstance =
                    new SQLiteOpenHelper(context, fileName).getWritableDatabase();
        }
        return Database.dbInstance;
    }

    @NonNull
    static SQLiteDatabase db(
            final Context context) {
        return Database.db(context, "seedtray1.db");
    }
}