package org.wheatgenetics.coordinate.activities;

import org.wheatgenetics.coordinate.R;
import org.wheatgenetics.coordinate.Coordinate;
import org.wheatgenetics.coordinate.barcodes.IntentIntegrator;
import org.wheatgenetics.coordinate.barcodes.IntentResult;
import org.wheatgenetics.coordinate.database.EntriesTable;
import org.wheatgenetics.coordinate.database.GridsTable;
import org.wheatgenetics.coordinate.model.GridModel;
import org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields;
import org.wheatgenetics.coordinate.optionalField.OptionalField;
import org.wheatgenetics.coordinate.utils.Constants;

/**
 * Uses:
 * android.os.Bundle
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
 * android.content.SharedPreferences
 * android.content.pm.PackageInfo
 * android.content.pm.PackageManager
 * android.database.Cursor
 * android.graphics.Point
 * android.media.MediaPlayer
 * android.media.MediaScannerConnection
 * android.net.Uri
 * android.os.AsyncTask
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
 * android.view.ViewGroup
 * android.view.WindowManager
 * android.view.inputmethod.EditorInfo
 * android.widget.AdapterView
 * android.widget.ArrayAdapter
 * android.widget.Button
 * android.widget.EditText
 * android.widget.ImageView
 * android.widget.LinearLayout
 * android.widget.ListView
 * android.widget.ScrollView
 * android.widget.Spinner
 * android.widget.TableLayout
 * android.widget.TableRow
 * android.widget.TextView
 * android.widget.TextView.OnEditorActionListener
 * android.widget.Toast
 *
 * org.json.JSONException
 *
 * org.wheatgenetics.coordinate.database.TemplatesTable
 * org.wheatgenetics.coordinate.model.TemplateModel
 * org.wheatgenetics.coordinate.model.TemplateType
 * org.wheatgenetics.coordinate.utils.Utils
 */

