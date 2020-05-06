package org.wheatgenetics.coordinate.te;

import android.app.Activity;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.wheatgenetics.androidlibrary.GetExportFileNameAlertDialog;
import org.wheatgenetics.coordinate.database.TemplatesTable;
import org.wheatgenetics.coordinate.model.TemplateModel;

public class TemplateExportPreprocessor {
    // region Fields
    @NonNull
    private final Activity activity;
    @NonNull
    private final
    TemplateExportPreprocessor.Handler handler;
    @IntRange(from = 1)
    private long templateId;
    private TemplatesTable templatesTableInstance = null; //ll
    private GetExportFileNameAlertDialog
            getExportFileNameAlertDialogInstance = null;                                    // lazy load
    public TemplateExportPreprocessor(
            @NonNull final Activity activity,
            @NonNull final
            TemplateExportPreprocessor.Handler handler) {
        super();
        this.activity = activity;
        this.handler = handler;
    }
    // endregion

    // region Private Methods
    @NonNull
    private TemplatesTable templatesTable() {
        if (null == this.templatesTableInstance) this.templatesTableInstance =
                new TemplatesTable(this.activity);
        return this.templatesTableInstance;
    }

    private void exportTemplate(final String fileName) {
        this.handler.exportTemplate(this.templateId, fileName);
    }

    @NonNull
    private GetExportFileNameAlertDialog getExportFileNameAlertDialog() {
        if (null == this.getExportFileNameAlertDialogInstance)
            this.getExportFileNameAlertDialogInstance =
                    new GetExportFileNameAlertDialog(this.activity,
                            new GetExportFileNameAlertDialog.Handler() {
                                @Override
                                public void handleGetFileNameDone(final String fileName) {
                                    TemplateExportPreprocessor
                                            .this.exportTemplate(fileName);
                                }
                            });
        return this.getExportFileNameAlertDialogInstance;
    }

    private void preprocessAfterSettingTemplateId(@Nullable final TemplateModel templateModel) {
        if (null != templateModel)
            this.getExportFileNameAlertDialog().show(templateModel.getTitle());
    }
    // endregion

    // region Public Methods
    public void preprocess(@IntRange(from = 1) final long templateId) {
        this.templateId = templateId;
        this.preprocessAfterSettingTemplateId(this.templatesTable().get(this.templateId));
    }

    public void preprocess(@Nullable final TemplateModel templateModel) {
        if (null != templateModel) {
            this.templateId = templateModel.getId();
            this.preprocessAfterSettingTemplateId(templateModel);
        }
    }

    @SuppressWarnings({"UnnecessaryInterfaceModifier"})
    public interface Handler {
        public abstract void exportTemplate(
                @IntRange(from = 1) long templateId, String fileName);
    }
    // endregion
}