package org.wheatgenetics.coordinate.exporter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;

import org.phenoapps.permissions.RequestDir;
import org.wheatgenetics.coordinate.R;
import org.wheatgenetics.coordinate.model.BaseJoinedGridModels;
import org.phenoapps.permissions.Dir;

import java.io.File;
import java.io.IOException;

public class EntireProjectProjectExporter
        extends ProjectExporter {
    // region Fields
    @NonNull
    private final String exportFileName;
    private EntireProjectProjectExporter.AsyncTask
            asyncTask = null;

    public EntireProjectProjectExporter(
            final BaseJoinedGridModels baseJoinedGridModels,
            @NonNull final Context context,
            final File exportDir,
            final String exportFileName) {
        super(baseJoinedGridModels, context, exportDir);
        this.exportFileName = exportFileName + ".csv";
    }
    // endregion

    // region Overridden Methods
    @Override
    public void execute() {
        final BaseJoinedGridModels baseJoinedGridModels =
                this.getBaseJoinedGridModels();
        if (null != baseJoinedGridModels) {
            final File exportDir = this.getExportDir();
            if (null != exportDir) {
                this.asyncTask = new AsyncTask(
                        /* context    => */ this.getContext(),
                        /* exportFile => */ exportDir,                        //  PermissionException
                        /* exportFileName       => */ this.exportFileName,
                        /* baseJoinedGridModels => */ baseJoinedGridModels);
                this.asyncTask.execute();
            }
        }
    }

    @Override
    public void cancel() {
        if (null != this.asyncTask) this.asyncTask.cancel();
    }

    private static class AsyncTask
            extends ProjectExporter.AsyncTask {
        private final BaseJoinedGridModels baseJoinedGridModels;

        private AsyncTask(@NonNull final Context context,
                          final File exportFile, final String exportFileName,
                          final BaseJoinedGridModels baseJoinedGridModels) {
            super(context, exportFile, exportFileName);
            this.baseJoinedGridModels = baseJoinedGridModels;
        }

        // region Overridden Methods
        @Override
        protected void onPostExecute(final Boolean result) {
            super.onPostExecute(this.setMessage(result, this.getString(R.string.EntireProjectProjectExporterAsyncTaskFailedMessage)));
        }

        @RestrictTo(RestrictTo.Scope.SUBCLASSES)
        @Override
        boolean export() {
            boolean success;
            if (null == this.baseJoinedGridModels)
                success = false;
            else {
                try {
                    if (this.baseJoinedGridModels.export(              // throws java.io.IOException
                            /* exportFile     => */ this.getExportFile(),
                            /* exportFileName => */ this.getExportFileName(),
                            /* helper         => */this)) {
                        this.makeExportFileDiscoverable();
                        success = true;
                    } else
                        success = false;
                } catch (final IOException e) {
                    e.printStackTrace();
                    this.setMessage(R.string.EntireProjectProjectExporterFailedMessage);
                    success = false;
                }
            }
            return success;
        }

        @RestrictTo(RestrictTo.Scope.SUBCLASSES)
        @Override
        void handleExportSuccess(final File exportFile) {
            this.alert();
            this.share();
        }
        // endregion
    }
    // endregion
}