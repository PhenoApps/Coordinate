package org.wheatgenetics.coordinate.database;

/**
 * Uses:
 * android.database.Cursor
 * android.database.sqlite.SQLiteDatabase
 * android.util.Log
 * android.support.test.InstrumentationRegistry
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
@java.lang.SuppressWarnings("ClassExplicitlyExtendsObject")
public class DatabaseTest extends java.lang.Object
{
    // region Constants
    private static final java.lang.String
        PARENT_PATH = "/data/data/org.wheatgenetics.coordinate/databases/",
        FILE_NAME   = "test"                                              ;

    private static final java.lang.String
        FILE_PATH = org.wheatgenetics.coordinate.database.DatabaseTest.PARENT_PATH +
            org.wheatgenetics.coordinate.database.DatabaseTest.FILE_NAME,
        DATABASE_EXTENSION_NAME = ".db";

    static final java.lang.String DATABASE_FILE_NAME =
        org.wheatgenetics.coordinate.database.DatabaseTest.FILE_NAME +
            org.wheatgenetics.coordinate.database.DatabaseTest.DATABASE_EXTENSION_NAME;
    // endregion

    private final java.io.File
        databaseFile = new java.io.File(
            org.wheatgenetics.coordinate.database.DatabaseTest.FILE_PATH +
                org.wheatgenetics.coordinate.database.DatabaseTest.DATABASE_EXTENSION_NAME),
        journalFile = new java.io.File(
            org.wheatgenetics.coordinate.database.DatabaseTest.FILE_PATH + ".db-journal");

    // region Private Methods
    private static int logInfo(final int a, final int e)
    { return android.util.Log.i("DatabaseTest", java.lang.String.format("'%c'|'%c'", a, e)); }

    private static void testTable(final java.lang.String name,
    final android.database.sqlite.SQLiteDatabase db, final int id) throws java.io.IOException
    {
        final java.io.StringReader actual;
        {
            org.junit.Assert.assertNotNull(db);
            final android.database.Cursor cursor = db.rawQuery(java.lang.String.format(
                "SELECT ALL [sql] FROM [sqlite_master] WHERE [name] = '%s'", name), null);
            if (null == cursor)
                actual = null;
            else
                try
                {
                    cursor.moveToFirst();
                    actual = new java.io.StringReader(cursor.getString(0));
                }
                finally { cursor.close(); }
        }
        org.junit.Assert.assertNotNull(actual);

        final java.io.InputStreamReader expected = new java.io.InputStreamReader(
            android.support.test.InstrumentationRegistry.getTargetContext()
                .getResources().openRawResource(id));

        // 'a' is for actual and 'e' is for expected.
        org.wheatgenetics.coordinate.database.DatabaseTest.logInfo('a', 'e');
        do
        {
            int a, e;

            do { a = actual.read() /* throws java.io.IOException */; }
            while (java.lang.Character.isWhitespace(a));

            do { e = expected.read() /* throws java.io.IOException */; }
            while (java.lang.Character.isWhitespace(e));

            if (-1 == a && -1 == e) break;

            org.junit.Assert.assertEquals(e, a);
            org.wheatgenetics.coordinate.database.DatabaseTest.logInfo(a, e);
        }
        while (true);
    }
    // endregion

    @java.lang.SuppressWarnings("ResultOfMethodCallIgnored")
    void cleanFilesystem()
    {
        final java.io.File parent = this.databaseFile.getParentFile();

        org.junit.Assert.assertNotNull(parent);
        if (parent.exists())
        {
            if (this.databaseFile.exists()) this.databaseFile.delete();
            if (this.journalFile.exists ()) this.journalFile.delete ();
            if (parent.list().length <= 0 ) parent.delete           ();
        }
    }

    // region Public Methods
    @org.junit.Before
    public void setUp() { this.cleanFilesystem(); }

    /** nullContextDb() must be run before nonNullContextDb(). */
    @org.junit.Test(expected = java.lang.NullPointerException.class)
    public void nullContextDb()
    {
        org.junit.Assert.assertFalse(this.databaseFile.exists());
        org.junit.Assert.assertFalse(this.journalFile.exists ());
        org.wheatgenetics.coordinate.database.Database.db(  // throws java.lang.NullPointerException
            /* context  => */ null,
            /* fileName => */
                org.wheatgenetics.coordinate.database.DatabaseTest.DATABASE_FILE_NAME);
    }

    /** nonNullContextDb() must be run after nullContextDb(). */
    @org.junit.Test
    public void nonNullContextDb() throws java.io.IOException
    {
        final android.database.sqlite.SQLiteDatabase db =
            org.wheatgenetics.coordinate.database.Database.db(
                /* context  => */ android.support.test.InstrumentationRegistry.getTargetContext(),
                /* fileName => */
                    org.wheatgenetics.coordinate.database.DatabaseTest.DATABASE_FILE_NAME);
        org.wheatgenetics.coordinate.database.DatabaseTest.testTable("templates",
            db, org.wheatgenetics.coordinate.R.raw.create_templates_table);
        org.wheatgenetics.coordinate.database.DatabaseTest.testTable("grids",
            db, org.wheatgenetics.coordinate.R.raw.create_grids_table);
        org.wheatgenetics.coordinate.database.DatabaseTest.testTable("entries",
            db, org.wheatgenetics.coordinate.R.raw.create_entries_table);
    }

    @org.junit.After
    public void tearDown() { this.cleanFilesystem(); }
    // endregion
}