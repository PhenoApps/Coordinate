package org.wheatgenetics.coordinate.projects;

/**
 * Uses:
 * android.content.pm.PackageManager
 * android.Manifest.permission
 * android.os.Bundle
 * android.view.Menu
 * android.view.MenuItem
 * android.view.View
 * android.widget.AdapterView<>
 * android.widget.AdapterView.OnItemClickListener
 * android.widget.ListView
 *
 * androidx.annotation.IntRange
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 * androidx.appcompat.app.AppCompatActivity
 *
 * org.wheatgenetics.javalib.Dir.PermissionException
 * org.wheatgenetics.javalib.Dir.PermissionRequestedException
 *
 * org.wheatgenetics.androidlibrary.RequestDir
 * org.wheatgenetics.androidlibrary.Utils
 *
 * org.wheatgenetics.coordinate.Consts
 * org.wheatgenetics.coordinate.R
 * org.wheatgenetics.coordinate.Utils
 * org.wheatgenetics.coordinate.Utils.ProjectExport
 *
 * org.wheatgenetics.coordinate.database.GridsTable
 *
 * org.wheatgenetics.coordinate.model.BaseJoinedGridModels
 * org.wheatgenetics.coordinate.model.EntireProjectProjectExporter
 * org.wheatgenetics.coordinate.model.JoinedGridModel
 * org.wheatgenetics.coordinate.model.PerGridProjectExporter
 *
 * org.wheatgenetics.coordinate.pc.ProjectCreator
 * org.wheatgenetics.coordinate.pc.ProjectCreator.Handler
 *
 * org.wheatgenetics.coordinate.projects.ProjectsAdapter
 * org.wheatgenetics.coordinate.projects.ProjectClickAlertDialog
 * org.wheatgenetics.coordinate.projects.ProjectClickAlertDialog.Handler
 */
public class ProjectsActivity extends androidx.appcompat.app.AppCompatActivity
{
    private static final int EXPORT_PROJECT_REQUEST_CODE = 10;

    // region Fields
    private org.wheatgenetics.coordinate.projects.ProjectsAdapter projectsAdapter = null;

    // region exportProject() Fields
    @androidx.annotation.IntRange(from = 1) private long             projectId                ;
                                            private java.lang.String directoryName            ;
    private org.wheatgenetics.coordinate.database.GridsTable         gridsTableInstance = null;// ll

    private org.wheatgenetics.coordinate.model.PerGridProjectExporter perGridProjectExporter = null;
    private org.wheatgenetics.coordinate.model.EntireProjectProjectExporter
        entireProjectProjectExporter = null;
    // endregion

    private org.wheatgenetics.coordinate.projects.ProjectClickAlertDialog
        projectClickAlertDialogInstance = null;                                         // lazy load
    private org.wheatgenetics.coordinate.pc.ProjectCreator projectCreator = null;       // lazy load
    // endregion

    // region Private Methods
    private void notifyDataSetChanged()
    { if (null != this.projectsAdapter) this.projectsAdapter.notifyDataSetChanged(); }

    // region exportProject() Private Methods
    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.database.GridsTable gridsTable()
    {
        if (null == this.gridsTableInstance) this.gridsTableInstance =
            new org.wheatgenetics.coordinate.database.GridsTable(this);
        return this.gridsTableInstance;
    }

    private void showLongToast(final java.lang.String text)
    { org.wheatgenetics.androidlibrary.Utils.showLongToast(this, text); }

    /** Exported data is saved to this folder. */
    private org.wheatgenetics.androidlibrary.RequestDir exportDir()
    throws java.io.IOException, org.wheatgenetics.javalib.Dir.PermissionException
    {
        org.wheatgenetics.coordinate.Utils.createCoordinateDirIfMissing(// throws java.io.IOExcep-
            this, org.wheatgenetics.coordinate.projects         //  tion, org.wheatgenetics-
                .ProjectsActivity.EXPORT_PROJECT_REQUEST_CODE);         //  .javalib.Dir.Permission-
                                                                        //  Exception
        final org.wheatgenetics.androidlibrary.RequestDir exportDir =
            org.wheatgenetics.coordinate.Utils.makeRequestDir(this,
                org.wheatgenetics.coordinate.Consts.COORDINATE_DIR_NAME + "/Export",
                org.wheatgenetics.coordinate.projects.ProjectsActivity.EXPORT_PROJECT_REQUEST_CODE);
        exportDir.createIfMissing();                     // throws java.io.IOException, org.wheatge-
        return exportDir;                                //  netics.javalib.Dir.PermissionException
    }

