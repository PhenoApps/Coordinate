package org.wheatgenetics.coordinate.pe;

import android.app.Activity;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.phenoapps.androidlibrary.GetExportFileNameAlertDialog;
import org.wheatgenetics.coordinate.R;
import org.wheatgenetics.coordinate.database.ProjectsTable;
import org.wheatgenetics.coordinate.model.ProjectModel;
import org.wheatgenetics.coordinate.utils.DocumentTreeUtil;
import org.wheatgenetics.coordinate.utils.TimestampUtil;

public class ProjectExportPreprocessor {
    // region Fields
    @NonNull
    private final Activity activity;
    @NonNull
    private final
    ProjectExportPreprocessor.Handler handler;
    @IntRange(from = 1)
    private long projectId;
    private ProjectsTable projectsTableInstance = null;  // ll                                  // lazy load

    public ProjectExportPreprocessor(
            @NonNull final Activity activity,
            @NonNull final
            ProjectExportPreprocessor.Handler handler) {
        super();
        this.activity = activity;
        this.handler = handler;
    }
    // endregion

    // region Private Methods
    @NonNull
    private ProjectsTable projectsTable() {
        if (null == this.projectsTableInstance) this.projectsTableInstance =
                new ProjectsTable(this.activity);
        return this.projectsTableInstance;
    }

    private void exportProject(final String directoryName) {
        this.handler.exportProject(this.projectId, directoryName);
    }

    @NonNull
    private GetExportFileNameAlertDialog getExportFileNameAlertDialog() {
        String rootDirectory = DocumentTreeUtil.Companion.getPath(activity);

        String message = String.format(activity.getString(R.string.export_dialog_directory_message), rootDirectory, activity.getString(R.string.export_dir));

        return (GetExportFileNameAlertDialog) new GetExportFileNameAlertDialog(this.activity,
                ProjectExportPreprocessor
                        .this::exportProject).setTitle(R.string.project_export_dialog_title).setMessage(message).setPositiveButton(R.string.export_dialog_positive_button_text);
    }

    private void preprocessAfterSettingProjectId(@Nullable final ProjectModel projectModel) {
        if (null != projectModel) {
            String initialFileName = projectModel.getTitle() + "_" + new TimestampUtil().getTime();
            this.getExportFileNameAlertDialog().show(initialFileName);
        }
    }
    // endregion

    // region Public Methods
    public void preprocess(@IntRange(from = 1) final long projectId) {
        this.projectId = projectId;
        this.preprocessAfterSettingProjectId(this.projectsTable().get(this.projectId));
    }

    public void preprocess(@Nullable final ProjectModel projectModel) {
        if (null != projectModel) {
            this.projectId = projectModel.getId();
            this.preprocessAfterSettingProjectId(projectModel);
        }
    }

    @SuppressWarnings({"UnnecessaryInterfaceModifier"})
    public interface Handler {
        public abstract void exportProject(
                @IntRange(from = 1) long projectId, String directoryName);
    }
    // endregion
}