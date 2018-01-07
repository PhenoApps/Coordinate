package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * android.annotation.SuppressLint
 *
 * org.json.JSONArray
 *
 * org.junit.Assert
 * org.junit.Test
 *
 * org.wheatgenetics.androidlibrary.Utils
 *
 * org.wheatgenetics.coordinate.model.JoinedGridModel
 * org.wheatgenetics.coordinate.model.TemplateModel
 */
@java.lang.SuppressWarnings("ClassExplicitlyExtendsObject")
public class JoinedGridModelTest extends java.lang.Object
{
    @org.junit.Test @android.annotation.SuppressLint("DefaultLocale")
    public void nameSucceeds()
    {
        final java.lang.String person    = "testPerson", title = "testTitle";
        final int              rows      = 5           , cols  = 2          ;
        final long             timestamp = 123                              ;

        final java.lang.String expectedName = java.lang.String.format(
            "Person: %s\n Template: %s\n Size: (%d, %d) Date: %s\n", person, title,
            cols, rows, org.wheatgenetics.androidlibrary.Utils.formatDate(timestamp));
        final org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel =
            new org.wheatgenetics.coordinate.model.JoinedGridModel(
                /* id                           => */ 5            ,
                /* person                       => */ person    ,
                /* excludedCells                => */ null         ,
                /* optionalFields               => */ null         ,
                /* timestamp                    => */ timestamp    ,

                /* templateId                   => */ 6            ,
                /* title                        => */ title        ,
                /* code                         => */ 1            ,
                /* rows                         => */ rows         ,
                /* cols                         => */ cols         ,
                /* generatedExcludedCellsAmount => */ 0            ,
                /* excludedCells                => */ null         ,
                /* excludedRows                 => */ null         ,
                /* excludedCols                 => */ null         ,
                /* colNumbering                 => */ 1            ,
                /* rowNumbering                 => */ 0            ,
                /* templateOptionalFields       => */ null         ,
                /* templateTimestamp            => */ 333          );
        org.junit.Assert.assertEquals(expectedName, joinedGridModel.name());
    }

    // region Public Method Tests
    @org.junit.Test
    public void populateSucceeds()
    {
        final long             templateId = 55555      ;
        final java.lang.String title      = "testTitle";
        final int code = 0, rows = 5, cols = 2, generatedExcludedCellsAmount = 0,
            colNumbering = 1, rowNumbering = 0;
        final long timestamp = 123;

        final org.wheatgenetics.coordinate.model.TemplateModel
            expectedTemplateModel = new org.wheatgenetics.coordinate.model.TemplateModel(
                /* id                           => */ templateId                  ,
                /* title                        => */ title                       ,
                /* code                         => */ code                        ,
                /* rows                         => */ rows                        ,
                /* cols                         => */ cols                        ,
                /* generatedExcludedCellsAmount => */ generatedExcludedCellsAmount,
                /* initialExcludedCells         => */ null                        ,
                /* excludedRows                 => */ null                        ,
                /* excludedCols                 => */ null                        ,
                /* colNumbering                 => */ colNumbering                ,
                /* rowNumbering                 => */ rowNumbering                ,
                /* templateOptionalFields       => */ null                        ,
                /* timestamp                    => */ timestamp                   ),
            actualTemplateModel = new org.wheatgenetics.coordinate.model.TemplateModel(
                /* id                           => */ templateId ,
                /* title                        => */ "different",
                /* code                         => */ 1          ,
                /* rows                         => */ 50         ,
                /* cols                         => */ 20         ,
                /* generatedExcludedCellsAmount => */ 3          ,
                /* initialExcludedCells         => */ null       ,
                /* excludedRows                 => */ null       ,
                /* excludedCols                 => */ null       ,
                /* colNumbering                 => */ 0          ,
                /* rowNumbering                 => */ 1          ,
                /* templateOptionalFields       => */ null       ,
                /* timestamp                    => */ timestamp  );
        org.junit.Assert.assertFalse(expectedTemplateModel.equals(actualTemplateModel));

        {
            final org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel =
                new org.wheatgenetics.coordinate.model.JoinedGridModel(
                    /* id                           => */ 5                           ,
                    /* person                       => */ "person"                 ,
                    /* excludedCells                => */ null                        ,
                    /* optionalFields               => */ null                        ,
                    /* timestamp                    => */ 456                         ,

                    /* templateId                   => */ templateId                  ,
                    /* title                        => */ title                       ,
                    /* code                         => */ code                        ,
                    /* rows                         => */ rows                        ,
                    /* cols                         => */ cols                        ,
                    /* generatedExcludedCellsAmount => */ generatedExcludedCellsAmount,
                    /* initialExcludedCells         => */ null                        ,
                    /* excludedRows                 => */ null                        ,
                    /* excludedCols                 => */ null                        ,
                    /* colNumbering                 => */ colNumbering                ,
                    /* rowNumbering                 => */ rowNumbering                ,
                    /* templateOptionalFields       => */ null                        ,
                    /* templateTimestamp            => */ 333                         );
            joinedGridModel.populate(actualTemplateModel);
        }
        org.junit.Assert.assertTrue(expectedTemplateModel.equals(actualTemplateModel));
    }

