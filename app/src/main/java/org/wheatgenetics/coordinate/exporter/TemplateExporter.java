package org.wheatgenetics.coordinate.exporter;

/**
 * Uses:
 * android.content.Context
 *
 * androidx.annotation.NonNull
 * androidx.annotation.RestrictTo
 * androidx.annotation.RestrictTo.Scope
 *
 * org.wheatgenetics.coordinate.model.TemplateModel
 *
 * org.wheatgenetics.coordinate.exporter.Exporter
 * org.wheatgenetics.coordinate.exporter.Exporter.AsyncTask
 */
public class TemplateExporter extends org.wheatgenetics.coordinate.exporter.Exporter
{
    private static class AsyncTask extends org.wheatgenetics.coordinate.exporter.Exporter.AsyncTask
    {
        private final org.wheatgenetics.coordinate.model.TemplateModel templateModel;

        private AsyncTask(
        @androidx.annotation.NonNull final android.content.Context context   ,
                                     final java.io.File            exportFile,
                                     final org.wheatgenetics.coordinate.model.TemplateModel
                                         templateModel)
        { super(context, exportFile); this.templateModel = templateModel; }

        // region Overridden Methods
        @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
        @java.lang.Override boolean export()
        {
            final boolean success;
            if (null == this.templateModel)
                success = false;
            else
                if (this.templateModel.export(this.getExportFile()))
                    { this.makeExportFileDiscoverable(); success = true; }
                else
                    success = false;
            return success;
        }

        @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
        @java.lang.Override void handleExportSuccess(final java.io.File exportFile)
        { this.alert(); this.share(); }
        // endregion
    }

    @androidx.annotation.NonNull private final
        org.wheatgenetics.coordinate.exporter.TemplateExporter.AsyncTask asyncTask;

    public TemplateExporter(
    @androidx.annotation.NonNull final android.content.Context context   ,
                                 final java.io.File            exportFile,
                                 final org.wheatgenetics.coordinate.model.TemplateModel
                                    templateModel)
    {
        super();
        this.asyncTask = new org.wheatgenetics.coordinate.exporter.TemplateExporter.AsyncTask(
            context, exportFile, templateModel);
    }

    // region Overridden Methods
    @java.lang.Override public void execute() { this.asyncTask.execute(); }
    @java.lang.Override public void cancel () { this.asyncTask.cancel (); }
    // endregion
}