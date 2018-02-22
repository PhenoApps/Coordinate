package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * android.content.Context
 * android.support.annotation.RestrictTo
 * android.support.annotation.RestrictTo.Scope
 *
 * org.wheatgenetics.coordinate.model.Exporter
 * org.wheatgenetics.coordinate.model.Exporter.AsyncTask
 * org.wheatgenetics.coordinate.model.Exporter.Helper
 * org.wheatgenetics.coordinate.model.TemplateModel
 */
public class TemplateExporter extends org.wheatgenetics.coordinate.model.Exporter
{
    private static class AsyncTask extends org.wheatgenetics.coordinate.model.Exporter.AsyncTask
    {
        private final org.wheatgenetics.coordinate.model.TemplateModel templateModel;

        private AsyncTask(final android.content.Context context, final java.io.File exportFile,
        final org.wheatgenetics.coordinate.model.Exporter.Helper helper       ,
        final org.wheatgenetics.coordinate.model.TemplateModel   templateModel)
        { super(context, exportFile, helper); this.templateModel = templateModel; }

        @java.lang.Override @java.lang.SuppressWarnings("PointlessBooleanExpression")
        @android.support.annotation.RestrictTo(
            android.support.annotation.RestrictTo.Scope.SUBCLASSES)
        boolean export()
        {
            final boolean success = true;
            if (null == this.templateModel)
                return !success;
            else
                if (this.templateModel.export(this.getExportFile()))
                {
                    this.makeExportFileDiscoverable();
                    return success;
                }
                else return !success;
        }
    }

    private final org.wheatgenetics.coordinate.model.TemplateExporter.AsyncTask asyncTask;

    public TemplateExporter(final android.content.Context context, final java.io.File exportFile,
    final org.wheatgenetics.coordinate.model.Exporter.Helper helper       ,
    final org.wheatgenetics.coordinate.model.TemplateModel   templateModel)
    {
        super();
        this.asyncTask = new org.wheatgenetics.coordinate.model.TemplateExporter.AsyncTask(
            context, exportFile, helper, templateModel);
    }

    // region Overridden Methods
    @java.lang.Override public void execute() { this.asyncTask.execute(); }
    @java.lang.Override public void cancel () { this.asyncTask.cancel (); }
    // endregion
}