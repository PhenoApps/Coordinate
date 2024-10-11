package org.wheatgenetics.coordinate.exporter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.documentfile.provider.DocumentFile;

import org.phenoapps.utils.BaseDocumentTreeUtil;
import org.wheatgenetics.coordinate.R;
import org.wheatgenetics.coordinate.model.BaseJoinedGridModels;
import org.wheatgenetics.coordinate.utils.FileUtil;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

public class EntireProjectProjectExporter
        extends ProjectExporter {
    // region Fields
    @NonNull
    private final String exportFileName;
    private EntireProjectProjectExporter.AsyncTask asyncTask = null;
    private OutputStream outputStream = null;

    public EntireProjectProjectExporter(
            final BaseJoinedGridModels baseJoinedGridModels,
            @NonNull final Context context,
            final File exportDir,
            final String exportFileName) {
        super(baseJoinedGridModels, context, exportDir);
        this.exportFileName = exportFileName + ".csv";
    }

    public EntireProjectProjectExporter(
            final BaseJoinedGridModels baseJoinedGridModels,
            @NonNull final Context context,
            final OutputStream stream,
            final String exportFileName) {
        super(baseJoinedGridModels, context);
        this.outputStream = stream;
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
            } else if (outputStream == null) {

                try {

                    if (BaseDocumentTreeUtil.Companion.isEnabled(getContext())) {

                        DocumentFile projectDir = BaseDocumentTreeUtil.Companion.getDirectory(getContext(), R.string.export_dir);

                        if (projectDir != null && projectDir.exists()) {
                            DocumentFile file = projectDir.createFile(getContext().getString(R.string.export_dir), this.exportFileName);

                            if (file != null) {

                                final OutputStream outputStream = getContext().getContentResolver().openOutputStream(file.getUri());

                                this.asyncTask = new AsyncTask(this.getContext(),
                                        outputStream, this.exportFileName, baseJoinedGridModels);
                                this.asyncTask.execute();

                                FileUtil.Companion.shareFile(getContext(), file.getUri());
                            }
                        }
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {

                try {

                    this.asyncTask = new AsyncTask(this.getContext(),
                            this.outputStream, this.exportFileName, baseJoinedGridModels);
                    this.asyncTask.execute();

                } catch (Exception e) {
                    e.printStackTrace();
                }
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

        private AsyncTask(@NonNull final Context context,
                          final OutputStream output, final String exportFileName,
                          final BaseJoinedGridModels baseJoinedGridModels) {
            super(context, output, exportFileName);
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
            boolean success = false;
            if (null == this.baseJoinedGridModels)
                success = false;
            else if (this.getExportFile() != null) {
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
            } else if (this.getOutputStream() != null) {
                try {
                    if (this.baseJoinedGridModels.export(              // throws java.io.IOException
                            /* exportFile     => */ this.getOutputStream(),
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