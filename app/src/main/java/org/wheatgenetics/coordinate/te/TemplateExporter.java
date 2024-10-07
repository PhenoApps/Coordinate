package org.wheatgenetics.coordinate.te;

import android.app.Activity;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.documentfile.provider.DocumentFile;

import org.phenoapps.permissions.Dir;
import org.wheatgenetics.coordinate.R;
import org.wheatgenetics.coordinate.TemplatesDir;
import org.wheatgenetics.coordinate.Utils;
import org.wheatgenetics.coordinate.database.TemplatesTable;
import org.wheatgenetics.coordinate.utils.DocumentTreeUtil;
import org.wheatgenetics.coordinate.utils.FileUtil;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

public class TemplateExporter {
    // region Fields
    @NonNull
    private final Activity activity;
    private final int requestCode;

    @IntRange(from = 1)
    private long templateId;
    private String fileName;

    private org.wheatgenetics.coordinate.exporter.TemplateExporter templateExporter = null;
    private TemplatesTable
            templatesTableInstance = null;                                                  // lazy load
    // endregion

    public TemplateExporter(@NonNull final Activity activity,
                            final int requestCode) {
        super();
        this.activity = activity;
        this.requestCode = requestCode;
    }

    // region Private Methods
    private void showLongToast(final String text) {
        org.phenoapps.androidlibrary.Utils.showLongToast(this.activity, text);
    }
    // endregion

    @NonNull
    private TemplatesTable templatesTable() {
        if (null == this.templatesTableInstance) this.templatesTableInstance =
                new TemplatesTable(this.activity);
        return this.templatesTableInstance;
    }

    @Override
    protected void finalize() throws Throwable {
        if (null != this.templateExporter) {
            this.templateExporter.cancel();
            this.templateExporter = null;
        }
        super.finalize();
    }

    // region Public Methods
    public void export() {
        File exportFile;

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            try {
                final TemplatesDir templatesDir =
                        Utils.templatesDir(             // throws IOException,
                                this.activity, this.requestCode);                        //  PermissionException

                exportFile = templatesDir.createNewFile(      // throws IOException, PermissionException
                        this.fileName + ".xml");
            } catch (final IOException | Dir.PermissionException e) {
                if (!(e instanceof Dir.PermissionRequestedException))
                    this.showLongToast(e.getMessage());
                exportFile = null;
            }

            if (null != exportFile) {
                this.templateExporter = new org.wheatgenetics.coordinate.exporter.TemplateExporter(
                        /* context       => */ this.activity,
                        /* exportFile    => */ exportFile,
                        /* templateModel => */ this.templatesTable().get(this.templateId));
                this.templateExporter.execute();
            }

        } else {

            if (DocumentTreeUtil.Companion.isEnabled(activity)) {

                DocumentFile file = DocumentTreeUtil.Companion.createFile(activity, activity.getString(R.string.template_dir),
                        this.templatesTable().get(this.templateId).getTitle() + ".xml");

                if (file != null) {
                    try {
                        OutputStream output = activity.getContentResolver().openOutputStream(file.getUri());
                        this.templateExporter = new org.wheatgenetics.coordinate.exporter.TemplateExporter(
                                this.activity, output, this.templatesTable().get(this.templateId));
                        this.templateExporter.execute();

                        FileUtil.Companion.shareFile(activity, file.getUri());

                    } catch (IOException io) {
                        io.printStackTrace();
                    }

                }

            } else {

                File templatesDir = new File(activity.getExternalFilesDir(null), activity.getString(R.string.template_dir));
                if (!templatesDir.isDirectory()) {
                    if (!templatesDir.mkdir()) {
                        Log.d("MakeDir", "Make dir failed for templates folder.");
                    }
                }
                exportFile = new File(templatesDir, this.fileName + ".xml");
                this.templateExporter = new org.wheatgenetics.coordinate.exporter.TemplateExporter(
                        /* context       => */ this.activity,
                        /* exportFile    => */ exportFile,
                        /* templateModel => */ this.templatesTable().get(this.templateId));
                this.templateExporter.execute();

                FileUtil.Companion.shareFile(activity, Uri.fromFile(exportFile));
            }
        }
    }

    private void export(OutputStream output) {

        if (output != null) {

            try {

                templateExporter = new org.wheatgenetics.coordinate.exporter.TemplateExporter(
                        activity, output, templatesTable().get(this.templateId)
                );
                templateExporter.execute();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void export(@IntRange(from = 1) final long templateId,
                       final String fileName) {
        this.templateId = templateId;
        this.fileName = fileName;
        this.export();
    }

    public void export(final long templateId, final OutputStream output) {
        this.templateId = templateId;
        this.export(output);
    }
    // endregion
}