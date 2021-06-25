package org.wheatgenetics.coordinate.exporter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;

import org.wheatgenetics.coordinate.model.TemplateModel;

import java.io.File;

public class TemplateExporter extends Exporter {
    @NonNull
    private final
    TemplateExporter.AsyncTask asyncTask;

    public TemplateExporter(
            @NonNull final Context context,
            final File exportFile,
            final TemplateModel
                    templateModel) {
        super();
        this.asyncTask = new TemplateExporter.AsyncTask(
                context, exportFile, templateModel);
    }

    // region Overridden Methods
    @Override
    public void execute() {
        this.asyncTask.execute();
    }

    @Override
    public void cancel() {
        this.asyncTask.cancel();
    }

    private static class AsyncTask extends Exporter.AsyncTask {
        private final TemplateModel templateModel;

        private AsyncTask(
                @NonNull final Context context,
                final File exportFile,
                final TemplateModel
                        templateModel) {
            super(context, exportFile);
            this.templateModel = templateModel;
        }

        // region Overridden Methods
        @RestrictTo(RestrictTo.Scope.SUBCLASSES)
        @Override
        boolean export() {
            final boolean success;
            if (null == this.templateModel)
                success = false;
            else if (this.templateModel.export(this.getExportFile())) {
                this.makeExportFileDiscoverable();
                success = true;
            } else
                success = false;
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