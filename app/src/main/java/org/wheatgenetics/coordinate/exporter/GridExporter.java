package org.wheatgenetics.coordinate.exporter;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.preference.PreferenceManager;

import org.wheatgenetics.coordinate.R;
import org.wheatgenetics.coordinate.Utils;
import org.wheatgenetics.coordinate.dialogs.CitationDialog;
import org.wheatgenetics.coordinate.model.JoinedGridModel;
import org.wheatgenetics.coordinate.utils.Keys;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

public class GridExporter extends Exporter {
    @NonNull
    private final
    GridExporter.AsyncTask asyncTask;

    public GridExporter(@NonNull final Context context,
                        final File exportFile, final String exportFileName,
                        @NonNull final GridExporter.Helper
                                helper, Boolean deleteGrid) {
        super();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        this.asyncTask = new GridExporter.AsyncTask(
                context, exportFile, exportFileName, helper, prefs, deleteGrid);
    }

    public GridExporter(@NonNull final Context context,
                        final OutputStream output, final String exportFileName,
                        @NonNull final GridExporter.Helper
                                helper, Boolean deleteGrid) {
        super();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        this.asyncTask = new GridExporter.AsyncTask(
                context, output, exportFileName, helper, prefs, deleteGrid);
    }

    // endregion

    // region Overridden Methods
    @Override
    public void execute() {
        this.asyncTask.execute();
    }

    @Override
    public void cancel() {
        this.asyncTask.cancel();
    }

    // region Types
    @SuppressWarnings({"UnnecessaryInterfaceModifier"})
    public interface Helper {
        public abstract JoinedGridModel getJoinedGridModel();

        public abstract void deleteGrid();
    }

    private static class AsyncTask extends Exporter.AsyncTask
            implements JoinedGridModel.Helper {
        // region Fields
        private final String exportFileName;
        @NonNull
        private final GridExporter.Helper helper;
        @NonNull
        private final SharedPreferences prefs;

        private final Boolean deleteGrid;
        // endregion

        private AsyncTask(
                @NonNull final Context context,
                final File exportFile,
                final String exportFileName,
                @NonNull final GridExporter.Helper helper,
                @NonNull final SharedPreferences prefs,
                final Boolean deleteGrid) {
            super(context, exportFile);
            this.exportFileName = exportFileName;
            this.helper = helper;
            this.prefs = prefs;
            this.deleteGrid = deleteGrid;
        }

        private AsyncTask(
                @NonNull final Context context,
                final OutputStream output,
                final String exportFileName,
                @NonNull final GridExporter.Helper helper,
                @NonNull final SharedPreferences prefs,
                final Boolean deleteGrid) {
            super(context, output);
            this.exportFileName = exportFileName;
            this.helper = helper;
            this.prefs = prefs;
            this.deleteGrid = deleteGrid;
        }
        private void deleteGrid() {
            this.helper.deleteGrid();
        }

        // region Overridden Methods
        @RestrictTo(RestrictTo.Scope.SUBCLASSES)
        @Override
        boolean export() {
            final JoinedGridModel joinedGridModel =
                    this.helper.getJoinedGridModel();

            boolean success;
            if (null == joinedGridModel)
                success = false;
            else
                try {
                    if (this.getExportFile() == null) {
                        if (joinedGridModel.export(this.getOutputStream(), this.exportFileName, this)) {
                            this.makeExportFileDiscoverable();
                            success = true;
                            resetLastGridId(joinedGridModel.getId());
                        } else success = false;
                    } else {
                        if (joinedGridModel.export(                        // throws java.io.IOException
                                this.getExportFile(), this.exportFileName)) {
                            this.makeExportFileDiscoverable();
                            success = true;
                            resetLastGridId(joinedGridModel.getId());
                        } else
                            success = false;
                    }

                } catch (final IOException e) {
                    e.printStackTrace();
                    this.setMessage(
                            R.string.GridExporterFailedMessage);
                    success = false;
                }
            return success;
        }

        private void resetLastGridId(long gridId) {
            long lastId = prefs.getLong(Keys.COLLECTOR_LAST_GRID, -1L);
            if (lastId == gridId) {
                prefs.edit().putLong(Keys.COLLECTOR_LAST_GRID, -1L).apply();
            }
        }

        @Override
        @RestrictTo(
                RestrictTo.Scope.SUBCLASSES)
        void handleExportSuccess(final File exportFile) {
            if (deleteGrid) {
                Utils.alert(getContext(), R.string.ExporterSuccessTitle, new Runnable() {
                    @Override
                    public void run() {
                        Utils.confirm(getContext(), R.string.GridExporterDeleteConfirmation,
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        GridExporter.AsyncTask.this.deleteGrid();
                                        GridExporter.AsyncTask.this.share();
                                        new CitationDialog(getContext()).show();
                                    }
                                },
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        GridExporter.AsyncTask.this.share();
                                        new CitationDialog(getContext()).show();
                                    }
                                });
                    }
                });
            } else {
                Utils.alert(getContext(), R.string.ExporterSuccessTitle, new Runnable() {
                    @Override
                    public void run() {
                        new CitationDialog(getContext()).show();
                    }
                });
            }
        }

        // region org.wheatgenetics.coordinate.model.JoinedGridModel.Helper Overridden Method
        @Override
        public void publishProgress(
                @IntRange(from = 1) final int col) {
            this.publishProgress(this.getString(
                    R.string.GridExporterProgressDialogMessage) + col);
        }
        // endregion
        // endregion
    }
    // endregion
}