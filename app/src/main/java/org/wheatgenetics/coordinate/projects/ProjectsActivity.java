package org.wheatgenetics.coordinate.projects;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.wheatgenetics.coordinate.AboutActivity;
import org.wheatgenetics.coordinate.BackActivity;
import org.wheatgenetics.coordinate.CollectorActivity;
import org.wheatgenetics.coordinate.R;
import org.wheatgenetics.coordinate.Types;
import org.wheatgenetics.coordinate.deleter.ProjectDeleter;
import org.wheatgenetics.coordinate.gc.StatelessGridCreator;
import org.wheatgenetics.coordinate.grids.GridsActivity;
import org.wheatgenetics.coordinate.pc.ProjectCreator;
import org.wheatgenetics.coordinate.pe.ProjectExportPreprocessor;
import org.wheatgenetics.coordinate.pe.ProjectExporter;
import org.wheatgenetics.coordinate.preference.PreferenceActivity;
import org.wheatgenetics.coordinate.templates.TemplatesActivity;

public class ProjectsActivity extends BackActivity {
    private static final int COLLECT_DATA_REQUEST_CODE = 10,
            EXPORT_PROJECT_REQUEST_CODE = 20, SHOW_GRIDS_REQUEST_CODE = 30;
    private static Intent INTENT_INSTANCE = null;                       // lazy load
    // region Fields
    private ProjectsViewModel projectsViewModel;
    private ProjectsAdapter projectsAdapter = null;
    private StatelessGridCreator
            statelessGridCreatorInstance = null;                                            // lazy load
    private ProjectDeleter projectDeleterInstance = null; // ll
    // region exportProject() Fields
    private ProjectExportPreprocessor
            projectExportPreprocessorInstance = null;                                       // lazy load
    // endregion
    private ProjectExporter projectExporterInstance = null;    // ll
    private ProjectCreator projectCreatorInstance = null;      // ll
    // endregion

    @NonNull
    public static Intent intent(
            @NonNull final Context context) {
        return null == ProjectsActivity.INTENT_INSTANCE ?
                ProjectsActivity.INTENT_INSTANCE =
                        new Intent(context,
                                ProjectsActivity.class) :
                ProjectsActivity.INTENT_INSTANCE;
    }

    // region Private Methods
    // region createGrid() Private Methods
    private void startCollectorActivity(@IntRange(from = 1) final long gridId) {
        this.startActivityForResult(
                CollectorActivity.intent(this, gridId),
                ProjectsActivity.COLLECT_DATA_REQUEST_CODE);
    }

    @NonNull
    private StatelessGridCreator statelessGridCreator() {
        if (null == this.statelessGridCreatorInstance) this.statelessGridCreatorInstance =
                new StatelessGridCreator(
                        this, Types.CREATE_GRID,
                        new StatelessGridCreator.Handler() {
                            @Override
                            public void handleGridCreated(
                                    @IntRange(from = 1) final long gridId) {
                                ProjectsActivity
                                        .this.startCollectorActivity(gridId);
                            }
                        });
        return this.statelessGridCreatorInstance;
    }
    // endregion

    private void createGrid(@IntRange(from = 1) final long projectId) {
        this.statelessGridCreator().createInProject(projectId);
    }

    private void notifyDataSetChanged() {
        if (null != this.projectsAdapter) this.projectsAdapter.notifyDataSetChanged();
    }

    // region deleteProject() Private Methods
    @NonNull
    private ProjectDeleter projectDeleter() {
        if (null == this.projectDeleterInstance) this.projectDeleterInstance =
                new ProjectDeleter(this,
                        new ProjectDeleter.Handler() {
                            @Override
                            public void respondToDeletedProject(
                                    @IntRange(from = 1) final long projectId) {
                                ProjectsActivity.this.notifyDataSetChanged();
                            }
                        });
        return this.projectDeleterInstance;
    }
    // endregion

    private void deleteProject(@IntRange(from = 1) final long projectId) {
        this.projectDeleter().delete(projectId);
    }

    // region Export Private Methods
    // region exportProject() Private Methods
    @NonNull
    private ProjectExporter projectExporter() {
        if (null == this.projectExporterInstance) this.projectExporterInstance =
                new ProjectExporter(this,
                        ProjectsActivity.EXPORT_PROJECT_REQUEST_CODE);
        return this.projectExporterInstance;
    }

    private void exportProject() {
        this.projectExporter().export(
                this.projectsViewModel.getId(), this.projectsViewModel.getDirectoryName());
    }
    // endregion

    private void exportProject(@IntRange(from = 1) final long projectId,
                               final String directoryName) {
        this.projectsViewModel.setProjectIdAndDirectoryName(projectId, directoryName);
        this.exportProject();
    }

    // region preprocessProjectExport() Private Methods
    @NonNull
    private ProjectExportPreprocessor projectExportPreprocessor() {
        if (null == this.projectExportPreprocessorInstance) this.projectExportPreprocessorInstance =
                new ProjectExportPreprocessor(this,
                        new ProjectExportPreprocessor.Handler() {
                            @Override
                            public void exportProject(
                                    @IntRange(from = 1) final long projectId,
                                    final String directoryName) {
                                ProjectsActivity
                                        .this.exportProject(projectId, directoryName);
                            }
                        });
        return this.projectExportPreprocessorInstance;
    }
    // endregion
    // endregion

