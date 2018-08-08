package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * android.os.Bundle
 *
 * org.junit.Assert
 * org.junit.Test
 *
 * org.wheatgenetics.coordinate.model.Cells
 * org.wheatgenetics.coordinate.model.DisplayTemplateModel
 * org.wheatgenetics.coordinate.model.TemplateType
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
public class DisplayTemplateModelTest extends java.lang.Object
{
    // region Constructor Tests
    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void invalidCodeFourthConstructorFails()
    {
        new org.wheatgenetics.coordinate.model.DisplayTemplateModel(
            /* id                           => */5,
            /* title                        => */"testTitle",
            /* code                         => */3,
            /* rows                         => */5,
            /* cols                         => */2,
            /* generatedExcludedCellsAmount => */0,
            /* excludedCells                => */null,
            /* excludedRows                 => */null,
            /* excludedCols                 => */null,
            /* colNumbering                 => */1,
            /* rowNumbering                 => */0,
            /* entryLabel                   => */null,
            /* timestamp                    => */0);
    }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void invalidColNumberingFourthConstructorFails()
    {
        new org.wheatgenetics.coordinate.model.DisplayTemplateModel(
            /* id                           => */5,
            /* title                        => */"testTitle",
            /* code                         => */1,
            /* rows                         => */5,
            /* cols                         => */2,
            /* generatedExcludedCellsAmount => */0,
            /* excludedCells                => */null,
            /* excludedRows                 => */null,
            /* excludedCols                 => */null,
            /* colNumbering                 => */3,
            /* rowNumbering                 => */0,
            /* entryLabel                   => */null,
            /* timestamp                    => */0);
    }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void invalidRowNumberingFourthConstructorFails()
    {
        new org.wheatgenetics.coordinate.model.DisplayTemplateModel(
            /* id                           => */5,
            /* title                        => */"testTitle",
            /* code                         => */1,
            /* rows                         => */5,
            /* cols                         => */2,
            /* generatedExcludedCellsAmount => */0,
            /* excludedCells                => */null,
            /* excludedRows                 => */null,
            /* excludedCols                 => */null,
            /* colNumbering                 => */1,
            /* rowNumbering                 => */56,
            /* entryLabel                   => */null,
            /* timestamp                    => */0);
    }
    // endregion

    // region Overridden Method Tests
    @org.junit.Test() public void toStringSucceeds()
    {
        final java.lang.String expectedString =
            "DisplayTemplateModel [id: 03, title=testTitle, type=1, rows=5, cols=2, generatedExcl" +
            "udedCellsAmount=0, colNumbering=true, rowNumbering=false, entryLabel=null, stamp=0, " +
            "excludedCells=null, excludedRows=null, excludedCols=null";
        final org.wheatgenetics.coordinate.model.DisplayTemplateModel displayTemplateModel =
            new org.wheatgenetics.coordinate.model.DisplayTemplateModel(
                /* id                           => */3,
                /* title                        => */"testTitle",
                /* code                         => */1,
                /* rows                         => */5,
                /* cols                         => */2,
                /* generatedExcludedCellsAmount => */0,
                /* excludedCells                => */null,
                /* excludedRows                 => */null,
                /* excludedCols                 => */null,
                /* colNumbering                 => */1,
                /* rowNumbering                 => */0,
                /* entryLabel                   => */null,
                /* timestamp                    => */0);
        org.junit.Assert.assertTrue(expectedString.equals(displayTemplateModel.toString()));
    }

