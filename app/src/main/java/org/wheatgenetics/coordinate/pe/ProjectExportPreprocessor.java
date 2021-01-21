package org.wheatgenetics.coordinate.pe;

import android.app.Activity;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.phenoapps.androidlibrary.GetExportFileNameAlertDialog;
import org.wheatgenetics.coordinate.database.ProjectsTable;
import org.wheatgenetics.coordinate.model.ProjectModel;

public class ProjectExportPreprocessor {
    // region Fields
    @NonNull
    private final Activity activity;
    @NonNull
    private final
    ProjectExportPreprocessor.Handler handler;
    @IntRange(from = 1)
    private long projectId;
    private ProjectsTable projectsTableInstance = null;  // ll
    private GetExportFileNameAlertDialog
            getExportFileNameAlertDialogInstance = null;                                    // lazy load
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
        if (null == this.getExportFileNameAlertDialogInstance)
            this.getExportFileNameAlertDialogInstance =
                    new GetExportFileNameAlertDialog(this.activity,
                            new GetExportFileNameAlertDialog.Handler() {
                                @Override
                                public void handleGetFileNameDone(final String fileName) {
                                    ProjectExportPreprocessor
                                            .this.exportProject(fileName);
                                }
                            });
        return this.getExportFileNameAlertDialogInstance;
    }

    private void preprocessAfterSettingProjectId(@Nullable final ProjectModel projectModel) {
        if (null != projectModel) this.getExportFileNameAlertDialog().show(projectModel.getTitle());
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