    // region addExcludedCell() Public Method Tests
    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void tooSmallRowAddExcludedCellFails()
    {
        final org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel =
            new org.wheatgenetics.coordinate.model.JoinedGridModel(
                    /* id                           => */ 5          ,
                    /* person                       => */ "person"   ,
                    /* excludedCells                => */ null       ,
                    /* optionalFields               => */ null       ,
                    /* timestamp                    => */ 456        ,

                    /* templateId                   => */ 55555      ,
                    /* title                        => */ "testTitle",
                    /* code                         => */ 0          ,
                    /* rows                         => */ 5          ,
                    /* cols                         => */ 2          ,
                    /* generatedExcludedCellsAmount => */ 0          ,
                    /* initialExcludedCells         => */ null       ,
                    /* excludedRows                 => */ null       ,
                    /* excludedCols                 => */ null       ,
                    /* colNumbering                 => */ 1          ,
                    /* rowNumbering                 => */ 0          ,
                    /* templateOptionalFields       => */ null       ,
                    /* templateTimestamp            => */ 333        );
        joinedGridModel.addExcludedCell(0, 1);
    }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void tooBigRowAddExcludedCellFails()
    {
        final org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel =
            new org.wheatgenetics.coordinate.model.JoinedGridModel(
                    /* id                           => */ 5          ,
                    /* person                       => */ "person"   ,
                    /* excludedCells                => */ null       ,
                    /* optionalFields               => */ null       ,
                    /* timestamp                    => */ 456        ,

                    /* templateId                   => */ 55555      ,
                    /* title                        => */ "testTitle",
                    /* code                         => */ 0          ,
                    /* rows                         => */ 5          ,
                    /* cols                         => */ 2          ,
                    /* generatedExcludedCellsAmount => */ 0          ,
                    /* initialExcludedCells         => */ null       ,
                    /* excludedRows                 => */ null       ,
                    /* excludedCols                 => */ null       ,
                    /* colNumbering                 => */ 1          ,
                    /* rowNumbering                 => */ 0          ,
                    /* templateOptionalFields       => */ null       ,
                    /* templateTimestamp            => */ 333        );
        joinedGridModel.addExcludedCell(10, 1);
    }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void tooSmallColAddExcludedCellFails()
    {
        final org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel =
            new org.wheatgenetics.coordinate.model.JoinedGridModel(
                    /* id                           => */ 5          ,
                    /* person                       => */ "person"   ,
                    /* excludedCells                => */ null       ,
                    /* optionalFields               => */ null       ,
                    /* timestamp                    => */ 456        ,

                    /* templateId                   => */ 55555      ,
                    /* title                        => */ "testTitle",
                    /* code                         => */ 0          ,
                    /* rows                         => */ 5          ,
                    /* cols                         => */ 2          ,
                    /* generatedExcludedCellsAmount => */ 0          ,
                    /* initialExcludedCells         => */ null       ,
                    /* excludedRows                 => */ null       ,
                    /* excludedCols                 => */ null       ,
                    /* colNumbering                 => */ 1          ,
                    /* rowNumbering                 => */ 0          ,
                    /* templateOptionalFields       => */ null       ,
                    /* templateTimestamp            => */ 333        );
        joinedGridModel.addExcludedCell(1, -1);
    }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void tooBigColAddExcludedCellFails()
    {
        final org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel =
            new org.wheatgenetics.coordinate.model.JoinedGridModel(
                    /* id                           => */ 5          ,
                    /* person                       => */ "person"   ,
                    /* excludedCells                => */ null       ,
                    /* optionalFields               => */ null       ,
                    /* timestamp                    => */ 456        ,

                    /* templateId                   => */ 55555      ,
                    /* title                        => */ "testTitle",
                    /* code                         => */ 0          ,
                    /* rows                         => */ 5          ,
                    /* cols                         => */ 2          ,
                    /* generatedExcludedCellsAmount => */ 0          ,
                    /* initialExcludedCells         => */ null       ,
                    /* excludedRows                 => */ null       ,
                    /* excludedCols                 => */ null       ,
                    /* colNumbering                 => */ 1          ,
                    /* rowNumbering                 => */ 0          ,
                    /* templateOptionalFields       => */ null       ,
                    /* templateTimestamp            => */ 333        );
        joinedGridModel.addExcludedCell(1, 111);
    }