    private void preprocessProjectExport(@IntRange(from = 1) final long projectId) {
        this.projectExportPreprocessor().preprocess(projectId);
    }

    private void startGridsActivity(@IntRange(from = 1) final long projectId) {
        this.startActivityForResult(
                GridsActivity.projectIdIntent(
                        this, projectId),
                ProjectsActivity.SHOW_GRIDS_REQUEST_CODE);
    }
    // endregion

    @NonNull
    private ProjectCreator projectCreator() {
        if (null == this.projectCreatorInstance) this.projectCreatorInstance =
                new ProjectCreator(this,
                        new ProjectCreator.Handler() {
                            @Override
                            public void handleCreateProjectDone(
                                    @IntRange(from = 1) final long projectId) {
                                ProjectsActivity.this.notifyDataSetChanged();
                            }
                        });
        return this.projectCreatorInstance;
    }

    // region Overridden Methods
    @Override
    protected void onCreate(
            @Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_projects);

        this.projectsViewModel = new ViewModelProvider(this).get(
                ProjectsViewModel.class);

        final ListView projectsListView = this.findViewById(
                R.id.projectsListView);

        setupBottomNavigationBar();

        if (null != projectsListView) projectsListView.setAdapter(this.projectsAdapter =
                new ProjectsAdapter(this,
                        /* onCreateGridButtonClickListener => */ new View.OnClickListener() {
                    @Override
                    public void onClick(final View view) {
                        if (null != view) ProjectsActivity
                                .this.createGrid((Long) view.getTag());
                    }
                }, /* onDeleteButtonClickListener => */ new View.OnClickListener() {
                    @Override
                    public void onClick(final View view) {
                        if (null != view) ProjectsActivity
                                .this.deleteProject((Long) view.getTag());
                    }
                }, /* onExportButtonClickListener => */ new View.OnClickListener() {
                    @Override
                    public void onClick(final View view) {
                        if (null != view) ProjectsActivity
                                .this.preprocessProjectExport((Long) view.getTag());
                    }
                }, /* onShowGridsButtonClickListener => */ new View.OnClickListener() {
                    @Override
                    public void onClick(final View view) {
                        if (null != view) ProjectsActivity
                                .this.startGridsActivity((Long) view.getTag());
                    }
                }));
    }

    @Override
    protected void onResume() {
        super.onResume();
        final BottomNavigationView bottomNavigationView = findViewById(R.id.act_projects_bnv);
        bottomNavigationView.setSelectedItemId(R.id.action_nav_projects);
    }

    private void setupBottomNavigationBar() {

        final BottomNavigationView bottomNavigationView = findViewById(R.id.act_projects_bnv);
        bottomNavigationView.inflateMenu(R.menu.menu_bottom_nav_bar);

        bottomNavigationView.setOnItemSelectedListener((item -> {

            final int templates = R.id.action_nav_templates;
            final int grids = R.id.action_nav_grids;
            final int settings = R.id.action_nav_settings;
            final int about = R.id.action_nav_about;

            switch(item.getItemId()) {
                case templates:
                    startActivity(TemplatesActivity.intent(this));
                    break;
                case grids:
                    startActivity(GridsActivity.intent(this));
                    break;
                case settings:
                    startActivity(PreferenceActivity.intent(this));
                    break;
                case about:
                    startActivity(new Intent(this, AboutActivity.class));
                    break;
                default:
                    break;
            }

            return true;
        }));
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        this.getMenuInflater().inflate(R.menu.menu_projects, menu);
        return true;
    }

    @Override
    protected void onActivityResult(final int requestCode,
                                    final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case ProjectsActivity.COLLECT_DATA_REQUEST_CODE:
            case ProjectsActivity.SHOW_GRIDS_REQUEST_CODE:
                this.notifyDataSetChanged();
                break;

            case Types.CREATE_GRID:
                if (Activity.RESULT_OK == resultCode && null != data)
                    this.statelessGridCreator().continueExcluding(data.getExtras());
                break;
        }
    }
    // endregion

    @Override
    public void onRequestPermissionsResult(final int requestCode,
                                           @SuppressWarnings({"CStyleArrayDeclaration"}) @NonNull final String permissions[],
                                           @SuppressWarnings({"CStyleArrayDeclaration"}) @NonNull final int grantResults[]) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean permissionFound = false;
        for (final String permission : permissions)
            if (Manifest.permission.WRITE_EXTERNAL_STORAGE.equals(permission)) {
                permissionFound = true;
                break;
            }

        if (permissionFound) for (final int grantResult : grantResults)
            if (PackageManager.PERMISSION_GRANTED == grantResult)
                // noinspection SwitchStatementWithTooFewBranches
                switch (requestCode) {
                    case ProjectsActivity
                            .EXPORT_PROJECT_REQUEST_CODE:
                        this.exportProject();
                        break;
                }
    }
    // endregion

    // region MenuItem Event Handler
    public void onNewProjectMenuItemClick(@SuppressWarnings({"unused"}) final MenuItem menuItem) {
        this.projectCreator().createAndReturn();
    }
}