package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * org.junit.Assert
 * org.junit.Test
 *
 * org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields
 *
 * org.wheatgenetics.coordinate.model.TemplateModel
 */
@java.lang.SuppressWarnings("ClassExplicitlyExtendsObject")
public class TemplateModelTest extends java.lang.Object
{
    // region Constructor Tests
    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void badIdThirdConstructorFails()
    {
        new org.wheatgenetics.coordinate.model.TemplateModel(
            /* id             => */ -9         ,
            /* title          => */ "testTitle",
            /* code           => */ 1          ,
            /* rows           => */ 5          ,
            /* cols           => */ 2          ,
            /* excludeCells   => */ null       ,
            /* excludeRows    => */ null       ,
            /* excludeCols    => */ null       ,
            /* colNumbering   => */ 1          ,
            /* rowNumbering   => */ 0          ,
            /* optionalFields => */ null       ,
            /* timestamp      => */ 0          );
    }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void invalidCodeThirdConstructorFails()
    {
        new org.wheatgenetics.coordinate.model.TemplateModel(
            /* id             => */ 5          ,
            /* title          => */ "testTitle",
            /* code           => */ 3          ,
            /* rows           => */ 5          ,
            /* cols           => */ 2          ,
            /* excludeCells   => */ null       ,
            /* excludeRows    => */ null       ,
            /* excludeCols    => */ null       ,
            /* colNumbering   => */ 1          ,
            /* rowNumbering   => */ 0          ,
            /* optionalFields => */ null       ,
            /* timestamp      => */ 0          );
    }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void invalidRowsThirdConstructorFails()
    {
        new org.wheatgenetics.coordinate.model.TemplateModel(
            /* id             => */ 5          ,
            /* title          => */ "testTitle",
            /* code           => */ 1          ,
            /* rows           => */ 0          ,
            /* cols           => */ 2          ,
            /* excludeCells   => */ null       ,
            /* excludeRows    => */ null       ,
            /* excludeCols    => */ null       ,
            /* colNumbering   => */ 1          ,
            /* rowNumbering   => */ 0          ,
            /* optionalFields => */ null       ,
            /* timestamp      => */ 0          );
    }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void invalidColsThirdConstructorFails()
    {
        new org.wheatgenetics.coordinate.model.TemplateModel(
            /* id             => */ 5          ,
            /* title          => */ "testTitle",
            /* code           => */  1         ,
            /* rows           => */  5         ,
            /* cols           => */ -2         ,
            /* excludeCells   => */ null       ,
            /* excludeRows    => */ null       ,
            /* excludeCols    => */ null       ,
            /* colNumbering   => */ 1          ,
            /* rowNumbering   => */ 0          ,
            /* optionalFields => */ null       ,
            /* timestamp      => */ 0          );
    }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void invalidColNumberingThirdConstructorFails()
    {
        new org.wheatgenetics.coordinate.model.TemplateModel(
            /* id             => */ 5          ,
            /* title          => */ "testTitle",
            /* code           => */ 1          ,
            /* rows           => */ 5          ,
            /* cols           => */ 2          ,
            /* excludeCells   => */ null       ,
            /* excludeRows    => */ null       ,
            /* excludeCols    => */ null       ,
            /* colNumbering   => */ 3          ,
            /* rowNumbering   => */ 0          ,
            /* optionalFields => */ null       ,
            /* timestamp      => */ 0          );
    }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void invalidRowNumberingThirdConstructorFails()
    {
        new org.wheatgenetics.coordinate.model.TemplateModel(
            /* id             => */ 5          ,
            /* title          => */ "testTitle",
            /* code           => */ 1          ,
            /* rows           => */ 5          ,
            /* cols           => */ 2          ,
            /* excludeCells   => */ null       ,
            /* excludeRows    => */ null       ,
            /* excludeCols    => */ null       ,
            /* colNumbering   => */ 1          ,
            /* rowNumbering   => */ 56         ,
            /* optionalFields => */ null       ,
            /* timestamp      => */ 0          );
    }
    // endregion

    // region Overridden Method Tests
    // region toString() Overridden Method Tests
    @org.junit.Test
    public void formatStringSucceeds()
    {
        final java.lang.String expectedFormatString =
            "%s [id: 09, title=testTitle, type=1, rows=5, cols=2, colNumbering=true, rowNumb" +
            "ering=false, options=, excludeCells=%s, excludeRows=%s, excludeCols=%s, stamp=%d]";
        final org.wheatgenetics.coordinate.model.TemplateModel templateModel =
            new org.wheatgenetics.coordinate.model.TemplateModel(
                /* id             => */ 9          ,
                /* title          => */ "testTitle",
                /* code           => */ 1          ,
                /* rows           => */ 5          ,
                /* cols           => */ 2          ,
                /* excludeCells   => */ null       ,
                /* excludeRows    => */ null       ,
                /* excludeCols    => */ null       ,
                /* colNumbering   => */ 1          ,
                /* rowNumbering   => */ 0          ,
                /* optionalFields => */ null       ,
                /* timestamp      => */ 0          );
        org.junit.Assert.assertEquals(templateModel.formatString(), expectedFormatString);
    }

