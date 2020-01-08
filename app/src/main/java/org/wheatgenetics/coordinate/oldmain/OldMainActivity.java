package org.wheatgenetics.coordinate.oldmain;

/**
 * Uses:
 * android.annotation.SuppressLint
 * android.app.Activity
 * android.content.Intent
 * android.content.pm.PackageInfo
 * android.content.pm.PackageManager
 * android.content.pm.PackageManager.NameNotFoundException
 * android.Manifest.permission
 * android.media.MediaPlayer
 * android.os.Bundle
 * android.view.inputmethod.InputMethodManager
 * android.view.Menu
 * android.view.MenuInflater
 * android.view.MenuItem
 * android.view.View
 * android.view.View.OnClickListener
 * android.widget.TextView
 *
 * androidx.annotation.IdRes
 * androidx.annotation.IntRange
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 * androidx.appcompat.app.ActionBar
 * androidx.appcompat.app.ActionBarDrawerToggle
 * androidx.appcompat.app.AppCompatActivity
 * androidx.appcompat.widget.Toolbar
 * androidx.core.view.GravityCompat
 * androidx.drawerlayout.widget.DrawerLayout
 * androidx.fragment.app.FragmentManager
 *
 * com.google.android.material.navigation.NavigationView
 *
 * org.wheatgenetics.javalib.Utils
 *
 * org.wheatgenetics.androidlibrary.R
 * org.wheatgenetics.changelog.ChangeLogAlertDialog
 * org.wheatgenetics.sharedpreferences.SharedPreferences
 * org.wheatgenetics.zxing.BarcodeScanner
 *
 * org.wheatgenetics.coordinate.database.EntriesTable
 * org.wheatgenetics.coordinate.database.GridsTable
 * org.wheatgenetics.coordinate.database.ProjectsTable
 * org.wheatgenetics.coordinate.database.TemplatesTable
 *
 * org.wheatgenetics.coordinate.deleter.GridDeleter.Handler
 *
 * org.wheatgenetics.coordinate.gc.GridCreator
 * org.wheatgenetics.coordinate.gc.GridCreator.Handler
 *
 * org.wheatgenetics.coordinate.ge.GridExporter
 * org.wheatgenetics.coordinate.ge.GridExporter.Handler
 *
 * org.wheatgenetics.coordinate.griddisplay.GridDisplayFragment
 * org.wheatgenetics.coordinate.griddisplay.GridDisplayFragment.Handler
 *
 * org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel
 * org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.Checker
 * org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.CheckException
 * org.wheatgenetics.coordinate.model.CurrentGridUniqueJoinedGridModel
 * org.wheatgenetics.coordinate.model.CurrentGridUniqueEntryModels
 * org.wheatgenetics.coordinate.model.DisplayModel
 * org.wheatgenetics.coordinate.model.ElementModel
 * org.wheatgenetics.coordinate.model.EntryModel
 * org.wheatgenetics.coordinate.model.EntryModels.FilledHandler
 * org.wheatgenetics.coordinate.model.ExcludedEntryModel
 * org.wheatgenetics.coordinate.model.IncludedEntryModel
 * org.wheatgenetics.coordinate.model.JoinedGridModel
 * org.wheatgenetics.coordinate.model.Model
 * org.wheatgenetics.coordinate.model.ProjectModel
 * org.wheatgenetics.coordinate.model.TemplateModel
 * org.wheatgenetics.coordinate.model.TemplateModels
 * org.wheatgenetics.coordinate.model.TemplateType
 * org.wheatgenetics.coordinate.model.UniqueEntryModels.DuplicateCheckException
 *
 * org.wheatgenetics.coordinate.nisl.NavigationItemSelectedListener
 * org.wheatgenetics.coordinate.nisl.NavigationItemSelectedListener.Handler
 *
 * org.wheatgenetics.coordinate.oldmain.DataEntryFragment
 * org.wheatgenetics.coordinate.oldmain.DataEntryFragment.Handler
 * org.wheatgenetics.coordinate.oldmain.UniqueAlertDialog
 * org.wheatgenetics.coordinate.oldmain.Utils
 *
 * org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields
 *
 * org.wheatgenetics.coordinate.pe.ProjectExporter
 *
 * org.wheatgenetics.coordinate.te.TemplateExporter
 *
 * org.wheatgenetics.coordinate.ti.MenuItemEnabler
 * org.wheatgenetics.coordinate.ti.TemplateImporter
 *
 * org.wheatgenetics.coordinate.R
 * org.wheatgenetics.coordinate.Types
 * org.wheatgenetics.coordinate.Utils
 * org.wheatgenetics.coordinate.Utils.Advancement
 */
