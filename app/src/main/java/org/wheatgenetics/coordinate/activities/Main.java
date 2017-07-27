package org.wheatgenetics.coordinate.activities;

/**
 * Uses:
 * android.annotation.SuppressLint
 * android.app.Activity
 * android.app.AlertDialog
 * android.app.AlertDialog.Builder
 * android.app.ProgressDialog
 * android.content.Context
 * android.content.DialogInterface
 * android.content.DialogInterface.OnCancelListener
 * android.content.DialogInterface.OnClickListener
 * android.content.DialogInterface.OnMultiChoiceClickListener
 * android.content.Intent
 * android.content.pm.PackageInfo
 * android.content.pm.PackageManager
 * android.content.res.Resources
 * android.database.Cursor
 * android.graphics.Point
 * android.media.MediaPlayer
 * android.media.MediaScannerConnection
 * android.net.Uri
 * android.os.AsyncTask
 * android.os.Bundle
 * android.support.design.widget.NavigationView
 * android.support.v4.view.GravityCompat
 * android.support.v4.widget.DrawerLayout
 * android.support.v7.app.ActionBarDrawerToggle
 * android.support.v7.app.AppCompatActivity
 * android.support.v7.widget.Toolbar
 * android.util.Log
 * android.view.KeyEvent
 * android.view.LayoutInflater
 * android.view.Menu
 * android.view.MenuInflater
 * android.view.MenuItem
 * android.view.View
 * android.view.View.OnKeyListener
 * android.view.WindowManager
 * android.view.inputmethod.EditorInfo
 * android.widget.Button
 * android.widget.EditText
 * android.widget.LinearLayout
 * android.widget.Spinner
 * android.widget.TableLayout
 * android.widget.TableRow
 * android.widget.TextView
 * android.widget.TextView.OnEditorActionListener
 * android.widget.Toast
 *
 * org.json.JSONException
 *
 * org.wheatgenetics.about.AboutAlertDialog
 * org.wheatgenetics.about.OtherApps.Index
 * org.wheatgenetics.androidlibrary.Utils
 * org.wheatgenetics.changelog.ChangeLogAlertDialog
 * org.wheatgenetics.sharedpreferences.SharedPreferences
 *
 * org.wheatgenetics.coordinate.R
 * org.wheatgenetics.coordinate.barcodes.IntentIntegrator
 * org.wheatgenetics.coordinate.barcodes.IntentResult
 * org.wheatgenetics.coordinate.database.EntriesTable
 * org.wheatgenetics.coordinate.database.GridsTable
 * org.wheatgenetics.coordinate.database.TemplatesTable
 * org.wheatgenetics.coordinate.model.GridModel
 * org.wheatgenetics.coordinate.model.TemplateModel
 * org.wheatgenetics.coordinate.model.TemplateType
 * org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields
 * org.wheatgenetics.coordinate.optionalField.OptionalField
 * org.wheatgenetics.coordinate.utils.Constants
 * org.wheatgenetics.coordinate.utils.Utils
 */

