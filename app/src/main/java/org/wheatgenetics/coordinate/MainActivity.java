package org.wheatgenetics.coordinate;

/**
 * Uses:
 * android.content.Intent
 * android.content.pm.PackageInfo
 * android.content.pm.PackageManager.NameNotFoundException
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
 * android.view.View
 * android.view.View.OnClickListener
 * android.widget.TextView
 *
 * org.wheatgenetics.javalib.Utils
 *
 * org.wheatgenetics.androidlibrary.Dir
 * org.wheatgenetics.androidlibrary.R
 *
 * org.wheatgenetics.changelog.ChangeLogAlertDialog
 * org.wheatgenetics.sharedpreferences.SharedPreferences
 * org.wheatgenetics.zxing.BarcodeScanner
 *
 * org.wheatgenetics.coordinate.database.GridsTable
 * org.wheatgenetics.coordinate.database.TemplatesTable
 *
 * org.wheatgenetics.coordinate.gc.GridCreator
 *
 * org.wheatgenetics.coordinate.model.JoinedGridModel
 * org.wheatgenetics.coordinate.model.TemplateModel
 * org.wheatgenetics.coordinate.model.TemplateModels
 * org.wheatgenetics.coordinate.model.TemplateType
 *
 * org.wheatgenetics.coordinate.navigation.NavigationItemSelectedListener
 * org.wheatgenetics.coordinate.navigation.NavigationItemSelectedListener.Handler
 *
 * org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields
 *
 * org.wheatgenetics.coordinate.DataEntryFragment
 * org.wheatgenetics.coordinate.DataEntryFragment.Handler
 * org.wheatgenetics.coordinate.DisplayFragment
 * org.wheatgenetics.coordinate.DisplayFragment.Handler
 * org.wheatgenetics.coordinate.R
 */