    @org.junit.Test() public void equalsAndHashCodeWork()
    {
        final long             id    = 44                                                       ;
        final java.lang.String title = "testTitle"                                              ;
        final int              code  = 1, rows = 5, cols = 2, colNumbering = 1, rowNumbering = 0;
        final long             timestamp = 0                                                    ;

        final org.wheatgenetics.coordinate.model.DisplayTemplateModel
            firstDisplayTemplateModel = new org.wheatgenetics.coordinate.model.DisplayTemplateModel(
                /* id                           => */ id   ,
                /* title                        => */ title,
                /* code                         => */ code ,
                /* rows                         => */ rows ,
                /* cols                         => */ cols ,
                /* generatedExcludedCellsAmount => */0,
                /* excludedCells                => */null,
                /* excludedRows                 => */null,
                /* excludedCols                 => */null,
                /* colNumbering                 => */ colNumbering,
                /* rowNumbering                 => */ rowNumbering,
                /* entryLabel                   => */null,
                /* timestamp                    => */ timestamp),
            secondDisplayTemplateModel =
                new org.wheatgenetics.coordinate.model.DisplayTemplateModel(
                    /* id                           => */ id   ,
                    /* title                        => */ title,
                    /* code                         => */ code ,
                    /* rows                         => */ rows ,
                    /* cols                         => */ cols ,
                    /* generatedExcludedCellsAmount => */0,
                    /* excludedCells                => */null,
                    /* excludedRows                 => */null,
                    /* excludedCols                 => */null,
                    /* colNumbering                 => */ colNumbering,
                    /* rowNumbering                 => */ rowNumbering,
                    /* entryLabel                   => */null,
                    /* timestamp                    => */ timestamp);
        org.junit.Assert.assertTrue  (firstDisplayTemplateModel.equals(secondDisplayTemplateModel));
        org.junit.Assert.assertEquals(
            firstDisplayTemplateModel.hashCode(), secondDisplayTemplateModel.hashCode());

        secondDisplayTemplateModel.setTitle("different");
        org.junit.Assert.assertFalse(firstDisplayTemplateModel.equals(secondDisplayTemplateModel));
        org.junit.Assert.assertNotEquals(
            firstDisplayTemplateModel.hashCode(), secondDisplayTemplateModel.hashCode());
        secondDisplayTemplateModel.setTitle(title);
        org.junit.Assert.assertTrue  (firstDisplayTemplateModel.equals(secondDisplayTemplateModel));
        org.junit.Assert.assertEquals(
            firstDisplayTemplateModel.hashCode(), secondDisplayTemplateModel.hashCode());

        secondDisplayTemplateModel.setType(org.wheatgenetics.coordinate.model.TemplateType.SEED);
        org.junit.Assert.assertFalse(firstDisplayTemplateModel.equals(secondDisplayTemplateModel));
        org.junit.Assert.assertNotEquals(
            firstDisplayTemplateModel.hashCode(), secondDisplayTemplateModel.hashCode());
        secondDisplayTemplateModel.setType(org.wheatgenetics.coordinate.model.TemplateType.DNA);
        org.junit.Assert.assertTrue  (firstDisplayTemplateModel.equals(secondDisplayTemplateModel));
        org.junit.Assert.assertEquals(
            firstDisplayTemplateModel.hashCode(), secondDisplayTemplateModel.hashCode());

        secondDisplayTemplateModel.setColNumbering(false);
        org.junit.Assert.assertFalse(firstDisplayTemplateModel.equals(secondDisplayTemplateModel));
        org.junit.Assert.assertNotEquals(
            firstDisplayTemplateModel.hashCode(), secondDisplayTemplateModel.hashCode());
        secondDisplayTemplateModel.setColNumbering(true);
        org.junit.Assert.assertTrue  (firstDisplayTemplateModel.equals(secondDisplayTemplateModel));
        org.junit.Assert.assertEquals(
            firstDisplayTemplateModel.hashCode(), secondDisplayTemplateModel.hashCode());

        secondDisplayTemplateModel.setRowNumbering(true);
        org.junit.Assert.assertFalse(firstDisplayTemplateModel.equals(secondDisplayTemplateModel));
        org.junit.Assert.assertNotEquals(
            firstDisplayTemplateModel.hashCode(), secondDisplayTemplateModel.hashCode());
        secondDisplayTemplateModel.setRowNumbering(false);
        org.junit.Assert.assertTrue  (firstDisplayTemplateModel.equals(secondDisplayTemplateModel));
        org.junit.Assert.assertEquals(
            firstDisplayTemplateModel.hashCode(), secondDisplayTemplateModel.hashCode());

        {
            final int row = 5;
            firstDisplayTemplateModel.addExcludedRow(row);
            org.junit.Assert.assertFalse(
                firstDisplayTemplateModel.equals(secondDisplayTemplateModel));
            org.junit.Assert.assertNotEquals(
                firstDisplayTemplateModel.hashCode(), secondDisplayTemplateModel.hashCode());

            secondDisplayTemplateModel.addExcludedRow(row);
            org.junit.Assert.assertTrue(
                firstDisplayTemplateModel.equals(secondDisplayTemplateModel));
            org.junit.Assert.assertEquals(
                firstDisplayTemplateModel.hashCode(), secondDisplayTemplateModel.hashCode());
        }

        final int col = 1;
        firstDisplayTemplateModel.addExcludedCol(col);
        org.junit.Assert.assertFalse(firstDisplayTemplateModel.equals(secondDisplayTemplateModel));
        org.junit.Assert.assertNotEquals(
            firstDisplayTemplateModel.hashCode(), secondDisplayTemplateModel.hashCode());

        secondDisplayTemplateModel.addExcludedCol(col);
        org.junit.Assert.assertTrue  (firstDisplayTemplateModel.equals(secondDisplayTemplateModel));
        org.junit.Assert.assertEquals(
            firstDisplayTemplateModel.hashCode(), secondDisplayTemplateModel.hashCode());
    }

