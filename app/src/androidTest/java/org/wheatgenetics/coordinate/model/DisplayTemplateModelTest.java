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
            /* code                         => */3,  // invalid
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
            /* colNumbering                 => */3,  // invalid
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
            /* rowNumbering                 => */56,  // invalid
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
        org.junit.Assert.assertEquals(expectedString, displayTemplateModel.toString());
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

        // noinspection SimplifiableJUnitAssertion
        org.junit.Assert.assertTrue  (firstDisplayTemplateModel.equals(secondDisplayTemplateModel));
        org.junit.Assert.assertEquals(
            firstDisplayTemplateModel.hashCode(), secondDisplayTemplateModel.hashCode());

        secondDisplayTemplateModel.setTitle("different");
        // noinspection SimplifiableJUnitAssertion
        org.junit.Assert.assertFalse(firstDisplayTemplateModel.equals(secondDisplayTemplateModel));
        org.junit.Assert.assertNotEquals(
            firstDisplayTemplateModel.hashCode(), secondDisplayTemplateModel.hashCode());
        secondDisplayTemplateModel.setTitle(title);
        // noinspection SimplifiableJUnitAssertion
        org.junit.Assert.assertTrue  (firstDisplayTemplateModel.equals(secondDisplayTemplateModel));
        org.junit.Assert.assertEquals(
            firstDisplayTemplateModel.hashCode(), secondDisplayTemplateModel.hashCode());

        secondDisplayTemplateModel.setType(org.wheatgenetics.coordinate.model.TemplateType.SEED);
        // noinspection SimplifiableJUnitAssertion
        org.junit.Assert.assertFalse(firstDisplayTemplateModel.equals(secondDisplayTemplateModel));
        org.junit.Assert.assertNotEquals(
            firstDisplayTemplateModel.hashCode(), secondDisplayTemplateModel.hashCode());
        secondDisplayTemplateModel.setType(org.wheatgenetics.coordinate.model.TemplateType.DNA);
        // noinspection SimplifiableJUnitAssertion
        org.junit.Assert.assertTrue  (firstDisplayTemplateModel.equals(secondDisplayTemplateModel));
        org.junit.Assert.assertEquals(
            firstDisplayTemplateModel.hashCode(), secondDisplayTemplateModel.hashCode());

        secondDisplayTemplateModel.setColNumbering(false);
        // noinspection SimplifiableJUnitAssertion
        org.junit.Assert.assertFalse(firstDisplayTemplateModel.equals(secondDisplayTemplateModel));
        org.junit.Assert.assertNotEquals(
            firstDisplayTemplateModel.hashCode(), secondDisplayTemplateModel.hashCode());
        secondDisplayTemplateModel.setColNumbering(true);
        // noinspection SimplifiableJUnitAssertion
        org.junit.Assert.assertTrue  (firstDisplayTemplateModel.equals(secondDisplayTemplateModel));
        org.junit.Assert.assertEquals(
            firstDisplayTemplateModel.hashCode(), secondDisplayTemplateModel.hashCode());

        secondDisplayTemplateModel.setRowNumbering(true);
        // noinspection SimplifiableJUnitAssertion
        org.junit.Assert.assertFalse(firstDisplayTemplateModel.equals(secondDisplayTemplateModel));
        org.junit.Assert.assertNotEquals(
            firstDisplayTemplateModel.hashCode(), secondDisplayTemplateModel.hashCode());
        secondDisplayTemplateModel.setRowNumbering(false);
        // noinspection SimplifiableJUnitAssertion
        org.junit.Assert.assertTrue  (firstDisplayTemplateModel.equals(secondDisplayTemplateModel));
        org.junit.Assert.assertEquals(
            firstDisplayTemplateModel.hashCode(), secondDisplayTemplateModel.hashCode());

        {
            final int row = 5;
            firstDisplayTemplateModel.addExcludedRow(row);
            // noinspection SimplifiableJUnitAssertion
            org.junit.Assert.assertFalse(firstDisplayTemplateModel.equals(
                secondDisplayTemplateModel));
            org.junit.Assert.assertNotEquals(
                firstDisplayTemplateModel.hashCode(), secondDisplayTemplateModel.hashCode());

            secondDisplayTemplateModel.addExcludedRow(row);
            // noinspection SimplifiableJUnitAssertion
            org.junit.Assert.assertTrue(firstDisplayTemplateModel.equals(
                secondDisplayTemplateModel));
            org.junit.Assert.assertEquals(
                firstDisplayTemplateModel.hashCode(), secondDisplayTemplateModel.hashCode());
        }

        final int col = 1;
        firstDisplayTemplateModel.addExcludedCol(col);
        // noinspection SimplifiableJUnitAssertion
        org.junit.Assert.assertFalse(firstDisplayTemplateModel.equals(secondDisplayTemplateModel));
        org.junit.Assert.assertNotEquals(
            firstDisplayTemplateModel.hashCode(), secondDisplayTemplateModel.hashCode());

        secondDisplayTemplateModel.addExcludedCol(col);
        // noinspection SimplifiableJUnitAssertion
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
        // noinspection SimplifiableJUnitAssertion
        org.junit.Assert.assertTrue(displayTemplateModel.equals(clonedDisplayTemplateModel));
    }
    // endregion

    // region Public Method Tests
    // region excludedCells Public Method Tests
    @org.junit.Test() public void getExcludedCellsAsJsonWorks()
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

    @org.junit.Test() public void isExcludedCellAndToggleWork()
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
        final org.wheatgenetics.coordinate.model.Cell cell =
            new org.wheatgenetics.coordinate.model.Cell(1,1);

        org.junit.Assert.assertFalse(displayTemplateModel.isExcludedCell(cell));

        displayTemplateModel.toggle(cell);
        org.junit.Assert.assertTrue(displayTemplateModel.isExcludedCell(cell));

        displayTemplateModel.toggle(cell);
        org.junit.Assert.assertFalse(displayTemplateModel.isExcludedCell(cell));
    }
    // endregion

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

    @org.junit.Test() public void getExcludedRowsAsJsonWorks()
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

    @org.junit.Test() public void isExcludedRowWorks()
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

    @org.junit.Test() public void isExcludedColWorks()
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
    // endregion
}