    @org.junit.Test
    public void addExcludedCellSucceeds()
    {
        final org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel =
            new org.wheatgenetics.coordinate.model.JoinedGridModel(
                    /* id                           => */ 5          ,
                    /* person                       => */ "person"   ,
                    /* excludedCells                => */ null       ,
                    /* optionalFields               => */ null       ,
                    /* timestamp                    => */ 456        ,

                    /* templateId                   => */ 55555      ,
                    /* title                        => */ "testTitle",
                    /* code                         => */ 0          ,
                    /* rows                         => */ 5          ,
                    /* cols                         => */ 2          ,
                    /* generatedExcludedCellsAmount => */ 0          ,
                    /* initialExcludedCells         => */ null       ,
                    /* excludedRows                 => */ null       ,
                    /* excludedCols                 => */ null       ,
                    /* colNumbering                 => */ 1          ,
                    /* rowNumbering                 => */ 0          ,
                    /* templateOptionalFields       => */ null       ,
                    /* templateTimestamp            => */ 333        );
        joinedGridModel.addExcludedCell(1, 1);
    }
    // endregion

    @org.junit.Test
    public void isExcludedCellSucceeds()
    {
        final org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel =
            new org.wheatgenetics.coordinate.model.JoinedGridModel(
                    /* id                           => */ 5          ,
                    /* person                       => */ "person"   ,
                    /* excludedCells                => */ null       ,
                    /* optionalFields               => */ null       ,
                    /* timestamp                    => */ 456        ,

                    /* templateId                   => */ 55555      ,
                    /* title                        => */ "testTitle",
                    /* code                         => */ 0          ,
                    /* rows                         => */ 5          ,
                    /* cols                         => */ 2          ,
                    /* generatedExcludedCellsAmount => */ 0          ,
                    /* initialExcludedCells         => */ null       ,
                    /* excludedRows                 => */ null       ,
                    /* excludedCols                 => */ null       ,
                    /* colNumbering                 => */ 1          ,
                    /* rowNumbering                 => */ 0          ,
                    /* templateOptionalFields       => */ null       ,
                    /* templateTimestamp            => */ 333        );
        org.junit.Assert.assertFalse(joinedGridModel.isExcludedCell(1, 1));
    }

