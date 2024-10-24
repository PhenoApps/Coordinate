package org.wheatgenetics.coordinate.grids;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.StringRes;
import androidx.appcompat.app.ActionBar;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;

import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.phenoapps.androidlibrary.Utils;
import org.wheatgenetics.coordinate.CollectorActivity;
import org.wheatgenetics.coordinate.R;
import org.wheatgenetics.coordinate.Types;
import org.wheatgenetics.coordinate.activities.BaseMainActivity;
import org.wheatgenetics.coordinate.activity.AppIntroActivity;
import org.wheatgenetics.coordinate.activity.GridCreatorActivity;
import org.wheatgenetics.coordinate.database.SampleData;
import org.wheatgenetics.coordinate.deleter.GridDeleter;
import org.wheatgenetics.coordinate.gc.GridCreator;
import org.wheatgenetics.coordinate.gc.StatelessGridCreator;
import org.wheatgenetics.coordinate.ge.GridExportPreprocessor;
import org.wheatgenetics.coordinate.ge.GridExporter;
import org.wheatgenetics.coordinate.model.ProjectModel;
import org.wheatgenetics.coordinate.model.TemplateModel;
import org.wheatgenetics.coordinate.pc.ProjectCreator;
import org.wheatgenetics.coordinate.preference.GeneralKeys;
import org.wheatgenetics.coordinate.preference.PreferenceActivity;
import org.wheatgenetics.coordinate.projects.ProjectsActivity;
import org.wheatgenetics.coordinate.tc.TemplateCreator;
import org.wheatgenetics.coordinate.templates.TemplatesActivity;
import org.wheatgenetics.coordinate.utils.Keys;
import org.wheatgenetics.coordinate.utils.TapTargetUtil;
import org.wheatgenetics.coordinate.viewmodel.ExportingViewModel;

import java.io.OutputStream;

public class GridsActivity extends BaseMainActivity implements TemplateCreator.Handler {
    // region Constants
    private static final String
            TEMPLATE_ID_KEY = "templateId", PROJECT_ID_KEY = "projectId";
    private static final int EXPORT_GRID_REQUEST_CODE = 10;
    private static final int CREATE_GRID_REFRESH = 102;
    private final int REQUEST_STORAGE_DEFINER = 104;

    private final int REQUEST_APP_INTRO = 112;
    // endregion
    private static Intent INTENT_INSTANCE = null;                       // lazy load
    // region Fields
    private ExportingViewModel gridsViewModel;
    private View.OnClickListener onCollectDataButtonClickListenerInstance = null,
            onDeleteButtonClickListenerInstance = null, onExportButtonClickListenerInstance = null;//lls
    private GridDeleter gridDeleterInstance = null;// lazy load
    // region exportGrid() Fields
    private GridExporter gridExporterInstance = null;// ll
    // endregion
    private GridExportPreprocessor
            gridExportPreprocessorInstance = null;                                          // lazy load
    private GridsAdapter gridsAdapter = null;
    private StatelessGridCreator
            statelessGridCreatorInstance = null;                                            // lazy load
    // endregion
    // region Fields
    private TemplateCreator templateCreatorInstance = null;    // ll
    private ProjectCreator projectCreatorInstance = null;    // ll
    // endregion

    private boolean isTemplateFilter = false;
    private boolean isProjectFilter = false;

    private Menu systemMenu;

    private final ActivityResultLauncher<String> exportGridsLauncher = registerForActivityResult(new ActivityResultContracts.CreateDocument(), (uri) -> {

        if (uri != null) {

            try {

                exportGrid(getContentResolver().openOutputStream(uri));

                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
                if (prefs.getBoolean(GeneralKeys.SHARE_EXPORTS, false)) {
                    Intent intent = new Intent();
                    intent.setAction(android.content.Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_STREAM, uri);
                    startActivity(Intent.createChooser(intent, "Sending File..."));
                }

            } catch (Exception e) {

                e.printStackTrace();

            }
        }
    });

    // region intent Private Methods
    @NonNull
    private static Intent INTENT(
            @NonNull final Context context) {
        return null == GridsActivity.INTENT_INSTANCE ?
                GridsActivity.INTENT_INSTANCE =
                        new Intent(context,
                                GridsActivity.class) :
                GridsActivity.INTENT_INSTANCE;
    }

    @NonNull
    private static Intent removeTEMPLATE_ID_KEY(
            @NonNull final Intent intent) {
        {
            final String TEMPLATE_ID_KEY =
                    GridsActivity.TEMPLATE_ID_KEY;
            if (intent.hasExtra(TEMPLATE_ID_KEY)) intent.removeExtra(TEMPLATE_ID_KEY);
        }
        return intent;
    }