    @org.junit.Test
    public void toStringSucceeds()
    {
        final java.lang.String expectedString = "TemplateModel [id: 03, title=testTitle, type=1, " +
            "rows=5, cols=2, colNumbering=true, rowNumbering=false, options=, excludeCells=null, " +
            "excludeRows=null, excludeCols=null, stamp=0]";
        final org.wheatgenetics.coordinate.model.TemplateModel templateModel =
            new org.wheatgenetics.coordinate.model.TemplateModel(
                /* id             => */ 3          ,
                /* title          => */ "testTitle",
                /* code           => */ 1          ,
                /* rows           => */ 5          ,
                /* cols           => */ 2          ,
                /* excludeCells   => */ null       ,
                /* excludeRows    => */ null       ,
                /* excludeCols    => */ null       ,
                /* colNumbering   => */ 1          ,
                /* rowNumbering   => */ 0          ,
                /* optionalFields => */ null       ,
                /* timestamp      => */ 0          );
        org.junit.Assert.assertEquals(templateModel.toString(), expectedString);
    }
    // endregion

    @org.junit.Test
    public void equalsAndHashCodeSucceedAndFail()
    {
        final long             id    = 44                                                       ;
        final java.lang.String title = "testTitle"                                              ;
        final int              code  = 1, rows = 5, cols = 2, colNumbering = 1, rowNumbering = 0;
        final long             timestamp = 0                                                    ;

        final org.wheatgenetics.coordinate.model.TemplateModel firstTemplateModel =
            new org.wheatgenetics.coordinate.model.TemplateModel(
                /* id             => */ id          ,
                /* title          => */ title       ,
                /* code           => */ code        ,
                /* rows           => */ rows        ,
                /* cols           => */ cols        ,
                /* excludeCells   => */ null        ,
                /* excludeRows    => */ null        ,
                /* excludeCols    => */ null        ,
                /* colNumbering   => */ colNumbering,
                /* rowNumbering   => */ rowNumbering,
                /* optionalFields => */ null        ,
                /* timestamp      => */ timestamp   );

        final int row = 5, col = 1;

        {
            final org.wheatgenetics.coordinate.model.TemplateModel secondTemplateModel =
                new org.wheatgenetics.coordinate.model.TemplateModel(
                    /* id             => */ id          ,
                    /* title          => */ title       ,
                    /* code           => */ code        ,
                    /* rows           => */ rows        ,
                    /* cols           => */ cols        ,
                    /* excludeCells   => */ null        ,
                    /* excludeRows    => */ null        ,
                    /* excludeCols    => */ null        ,
                    /* colNumbering   => */ colNumbering,
                    /* rowNumbering   => */ rowNumbering,
                    /* optionalFields => */ null        ,
                    /* timestamp      => */ timestamp   );
            org.junit.Assert.assertTrue  (firstTemplateModel.equals(secondTemplateModel));
            org.junit.Assert.assertEquals(
                firstTemplateModel.hashCode(), secondTemplateModel.hashCode());

            secondTemplateModel.setTitle("different");
            org.junit.Assert.assertFalse    (firstTemplateModel.equals(secondTemplateModel));
            org.junit.Assert.assertNotEquals(
                firstTemplateModel.hashCode(), secondTemplateModel.hashCode());
            secondTemplateModel.setTitle(title);
            org.junit.Assert.assertTrue  (firstTemplateModel.equals(secondTemplateModel));
            org.junit.Assert.assertEquals(
                firstTemplateModel.hashCode(), secondTemplateModel.hashCode());

            secondTemplateModel.setType(org.wheatgenetics.coordinate.model.TemplateType.SEED);
            org.junit.Assert.assertFalse    (firstTemplateModel.equals(secondTemplateModel));
            org.junit.Assert.assertNotEquals(
                firstTemplateModel.hashCode(), secondTemplateModel.hashCode());
            secondTemplateModel.setType(org.wheatgenetics.coordinate.model.TemplateType.DNA);
            org.junit.Assert.assertTrue  (firstTemplateModel.equals(secondTemplateModel));
            org.junit.Assert.assertEquals(
                firstTemplateModel.hashCode(), secondTemplateModel.hashCode());

            secondTemplateModel.setColNumbering(false);
            org.junit.Assert.assertFalse    (firstTemplateModel.equals(secondTemplateModel));
            org.junit.Assert.assertNotEquals(
                firstTemplateModel.hashCode(), secondTemplateModel.hashCode());
            secondTemplateModel.setColNumbering(true);
            org.junit.Assert.assertTrue  (firstTemplateModel.equals(secondTemplateModel));
            org.junit.Assert.assertEquals(
                firstTemplateModel.hashCode(), secondTemplateModel.hashCode());

            secondTemplateModel.setRowNumbering(true);
            org.junit.Assert.assertFalse    (firstTemplateModel.equals(secondTemplateModel));
            org.junit.Assert.assertNotEquals(
                firstTemplateModel.hashCode(), secondTemplateModel.hashCode());
            secondTemplateModel.setRowNumbering(false);
            org.junit.Assert.assertTrue  (firstTemplateModel.equals(secondTemplateModel));
            org.junit.Assert.assertEquals(
                firstTemplateModel.hashCode(), secondTemplateModel.hashCode());


            firstTemplateModel.addExcludedCell(row, col);
            org.junit.Assert.assertFalse    (firstTemplateModel.equals(secondTemplateModel));
            org.junit.Assert.assertNotEquals(
                firstTemplateModel.hashCode(), secondTemplateModel.hashCode());

            secondTemplateModel.addExcludedCell(row, col);
            org.junit.Assert.assertTrue  (firstTemplateModel.equals(secondTemplateModel));
            org.junit.Assert.assertEquals(
                firstTemplateModel.hashCode(), secondTemplateModel.hashCode());


            firstTemplateModel.addExcludeCol(col);
            org.junit.Assert.assertFalse    (firstTemplateModel.equals(secondTemplateModel));
            org.junit.Assert.assertNotEquals(
                firstTemplateModel.hashCode(), secondTemplateModel.hashCode());

            secondTemplateModel.addExcludeCol(col);
            org.junit.Assert.assertTrue  (firstTemplateModel.equals(secondTemplateModel));
            org.junit.Assert.assertEquals(
                firstTemplateModel.hashCode(), secondTemplateModel.hashCode());


            firstTemplateModel.addExcludeRow(row);
            org.junit.Assert.assertFalse    (firstTemplateModel.equals(secondTemplateModel));
            org.junit.Assert.assertNotEquals(
                firstTemplateModel.hashCode(), secondTemplateModel.hashCode());

            secondTemplateModel.addExcludeRow(row);
            org.junit.Assert.assertTrue  (firstTemplateModel.equals(secondTemplateModel));
            org.junit.Assert.assertEquals(
                firstTemplateModel.hashCode(), secondTemplateModel.hashCode());
        }

        org.wheatgenetics.coordinate.model.TemplateModel thirdTemplateModel =
            new org.wheatgenetics.coordinate.model.TemplateModel(
                /* id             => */ id          ,
                /* title          => */ title       ,
                /* code           => */ code        ,
                /* rows           => */ rows        ,
                /* cols           => */ cols        ,
                /* excludeCells   => */ null        ,
                /* excludeRows    => */ null        ,
                /* excludeCols    => */ null        ,
                /* colNumbering   => */ colNumbering,
                /* rowNumbering   => */ rowNumbering,
                /* optionalFields => */ null        ,                                     // Notice.
                /* timestamp      => */ timestamp   );
        thirdTemplateModel.addExcludedCell(row, col);
        thirdTemplateModel.addExcludeCol(col); thirdTemplateModel.addExcludeRow(row);
        org.junit.Assert.assertTrue  (firstTemplateModel.equals(thirdTemplateModel)               );
        org.junit.Assert.assertEquals(firstTemplateModel.hashCode(), thirdTemplateModel.hashCode());

        thirdTemplateModel = new org.wheatgenetics.coordinate.model.TemplateModel(
            /* id             => */ id          ,
            /* title          => */ title       ,
            /* code           => */ code        ,
            /* rows           => */ rows        ,
            /* cols           => */ cols        ,
            /* excludeCells   => */ null        ,
            /* excludeRows    => */ null        ,
            /* excludeCols    => */ null        ,
            /* colNumbering   => */ colNumbering,
            /* rowNumbering   => */ rowNumbering,
            /* optionalFields => */ ""          ,                                         // Notice.
            /* timestamp      => */ timestamp   );
        thirdTemplateModel.addExcludedCell(row, col);
        thirdTemplateModel.addExcludeCol(col); thirdTemplateModel.addExcludeRow(row);
        org.junit.Assert.assertTrue  (firstTemplateModel.equals(thirdTemplateModel)               );
        org.junit.Assert.assertEquals(firstTemplateModel.hashCode(), thirdTemplateModel.hashCode());

        thirdTemplateModel = new org.wheatgenetics.coordinate.model.TemplateModel(
            /* id             => */ id          ,
            /* title          => */ title       ,
            /* code           => */ code        ,
            /* rows           => */ rows        ,
            /* cols           => */ cols        ,
            /* excludeCells   => */ null        ,
            /* excludeRows    => */ null        ,
            /* excludeCols    => */ null        ,
            /* colNumbering   => */ colNumbering,
            /* rowNumbering   => */ rowNumbering,
            /* optionalFields => */ "  "        ,                                         // Notice.
            /* timestamp      => */ timestamp   );
        thirdTemplateModel.addExcludedCell(row, col);
        thirdTemplateModel.addExcludeCol(col); thirdTemplateModel.addExcludeRow(row);
        org.junit.Assert.assertTrue  (firstTemplateModel.equals(thirdTemplateModel)               );
        org.junit.Assert.assertEquals(firstTemplateModel.hashCode(), thirdTemplateModel.hashCode());

        thirdTemplateModel = new org.wheatgenetics.coordinate.model.TemplateModel(
            /* id             => */ id         ,
            /* title          => */ title,
            /* code           => */ code       ,
            /* rows           => */ rows       ,
            /* cols           => */ cols       ,
            /* excludeCells   => */ null       ,
            /* excludeRows    => */ null       ,
            /* excludeCols    => */ null       ,
            /* colNumbering   => */ colNumbering,
            /* rowNumbering   => */ rowNumbering,
            /* optionalFields => */ null       ,
            /* timestamp      => */ 5087       );                                         // Notice.
        thirdTemplateModel.addExcludedCell(row, col);
        thirdTemplateModel.addExcludeCol(col); thirdTemplateModel.addExcludeRow(row);
        org.junit.Assert.assertFalse    (firstTemplateModel.equals(thirdTemplateModel));
        org.junit.Assert.assertNotEquals(
            firstTemplateModel.hashCode(), thirdTemplateModel.hashCode());
    }