    // region nextFreeCell() Public Method Tests
    @org.junit.Test
    public void nextFreeCellFailsAndSucceeds()
    {
        org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel;
        {
            final org.json.JSONArray
                excludedRowsJSONArray = new org.json.JSONArray(),
                excludedColsJSONArray = new org.json.JSONArray();
            excludedRowsJSONArray.put(3); excludedColsJSONArray.put(2);
            joinedGridModel = new org.wheatgenetics.coordinate.model.JoinedGridModel(
                /* id                           => */ 5                               ,
                /* person                       => */ "person"                     ,
                /* excludedCells                => */ null                            ,
                /* optionalFields               => */ null                            ,
                /* timestamp                    => */ 456                             ,

                /* templateId                   => */ 55555                           ,
                /* title                        => */ "test"                          ,
                /* code                         => */ 0                               ,
                /* rows                         => */ 5                               ,
                /* cols                         => */ 5                               ,
                /* generatedExcludedCellsAmount => */ 0                               ,
                /* initialExcludedCells         => */ null                            ,
                /* excludedRows                 => */ excludedRowsJSONArray.toString(),
                /* excludedCols                 => */ excludedColsJSONArray.toString(),
                /* colNumbering                 => */ 1                               ,
                /* rowNumbering                 => */ 0                               ,
                /* templateOptionalFields       => */ null                            ,
                /* templateTimestamp            => */ 333                             );
        }

        org.junit.Assert.assertNull(joinedGridModel.nextFreeCell(null));

        {
            final org.wheatgenetics.coordinate.model.Cell
                candidateFreeCell = new org.wheatgenetics.coordinate.model.Cell(1, 1),
                expectedNextCell  = new org.wheatgenetics.coordinate.model.Cell(1, 1);
            org.junit.Assert.assertTrue(
                expectedNextCell.equals(joinedGridModel.nextFreeCell(candidateFreeCell)));
        }

        {
            final org.wheatgenetics.coordinate.model.Cell
                candidateFreeCell = new org.wheatgenetics.coordinate.model.Cell(5, 5),
                expectedNextCell  = new org.wheatgenetics.coordinate.model.Cell(5, 5);
            org.junit.Assert.assertTrue(
                expectedNextCell.equals(joinedGridModel.nextFreeCell(candidateFreeCell)));
        }

        {
            final org.wheatgenetics.coordinate.model.Cell
                candidateFreeCell = new org.wheatgenetics.coordinate.model.Cell(2, 2),
                expectedNextCell  = new org.wheatgenetics.coordinate.model.Cell(1, 3);
            org.junit.Assert.assertTrue(
                expectedNextCell.equals(joinedGridModel.nextFreeCell(candidateFreeCell)));
        }

        {
            final org.wheatgenetics.coordinate.model.Cell
                candidateFreeCell = new org.wheatgenetics.coordinate.model.Cell(3, 3),
                expectedNextCell  = new org.wheatgenetics.coordinate.model.Cell(4, 3);
            org.junit.Assert.assertTrue(
                expectedNextCell.equals(joinedGridModel.nextFreeCell(candidateFreeCell)));
        }

        {
            final org.wheatgenetics.coordinate.model.Cell
                candidateFreeCell = new org.wheatgenetics.coordinate.model.Cell(3, 2),
                expectedNextCell  = new org.wheatgenetics.coordinate.model.Cell(1, 3);
            org.junit.Assert.assertTrue(
                expectedNextCell.equals(joinedGridModel.nextFreeCell(candidateFreeCell)));
        }

        {
            final org.wheatgenetics.coordinate.model.Cell
                candidateFreeCell = new org.wheatgenetics.coordinate.model.Cell(3, 5),
                expectedNextCell  = new org.wheatgenetics.coordinate.model.Cell(4, 5);
            org.junit.Assert.assertTrue(
                expectedNextCell.equals(joinedGridModel.nextFreeCell(candidateFreeCell)));
        }

        joinedGridModel.addExcludedCell(4, 4);
        {
            final org.wheatgenetics.coordinate.model.Cell
                candidateFreeCell = new org.wheatgenetics.coordinate.model.Cell(4, 4),
                expectedNextCell  = new org.wheatgenetics.coordinate.model.Cell(5, 4);
            org.junit.Assert.assertTrue(
                expectedNextCell.equals(joinedGridModel.nextFreeCell(candidateFreeCell)));
        }

        joinedGridModel.addExcludedCell(5, 4);
        {
            final org.wheatgenetics.coordinate.model.Cell
                candidateFreeCell = new org.wheatgenetics.coordinate.model.Cell(5, 4),
                expectedNextCell  = new org.wheatgenetics.coordinate.model.Cell(1, 5);
            org.junit.Assert.assertTrue(
                expectedNextCell.equals(joinedGridModel.nextFreeCell(candidateFreeCell)));
        }

        joinedGridModel.addExcludedCell(5, 5);
        {
            final org.wheatgenetics.coordinate.model.Cell candidateFreeCell =
                new org.wheatgenetics.coordinate.model.Cell(5, 5);
            org.junit.Assert.assertNull(joinedGridModel.nextFreeCell(candidateFreeCell));
        }
    }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void tooBigRowCandidateFreeCellNextFreeCellFails()
    {
        final org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel =
            new org.wheatgenetics.coordinate.model.JoinedGridModel(
                /* id                           => */ 5          ,
                /* person                       => */ "person"   ,
                /* excludedCells                => */ null       ,
                /* optionalFields               => */ null       ,
                /* timestamp                    => */ 456        ,

                /* templateId                   => */ 55555      ,
                /* title                        => */ "testTitle",
                /* code                         => */ 0          ,
                /* rows                         => */ 5          ,
                /* cols                         => */ 5          ,
                /* generatedExcludedCellsAmount => */ 0          ,
                /* initialExcludedCells         => */ null       ,
                /* excludedRows                 => */ null       ,
                /* excludedCols                 => */ null       ,
                /* colNumbering                 => */ 1          ,
                /* rowNumbering                 => */ 0          ,
                /* templateOptionalFields       => */ null       ,
                /* templateTimestamp            => */ 333        );
        final org.wheatgenetics.coordinate.model.Cell candidateFreeCell =
            new org.wheatgenetics.coordinate.model.Cell(6, 1);
        joinedGridModel.nextFreeCell(candidateFreeCell);
    }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void tooBigColCandidateFreeCellNextFreeCellFails()
    {
        final org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel =
            new org.wheatgenetics.coordinate.model.JoinedGridModel(
                /* id                           => */ 5          ,
                /* person                       => */ "person"   ,
                /* excludedCells                => */ null       ,
                /* optionalFields               => */ null       ,
                /* timestamp                    => */ 456        ,

                /* templateId                   => */ 55555      ,
                /* title                        => */ "testTitle",
                /* code                         => */ 0          ,
                /* rows                         => */ 5          ,
                /* cols                         => */ 5          ,
                /* generatedExcludedCellsAmount => */ 0          ,
                /* initialExcludedCells         => */ null       ,
                /* excludedRows                 => */ null       ,
                /* excludedCols                 => */ null       ,
                /* colNumbering                 => */ 1          ,
                /* rowNumbering                 => */ 0          ,
                /* templateOptionalFields       => */ null       ,
                /* templateTimestamp + */ 333                );
        final org.wheatgenetics.coordinate.model.Cell candidateFreeCell =
            new org.wheatgenetics.coordinate.model.Cell(1, 6);
        joinedGridModel.nextFreeCell(candidateFreeCell);
    }
    // endregion
    // endregion
}