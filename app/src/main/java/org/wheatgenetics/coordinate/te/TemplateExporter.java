package org.wheatgenetics.coordinate.te;

import android.app.Activity;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

import org.wheatgenetics.coordinate.TemplatesDir;
import org.wheatgenetics.coordinate.Utils;
import org.wheatgenetics.coordinate.database.TemplatesTable;
import org.phenoapps.permissions.Dir;

import java.io.File;
import java.io.IOException;

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
    }

    public void export(@IntRange(from = 1) final long templateId,
                       final String fileName) {
        this.templateId = templateId;
        this.fileName = fileName;
        this.export();
    }
    // endregion
}