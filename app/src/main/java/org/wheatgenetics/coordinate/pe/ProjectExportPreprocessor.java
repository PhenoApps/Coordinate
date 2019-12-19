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
    private org.wheatgenetics.androidlibrary.GetExportFileNameAlertDialog
        getExportFileNameAlertDialogInstance = null;                                    // lazy load
    // endregion

    // region Private Methods
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
    // endregion

    public ProjectExportPreprocessor(
    @androidx.annotation.NonNull final android.app.Activity activity,
    @androidx.annotation.NonNull final
        org.wheatgenetics.coordinate.pe.ProjectExportPreprocessor.Handler handler)
    { super(); this.activity = activity; this.handler = handler; }

    public void preprocess(@androidx.annotation.Nullable
    final org.wheatgenetics.coordinate.model.ProjectModel projectModel)
    {
        if (null != projectModel)
        {
            this.projectId = projectModel.getId();
            this.getExportFileNameAlertDialog().show(projectModel.getTitle());
        }
    }
}