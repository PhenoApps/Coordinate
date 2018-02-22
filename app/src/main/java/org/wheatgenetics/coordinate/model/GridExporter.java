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
 * org.wheatgenetics.coordinate.model.Exporter.Helper
 * org.wheatgenetics.coordinate.model.JoinedGridModel
 * org.wheatgenetics.coordinate.model.JoinedGridModel.Helper
 */
public class GridExporter extends org.wheatgenetics.coordinate.model.Exporter
{
    // region Types
    @java.lang.SuppressWarnings("UnnecessaryInterfaceModifier")
    public interface Helper extends org.wheatgenetics.coordinate.model.Exporter.Helper
    { public abstract org.wheatgenetics.coordinate.model.JoinedGridModel getJoinedGridModel(); }

    private static class AsyncTask extends org.wheatgenetics.coordinate.model.Exporter.AsyncTask
    implements org.wheatgenetics.coordinate.model.JoinedGridModel.Helper
    {
        private final java.lang.String exportFileName;

        private AsyncTask(final android.content.Context context, final java.io.File exportFile,
        final java.lang.String exportFileName,
        final org.wheatgenetics.coordinate.model.GridExporter.Helper helper)
        { super(context, exportFile, helper); this.exportFileName = exportFileName; }

        // region Overridden Methods
        @java.lang.Override @java.lang.SuppressWarnings("PointlessBooleanExpression")
        @android.support.annotation.RestrictTo(
            android.support.annotation.RestrictTo.Scope.SUBCLASSES)
        boolean export()
        {
            final org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel;
            {
                final org.wheatgenetics.coordinate.model.GridExporter.Helper helper =
                    (org.wheatgenetics.coordinate.model.GridExporter.Helper) this.getHelper();
                assert null != helper; joinedGridModel = helper.getJoinedGridModel();
            }
            final boolean success = true;
            if (null == joinedGridModel)
                return !success;
            else
                try
                {
                    if (joinedGridModel.export(                        // throws java.io.IOException
                    this.getExportFile(), this.exportFileName, this))
                    {
                        this.makeExportFileDiscoverable();
                        return success;
                    }
                    return !success;
                }
                catch (final java.io.IOException e)
                {
                    e.printStackTrace();
                    this.setMessage(
                        org.wheatgenetics.coordinate.R.string.GridExporterFailedMessage);
                    return !success;
                }
        }

        // region org.wheatgenetics.coordinate.model.JoinedGridModel.Helper Overridden Method
        @java.lang.Override
        public void publishProgress(final int col)
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