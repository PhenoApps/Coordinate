package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * org.junit.Test
 *
 * org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel
 * org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.CheckException
 * org.wheatgenetics.coordinate.model.CurrentGridUniqueEntryModels
 * org.wheatgenetics.coordinate.model.CurrentGridUniqueEntryModels.DuplicateCheckException
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
public class CurrentGridUniqueEntryModelsTest extends java.lang.Object
{
    @org.junit.Test(expected = org.wheatgenetics.coordinate.model
        .CurrentGridUniqueEntryModels.DuplicateCheckException.class)
    public void checkThenSetThrows()
    throws org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.CheckException
    {
        final org.wheatgenetics.coordinate.model.CurrentGridUniqueEntryModels
            currentGridUniqueEntryModels =
                new org.wheatgenetics.coordinate.model.CurrentGridUniqueEntryModels(
                    1,1,2);
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