public class Main extends android.support.v7.app.AppCompatActivity
implements android.view.View.OnClickListener, android.widget.TextView.OnEditorActionListener,
android.view.View.OnKeyListener
{
    private class Exporter extends android.os.AsyncTask<
    java.lang.Void, java.lang.String, java.lang.Boolean>
    {
        // region Fields
        private final android.content.Context context                                   ;
        private final java.lang.String        progressDialogTitle, progressDialogMessage;
        private final long                    templateId                                ;
        private final java.lang.String        exportFileName, absolutePath              ;

        private android.app.ProgressDialog                           progressDialog;
        private org.wheatgenetics.coordinate.database.TemplatesTable templatesTable;
        private java.lang.String                                     message = null;
        private java.io.File                                         exportFile    ;
        // endregion

        // region Private Methods
        private java.io.File makeExportFile()
        {
            assert null != this.absolutePath  ;
            assert null != this.exportFileName;
            final java.io.File exportFile =
                new java.io.File(this.absolutePath, this.exportFileName + ".csv");
            if (this.exportFile.exists()) this.exportFile.delete();
            return exportFile;
        }

        private boolean exportSeed()
        {
            java.lang.String tray_id = "", person = "", date = "";
            assert null != org.wheatgenetics.coordinate.activities.Main.this.nonNullOptionalFields;
            for (final org.wheatgenetics.coordinate.optionalField.OptionalField optionalField:
            org.wheatgenetics.coordinate.activities.Main.this.nonNullOptionalFields)
            {
                assert null != optionalField;
                if (optionalField.namesAreEqual("Tray"))                  // TODO: Use polymorphism?
                    tray_id = optionalField.getValue();
                else
                    if (optionalField.namesAreEqual("Person"))
                        person = optionalField.getValue().replace(" ", "_");  // TODO: Put in OptionalField?
                    else
                        if (optionalField.namesAreEqual("date")) date = optionalField.getValue();
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
                assert null != this.templatesTable;
                for (int col = 1; col <= this.templatesTable.cols; col++)  // model
                {
                    for (int row = 1; row <= this.templatesTable.rows; row++)  // model
                    {
                        csvWriter.write(tray_id                                      );  // tray id
                        csvWriter.write("%s_C%02d_R%d", this.exportFileName, col, row);  // cell_id
                        csvWriter.write(                                             );  // tray_num
                        csvWriter.write(col                                          );  // tray_column
                        csvWriter.write(row                                          );  // tray_row
                        {
                            java.lang.String value;
                            if (isExcludedRow(row) || isExcludedCol(col) || isExcludedCell(row, col))
                                value = "exclude";
                            else
                            {
                                value = getValue(
                                    org.wheatgenetics.coordinate.activities.Main.this.gridId,
                                    row, col);
                                if (value == null) value = "BLANK_";
                            }
                            csvWriter.write(value);  // seed_id
                        }
                        csvWriter.write(person);  // person
                        csvWriter.write(date  );  // date

                        csvWriter.endRecord();
                    }
                    this.publishProgress(
                        org.wheatgenetics.coordinate.activities.Main.this.getString(
                            org.wheatgenetics.coordinate.R.string.exporting_column_title) + col);
                }
                csvWriter.close();
                makeFileDiscoverable(this.exportFile,
                    org.wheatgenetics.coordinate.activities.Main.this);
                success = true;
            }
            catch (final java.io.IOException e)
            {
                e.printStackTrace();
                this.message = org.wheatgenetics.coordinate.activities.Main.this.getString(
                    org.wheatgenetics.coordinate.R.string.export_failed);
            }

            return success;
        }

        private boolean exportDna()
        {
            java.lang.String date       = "", plate_id = "", plate_name  = ""                 ;
            java.lang.String dna_person = "", notes    = "", tissue_type = "", extraction = "";
            assert null != org.wheatgenetics.coordinate.activities.Main.this.nonNullOptionalFields;
            for (final org.wheatgenetics.coordinate.optionalField.OptionalField optionalField:
            org.wheatgenetics.coordinate.activities.Main.this.nonNullOptionalFields)
            {
                assert null != optionalField;
                if (optionalField.namesAreEqual("date"))                             // TODO: Ditto.
                    date = optionalField.getValue();
                else
                    if (optionalField.namesAreEqual("Plate"))
                        plate_id = optionalField.getValue();
                    else
                        if (optionalField.namesAreEqual("Plate Name"))
                            plate_name = optionalField.getValue();
                        else
                            if (optionalField.namesAreEqual("person"))
                                dna_person = optionalField.getValue().replace(" ", "_");  // TODO: Ditto.
                            else
                                if (optionalField.namesAreEqual("Notes"))
                                    notes = optionalField.getValue();
                                else
                                    if (optionalField.namesAreEqual("tissue_type"))
                                        tissue_type = optionalField.getValue();
                                    else
                                        if (optionalField.namesAreEqual("extraction"))
                                            extraction = optionalField.getValue();
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
                assert null != this.templatesTable;
                for (int col = 1; col <= this.templatesTable.cols; col++)  // model
                {
                    for (int r = 0; r < this.templatesTable.rows; r++)  // model
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
                                    if (isExcludedRow(row) || isExcludedCol(col) || isExcludedCell(row, col))
                                        tissue_id = "BLANK_" + sample_id;
                                    else
                                    {
                                        tissue_id = getValue(
                                            org.wheatgenetics.coordinate.
                                                activities.Main.this.gridId,
                                            row, col);
                                        if (tissue_id == null || tissue_id.trim().length() == 0)
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
                    this.publishProgress(
                        org.wheatgenetics.coordinate.activities.Main.this.getString(
                            org.wheatgenetics.coordinate.R.string.exporting_column_title) + col);
                }
                csvWriter.close();
                makeFileDiscoverable(this.exportFile,
                    org.wheatgenetics.coordinate.activities.Main.this);
                success = true;
            }
            catch (final java.io.IOException e)
            {
                e.printStackTrace();
                this.message = org.wheatgenetics.coordinate.activities.Main.this.getString(
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

                assert
                    null != org.wheatgenetics.coordinate.activities.Main.this.nonNullOptionalFields;
                for (final org.wheatgenetics.coordinate.optionalField.OptionalField optionalField:
                org.wheatgenetics.coordinate.activities.Main.this.nonNullOptionalFields)
                {
                    assert null != optionalField;
                    csvWriter.write(optionalField.getName());
                }
                csvWriter.endRecord();

                for (int col = 1; col <= this.templatesTable.cols; col++)  // model
                {
                    for (int row = 1; row <= this.templatesTable.rows; row++)  // model
                    {
                        {
                            java.lang.String value;
                            if (isExcludedRow(row) || isExcludedCol(col) || isExcludedCell(row, col))
                                value = "exclude";
                            else
                            {
                                value = getValue(
                                    org.wheatgenetics.coordinate.activities.Main.this.gridId,
                                    row, col);
                                if (value == null) value = "";
                            }
                            csvWriter.write(value);
                        }
                        csvWriter.write(col);
                        csvWriter.write(row);

                        for (final
                        org.wheatgenetics.coordinate.optionalField.OptionalField optionalField:
                        org.wheatgenetics.coordinate.activities.Main.this.nonNullOptionalFields)
                        {
                            assert null != optionalField;
                            csvWriter.write(optionalField.getValue());
                        }

                        csvWriter.endRecord();
                    }
                    this.publishProgress(
                        org.wheatgenetics.coordinate.activities.Main.this.getString(
                            org.wheatgenetics.coordinate.R.string.exporting_column_title) + col);
                }
                csvWriter.close();
                makeFileDiscoverable(this.exportFile,
                    org.wheatgenetics.coordinate.activities.Main.this);
                success = true;
            }
            catch (final java.io.IOException e)
            {
                e.printStackTrace();
                this.message = org.wheatgenetics.coordinate.activities.Main.this.getString(
                    org.wheatgenetics.coordinate.R.string.export_failed);
            }

            return success;
        }

        private void share()
        {
            final android.content.Intent intent =
                new android.content.Intent(android.content.Intent.ACTION_SEND);

            intent.addFlags(android.content.Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

            assert null != this.exportFile;
            intent.putExtra(android.content.Intent.EXTRA_STREAM,
                android.net.Uri.parse(this.exportFile.getAbsolutePath()));

            intent.setType("text/plain");

            org.wheatgenetics.coordinate.activities.Main.this.startActivity(
                android.content.Intent.createChooser(intent,
                    org.wheatgenetics.coordinate.activities.Main.this.getString(
                        org.wheatgenetics.coordinate.R.string.share_file)));
        }
        // endregion

        Exporter(final android.content.Context context, final java.lang.String progressDialogTitle,
        final java.lang.String progressDialogMessage, final long templateId,
        final java.lang.String exportFileName, final java.lang.String absolutePath)
        {
            super();

            this.context               = context              ;
            this.progressDialogTitle   = progressDialogTitle  ;
            this.progressDialogMessage = progressDialogMessage;
            this.templateId            = templateId           ;
            this.exportFileName        = exportFileName       ;
            this.absolutePath          = absolutePath         ;
        }

        // region Overridden Methods
        @java.lang.Override
        protected void onPreExecute()
        {
            super.onPreExecute();

            this.progressDialog = new android.app.ProgressDialog(this.context);
            this.progressDialog.setTitle     (this.progressDialogTitle  );
            this.progressDialog.setMessage   (this.progressDialogMessage);
            this.progressDialog.setCancelable(true                      );
            this.progressDialog.setOnCancelListener(
                new android.content.DialogInterface.OnCancelListener()
                {
                    @java.lang.Override
                    public void onCancel(final android.content.DialogInterface dialog)
                    {
                        org.wheatgenetics.coordinate.activities.Main.Exporter.this.cancel(
                            /* mayInterruptIfRunning => */ true);
                    }
                });
            this.progressDialog.show();
        }

        @java.lang.Override
        protected java.lang.Boolean doInBackground(final java.lang.Void... bparams)
        {
            try
            {
                this.templatesTable = new org.wheatgenetics.coordinate.database.TemplatesTable(
                    org.wheatgenetics.coordinate.activities.Main.this);
            }
            catch (final java.lang.Exception e)
            {
                this.message = org.wheatgenetics.coordinate.activities.Main.this.getString(
                    org.wheatgenetics.coordinate.R.string.import_template_failed);
                return false;
            }

            if (!this.templatesTable.get(this.templateId))
            {
                this.message = org.wheatgenetics.coordinate.activities.Main.this.getString(
                    org.wheatgenetics.coordinate.R.string.import_template_failed);
                return false;
            }

            if (this.templatesTable.type ==
            org.wheatgenetics.coordinate.model.TemplateType.SEED.getCode())
                return this.exportSeed();
            else
                if (this.templatesTable.type ==
                org.wheatgenetics.coordinate.model.TemplateType.DNA.getCode())
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
            if (null != this.progressDialog && this.progressDialog.isShowing())
            {
                this.progressDialog.dismiss();
                this.progressDialog = null;
            }

            // TODO: When grid is reset, make a new one.
            if (null != result && result)
            {
                org.wheatgenetics.coordinate.activities.Main.this.mLastExportGridId =  // TODO: Make into
                    org.wheatgenetics.coordinate.activities.Main.this.gridId;          // TODO:  Main method.
                org.wheatgenetics.coordinate.utils.Utils.alert(this.context,
                    org.wheatgenetics.coordinate.activities.Main.this.appNameStringResource,
                    org.wheatgenetics.coordinate.activities.Main.this.getString(
                        org.wheatgenetics.coordinate.R.string.export_success),
                    new java.lang.Runnable()
                    {
                        @java.lang.Override
                        public void run()
                        {
                            org.wheatgenetics.coordinate.utils.Utils.confirm(
                                org.wheatgenetics.coordinate.activities.Main.Exporter.this.context,
                                org.wheatgenetics.coordinate.activities.Main.this.appNameStringResource,
                                org.wheatgenetics.coordinate.activities.Main.this.getString(
                                    org.wheatgenetics.coordinate.R.string.clear_grid),
                                new java.lang.Runnable()
                                {
                                    @java.lang.Override
                                    public void run()
                                    {
                                        try
                                        {
                                            final org.wheatgenetics.coordinate.database.EntriesTable
                                                entriesTable = new org.wheatgenetics.
                                                    coordinate.database.EntriesTable(org.
                                                        wheatgenetics.coordinate.
                                                            activities.Main.this);
                                            if (entriesTable.deleteByGrid(org.wheatgenetics.
                                            coordinate.activities.Main.this.gridId))
                                            {
                                                org.wheatgenetics.coordinate.activities.
                                                    Main.this.populateTemplate();
                                                org.wheatgenetics.coordinate.activities.
                                                    Main.this.showTemplateUI();
                                            }
                                            else
                                                org.wheatgenetics.coordinate.utils.Utils.toast(
                                                    org.wheatgenetics.coordinate.activities.Main.
                                                        Exporter.this.context,
                                                    org.wheatgenetics.coordinate.activities.Main.
                                                        this.getString(org.wheatgenetics.coordinate.
                                                            R.string.clear_fail));
                                        }
                                        catch (final java.lang.Exception e)
                                        {
                                            org.wheatgenetics.coordinate.utils.Utils.toast(
                                                org.wheatgenetics.coordinate.activities.Main.
                                                    Exporter.this.context,
                                                org.wheatgenetics.coordinate.activities.Main.this.
                                                    getString(org.wheatgenetics.coordinate.
                                                        R.string.clear_fail));
                                            return;
                                        }
                                        org.wheatgenetics.coordinate.
                                            activities.Main.Exporter.this.share();
                                    }
                                },
                                new java.lang.Runnable()
                                {
                                    @java.lang.Override
                                    public void run()
                                    {
                                        org.wheatgenetics.coordinate.
                                            activities.Main.Exporter.this.share();
                                    }
                                });
                        }
                    });
            }
            else
            {
                if (null == this.message)
                    this.message = org.wheatgenetics.coordinate.activities.Main.this.getString(
                        org.wheatgenetics.coordinate.R.string.export_no_data);
                org.wheatgenetics.coordinate.utils.Utils.alert(this.context,
                    org.wheatgenetics.coordinate.activities.Main.this.getString(
                        org.wheatgenetics.coordinate.R.string.export),
                    this.message);
            }
        }
        // endregion
    }

    // region Constants
    private static final int STATE_NORMAL = 0, STATE_DONE = 1, STATE_ACTIVE = 2, STATE_INACTIVE = 3;
    private static final int MODE_DNA     = 0, MODE_SAVED = 1, MODE_DEFAULT = 2;
    // endregion

    // region Fields
    // region Widget Fields
    private android.support.v4.widget.DrawerLayout       drawerLayout         ;
    private android.support.v7.app.ActionBarDrawerToggle actionBarDrawerToggle;

    private android.widget.LinearLayout mainLayout,
        gridAreaLayout, gridTableLayout, optionalFieldLayout;
    private android.widget.EditText cellIDEditText        ;
    private android.widget.TextView templateTitleTextView ;
    private android.view.View       currentCellView = null;
    // endregion

    private long             gridId     =  0             ;
    private java.lang.String mGridTitle = ""             ;
    private int              mCurRow    =  1, mCurCol = 1;

    private org.wheatgenetics.sharedpreferences.SharedPreferences sharedPreferences          ;
    private org.wheatgenetics.changelog.ChangeLogAlertDialog      changeLogAlertDialog = null;
    private org.wheatgenetics.about.AboutAlertDialog              aboutAlertDialog     = null;

    // region Template
    private org.wheatgenetics.coordinate.model.TemplateModel templateModel =
        new org.wheatgenetics.coordinate.model.TemplateModel(
            /* title        => */ ""                                                  ,
            /* type         => */ org.wheatgenetics.coordinate.model.TemplateType.SEED,
            /* rows         => */ 20                                                  ,
            /* cols         => */ 10                                                  ,
            /* colNumbering => */ true                                                ,
            /* rowNumbering => */ false                                               );
    private org.wheatgenetics.coordinate.database.TemplatesTable templatesTableInstance = null;
    // endregion

    private java.lang.String templateOptions[];

    private org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields nonNullOptionalFields;

    private org.wheatgenetics.coordinate.activities.Main.Exporter exporter          = null;
    private long                                                  mLastExportGridId =   -1;

    // region Resources Fields
    private java.lang.String appNameStringResource, okStringResource;
    // endregion
    // endregion

    // region Class Method
    private static int sendErrorLogMsg(final java.lang.Exception e)
    {
        assert null != e;
        return android.util.Log.e("Coordinate", e.getMessage());
    }
    // endregion

    private org.wheatgenetics.coordinate.optionalField.CheckedOptionalFields
    makeCheckedOptionalFields()
    {
        return new org.wheatgenetics.coordinate.optionalField.CheckedOptionalFields(
            this.nonNullOptionalFields);
    }

    private org.wheatgenetics.coordinate.database.TemplatesTable templatesTable()
    {
        if (null == this.templatesTableInstance)this.templatesTableInstance =
            new org.wheatgenetics.coordinate.database.TemplatesTable(this);
        return this.templatesTableInstance;
    }

    // region Overridden Methods
    @java.lang.Override
    protected void onCreate(final android.os.Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.setContentView(org.wheatgenetics.coordinate.R.layout.main);

        {
            final android.content.res.Resources resources = this.getResources();
            assert null != resources;
            this.appNameStringResource =
                resources.getString(org.wheatgenetics.coordinate.R.string.app_name);
            this.okStringResource = resources.getString(org.wheatgenetics.coordinate.R.string.ok);
        }

        this.nonNullOptionalFields =
            new org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields();
        this.nonNullOptionalFields.add    ("Plate Id");
        this.nonNullOptionalFields.addDate("Date"    );

        this.templateOptions = new java.lang.String[]{
            this.getResources().getString(org.wheatgenetics.coordinate.R.string.template_load),
            this.getResources().getString(org.wheatgenetics.coordinate.R.string.template_new )};

        {
            final android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar)
                this.findViewById(org.wheatgenetics.coordinate.R.id.toolbar);

            this.setSupportActionBar(toolbar);
            assert null != toolbar;
            toolbar.bringToFront();
        }

        {
            final android.support.v7.app.ActionBar supportActionBar = this.getSupportActionBar();
            if (null != supportActionBar)
            {
                supportActionBar.setTitle(null);
                supportActionBar.getThemedContext();   // TODO: This appears to do nothing.  Remove?
                supportActionBar.setDisplayHomeAsUpEnabled(true);
                supportActionBar.setHomeButtonEnabled     (true);
            }
        }

        this.sharedPreferences = new org.wheatgenetics.sharedpreferences.SharedPreferences(
            this.getSharedPreferences("Settings", 0));

        this.drawerLayout = (android.support.v4.widget.DrawerLayout)
            this.findViewById(org.wheatgenetics.coordinate.R.id.drawer_layout);
        {
            final android.support.design.widget.NavigationView navigationView =
                (android.support.design.widget.NavigationView)
                this.findViewById(org.wheatgenetics.coordinate.R.id.nvView);
            assert null != navigationView;
            navigationView.setNavigationItemSelectedListener(
                new android.support.design.widget.NavigationView.OnNavigationItemSelectedListener()
                {
                    @java.lang.Override
                    public boolean onNavigationItemSelected(final android.view.MenuItem item)
                    {
                        return
                            org.wheatgenetics.coordinate.activities.Main.this.selectNavigationItem(
                                item);
                    }
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
                            org.wheatgenetics.coordinate.activities.Main.this.findViewById(
                                org.wheatgenetics.coordinate.R.id.nameLabel);
                        assert null !=
                            org.wheatgenetics.coordinate.activities.Main.this.sharedPreferences;
                        assert null != personTextView;
                        personTextView.setText(org.wheatgenetics.coordinate.
                            activities.Main.this.sharedPreferences.getPerson());
                    }

                    {
                        final android.widget.TextView templateTitleTextView =
                            (android.widget.TextView)
                            org.wheatgenetics.coordinate.activities.Main.this.findViewById(
                                org.wheatgenetics.coordinate.R.id.templateLabel);
                        assert
                            null != org.wheatgenetics.coordinate.activities.Main.this.templateModel;
                        assert null != templateTitleTextView;
                        templateTitleTextView.setText(org.wheatgenetics.coordinate.
                            activities.Main.this.templateModel.getTitle());
                    }
                }

                @java.lang.Override
                public void onDrawerClosed(final android.view.View drawerView) {}
            };
        this.actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        assert null != this.drawerLayout;
        this.drawerLayout.setDrawerListener(this.actionBarDrawerToggle);

        this.mainLayout = (android.widget.LinearLayout) this.findViewById(org.wheatgenetics.coordinate.R.id.mainLayout);
        this.gridAreaLayout = (android.widget.LinearLayout) this.findViewById(org.wheatgenetics.coordinate.R.id.gridArea);
        this.optionalFieldLayout = (android.widget.LinearLayout) this.findViewById(org.wheatgenetics.coordinate.R.id.optionalLayout);

        this.gridTableLayout = (android.widget.TableLayout) this.findViewById(org.wheatgenetics.coordinate.R.id.dataTable);

        this.templateTitleTextView = (android.widget.TextView) this.findViewById(org.wheatgenetics.coordinate.R.id.templateText);

        this.cellIDEditText = (android.widget.EditText) this.findViewById(org.wheatgenetics.coordinate.R.id.dataEdit);
        assert null != this.cellIDEditText;
        this.cellIDEditText.setImeActionLabel(
            this.getString(org.wheatgenetics.coordinate.R.string.keyboard_save),
            android.view.KeyEvent.KEYCODE_ENTER                               );
        this.cellIDEditText.setOnEditorActionListener(this);

        // this.cellIDEditText.setOnKeyListener(this);

        assert null != this.templateModel;
        this.templateTitleTextView.setText(this.templateModel.getTitle());

        this.mainLayout.setVisibility(android.view.View.INVISIBLE);

        this.initializeTemplatesTable();
        this.createDirs();

        if (this.sharedPreferences.currentGridIsSet())
            try { this.loadGrid(this.sharedPreferences.getCurrentGrid()); }
            catch (final java.lang.Exception e) {}
        else
            this.loadExistingTemplateOrCreateNewTemplate();

        this.showTemplateUI();

        if (!this.sharedPreferences.updateVersionIsSet(this.getVersion()))
        {
            this.sharedPreferences.setUpdateVersion(this.getVersion());
            this.showChangeLog();
        }
    }

    @java.lang.Override
    protected void onPostCreate(final android.os.Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        assert null != this.actionBarDrawerToggle;
        this.actionBarDrawerToggle.syncState();
    }

    @java.lang.Override
    public boolean onCreateOptionsMenu(final android.view.Menu menu)
    {
        new android.view.MenuInflater(org.wheatgenetics.coordinate.activities.Main.this).
            inflate(org.wheatgenetics.coordinate.R.menu.mainmenu, menu);
        assert null != menu;
        menu.findItem(org.wheatgenetics.coordinate.R.id.barcode_camera).setVisible(true);
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
                return true;

            case org.wheatgenetics.coordinate.R.id.barcode_camera:
                final org.wheatgenetics.coordinate.barcodes.IntentIntegrator intentIntegrator =
                    new org.wheatgenetics.coordinate.barcodes.IntentIntegrator(this);
                intentIntegrator.initiateScan();
                break;
        }

        return true;
    }

    @java.lang.Override
    public boolean onKey(final android.view.View v, final int keyCode,
    final android.view.KeyEvent event)
    {
        assert null != event;
        if (android.view.KeyEvent.ACTION_DOWN   == event.getAction()
        &&  android.view.KeyEvent.KEYCODE_ENTER == keyCode          )
        {
            this.saveData();
            return true;
        }
        else return false;
    }

    @java.lang.Override
    public boolean onEditorAction(final android.widget.TextView v, final int actionId, final android.view.KeyEvent event)
    {
        if (android.view.inputmethod.EditorInfo.IME_ACTION_DONE == actionId)
        {
            this.saveData();
            return true;
        }
        else
            if (null != event)
                if (android.view.KeyEvent.ACTION_DOWN   == event.getAction()
                &&  android.view.KeyEvent.KEYCODE_ENTER == event.getKeyCode())
                {
                    this.saveData();
                    return true;
                }
        return false;
    }

    // TODO: Don't toggle already selected cell.
    @java.lang.Override
    public void onClick(final android.view.View v)  // v is a cell
    {
        int              c = -1;
        int              r = -1;
        java.lang.Object obj;

        assert null != v;
        obj = v.getTag(org.wheatgenetics.coordinate.R.string.cell_col);
        if (obj instanceof java.lang.Integer) c = (java.lang.Integer) obj;

        obj = v.getTag(org.wheatgenetics.coordinate.R.string.cell_row);
        if (obj instanceof java.lang.Integer) r = (java.lang.Integer) obj;

        if (this.isExcludedRow(r) || this.isExcludedCol(c) || this.isExcludedCell(r, c))
        {
            assert null != this.cellIDEditText;
            this.cellIDEditText.setText("");
            return;
        }

        if (-1 != c && -1 != r)
        {
            mCurRow = r;
            mCurCol = c;

            java.lang.String value = this.getValue(this.gridId, mCurRow, mCurCol);

            if (null != value && value.contains("exclude")) return;

            this.setCellState(v, STATE_ACTIVE);

            if (null == value) value = "";

            assert null != this.cellIDEditText;
            this.cellIDEditText.setSelectAllOnFocus(true);
            this.cellIDEditText.setText(value);
            this.cellIDEditText.selectAll();
            this.cellIDEditText.requestFocus();
        }

        this.resetCurrentCell();
        this.currentCellView = v;
    }

    @java.lang.Override
    protected void onActivityResult(final int requestCode,
    final int resultCode, final android.content.Intent data)
    {
        final org.wheatgenetics.coordinate.barcodes.IntentResult scanResult =
            org.wheatgenetics.coordinate.barcodes.IntentIntegrator.parseActivityResult(
                requestCode, resultCode, data);

        if (null != scanResult)
        {
            assert null != this.cellIDEditText;
            this.cellIDEditText.setText(scanResult.getContents());
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
    // endregion

    private int getVersion()
    {
        int v = 0;
        try { v = this.getPackageManager().getPackageInfo(this.getPackageName(), 0).versionCode; }
        catch (final android.content.pm.PackageManager.NameNotFoundException e)
        { org.wheatgenetics.coordinate.activities.Main.sendErrorLogMsg(e); }
        return v;
    }

    private void createDirs()
    {
        this.createDir(org.wheatgenetics.coordinate.utils.Constants.MAIN_PATH    );
        this.createDir(org.wheatgenetics.coordinate.utils.Constants.EXPORT_PATH  );
        this.createDir(org.wheatgenetics.coordinate.utils.Constants.TEMPLATE_PATH);
    }

    private void createDir(final java.io.File path)
    {
        final java.io.File blankFile = new java.io.File(path, ".coordinate");

        assert null != path;
        if (!path.exists())
        {
            path.mkdirs();

            try
            {
                blankFile.getParentFile().mkdirs();
                blankFile.createNewFile();
                this.makeFileDiscoverable(blankFile, this);
            }
            catch (final java.io.IOException e)
            { org.wheatgenetics.coordinate.activities.Main.sendErrorLogMsg(e); }
        }
    }

    private void showChangeLog()
    {
        if (null == this.changeLogAlertDialog)
            this.changeLogAlertDialog = new org.wheatgenetics.changelog.ChangeLogAlertDialog(
                /* context                => */ this,
                /* changeLogRawResourceId => */
                    org.wheatgenetics.coordinate.R.raw.changelog_releases);
        try                                 { this.changeLogAlertDialog.show()       ; }
        catch (final java.io.IOException e) { throw new java.lang.RuntimeException(e); }
    }

    private void resetDatabase()
    {
        org.wheatgenetics.coordinate.utils.Utils.confirm(this,
            this.getString(org.wheatgenetics.coordinate.R.string.reset_database        ),
            this.getString(org.wheatgenetics.coordinate.R.string.reset_database_message),
            new java.lang.Runnable()
            {
                @java.lang.Override
                public void run()
                {
                    org.wheatgenetics.coordinate.activities.Main.this.deleteDatabase(
                        "seedtray1.db");                                       // TODO: Encapsulate!
                    org.wheatgenetics.coordinate.database.Database.initialize(
                        org.wheatgenetics.coordinate.activities.Main.this);
                    org.wheatgenetics.coordinate.activities.Main.this.finish();
                }
            }, null);
    }

    // region Drawer Methods
    // region Subsubaction Drawer Methods
    private void loadSeedTrayTemplate(
    final org.wheatgenetics.coordinate.model.TemplateModel templateModel)
    {
        android.app.AlertDialog alertDialog;
        {
            final android.app.AlertDialog.Builder builder =
                new android.app.AlertDialog.Builder(this);
            {
                final java.util.ArrayList<android.widget.EditText> editTextArrayList =
                    new java.util.ArrayList<android.widget.EditText>();
                {
                    android.view.View gridView;
                    {
                        final android.view.LayoutInflater layoutInflater = this.getLayoutInflater();

                        gridView = layoutInflater.inflate(
                            org.wheatgenetics.coordinate.R.layout.grid_new,
                            new android.widget.LinearLayout(this), false);

                        assert null != gridView;
                        final android.widget.LinearLayout linearLayout =
                            (android.widget.LinearLayout) gridView.findViewById(
                                org.wheatgenetics.coordinate.R.id.optionalLayout);

                        final org.wheatgenetics.coordinate.optionalField.CheckedOptionalFields
                            checkedOptionalFields = this.makeCheckedOptionalFields();

                        // load options
                        assert null != linearLayout;
                        for (final org.wheatgenetics.coordinate.optionalField.OptionalField
                        optionalField : checkedOptionalFields)
                        {
                            final android.view.View optionalFieldView = layoutInflater.inflate(
                                org.wheatgenetics.coordinate.R.layout.optional_edit, linearLayout,
                                false);

                            assert null != optionalFieldView;
                            {
                                final android.widget.TextView optionalFieldTextView =
                                    (android.widget.TextView) optionalFieldView.findViewById(
                                        org.wheatgenetics.coordinate.R.id.optionText);

                                assert null != optionalFieldTextView;
                                optionalFieldTextView.setText(optionalField.getName());
                            }

                            {
                                final android.widget.EditText optionalFieldEditText =
                                    (android.widget.EditText) optionalFieldView.findViewById(
                                        org.wheatgenetics.coordinate.R.id.optionEdit);

                                assert null != optionalFieldEditText;
                                optionalFieldEditText.setText(optionalField.getValue());
                                optionalFieldEditText.setHint(optionalField.getHint());

                                editTextArrayList.add(optionalFieldEditText);
                            }

                            linearLayout.addView(optionalFieldView);
                        }
                    }
                    assert null != templateModel;
                    builder.setTitle(templateModel.getTitle());
                    builder.setView(gridView);
                }
                builder.setCancelable(false);
                builder.setPositiveButton(org.wheatgenetics.coordinate.R.string.create,
                    new android.content.DialogInterface.OnClickListener()
                    {
                        @java.lang.Override
                        public void onClick(final android.content.DialogInterface dialog,
                        final int which)
                        {
                            {
                                      int                     i               = 0;
                                final android.widget.EditText editTextArray[] =
                                    editTextArrayList.toArray(
                                        new android.widget.EditText[editTextArrayList.size()]);

                                assert null != org.wheatgenetics.coordinate.
                                    activities.Main.this.nonNullOptionalFields;
                                for (final org.wheatgenetics.coordinate.optionalField.OptionalField
                                optionalField : org.wheatgenetics.coordinate.
                                activities.Main.this.nonNullOptionalFields) // Danger: CheckedOptionalFields above but nonNullOptionalFields here.
                                {
                                    final android.widget.EditText editText = editTextArray[i];
                                    if (null != editText)
                                    {
                                        final java.lang.String value =
                                            editText.getText().toString().trim();
                                        if (0 == i && 0 == value.length())
                                        {
                                            org.wheatgenetics.coordinate.utils.Utils.toast(
                                                org.wheatgenetics.coordinate.activities.Main.this,
                                                optionalField.getHint() + org.wheatgenetics.
                                                    coordinate.activities.Main.this.getString(
                                                        org.wheatgenetics.coordinate.
                                                            R.string.not_empty));
                                            org.wheatgenetics.coordinate.activities.
                                                Main.this.loadSeedTrayTemplate(templateModel);
                                            return;
                                        }

                                        optionalField.setValue(value);
                                        if (optionalField.namesAreEqual("Person")
                                        ||  optionalField.namesAreEqual("Name"  ))
                                        {
                                            assert null != org.wheatgenetics.coordinate.activities.
                                                Main.this.sharedPreferences;
                                            org.wheatgenetics.coordinate.activities.
                                                Main.this.sharedPreferences.setPerson(
                                                optionalField.getValue());
                                        }
                                        i++;
                                    }
                                }
                            }
                            assert null != dialog;
                            dialog.cancel();
                            org.wheatgenetics.coordinate.activities.Main.this.loadExistingTemplate(
                                org.wheatgenetics.coordinate.model.TemplateType.SEED);
                        }
                    });
            }
            builder.setNegativeButton(org.wheatgenetics.coordinate.R.string.cancel,
                org.wheatgenetics.androidlibrary.Utils.cancellingOnClickListener());
            alertDialog = builder.create();
        }
        assert null != alertDialog;
        alertDialog.getWindow().setSoftInputMode(
            android.view.WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    private void tempLoad(final int mode) throws org.json.JSONException
    {
        if (org.wheatgenetics.coordinate.activities.Main.MODE_DNA == mode)
            this.loadExistingTemplate(org.wheatgenetics.coordinate.model.TemplateType.DNA);
        else
            if (org.wheatgenetics.coordinate.activities.Main.MODE_SAVED == mode)
                this.loadExistingTemplate(org.wheatgenetics.coordinate.model.TemplateType.DEFAULT);
            else
                this.createNewTemplate(org.wheatgenetics.coordinate.model.TemplateType.DEFAULT);  // throws org.json.JSONException
    }
    // endregion

    // region Subaction Drawer Methods
    private void loadExistingTemplateOrCreateNewTemplate()
    {
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle(org.wheatgenetics.coordinate.R.string.template_options)
            .setItems(this.templateOptions, new android.content.DialogInterface.OnClickListener()
                {
                    @java.lang.Override
                    public void onClick(final android.content.DialogInterface dialog,
                    final int which)
                    {
                        if (which == 0)
                            org.wheatgenetics.coordinate.activities.
                                Main.this.loadExistingTemplate();
                        else
                            org.wheatgenetics.coordinate.activities.Main.this.createNewTemplate();
                    }
                }).show();
    }

    private void newGridNow() throws org.json.JSONException
    {
        this.deleteGrid(this.gridId);

        assert null != this.templateModel;
        final org.wheatgenetics.coordinate.model.TemplateType templateType =
            this.templateModel.getType();
        if (org.wheatgenetics.coordinate.model.TemplateType.SEED == templateType)
            this.loadSeedTrayTemplate(this.templatesTable().get(templateType));
        else if (org.wheatgenetics.coordinate.model.TemplateType.DNA == templateType)
            this.inputTemplateInput(                                // throws org.json.JSONException
                org.wheatgenetics.coordinate.activities.Main.MODE_DNA,
                this.templatesTable().get(templateType)              );
        else
            // reset options?
            this.inputTemplateInput(                                // throws org.json.JSONException
                org.wheatgenetics.coordinate.activities.Main.MODE_SAVED, this.templateModel);
    }

    private void export()
    {
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        {
            android.view.View view;
            {
                final android.view.LayoutInflater layoutInflater = this.getLayoutInflater();
                view = layoutInflater.inflate(
                    org.wheatgenetics.coordinate.R.layout.file_input, null);
            }

            assert null != view;
            final android.widget.EditText fileNameEditText = (android.widget.EditText)
                view.findViewById(org.wheatgenetics.coordinate.R.id.nameEdit);

            assert null != this.nonNullOptionalFields;
            assert null != fileNameEditText          ;
            fileNameEditText.setText(this.nonNullOptionalFields.get(0).getValue() +
                "_" + org.wheatgenetics.coordinate.utils.Utils.getCurrentDate().replace(".", "_"));

            builder.setTitle(this.getString(org.wheatgenetics.coordinate.R.string.filename_set));
            builder.setView(view);
            builder.setPositiveButton(this.okStringResource,
                new android.content.DialogInterface.OnClickListener()
                {
                    @java.lang.Override
                    public void onClick(final android.content.DialogInterface dialog,
                    final int which)
                    {
                        assert null != dialog;
                        dialog.cancel();

                        final java.lang.String fileName =
                            fileNameEditText.getText().toString().trim();
                        if (fileName.length() == 0)
                            org.wheatgenetics.coordinate.utils.Utils.alert(
                                org.wheatgenetics.coordinate.activities.Main.this,
                                org.wheatgenetics.coordinate.activities.Main.this.appNameStringResource,
                                org.wheatgenetics.coordinate.activities.Main.this.getString(
                                    org.wheatgenetics.coordinate.R.string.filename_empty));
                        else
                        {
                            {
                                assert null !=
                                    org.wheatgenetics.coordinate.activities.Main.this.templateModel;
                                final java.io.File path = new java.io.File(
                                    org.wheatgenetics.coordinate.utils.Constants.EXPORT_PATH,
                                    org.wheatgenetics.coordinate.activities.
                                        Main.this.templateModel.getTitle());
                                org.wheatgenetics.coordinate.activities.Main.this.createDir(path);

                                org.wheatgenetics.coordinate.activities.Main.this.exporter =
                                    new org.wheatgenetics.coordinate.activities.Main.Exporter(
                                        /* context => */
                                            org.wheatgenetics.coordinate.activities.Main.this,
                                        /* progressDialogTitle => */ org.wheatgenetics.coordinate.
                                            activities.Main.this.getString(org.wheatgenetics.
                                                coordinate.R.string.exporting_title),
                                        /* progressDialogMessage => */ org.wheatgenetics.coordinate.
                                            activities.Main.this.getString(org.wheatgenetics.
                                                coordinate.R.string.exporting_body),
                                        /* templateId => */ org.wheatgenetics.coordinate.
                                            activities.Main.this.templateModel.getId(),
                                        /* exportFileName => */ fileName              ,
                                        /* absolutePath   => */ path.getAbsolutePath());
                            }
                            org.wheatgenetics.coordinate.activities.Main.this.exporter.execute();
                        }
                    }
                });
        }
        builder.setNegativeButton(this.getString(org.wheatgenetics.coordinate.R.string.cancel),
            org.wheatgenetics.androidlibrary.Utils.cancellingOnClickListener());
        builder.show();
    }

    //TODO merge this method with the one above
    private void inputTemplateInput(final int mode,
    final org.wheatgenetics.coordinate.model.TemplateModel templateModel)
    throws org.json.JSONException
    {
        assert null != this.nonNullOptionalFields;
        if (this.nonNullOptionalFields.isEmpty())
        {
            this.tempLoad(mode);                                    // throws org.json.JSONException
            return;
        }

        final android.view.LayoutInflater layoutInflater =
            org.wheatgenetics.coordinate.activities.Main.this.getLayoutInflater();

        final android.view.View gridView = layoutInflater.inflate(
            org.wheatgenetics.coordinate.R.layout.grid_new, new android.widget.LinearLayout(this),
            false);

        assert null != gridView;
        final android.widget.LinearLayout linearLayout = (android.widget.LinearLayout)
            gridView.findViewById(org.wheatgenetics.coordinate.R.id.optionalLayout);

        final org.wheatgenetics.coordinate.optionalField.CheckedOptionalFields
            checkedOptionalFields = this.makeCheckedOptionalFields();

        final java.util.ArrayList<android.widget.EditText> editTextArrayList =
            new java.util.ArrayList<android.widget.EditText>();

        // load options
        assert null != linearLayout;
        for (final org.wheatgenetics.coordinate.optionalField.OptionalField optionalField :
        checkedOptionalFields)
        {
            final android.view.View optionalFieldView = layoutInflater.inflate(
                org.wheatgenetics.coordinate.R.layout.optional_edit, linearLayout, false);

            assert null != optionalFieldView;
            {
                final android.widget.TextView optionalFieldTextView = (android.widget.TextView)
                    optionalFieldView.findViewById(org.wheatgenetics.coordinate.R.id.optionText);

                assert null != optionalFieldTextView;
                optionalFieldTextView.setText(optionalField.getName());
            }

            {
                final android.widget.EditText optionalFieldEditText = (android.widget.EditText)
                    optionalFieldView.findViewById(org.wheatgenetics.coordinate.R.id.optionEdit);

                assert null != optionalFieldEditText;
                optionalFieldEditText.setText(optionalField.getValue());
                optionalFieldEditText.setHint(optionalField.getHint ());

                editTextArrayList.add(optionalFieldEditText);
            }

            linearLayout.addView(optionalFieldView);
        }

        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        assert null != templateModel;
        builder.setTitle(templateModel.getTitle());
        builder.setView(gridView);
        builder.setCancelable(false);
        builder.setPositiveButton(this.getString(org.wheatgenetics.coordinate.R.string.create),
            new android.content.DialogInterface.OnClickListener()
            {
                @java.lang.Override
                public void onClick(final android.content.DialogInterface dialog, final int which)
                {
                    {
                              int                     i               = 0;
                        final android.widget.EditText editTextArray[] = editTextArrayList.toArray(
                            new android.widget.EditText[editTextArrayList.size()]);

                        assert null !=
                            org.wheatgenetics.coordinate.activities.Main.this.nonNullOptionalFields;
                        for (final org.wheatgenetics.coordinate.optionalField.OptionalField
                        optionalField :
                        org.wheatgenetics.coordinate.activities.Main.this.nonNullOptionalFields)
                        {
                            final android.widget.EditText editText = editTextArray[i];
                            if (null != editText)
                            {
                                if (org.wheatgenetics.coordinate.activities.Main.MODE_DNA == mode)
                                {
                                    final java.lang.String value =
                                        editText.getText().toString().trim();
                                    if (0 == i && 0 == value.length())
                                    {
                                        org.wheatgenetics.coordinate.utils.Utils.toast(
                                            org.wheatgenetics.coordinate.activities.Main.this,
                                            optionalField.getHint() + org.wheatgenetics.coordinate.
                                                activities.Main.this.getString(org.wheatgenetics.
                                                    coordinate.R.string.not_empty));
                                        try
                                        {
                                            org.wheatgenetics.coordinate.activities.Main.this.
                                                inputTemplateInput(org.wheatgenetics.coordinate.
                                                    activities.Main.MODE_DNA, templateModel);
                                        }
                                        catch (final java.lang.Exception e) {}
                                        return;
                                    }
                                }

                                optionalField.setValue(editText.getText().toString().trim());

                                if (optionalField.namesAreEqual("Person")
                                ||  optionalField.namesAreEqual("Name"  ))
                                {
                                    assert null != org.wheatgenetics.coordinate.activities.
                                        Main.this.sharedPreferences;
                                    org.wheatgenetics.coordinate.activities.Main.this.
                                        sharedPreferences.setPerson(optionalField.getValue());
                                }
                            }
                            i++;
                        }
                    }
                    assert null != dialog;
                    dialog.cancel();
                    try { org.wheatgenetics.coordinate.activities.Main.this.tempLoad(mode); }
                    catch (final java.lang.Exception e) {}
                }
            });

        builder.setNegativeButton(org.wheatgenetics.coordinate.R.string.cancel,
            org.wheatgenetics.androidlibrary.Utils.cancellingOnClickListener());

        final android.app.AlertDialog alertDialog = builder.create();
        assert null != alertDialog;
        alertDialog.getWindow().setSoftInputMode(
            android.view.WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        alertDialog.setCancelable(false);
        alertDialog.show();
    }
    // endregion

    // region Action Drawer Methods
    private void newGrid() throws org.json.JSONException
    {
        if (0 == this.gridId)
            this.loadExistingTemplateOrCreateNewTemplate();
        else
            if (0 <= this.gridId && mLastExportGridId == this.gridId)
                this.newGridNow();                                  // throws org.json.JSONException
            else
                org.wheatgenetics.coordinate.utils.Utils.confirm(this, this.appNameStringResource,
                    this.getString(org.wheatgenetics.coordinate.R.string.new_grid_warning),
                    new java.lang.Runnable()
                    {
                        @java.lang.Override
                        public void run()
                        { org.wheatgenetics.coordinate.activities.Main.this.export(); }
                    },
                    new java.lang.Runnable()
                    {
                        @java.lang.Override
                        public void run()
                        {
                            try { org.wheatgenetics.coordinate.activities.Main.this.newGridNow(); }
                            catch (final org.json.JSONException e) {}
                        }
                    });
    }

    private void deleteGrid()
    {
        if (0 != this.gridId) org.wheatgenetics.coordinate.utils.Utils.confirm(this,
            this.appNameStringResource,
            this.getString(org.wheatgenetics.coordinate.R.string.delete_grid_warning),
            new java.lang.Runnable()
            {
                @java.lang.Override
                public void run()
                {
                    if (org.wheatgenetics.coordinate.activities.Main.this.deleteGrid(
                    org.wheatgenetics.coordinate.activities.Main.this.gridId))
                    {
                        android.widget.Toast.makeText(
                            org.wheatgenetics.coordinate.activities.Main.this,
                            org.wheatgenetics.coordinate.activities.Main.this.getString(
                                org.wheatgenetics.coordinate.R.string.grid_deleted),
                            android.widget.Toast.LENGTH_LONG).show();
                        org.wheatgenetics.coordinate.activities.Main.this.gridId = 0;
                        org.wheatgenetics.coordinate.activities.Main.this.
                            optionalFieldLayout.setVisibility(android.view.View.INVISIBLE);
                        org.wheatgenetics.coordinate.activities.Main.this.
                            gridAreaLayout.setVisibility(android.view.View.INVISIBLE);
                        org.wheatgenetics.coordinate.activities.Main.this.
                            loadExistingTemplateOrCreateNewTemplate();
                    }
                    else
                        android.widget.Toast.makeText(
                            org.wheatgenetics.coordinate.activities.Main.this,
                            org.wheatgenetics.coordinate.activities.Main.this.getString(
                                org.wheatgenetics.coordinate.R.string.grid_not_deleted),
                            android.widget.Toast.LENGTH_LONG).show();
                }
            }, null);
    }

    private void createNewTemplate()
    {
        assert null != this.templateModel;
        this.templateModel.clearExcludes();

        this.nonNullOptionalFields =
            new org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields();
        this.nonNullOptionalFields.add    ("Identification");
        this.nonNullOptionalFields.add    ("Person"        );
        this.nonNullOptionalFields.addDate("Date"          );

        android.view.View view;
        {
            final android.view.LayoutInflater layoutInflater =
                org.wheatgenetics.coordinate.activities.Main.this.getLayoutInflater();  // TODO: Why Main.this and not this?
            view = layoutInflater.inflate(org.wheatgenetics.coordinate.R.layout.template_new,
                new android.widget.LinearLayout(this), false);
        }

        assert null != view;
        final android.widget.EditText nameTextEdit = (android.widget.EditText)
            view.findViewById(org.wheatgenetics.coordinate.R.id.nameEdit);
        final android.widget.EditText rowsTextEdit = (android.widget.EditText)
            view.findViewById(org.wheatgenetics.coordinate.R.id.rowsEdit);
        final android.widget.EditText colsTextEdit = (android.widget.EditText)
            view.findViewById(org.wheatgenetics.coordinate.R.id.colsEdit);

        assert null != nameTextEdit;
        nameTextEdit.setText("");
        assert null != rowsTextEdit;
        rowsTextEdit.setText(this.templateModel.getRowsAsString());
        assert null != colsTextEdit;
        colsTextEdit.setText(this.templateModel.getColsAsString());

        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle(this.templateOptions[1])
            .setView(view)
            .setPositiveButton(org.wheatgenetics.coordinate.R.string.next,
                new android.content.DialogInterface.OnClickListener()
                {
                    @java.lang.Override
                    public void onClick(final android.content.DialogInterface dialog, final int which)
                    {
                        assert null != dialog;
                        dialog.cancel();

                        final java.lang.String name = nameTextEdit.getText().toString().trim();
                        final java.lang.String cols = colsTextEdit.getText().toString().trim();
                        final java.lang.String rows = rowsTextEdit.getText().toString().trim();

                        assert null !=
                            org.wheatgenetics.coordinate.activities.Main.this.templateModel;
                        org.wheatgenetics.coordinate.activities.Main.this.templateModel.assign(
                            /* title => */ name,
                            /* rows  => */ rows,
                            /* cols  => */ cols);

                        if (0 == name.length())
                        {
                            android.widget.Toast.makeText(
                                org.wheatgenetics.coordinate.activities.Main.this,
                                    org.wheatgenetics.coordinate.activities.Main.this.getString(
                                    org.wheatgenetics.coordinate.R.string.template_no_name),
                                android.widget.Toast.LENGTH_LONG).show();
                            org.wheatgenetics.coordinate.activities.Main.this.createNewTemplate();
                            return;
                        }

                        if (0 == rows.length() || -1 == org.wheatgenetics.coordinate.activities.Main.this.templateModel.getRows())
                        {
                            android.widget.Toast.makeText(
                                org.wheatgenetics.coordinate.activities.Main.this,
                                org.wheatgenetics.coordinate.activities.Main.this.getString(
                                org.wheatgenetics.coordinate.R.string.no_rows),
                                android.widget.Toast.LENGTH_LONG).show();
                            org.wheatgenetics.coordinate.activities.Main.this.createNewTemplate();
                            return;
                        }

                        if (0 == cols.length() || -1 == org.wheatgenetics.coordinate.activities.Main.this.templateModel.getCols())
                        {
                            android.widget.Toast.makeText(
                                org.wheatgenetics.coordinate.activities.Main.this,
                                org.wheatgenetics.coordinate.activities.Main.this.getString(
                                org.wheatgenetics.coordinate.R.string.no_cols),
                                android.widget.Toast.LENGTH_LONG).show();
                            org.wheatgenetics.coordinate.activities.Main.this.createNewTemplate();
                            return;
                        }

                        org.wheatgenetics.coordinate.activities.Main.this.inputTemplateNewExtra();
                    }
                })
            .setNegativeButton(org.wheatgenetics.coordinate.R.string.cancel,
                new android.content.DialogInterface.OnClickListener()
                {
                    @java.lang.Override
                    public void onClick(final android.content.DialogInterface dialog, final int which)
                    {
                        assert null != dialog;
                        dialog.cancel();
                    }
                }).show();
    }

    private void loadExistingTemplate()
    {
        final org.wheatgenetics.coordinate.model.TemplateModels templateModels =
            this.templatesTable().load();

        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle(this.templateOptions[0])
            .setItems(templateModels.titles(), new android.content.DialogInterface.OnClickListener()
                {
                    @java.lang.Override
                    public void onClick(final android.content.DialogInterface dialog,
                    final int which)
                    {
                        final org.wheatgenetics.coordinate.model.TemplateModel templateModel =
                            templateModels.get(which);
                        if (null != templateModel) switch (which)
                        {
                            case 0:                                                     // seed tray
                                org.wheatgenetics.coordinate.activities.
                                    Main.this.loadSeedTrayTemplate(templateModel);
                                break;

                            case 1:                                                     // dna plate
                                templateModel.makeOneRandomCell();
                                try
                                {
                                    org.wheatgenetics.coordinate.activities.Main.this.
                                        inputTemplateInput(         // throws org.json.JSONException
                                            org.wheatgenetics.coordinate.activities.Main.MODE_DNA,
                                            templateModel                                        );
                                }
                                catch (final org.json.JSONException e) {}
                                break;

                            default:
                                try
                                {
                                    org.wheatgenetics.coordinate.activities.Main.this.
                                        inputTemplateInput(         // throws org.json.JSONException
                                            org.wheatgenetics.coordinate.activities.Main.MODE_SAVED,
                                            templateModel                                          );
                                }
                                catch (final org.json.JSONException e) {}
                                break;
                        }
                    }
                }).show();
    }

    private void deleteTemplate()
    {
        final org.wheatgenetics.coordinate.model.TemplateModels templateModels =
            this.templatesTable().load();

        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle(org.wheatgenetics.coordinate.R.string.delete_template)
            .setItems(templateModels.titles(), new android.content.DialogInterface.OnClickListener()
                {
                    @java.lang.Override
                    public void onClick(final android.content.DialogInterface dialog,
                    final int which)
                    {
                        final org.wheatgenetics.coordinate.model.TemplateModel templateModel =
                            templateModels.get(which);
                        if (null != templateModel) org.wheatgenetics.coordinate.utils.Utils.confirm(
                            org.wheatgenetics.coordinate.activities.Main.this,
                            org.wheatgenetics.coordinate.activities.Main.this.getString(
                                org.wheatgenetics.coordinate.R.string.delete_template),
                            org.wheatgenetics.coordinate.activities.Main.this.getString(
                                org.wheatgenetics.coordinate.R.string.delete_template_warning),
                            new java.lang.Runnable()
                            {
                                @java.lang.Override
                                public void run()
                                {
                                    if (org.wheatgenetics.coordinate.activities.
                                    Main.this.deleteTemplate(templateModel))
                                    {
                                        android.widget.Toast.makeText(
                                            org.wheatgenetics.coordinate.activities.Main.this,
                                            org.wheatgenetics.coordinate.activities.
                                                Main.this.getString(org.wheatgenetics.coordinate.
                                                    R.string.template_deleted),
                                            android.widget.Toast.LENGTH_LONG).show();
                                        org.wheatgenetics.coordinate.activities.
                                            Main.this.loadExistingTemplateOrCreateNewTemplate();
                                    }
                                    else
                                        android.widget.Toast.makeText(
                                            org.wheatgenetics.coordinate.activities.Main.this,
                                            org.wheatgenetics.coordinate.activities.
                                                Main.this.getString(org.wheatgenetics.coordinate.
                                                    R.string.template_not_deleted),
                                            android.widget.Toast.LENGTH_LONG).show();
                                }
                            }, null);
                    }
                }).show();
    }

    @android.annotation.SuppressLint("DefaultLocale")
    private void importData()
    {
        java.lang.String names  [] = new java.lang.String[1];
        long             indexes[] = new long            [1];

        int pos = 0;

        final org.wheatgenetics.coordinate.database.GridsTable gridsTable =
            new org.wheatgenetics.coordinate.database.GridsTable(this);
        final android.database.Cursor gridCursor = gridsTable.getAllGrids();
        if (null != gridCursor)
        {
            final int size = gridCursor.getCount();

            names   = new java.lang.String[size];
            indexes = new long            [size];

            while (gridCursor.moveToNext())
            {
                final org.wheatgenetics.coordinate.database.GridsTable tmpG =
                    new org.wheatgenetics.coordinate.database.GridsTable(this);
                if (tmpG.copyAll(gridCursor))
                {
                    names[pos] = java.lang.String.format(
                        "Grid: %s\n Template: %s\n Size: (%d, %d) Date: %s\n", tmpG.title,
                        tmpG.templateTitle, tmpG.templateCols, tmpG.templateRows,
                        org.wheatgenetics.coordinate.utils.Utils.formatDate(tmpG.timestamp));
                    indexes[pos++] = tmpG.id;
                }
            }
            gridCursor.close();
        }

        if (0 == pos)
        {
            org.wheatgenetics.coordinate.utils.Utils.alert(this, this.appNameStringResource,
                this.getString(org.wheatgenetics.coordinate.R.string.no_templates));
            return;
        }

        final long gridIds[] = indexes;

        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle(this.getString(org.wheatgenetics.coordinate.R.string.import_grid));
        builder.setItems(names, new android.content.DialogInterface.OnClickListener()
            {
                @java.lang.Override
                public void onClick(final android.content.DialogInterface dialog, final int which)
                {
                    if (gridIds.length > which)
                    {
                        final long id = gridIds[which];

                        {
                            assert null !=
                                org.wheatgenetics.coordinate.activities.Main.this.sharedPreferences;
                            org.wheatgenetics.coordinate.activities.
                                Main.this.sharedPreferences.setCurrentGrid(id);
                        }

                        final org.wheatgenetics.coordinate.database.GridsTable grd =
                            new org.wheatgenetics.coordinate.database.GridsTable(Main.this);
                        if (grd.get(id))
                        {
                            org.wheatgenetics.coordinate.activities.Main.this.templateModel.assign(
                                /* title => */ grd.templateTitle,
                                /* rows  => */ grd.templateRows ,
                                /* cols  => */ grd.templateCols );
                            org.wheatgenetics.coordinate.activities.Main.this.gridId = grd.id;
                            mGridTitle = grd.title;
                            org.wheatgenetics.coordinate.activities.Main.this.templateModel.setType(
                                grd.templateType);

                            final org.wheatgenetics.coordinate.database.TemplatesTable
                                templatesTable = org.wheatgenetics.coordinate.activities.
                                    Main.this.templatesTable();

                            if (templatesTable.get(grd.templateId))  // database
                            {
                                try
                                {
                                    nonNullOptionalFields =
                                        new org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields(templatesTable.options);  // throws org.json.JSONException  // model
                                }
                                catch (final org.json.JSONException e) {}

                                org.wheatgenetics.coordinate.activities.Main.this.templateModel.setRowNumbering(templatesTable.rowNumbering);  // model
                                org.wheatgenetics.coordinate.activities.Main.this.templateModel.setColNumbering(templatesTable.colNumbering);  // model
                            }

                            org.wheatgenetics.coordinate.activities.
                                Main.this.templateModel.clearExcludes();  // model

                            populateTemplate();
                            showTemplateUI();
                        }
                        else
                            org.wheatgenetics.coordinate.utils.Utils.alert(Main.this,
                                org.wheatgenetics.coordinate.activities.Main.this.appNameStringResource,
                                getString
                                    (org.wheatgenetics.coordinate.R.string.import_grid_failed));

                        assert null != dialog;
                        dialog.cancel();
                    }
                }
            });
        builder.show();
    }

    private void about()
    {
        if (null == this.aboutAlertDialog)
        {
            java.lang.String versionName = null;
            {
                android.content.pm.PackageInfo packageInfo;
                {
                    final android.content.pm.PackageManager packageManager =
                        this.getPackageManager();
                    assert null != packageManager;
                    try
                    {
                        packageInfo = packageManager.getPackageInfo(  // throws android.content.
                            this.getPackageName(), 0);                //  pm.PackageManager.-
                    }                                                 //  NameNotFoundException
                    catch (final android.content.pm.PackageManager.NameNotFoundException e)
                    {
                        org.wheatgenetics.coordinate.activities.Main.sendErrorLogMsg(e);

                        packageInfo = null;

                        assert null != e;
                        versionName = e.getMessage();
                    }
                }
                versionName = null == packageInfo ? versionName : packageInfo.versionName;
            }

            java.lang.String title, msgs[];
            {
                final android.content.res.Resources resources = this.getResources();
                assert null != resources;
                title = resources.getString(org.wheatgenetics.coordinate.R.string.about);
                msgs  = new java.lang.String[]
                    { resources.getString(org.wheatgenetics.coordinate.R.string.grid_description) };
            }

            this.aboutAlertDialog = new org.wheatgenetics.about.AboutAlertDialog(
                /* context                => */ this       ,
                /* title                  => */ title      ,
                /* versionName            => */ versionName,
                /* msgs[]                 => */ msgs       ,
                /* suppressIndex          => */ org.wheatgenetics.about.OtherApps.Index.COORDINATE,
                /* versionOnClickListener => */ new android.view.View.OnClickListener()
                    {
                        @java.lang.Override
                        public void onClick(final android.view.View v)
                        { org.wheatgenetics.coordinate.activities.Main.this.showChangeLog(); }
                    });
        }
        this.aboutAlertDialog.show();
    }
    // endregion

    // region Selector Drawer Method
    private boolean selectNavigationItem(final android.view.MenuItem menuItem)
    {
        assert null != menuItem;
        switch (menuItem.getItemId())
        {
            case org.wheatgenetics.coordinate.R.id.menu_new_grid:
                try { this.newGrid(); } catch (final org.json.JSONException e) { return false; }
                break;

            case org.wheatgenetics.coordinate.R.id.menu_delete_grid: this.deleteGrid(); break;

            case org.wheatgenetics.coordinate.R.id.menu_new_template:
                this.createNewTemplate(); break;

            case org.wheatgenetics.coordinate.R.id.menu_load_template:
                this.loadExistingTemplate(); break;

            case org.wheatgenetics.coordinate.R.id.menu_delete_template:
                this.deleteTemplate(); break;

            case org.wheatgenetics.coordinate.R.id.menu_import: this.importData(); break;

            case org.wheatgenetics.coordinate.R.id.menu_export:
                assert null != this.templateModel;
                if (this.templateModel.getTitle().equals(""))
                    this.makeToast(this.getString(org.wheatgenetics.coordinate.R.string.grid_empty));
                else
                    this.export();
                break;

            case org.wheatgenetics.coordinate.R.id.about: this.about(); break;

            // Keeping this for debugging purposes:
            // case org.wheatgenetics.coordinate.R.id.reset_database: this.resetDatabase(); break;
            // case org.wheatgenetics.coordinate.R.id.copy_database : this.copydb       (); break;
        }

        assert null != this.drawerLayout;
        this.drawerLayout.closeDrawers();

        return true;
    }
    // endregion
    // endregion

    private void copydb()
    {
        final java.io.File f = new java.io.File("/data/data/org.wheatgenetics.coordinate/databases/seedtray1.db");
        java.io.FileInputStream  fis = null;
        java.io.FileOutputStream fos = null;

        try
        {
            fis = new java.io.FileInputStream(f);                    // throws java.io.FileNotFoundException
            fos = new java.io.FileOutputStream("/mnt/sdcard/db_dump.db");  // throws java.io.FileNotFoundException
            while (true)
            {
                final int i = fis.read();                              // throws java.io.IOException
                if (-1 != i)
                    fos.write(i);                                      // throws java.io.IOException
                else
                    break;
            }
            fos.flush();
            this.makeFileDiscoverable(new java.io.File("/mnt/sdcard/db_dump.db"), this);
            android.widget.Toast.makeText(this, "DB dump OK", android.widget.Toast.LENGTH_LONG).show();
        }
        catch (final java.io.IOException e)
        {
            e.printStackTrace();
            android.widget.Toast.makeText(this, "DB dump ERROR", android.widget.Toast.LENGTH_LONG).show();
        }
        finally
        {
            try
            {
                fos.close();
                fis.close();
            }
            catch (final java.io.IOException ioe) {}
        }
    }

    private void makeToast(final java.lang.String message)
    { android.widget.Toast.makeText(this, message, android.widget.Toast.LENGTH_SHORT).show(); }

    /**
     * Adds default templates to database if they aren't there already.  If they are there then they
     * are updated to their default values.
      */
    private void initializeTemplatesTable()
    {
        {
            final org.wheatgenetics.coordinate.model.TemplateModels defaultTemplateModels =
                org.wheatgenetics.coordinate.model.TemplateModels.makeDefault();
            if (defaultTemplateModels.size() > 0)
            {
                final org.wheatgenetics.coordinate.database.TemplatesTable templatesTable =
                    this.templatesTable();
                for (final org.wheatgenetics.coordinate.model.TemplateModel defaultTemplateModel:
                defaultTemplateModels)
                    if (templatesTable.exists(defaultTemplateModel.getType()))
                        templatesTable.update(defaultTemplateModel);
                    else
                        templatesTable.insert(defaultTemplateModel);
            }
        }
        this.templateModel.setTitle("");
    }

    private void inputTemplateNewExtra()
    {
        android.view.View view;
        {
            final android.view.LayoutInflater layoutInflater = this.getLayoutInflater();
            assert null != layoutInflater;
            view = layoutInflater.inflate(org.wheatgenetics.coordinate.R.layout.template_new_extra,
                new android.widget.LinearLayout(this), false);
        }

        assert null != view;
        final android.widget.Button optionalButton = (android.widget.Button) view.findViewById(org.wheatgenetics.coordinate.R.id.optionalButton);
        final android.widget.Button excludeButton  = (android.widget.Button) view.findViewById(org.wheatgenetics.coordinate.R.id.excludeButton );
        final android.widget.Button namingButton   = (android.widget.Button) view.findViewById(org.wheatgenetics.coordinate.R.id.namingButton  );

        assert null != optionalButton;
        optionalButton.setOnClickListener(new android.view.View.OnClickListener()
            {
                @java.lang.Override
                public void onClick(final android.view.View v)
                { org.wheatgenetics.coordinate.activities.Main.this.inputOptional(); }
            });

        assert null != excludeButton;
        excludeButton.setOnClickListener(new android.view.View.OnClickListener()
            {
                @java.lang.Override
                public void onClick(final android.view.View v)
                { org.wheatgenetics.coordinate.activities.Main.this.inputExclude(); }
            });

        assert null != namingButton;
        namingButton.setOnClickListener(new android.view.View.OnClickListener()
            {
                @java.lang.Override
                public void onClick(final android.view.View v)
                { org.wheatgenetics.coordinate.activities.Main.this.inputNaming(); }
            });

        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle(org.wheatgenetics.coordinate.R.string.template_new)
            .setView(view)
            .setPositiveButton(org.wheatgenetics.coordinate.R.string.next,
                new android.content.DialogInterface.OnClickListener()
                {
                    @java.lang.Override
                    public void onClick(final android.content.DialogInterface dialog,
                    final int which)
                    {
                        assert null != dialog;
                        dialog.cancel();

                        try
                        {
                            org.wheatgenetics.coordinate.activities.Main.this.inputTemplateInput(
                                org.wheatgenetics.coordinate.activities.Main.this.MODE_DEFAULT ,
                                org.wheatgenetics.coordinate.activities.Main.this.templateModel);
                        }
                        catch (final java.lang.Exception e) {}
                    }
                })
            .setNegativeButton(org.wheatgenetics.coordinate.R.string.cancel,
                org.wheatgenetics.androidlibrary.Utils.cancellingOnClickListener()).show();
    }

    private void inputOptional()
    {
        final java.util.ArrayList<java.lang.String > itemArrayList      = new java.util.ArrayList<java.lang.String >();
        final java.util.ArrayList<java.lang.Boolean> selectionArrayList = new java.util.ArrayList<java.lang.Boolean>();

        assert null != this.nonNullOptionalFields;
        for (final org.wheatgenetics.coordinate.optionalField.OptionalField optionalField: this.nonNullOptionalFields)
        {
            itemArrayList.add     (optionalField.getName   ());
            selectionArrayList.add(optionalField.getChecked());
        }


        final java.lang.String itemArray[] = itemArrayList.toArray(new java.lang.String[itemArrayList.size()]);

        final int     selectionArrayListSize = selectionArrayList.size();
        final boolean selectionArray[]       = new boolean[selectionArrayListSize];

        for (int i = 0; i < selectionArrayListSize; i++)
            selectionArray[i] = selectionArrayList.get(i);

        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle(getString(org.wheatgenetics.coordinate.R.string.optional_fields));
        builder.setMultiChoiceItems(itemArray, selectionArray,
            new android.content.DialogInterface.OnMultiChoiceClickListener()
            {
                @java.lang.Override
                public void onClick(final android.content.DialogInterface dialog, final int which,
                final boolean isChecked)
                {
                    assert null != nonNullOptionalFields;
                    nonNullOptionalFields.get(which).setChecked(isChecked);
                }
            });

        builder.setNeutralButton(this.getString(org.wheatgenetics.coordinate.R.string.add_new),
            new android.content.DialogInterface.OnClickListener()
            {
                @java.lang.Override
                public void onClick(final android.content.DialogInterface dialog, final int which)
                {
                    assert null != dialog;
                    dialog.cancel();
                    inputOptionalNew("", "");
                }
            });
        builder.setNegativeButton(org.wheatgenetics.coordinate.R.string.cancel,
            org.wheatgenetics.androidlibrary.Utils.cancellingOnClickListener());
        builder.setPositiveButton(org.wheatgenetics.coordinate.R.string.ok,
            org.wheatgenetics.androidlibrary.Utils.cancellingOnClickListener());

        builder.show();
    }

    private void inputOptionalNew(final java.lang.String field, final java.lang.String value)
    {
        android.view.View view;
        {
            final android.view.LayoutInflater layoutInflater = Main.this.getLayoutInflater();
            view = layoutInflater.inflate(org.wheatgenetics.coordinate.R.layout.optional_new, null);
        }
        final android.widget.EditText fieldEdit = (android.widget.EditText) view.findViewById(org.wheatgenetics.coordinate.R.id.fieldEdit);
        final android.widget.EditText valueEdit = (android.widget.EditText) view.findViewById(org.wheatgenetics.coordinate.R.id.valueEdit);

        assert null != fieldEdit;
        assert null != valueEdit;
        fieldEdit.setText(field);
        valueEdit.setText(value);

        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle(getString(org.wheatgenetics.coordinate.R.string.new_optional_field));
        builder.setView(view);
        builder.setCancelable(false);
        builder.setPositiveButton(getString(org.wheatgenetics.coordinate.R.string.ok),
            new android.content.DialogInterface.OnClickListener()
            {
                @java.lang.Override
                public void onClick(final android.content.DialogInterface dialog, final int which)
                {
                    final java.lang.String sfield = fieldEdit.getText().toString().trim();
                    final java.lang.String svalue = valueEdit.getText().toString().trim();

                    if (0 == sfield.length())
                    {
                        android.widget.Toast.makeText(Main.this, getString(org.wheatgenetics.coordinate.R.string.new_optional_field_no_name),
                            android.widget.Toast.LENGTH_LONG).show();
                        inputOptionalNew(field, value);
                        return;
                    }
                    assert null != dialog;
                    dialog.cancel();

                    assert null != nonNullOptionalFields;
                    nonNullOptionalFields.add(sfield, /* value => */ svalue, /* hint => */ "");

                    inputOptional();
                }
            });

        builder.setNegativeButton(org.wheatgenetics.coordinate.R.string.cancel,
            org.wheatgenetics.androidlibrary.Utils.cancellingOnClickListener());

        final android.app.AlertDialog alertDialog = builder.create();
        assert null != alertDialog;
        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    private void inputExclude()
    {
        final java.lang.String[] items = {
            this.getString(org.wheatgenetics.coordinate.R.string.rows  ),
            this.getString(org.wheatgenetics.coordinate.R.string.cols  ),
            this.getString(org.wheatgenetics.coordinate.R.string.random)};

        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle(getString(org.wheatgenetics.coordinate.R.string.exclude_title));
        builder.setItems(items, new android.content.DialogInterface.OnClickListener()
            {
                @java.lang.Override
                public void onClick(final android.content.DialogInterface dialog, final int which)
                {
                    if (which == 0)
                        inputExcludeBoxes(0);
                    else if (which == 1)
                        inputExcludeBoxes(1);
                    else if (which == 2)
                        inputExcludeInput();
                }
            });
        builder.setNegativeButton(org.wheatgenetics.coordinate.R.string.cancel,
            org.wheatgenetics.androidlibrary.Utils.cancellingOnClickListener());
        builder.show();
    }

    private void inputExcludeBoxes(final int type)
    {
        assert null != this.templateModel;
        final int total = 0 == type ? this.templateModel.getRows() : this.templateModel.getCols();
        final boolean           selections[] = new boolean         [total];
        final java.lang.String  items     [] = new java.lang.String[total];

        for (int i = 0; i < total; i++)
        {
            {
                final int resId = 0 == type ? org.wheatgenetics.coordinate.R.string.row :
                    org.wheatgenetics.coordinate.R.string.col;
                items[i] = java.lang.String.format("%s %d", this.getString(resId), i + 1);
            }
            {
                final int rowOrCol = i + 1;
                selections[i] = 0 == type ?
                    this.templateModel.isExcludedRow(rowOrCol) :
                    this.templateModel.isExcludedCol(rowOrCol) ;
            }
        }

        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        {
            final int resId = 0 == type ? org.wheatgenetics.coordinate.R.string.rows :
                org.wheatgenetics.coordinate.R.string.cols;
            builder.setTitle(this.getString(org.wheatgenetics.coordinate.R.string.exclude_title) +
                " - " + this.getString(resId));
        }
        builder.setMultiChoiceItems(items, selections,
            new android.content.DialogInterface.OnMultiChoiceClickListener()
            {
                @java.lang.Override
                public void onClick(final android.content.DialogInterface dialog, final int which,
                final boolean isChecked) { selections[which] = isChecked; }
            });

        builder.setPositiveButton(org.wheatgenetics.coordinate.R.string.ok,
            new android.content.DialogInterface.OnClickListener()
            {
                @java.lang.Override
                public void onClick(final android.content.DialogInterface dialog, final int which)
                {
                    // get choices
                    for (int i = 0; i < total; i++) if (selections[i])
                        if (0 == type)
                            org.wheatgenetics.coordinate.activities.Main.this.templateModel.addExcludeRow(i + 1);
                        else
                            org.wheatgenetics.coordinate.activities.Main.this.templateModel.addExcludeCol(i + 1);
                }
            });

        builder.setNegativeButton(org.wheatgenetics.coordinate.R.string.cancel,
            org.wheatgenetics.androidlibrary.Utils.cancellingOnClickListener());

        builder.show();
    }

    private void inputExcludeInput()
    {
        android.view.View view;
        {
            final android.view.LayoutInflater layoutInflater = this.getLayoutInflater();
            view = layoutInflater.inflate(org.wheatgenetics.coordinate.R.layout.random, null);
        }

        assert null != view;
        final android.widget.EditText cellsEdit = (android.widget.EditText)
            view.findViewById(org.wheatgenetics.coordinate.R.id.cellsEdit);

        assert null != cellsEdit;
        cellsEdit.setText("1");

        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle(org.wheatgenetics.coordinate.R.string.random)
            .setView(view)
            .setCancelable(false)
            .setNegativeButton(org.wheatgenetics.coordinate.R.string.cancel,
                org.wheatgenetics.androidlibrary.Utils.cancellingOnClickListener())
            .setPositiveButton(org.wheatgenetics.coordinate.R.string.ok,
                new android.content.DialogInterface.OnClickListener()
                {
                    @java.lang.Override
                    public void onClick(final android.content.DialogInterface dialog,
                    final int which)
                    {
                        final int amount = org.wheatgenetics.coordinate.utils.Utils.parseInt(
                            cellsEdit.getText().toString());

                        if (amount > 0)
                        {
                            assert null !=
                                org.wheatgenetics.coordinate.activities.Main.this.templateModel;
                            org.wheatgenetics.coordinate.activities.
                                Main.this.templateModel.makeRandomCells(amount);
                        }
                        else org.wheatgenetics.coordinate.activities.Main.this.inputExcludeInput();

                        assert null != dialog;
                        dialog.cancel();
                    }
                });

        final android.app.AlertDialog alertDialog = builder.create();
        assert null != alertDialog;
        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    private void inputNaming()
    {
        final android.view.LayoutInflater layoutInflater = Main.this.getLayoutInflater();
        final android.view.View           view           = layoutInflater.inflate(org.wheatgenetics.coordinate.R.layout.naming, null);

        assert null != view;
        final android.widget.Spinner rowSpinner = (android.widget.Spinner) view.findViewById(org.wheatgenetics.coordinate.R.id.rowSpinner);
        final android.widget.Spinner colSpinner = (android.widget.Spinner) view.findViewById(org.wheatgenetics.coordinate.R.id.colSpinner);

        assert null != this.templateModel;
        assert null != rowSpinner        ;
        assert null != colSpinner        ;
        rowSpinner.setSelection(this.templateModel.getRowNumbering() ? 0 : 1);
        colSpinner.setSelection(this.templateModel.getColNumbering() ? 0 : 1);

        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle(this.getString(org.wheatgenetics.coordinate.R.string.naming));
        builder.setView(view);
        builder.setCancelable(false);
        builder.setNegativeButton(org.wheatgenetics.coordinate.R.string.cancel,
            org.wheatgenetics.androidlibrary.Utils.cancellingOnClickListener());

        builder.setPositiveButton(org.wheatgenetics.coordinate.R.string.ok,
            new android.content.DialogInterface.OnClickListener()
            {
                @java.lang.Override
                public void onClick(final android.content.DialogInterface dialog, final int which)
                {
                    org.wheatgenetics.coordinate.activities.Main.this.templateModel.setRowNumbering((rowSpinner.getSelectedItemPosition() == 0));
                    org.wheatgenetics.coordinate.activities.Main.this.templateModel.setColNumbering((colSpinner.getSelectedItemPosition() == 0));

                    assert null != dialog;
                    dialog.cancel();
                }
            });

        final android.app.AlertDialog alertDialog = builder.create();
        assert null != alertDialog;
        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    private void loadGrid(final long id) throws org.json.JSONException
    {
        final org.wheatgenetics.coordinate.database.GridsTable grd =
            new org.wheatgenetics.coordinate.database.GridsTable(this);
        if (grd.get(id))
        {
            assert null != this.templateModel;
            this.templateModel.assign(
                /* title => */ grd.templateTitle,
                /* rows  => */ grd.templateRows ,
                /* cols  => */ grd.templateCols );
            this.gridId = grd.id   ;
            mGridTitle  = grd.title;
            this.templateModel.setType(grd.templateType);

            this.templateModel.clearExcludes();  // model

            final org.wheatgenetics.coordinate.database.TemplatesTable templatesTable =
                this.templatesTable();

            if (templatesTable.get(grd.templateId))  // database
            {
                this.nonNullOptionalFields =
                    new org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields(
                        templatesTable.options); // throws org.json.JSONException, model

                this.templateModel.setRowNumbering(templatesTable.rowNumbering);
                this.templateModel.setColNumbering(templatesTable.colNumbering);
            }

            this.populateTemplate();
            this.showTemplateUI();
        }
        else org.wheatgenetics.coordinate.utils.Utils.alert(Main.this, this.appNameStringResource,
            this.getString(org.wheatgenetics.coordinate.R.string.import_grid_failed));
    }

    private boolean isExcludedCell(final int r, final int c)
    {
        assert null != this.templateModel;
        return this.templateModel.isExcludedCell(c, r);
    }

    private boolean isExcludedRow(final int r)
    {
        assert null != this.templateModel;
        return this.templateModel.isExcludedRow(r);
    }

    private boolean isExcludedCol(final int c)
    {
        assert null != this.templateModel;
        return this.templateModel.isExcludedCol(c);
    }

    private void saveData()
    {
        assert null != this.cellIDEditText;
        java.lang.String value = this.cellIDEditText.getText().toString().trim();
        {
            boolean success;
            {
                final org.wheatgenetics.coordinate.database.EntriesTable entriesTable =
                    new org.wheatgenetics.coordinate.database.EntriesTable(this);
                entriesTable.value = value;
                if (entriesTable.getByGrid(this.gridId, mCurRow, mCurCol))
                    success = entriesTable.update();
                else
                {
                    entriesTable.grid = this.gridId ;
                    entriesTable.row  = this.mCurRow;
                    entriesTable.col  = this.mCurCol;
                    success = entriesTable.insert() > 0;
                }
            }
            if (!success)
            {
                android.widget.Toast.makeText(Main.this,
                    this.getString(org.wheatgenetics.coordinate.R.string.update_failed),
                    android.widget.Toast.LENGTH_SHORT).show();
                return;
            }
        }

        android.view.View view;
        view = this.gridTableLayout.findViewWithTag(org.wheatgenetics.coordinate.utils.Utils.getTag(mCurRow, mCurCol));

        if (null != view)
            if (0 != value.length())
                this.setCellState(view, STATE_DONE);
            else
                this.setCellState(view, STATE_NORMAL);

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
            if (this.isExcludedRow(mCurRow) || this.isExcludedCol(mCurCol) || this.isExcludedCell(mCurRow, mCurCol))
                if (!this.getNextFreeCell()) endOfCell = true;

        value = this.getValue(this.gridId, mCurRow, mCurCol);

        if (null == value) value = "";

        this.cellIDEditText.setSelectAllOnFocus(true);
        this.cellIDEditText.setText(value);
        this.cellIDEditText.selectAll();
        this.cellIDEditText.requestFocus();

        view = this.gridTableLayout.findViewWithTag(org.wheatgenetics.coordinate.utils.Utils.getTag(mCurRow, mCurCol));
        if (null != view)
            if (!this.isExcludedRow(mCurRow) && !this.isExcludedCol(mCurCol) && !this.isExcludedCell(mCurRow, mCurCol))
                this.setCellState(view, STATE_ACTIVE);

        this.resetCurrentCell();
        this.currentCellView = view;

        if (endOfCell)
        {
            org.wheatgenetics.coordinate.utils.Utils.alert(this, this.appNameStringResource,
                this.getString(org.wheatgenetics.coordinate.R.string.grid_filled));
            this.completeSound();
        }
    }

    private void completeSound()
    {
        try
        {
            final int resID = this.getResources().getIdentifier("plonk", "raw", this.getPackageName());
            final android.media.MediaPlayer chimePlayer = android.media.MediaPlayer.create(Main.this, resID);
            assert null != chimePlayer;
            chimePlayer.start();

            chimePlayer.setOnCompletionListener(new android.media.MediaPlayer.OnCompletionListener()
                {
                    @java.lang.Override
                    public void onCompletion(final android.media.MediaPlayer mp)
                    {
                        assert null != mp;
                        mp.release();
                    }
                });
        }
        catch (final java.lang.Exception e)
        { org.wheatgenetics.coordinate.activities.Main.sendErrorLogMsg(e); }
    }

    private void resetCurrentCell()
    {
        if (null != this.currentCellView)
        {
            int r = -1;
            int c = -1;

            {
                java.lang.Object obj =
                    this.currentCellView.getTag(org.wheatgenetics.coordinate.R.string.cell_col);
                if (obj instanceof java.lang.Integer) c = (java.lang.Integer) obj;

                obj = this.currentCellView.getTag(org.wheatgenetics.coordinate.R.string.cell_row);
                if (obj instanceof java.lang.Integer) r = (java.lang.Integer) obj;
            }

            if (this.isExcludedRow(r) || this.isExcludedCol(c) || this.isExcludedCell(r, c))
                this.setCellState(this.currentCellView, STATE_INACTIVE);
            else
            {
                java.lang.String value = this.getValue(this.gridId, r, c);
                if (null == value) value = "";

                if (0 == value.length())
                    this.setCellState(this.currentCellView, STATE_NORMAL);
                else
                    this.setCellState(this.currentCellView, STATE_DONE);
            }
        }
    }

    private boolean deleteTemplate(
    final org.wheatgenetics.coordinate.model.TemplateModel templateModel)
    {
        assert null != templateModel;
        final org.wheatgenetics.coordinate.database.TemplatesTable templatesTable =
            this.templatesTable();
        if (!templatesTable.get(templateModel.getId())) return false;  // database

        if (org.wheatgenetics.coordinate.model.TemplateType.SEED.getCode() == templatesTable.type
        ||  org.wheatgenetics.coordinate.model.TemplateType.DNA.getCode () == templatesTable.type)
        {
            this.makeToast(getString(org.wheatgenetics.coordinate.R.string.template_not_deleted_default));
            return false;
        }

        final org.wheatgenetics.coordinate.database.GridsTable gridsTable =
            new org.wheatgenetics.coordinate.database.GridsTable(this);
        final android.database.Cursor cursor = gridsTable.loadByTemplate(templateModel.getId());
        if (null != cursor)
        {
            while (cursor.moveToNext())
            {
                final org.wheatgenetics.coordinate.database.GridsTable g =
                    new org.wheatgenetics.coordinate.database.GridsTable(this);
                if (g.copy(cursor)) this.deleteGrid(g.id);
            }
            cursor.close();
        }

        return templatesTable.delete(templateModel);  // database
    }

    private boolean deleteGrid(final long id)
    {
        boolean success;

        {
            final org.wheatgenetics.coordinate.database.GridsTable gridsTable =
                new org.wheatgenetics.coordinate.database.GridsTable(this);
            success = gridsTable.delete(new org.wheatgenetics.coordinate.model.GridModel(id));
        }

        {
            final org.wheatgenetics.coordinate.database.EntriesTable entriesTable =
                new org.wheatgenetics.coordinate.database.EntriesTable(this);
            entriesTable.deleteByGrid(id);
        }

        return success;
    }

    private long createGrid(final long templateId)
    {
        final org.wheatgenetics.coordinate.database.GridsTable gridsTable =
            new org.wheatgenetics.coordinate.database.GridsTable(this);
        gridsTable.templateId = templateId;
        gridsTable.timestamp  = java.lang.System.currentTimeMillis();
        assert null != this.nonNullOptionalFields;
        gridsTable.title = this.nonNullOptionalFields.get(0).getValue();
        return gridsTable.insert();
    }

    private void createNewTemplate(
    final org.wheatgenetics.coordinate.model.TemplateType templateType)
    throws org.json.JSONException
    {
        assert null != this.templateModel;
        this.templateModel.setType(templateType);
        final org.wheatgenetics.coordinate.database.TemplatesTable templatesTable =
            this.templatesTable();
        templatesTable.title = this.templateModel.getTitle();
        templatesTable.type  = templateType.getCode()       ;
        templatesTable.cols  = this.templateModel.getCols() ;
        templatesTable.rows  = this.templateModel.getRows() ;

        templatesTable.excludeCells = this.templateModel.getExcludeCellsAsJson();  // throws org.json.JSONException, model
        templatesTable.excludeCols = this.templateModel.getExcludeColsAsJson();  // model
        templatesTable.excludeRows = this.templateModel.getExcludeRowsAsJson();  // model

        templatesTable.options = this.nonNullOptionalFields.toJson();     // throws org.json.JSONException, model

        templatesTable.colNumbering = this.templateModel.getColNumbering() ? 1 : 0;
        templatesTable.rowNumbering = this.templateModel.getRowNumbering() ? 1 : 0;

        templatesTable.stamp = java.lang.System.currentTimeMillis();  // model

        final long templateId = templatesTable.insert();  // database
        if (0 < templateId)
        {
            // deleteTemplate(this.templateModel); //TODO

            this.templateModel.setId(templateId);

            final long gridId = this.createGrid(this.templateModel.getId());
            if (0 < gridId)
            {
                this.gridId = gridId;

                {
                    assert null !=
                        org.wheatgenetics.coordinate.activities.Main.this.sharedPreferences;
                    org.wheatgenetics.coordinate.activities.
                        Main.this.sharedPreferences.setCurrentGrid(gridId);
                }

                this.populateTemplate();
                this.showTemplateUI();
            }
            else org.wheatgenetics.coordinate.utils.Utils.alert(this, this.appNameStringResource,
                this.getString(org.wheatgenetics.coordinate.R.string.create_grid_fail));
        }
        else org.wheatgenetics.coordinate.utils.Utils.alert(this, this.appNameStringResource,
            this.getString(org.wheatgenetics.coordinate.R.string.create_template_fail));
    }

    private void loadExistingTemplate(
    final org.wheatgenetics.coordinate.model.TemplateType templateType)
    {
        assert null != this.templateModel;
        this.templateModel.setType(templateType);

        final long gridId = this.createGrid(this.templateModel.getId());
        if (0 < gridId)
        {
            this.gridId = gridId;

            {
                assert null !=
                    org.wheatgenetics.coordinate.activities.Main.this.sharedPreferences;
                org.wheatgenetics.coordinate.activities.Main.this.sharedPreferences.setCurrentGrid(
                    gridId);
            }

            this.optionalFieldLayout.setVisibility(android.view.View.VISIBLE);
            this.gridAreaLayout.setVisibility(android.view.View.VISIBLE);

            this.populateTemplate();
            this.showTemplateUI();
        }
        else org.wheatgenetics.coordinate.utils.Utils.alert(this, this.appNameStringResource, getString(org.wheatgenetics.coordinate.R.string.create_grid_fail));

    }

    private void showTemplateUI()
    {
        assert null != this.templateModel        ;
        assert null != this.templateTitleTextView;
        this.templateTitleTextView.setText(this.templateModel.getTitle());

        assert null != this.mainLayout;
        this.mainLayout.setVisibility(android.view.View.VISIBLE);

        final java.lang.String value = this.getValue(this.gridId, 1, 1);
        assert null != this.cellIDEditText;
        this.cellIDEditText.setText(null == value ? "" : value);
    }

    // first non excluded cell
    private boolean getNextFreeCell()
    {
        {
            assert null != this.templateModel;
            final android.graphics.Point nextFreeCell = this.templateModel.nextFreeCell(
                new android.graphics.Point(this.mCurCol, this.mCurRow));
            this.mCurRow = nextFreeCell.y;
            this.mCurCol = nextFreeCell.x;
        }
        return true;
    }

    private void populateTemplate()
    {
        this.mCurRow = 1;
        this.mCurCol = 1;

        this.getNextFreeCell();

        final android.view.LayoutInflater layoutInflater = this.getLayoutInflater();

        assert null != this.optionalFieldLayout;
        this.optionalFieldLayout.removeAllViews();

        final org.wheatgenetics.coordinate.optionalField.CheckedOptionalFields
            checkedOptionalFields = this.makeCheckedOptionalFields();
        boolean first = true;
        for (final org.wheatgenetics.coordinate.optionalField.OptionalField optionalField:
        checkedOptionalFields)
        {
            final android.view.View view = layoutInflater.inflate(
                org.wheatgenetics.coordinate.R.layout.optional_line,
                new android.widget.LinearLayout(this), false);
            {
                assert null != view;
                final android.widget.TextView fieldText = (android.widget.TextView)
                    view.findViewById(org.wheatgenetics.coordinate.R.id.fieldText);
                assert null != fieldText;
                fieldText.setText(optionalField.getName());
            }
            {
                final android.widget.TextView valueText = (android.widget.TextView)
                    view.findViewById(org.wheatgenetics.coordinate.R.id.valueText);
                assert null != valueText;
                if (first)
                {
                    valueText.setText(this.mGridTitle);
                    first = false;
                }
                else valueText.setText(optionalField.getValue());
            }
            this.optionalFieldLayout.addView(view);
        }
        this.gridTableLayout.removeAllViews();

        // header
        @android.annotation.SuppressLint("InflateParams")
        final android.widget.TableRow hrow = (android.widget.TableRow)
            layoutInflater.inflate(org.wheatgenetics.coordinate.R.layout.table_row, null);
        int chcol = 0;
        assert null != this.templateModel;
        assert null != hrow              ;
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
                    layoutInflater.inflate(org.wheatgenetics.coordinate.R.layout.table_cell_box, null);
                assert null != cell_box;
                final android.widget.TextView cell_cnt = (android.widget.TextView)
                    cell_box.findViewById(org.wheatgenetics.coordinate.R.id.dataCell);

                @android.annotation.SuppressLint("InflateParams")
                final android.widget.LinearLayout cell_left = (android.widget.LinearLayout)
                    layoutInflater.inflate(org.wheatgenetics.coordinate.R.layout.table_cell_left, null);
                assert null != cell_left;
                final android.widget.TextView cell_num = (android.widget.TextView)
                    cell_left.findViewById(org.wheatgenetics.coordinate.R.id.dataCell);

                assert null != cell_num;
                assert null != brow    ;
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
                    final java.lang.String value = this.getValue(this.gridId, r, c);
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
                    cell_cnt.setTag(org.wheatgenetics.coordinate.utils.Utils.getTag(r, c));
                    cell_cnt.setTag(org.wheatgenetics.coordinate.R.string.cell_col, c);
                    cell_cnt.setTag(org.wheatgenetics.coordinate.R.string.cell_row, r);
                    brow.addView(cell_box);
                }
            }
            this.gridTableLayout.addView(brow);
        }
    }

    private void saveExcludedCell(final int r, final int c)
    {
        boolean success;
        {
            final org.wheatgenetics.coordinate.database.EntriesTable entriesTable =
                new org.wheatgenetics.coordinate.database.EntriesTable(this);
            entriesTable.value = "exclude";
            if (entriesTable.getByGrid(this.gridId, r, c))
                success = entriesTable.update();
            else
            {
                entriesTable.grid = this.gridId;
                entriesTable.row  = r          ;
                entriesTable.col  = c          ;
                success = entriesTable.insert() > 0;
            }
        }
        if (!success)
        {
            android.widget.Toast.makeText(Main.this,
                this.getString(org.wheatgenetics.coordinate.R.string.update_failed),
                android.widget.Toast.LENGTH_SHORT).show();
            return;
        }
    }

    private java.lang.String getValue(final long grid, final int r, final int c)
    {
        java.lang.String value = null;
        {
            final org.wheatgenetics.coordinate.database.EntriesTable entriesTable =
                new org.wheatgenetics.coordinate.database.EntriesTable(this);
            if (entriesTable.getByGrid(grid, r, c)) value = entriesTable.value;
        }
        return value;
    }

    private void setCellState(final android.view.View cell, final int state)
    {
        assert null != cell;
        if (STATE_DONE == state)                                // TODO: Turn into switch statement?
            cell.setBackgroundResource(org.wheatgenetics.coordinate.R.drawable.table_cell_done);
        else if (STATE_ACTIVE == state)
            cell.setBackgroundResource(org.wheatgenetics.coordinate.R.drawable.table_cell_active);
        else if (STATE_INACTIVE == state)
            cell.setBackgroundResource(org.wheatgenetics.coordinate.R.drawable.table_cell_inactive);
        else
            cell.setBackgroundResource(org.wheatgenetics.coordinate.R.drawable.table_cell);
    }

    private void makeFileDiscoverable(final java.io.File file,
    final android.content.Context context)
    {
        android.media.MediaScannerConnection.scanFile(context,
            new java.lang.String[]{file.getPath()}, null, null);
        assert null != context;
        context.sendBroadcast(new android.content.Intent(
            android.content.Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, android.net.Uri.fromFile(file)));
    }
}