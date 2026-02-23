package org.wheatgenetics.coordinate.deleter;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.preference.PreferenceManager;

import org.wheatgenetics.coordinate.R;
import org.wheatgenetics.coordinate.Utils;
import org.wheatgenetics.coordinate.database.TemplatesTable;
import org.wheatgenetics.coordinate.model.JoinedGridModel;
import org.wheatgenetics.coordinate.utils.Keys;

import java.util.List;

public class GridDeleter extends BaseGridDeleter {
    @NonNull
    private final
    GridDeleter.Handler handler;

    public GridDeleter(@NonNull final Context context,
                       @NonNull final GridDeleter.Handler
                               handler) {
        super(context);
        this.handler = handler;
    }

    public void deleteAll() {

        gridsTable().deleteAll();
        entriesTable().deleteAll();
    }

    //when a grid is deleted, check if the last preference needs to be reset
    private void checkPreferenceLastGrid(long gridId) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context());
        if (prefs.getLong(Keys.COLLECTOR_LAST_GRID, -1L) == gridId) {
            prefs.edit().putLong(Keys.COLLECTOR_LAST_GRID, -1L).apply();
        }
    }

    // region Public Methods
    public void deleteWithoutConfirm(@IntRange(from = 1) final long gridId) {
        // Load grid model before deletion to check if it's an imported grid
        final JoinedGridModel gridModel = this.gridsTable().get(gridId);
        final boolean isImported = gridModel != null && gridModel.isImported();
        final long sentinelTemplateId = isImported ? gridModel.getTemplateId() : -1L;

        {
            @StringRes final int text =
                    this.entriesTable().deleteByGridId(gridId) ?
                            R.string.GridDeleterEntriesOfGridDeletedToast :
                            R.string.GridDeleterEntriesOfGridNotDeletedToast;

            this.showShortToast(text);
        }

        final boolean success = this.gridsTable().delete(gridId);
        {
            @StringRes final int text = success ?
                    R.string.GridDeleterGridDeletedToast :
                    R.string.GridDeleterGridNotDeletedToast;

            this.showLongToast(text);
        }
        if (success) {
            // Delete sentinel template for imported grids
            if (isImported && sentinelTemplateId > 0) {
                new TemplatesTable(this.context()).delete(sentinelTemplateId);
            }

            checkPreferenceLastGrid(gridId);

            this.handler.respondToDeletedGrid();
        }
    }

    public void deleteWithConfirm(@IntRange(from = 1) final long gridId) {
        Utils.confirmDelete(
                /* context     => */ this.context(),
                /* message     => */ R.string.GridDeleterConfirmation,
                /* yesRunnable => */ new Runnable() {
                    @Override
                    public void run() {

                        checkPreferenceLastGrid(gridId);

                        GridDeleter.this.deleteWithoutConfirm(
                                gridId);
                    }
                });
    }

    public void deleteMultiple(@NonNull final List<Long> gridIds) {
        final TemplatesTable templatesTable = new TemplatesTable(this.context());
        for (final long gridId : gridIds) {
            final JoinedGridModel gridModel = this.gridsTable().get(gridId);
            final boolean isImported = gridModel != null && gridModel.isImported();
            final long sentinelTemplateId = isImported ? gridModel.getTemplateId() : -1L;

            this.entriesTable().deleteByGridId(gridId);
            this.gridsTable().delete(gridId);
            if (isImported && sentinelTemplateId > 0) {
                templatesTable.delete(sentinelTemplateId);
            }
            checkPreferenceLastGrid(gridId);
        }
        this.showLongToast(R.string.multi_delete_success);
        this.handler.respondToDeletedGrid();
    }

    @SuppressWarnings({"UnnecessaryInterfaceModifier"})
    public interface Handler {
        public abstract void respondToDeletedGrid();
    }
    // endregion
}