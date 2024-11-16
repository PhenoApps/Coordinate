package org.wheatgenetics.coordinate.te;

import android.app.Activity;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.phenoapps.androidlibrary.GetExportFileNameAlertDialog;
import org.wheatgenetics.coordinate.R;
import org.wheatgenetics.coordinate.database.TemplatesTable;
import org.wheatgenetics.coordinate.model.TemplateModel;
import org.wheatgenetics.coordinate.utils.DocumentTreeUtil;
import org.wheatgenetics.coordinate.utils.TimestampUtil;

public class TemplateExportPreprocessor {
    // region Fields
    @NonNull
    private final Activity activity;
    @NonNull
    private final
    TemplateExportPreprocessor.Handler handler;
    @IntRange(from = 1)
    private long templateId;
    private TemplatesTable templatesTableInstance = null; //ll                                 // lazy load
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
        String rootDirectory = DocumentTreeUtil.Companion.getPath(activity);

        String message = String.format(activity.getString(R.string.export_dialog_directory_message), rootDirectory, activity.getString(R.string.template_dir));

        return (GetExportFileNameAlertDialog) new GetExportFileNameAlertDialog(this.activity,
                TemplateExportPreprocessor
                        .this::exportTemplate).setTitle(R.string.template_export_dialog_title).setMessage(message).setPositiveButton(R.string.export_dialog_positive_button_text);
    }

    private void preprocessAfterSettingTemplateId(@Nullable final TemplateModel templateModel) {
        if (null != templateModel) {
            String initialFileName = templateModel.getTitle() + "_" + new TimestampUtil().getTime();
            this.getExportFileNameAlertDialog().show(initialFileName);
        }
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