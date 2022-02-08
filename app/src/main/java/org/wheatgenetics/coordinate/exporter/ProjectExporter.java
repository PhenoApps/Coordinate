package org.wheatgenetics.coordinate.exporter;

import android.content.Context;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.documentfile.provider.DocumentFile;

import org.phenoapps.permissions.RequestDir;
import org.wheatgenetics.coordinate.R;
import org.wheatgenetics.coordinate.Utils;
import org.wheatgenetics.coordinate.model.BaseJoinedGridModels;
import org.wheatgenetics.coordinate.model.JoinedGridModel;

import java.io.File;
import java.io.OutputStream;

abstract class ProjectExporter extends Exporter {
    // region Fields
    private final BaseJoinedGridModels baseJoinedGridModels;
    @NonNull
    private final Context context;
    private File exportDir;
    private DocumentFile docFile;
    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    ProjectExporter(
            final BaseJoinedGridModels baseJoinedGridModels,
            @NonNull final Context context,
            final File exportDir) {
        super();

        this.baseJoinedGridModels = baseJoinedGridModels;
        this.context = context;
        this.exportDir = exportDir;
    }

    ProjectExporter(
            final BaseJoinedGridModels baseJoinedGridModels,
            @NonNull final Context context,
            final DocumentFile exportDir) {
        super();

        this.baseJoinedGridModels = baseJoinedGridModels;
        this.context = context;
        this.docFile = exportDir;
    }
    // endregion

    // region Protected Methods
    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    BaseJoinedGridModels getBaseJoinedGridModels() {
        return this.baseJoinedGridModels;
    }

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    @NonNull
    Context getContext() {
        return this.context;
    }

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    File getExportDir() {
        return this.exportDir;
    }

    DocumentFile getDocFile() { return this.docFile; }

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    void unableToCreateFileAlert(final String exportFileName) {
        Utils.alert(
                /* context => */ this.context,
                /* title   => */ R.string.ExporterFailTitle,
                /* message => */ String.format(this.context.getString(
                        R.string.ProjectExporterFailedMessage),
                        exportFileName));
    }

    abstract static class AsyncTask extends Exporter.AsyncTask
            implements JoinedGridModel.Helper {
        private final String exportFileName;

        AsyncTask(@NonNull final Context context,
                  final File exportFile, final String exportFileName) {
            super(context, exportFile);
            this.exportFileName = exportFileName;
        }

        AsyncTask(@NonNull final Context context,
                  final OutputStream output, final String exportFileName) {
            super(context, output);
            this.exportFileName = exportFileName;
        }

        // region org.wheatgenetics.coordinate.model.JoinedGridModel.Helper Overridden Method
        @Override
        public void publishProgress(@IntRange(from = 1) final int col) {
            this.publishProgress(this.getString(
                    R.string.GridExporterProgressDialogMessage) + col);
        }
        // endregion

        // region Package Methods
        @RestrictTo(RestrictTo.Scope.SUBCLASSES)
        String getExportFileName() {
            return this.exportFileName;
        }

        @RestrictTo(RestrictTo.Scope.SUBCLASSES)
        Boolean setMessage(final Boolean result, final String message) {
            if (null == result || !result) this.setMessage(message);
            return result;
        }
        // endregion
    }
    // endregion
}