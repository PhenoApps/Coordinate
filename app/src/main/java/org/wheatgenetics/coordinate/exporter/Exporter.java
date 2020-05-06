package org.wheatgenetics.coordinate.exporter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.StringRes;

import org.wheatgenetics.androidlibrary.ProgressDialog;
import org.wheatgenetics.coordinate.R;
import org.wheatgenetics.coordinate.Utils;

import java.io.File;

abstract class Exporter {
    // region Public Methods
    @SuppressWarnings({"unused"})
    public abstract void execute();

    @SuppressWarnings({"unused"})
    public abstract void cancel();

    @SuppressLint({"StaticFieldLeak"})
    abstract static class AsyncTask
            extends android.os.AsyncTask<Void, String, Boolean>
            implements DialogInterface.OnCancelListener {
        // region Fields
        @NonNull
        private final Context context;
        @NonNull
        private final ProgressDialog
                progressDialog;
        private final File exportFile;

        private String message = null;
        // endregion

        @RestrictTo(RestrictTo.Scope.SUBCLASSES)
        AsyncTask(@NonNull final Context context,
                  final File exportFile) {
            super();

            this.context = context;
            this.progressDialog = new ProgressDialog(this.context,
                    /* title => */ R.string.ExporterProgressDialogTitle,
                    /* initialMessage => */
                    R.string.ExporterProgressDialogInitialMessage,
                    /* onCancelListener => */this);
            this.exportFile = exportFile;
        }

        private void confirm(@StringRes final int message,
                             final Runnable yesRunnable) {
            class NoRunnable extends Object implements Runnable {
                @Override
                public void run() {
                    Exporter.AsyncTask.this.share();
                }
            }

            Utils.confirm(
                    this.context, message, yesRunnable, new NoRunnable());
        }

        // region Overridden Methods
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.progressDialog.show();
        }

        @Override
        protected Boolean doInBackground(final Void... params) {
            if (null == this.exportFile)
                return false;
            else {
                if (this.exportFile.exists())
                    // noinspection ResultOfMethodCallIgnored
                    this.exportFile.delete();
                return this.export();
            }
        }

        @Override
        protected void onProgressUpdate(final String... messages) {
            if (null != messages) {
                final String message = messages[0];
                if (null != message) this.progressDialog.setMessage(message);
            }
        }

        @Override
        protected void onPostExecute(final Boolean result) {
            this.progressDialog.dismiss();
            if (null != result && result)
                this.handleExportSuccess(this.exportFile);
            else
                Utils.alert(
                        /* context => */ this.context,
                        /* title   => */ R.string.ExporterFailTitle,
                        /* message => */ org.wheatgenetics.javalib.Utils.replaceIfNull(this.message,
                                this.getString(R.string.ExporterFailMessage)));
        }

        // region android.content.DialogInterface.OnCancelListener Overridden Method
        @Override
        public void onCancel(final DialogInterface dialog) {
            this.cancel();
        }
        // endregion
        // endregion

        // region Package Methods
        // region Exporter.AsyncTask Package Methods
        @RestrictTo(RestrictTo.Scope.SUBCLASSES)
        abstract boolean export();

        @RestrictTo(RestrictTo.Scope.SUBCLASSES)
        abstract void handleExportSuccess(final File exportFile);

        @RestrictTo(RestrictTo.Scope.SUBCLASSES)
        String getString(@StringRes final int resId) {
            return this.context.getString(resId);
        }

        void cancel() {
            this.cancel(/* mayInterruptIfRunning => */true);
        }
        // endregion

        // region Subclass Package Methods
        // region export() Subclass Package Methods
        @RestrictTo(RestrictTo.Scope.SUBCLASSES)
        @Nullable
        File getExportFile() {
            return this.exportFile;
        }

        @RestrictTo(RestrictTo.Scope.SUBCLASSES)
        void makeExportFileDiscoverable() {
            org.wheatgenetics.androidlibrary.Utils.makeFileDiscoverable(
                    this.context, this.exportFile);
        }
        // endregion

        // region handleExportSuccess() Subclass Package Method
        @RestrictTo(RestrictTo.Scope.SUBCLASSES)
        void share() {
            if (null != this.exportFile) {
                final Intent intent =
                        new Intent(Intent.ACTION_SEND)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET)
                                .putExtra(Intent.EXTRA_STREAM,
                                        Uri.parse(this.exportFile.getAbsolutePath()))
                                .setType("text/plain");

                this.context.startActivity(Intent.createChooser(intent,
                        this.getString(R.string.ExporterShareTitle)));
            }
        }
        // endregion
        // endregion

        // region GridExporter.AsyncTask Package Method
        @RestrictTo(RestrictTo.Scope.SUBCLASSES)
        void alert(@SuppressWarnings({"SameParameterValue"})
                   @StringRes final int message,
                   final Runnable yesRunnable) {
            Utils.alert(this.context,
                    R.string.ExporterSuccessTitle, new Runnable() {
                        @Override
                        public void run() {
                            Exporter.AsyncTask.this.confirm(
                                    message, yesRunnable);
                        }
                    });
        }
        // endregion

        // region ProjectExporter.AsyncTask Package Method
        @RestrictTo(RestrictTo.Scope.SUBCLASSES)
        void setMessage(final String message) {
            this.message = message;
        }
        // endregion

        // region TemplateExporter.AsyncTask, EntireProjectProjectExporter.AsyncTask Package Method
        @RestrictTo(RestrictTo.Scope.SUBCLASSES)
        void alert() {
            Utils.alert(
                    /* context => */ this.context,
                    /* message => */ R.string.ExporterSuccessTitle);
        }
        // endregion

        // region GridExporter.AsyncTask, PerGridProjectExporter.AsyncTask, EntireProjectProjectExporter.AsyncTask Package Method
        @RestrictTo(RestrictTo.Scope.SUBCLASSES)
        void setMessage(@StringRes final int resId) {
            this.message = this.getString(resId);
        }
        // endregion
        // endregion
    }
    // endregion
}