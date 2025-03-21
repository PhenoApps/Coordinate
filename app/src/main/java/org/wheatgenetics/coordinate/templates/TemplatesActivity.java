package org.wheatgenetics.coordinate.templates;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
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
import androidx.annotation.StringRes;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;

import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.phenoapps.androidlibrary.Utils;
import org.phenoapps.utils.BaseDocumentTreeUtil;
import org.wheatgenetics.coordinate.BackActivity;
import org.wheatgenetics.coordinate.CollectorActivity;
import org.wheatgenetics.coordinate.R;
import org.wheatgenetics.coordinate.Types;
import org.wheatgenetics.coordinate.activity.DefineStorageActivity;
import org.wheatgenetics.coordinate.activity.GridCreatorActivity;
import org.wheatgenetics.coordinate.activity.TemplateCreatorActivity;
import org.wheatgenetics.coordinate.contracts.OpenDocumentFancy;
import org.wheatgenetics.coordinate.database.TemplatesTable;
import org.wheatgenetics.coordinate.deleter.TemplateDeleter;
import org.wheatgenetics.coordinate.gc.StatelessGridCreator;
import org.wheatgenetics.coordinate.grids.GridsActivity;
import org.wheatgenetics.coordinate.model.TemplateModel;
import org.wheatgenetics.coordinate.preference.GeneralKeys;
import org.wheatgenetics.coordinate.preference.PreferenceActivity;
import org.wheatgenetics.coordinate.projects.ProjectsActivity;
import org.wheatgenetics.coordinate.tc.TemplateCreator;
import org.wheatgenetics.coordinate.te.TemplateExportPreprocessor;
import org.wheatgenetics.coordinate.te.TemplateExporter;
import org.wheatgenetics.coordinate.ti.MenuItemEnabler;
import org.wheatgenetics.coordinate.ti.TemplateImportPreprocessor;
import org.wheatgenetics.coordinate.ti.TemplateImporter;
import org.wheatgenetics.coordinate.utils.DocumentTreeUtil;
import org.wheatgenetics.coordinate.utils.DocumentTreeUtil.Companion.CheckDocumentResult;
import org.wheatgenetics.coordinate.utils.FileUtil;
import org.wheatgenetics.coordinate.utils.TapTargetUtil;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;

