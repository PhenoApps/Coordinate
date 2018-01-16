package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * android.content.Context
 * android.content.DialogInterface
 * android.content.DialogInterface.OnCancelListener
 * android.os.AsyncTask
 *
 * org.wheatgenetics.androidlibrary.ProgressDialog
 * org.wheatgenetics.androidlibrary.Utils
 *
 * org.wheatgenetics.coordinate.R
 *
 * org.wheatgenetics.coordinate.model.JoinedGridModel.Helper
 */
@java.lang.SuppressWarnings("ClassExplicitlyExtendsObject")
public class Exporter extends java.lang.Object
{
    // region Types
    @java.lang.SuppressWarnings("UnnecessaryInterfaceModifier")
    public interface Helper
    {
        public abstract org.wheatgenetics.coordinate.model.JoinedGridModel getJoinedGridModel();
        public abstract void handleExportDone(java.lang.Boolean result,
            java.lang.String message, java.io.File exportFile);
    }

    private static class AsyncTask
    extends android.os.AsyncTask<java.lang.Void, java.lang.String, java.lang.Boolean> implements
    android.content.DialogInterface.OnCancelListener,
    org.wheatgenetics.coordinate.model.JoinedGridModel.Helper
    {
        // region Fields
        private final android.content.Context                            context       ;
        private final org.wheatgenetics.androidlibrary.ProgressDialog    progressDialog;
        private final java.io.File                                       exportFile    ;
        private final java.lang.String                                   exportFileName;
        private final org.wheatgenetics.coordinate.model.Exporter.Helper helper        ;

        private java.lang.String message = null;
        // endregion

        private void cancel() { this.cancel(/* mayInterruptIfRunning => */ true); }

        private AsyncTask(final android.content.Context context, final java.io.File exportFile,
        final java.lang.String exportFileName,
        final org.wheatgenetics.coordinate.model.Exporter.Helper helper)
        {
            super();

            this.context        = context;
            this.progressDialog = new org.wheatgenetics.androidlibrary.ProgressDialog(this.context,
                /* title => */ org.wheatgenetics.coordinate.R.string.ExporterProgressDialogTitle,
                /* initialMessage => */
                    org.wheatgenetics.coordinate.R.string.ExporterProgressDialogInitialMessage,
                /* onCancelListener => */ this);

            this.exportFile     = exportFile    ;
            this.exportFileName = exportFileName;
            this.helper         = helper        ;
        }

        // region Overridden Methods
        @java.lang.Override
        protected void onPreExecute() { super.onPreExecute(); this.progressDialog.show(); }

        @java.lang.Override @java.lang.SuppressWarnings("ResultOfMethodCallIgnored")
        protected java.lang.Boolean doInBackground(final java.lang.Void... params)
        {
            boolean success = false;
            if (null != this.exportFile)
            {
                if (this.exportFile.exists()) this.exportFile.delete();

                assert null != this.helper;
                final org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel =
                    this.helper.getJoinedGridModel();
                if (null != joinedGridModel)
                    try
                    {
                        if (joinedGridModel.export(                    // throws java.io.IOException
                        this.exportFile, this.exportFileName, this))
                        {
                            org.wheatgenetics.androidlibrary.Utils.makeFileDiscoverable(
                                this.context, this.exportFile);
                            success = true;
                        }
                    }
                    catch (final java.io.IOException e)
                    {
                        e.printStackTrace();
                        assert null != this.context; this.message = this.context.getString(
                            org.wheatgenetics.coordinate.R.string.ExporterFailedMessage);
                    }
            }
            return success;
        }

        @java.lang.Override
        protected void onProgressUpdate(final java.lang.String... messages)
        {
            if (null != messages)
            {
                final java.lang.String message = messages[0];
                if (null != message) this.progressDialog.setMessage(message);
            }
        }

        @java.lang.Override
        protected void onPostExecute(final java.lang.Boolean result)
        {
            this.progressDialog.dismiss();
            assert null != this.helper; this.helper.handleExportDone(
                result, this.message, this.exportFile);
        }

        // region android.content.DialogInterface.OnCancelListener Overridden Method
        @java.lang.Override
        public void onCancel(final android.content.DialogInterface dialog) { this.cancel(); }
        // endregion

        // region org.wheatgenetics.coordinate.model.JoinedGridModel.Helper Overridden Method
        @java.lang.Override
        public void publishProgress(final int col)
        {
            assert null != this.context;
            this.publishProgress(this.context.getString(
                org.wheatgenetics.coordinate.R.string.ExporterProgressDialogMessage) + col);
        }
        // endregion
        // endregion
    }
    // endregion

    private final org.wheatgenetics.coordinate.model.Exporter.AsyncTask asyncTask;

    public Exporter(final android.content.Context context, final java.io.File exportFile,
    final java.lang.String exportFileName,
    final org.wheatgenetics.coordinate.model.Exporter.Helper helper)
    {
        super();
        this.asyncTask = new org.wheatgenetics.coordinate.model.Exporter.AsyncTask(
            context, exportFile, exportFileName, helper);
    }

    // region Public Methods
    public void execute() { this.asyncTask.execute(); }
    public void cancel () { this.asyncTask.cancel (); }
    // endregion
}