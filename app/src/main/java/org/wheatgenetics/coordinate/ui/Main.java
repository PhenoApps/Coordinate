package org.wheatgenetics.coordinate.ui;

/**
 * Uses:
 * android.annotation.SuppressLint
 * android.content.Context
 * android.content.DialogInterface
 * android.content.DialogInterface.OnCancelListener
 * android.content.Intent
 * android.content.pm.PackageInfo
 * android.content.pm.PackageManager
 * android.content.res.Resources
 * android.graphics.Point
 * android.media.MediaPlayer
 * android.net.Uri
 * android.os.AsyncTask
 * android.os.Bundle
 * android.support.design.widget.NavigationView
 * android.support.v4.view.GravityCompat
 * android.support.v4.widget.DrawerLayout
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
 * android.view.View.OnKeyListener
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
 * org.wheatgenetics.androidlibrary.ProgressDialog
 * org.wheatgenetics.androidlibrary.R
 * org.wheatgenetics.androidlibrary.Utils
 * org.wheatgenetics.changelog.ChangeLogAlertDialog
 * org.wheatgenetics.sharedpreferences.SharedPreferences
 * org.wheatgenetics.zxing.BarcodeScanner
 *
 * org.wheatgenetics.coordinate.R
 *
 * org.wheatgenetics.coordinate.database.EntriesTable
 * org.wheatgenetics.coordinate.database.GridsTable
 * org.wheatgenetics.coordinate.database.TemplatesTable
 *
 * org.wheatgenetics.coordinate.model.GridModel
 * org.wheatgenetics.coordinate.model.JoinedGridModel
 * org.wheatgenetics.coordinate.model.PartialTemplateModel
 * org.wheatgenetics.coordinate.model.TemplateModel
 * org.wheatgenetics.coordinate.model.TemplateType
 *
 * org.wheatgenetics.coordinate.optionalField.OptionalField
 *
 * org.wheatgenetics.coordinate.utils.Utils
 *
 * org.wheatgenetics.coordinate.ui.tc.TemplateCreator
 * org.wheatgenetics.coordinate.ui.tc.TemplateCreator.Handler
 *
 * org.wheatgenetics.coordinate.ui.DeleteTemplateAlertDialog
 * org.wheatgenetics.coordinate.ui.DeleteTemplateAlertDialog.Handler
 * org.wheatgenetics.coordinate.ui.ExportAlertDialog
 * org.wheatgenetics.coordinate.ui.ExportAlertDialog.Handler
 * org.wheatgenetics.coordinate.ui.ImportAlertDialog
 * org.wheatgenetics.coordinate.ui.ImportAlertDialog.Handler
 * org.wheatgenetics.coordinate.ui.LoadExistingTemplateAlertDialog
 * org.wheatgenetics.coordinate.ui.LoadExistingTemplateAlertDialog.Handler
 * org.wheatgenetics.coordinate.ui.LoadTemplateAlertDialog
 * org.wheatgenetics.coordinate.ui.LoadTemplateAlertDialog.Handler
 * org.wheatgenetics.coordinate.ui.TemplateOptionsAlertDialog
 * org.wheatgenetics.coordinate.ui.TemplateOptionsAlertDialog.Handler
 * org.wheatgenetics.coordinate.ui.Utils
 */
