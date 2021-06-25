package org.wheatgenetics.coordinate.model;

import android.content.res.Resources;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.PluralsRes;
import androidx.annotation.StringRes;

import org.junit.Assert;
import org.junit.Test;
import org.wheatgenetics.coordinate.StringGetter;

/**
 * Uses:
 * android.content.res.Resources.NotFoundException
 * android.os.Bundle
 *
 * androidx.annotation.IntRange
 * androidx.annotation.Nullable
 * androidx.annotation.PluralsRes
 * androidx.annotation.StringRes
 *
 * org.junit.Assert
 * org.junit.Test
 *
 * org.wheatgenetics.coordinate.StringGetter
 *
 * org.wheatgenetics.coordinate.model.Cells
 * org.wheatgenetics.coordinate.model.DisplayTemplateModel
 * org.wheatgenetics.coordinate.model.TemplateType
 */
@SuppressWarnings({"ClassExplicitlyExtendsObject"}) public class DisplayTemplateModelTest
extends Object implements StringGetter
{
    // region org.wheatgenetics.coordinate.StringGetter Overridden Methods
    @Override @Nullable public String get(
    @StringRes final int resId)
    {
        // noinspection SwitchStatementWithTooFewBranches
        switch (resId) { default: Assert.fail(); return null; }
    }

    @Override @NonNull public String getQuantity(
    @PluralsRes         final int                 resId     ,
    @IntRange(from = 0) final int                 quantity  ,
    @Nullable           final Object... formatArgs)
    throws Resources.NotFoundException { Assert.fail(); return null; }
    // endregion

    // region Constructor Tests
    @Test(expected = IllegalArgumentException.class)
    public void invalidCodeFourthConstructorFails()
    {
        new DisplayTemplateModel(
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
            /* timestamp                    => */0,
            /* stringGetter                 => */this);
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidColNumberingFourthConstructorFails()
    {
        new DisplayTemplateModel(
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
            /* timestamp                    => */0,
            /* stringGetter                 => */this);
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidRowNumberingFourthConstructorFails()
    {
        new DisplayTemplateModel(
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
            /* timestamp                    => */0,
            /* stringGetter                 => */this);
    }
    // endregion

    // region Overridden Method Tests
    @Test() public void toStringSucceeds()
    {
        final String expectedString =
            "DisplayTemplateModel [id: 03, title=testTitle, type=1, rows=5, cols=2, generatedExcl" +
            "udedCellsAmount=0, colNumbering=true, rowNumbering=false, entryLabel=null, stamp=0, " +
            "excludedCells=null, excludedRows=null, excludedCols=null";
        final DisplayTemplateModel displayTemplateModel =
            new DisplayTemplateModel(
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
                /* timestamp                    => */0,
                /* stringGetter                 => */this);
        Assert.assertEquals(expectedString, displayTemplateModel.toString());
    }

    @Test() public void equalsAndHashCodeWork()
    {
        final long             id    = 44                                                       ;
        final String title = "testTitle"                                              ;
        final int              code  = 1, rows = 5, cols = 2, colNumbering = 1, rowNumbering = 0;
        final long             timestamp = 0                                                    ;

        final DisplayTemplateModel
            firstDisplayTemplateModel = new DisplayTemplateModel(
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
                /* timestamp                    => */ timestamp,
                /* stringGetter                 => */this),
            secondDisplayTemplateModel =
                new DisplayTemplateModel(
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
                    /* timestamp                    => */ timestamp,
                    /* stringGetter                 => */this);

        // noinspection SimplifiableJUnitAssertion
        Assert.assertTrue  (firstDisplayTemplateModel.equals(secondDisplayTemplateModel));
        Assert.assertEquals(
            firstDisplayTemplateModel.hashCode(), secondDisplayTemplateModel.hashCode());

        secondDisplayTemplateModel.setTitle("different");
        // noinspection SimplifiableJUnitAssertion
        Assert.assertFalse(firstDisplayTemplateModel.equals(secondDisplayTemplateModel));
        Assert.assertNotEquals(
            firstDisplayTemplateModel.hashCode(), secondDisplayTemplateModel.hashCode());
        secondDisplayTemplateModel.setTitle(title);
        // noinspection SimplifiableJUnitAssertion
        Assert.assertTrue  (firstDisplayTemplateModel.equals(secondDisplayTemplateModel));
        Assert.assertEquals(
            firstDisplayTemplateModel.hashCode(), secondDisplayTemplateModel.hashCode());

        secondDisplayTemplateModel.setType(TemplateType.SEED);
        // noinspection SimplifiableJUnitAssertion
        Assert.assertFalse(firstDisplayTemplateModel.equals(secondDisplayTemplateModel));
        Assert.assertNotEquals(
            firstDisplayTemplateModel.hashCode(), secondDisplayTemplateModel.hashCode());
        secondDisplayTemplateModel.setType(TemplateType.DNA);
        // noinspection SimplifiableJUnitAssertion
        Assert.assertTrue  (firstDisplayTemplateModel.equals(secondDisplayTemplateModel));
        Assert.assertEquals(
            firstDisplayTemplateModel.hashCode(), secondDisplayTemplateModel.hashCode());

        secondDisplayTemplateModel.setColNumbering(false);
        // noinspection SimplifiableJUnitAssertion
        Assert.assertFalse(firstDisplayTemplateModel.equals(secondDisplayTemplateModel));
        Assert.assertNotEquals(
            firstDisplayTemplateModel.hashCode(), secondDisplayTemplateModel.hashCode());
        secondDisplayTemplateModel.setColNumbering(true);
        // noinspection SimplifiableJUnitAssertion
        Assert.assertTrue  (firstDisplayTemplateModel.equals(secondDisplayTemplateModel));
        Assert.assertEquals(
            firstDisplayTemplateModel.hashCode(), secondDisplayTemplateModel.hashCode());

        secondDisplayTemplateModel.setRowNumbering(true);
        // noinspection SimplifiableJUnitAssertion
        Assert.assertFalse(firstDisplayTemplateModel.equals(secondDisplayTemplateModel));
        Assert.assertNotEquals(
            firstDisplayTemplateModel.hashCode(), secondDisplayTemplateModel.hashCode());
        secondDisplayTemplateModel.setRowNumbering(false);
        // noinspection SimplifiableJUnitAssertion
        Assert.assertTrue  (firstDisplayTemplateModel.equals(secondDisplayTemplateModel));
        Assert.assertEquals(
            firstDisplayTemplateModel.hashCode(), secondDisplayTemplateModel.hashCode());

        {
            final int row = 5;
            firstDisplayTemplateModel.addExcludedRow(row);
            // noinspection SimplifiableJUnitAssertion
            Assert.assertFalse(firstDisplayTemplateModel.equals(
                secondDisplayTemplateModel));
            Assert.assertNotEquals(
                firstDisplayTemplateModel.hashCode(), secondDisplayTemplateModel.hashCode());

            secondDisplayTemplateModel.addExcludedRow(row);
            // noinspection SimplifiableJUnitAssertion
            Assert.assertTrue(firstDisplayTemplateModel.equals(
                secondDisplayTemplateModel));
            Assert.assertEquals(
                firstDisplayTemplateModel.hashCode(), secondDisplayTemplateModel.hashCode());
        }

        final int col = 1;
        firstDisplayTemplateModel.addExcludedCol(col);
        // noinspection SimplifiableJUnitAssertion
        Assert.assertFalse(firstDisplayTemplateModel.equals(secondDisplayTemplateModel));
        Assert.assertNotEquals(
            firstDisplayTemplateModel.hashCode(), secondDisplayTemplateModel.hashCode());

        secondDisplayTemplateModel.addExcludedCol(col);
        // noinspection SimplifiableJUnitAssertion
        Assert.assertTrue  (firstDisplayTemplateModel.equals(secondDisplayTemplateModel));
        Assert.assertEquals(
            firstDisplayTemplateModel.hashCode(), secondDisplayTemplateModel.hashCode());
    }

    @Test() public void cloneSucceeds()
    {
        final DisplayTemplateModel displayTemplateModel =
            new DisplayTemplateModel(
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
                /* timestamp                    => */0,
                /* stringGetter                 => */this);
        displayTemplateModel.setEntryLabel("abc");

        final DisplayTemplateModel clonedDisplayTemplateModel =
            (DisplayTemplateModel) displayTemplateModel.clone();
        // noinspection SimplifiableJUnitAssertion
        Assert.assertTrue(displayTemplateModel.equals(clonedDisplayTemplateModel));
    }
    // endregion

    // region Public Method Tests
    // region excludedCells Public Method Tests
    @Test() public void getExcludedCellsAsJsonWorks()
    {
        final DisplayTemplateModel displayTemplateModel =
            new DisplayTemplateModel(
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
                /* timestamp                    => */880,
                /* stringGetter                 => */this);
        Assert.assertNull(displayTemplateModel.getExcludedCellsAsJson());
    }

    @Test() public void isExcludedCellAndToggleWork()
    {
        final DisplayTemplateModel displayTemplateModel =
            new DisplayTemplateModel(
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
                /* timestamp                    => */880,
                /* stringGetter                 => */this);
        final Cell cell =
            new Cell(1,1,this);

        Assert.assertFalse(displayTemplateModel.isExcludedCell(cell));

        displayTemplateModel.toggle(cell);
        Assert.assertTrue(displayTemplateModel.isExcludedCell(cell));

        displayTemplateModel.toggle(cell);
        Assert.assertFalse(displayTemplateModel.isExcludedCell(cell));
    }
    // endregion

    // region excludedRows Public Method Tests
    @Test() public void addExcludedRowAndClearExcludedRowsSucceed()
    {
        final DisplayTemplateModel displayTemplateModel =
            new DisplayTemplateModel(
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
                /* timestamp                    => */880,
                /* stringGetter                 => */this);
        final int row = 1;
        Assert.assertFalse(displayTemplateModel.isExcludedRow(row));

        displayTemplateModel.addExcludedRow(row);
        Assert.assertTrue(displayTemplateModel.isExcludedRow(row));

        displayTemplateModel.clearExcludedRows();
        Assert.assertFalse(displayTemplateModel.isExcludedRow(row));
    }

    @Test() public void getExcludedRowsAsJsonWorks()
    {
        final DisplayTemplateModel displayTemplateModel =
            new DisplayTemplateModel(
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
                /* timestamp                    => */880,
                /* stringGetter                 => */this);
        Assert.assertNull(displayTemplateModel.getExcludedRowsAsJson());
    }

    @Test() public void isExcludedRowWorks()
    {
        final DisplayTemplateModel displayTemplateModel =
            new DisplayTemplateModel(
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
                /* timestamp                    => */880,
                /* stringGetter                 => */this);
        Assert.assertFalse(displayTemplateModel.isExcludedRow(1));
    }
    // endregion

    // region excludedCols Public Method Tests
    @Test() public void addExcludedColAndClearExcludedColsSucceed()
    {
        final DisplayTemplateModel displayTemplateModel =
            new DisplayTemplateModel(
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
                /* timestamp                    => */880,
                /* stringGetter                 => */this);
        final int col = 1;
        Assert.assertFalse(displayTemplateModel.isExcludedCol(col));

        displayTemplateModel.addExcludedCol(col);
        Assert.assertTrue(displayTemplateModel.isExcludedCol(col));

        displayTemplateModel.clearExcludedCols();
        Assert.assertFalse(displayTemplateModel.isExcludedCol(col));
    }

    @Test() public void getExcludedColsAsJsonSucceeds()
    {
        final DisplayTemplateModel displayTemplateModel =
            new DisplayTemplateModel(
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
                /* timestamp                    => */880,
                /* stringGetter                 => */this);
        Assert.assertNull(displayTemplateModel.getExcludedColsAsJson());
    }

    @Test() public void isExcludedColWorks()
    {
        final DisplayTemplateModel displayTemplateModel =
            new DisplayTemplateModel(
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
                /* timestamp                    => */880,
                /* stringGetter                 => */this);
        Assert.assertFalse(displayTemplateModel.isExcludedCol(1));
    }
    // endregion

    // region checkedItems Public Method Tests
    @Test() public void rowCheckedItemsSucceeds()
    {
        final DisplayTemplateModel displayTemplateModel =
            new DisplayTemplateModel(
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
                /* timestamp                    => */880,
                /* stringGetter                 => */this);
        Assert.assertArrayEquals(new boolean[]{false, false, false, false, false},
            displayTemplateModel.rowCheckedItems());

        displayTemplateModel.addExcludedRow(4);
        Assert.assertArrayEquals(new boolean[]{false, false, false, true, false},
            displayTemplateModel.rowCheckedItems());
    }

    @Test() public void colCheckedItemsSucceeds()
    {
        final DisplayTemplateModel displayTemplateModel =
            new DisplayTemplateModel(
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
                /* timestamp                    => */880,
                /* stringGetter                 => */this);
        Assert.assertArrayEquals(
            new boolean[]{false, false}, displayTemplateModel.colCheckedItems());

        displayTemplateModel.addExcludedCol(1);
        Assert.assertArrayEquals(
            new boolean[]{true, false}, displayTemplateModel.colCheckedItems());
    }
    // endregion
    // endregion
}