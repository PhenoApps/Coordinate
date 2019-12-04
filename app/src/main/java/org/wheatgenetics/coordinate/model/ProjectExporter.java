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
 * org.wheatgenetics.androidlibrary.RequestDir
 *
 * org.wheatgenetics.coordinate.R
 * org.wheatgenetics.coordinate.Utils
 *
 * org.wheatgenetics.coordinate.model.BaseJoinedGridModels
 * org.wheatgenetics.coordinate.model.Exporter
 * org.wheatgenetics.coordinate.model.Exporter.AsyncTask
 * org.wheatgenetics.coordinate.model.JoinedGridModel.Helper
 */
abstract class ProjectExporter extends org.wheatgenetics.coordinate.model.Exporter
{
    abstract static class AsyncTask extends org.wheatgenetics.coordinate.model.Exporter.AsyncTask
    implements org.wheatgenetics.coordinate.model.JoinedGridModel.Helper
    {
        private final java.lang.String exportFileName;

        AsyncTask(@androidx.annotation.NonNull final android.content.Context context,
        final java.io.File exportFile, final java.lang.String exportFileName)
        { super(context, exportFile); this.exportFileName = exportFileName; }

        // region org.wheatgenetics.coordinate.model.JoinedGridModel.Helper Overridden Method
        @java.lang.Override
        public void publishProgress(@androidx.annotation.IntRange(from = 1) final int col)
        {
            this.publishProgress(this.getString(
                org.wheatgenetics.coordinate.R.string.GridExporterProgressDialogMessage) + col);
        }
        // endregion

        // region Package Methods
        @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
        java.lang.String getExportFileName() { return this.exportFileName; }

        @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
        java.lang.Boolean setMessage(final java.lang.Boolean result, final java.lang.String message)
        { if (null == result || !result) this.setMessage(message); return result; }
        // endregion
    }

    // region Fields
    private final org.wheatgenetics.coordinate.model.BaseJoinedGridModels baseJoinedGridModels;
    @androidx.annotation.NonNull private final android.content.Context    context             ;
                                 private final org.wheatgenetics.androidlibrary.RequestDir
                                    exportDir;
    // endregion

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    ProjectExporter(
    final org.wheatgenetics.coordinate.model.BaseJoinedGridModels baseJoinedGridModels,
    @androidx.annotation.NonNull final android.content.Context                     context  ,
                                 final org.wheatgenetics.androidlibrary.RequestDir exportDir)
    {
        super();

        this.baseJoinedGridModels = baseJoinedGridModels;
        this.context              = context         ;
        this.exportDir            = exportDir       ;
    }

    // region Protected Methods
    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    org.wheatgenetics.coordinate.model.BaseJoinedGridModels getBaseJoinedGridModels()
    { return this.baseJoinedGridModels; }

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @androidx.annotation.NonNull android.content.Context getContext() { return this.context; }

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    org.wheatgenetics.androidlibrary.RequestDir getExportDir() { return this.exportDir; }

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    void unableToCreateFileAlert(final java.lang.String exportFileName)
    {
        org.wheatgenetics.coordinate.Utils.alert(
            /* context => */ this.context                                           ,
            /* title   => */ org.wheatgenetics.coordinate.R.string.ExporterFailTitle,
            /* message => */ java.lang.String.format(this.context.getString(
                    org.wheatgenetics.coordinate.R.string.ProjectExporterFailedMessage),
                exportFileName));
    }
    // endregion
}