public class TemplatesActivity extends BackActivity
        implements TemplateCreator.Handler {
    private static final int CREATE_GRID_REQUEST_CODE = 13;
    private static final int
            COLLECT_DATA_REQUEST_CODE = 1, EXPORT_TEMPLATE_REQUEST_CODE = 2,
            SHOW_GRIDS_REQUEST_CODE = 3, CONFIGURE_IMPORT_MENU_ITEM_REQUEST_CODE = 4,
            PREPROCESS_TEMPLATE_IMPORT_REQUEST_CODE = 5, IMPORT_TEMPLATE_REQUEST_CODE = 6;
    private static Intent INTENT_INSTANCE = null;                       // lazy load
    // region Fields
    private TemplatesViewModel templatesViewModel;
    private TemplatesAdapter templatesAdapter = null;
    private StatelessGridCreator
            statelessGridCreatorInstance = null;                                            // lazy load
    private TemplateDeleter templateDeleterInstance = null;//ll
    // region exportTemplate() Fields
    private TemplateExportPreprocessor
            templateExportPreprocessorInstance = null;                                      // lazy load
    // endregion
    private TemplateExporter templateExporterInstance = null; // ll
    // region importMenuItem Fields
    private MenuItem importMenuItem = null;
    // endregion
    private MenuItemEnabler menuItemEnablerInstance = null;    // ll
    // region createTemplate() Fields
    private TemplateCreator templateCreatorInstance = null;//ll
    // endregion
    private TemplatesTable templatesTableInstance = null;//ll
    // region Import Fields
    private TemplateImportPreprocessor
            templateImportPreprocessorInstance = null;                                      // lazy load
    // endregion
    private TemplateImporter templateImporterInstance = null;  // ll
    // endregion

    private Menu systemMenu;

    private final ActivityResultLauncher<String> importTemplateLauncher = registerForActivityResult(new OpenDocumentFancy(), (uri) -> {

        if (uri != null) {

            try {

                importTemplate(getContentResolver().openInputStream(uri));

            } catch (FileNotFoundException e) {

                e.printStackTrace();

            }
        }
    });

    private final ActivityResultLauncher<String> exportTemplateLauncher = registerForActivityResult(new ActivityResultContracts.CreateDocument(), (uri) -> {

        if (uri != null) {

            try {

                exportTemplate(getContentResolver().openOutputStream(uri));

                FileUtil.Companion.shareFile(this, uri);

            } catch (Exception e) {

                e.printStackTrace();

            }
        }
    });

    @NonNull
    public static Intent intent(
            @NonNull final Context context) {
        return null == TemplatesActivity.INTENT_INSTANCE ?
                TemplatesActivity.INTENT_INSTANCE =
                        new Intent(context,
                                TemplatesActivity.class) :
                TemplatesActivity.INTENT_INSTANCE;
    }

    // region Private Methods
    // region createGrid() Private Methods
    private void startCollectorActivity(@IntRange(from = 1) final long gridId) {
        this.startActivityForResult(
                CollectorActivity.intent(this, gridId),
                TemplatesActivity.COLLECT_DATA_REQUEST_CODE);
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
                                TemplatesActivity
                                        .this.startCollectorActivity(gridId);
                            }
                        });
        return this.statelessGridCreatorInstance;
    }
    // endregion

    private void createGrid(@IntRange(from = 1) final long templateId) {

        Intent creator = new Intent(this, GridCreatorActivity.class);
        creator.putExtra("templateId", templateId);
        startActivityForResult(creator, CREATE_GRID_REQUEST_CODE);
        //statelessGridCreator().createFromTemplate(templateId);
    }

    private void notifyDataSetChanged() {
        if (null != this.templatesAdapter) this.templatesAdapter.notifyDataSetChanged();
    }

    // region deleteTemplate() Private Methods
    @NonNull
    private TemplateDeleter templateDeleter() {
        if (null == this.templateDeleterInstance) this.templateDeleterInstance =
                new TemplateDeleter(this,
                        new TemplateDeleter.TemplateHandler() {
                            @Override
                            public void respondToDeletedTemplate(
                                    @IntRange(from = 1) final long templateId) {
                                TemplatesActivity.this.notifyDataSetChanged();
                            }
                        });
        return this.templateDeleterInstance;
    }
    // endregion

    private void deleteTemplate(@IntRange(from = 1) final long templateId) {

        AlertDialog.Builder askDelete = new AlertDialog.Builder(this)
                .setTitle(R.string.dialog_act_templates_ask_delete)
                .setPositiveButton(android.R.string.ok, (dialog, which) -> {

                    this.templateDeleter().delete(templateId);

                })
                .setNegativeButton(android.R.string.cancel, (dialog, which) -> {

                });

        askDelete.create();

        askDelete.show();

    }

    // region Export Private Methods
    // region exportTemplate() Private Methods
    @NonNull
    private TemplateExporter templateExporter() {
        if (null == this.templateExporterInstance) this.templateExporterInstance =
                new TemplateExporter(this, TemplatesActivity.EXPORT_TEMPLATE_REQUEST_CODE);
        return this.templateExporterInstance;
    }

    private void exportTemplate(OutputStream output) {
        this.templateExporter().export(
                this.templatesViewModel.getId(),
                output);
    }

    private void exportTemplate() {
        this.templateExporter().export(
                this.templatesViewModel.getId(), this.templatesViewModel.getExportFileName());
    }
    // endregion

    private void exportTemplate(@IntRange(from = 1) final long templateId,
                                final String fileName) {
        this.templatesViewModel.setIdAndExportFileName(templateId, fileName);

        if (BaseDocumentTreeUtil.Companion.isEnabled(this)) {

            //result is CheckDocumentResult 1=Exists, 2=Define, 3=Dismiss
            DocumentTreeUtil.Companion.checkDir(this, (result) -> {

                if (result == CheckDocumentResult.DISMISS) {

                    exportTemplateLauncher.launch(fileName + ".xml");

                } else if (result == CheckDocumentResult.DEFINE) {

                    startActivity(new Intent(this, DefineStorageActivity.class));

                } else {

                    //export without prompt (auto export to exports folder)
                    exportTemplate();
                }

                return null;
            });

        } else {

            exportTemplateLauncher.launch(fileName + ".xml");
        }
    }

    // region preprocessTemplateExport() Private Methods
    @NonNull
    private TemplateExportPreprocessor templateExportPreprocessor() {
        if (null == this.templateExportPreprocessorInstance)
            this.templateExportPreprocessorInstance =
                    new TemplateExportPreprocessor(this,
                            new TemplateExportPreprocessor.Handler() {
                                @Override
                                public void exportTemplate(
                                        @IntRange(from = 1) final long templateId,
                                        final String fileName) {
                                    TemplatesActivity
                                            .this.exportTemplate(templateId, fileName);
                                }
                            });
        return this.templateExportPreprocessorInstance;
    }
    // endregion
    // endregion

    private void preprocessTemplateExport(@IntRange(from = 1) final long templateId) {
        this.templateExportPreprocessor().preprocess(templateId);
    }

    private void startTemplateEditor(final long templateId) {
        Intent creator = new Intent(this, TemplateCreatorActivity.class);
        creator.putExtra(TemplateCreatorActivity.TEMPLATE_EDIT, templateId);
        startActivityForResult(creator, TemplatesActivity.SHOW_GRIDS_REQUEST_CODE);
    }

    private void startGridsActivity(@IntRange(from = 1) final long templateId) {
        this.startActivityForResult(
                GridsActivity.templateIdIntent(
                        this, templateId),
                TemplatesActivity.SHOW_GRIDS_REQUEST_CODE);
    }

    // region importMenuItem Private Methods
    @NonNull
    private MenuItemEnabler menuItemEnabler() {
        if (null == this.menuItemEnablerInstance) this.menuItemEnablerInstance =
                new MenuItemEnabler(this, TemplatesActivity.CONFIGURE_IMPORT_MENU_ITEM_REQUEST_CODE);
        return this.menuItemEnablerInstance;
    }
    // endregion

    private void configureImportMenuItem() {
        if (null != this.importMenuItem)
            this.importMenuItem.setEnabled(true);
    }

    // region createTemplate() Private Methods
    private void showLongToast(final String text) {
        Utils.showLongToast(this, text);
    }

    @NonNull
    private TemplatesTable templatesTable() {
        if (null == this.templatesTableInstance) this.templatesTableInstance =
                new TemplatesTable(this);
        return this.templatesTableInstance;
    }

    @NonNull
    private TemplateCreator templateCreator() {
        if (null == this.templateCreatorInstance)
            this.templateCreatorInstance = new TemplateCreator(
                    this, Types.CREATE_TEMPLATE, this);
        return this.templateCreatorInstance;
    }
    // endregion

    private void createTemplate() {
        startActivityForResult(new Intent(this, TemplateCreatorActivity.class),
                TemplatesActivity.SHOW_GRIDS_REQUEST_CODE);

        //old template creator
        //this.templateCreator().create();
    }

    // region Import Private Methods
    // region importTemplate() Private Methods
    @NonNull
    private TemplateImporter templateImporter() {
        if (null == this.templateImporterInstance) this.templateImporterInstance =
                new TemplateImporter(this, TemplatesActivity.IMPORT_TEMPLATE_REQUEST_CODE,
                        new TemplateImporter.Adapter() {
                            @Override
                            public void notifyDataSetChanged() {
                                TemplatesActivity.this.notifyDataSetChanged();
                            }
                        });
        return this.templateImporterInstance;
    }

    private void importTemplate(InputStream input) {
        this.templateImporter().importTemplate(input);
    }

    private void importTemplate() {
        this.templateImporter().importTemplate(this.templatesViewModel.getImportFileName());
    }
    // endregion

    private void importTemplate(final String fileName) {
        this.templatesViewModel.setImportFileName(fileName);
        this.importTemplate();
    }

    // region preprocessTemplateImport() Import Private Methods
    @NonNull
    private TemplateImportPreprocessor templateImportPreprocessor() {
        if (null == this.templateImportPreprocessorInstance)
            this.templateImportPreprocessorInstance =
                    new TemplateImportPreprocessor(
                            this, TemplatesActivity.PREPROCESS_TEMPLATE_IMPORT_REQUEST_CODE,
                            new TemplateImportPreprocessor.Handler() {
                                @Override
                                public void importTemplate(final String fileName) {
                                    TemplatesActivity
                                            .this.importTemplate(fileName);
                                }
                            });
        return this.templateImportPreprocessorInstance;
    }
    // endregion
    // endregion
    // endregion

    private void preprocessTemplateImport() {
        this.templateImportPreprocessor().preprocess();
    }

    // region Overridden Methods
    @Override
    protected void onCreate(
            @Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_templates);

        this.templatesViewModel = new ViewModelProvider(this).get(
                TemplatesViewModel.class);

        final ListView templatesListView = this.findViewById(
                R.id.templatesListView);

        setupBottomNavigationBar();

        if (null != templatesListView) templatesListView.setAdapter(this.templatesAdapter =

                new TemplatesAdapter(this,
                        /* onCreateGridButtonClickListener => */ new View.OnClickListener() {
                    @Override
                    public void onClick(final View view) {
                        if (null != view)
                            TemplatesActivity
                                    .this.createGrid((Long) view.getTag());
                    }
                }, /* onDeleteButtonClickListener => */ new View.OnClickListener() {
                    @Override
                    public void onClick(final View view) {
                        if (null != view)
                            TemplatesActivity
                                    .this.deleteTemplate((Long) view.getTag());
                    }
                }, /* onExportButtonClickListener => */ new View.OnClickListener() {
                    @Override
                    public void onClick(final View view) {
                        if (null != view)
                            TemplatesActivity
                                    .this.preprocessTemplateExport((Long) view.getTag());
                    }
                }, /* onShowGridsButtonClickListener => */ new View.OnClickListener() {
                    @Override
                    public void onClick(final View view) {
                        if (null != view)
                            TemplatesActivity
                                    .this.startGridsActivity((Long) view.getTag());
                    }
                }, (view) -> {
                    if (view != null) {
                        TemplatesActivity.this.startTemplateEditor((Long) view.getTag());
                    }
                }));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_new_template) {
            createTemplate();
        } else if (item.getItemId() == R.id.help) {
            TapTargetSequence sequence = new TapTargetSequence(this)
                    .targets(templateActivityTapTargetView(R.id.action_new_template, getString(R.string.tutorial_template_create_title), getString(R.string.tutorial_template_create_summary), 60),
                            templateActivityTapTargetView(R.id.import_template_menu_item, getString(R.string.tutorial_template_import_title), getString(R.string.tutorial_template_import_summary), 60)
                    );
            if (!templatesAdapter.isEmpty()) {
                sequence.targets(
                        templateActivityTapTargetView(R.id.templatesListItemCreateGridButton, getString(R.string.tutorial_template_create_grid_title), getString(R.string.tutorial_template_create_grid_summary), 40),
                        templateActivityTapTargetView(R.id.templatesListItemShowGridsButton, getString(R.string.tutorial_template_show_grids_title), getString(R.string.tutorial_template_show_grids_summary), 40),
                        templateActivityTapTargetView(R.id.templatesListItemExportButton, getString(R.string.tutorial_template_export_title), getString(R.string.tutorial_template_export_summary), 40),
                        templateActivityTapTargetView(R.id.templatesListItemEditButton, getString(R.string.tutorial_template_edit_title), getString(R.string.tutorial_template_edit_summary), 40),
                        templateActivityTapTargetView(R.id.templatesListItemDeleteButton, getString(R.string.tutorial_template_delete_title), getString(R.string.tutorial_template_delete_summary), 40)
                );
            }
            sequence.start();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        final BottomNavigationView bottomNavigationView = findViewById(R.id.act_templates_bnv);
        bottomNavigationView.setSelectedItemId(R.id.action_nav_templates);
    }

    private TapTarget templateActivityTapTargetView(int id, String title, String desc, int targetRadius) {
        return TapTargetUtil.Companion.getTapTargetSettingsView(this, findViewById(id), title, desc, targetRadius);
    }

    private void setupBottomNavigationBar() {

        final BottomNavigationView bottomNavigationView = findViewById(R.id.act_templates_bnv);
        bottomNavigationView.inflateMenu(R.menu.menu_bottom_nav_bar);

        bottomNavigationView.setOnItemSelectedListener((item -> {

            final int grids = R.id.action_nav_grids;
            final int projects = R.id.action_nav_projects;
            final int settings = R.id.action_nav_settings;

            switch (item.getItemId()) {
                case grids:
                    Intent gridsIntent = GridsActivity.intent(this);
                    gridsIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(gridsIntent);
                    break;
                case settings:
                    Intent prefsIntent = PreferenceActivity.intent(this);
                    prefsIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(prefsIntent);
                    break;
                case projects:
                    Intent projectIntent = ProjectsActivity.intent(this);
                    projectIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(ProjectsActivity.intent(this));
                    break;
                default:
                    break;
            }

            return true;
        }));
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        this.getMenuInflater().inflate(R.menu.menu_templates, menu);

        if (null != menu) {
            this.importMenuItem = menu.findItem(
                    R.id.import_template_menu_item);
            this.configureImportMenuItem();

            systemMenu = menu;

            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

            systemMenu.findItem(R.id.help).setVisible(preferences.getBoolean(GeneralKeys.TIPS, false));
        }

        return true;
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
                switch (requestCode) {
                    case TemplatesActivity
                            .EXPORT_TEMPLATE_REQUEST_CODE:
                        this.exportTemplate();
                        break;

                    case TemplatesActivity
                            .CONFIGURE_IMPORT_MENU_ITEM_REQUEST_CODE:
                        this.configureImportMenuItem();
                        break;

                    case TemplatesActivity
                            .PREPROCESS_TEMPLATE_IMPORT_REQUEST_CODE:
                        this.preprocessTemplateImport();
                        break;

                    case TemplatesActivity
                            .IMPORT_TEMPLATE_REQUEST_CODE:
                        this.importTemplate();
                        break;
                }
    }

    @Override
    protected void onActivityResult(final int requestCode,
                                    final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case CREATE_GRID_REQUEST_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    long id = data.getLongExtra("gridId", -1L);
                    if (id != -1L) {
                        startActivityForResult(CollectorActivity.intent(this, id),
                                COLLECT_DATA_REQUEST_CODE);
                    }
                }
                break;
            case TemplatesActivity.COLLECT_DATA_REQUEST_CODE:
            case TemplatesActivity.SHOW_GRIDS_REQUEST_CODE:
                this.notifyDataSetChanged();
                break;

            case Types.CREATE_GRID:
            case Types.CREATE_TEMPLATE:
                if (Activity.RESULT_OK == resultCode && null != data)
                    switch (requestCode) {
                        case Types.CREATE_GRID:
                            this.statelessGridCreator().continueExcluding(data.getExtras());
                            break;

                        case Types.CREATE_TEMPLATE:
                            this.templateCreator().continueExcluding(data.getExtras());
                            break;
                    }
                break;
        }
    }
    // endregion
    // endregion

    // region org.wheatgenetics.coordinate.tc.TemplateCreator.Handler Overridden Method
    @Override
    public void handleTemplateCreated(@NonNull final TemplateModel templateModel) {
        @NonNull final String text;
        {
            @NonNull final String format;
            {
                @StringRes final int resId;
                {
                    final boolean templateCreated = this.templatesTable().insert(templateModel) > 0;
                    if (templateCreated) {
                        resId = R.string.TemplateCreatedToast;
                        this.notifyDataSetChanged();
                    } else resId = R.string.TemplateNotCreatedToast;
                }
                format = this.getString(resId);
            }
            text = String.format(format, templateModel.getTitle());
        }
        this.showLongToast(text);
    }

    // region MenuItem Event Handlers
    public void onNewTemplateMenuItemClick(@SuppressWarnings({"unused"}) final MenuItem menuItem) {
        this.createTemplate();
    }
    // endregion

    public void onImportTemplateMenuItem(@SuppressWarnings({"unused"}) final MenuItem menuItem) {

        importTemplateLauncher.launch("*/*");

    }
}