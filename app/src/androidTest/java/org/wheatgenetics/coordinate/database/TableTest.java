package org.wheatgenetics.coordinate.database;

/**
 * Uses:
 * android.database.Cursor
 * android.support.test.InstrumentationRegistry
 *
 * org.junit.After
 * org.junit.Assert
 * org.junit.Test
 *
 * org.wheatgenetics.javalib.Utils
 *
 * org.wheatgenetics.coordinate.model.Model
 *
 * org.wheatgenetics.coordinate.database.Table
 *
 * org.wheatgenetics.coordinate.database.DatabaseTest
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
public class TableTest extends java.lang.Object
{
    /**
     * This class was defined in order to test Table.  Why not just test Table directly?  Because
     * Table is abstract.  Why does that matter?  Because I can't instantiate an abstract class.  If
     * I can't instantiate it I can't test it.
     */
    private static class ConcreteTable extends org.wheatgenetics.coordinate.database.Table
    {
        private ConcreteTable()
        {
            super(
                /* context   => */ android.support.test.InstrumentationRegistry.getTargetContext(),
                /* tableName => */"sqlite_master",
                /* tag       => */"TableTest",
                /* databaseName => */
                    org.wheatgenetics.coordinate.database.DatabaseTest.DATABASE_FILE_NAME);
        }

        @android.support.annotation.NonNull @java.lang.Override
        org.wheatgenetics.coordinate.model.Model make(final android.database.Cursor cursor)
        {
            // noinspection ConstantConditions
            return null;
        }
    }

    @org.junit.Test() public void secondConstructorSucceeds()
    { new org.wheatgenetics.coordinate.database.TableTest.ConcreteTable(); }

    // region Package External Operation Tests
    @org.junit.Test() public void whereClauseSucceeds()
    {
        final long id = 45;
        org.junit.Assert.assertEquals(
            org.wheatgenetics.coordinate.database.Table.ID_FIELD_NAME + " = " + id,
            org.wheatgenetics.coordinate.database.Table.whereClause(id));
    }

    @org.junit.Test() public void ascendingQueryAll()
    {
        final android.database.Cursor cursor =
            new org.wheatgenetics.coordinate.database.TableTest.ConcreteTable().queryAll(
                "[name] ASC");
        org.junit.Assert.assertNotNull(cursor);
        try
        {
            org.junit.Assert.assertEquals     (5, cursor.getColumnCount());
            org.junit.Assert.assertArrayEquals(
                new java.lang.String[]{"type", "name", "tbl_name", "rootpage", "sql"},
                cursor.getColumnNames()                                              );
            org.junit.Assert.assertEquals(8, cursor.getCount());

            final java.lang.String firstRowName, lastRowName;
            {
                final int nameColumnIndex = 1;
                cursor.moveToFirst(); firstRowName = cursor.getString(nameColumnIndex);
                cursor.moveToLast (); lastRowName  = cursor.getString(nameColumnIndex);
            }
            org.junit.Assert.assertTrue(firstRowName.compareTo(lastRowName) < 0);
        }
        finally { cursor.close(); }
    }

    @org.junit.Test() public void descendingQueryAll()
    {
        final android.database.Cursor cursor =
            new org.wheatgenetics.coordinate.database.TableTest.ConcreteTable().queryAll(
                /* orderBy => */"[name] DESC");
        org.junit.Assert.assertNotNull(cursor);
        try
        {
            final java.lang.String firstRowName, lastRowName;
            {
                final int nameColumnIndex = 1;
                cursor.moveToFirst(); firstRowName = cursor.getString(nameColumnIndex);
                cursor.moveToLast (); lastRowName  = cursor.getString(nameColumnIndex);
            }
            org.junit.Assert.assertTrue(firstRowName.compareTo(lastRowName) > 0);
        }
        finally { cursor.close(); }
    }

    @org.junit.Test() public void rawQuerySucceeds()
    {
        final android.database.Cursor firstCursor, secondCursor;
        {
            final org.wheatgenetics.coordinate.database.TableTest.ConcreteTable concreteTable =
                new org.wheatgenetics.coordinate.database.TableTest.ConcreteTable();
            firstCursor = concreteTable.rawQuery(
                "SELECT ALL [sql] FROM [sqlite_master] WHERE [name] = 'templates'");
            secondCursor = concreteTable.rawQuery(
                "SELECT ALL [sql] FROM [sqlite_master] WHERE [name] = ?",
                org.wheatgenetics.javalib.Utils.stringArray("templates"));
        }
        org.junit.Assert.assertNotNull(firstCursor); org.junit.Assert.assertNotNull(secondCursor);

        try
        {
            org.junit.Assert.assertEquals(1, firstCursor.getCount ());
            org.junit.Assert.assertEquals(1, secondCursor.getCount());

            firstCursor.moveToFirst(); secondCursor.moveToFirst();
            org.junit.Assert.assertEquals(
                firstCursor.getString(0), secondCursor.getString(0));
        }
        finally { firstCursor.close(); secondCursor.close(); }
    }

    @org.junit.Test() public void queryDistinctSucceeds()
    {
        final org.wheatgenetics.coordinate.database.TableTest.ConcreteTable concreteTable =
            new org.wheatgenetics.coordinate.database.TableTest.ConcreteTable();
        {
            final android.database.Cursor cursor = concreteTable.rawQuery(
                "SELECT ALL * FROM [sqlite_master] WHERE [type] = 'table'");
            org.junit.Assert.assertNotNull(cursor);
            try     { org.junit.Assert.assertEquals(6, cursor.getCount()); }
            finally { cursor.close(); }
        }

        {
            final android.database.Cursor cursor =
                concreteTable.queryDistinct("[type] = 'table'");
            org.junit.Assert.assertNotNull(cursor);
            try     { org.junit.Assert.assertEquals(6, cursor.getCount()); }
            finally { cursor.close(); }
        }
    }

    @org.junit.Test() public void existsWorks()
    {
        org.junit.Assert.assertFalse(
            org.wheatgenetics.coordinate.database.Table.exists(null));

        final android.database.Cursor cursor = new
            org.wheatgenetics.coordinate.database.TableTest.ConcreteTable().queryAll(null);
        org.junit.Assert.assertNotNull(cursor);
        try
        { org.junit.Assert.assertTrue(org.wheatgenetics.coordinate.database.Table.exists(cursor)); }
        finally { cursor.close(); }
    }

    @org.junit.Test() public void nullCursorMakeFromFirstReturnsNull()
    {
        org.junit.Assert.assertNull(
            new org.wheatgenetics.coordinate.database.TableTest.ConcreteTable()
                .makeFromFirst(null));
    }

    @org.junit.Test() public void getContentValuesForInsertSucceeds()
    {
        // noinspection ConstantConditions
        org.junit.Assert.assertNotNull(
            new org.wheatgenetics.coordinate.database.TableTest.ConcreteTable()
                .getContentValuesForInsert(null));
    }
    // endregion

    @org.junit.After() public void tearDown()
    { new org.wheatgenetics.coordinate.database.DatabaseTest().cleanFilesystem(); }
}