package org.wheatgenetics.coordinate;

/**
 * Uses:
 * android.app.Activity
 * android.content.Intent
 * android.content.pm.PackageInfo
 * android.content.pm.PackageManager.NameNotFoundException
 * android.media.MediaPlayer
 * android.os.Bundle
 * android.support.design.widget.NavigationView
 * android.support.v4.app.FragmentManager
 * android.support.v4.view.GravityCompat
 * android.support.v4.widget.DrawerLayout
 * android.support.v7.app.ActionBar
 * android.support.v7.app.ActionBarDrawerToggle
 * android.support.v7.app.AppCompatActivity
 * android.support.v7.widget.Toolbar
 * android.view.Menu
 * android.view.MenuItem
 * android.view.MenuInflater
 * android.view.View
 * android.view.View.OnClickListener
 * android.widget.TextView
 *
 * org.wheatgenetics.javalib.Utils
 *
 * org.wheatgenetics.androidlibrary.Dir
 * org.wheatgenetics.androidlibrary.R
 * org.wheatgenetics.androidlibrary.Utils
 *
 * org.wheatgenetics.changelog.ChangeLogAlertDialog
 * org.wheatgenetics.sharedpreferences.SharedPreferences
 * org.wheatgenetics.zxing.BarcodeScanner
 *
 * org.wheatgenetics.coordinate.database.EntriesTable
 * org.wheatgenetics.coordinate.database.GridsTable
 * org.wheatgenetics.coordinate.database.TemplatesTable
 *
 * org.wheatgenetics.coordinate.display.GridDisplayFragment
 * org.wheatgenetics.coordinate.display.GridDisplayFragment.Handler
 *
 * org.wheatgenetics.coordinate.gc.GridCreator
 * org.wheatgenetics.coordinate.gc.GridCreator.Handler
 *
 * org.wheatgenetics.coordinate.model.DisplayModel
 * org.wheatgenetics.coordinate.model.ElementModel
 * org.wheatgenetics.coordinate.model.EntryModel
 * org.wheatgenetics.coordinate.model.EntryModels.FilledHandler
 * org.wheatgenetics.coordinate.model.ExcludedEntryModel
 * org.wheatgenetics.coordinate.model.Exporter.Helper
 * org.wheatgenetics.coordinate.model.GridExporter
 * org.wheatgenetics.coordinate.model.GridExporter.Helper
 * org.wheatgenetics.coordinate.model.IncludedEntryModel
 * org.wheatgenetics.coordinate.model.JoinedGridModel
 * org.wheatgenetics.coordinate.model.TemplateExporter
 * org.wheatgenetics.coordinate.model.TemplateModel
 * org.wheatgenetics.coordinate.model.TemplateModels
 * org.wheatgenetics.coordinate.model.TemplateType
 *
 * org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields
 *
 * org.wheatgenetics.coordinate.DataEntryFragment
 * org.wheatgenetics.coordinate.DataEntryFragment.Handler
 * org.wheatgenetics.coordinate.NavigationItemSelectedListener
 * org.wheatgenetics.coordinate.NavigationItemSelectedListener.Handler
 * org.wheatgenetics.coordinate.R
 * org.wheatgenetics.coordinate.Utils
 */
