package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * android.content.Context
 * android.support.annotation.NonNull
 * android.support.annotation.RestrictTo
 * android.support.annotation.RestrictTo.Scope
 *
 * org.wheatgenetics.coordinate.model.Exporter
 * org.wheatgenetics.coordinate.model.Exporter.AsyncTask
 * org.wheatgenetics.coordinate.model.TemplateModel
 */
public class TemplateExporter extends org.wheatgenetics.coordinate.model.Exporter
{
    private static class AsyncTask extends org.wheatgenetics.coordinate.model.Exporter.AsyncTask
    {
        private final org.wheatgenetics.coordinate.model.TemplateModel templateModel;

        private AsyncTask(
        @android.support.annotation.NonNull final android.content.Context context   ,
                                            final java.io.File            exportFile,
                                            final org.wheatgenetics.coordinate.model.TemplateModel
                                                templateModel)
        { super(context, exportFile); this.templateModel = templateModel; }

        // region Overridden Methods
        @android.support.annotation.RestrictTo(
            android.support.annotation.RestrictTo.Scope.SUBCLASSES)
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

        @java.lang.Override @android.support.annotation.RestrictTo(
            android.support.annotation.RestrictTo.Scope.SUBCLASSES)
        void handleExportSuccess(final java.io.File exportFile) { this.alert(); this.share(); }
        // endregion
    }

    @android.support.annotation.NonNull private final
        org.wheatgenetics.coordinate.model.TemplateExporter.AsyncTask asyncTask;

    public TemplateExporter(
    @android.support.annotation.NonNull final android.content.Context context   ,
                                        final java.io.File            exportFile,
                                        final org.wheatgenetics.coordinate.model.TemplateModel
                                            templateModel)
    {
        super();
        this.asyncTask = new org.wheatgenetics.coordinate.model.TemplateExporter.AsyncTask(
            context, exportFile, templateModel);
    }

    // region Overridden Methods
    @java.lang.Override public void execute() { this.asyncTask.execute(); }
    @java.lang.Override public void cancel () { this.asyncTask.cancel (); }
    // endregion
}