public class MainActivity extends android.support.v7.app.AppCompatActivity implements
org.wheatgenetics.coordinate.gc.GridCreator.Handler,
org.wheatgenetics.coordinate.DataEntryFragment.Handler,
org.wheatgenetics.coordinate.DisplayFragment.Handler
{
    // region Fields
    private android.support.v4.widget.DrawerLayout drawerLayout = null;

    private org.wheatgenetics.sharedpreferences.SharedPreferences sharedPreferences          ;
    private org.wheatgenetics.androidlibrary.Dir                  exportDir                  ;
    private org.wheatgenetics.changelog.ChangeLogAlertDialog      changeLogAlertDialog = null;
    private org.wheatgenetics.zxing.BarcodeScanner                barcodeScanner       = null;

    private org.wheatgenetics.coordinate.model.JoinedGridModel   joinedGridModel        = null;
    private org.wheatgenetics.coordinate.database.TemplatesTable templatesTableInstance = null;
    private org.wheatgenetics.coordinate.database.GridsTable     gridsTableInstance     = null;
    private org.wheatgenetics.coordinate.gc.GridCreator          gridCreator            = null;

    private org.wheatgenetics.coordinate.DisplayFragment   displayFragment  ;
    private org.wheatgenetics.coordinate.DataEntryFragment dataEntryFragment;
    // endregion

    // region Private Methods
    private void configureNavigationDrawer()
    {
        {
            final java.lang.String person =
                null == this.sharedPreferences ? "" : this.sharedPreferences.getPerson();
            {
                final android.widget.TextView personTextView =
                    (android.widget.TextView) this.findViewById(
                        org.wheatgenetics.coordinate.R.id.personTextView);         // From nav_hea-
                if (null != personTextView) personTextView.setText(person);        //  der_main.xml.
            }
            {
                final android.widget.TextView personTextView =
                    (android.widget.TextView) this.findViewById(
                        org.wheatgenetics.coordinate.R.id.sw600dpPersonTextView);  // From nav_hea-
                if (null != personTextView) personTextView.setText(person);        //  der_main.xml.
            }
        }

        final java.lang.String templateTitle =
            null == this.joinedGridModel ? "" : this.joinedGridModel.getTemplateTitle();
        {
            final android.widget.TextView templateTitleTextView =
                (android.widget.TextView) this.findViewById(
                    org.wheatgenetics.coordinate.R.id.templateTitleTextView);          // From nav_-
            if (null != templateTitleTextView)                                         //  header_-
                templateTitleTextView.setText(templateTitle);                          //  main.xml.
        }
        {
            final android.widget.TextView templateTitleTextView =
                (android.widget.TextView) this.findViewById(
                    org.wheatgenetics.coordinate.R.id.sw600dpTemplateTitleTextView);   // From nav_-
            if (null != templateTitleTextView)                                         //  header_-
                templateTitleTextView.setText(templateTitle);                          //  main.xml.
        }
    }

    private org.wheatgenetics.coordinate.database.TemplatesTable templatesTable()
    {
        if (null == this.templatesTableInstance) this.templatesTableInstance =
            new org.wheatgenetics.coordinate.database.TemplatesTable(this);
        return this.templatesTableInstance;
    }

    private void closeDrawer()
    {
        assert null != this.drawerLayout;
        this.drawerLayout.closeDrawer(android.support.v4.view.GravityCompat.START);
    }

    private void showChangeLog()
    {
        if (null == this.changeLogAlertDialog)
            this.changeLogAlertDialog = new org.wheatgenetics.changelog.ChangeLogAlertDialog(
                /* activity               => */ this                                        ,
                /* changeLogRawResourceId => */ org.wheatgenetics.coordinate.R.raw.changelog);
        this.changeLogAlertDialog.show();
    }

    private org.wheatgenetics.coordinate.database.GridsTable gridsTable()
    {
        if (null == this.gridsTableInstance) this.gridsTableInstance =
            new org.wheatgenetics.coordinate.database.GridsTable(this);
        return this.gridsTableInstance;
    }

    private void loadJoinedGridModel(final long gridId)
    { this.joinedGridModel = gridId > 0 ? this.gridsTable().get(gridId) : null; }
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

            final android.support.v7.app.ActionBarDrawerToggle toggle =
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
            assert null != this.drawerLayout; this.drawerLayout.setDrawerListener(toggle);
            toggle.syncState();
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
            try
            {
                new org.wheatgenetics.androidlibrary.Dir(this, coordinateDirName + "/Templates",
                    blankHiddenFileName).createIfMissing();            // throws java.io.IOException
            }
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

            this.displayFragment = (org.wheatgenetics.coordinate.DisplayFragment)
                fragmentManager.findFragmentById(org.wheatgenetics.coordinate.R.id.displayFragment);
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

            // region Configure navigation menu.
            final android.support.design.widget.NavigationView navigationView =
                (android.support.design.widget.NavigationView) this.findViewById(
                    org.wheatgenetics.coordinate.R.id.nav_view);   // From layout/activity_main.xml.
            assert null != navigationView;
            navigationView.setNavigationItemSelectedListener(
                new org.wheatgenetics.coordinate.navigation.NavigationItemSelectedListener(
                    /* activity    => */ this       ,
                    /* versionName => */ versionName,
                    /* handler     => */ new org.wheatgenetics.coordinate.navigation
                        .NavigationItemSelectedListener.Handler()
                        {
                            @java.lang.Override
                            public void closeDrawer()
                            { org.wheatgenetics.coordinate.MainActivity.this.closeDrawer(); }
                        },
                    /* versionOnClickListener => */ new android.view.View.OnClickListener()
                        {
                            @java.lang.Override
                            public void onClick(final android.view.View v)
                            { org.wheatgenetics.coordinate.MainActivity.this.showChangeLog(); }
                        }));
            // endregion

            this.sharedPreferences = new org.wheatgenetics.sharedpreferences.SharedPreferences(
                this.getSharedPreferences("Settings", /* mode => */ 0));
            if (this.sharedPreferences.currentGridIsSet())
                this.loadJoinedGridModel(this.sharedPreferences.getCurrentGrid());
            else
            {
                this.loadJoinedGridModel(0);

                this.gridCreator = new org.wheatgenetics.coordinate.gc.GridCreator(this, this);
                this.gridCreator.create();
            }

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
    protected void onStart()
    {
        super.onStart();

        assert null != this.displayFragment  ; this.displayFragment.populate  ();
        assert null != this.dataEntryFragment; this.dataEntryFragment.populate();
    }

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
        if (null != this.dataEntryFragment)
            this.dataEntryFragment.setEntry(org.wheatgenetics.javalib.Utils.replaceIfNull(
                org.wheatgenetics.zxing.BarcodeScanner.parseActivityResult(
                    requestCode, resultCode, data),
                "null"));
    }

    // region org.wheatgenetics.coordinate.gc.GridCreator.Handler Overridden Methods
    @java.lang.Override
    public void setPerson(final java.lang.String person)
    { assert null != this.sharedPreferences; this.sharedPreferences.setPerson(person); }

    @java.lang.Override
    public void handleGridCreated(final long gridId)
    {
        this.loadJoinedGridModel(gridId);
        if (null != this.joinedGridModel)
        {
            assert null != this.sharedPreferences;
            this.sharedPreferences.setCurrentGrid(this.joinedGridModel.getId());
        }
        this.onStart();
    }
    // endregion

    // region org.wheatgenetics.coordinate.DisplayFragment.Handler Overridden Method
    @java.lang.Override
    public org.wheatgenetics.coordinate.model.JoinedGridModel getJoinedGridModel()
    { return this.joinedGridModel; }
    // endregion

    // region org.wheatgenetics.coordinate.DataEntryFragment.Handler Overridden Methods
    @java.lang.Override
    public java.lang.String getEntry() { return "entry"; }                                   // TODO

    @java.lang.Override
    public java.lang.String getTemplateTitle()
    { return null == this.joinedGridModel ? "" : this.joinedGridModel.getTemplateTitle(); }

    @java.lang.Override
    public org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields getOptionalFields()
    { return null == this.joinedGridModel ? null : this.joinedGridModel.optionalFields(); }

    @java.lang.Override
    public void addEntry(final java.lang.String entry) {}                                    // TODO
    // endregion
    // endregion
}