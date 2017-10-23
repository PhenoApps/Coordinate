package org.wheatgenetics.coordinate.ui;

/**
 * Uses:
 * android.annotation.SuppressLint
 * android.content.Intent
 * android.content.pm.PackageInfo
 * android.content.pm.PackageManager.NameNotFoundException
 * android.content.res.Resources
 * android.graphics.Point
 * android.media.MediaPlayer
 * android.media.MediaPlayer.OnCompletionListener
 * android.net.Uri
 * android.os.Bundle
 * android.R
 * android.support.design.widget.NavigationView
 * android.support.design.widget.NavigationView.OnNavigationItemSelectedListener
 * android.support.v4.view.GravityCompat
 * android.support.v4.widget.DrawerLayout
 * android.support.v7.app.ActionBar
 * android.support.v7.app.ActionBarDrawerToggle
 * android.support.v7.app.AppCompatActivity
 * android.support.v7.widget.Toolbar
 * android.view.KeyEvent
 * android.view.LayoutInflater
 * android.view.Menu
 * android.view.MenuInflater
 * android.view.MenuItem
 * android.view.View
 * android.view.View.OnClickListener
 * android.view.inputmethod.EditorInfo
 * android.widget.EditText
 * android.widget.LinearLayout
 * android.widget.TableLayout
 * android.widget.TableRow
 * android.widget.TextView
 * android.widget.TextView.OnEditorActionListener
 *
 * org.json.JSONException
 *
 * org.wheatgenetics.javalib.Utils
 *
 * org.wheatgenetics.about.AboutAlertDialog
 * org.wheatgenetics.about.OtherApps.Index
 * org.wheatgenetics.androidlibrary.Dir
 * org.wheatgenetics.androidlibrary.R
 * org.wheatgenetics.androidlibrary.Utils
 * org.wheatgenetics.changelog.ChangeLogAlertDialog
 * org.wheatgenetics.zxing.BarcodeScanner
 *
 * org.wheatgenetics.sharedpreferences.SharedPreferences
 *
 * org.wheatgenetics.coordinate.R
 *
 * org.wheatgenetics.coordinate.database.EntriesTable
 * org.wheatgenetics.coordinate.database.GridsTable
 * org.wheatgenetics.coordinate.database.TemplatesTable
 *
 * org.wheatgenetics.coordinate.model.EntryModel
 * org.wheatgenetics.coordinate.model.Exporter
 * org.wheatgenetics.coordinate.model.Exporter.Helper
 * org.wheatgenetics.coordinate.model.GridModel
 * org.wheatgenetics.coordinate.model.GridModels
 * org.wheatgenetics.coordinate.model.JoinedGridModel
 * org.wheatgenetics.coordinate.model.PartialTemplateModel
 * org.wheatgenetics.coordinate.model.TemplateModel
 * org.wheatgenetics.coordinate.model.TemplateModels
 * org.wheatgenetics.coordinate.model.TemplateType
 *
 * org.wheatgenetics.coordinate.optionalField.CheckedOptionalFields
 * org.wheatgenetics.coordinate.optionalField.OptionalField
 *
 * org.wheatgenetics.coordinate.ui.tc.TemplateCreator
 * org.wheatgenetics.coordinate.ui.tc.TemplateCreator.Handler
 *
 * org.wheatgenetics.coordinate.ui.GetExportGridFileNameAlertDialog
 * org.wheatgenetics.coordinate.ui.GetExportGridFileNameAlertDialog.Handler
 * org.wheatgenetics.coordinate.ui.GetTemplateChoiceAlertDialog
 * org.wheatgenetics.coordinate.ui.GetTemplateChoiceAlertDialog.Handler
 * org.wheatgenetics.coordinate.ui.SelectAlertDialog
 * org.wheatgenetics.coordinate.ui.SelectAlertDialog.Handler
 * org.wheatgenetics.coordinate.ui.SetOptionalFieldValuesAlertDialog
 * org.wheatgenetics.coordinate.ui.SetOptionalFieldValuesAlertDialog.Handler
 * org.wheatgenetics.coordinate.ui.Utils
 */