public class Main extends android.support.v7.app.AppCompatActivity
implements android.view.View.OnClickListener, android.widget.TextView.OnEditorActionListener,
android.view.View.OnKeyListener
{
    private class Exporter extends android.os.AsyncTask<
    java.lang.Void, java.lang.String, java.lang.Boolean>
    {
        // region Fields
        private final android.content.Context                          context                     ;
        private final org.wheatgenetics.androidlibrary.ProgressDialog  progressDialog              ;
        private final org.wheatgenetics.coordinate.model.TemplateModel templateModel               ;
        private final java.lang.String                                 exportFileName, absolutePath;

        private java.lang.String message = null;
        private java.io.File     exportFile    ;
        // endregion

        // region Private Methods
        private java.io.File makeExportFile()
        {
            final java.io.File exportFile =
                new java.io.File(this.absolutePath, this.exportFileName + ".csv");
            if (this.exportFile.exists()) this.exportFile.delete();
            return exportFile;
        }

        private boolean isExcludedRow(final int row)
        { assert null != this.templateModel; return this.templateModel.isExcludedRow(row); }

        private boolean isExcludedCol(final int col)
        { assert null != this.templateModel; return this.templateModel.isExcludedCol(col); }

        private boolean isExcludedCell(final int row, final int col)
        { assert null != this.templateModel; return this.templateModel.isExcludedCell(col, row); }

        private boolean exportSeed()
        {
            java.lang.String tray_id = "", person = "", date = "";
            {
                final java.lang.String values[] = this.templateModel.optionalFieldValues(
                    new java.lang.String[] { "Tray", "Person", "date" });
                tray_id = values[0]; person = values[1]; date = values[2];
            }

            this.exportFile = this.makeExportFile();
            boolean success = false;
            try
            {
                final org.wheatgenetics.coordinate.csv.CoordinateCsvWriter csvWriter =
                    new org.wheatgenetics.coordinate.csv.CoordinateCsvWriter(
                        new java.io.FileWriter(this.exportFile, false));
                {
                    final java.lang.String header[] = {"tray_id", "cell_id", "tray_num",
                        "tray_column", "tray_row", "seed_id", "person", "date"};
                    csvWriter.writeRecord(header);
                }
                {
                    assert null != this.templateModel;
                    final int
                        cols = this.templateModel.getCols(), rows = this.templateModel.getRows();

                    for (int col = 1; col <= cols; col++)
                    {
                        for (int row = 1; row <= rows; row++)
                        {
                            csvWriter.write(tray_id                                      ); // tray id
                            csvWriter.write("%s_C%02d_R%d", this.exportFileName, col, row);  // cell_id
                            csvWriter.write(                                             );  // tray_num
                            csvWriter.write(col                                          );  // tray_column
                            csvWriter.write(row                                          );  // tray_row
                            {
                                java.lang.String value;
                                if (this.isExcludedRow(row) || this.isExcludedCol(col)
                                || this.isExcludedCell(row, col))
                                    value = "exclude";
                                else
                                    value = org.wheatgenetics.javalib.Utils.replaceIfNull(
                                        org.wheatgenetics.coordinate.ui.Main.this.getValue(
                                            row, col),
                                        "BLANK_");
                                csvWriter.write(value);  // seed_id
                            }
                            csvWriter.write(person);  // person
                            csvWriter.write(date  );  // date

                            csvWriter.endRecord();
                        }
                        this.publishProgress(org.wheatgenetics.coordinate.ui.Main.this.getString(
                            org.wheatgenetics.coordinate.R.string.exporting_column_title) + col);
                    }
                }
                csvWriter.close();
                org.wheatgenetics.androidlibrary.Utils.makeFileDiscoverable(
                    this.context, this.exportFile);
                success = true;
            }
            catch (final java.io.IOException e)
            {
                e.printStackTrace();
                this.message = org.wheatgenetics.coordinate.ui.Main.this.getString(
                    org.wheatgenetics.coordinate.R.string.export_failed);
            }

            return success;
        }

        @android.annotation.SuppressLint("DefaultLocale")
        private boolean exportDna()
        {
            java.lang.String date       = "", plate_id = "", plate_name  = ""                 ;
            java.lang.String dna_person = "", notes    = "", tissue_type = "", extraction = "";
            {
                final java.lang.String values[] = this.templateModel.optionalFieldValues(
                    new java.lang.String[] { "date", "Plate", "Plate Name",
                        "person", "Notes", "tissue_type", "extraction" });
                date       = values[0]; plate_id = values[1]; plate_name  = values[2];
                dna_person = values[3]; notes    = values[4]; tissue_type = values[5];
                extraction = values[6];
            }

            this.exportFile = this.makeExportFile();
            boolean success = false;
            try
            {
                final org.wheatgenetics.coordinate.csv.CoordinateCsvWriter csvWriter =
                    new org.wheatgenetics.coordinate.csv.CoordinateCsvWriter(
                        new java.io.FileWriter(this.exportFile, false));
                {
                    final java.lang.String header[] = {"date", "plate_id", "plate_name",
                        "sample_id", "well_A01", "well_01A", "tissue_id", "dna_person", "notes",
                        "tissue_type", "extraction"};
                    csvWriter.writeRecord(header);
                }
                {
                    assert null != this.templateModel;
                    final int
                        cols = this.templateModel.getCols(), rows = this.templateModel.getRows();

                    for (int col = 1; col <= cols; col++)
                    {
                        for (int r = 0; r < rows; r++)
                        {
                            csvWriter.write(date      );
                            csvWriter.write(plate_id  );
                            csvWriter.write(plate_name);
                            {
                                java.lang.String sample_id;
                                {
                                    final java.lang.String rowName =
                                        java.lang.Character.toString((char) ('A' + r));
                                    final java.lang.String colName =
                                        java.lang.String.format("%02d", col);

                                    sample_id = java.lang.String.format(
                                        "%s_%s%s", plate_id, rowName, colName);
                                    csvWriter.write(sample_id               );  // sample_id
                                    csvWriter.write("%s%s", rowName, colName);  // well_A01
                                    csvWriter.write("%s%s", colName, rowName);  // well_01A
                                }
                                {
                                    java.lang.String tissue_id;
                                    {
                                        final int row = r + 1;
                                        if (this.isExcludedRow(row) || this.isExcludedCol(col)
                                        || this.isExcludedCell(row, col))
                                            tissue_id = "BLANK_" + sample_id;
                                        else
                                        {
                                            tissue_id =
                                                org.wheatgenetics.coordinate.ui.Main.this.getValue(
                                                    row, col);
                                            if (null == tissue_id || tissue_id.trim().length() == 0)
                                                tissue_id = "BLANK_" + sample_id;
                                        }
                                    }
                                    csvWriter.write(tissue_id);
                                }
                            }
                            csvWriter.write(dna_person );
                            csvWriter.write(notes      );
                            csvWriter.write(tissue_type);
                            csvWriter.write(extraction );

                            csvWriter.endRecord();
                        }
                        this.publishProgress(org.wheatgenetics.coordinate.ui.Main.this.getString(
                            org.wheatgenetics.coordinate.R.string.exporting_column_title) + col);
                    }
                }
                csvWriter.close();
                org.wheatgenetics.androidlibrary.Utils.makeFileDiscoverable(
                    this.context, this.exportFile);
                success = true;
            }
            catch (final java.io.IOException e)
            {
                e.printStackTrace();
                this.message = org.wheatgenetics.coordinate.ui.Main.this.getString(
                    org.wheatgenetics.coordinate.R.string.export_failed);
            }

            return success;
        }

        private boolean exportDefault()
        {
            this.exportFile = this.makeExportFile();
            boolean success = false;
            try
            {
                final org.wheatgenetics.coordinate.csv.CoordinateCsvWriter csvWriter =
                    new org.wheatgenetics.coordinate.csv.CoordinateCsvWriter(
                        new java.io.FileWriter(this.exportFile, false));

                // Titles
                csvWriter.write("Value" );
                csvWriter.write("Column");
                csvWriter.write("Row"   );

                {
                    assert null != this.templateModel;
                    final java.lang.String names[] = this.templateModel.optionalFieldNames();
                    for (final java.lang.String name: names) csvWriter.write(name);
                }
                csvWriter.endRecord();

                {
                    final int
                        cols = this.templateModel.getCols(), rows = this.templateModel.getRows();

                    for (int col = 1; col <= cols; col++)
                    {
                        for (int row = 1; row <= rows; row++)
                        {
                            {
                                java.lang.String value;
                                if (this.isExcludedRow(row) || this.isExcludedCol(col)
                                || this.isExcludedCell(row, col))
                                    value = "exclude";
                                else
                                    value = org.wheatgenetics.javalib.Utils.makeEmptyIfNull(
                                        org.wheatgenetics.coordinate.ui.Main.this.getValue(
                                            row, col));
                                csvWriter.write(value);
                            }
                            csvWriter.write(col);
                            csvWriter.write(row);

                            {
                                final java.lang.String values[] =
                                    this.templateModel.optionalFieldValues();
                                for (final java.lang.String value : values) csvWriter.write(value);
                            }
                            csvWriter.endRecord();
                        }
                        this.publishProgress(org.wheatgenetics.coordinate.ui.Main.this.getString(
                            org.wheatgenetics.coordinate.R.string.exporting_column_title) + col);
                    }
                }
                csvWriter.close();
                org.wheatgenetics.androidlibrary.Utils.makeFileDiscoverable(
                    this.context, this.exportFile);
                success = true;
            }
            catch (final java.io.IOException e)
            {
                e.printStackTrace();
                this.message = org.wheatgenetics.coordinate.ui.Main.this.getString(
                    org.wheatgenetics.coordinate.R.string.export_failed);
            }

            return success;
        }

        private void share()
        {
            final android.content.Intent intent =
                new android.content.Intent(android.content.Intent.ACTION_SEND);

            intent.addFlags(android.content.Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

            assert null != this.exportFile; intent.putExtra(android.content.Intent.EXTRA_STREAM,
                android.net.Uri.parse(this.exportFile.getAbsolutePath()));

            intent.setType("text/plain");

            org.wheatgenetics.coordinate.ui.Main.this.startActivity(
                android.content.Intent.createChooser(intent,
                    org.wheatgenetics.coordinate.ui.Main.this.getString(
                        org.wheatgenetics.coordinate.R.string.share_file)));
        }
        // endregion

        Exporter(final android.content.Context context, final int progressDialogTitle,
        final int progressDialogMessage,
        final org.wheatgenetics.coordinate.model.TemplateModel templateModel,
        final java.lang.String exportFileName, final java.lang.String absolutePath)
        {
            super();

            this.context        = context;
            this.progressDialog = new org.wheatgenetics.androidlibrary.ProgressDialog(this.context,
                progressDialogTitle, progressDialogMessage,
                new android.content.DialogInterface.OnCancelListener()
                {
                    @java.lang.Override
                    public void onCancel(final android.content.DialogInterface dialog)
                    {
                        org.wheatgenetics.coordinate.ui.Main.Exporter.this.cancel(
                            /* mayInterruptIfRunning => */ true);
                    }
                });

            this.templateModel  = templateModel ;
            this.exportFileName = exportFileName;
            this.absolutePath   = absolutePath  ;
        }

        // region Overridden Methods
        @java.lang.Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            assert null != this.progressDialog; this.progressDialog.show();
        }

        @java.lang.Override
        protected java.lang.Boolean doInBackground(final java.lang.Void... bparams)
        {
            final org.wheatgenetics.coordinate.model.TemplateType templateType =
                this.templateModel.getType();
            if (org.wheatgenetics.coordinate.model.TemplateType.SEED == templateType)
                return this.exportSeed();
            else
                if (org.wheatgenetics.coordinate.model.TemplateType.DNA == templateType)
                    return this.exportDna();
                else
                    return this.exportDefault();
        }

        @java.lang.Override
        protected void onProgressUpdate(final java.lang.String... msg)
        {
            if (null != msg)
            {
                final java.lang.String message = msg[0];
                if (null != message)
                {
                    assert null != this.progressDialog;
                    this.progressDialog.setMessage(message);
                }
            }
        }

        @java.lang.Override
        protected void onPostExecute(final java.lang.Boolean result)
        {
            assert null != this.progressDialog; this.progressDialog.dismiss();

            // TODO: When grid is reset, make a new one.
            if (null != result && result)
            {
                org.wheatgenetics.coordinate.ui.Main.this.lastExportedGridId =  // TODO: Make into
                    org.wheatgenetics.coordinate.ui.Main.this.gridId;           // TODO:  Main method.
                org.wheatgenetics.coordinate.ui.Main.this.alert(
                    /* message     => */ org.wheatgenetics.coordinate.R.string.export_success,
                    /* yesRunnable => */ new java.lang.Runnable()
                    {
                        @java.lang.Override
                        public void run()
                        {
                            org.wheatgenetics.coordinate.ui.Main.this.confirm(
                                /* message => */ org.wheatgenetics.coordinate.R.string.clear_grid,
                                /* yesRunnable => */ new java.lang.Runnable()
                                {
                                    @java.lang.Override
                                    public void run()
                                    {
                                        try
                                        {
                                            if (org.wheatgenetics.coordinate.ui.
                                            Main.this.entriesTable().deleteByGrid(
                                            org.wheatgenetics.coordinate.ui.Main.this.gridId))
                                                org.wheatgenetics.coordinate.ui.
                                                    Main.this.populateUI();
                                            else
                                                org.wheatgenetics.coordinate.ui.Main.this.
                                                    showLongToast(org.wheatgenetics.coordinate.R.
                                                        string.clear_fail);
                                        }
                                        catch (final java.lang.Exception e)
                                        {
                                            org.wheatgenetics.coordinate.ui.Main.this.
                                                showLongToast(org.wheatgenetics.coordinate.R.string.
                                                    clear_fail);
                                            return;
                                        }
                                        org.wheatgenetics.coordinate.ui.Main.Exporter.this.share();
                                    }
                                },
                                /* noRunnable => */ new java.lang.Runnable()
                                {
                                    @java.lang.Override
                                    public void run()
                                    { org.wheatgenetics.coordinate.ui.Main.Exporter.this.share(); }
                                });
                        }
                    });
            }
            else
                org.wheatgenetics.coordinate.ui.Main.this.alert(
                    /* title         => */ org.wheatgenetics.coordinate.R.string.export        ,
                    /* message       => */ this.message                                        ,
                    /* messageIfNull => */ org.wheatgenetics.coordinate.R.string.export_no_data);
        }
        // endregion
    }

    // region Constants
    private static final int STATE_NORMAL = 0, STATE_DONE = 1, STATE_ACTIVE = 2, STATE_INACTIVE = 3;
    private static final int MODE_DNA     = 0, MODE_SAVED = 1, MODE_DEFAULT = 2                    ;
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

    private long             gridId    =  0             ;
    private java.lang.String gridTitle = ""             ;
    private int              mCurRow   =  1, mCurCol = 1;

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

    private org.wheatgenetics.androidlibrary.Dir          exportDir                ;
    private org.wheatgenetics.coordinate.ui.Main.Exporter exporter           = null;
    private long                                          lastExportedGridId =   -1;

    private org.wheatgenetics.coordinate.ui.tc.TemplateCreator templateCreator = null;

    // region AlertDialog Fields
    private org.wheatgenetics.coordinate.ui.TemplateOptionsAlertDialog
        templateOptionsAlertDialog = null;
    private org.wheatgenetics.coordinate.ui.LoadExistingTemplateAlertDialog
        loadExistingTemplateAlertDialog = null;
    private org.wheatgenetics.coordinate.ui.DeleteTemplateAlertDialog
        deleteTemplateAlertDialog = null;
    private org.wheatgenetics.coordinate.ui.ExportAlertDialog exportAlertDialog = null;
    private org.wheatgenetics.coordinate.ui.ImportAlertDialog importAlertDialog = null;
    private org.wheatgenetics.coordinate.ui.LoadTemplateAlertDialog
        loadSeedTrayTemplateAlertDialog = null, loadTemplateAlertDialog = null;
    // endregion
    // endregion

    public static java.lang.String getTag(final int r, final int c)
    { return java.lang.String.format(java.util.Locale.US, "tag_%d_%d", r, c); }

    // region Toast Methods
    private void showLongToast(final java.lang.String text)
    { org.wheatgenetics.androidlibrary.Utils.showLongToast(this, text); }

    private void showLongToast(final int text) { this.showLongToast(this.getString(text)); }

    private void showShortToast(final java.lang.String text)
    { org.wheatgenetics.androidlibrary.Utils.showShortToast(this, text); }

    private void showShortToast(final int text) { this.showShortToast(this.getString(text)); }
    // endregion

    private org.wheatgenetics.coordinate.optionalField.CheckedOptionalFields
    makeCheckedOptionalFields()
    { assert null != this.templateModel; return this.templateModel.makeCheckedOptionalFields(); }

    // region Utils AlertDialog Methods
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

    // region Table Methods
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
        // this.cellIDEditText.setOnKeyListener(this);

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

            new org.wheatgenetics.androidlibrary.Dir(this, coordinateDirName,
                blankHiddenFileName).createIfMissing();

            // Exported data is saved to this folder.
            this.exportDir = new org.wheatgenetics.androidlibrary.Dir(
                this, coordinateDirName + "/Export", blankHiddenFileName);
            this.exportDir.createIfMissing();

            // This directory will be used in the future to transfer templates between devices.
            new org.wheatgenetics.androidlibrary.Dir(this, coordinateDirName + "/Templates",
                blankHiddenFileName).createIfMissing();
        }

        if (this.sharedPreferences.currentGridIsSet())
            this.loadTemplateAndGridAndPopulateUI(this.sharedPreferences.getCurrentGrid(), true);
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
        if (null != this.exporter)
        {
            this.exporter.cancel(/* mayInterruptIfRunning => */ true);
            this.exporter = null;
        }
        super.onDestroy();
    }

    // region android.view.View.OnClickListener Overridden Method
    @java.lang.Override
    public void onClick(final android.view.View v)      // v is a cell.
    {                                                   // TODO: Don't toggle already selected cell.
        int c = -1, r = -1;
        {
            java.lang.Object obj;

            assert null != v;
            obj = v.getTag(org.wheatgenetics.coordinate.R.string.cell_col);
            if (obj instanceof java.lang.Integer) c = (java.lang.Integer) obj;

            obj = v.getTag(org.wheatgenetics.coordinate.R.string.cell_row);
            if (obj instanceof java.lang.Integer) r = (java.lang.Integer) obj;
        }

        if (this.isExcludedRow(r) || this.isExcludedCol(c) || this.isExcludedCell(r, c))
        {
            assert null != this.cellIDEditText; this.cellIDEditText.setText("");
            return;
        }

        if (-1 != c && -1 != r)
        {
            mCurRow = r;
            mCurCol = c;

            java.lang.String value = this.getValue(mCurRow, mCurCol);

            if (null != value && value.contains("exclude")) return;

            this.setCellState(v, STATE_ACTIVE);

            assert null != this.cellIDEditText;
            this.cellIDEditText.setSelectAllOnFocus(true);
            this.cellIDEditText.setText(org.wheatgenetics.javalib.Utils.makeEmptyIfNull(value));
            this.cellIDEditText.selectAll();
            this.cellIDEditText.requestFocus();
        }

        this.resetCurrentCell();
        this.currentCellView = v;
    }
    // endregion

    // region android.widget.TextView.OnEditorActionListener Overridden Method
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
                if (android.view.KeyEvent.ACTION_DOWN   == event.getAction()
                &&  android.view.KeyEvent.KEYCODE_ENTER == event.getKeyCode())
                    return this.saveData();
                else
                    return false;
    }
    // endregion

    // region android.view.View.OnKeyListener Overridden Method
    @java.lang.Override
    public boolean onKey(final android.view.View v, final int keyCode,
    final android.view.KeyEvent event)
    {
        assert null != event;
        if (android.view.KeyEvent.ACTION_DOWN   == event.getAction()
        &&  android.view.KeyEvent.KEYCODE_ENTER == keyCode          )
            return this.saveData();
        else
            return false;
    }
    // endregion
    // endregion

    // region Operations
    private long insertGrid(
    final org.wheatgenetics.coordinate.model.PartialTemplateModel partialTemplateModel)
    {
        assert null != partialTemplateModel;
        return this.gridsTable().insert(new org.wheatgenetics.coordinate.model.GridModel(
            /* temp  => */ partialTemplateModel.getId                     (),
            /* title => */ partialTemplateModel.getFirstOptionalFieldValue()));
    }

    private void loadTemplateAndGridAndPopulateUI(final long gridId,
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

    private void insertLoadTemplateAndGridAndPopulateUI()
    {
        assert null != this.templateModel;
        this.templateModel.setType(org.wheatgenetics.coordinate.model.TemplateType.DEFAULT);

        final long templateId = this.templatesTable().insert(this.templateModel);
        if (templateId > 0)
        {
            // deleteUserDefineTemplateAndItsGrids(this.templateModel); // TODO

            this.templateModel.setId(templateId);
            final long gridId = this.insertGrid(this.templateModel);
            if (gridId > 0)
                this.loadTemplateAndGridAndPopulateUI(gridId, false);
            else
                this.alert(org.wheatgenetics.coordinate.R.string.create_grid_fail);
        }
        else this.alert(org.wheatgenetics.coordinate.R.string.create_template_fail);
    }

    private boolean deleteEntriesAndGrid()
    { this.entriesTable().deleteByGrid(this.gridId); return this.gridsTable().delete(this.gridId); }

    // TODO: Shouldn't this also delete entries records associated with grids records?
    private boolean deleteUserDefineTemplateAndItsGrids(
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

        final long gridId = this.insertGrid(this.templateModel);
        if (gridId > 0)
        {
            assert null != this.optionalFieldLayout;
            this.optionalFieldLayout.setVisibility(android.view.View.VISIBLE);

            assert null != this.gridAreaLayout;
            this.gridAreaLayout.setVisibility(android.view.View.VISIBLE);

            this.loadTemplateAndGridAndPopulateUI(gridId, false);
        }
        else this.alert(org.wheatgenetics.coordinate.R.string.create_grid_fail);
    }

    private void tempLoad(final int mode)
    {
        if (org.wheatgenetics.coordinate.ui.Main.MODE_DNA == mode)
            this.loadExistingTemplate(org.wheatgenetics.coordinate.model.TemplateType.DNA);
        else
            if (org.wheatgenetics.coordinate.ui.Main.MODE_SAVED == mode)
                this.loadExistingTemplate(org.wheatgenetics.coordinate.model.TemplateType.DEFAULT);
            else
                this.insertLoadTemplateAndGridAndPopulateUI();
    }

    /**
     * First non-excluded cell.
      */
    private boolean getNextFreeCell()
    {
        {
            assert null != this.templateModel;
            final android.graphics.Point nextFreeCell = this.templateModel.nextFreeCell(
                new android.graphics.Point(this.mCurCol, this.mCurRow));
            assert null != nextFreeCell;
            this.mCurRow = nextFreeCell.y; this.mCurCol = nextFreeCell.x;
        }
        return true;
    }

    private boolean isExcludedRow(final int row)
    { assert null != this.templateModel; return this.templateModel.isExcludedRow(row); }

    private boolean isExcludedCol(final int col)
    { assert null != this.templateModel; return this.templateModel.isExcludedCol(col); }

    private java.lang.String getValue(final int row, final int col)
    {
        final org.wheatgenetics.coordinate.model.EntryModel entryModel =
            this.entriesTable().get(this.gridId, row, col);
        return null == entryModel ? null : entryModel.getValue();
    }

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
        if (null == this.templateOptionsAlertDialog) this.templateOptionsAlertDialog =
            new org.wheatgenetics.coordinate.ui.TemplateOptionsAlertDialog(this,
                new org.wheatgenetics.coordinate.ui.TemplateOptionsAlertDialog.Handler()
                {
                    @java.lang.Override
                    public void loadExistingTemplate()
                    { org.wheatgenetics.coordinate.ui.Main.this.loadExistingTemplate(); }

                    @java.lang.Override
                    public void createTemplate()
                    { org.wheatgenetics.coordinate.ui.Main.this.createTemplate(); }
                });
        this.templateOptionsAlertDialog.show();
    }

    private void newGridNow() throws org.json.JSONException
    {
        this.deleteEntriesAndGrid();

        assert null != this.templateModel;
        final org.wheatgenetics.coordinate.model.TemplateType templateType =
            this.templateModel.getType();
        if (org.wheatgenetics.coordinate.model.TemplateType.SEED == templateType)
            this.loadSeedTrayTemplate(this.templatesTable().get(templateType));
        else
            if (org.wheatgenetics.coordinate.model.TemplateType.DNA == templateType)
                this.loadTemplate(                                  // throws org.json.JSONException
                    org.wheatgenetics.coordinate.ui.Main.MODE_DNA,
                    this.templatesTable().get(templateType)      );
            else
                // reset options?
                this.loadTemplate(                                  // throws org.json.JSONException
                    org.wheatgenetics.coordinate.ui.Main.MODE_SAVED, this.templateModel);
    }

    private void loadSeedTrayTemplate(
    final org.wheatgenetics.coordinate.model.TemplateModel templateModel)
    {
        if (null == this.loadSeedTrayTemplateAlertDialog) this.loadSeedTrayTemplateAlertDialog =
            new org.wheatgenetics.coordinate.ui.LoadTemplateAlertDialog(this,
                new org.wheatgenetics.coordinate.ui.LoadTemplateAlertDialog.Handler()
                {
                    @java.lang.Override
                    public void processError(final java.lang.String message)
                    {
                        org.wheatgenetics.coordinate.ui.Main.this.showLongToast       (message);
                        org.wheatgenetics.coordinate.ui.Main.this.loadSeedTrayTemplate(
                            templateModel);
                        return;
                    }

                    @java.lang.Override
                    public void processPerson(final java.lang.String person)
                    {
                        assert null != org.wheatgenetics.coordinate.ui.Main.this.sharedPreferences;
                        org.wheatgenetics.coordinate.ui.Main.this.sharedPreferences.setPerson(
                            person);
                    }

                    @java.lang.Override
                    public void createGrid()
                    {
                        org.wheatgenetics.coordinate.ui.Main.this.loadExistingTemplate(
                            org.wheatgenetics.coordinate.model.TemplateType.SEED);
                    }
                });
        assert null != templateModel; this.loadSeedTrayTemplateAlertDialog.show(
            templateModel.getTitle(), this.makeCheckedOptionalFields(), true);
    }

    private void loadTemplate(final int mode,
    final org.wheatgenetics.coordinate.model.TemplateModel templateModel)  // TODO: Merge this method with the one above.
    {
        assert null != this.templateModel; if (this.templateModel.optionalFieldsIsEmpty())
            this.tempLoad(mode);
        else
        {
            if (null == this.loadTemplateAlertDialog) this.loadTemplateAlertDialog =
                new org.wheatgenetics.coordinate.ui.LoadTemplateAlertDialog(this,
                    new org.wheatgenetics.coordinate.ui.LoadTemplateAlertDialog.Handler()
                    {
                        @java.lang.Override
                        public void processError(final java.lang.String message)
                        {
                            org.wheatgenetics.coordinate.ui.Main.this.showLongToast(message);
                            org.wheatgenetics.coordinate.ui.Main.this.loadTemplate(
                                org.wheatgenetics.coordinate.ui.Main.MODE_DNA,
                                templateModel                                );
                        }

                        @java.lang.Override
                        public void processPerson(final java.lang.String person)
                        {
                            assert null != org.wheatgenetics.coordinate.ui.Main.this.sharedPreferences;
                            org.wheatgenetics.coordinate.ui.Main.this.sharedPreferences.setPerson(
                                person);
                        }

                        @java.lang.Override
                        public void createGrid()
                        { org.wheatgenetics.coordinate.ui.Main.this.tempLoad(mode); }
                    });
            assert null != templateModel; this.loadTemplateAlertDialog.show(
                templateModel.getTitle(), this.makeCheckedOptionalFields(),
                org.wheatgenetics.coordinate.ui.Main.MODE_DNA == mode);
        }
    }

    private void populateUI()
    {
        this.mCurRow = 1; this.mCurCol = 1; this.getNextFreeCell();

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

                    if (r == mCurRow && c == mCurCol)
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
        return this.exportDir.createNewFile(this.templateModel.getTitle());
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
    private void createNewGrid() throws org.json.JSONException
    {
        if (0 == this.gridId)
            this.loadExistingTemplateOrCreateNewTemplate();
        else
            if (this.gridId >= 0 && this.lastExportedGridId == this.gridId)
                this.newGridNow();                                  // throws org.json.JSONException
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
                            {
                                try { org.wheatgenetics.coordinate.ui.Main.this.newGridNow(); }
                                catch (final org.json.JSONException e) {}
                            }
                        });
    }

    private void deleteGrid()                     // TODO: DRY? (Compare to loadExistingTemplate().)
    {
        if (0 != this.gridId) this.confirm(
            /* message     => */ org.wheatgenetics.coordinate.R.string.delete_grid_warning,
            /* yesRunnable => */ new java.lang.Runnable()
                {
                    @java.lang.Override
                    public void run()
                    {
                        if (org.wheatgenetics.coordinate.ui.Main.this.deleteEntriesAndGrid())
                        {
                            org.wheatgenetics.coordinate.ui.Main.this.showLongToast(
                                org.wheatgenetics.coordinate.R.string.grid_deleted);
                            org.wheatgenetics.coordinate.ui.Main.this.gridId = 0;

                            assert null != org.wheatgenetics.coordinate.ui.Main.this.optionalFieldLayout;
                            org.wheatgenetics.coordinate.ui.Main.this.optionalFieldLayout.setVisibility(
                                android.view.View.INVISIBLE);

                            assert null != org.wheatgenetics.coordinate.ui.Main.this.gridAreaLayout;
                            org.wheatgenetics.coordinate.ui.Main.this.gridAreaLayout.setVisibility(
                                android.view.View.INVISIBLE);

                            org.wheatgenetics.coordinate.ui.Main.this.
                                loadExistingTemplateOrCreateNewTemplate();
                        }
                        else
                            org.wheatgenetics.coordinate.ui.Main.this.showLongToast(
                                org.wheatgenetics.coordinate.R.string.grid_not_deleted);
                    }
                });
    }

    private void createTemplate()
    {
        if (null == this.templateCreator)
            this.templateCreator = new org.wheatgenetics.coordinate.ui.tc.TemplateCreator(this,
                new org.wheatgenetics.coordinate.ui.tc.TemplateCreator.Handler()
                {
                    @java.lang.Override
                    public void handleTemplateCreated()
                    {
                        org.wheatgenetics.coordinate.ui.Main.this.loadTemplate(
                            org.wheatgenetics.coordinate.ui.Main.MODE_DEFAULT      ,
                            org.wheatgenetics.coordinate.ui.Main.this.templateModel);
                    }
                });
        this.templateCreator.create(this.templateModel);
    }

    private void loadExistingTemplate()
    {
        final org.wheatgenetics.coordinate.model.TemplateModels templateModels =
            this.templatesTable().load();
        if (null == this.loadExistingTemplateAlertDialog) this.loadExistingTemplateAlertDialog =
            new org.wheatgenetics.coordinate.ui.LoadExistingTemplateAlertDialog(this,
                new org.wheatgenetics.coordinate.ui.LoadExistingTemplateAlertDialog.Handler()
                {
                    @java.lang.Override
                    public void loadTemplate(final int which)
                    {
                        final org.wheatgenetics.coordinate.model.TemplateModel templateModel =
                            templateModels.get(which);
                        if (null != templateModel) switch (which)
                        {
                            case 0:                                                     // seed tray
                                org.wheatgenetics.coordinate.ui.Main.this.loadSeedTrayTemplate(
                                    templateModel);
                                break;

                            case 1:                                                     // dna plate
                                templateModel.makeOneRandomCell();
                                org.wheatgenetics.coordinate.ui.Main.this.loadTemplate(
                                    org.wheatgenetics.coordinate.ui.Main.MODE_DNA,
                                    templateModel                                );
                                break;

                            default:
                                org.wheatgenetics.coordinate.ui.Main.this.loadTemplate(
                                    org.wheatgenetics.coordinate.ui.Main.MODE_SAVED,
                                    templateModel                                  );
                                break;
                        }
                    }
                });
        this.loadExistingTemplateAlertDialog.show(templateModels.titles());
    }

    private void deleteTemplate()
    {
        final org.wheatgenetics.coordinate.model.TemplateModels templateModels =
            this.templatesTable().load();

        if (null == this.deleteTemplateAlertDialog) this.deleteTemplateAlertDialog =
            new org.wheatgenetics.coordinate.ui.DeleteTemplateAlertDialog(this,
                new org.wheatgenetics.coordinate.ui.DeleteTemplateAlertDialog.Handler()
                {
                    @java.lang.Override
                    public void deleteTemplate(final int which)
                    {
                        final org.wheatgenetics.coordinate.model.TemplateModel templateModel =
                            templateModels.get(which);
                        if (null != templateModel)
                            org.wheatgenetics.coordinate.ui.Main.this.confirm(
                                /* title => */
                                    org.wheatgenetics.coordinate.R.string.delete_template,
                                /* message => */
                                    org.wheatgenetics.coordinate.R.string.delete_template_warning,
                                /* yesRunnable => */ new java.lang.Runnable()
                                    {
                                        @java.lang.Override
                                        public void run()
                                        {
                                            if (org.wheatgenetics.coordinate.ui.
                                            Main.this.deleteUserDefineTemplateAndItsGrids(templateModel))
                                            {
                                                org.wheatgenetics.coordinate.ui.Main.this.
                                                    showLongToast(org.wheatgenetics.coordinate.
                                                        R.string.template_deleted);
                                                org.wheatgenetics.coordinate.ui.Main.this.
                                                    loadExistingTemplateOrCreateNewTemplate();
                                            }
                                            else
                                                org.wheatgenetics.coordinate.ui.Main.this.
                                                    showLongToast(org.wheatgenetics.coordinate.
                                                        R.string.template_not_deleted);
                                        }
                                    });
                    }
                });
        this.deleteTemplateAlertDialog.show(templateModels.titles());
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

            if (null == this.importAlertDialog) this.importAlertDialog =
                new org.wheatgenetics.coordinate.ui.ImportAlertDialog(this,
                    new org.wheatgenetics.coordinate.ui.ImportAlertDialog.Handler()
                    {
                        @Override
                        public void importGrid(final int which)
                        {
                            if (which < indexes.length) org.wheatgenetics.coordinate.ui.
                                Main.this.loadTemplateAndGridAndPopulateUI(indexes[which], false);
                        }
                    });
            this.importAlertDialog.show(names);
        }
    }

    private void exportGrid()
    {
        if (null == this.exportAlertDialog) this.exportAlertDialog =
            new org.wheatgenetics.coordinate.ui.ExportAlertDialog(this,
                new org.wheatgenetics.coordinate.ui.ExportAlertDialog.Handler()
                {
                    @java.lang.Override
                    public void exportGrid(final java.lang.String fileName)
                    {
                        if (0 == fileName.length())
                            org.wheatgenetics.coordinate.ui.Main.this.alert(
                                org.wheatgenetics.coordinate.R.string.filename_empty);
                        else
                        {
                            assert null != org.wheatgenetics.coordinate.ui.Main.this.templateModel;
                            final long templateId =
                                org.wheatgenetics.coordinate.ui.Main.this.templateModel.getId();

                            final org.wheatgenetics.coordinate.database.TemplatesTable
                                templatesTable =
                                    org.wheatgenetics.coordinate.ui.Main.this.templatesTable();

                            assert null != templatesTable;
                            if (templatesTable.exists(templateId))
                            {
                                final java.io.File exportFile =
                                    org.wheatgenetics.coordinate.ui.Main.this.createExportFile();
                                assert null != exportFile;
                                org.wheatgenetics.coordinate.ui.Main.this.exporter =
                                    new org.wheatgenetics.coordinate.ui.Main.Exporter(
                                        /* context => */ org.wheatgenetics.coordinate.ui.Main.this,
                                        /* progressDialogTitle => */
                                            org.wheatgenetics.coordinate.R.string.exporting_title,
                                        /* progressDialogMessage => */
                                            org.wheatgenetics.coordinate.R.string.exporting_body,
                                        /* templateModel  => */ templatesTable.get(templateId),
                                        /* exportFileName => */ fileName                      ,
                                        /* absolutePath   => */ exportFile.getAbsolutePath()  );
                                org.wheatgenetics.coordinate.ui.Main.this.exporter.execute();
                            }
                        }
                    }
                });
        assert null != this.templateModel;
        this.exportAlertDialog.show(this.templateModel.getFirstOptionalFieldDatedValue());
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
                msgs  = new java.lang.String[]
                    { resources.getString(org.wheatgenetics.coordinate.R.string.grid_description) };
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
            case org.wheatgenetics.coordinate.R.id.menu_new_grid:
                try                                    { this.createNewGrid(); }
                catch (final org.json.JSONException e) { return false        ; }
                break;

            case org.wheatgenetics.coordinate.R.id.menu_delete_grid : this.deleteGrid    (); break;
            case org.wheatgenetics.coordinate.R.id.menu_new_template: this.createTemplate(); break;

            case org.wheatgenetics.coordinate.R.id.menu_load_template:
                this.loadExistingTemplate(); break;

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
                    entriesTable.get(this.gridId, this.mCurRow, this.mCurCol);
                if (null == entryModel)
                    success = entriesTable.insert(new org.wheatgenetics.coordinate.model.EntryModel(
                        this.gridId, this.mCurRow, this.mCurCol, value)) > 0;
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
        android.view.View view = this.gridTableLayout.findViewWithTag(
            org.wheatgenetics.coordinate.ui.Main.getTag(mCurRow, mCurCol));

        if (null != view) this.setCellState(view, 0 == value.length() ? STATE_NORMAL : STATE_DONE);

        boolean endOfCell = false;

        assert null != this.templateModel;
        mCurRow++;
        if (this.templateModel.getRows() < mCurRow || (this.templateModel.getRows() == mCurRow
        &&  (this.isExcludedRow(this.templateModel.getRows()) || this.isExcludedCell(this.templateModel.getRows(), mCurCol))))  // TODO: Bug?
        {
            mCurRow = this.templateModel.getRows();

            mCurCol++;
            if (this.templateModel.getCols() < mCurCol || (this.templateModel.getCols() == mCurCol
            &&  (this.isExcludedCol(this.templateModel.getCols()) || this.isExcludedCell(mCurRow, this.templateModel.getCols()))))
            {
                mCurCol = this.templateModel.getCols();
                mCurCol = this.templateModel.getRows();

                endOfCell = true;
            }
            else mCurRow = 1;
        }

        if (!endOfCell)
            if (this.isExcludedRow(mCurRow) || this.isExcludedCol(mCurCol)
            ||  this.isExcludedCell(mCurRow, mCurCol)                     )
                if (!this.getNextFreeCell()) endOfCell = true;

        value = org.wheatgenetics.javalib.Utils.makeEmptyIfNull(this.getValue(mCurRow, mCurCol));

        assert null != this.cellIDEditText;
        this.cellIDEditText.setSelectAllOnFocus(true);
        this.cellIDEditText.setText(value);
        this.cellIDEditText.selectAll();
        this.cellIDEditText.requestFocus();

        view = this.gridTableLayout.findViewWithTag(
            org.wheatgenetics.coordinate.ui.Main.getTag(mCurRow, mCurCol));
        if (null != view)
            if (!this.isExcludedRow(mCurRow) && !this.isExcludedCol(mCurCol)
            &&  !this.isExcludedCell(mCurRow, mCurCol)                      )
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
            if (this.isExcludedRow(row) || this.isExcludedCol(col) || this.isExcludedCell(row, col))
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