    @org.junit.Test
    public void cloneSucceeds()
    {
        final org.wheatgenetics.coordinate.model.TemplateModel templateModel =
            new org.wheatgenetics.coordinate.model.TemplateModel(
                /* id             => */ 12         ,
                /* title          => */ "testTitle",
                /* code           => */ 1          ,
                /* rows           => */ 5          ,
                /* cols           => */ 2          ,
                /* excludeCells   => */ null       ,
                /* excludeRows    => */ null       ,
                /* excludeCols    => */ null       ,
                /* colNumbering   => */ 1          ,
                /* rowNumbering   => */ 0          ,
                /* optionalFields => */ null       ,
                /* timestamp      => */ 0          );
        final org.wheatgenetics.coordinate.model.TemplateModel clonedTemplateModel =
            (org.wheatgenetics.coordinate.model.TemplateModel) templateModel.clone();
        org.junit.Assert.assertTrue(templateModel.equals(clonedTemplateModel));
    }
    // endregion

    @org.junit.Test
    public void assignSucceedsAndFails()
    {
        final org.wheatgenetics.coordinate.model.TemplateModel
            firstTemplateModel = new org.wheatgenetics.coordinate.model.TemplateModel(
                /* id             => */ 10         ,
                /* title          => */ "testTitle",
                /* code           => */ 1          ,
                /* rows           => */ 5          ,
                /* cols           => */ 2          ,
                /* excludeCells   => */ null       ,
                /* excludeRows    => */ null       ,
                /* excludeCols    => */ null       ,
                /* colNumbering   => */ 1          ,
                /* rowNumbering   => */ 0          ,
                /* optionalFields => */ null       ,
                /* timestamp      => */ 880        ),
        secondTemplateModel = new org.wheatgenetics.coordinate.model.TemplateModel(
                /* id             => */ 10          ,       // same as firstTemplateModel on purpose
                /* title          => */ "qwertyasdf",
                /* code           => */ 2           ,
                /* rows           => */ 3           ,
                /* cols           => */ 29          ,
                /* excludeCells   => */ null        ,
                /* excludeRows    => */ null        ,
                /* excludeCols    => */ null        ,
                /* colNumbering   => */ 0           ,
                /* rowNumbering   => */ 1           ,
                /* optionalFields => */ null        ,
                /* timestamp      => */ 880         );      // same as firstTemplateModel on purpose
        org.junit.Assert.assertFalse(firstTemplateModel.equals(secondTemplateModel));

        firstTemplateModel.assign(secondTemplateModel);
        org.junit.Assert.assertTrue(firstTemplateModel.equals(secondTemplateModel));
        // When secondTemplateModel was given initial values for id and timestamp, I made sure that
        // these initial values were the same initial values given to firstTemplateModel.  I did
        // this so that assertTrue() would succeed.  It is not enough to rely on assign() to make
        // firstTemplateModel's id and timestamp the same as secondTemplateModel's id and timestamp.
        // Why?  Because assign() is intentionally built to *not* assign id and timestamp: to
        // assign() means to assign all the fields except id and timestamp (and clear the excludes,
        // as you will see below).

        {
            final int excludeCol = 20;
            {
                final int excludeRow = 3;
                {
                    final int excludedCellCol = 1, excludedCellRow = 1;
                    secondTemplateModel.addExcludedCell(excludedCellCol, excludedCellRow);
                    secondTemplateModel.addExcludeRow(excludeRow);
                    secondTemplateModel.addExcludeCol(excludeCol);
                    org.junit.Assert.assertFalse(firstTemplateModel.equals(secondTemplateModel));

                    firstTemplateModel.assign(secondTemplateModel);
                    org.junit.Assert.assertFalse(firstTemplateModel.equals(secondTemplateModel));
                    // Even though assign() was called the two TemplateModels are not equal.  They
                    // are not equal because secondTemplateModel has 1 excluded cell, 1 excluded
                    // row, and 1 excluded column while firstTemplateModel still has 0, 0, and 0.
                    // assign() does *not* assign the excludes.  (In fact, it clears them.)


                    secondTemplateModel.clearExcludesAndOptionalFields();
                    org.junit.Assert.assertFalse(firstTemplateModel.equals(secondTemplateModel));
                    // The two templateModels are *still* not equal even though
                    // clearExcludesAndOptionalFields() was called to make secondTemplateModel have
                    // 0 excluded cells, 0 excluded rows, and 0 excluded columns just like
                    // firstTemplateModel.  They are not equal for two reasons: 1) clearing empties
                    // the data structures used to hold the excludes but does not deallocate the
                    // data structures or assign null to them.  Thus, firstTemplateModel's data
                    // structure references still point to null while secondTemplateModel's data
                    // structure references point to allocated memory.
                    // 2) clearExcludesAndOptionalFields() "clears" the optional fields by
                    // initializing them.  This means secondTemplateModel's optional fields are
                    // initialized while firstTemplateModel's is still null.

                    firstTemplateModel.addExcludedCell(excludedCellCol, excludedCellRow);
                }
                firstTemplateModel.addExcludeRow(excludeRow);
            }
            firstTemplateModel.addExcludeCol(excludeCol);
        }
        firstTemplateModel.clearExcludesAndOptionalFields();
        org.junit.Assert.assertTrue(firstTemplateModel.equals(secondTemplateModel));
    }

