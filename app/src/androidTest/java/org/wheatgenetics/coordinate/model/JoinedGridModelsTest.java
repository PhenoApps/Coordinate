package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * android.support.annotation.IntRange
 *
 * org.junit.Assert
 * org.junit.Test
 *
 * org.wheatgenetics.javalib.Utils
 *
 * org.wheatgenetics.coordinate.model.Cells
 * org.wheatgenetics.coordinate.model.JoinedGridModel
 * org.wheatgenetics.coordinate.model.JoinedGridModels
 * org.wheatgenetics.coordinate.model.JoinedGridModels.Processor
 * org.wheatgenetics.coordinate.model.RowOrCols
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
public class JoinedGridModelsTest extends java.lang.Object
{
    @org.junit.Test() public void addAndSizeAndGetWork()
    {
        final org.wheatgenetics.coordinate.model.JoinedGridModels joinedGridModels =
            new org.wheatgenetics.coordinate.model.JoinedGridModels();
        org.junit.Assert.assertFalse (joinedGridModels.add(null));
        org.junit.Assert.assertEquals(0, joinedGridModels.size());

        {
            final org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel =
                new org.wheatgenetics.coordinate.model.JoinedGridModel(
                    /* id                           => */5,
                    /* projectId                    => */0,
                    /* person                       => */"testPerson",
                    /* activeRow                    => */0,
                    /* activeCol                    => */0,
                    /* optionalFields               => */null,
                    /* timestamp                    => */123,

                    /* templateId                   => */6,
                    /* title                        => */"testTitle",
                    /* code                         => */1,
                    /* rows                         => */5,
                    /* cols                         => */2,
                    /* generatedExcludedCellsAmount => */0,
                    /* initialExcludedCells         => */null,
                    /* excludedRows                 => */null,
                    /* excludedCols                 => */null,
                    /* colNumbering                 => */1,
                    /* rowNumbering                 => */0,
                    /* entryLabel                   => */null,
                    /* templateOptionalFields       => */null,
                    /* templateTimestamp            => */333,

                    /* entryModels                  => */null);
            org.junit.Assert.assertTrue  (joinedGridModels.add(joinedGridModel)   );
            org.junit.Assert.assertEquals(joinedGridModel, joinedGridModels.get(0));
        }
        org.junit.Assert.assertEquals(1, joinedGridModels.size());
    }

    @org.junit.Test() public void getWorks()
    {
        final org.wheatgenetics.coordinate.model.JoinedGridModels joinedGridModels =
            new org.wheatgenetics.coordinate.model.JoinedGridModels();
        org.junit.Assert.assertNull(joinedGridModels.get(0  ));
        org.junit.Assert.assertNull(joinedGridModels.get(999));
        org.junit.Assert.assertNull(joinedGridModels.get( -5));
    }

    @org.junit.Test() public void namesWorks()
    {
        final org.wheatgenetics.coordinate.model.JoinedGridModels joinedGridModels =
            new org.wheatgenetics.coordinate.model.JoinedGridModels();
        org.junit.Assert.assertNull(joinedGridModels.names());

        final org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel =
            new org.wheatgenetics.coordinate.model.JoinedGridModel(
                /* id                           => */5,
                /* projectId                    => */0,
                /* person                       => */"testPerson",
                /* activeRow                    => */0,
                /* activeCol                    => */0,
                /* optionalFields               => */null,
                /* timestamp                    => */123,

                /* templateId                   => */6,
                /* title                        => */"testTitle",
                /* code                         => */1,
                /* rows                         => */5,
                /* cols                         => */2,
                /* generatedExcludedCellsAmount => */0,
                /* initialExcludedCells         => */null,
                /* excludedRows                 => */null,
                /* excludedCols                 => */null,
                /* colNumbering                 => */1,
                /* rowNumbering                 => */0,
                /* entryLabel                   => */null,
                /* templateOptionalFields       => */null,
                /* templateTimestamp            => */333,

                /* entryModels                  => */null);
        org.junit.Assert.assertTrue       (joinedGridModels.add(joinedGridModel));
        org.junit.Assert.assertArrayEquals(
            org.wheatgenetics.javalib.Utils.stringArray(joinedGridModel.name()),
            joinedGridModels.names()                                           );
    }

