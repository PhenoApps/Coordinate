package org.wheatgenetics.coordinate.main;

/**
 * Uses:
 * android.annotation.SuppressLint
 * android.app.Activity
 * android.content.Intent
 * android.content.pm.PackageManager
 * android.Manifest.permission
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
 * androidx.annotation.RestrictTo
 * androidx.annotation.RestrictTo.Scope
 * androidx.appcompat.app.ActionBarDrawerToggle
 * androidx.appcompat.widget.Toolbar
 * androidx.core.view.GravityCompat
 * androidx.drawerlayout.widget.DrawerLayout
 *
 * com.google.android.material.navigation.NavigationView
 *
 * org.wheatgenetics.androidlibrary.R
 *
 * org.wheatgenetics.sharedpreferences.SharedPreferences
 *
 * org.wheatgenetics.coordinate.R
 * org.wheatgenetics.coordinate.Types
 *
 * org.wheatgenetics.coordinate.collector.DataEntryFragment.Handler
 * org.wheatgenetics.coordinate.collector.OldCollector
 *
 * org.wheatgenetics.coordinate.deleter.GridDeleter.Handler
 *
 * org.wheatgenetics.coordinate.gc.GridCreator
 * org.wheatgenetics.coordinate.gc.StatefulGridCreator
 * org.wheatgenetics.coordinate.gc.StatefulGridCreator.Handler
 *
 * org.wheatgenetics.coordinate.ge.GridExporter
 *
 * org.wheatgenetics.coordinate.griddisplay.GridDisplayFragment.Handler
 *
 * org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.Checker
 * org.wheatgenetics.coordinate.model.DisplayModel
 * org.wheatgenetics.coordinate.model.ElementModel
 * org.wheatgenetics.coordinate.model.ProjectModel
 * org.wheatgenetics.coordinate.model.TemplateType
 *
 * org.wheatgenetics.coordinate.nisl.NavigationItemSelectedListener
 * org.wheatgenetics.coordinate.nisl.NavigationItemSelectedListener.Handler
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
 * org.wheatgenetics.coordinate.main.BaseMainActivity
 */
