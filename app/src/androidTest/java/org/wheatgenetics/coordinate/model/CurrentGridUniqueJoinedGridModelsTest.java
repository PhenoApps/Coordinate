package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * org.junit.Assert
 * org.junit.Test
 *
 * org.wheatgenetics.coordinate.model.CurrentGridUniqueJoinedGridModel
 * org.wheatgenetics.coordinate.model.CurrentGridUniqueJoinedGridModels
 * org.wheatgenetics.coordinate.model.JoinedGridModel
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
public class CurrentGridUniqueJoinedGridModelsTest extends java.lang.Object
{
    @org.junit.Test() public void addWorks()
    {
        final org.wheatgenetics.coordinate.model.CurrentGridUniqueJoinedGridModels
            currentGridUniqueJoinedGridModels =
                new org.wheatgenetics.coordinate.model.CurrentGridUniqueJoinedGridModels();
        org.junit.Assert.assertFalse (currentGridUniqueJoinedGridModels.add(null));
        org.junit.Assert.assertEquals(0, currentGridUniqueJoinedGridModels.size());

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
            org.junit.Assert.assertFalse(currentGridUniqueJoinedGridModels.add(joinedGridModel));
            org.junit.Assert.assertEquals(0, currentGridUniqueJoinedGridModels.size());
        }

        {
            final org.wheatgenetics.coordinate.model.CurrentGridUniqueJoinedGridModel
                currentGridUniqueJoinedGridModel =
                    new org.wheatgenetics.coordinate.model.CurrentGridUniqueJoinedGridModel(
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

                        /* currentGridUniqueEntryModels => */null);
            org.junit.Assert.assertTrue(
                currentGridUniqueJoinedGridModels.add(currentGridUniqueJoinedGridModel));
            org.junit.Assert.assertEquals(
                currentGridUniqueJoinedGridModel, currentGridUniqueJoinedGridModels.get(0));
        }
        org.junit.Assert.assertEquals(1, currentGridUniqueJoinedGridModels.size());
    }
}