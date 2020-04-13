package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * android.content.res.Resources.NotFoundException
 *
 * androidx.annotation.IntRange
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 * androidx.annotation.PluralsRes
 * androidx.annotation.StringRes
 *
 * org.junit.Assert
 * org.junit.Test
 *
 * org.wheatgenetics.coordinate.preference.Utils.Direction
 *
 * org.wheatgenetics.coordinate.StringGetter
 *
 * org.wheatgenetics.coordinate.model.Cells
 * org.wheatgenetics.coordinate.model.EntryModel
 * org.wheatgenetics.coordinate.model.EntryModels
 * org.wheatgenetics.coordinate.model.EntryModels.FilledHandler
 * org.wheatgenetics.coordinate.model.EntryModels.Processor
 * org.wheatgenetics.coordinate.model.ExcludedEntryModel
 * org.wheatgenetics.coordinate.model.IncludedEntryModel
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"}) public class EntryModelsTest
extends java.lang.Object implements org.wheatgenetics.coordinate.StringGetter
{
    private static class FilledHandler extends java.lang.Object
    implements org.wheatgenetics.coordinate.model.EntryModels.FilledHandler
    {
        @java.lang.Override public void handleFilledGrid    () {}
        @java.lang.Override public void handleFilledRowOrCol() {}
    }

    // region org.wheatgenetics.coordinate.StringGetter Overridden Methods
    @java.lang.Override @androidx.annotation.Nullable public java.lang.String get(
    @androidx.annotation.StringRes final int resId)
    {
        // noinspection SwitchStatementWithTooFewBranches
        switch (resId) { default: org.junit.Assert.fail(); return null; }
    }

    @java.lang.Override @androidx.annotation.NonNull public java.lang.String getQuantity(
    @androidx.annotation.PluralsRes         final int                 resId     ,
    @androidx.annotation.IntRange(from = 0) final int                 quantity  ,
    @androidx.annotation.Nullable           final java.lang.Object... formatArgs)
    throws android.content.res.Resources.NotFoundException { org.junit.Assert.fail(); return null; }
    // endregion

    // region Constructor Tests
    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void badGridIdConstructorFails()
    { new org.wheatgenetics.coordinate.model.EntryModels(0,5,5,this); }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void badRowsConstructorFails()
    { new org.wheatgenetics.coordinate.model.EntryModels(1,0,5,this); }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void badColsConstructorFails()
    { new org.wheatgenetics.coordinate.model.EntryModels(1,5,0,this); }
    // endregion

    // region Package Method Tests
    // region makeExcludedEntry() Package Method Tests
    @org.junit.Test(expected = java.lang.ArrayIndexOutOfBoundsException.class)
    public void makeExcludedEntryFails()
    {
        new org.wheatgenetics.coordinate.model.EntryModels(1,5,5,this)
            .makeExcludedEntry(50,1);
    }

    @org.junit.Test() public void makeExcludedEntrySucceeds()
    {
        final org.wheatgenetics.coordinate.model.ExcludedEntryModel excludedEntryModel;
        final int                                                   row = 3, col = 3  ;
        {
            final long gridId = 1;
            {
                final org.wheatgenetics.coordinate.model.EntryModels entryModels =
                    new org.wheatgenetics.coordinate.model.EntryModels(
                        gridId,5,5,this);
                entryModels.makeExcludedEntry(row, col);
                org.junit.Assert.assertTrue(entryModels.get(row, col)
                    instanceof org.wheatgenetics.coordinate.model.ExcludedEntryModel);
                excludedEntryModel = (org.wheatgenetics.coordinate.model.ExcludedEntryModel)
                    entryModels.get(row, col);
            }
            org.junit.Assert.assertEquals(gridId, excludedEntryModel.getGridId());
        }
        org.junit.Assert.assertEquals(row, excludedEntryModel.getRow());
        org.junit.Assert.assertEquals(col, excludedEntryModel.getCol());
    }
    // endregion

    // region makeIncludedEntry() Package Method Tests
    @org.junit.Test(expected = java.lang.ArrayIndexOutOfBoundsException.class)
    public void makeIncludedEntryFails()
    {
        new org.wheatgenetics.coordinate.model.EntryModels(1,5,5,this)
            .makeIncludedEntry(5,10);
    }

    @org.junit.Test() public void makeIncludedEntrySucceeds()
    {
        final org.wheatgenetics.coordinate.model.IncludedEntryModel includedEntryModel;
        final int                                                   row = 3, col = 3  ;
        {
            final long gridId = 1;
            {
                final org.wheatgenetics.coordinate.model.EntryModels entryModels =
                    new org.wheatgenetics.coordinate.model.EntryModels(
                        gridId,5,5,this);
                entryModels.makeIncludedEntry(row, col);
                org.junit.Assert.assertTrue(entryModels.get(row, col)
                    instanceof org.wheatgenetics.coordinate.model.IncludedEntryModel);
                includedEntryModel = (org.wheatgenetics.coordinate.model.IncludedEntryModel)
                    entryModels.get(row, col);
            }
            org.junit.Assert.assertEquals(gridId, includedEntryModel.getGridId());
        }
        org.junit.Assert.assertEquals(row, includedEntryModel.getRow());
        org.junit.Assert.assertEquals(col, includedEntryModel.getCol());
    }
    // endregion

    // region excludedCells() Package Method Tests
    @org.junit.Test() public void emptyExcludedCellsWorks()
    {
        final org.wheatgenetics.coordinate.model.Cells       expectedCells;
        final org.wheatgenetics.coordinate.model.EntryModels entryModels  ;
        {
            final int rows = 5, cols = 5;
            expectedCells = new org.wheatgenetics.coordinate.model.Cells(rows, cols,this);
            entryModels   = new org.wheatgenetics.coordinate.model.EntryModels(
                1, rows, cols,this);
        }
        org.junit.Assert.assertEquals(expectedCells, entryModels.excludedCells());
    }

    @org.junit.Test() public void oneCellExcludedCellsWorks()
    {
        final org.wheatgenetics.coordinate.model.Cells       expectedCells;
        final org.wheatgenetics.coordinate.model.EntryModels entryModels  ;
        {
            final int rows = 5, cols = 5, excludedRow = 3, excludedCol = 3;

            expectedCells = new org.wheatgenetics.coordinate.model.Cells(rows, cols,this);
            expectedCells.add(excludedRow, excludedCol);

            entryModels = new org.wheatgenetics.coordinate.model.EntryModels(
                1, rows, cols,this);
            for (int row = 1; row <= rows; row++) for (int col = 1; col <= cols; col++)
                if (excludedRow == row && excludedCol == col)
                    entryModels.makeExcludedEntry(row, col);
                else
                    entryModels.makeIncludedEntry(row, col);
        }
        org.junit.Assert.assertEquals(expectedCells, entryModels.excludedCells());
    }
    // endregion

    // region next() Package Method Tests
    @org.junit.Test() public void allIncludedNextSucceeds()
    {
        final long                                           gridId = 1          ;
        final int                                            rows   = 5, cols = 5;
        final org.wheatgenetics.coordinate.model.EntryModels entryModels =
            new org.wheatgenetics.coordinate.model.EntryModels(gridId, rows, cols,this);
        for (int row = 1; row <= rows; row++) for (int col = 1; col <= cols; col++)
            entryModels.makeIncludedEntry(row, col);

        final org.wheatgenetics.coordinate.model.EntryModelsTest.FilledHandler filledHandler =
            new org.wheatgenetics.coordinate.model.EntryModelsTest.FilledHandler();


        org.junit.Assert.assertNull(entryModels.next(null,
            org.wheatgenetics.coordinate.preference.Utils.Direction.DOWN_THEN_ACROSS,
            filledHandler                                                           ));
        org.junit.Assert.assertNull(entryModels.next(null,
            org.wheatgenetics.coordinate.preference.Utils.Direction.ACROSS_THEN_DOWN,
            filledHandler                                                           ));


        org.wheatgenetics.coordinate.model.IncludedEntryModel activeIncludedEntryModel =
            new org.wheatgenetics.coordinate.model.IncludedEntryModel(gridId,1,1);
        org.wheatgenetics.coordinate.model.IncludedEntryModel nextIncludedEntryModel =
            entryModels.next(activeIncludedEntryModel,
                org.wheatgenetics.coordinate.preference.Utils.Direction.DOWN_THEN_ACROSS,
                filledHandler                                                           );
        org.junit.Assert.assertNotNull(nextIncludedEntryModel);
        org.junit.Assert.assertEquals (2, nextIncludedEntryModel.getRow());
        org.junit.Assert.assertEquals (1, nextIncludedEntryModel.getCol());

        nextIncludedEntryModel = entryModels.next(activeIncludedEntryModel,
            org.wheatgenetics.coordinate.preference.Utils.Direction.ACROSS_THEN_DOWN,
            filledHandler                                                           );
        org.junit.Assert.assertNotNull(nextIncludedEntryModel);
        org.junit.Assert.assertEquals (1, nextIncludedEntryModel.getRow());
        org.junit.Assert.assertEquals (2, nextIncludedEntryModel.getCol());


        activeIncludedEntryModel =
            new org.wheatgenetics.coordinate.model.IncludedEntryModel(gridId, rows,1);
        nextIncludedEntryModel = entryModels.next(activeIncludedEntryModel,
            org.wheatgenetics.coordinate.preference.Utils.Direction.DOWN_THEN_ACROSS,
            filledHandler                                                           );
        org.junit.Assert.assertNotNull(nextIncludedEntryModel);
        org.junit.Assert.assertEquals (1, nextIncludedEntryModel.getRow());
        org.junit.Assert.assertEquals (2, nextIncludedEntryModel.getCol());

        activeIncludedEntryModel =
            new org.wheatgenetics.coordinate.model.IncludedEntryModel(gridId,1, cols);
        nextIncludedEntryModel = entryModels.next(activeIncludedEntryModel,
            org.wheatgenetics.coordinate.preference.Utils.Direction.ACROSS_THEN_DOWN,
            filledHandler                                                           );
        org.junit.Assert.assertNotNull(nextIncludedEntryModel);
        org.junit.Assert.assertEquals (2, nextIncludedEntryModel.getRow());
        org.junit.Assert.assertEquals (1, nextIncludedEntryModel.getCol());


        activeIncludedEntryModel =
            new org.wheatgenetics.coordinate.model.IncludedEntryModel(gridId,999,1);
        nextIncludedEntryModel = entryModels.next(activeIncludedEntryModel,
            org.wheatgenetics.coordinate.preference.Utils.Direction.DOWN_THEN_ACROSS,
            filledHandler                                                           );
        org.junit.Assert.assertNotNull(nextIncludedEntryModel);
        org.junit.Assert.assertEquals (1, nextIncludedEntryModel.getRow());
        org.junit.Assert.assertEquals (2, nextIncludedEntryModel.getCol());

        activeIncludedEntryModel =
            new org.wheatgenetics.coordinate.model.IncludedEntryModel(gridId,1,999);
        nextIncludedEntryModel = entryModels.next(activeIncludedEntryModel,
            org.wheatgenetics.coordinate.preference.Utils.Direction.ACROSS_THEN_DOWN,
            filledHandler                                                           );
        org.junit.Assert.assertNotNull(nextIncludedEntryModel);
        org.junit.Assert.assertEquals (2, nextIncludedEntryModel.getRow());
        org.junit.Assert.assertEquals (1, nextIncludedEntryModel.getCol());


        activeIncludedEntryModel =
            new org.wheatgenetics.coordinate.model.IncludedEntryModel(gridId,1, cols);
        nextIncludedEntryModel = entryModels.next(activeIncludedEntryModel,
            org.wheatgenetics.coordinate.preference.Utils.Direction.DOWN_THEN_ACROSS,
            filledHandler                                                           );
        org.junit.Assert.assertNotNull(nextIncludedEntryModel);
        org.junit.Assert.assertEquals (2, nextIncludedEntryModel.getRow());
        org.junit.Assert.assertEquals (cols, nextIncludedEntryModel.getCol());

        activeIncludedEntryModel =
            new org.wheatgenetics.coordinate.model.IncludedEntryModel(gridId, rows,1);
        nextIncludedEntryModel = entryModels.next(activeIncludedEntryModel,
            org.wheatgenetics.coordinate.preference.Utils.Direction.ACROSS_THEN_DOWN,
            filledHandler                                                           );
        org.junit.Assert.assertNotNull(nextIncludedEntryModel               );
        org.junit.Assert.assertEquals (rows, nextIncludedEntryModel.getRow());
        org.junit.Assert.assertEquals (2, nextIncludedEntryModel.getCol());


        activeIncludedEntryModel =
            new org.wheatgenetics.coordinate.model.IncludedEntryModel(gridId,1,999);
        nextIncludedEntryModel = entryModels.next(activeIncludedEntryModel,
            org.wheatgenetics.coordinate.preference.Utils.Direction.DOWN_THEN_ACROSS,
            filledHandler                                                           );
        org.junit.Assert.assertNotNull(nextIncludedEntryModel);
        org.junit.Assert.assertEquals (2, nextIncludedEntryModel.getRow());
        org.junit.Assert.assertEquals (cols, nextIncludedEntryModel.getCol());

        activeIncludedEntryModel =
            new org.wheatgenetics.coordinate.model.IncludedEntryModel(gridId,999,1);
        nextIncludedEntryModel = entryModels.next(activeIncludedEntryModel,
            org.wheatgenetics.coordinate.preference.Utils.Direction.ACROSS_THEN_DOWN,
            filledHandler                                                           );
        org.junit.Assert.assertNotNull(nextIncludedEntryModel               );
        org.junit.Assert.assertEquals (rows, nextIncludedEntryModel.getRow());
        org.junit.Assert.assertEquals (2, nextIncludedEntryModel.getCol());


        activeIncludedEntryModel =
            new org.wheatgenetics.coordinate.model.IncludedEntryModel(gridId, rows, cols);
        nextIncludedEntryModel = entryModels.next(activeIncludedEntryModel,
            org.wheatgenetics.coordinate.preference.Utils.Direction.DOWN_THEN_ACROSS,
            filledHandler                                                           );
        org.junit.Assert.assertNull(nextIncludedEntryModel);

        nextIncludedEntryModel = entryModels.next(activeIncludedEntryModel,
            org.wheatgenetics.coordinate.preference.Utils.Direction.ACROSS_THEN_DOWN,
            filledHandler                                                           );
        org.junit.Assert.assertNull(nextIncludedEntryModel);


        activeIncludedEntryModel =
            new org.wheatgenetics.coordinate.model.IncludedEntryModel(gridId,999,999);
        nextIncludedEntryModel = entryModels.next(activeIncludedEntryModel,
            org.wheatgenetics.coordinate.preference.Utils.Direction.DOWN_THEN_ACROSS,
            filledHandler                                                             );
        org.junit.Assert.assertNull(nextIncludedEntryModel);

        nextIncludedEntryModel = entryModels.next(activeIncludedEntryModel,
            org.wheatgenetics.coordinate.preference.Utils.Direction.ACROSS_THEN_DOWN,
            filledHandler                                                           );
        org.junit.Assert.assertNull(nextIncludedEntryModel);
    }

    @org.junit.Test() public void someExcludedNextSucceeds()
    {
        final long                                           gridId = 1 ;
        final org.wheatgenetics.coordinate.model.EntryModels entryModels;
        {
            final int rows = 5, cols = 5;
            entryModels = new org.wheatgenetics.coordinate.model.EntryModels(
                gridId, rows, cols,this);
            for (int row = 1; row <= rows; row++) for (int col = 1; col <= cols; col++)
                if (2 == row && 2 == col)
                    entryModels.makeExcludedEntry(row, col);
                else
                    entryModels.makeIncludedEntry(row, col);
        }

        final org.wheatgenetics.coordinate.model.EntryModelsTest.FilledHandler filledHandler =
            new org.wheatgenetics.coordinate.model.EntryModelsTest.FilledHandler();


        org.wheatgenetics.coordinate.model.IncludedEntryModel activeIncludedEntryModel =
            new org.wheatgenetics.coordinate.model.IncludedEntryModel(gridId,1,2);
        org.wheatgenetics.coordinate.model.IncludedEntryModel nextIncludedEntryModel =
            entryModels.next(activeIncludedEntryModel,
                org.wheatgenetics.coordinate.preference.Utils.Direction.DOWN_THEN_ACROSS,
                filledHandler                                                           );
        org.junit.Assert.assertNotNull(nextIncludedEntryModel);
        org.junit.Assert.assertEquals (3, nextIncludedEntryModel.getRow());
        org.junit.Assert.assertEquals (2, nextIncludedEntryModel.getCol());

        activeIncludedEntryModel =
            new org.wheatgenetics.coordinate.model.IncludedEntryModel(gridId,2,1);
        nextIncludedEntryModel = entryModels.next(activeIncludedEntryModel,
            org.wheatgenetics.coordinate.preference.Utils.Direction.ACROSS_THEN_DOWN,
            filledHandler                                                           );
        org.junit.Assert.assertNotNull(nextIncludedEntryModel);
        org.junit.Assert.assertEquals (2, nextIncludedEntryModel.getRow());
        org.junit.Assert.assertEquals (3, nextIncludedEntryModel.getCol());


        activeIncludedEntryModel =
            new org.wheatgenetics.coordinate.model.IncludedEntryModel(gridId,1,2);
        entryModels.makeExcludedEntry(3,2);    // Two excluded entries next to each other.
        nextIncludedEntryModel = entryModels.next(activeIncludedEntryModel,
            org.wheatgenetics.coordinate.preference.Utils.Direction.DOWN_THEN_ACROSS,
            filledHandler                                                           );
        org.junit.Assert.assertNotNull(nextIncludedEntryModel);
        org.junit.Assert.assertEquals (4, nextIncludedEntryModel.getRow());
        org.junit.Assert.assertEquals (2, nextIncludedEntryModel.getCol());

        activeIncludedEntryModel =
            new org.wheatgenetics.coordinate.model.IncludedEntryModel(gridId,2,1);
        entryModels.makeExcludedEntry(2,3);    // Two excluded entries next to each other.
        nextIncludedEntryModel = entryModels.next(activeIncludedEntryModel,
            org.wheatgenetics.coordinate.preference.Utils.Direction.ACROSS_THEN_DOWN,
            filledHandler                                                           );
        org.junit.Assert.assertNotNull(nextIncludedEntryModel);
        org.junit.Assert.assertEquals (2, nextIncludedEntryModel.getRow());
        org.junit.Assert.assertEquals (4, nextIncludedEntryModel.getCol());
    }
    // endregion
    // endregion

    // region Public Method Tests
    @org.junit.Test() public void setSucceeds()
    {
        final long                                           gridId = 1 ;
        final org.wheatgenetics.coordinate.model.EntryModels entryModels;
        {
            final int rows = 5, cols = 5;
            entryModels = new org.wheatgenetics.coordinate.model.EntryModels(
                gridId, rows, cols,this);
            for (int row = 1; row <= rows; row++) for (int col = 1; col <= cols; col++)
                entryModels.makeIncludedEntry(row, col);
        }

        entryModels.set(null);

        final int row = 3, col = 3;
        {
            final org.wheatgenetics.coordinate.model.IncludedEntryModel
                expectedIncludedEntryModel = (org.wheatgenetics.coordinate.model.IncludedEntryModel)
                    entryModels.get(row, col),
                actualIncludedEntryModel = (org.wheatgenetics.coordinate.model.IncludedEntryModel)
                    entryModels.get(row, col);
            org.junit.Assert.assertEquals(expectedIncludedEntryModel, actualIncludedEntryModel);
        }

        final org.wheatgenetics.coordinate.model.IncludedEntryModel unexpectedIncludedEntryModel =
            (org.wheatgenetics.coordinate.model.IncludedEntryModel) entryModels.get(row, col);
        {
            final org.wheatgenetics.coordinate.model.IncludedEntryModel newIncludedEntryModel =
                new org.wheatgenetics.coordinate.model.IncludedEntryModel(gridId, row, col);
            newIncludedEntryModel.setValue("gobbledygook");
            entryModels.set(newIncludedEntryModel);
        }
        {
            final org.wheatgenetics.coordinate.model.IncludedEntryModel actualIncludedEntryModel =
                (org.wheatgenetics.coordinate.model.IncludedEntryModel) entryModels.get(row, col);
            org.junit.Assert.assertNotEquals(unexpectedIncludedEntryModel.getValue(),
                actualIncludedEntryModel.getValue());
            org.junit.Assert.assertEquals("gobbledygook",
                actualIncludedEntryModel.getValue());
        }
        org.junit.Assert.assertNull(unexpectedIncludedEntryModel.getValue());
    }

    @org.junit.Test() public void processAll()
    {
        final org.wheatgenetics.coordinate.model.EntryModels entryModels;
        {
            final int rows = 5, cols = 5;
            entryModels = new org.wheatgenetics.coordinate.model.EntryModels(
                1, rows, cols,this);
            for (int row = 1; row <= rows; row++) for (int col = 1; col <= cols; col++)
                entryModels.makeIncludedEntry(row, col);
        }

        class Processor extends java.lang.Object
        implements org.wheatgenetics.coordinate.model.EntryModels.Processor
        {
            @java.lang.Override
            public void process(final org.wheatgenetics.coordinate.model.EntryModel entryModel)
            { org.junit.Assert.assertNotNull(entryModel); }
        }

        entryModels.processAll(new Processor());
    }
    // endregion
}