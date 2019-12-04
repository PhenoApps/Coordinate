package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * android.content.Context
 *
 * androidx.annotation.IntRange
 * androidx.annotation.NonNull
 * androidx.annotation.RestrictTo
 * androidx.annotation.RestrictTo.Scope
 *
 * org.wheatgenetics.javalib.Dir.PermissionException
 *
 * org.wheatgenetics.androidlibrary.RequestDir
 *
 * org.wheatgenetics.coordinate.R
 * org.wheatgenetics.coordinate.Utils
 *
 * org.wheatgenetics.coordinate.model.BaseJoinedGridModels
 * org.wheatgenetics.coordinate.model.JoinedGridModel
 * org.wheatgenetics.coordinate.model.ProjectExporter
 * org.wheatgenetics.coordinate.model.ProjectExporter.AsyncTask
 */
public class PerGridProjectExporter extends org.wheatgenetics.coordinate.model.ProjectExporter
{
    private static class AsyncTask
    extends org.wheatgenetics.coordinate.model.ProjectExporter.AsyncTask
    {
        @java.lang.SuppressWarnings({"UnnecessaryInterfaceModifier"}) private interface Client
        { public abstract void execute(); }

        // region Fields
        @androidx.annotation.NonNull private final
            org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel;
        @androidx.annotation.NonNull private final
            org.wheatgenetics.coordinate.model.PerGridProjectExporter.AsyncTask.Client client;
        // endregion

        private AsyncTask(@androidx.annotation.NonNull final android.content.Context context,
        final java.io.File exportFile, final java.lang.String exportFileName,
        @androidx.annotation.NonNull final org.wheatgenetics.coordinate.model.JoinedGridModel
            joinedGridModel,
        @androidx.annotation.NonNull final
            org.wheatgenetics.coordinate.model.PerGridProjectExporter.AsyncTask.Client client)
        {
            super(context, exportFile, exportFileName);
            this.joinedGridModel = joinedGridModel; this.client = client;
        }

        // region Overridden Methods
        @java.lang.Override protected void onPostExecute(final java.lang.Boolean result)
        {
            super.onPostExecute(this.setMessage(result, java.lang.String.format(
                this.getString(org.wheatgenetics.coordinate
                    .R.string.PerGridProjectExporterAsyncTaskFailedMessage),
                this.joinedGridModel.getId())));
        }

        @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
        @java.lang.Override boolean export()
        {
            boolean success;
            try
            {
                if (this.joinedGridModel.export(                       // throws java.io.IOException
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
            return success;
        }

        @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
        @java.lang.Override void handleExportSuccess(final java.io.File exportFile)
        { this.client.execute(); }
        // endregion
    }

    // region Fields
    private final java.lang.String exportDirectoryName;

    @androidx.annotation.IntRange(from = 0) private int                         i         =    0;
    private org.wheatgenetics.coordinate.model.PerGridProjectExporter.AsyncTask asyncTask = null;
    // endregion

    public PerGridProjectExporter(
    final org.wheatgenetics.coordinate.model.BaseJoinedGridModels baseJoinedGridModels,
    @androidx.annotation.NonNull final android.content.Context                     context  ,
                                 final org.wheatgenetics.androidlibrary.RequestDir exportDir,
                                 final java.lang.String                  exportDirectoryName)
    {
        super(baseJoinedGridModels, context, exportDir);
        this.exportDirectoryName = exportDirectoryName;
    }

    // region Overridden Methods
    @java.lang.Override public void execute()
    {
        final org.wheatgenetics.coordinate.model.BaseJoinedGridModels baseJoinedGridModels =
            this.getBaseJoinedGridModels();
        if (null != baseJoinedGridModels)
        {
            final int size = baseJoinedGridModels.size(), last = size - 1;
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
                        final org.wheatgenetics.coordinate.model.JoinedGridModel
                            joinedGridModel = baseJoinedGridModels.get(this.i++);
                        if (null != joinedGridModel)
                        {
                            @java.lang.SuppressWarnings({"DefaultLocale"})
                            final java.lang.String exportFileName = java.lang.String.format(
                                "grid%d_%s.csv", joinedGridModel.getId(), this.exportDirectoryName);
                            try
                            {
                                this.asyncTask = new org.wheatgenetics.coordinate.model
                                    .PerGridProjectExporter.AsyncTask(
                                        /* context    => */ context,
                                        /* exportFile => */ exportDir.createNewFile(     // throws
                                            /* fileName => */ exportFileName),           //  IOE, PE
                                        /* exportFileName  => */ exportFileName ,
                                        /* joinedGridModel => */ joinedGridModel,
                                        /* client => */ new org.wheatgenetics.coordinate.model
                                            .PerGridProjectExporter.AsyncTask.Client()
                                            {
                                                @java.lang.Override public void execute()
                                                {
                                                    org.wheatgenetics.coordinate.model
                                                        .PerGridProjectExporter.this
                                                        .execute();                     // recursion
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

    @java.lang.Override public void cancel()
    { if (null != this.asyncTask) this.asyncTask.cancel(); }
    // endregion
}