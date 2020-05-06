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
import org.wheatgenetics.javalib.Utils;

/**
 * Uses:
 * android.content.res.Resources.NotFoundException
 *
 * androidx.annotation.IntRange
 * androidx.annotation.Nullable
 * androidx.annotation.PluralsRes
 * androidx.annotation.StringRes
 *
 * org.junit.Assert
 * org.junit.Test
 *
 * org.wheatgenetics.javalib.Utils
 *
 * org.wheatgenetics.coordinate.StringGetter
 *
 * org.wheatgenetics.coordinate.model.Cells
 * org.wheatgenetics.coordinate.model.JoinedGridModel
 * org.wheatgenetics.coordinate.model.JoinedGridModels
 * org.wheatgenetics.coordinate.model.JoinedGridModels.Processor
 * org.wheatgenetics.coordinate.model.RowOrCols
 */
@SuppressWarnings({"ClassExplicitlyExtendsObject"}) public class JoinedGridModelsTest
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

    // region Overridden Method Tests
    @Test() public void addAndSizeAndGetWork()
    {
        final JoinedGridModels joinedGridModels =
            new JoinedGridModels(this);
        Assert.assertFalse (joinedGridModels.add(null));
        Assert.assertEquals(0, joinedGridModels.size());

        {
            final JoinedGridModel joinedGridModel =
                new JoinedGridModel(
                    /* id                           => */5,
                    /* projectId                    => */0,
                    /* person                       => */"testPerson",
                    /* activeRow                    => */0,
                    /* activeCol                    => */0,
                    /* optionalFields               => */null,
                    /* stringGetter                 => */this,
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
            Assert.assertTrue  (joinedGridModels.add(joinedGridModel)   );
            Assert.assertEquals(joinedGridModel, joinedGridModels.get(0));
        }
        Assert.assertEquals(1, joinedGridModels.size());
    }

    @Test() public void getWorks()
    {
        final JoinedGridModels joinedGridModels =
            new JoinedGridModels(this);
        Assert.assertNull(joinedGridModels.get(0  ));
        Assert.assertNull(joinedGridModels.get(999));
        Assert.assertNull(joinedGridModels.get( -5));
    }

    @Test() public void processAllWorks()
    {
        final JoinedGridModels joinedGridModels =
            new JoinedGridModels(this);

        class Processor extends Object
        implements JoinedGridModels.Processor
        {
            @IntRange(from = 1) private final long gridId;

            private Processor(@SuppressWarnings({"SameParameterValue"})
            @IntRange(from = 1) final long gridId)
            { super(); this.gridId = gridId; }

            @Override public void process(
            final JoinedGridModel joinedGridModel)
            {
                Assert.assertNotNull(joinedGridModel                     );
                Assert.assertEquals (this.gridId, joinedGridModel.getId());
            }
        }
        final Processor processor;

        {
            final long gridId = 5;
            {
                final JoinedGridModel joinedGridModel =
                    new JoinedGridModel(
                        /* id                           => */ gridId,
                        /* projectId                    => */0,
                        /* person                       => */"testPerson",
                        /* activeRow                    => */0,
                        /* activeCol                    => */0,
                        /* optionalFields               => */null,
                        /* stringGetter                 => */this,
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
                Assert.assertTrue(joinedGridModels.add(joinedGridModel));
            }

            processor = new Processor(gridId);
        }
        joinedGridModels.processAll(processor);
    }
    // endregion

    // region Public Method Tests
    @Test() public void namesWorks()
    {
        final JoinedGridModels joinedGridModels =
            new JoinedGridModels(this);
        Assert.assertNull(joinedGridModels.names());

        final JoinedGridModel joinedGridModel =
            new JoinedGridModel(
                /* id                           => */5,
                /* projectId                    => */0,
                /* person                       => */"testPerson",
                /* activeRow                    => */0,
                /* activeCol                    => */0,
                /* optionalFields               => */null,
                /* stringGetter                 => */this,
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
        Assert.assertTrue       (joinedGridModels.add(joinedGridModel));
        Assert.assertArrayEquals(
            Utils.stringArray(joinedGridModel.name()),
            joinedGridModels.names()                                           );
    }

    @Test() public void excludedCellsWorks()
    {
        final int                                                 rows             = 3, cols = 3;
        final JoinedGridModels joinedGridModels =
            new JoinedGridModels(this);
        Assert.assertNull(joinedGridModels.excludedCells(
            /* maxRow => */ rows, /* maxCol => */ cols));

        {
            final JoinedGridModel joinedGridModel;
            {
                final RowOrCols excludedRows =
                    new RowOrCols(rows,this);
                excludedRows.add(1);
                joinedGridModel = new JoinedGridModel(
                    /* id             => */5,
                    /* projectId      => */0,
                    /* person         => */"testPerson",
                    /* activeRow      => */0,
                    /* activeCol      => */0,
                    /* optionalFields => */null,
                    /* stringGetter   => */this,
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
            Assert.assertTrue(joinedGridModels.add(joinedGridModel));
        }
        {
            final Cells excludedCells =
                joinedGridModels.excludedCells(rows, cols);
            Assert.assertNotNull(excludedCells             );
            Assert.assertEquals (cols, excludedCells.size());
        }

        {
            final JoinedGridModel joinedGridModel;
            {
                final RowOrCols excludedCols =
                    new RowOrCols(cols,this);
                excludedCols.add(1);
                joinedGridModel = new JoinedGridModel(
                    /* id             => */5,
                    /* projectId      => */0,
                    /* person         => */"testPerson",
                    /* activeRow      => */0,
                    /* activeCol      => */0,
                    /* optionalFields => */null,
                    /* stringGetter   => */this,
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
            Assert.assertTrue(joinedGridModels.add(joinedGridModel));
        }
        final Cells excludedCells =
            joinedGridModels.excludedCells(rows, cols);
        Assert.assertNotNull(excludedCells);
        Assert.assertEquals (rows + cols - 1, excludedCells.size());
    }
    // endregion
}