    private void exportProject()
    {
        final org.wheatgenetics.coordinate.model.BaseJoinedGridModels baseJoinedGridModels =
            this.gridsTable().loadByProjectId(this.projectId);
        if (null != baseJoinedGridModels) if (baseJoinedGridModels.size() > 0)
        {
            final org.wheatgenetics.coordinate.Utils.ProjectExport projectExport =
                org.wheatgenetics.coordinate.Utils.getProjectExport(this);
            if (org.wheatgenetics.coordinate.Utils.ProjectExport.ONE_FILE_PER_GRID
            == projectExport)
            {
                org.wheatgenetics.androidlibrary.RequestDir exportDir;

                final org.wheatgenetics.coordinate.model.JoinedGridModel firstJoinedGridModel =
                    baseJoinedGridModels.get(0);
                if (null == firstJoinedGridModel)
                    exportDir = null;
                else
                    try
                    {
                        final org.wheatgenetics.androidlibrary.RequestDir parentDir =
                            new org.wheatgenetics.androidlibrary.RequestDir(
                                /* activity => */this,
                                /* parent   => */ this.exportDir(),          // throws IOException,
                                                                             //  PermissionException
                                /* name        => */ firstJoinedGridModel.getTemplateTitle(),
                                /* requestCode => */ org.wheatgenetics.coordinate
                                    .projects.ProjectsActivity.EXPORT_PROJECT_REQUEST_CODE);
                        parentDir.createIfMissing();     // throws java.io.IOException, org.wheatge-
                                                         //  netics.javalib.Dir.PermissionException
                        exportDir = new org.wheatgenetics.androidlibrary.RequestDir(
                            /* activity    => */this,
                            /* parent      => */ parentDir         ,
                            /* name        => */ this.directoryName,
                            /* requestCode => */ org.wheatgenetics.coordinate
                                .projects.ProjectsActivity.EXPORT_PROJECT_REQUEST_CODE);
                        exportDir.createIfMissing();     // throws java.io.IOException, org.wheatge-
                    }                                    //  netics.javalib.Dir.PermissionException
                    catch (final java.io.IOException |
                    org.wheatgenetics.javalib.Dir.PermissionException e)
                    {
                        if (!(e instanceof
                        org.wheatgenetics.javalib.Dir.PermissionRequestedException))
                            this.showLongToast(e.getMessage());
                        exportDir = null;
                    }

                if (null != exportDir)
                {
                    this.perGridProjectExporter =
                        new org.wheatgenetics.coordinate.model.PerGridProjectExporter(
                            /* baseJoinedGridModels => */ baseJoinedGridModels,
                            /* context              => */this,
                            /* exportDir            => */ exportDir         ,
                            /* exportDirectoryName  => */ this.directoryName);
                    this.perGridProjectExporter.execute();
                }
            }
            else if (org.wheatgenetics.coordinate.Utils.ProjectExport.ONE_FILE_ENTIRE_PROJECT
            == projectExport)
                try
                {
                    this.entireProjectProjectExporter =
                        new org.wheatgenetics.coordinate.model.EntireProjectProjectExporter(
                            /* baseJoinedGridModels => */ baseJoinedGridModels,
                            /* context              => */this,
                            /* exportDir            => */ this.exportDir(),  // throws IOException,
                                                                             //  PermissionException
                            /* exportFileName => */ this.directoryName);
                    this.entireProjectProjectExporter.execute();
                }
                catch (final java.io.IOException |
                org.wheatgenetics.javalib.Dir.PermissionException e)
                {
                    if (!(e instanceof org.wheatgenetics.javalib.Dir.PermissionRequestedException))
                        this.showLongToast(e.getMessage());
                }
        }
    }

    private void exportProject(@androidx.annotation.IntRange(from = 1) final long projectId,
    final java.lang.String directoryName)
    { this.projectId = projectId; this.directoryName = directoryName; this.exportProject(); }
    // endregion

