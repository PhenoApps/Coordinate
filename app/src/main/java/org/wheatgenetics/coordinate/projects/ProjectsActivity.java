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
 * android.view.View.OnClickListener
 * android.widget.ListView
 *
 * androidx.annotation.IntRange
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 *
 * org.wheatgenetics.coordinate.BackActivity
 * org.wheatgenetics.coordinate.CollectorActivity
 * org.wheatgenetics.coordinate.R
 * org.wheatgenetics.coordinate.Types
 *
 * org.wheatgenetics.coordinate.deleter.ProjectDeleter
 * org.wheatgenetics.coordinate.deleter.ProjectDeleter.Handler
 *
 * org.wheatgenetics.coordinate.gc.StatelessGridCreator
 * org.wheatgenetics.coordinate.gc.StatelessGridCreator.Handler
 *
 * org.wheatgenetics.coordinate.grids.GridsActivity
 *
 * org.wheatgenetics.coordinate.pc.ProjectCreator
 * org.wheatgenetics.coordinate.pc.ProjectCreator.Handler
 *
 * org.wheatgenetics.coordinate.pe.ProjectExportPreprocessor
 * org.wheatgenetics.coordinate.pe.ProjectExportPreprocessor.Handler
 * org.wheatgenetics.coordinate.pe.ProjectExporter
 *
 * org.wheatgenetics.coordinate.projects.ProjectsAdapter
 */
public class ProjectsActivity extends org.wheatgenetics.coordinate.BackActivity
{
    // region Constants
    private static final int              EXPORT_PROJECT_REQUEST_CODE = 10;
    private static final java.lang.String
        PROJECT_ID_KEY = "projectId", DIRECTORY_NAME_KEY = "directoryName";
    // endregion

    // region Fields
    private org.wheatgenetics.coordinate.projects.ProjectsAdapter projectsAdapter = null;

    private org.wheatgenetics.coordinate.gc.StatelessGridCreator
        statelessGridCreatorInstance = null;                                            // lazy load

    private org.wheatgenetics.coordinate.deleter.ProjectDeleter projectDeleterInstance = null; // ll

    // region exportProject() Fields
    @androidx.annotation.IntRange(from = 1) private long              projectId    ;
                                            private java.lang.String  directoryName;
    private org.wheatgenetics.coordinate.pe.ProjectExportPreprocessor
        projectExportPreprocessorInstance = null;                                       // lazy load
    private org.wheatgenetics.coordinate.pe.ProjectExporter projectExporterInstance = null;    // ll
    // endregion

    private org.wheatgenetics.coordinate.pc.ProjectCreator projectCreatorInstance = null;      // ll

    private static android.content.Intent INTENT_INSTANCE = null;                       // lazy load
    // endregion

    // region Private Methods
    // region createGrid() Private Methods
    private void startCollectorActivity(@androidx.annotation.IntRange(from = 1) final long gridId)
    {
        this.startActivity(
            org.wheatgenetics.coordinate.CollectorActivity.intent(this, gridId));
    }

    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.gc.StatelessGridCreator statelessGridCreator()
    {
        if (null == this.statelessGridCreatorInstance) this.statelessGridCreatorInstance =
            new org.wheatgenetics.coordinate.gc.StatelessGridCreator(
                this, org.wheatgenetics.coordinate.Types.CREATE_GRID,
                new org.wheatgenetics.coordinate.gc.StatelessGridCreator.Handler()
                {
                    @java.lang.Override public void handleGridCreated(
                    @androidx.annotation.IntRange(from = 1) final long gridId)
                    {
                        org.wheatgenetics.coordinate.projects.ProjectsActivity
                            .this.startCollectorActivity(gridId);
                    }
                });
        return this.statelessGridCreatorInstance;
    }

    private void createGrid(@androidx.annotation.IntRange(from = 1) final long projectId)
    { this.statelessGridCreator().createInProject(projectId); }
    // endregion

    private void notifyDataSetChanged()
    { if (null != this.projectsAdapter) this.projectsAdapter.notifyDataSetChanged(); }

    // region deleteProject() Private Methods
    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.deleter.ProjectDeleter projectDeleter()
    {
        if (null == this.projectDeleterInstance) this.projectDeleterInstance =
            new org.wheatgenetics.coordinate.deleter.ProjectDeleter(this,
                new org.wheatgenetics.coordinate.deleter.ProjectDeleter.Handler()
                {
                    @java.lang.Override public void respondToDeletedProject(
                    @androidx.annotation.IntRange(from = 1) final long projectId)
                    {
                        org.wheatgenetics.coordinate.projects
                            .ProjectsActivity.this.notifyDataSetChanged();
                    }
                });
        return this.projectDeleterInstance;
    }

    private void deleteProject(@androidx.annotation.IntRange(from = 1) final long projectId)
    { this.projectDeleter().delete(projectId); }
    // endregion

