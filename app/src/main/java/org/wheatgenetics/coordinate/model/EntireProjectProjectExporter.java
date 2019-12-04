package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * android.content.Context
 *
 * androidx.annotation.NonNull
 * androidx.annotation.RestrictTo
 * androidx.annotation.RestrictTo.Scope
 *
 * org.wheatgenetics.javalib.Dir.PermissionException
 *
 * org.wheatgenetics.androidlibrary.RequestDir
 *
 * org.wheatgenetics.coordinate.R
 *
 * org.wheatgenetics.coordinate.model.BaseJoinedGridModels
 * org.wheatgenetics.coordinate.model.ProjectExporter
 * org.wheatgenetics.coordinate.model.ProjectExporter.AsyncTask
 */
public class EntireProjectProjectExporter extends org.wheatgenetics.coordinate.model.ProjectExporter
{
    private static class AsyncTask
    extends org.wheatgenetics.coordinate.model.ProjectExporter.AsyncTask
    {
        private final org.wheatgenetics.coordinate.model.BaseJoinedGridModels baseJoinedGridModels;

        private AsyncTask(@androidx.annotation.NonNull final android.content.Context context,
        final java.io.File exportFile, final java.lang.String exportFileName,
        final org.wheatgenetics.coordinate.model.BaseJoinedGridModels baseJoinedGridModels)
        {
            super(context, exportFile, exportFileName);
            this.baseJoinedGridModels = baseJoinedGridModels;
        }

        // region Overridden Methods
        @java.lang.Override protected void onPostExecute(final java.lang.Boolean result)
        {
            super.onPostExecute(this.setMessage(result, this.getString(org.wheatgenetics.coordinate
                .R.string.EntireProjectProjectExporterAsyncTaskFailedMessage)));
        }

        @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
        @java.lang.Override boolean export()
        {
            boolean success;
            if (null == this.baseJoinedGridModels)
                success = false;
            else
            {
                try
                {
                    if (this.baseJoinedGridModels.export(              // throws java.io.IOException
                    /* exportFile     => */ this.getExportFile    (),
                    /* exportFileName => */ this.getExportFileName(),
                    /* helper         => */this))
                        { this.makeExportFileDiscoverable(); success = true; }
                    else
                        success = false;
                }
                catch (final java.io.IOException e)
                {
                    e.printStackTrace();
                    this.setMessage(org.wheatgenetics.coordinate
                        .R.string.EntireProjectProjectExporterFailedMessage);
                    success = false;
                }
            }
            return success;
        }

        @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
        @java.lang.Override void handleExportSuccess(final java.io.File exportFile)
        { this.alert(); this.share(); }
        // endregion
    }

    // region Fields
    @androidx.annotation.NonNull private final java.lang.String exportFileName;

    private org.wheatgenetics.coordinate.model.EntireProjectProjectExporter.AsyncTask
        asyncTask = null;
    // endregion

    public EntireProjectProjectExporter(
    final org.wheatgenetics.coordinate.model.BaseJoinedGridModels baseJoinedGridModels,
    @androidx.annotation.NonNull final android.content.Context                     context       ,
                                 final org.wheatgenetics.androidlibrary.RequestDir exportDir     ,
                                 final java.lang.String                            exportFileName)
    {
        super(baseJoinedGridModels, context, exportDir);
        this.exportFileName = exportFileName + ".csv";
    }

    // region Overridden Methods
    @java.lang.Override public void execute()
    {
        final org.wheatgenetics.coordinate.model.BaseJoinedGridModels baseJoinedGridModels =
            this.getBaseJoinedGridModels();
        if (null != baseJoinedGridModels)
        {
            final org.wheatgenetics.androidlibrary.RequestDir exportDir = this.getExportDir();
            if (null != exportDir)
                try
                {
                    this.asyncTask = new
                        org.wheatgenetics.coordinate.model.EntireProjectProjectExporter.AsyncTask(
                            /* context    => */ this.getContext(),
                            /* exportFile => */ exportDir.createNewFile(     // throws IOException,
                                this.exportFileName),                        //  PermissionException
                            /* exportFileName       => */ this.exportFileName ,
                            /* baseJoinedGridModels => */ baseJoinedGridModels);
                    this.asyncTask.execute();
                }
                catch (final java.io.IOException | org.wheatgenetics.javalib.Dir.PermissionException
                e) { this.unableToCreateFileAlert(this.exportFileName); }
        }
    }

    @java.lang.Override public void cancel()
    { if (null != this.asyncTask) this.asyncTask.cancel(); }
    // endregion
}