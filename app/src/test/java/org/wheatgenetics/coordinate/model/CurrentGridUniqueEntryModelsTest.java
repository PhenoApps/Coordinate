package org.wheatgenetics.coordinate.model;

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
 * org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel
 * org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.CheckException
 * org.wheatgenetics.coordinate.model.CurrentGridUniqueEntryModels
 * org.wheatgenetics.coordinate.model.CurrentGridUniqueEntryModels.DuplicateCheckException
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
public class CurrentGridUniqueEntryModelsTest extends java.lang.Object
implements org.wheatgenetics.coordinate.StringGetter
{
    // region org.wheatgenetics.coordinate.StringGetter Overridden Methods
    @java.lang.Override @androidx.annotation.Nullable public java.lang.String get(
    @androidx.annotation.StringRes final int resId)
    {
        // noinspection SwitchStatementWithTooFewBranches
        switch (resId) { default: org.junit.Assert.fail(); return null; }
    }

    @java.lang.Override @androidx.annotation.NonNull public java.lang.String getQuantity(
    @androidx.annotation.PluralsRes         final int                 resId     ,
    @androidx.annotation.IntRange(from = 0) final int                 quantity  ,
    @androidx.annotation.Nullable           final java.lang.Object... formatArgs)
    throws android.content.res.Resources.NotFoundException { org.junit.Assert.fail(); return null; }
    // endregion

    @org.junit.Test(expected = org.wheatgenetics.coordinate.model
        .CurrentGridUniqueEntryModels.DuplicateCheckException.class)
    public void checkThenSetThrows()
    throws org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.CheckException
    {
        final org.wheatgenetics.coordinate.model.CurrentGridUniqueEntryModels
            currentGridUniqueEntryModels =
                new org.wheatgenetics.coordinate.model.CurrentGridUniqueEntryModels(
                    1,1,2,this);
        {
            // Set first entry.
            final org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel
                checkedIncludedEntryModel =
                    new org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel(
                        1,1,1, currentGridUniqueEntryModels);
            checkedIncludedEntryModel.checkThenSetValue("1.1");                            // throws
            currentGridUniqueEntryModels.checkThenSet(checkedIncludedEntryModel);          // throws
        }
        {
            // Set same value but is not a duplicate since entry *replaces* first entry.
            final org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel
                checkedIncludedEntryModel =
                    new org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel(
                        1,1,1, currentGridUniqueEntryModels);
            checkedIncludedEntryModel.checkThenSetValue("1.1");                            // throws
            currentGridUniqueEntryModels.checkThenSet(checkedIncludedEntryModel);          // throws
        }
        {
            // Set different value so is not a duplicate.
            final org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel
                checkedIncludedEntryModel =
                    new org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel(
                        1,1,2, currentGridUniqueEntryModels);
            checkedIncludedEntryModel.checkThenSetValue("2.2");                            // throws
            currentGridUniqueEntryModels.checkThenSet(checkedIncludedEntryModel);          // throws
        }
        {
            // Set same value in a different location: duplicate!
            final org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel
                checkedIncludedEntryModel =
                new org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel(
                    1,1,1, currentGridUniqueEntryModels);
            checkedIncludedEntryModel.checkThenSetValue("2.2");                            // throws
            currentGridUniqueEntryModels.checkThenSet(checkedIncludedEntryModel);          // throws
        }
    }
}