    // region Public Method Tests
    // region excludeCells Public Method Tests
    // region addExcludedCell() excludeCells Public Method Tests
    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void tooSmallRowAddExcludedCellFails()
    {
        final org.wheatgenetics.coordinate.model.TemplateModel templateModel =
            new org.wheatgenetics.coordinate.model.TemplateModel(
                /* id             => */ 10         ,
                /* title          => */ "testTitle",
                /* code           => */ 1          ,
                /* rows           => */ 5          ,
                /* cols           => */ 2          ,
                /* excludeCells   => */ null       ,
                /* excludeRows    => */ null       ,
                /* excludeCols    => */ null       ,
                /* colNumbering   => */ 1          ,
                /* rowNumbering   => */ 0          ,
                /* optionalFields => */ null       ,
                /* timestamp      => */ 880        );
        templateModel.addExcludedCell(0, 1);
    }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void tooBigRowAddExcludedCellFails()
    {
        final org.wheatgenetics.coordinate.model.TemplateModel templateModel =
            new org.wheatgenetics.coordinate.model.TemplateModel(
                /* id             => */ 10         ,
                /* title          => */ "testTitle",
                /* code           => */ 1          ,
                /* rows           => */ 5          ,
                /* cols           => */ 2          ,
                /* excludeCells   => */ null       ,
                /* excludeRows    => */ null       ,
                /* excludeCols    => */ null       ,
                /* colNumbering   => */ 1          ,
                /* rowNumbering   => */ 0          ,
                /* optionalFields => */ null       ,
                /* timestamp      => */ 880        );
        templateModel.addExcludedCell(10, 1);
    }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void tooSmallColAddExcludedCellFails()
    {
        final org.wheatgenetics.coordinate.model.TemplateModel templateModel =
            new org.wheatgenetics.coordinate.model.TemplateModel(
                /* id             => */ 10         ,
                /* title          => */ "testTitle",
                /* code           => */ 1          ,
                /* rows           => */ 5          ,
                /* cols           => */ 2          ,
                /* excludeCells   => */ null       ,
                /* excludeRows    => */ null       ,
                /* excludeCols    => */ null       ,
                /* colNumbering   => */ 1          ,
                /* rowNumbering   => */ 0          ,
                /* optionalFields => */ null       ,
                /* timestamp      => */ 880        );
        templateModel.addExcludedCell(1, -1);
    }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void tooBigColAddExcludedCellFails()
    {
        final org.wheatgenetics.coordinate.model.TemplateModel templateModel =
            new org.wheatgenetics.coordinate.model.TemplateModel(
                /* id             => */ 10         ,
                /* title          => */ "testTitle",
                /* code           => */ 1          ,
                /* rows           => */ 5          ,
                /* cols           => */ 2          ,
                /* excludeCells   => */ null       ,
                /* excludeRows    => */ null       ,
                /* excludeCols    => */ null       ,
                /* colNumbering   => */ 1          ,
                /* rowNumbering   => */ 0          ,
                /* optionalFields => */ null       ,
                /* timestamp      => */ 880        );
        templateModel.addExcludedCell(1, 111);
    }