    // region exportProject() Private Methods
    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.pe.ProjectExporter projectExporter()
    {
        if (null == this.projectExporterInstance) this.projectExporterInstance =
            new org.wheatgenetics.coordinate.pe.ProjectExporter(this,
                org.wheatgenetics.coordinate.projects.ProjectsActivity.EXPORT_PROJECT_REQUEST_CODE);
        return this.projectExporterInstance;
    }

    private void exportProject()
    { this.projectExporter().export(this.projectId, this.directoryName); }

    private void exportProject(@androidx.annotation.IntRange(from = 1) final long projectId,
    final java.lang.String directoryName)
    { this.projectId = projectId; this.directoryName = directoryName; this.exportProject(); }

    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.pe.ProjectExportPreprocessor projectExportPreprocessor()
    {
        if (null == this.projectExportPreprocessorInstance) this.projectExportPreprocessorInstance =
            new org.wheatgenetics.coordinate.pe.ProjectExportPreprocessor(this,
                new org.wheatgenetics.coordinate.pe.ProjectExportPreprocessor.Handler()
                {
                    @java.lang.Override public void exportProject(
                    @androidx.annotation.IntRange(from = 1) final long             projectId    ,
                                                            final java.lang.String directoryName)
                    {
                        org.wheatgenetics.coordinate.projects.ProjectsActivity
                            .this.exportProject(projectId, directoryName);
                    }
                });
        return this.projectExportPreprocessorInstance;
    }

    private void exportProject(@androidx.annotation.IntRange(from = 1) final long projectId)
    { this.projectExportPreprocessor().preprocess(projectId); }
    // endregion

    private void startGridsActivity(@androidx.annotation.IntRange(from = 1) final long projectId)
    {
        this.startActivity(org.wheatgenetics.coordinate.grids.GridsActivity.projectIdIntent(
            this, projectId));
    }

    private org.wheatgenetics.coordinate.pc.ProjectCreator projectCreator()
    {
        if (null == this.projectCreatorInstance) this.projectCreatorInstance =
            new org.wheatgenetics.coordinate.pc.ProjectCreator(this,
                new org.wheatgenetics.coordinate.pc.ProjectCreator.Handler()
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

        if (null != savedInstanceState)
        {
            this.projectId = savedInstanceState.getLong(
                org.wheatgenetics.coordinate.projects.ProjectsActivity.PROJECT_ID_KEY);
            this.directoryName = savedInstanceState.getString(
                org.wheatgenetics.coordinate.projects.ProjectsActivity.DIRECTORY_NAME_KEY);
        }

        final android.widget.ListView projectsListView = this.findViewById(
            org.wheatgenetics.coordinate.R.id.projectsListView);
        if (null != projectsListView) projectsListView.setAdapter(this.projectsAdapter =
            new org.wheatgenetics.coordinate.projects.ProjectsAdapter(this,
                /* onCreateGridButtonClickListener => */ new android.view.View.OnClickListener()
                {
                    @java.lang.Override public void onClick(final android.view.View view)
                    {
                        if (null != view) org.wheatgenetics.coordinate.projects.ProjectsActivity
                            .this.createGrid((java.lang.Long) view.getTag());
                    }
                }, /* onDeleteButtonClickListener => */ new android.view.View.OnClickListener()
                {
                    @java.lang.Override public void onClick(final android.view.View view)
                    {
                        if (null != view) org.wheatgenetics.coordinate.projects.ProjectsActivity
                            .this.deleteProject((java.lang.Long) view.getTag());
                    }
                }, /* onExportButtonClickListener => */ new android.view.View.OnClickListener()
                {
                    @java.lang.Override public void onClick(final android.view.View view)
                    {
                        if (null != view) org.wheatgenetics.coordinate.projects.ProjectsActivity
                            .this.exportProject((java.lang.Long) view.getTag());
                    }
                }, /* onShowGridsButtonClickListener => */ new android.view.View.OnClickListener()
                {
                    @java.lang.Override public void onClick(final android.view.View view)
                    {
                        if (null != view) org.wheatgenetics.coordinate.projects.ProjectsActivity
                            .this.startGridsActivity((java.lang.Long) view.getTag());
                    }
                }));
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
                    this.statelessGridCreator().continueExcluding(data.getExtras()); break;
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
                    .EXPORT_PROJECT_REQUEST_CODE: this.exportProject(); break;
                }
    }

    @java.lang.Override protected void onSaveInstanceState(
    @androidx.annotation.NonNull final android.os.Bundle outState)
    {
        outState.putLong(
            org.wheatgenetics.coordinate.projects.ProjectsActivity.PROJECT_ID_KEY, this.projectId);
        outState.putString(
            org.wheatgenetics.coordinate.projects.ProjectsActivity.DIRECTORY_NAME_KEY,
            this.directoryName                                                       );

        super.onSaveInstanceState(outState);
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