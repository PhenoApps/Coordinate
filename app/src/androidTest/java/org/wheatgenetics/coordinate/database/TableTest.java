package org.wheatgenetics.coordinate.database;

import android.database.Cursor;

import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.phenoapps.androidlibrary.Utils;
import org.wheatgenetics.coordinate.model.Model;

/**
 * Uses:
 * android.database.Cursor
 *
 * androidx.test.platform.app.InstrumentationRegistry
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
@SuppressWarnings({"ClassExplicitlyExtendsObject"})
public class TableTest extends Object
{
    /**
     * This class was defined in order to test Table.  Why not just test Table directly?  Because
     * Table is abstract.  Why does that matter?  Because I can't instantiate an abstract class.  If
     * I can't instantiate it I can't test it.
     */
    private static class ConcreteTable extends Table
    {
        private ConcreteTable()
        {
            super(
                /* context => */ InstrumentationRegistry
                    .getInstrumentation().getTargetContext(),
                /* databaseName => */
                    DatabaseTest.DATABASE_FILE_NAME);
        }

        @Override Model make(
        final Cursor cursor) { return null; }
    }

    @Test() public void secondConstructorSucceeds()
    { new TableTest.ConcreteTable(); }

    // region Package External Operation Tests
    @Test() public void whereClauseSucceeds()
    {
        final long id = 45;
        Assert.assertEquals(
            Table.ID_FIELD_NAME + " = " + id,
            Table.whereClause(id));
    }

    @Test() public void ascendingQueryAll()
    {
        final Cursor cursor =
            new TableTest.ConcreteTable().queryAll(
                "[name] ASC");
        Assert.assertNotNull(cursor);
        try
        {
            Assert.assertEquals     (5, cursor.getColumnCount());
            Assert.assertArrayEquals(
                new String[]{"type", "name", "tbl_name", "rootpage", "sql"},
                cursor.getColumnNames()                                              );
            Assert.assertEquals(8, cursor.getCount());

            final String firstRowName, lastRowName;
            {
                final int nameColumnIndex = 1;
                cursor.moveToFirst(); firstRowName = cursor.getString(nameColumnIndex);
                cursor.moveToLast (); lastRowName  = cursor.getString(nameColumnIndex);
            }
            Assert.assertTrue(firstRowName.compareTo(lastRowName) < 0);
        }
        finally { cursor.close(); }
    }

    @Test() public void descendingQueryAll()
    {
        final Cursor cursor =
            new TableTest.ConcreteTable().queryAll(
                /* orderBy => */"[name] DESC");
        Assert.assertNotNull(cursor);
        try
        {
            final String firstRowName, lastRowName;
            {
                final int nameColumnIndex = 1;
                cursor.moveToFirst(); firstRowName = cursor.getString(nameColumnIndex);
                cursor.moveToLast (); lastRowName  = cursor.getString(nameColumnIndex);
            }
            Assert.assertTrue(firstRowName.compareTo(lastRowName) > 0);
        }
        finally { cursor.close(); }
    }

    @Test() public void rawQuerySucceeds()
    {
        final Cursor firstCursor, secondCursor;
        {
            final TableTest.ConcreteTable concreteTable =
                new TableTest.ConcreteTable();
            firstCursor = concreteTable.rawQuery(
                "SELECT ALL [sql] FROM [sqlite_master] WHERE [name] = 'templates'");
            secondCursor = concreteTable.rawQuery(
                "SELECT ALL [sql] FROM [sqlite_master] WHERE [name] = ?",
                Utils.stringArray("templates"));
        }
        Assert.assertNotNull(firstCursor); Assert.assertNotNull(secondCursor);

        try
        {
            Assert.assertEquals(1, firstCursor.getCount ());
            Assert.assertEquals(1, secondCursor.getCount());

            firstCursor.moveToFirst(); secondCursor.moveToFirst();
            Assert.assertEquals(
                firstCursor.getString(0), secondCursor.getString(0));
        }
        finally { firstCursor.close(); secondCursor.close(); }
    }

    @Test() public void queryDistinctSucceeds()
    {
        final TableTest.ConcreteTable concreteTable =
            new TableTest.ConcreteTable();
        {
            final Cursor cursor = concreteTable.rawQuery(
                "SELECT ALL * FROM [sqlite_master] WHERE [type] = 'table'");
            Assert.assertNotNull(cursor);
            try     { Assert.assertEquals(6, cursor.getCount()); }
            finally { cursor.close(); }
        }

        {
            final Cursor cursor =
                concreteTable.queryDistinct("[type] = 'table'");
            Assert.assertNotNull(cursor);
            try     { Assert.assertEquals(6, cursor.getCount()); }
            finally { cursor.close(); }
        }
    }

    @Test() public void existsWorks()
    {
        Assert.assertFalse(
            Table.exists(null));

        final Cursor cursor = new
            TableTest.ConcreteTable().queryAll(null);
        Assert.assertNotNull(cursor);
        try
        { Assert.assertTrue(Table.exists(cursor)); }
        finally { cursor.close(); }
    }

    @Test() public void nullCursorMakeFromFirstReturnsNull()
    {
        Assert.assertNull(
            new TableTest.ConcreteTable().makeFromFirst(
                null));
    }

    @Test() public void getContentValuesForInsertSucceeds()
    {
        // noinspection ConstantConditions
        Assert.assertNotNull(
            new TableTest.ConcreteTable()
                .getContentValuesForInsert(null));
    }
    // endregion

    @After() public void tearDown()
    { new DatabaseTest().cleanFilesystem(); }
}