    @org.junit.Test
    public void addExcludedCellSucceeds()
    {
        final org.wheatgenetics.coordinate.model.TemplateModel templateModel =
            new org.wheatgenetics.coordinate.model.TemplateModel(
                /* id             => */ 10         ,
                /* title          => */ "testTitle",
                /* code           => */ 1          ,
                /* rows           => */ 5          ,
                /* cols           => */ 2          ,
                /* excludeCells   => */ null       ,
                /* excludeRows    => */ null       ,
                /* excludeCols    => */ null       ,
                /* colNumbering   => */ 1          ,
                /* rowNumbering   => */ 0          ,
                /* optionalFields => */ null       ,
                /* timestamp      => */ 880        );
        templateModel.addExcludedCell(1, 1);
    }
    // endregion

    @org.junit.Test
    public void getExcludeCellsAsJsonSucceeds()
    {
        final org.wheatgenetics.coordinate.model.TemplateModel templateModel =
            new org.wheatgenetics.coordinate.model.TemplateModel(
                /* id             => */ 10         ,
                /* title          => */ "testTitle",
                /* code           => */ 1          ,
                /* rows           => */ 5          ,
                /* cols           => */ 2          ,
                /* excludeCells   => */ null       ,
                /* excludeRows    => */ null       ,
                /* excludeCols    => */ null       ,
                /* colNumbering   => */ 1          ,
                /* rowNumbering   => */ 0          ,
                /* optionalFields => */ null       ,
                /* timestamp      => */ 880        );
        org.junit.Assert.assertNull(templateModel.getExcludeCellsAsJson());
    }