public class Main extends android.support.v7.app.AppCompatActivity
implements android.view.View.OnClickListener, android.widget.TextView.OnEditorActionListener,
android.view.View.OnKeyListener
{
    private class Exporter extends android.os.AsyncTask<
    java.lang.Void, java.lang.String, java.lang.Boolean>
    {
        private final android.content.Context context                     ;
        private final long                    templateId                  ;
        private final java.lang.String        exportFileName, absolutePath;

        private android.app.ProgressDialog                           progressDialog;
        private org.wheatgenetics.coordinate.database.TemplatesTable templatesTable;
        private java.lang.String                                     message = null;
        private java.io.File                                         exportFile    ;

        private java.io.File makeExportFile()
        {
            assert this.absolutePath   != null;
            assert this.exportFileName != null;
            final java.io.File exportFile =
                new java.io.File(this.absolutePath, this.exportFileName + ".csv");
            if (this.exportFile.exists()) this.exportFile.delete();
            return exportFile;
        }

        private boolean exportSeed()
        {
            java.lang.String tray_id = "", person = "", date = "";
            assert org.wheatgenetics.coordinate.activities.Main.this.nonNullOptionalFields != null;
            for (final org.wheatgenetics.coordinate.optionalField.OptionalField optionalField:
            org.wheatgenetics.coordinate.activities.Main.this.nonNullOptionalFields)
            {
                assert optionalField != null;
                if (optionalField.nameEqualsIgnoreCase("Tray"))
                    tray_id = optionalField.getValue();
                else
                    if (optionalField.nameEqualsIgnoreCase("Person"))
                        person = optionalField.getValue().replace(" ", "_");
                    else
                        if (optionalField.nameEqualsIgnoreCase("date"))
                            date = optionalField.getValue();
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
                assert this.templatesTable != null;
                for (int col = 1; col <= this.templatesTable.cols; col++)  // model
                {
                    for (int row = 1; row <= this.templatesTable.rows; row++)  // model
                    {
                        {
                            java.lang.String data;
                            if (isExcludedRow(row) || isExcludedCol(col) || isExcludedCell(row, col))
                                data = "exclude";
                            else
                            {
                                data = getDataEntry(
                                    org.wheatgenetics.coordinate.activities.Main.this.gridId,
                                    row, col);
                                if (data == null) data = "BLANK_";
                            }
                            csvWriter.write(tray_id                                      );  // tray id
                            csvWriter.write("%s_C%02d_R%d", this.exportFileName, col, row);  // cell_id
                            csvWriter.write(                                             );  // tray_num
                            csvWriter.write(col                                          );  // tray_column
                            csvWriter.write(row                                          );  // tray_row
                            csvWriter.write(data                                         );  // seed_id
                        }
                        csvWriter.write(person);  // person
                        csvWriter.write(date  );  // date

                        csvWriter.endRecord();
                    }
                    publishProgress(org.wheatgenetics.coordinate.activities.Main.this.getString(
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
            assert org.wheatgenetics.coordinate.activities.Main.this.nonNullOptionalFields != null;
            for (final org.wheatgenetics.coordinate.optionalField.OptionalField optionalField:
            org.wheatgenetics.coordinate.activities.Main.this.nonNullOptionalFields)
            {
                assert optionalField != null;
                if (optionalField.nameEqualsIgnoreCase("date"))
                    date = optionalField.getValue();
                else
                    if (optionalField.nameEqualsIgnoreCase("Plate"))
                        plate_id = optionalField.getValue();
                    else
                        if (optionalField.nameEqualsIgnoreCase("Plate Name"))
                            plate_name = optionalField.getValue();
                        else
                            if (optionalField.nameEqualsIgnoreCase("person"))
                                dna_person = optionalField.getValue().replace(" ", "_");
                            else
                                if (optionalField.nameEqualsIgnoreCase("Notes"))
                                    notes = optionalField.getValue();
                                else
                                    if (optionalField.nameEqualsIgnoreCase("tissue_type"))
                                        tissue_type = optionalField.getValue();
                                    else
                                        if (optionalField.nameEqualsIgnoreCase("extraction"))
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
                assert this.templatesTable != null;
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
                                        tissue_id = getDataEntry(
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
                    publishProgress(org.wheatgenetics.coordinate.activities.Main.this.getString(
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
                    org.wheatgenetics.coordinate.activities.Main.this.nonNullOptionalFields != null;
                for (final org.wheatgenetics.coordinate.optionalField.OptionalField optionalField:
                org.wheatgenetics.coordinate.activities.Main.this.nonNullOptionalFields)
                {
                    assert optionalField != null;
                    csvWriter.write(optionalField.getName());
                }
                csvWriter.endRecord();

                for (int col = 1; col <= this.templatesTable.cols; col++)  // model
                {
                    for (int row = 1; row <= this.templatesTable.rows; row++)  // model
                    {
                        {
                            java.lang.String data;
                            if (isExcludedRow(row) || isExcludedCol(col) || isExcludedCell(row, col))
                                data = "exclude";
                            else
                            {
                                data = getDataEntry(
                                    org.wheatgenetics.coordinate.activities.Main.this.gridId,
                                    row, col);
                                if (data == null) data = "";
                            }
                            csvWriter.write(data);
                        }
                        csvWriter.write(col);
                        csvWriter.write(row);

                        for (final
                        org.wheatgenetics.coordinate.optionalField.OptionalField optionalField:
                        org.wheatgenetics.coordinate.activities.Main.this.nonNullOptionalFields)
                        {
                            assert optionalField != null;
                            csvWriter.write(optionalField.getValue());
                        }

                        csvWriter.endRecord();
                    }
                    publishProgress(org.wheatgenetics.coordinate.activities.Main.this.getString(
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

            assert this.exportFile != null;
            intent.putExtra(android.content.Intent.EXTRA_STREAM,
                android.net.Uri.parse(this.exportFile.getAbsolutePath()));

            intent.setType("text/plain");

            org.wheatgenetics.coordinate.activities.Main.this.startActivity(
                android.content.Intent.createChooser(intent,
                    org.wheatgenetics.coordinate.activities.Main.this.getString(
                        org.wheatgenetics.coordinate.R.string.share_file)));
        }

        Exporter(final android.content.Context context, final long templateId,
        final java.lang.String exportFileName, final java.lang.String absolutePath)
        {
            super();

            this.context        = context       ;
            this.templateId     = templateId    ;
            this.exportFileName = exportFileName;
            this.absolutePath   = absolutePath  ;
        }

        @java.lang.Override
        protected void onPreExecute()
        {
            super.onPreExecute();

            this.progressDialog = new android.app.ProgressDialog(this.context);
            this.progressDialog.setTitle(
                org.wheatgenetics.coordinate.activities.Main.this.getString(
                    org.wheatgenetics.coordinate.R.string.exporting_title));
            this.progressDialog.setMessage(
                org.wheatgenetics.coordinate.activities.Main.this.getString(
                    org.wheatgenetics.coordinate.R.string.exporting_body));
            this.progressDialog.setCancelable(true);
            this.progressDialog.setOnCancelListener(
                new android.content.DialogInterface.OnCancelListener()
                {
                    @java.lang.Override
                    public void onCancel(final android.content.DialogInterface dialog)
                    { org.wheatgenetics.coordinate.activities.Main.this.cancelExporter(); }
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
            if (msg != null)
            {
                final java.lang.String message = msg[0];
                if (message != null)
                {
                    assert this.progressDialog != null;
                    this.progressDialog.setMessage(message);
                }
            }
        }

        @java.lang.Override
        protected void onPostExecute(final java.lang.Boolean result)
        {
            if (this.progressDialog != null && this.progressDialog.isShowing())
            {
                this.progressDialog.dismiss();
                this.progressDialog = null;
            }

            // TODO: When grid is reset, make a new one.
            if (result != null && result)
            {
                org.wheatgenetics.coordinate.activities.Main.this.mLastExportGridId =  // TODO: Make into
                    org.wheatgenetics.coordinate.activities.Main.this.gridId;          // TODO:  Main method.
                org.wheatgenetics.coordinate.utils.Utils.alert(this.context, Coordinate.appName,
                    org.wheatgenetics.coordinate.activities.Main.this.getString(
                        org.wheatgenetics.coordinate.R.string.export_success),
                    new java.lang.Runnable()
                    {
                        @java.lang.Override
                        public void run()
                        {
                            org.wheatgenetics.coordinate.utils.Utils.confirm(
                                org.wheatgenetics.coordinate.activities.Main.Exporter.this.context,
                                Coordinate.appName,
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
                if (this.message == null)
                    this.message = org.wheatgenetics.coordinate.activities.Main.this.getString(
                        org.wheatgenetics.coordinate.R.string.export_no_data);
                org.wheatgenetics.coordinate.utils.Utils.alert(this.context,
                    org.wheatgenetics.coordinate.activities.Main.this.getString(
                        org.wheatgenetics.coordinate.R.string.export),
                    this.message);
            }
        }
    }

    private static class OtherAppsArrayAdapter extends android.widget.ArrayAdapter<java.lang.String>
    {
        private final java.lang.String texts[] =
            {"Field Book", "Inventory", "1KK"/*, "Intercross", "Rangle"*/ };

        OtherAppsArrayAdapter(final android.app.Activity context)
        {
            super(
                /* context  => */ context                                      ,
                /* resource => */ org.wheatgenetics.coordinate.R.layout.appline);
            this.addAll(this.texts);
        }

        @java.lang.Override
        public @android.support.annotation.NonNull android.view.View getView(final int position,
        final android.view.View convertView,
        @android.support.annotation.NonNull final android.view.ViewGroup parent)
        {
            android.view.View appLineView;
            {
                final android.view.LayoutInflater layoutInflater = (android.view.LayoutInflater)
                    this.getContext().getSystemService(
                        android.content.Context.LAYOUT_INFLATER_SERVICE);
                assert layoutInflater != null;
                appLineView = layoutInflater.inflate(
                    org.wheatgenetics.coordinate.R.layout.appline, null, true);
            }
            assert appLineView != null;
            {
                final android.widget.TextView textView = (android.widget.TextView)
                    appLineView.findViewById(org.wheatgenetics.coordinate.R.id.txt);
                assert textView != null;
                textView.setText(this.texts[position]);
            }
            {
                final java.lang.Integer resIds[] = {
                    org.wheatgenetics.coordinate.R.drawable.other_ic_field_book,
                    org.wheatgenetics.coordinate.R.drawable.other_ic_inventory ,
                    org.wheatgenetics.coordinate.R.drawable.other_ic_1kk       };
                final android.widget.ImageView imageView = (android.widget.ImageView)
                    appLineView.findViewById(org.wheatgenetics.coordinate.R.id.img);
                assert imageView != null;
                imageView.setImageResource(resIds[position]);
            }
            return appLineView;
        }
    }

    // region Constants
    private static final int STATE_NORMAL = 0, STATE_DONE = 1, STATE_ACTIVE = 2, STATE_INACTIVE = 3;
    private static final int MODE_DNA     = 0, MODE_SAVED = 1, MODE_DEFAULT = 2;
    // endregion

    // region Fields
    // region Widget Fields
    private android.support.v4.widget.DrawerLayout       drawerLayout         ;
    private android.support.v7.app.ActionBarDrawerToggle actionBarDrawerToggle;

    private android.widget.LinearLayout parent         ;
    private android.widget.ScrollView   changeContainer;

    private android.widget.LinearLayout mainLayout,
        gridAreaLayout, gridTableLayout, optionalFieldLayout;
    private android.widget.EditText cellIDEditText        ;
    private android.widget.TextView templateTitleTextView ;
    private android.view.View       currentCellView = null;
    // endregion

    private long             gridId     =  0             ;
    private java.lang.String mGridTitle = ""             ;
    private int              mCurRow    =  1, mCurCol = 1;

    private android.content.SharedPreferences ep;

    // region Template
    private org.wheatgenetics.coordinate.model.TemplateModel templateModel =
        new org.wheatgenetics.coordinate.model.TemplateModel(
            "", org.wheatgenetics.coordinate.model.TemplateType.SEED, 20, 10, true, false);

    private java.util.List<java.lang.Integer> excludeRows = new java.util.ArrayList<>();
    private java.util.List<java.lang.Integer> excludeCols = new java.util.ArrayList<>();
    // private org.wheatgenetics.coordinate.model.TemplatesTable templatesTable;
    // endregion

    private java.lang.String templateOptions[];

    private org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields nonNullOptionalFields;

    private org.wheatgenetics.coordinate.activities.Main.Exporter exporter          = null;
    private long                                                  mLastExportGridId =   -1;
    // endregion

    // region Class Method
    private static int sendErrorLogMsg(final java.lang.Exception e)
    {
        assert e != null;
        return android.util.Log.e("Coordinate", e.getMessage());
    }
    // endregion

    private org.wheatgenetics.coordinate.optionalField.CheckedOptionalFields
    makeCheckedOptionalFields()
    {
        return new org.wheatgenetics.coordinate.optionalField.CheckedOptionalFields(
            this.nonNullOptionalFields);
    }

    // region Overridden Methods
    @java.lang.Override
    protected void onCreate(final android.os.Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.setContentView(org.wheatgenetics.coordinate.R.layout.main);

        this.nonNullOptionalFields =
            new org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields();
        this.nonNullOptionalFields.add    ("Plate Id");
        this.nonNullOptionalFields.addDate("Date"    );

        this.templateOptions = new java.lang.String[]{
            this.getResources().getString(org.wheatgenetics.coordinate.R.string.template_load),
            this.getResources().getString(org.wheatgenetics.coordinate.R.string.template_new )};

        final android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar)
            this.findViewById(org.wheatgenetics.coordinate.R.id.toolbar);

        this.setSupportActionBar(toolbar);
        toolbar.bringToFront();

        if (this.getSupportActionBar() != null)
        {
            this.getSupportActionBar().setTitle(null);
            this.getSupportActionBar().getThemedContext();
            this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            this.getSupportActionBar().setHomeButtonEnabled(true);
        }

        ep = this.getSharedPreferences("Settings", 0);

        this.drawerLayout = (android.support.v4.widget.DrawerLayout)
            this.findViewById(org.wheatgenetics.coordinate.R.id.drawer_layout);
        {
            final android.support.design.widget.NavigationView navigationView =
                (android.support.design.widget.NavigationView)
                this.findViewById(org.wheatgenetics.coordinate.R.id.nvView);
            assert navigationView != null;
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
                        assert ep             != null;
                        assert personTextView != null;
                        personTextView.setText(ep.getString("Person", ""));
                    }

                    {
                        final android.widget.TextView templateTitleTextView =
                            (android.widget.TextView)
                            org.wheatgenetics.coordinate.activities.Main.this.findViewById(
                                org.wheatgenetics.coordinate.R.id.templateLabel);
                        assert
                            org.wheatgenetics.coordinate.activities.Main.this.templateModel != null;
                        assert templateTitleTextView != null;
                        templateTitleTextView.setText(org.wheatgenetics.coordinate.
                            activities.Main.this.templateModel.getTitle());
                    }
                }

                @java.lang.Override
                public void onDrawerClosed(final android.view.View drawerView) {}
            };
        this.actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        assert this.drawerLayout != null;
        this.drawerLayout.setDrawerListener(this.actionBarDrawerToggle);

        parent = new android.widget.LinearLayout(this);
        changeContainer = new android.widget.ScrollView(this);
        changeContainer.removeAllViews();
        changeContainer.addView(parent);

        this.mainLayout = (android.widget.LinearLayout) this.findViewById(org.wheatgenetics.coordinate.R.id.mainLayout);
        this.gridAreaLayout = (android.widget.LinearLayout) this.findViewById(org.wheatgenetics.coordinate.R.id.gridArea);
        this.optionalFieldLayout = (android.widget.LinearLayout) this.findViewById(org.wheatgenetics.coordinate.R.id.optionalLayout);

        this.gridTableLayout = (android.widget.TableLayout) this.findViewById(org.wheatgenetics.coordinate.R.id.dataTable);

        this.templateTitleTextView = (android.widget.TextView) this.findViewById(org.wheatgenetics.coordinate.R.id.templateText);

        this.cellIDEditText = (android.widget.EditText) this.findViewById(org.wheatgenetics.coordinate.R.id.dataEdit);
        assert this.cellIDEditText != null;
        this.cellIDEditText.setImeActionLabel(getString(org.wheatgenetics.coordinate.R.string.keyboard_save), android.view.KeyEvent.KEYCODE_ENTER);
        this.cellIDEditText.setOnEditorActionListener(this);

        //this.cellIDEditText.setOnKeyListener(this);

        assert this.templateModel != null;
        this.templateTitleTextView.setText(this.templateModel.getTitle());

        this.mainLayout.setVisibility(android.view.View.INVISIBLE);

        try { this.initDb(); } catch (final org.json.JSONException e) {}
        this.createDirs();

        if (ep.getLong("CurrentGrid", -1) != -1)
            try { this.loadGrid(ep.getLong("CurrentGrid", -1)); }
            catch (final java.lang.Exception e) {}
        else
            this.loadExistingOrNewTemplate();

        this.showTemplateUI();

        if (ep.getInt("UpdateVersion", -1) < this.getVersion())
        {
            final android.content.SharedPreferences.Editor ed = ep.edit();
            ed.putInt("UpdateVersion", getVersion());
            ed.apply();
            this.changelog();
        }
    }

    @java.lang.Override
    protected void onPostCreate(final android.os.Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        assert this.actionBarDrawerToggle != null;
        this.actionBarDrawerToggle.syncState();
    }

    @java.lang.Override
    public boolean onCreateOptionsMenu(final android.view.Menu menu)
    {
        new android.view.MenuInflater(org.wheatgenetics.coordinate.activities.Main.this).
            inflate(org.wheatgenetics.coordinate.R.menu.mainmenu, menu);
        assert menu != null;
        menu.findItem(org.wheatgenetics.coordinate.R.id.barcode_camera).setVisible(true);
        return true;
    }

    @java.lang.Override
    public boolean onOptionsItemSelected(final android.view.MenuItem item)
    {
        assert this.actionBarDrawerToggle != null;
        if (this.actionBarDrawerToggle.onOptionsItemSelected(item)) return true;

        assert item != null;
        switch (item.getItemId())
        {
            case android.R.id.home:
                assert this.drawerLayout != null;
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
    public boolean onKey(final android.view.View v, final int keyCode, final android.view.KeyEvent event)
    {
        if ((event.getAction() == android.view.KeyEvent.ACTION_DOWN) && (keyCode == android.view.KeyEvent.KEYCODE_ENTER))
        {
            this.saveData();
            return true;
        }
        return false;
    }

    @java.lang.Override
    public boolean onEditorAction(final android.widget.TextView v, final int actionId, final android.view.KeyEvent event)
    {
        if (actionId == android.view.inputmethod.EditorInfo.IME_ACTION_DONE)
        {
            this.saveData();
            return true;
        }
        else
        if (event != null)
            if ((event.getAction() == android.view.KeyEvent.ACTION_DOWN) && (event.getKeyCode() == android.view.KeyEvent.KEYCODE_ENTER))
            {
                this.saveData();
                return true;
            }
        return false;
    }

    //TODO don't toggle already selected cell
    @java.lang.Override
    public void onClick(final android.view.View v)  // v is a cell
    {
        int    c = -1;
        int    r = -1;
        Object obj;

        obj = v.getTag(org.wheatgenetics.coordinate.R.string.cell_col);
        if (obj instanceof java.lang.Integer) c = (java.lang.Integer) obj;

        obj = v.getTag(org.wheatgenetics.coordinate.R.string.cell_row);
        if (obj instanceof java.lang.Integer) r = (java.lang.Integer) obj;

        if (isExcludedRow(r) || isExcludedCol(c) || isExcludedCell(r, c))
        {
            assert this.cellIDEditText != null;
            this.cellIDEditText.setText("");
            return;
        }

        if (c != -1 && r != -1)
        {
            mCurRow = r;
            mCurCol = c;

            java.lang.String data = getDataEntry(this.gridId, mCurRow, mCurCol);

            if (data != null && data.contains("exclude")) return;

            setCellState(v, STATE_ACTIVE);

            if (data == null) data = "";

            assert this.cellIDEditText != null;
            this.cellIDEditText.setSelectAllOnFocus(true);
            this.cellIDEditText.setText(data);
            this.cellIDEditText.selectAll();
            this.cellIDEditText.requestFocus();
        }

        resetCurrentCell();
        this.currentCellView = v;
    }

    @java.lang.Override
    protected void onActivityResult(final int requestCode,
    final int resultCode, final android.content.Intent data)
    {
        final org.wheatgenetics.coordinate.barcodes.IntentResult scanResult =
            org.wheatgenetics.coordinate.barcodes.IntentIntegrator.parseActivityResult(
                requestCode, resultCode, data);

        if (scanResult != null)
        {
            assert this.cellIDEditText != null;
            this.cellIDEditText.setText(scanResult.getContents());
            this.saveData();
        }
    }

    @java.lang.Override
    protected void onDestroy()
    {
        this.cancelExporter();
        super.onDestroy();
    }
    // endregion

    private int getVersion()
    {
        int v = 0;
        try { v = this.getPackageManager().getPackageInfo(getPackageName(), 0).versionCode; }
        catch (final android.content.pm.PackageManager.NameNotFoundException e)
        { org.wheatgenetics.coordinate.activities.Main.sendErrorLogMsg(e); }
        return v;
    }

    private void createDirs()
    {
        this.createDir(Constants.MAIN_PATH    );
        this.createDir(Constants.EXPORT_PATH  );
        this.createDir(Constants.TEMPLATE_PATH);
    }

    private void createDir(final java.io.File path)
    {
        final java.io.File blankFile = new java.io.File(path, ".coordinate");

        assert path != null;
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

    private void changelog()
    {
        this.parent.setOrientation(android.widget.LinearLayout.VERTICAL);
        this.parseLog(org.wheatgenetics.coordinate.R.raw.changelog_releases);

        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Main.this);
        builder.setTitle(this.getResources().getString(org.wheatgenetics.coordinate.R.string.updatemsg));
        builder.setView(changeContainer)
            .setCancelable(true)
            .setPositiveButton(this.getResources().getString(org.wheatgenetics.coordinate.R.string.ok),
                new android.content.DialogInterface.OnClickListener()
                {
                    @java.lang.Override
                    public void onClick(final android.content.DialogInterface dialog, final int which)
                    { dialog.dismiss(); }
                });
        builder.create().show();
    }

    private void parseLog(final int resId)
    {
        try
        {
            final java.io.InputStream inputStream = getResources().openRawResource(resId);
            final java.io.InputStreamReader inputStreamReader = new java.io.InputStreamReader(inputStream);
            final java.io.BufferedReader br = new java.io.BufferedReader(inputStreamReader, 8192);

            final android.widget.LinearLayout.LayoutParams lp = new android.widget.LinearLayout.LayoutParams(
                android.widget.LinearLayout.LayoutParams.MATCH_PARENT, android.widget.LinearLayout.LayoutParams.MATCH_PARENT);
            lp.setMargins(20, 5, 20, 0);

            java.lang.String curVersionName = null;
            java.lang.String line;

            while ((line = br.readLine()) != null)
            {
                final android.widget.TextView header  = new android.widget.TextView(this);
                final android.widget.TextView content = new android.widget.TextView(this);
                final android.widget.TextView spacer  = new android.widget.TextView(this);
                final android.view.View     ruler   = new android.view.View(this);

                header.setLayoutParams(lp);
                content.setLayoutParams(lp);
                spacer.setLayoutParams(lp);
                ruler.setLayoutParams(lp);

                spacer.setTextSize(5);

                ruler.setBackgroundColor(getResources().getColor(org.wheatgenetics.coordinate.R.color.main_colorAccent));
                header.setTextAppearance(getApplicationContext(), org.wheatgenetics.coordinate.R.style.ChangelogTitles);
                content.setTextAppearance(getApplicationContext(), org.wheatgenetics.coordinate.R.style.ChangelogContent);

                if (line.length() == 0)
                {
                    curVersionName = null;
                    spacer.setText("\n");
                    parent.addView(spacer);
                }
                else if (curVersionName == null)
                {
                    final java.lang.String[] lineSplit = line.split("/");
                    curVersionName = lineSplit[1];
                    header.setText(curVersionName);
                    parent.addView(header);
                    parent.addView(ruler);
                }
                else
                {
                    content.setText("•  " + line);
                    parent.addView(content);
                }
            }
        }
        catch (final java.io.IOException e) { throw new RuntimeException(e); }
    }

    private void showOtherAppsDialog()
    {
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(this.getResources().getString(
            org.wheatgenetics.coordinate.R.string.otherapps));
        {
            final android.widget.ListView listView = new android.widget.ListView(this);
            listView.setDivider      (null);
            listView.setDividerHeight(   0);
            listView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener()
                {
                    @java.lang.Override
                    public void onItemClick(final android.widget.AdapterView<?> parent,
                    final android.view.View view, final int position, final long id)
                    {
                        if (position >= 0 && position <= 2)
                        {
                            final java.lang.String[] links = {          // TODO: Update these links.
                                "https://play.google.com/store/apps/"       +
                                    "details?id=com.fieldbook.tracker"      ,
                                "https://play.google.com/store/apps/"       +
                                    "details?id=org.wheatgenetics.inventory",
                                "http://wheatgenetics.org/apps"             };
                            org.wheatgenetics.coordinate.activities.Main.this.startActivity(
                                new android.content.Intent(android.content.Intent.ACTION_VIEW,
                                    android.net.Uri.parse(links[position])));
                        }
                    }
                });
            listView.setAdapter(
                new org.wheatgenetics.coordinate.activities.Main.OtherAppsArrayAdapter(this));
            builder.setView(listView);
        }
        builder.setNegativeButton(
            this.getResources().getString(org.wheatgenetics.coordinate.R.string.ok),
            new android.content.DialogInterface.OnClickListener()
            {
                @java.lang.Override
                public void onClick(final android.content.DialogInterface dialog, final int which)
                {
                    assert dialog != null;
                    dialog.dismiss();
                }
            });
        builder.show();
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
    private void inputSeed()
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
                        final android.view.LayoutInflater layoutInflater =
                            org.wheatgenetics.coordinate.activities.Main.this.getLayoutInflater();

                        gridView = layoutInflater.inflate(
                            org.wheatgenetics.coordinate.R.layout.grid_new,
                            new android.widget.LinearLayout(this), false);

                        assert gridView != null;
                        final android.widget.LinearLayout linearLayout =
                            (android.widget.LinearLayout) gridView.findViewById(
                                org.wheatgenetics.coordinate.R.id.optionalLayout);

                        final org.wheatgenetics.coordinate.optionalField.CheckedOptionalFields
                            checkedOptionalFields = this.makeCheckedOptionalFields();

                        // load options
                        assert linearLayout != null;
                        for (final org.wheatgenetics.coordinate.optionalField.OptionalField
                        optionalField : checkedOptionalFields)
                        {
                            final android.view.View optionalFieldView = layoutInflater.inflate(
                                org.wheatgenetics.coordinate.R.layout.optional_edit, linearLayout,
                                false);

                            assert optionalFieldView != null;
                            {
                                final android.widget.TextView optionalFieldTextView =
                                    (android.widget.TextView) optionalFieldView.findViewById(
                                        org.wheatgenetics.coordinate.R.id.optionText);

                                assert optionalFieldTextView != null;
                                optionalFieldTextView.setText(optionalField.getName());
                            }

                            {
                                final android.widget.EditText optionalFieldEditText =
                                    (android.widget.EditText) optionalFieldView.findViewById(
                                        org.wheatgenetics.coordinate.R.id.optionEdit);

                                assert optionalFieldEditText != null;
                                optionalFieldEditText.setText(optionalField.getValue());
                                optionalFieldEditText.setHint(optionalField.getHint());

                                editTextArrayList.add(optionalFieldEditText);
                            }

                            linearLayout.addView(optionalFieldView);
                        }
                    }
                    assert this.templateModel != null;
                    builder.setTitle(this.templateModel.getTitle());
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

                                assert org.wheatgenetics.coordinate.activities.
                                    Main.this.nonNullOptionalFields != null;
                                for (final org.wheatgenetics.coordinate.optionalField.OptionalField
                                optionalField : org.wheatgenetics.coordinate.
                                activities.Main.this.nonNullOptionalFields) // Danger: CheckedOptionalFields above but nonNullOptionalFields here.
                                {
                                    final android.widget.EditText editText = editTextArray[i];
                                    if (editText != null)
                                    {
                                        final java.lang.String value =
                                            editText.getText().toString().trim();
                                        if (i == 0 && value.length() == 0)
                                        {
                                            org.wheatgenetics.coordinate.utils.Utils.toast(
                                                org.wheatgenetics.coordinate.activities.Main.this,
                                                optionalField.getHint() + org.wheatgenetics.
                                                    coordinate.activities.Main.this.getString(
                                                        org.wheatgenetics.coordinate.
                                                            R.string.not_empty));
                                            org.wheatgenetics.coordinate.
                                                activities.Main.this.inputSeed();
                                            return;
                                        }

                                        optionalField.setValue(value);
                                        if (optionalField.nameEqualsIgnoreCase("Person")
                                        ||  optionalField.nameEqualsIgnoreCase("Name"  ))
                                        {
                                            final android.content.SharedPreferences.Editor ed =
                                                org.wheatgenetics.coordinate.
                                                    activities.Main.this.ep.edit();
                                            assert ed != null;
                                            ed.putString("Person", optionalField.getValue());
                                            ed.apply();
                                        }

                                        i++;
                                    }
                                }
                            }
                            assert dialog != null;
                            dialog.cancel();
                            org.wheatgenetics.coordinate.activities.Main.this.loadTemplate(
                                org.wheatgenetics.coordinate.model.TemplateType.SEED);
                        }
                    });
            }
            builder.setNegativeButton(org.wheatgenetics.coordinate.R.string.cancel,
                new android.content.DialogInterface.OnClickListener()
                {
                    @java.lang.Override
                    public void onClick(final android.content.DialogInterface dialog,
                    final int which)
                    {
                        assert dialog != null;
                        dialog.cancel();
                    }
                });
            alertDialog = builder.create();
        }
        assert alertDialog != null;
        alertDialog.getWindow().setSoftInputMode(
            android.view.WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    private void tempLoad(final int mode) throws org.json.JSONException
    {
        if (mode == org.wheatgenetics.coordinate.activities.Main.MODE_DNA)
            this.loadTemplate(org.wheatgenetics.coordinate.model.TemplateType.DNA);
        else
            if (mode == org.wheatgenetics.coordinate.activities.Main.MODE_SAVED)
                this.loadTemplate(org.wheatgenetics.coordinate.model.TemplateType.DEFAULT);
            else
                this.newTemplate(org.wheatgenetics.coordinate.model.TemplateType.DEFAULT);  // throws org.json.JSONException
    }
    // endregion

    // region Subaction Drawer Methods
    private void loadExistingOrNewTemplate()
    {
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle(this.getString(org.wheatgenetics.coordinate.R.string.template_options));
        builder.setItems(this.templateOptions, new android.content.DialogInterface.OnClickListener()
            {
                @java.lang.Override
                public void onClick(final android.content.DialogInterface dialog, final int which)
                {
                    if (which == 0)
                        org.wheatgenetics.coordinate.activities.Main.this.loadTemplate();
                    else
                        org.wheatgenetics.coordinate.activities.Main.this.newTemplate();
                }
            });
        builder.show();
    }

    private void newGridNow() throws org.json.JSONException
    {
        this.deleteGrid(this.gridId);

        assert this.templateModel != null;
        final org.wheatgenetics.coordinate.model.TemplateType templateType =
            this.templateModel.getType();
        if (templateType == org.wheatgenetics.coordinate.model.TemplateType.SEED)
        {
            {
                final org.wheatgenetics.coordinate.database.TemplatesTable templatesTable =
                    new org.wheatgenetics.coordinate.database.TemplatesTable(this);
                if (templatesTable.getByType(templateType))         // database, model
                    this.copyTemplate(templatesTable);              // throws org.json.JSONException
            }
            this.inputSeed();
        }
        else if (templateType == org.wheatgenetics.coordinate.model.TemplateType.DNA)
        {
            {
                final org.wheatgenetics.coordinate.database.TemplatesTable templatesTable =
                    new org.wheatgenetics.coordinate.database.TemplatesTable(this);
                if (templatesTable.getByType(templateType))         // database, model
                    this.copyTemplate(templatesTable);              // throws org.json.JSONException
            }
            this.templateModel.getExcludeCells().clear();
            this.templateModel.getExcludeCells().add(new android.graphics.Point(
                this.randomBox(this.templateModel.getCols()),
                this.randomBox(this.templateModel.getRows())));

            this.inputTemplateInput(                                // throws org.json.JSONException
                org.wheatgenetics.coordinate.activities.Main.MODE_DNA);
        }
        else
        {
            // reset options?
            this.inputTemplateInput(                                // throws org.json.JSONException
                org.wheatgenetics.coordinate.activities.Main.MODE_SAVED);
        }
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

            assert view != null;
            final android.widget.EditText fileNameEditText = (android.widget.EditText)
                view.findViewById(org.wheatgenetics.coordinate.R.id.nameEdit);

            assert this.nonNullOptionalFields != null;
            assert fileNameEditText           != null;
            fileNameEditText.setText(this.nonNullOptionalFields.get(0).getValue() +
                "_" + org.wheatgenetics.coordinate.utils.Utils.getCurrentDate().replace(".", "_"));

            builder.setTitle(this.getString(org.wheatgenetics.coordinate.R.string.filename_set));
            builder.setView(view);
            builder.setPositiveButton(this.getString(org.wheatgenetics.coordinate.R.string.ok),
                new android.content.DialogInterface.OnClickListener()
                {
                    @java.lang.Override
                    public void onClick(final android.content.DialogInterface dialog,
                    final int which)
                    {
                        assert dialog != null;
                        dialog.cancel();

                        final java.lang.String fileName =
                            fileNameEditText.getText().toString().trim();
                        if (fileName.length() == 0)
                            org.wheatgenetics.coordinate.utils.Utils.alert(
                                org.wheatgenetics.coordinate.activities.Main.this,
                                Coordinate.appName,
                                org.wheatgenetics.coordinate.activities.Main.this.getString(
                                    org.wheatgenetics.coordinate.R.string.filename_empty));
                        else
                        {
                            {
                                assert
                                    org.wheatgenetics.coordinate.activities.Main.this.templateModel
                                    != null;
                                final java.io.File path = new java.io.File(Constants.EXPORT_PATH,
                                    org.wheatgenetics.coordinate.activities.
                                        Main.this.templateModel.getTitle());
                                org.wheatgenetics.coordinate.activities.Main.this.createDir(path);

                                org.wheatgenetics.coordinate.activities.Main.this.exporter =
                                    new org.wheatgenetics.coordinate.activities.Main.Exporter(
                                        /* context => */
                                            org.wheatgenetics.coordinate.activities.Main.this,
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
            new android.content.DialogInterface.OnClickListener()
            {
                @java.lang.Override
                public void onClick(final android.content.DialogInterface dialog, final int which)
                {
                    assert dialog != null;
                    dialog.cancel();
                }
            });
        builder.show();
    }

    //TODO merge this method with the one above
    private void inputTemplateInput(final int mode) throws org.json.JSONException
    {
        assert this.nonNullOptionalFields != null;
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

        assert gridView != null;
        final android.widget.LinearLayout linearLayout = (android.widget.LinearLayout)
            gridView.findViewById(org.wheatgenetics.coordinate.R.id.optionalLayout);

        final org.wheatgenetics.coordinate.optionalField.CheckedOptionalFields
            checkedOptionalFields = this.makeCheckedOptionalFields();

        final java.util.ArrayList<android.widget.EditText> editTextArrayList =
            new java.util.ArrayList<android.widget.EditText>();

        // load options
        assert linearLayout != null;
        for (final org.wheatgenetics.coordinate.optionalField.OptionalField optionalField :
        checkedOptionalFields)
        {
            final android.view.View optionalFieldView = layoutInflater.inflate(
                org.wheatgenetics.coordinate.R.layout.optional_edit, linearLayout, false);

            assert optionalFieldView != null;
            {
                final android.widget.TextView optionalFieldTextView = (android.widget.TextView)
                    optionalFieldView.findViewById(org.wheatgenetics.coordinate.R.id.optionText);

                assert optionalFieldTextView != null;
                optionalFieldTextView.setText(optionalField.getName());
            }

            {
                final android.widget.EditText optionalFieldEditText = (android.widget.EditText)
                    optionalFieldView.findViewById(org.wheatgenetics.coordinate.R.id.optionEdit);

                assert optionalFieldEditText != null;
                optionalFieldEditText.setText(optionalField.getValue());
                optionalFieldEditText.setHint(optionalField.getHint ());

                editTextArrayList.add(optionalFieldEditText);
            }

            linearLayout.addView(optionalFieldView);
        }

        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        assert this.templateModel != null;
        builder.setTitle(this.templateModel.getTitle());
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

                        assert
                            org.wheatgenetics.coordinate.activities.Main.this.nonNullOptionalFields
                            != null;
                        for (final org.wheatgenetics.coordinate.optionalField.OptionalField
                        optionalField :
                        org.wheatgenetics.coordinate.activities.Main.this.nonNullOptionalFields)
                        {
                            final android.widget.EditText editText = editTextArray[i];
                            if (editText != null)
                            {
                                if (mode == org.wheatgenetics.coordinate.activities.Main.MODE_DNA)
                                {
                                    final java.lang.String value =
                                        editText.getText().toString().trim();
                                    if (i == 0 && value.length() == 0)
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
                                                    activities.Main.MODE_DNA);
                                        }
                                        catch (final java.lang.Exception e) {}
                                        return;
                                    }
                                }

                                optionalField.setValue(editText.getText().toString().trim());

                                if (optionalField.nameEqualsIgnoreCase("Person")
                                ||  optionalField.nameEqualsIgnoreCase("Name"  ))
                                {
                                    final android.content.SharedPreferences.Editor ed = ep.edit();
                                    assert ed != null;
                                    ed.putString("Person", optionalField.getValue());
                                    ed.apply();
                                }
                            }
                            i++;
                        }
                    }
                    assert dialog != null;
                    dialog.cancel();
                    try { org.wheatgenetics.coordinate.activities.Main.this.tempLoad(mode); }
                    catch (final java.lang.Exception e) {}
                }
            });

        builder.setNegativeButton(org.wheatgenetics.coordinate.R.string.cancel,
            new android.content.DialogInterface.OnClickListener()
            {
                @java.lang.Override
                public void onClick(final android.content.DialogInterface dialog, final int which)
                {
                    assert dialog != null;
                    dialog.cancel();
                }
            });

        final android.app.AlertDialog alertDialog = builder.create();
        assert alertDialog != null;
        alertDialog.getWindow().setSoftInputMode(
            android.view.WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        alertDialog.setCancelable(false);
        alertDialog.show();
    }
    // endregion

    // region Action Drawer Methods
    private void newGrid() throws org.json.JSONException
    {
        if (this.gridId == 0)
            this.loadExistingOrNewTemplate();
        else
            if (this.gridId >= 0 && mLastExportGridId == this.gridId)
                this.newGridNow();                                  // throws org.json.JSONException
            else
                org.wheatgenetics.coordinate.utils.Utils.confirm(this, Coordinate.appName,
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
        if (this.gridId != 0) org.wheatgenetics.coordinate.utils.Utils.confirm(this,
            Coordinate.appName,
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
                            loadExistingOrNewTemplate();
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

    private void newTemplate()
    {
        assert this.templateModel != null;
        this.templateModel.getExcludeCells().clear();
        this.excludeRows  = new java.util.ArrayList<>();
        this.excludeCols  = new java.util.ArrayList<>();

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

        assert view != null;
        final android.widget.EditText nameTextEdit = (android.widget.EditText)
            view.findViewById(org.wheatgenetics.coordinate.R.id.nameEdit);
        final android.widget.EditText rowsTextEdit = (android.widget.EditText)
            view.findViewById(org.wheatgenetics.coordinate.R.id.rowsEdit);
        final android.widget.EditText colsTextEdit = (android.widget.EditText)
            view.findViewById(org.wheatgenetics.coordinate.R.id.colsEdit);

        assert nameTextEdit != null;
        nameTextEdit.setText("");
        assert rowsTextEdit != null;
        rowsTextEdit.setText(this.templateModel.getRowsAsString());
        assert colsTextEdit != null;
        colsTextEdit.setText(this.templateModel.getColsAsString());

        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle(this.templateOptions[1]);
        builder.setView(view);
        builder.setPositiveButton(org.wheatgenetics.coordinate.R.string.next,
            new android.content.DialogInterface.OnClickListener()
            {
                @java.lang.Override
                public void onClick(final android.content.DialogInterface dialog, final int which)
                {
                    assert dialog != null;
                    dialog.cancel();

                    final java.lang.String name = nameTextEdit.getText().toString().trim();
                    final java.lang.String cols = colsTextEdit.getText().toString().trim();
                    final java.lang.String rows = rowsTextEdit.getText().toString().trim();

                    assert org.wheatgenetics.coordinate.activities.Main.this.templateModel != null;
                    org.wheatgenetics.coordinate.activities.Main.this.templateModel.setTitle(name);  // TODO: Combine these
                    org.wheatgenetics.coordinate.activities.Main.this.templateModel.setRows (rows);  // TODO:  3 methods
                    org.wheatgenetics.coordinate.activities.Main.this.templateModel.setCols (cols);  // TODO:  into 1?

                    if (name.length() == 0)
                    {
                        android.widget.Toast.makeText(
                            org.wheatgenetics.coordinate.activities.Main.this,
                                org.wheatgenetics.coordinate.activities.Main.this.getString(
                                    org.wheatgenetics.coordinate.R.string.template_no_name),
                            android.widget.Toast.LENGTH_LONG).show();
                        org.wheatgenetics.coordinate.activities.Main.this.newTemplate();
                        return;
                    }

                    if (rows.length() == 0 || org.wheatgenetics.coordinate.activities.Main.this.templateModel.getRows() == -1)
                    {
                        android.widget.Toast.makeText(
                            org.wheatgenetics.coordinate.activities.Main.this,
                            org.wheatgenetics.coordinate.activities.Main.this.getString(
                                org.wheatgenetics.coordinate.R.string.no_rows),
                            android.widget.Toast.LENGTH_LONG).show();
                        org.wheatgenetics.coordinate.activities.Main.this.newTemplate();
                        return;
                    }

                    if (cols.length() == 0 || org.wheatgenetics.coordinate.activities.Main.this.templateModel.getCols() == -1)
                    {
                        android.widget.Toast.makeText(
                            org.wheatgenetics.coordinate.activities.Main.this,
                            org.wheatgenetics.coordinate.activities.Main.this.getString(
                                org.wheatgenetics.coordinate.R.string.no_cols),
                            android.widget.Toast.LENGTH_LONG).show();
                        org.wheatgenetics.coordinate.activities.Main.this.newTemplate();
                        return;
                    }

                    org.wheatgenetics.coordinate.activities.Main.this.inputTemplateNewExtra();
                }
            });

        builder.setNegativeButton(org.wheatgenetics.coordinate.R.string.cancel,
            new android.content.DialogInterface.OnClickListener()
            {
                @java.lang.Override
                public void onClick(final android.content.DialogInterface dialog, final int which)
                {
                    assert dialog != null;
                    dialog.cancel();
                }
            });

        builder.show();
    }

    private void loadTemplate()
    {
        final java.util.List<org.wheatgenetics.coordinate.database.TemplatesTable> templates = new java.util.ArrayList<>();

        final org.wheatgenetics.coordinate.database.TemplatesTable tmp =
            new org.wheatgenetics.coordinate.database.TemplatesTable(this);
        final android.database.Cursor cursor = tmp.load();  // database
        if (cursor != null)
        {
            while (cursor.moveToNext())
            {
                final org.wheatgenetics.coordinate.database.TemplatesTable templatesTable =
                    new org.wheatgenetics.coordinate.database.TemplatesTable(this);
                if (templatesTable.copy(cursor)) templates.add(templatesTable);  // database, model
            }
            cursor.close();
        }

        final int    size    = templates.size();
        final java.lang.String items[] = new java.lang.String[size];
        for (int i = 0; i < size; i++)
        {
            final org.wheatgenetics.coordinate.database.TemplatesTable item = templates.get(i);
            items[i] = item.title;  // model
        }

        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle(this.templateOptions[0]);
        builder.setItems(items, new android.content.DialogInterface.OnClickListener()
            {
                @java.lang.Override
                public void onClick(final android.content.DialogInterface dialog, final int which)
                {
                    if (which < 0 || which > templates.size()) return;

                    final org.wheatgenetics.coordinate.database.TemplatesTable tmp =
                        templates.get(which);
                    try { copyTemplate(tmp); } catch (final org.json.JSONException e) {}

                    if (which == 0)
                        Main.this.inputSeed();
                    else if (which == 1)
                    {
                        org.wheatgenetics.coordinate.activities.Main.this.templateModel.getExcludeCells().clear();
                        org.wheatgenetics.coordinate.activities.Main.this.templateModel.getExcludeCells().add(
                            new android.graphics.Point(
                                randomBox(Main.this.templateModel.getCols()),
                                randomBox(Main.this.templateModel.getRows())));

                        try { inputTemplateInput(org.wheatgenetics.coordinate.activities.Main.MODE_DNA); } catch (final java.lang.Exception e) {}
                    }
                    else
                        try { inputTemplateInput(org.wheatgenetics.coordinate.activities.Main.MODE_SAVED); }
                        catch (final java.lang.Exception e) {}
                }
            });
        builder.show();
    }

    private void deleteTemplate()
    {
        final java.util.List<org.wheatgenetics.coordinate.database.TemplatesTable> templates =
            new java.util.ArrayList<>();

        final org.wheatgenetics.coordinate.database.TemplatesTable tmp =
            new org.wheatgenetics.coordinate.database.TemplatesTable(this);
        final android.database.Cursor cursor = tmp.load();  // database
        if (cursor != null)
        {
            while (cursor.moveToNext())
            {
                final org.wheatgenetics.coordinate.database.TemplatesTable templatesTable =
                    new org.wheatgenetics.coordinate.database.TemplatesTable(this);
                if (templatesTable.copy(cursor)) templates.add(templatesTable);  // database, model
            }
            cursor.close();
        }

        final int    size    = templates.size();
        final java.lang.String items[] = new java.lang.String[size];

        for (int i = 0; i < size; i++)
        {
            final org.wheatgenetics.coordinate.database.TemplatesTable item = templates.get(i);
            assert item != null;
            items[i] = item.title;  // model
        }

        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle(getString(org.wheatgenetics.coordinate.R.string.delete_template));
        builder.setItems(items, new android.content.DialogInterface.OnClickListener()
            {
                @java.lang.Override
                public void onClick(final android.content.DialogInterface dialog, final int which)
                {
                    if (which < 0 || which > templates.size()) return;

                    final org.wheatgenetics.coordinate.database.TemplatesTable tmp =
                        templates.get(which);

                    org.wheatgenetics.coordinate.utils.Utils.confirm(Main.this,
                        getString(org.wheatgenetics.coordinate.R.string.delete_template        ),
                        getString(org.wheatgenetics.coordinate.R.string.delete_template_warning),
                        new java.lang.Runnable()
                        {
                            @java.lang.Override
                            public void run()
                            {
                                if (deleteTemplate(
                                new org.wheatgenetics.coordinate.model.TemplateModel(tmp.id)))
                                {
                                    android.widget.Toast.makeText(Main.this,
                                        getString(org.wheatgenetics.coordinate.R.string.template_deleted),
                                        android.widget.Toast.LENGTH_LONG).show();
                                    Main.this.loadExistingOrNewTemplate();
                                }
                                else
                                    android.widget.Toast.makeText(Main.this, getString(org.wheatgenetics.
                                            coordinate.R.string.template_not_deleted),
                                        android.widget.Toast.LENGTH_LONG).show();
                            }
                        }, null);
                }
            });
        builder.show();
    }

    private void importData()
    {
        java.lang.String names  [] = new java.lang.String[1];
        long   indexes[] = new long  [1];

        int pos = 0;

        final GridsTable              gridsTable = new GridsTable(this);
        final android.database.Cursor gridCursor = gridsTable.getAllGrids();
        if (gridCursor != null)
        {
            final int size = gridCursor.getCount();

            names   = new java.lang.String[size];
            indexes = new long  [size];

            while (gridCursor.moveToNext())
            {
                final GridsTable tmpG = new GridsTable(this);
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

        if (pos == 0)
        {
            org.wheatgenetics.coordinate.utils.Utils.alert(this, Coordinate.appName,
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
                    if (which < gridIds.length)
                    {
                        final long id = gridIds[which];

                        final android.content.SharedPreferences.Editor ed = ep.edit();
                        ed.putLong("CurrentGrid", id);
                        ed.apply();

                        final GridsTable grd = new GridsTable(Main.this);
                        if (grd.get(id))
                        {
                            org.wheatgenetics.coordinate.activities.Main.this.templateModel.setTitle(
                                grd.templateTitle);
                            org.wheatgenetics.coordinate.activities.Main.this.gridId = grd.id;
                            mGridTitle = grd.title;
                            org.wheatgenetics.coordinate.activities.Main.this.templateModel.setType(
                                grd.templateType);
                            Main.this.templateModel.setRows(grd.templateRows);
                            Main.this.templateModel.setCols(grd.templateCols);

                            final org.wheatgenetics.coordinate.database.TemplatesTable templatesTable =
                                new org.wheatgenetics.coordinate.database.TemplatesTable(Main.this);

                            if (templatesTable.get(grd.templateId))  // database
                            {
                                try
                                {
                                    nonNullOptionalFields =
                                        new NonNullOptionalFields(templatesTable.options);  // throws org.json.JSONException  // model
                                }
                                catch (final org.json.JSONException e) {}

                                org.wheatgenetics.coordinate.activities.Main.this.templateModel.setRowNumbering(templatesTable.rowNumbering == 1);  // model
                                org.wheatgenetics.coordinate.activities.Main.this.templateModel.setColNumbering(templatesTable.colNumbering == 1);  // model
                            }

                            org.wheatgenetics.coordinate.activities.Main.this.templateModel.getExcludeCells().clear();  // model
                            org.wheatgenetics.coordinate.activities.Main.this.excludeRows.clear();   // model
                            org.wheatgenetics.coordinate.activities.Main.this.excludeCols.clear();   // model

                            populateTemplate();
                            showTemplateUI();
                        }
                        else
                            org.wheatgenetics.coordinate.utils.Utils.alert(Main.this,
                                Coordinate.appName,
                                getString
                                    (org.wheatgenetics.coordinate.R.string.import_grid_failed));

                        assert dialog != null;
                        dialog.cancel();
                    }
                }
            });
        builder.show();
    }

    private void about()
    {
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        {
            android.view.View personView;
            {
                final android.view.LayoutInflater layoutInflater = this.getLayoutInflater();
                assert layoutInflater != null;
                personView = layoutInflater.inflate(org.wheatgenetics.coordinate.R.layout.about,
                    new android.widget.LinearLayout(this), false);
            }

            assert personView != null;
            {
                final android.widget.TextView versionTextView = (android.widget.TextView)
                    personView.findViewById(org.wheatgenetics.coordinate.R.id.tvVersion);
                {
                    android.content.pm.PackageInfo packageInfo;
                    {
                        final android.content.pm.PackageManager packageManager =
                            this.getPackageManager();
                        assert packageManager != null;
                        try
                        {
                            packageInfo = packageManager.getPackageInfo(  // throws android.content.
                                this.getPackageName(), 0);                //  pm.PackageManager.-
                        }                                                 //  NameNotFoundException
                        catch (final android.content.pm.PackageManager.NameNotFoundException e)
                        {
                            org.wheatgenetics.coordinate.activities.Main.sendErrorLogMsg(e);
                            return;
                        }
                    }
                    assert packageInfo != null;
                    assert versionTextView != null;
                    versionTextView.setText(this.getResources().getString(
                            org.wheatgenetics.coordinate.R.string.versiontitle) +
                        " " + packageInfo.versionName);
                }
                versionTextView.setOnClickListener(new android.view.View.OnClickListener()
                    {
                        @java.lang.Override
                        public void onClick(final android.view.View v)
                        { org.wheatgenetics.coordinate.activities.Main.this.changelog(); }
                    });
            }

            {
                final android.widget.TextView otherAppsTextView = (android.widget.TextView)
                    personView.findViewById(org.wheatgenetics.coordinate.R.id.tvOtherApps);
                assert otherAppsTextView != null;
                otherAppsTextView.setOnClickListener(new android.view.View.OnClickListener()
                    {
                        @java.lang.Override
                        public void onClick(final android.view.View v)
                        { org.wheatgenetics.coordinate.activities.Main.this.showOtherAppsDialog(); }
                    });
            }
            builder.setCancelable(true);
            builder.setTitle(this.getResources().getString(
                org.wheatgenetics.coordinate.R.string.about));
            builder.setView(personView);
        }
        builder.setNegativeButton(
            this.getResources().getString(org.wheatgenetics.coordinate.R.string.ok),
            new android.content.DialogInterface.OnClickListener()
            {
                @java.lang.Override
                public void onClick(final android.content.DialogInterface dialog, final int which)
                {
                    assert dialog != null;
                    dialog.dismiss();
                }
            });
        builder.show();
    }
    // endregion

    // region Selector Drawer Method
    private boolean selectNavigationItem(final android.view.MenuItem menuItem)
    {
        assert menuItem != null;
        switch (menuItem.getItemId())
        {
            case org.wheatgenetics.coordinate.R.id.menu_new_grid:
                try { this.newGrid(); } catch (final org.json.JSONException e) { return false; }
                break;
            case org.wheatgenetics.coordinate.R.id.menu_delete_grid: this.deleteGrid(); break;

            case org.wheatgenetics.coordinate.R.id.menu_new_template : this.newTemplate (); break;
            case org.wheatgenetics.coordinate.R.id.menu_load_template: this.loadTemplate(); break;
            case org.wheatgenetics.coordinate.R.id.menu_delete_template:
                this.deleteTemplate();
                break;

            case org.wheatgenetics.coordinate.R.id.menu_import: this.importData(); break;
            case org.wheatgenetics.coordinate.R.id.menu_export:
                assert this.templateModel != null;
                if (this.templateModel.getTitle().equals(""))
                    this.makeToast(this.getString(org.wheatgenetics.coordinate.R.string.grid_empty));
                else
                    this.export();
                break;
            case org.wheatgenetics.coordinate.R.id.about:
                this.about();
                break;

            // Keeping this for debugging purposes:
            // case org.wheatgenetics.coordinate.R.id.reset_database: this.resetDatabase(); break;
            // case org.wheatgenetics.coordinate.R.id.copy_database : this.copydb       (); break;
        }

        assert this.drawerLayout != null;
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
                if (i != -1)
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

    // Adds default templates to database
    private void initDb() throws org.json.JSONException  // model
    {
        assert this.templateModel != null;
        this.templateModel.setTitle("Seed Tray");
        this.templateModel.setType(org.wheatgenetics.coordinate.model.TemplateType.SEED);
        this.templateModel.setRows( 6);
        this.templateModel.setCols(20);
        this.templateModel.setRowNumbering(true);
        this.templateModel.setColNumbering(true);

        this.nonNullOptionalFields =
            new org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields();
        this.nonNullOptionalFields.add    ("Tray"  , /* hint => */ "Tray ID"    );
        this.nonNullOptionalFields.add    ("Person", /* hint => */ "Person name");
        this.nonNullOptionalFields.addDate("Date"                               );

        this.excludeRows = new java.util.ArrayList<>();
        this.excludeRows.add(2);
        this.excludeRows.add(5);

        this.excludeCols  = new java.util.ArrayList<>();
        this.templateModel.getExcludeCells().clear();

        this.createDb(this.templateModel.getType());                // throws org.json.JSONException

        this.templateModel.setTitle("DNA Plate");
        this.templateModel.setRows( 8);
        this.templateModel.setCols(12);
        this.templateModel.setType(org.wheatgenetics.coordinate.model.TemplateType.DNA);
        this.templateModel.setRowNumbering(true );
        this.templateModel.setColNumbering(false);

        this.nonNullOptionalFields =
            new org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields();
        this.nonNullOptionalFields.add("Plate", /* hint => */ "Plate ID"                     ); // TODO dna
        this.nonNullOptionalFields.add("Plate Name"                                          );
        this.nonNullOptionalFields.add("Notes"                                               );
        this.nonNullOptionalFields.add("tissue_type", /* value => */ "Leaf", /* hint => */ "");
        this.nonNullOptionalFields.add("extraction" , /* value => */ "CTAB", /* hint => */ "");
        this.nonNullOptionalFields.add("person"                                              );
        this.nonNullOptionalFields.add("date"                                                );

        this.excludeRows = new java.util.ArrayList<>();
        this.excludeCols = new java.util.ArrayList<>();

        this.templateModel.getExcludeCells().clear();

        this.createDb(this.templateModel.getType());                // throws org.json.JSONException

        this.templateModel.setTitle("");
    }

    private boolean createDb(final org.wheatgenetics.coordinate.model.TemplateType templateType)
    throws org.json.JSONException  // database, model
    {
        final org.wheatgenetics.coordinate.database.TemplatesTable templatesTable =
            new org.wheatgenetics.coordinate.database.TemplatesTable(this);

        assert this.templateModel != null;
        templatesTable.title = this.templateModel.getTitle()         ;
        templatesTable.type  = this.templateModel.getType().getCode();
        templatesTable.rows  = this.templateModel.getRows()          ;
        templatesTable.cols  = this.templateModel.getCols()          ;

        templatesTable.excludeCells = org.wheatgenetics.coordinate.utils.Utils.pointListToJson(this.templateModel.getExcludeCells()); // throws org.json.JSONException
        templatesTable.excludeCols  = org.wheatgenetics.coordinate.utils.Utils.integerListToJson(this.excludeCols);
        templatesTable.excludeRows  = org.wheatgenetics.coordinate.utils.Utils.integerListToJson(this.excludeRows);

        templatesTable.options = this.nonNullOptionalFields.toJson();     // throws org.json.JSONException

        templatesTable.colNumbering = this.templateModel.getColNumbering() ? 1 : 0;
        templatesTable.rowNumbering = this.templateModel.getRowNumbering() ? 1 : 0;

        templatesTable.stamp = java.lang.System.currentTimeMillis();

        if (templatesTable.getByType(templateType))
            return templatesTable.update();
        else
            return templatesTable.insert() > 0;
    }

    private void copyTemplate(
    final org.wheatgenetics.coordinate.database.TemplatesTable templatesTable)  // model
    throws org.json.JSONException
    {
        assert this.templateModel != null;
        this.templateModel.setId(templatesTable.id);
        this.templateModel.setTitle(templatesTable.title);
        this.templateModel.setType(templatesTable.type);
        this.templateModel.setRows(templatesTable.rows);
        this.templateModel.setCols(templatesTable.cols);

        this.templateModel.setExcludeCells(org.wheatgenetics.coordinate.utils.Utils.jsonToPointList(templatesTable.excludeCells));  // throws org.json.JSONException

        this.excludeCols = org.wheatgenetics.coordinate.utils.Utils.jsonToIntegerList(templatesTable.excludeCols);  // throws org.json.JSONException
        this.excludeRows = org.wheatgenetics.coordinate.utils.Utils.jsonToIntegerList(templatesTable.excludeRows);  // throws org.json.JSONException

        this.nonNullOptionalFields = new NonNullOptionalFields(templatesTable.options); // throws org.json.JSONException

        this.templateModel.setRowNumbering(templatesTable.rowNumbering == 1);
        this.templateModel.setColNumbering(templatesTable.colNumbering == 1);
    }

    private void inputTemplateNewExtra()
    {
        final android.view.LayoutInflater inflater = Main.this.getLayoutInflater();
        final android.view.View view =
            inflater.inflate(org.wheatgenetics.coordinate.R.layout.template_new_extra, new android.widget.LinearLayout(this), false);

        final android.widget.Button optionalButton = (android.widget.Button) view.findViewById(org.wheatgenetics.coordinate.R.id.optionalButton);
        final android.widget.Button excludeButton  = (android.widget.Button) view.findViewById(org.wheatgenetics.coordinate.R.id.excludeButton );
        final android.widget.Button namingButton   = (android.widget.Button) view.findViewById(org.wheatgenetics.coordinate.R.id.namingButton  );

        optionalButton.setOnClickListener(new android.view.View.OnClickListener()
            {
                @java.lang.Override
                public void onClick(final android.view.View v) { inputOptional(); }
            });

        excludeButton.setOnClickListener(new android.view.View.OnClickListener()
            {
                @java.lang.Override
                public void onClick(final android.view.View v) { inputExclude(); }
            });

        namingButton.setOnClickListener(new android.view.View.OnClickListener()
            {
                @java.lang.Override
                public void onClick(final android.view.View v) { inputNaming(); }
            });

        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle(org.wheatgenetics.coordinate.R.string.template_new);
        builder.setView(view);
        builder.setPositiveButton(org.wheatgenetics.coordinate.R.string.next, new android.content.DialogInterface.OnClickListener()
            {
                @java.lang.Override
                public void onClick(final android.content.DialogInterface dialog, final int which)
                {
                    dialog.cancel();
                    try { inputTemplateInput(MODE_DEFAULT); } catch (final java.lang.Exception e) {}
                }
            });

        builder.setNegativeButton(org.wheatgenetics.coordinate.R.string.cancel, new android.content.DialogInterface.OnClickListener()
            {
                @java.lang.Override
                public void onClick(final android.content.DialogInterface dialog, final int which)
                { dialog.cancel(); }
            });

        builder.show();
    }

    private void inputOptional()
    {
        final java.util.ArrayList<java.lang.String > itemArrayList      = new java.util.ArrayList<java.lang.String >();
        final java.util.ArrayList<java.lang.Boolean> selectionArrayList = new java.util.ArrayList<java.lang.Boolean>();

        assert this.nonNullOptionalFields != null;
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
        builder.setMultiChoiceItems(itemArray, selectionArray, new android.content.DialogInterface.OnMultiChoiceClickListener()
            {
                @java.lang.Override
                public void onClick(final android.content.DialogInterface dialog, final int which,
                final boolean isChecked)
                {
                    assert nonNullOptionalFields != null;
                    nonNullOptionalFields.get(which).setChecked(isChecked);
                }
            });

        builder.setNeutralButton(getString(org.wheatgenetics.coordinate.R.string.add_new), new android.content.DialogInterface.OnClickListener()
            {
                @java.lang.Override
                public void onClick(final android.content.DialogInterface dialog, final int which)
                {
                    dialog.cancel();
                    inputOptionalNew("", "");
                }
            });
        builder.setNegativeButton(org.wheatgenetics.coordinate.R.string.cancel, new android.content.DialogInterface.OnClickListener()
            {
                @java.lang.Override
                public void onClick(final android.content.DialogInterface dialog, final int which)
                { dialog.cancel(); }
            });
        builder.setPositiveButton(org.wheatgenetics.coordinate.R.string.ok, new android.content.DialogInterface.OnClickListener()
            {
                @java.lang.Override
                public void onClick(final android.content.DialogInterface dialog, final int which)
                { dialog.cancel(); }
            });

        builder.show();
    }

    private void inputOptionalNew(final java.lang.String field, final java.lang.String value)
    {
        final android.view.LayoutInflater layoutInflater = Main.this.getLayoutInflater();

        assert layoutInflater != null;
        final android.view.View view = layoutInflater.inflate(org.wheatgenetics.coordinate.R.layout.optional_new, null);

        final android.widget.EditText fieldEdit = (android.widget.EditText) view.findViewById(org.wheatgenetics.coordinate.R.id.fieldEdit);
        final android.widget.EditText valueEdit = (android.widget.EditText) view.findViewById(org.wheatgenetics.coordinate.R.id.valueEdit);

        fieldEdit.setText(field);
        valueEdit.setText(value);

        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle(getString(org.wheatgenetics.coordinate.R.string.new_optional_field));
        builder.setView(view);
        builder.setCancelable(false);
        builder.setPositiveButton(getString(org.wheatgenetics.coordinate.R.string.ok), new android.content.DialogInterface.OnClickListener()
            {
                @java.lang.Override
                public void onClick(final android.content.DialogInterface dialog, final int which)
                {
                    final java.lang.String sfield = fieldEdit.getText().toString().trim();
                    final java.lang.String svalue = valueEdit.getText().toString().trim();

                    if (sfield.length() == 0)
                    {
                        android.widget.Toast.makeText(Main.this, getString(org.wheatgenetics.coordinate.R.string.new_optional_field_no_name),
                            android.widget.Toast.LENGTH_LONG).show();
                        inputOptionalNew(field, value);
                        return;
                    }
                    dialog.cancel();

                    assert nonNullOptionalFields != null;
                    nonNullOptionalFields.add(sfield, /* value => */ svalue, /* hint => */ "");

                    inputOptional();
                }
            });

        builder.setNegativeButton(org.wheatgenetics.coordinate.R.string.cancel, new android.content.DialogInterface.OnClickListener()
            {
                @java.lang.Override
                public void onClick(final android.content.DialogInterface dialog, final int which)
                { dialog.cancel(); }
            });

        final android.app.AlertDialog alertDialog = builder.create();
        assert alertDialog != null;
        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    private void inputExclude()
    {
        final java.lang.String[] items = {getString(org.wheatgenetics.coordinate.R.string.rows), getString(org.wheatgenetics.coordinate.R.string.cols), getString(org.wheatgenetics.coordinate.R.string.random)};

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
        builder.setNegativeButton(org.wheatgenetics.coordinate.R.string.cancel, new android.content.DialogInterface.OnClickListener()
            {
                @java.lang.Override
                public void onClick(final android.content.DialogInterface dialog, final int which)
                { dialog.cancel(); }
            });

        builder.show();
    }

    private void inputExcludeBoxes(final int type)
    {
        assert this.templateModel != null;
        final int     total        = type == 0 ? this.templateModel.getRows() : this.templateModel.getCols();
        final boolean selections[] = new boolean[total];
        final java.lang.String  items[]      = new java.lang.String[total];

        for (int i = 0; i < total; i++)
        {
            items[i] = java.lang.String.format("%s %d", (type == 0 ? getString(org.wheatgenetics.coordinate.R.string.row) : getString(org.wheatgenetics.coordinate.R.string.col)), i + 1);
            selections[i] = (type == 0 ? this.excludeRows.contains(java.lang.Integer.valueOf(i + 1)) : this.excludeCols.contains(java.lang.Integer.valueOf(i + 1)));
        }

        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle(getString(org.wheatgenetics.coordinate.R.string.exclude_title) + " - " + (type == 0 ? getString(org.wheatgenetics.coordinate.R.string.rows) : getString(org.wheatgenetics.coordinate.R.string.cols)));
        builder.setMultiChoiceItems(items, selections, new android.content.DialogInterface.OnMultiChoiceClickListener()
            {
                @java.lang.Override
                public void onClick(final android.content.DialogInterface dialog, final int which,
                final boolean isChecked) { selections[which] = isChecked; }
            });

        builder.setPositiveButton(org.wheatgenetics.coordinate.R.string.ok, new android.content.DialogInterface.OnClickListener()
            {
                @java.lang.Override
                public void onClick(final android.content.DialogInterface dialog, final int which)
                {
                    // get choices
                    for (int i = 0; i < total; i++) if (selections[i])
                        if (type == 0)
                            org.wheatgenetics.coordinate.activities.Main.this.excludeRows.add(i + 1);
                        else
                            org.wheatgenetics.coordinate.activities.Main.this.excludeCols.add(i + 1);
                }
            });

        builder.setNegativeButton(org.wheatgenetics.coordinate.R.string.cancel, new android.content.DialogInterface.OnClickListener()
            {
                @java.lang.Override
                public void onClick(final android.content.DialogInterface dialog, final int which)
                { dialog.cancel(); }
            });

        builder.show();
    }

    private void inputExcludeInput()
    {
        final android.view.LayoutInflater layoutInflater = Main.this.getLayoutInflater();
        final android.view.View view = layoutInflater.inflate(org.wheatgenetics.coordinate.R.layout.random, null);

        final android.widget.EditText cellsEdit = (android.widget.EditText) view.findViewById(org.wheatgenetics.coordinate.R.id.cellsEdit);

        cellsEdit.setText("1");

        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle(getString(org.wheatgenetics.coordinate.R.string.random));
        builder.setView(view);
        builder.setCancelable(false);
        builder.setNegativeButton(org.wheatgenetics.coordinate.R.string.cancel, new android.content.DialogInterface.OnClickListener()
            {
                @java.lang.Override
                public void onClick(final android.content.DialogInterface dialog, final int which)
                { dialog.cancel(); }
            });

        builder.setPositiveButton(org.wheatgenetics.coordinate.R.string.ok, new android.content.DialogInterface.OnClickListener()
            {
                @java.lang.Override
                public void onClick(final android.content.DialogInterface dialog, final int which)
                {
                    final java.lang.String str = cellsEdit.getText().toString();

                    org.wheatgenetics.coordinate.activities.Main.this.templateModel.getExcludeCells().clear();

                    final int cells = org.wheatgenetics.coordinate.utils.Utils.parseInt(str);
                    if (cells > 0)
                        for (int i = 0; i < cells; i++)
                        {
                            final android.graphics.Point point = new android.graphics.Point(
                                randomBox(Main.this.templateModel.getCols()),
                                randomBox(Main.this.templateModel.getRows()));
                            org.wheatgenetics.coordinate.activities.Main.this.templateModel.getExcludeCells().add(point); // FIXME check exclusivity
                        }
                    else inputExcludeInput();

                    dialog.cancel();
                }
            });

        final android.app.AlertDialog alertDialog = builder.create();
        assert alertDialog != null;
        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    private void inputNaming()
    {
        final android.view.LayoutInflater layoutInflater = Main.this.getLayoutInflater();
        final android.view.View           view           = layoutInflater.inflate(org.wheatgenetics.coordinate.R.layout.naming, null);

        final android.widget.Spinner rowSpinner = (android.widget.Spinner) view.findViewById(org.wheatgenetics.coordinate.R.id.rowSpinner);
        final android.widget.Spinner colSpinner = (android.widget.Spinner) view.findViewById(org.wheatgenetics.coordinate.R.id.colSpinner);

        assert this.templateModel != null;
        rowSpinner.setSelection(this.templateModel.getRowNumbering() ? 0 : 1);
        colSpinner.setSelection(this.templateModel.getColNumbering() ? 0 : 1);

        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle(getString(org.wheatgenetics.coordinate.R.string.naming));
        builder.setView(view);
        builder.setCancelable(false);
        builder.setNegativeButton(org.wheatgenetics.coordinate.R.string.cancel, new android.content.DialogInterface.OnClickListener()
            {
                @java.lang.Override
                public void onClick(final android.content.DialogInterface dialog, final int which)
                { dialog.cancel(); }
            });

        builder.setPositiveButton(org.wheatgenetics.coordinate.R.string.ok, new android.content.DialogInterface.OnClickListener()
            {
                @java.lang.Override
                public void onClick(final android.content.DialogInterface dialog, final int which)
                {
                    org.wheatgenetics.coordinate.activities.Main.this.templateModel.setRowNumbering((rowSpinner.getSelectedItemPosition() == 0));
                    org.wheatgenetics.coordinate.activities.Main.this.templateModel.setColNumbering((colSpinner.getSelectedItemPosition() == 0));

                    dialog.cancel();
                }
            });

        final android.app.AlertDialog alertDialog = builder.create();
        assert alertDialog != null;
        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    private void loadGrid(final long id) throws org.json.JSONException
    {
        final GridsTable grd = new GridsTable(this);
        if (grd.get(id))
        {
            assert this.templateModel != null;
            this.templateModel.setTitle(grd.templateTitle);
            this.gridId = grd.id   ;
            mGridTitle  = grd.title;
            this.templateModel.setType(grd.templateType);
            this.templateModel.setRows(grd.templateRows);
            this.templateModel.setCols(grd.templateCols);

            this.templateModel.getExcludeCells().clear();  // model
            this.excludeRows.clear();   // model
            this.excludeCols.clear();   // model

            org.wheatgenetics.coordinate.database.TemplatesTable templatesTable =
                new org.wheatgenetics.coordinate.database.TemplatesTable(this);

            if (templatesTable.get(grd.templateId))  // database
            {
                this.nonNullOptionalFields = new NonNullOptionalFields(templatesTable.options); // throws org.json.JSONException, model

                this.templateModel.setRowNumbering(templatesTable.rowNumbering == 1);
                this.templateModel.setColNumbering(templatesTable.colNumbering == 1);
            }

            populateTemplate();
            showTemplateUI();
        }
        else org.wheatgenetics.coordinate.utils.Utils.alert(Main.this, Coordinate.appName, getString(org.wheatgenetics.coordinate.R.string.import_grid_failed));
    }

    private boolean isExcludedCell(final int r, final int c)
    {
        for (int i = 0; i < this.templateModel.getExcludeCells().size(); i++)
        {
            final android.graphics.Point point = this.templateModel.getExcludeCells().get(i);
            if (point.equals(c, r)) return true;
        }
        return false;
    }

    private boolean isExcludedRow(final int r)
    { return this.excludeRows.contains(java.lang.Integer.valueOf(r)); }

    private boolean isExcludedCol(final int c)
    { return this.excludeCols.contains(java.lang.Integer.valueOf(c)); }

    private int randomBox(final int size)
    {
        final java.util.Random rand = new java.util.Random();
        rand.setSeed(java.lang.System.currentTimeMillis());
        return rand.nextInt(size - 1) + 1;
    }

    private void saveData()
    {
        assert this.cellIDEditText != null;
        java.lang.String data = this.cellIDEditText.getText().toString().trim();
        {
            boolean success;
            {
                final org.wheatgenetics.coordinate.database.EntriesTable entriesTable = new org.wheatgenetics.coordinate.database.EntriesTable(this);
                entriesTable.value = data;
                if (entriesTable.getByGrid(this.gridId, mCurRow, mCurCol))
                    success = entriesTable.update();
                else
                {
                    entriesTable.grid = this.gridId;
                    entriesTable.row  = mCurRow;
                    entriesTable.col  = mCurCol;
                    success = entriesTable.insert() > 0;
                }
            }
            if (!success)
            {
                android.widget.Toast.makeText(Main.this, getString(org.wheatgenetics.coordinate.R.string.update_failed), android.widget.Toast.LENGTH_SHORT).show();
                return;
            }
        }

        android.view.View view;
        view = this.gridTableLayout.findViewWithTag(org.wheatgenetics.coordinate.utils.Utils.getTag(mCurRow, mCurCol));

        if (view != null)
            if (data.length() != 0)
                setCellState(view, STATE_DONE);
            else
                setCellState(view, STATE_NORMAL);

        boolean endOfCell = false;

        assert this.templateModel != null;
        mCurRow++;
        if (mCurRow > this.templateModel.getRows() || (mCurRow == this.templateModel.getRows() && (isExcludedRow(this.templateModel.getRows()) || isExcludedCell(this.templateModel.getRows(), mCurCol))))
        {
            mCurRow = this.templateModel.getRows();

            mCurCol++;
            if (mCurCol > this.templateModel.getCols() || (mCurCol == this.templateModel.getCols() && (isExcludedCol(this.templateModel.getCols()) || isExcludedCell(mCurRow, this.templateModel.getCols()))))
            {
                mCurCol = this.templateModel.getCols();
                mCurCol = this.templateModel.getRows();

                endOfCell = true;
            }
            else mCurRow = 1;
        }

        if (!endOfCell)
            if (isExcludedRow(mCurRow) || isExcludedCol(mCurCol) || isExcludedCell(mCurRow, mCurCol))
                if (!getNextFreeCell()) endOfCell = true;

        data = getDataEntry(this.gridId, mCurRow, mCurCol);

        if (data == null) data = "";

        this.cellIDEditText.setSelectAllOnFocus(true);
        this.cellIDEditText.setText(data);
        this.cellIDEditText.selectAll();
        this.cellIDEditText.requestFocus();

        view = this.gridTableLayout.findViewWithTag(org.wheatgenetics.coordinate.utils.Utils.getTag(mCurRow, mCurCol));
        if (view != null)
            if (!isExcludedRow(mCurRow) && !isExcludedCol(mCurCol) && !isExcludedCell(mCurRow, mCurCol))
                setCellState(view, STATE_ACTIVE);

        resetCurrentCell();
        this.currentCellView = view;

        if (endOfCell)
        {
            org.wheatgenetics.coordinate.utils.Utils.alert(this, Coordinate.appName, getString(org.wheatgenetics.coordinate.R.string.grid_filled));
            completeSound();
        }
    }

    private void completeSound()
    {
        try
        {
            final int resID = getResources().getIdentifier("plonk", "raw", getPackageName());
            final android.media.MediaPlayer chimePlayer = android.media.MediaPlayer.create(Main.this, resID);
            chimePlayer.start();

            chimePlayer.setOnCompletionListener(new android.media.MediaPlayer.OnCompletionListener()
                {
                    @java.lang.Override
                    public void onCompletion(final android.media.MediaPlayer mp) { mp.release(); }
                });
        }
        catch (final java.lang.Exception e)
        { org.wheatgenetics.coordinate.activities.Main.sendErrorLogMsg(e); }
    }

    private void resetCurrentCell()
    {
        if (this.currentCellView != null)
        {
            int r = -1;
            int c = -1;

            Object obj;

            obj = this.currentCellView.getTag(org.wheatgenetics.coordinate.R.string.cell_col);
            if (obj instanceof java.lang.Integer) c = (java.lang.Integer) obj;

            obj = this.currentCellView.getTag(org.wheatgenetics.coordinate.R.string.cell_row);
            if (obj instanceof java.lang.Integer) r = (java.lang.Integer) obj;

            if (isExcludedRow(r) || isExcludedCol(c) || isExcludedCell(r, c))
                setCellState(this.currentCellView, STATE_INACTIVE);
            else
            {
                java.lang.String data = getDataEntry(this.gridId, r, c);
                if (data == null) data = "";

                if (data.length() == 0)
                    setCellState(this.currentCellView, STATE_NORMAL);
                else
                    setCellState(this.currentCellView, STATE_DONE);
            }
        }
    }

    private boolean deleteTemplate(final org.wheatgenetics.coordinate.model.TemplateModel templateModel)
    {
        assert templateModel != null;
        boolean success;
        org.wheatgenetics.coordinate.database.TemplatesTable templatesTable =
            new org.wheatgenetics.coordinate.database.TemplatesTable(this);
        if (!templatesTable.get(templateModel.getId())) return false;  // database

        if (templatesTable.type == org.wheatgenetics.coordinate.model.TemplateType.SEED.getCode()
        ||  templatesTable.type == org.wheatgenetics.coordinate.model.TemplateType.DNA.getCode ())
        {
            makeToast(getString(org.wheatgenetics.coordinate.R.string.template_not_deleted_default));
            return false;
        }

        final GridsTable              gridsTable = new GridsTable(this);
        final android.database.Cursor cursor     = gridsTable.loadByTemplate(templateModel.getId());
        if (cursor != null)
        {
            while (cursor.moveToNext())
            {
                final GridsTable g = new GridsTable(this);
                if (g.copy(cursor)) this.deleteGrid(g.id);
            }
            cursor.close();
        }

        success = templatesTable.delete(templateModel);  // database
        return success;
    }

    private boolean deleteGrid(final long id)
    {
        boolean success;

        {
            final GridsTable gridsTable = new GridsTable(this);
            success = gridsTable.delete(new GridModel(id));
        }

        {
            final org.wheatgenetics.coordinate.database.EntriesTable entriesTable = new org.wheatgenetics.coordinate.database.EntriesTable(this);
            entriesTable.deleteByGrid(id);
        }

        return success;
    }

    private long createGrid(final long templateId)
    {
        final GridsTable gridsTable = new GridsTable(this);
        gridsTable.templateId = templateId;
        gridsTable.timestamp = java.lang.System.currentTimeMillis();
        assert this.nonNullOptionalFields != null;
        gridsTable.title = this.nonNullOptionalFields.get(0).getValue();
        return gridsTable.insert();
    }

    private void newTemplate(final org.wheatgenetics.coordinate.model.TemplateType templateType)
    throws org.json.JSONException
    {
        assert this.templateModel != null;
        this.templateModel.setType(templateType);
        final org.wheatgenetics.coordinate.database.TemplatesTable templatesTable =
            new org.wheatgenetics.coordinate.database.TemplatesTable(this);
        templatesTable.title = this.templateModel.getTitle();
        templatesTable.type  = templateType.getCode()       ;
        templatesTable.cols  = this.templateModel.getCols() ;
        templatesTable.rows  = this.templateModel.getRows() ;

        templatesTable.excludeCells = org.wheatgenetics.coordinate.utils.Utils.pointListToJson(this.templateModel.getExcludeCells());  // throws org.json.JSONException, model
        templatesTable.excludeCols = org.wheatgenetics.coordinate.utils.Utils.integerListToJson(this.excludeCols);  // model
        templatesTable.excludeRows = org.wheatgenetics.coordinate.utils.Utils.integerListToJson(this.excludeRows);  // model

        templatesTable.options = this.nonNullOptionalFields.toJson();     // throws org.json.JSONException, model

        templatesTable.colNumbering = this.templateModel.getColNumbering() ? 1 : 0;
        templatesTable.rowNumbering = this.templateModel.getRowNumbering() ? 1 : 0;

        templatesTable.stamp = java.lang.System.currentTimeMillis();  // model

        final long templateId = templatesTable.insert();  // database
        if (templateId > 0)
        {
            // deleteTemplate(this.templateModel); //TODO

            this.templateModel.setId(templateId);

            final long gridId = this.createGrid(this.templateModel.getId());
            if (gridId > 0)
            {
                this.gridId = gridId;

                final android.content.SharedPreferences.Editor ed = this.ep.edit();
                ed.putLong("CurrentGrid", gridId);
                ed.apply();

                this.populateTemplate();
                this.showTemplateUI();
            }
            else org.wheatgenetics.coordinate.utils.Utils.alert(this, Coordinate.appName, getString(org.wheatgenetics.coordinate.R.string.create_grid_fail));
        }
        else org.wheatgenetics.coordinate.utils.Utils.alert(this, Coordinate.appName, getString(org.wheatgenetics.coordinate.R.string.create_template_fail));
    }

    private void loadTemplate(final org.wheatgenetics.coordinate.model.TemplateType templateType)
    {
        assert this.templateModel != null;
        this.templateModel.setType(templateType);

        final long gridId = this.createGrid(this.templateModel.getId());
        if (gridId > 0)
        {
            this.gridId = gridId;

            final android.content.SharedPreferences.Editor ed = ep.edit();
            ed.putLong("CurrentGrid", gridId);
            ed.apply();

            this.optionalFieldLayout.setVisibility(android.view.View.VISIBLE);
            this.gridAreaLayout.setVisibility(android.view.View.VISIBLE);

            populateTemplate();
            showTemplateUI();
        }
        else org.wheatgenetics.coordinate.utils.Utils.alert(this, Coordinate.appName, getString(org.wheatgenetics.coordinate.R.string.create_grid_fail));

    }

    private void showTemplateUI()
    {
        assert this.templateModel         != null;
        assert this.templateTitleTextView != null;
        this.templateTitleTextView.setText(this.templateModel.getTitle());

        assert this.mainLayout != null;
        this.mainLayout.setVisibility(android.view.View.VISIBLE);

        final java.lang.String data = this.getDataEntry(this.gridId, 1, 1);
        assert this.cellIDEditText != null;
        this.cellIDEditText.setText(data == null ? "" : data);
    }

    // first non excluded cell
    private boolean getNextFreeCell()
    {
        int c;
        int r = 1;

        assert this.templateModel != null;
        for (c = mCurCol; c <= this.templateModel.getCols(); c++)
        {
            if (isExcludedCol(c)) continue;

            for (r = mCurRow; r <= this.templateModel.getRows(); r++)
            {
                if (isExcludedRow(r)) continue;
                if (!isExcludedCell(r, c)) break;
            }
            break;
        }

        mCurRow = r;
        mCurCol = c;
        return true;
    }

    private void populateTemplate()
    {
        this.mCurRow = 1;
        this.mCurCol = 1;

        this.getNextFreeCell();

        final android.view.LayoutInflater layoutInflater = this.getLayoutInflater();

        this.optionalFieldLayout.removeAllViews();

        final org.wheatgenetics.coordinate.optionalField.CheckedOptionalFields
            checkedOptionalFields = this.makeCheckedOptionalFields();
        boolean first = true;
        for (final org.wheatgenetics.coordinate.optionalField.OptionalField optionalField: checkedOptionalFields)
        {
            final android.view.View view =
                layoutInflater.inflate(org.wheatgenetics.coordinate.R.layout.optional_line, new android.widget.LinearLayout(this), false);
            {
                assert view != null;
                final android.widget.TextView fieldText = (android.widget.TextView) view.findViewById(org.wheatgenetics.coordinate.R.id.fieldText);
                assert fieldText != null;
                fieldText.setText(optionalField.getName());
            }
            {
                final android.widget.TextView valueText = (android.widget.TextView) view.findViewById(org.wheatgenetics.coordinate.R.id.valueText);
                assert valueText != null;
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
        final android.widget.TableRow hrow = (android.widget.TableRow) layoutInflater.inflate(org.wheatgenetics.coordinate.R.layout.table_row, null);
        int chcol = 0;
        assert this.templateModel != null;
        for (int c = 0; c < (this.templateModel.getCols() + 1); c++)
        {
            @android.annotation.SuppressLint("InflateParams")
            final android.widget.LinearLayout cell_top = (android.widget.LinearLayout) layoutInflater.inflate(org.wheatgenetics.coordinate.R.layout.table_cell_top, null);
            final android.widget.TextView cell_txt = (android.widget.TextView) cell_top.findViewById(org.wheatgenetics.coordinate.R.id.dataCell);

            if (c == 0)
                cell_txt.setText("");
            else
            {
                cell_txt.setText("" + (this.templateModel.getColNumbering() ? c : Character.toString((char) ('A' + chcol)))); // Row
                chcol++;
                if (chcol >= 26) chcol = 0;
            }
            hrow.addView(cell_top);
        }
        this.gridTableLayout.addView(hrow);

        // body
        int chrow = 0;
        for (int r = 1; r < (this.templateModel.getRows() + 1); r++)
        {
            @android.annotation.SuppressLint("InflateParams")
            final android.widget.TableRow brow = (android.widget.TableRow) layoutInflater.inflate(org.wheatgenetics.coordinate.R.layout.table_row, null);

            final boolean excludedRow = isExcludedRow(r);

            for (int c = 0; c < (this.templateModel.getCols() + 1); c++)
            {
                final boolean excludedCol = isExcludedCol(c);

                @android.annotation.SuppressLint("InflateParams")
                final android.widget.LinearLayout cell_box = (android.widget.LinearLayout) layoutInflater.inflate(org.wheatgenetics.coordinate.R.layout.table_cell_box, null);
                final android.widget.TextView cell_cnt = (android.widget.TextView) cell_box.findViewById(org.wheatgenetics.coordinate.R.id.dataCell);

                @android.annotation.SuppressLint("InflateParams")
                final android.widget.LinearLayout cell_left = (android.widget.LinearLayout) layoutInflater.inflate(org.wheatgenetics.coordinate.R.layout.table_cell_left, null);
                final android.widget.TextView cell_num = (android.widget.TextView) cell_left.findViewById(org.wheatgenetics.coordinate.R.id.dataCell);

                if (c == 0)
                {
                    cell_num.setText("" + (this.templateModel.getRowNumbering() ? r : Character.toString((char) ('A' + chrow)))); // Row
                    chrow++;
                    if (chrow >= 26) chrow = 0;

                    brow.addView(cell_left);
                }
                else
                {
                    final java.lang.String data = getDataEntry(this.gridId, r, c);
                    this.setCellState(cell_cnt, STATE_NORMAL);

                    if (data != null && data.trim().length() != 0)
                        this.setCellState(cell_cnt, STATE_DONE);

                    if (r == mCurRow && c == mCurCol)
                    {
                        this.setCellState(cell_cnt, STATE_ACTIVE);
                        this.currentCellView = cell_cnt;
                    }

                    if (excludedRow || excludedCol || isExcludedCell(r, c))
                    {
                        this.setCellState(cell_cnt, STATE_INACTIVE);
                        this.saveExcludedCell(r, c);
                    }

                    if (data != null && data.equals("exclude"))
                    {
                        this.setCellState(cell_cnt, STATE_INACTIVE);
                        this.templateModel.getExcludeCells().add(new android.graphics.Point(c, r));
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
            final org.wheatgenetics.coordinate.database.EntriesTable entriesTable = new org.wheatgenetics.coordinate.database.EntriesTable(this);
            entriesTable.value = "exclude";
            if (entriesTable.getByGrid(this.gridId, r, c))
                success = entriesTable.update();
            else
            {
                entriesTable.grid = this.gridId;
                entriesTable.row  = r;
                entriesTable.col  = c;
                success = entriesTable.insert() > 0;
            }
        }
        if (!success)
        {
            android.widget.Toast.makeText(Main.this, getString(org.wheatgenetics.coordinate.R.string.update_failed), android.widget.Toast.LENGTH_SHORT).show();
            return;
        }
    }

    private java.lang.String getDataEntry(final long grid, final int r, final int c)
    {
        java.lang.String data = null;
        {
            final org.wheatgenetics.coordinate.database.EntriesTable entriesTable =
                new org.wheatgenetics.coordinate.database.EntriesTable(this);
            if (entriesTable.getByGrid(grid, r, c)) data = entriesTable.value;
        }
        return data;
    }

    private void setCellState(final android.view.View cell, final int state)
    {
        assert cell != null;
        if (state == STATE_DONE)
            cell.setBackgroundResource(org.wheatgenetics.coordinate.R.drawable.table_cell_done);
        else if (state == STATE_ACTIVE)
            cell.setBackgroundResource(org.wheatgenetics.coordinate.R.drawable.table_cell_active);
        else if (state == STATE_INACTIVE)
            cell.setBackgroundResource(org.wheatgenetics.coordinate.R.drawable.table_cell_inactive);
        else
            cell.setBackgroundResource(org.wheatgenetics.coordinate.R.drawable.table_cell);
    }

    private void cancelExporter()
    {
        if (this.exporter != null)
        {
            this.exporter.cancel(true);
            this.exporter = null;
        }
    }

    private void makeFileDiscoverable(final java.io.File file, final android.content.Context context)
    {
        android.media.MediaScannerConnection.scanFile(context, new java.lang.String[]{file.getPath()}, null, null);
        context.sendBroadcast(new android.content.Intent(
            android.content.Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, android.net.Uri.fromFile(file)));
    }
}