    @org.junit.Test() public void cloneSucceeds()
    {
        final org.wheatgenetics.coordinate.model.DisplayTemplateModel displayTemplateModel =
            new org.wheatgenetics.coordinate.model.DisplayTemplateModel(
                /* id                           => */12,
                /* title                        => */"testTitle",
                /* code                         => */1,
                /* rows                         => */5,
                /* cols                         => */2,
                /* generatedExcludedCellsAmount => */1,
                /* excludedCells                => */null,
                /* excludedRows                 => */null,
                /* excludedCols                 => */null,
                /* colNumbering                 => */1,
                /* rowNumbering                 => */0,
                /* entryLabel                   => */null,
                /* timestamp                    => */0);
        displayTemplateModel.setEntryLabel("abc");

        final org.wheatgenetics.coordinate.model.DisplayTemplateModel clonedDisplayTemplateModel =
            (org.wheatgenetics.coordinate.model.DisplayTemplateModel) displayTemplateModel.clone();
        org.junit.Assert.assertTrue(displayTemplateModel.equals(clonedDisplayTemplateModel));
    }
    // endregion

    // region Package Method Tests
    @org.junit.Test() public void isExcludedRowSucceeds()
    {
        final org.wheatgenetics.coordinate.model.DisplayTemplateModel displayTemplateModel =
            new org.wheatgenetics.coordinate.model.DisplayTemplateModel(
                /* id                           => */10,
                /* title                        => */"testTitle",
                /* code                         => */1,
                /* rows                         => */5,
                /* cols                         => */2,
                /* generatedExcludedCellsAmount => */0,
                /* excludedCells                => */null,
                /* excludedRows                 => */null,
                /* excludedCols                 => */null,
                /* colNumbering                 => */1,
                /* rowNumbering                 => */0,
                /* entryLabel                   => */null,
                /* timestamp                    => */880);
        org.junit.Assert.assertFalse(displayTemplateModel.isExcludedRow(1));
    }

    @org.junit.Test() public void isExcludedColSucceeds()
    {
        final org.wheatgenetics.coordinate.model.DisplayTemplateModel displayTemplateModel =
            new org.wheatgenetics.coordinate.model.DisplayTemplateModel(
                /* id                           => */10,
                /* title                        => */"testTitle",
                /* code                         => */1,
                /* rows                         => */5,
                /* cols                         => */2,
                /* generatedExcludedCellsAmount => */0,
                /* excludedCells                => */null,
                /* excludedRows                 => */null,
                /* excludedCols                 => */null,
                /* colNumbering                 => */1,
                /* rowNumbering                 => */0,
                /* entryLabel                   => */null,
                /* timestamp                    => */880);
        org.junit.Assert.assertFalse(displayTemplateModel.isExcludedCol(1));
    }
    // endregion

    // region Public Method Tests
    @org.junit.Test() public void getExcludedCellsAsJsonSucceeds()
    {
        final org.wheatgenetics.coordinate.model.DisplayTemplateModel displayTemplateModel =
            new org.wheatgenetics.coordinate.model.DisplayTemplateModel(
                /* id                           => */10,
                /* title                        => */"testTitle",
                /* code                         => */1,
                /* rows                         => */5,
                /* cols                         => */2,
                /* generatedExcludedCellsAmount => */0,
                /* excludedCells                => */null,
                /* excludedRows                 => */null,
                /* excludedCols                 => */null,
                /* colNumbering                 => */1,
                /* rowNumbering                 => */0,
                /* entryLabel                   => */null,
                /* timestamp                    => */880);
        org.junit.Assert.assertNull(displayTemplateModel.getExcludedCellsAsJson());
    }

    // region excludedRows Public Method Tests
    @org.junit.Test() public void addExcludedRowAndClearExcludedRowsSucceed()
    {
        final org.wheatgenetics.coordinate.model.DisplayTemplateModel displayTemplateModel =
            new org.wheatgenetics.coordinate.model.DisplayTemplateModel(
                /* id                           => */10,
                /* title                        => */"testTitle",
                /* code                         => */1,
                /* rows                         => */5,
                /* cols                         => */2,
                /* generatedExcludedCellsAmount => */0,
                /* excludedCells                => */null,
                /* excludedRows                 => */null,
                /* excludedCols                 => */null,
                /* colNumbering                 => */1,
                /* rowNumbering                 => */0,
                /* entryLabel                   => */null,
                /* timestamp                    => */880);
        final int row = 1;
        org.junit.Assert.assertFalse(displayTemplateModel.isExcludedRow(row));

        displayTemplateModel.addExcludedRow(row);
        org.junit.Assert.assertTrue(displayTemplateModel.isExcludedRow(row));

        displayTemplateModel.clearExcludedRows();
        org.junit.Assert.assertFalse(displayTemplateModel.isExcludedRow(row));
    }