    @org.junit.Test
    public void isExcludedCellSucceeds()
    {
        final org.wheatgenetics.coordinate.model.TemplateModel templateModel =
            new org.wheatgenetics.coordinate.model.TemplateModel(
                /* id             => */ 10         ,
                /* title          => */ "testTitle",
                /* code           => */ 1          ,
                /* rows           => */ 5          ,
                /* cols           => */ 2          ,
                /* excludeCells   => */ null       ,
                /* excludeRows    => */ null       ,
                /* excludeCols    => */ null       ,
                /* colNumbering   => */ 1          ,
                /* rowNumbering   => */ 0          ,
                /* optionalFields => */ null       ,
                /* timestamp      => */ 880        );
        org.junit.Assert.assertFalse(templateModel.isExcludedCell(1, 1));
    }
    // endregion

    // region excludeRows, excludeCols Public Method Tests
    @org.junit.Test
    public void getExcludeRowsAsJsonSucceeds()
    {
        final org.wheatgenetics.coordinate.model.TemplateModel templateModel =
            new org.wheatgenetics.coordinate.model.TemplateModel(
                /* id             => */ 10         ,
                /* title          => */ "testTitle",
                /* code           => */ 1          ,
                /* rows           => */ 5          ,
                /* cols           => */ 2          ,
                /* excludeCells   => */ null       ,
                /* excludeRows    => */ null       ,
                /* excludeCols    => */ null       ,
                /* colNumbering   => */ 1          ,
                /* rowNumbering   => */ 0          ,
                /* optionalFields => */ null       ,
                /* timestamp      => */ 880        );
        org.junit.Assert.assertNull(templateModel.getExcludeRowsAsJson());
    }

    @org.junit.Test
    public void getExcludeColsAsJsonSucceeds()
    {
        final org.wheatgenetics.coordinate.model.TemplateModel templateModel =
            new org.wheatgenetics.coordinate.model.TemplateModel(
                /* id             => */ 10         ,
                /* title          => */ "testTitle",
                /* code           => */ 1          ,
                /* rows           => */ 5          ,
                /* cols           => */ 2          ,
                /* excludeCells   => */ null       ,
                /* excludeRows    => */ null       ,
                /* excludeCols    => */ null       ,
                /* colNumbering   => */ 1          ,
                /* rowNumbering   => */ 0          ,
                /* optionalFields => */ null       ,
                /* timestamp      => */ 880        );
        org.junit.Assert.assertNull(templateModel.getExcludeColsAsJson());
    }

    @org.junit.Test
    public void isExcludedRowSucceeds()
    {
        final org.wheatgenetics.coordinate.model.TemplateModel templateModel =
            new org.wheatgenetics.coordinate.model.TemplateModel(
                /* id             => */ 10         ,
                /* title          => */ "testTitle",
                /* code           => */ 1          ,
                /* rows           => */ 5          ,
                /* cols           => */ 2          ,
                /* excludeCells   => */ null       ,
                /* excludeRows    => */ null       ,
                /* excludeCols    => */ null       ,
                /* colNumbering   => */ 1          ,
                /* rowNumbering   => */ 0          ,
                /* optionalFields => */ null       ,
                /* timestamp      => */ 880        );
        org.junit.Assert.assertFalse(templateModel.isExcludedRow(1));
    }

    @org.junit.Test
    public void isExcludedColSucceeds()
    {
        final org.wheatgenetics.coordinate.model.TemplateModel templateModel =
            new org.wheatgenetics.coordinate.model.TemplateModel(
                /* id             => */ 10         ,
                /* title          => */ "testTitle",
                /* code           => */ 1          ,
                /* rows           => */ 5          ,
                /* cols           => */ 2          ,
                /* excludeCells   => */ null       ,
                /* excludeRows    => */ null       ,
                /* excludeCols    => */ null       ,
                /* colNumbering   => */ 1          ,
                /* rowNumbering   => */ 0          ,
                /* optionalFields => */ null       ,
                /* timestamp      => */ 880        );
        org.junit.Assert.assertFalse(templateModel.isExcludedCol(1));
    }
    // endregion

