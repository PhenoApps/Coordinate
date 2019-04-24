package org.wheatgenetics.coordinate;

/**
 * Uses:
 * android.app.Activity
 * android.content.Intent
 * android.content.pm.PackageInfo
 * android.content.pm.PackageManager
 * android.content.pm.PackageManager.NameNotFoundException
 * android.Manifest.permission
 * android.media.MediaPlayer
 * android.os.Bundle
 * android.support.annotation.IdRes
 * android.support.annotation.IntRange
 * android.support.annotation.NonNull
 * android.support.annotation.Nullable
 * android.support.annotation.StringRes
 * android.support.design.widget.NavigationView
 * android.support.v4.app.FragmentManager
 * android.support.v4.view.GravityCompat
 * android.support.v4.widget.DrawerLayout
 * android.support.v7.app.ActionBar
 * android.support.v7.app.ActionBarDrawerToggle
 * android.support.v7.app.AppCompatActivity
 * android.support.v7.widget.Toolbar
 * android.view.inputmethod.InputMethodManager
 * android.view.Menu
 * android.view.MenuInflater
 * android.view.MenuItem
 * android.view.View
 * android.view.View.OnClickListener
 * android.widget.TextView
 *
 * org.wheatgenetics.javalib.Dir.PermissionException
 * org.wheatgenetics.javalib.Dir.PermissionRequestedException
 * org.wheatgenetics.javalib.Utils
 *
 * org.wheatgenetics.androidlibrary.R
 * org.wheatgenetics.androidlibrary.RequestDir
 * org.wheatgenetics.androidlibrary.Utils
 * org.wheatgenetics.changelog.ChangeLogAlertDialog
 * org.wheatgenetics.sharedpreferences.SharedPreferences
 * org.wheatgenetics.zxing.BarcodeScanner
 *
 * org.wheatgenetics.coordinate.database.CurrentGridUniqueEntriesTable
 * org.wheatgenetics.coordinate.database.CurrentGridUniqueGridsTable
 * org.wheatgenetics.coordinate.database.EntriesTable
 * org.wheatgenetics.coordinate.database.GridsTable
 * org.wheatgenetics.coordinate.database.ProjectsTable
 * org.wheatgenetics.coordinate.database.TemplatesTable
 *
 * org.wheatgenetics.coordinate.display.GridDisplayFragment
 * org.wheatgenetics.coordinate.display.GridDisplayFragment.Handler
 *
 * org.wheatgenetics.coordinate.gc.GridCreator
 * org.wheatgenetics.coordinate.gc.GridCreator.Handler
 *
 * org.wheatgenetics.coordinate.model.BaseJoinedGridModels
 * org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel
 * org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.Checker
 * org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.CheckException
 * org.wheatgenetics.coordinate.model.CurrentGridUniqueEntryModels
 * org.wheatgenetics.coordinate.model.DisplayModel
 * org.wheatgenetics.coordinate.model.ElementModel
 * org.wheatgenetics.coordinate.model.EntireProjectProjectExporter
 * org.wheatgenetics.coordinate.model.EntryModel
 * org.wheatgenetics.coordinate.model.EntryModels.FilledHandler
 * org.wheatgenetics.coordinate.model.ExcludedEntryModel
 * org.wheatgenetics.coordinate.model.GridExporter
 * org.wheatgenetics.coordinate.model.GridExporter.Helper
 * org.wheatgenetics.coordinate.model.IncludedEntryModel
 * org.wheatgenetics.coordinate.model.JoinedGridModel
 * org.wheatgenetics.coordinate.model.Model
 * org.wheatgenetics.coordinate.model.PerGridProjectExporter
 * org.wheatgenetics.coordinate.model.ProjectModel
 * org.wheatgenetics.coordinate.model.TemplateExporter
 * org.wheatgenetics.coordinate.model.TemplateModel
 * org.wheatgenetics.coordinate.model.TemplateModels
 * org.wheatgenetics.coordinate.model.TemplateType
 *
 * org.wheatgenetics.coordinate.nisl.NavigationItemSelectedListener
 * org.wheatgenetics.coordinate.nisl.NavigationItemSelectedListener.Handler
 *
 * org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields
 *
 * org.wheatgenetics.coordinate.DataEntryFragment
 * org.wheatgenetics.coordinate.DataEntryFragment.Handler
 * org.wheatgenetics.coordinate.R
 * org.wheatgenetics.coordinate.TemplatesDir
 * org.wheatgenetics.coordinate.Types
 * org.wheatgenetics.coordinate.UniqueAlertDialog
 * org.wheatgenetics.coordinate.Utils
 * org.wheatgenetics.coordinate.Utils.Advancement
 * org.wheatgenetics.coordinate.Utils.ProjectExport
 * org.wheatgenetics.coordinate.Utils.TypeOfUniqueness
 */
