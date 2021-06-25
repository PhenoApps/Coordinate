package org.wheatgenetics.coordinate.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.IntRange;
import androidx.annotation.RawRes;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.wheatgenetics.coordinate.R;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;

/**
 * Uses:
 * android.database.Cursor
 * android.database.sqlite.SQLiteDatabase
 * android.util.Log
 *
 * androidx.annotation.IntRange
 * androidx.annotation.RawRes
 * androidx.test.platform.app.InstrumentationRegistry
 *
 * org.junit.After
 * org.junit.Assert
 * org.junit.Before
 * org.junit.Test
 *
 * org.wheatgenetics.coordinate.R
 *
 * org.wheatgenetics.coordinate.database.Database
 */
@SuppressWarnings({"ClassExplicitlyExtendsObject"})
public class DatabaseTest extends Object
{
    // region Constants
    private static final String
        PARENT_PATH = "/data/data/org.wheatgenetics.coordinate/databases/",
        FILE_NAME   = "test"                                              ;

    private static final String
        FILE_PATH = DatabaseTest.PARENT_PATH +
            DatabaseTest.FILE_NAME,
        DATABASE_EXTENSION_NAME = ".db";

    static final String DATABASE_FILE_NAME =
        DatabaseTest.FILE_NAME +
            DatabaseTest.DATABASE_EXTENSION_NAME;
    // endregion

    private final File
        databaseFile = new File(
            DatabaseTest.FILE_PATH +
                DatabaseTest.DATABASE_EXTENSION_NAME),
        journalFile = new File(
            DatabaseTest.FILE_PATH + ".db-journal");

    // region Private Methods
    private static void logInfo(final int a, final int e)
    { Log.i("DatabaseTest", String.format("'%c'|'%c'", a, e)); }

    private static void testTable(final String                       name,
                                  final SQLiteDatabase db  ,
    @RawRes   final int                                    id  )
    throws IOException
    {
        final StringReader actual;
        {
            Assert.assertNotNull(db);
            final Cursor cursor = db.rawQuery(String.format(
                    "SELECT ALL [sql] FROM [sqlite_master] WHERE [name] = '%s'", name),
                null);
            if (null == cursor)
                actual = null;
            else
                try
                {
                    cursor.moveToFirst();
                    actual = new StringReader(cursor.getString(0));
                }
                finally { cursor.close(); }
        }
        Assert.assertNotNull(actual);

        final InputStreamReader expected = new InputStreamReader(
            InstrumentationRegistry.getInstrumentation()
                .getTargetContext().getResources().openRawResource(id));

        // 'a' is for actual and 'e' is for expected.
        DatabaseTest.logInfo('a','e');
        do
        {
            @IntRange(from = -1) int a, e;

            do { a = actual.read() /* throws java.io.IOException */; }
            while (Character.isWhitespace(a));

            do { e = expected.read() /* throws java.io.IOException */; }
            while (Character.isWhitespace(e));

            if (-1 == a && -1 == e) break;

            Assert.assertEquals(e, a);
            DatabaseTest.logInfo(a, e);
        }
        while (true);
    }
    // endregion

    void cleanFilesystem()
    {
        final File parent = this.databaseFile.getParentFile();

        Assert.assertNotNull(parent);
        if (parent.exists())
        {
            if (this.databaseFile.exists())
                // noinspection ResultOfMethodCallIgnored
                this.databaseFile.delete();

            if (this.journalFile.exists())
                // noinspection ResultOfMethodCallIgnored
                this.journalFile.delete();

            // noinspection CStyleArrayDeclaration
            final String list[] = parent.list();
            if (null != list) if (list.length <= 0)
                // noinspection ResultOfMethodCallIgnored
                parent.delete();
        }
    }

    @Before() public void setUp() { this.cleanFilesystem(); }

    // region db() Tests
    /** nullContextDb() must be run before nonNullContextDb(). */
    @Test(expected = NullPointerException.class) public void nullContextDb()
    {
        Assert.assertFalse(this.databaseFile.exists());
        Assert.assertFalse(this.journalFile.exists ());
        Database.db(  // throws java.lang.NullPointerException
            /* context  => */null,
            /* fileName => */
                DatabaseTest.DATABASE_FILE_NAME);
    }

    /** nonNullContextDb() must be run after nullContextDb(). */
    @Test() public void nonNullContextDb() throws IOException
    {
        final SQLiteDatabase db =
            Database.db(
                /* context => */ InstrumentationRegistry
                    .getInstrumentation().getTargetContext(),
                /* fileName => */
                    DatabaseTest.DATABASE_FILE_NAME);
        DatabaseTest.testTable(  // throws java.io.IOException
            "templates", db, R.raw.create_templates_table);
        DatabaseTest.testTable(  // throws java.io.IOException
            "projects", db, R.raw.create_projects_table);
        DatabaseTest.testTable(  // throws java.io.IOException
            "grids", db, R.raw.create_grids_table);
        DatabaseTest.testTable(  // throws java.io.IOException
            "entries", db, R.raw.create_entries_table);
    }
    // endregion

    @After() public void tearDown() { this.cleanFilesystem(); }
}