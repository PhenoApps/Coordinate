package org.wheatgenetics.coordinate.ti;

import android.app.Activity;

import androidx.annotation.NonNull;

import org.wheatgenetics.coordinate.R;
import org.wheatgenetics.coordinate.SelectAlertDialog;
import org.wheatgenetics.coordinate.TemplatesDir;
import org.wheatgenetics.coordinate.Utils;
import org.phenoapps.permissions.Dir;

import java.io.IOException;

public class TemplateImportPreprocessor {
    // region Fields
    @NonNull
    private final Activity activity;
    private final int requestCode;
    @NonNull
    private final
    TemplateImportPreprocessor.Handler handler;
    public TemplateImportPreprocessor(
            @NonNull final Activity activity, final int requestCode,
            @NonNull final
            TemplateImportPreprocessor.Handler handler) {
        super();
        this.activity = activity;
        this.requestCode = requestCode;
        this.handler = handler;
    }
    // endregion

    // region Private Methods
    private void showLongToast(final String text) {
        org.wheatgenetics.coordinate.Utils.showLongToast(this.activity, text);
    }

    // region selectExportedTemplate() Private Methods
    private void importTemplate(String fileName) {
        this.handler.importTemplate(fileName);
    }

    private void selectExportedTemplate(@NonNull final TemplatesDir templatesDir) {
        // noinspection CStyleArrayDeclaration
        final String fileNames[];
        try {
            fileNames = templatesDir.listXml() /* throws PermissionException */;
        } catch (final Dir.PermissionException e) {
            this.showLongToast(e.getMessage());
            return;
        }

        if (null != fileNames) if (fileNames.length > 0) {
            final SelectAlertDialog selectAlertDialog =
                    new SelectAlertDialog(this.activity,
                            new SelectAlertDialog.Handler() {
                                @Override
                                public void select(final int which) {
                                    TemplateImportPreprocessor
                                            .this.importTemplate(fileNames[which]);
                                }
                            });
            selectAlertDialog.show(
                    R.string.SelectExportedTemplateTitle, fileNames);
        }
    }
    // endregion
    // endregion

    public void preprocess() {
        try {
            final TemplatesDir templatesDir =
                    Utils.templatesDir(             // throws IOException,
                            this.activity, this.requestCode);                        //  PermissionException
            this.selectExportedTemplate(templatesDir);
        } catch (final IOException | Dir.PermissionException e) {
            if (!(e instanceof Dir.PermissionRequestedException))
                this.showLongToast(e.getMessage());
        }
    }

    @SuppressWarnings({"UnnecessaryInterfaceModifier"})
    public interface Handler {
        public abstract void importTemplate(String fileName);
    }
}