public class Main extends android.support.v7.app.AppCompatActivity
implements android.widget.TextView.OnEditorActionListener, android.view.View.OnClickListener,
org.wheatgenetics.coordinate.model.Exporter.Helper
{
    // region Constants
    private static final int STATE_NORMAL = 0, STATE_DONE = 1, STATE_ACTIVE = 2, STATE_INACTIVE = 3;
    private static final int MODE_DNA = 0, MODE_USERDEFINED = 1;
    // endregion

    // region Fields
    // region Widget Fields
    private android.widget.LinearLayout mainLayout;                                      // main.xml

    private android.support.v4.widget.DrawerLayout       drawerLayout         ;          // main.xml
    private android.support.v7.app.ActionBarDrawerToggle actionBarDrawerToggle;

    private android.widget.LinearLayout
        gridAreaLayout ,                                                                 // main.xml
        gridTableLayout;                                                                 // main.xml

    private android.widget.EditText     cellIDEditText       ;                           // main.xml
    private android.widget.TextView     templateTitleTextView;                           // main.xml
    private android.widget.LinearLayout optionalFieldLayout  ;                           // main.xml

    private android.view.View currentCellView = null;
    // endregion

    private long             gridId     =  0                ;
    private java.lang.String gridTitle  = ""                ;
    private int              currentRow =  1, currentCol = 1;

    private java.lang.String                                      versionName                ;
    private org.wheatgenetics.sharedpreferences.SharedPreferences sharedPreferences          ;
    private org.wheatgenetics.zxing.BarcodeScanner                barcodeScanner       = null;
    private org.wheatgenetics.changelog.ChangeLogAlertDialog      changeLogAlertDialog = null;
    private org.wheatgenetics.about.AboutAlertDialog              aboutAlertDialog     = null;

    // region Table Fields
    private org.wheatgenetics.coordinate.database.TemplatesTable templatesTableInstance = null;
    private org.wheatgenetics.coordinate.database.GridsTable     gridsTableInstance     = null;
    private org.wheatgenetics.coordinate.database.EntriesTable   entriesTableInstance   = null;
    // endregion

    private org.wheatgenetics.coordinate.model.TemplateModel templateModel =
        org.wheatgenetics.coordinate.model.TemplateModel.makeInitial();

    private org.wheatgenetics.androidlibrary.Dir        exportDir                ;
    private org.wheatgenetics.coordinate.model.Exporter exporter           = null;
    private long                                        lastExportedGridId =   -1;

    private org.wheatgenetics.coordinate.ui.tc.TemplateCreator templateCreator = null;

    // region AlertDialog Fields
    private org.wheatgenetics.coordinate.ui.GetTemplateChoiceAlertDialog
        getTemplateChoiceAlertDialog = null;
    private org.wheatgenetics.coordinate.ui.SelectAlertDialog
        selectTemplateToLoadAlertDialog = null;
    private org.wheatgenetics.coordinate.ui.SetOptionalFieldValuesAlertDialog
        setSeedTrayOptionalFieldValuesAlertDialog = null, setOptionalFieldValuesAlertDialog = null;
    private org.wheatgenetics.coordinate.ui.SelectAlertDialog
        selectTemplateToDeleteAlertDialog = null;
    private org.wheatgenetics.coordinate.ui.GetExportGridFileNameAlertDialog
        getExportGridFileNameAlertDialog = null;
    private org.wheatgenetics.coordinate.ui.SelectAlertDialog selectGridToImportAlertDialog = null;
    // endregion
    // endregion

    // region Private Methods
    private org.wheatgenetics.coordinate.optionalField.CheckedOptionalFields
    makeCheckedOptionalFields()
    { assert null != this.templateModel; return this.templateModel.makeCheckedOptionalFields(); }

    private void share(final java.io.File exportFile)
    {
        final android.content.Intent intent =
            new android.content.Intent(android.content.Intent.ACTION_SEND);

        intent.addFlags(android.content.Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        assert null != exportFile; intent.putExtra(android.content.Intent.EXTRA_STREAM,
        android.net.Uri.parse(exportFile.getAbsolutePath()));
        intent.setType("text/plain");

        this.startActivity(android.content.Intent.createChooser(intent,
            this.getString(org.wheatgenetics.coordinate.R.string.share_file)));
    }

    private boolean isExcluded(final int row, final int col)
    { return this.isExcludedRow(row) || this.isExcludedCol(col) || this.isExcludedCell(row, col); }

    private boolean clearGrid()
    {
        try
        {
            if (this.entriesTable().deleteByGrid(this.gridId))
                this.populateUI();
            else
                this.showLongToast(org.wheatgenetics.coordinate.R.string.clear_fail);
            return true;
        }
        catch (final java.lang.Exception e)
        {
            this.showLongToast(org.wheatgenetics.coordinate.R.string.clear_fail);
            return false;
        }
    }

    // region getTag() Private Methods
    private static java.lang.String getTag(final int row, final int col)
    { return java.lang.String.format(java.util.Locale.US, "tag_%d_%d", row, col); }

    private java.lang.String getTag()
    { return org.wheatgenetics.coordinate.ui.Main.getTag(this.currentRow, this.currentCol); }
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

    // region Utils AlertDialog Private Methods
    // region alert() Utils AlertDialog Methods
    private void alert(final int message)
    { org.wheatgenetics.coordinate.ui.Utils.alert(this, message); }

    private void alert(final int title, final java.lang.String message, final int messageIfNull)
    {
        org.wheatgenetics.coordinate.ui.Utils.alert(
            /* context => */ this ,
            /* title   => */ title,
            /* message => */ org.wheatgenetics.javalib.Utils.replaceIfNull(
                message, this.getString(messageIfNull)));
    }

    private void alert(final int message, final java.lang.Runnable yesRunnable)
    { org.wheatgenetics.coordinate.ui.Utils.alert(this, message, yesRunnable); }
    // endregion

    // region confirm() Utils AlertDialog Methods
    private void confirm(final int title, final int message, final java.lang.Runnable yesRunnable)
    { org.wheatgenetics.coordinate.ui.Utils.confirm(this, title, message, yesRunnable); }

    private void confirm(final int message, final java.lang.Runnable yesRunnable)
    { org.wheatgenetics.coordinate.ui.Utils.confirm(this, message, yesRunnable); }

    private void confirm(final int message, final java.lang.Runnable yesRunnable,
    final java.lang.Runnable noRunnable)
    { org.wheatgenetics.coordinate.ui.Utils.confirm(this, message, yesRunnable, noRunnable); }
    // endregion
    // endregion

    // region Table Private Methods
    private org.wheatgenetics.coordinate.database.TemplatesTable templatesTable()
    {
        if (null == this.templatesTableInstance) this.templatesTableInstance =
            new org.wheatgenetics.coordinate.database.TemplatesTable(this);
        return this.templatesTableInstance;
    }

    private org.wheatgenetics.coordinate.database.GridsTable gridsTable()
    {
        if (null == this.gridsTableInstance)
            this.gridsTableInstance = new org.wheatgenetics.coordinate.database.GridsTable(this);
        return this.gridsTableInstance;
    }

    private org.wheatgenetics.coordinate.database.EntriesTable entriesTable()
    {
        if (null == this.entriesTableInstance) this.entriesTableInstance =
            new org.wheatgenetics.coordinate.database.EntriesTable(this);
        return this.entriesTableInstance;
    }
    // endregion
    // endregion

    // region Overridden Methods
    @java.lang.Override
    protected void onCreate(final android.os.Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.setContentView(org.wheatgenetics.coordinate.R.layout.main);

        {
            final android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar)
                this.findViewById(org.wheatgenetics.coordinate.R.id.toolbar);
            this.setSupportActionBar(toolbar);
            assert null != toolbar; toolbar.bringToFront();
        }

        {
            final android.support.v7.app.ActionBar supportActionBar = this.getSupportActionBar();
            if (null != supportActionBar)
            {
                supportActionBar.setTitle                 (null);
                supportActionBar.setDisplayHomeAsUpEnabled(true);
                supportActionBar.setHomeButtonEnabled     (true);
            }
        }

        this.mainLayout = (android.widget.LinearLayout)
            this.findViewById(org.wheatgenetics.coordinate.R.id.mainLayout);
        this.mainLayout.setVisibility(android.view.View.INVISIBLE);

        this.sharedPreferences = new org.wheatgenetics.sharedpreferences.SharedPreferences(
            this.getSharedPreferences("Settings", 0));

        this.drawerLayout = (android.support.v4.widget.DrawerLayout)
            this.findViewById(org.wheatgenetics.coordinate.R.id.drawer_layout);
        {
            final android.support.design.widget.NavigationView navigationView =
                (android.support.design.widget.NavigationView)
                this.findViewById(org.wheatgenetics.coordinate.R.id.nvView);
            assert null != navigationView; navigationView.setNavigationItemSelectedListener(
                new android.support.design.widget.NavigationView.OnNavigationItemSelectedListener()
                {
                    @java.lang.Override
                    public boolean onNavigationItemSelected(final android.view.MenuItem item)
                    { return org.wheatgenetics.coordinate.ui.Main.this.selectNavigationItem(item); }
                });
        }
        this.actionBarDrawerToggle = new android.support.v7.app.ActionBarDrawerToggle(this,
            this.drawerLayout, org.wheatgenetics.coordinate.R.string.drawer_open,
            org.wheatgenetics.coordinate.R.string.drawer_close)
            {
                @java.lang.Override
                public void onDrawerOpened(final android.view.View drawerView)
                {
                    {
                        final android.widget.TextView personTextView = (android.widget.TextView)
                            org.wheatgenetics.coordinate.ui.Main.this.findViewById(
                                org.wheatgenetics.coordinate.R.id.nameLabel);
                        assert null != org.wheatgenetics.coordinate.ui.Main.this.sharedPreferences;
                        assert null != personTextView                                             ;
                        personTextView.setText(org.wheatgenetics.coordinate.
                            ui.Main.this.sharedPreferences.getPerson());
                    }
                    {
                        final android.widget.TextView templateTitleTextView =
                            (android.widget.TextView)
                                org.wheatgenetics.coordinate.ui.Main.this.findViewById(
                                    org.wheatgenetics.coordinate.R.id.templateLabel);
                        assert null != org.wheatgenetics.coordinate.ui.Main.this.templateModel;
                        assert null != templateTitleTextView; templateTitleTextView.setText(
                            org.wheatgenetics.coordinate.ui.Main.this.templateModel.getTitle());
                    }
                }

                @java.lang.Override
                public void onDrawerClosed(final android.view.View drawerView) {}
            };
        this.actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        assert null != this.drawerLayout;
        this.drawerLayout.setDrawerListener(this.actionBarDrawerToggle);

        this.gridAreaLayout = (android.widget.LinearLayout)
            this.findViewById(org.wheatgenetics.coordinate.R.id.gridArea);
        this.gridTableLayout = (android.widget.TableLayout)
            this.findViewById(org.wheatgenetics.coordinate.R.id.dataTable);

        this.cellIDEditText = (android.widget.EditText)
            this.findViewById(org.wheatgenetics.coordinate.R.id.dataEdit);
        assert null != this.cellIDEditText; this.cellIDEditText.setImeActionLabel(
            this.getString(org.wheatgenetics.coordinate.R.string.keyboard_save),
            android.view.KeyEvent.KEYCODE_ENTER                               );
        this.cellIDEditText.setOnEditorActionListener(this);

        this.templateTitleTextView = (android.widget.TextView)
            this.findViewById(org.wheatgenetics.coordinate.R.id.templateText);
        this.optionalFieldLayout = (android.widget.LinearLayout)
            this.findViewById(org.wheatgenetics.coordinate.R.id.optionalLayout);

        assert null != this.templateModel; assert null != this.templateTitleTextView;
        this.templateTitleTextView.setText(this.templateModel.getTitle());

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

        this.templateModel.setTitle("");

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

        if (this.sharedPreferences.currentGridIsSet())
            this.getLoadTemplateGridPopulateUI(this.sharedPreferences.getCurrentGrid(), true);
        else
            this.loadExistingTemplateOrCreateNewTemplate();

        this.showUI();

        int versionCode;
        try
        {
            final android.content.pm.PackageInfo packageInfo = this.getPackageManager().
                getPackageInfo(    // throws android.content.pm.PackageManager.NameNotFoundException
                    /* packageName => */ this.getPackageName(),
                    /* flags       => */ 0                    );
            assert null != packageInfo;
            versionCode = packageInfo.versionCode; this.versionName = packageInfo.versionName;
        }
        catch (final android.content.pm.PackageManager.NameNotFoundException e)
        { versionCode = 0; this.versionName = org.wheatgenetics.javalib.Utils.adjust(null); }
        if (!this.sharedPreferences.updateVersionIsSet(versionCode))
        {
            this.sharedPreferences.setUpdateVersion(versionCode);
            this.showChangeLog();
        }
    }

    @java.lang.Override
    protected void onPostCreate(final android.os.Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        assert null != this.actionBarDrawerToggle; this.actionBarDrawerToggle.syncState();
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
        assert null != this.actionBarDrawerToggle;
        if (this.actionBarDrawerToggle.onOptionsItemSelected(item)) return true;

        assert null != item;
        switch (item.getItemId())
        {
            case android.R.id.home:
                assert null != this.drawerLayout;
                this.drawerLayout.openDrawer(android.support.v4.view.GravityCompat.START);
                break;

            case org.wheatgenetics.androidlibrary.R.id.cameraOptionsMenuItem:
                if (null == this.barcodeScanner)
                    this.barcodeScanner = new org.wheatgenetics.zxing.BarcodeScanner(this);
                this.barcodeScanner.scan();
                break;
        }

        return true;
    }

    @java.lang.Override
    protected void onActivityResult(final int requestCode,
    final int resultCode, final android.content.Intent data)
    {
        final java.lang.String scanResult =
            org.wheatgenetics.zxing.BarcodeScanner.parseActivityResult(
                requestCode, resultCode, data);
        if (null != scanResult)
        {
            assert null != this.cellIDEditText; this.cellIDEditText.setText(scanResult);
            this.saveData();
        }
    }

    @java.lang.Override
    protected void onDestroy()
    {
        if (null != this.exporter) { this.exporter.cancel(); this.exporter = null; }
        super.onDestroy();
    }

    // region android.widget.TextView.OnEditorActionListener Overridden Method
    // Enables Main to be a this.cellIDEditText editor action listener.
    @java.lang.Override
    public boolean onEditorAction(final android.widget.TextView v, final int actionId,
    final android.view.KeyEvent event)
    {
        if (android.view.inputmethod.EditorInfo.IME_ACTION_DONE == actionId)
            return this.saveData();
        else
            if (null == event)
                return false;
            else
                if (android.view.KeyEvent.ACTION_DOWN   == event.getAction ()
                &&  android.view.KeyEvent.KEYCODE_ENTER == event.getKeyCode())
                    return this.saveData();
                else
                    return false;
    }
    // endregion

    // region android.view.View.OnClickListener Overridden Method
    // Enables Main to be a cell click listener.
    /** @param v Cell in the grid. */
    @java.lang.Override
    public void onClick(final android.view.View v)
    {                                                   // TODO: Don't toggle already selected cell.
        int row, col;
        {
            java.lang.Object object; assert null != v;

            object = v.getTag(org.wheatgenetics.coordinate.R.string.cell_row);
            row = object instanceof java.lang.Integer ? (java.lang.Integer) object : -1;

            object = v.getTag(org.wheatgenetics.coordinate.R.string.cell_col);
            col = object instanceof java.lang.Integer ? (java.lang.Integer) object : -1;
        }

        assert null != this.cellIDEditText;
        if (this.isExcluded(row, col))
            this.cellIDEditText.setText("");
        else
        {
            if (-1 != row && -1 != col)
            {
                this.currentRow = row; this.currentCol = col;

                final java.lang.String value = this.getValue(this.currentRow, this.currentCol);

                if (null != value && value.contains("exclude"))
                    return;
                else
                {
                    this.setCellState(v, STATE_ACTIVE);

                    this.cellIDEditText.setSelectAllOnFocus(true);
                    this.cellIDEditText.setText(
                        org.wheatgenetics.javalib.Utils.makeEmptyIfNull(value));
                    this.cellIDEditText.selectAll();
                    this.cellIDEditText.requestFocus();
                }
            }

            this.resetCurrentCell();
            this.currentCellView = v;
        }
    }
    // endregion

    // region org.wheatgenetics.coordinate.model.Exporter.Helper Overridden Methods
    // Enables Main to be a this.exporter helper.
    @java.lang.Override
    public java.lang.String getValue(final int row, final int col)
    {
        final org.wheatgenetics.coordinate.model.EntryModel entryModel =
            this.entriesTable().get(this.gridId, row, col);
        return null == entryModel ? null : entryModel.getValue();
    }

    @java.lang.Override
    public void handleExportDone(final java.lang.Boolean result, final java.lang.String message,
    final java.io.File exportFile)
    {
        // TODO: When grid is reset, make a new one.
        if (null != result && result)
        {
            this.lastExportedGridId = this.gridId;                   // TODO: Make into Main method.

            @java.lang.SuppressWarnings("ClassExplicitlyExtendsObject")
            class YesRunnable extends java.lang.Object implements java.lang.Runnable
            {
                @java.lang.Override
                public void run()
                {
                    if (org.wheatgenetics.coordinate.ui.Main.this.clearGrid())
                        org.wheatgenetics.coordinate.ui.Main.this.share(exportFile);
                }
            }

            @java.lang.SuppressWarnings("ClassExplicitlyExtendsObject")
            class NoRunnable extends java.lang.Object implements java.lang.Runnable
            {
                @java.lang.Override
                public void run()
                { org.wheatgenetics.coordinate.ui.Main.this.share(exportFile); }
            }

            this.alert(
                /* message     => */ org.wheatgenetics.coordinate.R.string.export_success,
                /* yesRunnable => */ new java.lang.Runnable()
                    {
                        @java.lang.Override
                        public void run()
                        {
                            org.wheatgenetics.coordinate.ui.Main.this.confirm(
                                /* message => */ org.wheatgenetics.coordinate.R.string.clear_grid,
                                /* yesRunnable => */ new YesRunnable(),
                                /* noRunnable  => */ new NoRunnable ());
                        }
                    });
        }
        else
            this.alert(
                /* title         => */ org.wheatgenetics.coordinate.R.string.export        ,
                /* message       => */ message                                             ,
                /* messageIfNull => */ org.wheatgenetics.coordinate.R.string.export_no_data);
    }
    // endregion
    // endregion

    // region Operations
    private boolean insertTemplate()
    {
        boolean success;
        {
            final long templateId = this.templatesTable().insert(this.templateModel);
            if (templateId > 0)
            {
                assert null != this.templateModel; this.templateModel.setId(templateId);
                success = true;
            }
            else
            {
                this.alert(org.wheatgenetics.coordinate.R.string.create_template_fail);
                success = false;
            }
        }
        return success;
    }

    private long insertGrid()
    {
        assert null != this.templateModel;
        return this.gridsTable().insert(new org.wheatgenetics.coordinate.model.GridModel(
            /* temp  => */ this.templateModel.getId                     (),
            /* title => */ this.templateModel.getFirstOptionalFieldValue()));
    }

    private void getLoadTemplateGridPopulateUI(final long gridId,
    final boolean gridIdIsfromSharedPreferences)
    {
        final org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel =
            this.gridsTable().get(gridId);
        if (null == joinedGridModel)
            this.alert(org.wheatgenetics.coordinate.R.string.import_grid_failed);
        else
        {
            joinedGridModel.populate(this.templateModel);
            this.gridId = joinedGridModel.getId(); this.gridTitle = joinedGridModel.getTitle();

            if (!gridIdIsfromSharedPreferences)
            {
                assert null != this.sharedPreferences;
                this.sharedPreferences.setCurrentGrid(gridId);
            }

            this.populateUI();
        }
    }

    private void insertGetLoadTemplateGridPopulateUI()
    {
        if (this.insertTemplate())
        {
            // deleteUserDefinedTemplateItsGrids(this.templateModel); // TODO

            final long gridId = this.insertGrid();
            if (gridId > 0)
                this.getLoadTemplateGridPopulateUI(gridId, false);
            else
                this.alert(org.wheatgenetics.coordinate.R.string.create_grid_fail);
        }
    }

    private boolean deleteEntriesGrid()
    { this.entriesTable().deleteByGrid(this.gridId); return this.gridsTable().delete(this.gridId); }

    // TODO: Shouldn't this also delete entries records associated with grids records?
    private boolean deleteUserDefinedTemplateItsGrids(
    final org.wheatgenetics.coordinate.model.PartialTemplateModel partialTemplateModel)
    {
        assert null != partialTemplateModel; final long templateId = partialTemplateModel.getId();

        final org.wheatgenetics.coordinate.database.TemplatesTable templatesTable =
            this.templatesTable();
        assert null != templatesTable; if (templatesTable.exists(templateId))
        {
            org.wheatgenetics.coordinate.model.TemplateType templateType;
            {
                final org.wheatgenetics.coordinate.model.TemplateModel templateModel =
                    templatesTable.get(templateId);
                assert null != templateModel; templateType = templateModel.getType();
            }
            if (org.wheatgenetics.coordinate.model.TemplateType.SEED == templateType
            ||  org.wheatgenetics.coordinate.model.TemplateType.DNA  == templateType)
            {
                this.showShortToast(
                    org.wheatgenetics.coordinate.R.string.template_not_deleted_default);
                return false;
            }
            else
            {
                this.gridsTable().deleteByTemp(templateId);
                return templatesTable.delete(templateId);
            }
        }
        else return false;
    }
    // endregion

    // region Drawer Methods
    // region Subsubaction Drawer Methods
    private void loadExistingTemplate(                      // TODO: DRY? (Compare to deleteGrid().)
    final org.wheatgenetics.coordinate.model.TemplateType templateType)
    {
        assert null != this.templateModel; this.templateModel.setType(templateType);

        final long gridId = this.insertGrid();
        if (gridId > 0)
        {
            assert null != this.optionalFieldLayout;
            this.optionalFieldLayout.setVisibility(android.view.View.VISIBLE);

            assert null != this.gridAreaLayout;
            this.gridAreaLayout.setVisibility(android.view.View.VISIBLE);

            this.getLoadTemplateGridPopulateUI(gridId, false);
        }
        else this.alert(org.wheatgenetics.coordinate.R.string.create_grid_fail);
    }

    private void tempLoad(final int mode)
    {
        if (org.wheatgenetics.coordinate.ui.Main.MODE_DNA == mode)
            this.loadExistingTemplate(org.wheatgenetics.coordinate.model.TemplateType.DNA);
        else
            if (org.wheatgenetics.coordinate.ui.Main.MODE_USERDEFINED == mode)
                this.loadExistingTemplate(
                    org.wheatgenetics.coordinate.model.TemplateType.USERDEFINED);
    }

    /** First non-excluded cell. */
    private boolean getNextFreeCell()
    {
        {
            assert null != this.templateModel;
            final android.graphics.Point nextFreeCell = this.templateModel.nextFreeCell(
                new android.graphics.Point(this.currentCol, this.currentRow));
            assert null != nextFreeCell;
            this.currentRow = nextFreeCell.y; this.currentCol = nextFreeCell.x;
        }
        return true;
    }

    private boolean isExcludedRow(final int row)
    { assert null != this.templateModel; return this.templateModel.isExcludedRow(row); }

    private boolean isExcludedCol(final int col)
    { assert null != this.templateModel; return this.templateModel.isExcludedCol(col); }

    private void setCellState(final android.view.View cell, final int state)
    {
        int backgroundResourceId;
        switch (state)
        {
            case org.wheatgenetics.coordinate.ui.Main.STATE_DONE: backgroundResourceId =
                org.wheatgenetics.coordinate.R.drawable.table_cell_done; break;

            case org.wheatgenetics.coordinate.ui.Main.STATE_ACTIVE: backgroundResourceId =
                org.wheatgenetics.coordinate.R.drawable.table_cell_active; break;

            case org.wheatgenetics.coordinate.ui.Main.STATE_INACTIVE: backgroundResourceId =
                org.wheatgenetics.coordinate.R.drawable.table_cell_inactive; break;

            default: backgroundResourceId =
                org.wheatgenetics.coordinate.R.drawable.table_cell; break;
        }

        assert null != cell; cell.setBackgroundResource(backgroundResourceId);
    }

    private boolean isExcludedCell(final int row, final int col)
    { assert null != this.templateModel; return this.templateModel.isExcludedCell(col, row); }

    private void saveExcludedCell(final int row, final int col)
    {
        boolean success;                                                               // TODO: DRY!
        {
            final org.wheatgenetics.coordinate.database.EntriesTable entriesTable =
                this.entriesTable();
            assert null != entriesTable;
            final org.wheatgenetics.coordinate.model.EntryModel entryModel =
                entriesTable.get(this.gridId, row, col);
            if (null == entryModel)
            {
                success = entriesTable.insert(new org.wheatgenetics.coordinate.model.EntryModel(
                    this.gridId, row, col, "exclude")) > 0;
            }
            else
            {
                entryModel.setValue("exclude");
                success = entriesTable.update(entryModel);
            }
        }
        if (!success) this.showShortToast(org.wheatgenetics.coordinate.R.string.update_failed);
    }

    private void showUI()
    {
        assert null != this.templateModel; assert null != this.templateTitleTextView;
        this.templateTitleTextView.setText(this.templateModel.getTitle());

        assert null != this.mainLayout; this.mainLayout.setVisibility(android.view.View.VISIBLE);

        assert null != this.cellIDEditText;
        this.cellIDEditText.setText(org.wheatgenetics.javalib.Utils.makeEmptyIfNull(
            this.getValue(1, 1)));
    }
    // endregion

    // region Subaction Drawer Methods
    private void loadExistingTemplateOrCreateNewTemplate()
    {
        if (null == this.getTemplateChoiceAlertDialog) this.getTemplateChoiceAlertDialog =
            new org.wheatgenetics.coordinate.ui.GetTemplateChoiceAlertDialog(this,
                new org.wheatgenetics.coordinate.ui.GetTemplateChoiceAlertDialog.Handler()
                {
                    @java.lang.Override
                    public void chooseOld()
                    { org.wheatgenetics.coordinate.ui.Main.this.selectThenLoadTemplate(); }

                    @java.lang.Override
                    public void chooseNew()
                    { org.wheatgenetics.coordinate.ui.Main.this.insertThenLoadTemplate(); }
                });
        this.getTemplateChoiceAlertDialog.show();
    }

    private void newGridNow()
    {
        this.deleteEntriesGrid();

        assert null != this.templateModel;
        final org.wheatgenetics.coordinate.model.TemplateType templateType =
            this.templateModel.getType();
        if (org.wheatgenetics.coordinate.model.TemplateType.SEED == templateType)
            this.setValuesThenLoadSeedTrayTemplate(this.templatesTable().get(templateType));
        else
            if (org.wheatgenetics.coordinate.model.TemplateType.DNA == templateType)
                this.setValuesThenLoadDNAPlateTemplate(this.templatesTable().get(templateType));
            else
                // reset options?
                this.setValuesThenLoadUserDefinedTemplate(this.templateModel);
    }

    private void setValuesThenLoadSeedTrayTemplate(
    final org.wheatgenetics.coordinate.model.TemplateModel templateModel)
    {
        if (null == this.setSeedTrayOptionalFieldValuesAlertDialog)
            this.setSeedTrayOptionalFieldValuesAlertDialog =
                new org.wheatgenetics.coordinate.ui.SetOptionalFieldValuesAlertDialog(this,
                    new org.wheatgenetics.coordinate.ui.SetOptionalFieldValuesAlertDialog.Handler()
                    {
                        @java.lang.Override
                        public void setPerson(final java.lang.String person)
                        {
                            assert
                                null != org.wheatgenetics.coordinate.ui.Main.this.sharedPreferences;
                            org.wheatgenetics.coordinate.ui.Main.this.sharedPreferences.setPerson(
                                person);
                        }

                        @java.lang.Override
                        public void handleSetValuesDone()
                        {
                            org.wheatgenetics.coordinate.ui.Main.this.loadExistingTemplate(
                                org.wheatgenetics.coordinate.model.TemplateType.SEED);
                        }
                    });
        assert null != templateModel;
        this.setSeedTrayOptionalFieldValuesAlertDialog.show(templateModel.getTitle(),
            this.makeCheckedOptionalFields(), /* firstCannotBeEmpty => */ true);
    }

    private void setValuesThenLoadDNAPlateTemplate(
    final org.wheatgenetics.coordinate.model.TemplateModel templateModel)
    {
        this.setValuesThenLoadDNAPlateOrUserDefinedTemplate(
            org.wheatgenetics.coordinate.ui.Main.MODE_DNA, templateModel);
    }

    private void setValuesThenLoadUserDefinedTemplate(
    final org.wheatgenetics.coordinate.model.TemplateModel templateModel)
    {
        this.setValuesThenLoadDNAPlateOrUserDefinedTemplate(
            org.wheatgenetics.coordinate.ui.Main.MODE_USERDEFINED, templateModel);
    }

    private void setValuesThenLoadDNAPlateOrUserDefinedTemplate(final int mode,
    final org.wheatgenetics.coordinate.model.TemplateModel templateModel)  // TODO: Merge this method with the one above.
    {
        assert null != this.templateModel; if (this.templateModel.optionalFieldsIsEmpty())
            this.tempLoad(mode);                           // There is no need to set optional field
        else                                               //  values since optionalFields is empty.
        {
            if (null == this.setOptionalFieldValuesAlertDialog)
                this.setOptionalFieldValuesAlertDialog =
                    new org.wheatgenetics.coordinate.ui.SetOptionalFieldValuesAlertDialog(this, new
                        org.wheatgenetics.coordinate.ui.SetOptionalFieldValuesAlertDialog.Handler()
                        {
                            @java.lang.Override
                            public void setPerson(final java.lang.String person)
                            {
                                assert
                                    null != org.wheatgenetics.coordinate.ui.Main.this.sharedPreferences;
                                org.wheatgenetics.coordinate.ui.Main.this.sharedPreferences.setPerson(
                                    person);
                            }

                            @java.lang.Override
                            public void handleSetValuesDone()
                            { org.wheatgenetics.coordinate.ui.Main.this.tempLoad(mode); }
                        });
            assert null != templateModel; this.setOptionalFieldValuesAlertDialog.show(
                templateModel.getTitle(), this.makeCheckedOptionalFields(),
                /* firstCannotBeEmpty => */ org.wheatgenetics.coordinate.ui.Main.MODE_DNA == mode);
        }
    }

    private void populateUI()
    {
        this.currentRow = 1; this.currentCol = 1; this.getNextFreeCell();

        assert null != this.optionalFieldLayout; this.optionalFieldLayout.removeAllViews();

        final org.wheatgenetics.coordinate.optionalField.CheckedOptionalFields
            checkedOptionalFields = this.makeCheckedOptionalFields();
        final android.view.LayoutInflater layoutInflater = this.getLayoutInflater();
              boolean                     first          = true                    ;
        for (final org.wheatgenetics.coordinate.optionalField.OptionalField optionalField:
        checkedOptionalFields)                                                         // TODO: DRY!
        {
            final android.view.View view = layoutInflater.inflate(
                org.wheatgenetics.coordinate.R.layout.optional_line,
                new android.widget.LinearLayout(this), false);
            {
                assert null != view;
                final android.widget.TextView nameTextView = (android.widget.TextView)
                    view.findViewById(org.wheatgenetics.coordinate.R.id.fieldText);
                assert null != nameTextView; nameTextView.setText(optionalField.getName());
            }
            {
                java.lang.String text;
                if (first)
                {
                    text  = this.gridTitle;
                    first = false         ;
                }
                else text = optionalField.getValue();

                final android.widget.TextView valueTextView = (android.widget.TextView)
                    view.findViewById(org.wheatgenetics.coordinate.R.id.valueText);
                assert null != valueTextView; valueTextView.setText(text);
            }
            this.optionalFieldLayout.addView(view);
        }

        assert null != this.gridTableLayout; this.gridTableLayout.removeAllViews();

        // header
        @android.annotation.SuppressLint("InflateParams")
        final android.widget.TableRow hrow = (android.widget.TableRow)
            layoutInflater.inflate(org.wheatgenetics.coordinate.R.layout.table_row, null);
        int chcol = 0;
        assert null != this.templateModel; assert null != hrow;
        for (int c = 0; c < this.templateModel.getCols() + 1; c++)
        {
            @android.annotation.SuppressLint("InflateParams")
            final android.widget.LinearLayout cell_top = (android.widget.LinearLayout)
                layoutInflater.inflate(org.wheatgenetics.coordinate.R.layout.table_cell_top, null);
            assert null != cell_top;
            final android.widget.TextView cell_txt = (android.widget.TextView)
                cell_top.findViewById(org.wheatgenetics.coordinate.R.id.dataCell);

            if (0 == c)
                cell_txt.setText("");
            else
            {
                cell_txt.setText("" + (this.templateModel.getColNumbering() ? c :
                    java.lang.Character.toString((char) ('A' + chcol)))); // Row
                chcol++;
                if (26 <= chcol) chcol = 0;
            }
            hrow.addView(cell_top);
        }
        this.gridTableLayout.addView(hrow);

        // body
        int chrow = 0;
        for (int r = 1; r < this.templateModel.getRows() + 1; r++)
        {
            @android.annotation.SuppressLint("InflateParams")
            final android.widget.TableRow brow = (android.widget.TableRow)
                layoutInflater.inflate(org.wheatgenetics.coordinate.R.layout.table_row, null);

            final boolean excludedRow = this.isExcludedRow(r);

            for (int c = 0; c < this.templateModel.getCols() + 1; c++)
            {
                final boolean excludedCol = this.isExcludedCol(c);

                @android.annotation.SuppressLint("InflateParams")
                final android.widget.LinearLayout cell_box = (android.widget.LinearLayout)
                    layoutInflater.inflate(
                        org.wheatgenetics.coordinate.R.layout.table_cell_box, null);
                assert null != cell_box;
                final android.widget.TextView cell_cnt = (android.widget.TextView)
                    cell_box.findViewById(org.wheatgenetics.coordinate.R.id.dataCell);

                @android.annotation.SuppressLint("InflateParams")
                final android.widget.LinearLayout cell_left = (android.widget.LinearLayout)
                    layoutInflater.inflate(
                        org.wheatgenetics.coordinate.R.layout.table_cell_left, null);
                assert null != cell_left;
                final android.widget.TextView cell_num = (android.widget.TextView)
                    cell_left.findViewById(org.wheatgenetics.coordinate.R.id.dataCell);

                assert null != cell_num; assert null != brow;
                if (0 == c)
                {
                    cell_num.setText("" + (this.templateModel.getRowNumbering() ? r :
                        java.lang.Character.toString((char) ('A' + chrow)))); // Row
                    chrow++;
                    if (26 <= chrow) chrow = 0;

                    brow.addView(cell_left);
                }
                else
                {
                    final java.lang.String value = this.getValue(r, c);
                    this.setCellState(cell_cnt, STATE_NORMAL);

                    if (null != value && 0 != value.trim().length())
                        this.setCellState(cell_cnt, STATE_DONE);

                    if (r == this.currentRow && c == this.currentCol)
                    {
                        this.setCellState(cell_cnt, STATE_ACTIVE);
                        this.currentCellView = cell_cnt;
                    }

                    if (excludedRow || excludedCol || this.isExcludedCell(r, c))
                    {
                        this.setCellState(cell_cnt, STATE_INACTIVE);
                        this.saveExcludedCell(r, c);
                    }

                    if (null != value && value.equals("exclude"))
                    {
                        this.setCellState(cell_cnt, STATE_INACTIVE);
                        this.templateModel.addExcludedCell(c, r);
                    }

                    cell_cnt.setOnClickListener(this);
                    cell_cnt.setTag(org.wheatgenetics.coordinate.ui.Main.getTag(r, c));
                    cell_cnt.setTag(org.wheatgenetics.coordinate.R.string.cell_col, c);
                    cell_cnt.setTag(org.wheatgenetics.coordinate.R.string.cell_row, r);
                    brow.addView(cell_box);
                }
            }
            this.gridTableLayout.addView(brow);
        }

        this.showUI();
    }

    private java.io.File createExportFile()
    {
        assert null != this.templateModel; assert null != this.exportDir;
        try
        {
            return this.exportDir.createNewFile(                       // throws java.io.IOException
                this.templateModel.getTitle());
        }
        catch (final java.io.IOException e) { this.showLongToast(e.getMessage()); return null; }
    }

    private void showChangeLog()
    {
        if (null == this.changeLogAlertDialog)
            this.changeLogAlertDialog = new org.wheatgenetics.changelog.ChangeLogAlertDialog(
                /* activity               => */ this,
                /* changeLogRawResourceId => */
                    org.wheatgenetics.coordinate.R.raw.changelog_releases);
        this.changeLogAlertDialog.show();
    }
    // endregion

    // region Action Drawer Methods
    private void createNewGrid()
    {
        if (0 == this.gridId)
            this.loadExistingTemplateOrCreateNewTemplate();
        else
            if (this.gridId >= 0 && this.lastExportedGridId == this.gridId)
                this.newGridNow();
            else
                this.confirm(
                    /* message     => */ org.wheatgenetics.coordinate.R.string.new_grid_warning,
                    /* yesRunnable => */ new java.lang.Runnable()
                        {
                            @java.lang.Override
                            public void run()
                            { org.wheatgenetics.coordinate.ui.Main.this.exportGrid(); }
                        },
                    /* noRunnable => */ new java.lang.Runnable()
                        {
                            @java.lang.Override
                            public void run()
                            { org.wheatgenetics.coordinate.ui.Main.this.newGridNow(); }
                        });
    }

    private void deleteGridAfterConfirm()         // TODO: DRY? (Compare to chooseOld().)
    {
        if (this.deleteEntriesGrid())
        {
            this.showLongToast(org.wheatgenetics.coordinate.R.string.grid_deleted);
            this.gridId = 0;

            assert null != this.optionalFieldLayout;
            this.optionalFieldLayout.setVisibility(android.view.View.INVISIBLE);

            assert null != this.gridAreaLayout;
            this.gridAreaLayout.setVisibility(android.view.View.INVISIBLE);

            this.loadExistingTemplateOrCreateNewTemplate();
        }
        else this.showLongToast(org.wheatgenetics.coordinate.R.string.grid_not_deleted);
    }

    private void deleteGrid()
    {
        if (0 != this.gridId) this.confirm(
            /* message     => */ org.wheatgenetics.coordinate.R.string.delete_grid_warning,
            /* yesRunnable => */ new java.lang.Runnable()
                {
                    @java.lang.Override
                    public void run()
                    { org.wheatgenetics.coordinate.ui.Main.this.deleteGridAfterConfirm(); }
                });
    }

    private void insertThenLoadTemplate()
    {
        if (null == this.templateCreator)
            this.templateCreator = new org.wheatgenetics.coordinate.ui.tc.TemplateCreator(this,
                new org.wheatgenetics.coordinate.ui.tc.TemplateCreator.Handler()
                {
                    @java.lang.Override
                    public void handleTemplateCreated()
                    {
                        if (org.wheatgenetics.coordinate.ui.Main.this.insertTemplate())
                            org.wheatgenetics.coordinate.ui.Main.this
                            .setValuesThenLoadDNAPlateOrUserDefinedTemplate(
                                org.wheatgenetics.coordinate.ui.Main.MODE_USERDEFINED  ,
                                org.wheatgenetics.coordinate.ui.Main.this.templateModel);
                    }
                });
        this.templateCreator.create(this.templateModel);
    }

    private void selectThenLoadTemplate()
    {
        final org.wheatgenetics.coordinate.model.TemplateModels templateModels =
            this.templatesTable().load();
        if (null == this.selectTemplateToLoadAlertDialog) this.selectTemplateToLoadAlertDialog =
            new org.wheatgenetics.coordinate.ui.SelectAlertDialog(this,
                org.wheatgenetics.coordinate.R.string.template_load,
                new org.wheatgenetics.coordinate.ui.SelectAlertDialog.Handler()
                {
                    @java.lang.Override
                    public void select(final int which)
                    {
                        final org.wheatgenetics.coordinate.model.TemplateModel templateModel =
                            templateModels.get(which);
                        if (null != templateModel) switch (which)
                        {
                            case 0: org.wheatgenetics.coordinate.ui.Main.this           // seed tray
                                .setValuesThenLoadSeedTrayTemplate(templateModel); break;

                            case 1:                                                     // dna plate
                                templateModel.makeOneRandomCell();
                                org.wheatgenetics.coordinate.ui.Main.this
                                    .setValuesThenLoadDNAPlateTemplate(templateModel);
                                break;

                            default: org.wheatgenetics.coordinate.ui.Main.this       // user-defined
                                .setValuesThenLoadUserDefinedTemplate(templateModel); break;
                        }
                    }
                });
        this.selectTemplateToLoadAlertDialog.show(templateModels.titles());
    }

    private void deleteTemplateAfterConfirm(
    final org.wheatgenetics.coordinate.model.TemplateModel templateModel)
    {
        if (this.deleteUserDefinedTemplateItsGrids(templateModel))
        {
            this.showLongToast(org.wheatgenetics.coordinate.R.string.template_deleted);
            this.loadExistingTemplateOrCreateNewTemplate();
        }
        else this.showLongToast(org.wheatgenetics.coordinate.R.string.template_not_deleted);
    }

    private void deleteTemplateAfterSelect(
    final org.wheatgenetics.coordinate.model.TemplateModel templateModel)
    {
        if (null != templateModel) this.confirm(
            /* title       => */ org.wheatgenetics.coordinate.R.string.delete_template        ,
            /* message     => */ org.wheatgenetics.coordinate.R.string.delete_template_warning,
            /* yesRunnable => */ new java.lang.Runnable()
                {
                    @java.lang.Override
                    public void run()
                    {
                        org.wheatgenetics.coordinate.ui.Main.this.deleteTemplateAfterConfirm(
                            templateModel);
                    }
                });
    }

    private void deleteTemplate()
    {
        final org.wheatgenetics.coordinate.model.TemplateModels templateModels =
            this.templatesTable().load();

        if (null == this.selectTemplateToDeleteAlertDialog) this.selectTemplateToDeleteAlertDialog =
            new org.wheatgenetics.coordinate.ui.SelectAlertDialog(this,
                org.wheatgenetics.coordinate.R.string.delete_template,
                new org.wheatgenetics.coordinate.ui.SelectAlertDialog.Handler()
                {
                    @java.lang.Override
                    public void select(final int which)
                    {
                        org.wheatgenetics.coordinate.ui.Main.this.deleteTemplateAfterSelect(
                            templateModels.get(which));
                    }
                });
        this.selectTemplateToDeleteAlertDialog.show(templateModels.titles());
    }

    @android.annotation.SuppressLint("DefaultLocale")
    private void importGrid()
    {
        final org.wheatgenetics.coordinate.model.GridModels gridModels = this.gridsTable().load();
        if (null == gridModels)
            this.alert(org.wheatgenetics.coordinate.R.string.no_templates);
        else
        {
            final java.lang.String names  [] = gridModels.names  ();
            final long             indexes[] = gridModels.indexes();

            if (null == this.selectGridToImportAlertDialog) this.selectGridToImportAlertDialog =
                new org.wheatgenetics.coordinate.ui.SelectAlertDialog(this,
                    org.wheatgenetics.coordinate.R.string.import_grid,
                    new org.wheatgenetics.coordinate.ui.SelectAlertDialog.Handler()
                    {
                        @java.lang.Override
                        public void select(final int which)
                        {
                            if (which < indexes.length) org.wheatgenetics.coordinate.ui.
                                Main.this.getLoadTemplateGridPopulateUI(indexes[which], false);
                        }
                    });
            this.selectGridToImportAlertDialog.show(names);
        }
    }

    private void exportGridAfterGettingFileName(final java.lang.String fileName)
    {
        assert null != this.templateModel; final long templateId = this.templateModel.getId();
        final org.wheatgenetics.coordinate.database.TemplatesTable templatesTable =
            this.templatesTable();

        assert null != templatesTable; if (templatesTable.exists(templateId))
        {
            final java.io.File exportFile = this.createExportFile();
            assert null != exportFile;
            this.exporter = new org.wheatgenetics.coordinate.model.Exporter(
                /* helper         => */ this                          ,
                /* context        => */ this                          ,
                /* templateModel  => */ templatesTable.get(templateId),
                /* exportFileName => */ fileName                      ,
                /* absolutePath   => */ exportFile.getAbsolutePath()  );
            this.exporter.execute();
        }
    }

    private void exportGrid()
    {
        if (null == this.getExportGridFileNameAlertDialog) this.getExportGridFileNameAlertDialog =
            new org.wheatgenetics.coordinate.ui.GetExportGridFileNameAlertDialog(this,
                new org.wheatgenetics.coordinate.ui.GetExportGridFileNameAlertDialog.Handler()
                {
                    @java.lang.Override
                    public void handleGetFileNameDone(final java.lang.String fileName)
                    {
                        org.wheatgenetics.coordinate.ui.Main.this.exportGridAfterGettingFileName(
                            fileName);
                    }
                });
        assert null != this.templateModel;
        this.getExportGridFileNameAlertDialog.show(
            this.templateModel.getFirstOptionalFieldDatedValue());
    }

    private void showAboutAlertDialog()
    {
        if (null == this.aboutAlertDialog)
        {
            java.lang.String title, msgs[];
            {
                final android.content.res.Resources resources = this.getResources();
                assert null != resources;
                title = resources.getString(org.wheatgenetics.coordinate.R.string.about);
                msgs  = org.wheatgenetics.javalib.Utils.stringArray(
                    resources.getString(org.wheatgenetics.coordinate.R.string.grid_description));
            }

            this.aboutAlertDialog = new org.wheatgenetics.about.AboutAlertDialog(
                /* context                => */ this            ,
                /* title                  => */ title           ,
                /* versionName            => */ this.versionName,
                /* msgs[]                 => */ msgs            ,
                /* suppressIndex          => */ org.wheatgenetics.about.OtherApps.Index.COORDINATE,
                /* versionOnClickListener => */ new android.view.View.OnClickListener()
                    {
                        @java.lang.Override
                        public void onClick(final android.view.View v)
                        { org.wheatgenetics.coordinate.ui.Main.this.showChangeLog(); }
                    });
        }
        this.aboutAlertDialog.show();
    }
    // endregion

    private boolean selectNavigationItem(final android.view.MenuItem menuItem)
    {
        assert null != menuItem; switch (menuItem.getItemId())
        {
            case org.wheatgenetics.coordinate.R.id.menu_new_grid: this.createNewGrid(); break;

            case org.wheatgenetics.coordinate.R.id.menu_delete_grid : this.deleteGrid(); break;
            case org.wheatgenetics.coordinate.R.id.menu_new_template:
                this.insertThenLoadTemplate(); break;

            case org.wheatgenetics.coordinate.R.id.menu_load_template:
                this.selectThenLoadTemplate(); break;

            case org.wheatgenetics.coordinate.R.id.menu_delete_template:
                this.deleteTemplate(); break;

            case org.wheatgenetics.coordinate.R.id.menu_import: this.importGrid(); break;

            case org.wheatgenetics.coordinate.R.id.menu_export:
                assert null != this.templateModel;
                if (this.templateModel.getTitle().equals(""))
                    this.showShortToast(org.wheatgenetics.coordinate.R.string.grid_empty);
                else
                    this.exportGrid();
                break;

            case org.wheatgenetics.coordinate.R.id.about: this.showAboutAlertDialog(); break;

            // Keeping this for debugging purposes:
            // case org.wheatgenetics.coordinate.R.id.reset_database: this.resetDatabase(); break;
            // case org.wheatgenetics.coordinate.R.id.copy_database : this.copydb       (); break;
        }

        assert null != this.drawerLayout; this.drawerLayout.closeDrawers();
        return true;
    }
    // endregion

    private boolean saveData()
    {
        java.lang.String value =
            org.wheatgenetics.androidlibrary.Utils.getText(this.cellIDEditText);
        {
            boolean success;                                                           // TODO: DRY!
            {
                final org.wheatgenetics.coordinate.database.EntriesTable entriesTable =
                    this.entriesTable();
                assert null != entriesTable;
                final org.wheatgenetics.coordinate.model.EntryModel entryModel =
                    entriesTable.get(this.gridId, this.currentRow, this.currentCol);
                if (null == entryModel)
                    success = entriesTable.insert(new org.wheatgenetics.coordinate.model.EntryModel(
                        this.gridId, this.currentRow, this.currentCol, value)) > 0;
                else
                {
                    entryModel.setValue(value);
                    success = entriesTable.update(entryModel);
                }
            }
            if (!success)
            {
                this.showShortToast(org.wheatgenetics.coordinate.R.string.update_failed);
                return true;
            }
        }

        assert null != this.gridTableLayout;
        android.view.View view = this.gridTableLayout.findViewWithTag(this.getTag());

        if (null != view) this.setCellState(view, 0 == value.length() ? STATE_NORMAL : STATE_DONE);

        boolean endOfCell = false;

        assert null != this.templateModel;
        this.currentRow++;
        if (this.templateModel.getRows() < this.currentRow || (this.templateModel.getRows() == this.currentRow
        &&  (this.isExcludedRow(this.templateModel.getRows()) || this.isExcludedCell(this.templateModel.getRows(), this.currentCol))))  // TODO: Bug?
        {
            this.currentRow = this.templateModel.getRows();

            this.currentCol++;
            if (this.templateModel.getCols() < this.currentCol || (this.templateModel.getCols() == this.currentCol
            &&  (this.isExcludedCol(this.templateModel.getCols()) || this.isExcludedCell(this.currentRow, this.templateModel.getCols()))))
            {
                this.currentCol = this.templateModel.getCols();
                this.currentCol = this.templateModel.getRows();

                endOfCell = true;
            }
            else this.currentRow = 1;
        }

        if (!endOfCell) if (this.isExcluded(this.currentRow, this.currentCol))
            if (!this.getNextFreeCell()) endOfCell = true;

        value = org.wheatgenetics.javalib.Utils.makeEmptyIfNull(
            this.getValue(this.currentRow, this.currentCol));

        assert null != this.cellIDEditText;
        this.cellIDEditText.setSelectAllOnFocus(true);
        this.cellIDEditText.setText(value);
        this.cellIDEditText.selectAll();
        this.cellIDEditText.requestFocus();

        view = this.gridTableLayout.findViewWithTag(this.getTag());
        if (null != view) if (!this.isExcluded(this.currentRow, this.currentCol))
            this.setCellState(view, STATE_ACTIVE);

        this.resetCurrentCell();
        this.currentCellView = view;

        if (endOfCell)
        {
            this.alert(org.wheatgenetics.coordinate.R.string.grid_filled);

            android.media.MediaPlayer chimePlayer;
            {
                final int resID =
                    this.getResources().getIdentifier("plonk", "raw", this.getPackageName());
                chimePlayer = android.media.MediaPlayer.create(this, resID);
            }
            assert null != chimePlayer;
            chimePlayer.setOnCompletionListener(new android.media.MediaPlayer.OnCompletionListener()
                {
                    @java.lang.Override
                    public void onCompletion(final android.media.MediaPlayer mp)
                    { assert null != mp; mp.release(); }
                });
            chimePlayer.start();
        }

        return true;
    }

    private void resetCurrentCell()
    {
        if (null != this.currentCellView)
        {
            int row = -1, col = -1;

            {
                java.lang.Object obj =
                    this.currentCellView.getTag(org.wheatgenetics.coordinate.R.string.cell_col);
                if (obj instanceof java.lang.Integer) col = (java.lang.Integer) obj;

                obj = this.currentCellView.getTag(org.wheatgenetics.coordinate.R.string.cell_row);
                if (obj instanceof java.lang.Integer) row = (java.lang.Integer) obj;
            }

            int state;
            if (this.isExcluded(row, col))
                state = org.wheatgenetics.coordinate.ui.Main.STATE_INACTIVE;
            else
            {
                final java.lang.String value = org.wheatgenetics.javalib.Utils.makeEmptyIfNull(
                    this.getValue(row, col));
                state = 0 == value.length() ?
                    org.wheatgenetics.coordinate.ui.Main.STATE_NORMAL :
                    org.wheatgenetics.coordinate.ui.Main.STATE_DONE   ;
            }
            this.setCellState(this.currentCellView, state);
        }
    }
}