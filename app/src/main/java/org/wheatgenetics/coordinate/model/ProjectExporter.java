package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * android.content.Context
 * android.support.annotation.RestrictTo.Scope
 * android.support.annotation.RestrictTo.Scope.SUBCLASSES
 *
 * org.wheatgenetics.androidlibrary.Dir
 *
 * org.wheatgenetics.coordinate.R
 *
 * org.wheatgenetics.coordinate.Utils
 *
 * org.wheatgenetics.coordinate.model.Exporter
 * org.wheatgenetics.coordinate.model.Exporter.AsyncTask
 * org.wheatgenetics.coordinate.model.JoinedGridModel
 * org.wheatgenetics.coordinate.model.JoinedGridModel.Helper
 * org.wheatgenetics.coordinate.model.JoinedGridModels
 */
public class ProjectExporter extends org.wheatgenetics.coordinate.model.Exporter
{
    private static class AsyncTask extends org.wheatgenetics.coordinate.model.Exporter.AsyncTask
    implements org.wheatgenetics.coordinate.model.JoinedGridModel.Helper
    {
        @java.lang.SuppressWarnings({"UnnecessaryInterfaceModifier"})
        private interface Client { public abstract void execute(); }

        // region Fields
        private final org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel;
        private final java.lang.String                                   exportFileName ;
        private final org.wheatgenetics.coordinate.model.ProjectExporter.AsyncTask.Client
            client;
        // endregion

        AsyncTask(final android.content.Context context, final java.io.File exportFile,
        final java.lang.String                                                    exportFileName ,
        final org.wheatgenetics.coordinate.model.JoinedGridModel                  joinedGridModel,
        final org.wheatgenetics.coordinate.model.ProjectExporter.AsyncTask.Client client         )
        {
            super(context, exportFile);

            this.joinedGridModel = joinedGridModel;
            this.exportFileName  = exportFileName ;
            this.client          = client         ;
        }

        // region Overridden Methods
        @java.lang.Override protected void onPostExecute(final java.lang.Boolean result)
        {
            if (null == result || !result)
            {
                assert null != this.joinedGridModel;
                this.setMessage(java.lang.String.format(
                    this.getString(org.wheatgenetics.coordinate
                        .R.string.ProjectExporterAsyncTaskFailedMessage),
                    this.joinedGridModel.getId()));
            }

            super.onPostExecute(result);
        }

        @java.lang.Override @java.lang.SuppressWarnings({"PointlessBooleanExpression"})
        @android.support.annotation.RestrictTo(
            android.support.annotation.RestrictTo.Scope.SUBCLASSES)
        boolean export()
        {
            final boolean success = true;
            if (null == this.joinedGridModel)
                return !success;
            else
            {
                try
                {
                    if (!this.joinedGridModel.export(                  // throws java.io.IOException
                    this.getExportFile(), this.exportFileName, this))
                        return !success;
                }
                catch (final java.io.IOException e)
                {
                    e.printStackTrace();
                    this.setMessage(
                        org.wheatgenetics.coordinate.R.string.GridExporterFailedMessage);
                    return !success;
                }
                this.makeExportFileDiscoverable(); return success;
            }
        }

        @java.lang.Override @android.support.annotation.RestrictTo(
            android.support.annotation.RestrictTo.Scope.SUBCLASSES)
        void handleExportSuccess(final java.io.File exportFile)
        {
            this.alert(); this.share();
            assert null != this.client; this.client.execute();
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

    // region Fields
    private final org.wheatgenetics.coordinate.model.JoinedGridModels joinedGridModels   ;
    private final android.content.Context                             context            ;
    private final org.wheatgenetics.androidlibrary.Dir                exportDir          ;
    private final java.lang.String                                    exportDirectoryName;

    private int                                                          i         = 0   ;
    private org.wheatgenetics.coordinate.model.ProjectExporter.AsyncTask asyncTask = null;
    // endregion

    public ProjectExporter(
    final org.wheatgenetics.coordinate.model.JoinedGridModels joinedGridModels,
    final android.content.Context context, final org.wheatgenetics.androidlibrary.Dir exportDir,
    final java.lang.String exportDirectoryName)
    {
        super();

        this.joinedGridModels = joinedGridModels; this.context             = context            ;
        this.exportDir        = exportDir       ; this.exportDirectoryName = exportDirectoryName;
    }

    // region Overridden Methods
    @java.lang.Override public void execute()
    {
        if (null != this.joinedGridModels)
        {
            final int size = this.joinedGridModels.size(), last = size - 1;
            if (this.i > last)
                this.i = 0;
            else
                if (size > 0 && null != this.exportDir && null != this.context)
                {
                    final org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel =
                        this.joinedGridModels.get(this.i++);
                    if (null != joinedGridModel)
                    {
                        final java.lang.String exportFileName = "grid" +
                            joinedGridModel.getId() + '_' + this.exportDirectoryName + ".csv";
                        try
                        {
                            this.asyncTask =
                                new org.wheatgenetics.coordinate.model.ProjectExporter.AsyncTask(
                                    /* context    => */ this.context,
                                    /* exportFile => */ this.exportDir.createNewFile( // throws ja-
                                        /* fileName => */ exportFileName),            //  va.io.IO-
                                    /* exportFileName  => */ exportFileName ,         //  Exception
                                    /* joinedGridModel => */ joinedGridModel,
                                    /* client          => */ new org.wheatgenetics.coordinate.model
                                        .ProjectExporter.AsyncTask.Client()
                                        {
                                            @java.lang.Override public void execute()
                                            {
                                                org.wheatgenetics.coordinate.model.ProjectExporter
                                                    .this.execute();                    // recursion
                                            }
                                        });
                            this.asyncTask.execute();
                        }
                        catch (final java.io.IOException e)
                        {
                            org.wheatgenetics.coordinate.Utils.alert(
                                /* context => */ this.context,
                                /* title   => */
                                    org.wheatgenetics.coordinate.R.string.ExporterFailTitle,
                                /* message => */ java.lang.String.format(
                                    this.context.getString(org.wheatgenetics.coordinate
                                        .R.string.ProjectExporterFailedMessage),
                                    exportFileName));
                        }
                    }
                }
        }
    }

    @java.lang.Override public void cancel()
    { if (null != this.asyncTask) this.asyncTask.cancel(); }
    // endregion
}