public class OldMainActivity extends org.wheatgenetics.coordinate.main.BaseMainActivity implements
org.wheatgenetics.coordinate.griddisplay.GridDisplayFragment.Handler,
org.wheatgenetics.coordinate.collector.DataEntryFragment.Handler    ,
org.wheatgenetics.coordinate.gc.StatefulGridCreator.Handler
{
    private static final int CONFIGURE_NAVIGATION_DRAWER = 10, PREPROCESS_TEMPLATE_IMPORT = 20,
        IMPORT_TEMPLATE = 21, EXPORT_TEMPLATE = 22, EXPORT_GRID_REQUEST_CODE = 30,
        EXPORT_PROJECT_REQUEST_CODE = 31;

    // region Fields
    private androidx.drawerlayout.widget.DrawerLayout drawerLayout;
    private android.view.MenuItem manageGridMenuItem = null, exportGridMenuItem     = null;
    private android.view.MenuItem templateMenuItem   = null, importTemplateMenuItem = null,
        exportTemplateMenuItem = null, deleteTemplateMenuItem = null;
    private android.view.MenuItem projectMenuItem = null, manageProjectMenuItem = null,
        exportProjectMenuItem = null;

    private org.wheatgenetics.coordinate.ti.MenuItemEnabler menuItemEnablerInstance = null;    // ll

    private org.wheatgenetics.coordinate.collector.OldCollector       collectorInstance = null;// ll
    private org.wheatgenetics.coordinate.nisl.NavigationItemSelectedListener
        navigationItemSelectedListener;
    private org.wheatgenetics.coordinate.model.ProjectModel     projectModel = null;
    private org.wheatgenetics.coordinate.gc.StatefulGridCreator
        statefulGridCreatorInstance = null;                                             // lazy load
    private org.wheatgenetics.coordinate.ge.GridExporter     gridExporterInstance     = null;  // ll
    private org.wheatgenetics.coordinate.ti.TemplateImporter templateImporterInstance = null;  // ll
    private org.wheatgenetics.coordinate.te.TemplateExporter templateExporterInstance = null;  // ll
    private org.wheatgenetics.coordinate.pe.ProjectExporter  projectExporterInstance  = null;  // ll
    // endregion

    // region Private Methods
    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.collector.OldCollector collector()
    {
        if (null == this.collectorInstance) this.collectorInstance =
            new org.wheatgenetics.coordinate.collector.OldCollector(
                this, this.sharedPreferences());
        return this.collectorInstance;
    }

    private boolean joinedGridModelIsLoaded() { return this.collector().joinedGridModelIsLoaded(); }

    // region configureNavigationDrawer() Private Methods
    // region configureGridMenuItems() configureNavigationDrawer() Private Method
    private void configureGridMenuItems()
    {
        if (null != this.manageGridMenuItem) this.manageGridMenuItem.setEnabled(
            this.collector().manageGridMenuItemShouldBeEnabled());

        if (null != this.exportGridMenuItem)
            this.exportGridMenuItem.setEnabled(this.joinedGridModelIsLoaded());
    }
    // endregion

    // region configureTemplateMenuItems() configureNavigationDrawer() Private Methods
    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.ti.MenuItemEnabler menuItemEnabler()
    {
        if (null == this.menuItemEnablerInstance) this.menuItemEnablerInstance =
            new org.wheatgenetics.coordinate.ti.MenuItemEnabler(this,
                org.wheatgenetics.coordinate.main.OldMainActivity.CONFIGURE_NAVIGATION_DRAWER);
        return this.menuItemEnablerInstance;
    }

    private void configureTemplateMenuItems()
    {
        if (null != this.templateMenuItem)
        {
            final java.lang.StringBuilder stringBuilder = new java.lang.StringBuilder(
                this.getString(org.wheatgenetics.coordinate.R.string.template));
            {
                final java.lang.String templateTitle = this.collector().getTemplateTitle();
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
    // endregion

    // region configureProjectMenuItems() configureNavigationDrawer() Private Methods
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

        if (null != this.manageProjectMenuItem) this.manageProjectMenuItem.setEnabled(
            this.collector().manageProjectMenuItemShouldBeEnabled());

        if (null != this.exportProjectMenuItem) this.exportProjectMenuItem.setEnabled(
            this.collector().exportProjectMenuItemShouldBeEnabled());
    }
    // endregion

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
    { this.setPersonTextViewText(this.collector().getPerson()); }
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
                    org.wheatgenetics.coordinate.main.OldMainActivity.INPUT_METHOD_SERVICE);
            if (null != imm) imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }
    // endregion

    private void closeDrawer()
    {
        if (null != this.drawerLayout)
            this.drawerLayout.closeDrawer(androidx.core.view.GravityCompat.START);
    }

    // region Grid Private Methods
    private void createGrid()
    {
        ((org.wheatgenetics.coordinate.gc.StatefulGridCreator) this.gridCreator()).create(
            this.projectModel);
    }

    private void loadJoinedGridModelThenPopulate(@androidx.annotation.IntRange(from = 0)
    final long gridId) { this.collector().loadJoinedGridModelThenPopulate(gridId); }

    private long getGridId() { return this.collector().getGridId(); }

    private void respondToDeletedGrid()
    { this.collector().clearJoinedGridModelThenPopulate(); this.createGrid(); }

    // region Export Grid Private Methods
    @androidx.annotation.NonNull private org.wheatgenetics.coordinate.ge.GridExporter gridExporter()
    {
        if (null == this.gridExporterInstance) this.gridExporterInstance =
            new org.wheatgenetics.coordinate.ge.GridExporter(this,
                org.wheatgenetics.coordinate.main.OldMainActivity.EXPORT_GRID_REQUEST_CODE,
                new org.wheatgenetics.coordinate.deleter.GridDeleter.Handler()
                {
                    @java.lang.Override public void respondToDeletedGrid()
                    {
                        org.wheatgenetics.coordinate.main.OldMainActivity
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
    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.ti.TemplateImporter templateImporter()
    {
        if (null == this.templateImporterInstance) this.templateImporterInstance =
            new org.wheatgenetics.coordinate.ti.TemplateImporter(this,
                org.wheatgenetics.coordinate.main.OldMainActivity.IMPORT_TEMPLATE);
        return this.templateImporterInstance;
    }

    private void importTemplate(final java.lang.String fileName)
    { this.templateImporter().importTemplate(fileName); }
    // endregion

    // region Export Template Private Methods
    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.te.TemplateExporter templateExporter()
    {
        if (null == this.templateExporterInstance) this.templateExporterInstance =
            new org.wheatgenetics.coordinate.te.TemplateExporter(this,
                org.wheatgenetics.coordinate.main.OldMainActivity.EXPORT_TEMPLATE);
        return this.templateExporterInstance;
    }

    private void exportTemplate(@androidx.annotation.IntRange(from = 1) final long templateId,
    final java.lang.String fileName) { this.templateExporter().export(templateId, fileName); }
    // endregion

    private void handleGridDeleted() { this.collector().handleGridDeleted(); }
    // endregion

    // region Project Private Methods
    @androidx.annotation.IntRange(from = 0) private long getProjectId()
    { return this.projectModelIsLoaded() ? this.projectModel.getId() : 0; }

    private void handleProjectDeleted(@androidx.annotation.IntRange(from = 1) final long projectId)
    {
        if (this.projectModelIsLoaded()) if (!this.collector().projectExists(projectId))
            this.clearProjectModel();
        this.handleGridDeleted();
    }

    // region Export Project Private Methods
    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.pe.ProjectExporter projectExporter()
    {
        if (null == this.projectExporterInstance) this.projectExporterInstance =
            new org.wheatgenetics.coordinate.pe.ProjectExporter(this,
                org.wheatgenetics.coordinate.main.OldMainActivity.EXPORT_PROJECT_REQUEST_CODE);
        return this.projectExporterInstance;
    }

    private void exportProject(@androidx.annotation.IntRange(from = 1) final long projectId,
    final java.lang.String directoryName)
    { this.projectExporter().export(projectId, directoryName); }
    // endregion
    // endregion

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
                                .main.OldMainActivity.PREPROCESS_TEMPLATE_IMPORT,
                            /* clickUniquenessRequestCode => */
                                org.wheatgenetics.coordinate.Types.UNIQUENESS_CLICKED,
                            /* versionName => */ this.versionName(),
                            /* handler     => */ new org.wheatgenetics.coordinate
                                .nisl.NavigationItemSelectedListener.Handler()
                                {
                                    @java.lang.Override public void createGrid()
                                    {
                                        org.wheatgenetics.coordinate.main.OldMainActivity
                                            .this.createGrid();
                                    }

                                    @java.lang.Override public boolean joinedGridModelIsLoaded()
                                    {
                                        return org.wheatgenetics.coordinate.main.OldMainActivity
                                            .this.joinedGridModelIsLoaded();
                                    }

                                    @java.lang.Override public void loadGrid(
                                    @androidx.annotation.IntRange(from = 1) final long gridId)
                                    {
                                        org.wheatgenetics.coordinate.main.OldMainActivity
                                            .this.loadJoinedGridModelThenPopulate(gridId);
                                    }

                                    @java.lang.Override public long getGridId()
                                    {
                                        return org.wheatgenetics.coordinate.main.OldMainActivity
                                            .this.getGridId();
                                    }

                                    @java.lang.Override public void respondToDeleteGrid()
                                    {
                                        org.wheatgenetics.coordinate.main.OldMainActivity
                                            .this.respondToDeletedGrid();
                                    }

                                    @java.lang.Override public void exportGrid(
                                    @androidx.annotation.IntRange(from = 1) final long     gridId  ,
                                                                    final java.lang.String fileName)
                                    {
                                        org.wheatgenetics.coordinate.main.OldMainActivity
                                            .this.exportGrid(gridId, fileName);
                                    }


                                    @java.lang.Override
                                    public void importTemplate(final java.lang.String fileName)
                                    {
                                        org.wheatgenetics.coordinate.main.OldMainActivity
                                            .this.importTemplate(fileName);
                                    }

                                    @java.lang.Override public void exportTemplate(
                                    @androidx.annotation.IntRange(from = 1) final long   templateId,
                                                                  final java.lang.String fileName  )
                                    {
                                        org.wheatgenetics.coordinate.main.OldMainActivity
                                            .this.exportTemplate(templateId, fileName);
                                    }

                                    @java.lang.Override public void handleGridDeleted()
                                    {
                                        org.wheatgenetics.coordinate.main.OldMainActivity
                                            .this.handleGridDeleted();
                                    }


                                    @java.lang.Override public long getProjectId()
                                    {
                                        return org.wheatgenetics.coordinate.main.OldMainActivity
                                            .this.getProjectId();
                                    }

                                    @java.lang.Override public void loadProject(
                                    @androidx.annotation.IntRange(from = 1) final long projectId)
                                    {
                                        org.wheatgenetics.coordinate.main.OldMainActivity
                                            .this.loadProjectModel(projectId);
                                    }

                                    @java.lang.Override public void clearProject()
                                    {
                                        org.wheatgenetics.coordinate.main.OldMainActivity
                                            .this.clearProjectModel();
                                    }

                                    @java.lang.Override public void handleProjectDeleted(
                                    @androidx.annotation.IntRange(from = 1) final long projectId)
                                    {
                                        org.wheatgenetics.coordinate.main.OldMainActivity
                                            .this.handleProjectDeleted(projectId);
                                    }

                                    @java.lang.Override public boolean projectModelIsLoaded()
                                    {
                                        return org.wheatgenetics.coordinate.main.OldMainActivity
                                            .this.projectModelIsLoaded();
                                    }

                                    @java.lang.Override public void exportProject(
                                    @androidx.annotation.IntRange(from = 1) final long projectId   ,
                                                               final java.lang.String directoryName)
                                    {
                                        org.wheatgenetics.coordinate.main.OldMainActivity
                                            .this.exportProject(projectId, directoryName);
                                    }


                                    @java.lang.Override public void closeDrawer()
                                    {
                                        org.wheatgenetics.coordinate.main.OldMainActivity
                                            .this.closeDrawer();
                                    }
                                },
                            /* versionOnClickListener => */ new android.view.View.OnClickListener()
                                {
                                    @java.lang.Override
                                    public void onClick(final android.view.View v)
                                    {
                                        org.wheatgenetics.coordinate.main.OldMainActivity
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

            final androidx.appcompat.app.ActionBarDrawerToggle actionBarDrawerToggle =
                new androidx.appcompat.app.ActionBarDrawerToggle(
                    this, this.drawerLayout, toolbar,
                    org.wheatgenetics.coordinate.R.string.navigation_drawer_open ,
                    org.wheatgenetics.coordinate.R.string.navigation_drawer_close)
                    {
                        @java.lang.Override
                        public void onDrawerOpened(final android.view.View drawerView)
                        {
                            org.wheatgenetics.coordinate.main.OldMainActivity
                                .this.configureNavigationDrawer();
                        }
                    };
            if (null != this.drawerLayout)
                this.drawerLayout.setDrawerListener(actionBarDrawerToggle);
            actionBarDrawerToggle.syncState();
        }
        // endregion

        this.configureNavigationView();

        // region Load projectModel and joinedGridModel.
        @androidx.annotation.NonNull
        final org.wheatgenetics.sharedpreferences.SharedPreferences sharedPreferences =
            this.sharedPreferences();
        if (sharedPreferences.projectIdIsSet())
            this.loadProjectModel(sharedPreferences.getProjectId());
        if (sharedPreferences.gridIdIsSet())
            this.collector().loadJoinedGridModel(sharedPreferences.getGridId());
        else
            if (null == savedInstanceState) this.createGrid();
        // endregion
    }

    @java.lang.Override protected void onPostCreate(final android.os.Bundle savedInstanceState)
    { super.onPostCreate(savedInstanceState); this.closeDrawer(); }

    @java.lang.Override protected void onStart()
    { super.onStart(); this.collector().populateFragments(); }

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
            return this.collector().scanBarcode();
        else
            return super.onOptionsItemSelected(item);
    }

    @java.lang.Override protected void onActivityResult(final int requestCode,
    final int resultCode, final android.content.Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (!this.collector().parseActivityResult(requestCode, resultCode, data))
            if (android.app.Activity.RESULT_OK == resultCode && null != data)
                switch (requestCode)
                {
                    case org.wheatgenetics.coordinate.Types.CREATE_TEMPLATE:
                        if (null != this.navigationItemSelectedListener)
                            this.navigationItemSelectedListener.continueExcluding(data.getExtras());
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
                            if (uniquenessPreferenceWasClicked)
                                this.collector().reloadIfNecessary();
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
                    case
                    org.wheatgenetics.coordinate.main.OldMainActivity.CONFIGURE_NAVIGATION_DRAWER:
                        this.configureNavigationDrawer(); break;

                    case
                    org.wheatgenetics.coordinate.main.OldMainActivity.PREPROCESS_TEMPLATE_IMPORT:
                        if (null != this.navigationItemSelectedListener)
                            this.navigationItemSelectedListener.preprocessTemplateImport();
                        break;

                    case org.wheatgenetics.coordinate.main.OldMainActivity.IMPORT_TEMPLATE:
                        if (null != this.templateImporterInstance)
                            this.templateImporterInstance.importTemplate();
                        break;

                    case org.wheatgenetics.coordinate.main.OldMainActivity.EXPORT_TEMPLATE:
                        if (null != this.templateExporterInstance)
                            this.templateExporterInstance.export();
                        break;

                    case org.wheatgenetics.coordinate.main.OldMainActivity.EXPORT_GRID_REQUEST_CODE:
                        if (null != this.gridExporterInstance) this.gridExporterInstance.export();
                        break;

                    case
                    org.wheatgenetics.coordinate.main.OldMainActivity.EXPORT_PROJECT_REQUEST_CODE:
                        if (null != this.projectExporterInstance)
                            this.projectExporterInstance.export();
                        break;
                }
    }

    @java.lang.Override protected void onDestroy()
    {
        if (null != this.collectorInstance) this.collectorInstance.release();
        super.onDestroy();
    }

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @java.lang.Override @androidx.annotation.NonNull
    org.wheatgenetics.coordinate.gc.GridCreator gridCreator()
    {
        if (null == this.statefulGridCreatorInstance) this.statefulGridCreatorInstance =
            new org.wheatgenetics.coordinate.gc.StatefulGridCreator(
                this, org.wheatgenetics.coordinate.Types.CREATE_GRID,this);
        return this.statefulGridCreatorInstance;
    }

    // region org.wheatgenetics.coordinate.griddisplay.GridDisplayFragment.Handler Overridden Methods
    @java.lang.Override public org.wheatgenetics.coordinate.model.DisplayModel getDisplayModel()
    { return this.collector().getDisplayModel(); }

    @java.lang.Override public void toggle(@androidx.annotation.Nullable
    final org.wheatgenetics.coordinate.model.ElementModel elementModel)
    { this.collector().toggle(elementModel); }

    @java.lang.Override public int getActiveRow() { return this.collector().getActiveRow(); }
    @java.lang.Override public int getActiveCol() { return this.collector().getActiveCol(); }

    @java.lang.Override public void activate(final int row, final int col)
    { this.collector().activate(row, col); }

    @java.lang.Override @androidx.annotation.Nullable
    public org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.Checker getChecker()
    { return this.collector().getChecker(); }
    // endregion

    // region org.wheatgenetics.coordinate.collector.DataEntryFragment.Handler Overridden Methods
    @java.lang.Override public java.lang.String getEntryValue()
    { return this.collector().getEntryValue(); }

    @java.lang.Override public java.lang.String getProjectTitle()
    { return this.collector().getProjectTitle(); }

    @java.lang.Override public java.lang.String getTemplateTitle()
    { return this.collector().getTemplateTitle(); }

    @java.lang.Override
    public org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields getOptionalFields()
    { return this.collector().getOptionalFields(); }

    @java.lang.Override public void saveEntry(final java.lang.String entryValue)
    { this.collector().saveEntry(entryValue); }

    @java.lang.Override public void clearEntry() { this.collector().clearEntry(); }
    // endregion

    // region org.wheatgenetics.coordinate.gc.StatefulGridCreator.Handler Overridden Methods
    @java.lang.Override public void handleGridCreated(@androidx.annotation.IntRange(from = 1)
    final long gridId) { this.loadJoinedGridModelThenPopulate(gridId); }

    @java.lang.Override public void loadProjectModel(
    @androidx.annotation.IntRange(from = 1) final long projectId)
    {
        this.projectModel = this.collector().getProjectModel(projectId);

        @androidx.annotation.NonNull
        final org.wheatgenetics.sharedpreferences.SharedPreferences sharedPreferences =
            this.sharedPreferences();
        if (this.projectModelIsLoaded())
            sharedPreferences.setProjectId(this.projectModel.getId());
        else
            sharedPreferences.clearProjectId();
    }

    @android.annotation.SuppressLint({"Range"}) @java.lang.Override public void clearProjectModel()
    { this.loadProjectModel(0); }
    // endregion
    // endregion
}