    @NonNull
    private static Intent removePROJECT_ID_KEY(
            @NonNull final Intent intent) {
        {
            final String PROJECT_ID_KEY =
                    GridsActivity.PROJECT_ID_KEY;
            if (intent.hasExtra(PROJECT_ID_KEY)) intent.removeExtra(PROJECT_ID_KEY);
        }
        return intent;
    }

    // region Public Methods
    @NonNull
    public static Intent intent(
            @NonNull final Context context) {
        return GridsActivity.removePROJECT_ID_KEY(
                GridsActivity.removeTEMPLATE_ID_KEY(
                        GridsActivity.INTENT(context)));
    }
    // endregion

    @NonNull
    public static Intent templateIdIntent(
            @NonNull final Context context,
            @IntRange(from = 1) final long templateId) {
        return GridsActivity.removePROJECT_ID_KEY(
                GridsActivity.INTENT(context)).putExtra(
                GridsActivity.TEMPLATE_ID_KEY, templateId);
    }

    @NonNull
    public static Intent projectIdIntent(
            @NonNull final Context context,
            @IntRange(from = 1) final long projectId) {
        return GridsActivity.removeTEMPLATE_ID_KEY(
                GridsActivity.INTENT(context)).putExtra(
                GridsActivity.PROJECT_ID_KEY, projectId);
    }

    // region Private Methods
    private void startCollectorActivity(@IntRange(from = 1) final long gridId) {
        this.startActivity(
                CollectorActivity.intent(this, gridId));
    }
    // endregion

    private void notifyDataSetChanged() {
        if (null != this.gridsAdapter) this.gridsAdapter.notifyDataSetChanged();
    }

    // region deleteGrid() Private Methods
    @NonNull
    private GridDeleter gridDeleter() {
        if (null == this.gridDeleterInstance) this.gridDeleterInstance =
                new GridDeleter(this,
                        new GridDeleter.Handler() {
                            @Override
                            public void respondToDeletedGrid() {
                                GridsActivity.this.notifyDataSetChanged();
                            }
                        });
        return this.gridDeleterInstance;
    }
    // endregion
    // endregion

    private void deleteGrid(@IntRange(from = 1) final long gridId) {
        this.gridDeleter().deleteWithConfirm(gridId);
    }

    // region Export Private Methods
    // region exportGrid() Private Methods
    @NonNull
    private GridExporter gridExporter() {
        if (null == this.gridExporterInstance) this.gridExporterInstance =
                new GridExporter(this,
                        GridsActivity.EXPORT_GRID_REQUEST_CODE,
                        new GridDeleter.Handler() {
                            @Override
                            public void respondToDeletedGrid() {
                                GridsActivity.this.notifyDataSetChanged();
                            }
                        },
                        true);
        return this.gridExporterInstance;
    }

    private void exportGrid(OutputStream output) {
        this.gridExporter().export(
                this.gridsViewModel.getId(),
                this.gridsViewModel.getExportFileName(),
                output);
    }

    private void exportGrid() {
        this.gridExporter().export(
                this.gridsViewModel.getId(), this.gridsViewModel.getExportFileName());
    }
    // endregion

    private void exportGrid(@IntRange(from = 1) final long gridId,
                            final String fileName) {
        gridExportPreprocessor().handleExport(gridId, fileName, gridExporter(), this::launchExport);
    }

    private void launchExport(String fileName) {
        exportGridsLauncher.launch(fileName);
    }

    // region preprocessGridExport() Private Methods
    @NonNull
    public GridExportPreprocessor gridExportPreprocessor() {
        if (null == this.gridExportPreprocessorInstance) this.gridExportPreprocessorInstance =
                new GridExportPreprocessor(this,
                        new GridExportPreprocessor.Handler() {
                            @Override
                            public void exportGrid(
                                    @IntRange(from = 1) final long gridId,
                                    final String fileName) {
                                GridsActivity.this.exportGrid(
                                        gridId, fileName);
                            }
                        });
        return this.gridExportPreprocessorInstance;
    }

    private void preprocessGridExport(@IntRange(from = 1) final long gridId) {
        this.gridExportPreprocessor().preprocess(gridId);
    }

