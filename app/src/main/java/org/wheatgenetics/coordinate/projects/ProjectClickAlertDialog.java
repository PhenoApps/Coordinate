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
 * org.wheatgenetics.androidlibrary.GetExportFileNameAlertDialog
 * org.wheatgenetics.androidlibrary.GetExportFileNameAlertDialog.Handler
 * org.wheatgenetics.androidlibrary.Utils
 *
 * org.wheatgenetics.coordinate.R
 * org.wheatgenetics.coordinate.Utils
 *
 * org.wheatgenetics.coordinate.database.EntriesTable
 * org.wheatgenetics.coordinate.database.GridsTable
 * org.wheatgenetics.coordinate.database.ProjectsTable
 *
 * org.wheatgenetics.coordinate.model.BaseJoinedGridModels
 * org.wheatgenetics.coordinate.model.BaseJoinedGridModels.Processor
 * org.wheatgenetics.coordinate.model.JoinedGridModel
 * org.wheatgenetics.coordinate.model.ProjectModel
 */
class ProjectClickAlertDialog extends org.wheatgenetics.androidlibrary.AlertDialog
implements org.wheatgenetics.coordinate.model.BaseJoinedGridModels.Processor
{
    @java.lang.SuppressWarnings({"UnnecessaryInterfaceModifier"}) interface Handler
    {
        public abstract void createGrid(@androidx.annotation.IntRange(from = 1) long projectId);
        public abstract void showGrids (@androidx.annotation.IntRange(from = 1) long projectId);
        public abstract void respondToDeletedProject();
        public abstract void exportProject(@androidx.annotation.IntRange(from = 1) long projectId,
            java.lang.String directoryName);
    }

    // region Fields
    @androidx.annotation.NonNull private final
        org.wheatgenetics.coordinate.projects.ProjectClickAlertDialog.Handler handler;

    // region Table Fields
    private org.wheatgenetics.coordinate.database.EntriesTable  entriesTableInstance  = null;  // ll
    private org.wheatgenetics.coordinate.database.GridsTable    gridsTableInstance    = null;  // ll
    private org.wheatgenetics.coordinate.database.ProjectsTable projectsTableInstance = null;  // ll
    // endregion

    @androidx.annotation.IntRange(from = 1) private long    projectId      ;
                                            private boolean projectHasGrids;

    private org.wheatgenetics.androidlibrary.GetExportFileNameAlertDialog
        getExportFileNameAlertDialogInstance = null;                                    // lazy load

    private android.content.DialogInterface.OnClickListener onClickListenerInstance = null;    // ll
    // endregion

    // region Private Methods
    // region Table Private Methods
    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.database.EntriesTable entriesTable()
    {
        if (null == this.entriesTableInstance) this.entriesTableInstance =
            new org.wheatgenetics.coordinate.database.EntriesTable(this.activity());
        return this.entriesTableInstance;
    }

    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.database.GridsTable gridsTable()
    {
        if (null == this.gridsTableInstance) this.gridsTableInstance =
            new org.wheatgenetics.coordinate.database.GridsTable(this.activity());
        return this.gridsTableInstance;
    }

    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.database.ProjectsTable projectsTable()
    {
        if (null == this.projectsTableInstance) this.projectsTableInstance =
            new org.wheatgenetics.coordinate.database.ProjectsTable(this.activity());
        return this.projectsTableInstance;
    }
    // endregion

    // region Toast Private Methods
    // region Long Toast Private Methods
    private void showLongToast(final java.lang.String text)
    { org.wheatgenetics.androidlibrary.Utils.showLongToast(this.activity(), text); }

    private void showLongToast(@androidx.annotation.StringRes final int text)
    { this.showLongToast(this.activity().getString(text)); }
    // endregion

    // region Short Toast Private Methods
    private void showShortToast(final java.lang.String text)
    { org.wheatgenetics.androidlibrary.Utils.showShortToast(this.activity(), text); }

    private void showShortToast(@androidx.annotation.StringRes final int text)
    { this.showShortToast(this.activity().getString(text)); }
    // endregion
    // endregion

    private void createGrid() { this.handler.createGrid(this.projectId); }
    private void showGrids () { this.handler.showGrids (this.projectId); }

    // region deleteProjectStepN() Private Methods
    private void deleteProjectStep3()
    {
        final boolean success = this.projectsTable().delete(this.projectId);
        if (success)
        {
            this.showLongToast(org.wheatgenetics.coordinate
                .R.string.NavigationItemSelectedListenerDeleteProjectSuccessToast);
            this.handler.respondToDeletedProject();
        }
        else this.showLongToast(org.wheatgenetics.coordinate
            .R.string.NavigationItemSelectedListenerDeleteProjectFailToast);
    }

    private void deleteProjectStep2()
    {
        {
            final org.wheatgenetics.coordinate.model.BaseJoinedGridModels baseJoinedGridModels =
                this.gridsTable().loadByProjectId(this.projectId);
            if (null != baseJoinedGridModels)
                baseJoinedGridModels.processAll(this);                   // delete entries
        }

        if (this.gridsTable().deleteByProjectId(this.projectId))                   // delete grids
            this.showShortToast(org.wheatgenetics.coordinate
                .R.string.NavigationItemSelectedListenerDeleteGridsSuccessToast);
        else
            this.showShortToast(org.wheatgenetics.coordinate
                .R.string.NavigationItemSelectedListenerDeleteGridsFailToast);

        this.deleteProjectStep3();
    }

    private void deleteProjectStep1()
    {
        if (this.projectHasGrids)
            org.wheatgenetics.coordinate.Utils.confirm(
                /* context => */ this.activity(),
                /* title   => */ org.wheatgenetics.coordinate
                    .R.string.NavigationItemSelectedListenerDeleteProjectConfirmationTitle,
                /* message => */ org.wheatgenetics.coordinate
                    .R.string.NavigationItemSelectedListenerDeleteProjectConfirmationMessage,
                /* yesRunnable => */ new java.lang.Runnable()
                    {
                        @java.lang.Override public void run()
                        {
                            org.wheatgenetics.coordinate.projects
                                .ProjectClickAlertDialog.this.deleteProjectStep2();
                        }
                    });
        else this.deleteProjectStep3();
    }
    // endregion

    // region exportProject() Private Methods
    private void exportProjectAfterGettingDirectoryName(final java.lang.String directoryName)
    { this.handler.exportProject(this.projectId, directoryName);  }

    private org.wheatgenetics.androidlibrary.GetExportFileNameAlertDialog
    getExportFileNameAlertDialog()
    {
        if (null == this.getExportFileNameAlertDialogInstance)
            this.getExportFileNameAlertDialogInstance =
                new org.wheatgenetics.androidlibrary.GetExportFileNameAlertDialog(this.activity(),
                    new org.wheatgenetics.androidlibrary.GetExportFileNameAlertDialog.Handler()
                    {
                        @java.lang.Override
                        public void handleGetFileNameDone(final java.lang.String fileName)
                        {
                            org.wheatgenetics.coordinate.projects.ProjectClickAlertDialog
                                .this.exportProjectAfterGettingDirectoryName(fileName);
                        }
                    });
        return this.getExportFileNameAlertDialogInstance;
    }

    private void exportProject()
    {
        final org.wheatgenetics.coordinate.model.ProjectModel projectModel =
            this.projectsTable().get(this.projectId);
        if (null != projectModel) this.getExportFileNameAlertDialog().show(projectModel.getTitle());
    }
    // endregion

    private void handleCase1()
    { if (this.projectHasGrids) this.showGrids(); else this.deleteProjectStep1(); }

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
                            .this.deleteProjectStep1(); break;

                        case 3: org.wheatgenetics.coordinate.projects.ProjectClickAlertDialog
                            .this.exportProject(); break;
                    }
                }
            };
        return this.onClickListenerInstance;
    }
    // endregion

    ProjectClickAlertDialog(final android.app.Activity activity, @androidx.annotation.NonNull final
        org.wheatgenetics.coordinate.projects.ProjectClickAlertDialog.Handler handler)
    { super(activity); this.handler = handler; }

    // region Overridden Methods
    @java.lang.Override public void configure() {}

    // region org.wheatgenetics.coordinate.model.BaseJoinedGridModels.Processor Overridden Method
    @java.lang.Override
    public void process(final org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel)
    { if (null != joinedGridModel) this.entriesTable().deleteByGridId(joinedGridModel.getId()); }
    // endregion
    // endregion

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