package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * android.content.Context
 * android.support.annotation.RestrictTo
 * android.support.annotation.RestrictTo.Scope
 *
 * org.wheatgenetics.javalib.Dir.PermissionException
 *
 * org.wheatgenetics.androidlibrary.RequestDir
 *
 * org.wheatgenetics.coordinate.R
 *
 * org.wheatgenetics.coordinate.model.JoinedGridModels
 * org.wheatgenetics.coordinate.model.ProjectExporter
 * org.wheatgenetics.coordinate.model.ProjectExporter.AsyncTask
 */
public class EntireProjectProjectExporter extends org.wheatgenetics.coordinate.model.ProjectExporter
{
    private static class AsyncTask
    extends org.wheatgenetics.coordinate.model.ProjectExporter.AsyncTask
    {
        private final org.wheatgenetics.coordinate.model.JoinedGridModels joinedGridModels;

        private AsyncTask(final android.content.Context context, final java.io.File exportFile,
        final java.lang.String                                   exportFileName   ,
        final org.wheatgenetics.coordinate.model.JoinedGridModels joinedGridModels)
        { super(context, exportFile, exportFileName); this.joinedGridModels = joinedGridModels; }

        // region Overridden Methods
        @java.lang.Override protected void onPostExecute(final java.lang.Boolean result)
        {
            super.onPostExecute(this.setMessage(result, this.getString(org.wheatgenetics.coordinate
                .R.string.EntireProjectProjectExporterAsyncTaskFailedMessage)));
        }

        @java.lang.Override @android.support.annotation.RestrictTo(
            android.support.annotation.RestrictTo.Scope.SUBCLASSES)
        boolean export()
        {
            boolean success;
            if (null == this.joinedGridModels)
                success = false;
            else
            {
                try
                {
                    if (this.joinedGridModels.export(                  // throws java.io.IOException
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

        @java.lang.Override @android.support.annotation.RestrictTo(
            android.support.annotation.RestrictTo.Scope.SUBCLASSES)
        void handleExportSuccess(final java.io.File exportFile) { this.alert(); this.share(); }
        // endregion
    }

    // region Fields
    private final java.lang.String exportFileName;
    private       org.wheatgenetics.coordinate.model.EntireProjectProjectExporter.AsyncTask
        asyncTask = null;
    // endregion

    public EntireProjectProjectExporter(
    final org.wheatgenetics.coordinate.model.JoinedGridModels joinedGridModels,
    final android.content.Context                             context         ,
    final org.wheatgenetics.androidlibrary.RequestDir         exportDir       ,
    final java.lang.String                                    exportFileName  )
    { super(joinedGridModels, context, exportDir); this.exportFileName = exportFileName + ".csv"; }

    // region Overridden Methods
    @java.lang.Override public void execute()
    {
        final org.wheatgenetics.coordinate.model.JoinedGridModels joinedGridModels =
            this.getJoinedGridModels();
        if (null != joinedGridModels)
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
                            /* exportFileName   => */ this.exportFileName,
                            /* joinedGridModels => */ joinedGridModels   );
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