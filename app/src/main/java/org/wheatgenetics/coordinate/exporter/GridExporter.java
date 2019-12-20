package org.wheatgenetics.coordinate.exporter;

/**
 * Uses:
 * android.content.Context
 *
 * androidx.annotation.IntRange
 * androidx.annotation.NonNull
 * androidx.annotation.RestrictTo
 * androidx.annotation.RestrictTo.Scope
 *
 * org.wheatgenetics.coordinate.R
 *
 * org.wheatgenetics.coordinate.model.JoinedGridModel
 * org.wheatgenetics.coordinate.model.JoinedGridModel.Helper
 *
 * org.wheatgenetics.coordinate.exporter.Exporter
 * org.wheatgenetics.coordinate.exporter.Exporter.AsyncTask
 */
public class GridExporter extends org.wheatgenetics.coordinate.exporter.Exporter
{
    // region Types
    @java.lang.SuppressWarnings({"UnnecessaryInterfaceModifier"}) public interface Helper
    {
        public abstract org.wheatgenetics.coordinate.model.JoinedGridModel getJoinedGridModel();
        public abstract void                                               deleteGrid        ();
    }

    private static class AsyncTask extends org.wheatgenetics.coordinate.exporter.Exporter.AsyncTask
    implements org.wheatgenetics.coordinate.model.JoinedGridModel.Helper
    {
        // region Fields
                                     private final java.lang.String exportFileName;
        @androidx.annotation.NonNull private final
            org.wheatgenetics.coordinate.exporter.GridExporter.Helper helper;
        // endregion

        private void deleteGrid() { this.helper.deleteGrid(); }

        private AsyncTask(
        @androidx.annotation.NonNull final android.content.Context context       ,
                                     final java.io.File            exportFile    ,
                                     final java.lang.String        exportFileName,
        @androidx.annotation.NonNull final org.wheatgenetics.coordinate.exporter.GridExporter.Helper
            helper)
        { super(context, exportFile); this.exportFileName = exportFileName; this.helper = helper; }

        // region Overridden Methods
        @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
        @java.lang.Override boolean export()
        {
            final org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel =
                this.helper.getJoinedGridModel();

            boolean success;
            if (null == joinedGridModel)
                success = false;
            else
                try
                {
                    if (joinedGridModel.export(                        // throws java.io.IOException
                    this.getExportFile(), this.exportFileName,this))
                        { this.makeExportFileDiscoverable(); success = true; }
                    else
                        success = false;
                }
                catch (final java.io.IOException e)
                {
                    e.printStackTrace();
                    this.setMessage(
                        org.wheatgenetics.coordinate.R.string.GridExporterFailedMessage);
                    success = false;
                }
            return success;
        }

        @java.lang.Override @androidx.annotation.RestrictTo(
            androidx.annotation.RestrictTo.Scope.SUBCLASSES)
        void handleExportSuccess(final java.io.File exportFile)
        {
            @java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
            class YesRunnable extends java.lang.Object implements java.lang.Runnable
            {
                @java.lang.Override public void run()
                {
                    org.wheatgenetics.coordinate.exporter.GridExporter.AsyncTask.this.deleteGrid();
                    org.wheatgenetics.coordinate.exporter.GridExporter.AsyncTask.this.share     ();
                }
            }

            this.alert(org.wheatgenetics.coordinate.R.string.GridExporterDeleteConfirmation,
                new YesRunnable());
        }

        // region org.wheatgenetics.coordinate.model.JoinedGridModel.Helper Overridden Method
        @java.lang.Override public void publishProgress(
        @androidx.annotation.IntRange(from = 1) final int col)
        {
            this.publishProgress(this.getString(
                org.wheatgenetics.coordinate.R.string.GridExporterProgressDialogMessage) + col);
        }
        // endregion
        // endregion
    }
    // endregion

    @androidx.annotation.NonNull private final
        org.wheatgenetics.coordinate.exporter.GridExporter.AsyncTask asyncTask;

    public GridExporter(@androidx.annotation.NonNull final android.content.Context context,
    final java.io.File exportFile, final java.lang.String exportFileName,
    @androidx.annotation.NonNull final org.wheatgenetics.coordinate.exporter.GridExporter.Helper
        helper)
    {
        super();
        this.asyncTask = new org.wheatgenetics.coordinate.exporter.GridExporter.AsyncTask(
            context, exportFile, exportFileName, helper);
    }

    // region Overridden Methods
    @java.lang.Override public void execute() { this.asyncTask.execute(); }
    @java.lang.Override public void cancel () { this.asyncTask.cancel (); }
    // endregion
}