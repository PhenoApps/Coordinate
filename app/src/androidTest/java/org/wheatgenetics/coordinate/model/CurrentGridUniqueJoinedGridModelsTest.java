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
 * org.wheatgenetics.coordinate.model.CurrentGridUniqueJoinedGridModel
 * org.wheatgenetics.coordinate.model.CurrentGridUniqueJoinedGridModels
 * org.wheatgenetics.coordinate.model.JoinedGridModel
 */
@SuppressWarnings({"ClassExplicitlyExtendsObject"})
public class CurrentGridUniqueJoinedGridModelsTest extends Object
implements StringGetter
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

    @Test() public void addWorks()
    {
        final CurrentGridUniqueJoinedGridModels
            currentGridUniqueJoinedGridModels = new
            CurrentGridUniqueJoinedGridModels(this);
        Assert.assertFalse (currentGridUniqueJoinedGridModels.add(null));
        Assert.assertEquals(0, currentGridUniqueJoinedGridModels.size());

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
            Assert.assertFalse(currentGridUniqueJoinedGridModels.add(joinedGridModel));
            Assert.assertEquals(0, currentGridUniqueJoinedGridModels.size());
        }

        {
            final CurrentGridUniqueJoinedGridModel
                currentGridUniqueJoinedGridModel =
                    new CurrentGridUniqueJoinedGridModel(
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

                        /* currentGridUniqueEntryModels => */null);
            Assert.assertTrue(
                currentGridUniqueJoinedGridModels.add(currentGridUniqueJoinedGridModel));
            Assert.assertEquals(
                currentGridUniqueJoinedGridModel, currentGridUniqueJoinedGridModels.get(0));
        }
        Assert.assertEquals(1, currentGridUniqueJoinedGridModels.size());
    }
}