    @org.junit.Test
    public void getTimestampSucceeds()
    {
        final long timestamp = 880;
        final org.wheatgenetics.coordinate.model.TemplateModel templateModel =
            new org.wheatgenetics.coordinate.model.TemplateModel(
                /* id             => */ 10         ,
                /* title          => */ "testTitle",
                /* code           => */ 1          ,
                /* rows           => */ 5          ,
                /* cols           => */ 2          ,
                /* excludeCells   => */ null       ,
                /* excludeRows    => */ null       ,
                /* excludeCols    => */ null       ,
                /* colNumbering   => */ 1          ,
                /* rowNumbering   => */ 0          ,
                /* optionalFields => */ null       ,
                /* timestamp      => */ timestamp  );
        org.junit.Assert.assertEquals(templateModel.getTimestamp(), timestamp);
    }

    @org.junit.Test
    public void clearExcludesAndOptionalFieldsSucceeds()
    {
        final org.wheatgenetics.coordinate.model.TemplateModel templateModel =
            new org.wheatgenetics.coordinate.model.TemplateModel(
                /* id             => */ 10         ,
                /* title          => */ "testTitle",
                /* code           => */ 1          ,
                /* rows           => */ 5          ,
                /* cols           => */ 2          ,
                /* excludeCells   => */ null       ,
                /* excludeRows    => */ null       ,
                /* excludeCols    => */ null       ,
                /* colNumbering   => */ 1          ,
                /* rowNumbering   => */ 0          ,
                /* optionalFields => */ null       ,
                /* timestamp      => */ 880        );
        templateModel.clearExcludesAndOptionalFields();
        org.junit.Assert.assertEquals(templateModel.optionalFieldsAsJson(),
            org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields.makeNew().toJson());
    }

    // region nextFreeCell() Public Method Tests
    @org.junit.Test
    public void nextFreeCellFailsAndSucceeds()
    {
        final org.wheatgenetics.coordinate.model.TemplateModel templateModel =
            new org.wheatgenetics.coordinate.model.TemplateModel(
                /* id             => */ 10         ,
                /* title          => */ "testTitle",
                /* code           => */ 1          ,
                /* rows           => */ 5          ,
                /* cols           => */ 5          ,
                /* excludeCells   => */ null       ,
                /* excludeRows    => */ null       ,
                /* excludeCols    => */ null       ,
                /* colNumbering   => */ 1          ,
                /* rowNumbering   => */ 0          ,
                /* optionalFields => */ null       ,
                /* timestamp      => */ 880        );

        org.junit.Assert.assertNull(templateModel.nextFreeCell(null));

        {
            final org.wheatgenetics.coordinate.model.Cell
                currentCell      = new org.wheatgenetics.coordinate.model.Cell(1, 1),
                expectedNextCell = new org.wheatgenetics.coordinate.model.Cell(1, 1);
            org.junit.Assert.assertTrue(
                expectedNextCell.equals(templateModel.nextFreeCell(currentCell)));
        }

        {
            final org.wheatgenetics.coordinate.model.Cell
                currentCell      = new org.wheatgenetics.coordinate.model.Cell(5, 5),
                expectedNextCell = new org.wheatgenetics.coordinate.model.Cell(5, 5);
            org.junit.Assert.assertTrue(
                expectedNextCell.equals(templateModel.nextFreeCell(currentCell)));
        }

        templateModel.addExcludeCol(2);
        {
            final org.wheatgenetics.coordinate.model.Cell
                currentCell      = new org.wheatgenetics.coordinate.model.Cell(2, 2),
                expectedNextCell = new org.wheatgenetics.coordinate.model.Cell(1, 3);
            org.junit.Assert.assertTrue(
                expectedNextCell.equals(templateModel.nextFreeCell(currentCell)));
        }

        templateModel.addExcludeRow(3);
        {
            final org.wheatgenetics.coordinate.model.Cell
                currentCell      = new org.wheatgenetics.coordinate.model.Cell(3, 3),
                expectedNextCell = new org.wheatgenetics.coordinate.model.Cell(4, 3);
            org.junit.Assert.assertTrue(
                expectedNextCell.equals(templateModel.nextFreeCell(currentCell)));
        }

        {
            final org.wheatgenetics.coordinate.model.Cell
                currentCell      = new org.wheatgenetics.coordinate.model.Cell(3, 2),
                expectedNextCell = new org.wheatgenetics.coordinate.model.Cell(1, 3);
            org.junit.Assert.assertTrue(
                expectedNextCell.equals(templateModel.nextFreeCell(currentCell)));
        }

        {
            final org.wheatgenetics.coordinate.model.Cell
                currentCell      = new org.wheatgenetics.coordinate.model.Cell(3, 5),
                expectedNextCell = new org.wheatgenetics.coordinate.model.Cell(4, 5);
            org.junit.Assert.assertTrue(
                expectedNextCell.equals(templateModel.nextFreeCell(currentCell)));
        }

        templateModel.addExcludedCell(4, 4);
        {
            final org.wheatgenetics.coordinate.model.Cell
                currentCell      = new org.wheatgenetics.coordinate.model.Cell(4, 4),
                expectedNextCell = new org.wheatgenetics.coordinate.model.Cell(5, 4);
            org.junit.Assert.assertTrue(
                expectedNextCell.equals(templateModel.nextFreeCell(currentCell)));
        }

        templateModel.addExcludedCell(5, 4);
        {
            final org.wheatgenetics.coordinate.model.Cell
                currentCell      = new org.wheatgenetics.coordinate.model.Cell(5, 4),
                expectedNextCell = new org.wheatgenetics.coordinate.model.Cell(1, 5);
            org.junit.Assert.assertTrue(
                expectedNextCell.equals(templateModel.nextFreeCell(currentCell)));
        }

        templateModel.addExcludedCell(5, 5);
        {
            final org.wheatgenetics.coordinate.model.Cell currentCell =
                new org.wheatgenetics.coordinate.model.Cell(5, 5);
            org.junit.Assert.assertNull(templateModel.nextFreeCell(currentCell));
        }
    }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void tooBigRowCurrentCellNextFreeCellFails()
    {
        final org.wheatgenetics.coordinate.model.TemplateModel templateModel =
            new org.wheatgenetics.coordinate.model.TemplateModel(
                /* id             => */ 10         ,
                /* title          => */ "testTitle",
                /* code           => */ 1          ,
                /* rows           => */ 5          ,
                /* cols           => */ 5          ,
                /* excludeCells   => */ null       ,
                /* excludeRows    => */ null       ,
                /* excludeCols    => */ null       ,
                /* colNumbering   => */ 1          ,
                /* rowNumbering   => */ 0          ,
                /* optionalFields => */ null       ,
                /* timestamp      => */ 880        );
        final org.wheatgenetics.coordinate.model.Cell currentCell =
            new org.wheatgenetics.coordinate.model.Cell(6, 1);
        templateModel.nextFreeCell(currentCell);
    }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void tooBigColCurrentCellNextFreeCellFails()
    {
        final org.wheatgenetics.coordinate.model.TemplateModel templateModel =
            new org.wheatgenetics.coordinate.model.TemplateModel(
                /* id             => */ 10         ,
                /* title          => */ "testTitle",
                /* code           => */ 1          ,
                /* rows           => */ 5          ,
                /* cols           => */ 5          ,
                /* excludeCells   => */ null       ,
                /* excludeRows    => */ null       ,
                /* excludeCols    => */ null       ,
                /* colNumbering   => */ 1          ,
                /* rowNumbering   => */ 0          ,
                /* optionalFields => */ null       ,
                /* timestamp      => */ 880        );
        final org.wheatgenetics.coordinate.model.Cell currentCell =
            new org.wheatgenetics.coordinate.model.Cell(1, 6);
        templateModel.nextFreeCell(currentCell);
    }
    // endregion

