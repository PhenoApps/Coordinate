package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * org.junit.Assert
 * org.junit.Test
 *
 * org.wheatgenetics.javalib.Utils
 *
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
            org.junit.Assert.assertTrue(joinedGridModels.add(joinedGridModel)          );
            org.junit.Assert.assertTrue(joinedGridModel.equals(joinedGridModels.get(0)));
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
            private final long gridId;

            private Processor(final long gridId) { super(); this.gridId = gridId; }

            @java.lang.Override public void process(
            final org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel)
            {
                assert null != joinedGridModel;
                org.junit.Assert.assertEquals(this.gridId, joinedGridModel.getId());
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
                    /* excludedRows                 => */ excludedRows.json(),
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
        org.junit.Assert.assertEquals(cols, joinedGridModels.excludedCells(rows, cols).size());

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
                    /* excludedCols                 => */ excludedCols.json(),
                    /* colNumbering                 => */1,
                    /* rowNumbering                 => */0,
                    /* entryLabel                   => */null,
                    /* templateOptionalFields       => */null,
                    /* templateTimestamp            => */333,

                    /* entryModels => */null);
            }
            org.junit.Assert.assertTrue(joinedGridModels.add(joinedGridModel));
        }
        org.junit.Assert.assertEquals(5,
            joinedGridModels.excludedCells(rows, cols).size());
    }
}