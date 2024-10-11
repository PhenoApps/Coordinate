package org.wheatgenetics.coordinate.exporter;

import android.content.Context;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.documentfile.provider.DocumentFile;

import org.phenoapps.utils.BaseDocumentTreeUtil;
import org.wheatgenetics.coordinate.R;
import org.wheatgenetics.coordinate.Utils;
import org.wheatgenetics.coordinate.model.BaseJoinedGridModels;
import org.wheatgenetics.coordinate.model.JoinedGridModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Locale;

public class PerGridProjectExporter extends ProjectExporter {
    // region Fields
    private final String exportDirectoryName;
    @IntRange(from = 0)
    private int i = 0;
    private PerGridProjectExporter.AsyncTask asyncTask = null;
    public PerGridProjectExporter(
            final BaseJoinedGridModels baseJoinedGridModels,
            @NonNull final Context context,
            final File exportDir,
            final String exportDirectoryName) {
        super(baseJoinedGridModels, context, exportDir);
        this.exportDirectoryName = exportDirectoryName;
    }

    public PerGridProjectExporter(
            final BaseJoinedGridModels baseJoinedGridModels,
            @NonNull final Context context,
            final DocumentFile exportDir,
            final String exportDirectoryName) {
        super(baseJoinedGridModels, context, exportDir);
        this.exportDirectoryName = exportDirectoryName;
    }
    // endregion

    // region Overridden Methods
    @Override
    public void execute() {
        final BaseJoinedGridModels baseJoinedGridModels =
                this.getBaseJoinedGridModels();
        if (null != baseJoinedGridModels) {
            final int size = baseJoinedGridModels.size(), last = size - 1;
            if (this.i > last) {
                this.i = 0;
                Utils.alert(
                        /* context => */ this.getContext(),
                        /* message => */
                        R.string.PerGridProjectExporterSuccessMessage);
            } else if (size > 0) {
                final DocumentFile docFile = getDocFile();
                final File exportDir = this.getExportDir();
                if (null != exportDir) {
                    final Context context = this.getContext();
                    final JoinedGridModel
                            joinedGridModel = baseJoinedGridModels.get(this.i++);
                    if (null != joinedGridModel) {
                        final String exportFileName = String.format(
                                Locale.getDefault(), "grid%d_%s.csv",
                                joinedGridModel.getId(), this.exportDirectoryName);

                        this.asyncTask = new AsyncTask(
                                /* context    => */ context,
                                /* exportFile => */ new File(exportDir, exportFileName),           //  IOE, PE
                                /* exportFileName  => */ exportFileName,
                                /* joinedGridModel => */ joinedGridModel,
                                /* client => */ new AsyncTask.Client() {
                            @Override
                            public void execute() {
                                PerGridProjectExporter.this
                                        .execute();                     // recursion
                            }
                        });
                        this.asyncTask.execute();
                    }

                } else if (docFile != null) {

                    final Context context = this.getContext();
                    final JoinedGridModel
                            joinedGridModel = baseJoinedGridModels.get(this.i++);
                    if (null != joinedGridModel) {
                        final String exportFileName = String.format(
                                Locale.getDefault(), "grid%d_%s.csv",
                                joinedGridModel.getId(), this.exportDirectoryName);

                        if (docFile.getName() != null) {

                            DocumentFile projectDir = null;
                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                                projectDir = BaseDocumentTreeUtil.Companion
                                        .createDir(context, getContext().getString(R.string.export_dir), docFile.getName());
                            }

                            if (projectDir != null) {

                                DocumentFile exportFile =
                                        projectDir.createFile("*/*", exportFileName);

                                if (exportFile != null) {

                                    try {

                                        OutputStream output = context.getContentResolver()
                                                .openOutputStream(exportFile.getUri());

                                        // recursion
                                        this.asyncTask = new AsyncTask(context,
                                                output, exportFileName, joinedGridModel,
                                                PerGridProjectExporter.this::execute);

                                        this.asyncTask.execute();

                                    } catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }
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
        // region Fields
        @NonNull
        private final
        JoinedGridModel joinedGridModel;
        @NonNull
        private final
        PerGridProjectExporter.AsyncTask.Client client;
        private AsyncTask(@NonNull final Context context,
                          final File exportFile, final String exportFileName,
                          @NonNull final JoinedGridModel
                                  joinedGridModel,
                          @NonNull final
                          PerGridProjectExporter.AsyncTask.Client client) {
            super(context, exportFile, exportFileName);
            this.joinedGridModel = joinedGridModel;
            this.client = client;
        }

        private AsyncTask(@NonNull final Context context,
                          final OutputStream output, final String exportFileName,
                          @NonNull final JoinedGridModel
                                  joinedGridModel,
                          @NonNull final
                          PerGridProjectExporter.AsyncTask.Client client) {
            super(context, output, exportFileName);
            this.joinedGridModel = joinedGridModel;
            this.client = client;
        }

        // endregion

        // region Overridden Methods
        @Override
        protected void onPostExecute(final Boolean result) {
            super.onPostExecute(this.setMessage(result, String.format(
                    this.getString(R.string.PerGridProjectExporterAsyncTaskFailedMessage),
                    this.joinedGridModel.getId())));
        }

        @RestrictTo(RestrictTo.Scope.SUBCLASSES)
        @Override
        boolean export() {
            boolean success;
            if (this.getExportFile() == null) {
                return exportOutputStream();
            } else {
                try {
                    if (this.joinedGridModel.export(                       // throws java.io.IOException
                            /* exportFile     => */ this.getExportFile(),
                            /* exportFileName => */ this.getExportFileName())) {
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
            }
            return success;
        }

        boolean exportOutputStream() {

            boolean success = false;

            try {

                this.joinedGridModel.export(this.getOutputStream(), this.getExportFileName(), this);

                success = true;

            } catch (IOException e) {

                e.printStackTrace();

            }

            return success;
        }

        @RestrictTo(RestrictTo.Scope.SUBCLASSES)
        @Override
        void handleExportSuccess(final File exportFile) {
            this.client.execute();
        }

        @SuppressWarnings({"UnnecessaryInterfaceModifier"})
        private interface Client {
            public abstract void execute();
        }
        // endregion
    }
    // endregion
}