public class MainActivity extends android.support.v7.app.AppCompatActivity implements
org.wheatgenetics.coordinate.display.GridDisplayFragment.Handler,
org.wheatgenetics.coordinate.model.EntryModels.FilledHandler    ,
org.wheatgenetics.coordinate.DataEntryFragment.Handler          ,
org.wheatgenetics.coordinate.gc.GridCreator.Handler             ,
org.wheatgenetics.coordinate.model.GridExporter.Helper
{
    private final static int NAVIGATION_ITEM_SELECTED_LISTENER = 10, GRID_CREATOR = 20;

    // region Fields
    private android.support.v4.widget.DrawerLayout drawerLayout;
    private android.view.MenuItem loadGridMenuItem, deleteGridMenuItem, deleteTemplateMenuItem,
        exportTemplateMenuItem, exportGridMenuItem, turnSoundOnMenuItem, turnSoundOffMenuItem;
    private android.media.MediaPlayer gridEndMediaPlayer = null, columnEndMediaPlayer = null;

    private org.wheatgenetics.sharedpreferences.SharedPreferences sharedPreferences          ;
    private org.wheatgenetics.androidlibrary.Dir                  exportDir, templatesDir    ;
    private org.wheatgenetics.changelog.ChangeLogAlertDialog      changeLogAlertDialog = null;
    private org.wheatgenetics.zxing.BarcodeScanner                barcodeScanner       = null;

    // region Table Fields
    private org.wheatgenetics.coordinate.database.TemplatesTable templatesTableInstance = null;
    private org.wheatgenetics.coordinate.database.GridsTable     gridsTableInstance     = null;
    private org.wheatgenetics.coordinate.database.EntriesTable   entriesTableInstance   = null;
    // endregion

    private org.wheatgenetics.coordinate.NavigationItemSelectedListener
        navigationItemSelectedListener;
    private org.wheatgenetics.coordinate.model.JoinedGridModel  joinedGridModel  = null;
    private org.wheatgenetics.coordinate.gc.GridCreator         gridCreator      = null;
    private org.wheatgenetics.coordinate.model.GridExporter     gridExporter     = null;
    private org.wheatgenetics.coordinate.model.TemplateExporter templateExporter = null;

    private org.wheatgenetics.coordinate.display.GridDisplayFragment gridDisplayFragment;
    private org.wheatgenetics.coordinate.DataEntryFragment           dataEntryFragment  ;
    // endregion

    // region Private Methods
    // region Table Private Methods
    private org.wheatgenetics.coordinate.database.TemplatesTable templatesTable()
    {
        if (null == this.templatesTableInstance) this.templatesTableInstance =
            new org.wheatgenetics.coordinate.database.TemplatesTable(this);
        return this.templatesTableInstance;
    }

    private org.wheatgenetics.coordinate.database.GridsTable gridsTable()
    {
        if (null == this.gridsTableInstance) this.gridsTableInstance =
            new org.wheatgenetics.coordinate.database.GridsTable(this);
        return this.gridsTableInstance;
    }

    private org.wheatgenetics.coordinate.database.EntriesTable entriesTable()
    {
        if (null == this.entriesTableInstance) this.entriesTableInstance =
            new org.wheatgenetics.coordinate.database.EntriesTable(this);
        return this.entriesTableInstance;
    }
    // endregion

    // region Toast Private Methods
    // region Long Toast Private Methods
    private void showLongToast(final java.lang.String text)
    { org.wheatgenetics.androidlibrary.Utils.showLongToast(this, text); }

    private void showLongToast(final int text) { this.showLongToast(this.getString(text)); }
    // endregion

    // region Short Toast Private Methods
    private void showShortToast(final java.lang.String text)
    { org.wheatgenetics.androidlibrary.Utils.showShortToast(this, text); }

    private void showShortToast(final int text) { this.showShortToast(this.getString(text)); }
    // endregion
    // endregion

    // region configureNavigationDrawer() Private Methods
    private void setTextViewText(final int textViewId, final java.lang.String text)
    {
        final android.widget.TextView textView =
            (android.widget.TextView) this.findViewById(textViewId);
        if (null != textView) textView.setText(text);
    }

    private void configureNavigationDrawer()
    {
        {
            final boolean userDefinedTemplatesExist;
            {
                final boolean enableDeleteGridMenuItem, enableExportGridMenuItem;
                {
                    final java.lang.String person, templateTitle;
                    if (null == this.joinedGridModel)
                    {
                        person                   = templateTitle            = ""   ;
                        enableDeleteGridMenuItem = enableExportGridMenuItem = false;
                    }
                    else
                    {
                        person        = this.joinedGridModel.getPerson       ();
                        templateTitle = this.joinedGridModel.getTemplateTitle();
                        enableDeleteGridMenuItem = enableExportGridMenuItem = true;
                    }


                    this.setTextViewText(
                        org.wheatgenetics.coordinate.R.id.personTextView,          // From nav_hea-
                        person                                          );         //  der_main.xml.
                    this.setTextViewText(
                        org.wheatgenetics.coordinate.R.id.sw600dpPersonTextView,   // From nav_hea-
                        person                                                 );  //  der_main.xml.

                    this.setTextViewText(
                        org.wheatgenetics.coordinate.R.id.templateTitleTextView,   // From nav_hea-
                        templateTitle                                          );  //  der_main.xml.
                    this.setTextViewText(
                        org.wheatgenetics.coordinate.R.id.sw600dpTemplateTitleTextView, // From nav-
                        templateTitle                                                 );//  _header-
                }                                                                       //  _main-
                                                                                        //  .xml.

                assert null != this.loadGridMenuItem;
                this.loadGridMenuItem.setEnabled(this.gridsTable().exists());

                assert null != this.deleteGridMenuItem;
                this.deleteGridMenuItem.setEnabled(enableDeleteGridMenuItem);

                userDefinedTemplatesExist = this.templatesTable().exists(
                    org.wheatgenetics.coordinate.model.TemplateType.USERDEFINED);
                assert null != this.deleteTemplateMenuItem;
                this.deleteTemplateMenuItem.setEnabled(userDefinedTemplatesExist);

                assert null != this.exportGridMenuItem;
                this.exportGridMenuItem.setEnabled(enableExportGridMenuItem);
            }
            assert null != this.exportTemplateMenuItem;
            this.exportTemplateMenuItem.setEnabled(userDefinedTemplatesExist);
        }

        assert null != this.navigationItemSelectedListener; assert null != this.turnSoundOnMenuItem;
        this.turnSoundOnMenuItem.setEnabled(!this.navigationItemSelectedListener.getSoundOn());

        assert null != this.turnSoundOffMenuItem;
        this.turnSoundOffMenuItem.setEnabled(!this.turnSoundOnMenuItem.isEnabled());
    }
    // endregion

    private void closeDrawer()
    {
        assert null != this.drawerLayout;
        this.drawerLayout.closeDrawer(android.support.v4.view.GravityCompat.START);
    }

    private void populateFragments()
    {
        assert null != this.gridDisplayFragment; this.gridDisplayFragment.populate();
        assert null != this.dataEntryFragment  ; this.dataEntryFragment.populate  ();
    }

    // region loadJoinedGridModel() Private Methods
    private void loadJoinedGridModel(final long gridId)
    {
        this.joinedGridModel = gridId > 0 ? this.gridsTable().get(gridId) : null;

        assert null != this.sharedPreferences;
        if (null == this.joinedGridModel)
            this.sharedPreferences.clearLoadedGridId();
        else
            this.sharedPreferences.setLoadedGridId(this.joinedGridModel.getId());
    }

    private void loadJoinedGridModelThenPopulate(final long gridId)
    { this.loadJoinedGridModel(gridId); this.populateFragments(); }

    private void clearJoinedGridModelThenPopulate() { this.loadJoinedGridModelThenPopulate(0); }
    // endregion

    private void createGrid()
    {
        if (null == this.gridCreator)
            this.gridCreator = new org.wheatgenetics.coordinate.gc.GridCreator(
                this, org.wheatgenetics.coordinate.MainActivity.GRID_CREATOR, this);
        this.gridCreator.create();
    }

    private void handleTemplateDeleted()
    {
        if (null != this.joinedGridModel)
            if (!this.gridsTable().exists(this.joinedGridModel.getId()))
                this.clearJoinedGridModelThenPopulate();
    }

    // region Grid Export Private Methods
    private java.lang.String initialExportFileName()
    {
        return null == this.joinedGridModel ? null :
            this.joinedGridModel.getFirstOptionalFieldDatedValue();
    }

    private void exportGrid(final java.lang.String fileName)
    {
        if (null != this.joinedGridModel)
        {
            assert null != this.exportDir;
            try
            {
                this.gridExporter = new org.wheatgenetics.coordinate.model.GridExporter(
                    /* context    => */ this                                           ,
                    /* exportFile => */ this.exportDir.createNewFile(fileName + ".csv"),  // throws
                    /* exportFileName => */ fileName                                   ,  //  java.-
                    /* helper         => */ this                                       ); //  io.IO-
                this.gridExporter.execute();                                              //  Excep-
            }                                                                             //  tion
            catch (final java.io.IOException e) { this.showLongToast(e.getMessage()); }
        }
    }
    // endregion

    private void exportTemplate(
    final org.wheatgenetics.coordinate.model.TemplateModel templateModel,
    final java.lang.String                                 fileName     )
    {
        if (null != templateModel)
        {
            assert null != this.templatesDir;
            try
            {
                this.templateExporter = new org.wheatgenetics.coordinate.model.TemplateExporter(
                    /* context    => */ this                                              ,
                    /* exportFile => */ this.templatesDir.createNewFile(fileName + ".xml"),// throws
                    /* templateModel => */ templateModel);                                 //  java-
                this.templateExporter.execute();                                           //  .io.-
            }                                                                              //  IOEx-
            catch (final java.io.IOException e) { this.showLongToast(e.getMessage()); }    //  cep-
        }                                                                                  //  tion
    }

    private void storeSoundOn(final boolean soundOn)
    { assert null != this.sharedPreferences; this.sharedPreferences.setSoundOn(soundOn); }

    private void showChangeLog()
    {
        if (null == this.changeLogAlertDialog)
            this.changeLogAlertDialog = new org.wheatgenetics.changelog.ChangeLogAlertDialog(
                /* activity               => */ this                                        ,
                /* changeLogRawResourceId => */ org.wheatgenetics.coordinate.R.raw.changelog);
        this.changeLogAlertDialog.show();
    }

    private void goToNext(final org.wheatgenetics.coordinate.model.EntryModel entryModel)
    {
        if (null != this.joinedGridModel) if (this.joinedGridModel.goToNext(entryModel, this))
        {
            this.gridsTable().update(this.joinedGridModel);
            this.populateFragments();
        }
    }
    // endregion

    // region Overridden Methods
    @java.lang.Override
    protected void onCreate(final android.os.Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.setContentView(org.wheatgenetics.coordinate.R.layout.activity_main);

        this.drawerLayout = (android.support.v4.widget.DrawerLayout) this.findViewById(
            org.wheatgenetics.coordinate.R.id.drawer_layout);      // From layout/activity_main.xml.

        // region Configure action bar.
        {
            final android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar)
                this.findViewById(org.wheatgenetics.coordinate.R.id.toolbar);   // From layout/app_-
            this.setSupportActionBar(toolbar);                                  //  bar_main.xml.

            {
                final android.support.v7.app.ActionBar supportActionBar =
                    this.getSupportActionBar();
                if (null != supportActionBar) supportActionBar.setTitle(null);
            }

            final android.support.v7.app.ActionBarDrawerToggle actionBarDrawerToggle =
                new android.support.v7.app.ActionBarDrawerToggle(this, this.drawerLayout, toolbar,
                    org.wheatgenetics.coordinate.R.string.navigation_drawer_open ,
                    org.wheatgenetics.coordinate.R.string.navigation_drawer_close)
                    {
                        @java.lang.Override
                        public void onDrawerOpened(final android.view.View drawerView)
                        {
                            org.wheatgenetics.coordinate
                                .MainActivity.this.configureNavigationDrawer();
                        }
                    };
            assert null != this.drawerLayout;
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
            assert null != defaultTemplateModels; if (defaultTemplateModels.size() > 0)
            {
                final org.wheatgenetics.coordinate.database.TemplatesTable templatesTable =
                    this.templatesTable();
                assert null != templatesTable;
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
                            defaultTemplateModel.setId(existingTemplateModel.getId());
                        }
                        templatesTable.update(defaultTemplateModel);
                    }
                    else templatesTable.insert(defaultTemplateModel);
                }
            }
        }
        // endregion

        // region Directories
        {
            final java.lang.String coordinateDirName = "Coordinate",
                blankHiddenFileName = ".coordinate";

            try
            {
                new org.wheatgenetics.androidlibrary.Dir(this, coordinateDirName,
                    blankHiddenFileName).createIfMissing();            // throws java.io.IOException
            }
            catch (final java.io.IOException e)
            {
                // Do nothing.  The reason I do nothing is because when an exception is thrown it
                // does not mean there is a problem.  For example, an exception is thrown when a di-
                // rectory already exists.  If I try to create a directory and I fail because the
                // directory already exists then I don't have a problem.
            }

            // Exported data is saved to this folder.
            this.exportDir = new org.wheatgenetics.androidlibrary.Dir(
                this, coordinateDirName + "/Export", blankHiddenFileName);
            try { this.exportDir.createIfMissing();  /* throws java.io.IOException */ }
            catch (final java.io.IOException e)
            {
                // Do nothing.  The reason I do nothing is because when an exception is thrown it
                // does not mean there is a problem.  For example, an exception is thrown when a di-
                // rectory already exists.  If I try to create a directory and I fail because the
                // directory already exists then I don't have a problem.
            }

            // This directory will be used in the future to transfer templates between devices.
            this.templatesDir = new org.wheatgenetics.androidlibrary.Dir(
                this, coordinateDirName + "/Templates", blankHiddenFileName);
            try { this.templatesDir.createIfMissing();  /* throws java.io.IOException */ }
            catch (final java.io.IOException e)
            {
                // Do nothing.  The reason I do nothing is because when an exception is thrown it
                // does not mean there is a problem.  For example, an exception is thrown when a di-
                // rectory already exists.  If I try to create a directory and I fail because the
                // directory already exists then I don't have a problem.
            }
        }
        // endregion

        // region Configure fragments.
        {
            final android.support.v4.app.FragmentManager fragmentManager =
                this.getSupportFragmentManager();
            assert null != fragmentManager;

            this.gridDisplayFragment = (org.wheatgenetics.coordinate.display.GridDisplayFragment)
                fragmentManager.findFragmentById(
                    org.wheatgenetics.coordinate.R.id.gridDisplayFragment);
            this.dataEntryFragment = (org.wheatgenetics.coordinate.DataEntryFragment)
                fragmentManager.findFragmentById(
                    org.wheatgenetics.coordinate.R.id.dataEntryFragment);
        }
        // endregion

        {
            // region Get version.
            int versionCode; java.lang.String versionName;
            try
            {
                final android.content.pm.PackageInfo packageInfo =
                    this.getPackageManager().getPackageInfo(
                        this.getPackageName(), /* flags => */ 0);
                assert null != packageInfo;
                versionCode = packageInfo.versionCode; versionName = packageInfo.versionName;
            }
            catch (final android.content.pm.PackageManager.NameNotFoundException e)
            { versionCode = 0; versionName = org.wheatgenetics.javalib.Utils.adjust(null); }
            // endregion

            // region Configure navigation view.
            this.sharedPreferences = new org.wheatgenetics.sharedpreferences.SharedPreferences(
                this.getSharedPreferences("Settings", /* mode => */ 0));
            {
                final android.view.Menu menu;
                {
                    final android.support.design.widget.NavigationView navigationView =
                        (android.support.design.widget.NavigationView) this.findViewById(
                            org.wheatgenetics.coordinate.R.id.nav_view);        // From layout/acti-
                                                                                //  vity_main.xml.
                    assert null != navigationView; navigationView.setNavigationItemSelectedListener(
                        this.navigationItemSelectedListener =
                            new org.wheatgenetics.coordinate.NavigationItemSelectedListener(
                                /* activity    => */ this,
                                /* requestCode => */ org.wheatgenetics.coordinate
                                    .MainActivity.NAVIGATION_ITEM_SELECTED_LISTENER,
                                /* versionName => */ versionName                        ,
                                /* soundOn     => */ this.sharedPreferences.getSoundOn(),
                                /* handler     => */ new org.wheatgenetics.coordinate
                                    .NavigationItemSelectedListener.Handler()
                                    {
                                        @java.lang.Override
                                        public void createGrid()
                                        {
                                            org.wheatgenetics.coordinate
                                                .MainActivity.this.createGrid();
                                        }

                                        @java.lang.Override
                                        public void loadGrid(final long gridId)
                                        {
                                            org.wheatgenetics.coordinate.MainActivity.this
                                                .loadJoinedGridModelThenPopulate(gridId);
                                        }

                                        @java.lang.Override
                                        public void deleteGrid()
                                        {
                                            org.wheatgenetics.coordinate
                                                .MainActivity.this.deleteGrid();
                                        }

                                        @java.lang.Override
                                        public void handleTemplateDeleted()
                                        {
                                            org.wheatgenetics.coordinate
                                                .MainActivity.this.handleTemplateDeleted();
                                        }

                                        @java.lang.Override
                                        public java.lang.String initialExportFileName()
                                        {
                                            return org.wheatgenetics.coordinate
                                                .MainActivity.this.initialExportFileName();
                                        }

                                        @java.lang.Override
                                        public void exportGrid(final java.lang.String fileName)
                                        {
                                            org.wheatgenetics.coordinate
                                                .MainActivity.this.exportGrid(fileName);
                                        }

                                        @java.lang.Override
                                        public void exportTemplate(
                                        final org.wheatgenetics.coordinate.model.TemplateModel
                                            templateModel,
                                        final java.lang.String fileName)
                                        {
                                            org.wheatgenetics.coordinate.MainActivity
                                                .this.exportTemplate(templateModel, fileName);
                                        }

                                        @java.lang.Override
                                        public void storeSoundOn(final boolean soundOn)
                                        {
                                            org.wheatgenetics.coordinate
                                                .MainActivity.this.storeSoundOn(soundOn);
                                        }

                                        @java.lang.Override
                                        public void closeDrawer()
                                        {
                                            org.wheatgenetics.coordinate
                                                .MainActivity.this.closeDrawer();
                                        }
                                    },
                                /* versionOnClickListener => */
                                    new android.view.View.OnClickListener()
                                    {
                                        @java.lang.Override
                                        public void onClick(final android.view.View v)
                                        {
                                            org.wheatgenetics.coordinate
                                                .MainActivity.this.showChangeLog();
                                        }
                                    }));

                    menu = navigationView.getMenu();
                }

                assert null != menu; this.loadGridMenuItem =
                    menu.findItem(org.wheatgenetics.coordinate.R.id.nav_load_grid);
                this.deleteGridMenuItem =
                    menu.findItem(org.wheatgenetics.coordinate.R.id.nav_delete_grid);
                this.deleteTemplateMenuItem =
                    menu.findItem(org.wheatgenetics.coordinate.R.id.nav_delete_template);
                this.exportTemplateMenuItem =
                    menu.findItem(org.wheatgenetics.coordinate.R.id.nav_export_template);
                this.exportGridMenuItem =
                    menu.findItem(org.wheatgenetics.coordinate.R.id.nav_export_grid);
                this.turnSoundOnMenuItem =
                    menu.findItem(org.wheatgenetics.coordinate.R.id.nav_turn_sound_on);
                this.turnSoundOffMenuItem =
                    menu.findItem(org.wheatgenetics.coordinate.R.id.nav_turn_sound_off);
            }
            // endregion

            // region Load joinedGridModel.
            if (this.sharedPreferences.loadedGridIdIsSet())
                this.loadJoinedGridModel(this.sharedPreferences.getLoadedGridId());
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

    @java.lang.Override
    protected void onPostCreate(final android.os.Bundle savedInstanceState)
    { super.onPostCreate(savedInstanceState); this.closeDrawer(); }

    @java.lang.Override
    protected void onStart() { super.onStart(); this.populateFragments(); }

    @java.lang.Override
    public void onBackPressed()
    {
        assert null != this.drawerLayout;
        if (this.drawerLayout.isDrawerOpen(android.support.v4.view.GravityCompat.START))
            this.closeDrawer();
        else
            super.onBackPressed();
    }

    @java.lang.Override
    public boolean onCreateOptionsMenu(final android.view.Menu menu)
    {
        new android.view.MenuInflater(this).inflate(
            org.wheatgenetics.androidlibrary.R.menu.camera_options_menu, menu);
        return true;
    }

    @java.lang.Override
    public boolean onOptionsItemSelected(final android.view.MenuItem item)
    {
        // Handle action bar item clicks here.  The action bar will automatically handle clicks on
        // the Home/Up button, so long as you specify a parent activity in AndroidManifest.xml.
        assert null != item; final int itemId = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (org.wheatgenetics.androidlibrary.R.id.cameraOptionsMenuItem == itemId)
        {
            if (null == this.barcodeScanner)
                this.barcodeScanner = new org.wheatgenetics.zxing.BarcodeScanner(this);
            this.barcodeScanner.scan(); return true;
        }
        else return super.onOptionsItemSelected(item);
    }

    @java.lang.Override
    protected void onActivityResult(final int requestCode, final int resultCode,
    final android.content.Intent data)
    {
        final java.lang.String barcodeScannerResult =
            org.wheatgenetics.zxing.BarcodeScanner.parseActivityResult(
                requestCode, resultCode, data);
        if (null != barcodeScannerResult)
        {
            assert null != this.dataEntryFragment;
            this.dataEntryFragment.setEntry(barcodeScannerResult);
        }
        else if (android.app.Activity.RESULT_OK == resultCode)
        {
            assert null != data;
            switch (requestCode)
            {
                case org.wheatgenetics.coordinate.MainActivity.NAVIGATION_ITEM_SELECTED_LISTENER:
                    assert null != this.navigationItemSelectedListener;
                    this.navigationItemSelectedListener.setExcludedCells(data.getExtras());
                    break;

                case org.wheatgenetics.coordinate.MainActivity.GRID_CREATOR:
                    assert null != this.gridCreator;
                    this.gridCreator.setExcludedCells(data.getExtras());
                    break;
            }
        }
    }

    @java.lang.Override
    protected void onPause()
    {
        if (null != this.columnEndMediaPlayer) this.columnEndMediaPlayer.release();
        if (null != this.gridEndMediaPlayer  ) this.gridEndMediaPlayer.release  ();
        super.onPause();
    }

    @java.lang.Override
    protected void onDestroy()
    {
        if (null != this.templateExporter)
        {
            this.templateExporter.cancel();
            this.templateExporter = null;
        }
        if (null != this.gridExporter) { this.gridExporter.cancel(); this.gridExporter = null; }

        super.onDestroy();
    }

    // region org.wheatgenetics.coordinate.display.GridDisplayFragment.Handler Overridden Methods
    @java.lang.Override
    public org.wheatgenetics.coordinate.model.DisplayModel getDisplayModel()
    { return this.getJoinedGridModel(); }

    @java.lang.Override
    public void toggle(final org.wheatgenetics.coordinate.model.ElementModel elementModel)
    {
        if (null != this.joinedGridModel)
        {
            final org.wheatgenetics.coordinate.model.EntryModel entryModel =
                (org.wheatgenetics.coordinate.model.EntryModel) elementModel;
            this.joinedGridModel.setEntryModel(entryModel);
            this.entriesTable().insertOrUpdate(entryModel);
            if (entryModel instanceof org.wheatgenetics.coordinate.model.ExcludedEntryModel)
                if (this.joinedGridModel.getActiveEntryModel() == entryModel)
                    this.goToNext(entryModel);
        }
    }

    @java.lang.Override
    public int getActiveRow()
    { assert null != this.joinedGridModel; return this.joinedGridModel.getActiveRow(); }

    @java.lang.Override
    public int getActiveCol()
    { assert null != this.joinedGridModel; return this.joinedGridModel.getActiveCol(); }

    @java.lang.Override
    public void activate(final int row, final int col)
    {
        if (null != this.joinedGridModel)
            if (this.joinedGridModel.setActiveRowAndActiveCol(row, col))
                this.gridsTable().update(this.joinedGridModel);

        assert null != this.dataEntryFragment;
        this.dataEntryFragment.setEntry(this.getEntryValue());
    }
    // endregion

    // region org.wheatgenetics.coordinate.model.EntryModels.FilledHandler Overridden Methods
    @java.lang.Override
    public void handleFilledGrid()
    {
        org.wheatgenetics.coordinate.Utils.alert(this,
            org.wheatgenetics.coordinate.R.string.MainActivityFilledGridAlertMessage);

        assert null != this.navigationItemSelectedListener;
        if (this.navigationItemSelectedListener.getSoundOn())
        {
            if (null == this.gridEndMediaPlayer) this.gridEndMediaPlayer =
                android.media.MediaPlayer.create(this, org.wheatgenetics.coordinate.R.raw.plonk);
            this.gridEndMediaPlayer.start();
        }
    }

    @java.lang.Override
    public void handleFilledRowOrCol()
    {
        assert null != this.navigationItemSelectedListener;
        if (this.navigationItemSelectedListener.getSoundOn())
        {
            if (null == this.columnEndMediaPlayer)
                this.columnEndMediaPlayer = android.media.MediaPlayer.create(
                    this, org.wheatgenetics.coordinate.R.raw.column_end);
            this.columnEndMediaPlayer.start();
        }
    }
    // endregion

    // region org.wheatgenetics.coordinate.DataEntryFragment.Handler Overridden Methods
    @java.lang.Override
    public java.lang.String getEntryValue()
    {
        if (null == this.joinedGridModel)
            return null;
        else
        {
            final org.wheatgenetics.coordinate.model.EntryModel activeEntryModel =
                this.joinedGridModel.getActiveEntryModel();
            return null == activeEntryModel ? null : activeEntryModel.getValue();
        }
    }

    @java.lang.Override
    public java.lang.String getTemplateTitle()
    { return null == this.joinedGridModel ? "" : this.joinedGridModel.getTemplateTitle(); }

    @java.lang.Override
    public org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields getOptionalFields()
    { return null == this.joinedGridModel ? null : this.joinedGridModel.optionalFields(); }

    @java.lang.Override
    public void saveEntry(final java.lang.String entryValue)
    {
        if (null != this.joinedGridModel)
        {
            final org.wheatgenetics.coordinate.model.EntryModel activeEntryModel =
                this.joinedGridModel.getActiveEntryModel();
            if (activeEntryModel instanceof org.wheatgenetics.coordinate.model.IncludedEntryModel)
            {
                final org.wheatgenetics.coordinate.model.IncludedEntryModel
                    activeIncludedEntryModel =
                        (org.wheatgenetics.coordinate.model.IncludedEntryModel) activeEntryModel;
                activeIncludedEntryModel.setValue(entryValue);
                this.entriesTable().insertOrUpdate(activeIncludedEntryModel);
            }
            this.goToNext(activeEntryModel);
        }
    }

    @java.lang.Override public void clearEntry() { this.saveEntry(null); }
    // endregion

    // region org.wheatgenetics.coordinate.gc.GridCreator.Handler Overridden Method
    @java.lang.Override
    public void handleGridCreated(final long gridId)
    { this.loadJoinedGridModelThenPopulate(gridId); }
    // endregion

    // region org.wheatgenetics.coordinate.model.GridExporter.Helper Overridden Methods
    @java.lang.Override
    public void deleteGrid()
    {
        assert null != this.joinedGridModel; final long gridId = this.joinedGridModel.getId();

        if (this.entriesTable().deleteByGridId(gridId))
            this.showShortToast(
                org.wheatgenetics.coordinate.R.string.MainActivityEntriesOfGridSuccessToast);
        else
            this.showShortToast(
                org.wheatgenetics.coordinate.R.string.MainActivityEntriesOfGridFailToast);

        if (this.gridsTable().delete(gridId))
        {
            this.showLongToast(org.wheatgenetics.coordinate.R.string.MainActivityGridDeletedToast);
            this.clearJoinedGridModelThenPopulate(); this.createGrid();
        }
        else
            this.showLongToast(
                org.wheatgenetics.coordinate.R.string.MainActivityGridNotDeletedToast);
    }

    @java.lang.Override
    public org.wheatgenetics.coordinate.model.JoinedGridModel getJoinedGridModel()
    { return this.joinedGridModel; }
    // endregion
    // endregion
}