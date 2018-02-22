package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * android.content.Context
 * android.content.DialogInterface
 * android.content.DialogInterface.OnCancelListener
 * android.os.AsyncTask
 * android.support.annotation.RestrictTo
 * android.support.annotation.RestrictTo.Scope
 *
 * org.wheatgenetics.androidlibrary.ProgressDialog
 * org.wheatgenetics.androidlibrary.Utils
 *
 * org.wheatgenetics.coordinate.R
 */
@java.lang.SuppressWarnings("ClassExplicitlyExtendsObject")
public abstract class Exporter extends java.lang.Object
{
    // region Types
    @java.lang.SuppressWarnings("UnnecessaryInterfaceModifier")
    public interface Helper
    {
        public abstract void handleExportDone(java.lang.Boolean result,
            java.lang.String message, java.io.File exportFile);
    }

    abstract static class AsyncTask
    extends android.os.AsyncTask<java.lang.Void, java.lang.String, java.lang.Boolean>
    implements android.content.DialogInterface.OnCancelListener
    {
        // region Fields
        private final android.content.Context                            context       ;
        private final org.wheatgenetics.androidlibrary.ProgressDialog    progressDialog;
        private final java.io.File                                       exportFile    ;
        private final org.wheatgenetics.coordinate.model.Exporter.Helper helper        ;

        private java.lang.String message = null;
        // endregion

        @android.support.annotation.RestrictTo(
            android.support.annotation.RestrictTo.Scope.SUBCLASSES)
        AsyncTask(final android.content.Context context, final java.io.File exportFile,
        final org.wheatgenetics.coordinate.model.Exporter.Helper helper)
        {
            super();

            this.context        = context;
            this.progressDialog = new org.wheatgenetics.androidlibrary.ProgressDialog(this.context,
                /* title => */ org.wheatgenetics.coordinate.R.string.ExporterProgressDialogTitle,
                /* initialMessage => */
                    org.wheatgenetics.coordinate.R.string.ExporterProgressDialogInitialMessage,
                /* onCancelListener => */ this);

            this.exportFile = exportFile; this.helper = helper;
        }

        // region Overridden Methods
        @java.lang.Override
        protected void onPreExecute() { super.onPreExecute(); this.progressDialog.show(); }

        @java.lang.Override
        @java.lang.SuppressWarnings("ResultOfMethodCallIgnored")
        protected java.lang.Boolean doInBackground(final java.lang.Void... params)
        {
            if (null == this.exportFile)
                return false;
            else
            {
                if (this.exportFile.exists()) this.exportFile.delete();
                return this.export();
            }
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
        // endregion

        // region Package Methods
        @android.support.annotation.RestrictTo(
            android.support.annotation.RestrictTo.Scope.SUBCLASSES)
        abstract boolean export();

        void cancel() { this.cancel(/* mayInterruptIfRunning => */ true); }


        @android.support.annotation.RestrictTo(
            android.support.annotation.RestrictTo.Scope.SUBCLASSES)
        java.io.File getExportFile() { return this.exportFile; }

        @android.support.annotation.RestrictTo(
            android.support.annotation.RestrictTo.Scope.SUBCLASSES)
        org.wheatgenetics.coordinate.model.Exporter.Helper getHelper() { return this.helper; }


        @android.support.annotation.RestrictTo(
            android.support.annotation.RestrictTo.Scope.SUBCLASSES)
        void makeExportFileDiscoverable()
        {
            org.wheatgenetics.androidlibrary.Utils.makeFileDiscoverable(
                this.context, this.exportFile);
        }

        @android.support.annotation.RestrictTo(
            android.support.annotation.RestrictTo.Scope.SUBCLASSES)
        java.lang.String getString(final int resId)
        { assert null != this.context; return this.context.getString(resId); }

        @android.support.annotation.RestrictTo(
            android.support.annotation.RestrictTo.Scope.SUBCLASSES)
        void setMessage(final int resId) { this.message = this.getString(resId); }
        // endregion
    }
    // endregion

    // region Public Methods
    public abstract void execute();
    public abstract void cancel ();
    // endregion
}