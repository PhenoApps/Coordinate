package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * android.content.Context context
 * android.support.annotation.RestrictTo
 * android.support.annotation.RestrictTo.Scope
 *
 * org.wheatgenetics.javalib.Dir.PermissionException
 *
 * org.wheatgenetics.androidlibrary.RequestDir
 *
 * org.wheatgenetics.coordinate.R
 * org.wheatgenetics.coordinate.Utils
 *
 * org.wheatgenetics.coordinate.model.JoinedGridModel
 * org.wheatgenetics.coordinate.model.JoinedGridModels
 * org.wheatgenetics.coordinate.model.ProjectExporter
 * org.wheatgenetics.coordinate.model.ProjectExporter.AsyncTask
 */
public class PerGridProjectExporter extends org.wheatgenetics.coordinate.model.ProjectExporter
{
    private static class AsyncTask
    extends org.wheatgenetics.coordinate.model.ProjectExporter.AsyncTask
    {
        @java.lang.SuppressWarnings({"UnnecessaryInterfaceModifier"})
        private interface Client { public abstract void execute(); }

        // region Fields
        private final org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel;
        private final org.wheatgenetics.coordinate.model.PerGridProjectExporter.AsyncTask.Client
            client;
        // endregion

        private AsyncTask(final android.content.Context context, final java.io.File exportFile,
        final java.lang.String                                   exportFileName,
        final org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel,
        final org.wheatgenetics.coordinate.model.PerGridProjectExporter.AsyncTask.Client client)
        {
            super(context, exportFile, exportFileName);
            this.joinedGridModel = joinedGridModel; this.client = client;
        }

        // region Overridden Methods
        @java.lang.Override protected void onPostExecute(final java.lang.Boolean result)
        {
            assert null != this.joinedGridModel;
            super.onPostExecute(this.setMessage(result, java.lang.String.format(
                this.getString(org.wheatgenetics.coordinate
                    .R.string.PerGridProjectExporterAsyncTaskFailedMessage),
                this.joinedGridModel.getId())));
        }

        @java.lang.Override @android.support.annotation.RestrictTo(
            android.support.annotation.RestrictTo.Scope.SUBCLASSES)
        boolean export()
        {
            boolean success;
            if (null == this.joinedGridModel)
                success = false;
            else
            {
                try
                {
                    if (this.joinedGridModel.export(                   // throws java.io.IOException
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
                    this.setMessage(
                        org.wheatgenetics.coordinate.R.string.GridExporterFailedMessage);
                    success = false;
                }
            }
            return success;
        }

        @java.lang.Override @android.support.annotation.RestrictTo(
            android.support.annotation.RestrictTo.Scope.SUBCLASSES)
        void handleExportSuccess(final java.io.File exportFile)
        { assert null != this.client; this.client.execute(); }
        // endregion
    }

    // region Fields
    private final java.lang.String exportDirectoryName;

    private @android.support.annotation.IntRange(from = 0) int                  i         = 0   ;
    private org.wheatgenetics.coordinate.model.PerGridProjectExporter.AsyncTask asyncTask = null;
    // endregion

    public PerGridProjectExporter(
    final org.wheatgenetics.coordinate.model.JoinedGridModels joinedGridModels   ,
    final android.content.Context                             context            ,
    final org.wheatgenetics.androidlibrary.RequestDir         exportDir          ,
    final java.lang.String                                    exportDirectoryName)
    { super(joinedGridModels, context, exportDir); this.exportDirectoryName = exportDirectoryName; }

    // region Overridden Methods
    @java.lang.Override public void execute()
    {
        final org.wheatgenetics.coordinate.model.JoinedGridModels joinedGridModels =
            this.getJoinedGridModels();
        if (null != joinedGridModels)
        {
            final int size = joinedGridModels.size(), last = size - 1;
            if (this.i > last)
            {
                this.i = 0;
                org.wheatgenetics.coordinate.Utils.alert(
                    /* context => */ this.getContext(),
                    /* message => */
                        org.wheatgenetics.coordinate.R.string.PerGridProjectExporterSuccessMessage);
            }
            else
                if (size > 0)
                {
                    final org.wheatgenetics.androidlibrary.RequestDir exportDir =
                        this.getExportDir();
                    if (null != exportDir)
                    {
                        final android.content.Context context = this.getContext();
                        if (null != context)
                        {
                            final org.wheatgenetics.coordinate.model.JoinedGridModel
                                joinedGridModel = joinedGridModels.get(this.i++);
                            if (null != joinedGridModel)
                            {
                                @java.lang.SuppressWarnings({"DefaultLocale"})
                                final java.lang.String exportFileName = java.lang.String.format(
                                    "grid%d_%s.csv",
                                    joinedGridModel.getId(), this.exportDirectoryName);
                                try
                                {
                                    this.asyncTask = new org.wheatgenetics.coordinate.model
                                        .PerGridProjectExporter.AsyncTask(
                                            /* context    => */ context,
                                            /* exportFile => */ exportDir.createNewFile( // throws
                                                /* fileName => */ exportFileName),       //  IOE, PE
                                            /* exportFileName  => */ exportFileName ,
                                            /* joinedGridModel => */ joinedGridModel,
                                            /* client => */ new org.wheatgenetics.coordinate.model
                                                .PerGridProjectExporter.AsyncTask.Client()
                                                {
                                                    @java.lang.Override public void execute()
                                                    {
                                                        org.wheatgenetics.coordinate.model
                                                            .PerGridProjectExporter.this
                                                            .execute();                 // recursion
                                                    }
                                                });
                                    this.asyncTask.execute();
                                }
                                catch (final java.io.IOException |
                                org.wheatgenetics.javalib.Dir.PermissionException e)
                                { this.unableToCreateFileAlert(exportFileName); }
                            }
                        }
                    }
                }
        }
    }

    @java.lang.Override public void cancel()
    { if (null != this.asyncTask) this.asyncTask.cancel(); }
    // endregion
}