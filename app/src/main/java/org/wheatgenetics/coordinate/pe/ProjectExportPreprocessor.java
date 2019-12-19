package org.wheatgenetics.coordinate.pe;

/**
 * Uses:
 * android.app.Activity
 *
 * androidx.annotation.IntRange
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 *
 * org.wheatgenetics.androidlibrary.GetExportFileNameAlertDialog
 * org.wheatgenetics.androidlibrary.GetExportFileNameAlertDialog.Handler
 *
 * org.wheatgenetics.coordinate.database.ProjectsTable
 *
 * org.wheatgenetics.coordinate.model.ProjectModel
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
public class ProjectExportPreprocessor extends java.lang.Object
{
    @java.lang.SuppressWarnings({"UnnecessaryInterfaceModifier"}) public interface Handler
    {
        public abstract void exportProject(
        @androidx.annotation.IntRange(from = 1) long projectId, java.lang.String directoryName);
    }

    // region Fields
    @androidx.annotation.NonNull private final android.app.Activity activity;
    @androidx.annotation.NonNull private final
        org.wheatgenetics.coordinate.pe.ProjectExportPreprocessor.Handler handler;

    @androidx.annotation.IntRange(from = 1) private long projectId;

    private org.wheatgenetics.coordinate.database.ProjectsTable projectsTableInstance = null;  // ll
    private org.wheatgenetics.androidlibrary.GetExportFileNameAlertDialog
        getExportFileNameAlertDialogInstance = null;                                    // lazy load
    // endregion

    // region Private Methods
    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.database.ProjectsTable projectsTable()
    {
        if (null == this.projectsTableInstance) this.projectsTableInstance =
            new org.wheatgenetics.coordinate.database.ProjectsTable(this.activity);
        return this.projectsTableInstance;
    }

    private void exportProject(final java.lang.String directoryName)
    { this.handler.exportProject(this.projectId, directoryName);  }

    @androidx.annotation.NonNull private
    org.wheatgenetics.androidlibrary.GetExportFileNameAlertDialog getExportFileNameAlertDialog()
    {
        if (null == this.getExportFileNameAlertDialogInstance)
            this.getExportFileNameAlertDialogInstance =
                new org.wheatgenetics.androidlibrary.GetExportFileNameAlertDialog(this.activity,
                    new org.wheatgenetics.androidlibrary.GetExportFileNameAlertDialog.Handler()
                    {
                        @java.lang.Override
                        public void handleGetFileNameDone(final java.lang.String fileName)
                        {
                            org.wheatgenetics.coordinate.pe.ProjectExportPreprocessor
                                .this.exportProject(fileName);
                        }
                    });
        return this.getExportFileNameAlertDialogInstance;
    }

    private void preprocess(final java.lang.String initialFileName)
    { this.getExportFileNameAlertDialog().show(initialFileName); }
    // endregion

    public ProjectExportPreprocessor(
    @androidx.annotation.NonNull final android.app.Activity activity,
    @androidx.annotation.NonNull final
        org.wheatgenetics.coordinate.pe.ProjectExportPreprocessor.Handler handler)
    { super(); this.activity = activity; this.handler = handler; }

    // region Public Methods
    public void preprocess(@androidx.annotation.IntRange(from = 1) final long projectId)
    {
        this.projectId = projectId;
        final org.wheatgenetics.coordinate.model.ProjectModel projectModel =
            this.projectsTable().get(this.projectId);
        if (null != projectModel) this.preprocess(projectModel.getTitle());
    }

    public void preprocess(@androidx.annotation.Nullable
    final org.wheatgenetics.coordinate.model.ProjectModel projectModel)
    {
        if (null != projectModel)
            { this.projectId = projectModel.getId(); this.preprocess(projectModel.getTitle()); }
    }
    // endregion
}