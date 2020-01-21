package org.wheatgenetics.coordinate.collector;

/**
 * Uses:
 * android.content.Context
 *
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 *
 * org.wheatgenetics.coordinate.database.AllGridsUniqueEntriesTable
 * org.wheatgenetics.coordinate.database.AllGridsUniqueGridsTable
 * org.wheatgenetics.coordinate.database.CurrentGridUniqueEntriesTable
 * org.wheatgenetics.coordinate.database.CurrentGridUniqueGridsTable
 * org.wheatgenetics.coordinate.database.CurrentProjectUniqueEntriesTable
 * org.wheatgenetics.coordinate.database.CurrentProjectUniqueGridsTable
 * org.wheatgenetics.coordinate.database.EntriesTable
 * org.wheatgenetics.coordinate.database.GridsTable
 *
 * org.wheatgenetics.coordinate.model.AllGridsUniqueJoinedGridModel
 * org.wheatgenetics.coordinate.model.CurrentGridUniqueJoinedGridModel
 * org.wheatgenetics.coordinate.model.CurrentProjectUniqueJoinedGridModel
 * org.wheatgenetics.coordinate.model.JoinedGridModel
 *
 * org.wheatgenetics.coordinate.preference.Utils
 * org.wheatgenetics.coordinate.preference.Utils.TypeOfUniqueness
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
class Utils extends java.lang.Object
{
    // region Table Methods
    @androidx.annotation.Nullable
    static org.wheatgenetics.coordinate.database.GridsTable gridsTable(
    @androidx.annotation.Nullable final org.wheatgenetics.coordinate.database.GridsTable
        gridsTableInstance,
    @androidx.annotation.NonNull final android.content.Context context)
    {
        if (null == gridsTableInstance)
            if (org.wheatgenetics.coordinate.preference.Utils.getUniqueness(context))
                switch (org.wheatgenetics.coordinate.preference.Utils.getTypeOfUniqueness(context))
                {
                    case CURRENT_GRID: return new
                        org.wheatgenetics.coordinate.database.CurrentGridUniqueGridsTable(
                            context);

                    case CURRENT_PROJECT: return new
                        org.wheatgenetics.coordinate.database.CurrentProjectUniqueGridsTable(
                            context);

                    case ALL_GRIDS:
                        return new org.wheatgenetics.coordinate.database.AllGridsUniqueGridsTable(
                            context);

                    default: return null;
                }
            else return new org.wheatgenetics.coordinate.database.GridsTable(context);
        else return gridsTableInstance;
    }

    @androidx.annotation.Nullable
    static org.wheatgenetics.coordinate.database.EntriesTable entriesTable(
    @androidx.annotation.Nullable final org.wheatgenetics.coordinate.database.EntriesTable
        entriesTableInstance,
    @androidx.annotation.Nullable final org.wheatgenetics.coordinate.database.GridsTable
        gridsTableInstance,
    @androidx.annotation.NonNull final android.content.Context context)
    {
        if (null == entriesTableInstance)
            if (org.wheatgenetics.coordinate.preference.Utils.getUniqueness(context))
                switch (org.wheatgenetics.coordinate.preference.Utils.getTypeOfUniqueness(context))
                {
                    case CURRENT_GRID: return new
                        org.wheatgenetics.coordinate.database.CurrentGridUniqueEntriesTable(
                            context);

                    case CURRENT_PROJECT:
                        final org.wheatgenetics.coordinate.database.CurrentProjectUniqueGridsTable
                            currentProjectUniqueGridsTable = (org.wheatgenetics
                                .coordinate.database.CurrentProjectUniqueGridsTable)
                                org.wheatgenetics.coordinate.collector.Utils.gridsTable(
                                    gridsTableInstance, context);
                        return null == currentProjectUniqueGridsTable ? null : new
                            org.wheatgenetics.coordinate.database.CurrentProjectUniqueEntriesTable(
                                context, currentProjectUniqueGridsTable);

                    case ALL_GRIDS:
                        return new org.wheatgenetics.coordinate.database.AllGridsUniqueEntriesTable(
                            context);

                    default: return null;
                }
            else return new org.wheatgenetics.coordinate.database.EntriesTable(context);
        else return entriesTableInstance;
    }
    // endregion

    // region needsReloading() Methods
    static boolean gridsTableNeedsReloading(@androidx.annotation.Nullable
        final org.wheatgenetics.coordinate.database.GridsTable gridsTableInstance,
    @androidx.annotation.NonNull final android.content.Context context)
    {
        if (null == gridsTableInstance)
            return false;
        else
            if (org.wheatgenetics.coordinate.preference.Utils.getUniqueness(context))
                switch (org.wheatgenetics.coordinate.preference.Utils.getTypeOfUniqueness(context))
                {
                    case CURRENT_GRID: return !(gridsTableInstance instanceof
                        org.wheatgenetics.coordinate.database.CurrentGridUniqueGridsTable);

                    case CURRENT_PROJECT: return !(gridsTableInstance instanceof
                        org.wheatgenetics.coordinate.database.CurrentProjectUniqueGridsTable);

                    case ALL_GRIDS: return !(gridsTableInstance instanceof
                        org.wheatgenetics.coordinate.database.AllGridsUniqueGridsTable);

                    default: return true;
                }
            else return
                gridsTableInstance instanceof
                    org.wheatgenetics.coordinate.database.CurrentGridUniqueGridsTable ||
                gridsTableInstance instanceof
                    org.wheatgenetics.coordinate.database.CurrentProjectUniqueGridsTable ||
                gridsTableInstance instanceof
                    org.wheatgenetics.coordinate.database.AllGridsUniqueGridsTable;
    }

    static boolean entriesTableNeedsReloading(@androidx.annotation.Nullable
        final org.wheatgenetics.coordinate.database.EntriesTable entriesTableInstance,
    @androidx.annotation.NonNull final android.content.Context context)
    {
        if (null == entriesTableInstance)
            return false;
        else
            if (org.wheatgenetics.coordinate.preference.Utils.getUniqueness(context))
                switch (org.wheatgenetics.coordinate.preference.Utils.getTypeOfUniqueness(context))
                {
                    case CURRENT_GRID: return !(entriesTableInstance instanceof
                        org.wheatgenetics.coordinate.database.CurrentGridUniqueEntriesTable);

                    case CURRENT_PROJECT: return !(entriesTableInstance instanceof
                        org.wheatgenetics.coordinate.database.CurrentProjectUniqueEntriesTable);

                    case ALL_GRIDS: return !(entriesTableInstance instanceof
                        org.wheatgenetics.coordinate.database.AllGridsUniqueEntriesTable);

                    default: return true;
                }
            else return
                entriesTableInstance instanceof
                    org.wheatgenetics.coordinate.database.CurrentGridUniqueEntriesTable ||
                entriesTableInstance instanceof
                    org.wheatgenetics.coordinate.database.CurrentProjectUniqueEntriesTable ||
                entriesTableInstance instanceof
                    org.wheatgenetics.coordinate.database.AllGridsUniqueEntriesTable;
    }

    static boolean joinedGridModelNeedsReloading(
        final org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel,
    @androidx.annotation.NonNull final android.content.Context context)
    {
        if (null == joinedGridModel)
            return false;
        else
            if (org.wheatgenetics.coordinate.preference.Utils.getUniqueness(context))
                switch (org.wheatgenetics.coordinate.preference.Utils.getTypeOfUniqueness(context))
                {
                    case CURRENT_GRID: return
                        joinedGridModel instanceof
                            org.wheatgenetics.coordinate.model.CurrentGridUniqueJoinedGridModel
                        && !(joinedGridModel instanceof
                            org.wheatgenetics.coordinate.model.CurrentProjectUniqueJoinedGridModel)
                        && !(joinedGridModel instanceof
                            org.wheatgenetics.coordinate.model.AllGridsUniqueJoinedGridModel);

                    case CURRENT_PROJECT: return !(joinedGridModel instanceof
                        org.wheatgenetics.coordinate.model.CurrentProjectUniqueJoinedGridModel);

                    case ALL_GRIDS: return !(joinedGridModel instanceof
                        org.wheatgenetics.coordinate.model.AllGridsUniqueJoinedGridModel);

                    default: return true;
                }
            else return joinedGridModel instanceof
                org.wheatgenetics.coordinate.model.CurrentGridUniqueJoinedGridModel;
    }
    // endregion
}