public class MainActivity extends android.support.v7.app.AppCompatActivity implements
org.wheatgenetics.coordinate.display.GridDisplayFragment.Handler,
org.wheatgenetics.coordinate.model.EntryModels.FilledHandler    ,
org.wheatgenetics.coordinate.DataEntryFragment.Handler          ,
org.wheatgenetics.coordinate.gc.GridCreator.Handler             ,
org.wheatgenetics.coordinate.model.GridExporter.Helper
{
    // region Constants
    private static final java.lang.String COORDINATE_DIR_NAME = "Coordinate",
        BLANK_HIDDEN_FILE_NAME = ".coordinate";
    private static final int CONFIGURE_NAVIGATION_DRAWER = 10, IMPORT_TEMPLATE = 11,
        EXPORT_TEMPLATE = 12, CONFIGURE_NAVIGATION_VIEW = 13, EXPORT_GRID_REQUEST_CODE = 30,
        EXPORT_PROJECT_REQUEST_CODE = 31;
    // endregion

    // region Fields
    private android.support.v4.widget.DrawerLayout drawerLayout;
    private android.view.MenuItem manageGridMenuItem = null, exportGridMenuItem = null;
    private android.view.MenuItem templateMenuItem = null, importTemplateMenuItem = null,
        exportTemplateMenuItem = null, deleteTemplateMenuItem = null;
    private android.view.MenuItem projectMenuItem = null, manageProjectMenuItem = null,
        exportProjectMenuItem = null;
    private android.media.MediaPlayer gridEndMediaPlayer = null,
        rowOrColumnEndMediaPlayer = null, disallowedDuplicateMediaPlayer = null;       // lazy loads

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
    private org.wheatgenetics.coordinate.model.JoinedGridModel        joinedGridModel  = null;
    private org.wheatgenetics.coordinate.model.ProjectModel           projectModel     = null;
    private org.wheatgenetics.coordinate.gc.GridCreator               gridCreator      = null; // ll
    private org.wheatgenetics.coordinate.model.GridExporter           gridExporter     = null;
    private org.wheatgenetics.coordinate.model.TemplateExporter       templateExporter = null;
    private org.wheatgenetics.coordinate.model.TemplateModel          templateModel          ;
    private org.wheatgenetics.coordinate.model.PerGridProjectExporter perGridProjectExporter = null;
    private org.wheatgenetics.coordinate.model.EntireProjectProjectExporter
        entireProjectProjectExporter = null;

    private org.wheatgenetics.coordinate.display.GridDisplayFragment gridDisplayFragment;
    private org.wheatgenetics.coordinate.DataEntryFragment           dataEntryFragment  ;

    private java.lang.String versionName, fileName, directoryName;
    @android.support.annotation.IntRange(from = 1) private long projectId;

    private org.wheatgenetics.coordinate.UniqueAlertDialog currentGridUniqueAlertDialog = null;//ll
    // endregion

    // region Private Methods
    // region Table Private Methods
    @android.support.annotation.NonNull
    private org.wheatgenetics.coordinate.database.TemplatesTable templatesTable()
    {
        if (null == this.templatesTableInstance) this.templatesTableInstance =
            new org.wheatgenetics.coordinate.database.TemplatesTable(this);
        return this.templatesTableInstance;
    }

    @android.support.annotation.NonNull
    private org.wheatgenetics.coordinate.database.ProjectsTable projectsTable()
    {
        if (null == this.projectsTableInstance) this.projectsTableInstance =
            new org.wheatgenetics.coordinate.database.ProjectsTable(this);
        return this.projectsTableInstance;
    }

    @android.support.annotation.Nullable
    private org.wheatgenetics.coordinate.database.GridsTable gridsTable()
    {
        if (null == this.gridsTableInstance)
            if (this.getUniqueness())
                switch (this.getTypeOfUniqueness())
                {
                    case CURRENT_GRID: this.gridsTableInstance =
                        new org.wheatgenetics.coordinate.database.CurrentGridUniqueGridsTable(
                            this); break;

                    case CURRENT_PROJECT: case ALL_GRIDS: break;                             // TODO
                }
            else
                this.gridsTableInstance =
                    new org.wheatgenetics.coordinate.database.GridsTable(this);
        return this.gridsTableInstance;
    }

    @android.support.annotation.Nullable
    private org.wheatgenetics.coordinate.database.EntriesTable entriesTable()
    {
        if (null == this.entriesTableInstance)
            if (this.getUniqueness())
                switch (this.getTypeOfUniqueness())
                {
                    case CURRENT_GRID: this.entriesTableInstance =
                        new org.wheatgenetics.coordinate.database.CurrentGridUniqueEntriesTable(
                            this); break;

                    case CURRENT_PROJECT: case ALL_GRIDS: break;                             // TODO
                }
            else
                this.entriesTableInstance =
                    new org.wheatgenetics.coordinate.database.EntriesTable(this);
        return this.entriesTableInstance;
    }
    // endregion

    // region Toast Private Methods
    // region Long Toast Private Methods
    private void showLongToast(final java.lang.String text)
    { org.wheatgenetics.androidlibrary.Utils.showLongToast(this, text); }

    private void showLongToast(@android.support.annotation.StringRes final int text)
    { this.showLongToast(this.getString(text)); }
    // endregion

    // region Short Toast Private Methods
    private void showShortToast(final java.lang.String text)
    { org.wheatgenetics.androidlibrary.Utils.showShortToast(this, text); }

    private void showShortToast(@android.support.annotation.StringRes final int text)
    { this.showShortToast(this.getString(text)); }
    // endregion
    // endregion

    // region RequestDir PrivateMethods
    private org.wheatgenetics.androidlibrary.RequestDir makeRequestDir(
    final java.lang.String name, final int requestCode)
    {
        return new org.wheatgenetics.androidlibrary.RequestDir(
            /* activity            => */this,
            /* name                => */ name,
            /* blankHiddenFileName => */
                org.wheatgenetics.coordinate.MainActivity.BLANK_HIDDEN_FILE_NAME,
            /* requestCode => */ requestCode);
    }

    private void createCoordinateDirIfMissing(final int requestCode)
    throws java.io.IOException, org.wheatgenetics.javalib.Dir.PermissionException
    {
        final org.wheatgenetics.androidlibrary.RequestDir coordinateDir = this.makeRequestDir(
            org.wheatgenetics.coordinate.MainActivity.COORDINATE_DIR_NAME, requestCode);
        coordinateDir.createIfMissing();                 // throws java.io.IOException, org.wheatge-
    }                                                    //  netics.javalib.Dir.PermissionException

    /** This directory is used to transfer templates between devices. */
    private org.wheatgenetics.coordinate.TemplatesDir templatesDir(final int requestCode)
    throws java.io.IOException, org.wheatgenetics.javalib.Dir.PermissionException
    {
        this.createCoordinateDirIfMissing(requestCode);  // throws java.io.IOException, org.wheatge-
                                                         //  netics.javalib.Dir.PermissionException
        final org.wheatgenetics.coordinate.TemplatesDir templatesDir =
            new org.wheatgenetics.coordinate.TemplatesDir(
                /* activity            => */this,
                /* name                => */org.wheatgenetics
                    .coordinate.MainActivity.COORDINATE_DIR_NAME + "/Templates",
                /* blankHiddenFileName => */
                    org.wheatgenetics.coordinate.MainActivity.BLANK_HIDDEN_FILE_NAME,
                /* requestCode => */ requestCode);
        templatesDir.createIfMissing();                  // throws java.io.IOException, org.wheatge-
        return templatesDir;                             //  netics.javalib.Dir.PermissionException
    }

    /** Exported data is saved to this folder. */
    private org.wheatgenetics.androidlibrary.RequestDir exportDir(final int requestCode)
    throws java.io.IOException, org.wheatgenetics.javalib.Dir.PermissionException
    {
        this.createCoordinateDirIfMissing(requestCode);  // throws java.io.IOException, org.wheatge-
                                                         //  netics.javalib.Dir.PermissionException
        final org.wheatgenetics.androidlibrary.RequestDir exportDir = this.makeRequestDir(
            org.wheatgenetics.coordinate.MainActivity.COORDINATE_DIR_NAME + "/Export",
            requestCode);
        exportDir.createIfMissing();                     // throws java.io.IOException, org.wheatge-
        return exportDir;                                //  netics.javalib.Dir.PermissionException
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
            try
            {
                final org.wheatgenetics.coordinate.TemplatesDir templatesDir =
                    this.templatesDir(org.wheatgenetics.coordinate    // throws java.io.IOException,
                        .MainActivity.CONFIGURE_NAVIGATION_DRAWER);   //  org.wheatgenetics.javalib-
                this.importTemplateMenuItem.setEnabled(               //  .Dir.PermissionException
                    templatesDir.atLeastOneXmlFileExists() /* throws PermissionException */);
            }
            catch (final java.io.IOException | org.wheatgenetics.javalib.Dir.PermissionException e)
            {
                if (!(e instanceof org.wheatgenetics.javalib.Dir.PermissionRequestedException))
                    this.showLongToast(e.getMessage());
                return;
            }

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
            else
                this.exportProjectMenuItem.setEnabled(false);
    }

    // region configureNavHeaderMain() configureNavigationDrawer() Private Methods
    private void setTextViewText(@android.support.annotation.IdRes final int textViewId,
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
                    org.wheatgenetics.coordinate.MainActivity.INPUT_METHOD_SERVICE);
            if (null != imm) imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
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
    private void loadJoinedGridModel(
    @android.support.annotation.IntRange(from = 0) final long gridId)
    {
        if (org.wheatgenetics.coordinate.model.Model.illegal(gridId))
            this.joinedGridModel = null;
        else
        {
            final org.wheatgenetics.coordinate.database.GridsTable gridsTable = this.gridsTable();
            this.joinedGridModel = null == gridsTable ? null : gridsTable.get(gridId);
        }

        assert null != this.sharedPreferences;
        if (this.joinedGridModelIsLoaded())
            // noinspection ConstantConditions
            this.sharedPreferences.setGridId(this.joinedGridModel.getId());
        else
            this.sharedPreferences.clearGridId();
    }

    private void loadJoinedGridModelThenPopulate(
    @android.support.annotation.IntRange(from = 0) final long gridId)
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

    // region Export Grid Private Methods
    private java.lang.String getInitialGridExportFileName()
    {
        return this.joinedGridModelIsLoaded() ?
            this.joinedGridModel.getFirstOptionalFieldDatedValue() : null;
    }

    private void exportGrid()
    {
        if (this.joinedGridModelIsLoaded())
            try
            {
                final org.wheatgenetics.androidlibrary.RequestDir exportDir =
                    new org.wheatgenetics.androidlibrary.RequestDir(
                        /* activity => */this,
                        /* parent   => */ this.exportDir(                          // throws IOE, PE
                            org.wheatgenetics.coordinate.MainActivity.EXPORT_GRID_REQUEST_CODE),
                        /* name        => */ this.joinedGridModel.getTemplateTitle(),
                        /* requestCode => */
                            org.wheatgenetics.coordinate.MainActivity.EXPORT_GRID_REQUEST_CODE);
                exportDir.createIfMissing();             // throws java.io.IOException, org.wheatge-
                                                         //  netics.javalib.Dir.PermissionException
                this.gridExporter = new org.wheatgenetics.coordinate.model.GridExporter(
                    /* context    => */this,
                    /* exportFile => */ exportDir.createNewFile(   // throws org.wheatgenetics.java-
                        this.fileName + ".csv"),          //  lib.Dir.PermissionException
                    /* exportFileName => */ this.fileName,
                    /* helper         => */this);
                this.gridExporter.execute();
            }
            catch (final java.io.IOException | org.wheatgenetics.javalib.Dir.PermissionException e)
            {
                if (!(e instanceof org.wheatgenetics.javalib.Dir.PermissionRequestedException))
                    this.showLongToast(e.getMessage());
            }
    }

    private void exportGrid(final java.lang.String fileName)
    { this.fileName = fileName; this.exportGrid(); }
    // endregion
    // endregion

    // region Template Private Methods
    // region Import Template Private Methods
    private void importTemplate()
    {
        java.io.File file;
        try
        {
            final org.wheatgenetics.coordinate.TemplatesDir templatesDir =
                this.templatesDir(                        // throws IOException, PermissionException
                    org.wheatgenetics.coordinate.MainActivity.IMPORT_TEMPLATE);
            file = templatesDir.makeFile(this.fileName);  // throws IOException, PermissionException
        }
        catch (final java.io.IOException | org.wheatgenetics.javalib.Dir.PermissionException e)
        {
            if (!(e instanceof org.wheatgenetics.javalib.Dir.PermissionRequestedException))
                this.showLongToast(e.getMessage());
            file = null;
        }

        if (null != file) this.templatesTable().insert(
            org.wheatgenetics.coordinate.model.TemplateModel.makeUserDefined(file));
    }

    private void importTemplate(final java.lang.String fileName)
    { this.fileName = fileName; this.importTemplate(); }
    // endregion

    // region Export Template Private Methods
    private void exportTemplate()
    {
        if (null != this.templateModel)
        {
            java.io.File exportFile;
            try
            {
                final org.wheatgenetics.coordinate.TemplatesDir templatesDir =
                    this.templatesDir(                    // throws IOException, PermissionException
                        org.wheatgenetics.coordinate.MainActivity.EXPORT_TEMPLATE);
                exportFile = templatesDir.createNewFile(  // throws IOException, PermissionException
                    this.fileName + ".xml");
            }
            catch (final java.io.IOException | org.wheatgenetics.javalib.Dir.PermissionException e)
            {
                if (!(e instanceof org.wheatgenetics.javalib.Dir.PermissionRequestedException))
                    this.showLongToast(e.getMessage());
                exportFile = null;
            }

            if (null != exportFile)
            {
                this.templateExporter = new org.wheatgenetics.coordinate.model.TemplateExporter(
                    /* context       => */this,
                    /* exportFile    => */ exportFile        ,
                    /* templateModel => */ this.templateModel);
                this.templateExporter.execute();
            }
        }
    }

    private void exportTemplate(@android.support.annotation.NonNull
    final org.wheatgenetics.coordinate.model.TemplateModel templateModel,
    final java.lang.String                                 fileName     )
    { this.templateModel = templateModel; this.fileName = fileName; this.exportTemplate(); }
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
    @android.support.annotation.IntRange(from = 0) private long getProjectId()
    { return this.projectModelIsLoaded() ? this.projectModel.getId() : 0; }

    private void handleProjectDeleted(
    @android.support.annotation.IntRange(from = 1) final long projectId)
    {
        if (this.projectModelIsLoaded()) if (!this.projectsTable().exists(projectId))
            this.clearProjectModel();
        this.handleGridDeleted();
    }

    private void exportProject()
    {
        final org.wheatgenetics.coordinate.database.GridsTable gridsTable = this.gridsTable();
        if (null != gridsTable)
        {
            final org.wheatgenetics.coordinate.model.BaseJoinedGridModels baseJoinedGridModels =
                gridsTable.loadByProjectId(this.projectId);
            if (null != baseJoinedGridModels) if (baseJoinedGridModels.size() > 0)
            {
                final org.wheatgenetics.coordinate.Utils.ProjectExport projectExport =
                    this.getProjectExport();
                if (org.wheatgenetics.coordinate.Utils.ProjectExport.ONE_FILE_PER_GRID
                == projectExport)
                {
                    org.wheatgenetics.androidlibrary.RequestDir exportDir;

                    final org.wheatgenetics.coordinate.model.JoinedGridModel firstJoinedGridModel =
                        baseJoinedGridModels.get(0);
                    if (null == firstJoinedGridModel)
                        exportDir = null;
                    else
                        try
                        {
                            final org.wheatgenetics.androidlibrary.RequestDir parentDir =
                                new org.wheatgenetics.androidlibrary.RequestDir(
                                    /* activity => */this,
                                    /* parent   => */ this.exportDir(       // throws IOException,
                                        org.wheatgenetics                   //   PermissionException
                                            .coordinate.MainActivity.EXPORT_PROJECT_REQUEST_CODE),
                                    /* name        => */ firstJoinedGridModel.getTemplateTitle(),
                                    /* requestCode => */ org.wheatgenetics
                                        .coordinate.MainActivity.EXPORT_PROJECT_REQUEST_CODE);
                            parentDir.createIfMissing(); // throws java.io.IOException, org.wheatge-
                                                         //  netics.javalib.Dir.PermissionException
                            exportDir = new org.wheatgenetics.androidlibrary.RequestDir(
                                /* activity    => */this,
                                /* parent      => */ parentDir         ,
                                /* name        => */ this.directoryName,
                                /* requestCode => */ org.wheatgenetics.coordinate
                                    .MainActivity.EXPORT_PROJECT_REQUEST_CODE);
                            exportDir.createIfMissing(); // throws java.io.IOException, org.wheatge-
                        }                                //  netics.javalib.Dir.PermissionException
                        catch (final java.io.IOException |
                        org.wheatgenetics.javalib.Dir.PermissionException e)
                        {
                            if (!(e instanceof
                            org.wheatgenetics.javalib.Dir.PermissionRequestedException))
                                this.showLongToast(e.getMessage());
                            exportDir = null;
                        }

                    if (null != exportDir)
                    {
                        this.perGridProjectExporter =
                            new org.wheatgenetics.coordinate.model.PerGridProjectExporter(
                                /* baseJoinedGridModels => */ baseJoinedGridModels,
                                /* context              => */this,
                                /* exportDir            => */ exportDir         ,
                                /* exportDirectoryName  => */ this.directoryName);
                        this.perGridProjectExporter.execute();
                    }
                }
                else
                    if (org.wheatgenetics.coordinate.Utils.ProjectExport.ONE_FILE_ENTIRE_PROJECT
                    == projectExport)
                        try
                        {
                            this.entireProjectProjectExporter =
                                new org.wheatgenetics.coordinate.model.EntireProjectProjectExporter(
                                    /* baseJoinedGridModels => */ baseJoinedGridModels,
                                    /* context              => */this,
                                    /* exportDir => */ this.exportDir(       // throws IOException,
                                        org.wheatgenetics                    //  PermissionException
                                            .coordinate.MainActivity.EXPORT_PROJECT_REQUEST_CODE),
                                    /* exportFileName => */ this.directoryName);
                            this.entireProjectProjectExporter.execute();
                        }
                        catch (final java.io.IOException |
                        org.wheatgenetics.javalib.Dir.PermissionException e)
                        {
                            if (!(e instanceof
                            org.wheatgenetics.javalib.Dir.PermissionRequestedException))
                                this.showLongToast(e.getMessage());
                        }
            }
        }
    }

    private void exportProject(@android.support.annotation.IntRange(from = 1) final long projectId,
    final java.lang.String directoryName)
    { this.projectId = projectId; this.directoryName = directoryName; this.exportProject(); }
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
        final org.wheatgenetics.coordinate.TemplatesDir templatesDir;
        try
        {
            templatesDir = this.templatesDir(             // throws IOException, PermissionException
                org.wheatgenetics.coordinate.MainActivity.CONFIGURE_NAVIGATION_VIEW);
        }
        catch (final java.io.IOException | org.wheatgenetics.javalib.Dir.PermissionException e)
        {
            if (!(e instanceof org.wheatgenetics.javalib.Dir.PermissionRequestedException))
                this.showLongToast(e.getMessage());
            return;
        }

        final android.view.Menu menu;
        {
            final android.support.design.widget.NavigationView navigationView =
                this.findViewById(org.wheatgenetics.coordinate.R.id.nav_view);  // From layout/acti-
                                                                                //  vity_main.xml.
            assert null != navigationView; navigationView.setNavigationItemSelectedListener(
                this.navigationItemSelectedListener =
                    new org.wheatgenetics.coordinate.nisl.NavigationItemSelectedListener(
                        /* activity                  => */this,
                        /* createTemplateRequestCode => */
                            org.wheatgenetics.coordinate.Types.CREATE_TEMPLATE,
                        /* clickUniquenessRequestCode => */
                            org.wheatgenetics.coordinate.Types.UNIQUENESS_CLICKED,
                        /* templatesDir => */ templatesDir    ,
                        /* versionName  => */ this.versionName,
                        /* handler      => */ new org.wheatgenetics.coordinate.nisl
                            .NavigationItemSelectedListener.Handler()
                            {
                                @java.lang.Override public void createGrid()
                                { org.wheatgenetics.coordinate.MainActivity.this.createGrid(); }

                                @java.lang.Override public boolean joinedGridModelIsLoaded()
                                {
                                    return org.wheatgenetics.coordinate
                                        .MainActivity.this.joinedGridModelIsLoaded();
                                }

                                @java.lang.Override public void loadGrid(
                                @android.support.annotation.IntRange(from = 1) final long gridId)
                                {
                                    org.wheatgenetics.coordinate.MainActivity
                                        .this.loadJoinedGridModelThenPopulate(gridId);
                                }

                                @java.lang.Override public void deleteGrid()
                                { org.wheatgenetics.coordinate.MainActivity.this.deleteGrid(); }

                                @java.lang.Override
                                public java.lang.String getInitialGridExportFileName()
                                {
                                    return org.wheatgenetics.coordinate
                                        .MainActivity.this.getInitialGridExportFileName();
                                }

                                @java.lang.Override
                                public void exportGrid(final java.lang.String fileName)
                                {
                                    org.wheatgenetics.coordinate
                                        .MainActivity.this.exportGrid(fileName);
                                }


                                @java.lang.Override
                                public void importTemplate(final java.lang.String fileName)
                                {
                                    org.wheatgenetics.coordinate
                                        .MainActivity.this.importTemplate(fileName);
                                }

                                @java.lang.Override public void exportTemplate(
                                @android.support.annotation.NonNull final
                                    org.wheatgenetics.coordinate.model.TemplateModel templateModel,
                                final java.lang.String fileName)
                                {
                                    org.wheatgenetics.coordinate.MainActivity
                                        .this.exportTemplate(templateModel, fileName);
                                }

                                @java.lang.Override public void handleGridDeleted()
                                {
                                    org.wheatgenetics.coordinate
                                        .MainActivity.this.handleGridDeleted();
                                }


                                @java.lang.Override public long getProjectId()
                                {
                                    return org.wheatgenetics.coordinate
                                        .MainActivity.this.getProjectId();
                                }

                                @java.lang.Override public boolean projectModelIsLoaded()
                                {
                                    return org.wheatgenetics.coordinate
                                        .MainActivity.this.projectModelIsLoaded();
                                }

                                @java.lang.Override public void loadProject(
                                @android.support.annotation.IntRange(from = 1) final long projectId)
                                {
                                    org.wheatgenetics.coordinate
                                        .MainActivity.this.loadProjectModel(projectId);
                                }

                                @java.lang.Override public void clearProject()
                                {
                                    org.wheatgenetics.coordinate
                                        .MainActivity.this.clearProjectModel();
                                }

                                @java.lang.Override public void handleProjectDeleted(
                                @android.support.annotation.IntRange(from = 1) final long projectId)
                                {
                                    org.wheatgenetics.coordinate
                                        .MainActivity.this.handleProjectDeleted(projectId);
                                }

                                @java.lang.Override public void exportProject(
                                @android.support.annotation.IntRange(from = 1) final long projectId,
                                final java.lang.String directoryName)
                                {
                                    org.wheatgenetics.coordinate.MainActivity
                                        .this.exportProject(projectId, directoryName);
                                }


                                @java.lang.Override public void closeDrawer()
                                { org.wheatgenetics.coordinate.MainActivity.this.closeDrawer(); }
                            },
                        /* versionOnClickListener => */ new android.view.View.OnClickListener()
                            {
                                @java.lang.Override public void onClick(final android.view.View v)
                                { org.wheatgenetics.coordinate.MainActivity.this.showChangeLog(); }
                            }));

            menu = navigationView.getMenu();
        }

        assert null != menu;
        this.manageGridMenuItem = menu.findItem(org.wheatgenetics.coordinate.R.id.nav_manage_grid);
        this.exportGridMenuItem = menu.findItem(org.wheatgenetics.coordinate.R.id.nav_export_grid);

        this.templateMenuItem       = menu.findItem(org.wheatgenetics.coordinate.R.id.nav_template);
        this.importTemplateMenuItem =
            menu.findItem(org.wheatgenetics.coordinate.R.id.nav_import_template);
        this.deleteTemplateMenuItem =
            menu.findItem(org.wheatgenetics.coordinate.R.id.nav_delete_template);
        this.exportTemplateMenuItem =
            menu.findItem(org.wheatgenetics.coordinate.R.id.nav_export_template);

        this.projectMenuItem       = menu.findItem(org.wheatgenetics.coordinate.R.id.nav_project);
        this.manageProjectMenuItem =
            menu.findItem(org.wheatgenetics.coordinate.R.id.nav_manage_project);
        this.exportProjectMenuItem =
            menu.findItem(org.wheatgenetics.coordinate.R.id.nav_export_project);
    }

    // region Default SharedPreferences Private Methods
    private org.wheatgenetics.coordinate.Utils.Advancement getAdvancement()
    { return org.wheatgenetics.coordinate.Utils.getAdvancement(this); }

    private boolean getSoundOn()
    { return org.wheatgenetics.coordinate.Utils.getSoundOn(this); }

    private org.wheatgenetics.coordinate.Utils.ProjectExport getProjectExport()
    { return org.wheatgenetics.coordinate.Utils.getProjectExport(this); }

    private boolean getUniqueness()
    { return org.wheatgenetics.coordinate.Utils.getUniqueness(this); }

    private org.wheatgenetics.coordinate.Utils.TypeOfUniqueness getTypeOfUniqueness()
    { return org.wheatgenetics.coordinate.Utils.getTypeOfUniqueness(this); }
    // endregion

    // region reloadIfNecessary() Private Methods
    private boolean gridsTableNeedsReloading()
    {
        if (this.gridsTableInstance == null)
            return false;
        else
            if (this.getUniqueness())
                switch (this.getTypeOfUniqueness())
                {
                    case CURRENT_GRID: return !(this.gridsTableInstance instanceof
                        org.wheatgenetics.coordinate.database.CurrentGridUniqueGridsTable);

                    case CURRENT_PROJECT: case ALL_GRIDS: default: return true;              // TODO
                }
            else
                return this.gridsTableInstance instanceof
                    org.wheatgenetics.coordinate.database.CurrentGridUniqueGridsTable;       // TODO
    }

    private boolean entriesTableNeedsReloading()
    {
        if (this.entriesTableInstance == null)
            return false;
        else
            if (this.getUniqueness())
                switch (this.getTypeOfUniqueness())
                {
                    case CURRENT_GRID: return !(this.entriesTableInstance instanceof
                        org.wheatgenetics.coordinate.database.CurrentGridUniqueEntriesTable);

                    case CURRENT_PROJECT: case ALL_GRIDS: default: return true;              // TODO
                }
            else
                return this.entriesTableInstance instanceof
                    org.wheatgenetics.coordinate.database.CurrentGridUniqueEntriesTable;     // TODO
    }

    private boolean joinedGridModelNeedsReloading()
    {
        if (this.joinedGridModelIsLoaded())
            if (this.getUniqueness())
                switch (this.getTypeOfUniqueness())
                {
                    case CURRENT_GRID: return !(this.joinedGridModel instanceof
                        org.wheatgenetics.coordinate.model.CurrentGridUniqueJoinedGridModel);

                    case CURRENT_PROJECT: case ALL_GRIDS: return true;                       // TODO

                    default: return true;
                }
            else
                return this.joinedGridModel instanceof
                    org.wheatgenetics.coordinate.model.CurrentGridUniqueJoinedGridModel;     // TODO
        else return false;
    }

    private void reloadIfNecessary()
    {
        if (this.gridsTableNeedsReloading     ()) this.gridsTableInstance   = null;
        if (this.entriesTableNeedsReloading   ()) this.entriesTableInstance = null;
        if (this.joinedGridModelNeedsReloading())
            this.loadJoinedGridModelThenPopulate(this.joinedGridModel.getId());
    }
    // endregion

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

    private void handleCurrentGridDuplicateCheckException()
    {
        if (this.getSoundOn())
        {
            if (null == this.disallowedDuplicateMediaPlayer)
                this.disallowedDuplicateMediaPlayer = android.media.MediaPlayer.create(
                    this, org.wheatgenetics.coordinate.R.raw.unsure);
            this.disallowedDuplicateMediaPlayer.start();
        }

        if (null == this.currentGridUniqueAlertDialog) this.currentGridUniqueAlertDialog =
            new org.wheatgenetics.coordinate.UniqueAlertDialog(this);
        this.currentGridUniqueAlertDialog.show(
            org.wheatgenetics.coordinate.R.string.CurrentGridUniqueAlertDialogMessage);
    }
    // endregion

    // region Overridden Methods
    @java.lang.Override protected void onCreate(final android.os.Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.setContentView(org.wheatgenetics.coordinate.R.layout.activity_main);

        this.drawerLayout = this.findViewById(
            org.wheatgenetics.coordinate.R.id.drawer_layout);      // From layout/activity_main.xml.

        // region Configure action bar.
        {
            final android.support.v7.widget.Toolbar toolbar = this.findViewById(
                org.wheatgenetics.coordinate.R.id.toolbar);         // From layout/app_bar_main.xml.
            this.setSupportActionBar(toolbar);

            {
                final android.support.v7.app.ActionBar supportActionBar =
                    this.getSupportActionBar();
                if (null != supportActionBar) supportActionBar.setTitle(null);
            }

            final android.support.v7.app.ActionBarDrawerToggle actionBarDrawerToggle =
                new android.support.v7.app.ActionBarDrawerToggle(
                    this, this.drawerLayout, toolbar,
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
            int versionCode;
            try
            {
                final android.content.pm.PackageInfo packageInfo =
                    this.getPackageManager().getPackageInfo(   // throws android.content.pm.Package-
                        this.getPackageName(),0);           //  Manager.NameNotFoundException
                assert null != packageInfo;
                versionCode = packageInfo.versionCode; this.versionName = packageInfo.versionName;
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
        assert null != this.drawerLayout;
        if (this.drawerLayout.isDrawerOpen(android.support.v4.view.GravityCompat.START))
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

    @java.lang.Override public boolean onOptionsItemSelected(final android.view.MenuItem item)
    {
        // Handle action bar item clicks here.  The action bar will automatically handle clicks on
        // the Home/Up button, so long as you specify a parent activity in AndroidManifest.xml.
        assert null != item; final int itemId = item.getItemId();

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
        final java.lang.String barcodeScannerResult =
            org.wheatgenetics.zxing.BarcodeScanner.parseActivityResult(
                requestCode, resultCode, data);
        if (null != barcodeScannerResult)
        {
            assert null != this.dataEntryFragment;
            this.dataEntryFragment.setEntry(barcodeScannerResult);
            this.saveEntry(barcodeScannerResult);
        }
        else
            if (android.app.Activity.RESULT_OK == resultCode)
            {
                assert null != data;
                switch (requestCode)
                {
                    case org.wheatgenetics.coordinate.Types.CREATE_TEMPLATE:
                        assert null != this.navigationItemSelectedListener;
                        this.navigationItemSelectedListener.setExcludedCells(data.getExtras());
                        break;

                    case org.wheatgenetics.coordinate.Types.CREATE_GRID:
                        assert null != this.gridCreator;
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
    }

    @java.lang.Override public void onRequestPermissionsResult(final int requestCode,
    @java.lang.SuppressWarnings({"CStyleArrayDeclaration"}) @android.support.annotation.NonNull
        final java.lang.String permissions [],
    @java.lang.SuppressWarnings({"CStyleArrayDeclaration"}) @android.support.annotation.NonNull
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
                    case org.wheatgenetics.coordinate.MainActivity.CONFIGURE_NAVIGATION_DRAWER:
                        this.configureNavigationDrawer(); break;

                    case org.wheatgenetics.coordinate.MainActivity.IMPORT_TEMPLATE:
                        this.importTemplate(); break;

                    case org.wheatgenetics.coordinate.MainActivity.EXPORT_TEMPLATE:
                        this.exportTemplate(); break;

                    case org.wheatgenetics.coordinate.MainActivity.CONFIGURE_NAVIGATION_VIEW:
                        this.configureNavigationView(); break;

                    case org.wheatgenetics.coordinate.MainActivity.EXPORT_GRID_REQUEST_CODE:
                        this.exportGrid(); break;

                    case org.wheatgenetics.coordinate.MainActivity.EXPORT_PROJECT_REQUEST_CODE:
                        this.exportProject(); break;
                }
    }

    @java.lang.Override protected void onDestroy()
    {
        if (null != this.disallowedDuplicateMediaPlayer)
            this.disallowedDuplicateMediaPlayer.release();
        if (null != this.rowOrColumnEndMediaPlayer) this.rowOrColumnEndMediaPlayer.release();
        if (null != this.gridEndMediaPlayer       ) this.gridEndMediaPlayer.release       ();


        if (null != this.entireProjectProjectExporter)
        {
            this.entireProjectProjectExporter.cancel();
            this.entireProjectProjectExporter = null;
        }

        if (null != this.perGridProjectExporter)
            { this.perGridProjectExporter.cancel(); this.perGridProjectExporter = null; }

        if (null != this.templateExporter)
            { this.templateExporter.cancel(); this.templateExporter = null; }

        if (null != this.gridExporter) { this.gridExporter.cancel(); this.gridExporter = null; }


        super.onDestroy();
    }

    // region org.wheatgenetics.coordinate.display.GridDisplayFragment.Handler Overridden Methods
    @java.lang.Override public org.wheatgenetics.coordinate.model.DisplayModel getDisplayModel()
    { return this.getJoinedGridModel(); }

    @java.lang.Override
    public void toggle(final org.wheatgenetics.coordinate.model.ElementModel elementModel)
    {
        final org.wheatgenetics.coordinate.database.EntriesTable entriesTable = this.entriesTable();
        if (this.joinedGridModelIsLoaded() && null != entriesTable)
        {
            final org.wheatgenetics.coordinate.model.EntryModel entryModel =
                (org.wheatgenetics.coordinate.model.EntryModel) elementModel;
            if (this.joinedGridModel instanceof
            org.wheatgenetics.coordinate.model.CurrentGridUniqueJoinedGridModel)             // TODO
            {
                final org.wheatgenetics.coordinate.model.CurrentGridUniqueJoinedGridModel
                    currentGridUniqueJoinedGridModel =
                        (org.wheatgenetics.coordinate.model.CurrentGridUniqueJoinedGridModel)
                            this.joinedGridModel;
                try
                {
                    currentGridUniqueJoinedGridModel.checkThenSetEntryModel(entryModel);   // throws
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

    @java.lang.Override public int getActiveRow()
    { assert null != this.joinedGridModel; return this.joinedGridModel.getActiveRow(); }

    @java.lang.Override public int getActiveCol()
    { assert null != this.joinedGridModel; return this.joinedGridModel.getActiveCol(); }

    @java.lang.Override public void activate(final int row, final int col)
    {
        final org.wheatgenetics.coordinate.database.GridsTable gridsTable = this.gridsTable();
        if (null != gridsTable)
        {
            if (this.joinedGridModelIsLoaded())
                if (this.joinedGridModel.setActiveRowAndActiveCol(row, col))
                    gridsTable.update(this.joinedGridModel);

            assert null != this.dataEntryFragment;
            this.dataEntryFragment.setEntry(this.getEntryValue());
        }
    }

    @java.lang.Override @android.support.annotation.Nullable
    public org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.Checker getChecker()
    {
        if (this.joinedGridModel instanceof
        org.wheatgenetics.coordinate.model.CurrentGridUniqueJoinedGridModel)                 // TODO
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

    // region org.wheatgenetics.coordinate.DataEntryFragment.Handler Overridden Methods
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
        final org.wheatgenetics.coordinate.database.EntriesTable entriesTable = this.entriesTable();
        if (this.joinedGridModelIsLoaded() && null != entriesTable)
        {
            final org.wheatgenetics.coordinate.model.EntryModel activeEntryModel =
                this.joinedGridModel.getActiveEntryModel();
            if (activeEntryModel instanceof org.wheatgenetics.coordinate.model.IncludedEntryModel)
            {
                final org.wheatgenetics.coordinate.model.IncludedEntryModel
                    activeIncludedEntryModel =
                        (org.wheatgenetics.coordinate.model.IncludedEntryModel) activeEntryModel;
                if (this.getUniqueness())
                {
                    final java.lang.String oldEntryValue = activeIncludedEntryModel.getValue();
                    final org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel
                        checkedIncludedEntryModel =
                            (org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel)
                                activeIncludedEntryModel;
                    try { checkedIncludedEntryModel.checkThenSetValue(entryValue) /* throws */; }
                    catch (final
                    org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.CheckException e)
                    {
                        if (e instanceof org.wheatgenetics.coordinate.model
                        .CurrentGridUniqueEntryModels.CurrentGridDuplicateCheckException)
                        {
                            this.handleCurrentGridDuplicateCheckException();

                            assert null != this.dataEntryFragment;
                            this.dataEntryFragment.setEntry(oldEntryValue);

                            return;
                        }
                        else
                            ;                                                                // TODO
                    }
                }
                else activeIncludedEntryModel.setValue(entryValue);
                entriesTable.insertOrUpdate(activeIncludedEntryModel);
            }
            this.goToNext(activeEntryModel);
        }
    }

    @java.lang.Override public void clearEntry() { this.saveEntry(null); }
    // endregion

    // region org.wheatgenetics.coordinate.gc.GridCreator.Handler Overridden Methods
    @java.lang.Override public void handleGridCreated(
    @android.support.annotation.IntRange(from = 1) final long gridId)
    { this.loadJoinedGridModelThenPopulate(gridId); }

    @java.lang.Override public void loadProjectModel(
    @android.support.annotation.IntRange(from = 0) final long projectId)
    {
        this.projectModel = org.wheatgenetics.coordinate.model.Model.illegal(projectId) ?
            null : this.projectsTable().get(projectId);

        assert null != this.sharedPreferences;
        if (this.projectModelIsLoaded())
            // noinspection ConstantConditions
            this.sharedPreferences.setProjectId(this.projectModel.getId());
        else
            this.sharedPreferences.clearProjectId();
    }

    @java.lang.Override public void clearProjectModel() { this.loadProjectModel(0); }
    // endregion

    // region org.wheatgenetics.coordinate.model.GridExporter.Helper Overridden Methods
    @java.lang.Override public void deleteGrid()
    {
        final org.wheatgenetics.coordinate.database.GridsTable gridsTable = this.gridsTable();
        if (null == gridsTable)
            this.showLongToast(
                org.wheatgenetics.coordinate.R.string.MainActivityGridNotDeletedToast);
        else
        {
            final org.wheatgenetics.coordinate.database.EntriesTable entriesTable =
                this.entriesTable();
            if (null == entriesTable)
                this.showShortToast(
                    org.wheatgenetics.coordinate.R.string.MainActivityEntriesOfGridFailToast);
            else
            {
                assert null != this.joinedGridModel;
                final long gridId = this.joinedGridModel.getId();

                if (entriesTable.deleteByGridId(gridId))
                    this.showShortToast(org.wheatgenetics.coordinate
                        .R.string.MainActivityEntriesOfGridSuccessToast);
                else
                    this.showShortToast(
                        org.wheatgenetics.coordinate.R.string.MainActivityEntriesOfGridFailToast);

                if (gridsTable.delete(gridId))
                {
                    this.showLongToast(
                        org.wheatgenetics.coordinate.R.string.MainActivityGridDeletedToast);
                    this.clearJoinedGridModelThenPopulate(); this.createGrid();
                }
                else
                    this.showLongToast(
                        org.wheatgenetics.coordinate.R.string.MainActivityGridNotDeletedToast);
            }
        }
    }

    @java.lang.Override
    public org.wheatgenetics.coordinate.model.JoinedGridModel getJoinedGridModel()
    { return this.joinedGridModel; }
    // endregion
    // endregion
}