    private org.wheatgenetics.coordinate.projects.ProjectClickAlertDialog projectClickAlertDialog()
    {
        if (null == this.projectClickAlertDialogInstance) this.projectClickAlertDialogInstance =
            new org.wheatgenetics.coordinate.projects.ProjectClickAlertDialog(this,
                new org.wheatgenetics.coordinate.projects.ProjectClickAlertDialog.Handler()
                {
                    @java.lang.Override public void createGrid(
                    @androidx.annotation.IntRange(from = 1) final long projectId)
                    {
                        // TODO
                    }

                    @java.lang.Override public void showGrids(
                    @androidx.annotation.IntRange(from = 1) final long projectId)
                    {
                        // TODO
                    }

                    @java.lang.Override public void respondToDeletedProject()
                    {
                        org.wheatgenetics.coordinate.projects.ProjectsActivity
                            .this.notifyDataSetChanged();
                    }

                    @java.lang.Override public void exportProject(
                    @androidx.annotation.IntRange(from = 1) final long             projectId    ,
                                                            final java.lang.String directoryName)
                    {
                        org.wheatgenetics.coordinate.projects.ProjectsActivity
                            .this.exportProject(projectId, directoryName);
                    }
                });
        return this.projectClickAlertDialogInstance;
    }

    private void showProjectClickAlertDialog(@androidx.annotation.IntRange(from = 1)
    final long projectId) { this.projectClickAlertDialog().show(projectId); }

    private void handleCreateProjectDone() { this.notifyDataSetChanged(); }
    // endregion

    // region Overridden Methods
    @java.lang.Override protected void onCreate(
    @androidx.annotation.Nullable final android.os.Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.setContentView(org.wheatgenetics.coordinate.R.layout.activity_projects);

        final android.widget.ListView projectsListView = this.findViewById(
            org.wheatgenetics.coordinate.R.id.projectsListView);
        if (null != projectsListView)
        {
            projectsListView.setAdapter(this.projectsAdapter =
                new org.wheatgenetics.coordinate.projects.ProjectsAdapter(this));
            projectsListView.setOnItemClickListener(
                new android.widget.AdapterView.OnItemClickListener()
                {
                    @java.lang.Override public void onItemClick(
                    final android.widget.AdapterView<?> parent, final android.view.View view,
                    final int position, final long id)
                    {
                        org.wheatgenetics.coordinate.projects.ProjectsActivity
                            .this.showProjectClickAlertDialog(id);
                    }
                });
        }
    }

    @java.lang.Override public boolean onCreateOptionsMenu(final android.view.Menu menu)
    {
        this.getMenuInflater().inflate(org.wheatgenetics.coordinate.R.menu.menu_projects, menu);
        return true;
    }

    @java.lang.Override public void onRequestPermissionsResult(final int requestCode,
    @java.lang.SuppressWarnings({"CStyleArrayDeclaration"}) @androidx.annotation.NonNull
        final java.lang.String permissions[],
    @java.lang.SuppressWarnings({"CStyleArrayDeclaration"}) @androidx.annotation.NonNull
        final int grantResults[])
    {
        boolean permissionFound = false;
        for (final java.lang.String permission: permissions)
            if (android.Manifest.permission.WRITE_EXTERNAL_STORAGE.equals(permission))
            { permissionFound = true; break; }

        if (permissionFound) for (final int grantResult: grantResults)
            if (android.content.pm.PackageManager.PERMISSION_GRANTED == grantResult)
                // noinspection SwitchStatementWithTooFewBranches
                switch (requestCode)
                {
                    case org.wheatgenetics.coordinate.projects.ProjectsActivity
                        .EXPORT_PROJECT_REQUEST_CODE: this.exportProject(); break;
                }
    }

    @java.lang.Override protected void onDestroy()
    {
        if (null != this.entireProjectProjectExporter)
        {
            this.entireProjectProjectExporter.cancel();
            this.entireProjectProjectExporter = null;
        }

        if (null != this.perGridProjectExporter)
            { this.perGridProjectExporter.cancel(); this.perGridProjectExporter = null; }

        super.onDestroy();
    }
    // endregion

    public void onNewProjectMenuItemClick(@java.lang.SuppressWarnings({"unused"})
    final android.view.MenuItem menuItem)
    {
        if (null == this.projectCreator)
            this.projectCreator = new org.wheatgenetics.coordinate.pc.ProjectCreator(this,
                new org.wheatgenetics.coordinate.pc.ProjectCreator.Handler()
                {
                    @java.lang.Override public void handleCreateProjectDone(
                    @androidx.annotation.IntRange(from = 1) final long projectId)
                    {
                        org.wheatgenetics.coordinate.projects
                            .ProjectsActivity.this.handleCreateProjectDone();
                    }
                });
        this.projectCreator.createAndReturn();
    }
}