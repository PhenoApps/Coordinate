package org.wheatgenetics.coordinate.exporter;

import android.content.Context;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;

import org.wheatgenetics.coordinate.R;
import org.wheatgenetics.coordinate.model.JoinedGridModel;

import java.io.File;
import java.io.IOException;

public class GridExporter extends Exporter {
    @NonNull
    private final
    GridExporter.AsyncTask asyncTask;

    public GridExporter(@NonNull final Context context,
                        final File exportFile, final String exportFileName,
                        @NonNull final GridExporter.Helper
                                helper) {
        super();
        this.asyncTask = new GridExporter.AsyncTask(
                context, exportFile, exportFileName, helper);
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
        private final
        GridExporter.Helper helper;
        // endregion

        private AsyncTask(
                @NonNull final Context context,
                final File exportFile,
                final String exportFileName,
                @NonNull final GridExporter.Helper
                        helper) {
            super(context, exportFile);
            this.exportFileName = exportFileName;
            this.helper = helper;
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
                    if (joinedGridModel.export(                        // throws java.io.IOException
                            this.getExportFile(), this.exportFileName, this)) {
                        this.makeExportFileDiscoverable();
                        success = true;
                    } else
                        success = false;
                } catch (final IOException e) {
                    e.printStackTrace();
                    this.setMessage(
                            R.string.GridExporterFailedMessage);
                    success = false;
                }
            return success;
        }

        @Override
        @RestrictTo(
                RestrictTo.Scope.SUBCLASSES)
        void handleExportSuccess(final File exportFile) {
            @SuppressWarnings({"ClassExplicitlyExtendsObject"})
            class YesRunnable extends Object implements Runnable {
                @Override
                public void run() {
                    GridExporter.AsyncTask.this.deleteGrid();
                    GridExporter.AsyncTask.this.share();
                }
            }

            this.alert(R.string.GridExporterDeleteConfirmation,
                    new YesRunnable());
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