    @org.junit.Test() public void getExcludedRowsAsJsonSucceeds()
    {
        final org.wheatgenetics.coordinate.model.DisplayTemplateModel displayTemplateModel =
            new org.wheatgenetics.coordinate.model.DisplayTemplateModel(
                /* id                           => */10,
                /* title                        => */"testTitle",
                /* code                         => */1,
                /* rows                         => */5,
                /* cols                         => */2,
                /* generatedExcludedCellsAmount => */0,
                /* excludedCells                => */null,
                /* excludedRows                 => */null,
                /* excludedCols                 => */null,
                /* colNumbering                 => */1,
                /* rowNumbering                 => */0,
                /* entryLabel                   => */null,
                /* timestamp                    => */880);
        org.junit.Assert.assertNull(displayTemplateModel.getExcludedRowsAsJson());
    }
    // endregion

    // region excludedCols Public Method Tests
    @org.junit.Test() public void addExcludedColAndClearExcludedColsSucceed()
    {
        final org.wheatgenetics.coordinate.model.DisplayTemplateModel displayTemplateModel =
            new org.wheatgenetics.coordinate.model.DisplayTemplateModel(
                /* id                           => */10,
                /* title                        => */"testTitle",
                /* code                         => */1,
                /* rows                         => */5,
                /* cols                         => */2,
                /* generatedExcludedCellsAmount => */0,
                /* excludedCells                => */null,
                /* excludedRows                 => */null,
                /* excludedCols                 => */null,
                /* colNumbering                 => */1,
                /* rowNumbering                 => */0,
                /* entryLabel                   => */null,
                /* timestamp                    => */880);
        final int col = 1;
        org.junit.Assert.assertFalse(displayTemplateModel.isExcludedCol(col));

        displayTemplateModel.addExcludedCol(col);
        org.junit.Assert.assertTrue(displayTemplateModel.isExcludedCol(col));

        displayTemplateModel.clearExcludedCols();
        org.junit.Assert.assertFalse(displayTemplateModel.isExcludedCol(col));
    }

    @org.junit.Test() public void getExcludedColsAsJsonSucceeds()
    {
        final org.wheatgenetics.coordinate.model.DisplayTemplateModel displayTemplateModel =
            new org.wheatgenetics.coordinate.model.DisplayTemplateModel(
                /* id                           => */10,
                /* title                        => */"testTitle",
                /* code                         => */1,
                /* rows                         => */5,
                /* cols                         => */2,
                /* generatedExcludedCellsAmount => */0,
                /* excludedCells                => */null,
                /* excludedRows                 => */null,
                /* excludedCols                 => */null,
                /* colNumbering                 => */1,
                /* rowNumbering                 => */0,
                /* entryLabel                   => */null,
                /* timestamp                    => */880);
        org.junit.Assert.assertNull(displayTemplateModel.getExcludedColsAsJson());
    }
    // endregion

    // region checkedItems Public Method Tests
    @org.junit.Test() public void rowCheckedItemsSucceeds()
    {
        final org.wheatgenetics.coordinate.model.DisplayTemplateModel displayTemplateModel =
            new org.wheatgenetics.coordinate.model.DisplayTemplateModel(
                /* id                           => */10,
                /* title                        => */"testTitle",
                /* code                         => */1,
                /* rows                         => */5,
                /* cols                         => */2,
                /* generatedExcludedCellsAmount => */0,
                /* excludedCells                => */null,
                /* excludedRows                 => */null,
                /* excludedCols                 => */null,
                /* colNumbering                 => */1,
                /* rowNumbering                 => */0,
                /* entryLabel                   => */null,
                /* timestamp                    => */880);
        org.junit.Assert.assertArrayEquals(new boolean[]{false, false, false, false, false},
            displayTemplateModel.rowCheckedItems());

        displayTemplateModel.addExcludedRow(4);
        org.junit.Assert.assertArrayEquals(new boolean[]{false, false, false, true, false},
            displayTemplateModel.rowCheckedItems());
    }