    // region onButtonClickListener() Private Methods
    @NonNull
    private View.OnClickListener onCollectDataButtonClickListener() {
        if (null == this.onCollectDataButtonClickListenerInstance)
            this.onCollectDataButtonClickListenerInstance = new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    if (null != view) GridsActivity
                            .this.startCollectorActivity((Long) view.getTag());
                }
            };
        return this.onCollectDataButtonClickListenerInstance;
    }
    // endregion

    @NonNull
    private View.OnClickListener onDeleteButtonClickListener() {
        if (null == this.onDeleteButtonClickListenerInstance)
            this.onDeleteButtonClickListenerInstance = new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    if (null != view) GridsActivity
                            .this.deleteGrid((Long) view.getTag());
                }
            };
        return this.onDeleteButtonClickListenerInstance;
    }

    @NonNull
    private View.OnClickListener onExportButtonClickListener() {
        if (null == this.onExportButtonClickListenerInstance)
            this.onExportButtonClickListenerInstance = new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    if (null != view) GridsActivity
                            .this.preprocessGridExport((Long) view.getTag());
                }
            };
        return this.onExportButtonClickListenerInstance;
    }

    @NonNull
    private AllGridsAdapter makeAllGridsAdapter() {
        return new AllGridsAdapter(this,
                this.onCollectDataButtonClickListener(), this.onDeleteButtonClickListener(),
                this.onExportButtonClickListener());
    }
    // endregion
    // endregion

    // region createGrid() Private Methods
    private void handleGridCreated(@IntRange(from = 1) final long gridId) {
        this.notifyDataSetChanged();
        this.startCollectorActivity(gridId);
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
                                GridsActivity.this.handleGridCreated(
                                        gridId);
                            }
                        });
        return this.statelessGridCreatorInstance;
    }

    private void setupActionBar() {
        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            getSupportActionBar().setTitle(R.string.act_grids_title);
        }
    }

    private void createGrid() {
        Intent intent = this.getIntent();
        long projectId = intent.getLongExtra(PROJECT_ID_KEY, -1);
        long templateId = intent.getLongExtra(TEMPLATE_ID_KEY, -1);

        Intent creator = new Intent(this, GridCreatorActivity.class);
        if (projectId != -1) {
            creator.putExtra("projectId", projectId);
        } else if (templateId != - 1) {
            creator.putExtra("templateId", templateId);
        }
        startActivityForResult(creator, CREATE_GRID_REFRESH);
        //statelessGridCreator().create();
    }

    // region Overridden Methods
    @Override
    protected void onCreate(
            @Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_grids);

        this.gridsViewModel = new ViewModelProvider(this).get(
                ExportingViewModel.class);

        final ListView gridsListView = this.findViewById(
                R.id.gridsListView);


        final String TEMPLATE_ID_KEY = GridsActivity.TEMPLATE_ID_KEY;

        final String PROJECT_ID_KEY = GridsActivity.PROJECT_ID_KEY;

        final Intent intent = this.getIntent();
        if (intent != null) {
            if (!(intent.hasExtra(TEMPLATE_ID_KEY) || intent.hasExtra(PROJECT_ID_KEY)))  {
                // if intent does not have template/project id
                // (if no filter was applied on template/project)
                // navigate to last grid
                navigateToLastGrid();
            } else {
                // if filter was applied, then don't navigate and also clear the last saved grid
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
                prefs.edit().putLong(Keys.COLLECTOR_LAST_GRID, -1L).apply();
            }
        }

        setupBottomNavigationBar();

        setupActionBar();

        if (null != gridsListView) {
            {
                if (null == intent)
                    this.gridsAdapter = this.makeAllGridsAdapter();
                else {
                    if (intent.hasExtra(TEMPLATE_ID_KEY)) {
                        @IntRange(from = 1) final long templateId =
                                intent.getLongExtra(TEMPLATE_ID_KEY, -1);
                        this.gridsAdapter =
                                new TemplateGridsAdapter(
                                        this, templateId,
                                        this.onCollectDataButtonClickListener(),
                                        this.onDeleteButtonClickListener(),
                                        this.onExportButtonClickListener());
                    } else {
                        if (intent.hasExtra(PROJECT_ID_KEY)) {
                            @IntRange(from = 1) final long projectId =
                                    intent.getLongExtra(PROJECT_ID_KEY, -1);
                            this.gridsAdapter =
                                    new ProjectGridsAdapter(
                                            this, projectId,
                                            this.onCollectDataButtonClickListener(),
                                            this.onDeleteButtonClickListener(),
                                            this.onExportButtonClickListener());
                        } else this.gridsAdapter = this.makeAllGridsAdapter();
                    }
                }
            }
            gridsListView.setAdapter(this.gridsAdapter);

            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
            boolean firstLoadComplete = prefs.getBoolean(GeneralKeys.FIRST_LOAD_COMPLETE, false);
            if (!firstLoadComplete) {

                Intent appIntoIntent = new Intent(this, AppIntroActivity.class);
                startActivityForResult(appIntoIntent, REQUEST_APP_INTRO);

            }
        }
    }
    // endregion

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        final Intent intent = this.getIntent();
        if (intent != null) {
            if (intent.hasExtra(TEMPLATE_ID_KEY)) {
                isTemplateFilter = true;
                isProjectFilter = false;
                long templateId = intent.getLongExtra(TEMPLATE_ID_KEY, -1);
                updateTitleByTemplateId(templateId);
            } else {
                isTemplateFilter = false;
                isProjectFilter = true;
                final String PROJECT_ID_KEY = GridsActivity.PROJECT_ID_KEY;
                if (intent.hasExtra(PROJECT_ID_KEY)) {
                    long projectId = intent.getLongExtra(PROJECT_ID_KEY, -1);
                    updateTitleByProjectId(projectId);
                }
            }
        }
    }

    private void updateTitle(String title) {
        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME, ActionBar.DISPLAY_SHOW_CUSTOM);
            bar.setTitle(title);
            bar.setHomeButtonEnabled(true);
            bar.setDisplayHomeAsUpEnabled(true);
            bar.setDisplayShowTitleEnabled(true);
        }
    }

    private void updateTitleByTemplateId(long tid) {

        try {
            TemplateModel model = templatesTable().get(tid);
            if (model != null) {
                updateTitle(model.getTitle());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateTitleByProjectId(long pid) {

        try {
            ProjectModel model = projectsTable().get(pid);
            if (model != null) {
                updateTitle(model.getTitle());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Called in onCreate, this will navigate to the last opened grid.
     * Based on the saved preference grid id.
     */
    private void navigateToLastGrid() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        long lastGridId = prefs.getLong(Keys.COLLECTOR_LAST_GRID, -1L);

        if (lastGridId != -1L) {

            this.startCollectorActivity(lastGridId);

        }

        prefs.edit().putLong(Keys.COLLECTOR_LAST_GRID, -1L).apply();
    }

    @Override
    protected void onResume() {
        super.onResume();
        final BottomNavigationView bottomNavigationView = findViewById(R.id.act_grids_bnv);
        bottomNavigationView.setSelectedItemId(R.id.action_nav_grids);
    }

    private void setupBottomNavigationBar() {

        final BottomNavigationView bottomNavigationView = findViewById(R.id.act_grids_bnv);
        bottomNavigationView.inflateMenu(R.menu.menu_bottom_nav_bar);

        bottomNavigationView.setOnItemSelectedListener((item -> {

            final int templates = R.id.action_nav_templates;
            final int projects = R.id.action_nav_projects;
            final int settings = R.id.action_nav_settings;

            switch(item.getItemId()) {
                case projects:
                    navigateToProjects();
                    break;
                case templates:
                    navigateToTemplates();
                    break;
                case settings:
                    Intent prefsIntent = PreferenceActivity.intent(this);
                    prefsIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(prefsIntent);
                    break;
            }

            return true;
        }));
    }

    private void navigateToProjects() {
        Intent projectIntent = ProjectsActivity.intent(this);
        projectIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(ProjectsActivity.intent(this));
    }

    private void navigateToTemplates() {
        Intent templateIntent = TemplatesActivity.intent(this);
        templateIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(templateIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        this.getMenuInflater().inflate(R.menu.menu_grids, menu);

        systemMenu = menu;

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        systemMenu.findItem(R.id.help).setVisible(preferences.getBoolean(GeneralKeys.TIPS, false));
        return true;
    }
    // endregion

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_new_grid) {
            createGrid();
        } else if (item.getItemId() == R.id.help) {
            TapTargetSequence sequence = new TapTargetSequence(this)
                    .targets(gridActivityTapTargetView(R.id.action_new_grid, getString(R.string.tutorial_grid_create_title), getString(R.string.tutorial_grid_create_summary), 60)
                    );
            if (!gridsAdapter.isEmpty()) {
                sequence.targets(
                        gridActivityTapTargetView(R.id.gridsListItemCollectDataButton, getString(R.string.tutorial_grid_collect_title), getString(R.string.tutorial_grid_collect_summary), 40),
                        gridActivityTapTargetView(R.id.gridsListItemDeleteButton, getString(R.string.tutorial_grid_delete_title), getString(R.string.tutorial_grid_delete_summary), 40),
                        gridActivityTapTargetView(R.id.gridsListItemExportButton, getString(R.string.tutorial_grid_export_title), getString(R.string.tutorial_grid_export_summary), 40)
                        );
            }
            sequence.start();
        }else if (item.getItemId() == android.R.id.home) {
            if (isProjectFilter) {
                navigateToProjects();
            } else if (isTemplateFilter) {
                navigateToTemplates();
            } else finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private TapTarget gridActivityTapTargetView(int id, String title, String desc, int targetRadius) {
        return TapTargetUtil.Companion.getTapTargetSettingsView(this, findViewById(id), title, desc, targetRadius);
    }

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
                    case GridsActivity.EXPORT_GRID_REQUEST_CODE:
                        this.exportGrid();
                        break;
                }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    // region MenuItem Event Handler
    public void onNewGridMenuItemClick(@SuppressWarnings({"unused"}) final MenuItem menuItem) { }
    // endregion

    // region Create Template Private Methods
    private void showLongToast(final String text) {
        Utils.showLongToast(this, text);
    }

    @NonNull
    private TemplateCreator templateCreator() {
        if (null == this.templateCreatorInstance)
            this.templateCreatorInstance = new TemplateCreator(
                    this, Types.CREATE_TEMPLATE, this);
        return this.templateCreatorInstance;
    }
    // endregion

    private ProjectCreator projectCreator() {
        if (null == this.projectCreatorInstance) this.projectCreatorInstance =
                new ProjectCreator(this);
        return this.projectCreatorInstance;
    }
    // endregion

    // region Overridden Methods

    @Override
    protected void onActivityResult(final int requestCode,
                                    final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (Activity.RESULT_OK == resultCode && null != data) {
            switch (requestCode) {
                case Types.CREATE_TEMPLATE:
                    this.templateCreator().continueExcluding(data.getExtras());
                    break;
                case Types.CREATE_GRID:
                    this.statelessGridCreator().continueExcluding(data.getExtras());
                    break;
                case CREATE_GRID_REFRESH:
                    if (resultCode == Activity.RESULT_OK) {
                        long gridId = data.getLongExtra("gridId", -1L);
                        if (gridId != -1L) {
                            startCollectorActivity(gridId);
                        }
                        this.notifyDataSetChanged();
                    }
                    break;
            }
        }
        if (requestCode == REQUEST_APP_INTRO) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
            if (resultCode == Activity.RESULT_OK) {
                prefs.edit().putBoolean(GeneralKeys.FIRST_LOAD_COMPLETE, true).apply();
            }
            boolean loadSampleData = prefs.getBoolean(GeneralKeys.LOAD_SAMPLE_DATA, false);
            if (loadSampleData) {
                new SampleData(this).insertSampleData();
                notifyDataSetChanged();
            }
        }
        if (requestCode == REQUEST_STORAGE_DEFINER) {
            if (resultCode == Activity.RESULT_OK) {
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
                prefs.edit().putBoolean(GeneralKeys.STORAGE_ASK_KEY, false).apply();
            }
        }
    }

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    @Override
    @NonNull
    protected GridCreator gridCreator() {
        if (null == this.statelessGridCreatorInstance) this.statelessGridCreatorInstance =
                new StatelessGridCreator(
                        this, Types.CREATE_GRID,
                        this::startCollectorActivity);
        return this.statelessGridCreatorInstance;
    }

    // region org.wheatgenetics.coordinate.tc.TemplateCreator.Handler Overridden Method
    @Override
    public void handleTemplateCreated(@NonNull final TemplateModel templateModel) {
        @NonNull final String text;
        {
            @NonNull final String format;
            {
                @StringRes final int resId =
                        this.templatesTable().insert(templateModel) > 0 ?
                                R.string.TemplateCreatedToast :
                                R.string.TemplateNotCreatedToast;
                format = this.getString(resId);
            }
            text = String.format(format, templateModel.getTitle());
        }
        this.showLongToast(text);
    }
    // endregion
    // endregion

    // region MenuItem Event Handlers
    public void onGridMenuItemClick(@SuppressWarnings({"unused"}) final MenuItem menuItem) {
        ((StatelessGridCreator) this.gridCreator()).create();
    }

    public void onTemplateMenuItemClick(@SuppressWarnings({"unused"}) final MenuItem menuItem) {
        this.templateCreator().create();
    }

    public void onProjectMenuItemClick(@SuppressWarnings({"unused"}) final MenuItem menuItem) {
        this.projectCreator().create();
    }
    // endregion
}