public class OldMainActivity extends androidx.appcompat.app.AppCompatActivity implements
org.wheatgenetics.coordinate.griddisplay.GridDisplayFragment.Handler,
org.wheatgenetics.coordinate.model.EntryModels.FilledHandler    ,
org.wheatgenetics.coordinate.oldmain.DataEntryFragment.Handler  ,
org.wheatgenetics.coordinate.gc.GridCreator.Handler
{
    private static final int CONFIGURE_NAVIGATION_DRAWER = 10, PREPROCESS_TEMPLATE_IMPORT = 20,
        IMPORT_TEMPLATE = 21, EXPORT_TEMPLATE = 22, EXPORT_GRID_REQUEST_CODE = 30,
        EXPORT_PROJECT_REQUEST_CODE = 31;

    // region Fields
    private androidx.drawerlayout.widget.DrawerLayout drawerLayout;
    private android.view.MenuItem manageGridMenuItem = null, exportGridMenuItem = null;
    private android.view.MenuItem templateMenuItem = null, importTemplateMenuItem = null,
        exportTemplateMenuItem = null, deleteTemplateMenuItem = null;
    private android.view.MenuItem projectMenuItem = null, manageProjectMenuItem = null,
        exportProjectMenuItem = null;
    private android.media.MediaPlayer gridEndMediaPlayer = null,
        rowOrColumnEndMediaPlayer = null, disallowedDuplicateMediaPlayer = null;       // lazy loads

    private org.wheatgenetics.coordinate.ti.MenuItemEnabler menuItemEnablerInstance    = null; // ll
    private org.wheatgenetics.sharedpreferences.SharedPreferences sharedPreferences          ;
    private org.wheatgenetics.changelog.ChangeLogAlertDialog      changeLogAlertDialog = null; // ll
    private org.wheatgenetics.zxing.BarcodeScanner                barcodeScanner       = null; // ll

    // region Table Fields
    private org.wheatgenetics.coordinate.database.TemplatesTable templatesTableInstance = null;// ll
    private org.wheatgenetics.coordinate.database.ProjectsTable  projectsTableInstance  = null;// ll
    private org.wheatgenetics.coordinate.database.GridsTable     gridsTableInstance     = null;// ll
    private org.wheatgenetics.coordinate.database.EntriesTable   entriesTableInstance   = null;// ll
    // endregion

    private org.wheatgenetics.coordinate.nisl.NavigationItemSelectedListener
        navigationItemSelectedListener;
    private org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel          = null;
    private org.wheatgenetics.coordinate.model.ProjectModel    projectModel             = null;
    private org.wheatgenetics.coordinate.gc.GridCreator        gridCreator              = null;// ll
    private org.wheatgenetics.coordinate.ge.GridExporter       gridExporterInstance     = null;// ll
    private org.wheatgenetics.coordinate.ti.TemplateImporter   templateImporterInstance = null;// ll
    private org.wheatgenetics.coordinate.te.TemplateExporter   templateExporterInstance = null;// ll
    private org.wheatgenetics.coordinate.pe.ProjectExporter    projectExporterInstance  = null;// ll

    private org.wheatgenetics.coordinate.griddisplay.GridDisplayFragment gridDisplayFragment;
    private org.wheatgenetics.coordinate.oldmain.DataEntryFragment       dataEntryFragment  ;

    private java.lang.String versionName;

    private org.wheatgenetics.coordinate.oldmain.UniqueAlertDialog uniqueAlertDialog = null;   // ll
    // endregion

    // region Private Methods
    // region Table Private Methods
    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.database.TemplatesTable templatesTable()
    {
        if (null == this.templatesTableInstance) this.templatesTableInstance =
            new org.wheatgenetics.coordinate.database.TemplatesTable(this);
        return this.templatesTableInstance;
    }

    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.database.ProjectsTable projectsTable()
    {
        if (null == this.projectsTableInstance) this.projectsTableInstance =
            new org.wheatgenetics.coordinate.database.ProjectsTable(this);
        return this.projectsTableInstance;
    }

    @androidx.annotation.Nullable
    private org.wheatgenetics.coordinate.database.GridsTable gridsTable()
    {
        return org.wheatgenetics.coordinate.oldmain.Utils.gridsTable(
            this.gridsTableInstance,this);
    }

    @androidx.annotation.Nullable
    private org.wheatgenetics.coordinate.database.EntriesTable entriesTable()
    {
        return org.wheatgenetics.coordinate.oldmain.Utils.entriesTable(
            this.entriesTableInstance, this.gridsTable(),this);
    }
    // endregion

    // region configureNavigationDrawer() Private Methods
    private boolean joinedGridModelIsLoaded() { return null != this.joinedGridModel; }

    private void configureGridMenuItems()
    {
        if (null != this.manageGridMenuItem)
        {
            final org.wheatgenetics.coordinate.database.GridsTable gridsTable = this.gridsTable();
            if (null != gridsTable) this.manageGridMenuItem.setEnabled(gridsTable.exists());
        }

        if (null != this.exportGridMenuItem)
            this.exportGridMenuItem.setEnabled(this.joinedGridModelIsLoaded());
    }

    private org.wheatgenetics.coordinate.ti.MenuItemEnabler menuItemEnabler()
    {
        if (null == this.menuItemEnablerInstance) this.menuItemEnablerInstance =
            new org.wheatgenetics.coordinate.ti.MenuItemEnabler(this,
                org.wheatgenetics.coordinate.oldmain.OldMainActivity.CONFIGURE_NAVIGATION_DRAWER);
        return this.menuItemEnablerInstance;
    }

    private void configureTemplateMenuItems()
    {
        if (null != this.templateMenuItem)
        {
            final java.lang.StringBuilder stringBuilder = new java.lang.StringBuilder(
                this.getString(org.wheatgenetics.coordinate.R.string.template));
            {
                final java.lang.String templateTitle = this.getTemplateTitle();
                if (templateTitle.length() > 0) stringBuilder.append(": ").append(templateTitle);
            }
            this.templateMenuItem.setTitle(stringBuilder.toString());
        }

        if (null != this.importTemplateMenuItem)
            this.importTemplateMenuItem.setEnabled(this.menuItemEnabler().shouldBeEnabled());

        final boolean userDefinedTemplatesExist = this.templatesTable().exists(
            org.wheatgenetics.coordinate.model.TemplateType.USERDEFINED);

        if (null != this.exportTemplateMenuItem)
            this.exportTemplateMenuItem.setEnabled(userDefinedTemplatesExist);

        if (null != this.deleteTemplateMenuItem)
            this.deleteTemplateMenuItem.setEnabled(userDefinedTemplatesExist);
    }

    private boolean projectModelIsLoaded() { return null != this.projectModel; }

    private void configureProjectMenuItems()
    {
        if (null != this.projectMenuItem)
        {
            final java.lang.StringBuilder stringBuilder = new java.lang.StringBuilder(
                this.getString(org.wheatgenetics.coordinate.R.string.project));
            if (this.projectModelIsLoaded())
            {
                final java.lang.String projectTitle = this.projectModel.getTitle();
                if (projectTitle.length() > 0) stringBuilder.append(": ").append(projectTitle);
            }
            this.projectMenuItem.setTitle(stringBuilder.toString());
        }

        final boolean projectsExists = this.projectsTable().exists();
        if (null != this.manageProjectMenuItem)
            this.manageProjectMenuItem.setEnabled(projectsExists);

        if (null != this.exportProjectMenuItem)
            if (projectsExists)
            {
                final org.wheatgenetics.coordinate.database.GridsTable gridsTable =
                    this.gridsTable();
                // noinspection SimplifiableConditionalExpression
                this.exportProjectMenuItem.setEnabled(
                    null == gridsTable ? false : gridsTable.existsInProject());
            }
            else this.exportProjectMenuItem.setEnabled(false);
    }

    // region configureNavHeaderMain() configureNavigationDrawer() Private Methods
    private void setTextViewText(@androidx.annotation.IdRes final int textViewId,
    final java.lang.String text)
    {
        final android.widget.TextView textView = this.findViewById(textViewId);
        if (null != textView) textView.setText(text);
    }

    private void setPersonTextViewText(final java.lang.String person)
    {
        this.setTextViewText(
            org.wheatgenetics.coordinate.R.id.personTextView,           // From nav_header_main.xml.
            person                                          );
        this.setTextViewText(
            org.wheatgenetics.coordinate.R.id.sw600dpPersonTextView,    // From nav_header_main.xml.
            person                                                 );
    }

    private void configureNavHeaderMain()
    {
        this.setPersonTextViewText(
            this.joinedGridModelIsLoaded() ? this.joinedGridModel.getPerson() : "");
    }
    // endregion

    private void configureNavigationDrawer()
    {
        this.configureGridMenuItems   (); this.configureTemplateMenuItems();
        this.configureProjectMenuItems(); this.configureNavHeaderMain    ();

        // hide keyboard
        final android.view.View view = this.getCurrentFocus();
        if (null != view)
        {
            final android.view.inputmethod.InputMethodManager imm =
                (android.view.inputmethod.InputMethodManager) this.getSystemService(
                    org.wheatgenetics.coordinate.oldmain.OldMainActivity.INPUT_METHOD_SERVICE);
            if (null != imm) imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }
    // endregion

    private void closeDrawer()
    {
        if (null != this.drawerLayout)
            this.drawerLayout.closeDrawer(androidx.core.view.GravityCompat.START);
    }

    private void populateFragments()
    {
        if (null != this.gridDisplayFragment) this.gridDisplayFragment.populate();
        if (null != this.dataEntryFragment  ) this.dataEntryFragment.populate  ();
    }

    // region loadJoinedGridModel() Private Methods
    private void loadJoinedGridModel(@androidx.annotation.IntRange(from = 0) final long gridId)
    {
        if (org.wheatgenetics.coordinate.model.Model.illegal(gridId))
            this.joinedGridModel = null;
        else
        {
            final org.wheatgenetics.coordinate.database.GridsTable gridsTable = this.gridsTable();
            this.joinedGridModel = null == gridsTable ? null : gridsTable.get(gridId);
        }

        if (null != this.sharedPreferences)
            if (this.joinedGridModelIsLoaded())
                this.sharedPreferences.setGridId(this.joinedGridModel.getId());
            else
                this.sharedPreferences.clearGridId();
    }

    private void loadJoinedGridModelThenPopulate(
    @androidx.annotation.IntRange(from = 0) final long gridId)
    { this.loadJoinedGridModel(gridId); this.populateFragments(); }

    private void clearJoinedGridModelThenPopulate()
    { this.loadJoinedGridModelThenPopulate(0); }
    // endregion

    // region Grid Private Methods
    private void createGrid()
    {
        if (null == this.gridCreator)
            this.gridCreator = new org.wheatgenetics.coordinate.gc.GridCreator(
                this, org.wheatgenetics.coordinate.Types.CREATE_GRID,this);
        this.gridCreator.create(this.projectModel);
    }

    private long getGridId()
    { return this.joinedGridModelIsLoaded() ? this.joinedGridModel.getId() : 0; }

    private void respondToDeletedGrid()
    { this.clearJoinedGridModelThenPopulate(); this.createGrid(); }

    // region Export Grid Private Methods
    private org.wheatgenetics.coordinate.ge.GridExporter gridExporter()
    {
        if (null == this.gridExporterInstance) this.gridExporterInstance =
            new org.wheatgenetics.coordinate.ge.GridExporter(this,
                org.wheatgenetics.coordinate.oldmain.OldMainActivity.EXPORT_GRID_REQUEST_CODE,
                new org.wheatgenetics.coordinate.deleter.GridDeleter.Handler()
                {
                    @java.lang.Override public void respondToDeletedGrid()
                    {
                        org.wheatgenetics.coordinate.oldmain.OldMainActivity
                            .this.respondToDeletedGrid();
                    }
                });
        return this.gridExporterInstance;
    }

    private void exportGrid(@androidx.annotation.IntRange(from = 1) final long gridId,
    final java.lang.String fileName) { this.gridExporter().export(gridId, fileName); }
    // endregion
    // endregion

    // region Template Private Methods
    // region Import Template Private Methods
    private org.wheatgenetics.coordinate.ti.TemplateImporter templateImporter()
    {
        if (null == this.templateImporterInstance) this.templateImporterInstance =
            new org.wheatgenetics.coordinate.ti.TemplateImporter(this,
                org.wheatgenetics.coordinate.oldmain.OldMainActivity.IMPORT_TEMPLATE);
        return this.templateImporterInstance;
    }

    private void importTemplate(final java.lang.String fileName)
    { this.templateImporter().importTemplate(fileName); }
    // endregion

    // region Export Template Private Methods
    private org.wheatgenetics.coordinate.te.TemplateExporter templateExporter()
    {
        if (null == this.templateExporterInstance) this.templateExporterInstance =
            new org.wheatgenetics.coordinate.te.TemplateExporter(this,
                org.wheatgenetics.coordinate.oldmain.OldMainActivity.EXPORT_TEMPLATE);
        return this.templateExporterInstance;
    }

    private void exportTemplate(@androidx.annotation.IntRange(from = 1) final long templateId,
    final java.lang.String fileName) { this.templateExporter().export(templateId, fileName); }
    // endregion

    private void handleGridDeleted()
    {
        if (this.joinedGridModelIsLoaded())
        {
            final org.wheatgenetics.coordinate.database.GridsTable gridsTable = this.gridsTable();
            if (null != gridsTable) if (!gridsTable.exists(this.joinedGridModel.getId()))
                this.clearJoinedGridModelThenPopulate();
        }
    }
    // endregion

    // region Project Private Methods
    @androidx.annotation.IntRange(from = 0) private long getProjectId()
    { return this.projectModelIsLoaded() ? this.projectModel.getId() : 0; }

    private void handleProjectDeleted(@androidx.annotation.IntRange(from = 1) final long projectId)
    {
        if (this.projectModelIsLoaded()) if (!this.projectsTable().exists(projectId))
            this.clearProjectModel();
        this.handleGridDeleted();
    }

    // region Export Project Private Methods
    private org.wheatgenetics.coordinate.pe.ProjectExporter projectExporter()
    {
        if (null == this.projectExporterInstance) this.projectExporterInstance =
            new org.wheatgenetics.coordinate.pe.ProjectExporter(this,
                org.wheatgenetics.coordinate.oldmain.OldMainActivity.EXPORT_PROJECT_REQUEST_CODE);
        return this.projectExporterInstance;
    }

    private void exportProject(@androidx.annotation.IntRange(from = 1) final long projectId,
    final java.lang.String directoryName)
    { this.projectExporter().export(projectId, directoryName); }
    // endregion
    // endregion

    private void showChangeLog()
    {
        if (null == this.changeLogAlertDialog)
            this.changeLogAlertDialog = new org.wheatgenetics.changelog.ChangeLogAlertDialog(
                /* activity               => */this,
                /* changeLogRawResourceId => */ org.wheatgenetics.coordinate.R.raw.changelog);
        this.changeLogAlertDialog.show();
    }

    private void configureNavigationView()
    {
        final android.view.Menu menu;
        {
            final com.google.android.material.navigation.NavigationView navigationView =
                this.findViewById(org.wheatgenetics.coordinate.R.id.nav_view);  // From layout/acti-
            if (null == navigationView)                                         //  vity_main.xml.
                menu = null;
            else
            {
                navigationView.setNavigationItemSelectedListener(
                    this.navigationItemSelectedListener =
                        new org.wheatgenetics.coordinate.nisl.NavigationItemSelectedListener(
                            /* activity                  => */this,
                            /* createTemplateRequestCode => */
                                org.wheatgenetics.coordinate.Types.CREATE_TEMPLATE,
                            /* importTemplateRequestCode => */ org.wheatgenetics.coordinate
                                .oldmain.OldMainActivity.PREPROCESS_TEMPLATE_IMPORT,
                            /* clickUniquenessRequestCode => */
                                org.wheatgenetics.coordinate.Types.UNIQUENESS_CLICKED,
                            /* versionName => */ this.versionName,
                            /* handler     => */ new org.wheatgenetics.coordinate
                                .nisl.NavigationItemSelectedListener.Handler()
                                {
                                    @java.lang.Override public void createGrid()
                                    {
                                        org.wheatgenetics.coordinate.oldmain.OldMainActivity
                                            .this.createGrid();
                                    }

                                    @java.lang.Override public boolean joinedGridModelIsLoaded()
                                    {
                                        return org.wheatgenetics.coordinate.oldmain.OldMainActivity
                                            .this.joinedGridModelIsLoaded();
                                    }

                                    @java.lang.Override public void loadGrid(
                                    @androidx.annotation.IntRange(from = 1) final long gridId)
                                    {
                                        org.wheatgenetics.coordinate.oldmain.OldMainActivity
                                            .this.loadJoinedGridModelThenPopulate(gridId);
                                    }

                                    @java.lang.Override public long getGridId()
                                    {
                                        return org.wheatgenetics.coordinate.oldmain.OldMainActivity
                                            .this.getGridId();
                                    }

                                    @java.lang.Override public void respondToDeleteGrid()
                                    {
                                        org.wheatgenetics.coordinate.oldmain.OldMainActivity
                                            .this.respondToDeletedGrid();
                                    }

                                    @java.lang.Override
                                    public void exportGrid(@androidx.annotation.IntRange(from = 1)
                                    final long gridId, final java.lang.String fileName)
                                    {
                                        org.wheatgenetics.coordinate.oldmain.OldMainActivity
                                            .this.exportGrid(gridId, fileName);
                                    }


                                    @java.lang.Override
                                    public void importTemplate(final java.lang.String fileName)
                                    {
                                        org.wheatgenetics.coordinate.oldmain.OldMainActivity
                                            .this.importTemplate(fileName);
                                    }

                                    @java.lang.Override public void exportTemplate(
                                    @androidx.annotation.IntRange(from = 1) final long   templateId,
                                                                    final java.lang.String fileName)
                                    {
                                        org.wheatgenetics.coordinate.oldmain.OldMainActivity
                                            .this.exportTemplate(templateId, fileName);
                                    }

                                    @java.lang.Override public void handleGridDeleted()
                                    {
                                        org.wheatgenetics.coordinate.oldmain.OldMainActivity
                                            .this.handleGridDeleted();
                                    }


                                    @java.lang.Override public long getProjectId()
                                    {
                                        return org.wheatgenetics.coordinate.oldmain.OldMainActivity
                                            .this.getProjectId();
                                    }

                                    @java.lang.Override public void loadProject(
                                    @androidx.annotation.IntRange(from = 1) final long projectId)
                                    {
                                        org.wheatgenetics.coordinate.oldmain.OldMainActivity
                                            .this.loadProjectModel(projectId);
                                    }

                                    @java.lang.Override public void clearProject()
                                    {
                                        org.wheatgenetics.coordinate.oldmain.OldMainActivity
                                            .this.clearProjectModel();
                                    }

                                    @java.lang.Override public void handleProjectDeleted(
                                    @androidx.annotation.IntRange(from = 1) final long projectId)
                                    {
                                        org.wheatgenetics.coordinate.oldmain.OldMainActivity
                                            .this.handleProjectDeleted(projectId);
                                    }

                                    @java.lang.Override public boolean projectModelIsLoaded()
                                    {
                                        return org.wheatgenetics.coordinate.oldmain.OldMainActivity
                                            .this.projectModelIsLoaded();
                                    }

                                    @java.lang.Override public void exportProject(
                                    @androidx.annotation.IntRange(from = 1) final long projectId,
                                    final java.lang.String directoryName)
                                    {
                                        org.wheatgenetics.coordinate.oldmain.OldMainActivity
                                            .this.exportProject(projectId, directoryName);
                                    }


                                    @java.lang.Override public void closeDrawer()
                                    {
                                        org.wheatgenetics.coordinate.oldmain.OldMainActivity
                                            .this.closeDrawer();
                                    }
                                },
                            /* versionOnClickListener => */ new android.view.View.OnClickListener()
                                {
                                    @java.lang.Override
                                    public void onClick(final android.view.View v)
                                    {
                                        org.wheatgenetics.coordinate.oldmain.OldMainActivity
                                            .this.showChangeLog();
                                    }
                                }));
                menu = navigationView.getMenu();
            }
        }

        if (null != menu)
        {
            this.manageGridMenuItem =
                menu.findItem(org.wheatgenetics.coordinate.R.id.nav_manage_grid);
            this.exportGridMenuItem =
                menu.findItem(org.wheatgenetics.coordinate.R.id.nav_export_grid);

            this.templateMenuItem = menu.findItem(org.wheatgenetics.coordinate.R.id.nav_template);
            this.importTemplateMenuItem =
                menu.findItem(org.wheatgenetics.coordinate.R.id.nav_import_template);
            this.deleteTemplateMenuItem =
                menu.findItem(org.wheatgenetics.coordinate.R.id.nav_delete_template);
            this.exportTemplateMenuItem =
                menu.findItem(org.wheatgenetics.coordinate.R.id.nav_export_template);

            this.projectMenuItem = menu.findItem(org.wheatgenetics.coordinate.R.id.nav_project);
            this.manageProjectMenuItem =
                menu.findItem(org.wheatgenetics.coordinate.R.id.nav_manage_project);
            this.exportProjectMenuItem =
                menu.findItem(org.wheatgenetics.coordinate.R.id.nav_export_project);
        }
    }

    // region Default SharedPreferences Private Methods
    private org.wheatgenetics.coordinate.Utils.Advancement getAdvancement()
    { return org.wheatgenetics.coordinate.Utils.getAdvancement(this); }

    private boolean getSoundOn()
    { return org.wheatgenetics.coordinate.Utils.getSoundOn(this); }

    private boolean getUniqueness()
    { return org.wheatgenetics.coordinate.Utils.getUniqueness(this); }
    // endregion

    private void reloadIfNecessary()
    {
        if (org.wheatgenetics.coordinate.oldmain.Utils.gridsTableNeedsReloading(
        this.gridsTableInstance,this))
            this.gridsTableInstance = null;

        if (org.wheatgenetics.coordinate.oldmain.Utils.entriesTableNeedsReloading(
        this.entriesTableInstance,this))
            this.entriesTableInstance = null;

        if (org.wheatgenetics.coordinate.oldmain.Utils.joinedGridModelNeedsReloading(
        this.joinedGridModel,this))
            this.loadJoinedGridModelThenPopulate(this.joinedGridModel.getId());
    }

    private void goToNext(final org.wheatgenetics.coordinate.model.EntryModel entryModel)
    {
        final org.wheatgenetics.coordinate.database.GridsTable gridsTable = this.gridsTable();
        if (this.joinedGridModelIsLoaded() && null != gridsTable)
            if (this.joinedGridModel.goToNext(entryModel, this.getAdvancement(),this))
            {
                gridsTable.update(this.joinedGridModel);             // Update activeRow, activeCol.
                this.populateFragments();
            }
    }

    private void handleDuplicateCheckException(
    @androidx.annotation.NonNull final java.lang.String message)
    {
        if (this.getSoundOn())
        {
            if (null == this.disallowedDuplicateMediaPlayer)
                this.disallowedDuplicateMediaPlayer = android.media.MediaPlayer.create(
                    this, org.wheatgenetics.coordinate.R.raw.unsure);
            this.disallowedDuplicateMediaPlayer.start();
        }

        if (null == this.uniqueAlertDialog) this.uniqueAlertDialog =
            new org.wheatgenetics.coordinate.oldmain.UniqueAlertDialog(this);
        this.uniqueAlertDialog.show(message);
    }
    // endregion

    // region Overridden Methods
    @java.lang.Override protected void onCreate(
    @androidx.annotation.Nullable final android.os.Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.setContentView(org.wheatgenetics.coordinate.R.layout.activity_old_main);

        this.drawerLayout = this.findViewById(
            org.wheatgenetics.coordinate.R.id.drawer_layout);  // From layout/activity_old_main.xml.

        // region Configure action bar.
        {
            final androidx.appcompat.widget.Toolbar toolbar = this.findViewById(
                org.wheatgenetics.coordinate.R.id.toolbar);         // From layout/app_bar_main.xml.
            this.setSupportActionBar(toolbar);

            {
                final androidx.appcompat.app.ActionBar supportActionBar =
                    this.getSupportActionBar();
                if (null != supportActionBar) supportActionBar.setTitle(null);
            }

            final androidx.appcompat.app.ActionBarDrawerToggle actionBarDrawerToggle =
                new androidx.appcompat.app.ActionBarDrawerToggle(
                    this, this.drawerLayout, toolbar,
                    org.wheatgenetics.coordinate.R.string.navigation_drawer_open ,
                    org.wheatgenetics.coordinate.R.string.navigation_drawer_close)
                    {
                        @java.lang.Override
                        public void onDrawerOpened(final android.view.View drawerView)
                        {
                            org.wheatgenetics.coordinate.oldmain.OldMainActivity
                                .this.configureNavigationDrawer();
                        }
                    };
            if (null != this.drawerLayout)
                this.drawerLayout.setDrawerListener(actionBarDrawerToggle);
            actionBarDrawerToggle.syncState();
        }
        // endregion

        // region Default Templates
        // Adds default templates to database if they aren't there already.  If they are there then
        // they are updated to their default values.
        {
            final org.wheatgenetics.coordinate.model.TemplateModels defaultTemplateModels =
                org.wheatgenetics.coordinate.model.TemplateModels.makeDefault();
            if (defaultTemplateModels.size() > 0)
            {
                final org.wheatgenetics.coordinate.database.TemplatesTable templatesTable =
                    this.templatesTable();
                for (final org.wheatgenetics.coordinate.model.TemplateModel defaultTemplateModel:
                defaultTemplateModels)
                {
                    final org.wheatgenetics.coordinate.model.TemplateType defaultTemplateType =
                        defaultTemplateModel.getType();
                    if (templatesTable.exists(defaultTemplateType))
                    {
                        {
                            final org.wheatgenetics.coordinate.model.TemplateModel
                                existingTemplateModel = templatesTable.get(defaultTemplateType);
                            if (null != existingTemplateModel)
                                defaultTemplateModel.setId(existingTemplateModel.getId());
                        }
                        templatesTable.update(defaultTemplateModel);
                    }
                    else templatesTable.insert(defaultTemplateModel);
                }
            }
        }
        // endregion

        // region Find fragments.
        {
            final androidx.fragment.app.FragmentManager fragmentManager =
                this.getSupportFragmentManager();
            this.gridDisplayFragment =
                (org.wheatgenetics.coordinate.griddisplay.GridDisplayFragment)
                fragmentManager.findFragmentById(
                    org.wheatgenetics.coordinate.R.id.gridDisplayFragment);
            this.dataEntryFragment = (org.wheatgenetics.coordinate.oldmain.DataEntryFragment)
                fragmentManager.findFragmentById(
                    org.wheatgenetics.coordinate.R.id.dataEntryFragment);
        }
        // endregion

        {
            // region Get version.
            int versionCode;
            try
            {
                final android.content.pm.PackageInfo packageInfo =
                    this.getPackageManager().getPackageInfo(   // throws android.content.pm.Package-
                        this.getPackageName(),0);           //  Manager.NameNotFoundException
                if (null == packageInfo)
                {
                    versionCode      = 0;
                    this.versionName = org.wheatgenetics.javalib.Utils.adjust(null);
                }
                else
                {
                    versionCode      = packageInfo.versionCode;
                    this.versionName = packageInfo.versionName;
                }
            }
            catch (final android.content.pm.PackageManager.NameNotFoundException e)
            {
                versionCode      = 0;
                this.versionName = org.wheatgenetics.javalib.Utils.adjust(null);
            }
            // endregion

            // region Configure navigation view.
            this.sharedPreferences = new org.wheatgenetics.sharedpreferences.SharedPreferences(
                this.getSharedPreferences("Settings", /* mode => */0));
            this.configureNavigationView();
            // endregion

            // region Load projectModel and joinedGridModel.
            if (this.sharedPreferences.projectIdIsSet())
                this.loadProjectModel(this.sharedPreferences.getProjectId());

            if (this.sharedPreferences.gridIdIsSet())
                this.loadJoinedGridModel(this.sharedPreferences.getGridId());
            else
                if (null == savedInstanceState) this.createGrid();
            // endregion

            // region Set version.
            if (!this.sharedPreferences.updateVersionIsSet(versionCode))
            {
                this.sharedPreferences.setUpdateVersion(versionCode);
                this.showChangeLog();
            }
            // endregion
        }
    }

    @java.lang.Override protected void onPostCreate(final android.os.Bundle savedInstanceState)
    { super.onPostCreate(savedInstanceState); this.closeDrawer(); }

    @java.lang.Override protected void onStart() { super.onStart(); this.populateFragments(); }

    @java.lang.Override public void onBackPressed()
    {
        if (null != this.drawerLayout
        &&  this.drawerLayout.isDrawerOpen(androidx.core.view.GravityCompat.START))
            this.closeDrawer();
        else
            super.onBackPressed();
    }

    @java.lang.Override public boolean onCreateOptionsMenu(final android.view.Menu menu)
    {
        new android.view.MenuInflater(this).inflate(
            org.wheatgenetics.androidlibrary.R.menu.camera_options_menu, menu);
        return true;
    }

    @java.lang.Override public boolean onOptionsItemSelected(
    @androidx.annotation.NonNull final android.view.MenuItem item)
    {
        // Handle action bar item clicks here.  The action bar will automatically handle clicks on
        // the Home/Up button, so long as you specify a parent activity in AndroidManifest.xml.
        final int itemId = item.getItemId();

        if (org.wheatgenetics.androidlibrary.R.id.cameraOptionsMenuItem == itemId)
        {
            if (null == this.barcodeScanner)
                this.barcodeScanner = new org.wheatgenetics.zxing.BarcodeScanner(this);
            this.barcodeScanner.scan(); return true;
        }
        else return super.onOptionsItemSelected(item);
    }

    @java.lang.Override protected void onActivityResult(final int requestCode,
    final int resultCode, final android.content.Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        final java.lang.String barcodeScannerResult =
            org.wheatgenetics.zxing.BarcodeScanner.parseActivityResult(
                requestCode, resultCode, data);
        if (null != barcodeScannerResult)
        {
            if (null != this.dataEntryFragment)
                this.dataEntryFragment.setEntry(barcodeScannerResult);
            this.saveEntry(barcodeScannerResult);
        }
        else
            if (android.app.Activity.RESULT_OK == resultCode && null != data)
                switch (requestCode)
                {
                    case org.wheatgenetics.coordinate.Types.CREATE_TEMPLATE:
                        if (null != this.navigationItemSelectedListener)
                            this.navigationItemSelectedListener.setExcludedCells(data.getExtras());
                        break;

                    case org.wheatgenetics.coordinate.Types.CREATE_GRID:
                        if (null != this.gridCreator)
                            this.gridCreator.setExcludedCells(data.getExtras());
                        break;

                    case org.wheatgenetics.coordinate.Types.UNIQUENESS_CLICKED:
                        {
                            final boolean uniquenessPreferenceWasClicked;
                            {
                                final android.os.Bundle bundle = data.getExtras();
                                // noinspection SimplifiableConditionalExpression
                                uniquenessPreferenceWasClicked = null == bundle ?
                                    false : bundle.getBoolean(
                                        org.wheatgenetics.coordinate.Types.UNIQUENESS_BUNDLE_KEY,
                                        false);
                            }
                            if (uniquenessPreferenceWasClicked) this.reloadIfNecessary();
                        }
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
                switch (requestCode)
                {
                    case org.wheatgenetics.coordinate.oldmain.OldMainActivity
                    .CONFIGURE_NAVIGATION_DRAWER: this.configureNavigationDrawer(); break;

                    case
                    org.wheatgenetics.coordinate.oldmain.OldMainActivity.PREPROCESS_TEMPLATE_IMPORT:
                        if (null != this.navigationItemSelectedListener)
                            this.navigationItemSelectedListener.preprocessTemplateImport();
                        break;

                    case org.wheatgenetics.coordinate.oldmain.OldMainActivity.IMPORT_TEMPLATE:
                        if (null != this.templateImporterInstance)
                            this.templateImporterInstance.importTemplate();
                        break;

                    case org.wheatgenetics.coordinate.oldmain.OldMainActivity.EXPORT_TEMPLATE:
                        if (null != this.templateExporterInstance)
                            this.templateExporterInstance.export();
                        break;

                    case
                    org.wheatgenetics.coordinate.oldmain.OldMainActivity.EXPORT_GRID_REQUEST_CODE:
                        if (null != this.gridExporterInstance) this.gridExporterInstance.export();
                        break;

                    case org.wheatgenetics.coordinate.oldmain.OldMainActivity
                    .EXPORT_PROJECT_REQUEST_CODE:
                        if (null != this.projectExporterInstance)
                            this.projectExporterInstance.export();
                        break;
                }
    }

    @java.lang.Override protected void onDestroy()
    {
        if (null != this.disallowedDuplicateMediaPlayer)
            this.disallowedDuplicateMediaPlayer.release();
        if (null != this.rowOrColumnEndMediaPlayer) this.rowOrColumnEndMediaPlayer.release();
        if (null != this.gridEndMediaPlayer       ) this.gridEndMediaPlayer.release       ();

        super.onDestroy();
    }

    // region org.wheatgenetics.coordinate.griddisplay.GridDisplayFragment.Handler Overridden Methods
    @java.lang.Override public org.wheatgenetics.coordinate.model.DisplayModel getDisplayModel()
    { return this.joinedGridModel; }

    @java.lang.Override
    public void toggle(final org.wheatgenetics.coordinate.model.ElementModel elementModel)
    {
        if (this.joinedGridModelIsLoaded())
        {
            final org.wheatgenetics.coordinate.database.EntriesTable entriesTable =
                this.entriesTable();
            if (null != entriesTable)
            {
                final org.wheatgenetics.coordinate.model.EntryModel entryModel =
                    (org.wheatgenetics.coordinate.model.EntryModel) elementModel;
                if (this.joinedGridModel instanceof
                org.wheatgenetics.coordinate.model.CurrentGridUniqueJoinedGridModel)
                {
                    final org.wheatgenetics.coordinate.model.CurrentGridUniqueJoinedGridModel
                        currentGridUniqueJoinedGridModel =
                            (org.wheatgenetics.coordinate.model.CurrentGridUniqueJoinedGridModel)
                                this.joinedGridModel;
                    try
                    {
                        currentGridUniqueJoinedGridModel.checkThenSetEntryModel(entryModel);//throws
                    }
                    catch (final
                    org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.CheckException e)
                    { return; }
                }
                else this.joinedGridModel.setEntryModel(entryModel);
                entriesTable.insertOrUpdate(entryModel);
                if (entryModel instanceof org.wheatgenetics.coordinate.model.ExcludedEntryModel)
                    if (this.joinedGridModel.getActiveEntryModel() == entryModel)
                        this.goToNext(entryModel);
            }
        }
    }

    @java.lang.Override public int getActiveRow()
    { return this.joinedGridModelIsLoaded() ? this.joinedGridModel.getActiveRow() : -1; }

    @java.lang.Override public int getActiveCol()
    { return this.joinedGridModelIsLoaded() ? this.joinedGridModel.getActiveCol() : -1; }

    @java.lang.Override public void activate(final int row, final int col)
    {
        final org.wheatgenetics.coordinate.database.GridsTable gridsTable = this.gridsTable();
        if (null != gridsTable)
        {
            if (this.joinedGridModelIsLoaded())
                if (this.joinedGridModel.setActiveRowAndActiveCol(row, col))
                    gridsTable.update(this.joinedGridModel);

            if (null != this.dataEntryFragment)
                this.dataEntryFragment.setEntry(this.getEntryValue());
        }
    }

    @java.lang.Override @androidx.annotation.Nullable
    public org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.Checker getChecker()
    {
        if (this.joinedGridModel instanceof
        org.wheatgenetics.coordinate.model.CurrentGridUniqueJoinedGridModel)
            return (org.wheatgenetics.coordinate.model.CurrentGridUniqueEntryModels)
                this.joinedGridModel.getEntryModels();
        else
            return null;
    }
    // endregion

    // region org.wheatgenetics.coordinate.model.EntryModels.FilledHandler Overridden Methods
    @java.lang.Override public void handleFilledGrid()
    {
        org.wheatgenetics.coordinate.Utils.alert(this,
            org.wheatgenetics.coordinate.R.string.MainActivityFilledGridAlertMessage);

        if (this.getSoundOn())
        {
            if (null == this.gridEndMediaPlayer)
                this.gridEndMediaPlayer = android.media.MediaPlayer.create(
                    this, org.wheatgenetics.coordinate.R.raw.plonk);
            this.gridEndMediaPlayer.start();
        }
    }

    @java.lang.Override public void handleFilledRowOrCol()
    {
        if (this.getSoundOn())
        {
            if (null == this.rowOrColumnEndMediaPlayer)
                this.rowOrColumnEndMediaPlayer = android.media.MediaPlayer.create(
                    this, org.wheatgenetics.coordinate.R.raw.row_or_column_end);
            this.rowOrColumnEndMediaPlayer.start();
        }
    }
    // endregion

    // region org.wheatgenetics.coordinate.oldmain.DataEntryFragment.Handler Overridden Methods
    @java.lang.Override public java.lang.String getEntryValue()
    {
        if (this.joinedGridModelIsLoaded())
        {
            final org.wheatgenetics.coordinate.model.EntryModel activeEntryModel =
                this.joinedGridModel.getActiveEntryModel();
            return null == activeEntryModel ? null : activeEntryModel.getValue();
        }
        else return null;
    }

    @java.lang.Override public java.lang.String getProjectTitle()
    {
        if (this.joinedGridModelIsLoaded())
        {
            final long projectId = this.joinedGridModel.getProjectId();
            if (org.wheatgenetics.coordinate.model.Model.illegal(projectId))
                return "none";
            else
            {
                final org.wheatgenetics.coordinate.model.ProjectModel projectModel =
                    this.projectsTable().get(projectId);
                return null == projectModel ? "none" : projectModel.getTitle();
            }
        }
        else return "";
    }

    @java.lang.Override public java.lang.String getTemplateTitle()
    { return this.joinedGridModelIsLoaded() ? this.joinedGridModel.getTemplateTitle() : ""; }

    @java.lang.Override
    public org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields getOptionalFields()
    { return this.joinedGridModelIsLoaded() ? this.joinedGridModel.optionalFields() : null; }

    @java.lang.Override public void saveEntry(final java.lang.String entryValue)
    {
        if (this.joinedGridModelIsLoaded())
        {
            final org.wheatgenetics.coordinate.model.EntryModel activeEntryModel =
                this.joinedGridModel.getActiveEntryModel();
            if (activeEntryModel instanceof org.wheatgenetics.coordinate.model.IncludedEntryModel)
            {
                final org.wheatgenetics.coordinate.database.EntriesTable entriesTable =
                    this.entriesTable();
                if (null != entriesTable)
                {
                    final org.wheatgenetics.coordinate.model.IncludedEntryModel
                        activeIncludedEntryModel =
                            (org.wheatgenetics.coordinate.model.IncludedEntryModel)
                                activeEntryModel;
                    if (this.getUniqueness())
                    {
                        final java.lang.String oldEntryValue = activeIncludedEntryModel.getValue();
                        final org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel
                            checkedIncludedEntryModel =
                                (org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel)
                                    activeIncludedEntryModel;
                        try
                        { checkedIncludedEntryModel.checkThenSetValue(entryValue) /* throws */; }
                        catch (final
                        org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.CheckException
                        e)
                        {
                            if (e instanceof org.wheatgenetics.coordinate.model
                            .UniqueEntryModels.DuplicateCheckException)
                            {
                                {
                                    final java.lang.String message = e.getMessage();
                                    this.handleDuplicateCheckException(
                                        null == message ? e.toString() : message);
                                }
                                if (null != this.dataEntryFragment)
                                    this.dataEntryFragment.setEntry(oldEntryValue);
                            }
                            return;
                        }
                    }
                    else activeIncludedEntryModel.setValue(entryValue);

                    entriesTable.insertOrUpdate(activeIncludedEntryModel);
                }
            }
            this.goToNext(activeEntryModel);
        }
    }

    @java.lang.Override public void clearEntry() { this.saveEntry(null); }
    // endregion

    // region org.wheatgenetics.coordinate.gc.GridCreator.Handler Overridden Methods
    @java.lang.Override public void handleGridCreated(
    @androidx.annotation.IntRange(from = 1) final long gridId)
    { this.loadJoinedGridModelThenPopulate(gridId); }

    @java.lang.Override public void loadProjectModel(
    @androidx.annotation.IntRange(from = 1) final long projectId)
    {
        this.projectModel = org.wheatgenetics.coordinate.model.Model.illegal(projectId) ?
            null : this.projectsTable().get(projectId);

        if (null != this.sharedPreferences)
            if (this.projectModelIsLoaded())
                this.sharedPreferences.setProjectId(this.projectModel.getId());
            else
                this.sharedPreferences.clearProjectId();
    }

    @android.annotation.SuppressLint({"Range"}) @java.lang.Override public void clearProjectModel()
    { this.loadProjectModel(0); }
    // endregion
    // endregion
}