    @org.junit.Test() public void colCheckedItemsSucceeds()
    {
        final org.wheatgenetics.coordinate.model.DisplayTemplateModel displayTemplateModel =
            new org.wheatgenetics.coordinate.model.DisplayTemplateModel(
                /* id                           => */10,
                /* title                        => */"testTitle",
                /* code                         => */1,
                /* rows                         => */5,
                /* cols                         => */2,
                /* generatedExcludedCellsAmount => */0,
                /* excludedCells                => */null,
                /* excludedRows                 => */null,
                /* excludedCols                 => */null,
                /* colNumbering                 => */1,
                /* rowNumbering                 => */0,
                /* entryLabel                   => */null,
                /* timestamp                    => */880);
        org.junit.Assert.assertArrayEquals(
            new boolean[]{false, false}, displayTemplateModel.colCheckedItems());

        displayTemplateModel.addExcludedCol(1);
        org.junit.Assert.assertArrayEquals(
            new boolean[]{true, false}, displayTemplateModel.colCheckedItems());
    }
    // endregion

    @org.junit.Test() public void getStateWorks()
    {
        final int                                                     rows = 5, cols = 2;
        final org.wheatgenetics.coordinate.model.DisplayTemplateModel displayTemplateModel =
            new org.wheatgenetics.coordinate.model.DisplayTemplateModel(
                /* id                           => */10,
                /* title                        => */"testTitle",
                /* code                         => */1,
                /* rows                         => */ rows,
                /* cols                         => */ cols,
                /* generatedExcludedCellsAmount => */0,
                /* excludedCells                => */null,
                /* excludedRows                 => */null,
                /* excludedCols                 => */null,
                /* colNumbering                 => */1,
                /* rowNumbering                 => */0,
                /* entryLabel                   => */null,
                /* timestamp                    => */880);
        android.os.Bundle state = displayTemplateModel.getState();
        org.junit.Assert.assertNotNull(state);

        org.junit.Assert.assertEquals(rows, state.getInt("rows"));
        org.junit.Assert.assertEquals(cols, state.getInt("cols"));

        org.junit.Assert.assertEquals(null, state.getString("excludedCells"));

        org.junit.Assert.assertEquals(null, state.getString("excludedRows"));
        org.junit.Assert.assertEquals(null, state.getString("excludedCols"));

        org.junit.Assert.assertTrue (state.getBoolean("colNumbering"));
        org.junit.Assert.assertFalse(state.getBoolean("rowNumbering"));


        {
            final android.os.Bundle excludedCellsBundle = new android.os.Bundle();
            {
                final org.wheatgenetics.coordinate.model.Cells excludedCells =
                    new org.wheatgenetics.coordinate.model.Cells(rows, cols);
                excludedCells.add(1,1);
                excludedCellsBundle.putString("excludedCells", excludedCells.json());
            }
            displayTemplateModel.setExcludedCells(excludedCellsBundle);
        }
        displayTemplateModel.addExcludedRow(1); displayTemplateModel.addExcludedCol(1);
        state = displayTemplateModel.getState();
        org.junit.Assert.assertNotNull(state);

        org.junit.Assert.assertEquals("[{\"row\":1,\"col\":1}]",
            state.getString("excludedCells"));

        org.junit.Assert.assertEquals("[1]", state.getString("excludedRows"));
        org.junit.Assert.assertEquals("[1]", state.getString("excludedCols"));
    }

    @org.junit.Test() public void setExcludedCellsWorks()
    {
        final org.wheatgenetics.coordinate.model.DisplayTemplateModel displayTemplateModel =
            new org.wheatgenetics.coordinate.model.DisplayTemplateModel(
                /* id                           => */10,
                /* title                        => */"testTitle",
                /* code                         => */1,
                /* rows                         => */5,
                /* cols                         => */2,
                /* generatedExcludedCellsAmount => */0,
                /* excludedCells                => */null,
                /* excludedRows                 => */null,
                /* excludedCols                 => */null,
                /* colNumbering                 => */1,
                /* rowNumbering                 => */0,
                /* entryLabel                   => */null,
                /* timestamp                    => */880);
        final java.lang.String json = "[{\"row\":1,\"col\":1}]";
        {
            final android.os.Bundle excludedCellsBundle = new android.os.Bundle();
            excludedCellsBundle.putString("excludedCells", json);
            displayTemplateModel.setExcludedCells(excludedCellsBundle);
        }
        org.junit.Assert.assertTrue(json.equals(displayTemplateModel.getExcludedCellsAsJson()));
    }
    // endregion
}