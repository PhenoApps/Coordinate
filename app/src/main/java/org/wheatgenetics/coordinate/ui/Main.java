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
 * org.wheatgenetics.coordinate.model.TemplateModel
 * org.wheatgenetics.coordinate.model.TemplateType
 *
 * org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields
 * org.wheatgenetics.coordinate.optionalField.OptionalField
 *
 * org.wheatgenetics.coordinate.utils.Utils
 *
 * org.wheatgenetics.coordinate.ui.DeleteTemplateAlertDialog
 * org.wheatgenetics.coordinate.ui.DeleteTemplateAlertDialog.Handler
 * org.wheatgenetics.coordinate.ui.ExcludeAlertDialog
 * org.wheatgenetics.coordinate.ui.ExcludeAlertDialog.Handler
 * org.wheatgenetics.coordinate.ui.ExcludeCellsAlertDialog
 * org.wheatgenetics.coordinate.ui.ExcludeCellsAlertDialog.Handler
 * org.wheatgenetics.coordinate.ui.ExcludeRowsOrColsAlertDialog
 * org.wheatgenetics.coordinate.ui.ExcludeRowsOrColsAlertDialog.Handler
 * org.wheatgenetics.coordinate.ui.ExportAlertDialog
 * org.wheatgenetics.coordinate.ui.ExportAlertDialog.Handler
 * org.wheatgenetics.coordinate.ui.ExtraNewTemplateAlertDialog
 * org.wheatgenetics.coordinate.ui.ExtraNewTemplateAlertDialog.Handler
 * org.wheatgenetics.coordinate.ui.ImportAlertDialog
 * org.wheatgenetics.coordinate.ui.ImportAlertDialog.Handler
 * org.wheatgenetics.coordinate.ui.LoadExistingTemplateAlertDialog
 * org.wheatgenetics.coordinate.ui.LoadExistingTemplateAlertDialog.Handler
 * org.wheatgenetics.coordinate.ui.LoadTemplateAlertDialog
 * org.wheatgenetics.coordinate.ui.LoadTemplateAlertDialog.Handler
 * org.wheatgenetics.coordinate.ui.NamingAlertDialog
 * org.wheatgenetics.coordinate.ui.NamingAlertDialog.Handler
 * org.wheatgenetics.coordinate.ui.NewOptionalFieldAlertDialog
 * org.wheatgenetics.coordinate.ui.NewOptionalFieldAlertDialog.Handler
 * org.wheatgenetics.coordinate.ui.NewTemplateAlertDialog
 * org.wheatgenetics.coordinate.ui.NewTemplateAlertDialog.Handler
 * org.wheatgenetics.coordinate.ui.OptionalFieldsAlertDialog
 * org.wheatgenetics.coordinate.ui.OptionalFieldsAlertDialog.Handler
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

        private boolean exportSeed()
        {
            java.lang.String tray_id = "", person = "", date = "";
            {
                final java.lang.String values[] =
                    org.wheatgenetics.coordinate.ui.Main.this.optionalFieldValues(
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
                                if (isExcludedRow(row) || isExcludedCol(col)
                                || isExcludedCell(row, col))
                                    value = "exclude";
                                else
                                    value = org.wheatgenetics.javalib.Utils.replaceIfNull(
                                        org.wheatgenetics.coordinate.ui.Main.this.getValue(
                                            org.wheatgenetics.coordinate.ui.Main.this.gridId,
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
                final java.lang.String values[] =
                    org.wheatgenetics.coordinate.ui.Main.this.optionalFieldValues(
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
                                        if (isExcludedRow(row) || isExcludedCol(col)
                                        || isExcludedCell(row, col))
                                            tissue_id = "BLANK_" + sample_id;
                                        else
                                        {
                                            tissue_id =
                                                org.wheatgenetics.coordinate.ui.Main.this.getValue(
                                                    org.wheatgenetics.coordinate.ui.Main.this.gridId,
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
                    final java.lang.String names[] =
                        org.wheatgenetics.coordinate.ui.Main.this.optionalFieldNames();
                    for (final java.lang.String name: names) csvWriter.write(name);
                }
                csvWriter.endRecord();

                {
                    assert null != this.templateModel;
                    final int
                        cols = this.templateModel.getCols(), rows = this.templateModel.getRows();

                    for (int col = 1; col <= cols; col++)
                    {
                        for (int row = 1; row <= rows; row++)
                        {
                            {
                                java.lang.String value;
                                if (isExcludedRow(row) || isExcludedCol(col)
                                || isExcludedCell(row, col))
                                    value = "exclude";
                                else
                                    value = org.wheatgenetics.javalib.Utils.makeEmptyIfNull(
                                        org.wheatgenetics.coordinate.ui.Main.this.getValue(
                                            org.wheatgenetics.coordinate.ui.Main.this.gridId,
                                            row, col));
                                csvWriter.write(value);
                            }
                            csvWriter.write(col);
                            csvWriter.write(row);

                            {
                                final java.lang.String values[] =
                                    org.wheatgenetics.coordinate.ui.Main.this.optionalFieldValues();
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
                org.wheatgenetics.coordinate.ui.Main.this.mLastExportGridId =  // TODO: Make into
                    org.wheatgenetics.coordinate.ui.Main.this.gridId;          // TODO:  Main method.
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
                                            Main.this.entriesTable().delete(
                                            org.wheatgenetics.coordinate.ui.Main.this.gridId))
                                                org.wheatgenetics.coordinate.ui.
                                                    Main.this.populateTemplate();
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
    private android.support.v4.widget.DrawerLayout       drawerLayout         ;
    private android.support.v7.app.ActionBarDrawerToggle actionBarDrawerToggle;

    private android.widget.LinearLayout mainLayout,
        gridAreaLayout, gridTableLayout, optionalFieldLayout;
    private android.widget.EditText cellIDEditText        ;
    private android.widget.TextView templateTitleTextView ;
    private android.view.View       currentCellView = null;
    // endregion

    private long                                             gridId             =    0             ;
    private java.lang.String                                 gridTitle          =   ""             ;
    private int                                              mCurRow            =    1, mCurCol = 1;

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

    // region Template
    private org.wheatgenetics.coordinate.model.TemplateModel templateModel =
        org.wheatgenetics.coordinate.model.TemplateModel.makeInitial();
    private org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields nonNullOptionalFields;
    // endregion

    private org.wheatgenetics.androidlibrary.Dir          exportDir               ;
    private org.wheatgenetics.coordinate.ui.Main.Exporter exporter          = null;
    private long                                          mLastExportGridId =   -1;

    // region AlertDialog Fields
    private org.wheatgenetics.coordinate.ui.NewOptionalFieldAlertDialog
        newOptionalFieldAlertDialog = null;
    private org.wheatgenetics.coordinate.ui.ExcludeAlertDialog excludeAlertDialog = null;
    private org.wheatgenetics.coordinate.ui.ExcludeRowsOrColsAlertDialog
        excludeRowsAlertDialog = null, excludeColsAlertDialog = null;
    private org.wheatgenetics.coordinate.ui.ExcludeCellsAlertDialog excludeCellsAlertDialog = null;
    private org.wheatgenetics.coordinate.ui.NewTemplateAlertDialog  newTemplateAlertDialog  = null;
    private org.wheatgenetics.coordinate.ui.OptionalFieldsAlertDialog
        optionalFieldsAlertDialog = null;
    private org.wheatgenetics.coordinate.ui.NamingAlertDialog namingAlertDialog = null;
    private org.wheatgenetics.coordinate.ui.TemplateOptionsAlertDialog
        templateOptionsAlertDialog = null;
    private org.wheatgenetics.coordinate.ui.ExtraNewTemplateAlertDialog
        extraNewTemplateAlertDialog = null;
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

    // region Class Method
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
    { assert null != this.nonNullOptionalFields; return this.nonNullOptionalFields.values(); }

    private java.lang.String[] optionalFieldValues(final java.lang.String names[])
    { assert null != this.nonNullOptionalFields; return this.nonNullOptionalFields.values(names); }

    private java.lang.String[] optionalFieldNames()
    { assert null != this.nonNullOptionalFields; return this.nonNullOptionalFields.names(); }

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

    // region confirm() AlertDialog Methods
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

        this.nonNullOptionalFields =
            org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields.makeInitial();

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

        this.mainLayout = (android.widget.LinearLayout) this.findViewById(org.wheatgenetics.coordinate.R.id.mainLayout);
        this.gridAreaLayout = (android.widget.LinearLayout) this.findViewById(org.wheatgenetics.coordinate.R.id.gridArea);
        this.optionalFieldLayout = (android.widget.LinearLayout) this.findViewById(org.wheatgenetics.coordinate.R.id.optionalLayout);

        this.gridTableLayout = (android.widget.TableLayout) this.findViewById(org.wheatgenetics.coordinate.R.id.dataTable);

        this.templateTitleTextView = (android.widget.TextView)
            this.findViewById(org.wheatgenetics.coordinate.R.id.templateText);

        this.cellIDEditText = (android.widget.EditText) this.findViewById(org.wheatgenetics.coordinate.R.id.dataEdit);
        assert null != this.cellIDEditText; this.cellIDEditText.setImeActionLabel(
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
            assert null != defaultTemplateModels; if (defaultTemplateModels.size() > 0)
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
        {
            // Load grid:
            final org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel =
                this.gridsTable().get(this.sharedPreferences.getCurrentGrid());
            if (null != joinedGridModel)
            {
                assert null != this.templateModel; this.templateModel.assign(
                    /* title => */ joinedGridModel.getTemplateTitle(),
                    /* rows  => */ joinedGridModel.getRows()         ,
                    /* cols  => */ joinedGridModel.getCols()         );
                this.templateModel.setType(joinedGridModel.getType());
                this.templateModel.clearExcludes();

                this.gridId    = joinedGridModel.getId()   ;
                this.gridTitle = joinedGridModel.getTitle();

                {
                    final org.wheatgenetics.coordinate.model.TemplateModel templateModel =
                        this.templatesTable().get(joinedGridModel.getTemplateId());
                    if (null != templateModel)
                    {
                        this.importOptionalFields(templateModel.getOptionalFields());

                        this.templateModel.setRowNumbering(templateModel.getRowNumbering());
                        this.templateModel.setColNumbering(templateModel.getColNumbering());
                    }
                }

                this.populateTemplate();
            }
            else this.alert(org.wheatgenetics.coordinate.R.string.import_grid_failed);
        }
        else this.loadExistingTemplateOrCreateNewTemplate();

        this.showTemplateUI();

        int versionCode;
        try
        {
            final android.content.pm.PackageInfo packageInfo = this.getPackageManager().
                getPackageInfo(    // throws android.content.pm.PackageManager.NameNotFoundException
                    /* packageName => */ this.getPackageName(),
                    /* flags       => */ 0                    );
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
    //                 createGrid()
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
    //         exclude()
    //             excludeRows()
    //             excludeCols()
    //             excludeCells()
    //         inputNaming()
    //         loadTemplate()
    //             tempLoad()
    // loadExistingTemplate()
    //     loadSeedTrayTemplate()
    //         loadExistingTemplate(TemplateType)
    //             createGrid()
    //     loadTemplate()
    //         tempLoad()
    //              loadExistingTemplate(TemplateType)
    //                 createGrid()
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
            new org.wheatgenetics.coordinate.ui.NewOptionalFieldAlertDialog(this,
                new org.wheatgenetics.coordinate.ui.NewOptionalFieldAlertDialog.Handler()
                {
                    @java.lang.Override
                    public void retry(final int errorMsgResId, final java.lang.String oldName,
                    final java.lang.String newDefault)
                    {
                        org.wheatgenetics.coordinate.ui.Main.this.showLongToast(errorMsgResId);
                        org.wheatgenetics.coordinate.ui.Main.this.addNewOptionalField(
                            oldName, newDefault);
                    }

                    @java.lang.Override
                    public void addOptionalField(final java.lang.String newName,
                    final java.lang.String newDefault)
                    {
                        org.wheatgenetics.coordinate.ui.Main.this.addOptionalField(
                            newName, newDefault);
                    }
                });
        this.newOptionalFieldAlertDialog.show(oldName, oldDefault);
    }

    private void excludeRows()
    {
        if (null == this.excludeRowsAlertDialog) this.excludeRowsAlertDialog =
            new org.wheatgenetics.coordinate.ui.ExcludeRowsOrColsAlertDialog(this,
                org.wheatgenetics.coordinate.R.string.row,
                new org.wheatgenetics.coordinate.ui.ExcludeRowsOrColsAlertDialog.Handler()
                {
                    @java.lang.Override
                    public void process(final boolean checkedItems[])
                    {
                        int i = 1;
                        assert null != checkedItems                                           ;
                        assert null != org.wheatgenetics.coordinate.ui.Main.this.templateModel;
                        for (final boolean checkedItem: checkedItems) if (checkedItem)
                            // TODO: Are they cleared first?
                            org.wheatgenetics.coordinate.ui.Main.this.templateModel.addExcludeRow(
                                i++);
                    }
                });
        assert null != this.templateModel;
        this.excludeRowsAlertDialog.show(
            this.templateModel.rowItems(this.getString(org.wheatgenetics.coordinate.R.string.row)),
            this.templateModel.rowCheckedItems()                                                 );
    }

    private void excludeCols()
    {
        if (null == this.excludeColsAlertDialog) this.excludeColsAlertDialog =
            new org.wheatgenetics.coordinate.ui.ExcludeRowsOrColsAlertDialog(this,
                org.wheatgenetics.coordinate.R.string.col,
                new org.wheatgenetics.coordinate.ui.ExcludeRowsOrColsAlertDialog.Handler()
                {
                    @java.lang.Override
                    public void process(final boolean[] checkedItems)
                    {
                        int i = 1;
                        assert null != checkedItems                                           ;
                        assert null != org.wheatgenetics.coordinate.ui.Main.this.templateModel;
                        for (final boolean checkedItem: checkedItems) if (checkedItem)
                            // TODO: Are they cleared first?
                            org.wheatgenetics.coordinate.ui.Main.this.templateModel.addExcludeCol(
                                i++);
                    }
                });
        assert null != this.templateModel;
        this.excludeColsAlertDialog.show(
            this.templateModel.colItems(this.getString(org.wheatgenetics.coordinate.R.string.col)),
            this.templateModel.colCheckedItems()                                                 );
    }

    private void excludeCells()
    {
        if (null == this.excludeCellsAlertDialog) this.excludeCellsAlertDialog =
            new org.wheatgenetics.coordinate.ui.ExcludeCellsAlertDialog(this,
                new org.wheatgenetics.coordinate.ui.ExcludeCellsAlertDialog.Handler()
                {
                    @java.lang.Override
                    public void excludeCells(final int amount)
                    {
                        if (amount > 0)
                        {
                            assert null != org.wheatgenetics.coordinate.ui.Main.this.templateModel;
                            org.wheatgenetics.coordinate.ui.Main.this.templateModel.makeRandomCells(
                                amount);
                        }
                        else org.wheatgenetics.coordinate.ui.Main.this.excludeCells();
                    }
                });
        this.excludeCellsAlertDialog.show();
    }

    private long createGrid(final long templateId)
    {
        final org.wheatgenetics.coordinate.database.GridsTable gridsTable = this.gridsTable();
        assert null != gridsTable;
        gridsTable.templateId = templateId                          ;
        gridsTable.timestamp  = java.lang.System.currentTimeMillis();
        assert null != this.nonNullOptionalFields;
        gridsTable.title = this.nonNullOptionalFields.getFirstValue();
        return gridsTable.insert();
    }

    private void createNewTemplate(
    final org.wheatgenetics.coordinate.model.TemplateType templateType)
    {
        assert null != this.templateModel; this.templateModel.setType(templateType);
        final long templateId = this.templatesTable().insert(this.templateModel);
        if (templateId > 0)
        {
            // deleteTemplate(this.templateModel); // TODO

            this.templateModel.setId(templateId);

            final long gridId = this.createGrid(this.templateModel.getId());
            if (gridId > 0)
            {
                this.gridId = gridId;  // TODO: make setGridId() with sharedPreferences side effect?

                assert null != this.sharedPreferences;
                this.sharedPreferences.setCurrentGrid(gridId);

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
        assert null != this.templateModel; this.templateModel.setType(templateType);

        final long gridId = this.createGrid(this.templateModel.getId());
        if (gridId > 0)
        {
            this.gridId = gridId;                                                    // TODO: Ditto.

            assert null != org.wheatgenetics.coordinate.ui.Main.this.sharedPreferences;
            org.wheatgenetics.coordinate.ui.Main.this.sharedPreferences.setCurrentGrid(gridId);

            this.optionalFieldLayout.setVisibility(android.view.View.VISIBLE);
            this.gridAreaLayout.setVisibility     (android.view.View.VISIBLE);

            this.populateTemplate();
        }
        else this.alert(org.wheatgenetics.coordinate.R.string.create_grid_fail);
    }

    private void addNewOptionalFields()
    {
        if (null == this.optionalFieldsAlertDialog) this.optionalFieldsAlertDialog =
            new org.wheatgenetics.coordinate.ui.OptionalFieldsAlertDialog(this,
                new org.wheatgenetics.coordinate.ui.OptionalFieldsAlertDialog.Handler()
                {
                    @java.lang.Override
                    public void checkOptionalField(final int i, final boolean b)
                    { org.wheatgenetics.coordinate.ui.Main.this.setOptionalFieldChecked(i, b); }

                    @java.lang.Override
                    public void addNewOptionalField()
                    { org.wheatgenetics.coordinate.ui.Main.this.addNewOptionalField("", ""); }
                });
        assert null != this.nonNullOptionalFields;
        this.optionalFieldsAlertDialog.show(this.nonNullOptionalFields.names(),
            this.nonNullOptionalFields.checks());
    }

    private void exclude()
    {
        if (null == this.excludeAlertDialog) this.excludeAlertDialog =
            new org.wheatgenetics.coordinate.ui.ExcludeAlertDialog(this,
                new org.wheatgenetics.coordinate.ui.ExcludeAlertDialog.Handler()
                {
                    @java.lang.Override
                    public void excludeRows()
                    { org.wheatgenetics.coordinate.ui.Main.this.excludeRows(); }

                    @java.lang.Override
                    public void excludeCols()
                    { org.wheatgenetics.coordinate.ui.Main.this.excludeCols(); }

                    @java.lang.Override
                    public void excludeCells()
                    { org.wheatgenetics.coordinate.ui.Main.this.excludeCells(); }
                });
        this.excludeAlertDialog.show();
    }

    private void inputNaming()
    {
        if (null == this.namingAlertDialog) this.namingAlertDialog =
            new org.wheatgenetics.coordinate.ui.NamingAlertDialog(this,
                new org.wheatgenetics.coordinate.ui.NamingAlertDialog.Handler()
                {
                    @java.lang.Override
                    public void setNumbering(final boolean rowNumbering, final boolean colNumbering)
                    {
                        assert null != org.wheatgenetics.coordinate.ui.Main.this.templateModel;
                        org.wheatgenetics.coordinate.ui.Main.this.templateModel.setRowNumbering(
                            rowNumbering);
                        org.wheatgenetics.coordinate.ui.Main.this.templateModel.setColNumbering(
                            colNumbering);
                    }
                });

        assert null != this.templateModel; this.namingAlertDialog.show(
            this.templateModel.getRowNumbering(), this.templateModel.getColNumbering());
    }

    private void tempLoad(final int mode) throws org.json.JSONException
    {
        if (org.wheatgenetics.coordinate.ui.Main.MODE_DNA == mode)
            this.loadExistingTemplate(org.wheatgenetics.coordinate.model.TemplateType.DNA);
        else
            if (org.wheatgenetics.coordinate.ui.Main.MODE_SAVED == mode)
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
    { assert null != this.templateModel; return this.templateModel.isExcludedRow(r); }

    private boolean isExcludedCol(final int c)
    { assert null != this.templateModel; return this.templateModel.isExcludedCol(c); }

    private java.lang.String getValue(final long grid, final int row, final int col)
    {
        final org.wheatgenetics.coordinate.model.EntryModel entryModel =
            this.entriesTable().get(grid, row, col);
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

    private boolean isExcludedCell(final int r, final int c)
    { assert null != this.templateModel; return this.templateModel.isExcludedCell(c, r); }

    private void saveExcludedCell(final int row, final int col)
    {
        boolean success;                                                               // TODO: DRY!
        {
            final org.wheatgenetics.coordinate.database.EntriesTable entriesTable =
                this.entriesTable();
            assert null != entriesTable; entriesTable.value = "exclude";
            if (entriesTable.exists(this.gridId, row, col))
                success = entriesTable.update();                         // TODO: Update only edata!
            else
            {
                entriesTable.grid = this.gridId;
                entriesTable.row  = row          ;
                entriesTable.col  = col          ;
                success = entriesTable.insert() > 0;
            }
        }
        if (!success) this.showShortToast(org.wheatgenetics.coordinate.R.string.update_failed);
    }

    private void showTemplateUI()
    {
        assert null != this.templateModel; assert null != this.templateTitleTextView;
        this.templateTitleTextView.setText(this.templateModel.getTitle());

        assert null != this.mainLayout; this.mainLayout.setVisibility(android.view.View.VISIBLE);

        assert null != this.cellIDEditText;
        this.cellIDEditText.setText(org.wheatgenetics.javalib.Utils.makeEmptyIfNull(
            this.getValue(this.gridId, 1, 1)));
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
                    public void createNewTemplate()
                    { org.wheatgenetics.coordinate.ui.Main.this.createNewTemplate(); }
                });
        this.templateOptionsAlertDialog.show();
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
                    org.wheatgenetics.coordinate.ui.Main.MODE_DNA,
                    this.templatesTable().get(templateType)      );
            else
                // reset options?
                this.loadTemplate(                                  // throws org.json.JSONException
                    org.wheatgenetics.coordinate.ui.Main.MODE_SAVED, this.templateModel);
    }

    private boolean deleteGrid(final long gridId)
    {
        this.entriesTable().delete(gridId);
        return this.gridsTable().delete(new org.wheatgenetics.coordinate.model.GridModel(gridId));
    }

    private void inputTemplateNewExtra()
    {
        if (null == this.extraNewTemplateAlertDialog) this.extraNewTemplateAlertDialog =
            new org.wheatgenetics.coordinate.ui.ExtraNewTemplateAlertDialog(this,
                new org.wheatgenetics.coordinate.ui.ExtraNewTemplateAlertDialog.Handler()
                {
                    @java.lang.Override
                    public void addOptionalFields()
                    { org.wheatgenetics.coordinate.ui.Main.this.addNewOptionalFields(); }

                    @java.lang.Override
                    public void addExcludes()
                    { org.wheatgenetics.coordinate.ui.Main.this.exclude(); }

                    @java.lang.Override
                    public void addNaming()
                    { org.wheatgenetics.coordinate.ui.Main.this.inputNaming(); }

                    @java.lang.Override
                    public void handleNext()
                    {
                        try
                        {
                            org.wheatgenetics.coordinate.ui.Main.this.loadTemplate(  // throws org.json.JSONException
                                org.wheatgenetics.coordinate.ui.Main.MODE_DEFAULT      ,
                                org.wheatgenetics.coordinate.ui.Main.this.templateModel);
                        }
                        catch (final org.json.JSONException e) {}
                    }
                });
        this.excludeAlertDialog.show();
    }

    private void loadSeedTrayTemplate(
    final org.wheatgenetics.coordinate.model.TemplateModel templateModel)
    {
        if (null == this.loadSeedTrayTemplateAlertDialog) this.loadSeedTrayTemplateAlertDialog =
            new org.wheatgenetics.coordinate.ui.LoadTemplateAlertDialog(this,
                new org.wheatgenetics.coordinate.ui.LoadTemplateAlertDialog.Handler()
                {
                    @java.lang.Override
                    public void process(final java.lang.String values[])
                    {
                        int i = 0;
                        for (final org.wheatgenetics.coordinate.optionalField.OptionalField
                        optionalField:
                        org.wheatgenetics.coordinate.ui.Main.this.makeCheckedOptionalFields())
                        {
                            final java.lang.String value = values[i];
                            if (0 == i && 0 == value.length())
                            {
                                org.wheatgenetics.coordinate.ui.Main.this.showLongToast(
                                    optionalField.getHint()                        ,
                                    org.wheatgenetics.coordinate.R.string.not_empty);
                                org.wheatgenetics.coordinate.ui.Main.this.loadSeedTrayTemplate(
                                    templateModel);
                                return;
                            }

                            optionalField.setValue(value);
                            if (optionalField.namesAreEqual("Person")
                            ||  optionalField.namesAreEqual("Name"  ))
                            {
                                assert null !=
                                    org.wheatgenetics.coordinate.ui.Main.this.sharedPreferences;
                                org.wheatgenetics.coordinate.ui.Main.this.
                                    sharedPreferences.setPerson(optionalField.getValue());
                            }
                            i++;
                        }
                        org.wheatgenetics.coordinate.ui.Main.this.loadExistingTemplate(
                            org.wheatgenetics.coordinate.model.TemplateType.SEED);
                    }
                });
        assert null != templateModel; this.loadSeedTrayTemplateAlertDialog.show(
            templateModel.getTitle(), this.makeCheckedOptionalFields());
    }

    private void loadTemplate(final int mode,
    final org.wheatgenetics.coordinate.model.TemplateModel templateModel)
    throws org.json.JSONException                     // TODO: Merge this method with the one above.
    {
        assert null != this.nonNullOptionalFields; if (this.nonNullOptionalFields.isEmpty())
            this.tempLoad(mode);                                    // throws org.json.JSONException
        else
        {
            if (null == this.loadTemplateAlertDialog) this.loadTemplateAlertDialog =
                new org.wheatgenetics.coordinate.ui.LoadTemplateAlertDialog(this,
                    new org.wheatgenetics.coordinate.ui.LoadTemplateAlertDialog.Handler()
                    {
                        @java.lang.Override
                        public void process(final java.lang.String[] values)
                        {
                            {
                                int i = 0;
                                for (final org.wheatgenetics.coordinate.optionalField.OptionalField
                                optionalField: org.wheatgenetics.coordinate.ui.
                                Main.this.makeCheckedOptionalFields())
                                {
                                    final java.lang.String value = values[i];
                                    if (org.wheatgenetics.coordinate.ui.Main.MODE_DNA == mode)
                                        if (0 == i && 0 == value.length())
                                        {
                                            org.wheatgenetics.coordinate.ui.Main.this.showLongToast(
                                                optionalField.getHint()                        ,
                                                org.wheatgenetics.coordinate.R.string.not_empty);
                                            try
                                            {
                                                org.wheatgenetics.coordinate.ui.
                                                    Main.this.loadTemplate(org.wheatgenetics.
                                                        coordinate.ui.Main.MODE_DNA,
                                                    templateModel); // throws org.json.JSONException
                                            }
                                            catch (final org.json.JSONException e) {}
                                            return;
                                        }

                                    optionalField.setValue(value);

                                    if (optionalField.namesAreEqual("Person")
                                    ||  optionalField.namesAreEqual("Name"  ))
                                    {
                                        assert null != org.wheatgenetics.coordinate.ui.
                                            Main.this.sharedPreferences;
                                        org.wheatgenetics.coordinate.ui.Main.this.
                                            sharedPreferences.setPerson(optionalField.getValue());
                                    }
                                    i++;
                                }
                            }
                            try
                            {
                                org.wheatgenetics.coordinate.ui.Main.this.tempLoad(  // throws org.-
                                    mode);                                           //  json.JSON-
                            }                                                        //  Exception
                            catch (final org.json.JSONException e) {}
                        }
                    });
            assert null != templateModel; this.loadTemplateAlertDialog.show(
                templateModel.getTitle(), this.makeCheckedOptionalFields());
        }
    }

    private boolean deleteTemplate(
    final org.wheatgenetics.coordinate.model.TemplateModel searchTemplateModel)
    {
        assert null != searchTemplateModel; final long templateId = searchTemplateModel.getId();

        final org.wheatgenetics.coordinate.database.TemplatesTable templatesTable =
            this.templatesTable();
        assert null != templatesTable;
        if (templatesTable.exists(templateId))
        {
            {
                org.wheatgenetics.coordinate.model.TemplateType templateType;
                {
                    final org.wheatgenetics.coordinate.model.TemplateModel tableTemplateModel =
                        templatesTable.get(templateId);
                    assert null != tableTemplateModel; templateType = tableTemplateModel.getType();
                }
                if (org.wheatgenetics.coordinate.model.TemplateType.SEED == templateType
                || org.wheatgenetics.coordinate.model.TemplateType.DNA   == templateType)
                {
                    this.showShortToast(org.wheatgenetics.coordinate.R.string.template_not_deleted_default);  // TODO: Move to TemplatesTable.
                    return false;
                }
            }
            this.gridsTable().delete(templateId);
            return templatesTable.delete(searchTemplateModel);
        }
        else return false;
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
        this.mCurRow = 1; this.mCurCol = 1;

        this.getNextFreeCell();

        final android.view.LayoutInflater layoutInflater = this.getLayoutInflater();

        assert null != this.optionalFieldLayout; this.optionalFieldLayout.removeAllViews();

        final org.wheatgenetics.coordinate.optionalField.CheckedOptionalFields
            checkedOptionalFields = this.makeCheckedOptionalFields();
        boolean first = true;
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
                    cell_cnt.setTag(org.wheatgenetics.coordinate.ui.Main.getTag(r, c));
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
        assert null != this.templateModel; assert null != this.exportDir;
        return this.exportDir.createNewFile(this.templateModel.getTitle());
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

    private void deleteGrid()
    {
        if (0 != this.gridId) this.confirm(
            /* message     => */ org.wheatgenetics.coordinate.R.string.delete_grid_warning,
            /* yesRunnable => */ new java.lang.Runnable()
            {
                @java.lang.Override
                public void run()
                {
                    if (org.wheatgenetics.coordinate.ui.Main.this.deleteGrid(
                    org.wheatgenetics.coordinate.ui.Main.this.gridId))
                    {
                        org.wheatgenetics.coordinate.ui.Main.this.showLongToast(
                            org.wheatgenetics.coordinate.R.string.grid_deleted);
                        org.wheatgenetics.coordinate.ui.Main.this.gridId = 0;
                        org.wheatgenetics.coordinate.ui.Main.this.optionalFieldLayout.setVisibility(
                            android.view.View.INVISIBLE);
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

    private void createNewTemplate()
    {
        if (null == this.newTemplateAlertDialog) this.newTemplateAlertDialog =
            new org.wheatgenetics.coordinate.ui.NewTemplateAlertDialog(this,
                new org.wheatgenetics.coordinate.ui.NewTemplateAlertDialog.Handler()
                {
                    @java.lang.Override
                    public void createNewTemplate(final java.lang.String name,
                    final java.lang.String rows, final java.lang.String cols)
                    {
                        assert null != org.wheatgenetics.coordinate.ui.Main.this.templateModel;
                        org.wheatgenetics.coordinate.ui.Main.this.templateModel.assign(
                            /* title => */ name, /* rows => */ rows, /* cols => */ cols);

                        if (0 == name.length())
                        {
                            org.wheatgenetics.coordinate.ui.Main.this.showLongToast(
                                org.wheatgenetics.coordinate.R.string.template_no_name);
                            org.wheatgenetics.coordinate.ui.Main.this.createNewTemplate();
                            return;
                        }

                        if (0 == rows.length() ||
                        !org.wheatgenetics.coordinate.ui.Main.this.templateModel.rowsIsSpecified())
                        {
                            org.wheatgenetics.coordinate.ui.Main.this.showLongToast(
                                org.wheatgenetics.coordinate.R.string.no_rows);
                            org.wheatgenetics.coordinate.ui.Main.this.createNewTemplate();
                            return;
                        }

                        if (0 == cols.length() ||
                        !org.wheatgenetics.coordinate.ui.Main.this.templateModel.colsIsSpecified())
                        {
                            org.wheatgenetics.coordinate.ui.Main.this.showLongToast(
                                org.wheatgenetics.coordinate.R.string.no_cols);
                            org.wheatgenetics.coordinate.ui.Main.this.createNewTemplate();
                            return;
                        }

                        org.wheatgenetics.coordinate.ui.Main.this.inputTemplateNewExtra();
                    }
                });

        assert null != this.templateModel; this.templateModel.clearExcludes();
        this.nonNullOptionalFields =
            org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields.makeNew();

        this.newTemplateAlertDialog.show(
            this.templateModel.getRowsAsString(), this.templateModel.getColsAsString());
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
                                try
                                {
                                    org.wheatgenetics.coordinate.ui.Main.this.loadTemplate(
                                        org.wheatgenetics.coordinate.ui.Main.MODE_DNA,
                                        templateModel);             // throws org.json.JSONException
                                }
                                catch (final org.json.JSONException e) {}
                                break;

                            default:
                                try
                                {
                                    org.wheatgenetics.coordinate.ui.Main.this.loadTemplate(
                                        org.wheatgenetics.coordinate.ui.Main.MODE_SAVED,
                                        templateModel);             // throws org.json.JSONException
                                }
                                catch (final org.json.JSONException e) {}
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
                                            Main.this.deleteTemplate(templateModel))
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
                        this.gridsTable();
                    assert null != gridsTable;
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

            if (null == this.importAlertDialog) this.importAlertDialog =
                new org.wheatgenetics.coordinate.ui.ImportAlertDialog(this,
                    new org.wheatgenetics.coordinate.ui.ImportAlertDialog.Handler()
                    {
                        @Override
                        public void importGrid(final int which)
                        {
                            if (which < gridIds.length)
                            {
                                final long gridId = gridIds[which];

                                assert null !=
                                    org.wheatgenetics.coordinate.ui.Main.this.sharedPreferences;
                                org.wheatgenetics.coordinate.ui.
                                    Main.this.sharedPreferences.setCurrentGrid(gridId);

                                final org.wheatgenetics.coordinate.model.JoinedGridModel
                                    joinedGridModel = org.wheatgenetics.coordinate.ui.
                                        Main.this.gridsTable().get(gridId);
                                if (null != joinedGridModel)                                 // TODO: DRY!
                                {
                                    org.wheatgenetics.coordinate.ui.Main.this.templateModel.assign(
                                        /* title => */ joinedGridModel.getTemplateTitle(),
                                        /* rows  => */ joinedGridModel.getRows()         ,
                                        /* cols  => */ joinedGridModel.getCols()         );
                                    org.wheatgenetics.coordinate.ui.Main.this.gridId =
                                        joinedGridModel.getId();
                                    org.wheatgenetics.coordinate.ui.Main.this.gridTitle =
                                        joinedGridModel.getTitle();
                                    org.wheatgenetics.coordinate.ui.Main.this.templateModel.setType(
                                        joinedGridModel.getType());

                                    {
                                        final org.wheatgenetics.coordinate.model.TemplateModel
                                            templateModel = org.wheatgenetics.coordinate.ui.Main.
                                                this.templatesTable().get(
                                                    joinedGridModel.getTemplateId());
                                        if (null != templateModel)
                                        {
                                            org.wheatgenetics.coordinate.ui.
                                                Main.this.importOptionalFields(
                                                    templateModel.getOptionalFields());

                                            org.wheatgenetics.coordinate.ui.Main.this.templateModel.
                                                setRowNumbering(templateModel.getRowNumbering());
                                            org.wheatgenetics.coordinate.ui.Main.this.templateModel.
                                                setColNumbering(templateModel.getColNumbering());
                                        }
                                    }

                                    org.wheatgenetics.coordinate.ui.
                                        Main.this.templateModel.clearExcludes();  // model

                                    org.wheatgenetics.coordinate.ui.Main.this.populateTemplate();
                                }
                                else
                                    org.wheatgenetics.coordinate.ui.Main.this.alert(
                                        org.wheatgenetics.coordinate.R.string.import_grid_failed);
                            }
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
        assert null != this.nonNullOptionalFields;
        this.exportAlertDialog.show(this.nonNullOptionalFields.getDatedFirstValue());
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

    // region Selector Drawer Method
    private boolean selectNavigationItem(final android.view.MenuItem menuItem)
    {
        assert null != menuItem; switch (menuItem.getItemId())
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

        assert null != this.drawerLayout; this.drawerLayout.closeDrawers();
        return true;
    }
    // endregion
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
                assert null != entriesTable; entriesTable.value = value;
                if (entriesTable.exists(this.gridId, mCurRow, mCurCol))
                    success = entriesTable.update();                     // TODO: Update only edata!
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
                return true;
            }
        }

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
            if (this.isExcludedRow(mCurRow) || this.isExcludedCol(mCurCol) || this.isExcludedCell(mCurRow, mCurCol))
                if (!this.getNextFreeCell()) endOfCell = true;

        value = org.wheatgenetics.javalib.Utils.makeEmptyIfNull(
            this.getValue(this.gridId, mCurRow, mCurCol));

        assert null != this.cellIDEditText;
        this.cellIDEditText.setSelectAllOnFocus(true);
        this.cellIDEditText.setText(value);
        this.cellIDEditText.selectAll();
        this.cellIDEditText.requestFocus();

        view = this.gridTableLayout.findViewWithTag(
            org.wheatgenetics.coordinate.ui.Main.getTag(mCurRow, mCurCol));
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

        return true;
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
                state = org.wheatgenetics.coordinate.ui.Main.STATE_INACTIVE;
            else
            {
                final java.lang.String value = org.wheatgenetics.javalib.Utils.makeEmptyIfNull(
                    this.getValue(this.gridId, r, c));
                state = 0 == value.length() ?
                    org.wheatgenetics.coordinate.ui.Main.STATE_NORMAL :
                    org.wheatgenetics.coordinate.ui.Main.STATE_DONE   ;
            }
            this.setCellState(this.currentCellView, state);
        }
    }
}