    @org.junit.Test() public void processAllWorks()
    {
        final org.wheatgenetics.coordinate.model.JoinedGridModels joinedGridModels =
            new org.wheatgenetics.coordinate.model.JoinedGridModels();

        class Processor extends java.lang.Object
        implements org.wheatgenetics.coordinate.model.JoinedGridModels.Processor
        {
            @android.support.annotation.IntRange(from = 1) private final long gridId;

            private Processor(@java.lang.SuppressWarnings({"SameParameterValue"})
            @android.support.annotation.IntRange(from = 1) final long gridId)
            { super(); this.gridId = gridId; }

            @java.lang.Override public void process(
            final org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel)
            {
                org.junit.Assert.assertNotNull(joinedGridModel                     );
                org.junit.Assert.assertEquals (this.gridId, joinedGridModel.getId());
            }
        }
        final Processor processor;

        {
            final long gridId = 5;
            {
                final org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel =
                    new org.wheatgenetics.coordinate.model.JoinedGridModel(
                        /* id                           => */ gridId,
                        /* projectId                    => */0,
                        /* person                       => */"testPerson",
                        /* activeRow                    => */0,
                        /* activeCol                    => */0,
                        /* optionalFields               => */null,
                        /* timestamp                    => */123,

                        /* templateId                   => */6,
                        /* title                        => */"testTitle",
                        /* code                         => */1,
                        /* rows                         => */5,
                        /* cols                         => */2,
                        /* generatedExcludedCellsAmount => */0,
                        /* initialExcludedCells         => */null,
                        /* excludedRows                 => */null,
                        /* excludedCols                 => */null,
                        /* colNumbering                 => */1,
                        /* rowNumbering                 => */0,
                        /* entryLabel                   => */null,
                        /* templateOptionalFields       => */null,
                        /* templateTimestamp            => */333,

                        /* entryModels                  => */null);
                org.junit.Assert.assertTrue(joinedGridModels.add(joinedGridModel));
            }

            processor = new Processor(gridId);
        }
        joinedGridModels.processAll(processor);
    }

    @org.junit.Test() public void excludedCellsWorks()
    {
        final int                                                 rows             = 3, cols = 3;
        final org.wheatgenetics.coordinate.model.JoinedGridModels joinedGridModels =
            new org.wheatgenetics.coordinate.model.JoinedGridModels();
        org.junit.Assert.assertNull(joinedGridModels.excludedCells(
            /* maxRow => */ rows, /* maxCol => */ cols));

        {
            final org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel;
            {
                final org.wheatgenetics.coordinate.model.RowOrCols excludedRows =
                    new org.wheatgenetics.coordinate.model.RowOrCols(rows);
                excludedRows.add(1);
                joinedGridModel = new org.wheatgenetics.coordinate.model.JoinedGridModel(
                    /* id             => */5,
                    /* projectId      => */0,
                    /* person         => */"testPerson",
                    /* activeRow      => */0,
                    /* activeCol      => */0,
                    /* optionalFields => */null,
                    /* timestamp      => */123,

                    /* templateId                   => */6,
                    /* title                        => */"testTitle",
                    /* code                         => */1,
                    /* rows                         => */ rows,
                    /* cols                         => */ cols,
                    /* generatedExcludedCellsAmount => */0,
                    /* initialExcludedCells         => */null,
                    /* excludedRows                 => */ excludedRows.json(),          // Not null.
                    /* excludedCols                 => */null,
                    /* colNumbering                 => */1,
                    /* rowNumbering                 => */0,
                    /* entryLabel                   => */null,
                    /* templateOptionalFields       => */null,
                    /* templateTimestamp            => */333,

                    /* entryModels => */null);
            }
            org.junit.Assert.assertTrue(joinedGridModels.add(joinedGridModel));
        }
        {
            final org.wheatgenetics.coordinate.model.Cells excludedCells =
                joinedGridModels.excludedCells(rows, cols);
            org.junit.Assert.assertNotNull(excludedCells             );
            org.junit.Assert.assertEquals (cols, excludedCells.size());
        }

        {
            final org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel;
            {
                final org.wheatgenetics.coordinate.model.RowOrCols excludedCols =
                    new org.wheatgenetics.coordinate.model.RowOrCols(cols);
                excludedCols.add(1);
                joinedGridModel = new org.wheatgenetics.coordinate.model.JoinedGridModel(
                    /* id             => */5,
                    /* projectId      => */0,
                    /* person         => */"testPerson",
                    /* activeRow      => */0,
                    /* activeCol      => */0,
                    /* optionalFields => */null,
                    /* timestamp      => */123,

                    /* templateId                   => */6,
                    /* title                        => */"testTitle",
                    /* code                         => */1,
                    /* rows                         => */ rows,
                    /* cols                         => */ cols,
                    /* generatedExcludedCellsAmount => */0,
                    /* initialExcludedCells         => */null,
                    /* excludedRows                 => */null,
                    /* excludedCols                 => */ excludedCols.json(),          // Not null.
                    /* colNumbering                 => */1,
                    /* rowNumbering                 => */0,
                    /* entryLabel                   => */null,
                    /* templateOptionalFields       => */null,
                    /* templateTimestamp            => */333,

                    /* entryModels => */null);
            }
            org.junit.Assert.assertTrue(joinedGridModels.add(joinedGridModel));
        }
        final org.wheatgenetics.coordinate.model.Cells excludedCells =
            joinedGridModels.excludedCells(rows, cols);
        org.junit.Assert.assertNotNull(excludedCells);
        org.junit.Assert.assertEquals (rows + cols - 1, excludedCells.size());
    }
}