    // region checkedItems Public Method Tests
    @org.junit.Test
    public void rowCheckedItemsSucceeds()
    {
        final org.wheatgenetics.coordinate.model.TemplateModel templateModel =
            new org.wheatgenetics.coordinate.model.TemplateModel(
                /* id             => */ 10         ,
                /* title          => */ "testTitle",
                /* code           => */ 1          ,
                /* rows           => */ 5          ,
                /* cols           => */ 2          ,
                /* excludeCells   => */ null       ,
                /* excludeRows    => */ null       ,
                /* excludeCols    => */ null       ,
                /* colNumbering   => */ 1          ,
                /* rowNumbering   => */ 0          ,
                /* optionalFields => */ null       ,
                /* timestamp      => */ 880        );
        org.junit.Assert.assertArrayEquals(
            new boolean[] {false, false, false, false, false}, templateModel.rowCheckedItems());

        templateModel.addExcludeRow(4);
        org.junit.Assert.assertArrayEquals(
            new boolean[] {false, false, false, true, false}, templateModel.rowCheckedItems());
    }

    @org.junit.Test
    public void colCheckedItemsSucceeds()
    {
        final org.wheatgenetics.coordinate.model.TemplateModel templateModel =
            new org.wheatgenetics.coordinate.model.TemplateModel(
                /* id             => */ 10         ,
                /* title          => */ "testTitle",
                /* code           => */ 1          ,
                /* rows           => */ 5          ,
                /* cols           => */ 2          ,
                /* excludeCells   => */ null       ,
                /* excludeRows    => */ null       ,
                /* excludeCols    => */ null       ,
                /* colNumbering   => */ 1          ,
                /* rowNumbering   => */ 0          ,
                /* optionalFields => */ null       ,
                /* timestamp      => */ 880        );
        org.junit.Assert.assertArrayEquals(
            new boolean[] {false, false}, templateModel.colCheckedItems());

        templateModel.addExcludeCol(1);
        org.junit.Assert.assertArrayEquals(
            new boolean[] {true, false}, templateModel.colCheckedItems());
    }
    // endregion
    // endregion
}