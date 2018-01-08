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
            /* id                           => */ -9         ,
            /* title                        => */ "testTitle",
            /* code                         => */ 1          ,
            /* rows                         => */ 5          ,
            /* cols                         => */ 2          ,
            /* generatedExcludedCellsAmount => */ 0          ,
            /* initialExcludedCells         => */ null       ,
            /* excludedRows                 => */ null       ,
            /* excludedCols                 => */ null       ,
            /* colNumbering                 => */ 1          ,
            /* rowNumbering                 => */ 0          ,
            /* optionalFields               => */ null       ,
            /* timestamp                    => */ 0          );
    }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void invalidCodeThirdConstructorFails()
    {
        new org.wheatgenetics.coordinate.model.TemplateModel(
            /* id                           => */ 5          ,
            /* title                        => */ "testTitle",
            /* code                         => */ 3          ,
            /* rows                         => */ 5          ,
            /* cols                         => */ 2          ,
            /* generatedExcludedCellsAmount => */ 0          ,
            /* initialExcludedCells         => */ null       ,
            /* excludedRows                 => */ null       ,
            /* excludedCols                 => */ null       ,
            /* colNumbering                 => */ 1          ,
            /* rowNumbering                 => */ 0          ,
            /* optionalFields               => */ null       ,
            /* timestamp                    => */ 0          );
    }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void invalidRowsThirdConstructorFails()
    {
        new org.wheatgenetics.coordinate.model.TemplateModel(
            /* id                           => */ 5          ,
            /* title                        => */ "testTitle",
            /* code                         => */ 1          ,
            /* rows                         => */ 0          ,
            /* cols                         => */ 2          ,
            /* generatedExcludedCellsAmount => */ 0          ,
            /* initialExcludedCells         => */ null       ,
            /* excludedRows                 => */ null       ,
            /* excludedCols                 => */ null       ,
            /* colNumbering                 => */ 1          ,
            /* rowNumbering                 => */ 0          ,
            /* optionalFields               => */ null       ,
            /* timestamp                    => */ 0          );
    }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void invalidColsThirdConstructorFails()
    {
        new org.wheatgenetics.coordinate.model.TemplateModel(
            /* id                           => */ 5          ,
            /* title                        => */ "testTitle",
            /* code                         => */  1         ,
            /* rows                         => */  5         ,
            /* cols                         => */ -2         ,
            /* generatedExcludedCellsAmount => */  0         ,
            /* initialExcludedCells         => */ null       ,
            /* excludedRows                 => */ null       ,
            /* excludedCols                 => */ null       ,
            /* colNumbering                 => */ 1          ,
            /* rowNumbering                 => */ 0          ,
            /* optionalFields               => */ null       ,
            /* timestamp                    => */ 0          );
    }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void invalidColNumberingThirdConstructorFails()
    {
        new org.wheatgenetics.coordinate.model.TemplateModel(
            /* id                           => */ 5          ,
            /* title                        => */ "testTitle",
            /* code                         => */ 1          ,
            /* rows                         => */ 5          ,
            /* cols                         => */ 2          ,
            /* generatedExcludedCellsAmount => */ 0          ,
            /* initialExcludedCells         => */ null       ,
            /* excludedRows                 => */ null       ,
            /* excludedCols                 => */ null       ,
            /* colNumbering                 => */ 3          ,
            /* rowNumbering                 => */ 0          ,
            /* optionalFields               => */ null       ,
            /* timestamp                    => */ 0          );
    }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void invalidRowNumberingThirdConstructorFails()
    {
        new org.wheatgenetics.coordinate.model.TemplateModel(
            /* id                           => */ 5          ,
            /* title                        => */ "testTitle",
            /* code                         => */ 1          ,
            /* rows                         => */ 5          ,
            /* cols                         => */ 2          ,
            /* generatedExcludedCellsAmount => */ 0          ,
            /* initialExcludedCells         => */ null       ,
            /* excludedRows                 => */ null       ,
            /* excludedCols                 => */ null       ,
            /* colNumbering                 => */  1         ,
            /* rowNumbering                 => */ 56         ,
            /* optionalFields               => */ null       ,
            /* timestamp                    => */ 0          );
    }
    // endregion

    // region Overridden Method Tests
    // region toString() Overridden Method Tests
    @org.junit.Test
    public void formatStringSucceeds()
    {
        final java.lang.String expectedFormatString = "%s [id: 09, title=testTitle, type=1, ro" +
            "ws=5, cols=2, generatedExcludedCellsAmount=0, colNumbering=true, rowNumbering=fal" +
            "se, stamp=0, options=, initialExcludedCells=%s, excludedRows=%s, excludedCols=%s]";
        final org.wheatgenetics.coordinate.model.TemplateModel templateModel =
            new org.wheatgenetics.coordinate.model.TemplateModel(
                /* id                           => */ 9          ,
                /* title                        => */ "testTitle",
                /* code                         => */ 1          ,
                /* rows                         => */ 5          ,
                /* cols                         => */ 2          ,
                /* generatedExcludedCellsAmount => */ 0          ,
                /* initialExcludedCells         => */ null       ,
                /* excludedRows                 => */ null       ,
                /* excludedCols                 => */ null       ,
                /* colNumbering                 => */ 1          ,
                /* rowNumbering                 => */ 0          ,
                /* optionalFields               => */ null       ,
                /* timestamp                    => */ 0          );
        org.junit.Assert.assertEquals(templateModel.formatString(), expectedFormatString);
    }

    @org.junit.Test
    public void toStringSucceeds()
    {
        final java.lang.String expectedString =
            "TemplateModel [id: 03, title=testTitle, type=1, rows=5, cols=2, generatedExcludedCel" +
            "lsAmount=0, colNumbering=true, rowNumbering=false, stamp=0, options=, initialExclude" +
            "dCells=null, excludedRows=null, excludedCols=null]";
        final org.wheatgenetics.coordinate.model.TemplateModel templateModel =
            new org.wheatgenetics.coordinate.model.TemplateModel(
                /* id                           => */ 3          ,
                /* title                        => */ "testTitle",
                /* code                         => */ 1          ,
                /* rows                         => */ 5          ,
                /* cols                         => */ 2          ,
                /* generatedExcludedCellsAmount => */ 0          ,
                /* initialExcludedCells         => */ null       ,
                /* excludedRows                 => */ null       ,
                /* excludedCols                 => */ null       ,
                /* colNumbering                 => */ 1          ,
                /* rowNumbering                 => */ 0          ,
                /* optionalFields               => */ null       ,
                /* timestamp                    => */ 0          );
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
                /* id                           => */ id          ,
                /* title                        => */ title       ,
                /* code                         => */ code        ,
                /* rows                         => */ rows        ,
                /* cols                         => */ cols        ,
                /* generatedExcludedCellsAmount => */ 0           ,
                /* initialExcludedCells         => */ null        ,
                /* excludedRows                 => */ null        ,
                /* excludedCols                 => */ null        ,
                /* colNumbering                 => */ colNumbering,
                /* rowNumbering                 => */ rowNumbering,
                /* optionalFields               => */ null        ,
                /* timestamp                    => */ timestamp   );

        final int row = 5, col = 1;

        {
            final org.wheatgenetics.coordinate.model.TemplateModel secondTemplateModel =
                new org.wheatgenetics.coordinate.model.TemplateModel(
                    /* id                           => */ id          ,
                    /* title                        => */ title       ,
                    /* code                         => */ code        ,
                    /* rows                         => */ rows        ,
                    /* cols                         => */ cols        ,
                    /* generatedExcludedCellsAmount => */ 0           ,
                    /* initialExcludedCells         => */ null        ,
                    /* excludedRows                 => */ null        ,
                    /* excludedCols                 => */ null        ,
                    /* colNumbering                 => */ colNumbering,
                    /* rowNumbering                 => */ rowNumbering,
                    /* optionalFields               => */ null        ,
                    /* timestamp                    => */ timestamp   );
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


            firstTemplateModel.addInitialExcludedCell(row, col);
            org.junit.Assert.assertFalse    (firstTemplateModel.equals(secondTemplateModel));
            org.junit.Assert.assertNotEquals(
                firstTemplateModel.hashCode(), secondTemplateModel.hashCode());

            secondTemplateModel.addInitialExcludedCell(row, col);
            org.junit.Assert.assertTrue  (firstTemplateModel.equals(secondTemplateModel));
            org.junit.Assert.assertEquals(
                firstTemplateModel.hashCode(), secondTemplateModel.hashCode());


            firstTemplateModel.addExcludedCol(col);
            org.junit.Assert.assertFalse    (firstTemplateModel.equals(secondTemplateModel));
            org.junit.Assert.assertNotEquals(
                firstTemplateModel.hashCode(), secondTemplateModel.hashCode());

            secondTemplateModel.addExcludedCol(col);
            org.junit.Assert.assertTrue  (firstTemplateModel.equals(secondTemplateModel));
            org.junit.Assert.assertEquals(
                firstTemplateModel.hashCode(), secondTemplateModel.hashCode());


            firstTemplateModel.addExcludedRow(row);
            org.junit.Assert.assertFalse    (firstTemplateModel.equals(secondTemplateModel));
            org.junit.Assert.assertNotEquals(
                firstTemplateModel.hashCode(), secondTemplateModel.hashCode());

            secondTemplateModel.addExcludedRow(row);
            org.junit.Assert.assertTrue  (firstTemplateModel.equals(secondTemplateModel));
            org.junit.Assert.assertEquals(
                firstTemplateModel.hashCode(), secondTemplateModel.hashCode());
        }

        org.wheatgenetics.coordinate.model.TemplateModel thirdTemplateModel =
            new org.wheatgenetics.coordinate.model.TemplateModel(
                /* id                           => */ id          ,
                /* title                        => */ title       ,
                /* code                         => */ code        ,
                /* rows                         => */ rows        ,
                /* cols                         => */ cols        ,
                /* generatedExcludedCellsAmount => */ 0           ,
                /* initialExcludedCells         => */ null        ,
                /* excludedRows                 => */ null        ,
                /* excludedCols                 => */ null        ,
                /* colNumbering                 => */ colNumbering,
                /* rowNumbering                 => */ rowNumbering,
                /* optionalFields               => */ null        ,                       // Notice.
                /* timestamp                    => */ timestamp   );
        thirdTemplateModel.addInitialExcludedCell(row, col);
        thirdTemplateModel.addExcludedCol(col); thirdTemplateModel.addExcludedRow(row);
        org.junit.Assert.assertTrue  (firstTemplateModel.equals(thirdTemplateModel)               );
        org.junit.Assert.assertEquals(firstTemplateModel.hashCode(), thirdTemplateModel.hashCode());

        thirdTemplateModel = new org.wheatgenetics.coordinate.model.TemplateModel(
            /* id                           => */ id          ,
            /* title                        => */ title       ,
            /* code                         => */ code        ,
            /* rows                         => */ rows        ,
            /* cols                         => */ cols        ,
            /* generatedExcludedCellsAmount => */ 0           ,
            /* initialExcludedCells         => */ null        ,
            /* excludedRows                 => */ null        ,
            /* excludedCols                 => */ null        ,
            /* colNumbering                 => */ colNumbering,
            /* rowNumbering                 => */ rowNumbering,
            /* optionalFields               => */ ""          ,                           // Notice.
            /* timestamp                    => */ timestamp   );
        thirdTemplateModel.addInitialExcludedCell(row, col);
        thirdTemplateModel.addExcludedCol(col); thirdTemplateModel.addExcludedRow(row);
        org.junit.Assert.assertTrue  (firstTemplateModel.equals(thirdTemplateModel)               );
        org.junit.Assert.assertEquals(firstTemplateModel.hashCode(), thirdTemplateModel.hashCode());

        thirdTemplateModel = new org.wheatgenetics.coordinate.model.TemplateModel(
            /* id                           => */ id          ,
            /* title                        => */ title       ,
            /* code                         => */ code        ,
            /* rows                         => */ rows        ,
            /* cols                         => */ cols        ,
            /* generatedExcludedCellsAmount => */ 0           ,
            /* initialExcludedCells         => */ null        ,
            /* excludedRows                 => */ null        ,
            /* excludedCols                 => */ null        ,
            /* colNumbering                 => */ colNumbering,
            /* rowNumbering                 => */ rowNumbering,
            /* optionalFields               => */ "  "        ,                           // Notice.
            /* timestamp                    => */ timestamp   );
        thirdTemplateModel.addInitialExcludedCell(row, col);
        thirdTemplateModel.addExcludedCol(col); thirdTemplateModel.addExcludedRow(row);
        org.junit.Assert.assertTrue  (firstTemplateModel.equals(thirdTemplateModel)               );
        org.junit.Assert.assertEquals(firstTemplateModel.hashCode(), thirdTemplateModel.hashCode());

        thirdTemplateModel = new org.wheatgenetics.coordinate.model.TemplateModel(
            /* id                           => */ id         ,
            /* title                        => */ title,
            /* code                         => */ code       ,
            /* rows                         => */ rows       ,
            /* cols                         => */ cols       ,
            /* generatedExcludedCellsAmount => */ 0          ,
            /* initialExcludedCells         => */ null       ,
            /* excludedRows                 => */ null       ,
            /* excludedCols                 => */ null       ,
            /* colNumbering                 => */ colNumbering,
            /* rowNumbering                 => */ rowNumbering,
            /* optionalFields               => */ null       ,
            /* timestamp                    => */ 5087       );                           // Notice.
        thirdTemplateModel.addInitialExcludedCell(row, col);
        thirdTemplateModel.addExcludedCol(col); thirdTemplateModel.addExcludedRow(row);
        org.junit.Assert.assertFalse    (firstTemplateModel.equals(thirdTemplateModel));
        org.junit.Assert.assertNotEquals(
            firstTemplateModel.hashCode(), thirdTemplateModel.hashCode());
    }

    @org.junit.Test
    public void cloneSucceeds()
    {
        final org.wheatgenetics.coordinate.model.TemplateModel templateModel =
            new org.wheatgenetics.coordinate.model.TemplateModel(
                /* id                           => */ 12         ,
                /* title                        => */ "testTitle",
                /* code                         => */ 1          ,
                /* rows                         => */ 5          ,
                /* cols                         => */ 2          ,
                /* generatedExcludedCellsAmount => */ 1          ,
                /* initialExcludedCells         => */ null       ,
                /* excludedRows                 => */ null       ,
                /* excludedCols                 => */ null       ,
                /* colNumbering                 => */ 1          ,
                /* rowNumbering                 => */ 0          ,
                /* optionalFields               => */ null       ,
                /* timestamp                    => */ 0          );
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
                /* id                           => */ 10         ,
                /* title                        => */ "testTitle",
                /* code                         => */ 1          ,
                /* rows                         => */ 5          ,
                /* cols                         => */ 2          ,
                /* generatedExcludedCellsAmount => */ 0          ,
                /* initialExcludedCells         => */ null       ,
                /* excludedRows                 => */ null       ,
                /* excludedCols                 => */ null       ,
                /* colNumbering                 => */ 1          ,
                /* rowNumbering                 => */ 0          ,
                /* optionalFields               => */ null       ,
                /* timestamp                    => */ 880        ),
            secondTemplateModel = new org.wheatgenetics.coordinate.model.TemplateModel(
                /* id                           => */ 10          ,               // same on purpose
                /* title                        => */ "qwertyasdf",
                /* code                         => */ 2           ,
                /* rows                         => */ 3           ,
                /* cols                         => */ 29          ,
                /* generatedExcludedCellsAmount => */ 5           ,
                /* initialExcludedCells         => */ null        ,
                /* excludedRows                 => */ null        ,
                /* excludedCols                 => */ null        ,
                /* colNumbering                 => */ 0           ,
                /* rowNumbering                 => */ 1           ,
                /* optionalFields               => */ null        ,
                /* timestamp                    => */ 880         );              // same on purpose
        org.junit.Assert.assertFalse(firstTemplateModel.equals(secondTemplateModel));

        firstTemplateModel.assign(secondTemplateModel);
        org.junit.Assert.assertTrue(firstTemplateModel.equals(secondTemplateModel));
        // When secondTemplateModel was given initial values for id and timestamp, I made sure that
        // these initial values were the same initial values given to firstTemplateModel.  I did
        // this so that assertTrue() would succeed.  It is not enough to rely on assign() to make
        // firstTemplateModel's id and timestamp the same as secondTemplateModel's id and timestamp.
        // Why?  Because assign() is intentionally built to *not* assign id and timestamp: to
        // assign() means to assign all the fields except id and timestamp (and clear the excludeds,
        // as you will see below).

        {
            final int excludedCol = 20;
            {
                final int excludedRow = 3;
                {
                    final int excludedCellCol = 1, excludedCellRow = 1;
                    secondTemplateModel.addInitialExcludedCell(excludedCellCol, excludedCellRow);
                    secondTemplateModel.addExcludedRow(excludedRow);
                    secondTemplateModel.addExcludedCol(excludedCol);
                    org.junit.Assert.assertFalse(firstTemplateModel.equals(secondTemplateModel));

                    firstTemplateModel.assign(secondTemplateModel);
                    org.junit.Assert.assertFalse(firstTemplateModel.equals(secondTemplateModel));
                    // Even though assign() was called the two TemplateModels are not equal.  They
                    // are not equal because secondTemplateModel has 1 excluded cell, 1 excluded
                    // row, and 1 excluded column while firstTemplateModel still has 0, 0, and 0.
                    // assign() does *not* assign the excludeds.  (In fact, it clears them.)


                    firstTemplateModel.addInitialExcludedCell(excludedCellCol, excludedCellRow);
                }
                firstTemplateModel.addExcludedRow(excludedRow);
            }
            firstTemplateModel.addExcludedCol(excludedCol);
        }
        org.junit.Assert.assertTrue(firstTemplateModel.equals(secondTemplateModel));
    }

    // region Public Method Tests
    // region initialExcludedCells Public Method Tests
    // region addInitialExcludedCell() initialExcludedCells Public Method Tests
    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void tooSmallRowAddInitialExcludedCellFails()
    {
        final org.wheatgenetics.coordinate.model.TemplateModel templateModel =
            new org.wheatgenetics.coordinate.model.TemplateModel(
                /* id                           => */ 10         ,
                /* title                        => */ "testTitle",
                /* code                         => */ 1          ,
                /* rows                         => */ 5          ,
                /* cols                         => */ 2          ,
                /* generatedExcludedCellsAmount => */ 0          ,
                /* initialExcludedCells         => */ null       ,
                /* excludedRows                 => */ null       ,
                /* excludedCols                 => */ null       ,
                /* colNumbering                 => */ 1          ,
                /* rowNumbering                 => */ 0          ,
                /* optionalFields               => */ null       ,
                /* timestamp                    => */ 880        );
        templateModel.addInitialExcludedCell(0, 1);
    }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void tooBigRowAddInitialExcludedCellFails()
    {
        final org.wheatgenetics.coordinate.model.TemplateModel templateModel =
            new org.wheatgenetics.coordinate.model.TemplateModel(
                /* id                           => */ 10         ,
                /* title                        => */ "testTitle",
                /* code                         => */ 1          ,
                /* rows                         => */ 5          ,
                /* cols                         => */ 2          ,
                /* generatedExcludedCellsAmount => */ 0          ,
                /* initialExcludedCells         => */ null       ,
                /* excludedRows                 => */ null       ,
                /* excludedCols                 => */ null       ,
                /* colNumbering                 => */ 1          ,
                /* rowNumbering                 => */ 0          ,
                /* optionalFields               => */ null       ,
                /* timestamp                    => */ 880        );
        templateModel.addInitialExcludedCell(10, 1);
    }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void tooSmallColAddInitialExcludedCellFails()
    {
        final org.wheatgenetics.coordinate.model.TemplateModel templateModel =
            new org.wheatgenetics.coordinate.model.TemplateModel(
                /* id                           => */ 10         ,
                /* title                        => */ "testTitle",
                /* code                         => */ 1          ,
                /* rows                         => */ 5          ,
                /* cols                         => */ 2          ,
                /* generatedExcludedCellsAmount => */ 0          ,
                /* initialExcludedCells         => */ null       ,
                /* excludedRows                 => */ null       ,
                /* excludedCols                 => */ null       ,
                /* colNumbering                 => */ 1          ,
                /* rowNumbering                 => */ 0          ,
                /* optionalFields               => */ null       ,
                /* timestamp                    => */ 880        );
        templateModel.addInitialExcludedCell(1, -1);
    }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void tooBigColAddInitialExcludedCellFails()
    {
        final org.wheatgenetics.coordinate.model.TemplateModel templateModel =
            new org.wheatgenetics.coordinate.model.TemplateModel(
                /* id                           => */ 10         ,
                /* title                        => */ "testTitle",
                /* code                         => */ 1          ,
                /* rows                         => */ 5          ,
                /* cols                         => */ 2          ,
                /* generatedExcludedCellsAmount => */ 0          ,
                /* initialExcludedCells         => */ null       ,
                /* excludedRows                 => */ null       ,
                /* excludedCols                 => */ null       ,
                /* colNumbering                 => */ 1          ,
                /* rowNumbering                 => */ 0          ,
                /* optionalFields               => */ null       ,
                /* timestamp                    => */ 880        );
        templateModel.addInitialExcludedCell(1, 111);
    }

    @org.junit.Test
    public void addInitialExcludedCellSucceeds()
    {
        final org.wheatgenetics.coordinate.model.TemplateModel templateModel =
            new org.wheatgenetics.coordinate.model.TemplateModel(
                /* id                           => */ 10         ,
                /* title                        => */ "testTitle",
                /* code                         => */ 1          ,
                /* rows                         => */ 5          ,
                /* cols                         => */ 2          ,
                /* generatedExcludedCellsAmount => */ 0          ,
                /* initialExcludedCells         => */ null       ,
                /* excludedRows                 => */ null       ,
                /* excludedCols                 => */ null       ,
                /* colNumbering                 => */ 1          ,
                /* rowNumbering                 => */ 0          ,
                /* optionalFields               => */ null       ,
                /* timestamp                    => */ 880        );
        templateModel.addInitialExcludedCell(1, 1);
    }
    // endregion

    @org.junit.Test
    public void getInitialExcludedCellsAsJsonSucceeds()
    {
        final org.wheatgenetics.coordinate.model.TemplateModel templateModel =
            new org.wheatgenetics.coordinate.model.TemplateModel(
                /* id                           => */ 10         ,
                /* title                        => */ "testTitle",
                /* code                         => */ 1          ,
                /* rows                         => */ 5          ,
                /* cols                         => */ 2          ,
                /* generatedExcludedCellsAmount => */ 0          ,
                /* initialExcludedCells         => */ null       ,
                /* excludedRows                 => */ null       ,
                /* excludedCols                 => */ null       ,
                /* colNumbering                 => */ 1          ,
                /* rowNumbering                 => */ 0          ,
                /* optionalFields               => */ null       ,
                /* timestamp                    => */ 880        );
        org.junit.Assert.assertNull(templateModel.getInitialExcludedCellsAsJson());
    }
    // endregion

    // region excludedRows, excludedCols Public Method Tests
    @org.junit.Test
    public void getExcludedRowsAsJsonSucceeds()
    {
        final org.wheatgenetics.coordinate.model.TemplateModel templateModel =
            new org.wheatgenetics.coordinate.model.TemplateModel(
                /* id                           => */ 10         ,
                /* title                        => */ "testTitle",
                /* code                         => */ 1          ,
                /* rows                         => */ 5          ,
                /* cols                         => */ 2          ,
                /* generatedExcludedCellsAmount => */ 0          ,
                /* initialExcludedCells         => */ null       ,
                /* excludedRows                 => */ null       ,
                /* excludedCols                 => */ null       ,
                /* colNumbering                 => */ 1          ,
                /* rowNumbering                 => */ 0          ,
                /* optionalFields               => */ null       ,
                /* timestamp                    => */ 880        );
        org.junit.Assert.assertNull(templateModel.getExcludedRowsAsJson());
    }

    @org.junit.Test
    public void getExcludedColsAsJsonSucceeds()
    {
        final org.wheatgenetics.coordinate.model.TemplateModel templateModel =
            new org.wheatgenetics.coordinate.model.TemplateModel(
                /* id                           => */ 10         ,
                /* title                        => */ "testTitle",
                /* code                         => */ 1          ,
                /* rows                         => */ 5          ,
                /* cols                         => */ 2          ,
                /* generatedExcludedCellsAmount => */ 0          ,
                /* initialExcludedCells         => */ null       ,
                /* excludedRows                 => */ null       ,
                /* excludedCols                 => */ null       ,
                /* colNumbering                 => */ 1          ,
                /* rowNumbering                 => */ 0          ,
                /* optionalFields               => */ null       ,
                /* timestamp                    => */ 880        );
        org.junit.Assert.assertNull(templateModel.getExcludedColsAsJson());
    }

    @org.junit.Test
    public void isExcludedRowSucceeds()
    {
        final org.wheatgenetics.coordinate.model.TemplateModel templateModel =
            new org.wheatgenetics.coordinate.model.TemplateModel(
                /* id                           => */ 10         ,
                /* title                        => */ "testTitle",
                /* code                         => */ 1          ,
                /* rows                         => */ 5          ,
                /* cols                         => */ 2          ,
                /* generatedExcludedCellsAmount => */ 0          ,
                /* initialExcludedCells         => */ null       ,
                /* excludedRows                 => */ null       ,
                /* excludedCols                 => */ null       ,
                /* colNumbering                 => */ 1          ,
                /* rowNumbering                 => */ 0          ,
                /* optionalFields               => */ null       ,
                /* timestamp                    => */ 880        );
        org.junit.Assert.assertFalse(templateModel.isExcludedRow(1));
    }

    @org.junit.Test
    public void isExcludedColSucceeds()
    {
        final org.wheatgenetics.coordinate.model.TemplateModel templateModel =
            new org.wheatgenetics.coordinate.model.TemplateModel(
                /* id                           => */ 10         ,
                /* title                        => */ "testTitle",
                /* code                         => */ 1          ,
                /* rows                         => */ 5          ,
                /* cols                         => */ 2          ,
                /* generatedExcludedCellsAmount => */ 0          ,
                /* initialExcludedCells         => */ null       ,
                /* excludedRows                 => */ null       ,
                /* excludedCols                 => */ null       ,
                /* colNumbering                 => */ 1          ,
                /* rowNumbering                 => */ 0          ,
                /* optionalFields               => */ null       ,
                /* timestamp                    => */ 880        );
        org.junit.Assert.assertFalse(templateModel.isExcludedCol(1));
    }
    // endregion

    // region optionalFields Public Method Tests
    @org.junit.Test
    public void optionalFieldsMethodsSucceed()
    {
        final org.wheatgenetics.coordinate.model.TemplateModel templateModel =
            new org.wheatgenetics.coordinate.model.TemplateModel(
                /* id                           => */ 10         ,
                /* title                        => */ "testTitle",
                /* code                         => */ 1          ,
                /* rows                         => */ 5          ,
                /* cols                         => */ 5          ,
                /* generatedExcludedCellsAmount => */ 0          ,
                /* initialExcludedCells         => */ null       ,
                /* excludedRows                 => */ null       ,
                /* excludedCols                 => */ null       ,
                /* colNumbering                 => */ 1          ,
                /* rowNumbering                 => */ 0          ,
                /* optionalFields               => */ null       ,
                /* timestamp                    => */ 880        );
        org.junit.Assert.assertNull(templateModel.optionalFields       ());
        org.junit.Assert.assertTrue(templateModel.optionalFieldsIsEmpty());
        org.junit.Assert.assertNull(templateModel.optionalFieldsClone  ());
    }
    // endregion

    // region checkedItems Public Method Tests
    @org.junit.Test
    public void rowCheckedItemsSucceeds()
    {
        final org.wheatgenetics.coordinate.model.TemplateModel templateModel =
            new org.wheatgenetics.coordinate.model.TemplateModel(
                /* id                           => */ 10         ,
                /* title                        => */ "testTitle",
                /* code                         => */ 1          ,
                /* rows                         => */ 5          ,
                /* cols                         => */ 2          ,
                /* generatedExcludedCellsAmount => */ 0          ,
                /* initialExcludedCells         => */ null       ,
                /* excludedRows                 => */ null       ,
                /* excludedCols                 => */ null       ,
                /* colNumbering                 => */ 1          ,
                /* rowNumbering                 => */ 0          ,
                /* optionalFields               => */ null       ,
                /* timestamp                    => */ 880        );
        org.junit.Assert.assertArrayEquals(
            new boolean[] {false, false, false, false, false}, templateModel.rowCheckedItems());

        templateModel.addExcludedRow(4);
        org.junit.Assert.assertArrayEquals(
            new boolean[] {false, false, false, true, false}, templateModel.rowCheckedItems());
    }

    @org.junit.Test
    public void colCheckedItemsSucceeds()
    {
        final org.wheatgenetics.coordinate.model.TemplateModel templateModel =
            new org.wheatgenetics.coordinate.model.TemplateModel(
                /* id                           => */ 10         ,
                /* title                        => */ "testTitle",
                /* code                         => */ 1          ,
                /* rows                         => */ 5          ,
                /* cols                         => */ 2          ,
                /* generatedExcludedCellsAmount => */ 0          ,
                /* initialExcludedCells         => */ null       ,
                /* excludedRows                 => */ null       ,
                /* excludedCols                 => */ null       ,
                /* colNumbering                 => */ 1          ,
                /* rowNumbering                 => */ 0          ,
                /* optionalFields               => */ null       ,
                /* timestamp                    => */ 880        );
        org.junit.Assert.assertArrayEquals(
            new boolean[] {false, false}, templateModel.colCheckedItems());

        templateModel.addExcludedCol(1);
        org.junit.Assert.assertArrayEquals(
            new boolean[] {true, false}, templateModel.colCheckedItems());
    }
    // endregion
    // endregion
}