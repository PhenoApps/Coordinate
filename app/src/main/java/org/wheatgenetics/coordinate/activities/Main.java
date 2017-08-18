package org.wheatgenetics.coordinate.activities;

/**
 * Uses:
 * android.annotation.SuppressLint
 * android.app.AlertDialog
 * android.app.AlertDialog.Builder
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
 * android.view.View.OnClickListener
 * android.view.View.OnKeyListener
 * android.view.WindowManager.LayoutParams
 * android.view.inputmethod.EditorInfo
 * android.widget.Button
 * android.widget.EditText
 * android.widget.LinearLayout
 * android.widget.Spinner
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
 * org.wheatgenetics.androidlibrary.Utils
 * org.wheatgenetics.changelog.ChangeLogAlertDialog
 * org.wheatgenetics.sharedpreferences.SharedPreferences
 *
 * org.wheatgenetics.coordinate.activities.NewOptionalFieldAlertDialog
 * org.wheatgenetics.coordinate.activities.NewOptionalFieldAlertDialog.Handler
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
 * org.wheatgenetics.coordinate.R
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
        private final android.content.Context                         context                     ;
        private final org.wheatgenetics.androidlibrary.ProgressDialog progressDialog              ;
        private final long                                            templateId                  ;
        private final java.lang.String                                exportFileName, absolutePath;

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
            {
                final java.lang.String values[] =
                    org.wheatgenetics.coordinate.activities.Main.this.optionalFieldValues(
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
                org.wheatgenetics.androidlibrary.Utils.makeFileDiscoverable(
                    this.context, this.exportFile);
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
            {
                final java.lang.String values[] =
                    org.wheatgenetics.coordinate.activities.Main.this.optionalFieldValues(
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
                org.wheatgenetics.androidlibrary.Utils.makeFileDiscoverable(
                    this.context, this.exportFile);
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

                {
                    final java.lang.String names[] =
                        org.wheatgenetics.coordinate.activities.Main.this.optionalFieldNames();
                    for (final java.lang.String name: names) csvWriter.write(name);
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

                        {
                            final java.lang.String values[] = org.wheatgenetics.
                                coordinate.activities.Main.this.optionalFieldValues();
                            for (final java.lang.String value: values) csvWriter.write(value);
                        }
                        csvWriter.endRecord();
                    }
                    this.publishProgress(
                        org.wheatgenetics.coordinate.activities.Main.this.getString(
                            org.wheatgenetics.coordinate.R.string.exporting_column_title) + col);
                }
                csvWriter.close();
                org.wheatgenetics.androidlibrary.Utils.makeFileDiscoverable(
                    this.context, this.exportFile);
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

            this.context        = context;
            this.progressDialog = new org.wheatgenetics.androidlibrary.ProgressDialog(this.context,
                progressDialogTitle, progressDialogMessage,
                new android.content.DialogInterface.OnCancelListener()
                {
                    @java.lang.Override
                    public void onCancel(final android.content.DialogInterface dialog)
                    {
                        org.wheatgenetics.coordinate.activities.Main.Exporter.this.cancel(
                            /* mayInterruptIfRunning => */ true);
                    }
                });

            this.templateId     = templateId    ;
            this.exportFileName = exportFileName;
            this.absolutePath   = absolutePath  ;
        }

        // region Overridden Methods
        @java.lang.Override
        protected void onPreExecute()
        {
            super.onPreExecute();
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
            this.progressDialog.dismiss();

            // TODO: When grid is reset, make a new one.
            if (null != result && result)
            {
                org.wheatgenetics.coordinate.activities.Main.this.mLastExportGridId =  // TODO: Make into
                    org.wheatgenetics.coordinate.activities.Main.this.gridId;          // TODO:  Main method.
                org.wheatgenetics.coordinate.activities.Main.this.alert(
                    /* message     => */ org.wheatgenetics.coordinate.R.string.export_success,
                    /* yesRunnable => */ new java.lang.Runnable()
                    {
                        @java.lang.Override
                        public void run()
                        {
                            org.wheatgenetics.coordinate.activities.Main.this.confirm(
                                /* message => */ org.wheatgenetics.coordinate.R.string.clear_grid,
                                /* yesRunnable => */ new java.lang.Runnable()
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
                                                org.wheatgenetics.coordinate.activities.
                                                    Main.this.populateTemplate();
                                            else
                                                org.wheatgenetics.coordinate.activities.Main.this.
                                                    showLongToast(org.wheatgenetics.coordinate.R.
                                                        string.clear_fail);
                                        }
                                        catch (final java.lang.Exception e)
                                        {
                                            org.wheatgenetics.coordinate.activities.Main.this.
                                                showLongToast(org.wheatgenetics.coordinate.R.string.
                                                    clear_fail);
                                            return;
                                        }
                                        org.wheatgenetics.coordinate.
                                            activities.Main.Exporter.this.share();
                                    }
                                },
                                /* noRunnable => */ new java.lang.Runnable()
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
                org.wheatgenetics.coordinate.activities.Main.this.alert(
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
    private android.support.v4.widget.DrawerLayout       drawerLayout         ;
    private android.support.v7.app.ActionBarDrawerToggle actionBarDrawerToggle;

    private android.widget.LinearLayout mainLayout,
        gridAreaLayout, gridTableLayout, optionalFieldLayout;
    private android.widget.EditText cellIDEditText        ;
    private android.widget.TextView templateTitleTextView ;
    private android.view.View       currentCellView = null;
    // endregion

    private long             gridId    =  0             ;
    private java.lang.String gridTitle = ""             ;
    private int              mCurRow   =  1, mCurCol = 1;

    private java.lang.String                                      versionName                ;
    private org.wheatgenetics.sharedpreferences.SharedPreferences sharedPreferences          ;
    private org.wheatgenetics.changelog.ChangeLogAlertDialog      changeLogAlertDialog = null;
    private org.wheatgenetics.about.AboutAlertDialog              aboutAlertDialog     = null;

    // region Template
    private org.wheatgenetics.coordinate.model.TemplateModel templateModel =
        org.wheatgenetics.coordinate.model.TemplateModel.makeInitial();
    private org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields nonNullOptionalFields;
    private org.wheatgenetics.coordinate.database.TemplatesTable templatesTableInstance = null;
    // endregion

    private org.wheatgenetics.androidlibrary.Dir                  exportDir               ;
    private org.wheatgenetics.coordinate.activities.Main.Exporter exporter          = null;
    private long                                                  mLastExportGridId =   -1;

    private org.wheatgenetics.coordinate.activities.NewOptionalFieldAlertDialog
        newOptionalFieldAlertDialog = null;

    // region Resources Fields
    private java.lang.String appNameStringResource, okStringResource, templateOptions[];
    // endregion
    // endregion

    // region Class Methods
    private static int sendErrorLogMsg(final java.lang.Exception e)
    {
        assert null != e;
        return android.util.Log.e("Coordinate", e.getMessage());
    }

    public static java.lang.String getTag(final int r, final int c)
    { return java.lang.String.format(java.util.Locale.US, "tag_%d_%d", r, c); }
    // endregion

    // region Toast Methods
    private void showLongToast(final java.lang.String text)
    { org.wheatgenetics.androidlibrary.Utils.showLongToast(this, text); }

    private void showLongToast(final int text) { this.showLongToast(this.getString(text)); }

    private void showLongToast(final java.lang.String firstText, final int secondText)
    { this.showLongToast(firstText + this.getString(secondText)); }

    private void showShortToast(final java.lang.String text)
    { org.wheatgenetics.androidlibrary.Utils.showShortToast(this, text); }

    private void showShortToast(final int text) { this.showShortToast(this.getString(text)); }
    // endregion

    // region OptionalField Methods
    private java.lang.String[] optionalFieldValues()
    {
        assert null != this.nonNullOptionalFields;
        return this.nonNullOptionalFields.values();
    }

    private java.lang.String[] optionalFieldValues(final java.lang.String names[])
    {
        assert null != this.nonNullOptionalFields;
        return this.nonNullOptionalFields.values(names);
    }

    private java.lang.String[] optionalFieldNames()
    {
        assert null != this.nonNullOptionalFields;
        return this.nonNullOptionalFields.names();
    }

    private void setOptionalFieldChecked(final int index, final boolean checked)
    {
        assert null != this.nonNullOptionalFields;
        this.nonNullOptionalFields.get(index).setChecked(checked);
    }

    private void addOptionalField(final java.lang.String name, final java.lang.String value)
    {
        assert null != this.nonNullOptionalFields;
        this.nonNullOptionalFields.add(/* name => */ name, /* value => */ value, /* hint => */ "");
        this.addNewOptionalFields();
    }

    private org.wheatgenetics.coordinate.optionalField.CheckedOptionalFields
    makeCheckedOptionalFields()
    {
        return new org.wheatgenetics.coordinate.optionalField.CheckedOptionalFields(
            this.nonNullOptionalFields);
    }
    // endregion

    // region AlertDialog Methods
    // region alert() AlertDialog Methods
    private void alert(final int message)
    { org.wheatgenetics.coordinate.utils.Utils.alert(this, this.appNameStringResource, message); }

    private void alert(final int title, final java.lang.String message, final int messageIfNull)
    {
        org.wheatgenetics.coordinate.utils.Utils.alert(
            /* context => */ this ,
            /* title   => */ title,
            /* message => */ org.wheatgenetics.javalib.Utils.replaceIfNull(
                message, this.getString(messageIfNull)));
    }

    private void alert(final int message, final java.lang.Runnable yesRunnable)
    {
        org.wheatgenetics.coordinate.utils.Utils.alert(
            this, this.appNameStringResource, message, yesRunnable);
    }
    // endregion

    // region confirm() AlertDialog Methods
    private void confirm(final int title, final int message, final java.lang.Runnable yesRunnable)
    { org.wheatgenetics.coordinate.utils.Utils.confirm(this, title, message, yesRunnable); }

    private void confirm(final int message, final java.lang.Runnable yesRunnable)
    {
        org.wheatgenetics.coordinate.utils.Utils.confirm(
            this, this.appNameStringResource, message, yesRunnable);
    }

    private void confirm(final int message, final java.lang.Runnable yesRunnable,
    final java.lang.Runnable noRunnable)
    {
        org.wheatgenetics.coordinate.utils.Utils.confirm(
            this, this.appNameStringResource, message, yesRunnable, noRunnable);
    }
    // endregion
    // endregion

    private org.wheatgenetics.coordinate.database.TemplatesTable templatesTable()
    {
        if (null == this.templatesTableInstance) this.templatesTableInstance =
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

            this.templateOptions = new java.lang.String[] {
                resources.getString(org.wheatgenetics.coordinate.R.string.template_load),
                resources.getString(org.wheatgenetics.coordinate.R.string.template_new )};
        }

        this.nonNullOptionalFields =
            org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields.makeInitial();

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

        this.templateTitleTextView = (android.widget.TextView)
            this.findViewById(org.wheatgenetics.coordinate.R.id.templateText);

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

            // Exported data is saved to this folder.
            this.exportDir = new org.wheatgenetics.androidlibrary.Dir(
                this, coordinateDirName + "/Export", blankHiddenFileName);
            try
            {
                new org.wheatgenetics.androidlibrary.Dir(this, coordinateDirName,
                    blankHiddenFileName).createIfMissing();            // throws java.io.IOException

                this.exportDir.createIfMissing();                      // throws java.io.IOException

                // This directory will be used in the future to transfer templates between devices.
                new org.wheatgenetics.androidlibrary.Dir(this, coordinateDirName + "/Templates",
                    blankHiddenFileName).createIfMissing();            // throws java.io.IOException
            }
            catch (final java.io.IOException e)
            { org.wheatgenetics.coordinate.activities.Main.sendErrorLogMsg(e); }
        }

        if (this.sharedPreferences.currentGridIsSet())
        {
            // Load grid:
            final org.wheatgenetics.coordinate.database.GridsTable gridsTable =
                new org.wheatgenetics.coordinate.database.GridsTable(this);
            if (gridsTable.get(this.sharedPreferences.getCurrentGrid()))
            {
                assert null != this.templateModel;
                this.templateModel.assign(
                    /* title => */ gridsTable.templateTitle,
                    /* rows  => */ gridsTable.templateRows ,
                    /* cols  => */ gridsTable.templateCols );
                this.templateModel.setType(gridsTable.templateType);
                this.templateModel.clearExcludes();

                this.gridId    = gridsTable.id   ;
                this.gridTitle = gridsTable.title;

                {
                    final org.wheatgenetics.coordinate.database.TemplatesTable templatesTable =
                        this.templatesTable();
                    if (templatesTable.get(gridsTable.templateId))
                    {
                        this.importOptionalFields(templatesTable.options);

                        this.templateModel.setRowNumbering(templatesTable.rowNumbering);
                        this.templateModel.setColNumbering(templatesTable.colNumbering);
                    }
                }

                this.populateTemplate();
            }
            else this.alert(org.wheatgenetics.coordinate.R.string.import_grid_failed);
        }
        else
            this.loadExistingTemplateOrCreateNewTemplate();

        this.showTemplateUI();

        int versionCode;
        try
        {
            final android.content.pm.PackageInfo packageInfo =
                this.getPackageManager().getPackageInfo(this.getPackageName(), /* flags => 0 */ 0);
            assert null != packageInfo;
            versionCode      = packageInfo.versionCode;
            this.versionName = packageInfo.versionName;
        }
        catch (final android.content.pm.PackageManager.NameNotFoundException e)
        {
            versionCode      = 0                                           ;
            this.versionName = org.wheatgenetics.javalib.Utils.adjust(null);
        }
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

    // region android.view.View.OnClickListener Overridden Method
    @java.lang.Override
    public void onClick(final android.view.View v)      // v is a cell.
    {                                                   // TODO: Don't toggle already selected cell.
        int              c = -1, r = -1;
        java.lang.Object obj           ;

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
    // endregion

    // region android.view.View.OnKeyListener Overridden Method
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
    // endregion
    // endregion

    // region Drawer Methods
    // region Overview
    // createNewGrid()
    //     loadExistingTemplateOrCreateNewTemplate()
    //         loadExistingTemplate()
    //         createNewTemplate()
    //             inputTemplateNewExtra()
    //     newGridNow()
    //         deleteGrid(long)
    //         loadSeedTrayTemplate()
    //             loadExistingTemplate(TemplateType)
    //         loadTemplate()
    //             tempLoad()
    //     exportGrid()
    //         createExportFile()
    //         exporter.execute()
    // deleteGrid()
    //     deleteGrid(long)
    //     loadExistingTemplateOrCreateNewTemplate()
    //         loadExistingTemplate()
    //         createNewTemplate()
    //             inputTemplateNewExtra()
    // createNewTemplate()
    //     inputTemplateNewExtra()
    //         addNewOptionalFields()
    //             addNewOptionalField()
    //         inputExclude()
    //             inputExcludeBoxes()
    //             inputExcludeInput()
    //         inputNaming()
    //         loadTemplate()
    //             tempLoad()
    // loadExistingTemplate()
    //     loadSeedTrayTemplate()
    //         loadExistingTemplate(TemplateType)
    //     loadTemplate()
    //         tempLoad()
    //              loadExistingTemplate(TemplateType)
    //              createNewTemplate(TemplateType)
    // deleteTemplate()
    //     deleteTemplate(TemplateModel)
    //     loadExistingTemplateOrCreateNewTemplate()
    //         loadExistingTemplate()
    //             loadSeedTrayTemplate()
    //             loadTemplate()
    //         createNewTemplate()
    //             inputTemplateNewExtra()
    // importGrid()
    //     importOptionalFields()
    //     populateTemplate()
    //         getNextFreeCell()
    //         isExcludedRow()
    //         isExcludedCol()
    //         getValue()
    //         setCellState()
    //         isExcludedCell()
    //         saveExcludedCell()
    //         showTemplateUI()
    // exportGrid()
    //     createExportFile()
    //     exporter.execute()
    // showAboutAlertDialog()
    //     showChangeLog()
    // endregion

    // region Subsubsubaction Drawer Methods
    private void addNewOptionalField(final java.lang.String oldName,
    final java.lang.String oldDefault)
    {
        if (null == this.newOptionalFieldAlertDialog) this.newOptionalFieldAlertDialog =
            new org.wheatgenetics.coordinate.activities.NewOptionalFieldAlertDialog(this,
                new org.wheatgenetics.coordinate.activities.NewOptionalFieldAlertDialog.Handler()
                {
                    @java.lang.Override
                    public void retry(final int errorMsgResId, final java.lang.String oldName,
                    final java.lang.String newDefault)
                    {
                        org.wheatgenetics.coordinate.activities.Main.this.showLongToast(
                            errorMsgResId);
                        org.wheatgenetics.coordinate.activities.Main.this.addNewOptionalField(
                            oldName, newDefault);
                    }

                    @java.lang.Override
                    public void addOptionalField(final java.lang.String newName,
                    final java.lang.String newDefault)
                    {
                        org.wheatgenetics.coordinate.activities.Main.this.addOptionalField(
                            newName, newDefault);
                    }
                });
        this.newOptionalFieldAlertDialog.show(oldName, oldDefault);
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
            }).setPositiveButton(org.wheatgenetics.coordinate.R.string.ok,
                new android.content.DialogInterface.OnClickListener()
                {
                    @java.lang.Override
                    public void onClick(final android.content.DialogInterface dialog,
                    final int which)
                    {
                        // get choices
                        for (int i = 0; i < total; i++) if (selections[i])
                            if (0 == type)
                                org.wheatgenetics.coordinate.activities.
                                    Main.this.templateModel.addExcludeRow(i + 1);
                            else
                                org.wheatgenetics.coordinate.activities.
                                    Main.this.templateModel.addExcludeCol(i + 1);
                    }
                }).setNegativeButton(org.wheatgenetics.coordinate.R.string.cancel,
                    org.wheatgenetics.androidlibrary.Utils.cancellingOnClickListener()).show();
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
        builder.setTitle(org.wheatgenetics.coordinate.R.string.random).setView(view)
            .setCancelable(false).setNegativeButton(org.wheatgenetics.coordinate.R.string.cancel,
                org.wheatgenetics.androidlibrary.Utils.cancellingOnClickListener())
            .setPositiveButton(org.wheatgenetics.coordinate.R.string.ok,
                new android.content.DialogInterface.OnClickListener()
                {
                    @java.lang.Override
                    public void onClick(final android.content.DialogInterface dialog,
                    final int which)
                    {
                        final int amount = org.wheatgenetics.coordinate.utils.Utils.convert(
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
        templatesTable.excludeCols  = this.templateModel.getExcludeColsAsJson ();
        templatesTable.excludeRows  = this.templateModel.getExcludeRowsAsJson ();

        templatesTable.options = this.nonNullOptionalFields.toJson();  // throws org.json.JSONException, model

        templatesTable.colNumbering = this.templateModel.getColNumbering() ? 1 : 0;
        templatesTable.rowNumbering = this.templateModel.getRowNumbering() ? 1 : 0;

        templatesTable.stamp = java.lang.System.currentTimeMillis();

        final long templateId = templatesTable.insert();
        if (templateId > 0)
        {
            // deleteTemplate(this.templateModel); // TODO

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
            }
            else this.alert(org.wheatgenetics.coordinate.R.string.create_grid_fail);
        }
        else this.alert(org.wheatgenetics.coordinate.R.string.create_template_fail);
    }
    // endregion

    // region Subsubaction Drawer Methods
    private void loadExistingTemplate(
    final org.wheatgenetics.coordinate.model.TemplateType templateType)
    {
        assert null != this.templateModel;
        this.templateModel.setType(templateType);

        final long gridId = this.createGrid(this.templateModel.getId());
        if (gridId > 0)
        {
            this.gridId = gridId;

            {
                assert null !=
                    org.wheatgenetics.coordinate.activities.Main.this.sharedPreferences;
                org.wheatgenetics.coordinate.activities.Main.this.sharedPreferences.setCurrentGrid(
                    gridId);
            }

            this.optionalFieldLayout.setVisibility(android.view.View.VISIBLE);
            this.gridAreaLayout.setVisibility     (android.view.View.VISIBLE);

            this.populateTemplate();
        }
        else this.alert(org.wheatgenetics.coordinate.R.string.create_grid_fail);
    }

    private void addNewOptionalFields()
    {
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        assert null != this.nonNullOptionalFields;
        builder.setTitle(org.wheatgenetics.coordinate.R.string.optional_fields)
            .setMultiChoiceItems(this.nonNullOptionalFields.names(),
                this.nonNullOptionalFields.checks(),
                new android.content.DialogInterface.OnMultiChoiceClickListener()
                {
                    @java.lang.Override
                    public void onClick(final android.content.DialogInterface dialog,
                    final int which, final boolean isChecked)
                    {
                        org.wheatgenetics.coordinate.activities.Main.this.setOptionalFieldChecked(
                            which, isChecked);
                    }
                })
            .setNeutralButton(org.wheatgenetics.coordinate.R.string.add_new,
                new android.content.DialogInterface.OnClickListener()
                {
                    @java.lang.Override
                    public void onClick(final android.content.DialogInterface dialog,
                    final int which)
                    {
                        assert null != dialog;
                        dialog.cancel();
                        org.wheatgenetics.coordinate.activities.Main.this.addNewOptionalField(
                            "", "");
                    }
                })
            .setNegativeButton(org.wheatgenetics.coordinate.R.string.cancel,
                org.wheatgenetics.androidlibrary.Utils.cancellingOnClickListener())
            .setPositiveButton(org.wheatgenetics.coordinate.R.string.ok,
                org.wheatgenetics.androidlibrary.Utils.cancellingOnClickListener()).show();
    }

    private void inputExclude()
    {
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        {
            final java.lang.String[] items = {
                this.getString(org.wheatgenetics.coordinate.R.string.rows  ),
                this.getString(org.wheatgenetics.coordinate.R.string.cols  ),
                this.getString(org.wheatgenetics.coordinate.R.string.random) };
            builder.setItems(items, new android.content.DialogInterface.OnClickListener()
                {
                    @java.lang.Override
                    public void onClick(final android.content.DialogInterface dialog,
                    final int which)
                    {
                        switch (which)
                        {
                            case 0: case 1: org.wheatgenetics.coordinate.activities.
                                Main.this.inputExcludeBoxes(which); break;

                            case 2: org.wheatgenetics.coordinate.activities.
                                Main.this.inputExcludeInput() ; break;
                        }
                    }
                });
        }
        builder.setTitle(org.wheatgenetics.coordinate.R.string.exclude_title).setNegativeButton(
            org.wheatgenetics.coordinate.R.string.cancel                      ,
            org.wheatgenetics.androidlibrary.Utils.cancellingOnClickListener()).show();
    }

    private void inputNaming()
    {
        android.app.AlertDialog alertDialog;
        {
            final android.app.AlertDialog.Builder builder =
                new android.app.AlertDialog.Builder(this);
            builder.setTitle(org.wheatgenetics.coordinate.R.string.naming).setCancelable(false)
                .setNegativeButton(org.wheatgenetics.coordinate.R.string.cancel,
                    org.wheatgenetics.androidlibrary.Utils.cancellingOnClickListener());
            {
                android.view.View view;
                {
                    final android.view.LayoutInflater layoutInflater = this.getLayoutInflater();
                    view =
                        layoutInflater.inflate(org.wheatgenetics.coordinate.R.layout.naming, null);
                }

                assert null != view;
                final android.widget.Spinner rowSpinner = (android.widget.Spinner)
                    view.findViewById(org.wheatgenetics.coordinate.R.id.rowSpinner);
                final android.widget.Spinner colSpinner = (android.widget.Spinner)
                    view.findViewById(org.wheatgenetics.coordinate.R.id.colSpinner);

                assert null != this.templateModel;
                assert null != rowSpinner        ;
                assert null != colSpinner        ;
                rowSpinner.setSelection(this.templateModel.getRowNumbering() ? 0 : 1);
                colSpinner.setSelection(this.templateModel.getColNumbering() ? 0 : 1);

                builder.setView(view).setPositiveButton(org.wheatgenetics.coordinate.R.string.ok,
                    new android.content.DialogInterface.OnClickListener()
                    {
                        @java.lang.Override
                        public void onClick(final android.content.DialogInterface dialog,
                        final int which)
                        {
                            org.wheatgenetics.coordinate.activities.Main.this.templateModel.
                                setRowNumbering(rowSpinner.getSelectedItemPosition() == 0);
                            org.wheatgenetics.coordinate.activities.Main.this.templateModel.
                                setColNumbering(colSpinner.getSelectedItemPosition() == 0);

                            assert null != dialog;
                            dialog.cancel();
                        }
                    });
            }
            alertDialog = builder.create();
        }
        assert null != alertDialog;
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

    /**
     * First non-excluded cell.
      */
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
        int backgroundResourceId;
        switch (state)
        {
            case org.wheatgenetics.coordinate.activities.Main.STATE_DONE: backgroundResourceId =
                org.wheatgenetics.coordinate.R.drawable.table_cell_done; break;

            case org.wheatgenetics.coordinate.activities.Main.STATE_ACTIVE: backgroundResourceId =
                org.wheatgenetics.coordinate.R.drawable.table_cell_active; break;

            case org.wheatgenetics.coordinate.activities.Main.STATE_INACTIVE: backgroundResourceId =
                org.wheatgenetics.coordinate.R.drawable.table_cell_inactive; break;

            default: backgroundResourceId =
                org.wheatgenetics.coordinate.R.drawable.table_cell; break;
        }

        assert null != cell;
        cell.setBackgroundResource(backgroundResourceId);
    }

    private boolean isExcludedCell(final int r, final int c)
    {
        assert null != this.templateModel;
        return this.templateModel.isExcludedCell(c, r);
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
        if (!success) this.showShortToast(org.wheatgenetics.coordinate.R.string.update_failed);
    }

    private void showTemplateUI()
    {
        assert null != this.templateModel        ;
        assert null != this.templateTitleTextView;
        this.templateTitleTextView.setText(this.templateModel.getTitle());

        assert null != this.mainLayout;
        this.mainLayout.setVisibility(android.view.View.VISIBLE);

        assert null != this.cellIDEditText;
        this.cellIDEditText.setText(org.wheatgenetics.javalib.Utils.makeEmptyIfNull(
            this.getValue(this.gridId, 1, 1)));
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
                        if (0 == which)
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
        else
            if (org.wheatgenetics.coordinate.model.TemplateType.DNA == templateType)
                this.loadTemplate(                                  // throws org.json.JSONException
                    org.wheatgenetics.coordinate.activities.Main.MODE_DNA,
                    this.templatesTable().get(templateType)              );
            else
                // reset options?
                this.loadTemplate(                                  // throws org.json.JSONException
                    org.wheatgenetics.coordinate.activities.Main.MODE_SAVED, this.templateModel);
    }

    private boolean deleteGrid(final long gridId)
    {
        boolean success;

        {
            final org.wheatgenetics.coordinate.database.GridsTable gridsTable =
                new org.wheatgenetics.coordinate.database.GridsTable(this);
            success = gridsTable.delete(new org.wheatgenetics.coordinate.model.GridModel(gridId));
        }

        {
            final org.wheatgenetics.coordinate.database.EntriesTable entriesTable =
                new org.wheatgenetics.coordinate.database.EntriesTable(this);
            entriesTable.deleteByGrid(gridId);
        }

        return success;
    }

    private void inputTemplateNewExtra()
    {
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        {
            android.view.View view;
            {
                final android.view.LayoutInflater layoutInflater = this.getLayoutInflater();
                view = layoutInflater.inflate(
                    org.wheatgenetics.coordinate.R.layout.template_new_extra,
                    new android.widget.LinearLayout(this), false);
            }

            {
                assert null != view;
                final android.widget.Button optionalButton = (android.widget.Button)
                    view.findViewById(org.wheatgenetics.coordinate.R.id.optionalButton);
                assert null != optionalButton;
                optionalButton.setOnClickListener(new android.view.View.OnClickListener()
                    {
                        @java.lang.Override
                        public void onClick(final android.view.View v)
                        {
                            org.wheatgenetics.coordinate.activities.
                                Main.this.addNewOptionalFields();
                        }
                    });
            }

            {
                final android.widget.Button excludeButton = (android.widget.Button)
                    view.findViewById(org.wheatgenetics.coordinate.R.id.excludeButton);
                assert null != excludeButton;
                excludeButton.setOnClickListener(new android.view.View.OnClickListener()
                    {
                        @java.lang.Override
                        public void onClick(final android.view.View v)
                        { org.wheatgenetics.coordinate.activities.Main.this.inputExclude(); }
                    });
            }

            {
                final android.widget.Button namingButton = (android.widget.Button)
                    view.findViewById(org.wheatgenetics.coordinate.R.id.namingButton);
                assert null != namingButton;
                namingButton.setOnClickListener(new android.view.View.OnClickListener()
                    {
                        @java.lang.Override
                        public void onClick(final android.view.View v)
                        { org.wheatgenetics.coordinate.activities.Main.this.inputNaming(); }
                    });
            }

            builder.setView(view);
        }
        builder.setTitle(org.wheatgenetics.coordinate.R.string.template_new).setPositiveButton(
            org.wheatgenetics.coordinate.R.string.next,
            new android.content.DialogInterface.OnClickListener()
            {
                @java.lang.Override
                public void onClick(final android.content.DialogInterface dialog, final int which)
                {
                    assert null != dialog;
                    dialog.cancel();

                    try
                    {
                        org.wheatgenetics.coordinate.activities.Main.this.loadTemplate(
                            org.wheatgenetics.coordinate.activities.Main.MODE_DEFAULT      ,
                            org.wheatgenetics.coordinate.activities.Main.this.templateModel);
                    }
                    catch (final org.json.JSONException e) {}
                }
            }).setNegativeButton(org.wheatgenetics.coordinate.R.string.cancel,
                org.wheatgenetics.androidlibrary.Utils.cancellingOnClickListener()).show();
    }

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
                        optionalField: checkedOptionalFields)
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
                    builder.setView(gridView);
                }
                assert null != templateModel;
                builder.setTitle(templateModel.getTitle()).setCancelable(false).setPositiveButton(
                    org.wheatgenetics.coordinate.R.string.create,
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
                                optionalField: org.wheatgenetics.coordinate.
                                activities.Main.this.nonNullOptionalFields) // Danger: CheckedOptionalFields above but nonNullOptionalFields here.
                                {
                                    final android.widget.EditText editText = editTextArray[i];
                                    if (null != editText)
                                    {
                                        final java.lang.String value =
                                            editText.getText().toString().trim();
                                        if (0 == i && 0 == value.length())
                                        {
                                            org.wheatgenetics.coordinate.activities.Main.this.
                                                showLongToast(optionalField.getHint(), org.
                                                    wheatgenetics.coordinate.R.string.not_empty);
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
            alertDialog = builder.setNegativeButton(org.wheatgenetics.coordinate.R.string.cancel,
                org.wheatgenetics.androidlibrary.Utils.cancellingOnClickListener()).create();
        }
        assert null != alertDialog;
        alertDialog.getWindow().setSoftInputMode(
            android.view.WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    private void loadTemplate(final int mode,
    final org.wheatgenetics.coordinate.model.TemplateModel templateModel)
    throws org.json.JSONException                     // TODO: Merge this method with the one above.
    {
        assert null != this.nonNullOptionalFields;
        if (this.nonNullOptionalFields.isEmpty())
        {
            this.tempLoad(mode);                                    // throws org.json.JSONException
            return;
        }

        android.app.AlertDialog alertDialog;
        {
            assert null != templateModel;
            final android.app.AlertDialog.Builder builder =
                new android.app.AlertDialog.Builder(this);
            builder.setTitle(templateModel.getTitle()).setCancelable(false);
            {
                final java.util.ArrayList<android.widget.EditText> editTextArrayList =
                    new java.util.ArrayList<android.widget.EditText>();
                {
                    android.view.View gridView;
                    {
                        final android.view.LayoutInflater layoutInflater =
                            org.wheatgenetics.coordinate.activities.Main.this.getLayoutInflater();
                        gridView = layoutInflater.inflate(
                            org.wheatgenetics.coordinate.R.layout.grid_new,
                            new android.widget.LinearLayout(this), false);

                        assert null != gridView;
                        final android.widget.LinearLayout linearLayout =
                            (android.widget.LinearLayout) gridView.findViewById(
                                org.wheatgenetics.coordinate.R.id.optionalLayout);

                        assert null != linearLayout;
                        final org.wheatgenetics.coordinate.optionalField.CheckedOptionalFields
                            checkedOptionalFields = this.makeCheckedOptionalFields();
                        for (final org.wheatgenetics.coordinate.optionalField.OptionalField
                        optionalField: checkedOptionalFields)
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
                                optionalFieldEditText.setHint(optionalField.getHint() );

                                editTextArrayList.add(optionalFieldEditText);
                            }

                            linearLayout.addView(optionalFieldView);
                        }
                    }
                    builder.setView(gridView);
                }
                builder.setPositiveButton(org.wheatgenetics.coordinate.R.string.create,
                    new android.content.DialogInterface.OnClickListener()
                    {
                        @java.lang.Override
                        public void onClick(final android.content.DialogInterface dialog,
                        final int which)
                        {
                            {
                                int i = 0;
                                final android.widget.EditText editTextArray[] =
                                    editTextArrayList.toArray(
                                        new android.widget.EditText[editTextArrayList.size()]);

                                assert null != org.wheatgenetics.coordinate.activities.
                                    Main.this.nonNullOptionalFields;
                                for (final org.wheatgenetics.coordinate.optionalField.OptionalField
                                optionalField: org.wheatgenetics.coordinate.activities.
                                Main.this.nonNullOptionalFields)
                                {
                                    final android.widget.EditText editText = editTextArray[i];
                                    if (null != editText)
                                    {
                                        final java.lang.String value =
                                            editText.getText().toString().trim();
                                        if (org.wheatgenetics.coordinate.activities.Main.MODE_DNA ==
                                        mode)
                                            if (0 == i && 0 == value.length())
                                            {
                                                org.wheatgenetics.coordinate.activities.Main.this.
                                                    showLongToast(optionalField.getHint(),
                                                        org.wheatgenetics.coordinate.
                                                            R.string.not_empty);
                                                try
                                                {
                                                    org.wheatgenetics.coordinate.activities.Main.
                                                        this.loadTemplate(org.wheatgenetics.
                                                            coordinate.activities.Main.MODE_DNA,
                                                        templateModel);
                                                }
                                                catch (final org.json.JSONException e) {}
                                                return;
                                            }

                                        optionalField.setValue(value);

                                        if (optionalField.namesAreEqual("Person")
                                        ||  optionalField.namesAreEqual("Name"  ))
                                        {
                                            assert null != org.wheatgenetics.coordinate.activities.
                                                Main.this.sharedPreferences;
                                            org.wheatgenetics.coordinate.activities.Main.this.
                                                sharedPreferences.setPerson(
                                                optionalField.getValue());
                                        }
                                    }
                                    i++;
                                }
                            }
                            assert null != dialog;
                            dialog.cancel();
                            try
                            {
                                org.wheatgenetics.coordinate.activities.Main.this.tempLoad(mode);
                            }
                            catch (final org.json.JSONException e) {}
                        }
                    });
            }
            alertDialog = builder.setNegativeButton(org.wheatgenetics.coordinate.R.string.cancel,
                org.wheatgenetics.androidlibrary.Utils.cancellingOnClickListener()).create();
        }
        assert null != alertDialog;
        alertDialog.getWindow().setSoftInputMode(
            android.view.WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        alertDialog.setCancelable(false);
        alertDialog.show();
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
            this.showShortToast(org.wheatgenetics.coordinate.R.string.template_not_deleted_default);  // TODO: Move to TemplatesTable.
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

    private void importOptionalFields(final java.lang.String json)
    {
        try
        {
            this.nonNullOptionalFields = new org.wheatgenetics.coordinate.
                optionalField.NonNullOptionalFields(json);          // throws org.json.JSONException
        }
        catch (final org.json.JSONException e) { this.nonNullOptionalFields = null; }
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
                final android.widget.TextView nameTextView = (android.widget.TextView)
                    view.findViewById(org.wheatgenetics.coordinate.R.id.fieldText);
                assert null != nameTextView;
                nameTextView.setText(optionalField.getName());
            }
            {
                final android.widget.TextView valueTextView = (android.widget.TextView)
                    view.findViewById(org.wheatgenetics.coordinate.R.id.valueText);
                assert null != valueTextView;
                if (first)
                {
                    valueTextView.setText(this.gridTitle);
                    first = false;
                }
                else valueTextView.setText(optionalField.getValue());
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
                    cell_cnt.setTag(org.wheatgenetics.coordinate.activities.Main.getTag(r, c));
                    cell_cnt.setTag(org.wheatgenetics.coordinate.R.string.cell_col, c);
                    cell_cnt.setTag(org.wheatgenetics.coordinate.R.string.cell_row, r);
                    brow.addView(cell_box);
                }
            }
            this.gridTableLayout.addView(brow);
        }

        this.showTemplateUI();
    }

    private java.io.File createExportFile()
    {
        assert null != this.templateModel;
        assert null != this.exportDir    ;
        try { return this.exportDir.createNewFile(this.templateModel.getTitle()); }
        catch (final java.io.IOException e)
        {
            org.wheatgenetics.coordinate.activities.Main.sendErrorLogMsg(e);
            return null;
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
    // endregion

    // region Action Drawer Methods
    private void createNewGrid() throws org.json.JSONException
    {
        if (0 == this.gridId)
            this.loadExistingTemplateOrCreateNewTemplate();
        else
            if (this.gridId >= 0 && mLastExportGridId == this.gridId)
                this.newGridNow();                                  // throws org.json.JSONException
            else
                this.confirm(
                    /* message     => */ org.wheatgenetics.coordinate.R.string.new_grid_warning,
                    /* yesRunnable => */ new java.lang.Runnable()
                    {
                        @java.lang.Override
                        public void run()
                        { org.wheatgenetics.coordinate.activities.Main.this.exportGrid(); }
                    },
                    /* noRunnable => */ new java.lang.Runnable()
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
        if (0 != this.gridId) this.confirm(
            /* message     => */ org.wheatgenetics.coordinate.R.string.delete_grid_warning,
            /* yesRunnable => */ new java.lang.Runnable()
            {
                @java.lang.Override
                public void run()
                {
                    if (org.wheatgenetics.coordinate.activities.Main.this.deleteGrid(
                    org.wheatgenetics.coordinate.activities.Main.this.gridId))
                    {
                        org.wheatgenetics.coordinate.activities.Main.this.showLongToast(
                            org.wheatgenetics.coordinate.R.string.grid_deleted);
                        org.wheatgenetics.coordinate.activities.Main.this.gridId = 0;
                        org.wheatgenetics.coordinate.activities.Main.this.
                            optionalFieldLayout.setVisibility(android.view.View.INVISIBLE);
                        org.wheatgenetics.coordinate.activities.Main.this.
                            gridAreaLayout.setVisibility(android.view.View.INVISIBLE);
                        org.wheatgenetics.coordinate.activities.Main.this.
                            loadExistingTemplateOrCreateNewTemplate();
                    }
                    else
                        org.wheatgenetics.coordinate.activities.Main.this.showLongToast(
                            org.wheatgenetics.coordinate.R.string.grid_not_deleted);
                }
            });
    }

    private void createNewTemplate()
    {
        assert null != this.templateModel;
        this.templateModel.clearExcludes();

        this.nonNullOptionalFields =
            org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields.makeNew();

        android.view.View view;
        {
            final android.view.LayoutInflater layoutInflater = this.getLayoutInflater();
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
                    public void onClick(final android.content.DialogInterface dialog,
                    final int which)
                    {
                        assert null != dialog;
                        dialog.cancel();

                        final java.lang.String name = nameTextEdit.getText().toString().trim();
                        final java.lang.String cols = colsTextEdit.getText().toString().trim();
                        final java.lang.String rows = rowsTextEdit.getText().toString().trim();

                        assert null !=
                            org.wheatgenetics.coordinate.activities.Main.this.templateModel;
                        org.wheatgenetics.coordinate.activities.Main.this.templateModel.assign(
                            /* title => */ name, /* rows => */ rows, /* cols => */ cols);

                        if (0 == name.length())
                        {
                            org.wheatgenetics.coordinate.activities.Main.this.showLongToast(
                                org.wheatgenetics.coordinate.R.string.template_no_name);
                            org.wheatgenetics.coordinate.activities.Main.this.createNewTemplate();
                            return;
                        }

                        if (0 == rows.length()
                        || !org.wheatgenetics.coordinate.activities.Main.this.templateModel.rowsIsSpecified())
                        {
                            org.wheatgenetics.coordinate.activities.Main.this.showLongToast(
                                org.wheatgenetics.coordinate.R.string.no_rows);
                            org.wheatgenetics.coordinate.activities.Main.this.createNewTemplate();
                            return;
                        }

                        if (0 == cols.length()
                        || !org.wheatgenetics.coordinate.activities.Main.this.templateModel.colsIsSpecified())
                        {
                            org.wheatgenetics.coordinate.activities.Main.this.showLongToast(
                                org.wheatgenetics.coordinate.R.string.no_cols);
                            org.wheatgenetics.coordinate.activities.Main.this.createNewTemplate();
                            return;
                        }

                        org.wheatgenetics.coordinate.activities.Main.this.inputTemplateNewExtra();
                    }
                })
            .setNegativeButton(org.wheatgenetics.coordinate.R.string.cancel,
                org.wheatgenetics.androidlibrary.Utils.cancellingOnClickListener()).show();
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
                                    org.wheatgenetics.coordinate.activities.Main.this.loadTemplate(
                                        org.wheatgenetics.coordinate.activities.Main.MODE_DNA,
                                        templateModel);             // throws org.json.JSONException
                                }
                                catch (final org.json.JSONException e) {}
                                break;

                            default:
                                try
                                {
                                    org.wheatgenetics.coordinate.activities.Main.this.loadTemplate(
                                        org.wheatgenetics.coordinate.activities.Main.MODE_SAVED,
                                        templateModel);             // throws org.json.JSONException
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
                        if (null != templateModel)
                            org.wheatgenetics.coordinate.activities.Main.this.confirm(
                                /* title => */
                                    org.wheatgenetics.coordinate.R.string.delete_template,
                                /* message => */
                                    org.wheatgenetics.coordinate.R.string.delete_template_warning,
                                /* yesRunnable => */ new java.lang.Runnable()
                                {
                                    @java.lang.Override
                                    public void run()
                                    {
                                        if (org.wheatgenetics.coordinate.activities.
                                        Main.this.deleteTemplate(templateModel))
                                        {
                                            org.wheatgenetics.coordinate.activities.Main.this.
                                                showLongToast(org.wheatgenetics.coordinate.
                                                    R.string.template_deleted);
                                            org.wheatgenetics.coordinate.activities.
                                                Main.this.loadExistingTemplateOrCreateNewTemplate();
                                        }
                                        else
                                            org.wheatgenetics.coordinate.activities.Main.this.
                                                showLongToast(org.wheatgenetics.coordinate.
                                                    R.string.template_not_deleted);
                                    }
                                });
                    }
                }).show();
    }

    @android.annotation.SuppressLint("DefaultLocale")
    private void importGrid()
    {
        int pos = 0;

        java.lang.String names  [];
        long             indexes[];
        {
            final android.database.Cursor gridCursor =
                new org.wheatgenetics.coordinate.database.GridsTable(this).getAllGrids();
            if (null == gridCursor)
            {
                names   = new java.lang.String[1];
                indexes = new long            [1];
            }
            else
            {
                {
                    final int size = gridCursor.getCount();

                    names   = new java.lang.String[size];
                    indexes = new long            [size];
                }

                while (gridCursor.moveToNext())
                {
                    final org.wheatgenetics.coordinate.database.GridsTable gridsTable =
                        new org.wheatgenetics.coordinate.database.GridsTable(this);
                    if (gridsTable.copyAll(gridCursor))
                    {
                        names[pos] = java.lang.String.format(
                            "Grid: %s\n Template: %s\n Size: (%d, %d) Date: %s\n", gridsTable.title,
                            gridsTable.templateTitle, gridsTable.templateCols,
                            gridsTable.templateRows, org.wheatgenetics.coordinate.utils.Utils.
                                formatDate(gridsTable.timestamp));
                        indexes[pos++] = gridsTable.id;
                    }
                }
                gridCursor.close();
            }
        }

        if (0 == pos)
            this.alert(org.wheatgenetics.coordinate.R.string.no_templates);
        else
        {
            final long gridIds[] = indexes;

            final android.app.AlertDialog.Builder builder =
                new android.app.AlertDialog.Builder(this);
            builder.setTitle(org.wheatgenetics.coordinate.R.string.import_grid).setItems(names,
                new android.content.DialogInterface.OnClickListener()
                {
                    @java.lang.Override
                    public void onClick(final android.content.DialogInterface dialog,
                    final int which)
                    {
                        if (which < gridIds.length)
                        {
                            final long gridId = gridIds[which];

                            {
                                assert null != org.wheatgenetics.coordinate.
                                    activities.Main.this.sharedPreferences;
                                org.wheatgenetics.coordinate.activities.
                                    Main.this.sharedPreferences.setCurrentGrid(gridId);
                            }

                            final org.wheatgenetics.coordinate.database.GridsTable gridsTable =
                                new org.wheatgenetics.coordinate.database.GridsTable(
                                    org.wheatgenetics.coordinate.activities.Main.this);
                            if (gridsTable.get(gridId))
                            {
                                org.wheatgenetics.coordinate.activities.Main.this.templateModel.assign(
                                    /* title => */ gridsTable.templateTitle,
                                    /* rows  => */ gridsTable.templateRows ,
                                    /* cols  => */ gridsTable.templateCols );
                                org.wheatgenetics.coordinate.activities.Main.this.gridId =
                                    gridsTable.id;
                                org.wheatgenetics.coordinate.activities.Main.this.gridTitle =
                                    gridsTable.title;
                                org.wheatgenetics.coordinate.activities.Main.this.templateModel.
                                    setType(gridsTable.templateType);

                                final org.wheatgenetics.coordinate.database.TemplatesTable
                                    templatesTable = org.wheatgenetics.coordinate.activities.
                                    Main.this.templatesTable();

                                if (templatesTable.get(gridsTable.templateId))  // database
                                {
                                    org.wheatgenetics.coordinate.activities.Main.this.
                                        importOptionalFields(templatesTable.options);

                                    org.wheatgenetics.coordinate.activities.Main.this.templateModel.
                                        setRowNumbering(templatesTable.rowNumbering);  // model
                                    org.wheatgenetics.coordinate.activities.Main.this.templateModel.
                                        setColNumbering(templatesTable.colNumbering);  // model
                                }

                                org.wheatgenetics.coordinate.activities.
                                    Main.this.templateModel.clearExcludes();  // model

                                org.wheatgenetics.coordinate.activities.Main.this.populateTemplate();
                            }
                            else
                                org.wheatgenetics.coordinate.activities.Main.this.alert(
                                    org.wheatgenetics.coordinate.R.string.import_grid_failed);

                            assert null != dialog;
                            dialog.cancel();
                        }
                    }
                }).show();
        }
    }

    private void exportGrid()
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
            fileNameEditText.setText(this.nonNullOptionalFields.getDatedFirstValue());

            builder.setTitle(this.getString(org.wheatgenetics.coordinate.R.string.filename_set))
                .setView(view).setPositiveButton(this.okStringResource,
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
                            if (0 == fileName.length())
                                org.wheatgenetics.coordinate.activities.Main.this.alert(
                                    org.wheatgenetics.coordinate.R.string.filename_empty);
                            else
                            {
                                {
                                    final java.io.File exportFile = org.wheatgenetics.coordinate.
                                        activities.Main.this.createExportFile();

                                    org.wheatgenetics.coordinate.activities.Main.this.exporter =
                                        new org.wheatgenetics.coordinate.activities.Main.Exporter(
                                            /* context => */
                                                org.wheatgenetics.coordinate.activities.Main.this,
                                            /* progressDialogTitle => */ org.wheatgenetics.
                                                coordinate.activities.Main.this.getString(
                                                    org.wheatgenetics.coordinate.
                                                        R.string.exporting_title),
                                            /* progressDialogMessage => */ org.wheatgenetics.
                                                coordinate.activities.Main.this.getString(
                                                    org.wheatgenetics.coordinate.
                                                        R.string.exporting_body),
                                            /* templateId => */ org.wheatgenetics.coordinate.
                                                activities.Main.this.templateModel.getId(),
                                            /* exportFileName => */ fileName                    ,
                                            /* absolutePath   => */ exportFile.getAbsolutePath());
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
                try                                    { this.createNewGrid(); }
                catch (final org.json.JSONException e) { return false        ; }
                break;

            case org.wheatgenetics.coordinate.R.id.menu_delete_grid: this.deleteGrid(); break;

            case org.wheatgenetics.coordinate.R.id.menu_new_template:
                this.createNewTemplate(); break;

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

        assert null != this.drawerLayout;
        this.drawerLayout.closeDrawers();

        return true;
    }
    // endregion
    // endregion

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
                this.showShortToast(org.wheatgenetics.coordinate.R.string.update_failed);
                return;
            }
        }

        android.view.View view = this.gridTableLayout.findViewWithTag(
            org.wheatgenetics.coordinate.activities.Main.getTag(mCurRow, mCurCol));

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
            if (this.isExcludedRow(mCurRow) || this.isExcludedCol(mCurCol) || this.isExcludedCell(mCurRow, mCurCol))
                if (!this.getNextFreeCell()) endOfCell = true;

        value = org.wheatgenetics.javalib.Utils.makeEmptyIfNull(
            this.getValue(this.gridId, mCurRow, mCurCol));

        this.cellIDEditText.setSelectAllOnFocus(true);
        this.cellIDEditText.setText(value);
        this.cellIDEditText.selectAll();
        this.cellIDEditText.requestFocus();

        view = this.gridTableLayout.findViewWithTag(org.wheatgenetics.coordinate.activities.Main.getTag(mCurRow, mCurCol));
        if (null != view)
            if (!this.isExcludedRow(mCurRow) && !this.isExcludedCol(mCurCol) && !this.isExcludedCell(mCurRow, mCurCol))
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
    }

    private void resetCurrentCell()
    {
        if (null != this.currentCellView)
        {
            int r = -1, c = -1;

            {
                java.lang.Object obj =
                    this.currentCellView.getTag(org.wheatgenetics.coordinate.R.string.cell_col);
                if (obj instanceof java.lang.Integer) c = (java.lang.Integer) obj;

                obj = this.currentCellView.getTag(org.wheatgenetics.coordinate.R.string.cell_row);
                if (obj instanceof java.lang.Integer) r = (java.lang.Integer) obj;
            }

            int state;
            if (this.isExcludedRow(r) || this.isExcludedCol(c) || this.isExcludedCell(r, c))
                state = org.wheatgenetics.coordinate.activities.Main.STATE_INACTIVE;
            else
            {
                final java.lang.String value = org.wheatgenetics.javalib.Utils.makeEmptyIfNull(
                    this.getValue(this.gridId, r, c));
                state = 0 == value.length() ?
                    org.wheatgenetics.coordinate.activities.Main.STATE_NORMAL :
                    org.wheatgenetics.coordinate.activities.Main.STATE_DONE    ;
            }
            this.setCellState(this.currentCellView, state);
        }
    }

    private long createGrid(final long templateId)
    {
        final org.wheatgenetics.coordinate.database.GridsTable gridsTable =
            new org.wheatgenetics.coordinate.database.GridsTable(this);
        gridsTable.templateId = templateId                          ;
        gridsTable.timestamp  = java.lang.System.currentTimeMillis();
        assert null != this.nonNullOptionalFields;
        gridsTable.title = this.nonNullOptionalFields.getFirstValue();
        return gridsTable.insert();
    }
}