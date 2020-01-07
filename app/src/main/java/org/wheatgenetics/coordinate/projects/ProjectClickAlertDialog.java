package org.wheatgenetics.coordinate.projects;

/**
 * Uses:
 * android.app.Activity
 * android.content.DialogInterface
 * android.content.DialogInterface.OnClickListener
 *
 * androidx.annotation.IntRange
 * androidx.annotation.NonNull
 * androidx.annotation.StringRes
 *
 * org.wheatgenetics.androidlibrary.AlertDialog
 *
 * org.wheatgenetics.coordinate.ProjectDeleter
 * org.wheatgenetics.coordinate.ProjectDeleter.Handler
 * org.wheatgenetics.coordinate.R
 *
 * org.wheatgenetics.coordinate.database.GridsTable
 *
 * org.wheatgenetics.coordinate.model.BaseJoinedGridModels.Processor
 *
 * org.wheatgenetics.coordinate.pe.ProjectExportPreprocessor
 * org.wheatgenetics.coordinate.pe.ProjectExportPreprocessor.Handler
 */
class ProjectClickAlertDialog extends org.wheatgenetics.androidlibrary.AlertDialog
{
    @java.lang.SuppressWarnings({"UnnecessaryInterfaceModifier"}) interface Handler
    {
        public abstract void createGrid(@androidx.annotation.IntRange(from = 1) long projectId);
        public abstract void showGrids (@androidx.annotation.IntRange(from = 1) long projectId);
        public abstract void respondToDeletedProject();

        public abstract void exportProject(
        @androidx.annotation.IntRange(from = 1) long projectId, java.lang.String directoryName);
    }

    // region Fields
    @androidx.annotation.NonNull private final
        org.wheatgenetics.coordinate.projects.ProjectClickAlertDialog.Handler handler;

    @androidx.annotation.IntRange(from = 1) private long    projectId      ;
                                            private boolean projectHasGrids;

    private org.wheatgenetics.coordinate.database.GridsTable gridsTableInstance = null; // lazy load

    private android.content.DialogInterface.OnClickListener onClickListenerInstance = null;    // ll

    private org.wheatgenetics.coordinate.deleter.ProjectDeleter projectDeleterInstance = null; // ll
    private org.wheatgenetics.coordinate.pe.ProjectExportPreprocessor
        projectExportPreprocessorInstance = null;                                       // lazy load
    // endregion

    // region Private Methods
    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.database.GridsTable gridsTable()
    {
        if (null == this.gridsTableInstance) this.gridsTableInstance =
            new org.wheatgenetics.coordinate.database.GridsTable(this.activity());
        return this.gridsTableInstance;
    }

    private void createGrid() { this.handler.createGrid(this.projectId); }
    private void showGrids () { this.handler.showGrids (this.projectId); }

    // region deleteProject() Private Methods
    private void respondToDeletedProject() { this.handler.respondToDeletedProject(); }

    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.deleter.ProjectDeleter projectDeleter()
    {
        if (null == this.projectDeleterInstance) this.projectDeleterInstance =
            new org.wheatgenetics.coordinate.deleter.ProjectDeleter(this.activity(),
                new org.wheatgenetics.coordinate.deleter.ProjectDeleter.Handler()
                {
                    @java.lang.Override public void respondToDeletedProject(
                    @androidx.annotation.IntRange(from = 1) final long projectId)
                    {
                        org.wheatgenetics.coordinate.projects
                            .ProjectClickAlertDialog.this.respondToDeletedProject();
                    }
                });
        return this.projectDeleterInstance;
    }

    private void deleteProject() { this.projectDeleter().delete(this.projectId); }
    // endregion

    // region exportProject() Private Methods
    private void exportProject(
    @androidx.annotation.IntRange(from = 1) long projectId, final java.lang.String directoryName)
    { this.handler.exportProject(projectId, directoryName);  }

    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.pe.ProjectExportPreprocessor projectExportPreprocessor()
    {
        if (null == this.projectExportPreprocessorInstance) this.projectExportPreprocessorInstance =
            new org.wheatgenetics.coordinate.pe.ProjectExportPreprocessor(this.activity(),
                new org.wheatgenetics.coordinate.pe.ProjectExportPreprocessor.Handler()
                {
                    @java.lang.Override public void exportProject(
                    @androidx.annotation.IntRange(from = 1) final long             projectId    ,
                                                            final java.lang.String directoryName)
                    {
                        org.wheatgenetics.coordinate.projects.ProjectClickAlertDialog
                            .this.exportProject(projectId, directoryName);
                    }
                });
        return this.projectExportPreprocessorInstance;
    }

    private void exportProject() { this.projectExportPreprocessor().preprocess(this.projectId); }
    // endregion

    private void handleCase1()
    { if (this.projectHasGrids) this.showGrids(); else this.deleteProject(); }

    @androidx.annotation.NonNull
    private android.content.DialogInterface.OnClickListener onClickListener()
    {
        if (null == this.onClickListenerInstance) this.onClickListenerInstance =
            new android.content.DialogInterface.OnClickListener()
            {
                @java.lang.Override
                public void onClick(final android.content.DialogInterface dialog, final int which)
                {
                    switch (which)
                    {
                        case 0: org.wheatgenetics.coordinate.projects.ProjectClickAlertDialog
                            .this.createGrid(); break;

                        case 1: org.wheatgenetics.coordinate.projects.ProjectClickAlertDialog
                            .this.handleCase1(); break;

                        case 2: org.wheatgenetics.coordinate.projects.ProjectClickAlertDialog
                            .this.deleteProject(); break;

                        case 3: org.wheatgenetics.coordinate.projects.ProjectClickAlertDialog
                            .this.exportProject(); break;
                    }
                }
            };
        return this.onClickListenerInstance;
    }
    // endregion

    ProjectClickAlertDialog(final android.app.Activity activity, @androidx.annotation.NonNull
    final org.wheatgenetics.coordinate.projects.ProjectClickAlertDialog.Handler handler)
    { super(activity); this.handler = handler; }

    @java.lang.Override public void configure() {}

    void show(@androidx.annotation.IntRange(from = 1) final long projectId)
    {
        this.projectId = projectId;
        {
            @java.lang.SuppressWarnings({"CStyleArrayDeclaration"}) @androidx.annotation.StringRes
            final int items[];
            {
                @java.lang.SuppressWarnings({"Convert2Diamond"})
                final java.util.ArrayList<java.lang.Integer> arrayList =
                    new java.util.ArrayList<java.lang.Integer>();

                arrayList.add(
                    org.wheatgenetics.coordinate.R.string.ProjectClickAlertDialogCreateGrid);

                this.projectHasGrids = this.gridsTable().existsInProject(this.projectId);
                if (this.projectHasGrids) arrayList.add(
                    org.wheatgenetics.coordinate.R.string.ProjectClickAlertDialogShowGrids);

                arrayList.add(
                    org.wheatgenetics.coordinate.R.string.ProjectClickAlertDialogDeleteProject);

                if (this.projectHasGrids) arrayList.add(
                    org.wheatgenetics.coordinate.R.string.ProjectClickAlertDialogExportProject);

                items = new int[arrayList.size()];
                @androidx.annotation.IntRange(from = 0) int i = 0;
                for (final java.lang.Integer integer: arrayList) items[i++] = integer;
            }
            this.setItems(items, this.onClickListener());
        }
        this.createShow();
    }
}