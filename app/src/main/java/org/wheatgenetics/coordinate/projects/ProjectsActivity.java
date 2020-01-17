package org.wheatgenetics.coordinate.projects;

/**
 * Uses:
 * android.app.Activity
 * android.content.pm.PackageManager
 * android.content.Context
 * android.content.Intent
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
 * org.wheatgenetics.coordinate.R
 * org.wheatgenetics.coordinate.Types
 *
 * org.wheatgenetics.coordinate.gc.StatelessGridCreator
 *
 * org.wheatgenetics.coordinate.grids.GridsActivity
 *
 * org.wheatgenetics.coordinate.pc.ProjectCreator
 * org.wheatgenetics.coordinate.pc.ProjectCreator.Handler
 *
 * org.wheatgenetics.coordinate.pe.ProjectExporter
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

    private org.wheatgenetics.coordinate.gc.StatelessGridCreator
        statelessGridCreatorInstance = null;                                            // lazy load

    private org.wheatgenetics.coordinate.pe.ProjectExporter projectExporterInstance = null;    // ll

    private org.wheatgenetics.coordinate.projects.ProjectClickAlertDialog
        projectClickAlertDialogInstance = null;                                         // lazy load
    private org.wheatgenetics.coordinate.pc.ProjectCreator projectCreatorInstance = null;      // ll

    private static android.content.Intent INTENT_INSTANCE = null;                       // lazy load
    // endregion

    // region Private Methods
    // region createGrid() Private Methods
    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.gc.StatelessGridCreator statelessGridCreator()
    {
        if (null == this.statelessGridCreatorInstance) this.statelessGridCreatorInstance =
            new org.wheatgenetics.coordinate.gc.StatelessGridCreator(
                this, org.wheatgenetics.coordinate.Types.CREATE_GRID);
        return this.statelessGridCreatorInstance;
    }

    private void createGrid(@androidx.annotation.IntRange(from = 1) final long projectId)
    { this.statelessGridCreator().createInProject(projectId); }
    // endregion

    private void startGridsActivity(@androidx.annotation.IntRange(from = 1) final long projectId)
    {
        this.startActivity(org.wheatgenetics.coordinate.grids.GridsActivity.projectIdIntent(
            this, projectId));
    }

    private void notifyDataSetChanged()
    { if (null != this.projectsAdapter) this.projectsAdapter.notifyDataSetChanged(); }

    // region exportProject() Private Methods
    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.pe.ProjectExporter projectExporter()
    {
        if (null == this.projectExporterInstance) this.projectExporterInstance =
            new org.wheatgenetics.coordinate.pe.ProjectExporter(this,
                org.wheatgenetics.coordinate.projects.ProjectsActivity.EXPORT_PROJECT_REQUEST_CODE);
        return this.projectExporterInstance;
    }

    private void exportProject(@androidx.annotation.IntRange(from = 1) final long projectId,
    final java.lang.String directoryName)
    { this.projectExporter().export(projectId, directoryName); }
    // endregion

    // region projectClickAlertDialog() Private Methods
    private org.wheatgenetics.coordinate.projects.ProjectClickAlertDialog projectClickAlertDialog()
    {
        if (null == this.projectClickAlertDialogInstance) this.projectClickAlertDialogInstance =
            new org.wheatgenetics.coordinate.projects.ProjectClickAlertDialog(this,
                new org.wheatgenetics.coordinate.projects.ProjectClickAlertDialog.Handler()
                {
                    @java.lang.Override public void createGrid(
                    @androidx.annotation.IntRange(from = 1) final long projectId)
                    {
                        org.wheatgenetics.coordinate.projects.ProjectsActivity.this.createGrid(
                            projectId);
                    }

                    @java.lang.Override public void showGrids(
                    @androidx.annotation.IntRange(from = 1) final long projectId)
                    {
                        org.wheatgenetics.coordinate.projects.ProjectsActivity
                            .this.startGridsActivity(projectId);
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
    // endregion

    private org.wheatgenetics.coordinate.pc.ProjectCreator projectCreator()
    {
        if (null == this.projectCreatorInstance)
            this.projectCreatorInstance = new org.wheatgenetics.coordinate.pc.ProjectCreator(
                this, new org.wheatgenetics.coordinate.pc.ProjectCreator.Handler()
                {
                    @java.lang.Override public void handleCreateProjectDone(
                    @androidx.annotation.IntRange(from = 1) final long projectId)
                    {
                        org.wheatgenetics.coordinate.projects
                            .ProjectsActivity.this.notifyDataSetChanged();
                    }
                });
        return this.projectCreatorInstance;
    }
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

    @java.lang.Override protected void onActivityResult(final int requestCode,
    final int resultCode, final android.content.Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (android.app.Activity.RESULT_OK == resultCode && null != data)
            // noinspection SwitchStatementWithTooFewBranches
            switch (requestCode)
            {
                case org.wheatgenetics.coordinate.Types.CREATE_GRID:
                    if (null != this.statelessGridCreatorInstance)
                        this.statelessGridCreatorInstance.setExcludedCells(data.getExtras());
                    break;
            }
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
                    .EXPORT_PROJECT_REQUEST_CODE:
                        if (null != this.projectExporterInstance)
                            this.projectExporterInstance.export();
                        break;
                }
    }
    // endregion

    // region MenuItem Event Handler
    public void onNewProjectMenuItemClick(@java.lang.SuppressWarnings({"unused"})
    final android.view.MenuItem menuItem) { this.projectCreator().createAndReturn(); }
    // endregion

    @androidx.annotation.NonNull public static android.content.Intent intent(
    @androidx.annotation.NonNull final android.content.Context context)
    {
        return null == org.wheatgenetics.coordinate.projects.ProjectsActivity.INTENT_INSTANCE ?
            org.wheatgenetics.coordinate.projects.ProjectsActivity.INTENT_INSTANCE =
                new android.content.Intent(context,
                    org.wheatgenetics.coordinate.projects.ProjectsActivity.class) :
            org.wheatgenetics.coordinate.projects.ProjectsActivity.INTENT_INSTANCE;
    }
}