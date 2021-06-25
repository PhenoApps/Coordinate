package org.wheatgenetics.coordinate.collector;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.wheatgenetics.coordinate.database.AllGridsUniqueEntriesTable;
import org.wheatgenetics.coordinate.database.AllGridsUniqueGridsTable;
import org.wheatgenetics.coordinate.database.CurrentGridUniqueEntriesTable;
import org.wheatgenetics.coordinate.database.CurrentGridUniqueGridsTable;
import org.wheatgenetics.coordinate.database.CurrentProjectUniqueEntriesTable;
import org.wheatgenetics.coordinate.database.CurrentProjectUniqueGridsTable;
import org.wheatgenetics.coordinate.database.EntriesTable;
import org.wheatgenetics.coordinate.database.GridsTable;
import org.wheatgenetics.coordinate.model.AllGridsUniqueJoinedGridModel;
import org.wheatgenetics.coordinate.model.CurrentGridUniqueJoinedGridModel;
import org.wheatgenetics.coordinate.model.CurrentProjectUniqueJoinedGridModel;
import org.wheatgenetics.coordinate.model.JoinedGridModel;

class Utils {
    // region Table Methods
    @Nullable
    static GridsTable gridsTable(
            @Nullable final GridsTable
                    gridsTableInstance,
            @NonNull final Context context) {
        if (null == gridsTableInstance)
            if (org.wheatgenetics.coordinate.preference.Utils.getUnique(context))
                switch (org.wheatgenetics.coordinate.preference.Utils.getTypeOfUniqueness(context)) {
                    case CURRENT_GRID:
                        return new
                                CurrentGridUniqueGridsTable(
                                context);

                    case CURRENT_PROJECT:
                        return new
                                CurrentProjectUniqueGridsTable(
                                context);

                    case ALL_GRIDS:
                        return new AllGridsUniqueGridsTable(
                                context);

                    default:
                        return null;
                }
            else return new GridsTable(context);
        else return gridsTableInstance;
    }

    @Nullable
    static EntriesTable entriesTable(
            @Nullable final EntriesTable
                    entriesTableInstance,
            @Nullable final GridsTable
                    gridsTableInstance,
            @NonNull final Context context) {
        if (null == entriesTableInstance)
            if (org.wheatgenetics.coordinate.preference.Utils.getUnique(context))
                switch (org.wheatgenetics.coordinate.preference.Utils.getTypeOfUniqueness(context)) {
                    case CURRENT_GRID:
                        return new
                                CurrentGridUniqueEntriesTable(
                                context);

                    case CURRENT_PROJECT:
                        final CurrentProjectUniqueGridsTable
                                currentProjectUniqueGridsTable = (CurrentProjectUniqueGridsTable)
                                Utils.gridsTable(
                                        gridsTableInstance, context);
                        return null == currentProjectUniqueGridsTable ? null : new
                                CurrentProjectUniqueEntriesTable(
                                context, currentProjectUniqueGridsTable);

                    case ALL_GRIDS:
                        return new AllGridsUniqueEntriesTable(
                                context);

                    default:
                        return null;
                }
            else return new EntriesTable(context);
        else return entriesTableInstance;
    }
    // endregion

    // region needsReloading() Methods
    static boolean gridsTableNeedsReloading(@Nullable final GridsTable gridsTableInstance,
                                            @NonNull final Context context) {
        if (null == gridsTableInstance)
            return false;
        else if (org.wheatgenetics.coordinate.preference.Utils.getUnique(context))
            switch (org.wheatgenetics.coordinate.preference.Utils.getTypeOfUniqueness(context)) {
                case CURRENT_GRID:
                    return !(gridsTableInstance instanceof
                            CurrentGridUniqueGridsTable);

                case CURRENT_PROJECT:
                    return !(gridsTableInstance instanceof
                            CurrentProjectUniqueGridsTable);

                case ALL_GRIDS:
                    return !(gridsTableInstance instanceof
                            AllGridsUniqueGridsTable);

                default:
                    return true;
            }
        else return
                    gridsTableInstance instanceof
                            CurrentGridUniqueGridsTable ||
                            gridsTableInstance instanceof
                                    CurrentProjectUniqueGridsTable ||
                            gridsTableInstance instanceof
                                    AllGridsUniqueGridsTable;
    }

    static boolean entriesTableNeedsReloading(@Nullable final EntriesTable entriesTableInstance,
                                              @NonNull final Context context) {
        if (null == entriesTableInstance)
            return false;
        else if (org.wheatgenetics.coordinate.preference.Utils.getUnique(context))
            switch (org.wheatgenetics.coordinate.preference.Utils.getTypeOfUniqueness(context)) {
                case CURRENT_GRID:
                    return !(entriesTableInstance instanceof
                            CurrentGridUniqueEntriesTable);

                case CURRENT_PROJECT:
                    return !(entriesTableInstance instanceof
                            CurrentProjectUniqueEntriesTable);

                case ALL_GRIDS:
                    return !(entriesTableInstance instanceof
                            AllGridsUniqueEntriesTable);

                default:
                    return true;
            }
        else return
                    entriesTableInstance instanceof
                            CurrentGridUniqueEntriesTable ||
                            entriesTableInstance instanceof
                                    CurrentProjectUniqueEntriesTable ||
                            entriesTableInstance instanceof
                                    AllGridsUniqueEntriesTable;
    }

    static boolean joinedGridModelNeedsReloading(
            final JoinedGridModel joinedGridModel,
            @NonNull final Context context) {
        if (null == joinedGridModel)
            return false;
        else if (org.wheatgenetics.coordinate.preference.Utils.getUnique(context))
            switch (org.wheatgenetics.coordinate.preference.Utils.getTypeOfUniqueness(context)) {
                case CURRENT_GRID:
                    return
                            joinedGridModel instanceof
                                    CurrentGridUniqueJoinedGridModel
                                    && !(joinedGridModel instanceof
                                    CurrentProjectUniqueJoinedGridModel)
                                    && !(joinedGridModel instanceof
                                    AllGridsUniqueJoinedGridModel);

                case CURRENT_PROJECT:
                    return !(joinedGridModel instanceof
                            CurrentProjectUniqueJoinedGridModel);

                case ALL_GRIDS:
                    return !(joinedGridModel instanceof
                            AllGridsUniqueJoinedGridModel);

                default:
                    return true;
            }
        else return joinedGridModel instanceof
                    CurrentGridUniqueJoinedGridModel;
    }
    // endregion
}