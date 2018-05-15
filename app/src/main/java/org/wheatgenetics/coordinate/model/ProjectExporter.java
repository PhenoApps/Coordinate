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
 * org.wheatgenetics.coordinate.model.JoinedGridModel.Helper
 * org.wheatgenetics.coordinate.model.JoinedGridModels
 */
abstract class ProjectExporter extends org.wheatgenetics.coordinate.model.Exporter
{
    abstract static class AsyncTask extends org.wheatgenetics.coordinate.model.Exporter.AsyncTask
    implements org.wheatgenetics.coordinate.model.JoinedGridModel.Helper
    {
        private final java.lang.String exportFileName;

        AsyncTask(final android.content.Context context, final java.io.File exportFile,
        final java.lang.String exportFileName)
        { super(context, exportFile); this.exportFileName = exportFileName; }

        // region Overridden Methods
        @java.lang.Override @android.support.annotation.RestrictTo(
            android.support.annotation.RestrictTo.Scope.SUBCLASSES)
        void handleExportSuccess(final java.io.File exportFile) { this.alert(); this.share(); }

        // region org.wheatgenetics.coordinate.model.JoinedGridModel.Helper Overridden Method
        @java.lang.Override public void publishProgress(final int col)
        {
            this.publishProgress(this.getString(
                org.wheatgenetics.coordinate.R.string.GridExporterProgressDialogMessage) + col);
        }
        // endregion
        // endregion

        // region Package Methods
        @android.support.annotation.RestrictTo(
            android.support.annotation.RestrictTo.Scope.SUBCLASSES)
        java.lang.String getExportFileName() { return this.exportFileName; }

        @android.support.annotation.RestrictTo(
            android.support.annotation.RestrictTo.Scope.SUBCLASSES)
        java.lang.Boolean setMessage(final java.lang.Boolean result, final java.lang.String message)
        { if (null == result || !result) this.setMessage(message); return result; }
        // endregion
    }

    // region Fields
    private final org.wheatgenetics.coordinate.model.JoinedGridModels joinedGridModels;
    private final android.content.Context                             context         ;
    private final org.wheatgenetics.androidlibrary.Dir                exportDir       ;
    // endregion

    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    ProjectExporter(final org.wheatgenetics.coordinate.model.JoinedGridModels joinedGridModels,
    final android.content.Context context, final org.wheatgenetics.androidlibrary.Dir exportDir)
    {
        super();

        this.joinedGridModels = joinedGridModels;
        this.context          = context         ;
        this.exportDir        = exportDir       ;
    }

    // region Protected Methods
    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    org.wheatgenetics.coordinate.model.JoinedGridModels getJoinedGridModels()
    { return this.joinedGridModels; }

    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    android.content.Context getContext() { return this.context; }

    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    org.wheatgenetics.androidlibrary.Dir getExportDir() { return this.exportDir; }

    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    void unableToCreateFileAlert(final java.lang.String exportFileName)
    {
        if (null != this.context) org.wheatgenetics.coordinate.Utils.alert(
            /* context => */ this.context                                           ,
            /* title   => */ org.wheatgenetics.coordinate.R.string.ExporterFailTitle,
            /* message => */ java.lang.String.format(this.context.getString(
                    org.wheatgenetics.coordinate.R.string.ProjectExporterFailedMessage),
                exportFileName));
    }
    // endregion
}