package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * android.content.Context
 * android.support.annotation.RestrictTo
 * android.support.annotation.RestrictTo.Scope
 *
 * org.wheatgenetics.coordinate.R
 *
 * org.wheatgenetics.coordinate.model.Exporter
 * org.wheatgenetics.coordinate.model.Exporter.AsyncTask
 * org.wheatgenetics.coordinate.model.JoinedGridModel
 * org.wheatgenetics.coordinate.model.JoinedGridModel.Helper
 */
public class GridExporter extends org.wheatgenetics.coordinate.model.Exporter
{
    // region Types
    @java.lang.SuppressWarnings({"UnnecessaryInterfaceModifier"}) public interface Helper
    {
        public abstract org.wheatgenetics.coordinate.model.JoinedGridModel getJoinedGridModel();
        public abstract void                                               deleteGrid        ();
    }

    private static class AsyncTask extends org.wheatgenetics.coordinate.model.Exporter.AsyncTask
    implements org.wheatgenetics.coordinate.model.JoinedGridModel.Helper
    {
        // region Fields
        private final java.lang.String                                       exportFileName;
        private final org.wheatgenetics.coordinate.model.GridExporter.Helper helper        ;
        // endregion

        private void deleteGrid() { assert null != this.helper; this.helper.deleteGrid(); }

        private AsyncTask(final android.content.Context context, final java.io.File exportFile,
        final java.lang.String exportFileName,
        final org.wheatgenetics.coordinate.model.GridExporter.Helper helper)
        { super(context, exportFile); this.exportFileName = exportFileName; this.helper = helper; }

        // region Overridden Methods
        @java.lang.Override @android.support.annotation.RestrictTo(
            android.support.annotation.RestrictTo.Scope.SUBCLASSES)
        boolean export()
        {
            assert null != this.helper;
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

        @java.lang.Override @android.support.annotation.RestrictTo(
            android.support.annotation.RestrictTo.Scope.SUBCLASSES)
        void handleExportSuccess(final java.io.File exportFile)
        {
            @java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
            class YesRunnable extends java.lang.Object implements java.lang.Runnable
            {
                @java.lang.Override public void run()
                {
                    org.wheatgenetics.coordinate.model.GridExporter.AsyncTask.this.deleteGrid();
                    org.wheatgenetics.coordinate.model.GridExporter.AsyncTask.this.share     ();
                }
            }

            this.alert(org.wheatgenetics.coordinate.R.string.GridExporterDeleteConfirmation,
                new YesRunnable());
        }

        // region org.wheatgenetics.coordinate.model.JoinedGridModel.Helper Overridden Method
        @java.lang.Override public void publishProgress(final int col)
        {
            this.publishProgress(this.getString(
                org.wheatgenetics.coordinate.R.string.GridExporterProgressDialogMessage) + col);
        }
        // endregion
        // endregion
    }
    // endregion

    private final org.wheatgenetics.coordinate.model.GridExporter.AsyncTask asyncTask;

    public GridExporter(final android.content.Context context, final java.io.File exportFile,
    final java.lang.String exportFileName,
    final org.wheatgenetics.coordinate.model.GridExporter.Helper helper)
    {
        super();
        this.asyncTask = new org.wheatgenetics.coordinate.model.GridExporter.AsyncTask(
            context, exportFile, exportFileName, helper);
    }

    // region Overridden Methods
    @java.lang.Override public void execute() { this.asyncTask.execute(); }
    @java.lang.Override public void cancel () { this.asyncTask.cancel (); }
    // endregion
}